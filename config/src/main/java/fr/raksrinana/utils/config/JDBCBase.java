package fr.raksrinana.utils.config;

import org.jdeferred.Promise;
import org.jdeferred.impl.DefaultDeferredManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Nonnull;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public abstract class JDBCBase implements AutoCloseable{
	private static final Logger LOGGER = LoggerFactory.getLogger(JDBCBase.class);
	protected final DefaultDeferredManager dm;
	private final String NAME;
	private final ArrayList<Promise<?, ?, ?>> promises;
	protected Connection connection;
	private final Object lock = new Object();
	
	/**
	 * Constructor.
	 *
	 * @param name Name of the database.
	 */
	protected JDBCBase(@Nonnull String name){
		dm = new DefaultDeferredManager();
		this.NAME = name;
		this.promises = new ArrayList<>();
	}
	
	/**
	 * Initialize the database connection.
	 */
	protected abstract void login();
	
	/**
	 * Sends a query.
	 *
	 * @param query  The SQL query.
	 * @param parser The parser of the result.
	 *
	 * @return The promise.
	 */
	@Nonnull
	public Promise<Optional<ResultSet>, Throwable, Void> sendQueryRequest(@Nonnull String query, @Nonnull ResultsParser<?> parser){
		Promise<Optional<ResultSet>, Throwable, Void> promise = dm.when(() -> sendQueryRequest(query, true)).done(result -> result.ifPresent(parser::parse)).fail(event -> LOGGER.warn("SQL query on {} failed!", NAME, event));
		promises.add(promise);
		return promise;
	}
	
	/**
	 * Sends a query.
	 *
	 * @param query The SQL query.
	 *
	 * @return The promise.
	 */
	@Nonnull
	public Promise<Optional<ResultSet>, Throwable, Void> sendQueryRequest(@Nonnull String query){
		Promise<Optional<ResultSet>, Throwable, Void> promise = dm.when(() -> sendQueryRequest(query, true)).fail(event -> LOGGER.warn("SQL query on {} failed!", NAME, event));
		promises.add(promise);
		return promise;
	}
	
	/**
	 * Sends an update.
	 *
	 * @param query The SQL query.
	 *
	 * @return The promise.
	 */
	@Nonnull
	public Promise<Integer, Throwable, Void> sendUpdateRequest(@Nonnull String query){
		Promise<Integer, Throwable, Void> promise = dm.when(() -> sendUpdateRequest(query, true)).fail(event -> LOGGER.warn("SQL update on {} failed!", NAME, event));
		promises.add(promise);
		return promise;
	}
	
	/**
	 * Perform a commit.
	 *
	 * @throws SQLException If the request couldn't be made.
	 */
	public void commit() throws SQLException{
		this.connection.commit();
	}
	
	/**
	 * Perform a rollback.
	 *
	 * @throws SQLException If the request couldn't be made.
	 */
	public void rollback() throws SQLException{
		this.connection.rollback();
	}
	
	/**
	 * Close the connection.
	 */
	public void close(){
		for(Promise<?, ?, ?> promise : promises){
			try{
				promise.waitSafely();
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		dm.shutdownNow();
		if(connection != null){
			try{
				this.connection.close();
			}
			catch(SQLException ignored){
			}
		}
		LOGGER.info("SQL connection closed");
	}
	
	/**
	 * Sends a query.
	 *
	 * @param query The query.
	 * @param retry If we retry or not.
	 *
	 * @return The result.
	 *
	 * @throws SQLException If the request couldn't be made.
	 */
	@Nonnull
	private Optional<ResultSet> sendQueryRequest(@Nonnull String query, boolean retry) throws SQLException{
		ResultSet result;
		synchronized(lock){
			if(this.connection == null){
				return Optional.empty();
			}
			LOGGER.debug("Sending SQL request to {} (retry: {}): {}", NAME, retry, query);
			try{
				Statement stmt = this.connection.createStatement();
				result = stmt.executeQuery(query.replace(";", ""));
			}
			catch(SQLTimeoutException e){
				if(!retry){
					throw e;
				}
				login();
				return sendQueryRequest(query, false);
			}
		}
		return Optional.ofNullable(result);
	}
	
	/**
	 * Sends an update.
	 *
	 * @param query The query.
	 * @param retry If we retry or not.
	 *
	 * @return The number of lines modified.
	 *
	 * @throws SQLException If the request couldn't be made.
	 */
	private int sendUpdateRequest(@Nonnull String query, boolean retry) throws SQLException{
		if(this.connection == null){
			return 0;
		}
		int result = 0;
		synchronized(lock){
			LOGGER.debug("Sending SQL update to {} (retry: {}): {}", NAME, retry, query);
			try{
				for(String req : query.split(";")){
					Statement stmt = this.connection.createStatement();
					result += stmt.executeUpdate(req);
				}
			}
			catch(SQLException e){
				if(!retry){
					throw e;
				}
				login();
				return sendUpdateRequest(query, false);
			}
		}
		return result;
	}
	
	/**
	 * Sends a prepared update.
	 *
	 * @param request The prepared request.
	 * @param filler  The filler of the request.
	 *
	 * @return The promise.
	 */
	@Nonnull
	public Promise<Integer, Throwable, Void> sendPreparedUpdateRequest(@Nonnull String request, @Nonnull PreparedStatementFiller filler){
		Promise<Integer, Throwable, Void> promise = dm.when(() -> sendPreparedUpdateRequest(request, filler, true)).fail(event -> LOGGER.warn("SQL update request on {} failed!", NAME, event));
		promises.add(promise);
		return promise;
	}
	
	/**
	 * Sends a prepared update.
	 *
	 * @param request The prepared request.
	 * @param filler  The filler of the request.
	 * @param retry   If we retry or not.
	 *
	 * @return The number of lines modified.
	 *
	 * @throws SQLException If the request couldn't be made.
	 */
	private int sendPreparedUpdateRequest(@Nonnull String request, @Nonnull PreparedStatementFiller filler, boolean retry) throws SQLException{
		if(this.connection == null){
			return 0;
		}
		int result = 0;
		synchronized(lock){
			LOGGER.debug("Sending SQL update to {} (retry: {}): {}\nWith filler {}", NAME, retry, request, filler);
			try{
				PreparedStatement preparedStatement = connection.prepareStatement(request.replace(";", ""));
				filler.fill(preparedStatement);
				result += preparedStatement.executeUpdate();
			}
			catch(SQLException e){
				if(!retry){
					throw e;
				}
				login();
				return sendPreparedUpdateRequest(request, filler, false);
			}
		}
		return result;
	}
}

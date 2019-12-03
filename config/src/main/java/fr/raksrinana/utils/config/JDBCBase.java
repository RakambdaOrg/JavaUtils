package fr.raksrinana.utils.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Nonnull;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public abstract class JDBCBase implements AutoCloseable{
	private static final Logger LOGGER = LoggerFactory.getLogger(JDBCBase.class);
	private final String NAME;
	private final ArrayList<CompletableFuture<?>> futures;
	protected Connection connection;
	private final Object lock = new Object();
	
	/**
	 * Constructor.
	 *
	 * @param name Name of the database.
	 */
	protected JDBCBase(@Nonnull String name){
		this.NAME = name;
		this.futures = new ArrayList<>();
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
	public <T> CompletableFuture<Optional<List<T>>> sendQueryRequest(@Nonnull String query, @Nonnull ResultsParser<T> parser){
		final var future = CompletableFuture.supplyAsync(() -> {
			try{
				return sendQueryRequest(query, true);
			}
			catch(SQLException e){
				throw new CompletionException(e);
			}
		}).thenApply(result1 -> result1.map(parser::parse));
		futures.add(future);
		return future;
	}
	
	/**
	 * Sends a query.
	 *
	 * @param query The SQL query.
	 *
	 * @return The promise.
	 */
	@Nonnull
	public CompletableFuture<Optional<ResultSet>> sendQueryRequest(@Nonnull String query){
		final var future = CompletableFuture.supplyAsync(() -> {
			try{
				return sendQueryRequest(query, true);
			}
			catch(SQLException e){
				throw new CompletionException(e);
			}
		});
		futures.add(future);
		return future;
	}
	
	/**
	 * Sends an update.
	 *
	 * @param query The SQL query.
	 *
	 * @return The promise.
	 */
	@Nonnull
	public CompletableFuture<Integer> sendUpdateRequest(@Nonnull String query){
		final var future = CompletableFuture.supplyAsync(() -> {
			try{
				return sendUpdateRequest(query, true);
			}
			catch(SQLException e){
				throw new CompletionException(e);
			}
		});
		futures.add(future);
		return future;
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
		for(CompletableFuture<?> future : futures){
			future.cancel(true);
		}
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
	public CompletableFuture<Integer> sendPreparedUpdateRequest(@Nonnull String request, @Nonnull PreparedStatementFiller filler){
		final var future = CompletableFuture.supplyAsync(() -> {
			try{
				return sendPreparedUpdateRequest(request, filler, true);
			}
			catch(SQLException e){
				throw new CompletionException(e);
			}
		});
		futures.add(future);
		return future;
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

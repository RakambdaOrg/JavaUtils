package fr.raksrinana.utils.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Slf4j
public abstract class JDBCBase implements AutoCloseable{
	private final String NAME;
	private final ArrayList<CompletableFuture<?>> futures;
	private final Object lock = new Object();
	
	/**
	 * Constructor.
	 *
	 * @param name Name of the database.
	 */
	protected JDBCBase(@NonNull String name){
		this.NAME = name;
		this.futures = new ArrayList<>();
	}
	
	/**
	 * Sends a query.
	 *
	 * @param query  The SQL query.
	 * @param parser The parser of the result.
	 *
	 * @return The promise.
	 */
	@NonNull
	public <T> CompletableFuture<List<T>> sendCompletableQueryRequest(@NonNull String query, @NonNull ResultsParser<T> parser){
		final var future = CompletableFuture.supplyAsync(() -> {
			try{
				return sendQueryRequest(query, parser);
			}
			catch(SQLException e){
				throw new CompletionException(e);
			}
		});
		futures.add(future);
		return future;
	}
	
	/**
	 * Sends a query.
	 *
	 * @param query  The query.
	 * @param <T>    The returned type.
	 * @param parser The parser of the result.
	 *
	 * @return The result.
	 *
	 * @throws SQLException If the request couldn't be made.
	 */
	public <T> List<T> sendQueryRequest(@NonNull String query, @NonNull ResultsParser<T> parser) throws SQLException{
		ResultSet result;
		try(var connection = getDatasource().getConnection()){
			log.debug("Sending SQL request to {}: {}", NAME, query);
			try(var stmt = connection.createStatement()){
				result = stmt.executeQuery(query.replace(";", ""));
				return parser.parse(result);
			}
		}
	}
	
	/**
	 * Initialize the database connection.
	 */
	protected abstract HikariDataSource getDatasource();
	
	/**
	 * Sends an update.
	 *
	 * @param query The SQL query.
	 *
	 * @return The promise.
	 */
	@NonNull
	public CompletableFuture<Integer> sendCompletableUpdateRequest(@NonNull String query){
		final var future = CompletableFuture.supplyAsync(() -> {
			try{
				return sendUpdateRequest(query);
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
	 * @param query The query.
	 *
	 * @return The number of lines modified.
	 *
	 * @throws SQLException If the request couldn't be made.
	 */
	public int sendUpdateRequest(@NonNull String query) throws SQLException{
		int result = 0;
		try(var connection = this.getDatasource().getConnection()){
			log.debug("Sending SQL update to {}: {}", NAME, query);
			for(String req : query.split(";")){
				try(var stmt = connection.createStatement()){
					result += stmt.executeUpdate(req);
				}
			}
		}
		return result;
	}
	
	/**
	 * Close the connection.
	 */
	public void close(){
		for(CompletableFuture<?> future : futures){
			future.cancel(true);
		}
		getDatasource().close();
		log.info("SQL connection closed");
	}
	
	/**
	 * Sends a prepared update.
	 *
	 * @param request The prepared request.
	 * @param filler  The filler of the request.
	 *
	 * @return The promise.
	 */
	@NonNull
	public CompletableFuture<Integer> sendCompletablePreparedUpdateRequest(@NonNull String request, @NonNull PreparedStatementFiller filler){
		final var future = CompletableFuture.supplyAsync(() -> {
			try{
				return sendPreparedUpdateRequest(request, filler);
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
	 *
	 * @return The number of lines modified.
	 *
	 * @throws SQLException If the request couldn't be made.
	 */
	public int sendPreparedUpdateRequest(@NonNull String request, @NonNull PreparedStatementFiller filler) throws SQLException{
		int result = 0;
		try(var connection = this.getDatasource().getConnection()){
			log.debug("Sending SQL update to {}: {}\nWith filler {}", NAME, request, filler);
			try(var preparedStatement = connection.prepareStatement(request.replace(";", ""))){
				filler.fill(preparedStatement);
				result += preparedStatement.executeUpdate();
			}
		}
		return result;
	}
}

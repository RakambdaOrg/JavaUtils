package fr.raksrinana.utils.config;

import lombok.NonNull;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ResultsParser<T>{
	private final Function<ResultSet, List<T>> parser;
	private final List<Consumer<List<T>>> parsedCallbacks;
	
	/**
	 * Constructor.
	 *
	 * @param parser The parser.
	 */
	public ResultsParser(@NonNull Function<ResultSet, List<T>> parser){
		this.parsedCallbacks = new ArrayList<>();
		this.parser = parser;
	}
	
	/**
	 * Add a callback that will be notified when parsed values are available.
	 *
	 * @param callback The callback to add.
	 *
	 * @return This object.
	 */
	public ResultsParser<T> addCallback(@NonNull Consumer<List<T>> callback){
		this.parsedCallbacks.add(callback);
		return this;
	}
	
	/**
	 * Parse a result set.
	 *
	 * @param resultSet The result set to parse.
	 *
	 * @return A list of addCallback values.
	 */
	public List<T> parse(@NonNull ResultSet resultSet){
		List<T> parsed = parser.apply(resultSet);
		parsedCallbacks.forEach(callback -> callback.accept(parsed));
		return parsed;
	}
}

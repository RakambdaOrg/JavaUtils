package fr.raksrinana.utils.config;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class PreparedStatementFiller{
	private static final Logger LOGGER = LoggerFactory.getLogger(PreparedStatementFiller.class);
	private HashMap<Integer, SQLValue> values;
	
	/**
	 * Constructor.
	 *
	 * @param sqlValues The values to fill.
	 */
	public PreparedStatementFiller(@NonNull SQLValue... sqlValues){
		this.values = new HashMap<>();
		for(int i = 0; i < sqlValues.length; i++){
			this.values.put(i + 1, sqlValues[i]);
		}
	}
	
	/**
	 * Called to fill a prepared statement.
	 *
	 * @param statement The statement to fill.
	 */
	public void fill(@NonNull PreparedStatement statement){
		for(int i : values.keySet()){
			try{
				this.values.get(i).fill(i, statement);
			}
			catch(SQLException e){
				LOGGER.warn("Error filling prepared statement with parameter: {} -> {}", i, this.values.get(i));
			}
		}
	}
	
	@Override
	public String toString(){
		return this.values.toString();
	}
}

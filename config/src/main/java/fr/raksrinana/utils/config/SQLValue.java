package fr.raksrinana.utils.config;

import lombok.NonNull;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLValue{
	private final Object value;
	private final Type type;
	
	/**
	 * A type that the value can take.
	 */
	public enum Type{
		INT, STRING, LONG, DOUBLE
	}
	
	/**
	 * Constructor.
	 *
	 * @param type  The type of the value.
	 * @param value The value.
	 */
	public SQLValue(@NonNull Type type, @NonNull Object value){
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Fill a prepared statement.
	 *
	 * @param index     The index of the value.
	 * @param statement The statement to fill.
	 *
	 * @throws SQLException If the statement couldn't be filled.
	 */
	public void fill(int index, @NonNull PreparedStatement statement) throws SQLException{
		switch(this.type){
			case INT -> statement.setInt(index, (Integer) this.value);
			case LONG -> statement.setLong(index, (Long) this.value);
			case DOUBLE -> statement.setDouble(index, (Double) this.value);
			default -> statement.setString(index, this.value.toString());
		}
	}
	
	@Override
	public String toString(){
		return this.type.toString() + " -> " + this.value.toString();
	}
}

package fr.mrcraftcod.utils.config;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 12/09/2016.
 *
 * @author Thomas Couchoud
 * @since 2016-09-12
 */
@SuppressWarnings("WeakerAccess")
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
	public SQLValue(Type type, Object value){
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
	public void fill(int index, PreparedStatement statement) throws SQLException{
		switch(this.type){
			case INT:
				statement.setInt(index, (Integer) this.value);
				break;
			case LONG:
				statement.setLong(index, (Long) this.value);
				break;
			case DOUBLE:
				statement.setDouble(index, (Double) this.value);
				break;
			case STRING:
			default:
				statement.setString(index, this.value.toString());
				break;
		}
	}
	
	@Override
	public String toString(){
		return this.type.toString() + " -> " + this.value.toString();
	}
}

package fr.raksrinana.utils.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteManager extends JDBCBase{
	private static final Logger LOGGER = LoggerFactory.getLogger(SQLiteManager.class);
	private Path databaseURL;
	
	public SQLiteManager(Path databaseURL) throws ClassNotFoundException{
		super("SQLITE/" + databaseURL);
		Class.forName("org.sqlite.JDBC");
		databaseURL.getParent().toFile().mkdirs();
		this.databaseURL = databaseURL;
		login();
		LOGGER.info("Initializing SQLite connection...");
	}
	
	protected void login(){
		try{
			this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.databaseURL.toAbsolutePath());
		}
		catch(SQLException e){
			LOGGER.warn("Error connecting to SQLite database!", e);
		}
	}
}

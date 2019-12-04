package fr.raksrinana.utils.config;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class SQLiteManager extends JDBCBase{
	private Path databaseURL;
	
	public SQLiteManager(@NonNull Path databaseURL) throws ClassNotFoundException{
		super("SQLITE/" + databaseURL);
		Class.forName("org.sqlite.JDBC");
		databaseURL.getParent().toFile().mkdirs();
		this.databaseURL = databaseURL;
		login();
		log.info("Initializing SQLite connection...");
	}
	
	protected void login(){
		try{
			this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.databaseURL.toAbsolutePath());
		}
		catch(SQLException e){
			log.warn("Error connecting to SQLite database!", e);
		}
	}
}

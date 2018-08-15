package fr.mrcraftcod.utils.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MYSQLManager extends JDBCBase
{
	private static final Logger LOGGER = LoggerFactory.getLogger(MYSQLManager.class);
	
	private String databaseURL;
	private int port;
	private String databaseName;
	private String user;
	private String password;
	
	public MYSQLManager(String databaseURL, int port, String databaseName, String user, String password){
		super("MYSQL/" + databaseURL + "/" + databaseName + ":" + port + ":" + user);
		this.databaseURL = databaseURL;
		this.port = port;
		this.databaseName = databaseName;
		this.user = user;
		this.password = password;
		login();
		LOGGER.info("Initializing MySQL connection...");
	}
	
	@Override
	protected void login(){
		try{
			this.connection = DriverManager.getConnection("jdbc:mysql://" + this.databaseURL + ":" + this.port + "/" + this.databaseName + "?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=UTC", this.user, this.password);
		}
		catch(SQLException e){
			LOGGER.warn("Error connecting to MySQL database!", e);
		}
	}
}

package fr.raksrinana.utils.config;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class MYSQLManager extends JDBCBase{
	private String databaseURL;
	private int port;
	private String databaseName;
	private String user;
	private String password;
	
	public MYSQLManager(@NonNull String databaseURL, int port, @NonNull String databaseName, @NonNull String user, @NonNull String password){
		super("MYSQL/" + databaseURL + "/" + databaseName + ":" + port + ":" + user);
		this.databaseURL = databaseURL;
		this.port = port;
		this.databaseName = databaseName;
		this.user = user;
		this.password = password;
		login();
		log.info("Initializing MySQL connection...");
	}
	
	@Override
	protected void login(){
		try{
			this.connection = DriverManager.getConnection("jdbc:mysql://" + this.databaseURL + ":" + this.port + "/" + this.databaseName + "?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=UTC", this.user, this.password);
		}
		catch(SQLException e){
			log.warn("Error connecting to MySQL database!", e);
		}
	}
}

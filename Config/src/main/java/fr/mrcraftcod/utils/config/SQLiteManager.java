package fr.mrcraftcod.utils.config;

import fr.mrcraftcod.utils.base.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteManager extends JDBCBase
{
	private static final Logger LOGGER = LoggerFactory.getLogger(SQLiteManager.class);
	private File databaseURL;
	
	public SQLiteManager(File databaseURL) throws ClassNotFoundException{
		super("SQLITE/" + databaseURL);
		Class.forName("org.sqlite.JDBC");
		FileUtils.createDirectories(databaseURL);
		this.databaseURL = databaseURL;
		login();
		LOGGER.info("Initializing SQLite connection...");
	}

	protected void login()
	{
		try
		{
			this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.databaseURL.getAbsolutePath());
		}
		catch(SQLException e)
		{
			LOGGER.warn("Error connecting to SQLite database!", e);
		}
	}
}

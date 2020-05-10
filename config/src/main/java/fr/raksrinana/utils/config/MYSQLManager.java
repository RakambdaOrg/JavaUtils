package fr.raksrinana.utils.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
public class MYSQLManager extends JDBCBase{
	private final String databaseURL;
	private final int port;
	private final String databaseName;
	private final String user;
	private final String password;
	private HikariDataSource datasource;
	private Consumer<HikariConfig> configurator;
	
	public MYSQLManager(@NonNull String databaseURL, int port, @NonNull String databaseName, @NonNull String user, @NonNull String password){
		super("MYSQL/" + databaseURL + "/" + databaseName + ":" + port + ":" + user);
		this.databaseURL = databaseURL;
		this.port = port;
		this.databaseName = databaseName;
		this.user = user;
		this.password = password;
	}
	
	@Override
	protected HikariDataSource getDatasource(){
		if(Objects.isNull(datasource)){
			final var config = new HikariConfig();
			config.setDriverClassName("com.mysql.jdbc.Driver");
			config.setJdbcUrl("jdbc:mysql://" + this.databaseURL + ":" + this.port + "/" + this.databaseName);
			config.setUsername(this.user);
			config.setPassword(this.password);
			config.addDataSourceProperty("useUnicode", "true");
			config.addDataSourceProperty("useLegacyDatetimeCode", "false");
			config.addDataSourceProperty("serverTimezone", "UTC");
			config.setAutoCommit(true);
			Optional.ofNullable(configurator).ifPresent(conf -> conf.accept(config));
			datasource = new HikariDataSource(config);
		}
		return datasource;
	}
	
	public void setConfigurator(Consumer<HikariConfig> configurator){
		this.configurator = configurator;
	}
}

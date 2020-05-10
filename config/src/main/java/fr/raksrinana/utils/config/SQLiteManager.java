package fr.raksrinana.utils.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
public class SQLiteManager extends JDBCBase{
	private final Path databaseURL;
	private HikariDataSource datasource;
	private Consumer<HikariConfig> configurator;
	
	public SQLiteManager(@NonNull Path databaseURL) throws IOException{
		super("SQLITE/" + databaseURL);
		Files.createDirectories(databaseURL.getParent());
		this.databaseURL = databaseURL;
	}
	
	@Override
	protected HikariDataSource getDatasource(){
		if(Objects.isNull(datasource)){
			final var config = new HikariConfig();
			config.setDriverClassName("org.sqlite.JDBC");
			config.setJdbcUrl("jdbc:sqlite:" + this.databaseURL.toAbsolutePath());
			config.setMaximumPoolSize(1);
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

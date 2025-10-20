package ru.polovinko.state_machine.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {
    private static final String URL_FULL_SCHEMA = "jdbc:postgresql://%s:%s/%s?currentSchema=%s";
    private static final String SCHEMA_PARAMETER = "?currentSchema=";

    @Value("${custom-datasource.schema}")
    private String schema;
    @Value("${custom-datasource.init-sql}")
    private String initSql;
    @Value("${custom-datasource.driver-class-name}")
    private String driver;
    @Value("${custom-datasource.env-url}")
    private String envUrl;
    @Value("${custom-datasource.database-host}")
    private String databaseHost;
    @Value("${custom-datasource.database-port}")
    private String databasePort;
    @Value("${custom-datasource.database-name}")
    private String databaseName;
    @Value("${custom-datasource.username}")
    private String username;
    @Value("${custom-datasource.password}")
    private String password;

    @Value("${custom-datasource.connection-timeout:null}")
    private Long connectionTimeout;
    @Value("${custom-datasource.idle-timeout:null}")
    private Long idleTimeout;
    @Value("${custom-datasource.maximum-pool-size}")
    private Integer maximumPoolSize;
    @Value("${custom-datasource.minimum-idle-size}")
    private Integer minIdle;
    @Value("${custom-datasource.max-lifetime:null}")
    private Long maxLifetime;

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        String url;
        if (StringUtils.isEmpty(envUrl)) {
            url = String.format(URL_FULL_SCHEMA, databaseHost, databasePort, databaseName, schema);
        } else if (!envUrl.toLowerCase().contains(SCHEMA_PARAMETER.toLowerCase())) {
            url = envUrl + SCHEMA_PARAMETER + schema;
        } else {
            url = envUrl;
        }
        var hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);

        hikariConfig.setMaximumPoolSize(maximumPoolSize);
        hikariConfig.setMinimumIdle(minIdle);
        if (connectionTimeout != null) {
            hikariConfig.setConnectionTimeout(connectionTimeout);
        }
        if (idleTimeout != null) {
            hikariConfig.setIdleTimeout(idleTimeout);
        }
        if (maxLifetime != null) {
            hikariConfig.setMaxLifetime(maxLifetime);
        }

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        if (!StringUtils.isEmpty(initSql)) {
            Resource initSchema = new ClassPathResource(initSql);
            DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema);
            DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        }
        return dataSource;
    }
}

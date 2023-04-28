package wtf.triplapeeck.oatmeal.util;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

import java.sql.SQLException;

public class DatabaseUtil {
    private JdbcPooledConnectionSource connectionSource;
    private ConfigParser.DatabaseConfiguration databaseConfiguration;
    public DatabaseUtil() throws SQLException {
        this.databaseConfiguration = ConfigParser.getDatabaseConfiguration();
        this.connectionSource = new JdbcPooledConnectionSource("jdbc:mariadb://" + this.databaseConfiguration.getAddress() + ":" +this.databaseConfiguration.getPort() + "/" + this.databaseConfiguration.getDatabase() + "?user="+ this.databaseConfiguration.getUsername() + "&password=" + this.databaseConfiguration.getPassword(), this.databaseConfiguration.getType());
        this.connectionSource.setMaxConnectionsFree(this.databaseConfiguration.getMaxConnections());
    }

    public JdbcPooledConnectionSource getConncectionSource() {
        return connectionSource;
    }

    public ConfigParser.DatabaseConfiguration getDatabaseConfiguration() {
        return databaseConfiguration;
    }
}

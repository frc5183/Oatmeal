package wtf.triplapeeck.oatmeal.util;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.jdbc.db.MariaDbDatabaseType;
import wtf.triplapeeck.oatmeal.Config;
import wtf.triplapeeck.oatmeal.errors.ArgumentError;

public class ConfigParser {
    private static final Config config = Config.getConfig();
    public static String getToken(String[] args) throws ArgumentError {
        if (config.token != null && !config.token.equals("")) {
            return config.token;
        }
        Config.saveConfig();
        if (args.length > 0) {
            if (!(args[0].equals(""))) {
                return args[0];
            }
        }
        if (System.getenv("TOKEN") != null) {
            return System.getenv("TOKEN");
        }

        throw new ArgumentError("Missing Token");
    }

    public static DatabaseConfiguration getDatabaseConfiguration() throws ArgumentError {
        if (
                (System.getenv("DATABASE_PORT") != null && config.port == 0)
                || (System.getenv("DATABASE_USERNAME") != null && config.username.equals(""))
                || (System.getenv("DATABASE_PASSWORD") != null && config.password.equals(""))
                || (System.getenv("DATABASE_NAME") != null && config.database.equals(""))
                || (System.getenv("DATABASE_MAXCONNECTIONS") != null && config.maxConnections == 0)
        ) throw new ArgumentError("Missing one or more database configurations.");
        String address=config.address;
        String username=config.username;
        String password=config.password;
        String database=config.database;
        int port = config.port;
        int maxConnections = config.maxConnections;
        if (address.equals("")) address=System.getenv("DATABASE_ADDRESS");
        if (password.equals("")) username=System.getenv("DATABASE_USERNAME");
        if (username.equals("")) password=System.getenv("DATABASE_PASSWORD");
        if (database.equals("")) database=System.getenv("DATABASE_NAME");
        try {
            if (port == 0) port = Integer.parseInt(System.getenv("DATABASE_PORT"));
            if (maxConnections == 0) maxConnections = Integer.parseInt(System.getenv("DATABASE_MAXCONNECTIONS"));
        } catch (NumberFormatException e) {
            throw new ArgumentError("Database port is not an integer.");
        }
        if (port > 65535 || port < 1) throw new ArgumentError("Database port is not a valid port number. (1-65535)");
        if (maxConnections < 1) throw new ArgumentError("Database max connections must be greater than 0.");
        return new DatabaseConfiguration(new MariaDbDatabaseType(), address, port, username, password, database, maxConnections);
    }

    public static class DatabaseConfiguration {
        public DatabaseType type;
        public String address;
        public int port;
        public String username;
        public String password;
        public String database;
        public int maxConnections;

        public DatabaseConfiguration(DatabaseType type, String address, int port, String username, String password, String database, int maxConnections) {
            this.type = type;
            this.address = address;
            this.port = port;
            this.username = username;
            this.password = password;
            this.database = database;
            this.maxConnections = maxConnections;
        }

        public DatabaseType getType() {
            return type;
        }

        public void setType(DatabaseType type) {
            this.type = type;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDatabase() {
            return database;
        }

        public void setDatabase(String database) {
            this.database = database;
        }

        public int getMaxConnections() {
            return maxConnections;
        }

        public void setMaxConnections(int maxConnections) {
            this.maxConnections = maxConnections;
        }
    }
}

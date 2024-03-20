package wtf.triplapeeck.oatmeal.util;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.jdbc.db.MariaDbDatabaseType;
import wtf.triplapeeck.oatmeal.Config;
import wtf.triplapeeck.oatmeal.errors.ArgumentError;

/**
 * This class is used to parse the config file and command line arguments.
 * This class allows multiple configuration options for the bot
 * This class is used to get the token, path, and database configuration
 * This class can get configuration from command line args, environment variables, or the Config file
 */
public class ConfigParser {
    private static final Config config = Config.getConfig();

    /**
     * Gets the token from the command line arguments, environment variables, or the config file
     * @param args The command line arguments
     * @return The toke
     * @throws ArgumentError
     */
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

    /**
     * @deprecated This method is deprecated and will be removed in a future version.
     * As JSON data configuration is used, this method is now obsolete.
     * @return The path to the database
     */
    @Deprecated
    public static String getPath() {
        return config.path;
    }

    /**
     * Gets the database configuration from the command line arguments, environment variables, or the config file
     * @return The database configuration
     * @throws ArgumentError
     */
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

    /**
     * This class is used to store the database configuration
     * This class is used to store the database type, address, port, username, password, database name, and max connections
     */
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

        /**
         * This method is used to get the database type
         * @return The database type
         */
        public DatabaseType getType() {
            return type;
        }

        /**
         * This method should not be used. Database type configuration should be done by the user in the config, not at runtime
         * @deprecated This method is deprecated and will be removed in a future version.
         * @param type The database type
         */
        @Deprecated
        public void setType(DatabaseType type) {
            this.type = type;
        }

        /**
         * This method is used to get the database address
         * @return The database address
         */
        public String getAddress() {
            return address;
        }

        /**
         * This method should not be used. Database address configuration should be done by the user in the config, not at runtime
         * @deprecated This method is deprecated and will be removed in a future version.
         * @param address The database address
         */
        @Deprecated
        public void setAddress(String address) {
            this.address = address;
        }

        /**
         * This method is used to get the database port
         * @return The database port
         */
        public int getPort() {
            return port;
        }

        /**
         * This method should not be used. Database port configuration should be done by the user in the config, not at runtime
         * @deprecated This method is deprecated and will be removed in a future version.
         * @param port The database port
         */
        @Deprecated
        public void setPort(int port) {
            this.port = port;
        }

        /**
         * This method is used to get the database username
         * @return The database username
         */
        public String getUsername() {
            return username;
        }

        /**
         * This method should not be used. Database username configuration should be done by the user in the config, not at runtime
         * @deprecated This method is deprecated and will be removed in a future version.
         * @param username The database username
         */
        @Deprecated
        public void setUsername(String username) {
            this.username = username;
        }

        /**
         * This method is used to get the database password
         * @return The database password
         */
        public String getPassword() {
            return password;
        }

        /**
         * This method should not be used. Database password configuration should be done by the user in the config, not at runtime
         * @deprecated This method is deprecated and will be removed in a future version.
         * @param password The database password
         */
        @Deprecated
        public void setPassword(String password) {
            this.password = password;
        }

        /**
         * This method is used to get the database name
         * @return The database name
         */
        public String getDatabase() {
            return database;
        }

        /**
         * This method should not be used. Database name configuration should be done by the user in the config, not at runtime
         * @deprecated This method is deprecated and will be removed in a future version.
         * @param database The database name
         */
        @Deprecated
        public void setDatabase(String database) {
            this.database = database;
        }

        /**
         * This method is used to get the maximum number of connections to the database
         * @return The maximum number of connections to the database
         */
        public int getMaxConnections() {
            return maxConnections;
        }

        /**
         * This method should not be used. Database max connections configuration should be done by the user in the config, not at runtime
         * @deprecated This method is deprecated and will be removed in a future version.
         * @param maxConnections The maximum number of connections to the database
         */
        @Deprecated
        public void setMaxConnections(int maxConnections) {
            this.maxConnections = maxConnections;
        }
    }
}

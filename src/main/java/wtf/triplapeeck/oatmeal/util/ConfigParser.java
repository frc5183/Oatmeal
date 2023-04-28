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

    public DatabaseConfiguration getDatabaseConfiguration() throws ArgumentError {
        if (
                (System.getenv("TOKEN") != null && config.address.equals(""))
                || (System.getenv("TOKEN") != null && config.port == 0)
                || (System.getenv("TOKEN") != null && config.username.equals(""))
                || (System.getenv("TOKEN") != null && config.password.equals(""))
                || (System.getenv("TOKEN") != null && config.database.equals(""))
        ) throw new ArgumentError("Missing one or more database configurations.");
        String address=config.address;
        String username=config.username;
        String password=config.password;
        String database=config.database;
        int port = config.port;
        if (address.equals("")) address=System.getenv("DATABASE_ADDRESS");
        if (password.equals("")) username=System.getenv("DATABASE_USERNAME");
        if (username.equals("")) password=System.getenv("DATABASE_PASSWORD");
        if (database.equals("")) database=System.getenv("DATABASE_NAME");
        if (port == 0) port = Integer.parseInt(System.getenv("DATABASE_PORT"));
        if (Integer.parseInt(System.getenv("DATABASE_PORT")) > 65535 || Integer.parseInt(System.getenv("DATABASE_PORT")) < 1) throw new ArgumentError("DATABASE_PORT is not a valid port number. (1-65535)");
        try {
            return new DatabaseConfiguration(new MariaDbDatabaseType(), address, port, username, password, database);
        } catch (NumberFormatException e) {
            throw new ArgumentError("DATABASE_PORT is not an integer.");
        }
    }

    public static class DatabaseConfiguration {
        public DatabaseType type;
        public String address;
        public int port;
        public String username;
        public String password;
        public String database;

        public DatabaseConfiguration(DatabaseType type, String address, int port, String username, String password, String database) {
            this.type = type;
            this.address = address;
            this.port = port;
            this.username = username;
            this.password = password;
            this.database = database;
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
    }
}

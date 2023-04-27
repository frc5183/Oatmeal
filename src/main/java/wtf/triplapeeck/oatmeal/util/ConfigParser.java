package wtf.triplapeeck.oatmeal.util;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.jdbc.db.MariaDbDatabaseType;
import wtf.triplapeeck.oatmeal.Config;
import wtf.triplapeeck.oatmeal.errors.ArgumentException;

import java.util.Objects;

public class ConfigParser {
    private static final Config config = Config.getConfig();
    public static String getToken(String[] args) throws ArgumentException {
        if (config.token!=null) {
            return config.token;
        }
        Config.saveConfig();
        if (!(args[0].equals(""))) {
            return args[0];
        }

        if (!(System.getenv("TOKEN").equals(""))) {
            return System.getenv("TOKEN");
        }

        throw new ArgumentException("Missing Token");
    }

    public DatabaseConfiguration getDatabaseConfiguration() throws ArgumentException {
        if (
                (System.getenv("DATABASE_ADDRESS").equals("") && Objects.equals(config.address, ""))
                || (System.getenv("DATABASE_PORT").equals("") && config.port == 0)
                || (System.getenv("DATABASE_USERNAME").equals("") && Objects.equals(config.username, "usr"))
                || (System.getenv("DATABASE_PASSWORD").equals("") && Objects.equals(config.password, "pwd"))
                || (System.getenv("DATABASE_NAME").equals("") && Objects.equals(config.database, "db"))
        ) throw new ArgumentException("Missing one or more database configurations. ");
        String address=config.address;
        String username=config.username;
        String password=config.password;
        String database=config.database;
        int port = config.port;
        if (Objects.equals(address, "")) {
            address=System.getenv("DATABASE_ADDRESS");
        }
        if (Objects.equals(username, "usr")) {
            username=System.getenv("DATABASE_USERNAME");
        }
        if (Objects.equals(password, "pwd")) {
            password = System.getenv("DATABASE_PASSWORD");
        }
        if (Objects.equals(database, "db")) {
            database=System.getenv("DATABASE_NAME");
        }
        if (port==0) {
            port=Integer.parseInt(System.getenv("DATABASE_PORT"));
        }
        if (Integer.parseInt(System.getenv("DATABASE_PORT")) > 65535 || Integer.parseInt(System.getenv("DATABASE_PORT")) < 1) throw new ArgumentException("DATABASE_PORT is not a valid port number. (1-65535)");
        try {
            return new DatabaseConfiguration(new MariaDbDatabaseType(), address, port, username, password, database);
        } catch (NumberFormatException e) {
            throw new ArgumentException("DATABASE_PORT is not an integer.");
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

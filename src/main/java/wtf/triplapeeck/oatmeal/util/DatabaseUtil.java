package wtf.triplapeeck.oatmeal.util;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import wtf.triplapeeck.oatmeal.entities.ChannelEntity;
import wtf.triplapeeck.oatmeal.entities.GuildEntity;
import wtf.triplapeeck.oatmeal.entities.UserEntity;

import java.sql.SQLException;

public class DatabaseUtil {
    private final JdbcPooledConnectionSource conncectionSource;
    private final ConfigParser.DatabaseConfiguration databaseConfiguration;

    private final Dao<GuildEntity, Long> guildEntityDao;
    private final Dao<UserEntity, Long> userEntityDao;
    private final Dao<ChannelEntity, Long> channelEntityDao;

    public DatabaseUtil() throws SQLException {
        // init database
        this.databaseConfiguration = ConfigParser.getDatabaseConfiguration();
        this.conncectionSource = new JdbcPooledConnectionSource("jdbc:mariadb://" + this.databaseConfiguration.getAddress() + ":" +this.databaseConfiguration.getPort() + "/" + this.databaseConfiguration.getDatabase() + "?user="+ this.databaseConfiguration.getUsername() + "&password=" + this.databaseConfiguration.getPassword(), this.databaseConfiguration.getType());
        this.conncectionSource.setMaxConnectionsFree(this.databaseConfiguration.getMaxConnections());

        // init tables
        TableUtils.createTableIfNotExists(this.conncectionSource, GuildEntity.class);
        TableUtils.createTableIfNotExists(this.conncectionSource, UserEntity.class);
        TableUtils.createTableIfNotExists(this.conncectionSource, ChannelEntity.class);

        // init dao
        this.guildEntityDao = DaoManager.createDao(this.conncectionSource, GuildEntity.class);
        this.userEntityDao = DaoManager.createDao(this.conncectionSource, UserEntity.class);
        this.channelEntityDao = DaoManager.createDao(this.conncectionSource, ChannelEntity.class);
    }

    public JdbcPooledConnectionSource getConncectionSource() {
        return conncectionSource;
    }

    public ConfigParser.DatabaseConfiguration getDatabaseConfiguration() {
        return databaseConfiguration;
    }

    public synchronized Dao<GuildEntity, Long> getGuildEntityDao() {
        return guildEntityDao;
    }

    public synchronized Dao<UserEntity, Long> getUserEntityDao() {
        return userEntityDao;
    }

    public synchronized Dao<ChannelEntity, Long> getChannelEntityDao() {
        return channelEntityDao;
    }
}

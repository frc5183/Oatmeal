package wtf.triplapeeck.oatmeal.util;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.triplapeeck.oatmeal.entities.ChannelEntity;
import wtf.triplapeeck.oatmeal.entities.GuildEntity;
import wtf.triplapeeck.oatmeal.entities.UserEntity;
import wtf.triplapeeck.oatmeal.errors.database.ExistingEntryException;
import wtf.triplapeeck.oatmeal.errors.database.MissingEntryException;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class DatabaseUtil {
    private static JdbcPooledConnectionSource connectionSource;
    private static ConfigParser.DatabaseConfiguration databaseConfiguration;


    // Daos
    private static Dao<GuildEntity, String> guildDao;
    private static Dao<UserEntity, String> userDao;
    private static Dao<ChannelEntity, String> channelDao;

    public DatabaseUtil() throws SQLException {
        // init database
        databaseConfiguration = ConfigParser.getDatabaseConfiguration();
        connectionSource = new JdbcPooledConnectionSource("jdbc:mariadb://" + databaseConfiguration.getAddress() + ":" + databaseConfiguration.getPort() + "/" + databaseConfiguration.getDatabase() + "?user="+ databaseConfiguration.getUsername() + "&password=" + databaseConfiguration.getPassword(), databaseConfiguration.getType());
        connectionSource.setMaxConnectionsFree(databaseConfiguration.getMaxConnections());

        // init tables
        TableUtils.createTableIfNotExists(connectionSource, GuildEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, UserEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, ChannelEntity.class);
        // init dao
        guildDao = DaoManager.createDao(connectionSource, GuildEntity.class);
        userDao = DaoManager.createDao(connectionSource, UserEntity.class);
        channelDao = DaoManager.createDao(connectionSource, ChannelEntity.class);
    }

    public JdbcPooledConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public ConfigParser.DatabaseConfiguration getDatabaseConfiguration() {
        return databaseConfiguration;
    }

    @Nullable
    public GuildEntity getGuildEntity(@NotNull String id) throws SQLException {
        return guildDao.queryForId(id);
    }

    public void updateGuildEntity(@NotNull GuildEntity guildEntity) throws SQLException {

        guildDao.createOrUpdate(guildEntity);
    }
    @Nullable
    public UserEntity getUserEntity(@NotNull String id) throws SQLException {
        return userDao.queryForId(id);
    }
    public void updateUserEntity(@NotNull UserEntity userEntity) throws SQLException {
        userDao.createOrUpdate(userEntity);
    }

    @Nullable
    public static ChannelEntity getChannelEntity(@NotNull Long id) throws SQLException {
        QueryBuilder<ChannelEntity, String> qb = channelDao.queryBuilder();
        qb.where().eq("id", id);
        return qb.queryForFirst();
    }

    public static void updateChannelEntity(@NotNull ChannelEntity channelEntity) throws SQLException {
        channelDao.createOrUpdate(channelEntity);
    }


}

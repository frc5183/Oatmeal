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
    private static JdbcPooledConnectionSource conncectionSource;
    private static ConfigParser.DatabaseConfiguration databaseConfiguration;


    // Daos
    private static Dao<GuildEntity, Long> guildDao;
    private static Dao<UserEntity, Long> userDao;
    private static Dao<ChannelEntity, Long> channelDao;

    public DatabaseUtil() throws SQLException {
        // init database
        databaseConfiguration = ConfigParser.getDatabaseConfiguration();
        conncectionSource = new JdbcPooledConnectionSource("jdbc:mariadb://" + databaseConfiguration.getAddress() + ":" + databaseConfiguration.getPort() + "/" + databaseConfiguration.getDatabase() + "?user="+ databaseConfiguration.getUsername() + "&password=" + databaseConfiguration.getPassword(), databaseConfiguration.getType());
        conncectionSource.setMaxConnectionsFree(databaseConfiguration.getMaxConnections());

        // init tables
        TableUtils.createTableIfNotExists(conncectionSource, GuildEntity.class);
        TableUtils.createTableIfNotExists(conncectionSource, UserEntity.class);
        TableUtils.createTableIfNotExists(conncectionSource, ChannelEntity.class);
        // init dao
        guildDao = DaoManager.createDao(conncectionSource, GuildEntity.class);
        userDao = DaoManager.createDao(conncectionSource, UserEntity.class);
        channelDao = DaoManager.createDao(conncectionSource, ChannelEntity.class);
    }

    public JdbcPooledConnectionSource getConncectionSource() {
        return conncectionSource;
    }

    public ConfigParser.DatabaseConfiguration getDatabaseConfiguration() {
        return databaseConfiguration;
    }

    @Nullable
    public static GuildEntity getGuildEntity(@NotNull Long id) throws SQLException {
        QueryBuilder<GuildEntity, Long> qb = guildDao.queryBuilder();
        qb.where().eq("id", id);
        return qb.queryForFirst();
    }

    public static void updateGuildEntity(@NotNull GuildEntity guildEntity) throws SQLException, MissingEntryException {
        if (getGuildEntity(guildEntity.getId()) == null) throw new MissingEntryException("Guild does not exist in the database.");
        guildDao.update(guildEntity);
    }

    public static void createGuildEntity(@NotNull GuildEntity guildEntity) throws SQLException, ExistingEntryException {
        if (getGuildEntity(guildEntity.getId()) != null) throw new ExistingEntryException("Guild already exists");
        guildDao.create(guildEntity);
    }

    @Nullable
    public static UserEntity getUserEntity(@NotNull Long id) throws SQLException {
        QueryBuilder<UserEntity, Long> qb = userDao.queryBuilder();
        qb.where().eq("id", id);
        return qb.queryForFirst();
    }

    public static void updateUserEntity(@NotNull UserEntity userEntity) throws SQLException, MissingEntryException {
        if (getUserEntity(userEntity.getId()) == null) throw new MissingEntryException("User does not exist in the database.");
        userDao.update(userEntity);
    }

    public static void createUserEntity(@NotNull UserEntity userEntity) throws SQLException, ExistingEntryException {
        if (getUserEntity(userEntity.getId()) != null) throw new ExistingEntryException("User already exists");
        userDao.create(userEntity);
    }

    @Nullable
    public static ChannelEntity getChannelEntity(@NotNull Long id) throws SQLException {
        QueryBuilder<ChannelEntity, Long> qb = channelDao.queryBuilder();
        qb.where().eq("id", id);
        return qb.queryForFirst();
    }

    public static void updateChannelEntity(@NotNull ChannelEntity channelEntity) throws SQLException, MissingEntryException {
        if (getChannelEntity(channelEntity.getId()) == null) throw new MissingEntryException("Channel does not exist in the database.");
        channelDao.update(channelEntity);
    }

    public static void createChannelEntity(@NotNull ChannelEntity channelEntity) throws SQLException, ExistingEntryException {
        if (getChannelEntity(channelEntity.getId()) != null) throw new ExistingEntryException("Channel already exists");
        channelDao.create(channelEntity);
    }
}

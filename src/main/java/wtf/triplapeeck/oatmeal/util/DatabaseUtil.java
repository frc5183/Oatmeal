package wtf.triplapeeck.oatmeal.util;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.triplapeeck.oatmeal.cards.Table;
import wtf.triplapeeck.oatmeal.entities.*;
import wtf.triplapeeck.oatmeal.errors.database.MissingEntryException;

import java.sql.SQLException;

public class DatabaseUtil {
    private static JdbcPooledConnectionSource connectionSource;
    private static ConfigParser.DatabaseConfiguration databaseConfiguration;


    // Daos
    private static Dao<GuildEntity, String> guildDao;
    private static Dao<UserEntity, String> userDao;
    private static Dao<ChannelEntity, String> channelDao;
    private static Dao<BanEntity, String> banDao;
    private static Dao<MuteEntity, String> muteDao;

    public DatabaseUtil() throws SQLException {
        // init database
        databaseConfiguration = ConfigParser.getDatabaseConfiguration();
        connectionSource = new JdbcPooledConnectionSource("jdbc:mariadb://" + databaseConfiguration.getAddress() + ":" + databaseConfiguration.getPort() + "/" + databaseConfiguration.getDatabase() + "?user="+ databaseConfiguration.getUsername() + "&password=" + databaseConfiguration.getPassword(), databaseConfiguration.getType());
        connectionSource.setMaxConnectionsFree(databaseConfiguration.getMaxConnections());

        // init tables
        TableUtils.createTableIfNotExists(connectionSource, GuildEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, UserEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, ChannelEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, BanEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, MuteEntity.class);
        // init dao
        guildDao = DaoManager.createDao(connectionSource, GuildEntity.class);
        userDao = DaoManager.createDao(connectionSource, UserEntity.class);
        channelDao = DaoManager.createDao(connectionSource, ChannelEntity.class);
        banDao = DaoManager.createDao(connectionSource, BanEntity.class);
        muteDao = DaoManager.createDao(connectionSource, MuteEntity.class);
    }

    public JdbcPooledConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public ConfigParser.DatabaseConfiguration getDatabaseConfiguration() {
        return databaseConfiguration;
    }

    @Nullable
    public static GuildEntity getGuildEntity(@NotNull String id) throws SQLException, MissingEntryException {
        if (guildDao.queryForId(id) == null) throw new MissingEntryException("Channel doesn't exist in database.");
        return guildDao.queryForId(id);
    }

    public static void updateGuildEntity(@NotNull GuildEntity guildEntity) throws SQLException {
        guildDao.createOrUpdate(guildEntity);
    }

    public static void deleteGuildEntity(@NotNull GuildEntity guildEntity) throws SQLException {
        guildDao.delete(guildEntity);
    }

    @Nullable
    public static UserEntity getUserEntity(@NotNull String id) throws SQLException, MissingEntryException {
        if (userDao.queryForId(id) == null) throw new MissingEntryException("Channel doesn't exist in database.");
        return userDao.queryForId(id);
    }

    public static void updateUserEntity(@NotNull UserEntity userEntity) throws SQLException {
        userDao.createOrUpdate(userEntity);
    }

    public static void deleteUserEntity(@NotNull UserEntity userEntity) throws SQLException {
        userDao.delete(userEntity);
    }

    @Nullable
    public static ChannelEntity getChannelEntity(@NotNull String id) throws SQLException, MissingEntryException {
        if (channelDao.queryForId(id) == null) throw new MissingEntryException("Channel doesn't exist in database.");
        return channelDao.queryForId(id);
    }

    public static void updateChannelEntity(@NotNull ChannelEntity channelEntity) throws SQLException {
        channelDao.createOrUpdate(channelEntity);
    }

    public static void deleteChannelEntity(@NotNull ChannelEntity channelEntity) throws SQLException {
        channelDao.delete(channelEntity);
    }

    @Nullable
    public static BanEntity getBanEntity(@NotNull String id) throws SQLException, MissingEntryException {
        if (banDao.queryForId(id) == null) throw new MissingEntryException("Ban doesn't exist in database.");
        return banDao.queryForId(id);
    }

    public static void updateBanEntity(@NotNull BanEntity banEntity) throws SQLException {
        banDao.createOrUpdate(banEntity);
    }

    public static void deleteBanEntity(@NotNull BanEntity banEntity) throws SQLException {
        banDao.delete(banEntity);
    }

    @Nullable
    public static MuteEntity getMuteEntity(@NotNull String id) throws SQLException, MissingEntryException {
        if (muteDao.queryForId(id) == null) throw new MissingEntryException("Mute doesn't exist in database.");
        return muteDao.queryForId(id);
    }

    public static void updateMuteEntity(@NotNull MuteEntity muteEntity) throws SQLException {
        muteDao.createOrUpdate(muteEntity);
    }

    public static void deleteMuteEntity(@NotNull MuteEntity muteEntity) throws SQLException {
        muteDao.delete(muteEntity);
    }
}

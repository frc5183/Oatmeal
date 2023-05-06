package wtf.triplapeeck.oatmeal.util;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.triplapeeck.oatmeal.entities.mariadb.*;

import java.sql.SQLException;

public class DatabaseUtil {
    private static JdbcPooledConnectionSource connectionSource;
    private static ConfigParser.DatabaseConfiguration databaseConfiguration;


    // Daos
    private static Dao<MariaGuild, String> guildDao;
    private static Dao<MariaUser, String> userDao;
    private static Dao<MariaChannel, String> channelDao;
    private static Dao<MariaBan, Long> banDao;
    private static Dao<MariaMute, Long> muteDao;
    private static Dao<MariaMember, String> memberDao;
    private static Dao<MariaGeneric, String> genericDao;
    public DatabaseUtil() throws SQLException {
        // init database
        databaseConfiguration = ConfigParser.getDatabaseConfiguration();
        connectionSource = new JdbcPooledConnectionSource("jdbc:mariadb://" + databaseConfiguration.getAddress() + ":" + databaseConfiguration.getPort() + "/" + databaseConfiguration.getDatabase() + "?user="+ databaseConfiguration.getUsername() + "&password=" + databaseConfiguration.getPassword(), databaseConfiguration.getType());
        connectionSource.setMaxConnectionsFree(databaseConfiguration.getMaxConnections());

        // init tables
        TableUtils.createTableIfNotExists(connectionSource, MariaGuild.class);
        TableUtils.createTableIfNotExists(connectionSource, MariaUser.class);
        TableUtils.createTableIfNotExists(connectionSource, MariaChannel.class);
        TableUtils.createTableIfNotExists(connectionSource, MariaBan.class);
        TableUtils.createTableIfNotExists(connectionSource, MariaMute.class);
        TableUtils.createTableIfNotExists(connectionSource, MariaMember.class);
        TableUtils.createTableIfNotExists(connectionSource, MariaGeneric.class);
        // init dao
        guildDao = DaoManager.createDao(connectionSource, MariaGuild.class);
        userDao = DaoManager.createDao(connectionSource, MariaUser.class);
        channelDao = DaoManager.createDao(connectionSource, MariaChannel.class);
        banDao = DaoManager.createDao(connectionSource, MariaBan.class);
        muteDao = DaoManager.createDao(connectionSource, MariaMute.class);
        memberDao = DaoManager.createDao(connectionSource, MariaMember.class);
        genericDao = DaoManager.createDao(connectionSource, MariaGeneric.class);
    }

    public JdbcPooledConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public ConfigParser.DatabaseConfiguration getDatabaseConfiguration() {
        return databaseConfiguration;
    }

    @Nullable
    public static MariaGuild getGuildEntity(@NotNull String id) throws SQLException{
        return guildDao.queryForId(id);
    }

    public static void updateGuildEntity(@NotNull MariaGuild guildEntity) throws SQLException {
        guildDao.createOrUpdate(guildEntity);
    }

    public static void deleteGuildEntity(@NotNull MariaGuild guildEntity) throws SQLException {
        guildDao.delete(guildEntity);
    }

    @Nullable
    public static MariaUser getUserEntity(@NotNull String id) throws SQLException {
        return userDao.queryForId(id);
    }

    public static void updateUserEntity(@NotNull MariaUser userEntity) throws SQLException {
        userDao.createOrUpdate(userEntity);
    }

    public static void deleteUserEntity(@NotNull MariaUser userEntity) throws SQLException {

        userDao.delete(userEntity);
    }

    @Nullable
    public static MariaChannel getChannelEntity(@NotNull String id) throws SQLException {
        return channelDao.queryForId(id);
    }

    public static void updateChannelEntity(@NotNull MariaChannel channelEntity) throws SQLException {
        channelDao.createOrUpdate(channelEntity);
    }

    public static void deleteChannelEntity(@NotNull MariaChannel channelEntity) throws SQLException {

        channelDao.delete(channelEntity);
    }

    @Nullable
    public static MariaBan getBanEntity(@NotNull Long id) throws SQLException {
        return banDao.queryForId(id);
    }

    public static void updateBanEntity(@NotNull MariaBan banEntity) throws SQLException {
        banDao.createOrUpdate(banEntity);
    }

    public static void deleteBanEntity(@NotNull MariaBan banEntity) throws SQLException {
        banDao.delete(banEntity);
    }

    @Nullable
    public static MariaMute getMuteEntity(@NotNull Long id) throws SQLException {
        return muteDao.queryForId(id);
    }

    public static void updateMuteEntity(@NotNull MariaMute muteEntity) throws SQLException {
        muteDao.createOrUpdate(muteEntity);
    }

    public static void deleteMuteEntity(@NotNull MariaMute muteEntity) throws SQLException {

        muteDao.delete(muteEntity);
    }
    @Nullable
    public static MariaMember getMemberEntity(@NotNull String id) throws SQLException {
        return memberDao.queryForId(id);
    }
    public static void updateMemberEntity(@NotNull MariaMember memberEntity) throws SQLException {
        memberDao.createOrUpdate(memberEntity);
    }
    public static void deleteMemberEntity(@NotNull MariaMember memberEntity) throws SQLException {
        memberDao.delete(memberEntity);
    }
    @Nullable
    public static MariaGeneric getGenericEntity(@NotNull String id) throws SQLException {
        return genericDao.queryForId(id);
    }
    public static void updateGenericEntity(@NotNull MariaGeneric mariaGeneric) throws SQLException {
        genericDao.createOrUpdate(mariaGeneric);
    }
    public static void deleteGenericEntity(@NotNull MariaGeneric mariaGeneric) throws SQLException {
        genericDao.delete(mariaGeneric);
    }
}

package wtf.triplapeeck.oatmeal.util;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.apache.commons.logging.Log;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.triplapeeck.oatmeal.Config;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.entities.mariadb.*;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class ORMLiteDatabaseUtil {
    private static JdbcPooledConnectionSource connectionSource;
    private static ConfigParser.DatabaseConfiguration databaseConfiguration;


    // Daos
    private static Dao<MariaGuild, String> guildDao;
    private static Dao<MariaUser, String> userDao;
    private static Dao<MariaChannel, String> channelDao;
    private static Dao<MariaBan, Long> banDao;
    private static Dao<MariaMute, Long> muteDao;
    private static Dao<MariaMember, String> memberDao;
    private static Dao<MariaReminder, Long> reminderDao;
    private static Dao<MariaCustomResponse, Long> customResponseDao;
    public ORMLiteDatabaseUtil() throws SQLException {
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
        TableUtils.createTableIfNotExists(connectionSource, MariaReminder.class);
        // init dao
        guildDao = DaoManager.createDao(connectionSource, MariaGuild.class);
        userDao = DaoManager.createDao(connectionSource, MariaUser.class);
        channelDao = DaoManager.createDao(connectionSource, MariaChannel.class);
        banDao = DaoManager.createDao(connectionSource, MariaBan.class);
        muteDao = DaoManager.createDao(connectionSource, MariaMute.class);
        memberDao = DaoManager.createDao(connectionSource, MariaMember.class);
        reminderDao = DaoManager.createDao(connectionSource, MariaReminder.class);
        customResponseDao = DaoManager.createDao(connectionSource, MariaCustomResponse.class);
        upgrade();
    }

    private static final int VERSION=4;

    public static int getVersion() {
        return VERSION;
    }
/**
 * Upgrades the database schema to the latest version.
 * <p>
 * This method modifies the {@link Config} object to update the version number and saves it.
 * If the current version is greater than the latest version, the program will inform the user of this and exit.
 * </p>
 */
    public static void upgrade() {
        Config config = Config.getConfig();
        if (config.version>VERSION) {
            Logger.basicLog(Logger.Level.FATAL, "Database version is newer than program. Please upgrade the program!");
            throw new RuntimeException("Database version is newer than program. Please upgrade the program!");
        }
        switch (config.version) {
            case 1: // No Break: Will run all needed upgrades consecutively. Should allow an upgrade from 1 to newest in one go.
                Logger.basicLog(Logger.Level.INFO, "Upgrading Database from ORM Version 1 to 2");
                try {
                    userDao.executeRaw("ALTER TABLE oatmeal_users DROP COLUMN jsonReminders;");
                    //reminderDao is new, so it is handled via a createTableIfNotExists;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            case 2:
                Logger.basicLog(Logger.Level.INFO, "Database now ORM Version 2");
                try {
                    userDao.executeRaw("ALTER TABLE oatmeal_reminders MODIFY COLUMN text VARCHAR (5000);");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            case 3:
                Logger.basicLog(Logger.Level.INFO, "Database now ORM Version 3");
                try {
                    userDao.executeRaw("ALTER TABLE oatmeal_channels ADD COLUMN autoThread TINYINT(1);");
                    userDao.executeRaw("ALTER TABLE oatmeal_channels MODIFY COLUMN autoThread TINYINT(1) NOT NULL;");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            case 4:
                Logger.basicLog(Logger.Level.INFO, "Database now ORM Version 4");
        }
        config.version=VERSION;
        Config.saveConfig();
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
    public static MariaReminder getReminderEntity(@NotNull Long id) throws SQLException {
        return reminderDao.queryForId(id);
    }
    public static List<MariaReminder> getAllReminderEntity() throws SQLException {
        return reminderDao.queryForAll();
    }
    public static void updateReminderEntity(@NotNull MariaReminder reminderEntity) throws SQLException {
        reminderDao.createOrUpdate(reminderEntity);
    }
    public static void deleteReminderEntity(@NotNull MariaReminder reminderEntity) throws SQLException {
        reminderDao.delete(reminderEntity);
    }
    @Nullable
    public static MariaCustomResponse getCustomResponseEntity(@NotNull Long id) throws SQLException {
        return customResponseDao.queryForId(id);
    }
    public static List<MariaCustomResponse> getAllCustomResponseEntity() throws SQLException {
        return customResponseDao.queryForAll();
    }
    public static List<MariaCustomResponse> queryAllCustomResponseEntity(MariaGuild data) throws SQLException {
        return customResponseDao.queryForEq("guild", data);
    }
    public static void updateCustomResponseEntity(@NotNull MariaCustomResponse customResponse) throws SQLException {
        customResponseDao.createOrUpdate(customResponse);
    }
    public static void deleteCustomResponseEntity(@NotNull MariaCustomResponse customResponse) throws SQLException {
        customResponseDao.delete(customResponse);
    }
    public static void deleteCustomResponseEntity(@NotNull Long id) throws SQLException {
        customResponseDao.deleteById(id);
    }
    public static void deleteCustomResponseEntities(@NotNull Collection<Long> id) throws SQLException {
        customResponseDao.deleteIds(id);
    }
    public static void deleteReminderEntity(@NotNull Long id) throws SQLException {
        reminderDao.deleteById(id);
    }
    public static void deleteReminderEntities(@NotNull Collection<Long> id) throws SQLException {
        reminderDao.deleteIds(id);
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
}

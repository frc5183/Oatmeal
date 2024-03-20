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

/**
 * This class is used to interact with the database from ORMLite
 */
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
        upgrade();
    }

    private static final int VERSION=4;

    /**
     * Gets the current version of the database schema
     * @return The current version of the database schema
     */
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

    /** Gets the guild entity from the database
     * @param id The id of the guild
     * @return The guild entity
     * @throws SQLException If there is an error getting the guild entity
     */
    @Nullable
    public static MariaGuild getGuildEntity(@NotNull String id) throws SQLException{
        return guildDao.queryForId(id);
    }

    /** Updates the guild entity in the database
     * @param guildEntity  The guild entity to update
     * @throws SQLException If there is an error updating the guild entity
     */
    public static void updateGuildEntity(@NotNull MariaGuild guildEntity) throws SQLException {
        guildDao.createOrUpdate(guildEntity);
    }

    /** Deletes the guild entity from the database
     * @param guildEntity The guild entity to delete
     * @throws SQLException If there is an error deleting the guild entity
     */
    public static void deleteGuildEntity(@NotNull MariaGuild guildEntity) throws SQLException {
        guildDao.delete(guildEntity);
    }

    /**
     * Gets the user entity from the database
     * @param id The id of the user
     * @return The user entity
     * @throws SQLException If there is an error getting the user entity
     */
    @Nullable
    public static MariaUser getUserEntity(@NotNull String id) throws SQLException {
        return userDao.queryForId(id);
    }

    /** Updates the user entity in the database
     * @param userEntity The user entity to update
     * @throws SQLException If there is an error updating the user entity
     */
    public static void updateUserEntity(@NotNull MariaUser userEntity) throws SQLException {
        userDao.createOrUpdate(userEntity);
    }

    /** Deletes the user entity from the database
     * @param userEntity The user entity to delete
     * @throws SQLException If there is an error deleting the user entity
     */
    public static void deleteUserEntity(@NotNull MariaUser userEntity) throws SQLException {
        userDao.delete(userEntity);
    }

    /** Gets the reminder entity from the database
     * @param id The id of the reminder
     * @return The reminder entity
     * @throws SQLException If there is an error getting the reminder entity
     */
    @Nullable
    public static MariaReminder getReminderEntity(@NotNull Long id) throws SQLException {
        return reminderDao.queryForId(id);
    }

    /** Gets all reminder entities from the database
     * @return All reminder entities in the database
     * @throws SQLException If there is an error getting the reminder entities
     */
    public static List<MariaReminder> getAllReminderEntity() throws SQLException {
        return reminderDao.queryForAll();
    }

    /** Updates the reminder entity in the database
     * @param reminderEntity The reminder entity to update
     * @throws SQLException If there is an error updating the reminder entity
     */
    public static void updateReminderEntity(@NotNull MariaReminder reminderEntity) throws SQLException {
        reminderDao.createOrUpdate(reminderEntity);
    }

    /** Deletes the reminder entity from the database
     * @param reminderEntity The reminder entity to delete
     * @throws SQLException If there is an error deleting the reminder entity
     */
    public static void deleteReminderEntity(@NotNull MariaReminder reminderEntity) throws SQLException {
        reminderDao.delete(reminderEntity);
    }

    /** Deletes the reminder entity from the database
     * @param id The id of the reminder to delete
     * @throws SQLException If there is an error deleting the reminder entity
     */
    public static void deleteReminderEntity(@NotNull Long id) throws SQLException {
        reminderDao.deleteById(id);
    }

    /** Deletes the reminder entity from the database
     * @param id The id of the reminder to delete
     * @throws SQLException If there is an error deleting the reminder entity
     */
    public static void deleteReminderEntities(@NotNull Collection<Long> id) throws SQLException {
        reminderDao.deleteIds(id);
    }

    /** Gets the channel entity from the database
     * @param id The id of the channel
     * @return The channel entity
     * @throws SQLException If there is an error getting the channel entity
     */
    @Nullable
    public static MariaChannel getChannelEntity(@NotNull String id) throws SQLException {
        return channelDao.queryForId(id);
    }

    /** Updates the channel entity in the database
     * @param channelEntity The channel entity to update
     * @throws SQLException If there is an error updating the channel entity
     */
    public static void updateChannelEntity(@NotNull MariaChannel channelEntity) throws SQLException {
        channelDao.createOrUpdate(channelEntity);
    }

    /** Deletes the channel entity from the database
     * @param channelEntity The channel entity to delete
     * @throws SQLException If there is an error deleting the channel entity
     */
    public static void deleteChannelEntity(@NotNull MariaChannel channelEntity) throws SQLException {

        channelDao.delete(channelEntity);
    }

    /** Gets the ban entity from the database
     * @param id The id of the ban
     * @return The ban entity
     * @throws SQLException If there is an error getting the ban entity
     */
    @Nullable
    public static MariaBan getBanEntity(@NotNull Long id) throws SQLException {
        return banDao.queryForId(id);
    }

    /** Updates the ban entity in the database
     * @param banEntity  The ban entity to update
     * @throws SQLException If there is an error updating the ban entity
     */
    public static void updateBanEntity(@NotNull MariaBan banEntity) throws SQLException {
        banDao.createOrUpdate(banEntity);
    }

    /** Deletes the ban entity from the database
     * @param banEntity The ban entity to delete
     * @throws SQLException If there is an error deleting the ban entity
     */
    public static void deleteBanEntity(@NotNull MariaBan banEntity) throws SQLException {
        banDao.delete(banEntity);
    }

    /** Gets the mute entity from the database
     * @param id The id of the mute
     * @return The mute entity
     * @throws SQLException If there is an error getting the mute entity
     */
    @Nullable
    public static MariaMute getMuteEntity(@NotNull Long id) throws SQLException {
        return muteDao.queryForId(id);
    }

    /** Updates the mute entity in the database
     * @param muteEntity The mute entity to update
     * @throws SQLException If there is an error updating the mute entity
     */
    public static void updateMuteEntity(@NotNull MariaMute muteEntity) throws SQLException {
        muteDao.createOrUpdate(muteEntity);
    }

    /** Deletes the mute entity from the database
     * @param muteEntity The mute entity to delete
     * @throws SQLException If there is an error deleting the mute entity
     */
    public static void deleteMuteEntity(@NotNull MariaMute muteEntity) throws SQLException {

        muteDao.delete(muteEntity);
    }

    /** Gets the member entity from the database
     * @param id The id of the member
     * @return The member entity
     * @throws SQLException If there is an error getting the member entity
     */
    @Nullable
    public static MariaMember getMemberEntity(@NotNull String id) throws SQLException {
        return memberDao.queryForId(id);
    }

    /** Updates the member entity in the database
     * @param memberEntity The member entity to update
     * @throws SQLException If there is an error updating the member entity
     */
    public static void updateMemberEntity(@NotNull MariaMember memberEntity) throws SQLException {
        memberDao.createOrUpdate(memberEntity);
    }

    /** Deletes the member entity from the database
     * @param memberEntity The member entity to delete
     * @throws SQLException If there is an error deleting the member entity
     */
    public static void deleteMemberEntity(@NotNull MariaMember memberEntity) throws SQLException {
        memberDao.delete(memberEntity);
    }
}

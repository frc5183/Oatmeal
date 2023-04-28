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
    private final JdbcPooledConnectionSource conncectionSource;
    private final ConfigParser.DatabaseConfiguration databaseConfiguration;


    // Daos
    private final Dao<GuildEntity, Long> guildDao;
    private final Dao<UserEntity, Long> userDao;
    private final Dao<ChannelEntity, Long> channelDao;

    // Caches
    private final ConcurrentHashMap<Long, GuildEntity> guildCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, UserEntity> userCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, ChannelEntity> channelCache = new ConcurrentHashMap<>();

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
        this.guildDao = DaoManager.createDao(this.conncectionSource, GuildEntity.class);
        this.userDao = DaoManager.createDao(this.conncectionSource, UserEntity.class);
        this.channelDao = DaoManager.createDao(this.conncectionSource, ChannelEntity.class);
    }

    public JdbcPooledConnectionSource getConncectionSource() {
        return conncectionSource;
    }

    public ConfigParser.DatabaseConfiguration getDatabaseConfiguration() {
        return databaseConfiguration;
    }

    @Nullable
    public GuildEntity getGuildEntity(@NotNull Long id) throws SQLException {
        if (guildCache.containsKey(id)) return guildCache.get(id);
        QueryBuilder<GuildEntity, Long> qb = guildDao.queryBuilder();
        qb.where().eq("id", id);
        guildCache.put(id, guildDao.queryForFirst(qb.prepare()));
        return guildCache.get(id);
    }

    public void updateGuildEntity(@NotNull GuildEntity guildEntity) throws SQLException, MissingEntryException {
        if (getGuildEntity(guildEntity.getId()) == null) throw new MissingEntryException("Guild does not exist in the database.");
        guildCache.put(guildEntity.getId(), guildEntity);
        //guildDao.update(guildEntity);
    }

    public void createGuildEntity(@NotNull GuildEntity guildEntity) throws SQLException, ExistingEntryException {
        if (getGuildEntity(guildEntity.getId()) != null) throw new ExistingEntryException("Guild already exists");
        guildCache.put(guildEntity.getId(), guildEntity);
        // guildDao.create(guildEntity);
    }


}

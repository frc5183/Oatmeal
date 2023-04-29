package wtf.triplapeeck.oatmeal.entities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.triplapeeck.oatmeal.cards.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.Table(name = "oatmeal_channels")
public class ChannelEntity extends AccessibleEntity {
    @Id
    public @NotNull String id;

    @Column(columnDefinition = "TEXT")
    public @Nullable String table;

    @Column(nullable = false)
    public @NotNull Integer tableCount;

    @Column(nullable = false)
    public @NotNull Boolean tableRecruiting;

    @Column(nullable = false)
    public @NotNull Boolean tableHeld;

    @Column(nullable = false)
    public @NotNull Boolean tableInsured;

    public ChannelEntity(@NotNull String id) {
        super();
        this.id = id;
        this.table = null;
        this.tableCount = 0;
        this.tableRecruiting = false;
        this.tableHeld = false;
        this.tableInsured = false;
    }

    @Deprecated
    public ChannelEntity() {}

    @NotNull
    public synchronized String getId() {
        return id;
    }

    @Nullable
    public synchronized Table getTable() {
        return Table.fromString(table);
    }

    public synchronized void setTable(@Nullable Table table) {
        if (table == null) {
            this.table = null;
            return;
        }
        this.table = table.toString();
    }

    @NotNull
    public synchronized Integer getTableCount() {
        return tableCount;
    }

    public synchronized void setTableCount(@NotNull Integer tableCount) {
        this.tableCount = tableCount;
    }

    @NotNull
    public synchronized Boolean isTableRecruiting() {
        return tableRecruiting;
    }

    public synchronized void setTableRecruiting(@NotNull Boolean tableRecruiting) {
        this.tableRecruiting = tableRecruiting;
    }

    @NotNull
    public synchronized Boolean isTableHeld() {
        return tableHeld;
    }

    public synchronized void setTableHeld(@NotNull Boolean tableHeld) {
        this.tableHeld = tableHeld;
    }

    @NotNull
    public synchronized Boolean isTableInsured() {
        return tableInsured;
    }

    public synchronized void setTableInsured(@NotNull Boolean tableInsured) {
        this.tableInsured = tableInsured;
    }
}

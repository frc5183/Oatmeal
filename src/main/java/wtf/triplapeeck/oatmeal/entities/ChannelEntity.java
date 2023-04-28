package wtf.triplapeeck.oatmeal.entities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.triplapeeck.oatmeal.cards.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@javax.persistence.Table(name = "oatmeal_channels")
public class ChannelEntity {
    @Id
    public @NotNull final Long id;

    @Column
    public @Nullable Table table;

    @Column(nullable = false)
    public @NotNull Integer tableCount;

    @Column(nullable = false)
    public @NotNull Boolean tableRecruiting;

    @Column(nullable = false)
    public @NotNull Boolean tableHeld;

    @Column(nullable = false)
    public @NotNull Boolean tableInsured;

    public ChannelEntity(long id) {
        this.id = id;
        this.table = null;
        this.tableCount = 0;
        this.tableRecruiting = false;
        this.tableHeld = false;
        this.tableInsured = false;
    }

    @NotNull
    public synchronized Long getId() {
        return id;
    }

    @Nullable
    public synchronized Table getTable() {
        return table;
    }

    public synchronized void setTable(@Nullable Table table) {
        this.table = table;
    }

    @NotNull
    public synchronized Integer getTableCount() {
        return tableCount;
    }

    public synchronized void setTableCount(@NotNull Integer tableCount) {
        this.tableCount = tableCount;
    }

    @NotNull
    public synchronized Boolean getTableRecruiting() {
        return tableRecruiting;
    }

    public synchronized void setTableRecruiting(@NotNull Boolean tableRecruiting) {
        this.tableRecruiting = tableRecruiting;
    }

    @NotNull
    public synchronized Boolean getTableHeld() {
        return tableHeld;
    }

    public synchronized void setTableHeld(@NotNull Boolean tableHeld) {
        this.tableHeld = tableHeld;
    }

    @NotNull
    public synchronized Boolean getTableInsured() {
        return tableInsured;
    }

    public synchronized void setTableInsured(@NotNull Boolean tableInsured) {
        this.tableInsured = tableInsured;
    }
}

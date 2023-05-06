package wtf.triplapeeck.oatmeal.entities.mariadb;

import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.entities.MemberData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

@Entity
@Table(name = "oatmeal_members")
public class MariaMember extends MemberData {
    @Id
    public @NotNull String id;
    @Column(nullable=false)
    public @NotNull BigInteger rak;
    @Column(nullable=false)
    public @NotNull Integer messageCount;
    public MariaMember(@NotNull String id) {
        super();
        this.id=id;
        this.rak=BigInteger.ZERO;
        this.messageCount=0;
    }
    public MariaMember() {}

    @NotNull
    public synchronized BigInteger getRak() {
        return rak;
    }
    public synchronized void setRak(@NotNull BigInteger rak) {
        this.rak=rak;
    }
    public synchronized String getID() {
        return id;
    }
    public synchronized void load() {}
    @NotNull
    public synchronized Integer getMessageCount() {
        return messageCount;
    }
    public synchronized void setMessageCount(@NotNull Integer messageCount) {
        this.messageCount=messageCount;
    }
}

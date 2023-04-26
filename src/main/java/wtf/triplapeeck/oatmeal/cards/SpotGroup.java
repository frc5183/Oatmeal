package wtf.triplapeeck.oatmeal.cards;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import wtf.triplapeeck.oatmeal.errors.InvalidCardActionException;
import wtf.triplapeeck.oatmeal.storable.MemberStorable;
import wtf.triplapeeck.oatmeal.storable.StorableManager;
import wtf.triplapeeck.oatmeal.Logger;

import java.math.BigInteger;

public class SpotGroup {
    public boolean insured=false;
    public String id;

    public long userId;
    private BigInteger BaseBet;
    boolean spot1Doubled=false;
    boolean spot2Doubled=false;

    private BigInteger getBaseBet(int place) {
        if (place!=1 && place!=2) {
            throw new IndexOutOfBoundsException();
        }
        if (place==1) {
            if (spot1Doubled) {
                return BaseBet.multiply(BigInteger.TWO);
            } else {
                return BaseBet;
            }
        }
        if (place==2) {
            if (spot2Doubled) {
                return BaseBet.multiply(BigInteger.TWO);
            } else {
                return BaseBet;
            }
        }
        return BaseBet;
    }
    public Spot spot1;
    public Spot spot2;
    public synchronized void winBlackjack() {
        Logger.customLog("SpotGroup", "Winning Blackjack");
                MemberStorable memberStorable = StorableManager.getMember(id);
        BigInteger rak = memberStorable.getRak();

        memberStorable.setRak(rak.add(BaseBet).add(BaseBet).add(BaseBet.divide(BigInteger.TWO)));
        memberStorable.relinquishAccess();
    }
    public synchronized void winNormal(int place) {
        Logger.customLog("SpotGroup", "Winning Normal");
        MemberStorable memberStorable = StorableManager.getMember(id);
        BigInteger rak = memberStorable.getRak();
        BigInteger BaseBet = getBaseBet(place);
        memberStorable.setRak(rak.add(BaseBet).add(BaseBet));
        memberStorable.relinquishAccess();
    }
    public synchronized void winPush(int place) {
        Logger.customLog("SpotGroup", "Winning Push");
        MemberStorable memberStorable = StorableManager.getMember(id);
        BigInteger BaseBet = getBaseBet(place);
        BigInteger rak = memberStorable.getRak();
        memberStorable.setRak(rak.add(BaseBet));
        memberStorable.relinquishAccess();
    }
    public synchronized void winInsurance() {
        Logger.customLog("SpotGroup", "Winning Insurnace");
        MemberStorable memberStorable = StorableManager.getMember(id);
        BigInteger rak = memberStorable.getRak();
        memberStorable.setRak(rak.add(BaseBet).add(BaseBet.divide(BigInteger.TWO)));
        memberStorable.relinquishAccess();
    }
    public synchronized boolean isFinished() {
        if (spot1==null) {
            return true;
        }
        if (spot2!=null) {
            return (!spot1.active && !spot2.active);
        } else {
            return !spot1.active;
        }
    }
    public synchronized boolean isActive() {
        return (spot1!=null);
    }
    public synchronized void setBet(BigInteger bet) throws InvalidCardActionException {
        Logger.customLog("SpotGroup", "Attempting To Set Bet");
        Logger.customLog("SpotGroup","Getting Member Storable");
        MemberStorable memberStorable = StorableManager.getMember(id);
        Logger.customLog("SpotGroup","Getting Rak Value");
        BigInteger rak = memberStorable.getRak();
        Logger.customLog("SpotGroup","Reassigning Bet");
        BaseBet=bet;
        if (rak.compareTo(BaseBet)==-1) {
            Logger.customLog("SpotGroup","Throwing Exception");
            memberStorable.relinquishAccess();
            throw new InvalidCardActionException("You Don't Have Enough Rak For The Bet Of: " + bet.toString() );
        } else {
            Logger.customLog("SpotGroup","Setting Bet. Finished.");
            memberStorable.setRak(rak.subtract(BaseBet));
            memberStorable.relinquishAccess();
        }
    }

    public synchronized void DoubleDownFirstSpot(Deck deck, Table table) throws InvalidCardActionException {
        Logger.customLog("SpotGroup", "Attempting To Double Down Spot 1");
                DoubleDown(deck, spot1, table);
    }

    public synchronized void DoubleDownSecondSpot(Deck deck, Table table) throws InvalidCardActionException {
        Logger.customLog("SpotGroup", "Attempting To Double Down Spot 2");
        DoubleDown(deck, spot2, table);
    }

    private synchronized void DoubleDown(Deck deck, Spot spot, Table table) throws InvalidCardActionException {
        Logger.customLog("SpotGroup", "Attempting To Double Down Internal");
        MemberStorable memberStorable = StorableManager.getMember(id);
        BigInteger rak = memberStorable.getRak();
        if (rak.compareTo(BaseBet)==-1) {
            memberStorable.relinquishAccess();
            throw new InvalidCardActionException("You Don't Have Enough Rak For That Doubled Bet");
        } else {
            try {
                spot.DoubleDown(deck, table);
                Logger.customLog("SpotGroup", "Setting Rak For Double Down");
                memberStorable.setRak(rak.subtract(BaseBet));
                Logger.customLog("SpotGroup", "Doubling Base Bet");
                if (spot==spot1) {
                    spot1Doubled=true;
                }
                if (spot==spot2) {
                    spot2Doubled=true;
                }
                memberStorable.relinquishAccess();
            } catch (InvalidCardActionException e) {
                memberStorable.relinquishAccess();
                throw e;
            }
        }
    }

    public synchronized void Split(Deck deck, TextChannel channel) throws InvalidCardActionException {
        Logger.customLog("SpotGroup", "Attempting To Split");
        if (spot2!=null) {
            throw new InvalidCardActionException("You have already split.");
        }
        MemberStorable memberStorable = StorableManager.getMember(id);
        BigInteger rak = memberStorable.getRak();
        if (rak.compareTo(BaseBet)==-1) {
            memberStorable.relinquishAccess();
            throw new InvalidCardActionException("You Don't Have Enough Rak For That Split Bet");
        }
        else {
            try {
                spot2= spot1.Split(deck, channel);
                memberStorable.setRak(rak.subtract(BaseBet));
                memberStorable.relinquishAccess();
            } catch (InvalidCardActionException e) {
                memberStorable.relinquishAccess();
                throw e;
            }
        }
    }

    public synchronized void Insure() throws InvalidCardActionException {
        Logger.customLog("SpotGroup", "Attempting To Insure");
        if (insured) {
            throw new InvalidCardActionException("You are already insured.");
        }
        MemberStorable memberStorable = StorableManager.getMember(id);
        BigInteger rak = memberStorable.getRak();
        if (rak.compareTo(BaseBet.divide(BigInteger.TWO))==-1) {
            memberStorable.relinquishAccess();
            throw new InvalidCardActionException("You Don't Have Enough Rak For An Insurance Bet");
        } else {
            memberStorable.setRak(rak.subtract(BaseBet.divide(BigInteger.TWO)));
            memberStorable.relinquishAccess();
        }
    }
}

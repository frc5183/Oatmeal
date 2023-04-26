package wtf.triplapeeck.oatmeal.cards;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import wtf.triplapeeck.oatmeal.errors.InvalidCardActionException;
import wtf.triplapeeck.oatmeal.Logger;

import java.util.ArrayList;

public class Spot {
    public Hand hand = new Hand();
    public boolean active=true;

    public boolean isBlackjack() {
        return (hand.hand.size()==2 && hand.getValue()==21);
    }

    public boolean isBlackjack(SpotGroup group) {
        return(hand.hand.size()==2 && hand.getValue()==21 && group.spot2==null);
    }
    public Spot(PlayingCard card) {
        hand.hand.add(card);
    }

    public Spot () {
    }
    public PlayingCard Draw(Deck deck) {
        PlayingCard drawnCard = deck.deck.get(deck.deck.size() - 1);
        deck.deck.remove(deck.deck.size() - 1);
        hand.hand.add(drawnCard);
        return drawnCard;
    }
    public void Hit(Deck deck, Table table) throws InvalidCardActionException {
        Logger.customLog("PlayerSpot", "Attempting To Hit");
        if (active) {
            Draw(deck);
            int value = hand.getValue();
            if (value>21) {
                Stand(table);
                throw new InvalidCardActionException("You Busted");
            }
        } else {
            throw new InvalidCardActionException("This Hand Isn't Active");
        }
    }

    public synchronized void Clean(Deck deck) {
        ArrayList<PlayingCard> temp = new ArrayList<>();
        Logger.customLog("PlayerSpot", "Cleaning Up Hand");
        for (PlayingCard card: hand.hand) {

            temp.add(card);
            deck.discard.add(card);
        }
        for (PlayingCard card: temp) {
            hand.hand.remove(card);
        }
        temp.clear();

    }
    public void Stand(Table table) {
        Logger.customLog("PlayerSpot", "Attempting To Stand");
        if (active==false) {
            return;
        }
        active=false;

    }

    public Spot Split(Deck deck, TextChannel channel) throws InvalidCardActionException {
        Logger.customLog("PlayerSpot", "Attempting To Split");
        if (active) {
            if (hand.hand.size()==2) {
                PlayingCard card = hand.hand.get(1);
                if (hand.hand.get(0).sameValue(card)) {
                    channel.sendMessage("You have successfully split.\nUse s!gettable to view your hands.").queue();
                    hand.hand.remove(1);
                    Spot spot2 = new Spot(card);
                    spot2.Draw(deck);
                    Draw(deck);
                    return spot2;
                } else {
                    throw new InvalidCardActionException("You Can't Split This Hand.");
                }
            } else {
                throw new InvalidCardActionException("You Can't Split This Hand.");
            }
        } else {
            throw new InvalidCardActionException("This Hand Isn't Active.");
        }
    }

    public void DoubleDown(Deck deck, Table table) throws InvalidCardActionException {
        Logger.customLog("PlayerSpot", "Attempting To Double Down");
        if (active) {
            if (hand.hand.size()==2) {
                try {
                    Hit(deck, table);
                    Stand(table);
                } catch (InvalidCardActionException ignored) {

                }
            } else {
                throw new InvalidCardActionException("You Can't Double Down On This Hand.");
            }
        } else {
            throw new InvalidCardActionException("This Hand Isn't Active.");
        }
    }
}

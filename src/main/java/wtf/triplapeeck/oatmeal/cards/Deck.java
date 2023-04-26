package wtf.triplapeeck.oatmeal.cards;

import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.Logger;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    public ArrayList<PlayingCard> deck = new ArrayList<>();
    public ArrayList<PlayingCard> discard = new ArrayList<>();
    public Deck() {
        for (PlayingCard.Suit suit : PlayingCard.Suit.values()) {
            for (PlayingCard.Face face : PlayingCard.Face.values()) {
                deck.add(new PlayingCard(suit, face));
            }
        }

        Collections.shuffle(deck);
    }

    public Deck(@NotNull Hand hand) {
        for (PlayingCard card: hand.hand) {
            deck.add(card);

        }

    }
    public void Shuffle() {
        for (PlayingCard card: discard) {
            discard.remove(card);
            deck.add(card);
        }
        Logger.customLog("Deck", "Shuffling Deck");
        Collections.shuffle(deck);
    }
    public Deck(@NotNull Deck deck1, @NotNull Deck deck2) {
        for (PlayingCard card : deck1.deck) {
            deck.add(card);
        }
        for (PlayingCard card : deck2.deck) {
            deck.add(card);
        }
        for (PlayingCard card : deck1.discard) {
            discard.add(card);
        }
        for (PlayingCard card : deck2.discard) {
            discard.add(card);
        }
        Collections.shuffle(deck);
    }
    public Deck add( @NotNull Deck deck) {
        return new Deck(this, deck);
    }
}

package wtf.triplapeeck.oatmeal;

import wtf.triplapeeck.oatmeal.cards.PlayingCard;

/**
 * A class to handle getting Oatmeal's custom emojis from inputs
 */
public class Emoji {
    /**
     * This method is used to get the emoji for a playing card, there are 52 hardcoded emojis for this
     * @param suit The suit of the card
     * @param face The face of the card
     * @return the string representation of the emoji tag
     */
    public static String getEmoji(PlayingCard.Suit suit, PlayingCard.Face face) {
        return switch (face) {
            case ACE -> switch (suit) {
                case SPADES -> "<:AceOfSpades:867896589296861225>";
                case HEARTS -> "<:AceOfHearts:867896634058604615>";
                case DIAMONDS -> "<:AceOfDiamonds:867896723258605589>";
                case CLUBS -> "<:AceOfClubs:867896762790445079>";
            };
            case TWO -> switch (suit) {
                case SPADES -> "<:2OfSpades:867897088574095370>";
                case HEARTS -> "<:2OfHearts:867897401623576607>";
                case DIAMONDS -> "<:2OfDiamonds:867897441402486824>";
                case CLUBS -> "<:2OfClubs:867897480280014888>";
            };
            case THREE -> switch (suit) {
                case SPADES -> "<:3OfSpades:867898251164385281>";
                case HEARTS -> "<:3OfHearts:867898283170725948>";
                case DIAMONDS -> "<:3OfDiamonds:867898328738037762>";
                case CLUBS -> "<:3OfClubs:867898358270263317>";
            };
            case FOUR -> switch (suit) {
                case SPADES -> "<:4OfSpades:867898407632371743>";
                case HEARTS -> "<:4OfHearts:867898443208589343>";
                case DIAMONDS -> "<:4OfDiamonds:867898473290137632>";
                case CLUBS -> "<:4OfClubs:867898503715094560>";
            };
            case FIVE -> switch (suit) {
                case SPADES -> "<:5OfSpades:867898631578058822>";
                case HEARTS -> "<:5OfHearts:867898660532256778>";
                case DIAMONDS -> "<:5OfDiamonds:867898692408442910>";
                case CLUBS -> "<:5OfClubs:867898721542078534>";
            };
            case SIX -> switch (suit) {
                case SPADES -> "<:6OfSpades:867898796436094992>";
                case HEARTS -> "<:6OfHearts:867898830296449065>";
                case DIAMONDS -> "<:6OfDiamonds:867898864320380968>";
                case CLUBS -> "<:6OfClubs:867898915042230314>";
            };
            case SEVEN -> switch (suit) {
                case SPADES -> "<:7OfSpades:867898988610191380>";
                case HEARTS -> "<:7OfHearts:867899033875382283>";
                case DIAMONDS -> "<:7OfDiamonds:867899066138624020>";
                case CLUBS -> "<:7OfClubs:867899098765459517>";
            };
            case EIGHT -> switch (suit) {
                case SPADES -> "<:8OfSpades:867899149428981790>";
                case HEARTS -> "<:8OfHearts:867899183871950849>";
                case DIAMONDS -> "<:8OfDiamonds:867899217154146364>";
                case CLUBS -> "<:8OfClubs:867899254831054859>";
            };
            case NINE -> switch (suit) {
                case SPADES -> "<:9OfSpades:867899306321117235>";
                case HEARTS -> "<:9OfHearts:867899336130429019>";
                case DIAMONDS -> "<:9OfDiamonds:867899365262491668>";
                case CLUBS -> "<:9OfClubs:867899395197763584>";
            };
            case TEN -> switch (suit) {
                case SPADES -> "<:10OfSpades:867899588806967336>";
                case HEARTS -> "<:10OfHearts:867899629927399465>";
                case DIAMONDS -> "<:10OfDiamonds:867899715534454855>";
                case CLUBS -> "<:10OfClubs:867899756264685589>";
            };
            case JACK -> switch (suit) {
                case SPADES -> "<:JackOfSpades:867900135191216178>";
                case HEARTS -> "<:JackOfHearts:867900163553230888>";
                case DIAMONDS -> "<:JackOfDiamonds:867900189065740288>";
                case CLUBS -> "<:JackOfClubs:867900218732969984>";
            };
            case QUEEN -> switch (suit) {
                case SPADES -> "<:QueenOfSpades:867900268687785984>";
                case HEARTS -> "<:QueenOfHearts:867900313507069964>";
                case DIAMONDS -> "<:QueenOfDiamonds:867900346365902879>";
                case CLUBS -> "<:QueenOfClubs:867900405466791996>";
            };
            case KING -> switch (suit) {
                case SPADES -> "<:KingOfSpades:867900453421056030>";
                case HEARTS -> "<:KingOfHearts:867900514380546109>";
                case DIAMONDS -> "<:KingOfDiamonds:867900554390405170>";
                case CLUBS -> "<:KingOfClubs:867900591095283752>";
            };
        };
    }
}

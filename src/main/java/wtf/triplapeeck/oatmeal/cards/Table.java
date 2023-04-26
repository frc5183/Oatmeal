package wtf.triplapeeck.oatmeal.cards;


import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import wtf.triplapeeck.oatmeal.errors.InvalidCardActionException;
import wtf.triplapeeck.oatmeal.runnable.Waiting;
import wtf.triplapeeck.oatmeal.storable.ChannelStorable;
import wtf.triplapeeck.oatmeal.storable.StorableManager;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.runnable.Insuring;

import java.math.BigInteger;

import static wtf.triplapeeck.oatmeal.cards.PlayingCard.Face.TEN;
import static wtf.triplapeeck.oatmeal.cards.PlayingCard.Suit.SPADES;
import static wtf.triplapeeck.oatmeal.cards.TableState.*;


public class Table {

    public Deck newShoe() {
        return new Deck(
                new Deck(
                        new Deck(),
                        new Deck()
                ),
                new Deck(
                        new Deck(
                                new Deck(),
                                new Deck()
                        ),
                        new Deck(
                                new Deck(),
                                new Deck()
                        )
                )
        );
    }
    public TableState state = TableState.INACTIVE;

    public long id;
    public Deck shoe;

    public Spot dealer=new Spot();

    public SpotGroup player1 = new SpotGroup();

    public SpotGroup player2 = new SpotGroup();

    public SpotGroup player3 = new SpotGroup();

    public SpotGroup player4 = new SpotGroup();

    public synchronized void Update() {
        Logger.customLog("Table", "Checking If Needing Shuffle");
        if (shoe.deck.size() <= 26) {
            TextChannel channel = Main.api.getTextChannelById(id);
            channel.sendMessage("The shoe has been reshuffled.").queue();
            shoe.Shuffle();
        }
        if (isFinished()) {
            Finish();
        }
    }
    public synchronized boolean isFinished() {
        boolean finished=true;
        if (player1.isFinished()==false) {
            finished=false;
        }
        if (player2.isFinished()==false) {
            finished=false;
        }
        if (player3.isFinished()==false) {
            finished=false;
        }
        if (player4.isFinished()==false) {
            finished=false;
        }
        if (state!=PLAYING) {
            finished=false;
        }
        return finished;
    }
    public synchronized int getNumberOfPlayers() {
        int count = 0;
        if (player1.isActive()) {count++;}
        if (player2.isActive()) {count++;}
        if (player3.isActive()) {count++;}
        if (player4.isActive()) {count++;}
        return count;
    }
    public Table(long id1) {

        id = id1;
        shoe = newShoe();

    }
    public synchronized boolean canInsure() {
        return (state== INSURING);
    }
    public SpotGroup findUser(String id) throws InvalidCardActionException {
        Logger.customLog("Table", "Finding A User Within The Table");
        try {


            if (new BigInteger(id).equals(new BigInteger(player1.id))) {
                return player1;
            }
        } catch (NullPointerException e) {

        }
        try {
            if ( new BigInteger(id).equals(new BigInteger(player2.id))) {
                return player2;
            }
        } catch (NullPointerException e) {

        }
        try {
        if ( new BigInteger(id).equals(new BigInteger(player3.id))) {
            return player3;
        }
        } catch (NullPointerException e) {

        }
        try {
        if ( new BigInteger(id).equals(new BigInteger(player4.id))) {
            return player4;
        }
        } catch (NullPointerException e) {

        }
        throw new InvalidCardActionException("You Aren't Playing At This Table");
    }
    public void FirstDeal() {
        Logger.customLog("Table", "Performing The Initial Deal");
        if (player1.isActive()) {
            player1.spot1.Draw(shoe);
            player1.spot1.Draw(shoe);
        }
        if (player2.isActive()) {
            player2.spot1.Draw(shoe);
            player2.spot1.Draw(shoe);
        }
        if (player3.isActive()) {
            player3.spot1.Draw(shoe);
            player3.spot1.Draw(shoe);
        }
        if (player4.isActive()) {
            player4.spot1.Draw(shoe);
            player4.spot1.Draw(shoe);
        }
        dealer.Draw(shoe);
        dealer.Draw(shoe);
        TextChannel channel = Main.api.getTextChannelById(id);
        Guild guild = channel.getGuild();
        Logger.customLog("Table", "Initial Deal: Telling Player Hands And Dealer Up Card To Discord Channel");
        TellPlayerHand(channel, player1);
        TellPlayerHand(channel, player2);
        TellPlayerHand(channel, player3);
        TellPlayerHand(channel, player4);

        channel.sendMessage("The dealer's face up card is " + dealer.hand.hand.get(0).toString()).queue();
        Logger.customLog("Table", "Initial Deal: Checking For Insurance");
        if (dealer.hand.hand.get(0).getFace()== PlayingCard.Face.ACE) {
            Logger.customLog("Table", "Initial Deal: Waiting for 15 Seconds to allow insurance (Starting INSURANCE)");
            channel.sendMessage("If you would like to buy an insurance bet, do s!insure within 15 Seconds").queue();
            state=INSURING;
            ChannelStorable channelStorable = StorableManager.getChannel(id);
            channelStorable.setTableInsuring(true);
            channelStorable.relinquishAccess();
            Main.threadManager.addTask(new Waiting(15, new Insuring(id)));
        } else if (dealer.hand.hand.get(0).sameValue(new PlayingCard(SPADES,TEN))) {
            Logger.customLog("Table", "Initial Deal: No Insurance, Checking for blackjack");
            PlayingCard secondCard = dealer.hand.hand.get(1);
            if (secondCard.getFace()== PlayingCard.Face.ACE) {
                Logger.customLog("Table", "Initial Deal: No Insurance, Dealer Blackjack, Cleaning Up (Starting table Finish()");
                channel.sendMessage("Dealer has a blackjack. Cleaning up the table.").queue();
                Finish();
                return;
            } else {
                Logger.customLog("Table", "Initial Deal: No Edge Cases, Continuing Into Game Player");
                channel.sendMessage("Dealer does not have a blackjack. Resuming Play.").queue();
            }
            state=PLAYING;
        } else {
            state=PLAYING;
        }
    }

    private void TellPlayerHand(TextChannel channel, SpotGroup player) {
        if (player.isActive()) {
            channel.sendMessage(Main.api.retrieveUserById(player.userId).complete().getAsMention() + "'s " + player.spot1.hand.toString()).queue();
        }
    }

    public synchronized SpotGroup GetOpenSpotGroup() throws InvalidCardActionException {
        Logger.customLog("Table", "Attempting To Return An Open SpotGroup");
        if (!player1.isActive()) {
            Logger.customLog("Table", "Returning SpotGroup1");
            return player1;
        }
        if (!player2.isActive()) {
            Logger.customLog("Table", "Returning SpotGroup2");
            return player2;
        }
        if (!player3.isActive()) {
            Logger.customLog("Table", "Returning SpotGroup3");
            return player3;
        }
        if (!player4.isActive()) {
            Logger.customLog("Table", "Returning SpotGroup4");
            return player4;
        }
        Logger.customLog("Table","No Spot Group Found. Throwing Exception");

        throw new InvalidCardActionException("All Spots On This Table Are Full.");
    }
    public synchronized void RemoveSpotGroup(SpotGroup group) {
        Logger.customLog("Table","Attempting To Remove A SpotGroup");
        if (player1==group) {
            player1 = new SpotGroup();
        } else
        if (player2==group) {
            player2 = new SpotGroup();
        } else
        if (player3==group) {
            player3 = new SpotGroup();
        } else
        if (player4==group) {
            player4 = new SpotGroup();
        }

    }
    public synchronized void Finish() {
        Logger.customLog("Table", "Finish: Game Concluded. Beginning Scoring");
        String outText="Results:\n";
        Boolean dealerBlackjack = dealer.isBlackjack();
        TextChannel channel = Main.api.getTextChannelById(id);
        Guild guild = channel.getGuild();
        while (dealer.hand.getValue()<17) {
            dealer.Draw(shoe);
        }
        if (dealerBlackjack) {
            outText = dealerBlackjackPlayer(outText, guild, player1);
            outText = dealerBlackjackPlayer(outText, guild, player2);
            outText = dealerBlackjackPlayer(outText, guild, player3);
            outText = dealerBlackjackPlayer(outText, guild, player4);
        } else {
            outText = dealerNoBlackjackPlayer(outText, guild, player1);
            outText = dealerNoBlackjackPlayer(outText, guild, player2);
            outText = dealerNoBlackjackPlayer(outText, guild, player3);
            outText = dealerNoBlackjackPlayer(outText, guild, player4);
        }
        outText =outTextPlayer(outText, player1);
        outText =outTextPlayer(outText, player2);
        outText =outTextPlayer(outText, player3);
        outText =outTextPlayer(outText, player4);
        outText+="Dealers's " + dealer.hand.toString()+"\n";
        channel.sendMessage(outText).queue();
        Logger.customLog("Table", "Finish: Game Concluded. Beginning Cleanup");
        CleanPlayer(player1);
        CleanPlayer(player2);
        CleanPlayer(player3);
        CleanPlayer(player4);
        dealer.Clean(shoe);
        player1=new SpotGroup();
        player2=new SpotGroup();
        player3=new SpotGroup();
        player4=new SpotGroup();
        dealer=new Spot();
        state=INACTIVE;
        Logger.customLog("Table", "Finished");
    }

    private String outTextPlayer(String outText, SpotGroup player) {
        if (player.spot2!=null) {
            outText+=Main.api.retrieveUserById(player.userId).complete().getAsMention() + "'s First " + player.spot1.hand.toString()+"\n";
            outText+=Main.api.retrieveUserById(player.userId).complete().getAsMention() + "'s Second " + player.spot2.hand.toString()+"\n";
        } else if (player.spot1!=null) {
            outText+=Main.api.retrieveUserById(player.userId).complete().getAsMention() + "'s " + player.spot1.hand.toString()+"\n";
        }
        return outText;
    }

    private void CleanPlayer(SpotGroup player) {
        if (player.isActive()) {
            player.spot1.Clean(shoe);
            if (player.spot2!=null) {
                player.spot2.Clean(shoe);
            }
        }
    }

    private String dealerBlackjackPlayer(String outText, Guild guild, SpotGroup player) {
        if (player.isActive()) {
            if (player.spot1.isBlackjack()) {

                outText+=Main.api.retrieveUserById(player.userId).complete().getAsMention() + " v Dealer: PUSH/TIE\n";
                player.winPush(1);
            } else {

                outText+=Main.api.retrieveUserById(player.userId).complete().getAsMention() + " v Dealer: DEALER WINS\n";
            }
        }
        return outText;
    }
    private String dealerNoBlackjackPlayer(String outText, Guild guild, SpotGroup player) {
        if (player.isActive()) {
            if (player.spot1.isBlackjack(player)) {
                outText+=Main.api.retrieveUserById(player.userId).complete().getAsMention() + " (First Hand) v Dealer : YOU WIN (WITH A BLACKJACK)\n";
                player.winBlackjack();
            } else {
                int dealerValue=dealer.hand.getValue();
                int playerValue=player.spot1.hand.getValue();
                if (playerValue>21) {
                    outText+=Main.api.retrieveUserById(player.userId).complete().getAsMention() + " (First Hand) v Dealer: DEALER WINS\n";
                } else if (dealerValue>21) {
                    outText+=Main.api.retrieveUserById(player.userId).complete().getAsMention() + " (First Hand) v Dealer : YOU WIN\n";
                    player.winNormal(1);
                } else {
                    if (dealerValue>playerValue) {
                        outText+=Main.api.retrieveUserById(player.userId).complete().getAsMention() + " (First Hand) v Dealer: DEALER WINS\n";
                    } else if (dealerValue<playerValue) {
                        outText+=Main.api.retrieveUserById(player.userId).complete().getAsMention() + " (First Hand) v Dealer : YOU WIN\n";
                        player.winNormal(1);
                    } else {
                        outText+=Main.api.retrieveUserById(player.userId).complete().getAsMention() + " (First Hand) v Dealer: PUSH/TIE\n";
                        player.winPush(1);
                    }
                }
            }
            if (player.spot2!=null) {
                int dealerValue=dealer.hand.getValue();
                int playerValue=player.spot2.hand.getValue();
                if (playerValue>21) {
                    outText+=Main.api.retrieveUserById(player.userId).complete().getAsMention() + " (Second Hand) v Dealer: DEALER WINS\n";
                } else if (dealerValue>21) {
                    outText+=Main.api.retrieveUserById(player.userId).complete().getAsMention() + " (Second Hand) v Dealer : YOU WIN\n";
                    player.winNormal(2);
                } else {
                    if (dealerValue>playerValue) {
                        outText+=Main.api.retrieveUserById(player.userId).complete().getAsMention() + " (Second Hand) v Dealer: DEALER WINS\n";
                    } else if (dealerValue<playerValue) {
                        outText+=Main.api.retrieveUserById(player.userId).complete().getAsMention() + " (Second Hand) v Dealer : YOU WIN\n";
                        player.winNormal(2);
                    } else {
                        outText+=Main.api.retrieveUserById(player.userId).complete().getAsMention() + " (Second Hand) v Dealer: PUSH/TIE\n";
                        player.winPush(2);
                    }
                }
            }
        }

        return outText;
    }
}

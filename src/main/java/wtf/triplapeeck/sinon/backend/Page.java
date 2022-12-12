package wtf.triplapeeck.sinon.backend;

import wtf.triplapeeck.sinon.backend.commands.Command;

import java.util.ArrayList;

public class Page {

    public static ArrayList<Page> getPages() {
        ArrayList<Page> pages = new ArrayList<>();
        pages.add(Miscellaneous);
        pages.add(SinonOwner);
        pages.add(SinonAdmin);
        pages.add(CardGames);
        pages.add(Essential);
        pages.add(TripOnly);
        return pages;
    }
    public static Page Miscellaneous = new Page("Miscellaneous");
    public static Page SinonOwner = new Page("Owner");
    public static Page SinonAdmin = new Page("Sinon Admin");
    public static Page CardGames = new Page("Card Games");
    public static Page Essential = new Page("Essential");
    public static Page TripOnly = new Page("Trip-kun");

    public static Page Currency = new Page("Currency (RAK)");
    private String name;
    private ArrayList<Command> commandList = new ArrayList<>();

    public Page(String Name) {
        name=Name;
    }
    public void addCommand(Command command) {
        commandList.add(command);
    }
    public ArrayList<Command> getCommandList() {
        return commandList;
    }

    public String getName() {
        return name;
    }
}

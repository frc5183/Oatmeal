package wtf.triplapeeck.oatmeal.runnable;

import net.dv8tion.jda.api.entities.Guild;
import wtf.triplapeeck.oatmeal.Main;

import javax.swing.text.html.parser.Entity;

public class CheckMissingServers implements NamedRunnable {
    String name = "CHECKMISSINGSERVERS";
    public String getName() {
        return name;
    }
    @Override
    public void run() {
        for (Guild g : Main.api.getGuilds()) {
            /* TODO: make this pseudo-code real.
             if ( !(database.has(g)) ) {
                 database.add(g);
             }
             */
        }
    }
}

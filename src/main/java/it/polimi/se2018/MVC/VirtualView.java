package it.polimi.se2018.MVC;

import it.polimi.se2018.Model;
import it.polimi.se2018.Player;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;
import java.util.Set;

/**
 * this class is the view for the server
 * it occupies to notify client View by calling methods on it
 * contains the CLIENT View pointer
 */
public class VirtualView implements Observer, Observable {

    private Model model = null;

    private CLIView view1 = null;

    private CLIView view2 = null;

    private CLIView view3 = null;

    private CLIView view4 = null;

    private HashMap<Player, CLIView> playerViewHashMap;

    private Controller controller;

    public VirtualView(Model model) {
        this.model = model;
    }


    public void initializeAllGames(Player firstToStart) {         //TODO Concorrente con thread
        //dall'Hash map ottengo la sua CLI

        for (Player p : playerViewHashMap.keySet()) {
            playerViewHashMap.get(p).initializePatternCardChoosingPanel(model.getExtractedWindowPatternCard(4));
        }

        // Join the thread

        // now start the turns and
        // call CLIview for display the table


    }

    public void startTurn(Player p){
        playerViewHashMap.get(p).startTurn(model);

    }

    public void waitForYourTurn(Player p){
        playerViewHashMap.get(p).waitForYourTurn(model);
    }

    public void addClientView(CLIView view) {
        // i can do it while view4 != null.
        // add ordered views from vie1 to vie4
        if (view1 == null) {
            view1 = view;
            view.initializeChoosingPanel();
        } else if (view2 == null) {
            view2 = view;
            view.initializeChoosingPanel();
        } else if (view3 == null) {
            view3 = view;
            view.initializeChoosingPanel();
        } else if (view4 == null) {
            view4 = view;
            view.initializeChoosingPanel();
        } else
            view.notifyAlreadyFourPlayers();
    }

    public void waitForPlayers() {
        while (view4 == null) {
            //thread says to wait
            //TODO limit time wait... threads..
        }

        assignViewToPlayers(view1, view2, view3, view4);
        controller.startGame((ArrayList) playerViewHashMap.keySet());

    }

    public void assignViewToPlayers(CLIView one, CLIView two, CLIView three, CLIView four){
        Player p1 = new Player(); //TODO completa
        Player p2 = new Player();
        Player p3 = new Player();
        Player p4 = new Player();

        playerViewHashMap.put(p1, one);
        playerViewHashMap.put(p2, two);
        playerViewHashMap.put(p3, three);
        playerViewHashMap.put(p4, four);

        Set<Player> orderedPlayers = new ArrayList<Player>;
        orderedPlayers = playerViewHashMap.keySet();
        controller.startGame((ArrayList<Player>) orderedPlayers); //TODO probabile errore per casting
    }
    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }

    @Override
    public void update(java.util.Observable o, Object arg) {

    }
}
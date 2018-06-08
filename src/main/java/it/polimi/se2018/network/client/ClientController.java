package it.polimi.se2018.network.client;

import it.polimi.se2018.*;
import it.polimi.se2018.MVC.CLIView;
import it.polimi.se2018.MVC.GUIView;
import it.polimi.se2018.MVC.View;
import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.parser.ParserWindowPatternCard;
import it.polimi.se2018.network.server.ServerConnection;
import it.polimi.se2018.server_to_client_command.*;
import it.polimi.se2018.utils.ControllerClientInterface;
import it.polimi.se2018.utils.Observer;

import java.util.ArrayList;


public class ClientController implements Observer, ControllerClientInterface {

    /**
     * La classe che viene in contatto con la connessione (Socket o RMI)
     *
     * First role of ClientController is to send commands to Server requesting a move, or a Tool use.
     *
     */

    private View view;

    private Model playerModel;

    private String username;

    private ServerConnection connection;

    /**
     * Contructor:
     */
    public ClientController(int viewChoice, ServerConnection connection){
        this.connection = connection;
        if (viewChoice==1){
            this.view = new CLIView(this);
        }
        else
            this.view = new GUIView();
    }

    public void setUsername(String username){
        this.username = username;
    }

    // Main method for sending commands to Server


    public void update(Object command) {
        try {
            ClientToServerCommand castedCommand = (ClientToServerCommand) command;
            System.out.println("Sto inviando un command " + castedCommand.getMessage());
            castedCommand.setUsername(username);
            connection.send(castedCommand);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /*

    public void performMove(Integer draftPoolPosition, Integer schemaPosition){
        //Create the Command and send to Server to wait for validation
        sendCommand(new MoveChoiceDicePlacement("MoveChoiceDicePlacement", schemaPosition, draftPoolPosition));
    }

    public void useToolCard(Integer toolNumber){
        sendCommand(new MoveChoiceToolCard("MoveChoiceToolCard", toolNumber));
    }

    public void passTurn(){
        sendCommand(new MoveChoicePassTurn("MoveChoicePassTurn"));
    }

    public void chosenWindowPatternCard(WindowPatternCard card){
        sendCommand(new ChosenWindowPatternCard(card.getCardName()));
    }

    public void useToolFirmPastryBrushDRAFTPOOL(Integer dieNewValue, Integer dieOldPosition){
        //Gives the chosen DraftPoolDie (position)
        //and the second word says DRAFTPOOL
        sendCommand(new UseToolFirmPastryBrush("DRAFTPOOL", dieNewValue, dieOldPosition , null));

    }
    public void useToolFirmPastryBrushMOVE(Integer dieNewValue, Integer dieOldPosition, Integer diePosition){
        //Gives the old position of the chosen die from DraftPool
        //Gives the new position in the Schema
        //if gives the new Value (received from the server before!!)
        //and the second word says MOVE
        sendCommand(new UseToolFirmPastryBrush("MOVE", dieNewValue, dieOldPosition, diePosition));
    }

    public void useToolFirmPastryThinnerMOVE(Integer dieNewValue, Integer dieOldPosition, Integer diePosition){
        //Gives the position of the old die in the DraftPool (to reinsert in the diceBag
        //Gives the new position in the Schema
        //if gives the new Value (decided by the Client!)
        //and the second word says MOVE
        sendCommand(new UseToolFirmPastryThinner("MOVE", dieNewValue, dieOldPosition, diePosition));
    }

    public void useToolFirmPastryThinnerDRAFTPOOL(Integer dieNewValue, Integer dieOldPosition){
        //Gives the position of the old die in the DraftPool (to reinsert in the diceBag
        //Gives the new position in the Schema
        //if gives the new Value (decided by the Client!)
        //and the second word says MOVE
        sendCommand(new UseToolFirmPastryThinner("DRAFTPOOL", dieNewValue, dieOldPosition, null));
    }
    */

















    /**
     * Second role of the ClientController is to receive commands from Server and apply them (to Client)
     *
     * I have multiple methods called applyCommand with a different command as parameter
     * Firstly from calling distinguishCommand(), ClientController understand which kind of event server sent and applies it through calling
     * correct applyCommand() methods
     * @param
     */

    public void applyCommand(ServerToClientCommand command){
        System.out.println("Non dovresti essere qui.");
    }

    /**
     * USO TOOL:
     * LA RICHIESTA PARTE DAL CLIENTCONTROLLER->
     * Caso Richiedo il dado dalla dicebag
     * Caso ..
     *
     *
     *
     * TUTTI GLI ALTRI:
     * Faccio la richiesta al ClientController, lei mi controlla che ho abbastanza token , e se sì raccoglie i dati per la mossa ufficiale
     * Se la mossa ufficiale parte, -> Costruisco il metodo Richeista
     */


    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(ChooseWindowPatternCardCommand command){
        //Splitting the string obtaining the correct Wpc
        String[] words = command.getMessage().split(",");
        ArrayList<WindowPatternCard> wpc = new ArrayList<>();

        //Parse the entire list of wpc, remove all the non occurences
        ParserWindowPatternCard parser = new ParserWindowPatternCard();
        wpc.add(0, parser.getCardFromName(words[0]));
        wpc.add(1, parser.getCardFromName(words[1]));
        wpc.add(2, parser.getCardFromName(words[2]));
        wpc.add(3, parser.getCardFromName(words[3]));
        //TODO Controlla che funzioni
        view.chooseWindowPatternCardMenu(wpc);
    }

    /**
     * It shows correct authentication printing the message
     * Applies commands coming from the Server, calling the right graphical methods of the View
     * @param command Command received
     */
    public void applyCommand(AuthenticatedCorrectlyCommand command){ //TODO quando ale finisce la rete
        //AGGIORNO USERNAME
        this.username = command.getMessage();
        view.correctAuthenthication(command.getMessage());
    }

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InitializeMatchCommand command){
    }

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidActionCommand command){
        view.invalidActionMessage(command.getMessage());
    }

    /**
     * The command created an ArrayList of strings in the format "PlayerUsername,playerScore"
     * It gives it to the View.
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(WinCommand command){
        String[] words = command.getMessage().split(" ");
        ArrayList<String> scores = new ArrayList<>();
        for (int i = 1; i < words.length; i++) {
            scores.add(words[i]);
        }
        view.winMessage(scores);

    }

    /**
     * The command created an ArrayList of strings in the format "PlayterUsername,playerScore"
     * It gives it to the View.
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(LoseCommand command){
        String[] words = command.getMessage().split(" ");
        ArrayList<String> scores = new ArrayList<>();
        //Excluding first word (= CommandMessage)
        for (int i = 1; i < words.length; i++) {
            scores.add(words[i]);
        }
        view.loseMessage(command.getPosition() , scores);

    }

    /**
     * Refresh the player model and calls a function of the view that modifies the board with the edits
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(RefreshBoardCommand command) {
        //setPlayerModel(command.getMessage());
        view.update(command);

    }

    public void setPlayerModel(String modelString){
        //TODO: Edit the modelView
    }

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(StartPlayerTurnCommand command){
        view.startTurnMenu();
    }




    //Correct use-> performs the move
    //USING OF TOOLS: Correct Move performed -> has to update the View (The answer will be the new model or the move performed)



    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolCopperFoilReamer command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolCorkLine command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolDiamondSwab command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolEglomiseBrush command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolFirmPastryBrush1 command){
        Integer valueForDraftPoolDie = command.getDieValue();
        //view.showUseToolFirmPastryBrush1(valueForDraftPoolDie);

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolFirmPastryThinner1 command){
        String[] words = command.getMessage().split(" ");
        Die dieFromDiceBag = new Die(COLOR.valueOf(words[1]), command.getDieValue());
        //view.showUseToolFirmPastryBrush1(dieFromDiceBag)
    }

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolFirmPastryBrush2 command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolFirmPastryThinner2 command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolGavel command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolLathekin command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolManualCutter command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolRoughingForceps command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolWheelsPincher command){

    }







    //InvalidToolUse-> The ClientController has to call the box that let the player do a second chance


    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolCopperFoilReamer command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolCorkLine command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolDiamondSwab command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolEglomiseBrush command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolFirmPastryBrush1 command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolFirmPastryBrush2 command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolFirmPastryThinner1 command){

    }

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolFirmPastryThinner2 command){

    }

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolGavel command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolLathekin command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolManualCutter command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolRoughingForceps command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolWheelsPincher command){

    }



    @Override
    public void dispatchCommand(Object command) {
        ServerToClientCommand castedCommand = (ServerToClientCommand) command;
        castedCommand.visit(this);
    }

}


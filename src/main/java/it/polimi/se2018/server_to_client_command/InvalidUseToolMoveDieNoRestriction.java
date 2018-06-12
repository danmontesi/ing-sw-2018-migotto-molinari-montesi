package it.polimi.se2018.server_to_client_command;

import it.polimi.se2018.server_to_client_command.ServerToClientCommand;
import it.polimi.se2018.utils.ControllerClientInterface;

public class InvalidUseToolMoveDieNoRestriction extends ServerToClientCommand {


    public InvalidUseToolMoveDieNoRestriction(String message) {
        this.message=message;
    }

    /**
     * Visitor method for dynamic binding
     * @param clientController
     */
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }


}

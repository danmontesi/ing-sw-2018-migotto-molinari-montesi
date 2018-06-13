package it.polimi.se2018.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

public class AskAuthenticationCommand extends ServerToClientCommand{

    /**
     * Send a request for a new Username (probably because the one chosen is incorrect)
     * String that cointains NameClass
     */

    public AskAuthenticationCommand(String message) {
        this.message = "AskAuthenticationCommand" + " " + message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void visit(ControllerClientInterface clientController) {

    }

}

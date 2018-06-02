package it.polimi.se2018.network.client.socket;


import it.polimi.se2018.network.client.ClientConnection;
import it.polimi.se2018.server_to_client_command.ServerToClientCommand;

public class SocketClientImplementation implements ClientConnection {

    @Override
    public void notifyClient(ServerToClientCommand command) {
        System.out.println("Comando ricevuto " + command.toString());
    }
}
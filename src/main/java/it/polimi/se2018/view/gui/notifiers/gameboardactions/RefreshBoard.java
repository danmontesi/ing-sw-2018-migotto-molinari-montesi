package it.polimi.se2018.view.gui.notifiers.gameboardactions;

import it.polimi.se2018.commands.server_to_client_command.RefreshBoardCommand;

public class RefreshBoard implements GameBoardAction {
    private RefreshBoardCommand modelRepresentation;

    /**
     * Updates the entire game board
     */
    public RefreshBoard(RefreshBoardCommand modelRepresentation) {
        this.modelRepresentation = modelRepresentation;
    }

    @Override
    public void acceptGameBoardVisitor(GameBoardVisitor gameBoardVisitor) {
        gameBoardVisitor.visitGameBoardAction(this);
    }

    public RefreshBoardCommand getModelRepresentation() {
        return modelRepresentation;
    }
}

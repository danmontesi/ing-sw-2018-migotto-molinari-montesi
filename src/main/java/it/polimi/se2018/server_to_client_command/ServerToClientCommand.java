package it.polimi.se2018.server_to_client_command;

import java.io.Serializable;

public class ServerToClientCommand implements Serializable {
    /**
     * This is the abstract class representing all possible command from Server to Client
     */
    protected String message;

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6460847901998831472L;


    public String getMessage(){
        return message;
    }

    }


/*
LEGENDA TOOL
Tool1: aumenta il valore di un dado che scegli dalla riserva di 1
Tool2 : Muovo un dado senza restrizioni di colore
Tool3: " " senza restizioni di valore (numero) del dado
Tool4: Muovi 2 dadi con tutte le restrizioni standard
Tool5: scambio un dado della riserva con uno del roundTrack
Tool6: scegli un dado della riserva e ritiralo, poi devi posizionarlo
Tool7: rilancio i dadi della riserva
Tool8: anticipa il secondo turno del round
Tool9: pongo(dalla riserva) un dado senza restrizione di Adiacenza
Tool10: flip the die
Tool11: metti un dado nel sacco dadi, poi prendine uno e scegli il valore, poi piazzalo!
Tool12: muovi uno o due dadi dello stesso colore di un dado a scelta del RoundTrack,  sul tuo Pattern in un'altra posizione che rispetti tutte le restrizioni
*/



package it.polimi.se2018.view.GUI;

import it.polimi.se2018.commands.client_to_server_command.ChosenWindowPatternCard;
import it.polimi.se2018.view.GUI.Notifiers.GUIReplies.*;
import it.polimi.se2018.view.GUI.Notifiers.WPCChoiceNotifier;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WPCChoiceController extends Observable implements Observer {

    private static final Logger LOGGER = Logger.getLogger(WPCChoiceController.class.getName());

    private GUIView guiViewT;

    private List<ToggleButton> wpcards;

    @FXML
    private Label choose;

    @FXML
    private ToggleButton wpc1;
    @FXML
    private ToggleButton wpc2;
    @FXML
    private ToggleButton wpc3;
    @FXML
    private ToggleButton wpc4;

    @FXML
    private Button start;

    private ToggleGroup wpcs;

    private DropShadow shadow = new DropShadow();
    private DropShadow redShadow = new DropShadow();

    private String selectedWPC;

    public WPCChoiceController() {
        wpcards = new ArrayList<>();
        wpcs = new ToggleGroup();
        selectedWPC = new String();
    }

    public void initialize() {
        WPCChoiceNotifier.getInstance().addObserver(this);
        initWPCards();
        setTGroup();
    }

    public void update(Observable o, Object arg) {
        GUIReply guiReply = (GUIReply)arg;
        GUIVisitor guiVisitor = new GUIVisitor() {
            @Override
            public void visitGUIReply(GUIViewSetting guiViewSetting) {
                guiViewT = guiViewSetting.getGuiView();
            }

            @Override
            public void visitGUIReply(WPCChoice wpcChoice) {
                setWPCards(wpcChoice.getWpcards());
            }

            @Override
            public void visitGUIReply(RefreshBoard refreshBoard) {}
        };
        guiReply.acceptGUIVisitor(guiVisitor);
    }

    private void setTGroup() {
        wpcs.getToggles().addAll(wpc1, wpc2, wpc3, wpc4);
    }

    private void initWPCards() {
        wpcards.add(wpc1);
        wpcards.add(wpc2);
        wpcards.add(wpc3);
        wpcards.add(wpc4);
    }

    private void setWPCards(String cards) {
        Platform.runLater(() -> {
            ArrayList<String> wpcCards = stringToArray(cards);
            int i=0;
            for (ToggleButton wpc : wpcards) {
                String img = wpcCards.get(i);
                String path = "/client/WPC/" + img + ".jpg";
                Image image = new Image(path);
                ImageView iv = new ImageView(image);
                iv.setFitHeight(184);
                iv.setFitWidth(230);
                wpc.setGraphic(iv);
                wpc.setText(img);
                wpc.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> wpc.setEffect(shadow));
                wpc.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                    if (!wpc.isSelected()) wpc.setEffect(null);
                });
                i++;
            }
        });
    }

    @FXML
    public void wpc1Action(){
        if (wpc1.isSelected()) {
            wpc2.setDisable(true);
            wpc3.setDisable(true);
            wpc4.setDisable(true);
        }
        if (!wpc1.isSelected()) {
            wpc2.setDisable(false);
            wpc3.setDisable(false);
            wpc4.setDisable(false);
        }
        selectedWPC = wpc1.getText();
        System.out.println("selected WPC: "+selectedWPC);
    }

    @FXML
    public void wpc2Action(){
        if (wpc2.isSelected()) {
            wpc1.setDisable(true);
            wpc3.setDisable(true);
            wpc4.setDisable(true);
        }
        if (!wpc2.isSelected()) {
            wpc1.setDisable(false);
            wpc3.setDisable(false);
            wpc4.setDisable(false);
        }
        selectedWPC = wpc2.getText();
    }

    @FXML
    public void wpc3Action(){
        if (wpc3.isSelected()) {
            wpc2.setDisable(true);
            wpc1.setDisable(true);
            wpc4.setDisable(true);
        }
        if (!wpc3.isSelected()) {
            wpc2.setDisable(false);
            wpc1.setDisable(false);
            wpc4.setDisable(false);
        }
        selectedWPC = wpc3.getText();
    }

    @FXML
    public void wpc4Action(){
        if (wpc4.isSelected()) {
            wpc2.setDisable(true);
            wpc3.setDisable(true);
            wpc1.setDisable(true);
        }
        if (!wpc4.isSelected()) {
            wpc2.setDisable(false);
            wpc3.setDisable(false);
            wpc1.setDisable(false);
        }
        selectedWPC = wpc4.getText();
    }

    public void closeStage() {
        Stage stage = (Stage)start.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void showGameBoard(){
        if (!wpc1.isSelected() && !wpc2.isSelected() && !wpc3.isSelected() && !wpc4.isSelected()) {
            inputError();
        } else {
            guiViewT.notify(new ChosenWindowPatternCard(selectedWPC));
            Platform.runLater(() -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/gameboard.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage wpcChoiceStage = new Stage();
                    wpcChoiceStage.setScene(new Scene(root));
                    wpcChoiceStage.show();
                    closeStage();
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "An exception was thrown: cannot launch game board", e);
                }
            });
        }
    }

    private ArrayList<String> stringToArray(String s1) {
        String r = s1.replace("[","");
        String r1 = r.replace("]","");
        ArrayList<String> a = new ArrayList<String>(Arrays.asList(r1.split(", ")));
        return a;
    }

    private void inputError() {
        redShadow.setColor(new Color(0.7, 0,0,1));
        choose.setEffect(redShadow);
    }
}

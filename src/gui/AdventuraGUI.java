package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import logika.Hra;
import logika.IHra;
import uiText.TextoveRozhrani;

import java.util.List;

/**
 * @author Jan Jenyisek and Nikita Kot
 */
public class AdventuraGUI extends Application {

    IHra hra;
    private TextField commandTextField;
    private TextArea centerTextArea;
    private OknoProstoru oknoProstoru;
    private PanelVychodu panelVychodu;
    private OknoBatohu oknoBatohu;
    private MenuBar menuBar;
    private Stage primaryStage;
    private ComboBox comboBox;

    @Override
    /**
     * the main method for launching the game
     */
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        hra = new Hra();

        BorderPane border = new BorderPane();

        Scene scene = new Scene(border, 900, 500);

        primaryStage.setTitle("Adventura");
        primaryStage.setScene(scene);
        primaryStage.show();
        oknoBatohu = new OknoBatohu(hra);
        oknoProstoru = new OknoProstoru(hra);

        VBox box = new VBox();
        initMenu();
        box.getChildren().addAll(menuBar, oknoProstoru.getHorniPanel());

        border.setTop(box);
        border.setCenter(centralniPanel());

        border.setBottom(dolniPanel());
        border.setLeft(oknoBatohu.getFlowPane());

        commandTextField.requestFocus();
        panelVychodu = new PanelVychodu(hra, centerTextArea);
        border.setRight(panelVychodu.getListView());

    }

    /**
     * @param args - if param is "-text"- the game launches in command line
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            launch(args);
        } else {
            if (args[0].equals("-text")) {
                IHra hra = new Hra();
                TextoveRozhrani ui = new TextoveRozhrani(hra);
                ui.hraj();
            } else {
                System.out.println("Neplatny parametr");
            }
        }

    }

    /**
     * method for bottom flowpane initialization
     * @return FlowPane
     */
    private FlowPane dolniPanel() {
        FlowPane dolniFlowPane = new FlowPane();
        dolniFlowPane.setAlignment(Pos.CENTER);

        comboBox = new ComboBox<>();

        comboBox.setPromptText("command");
        List<String> prikazList = hra.seznamPrikazu();
        for (String s : prikazList) {
            comboBox.getItems().add(s);
        }
        dolniFlowPane.getChildren().add(comboBox);

        commandTextField = new TextField();
        commandTextField.setPromptText("parametr");

        dolniFlowPane.getChildren().add(commandTextField);

        commandTextField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String radek = (String) comboBox.getValue();
                String line = commandTextField.getText();
                String text = hra.zpracujPrikaz(radek + " " + line);
                centerTextArea.appendText("\n\n" + line + "\n");
                centerTextArea.appendText("\n" + text + "\n");
                commandTextField.setText("");
                if (hra.konecHry()) {
                    centerTextArea.appendText(hra.vratEpilog());
                    commandTextField.setEditable(false);

                    System.exit(0);
                }

            }
        });

        return dolniFlowPane;
    }

    /**
     * method for text area initialization
     * @return TextArea
     */
    private TextArea centralniPanel() {
        centerTextArea = new TextArea();
        centerTextArea.setEditable(false);
        centerTextArea.setText(hra.vratUvitani());
        return centerTextArea;
    }

    /**
     * menu initialization
     */
    private void initMenu() {
//        menu lista
        menuBar = new MenuBar();
        menuBar.useSystemMenuBarProperty().set(true);

//        menu tlacitko
        Menu menuPolozkaSoubor = new Menu("_File");

//        menu polozka
        MenuItem novaHra = new MenuItem("New game");

        novaHra.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            /**
             * handler for staring new game
             */
            public void handle(ActionEvent event) {
                hra = new Hra();
                oknoProstoru.setHra(hra);
                hra.getHerniPlan().registraceObserver(oknoProstoru);
                oknoProstoru.aktualizuj();
                panelVychodu.setHra(hra);
                hra.getHerniPlan().registraceObserver(panelVychodu);
                panelVychodu.aktualizuj();
                oknoBatohu.setHra(hra);
                oknoBatohu.aktualizuj();
                centerTextArea.setText(hra.vratUvitani());
            }
        });

//        klavesova zkratka
        novaHra.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));

        MenuItem konec = new MenuItem("_Exit");
        konec.setMnemonicParsing(true);

        konec.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                System.exit(0);

            }
        });

        menuPolozkaSoubor.getItems().addAll(novaHra, konec);

        Menu help = new Menu("Help");

        MenuItem oProgramu = new MenuItem("About");

        oProgramu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Alert alertOkno = new Alert(Alert.AlertType.INFORMATION);

                alertOkno.setTitle("About");
                alertOkno.setHeaderText("GUI adventura");
                alertOkno.setContentText("Adventura 06.11.2016");
                alertOkno.initOwner(primaryStage);
                alertOkno.showAndWait();

            }
        });

        MenuItem napoveda = new MenuItem("Help");

        napoveda.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Stage stage = new Stage();
                stage.setTitle("Help");
                WebView webView = new WebView();

                webView.getEngine().load(AdventuraGUI.class.getResource("/zdroje/napoveda.html").toExternalForm());

                stage.setScene(new Scene(webView, 500, 500));
                stage.show();

            }
        });

        help.getItems().addAll(oProgramu, napoveda);

        menuBar.getMenus().addAll(menuPolozkaSoubor, help);

    }
}

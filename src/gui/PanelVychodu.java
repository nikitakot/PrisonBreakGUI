/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import logika.IHra;
import util.ObserverZmeny;

/**
 * @author xzenj02
 */
public class PanelVychodu implements ObserverZmeny {

    private IHra hra;
    private TextArea centerTextArea;

    private ListView<String> list;
    private ObservableList<String> data;

    public PanelVychodu(IHra hra, TextArea centerTextArea) {
        this.hra = hra;
        hra.getHerniPlan().registraceObserver(this);
        initGUI();
        this.centerTextArea = centerTextArea;
    }

    public void setHra(IHra hra) {
        this.hra = hra;
    }

    private void initGUI() {

        list = new ListView<>();

        list.setOnMouseClicked((event) -> {
            String selected = list.getSelectionModel().getSelectedItem();
            String text = hra.zpracujPrikaz("go" + " " + selected);
            String line = "go" + " " + selected;
            centerTextArea.appendText("\n\n" + line + "\n");
            centerTextArea.appendText("\n" + text + "\n");
        });

        data = FXCollections.observableArrayList();

        list.setItems(data);
        list.setPrefWidth(100);

        String vychody = hra.getHerniPlan().getAktualniProstor().popisVychodu();

        String[] vychodyZProstoru = vychody.split(" ");
        for (int i = 1; i < vychodyZProstoru.length; i++) {
            data.add(vychodyZProstoru[i]);

        }

        aktualizuj();

    }

    public ListView<String> getListView() {
        return list;
    }

    @Override
    public void aktualizuj() {
        String vychody = hra.getHerniPlan().getAktualniProstor().popisVychodu();
        data.clear();
        String[] oddeleneVychody = vychody.split(" ");
        for (int i = 1; i < oddeleneVychody.length; i++) {
            data.add(oddeleneVychody[i]);

        }
    }

}

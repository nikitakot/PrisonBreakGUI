/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import logika.IHra;
import util.ObserverZmeny;

/**
 * @author xzenj02 and Nikita Kot
 */
public class OknoProstoru implements ObserverZmeny {

    private IHra hra;

    private FlowPane horniPanel;
    private TextArea popisProstoru;
    Circle tecka;

    public OknoProstoru(IHra hra) {
        this.hra = hra;
        hra.getHerniPlan().registraceObserver(this);
        initGUI();
    }

    public void setHra(IHra hra) {
        this.hra = hra;
    }

    public FlowPane getHorniPanel() {
        return horniPanel;
    }

    private void initGUI() {
        horniPanel = new FlowPane();

        AnchorPane obrazekPane = new AnchorPane();
        ImageView obrazek = new ImageView(new Image(AdventuraGUI.class.getResourceAsStream("/zdroje/planek.jpg"), 400, 250, false, false));

        tecka = new Circle(5, Paint.valueOf("green"));


        obrazekPane.getChildren().addAll(obrazek, tecka);

        popisProstoru = new TextArea();
        popisProstoru.setPrefWidth(500);
        popisProstoru.setEditable(false);
        popisProstoru.setText(hra.getHerniPlan().getAktualniProstor().dlouhyPopis());

        horniPanel.getChildren().addAll(popisProstoru, obrazekPane);
        aktualizuj();
    }

    @Override
    public void aktualizuj() {
        popisProstoru.setText(hra.getHerniPlan().getAktualniProstor().dlouhyPopis());
        AnchorPane.setTopAnchor(tecka, hra.getHerniPlan().getAktualniProstor().getTop());
        AnchorPane.setLeftAnchor(tecka, hra.getHerniPlan().getAktualniProstor().getLeft());

    }

}

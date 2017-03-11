/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import logika.Batoh;
import logika.IHra;
import logika.Vec;
import util.ObserverZmeny;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kotn0
 */
public class OknoBatohu implements ObserverZmeny {

    private Batoh batoh;
    private IHra hra;

    public void setHra(IHra hra) {
        this.hra = hra;
        batoh = hra.getHerniPlan().getBatoh();
        batoh.registraceObserver(this);
    }

    private ObservableList<String> data;
    private List<ImageView> imageView;
    private FlowPane flowPane;

    public OknoBatohu(IHra hra) {
        this.hra = hra;
        batoh = hra.getHerniPlan().getBatoh();
        batoh.registraceObserver(this);
        initGUI();
    }

    @Override
    public void aktualizuj() {
        //flowPane.getChildren().removeAll(imageView);
        flowPane.getChildren().clear();
        imageView.clear();
        for (Vec i : batoh.getObsah()) {
            ImageView m = new ImageView(new Image(i.getImage()));
            imageView.add(m);
        }

        flowPane.getChildren().addAll(imageView);

    }

    private void initGUI() {
        imageView = new ArrayList<>();
        for (Vec i : batoh.getObsah()) {
            ImageView m = new ImageView(new Image(i.getImage()));
            imageView.add(m);
        }
        flowPane = new FlowPane();
        flowPane.getChildren().addAll(imageView);
    }

    public FlowPane getFlowPane() {
        return flowPane;
    }

}

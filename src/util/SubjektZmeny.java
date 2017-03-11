/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 * @author xzenj02
 */
public interface SubjektZmeny {

    void registraceObserver(ObserverZmeny observer);

    void odebraniObserver(ObserverZmeny observer);

    void upozorniPozorovatele();

}

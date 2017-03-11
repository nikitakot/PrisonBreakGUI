/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

import util.ObserverZmeny;
import util.SubjektZmeny;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************************
 * Instance třídy Batoh představují ...
 *
 * @author Nikita Kot
 * @version ls 2016
 */
public class Batoh implements SubjektZmeny {
    //== Datové atributy (statické i instancí)======================================

    private static final int MAX_VECI = 2;    // Maximální počet věcí v batohu
    private List<Vec> obsah;            // Seznam věcí v batohu
    private List<ObserverZmeny> seznamObservers;

    //== Konstruktory a tovární metody =============================================

    /**
     * *************************************************************************
     * Konstruktor ....
     */
    public Batoh() {
        seznamObservers = new ArrayList<>();
        obsah = new ArrayList<Vec>();
    }

    //== Nesoukromé metody (instancí i třídy) ======================================

    /**
     * Metoda pro vložení věci.
     *
     * @param vec vkládaná věc
     * @return Vec vrátí tu samou věc, pokud se ji podaří přidat do batohu.
     * Null, pokud ne.
     */
    public Vec vlozVec(Vec pridavana) {
        if (jeMisto()) {
            obsah.add(pridavana);
            upozorniPozorovatele();
            return pridavana;
        }
        upozorniPozorovatele();
        return null;
    }

    /**
     * Zjišťuje, zda je v batohu ještě místo.
     *
     * @return true -pokud místo je.
     */
    public boolean jeMisto() {
        return obsah.size() < MAX_VECI;
    }

    /**
     * Zjišťuje, zda je věc v batohu.
     *
     * @param hledana hledana vec
     * @return true pokud se věc v batohu nachází, jinak false
     */
    public boolean obsahujeVec(String hledana) {
        for (Vec aktualni : obsah) {
            if (aktualni.getNazev().equals(hledana)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Vrací seznam věcí v batohu
     *
     * @return seznam grafický výpis věcí v batohu
     */
    public String getVeci() {
        String seznam = "";
        for (Vec aktualni : obsah) {
            seznam += " " + aktualni.getNazev();
        }
        return seznam;
    }

    /**
     * Metoda najde věc, na kterou chceme odkázat
     *
     * @param nazev -název věci, kterou chceme najít
     * @return hledana -odkaz na nalezenou věc. Je null, pokud věc nebyla
     * nalezena
     */
    public Vec getVec(String nazev) {
        Vec hledana = null;
        for (Vec aktualni : obsah) {
            if (aktualni.getNazev().equals(nazev)) {
                hledana = aktualni;
                break;
            }
        }

        return hledana;
    }

    /**
     * Odstrani veci z inventare
     *
     * @param mazana odstraňovaná věc
     * @return smazana odstraněná věc. Je null, pokud věc nebyla v batohu
     * nalezena
     */
    public Vec smazVec(String mazana) {
        Vec smazana = null;
        for (Vec aktualni : obsah) {
            if (aktualni.getNazev().equals(mazana)) {
                smazana = aktualni;
                obsah.remove(aktualni);
                upozorniPozorovatele();
                break;
            }
        }
        return smazana;
    }

    public List<Vec> getObsah() {
        return obsah;
    }

    /**
     * Metoda vrací maximální počet věcí, které lze přidat do batohu.
     *
     * @return -počet věcí
     */
    public int getMaxVeci() {
        return MAX_VECI;
    }

    //== Soukromé metody (instancí i třídy) ========================================
    @Override
    public void registraceObserver(ObserverZmeny observer) {
        seznamObservers.add(observer);
    }

    @Override
    public void odebraniObserver(ObserverZmeny observer) {
        seznamObservers.remove(observer);
    }

    @Override
    public void upozorniPozorovatele() {
        for (ObserverZmeny seznamObserver : seznamObservers) {
            seznamObserver.aktualizuj();
        }

    }
}

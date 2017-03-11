package logika;

import util.ObserverZmeny;
import util.SubjektZmeny;

import java.util.ArrayList;
import java.util.List;

/**
 * Class HerniPlan - třída představující mapu a stav adventury.
 * <p>
 * Tato třída inicializuje prvky ze kterých se hra skládá: vytváří všechny
 * prostory, propojuje je vzájemně pomocí východů a pamatuje si aktuální
 * prostor, ve kterém se hráč právě nachází.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Nikita Kot
 * @version ls 2016
 */
public class HerniPlan implements SubjektZmeny {

    private Prostor aktualniProstor;
    private Batoh batoh;
    private boolean sJohnem;

    private List<ObserverZmeny> seznamObservers;

    /**
     * Konstruktor který vytváří jednotlivé prostory a propojuje je pomocí
     * východů. Jako výchozí aktuální prostor nastaví halu.
     */
    public HerniPlan() {
        seznamObservers = new ArrayList<>();
        zalozProstoryHry();
        batoh = new Batoh();
    }

    /**
     * Vytváří jednotlivé prostory a propojuje je pomocí východů. Jako výchozí
     * aktuální prostor nastaví domeček.
     */
    private void zalozProstoryHry() {
        // vytvářejí se jednotlivé prostory
        Prostor cell = new Prostor("cell", "my cell, I was imprisoned here for 10 years, I hate this place...", 25.0, 200.0);
        Prostor johnsCell = new Prostor("johns_cell", "hey, it's Johns cell!", 50.0, 300.0);
        Prostor exit = new Prostor("exit", "finally we are free!", 170.0, 350.0);
        Prostor gunStore = new Prostor("gunstore", "it's gunstore...with guns!", 200.0, 200.0);
        Prostor guard = new Prostor("guard", "it's guard room, what are we doing here? it's a bit risky to be here", 200.0, 100.0);
        Prostor nonamesCell = new Prostor("nonames_cell", "just a cell with a strange guy", 100.0, 80.0);
        Prostor mainHall = new Prostor("main_hall", "it's the main hall, dude", 100.0, 200.0);
        // přiřazují se průchody mezi prostory (sousedící prostory)
        cell.setVychod(mainHall);
        johnsCell.setVychod(mainHall);
        gunStore.setVychod(mainHall);
        guard.setVychod(mainHall);
        nonamesCell.setVychod(mainHall);
        mainHall.setVychod(cell);
        mainHall.setVychod(johnsCell);
        mainHall.setVychod(exit);
        mainHall.setVychod(gunStore);
        mainHall.setVychod(guard);
        mainHall.setVychod(nonamesCell);
        //postavy
        Postava noname = new Postava("Noname", "Henry: Hi man, my name is Henry, are you trying to escape? \n"
                + "I can help you! The pasword to the gunstore is \"5505\".\nTake a pistol there and shoot down the guardian!");
        Postava guardian = new Postava("Guardian", "Hey...be quiet...I don't think it's a good idea to talk with him, \n"
                + "we're actually going to escape from here.\nI don't think he will like it.");
        Postava john = new Postava("John", "John: Hey bro! Finally you are here! Let's get out of here!");
        //veci
        Vec pistol = new Vec("pistol", true, "zdroje/pistol.png");
        Vec key = new Vec("key", true, "zdroje/key.png");
        Vec stone = new Vec("stone", false, "https://yt3.ggpht.com/-DzBGmCl67TU/AAAAAAAAAAI/AAAAAAAAAAA/pJaSJPhJ6co/s88-c-k-no-rj-c0xffffff/photo.jpg");//jenom pro ujisteni ze nemuzete sebrat vsechno, ve hre je k nicemu
        Vec object = new Vec("object", true, "zdroje/object.png");//jenom pro ujisteni ze fungue kapacita batohu, ve hre je k nicemu
        //vkladani do prostoru a postav
        gunStore.vlozVec(object);
        gunStore.vlozVec(pistol);
        gunStore.vlozVec(stone);
        guardian.setVec(key);
        johnsCell.vlozPostavu(john);
        guard.vlozPostavu(guardian);
        nonamesCell.vlozPostavu(noname);
        //nastaveni klicu a hesel
        johnsCell.zamknout(true);
        johnsCell.nastavKlic(key);
        aktualniProstor = cell;  // hra začíná v celi
    }

    /**
     * Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.
     *
     * @return aktuální prostor
     */
    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }

    public Batoh getBatoh() {
        return this.batoh;
    }

    /**
     * Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi
     * prostory
     *
     * @param prostor nový aktuální prostor
     */
    public void setAktualniProstor(Prostor prostor) {
        aktualniProstor = prostor;
        upozorniPozorovatele();
    }

    public boolean jeVyhra() {
        return aktualniProstor.getNazev().equals("exit");
    }

    /**
     * Metoda sJohnem
     *
     * @return vrací boolean hodnotu sJohnem
     */
    public boolean sJohnem() {
        return sJohnem;
    }

    /**
     * Metoda setSJohnem
     *
     * @param nastaví sJohnem jako true pokud hrač promluví(příkaz talk_to) s
     *                Johnem
     */
    public void setSJohnem(boolean sJohnem) {
        this.sJohnem = sJohnem;
    }

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

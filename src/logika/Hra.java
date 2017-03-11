package logika;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Třída Hra - třída představující logiku adventury.
 * <p>
 * Toto je hlavní třída logiky aplikace. Tato třída vytváří instanci třídy
 * HerniPlan, která inicializuje mistnosti hry a vytváří seznam platných příkazů
 * a instance tříd provádějící jednotlivé příkazy. Vypisuje uvítací a ukončovací
 * text hry. Také vyhodnocuje jednotlivé příkazy zadané uživatelem.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Nikita Kot
 * @version ls 2016
 */
public class Hra implements IHra {

    private SeznamPrikazu platnePrikazy;    // obsahuje seznam přípustných příkazů
    private HerniPlan herniPlan;
    private boolean konecHry = false;

    /**
     * Vytváří hru a inicializuje místnosti (prostřednictvím třídy HerniPlan) a
     * seznam platných příkazů.
     */
    public Hra() {
        herniPlan = new HerniPlan();
        platnePrikazy = new SeznamPrikazu();
        platnePrikazy.vlozPrikaz(new PrikazNapoveda(platnePrikazy));
        platnePrikazy.vlozPrikaz(new PrikazJdi(herniPlan, this));
        platnePrikazy.vlozPrikaz(new PrikazKonec(this));
        platnePrikazy.vlozPrikaz(new PrikazSeber(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazVeci(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazZahod(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazOdemkni(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazMluv(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazZastrel(herniPlan, this));

    }

    /**
     * Vrátí úvodní zprávu pro hráče.
     */
    public String vratUvitani() {
        return "Hi!\n"
                + "This is a text-quest computer game The Prison Break.\n"
                + "If you have never played this game and you don't know \nwhat to do type in console \"help\".\n"
                + "\n"
                + herniPlan.getAktualniProstor().dlouhyPopis();
    }

    /**
     * Vrátí závěrečnou zprávu pro hráče.
     */
    public String vratEpilog() {
        if (herniPlan.jeVyhra()) {
            return "You win, we're free! Thanks for help dude!";
        }
        return "Thanks for playing this game. Bye-Bye.";
    }

    /**
     * Vrací true, pokud hra skončila.
     */
    public boolean konecHry() {

        return konecHry;

    }

    /**
     * Metoda zpracuje řetězec uvedený jako parametr, rozdělí ho na slovo
     * příkazu a další parametry. Pak otestuje zda příkaz je klíčovým slovem
     * např. jdi. Pokud ano spustí samotné provádění příkazu.
     *
     * @param radek text, který zadal uživatel jako příkaz do hry.
     * @return vrací se řetězec, který se má vypsat na obrazovku
     */
    public String zpracujPrikaz(String radek) {
        String[] slova = radek.split("[ \t]+");
        String slovoPrikazu = slova[0];
        String[] parametry = new String[slova.length - 1];
        for (int i = 0; i < parametry.length; i++) {
            parametry[i] = slova[i + 1];
        }
        String textKVypsani = " .... ";
        if (platnePrikazy.jePlatnyPrikaz(slovoPrikazu)) {
            IPrikaz prikaz = platnePrikazy.vratPrikaz(slovoPrikazu);
            textKVypsani = prikaz.proved(parametry);
            if (herniPlan.jeVyhra()) {

                konecHry = true;
            }
        } else {
            textKVypsani = "Unknown command.";
        }
        return textKVypsani;
    }

    /**
     * Nastaví, že je konec hry, metodu využívá třída PrikazKonec, mohou ji
     * použít i další implementace rozhraní Prikaz.
     *
     * @param konecHry hodnota false= konec hry, true = hra pokračuje
     */
    void setKonecHry(boolean konecHry) {
        this.konecHry = konecHry;
    }

    /**
     * Metoda vrátí odkaz na herní plán, je využita hlavně v testech, kde se
     * jejím prostřednictvím získává aktualní místnost hry.
     *
     * @return odkaz na herní plán
     */
    public HerniPlan getHerniPlan() {
        return herniPlan;
    }

    public List<String> seznamPrikazu() {
        String prikazy = platnePrikazy.vratNazvyPrikazu();
        List<String> temp = Arrays.asList(prikazy.split("\\s+"));
        ArrayList<String> seznamPrikazu = new ArrayList<>();
        seznamPrikazu.addAll(temp);

        return seznamPrikazu;
    }

}

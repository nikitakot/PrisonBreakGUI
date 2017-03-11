/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/**
 * *****************************************************************************
 * Instance třídy PrikazOdemkni představují ...
 *
 * @author Nikita Kot
 * @version ls 2016
 */

/**
 * Třída PrikazOdemkni implementuje pro hru příkaz odemkni.
 */
public class PrikazOdemkni implements IPrikaz {

    private static final String NAZEV = "open";
    private HerniPlan plan;

    /**
     * Konstruktor třídy
     *
     * @param plan herní plán, je potřeba zjistit, zda jsem v místnosti vedle
     *             místnosti, která se má odemknout
     */
    public PrikazOdemkni(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     * Provádí příkaz "open", jak pro prostotry s heslem, tak i pro kliče.
     * Zjišťuji, zda z aktuální místnosti je východ do zadané místnosti. Pokud
     * místnost existuje a je zamčená, tak se zjistí, zda v batohu je potřebný
     * klíč. Pokud ano, odemkne místnost. Pokud místnot je zaheslovaná,
     * potřebuje od hrače napsát do console heslo. Jestli heslo bude zadano
     * spravně, prostor se odemkne.
     *
     * @param parametry - jako parametr obsahuje jméno místnosti, do které se má
     *                  jít.
     * @return zpráva, kterou vypíše hra hráči
     */
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední místnost), tak ....
            return "What should I open? Type a room name too.";
        }
        String prostor = parametry[0];
        // hledám zadanou místnost mezi východy
        Prostor sousedniMistnost = plan.getAktualniProstor().vratSousedniProstor(prostor);

        if (sousedniMistnost == null) {
            return "I can't open the " + prostor + " from the " + plan.getAktualniProstor().getNazev() + ", it's impossible!";
        } else if (sousedniMistnost.jeZamceno() && sousedniMistnost.getKlic() != null) {
            if (plan.getBatoh().obsahujeVec(sousedniMistnost.getKlic().getNazev())) {
                sousedniMistnost.zamknout(false);
                return "The door to the "
                        + prostor + " is opend. We can get there.";
            } else {
                return "To open the " + prostor + " I need to have a "
                        + sousedniMistnost.getKlic().getNazev()
                        + " with me.";
            }
        } else {
            return "Room " + prostor + " was already opened!!!";
        }
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *
     * @ return nazev prikazu
     */
    public String getNazev() {
        return NAZEV;
    }

}

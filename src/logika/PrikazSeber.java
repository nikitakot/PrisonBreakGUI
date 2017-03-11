/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/**
 * *****************************************************************************
 * Instance třídy PrikazSeber představují ...
 *
 * @author Nikita Kot
 * @version ls 2016
 */
public class PrikazSeber implements IPrikaz {

    //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV = "pick_up";
    private HerniPlan plan;
    private Batoh batoh;
    //== Konstruktory a tovární metody =============================================

    /**
     * *************************************************************************
     * Konstruktor třidy
     *
     * @param plan
     */
    public PrikazSeber(HerniPlan plan) {
        this.plan = plan;
        this.batoh = plan.getBatoh();
    }

    //== Nesoukromé metody (instancí i třídy) ======================================

    /**
     * Metoda provede přikaz "pick_up". Zkontroluje jestli věc se dá sebrat a
     * je-li v aktualní místnosti a vloží jí do batohu
     *
     * @param parametry nazev věci
     * @return zpáva
     */
    @Override
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return "What should I pick up, dude? Type an object name too.";
        }

        String nazevSbiraneho = parametry[0];

        // zkoušíme přejít do sousedního prostoru
        Prostor aktualni = plan.getAktualniProstor();
        Vec sbirana = aktualni.odeberVec(nazevSbiraneho);

        if (sbirana == null) {
            return "No way, man, I don't see it here.";
        } else {
            if (sbirana.jePrenositelna() && batoh.jeMisto()) {
                return batoh.vlozVec(sbirana).getNazev() + " was picked up.";
            } else {
                aktualni.vlozVec(sbirana);
                return "Sh*t! I can't take it with me" + (batoh.jeMisto() ? "." : ", my bag is full.");
            }
        }
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *
     * @ return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
    //== Soukromé metody (instancí i třídy) ========================================

}

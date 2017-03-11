/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/**
 * *****************************************************************************
 * Instance třídy PrikazVeci představují ...
 *
 * @author Nikita Kot
 * @version ls 2016
 */
public class PrikazVeci implements IPrikaz {
    //== Datové atributy (statické i instancí)======================================

    private static final String NAZEV = "bag_content";
    private HerniPlan herniPlan;

    //== Konstruktory a tovární metody =============================================

    /**
     * *************************************************************************
     * Konstruktor ....
     *
     * @param herniPlan herní plán, ve kterém se bude ve hře "chodit"
     */
    public PrikazVeci(HerniPlan herniPlan) {
        this.herniPlan = herniPlan;
    }

    //== Nesoukromé metody (instancí i třídy) ======================================

    /**
     * Provádí příkaz "bag_content". Vypíše všechny věci, které jsou v batohu.
     *
     * @return zpráva, kterou vypíše hra hráči
     */
    @Override
    public String proved(String... parametry) {
        if (herniPlan.getBatoh().getVeci().isEmpty()) {
            // pokud je batoh prázdný
            return "My bag is empty, man.";
        } else {
            // pokud je v batohu jedna a více věcí.
            return "Wow, look at this cool stuff in my bag:" + herniPlan.getBatoh().getVeci() + ".";
        }
    }

    /**
     * Metoda vrací název příkazu
     *
     * @ return název příkazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }

    //== Soukromé metody (instancí i třídy) ========================================
}

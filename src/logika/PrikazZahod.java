/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/**
 * *****************************************************************************
 * Instance třídy PrikazZahod představují ...
 *
 * @author Nikita Kot
 * @version ls 2016
 */
public class PrikazZahod implements IPrikaz {
    //== Datové atributy (statické i instancí)======================================

    private static final String NAZEV = "throw";
    private HerniPlan herniPlan;

    //== Konstruktory a tovární metody =============================================

    /**
     * *************************************************************************
     * Konstruktor ....
     *
     * @param herniPlan herní plán, ve kterém se bude ve hře "chodit"
     */
    public PrikazZahod(HerniPlan herniPlan) {
        this.herniPlan = herniPlan;
    }

    //== Nesoukromé metody (instancí i třídy) ======================================

    /**
     * Provádí příkaz "throw". Pokud v batohu věc není vypíše chybovou hlášku.
     * Jinak věc odhodí do aktuálního prostoru. Lze zahodit jen jednu věc
     * najednou.
     *
     * @param parametry -jako parametr se zadává jméno odhazované věci
     * @return zpráva, kterou vypíše hra hráči
     */
    @Override
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            return "What should I throw man? Type a object name too.";
        }

        String nazevVeci = parametry[0];
        Prostor aktualniProstor = herniPlan.getAktualniProstor();
        Batoh batoh = herniPlan.getBatoh();
        Vec mazana = batoh.getVec(nazevVeci);

        if (mazana == null) {
            // pokud mazana věc není v batohu.
            return "Wow, how can I throw smth, that I don't have in my bag? You'r crazy?";
        } else {
            // pokud je věc smazána z batohu, přesune se do aktualního prostoru
            mazana = batoh.smazVec(nazevVeci);
            aktualniProstor.vlozVec(mazana);
            return nazevVeci + " was throwed.";
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

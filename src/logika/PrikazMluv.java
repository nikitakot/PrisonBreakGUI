/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/**
 * *****************************************************************************
 * Instance třídy PrikazMluv představují ...
 *
 * @author Nikita Kot
 * @version ls 2016
 */
public class PrikazMluv implements IPrikaz {

    //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV = "talk_to";
    private HerniPlan plan;
    //== Konstruktory a tovární metody =============================================

    /**
     * *************************************************************************
     * Konstruktor třídy
     *
     * @param herniplan
     */
    public PrikazMluv(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     * Provadí příkaz talk_to, vratí proslov postavy, pokud postava je naživu.
     * Nastavuje hodnotu sJhonem na true, aby se dalo jít do exitu a výhrat.
     *
     * @param parametry Parametr - jmeno postavy
     * @return Návratová hodnota - zprava, kterou vypíše hra hráči
     */
    @Override
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return "Hey! How can I talk to nobody? Type a person name too.";
        }

        String jmeno = parametry[0];

        // zkoušíme přejít do sousedního prostoru
        Prostor aktualni = plan.getAktualniProstor();
        Postava person = aktualni.vratPostavu(jmeno);

        if (person == null) {
            return "No way man, I don't see this person here.";
        } else {
            if (person.jeMrtvy()) {
                return "He's dead, man...I don't think he could tell us smth.";
            } else {
                if (person.getJmeno().equals("John")) {
                    plan.setSJohnem(true);
                    aktualni.odeberPostavu(person.getJmeno());
                    return person.getProslov() + "\n"
                            + "*" + person.getJmeno() + " now with you, just move to the exit!*";
                }
                return person.getProslov();
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

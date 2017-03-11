/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/**
 * *****************************************************************************
 * Třída PrikazZastrel implementuje pro hru příkaz shoot_down. Tato třída je
 * součástí jednoduché textové hry.
 *
 * @author Nikita Kot
 * @version ls 2016
 */
public class PrikazZastrel implements IPrikaz {

    //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV = "shoot_down";
    private HerniPlan plan;
    private Batoh batoh;
    private Hra hra;
    //== Konstruktory a tovární metody =============================================

    /**
     * *************************************************************************
     * Konstruktor třidy
     *
     * @param plan
     * @param hra
     */
    public PrikazZastrel(HerniPlan plan, Hra hra) {
        this.plan = plan;
        this.batoh = plan.getBatoh();
        this.hra = hra;
    }

    /**
     * Provádí příkaz "shoot_down". Pokud v batohu je pistola, příkaz se
     * provede, pokud není, vratí se zpráva že nemáte pistolu abyste střileli.
     * Pokud pistola v batohu je, v parametru se předavá jmeno postavy, kterou
     * chcete zastřelit a u postavy se nastaví hodnota jeMrtvy na true. Pokud
     * zabijete Johna hra se skončí.
     *
     * @param parametry - jmeno postavy
     * @return zpráva, kterou vypíše hra hráči
     */
    @Override
    public String proved(String... parametry) {
        if (batoh.obsahujeVec("pistol")) {
            if (parametry.length == 0) {
                // pokud chybí druhé slovo (sousední prostor), tak ....
                return "Who should I shoot? Type a person name too.";
            }

            String jmeno = parametry[0];

            // zkoušíme přejít do sousedního prostoru
            Prostor aktualni = plan.getAktualniProstor();
            Postava person = aktualni.vratPostavu(jmeno);

            if (person == null) {
                return "No way man, I can't shoot it down.";
            } else {
                if (person.jeMrtvy()) {
                    return "He's already dead, man...";
                } else {
                    if (person.getJmeno().equals("John")) {
                        person.setMrtvy(true);
                        hra.setKonecHry(true);
                        return "F*ck! You killed Johny! The game is over you bastard!";
                    }
                    if (person.dropVec() != null) {
                        person.setMrtvy(true);
                        aktualni.vlozVec(person.dropVec());
                        return person.getJmeno() + " was shooted down.\nLook! A " + person.dropVec().getNazev() + " dropped from him!";
                    }
                    person.setMrtvy(true);
                    return person.getJmeno() + " was shooted down.";
                }
            }
        } else {
            return "Hey man, I can't shoot without a gun...\nWe need to find a pistol at first.";
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

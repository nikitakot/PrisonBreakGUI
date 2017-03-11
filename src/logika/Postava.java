/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/**
 * *****************************************************************************
 * Instance třídy Postava představují ...
 *
 * @author Nikita Kot
 * @version ls 2016
 */
public class Postava {

    //== Datové atributy (statické i instancí)======================================
    private String jmeno;
    private String proslov;
    private boolean mrtvy;
    private Vec vec;
    //== Konstruktory a tovární metody =============================================

    /**
     * *************************************************************************
     * Konstruktor třidy
     *
     * @param -       jmeno postavy
     * @param proslov (co bude říkat)
     */
    public Postava(String jmeno, String proslov) {
        this.jmeno = jmeno;
        this.proslov = proslov;
    }

    //== Nesoukromé metody (instancí i třídy) ======================================

    /**
     * Metoda getJmeno vrací jmeno postavy
     *
     * @return Návratová hodnota
     */
    public String getJmeno() {
        return jmeno;
    }

    /**
     * Metoda getProslov vrací proslov postavy
     *
     * @return Návratová hodnota
     */
    public String getProslov() {
        return proslov;
    }

    /**
     * Metoda setMrtvy zabije postavu
     *
     * @param mrtvy Parametr
     */
    public void setMrtvy(boolean mrtvy) {
        this.mrtvy = mrtvy;
    }

    /**
     * Metoda jeMrtvy vrací je-li postava mrtvá
     *
     * @return Návratová hodnota
     */
    public boolean jeMrtvy() {
        return mrtvy;
    }

    /**
     * Metoda setVec nastaví věc postavě, aby po smrtí jí mohla vložit do
     * prostoru
     *
     * @param vec Parametr
     */
    public void setVec(Vec vec) {
        this.vec = vec;
    }

    /**
     * Metoda dropVec vrací věc potřebnou pro "drop"
     *
     * @return Návratová hodnota
     */
    public Vec dropVec() {
        return vec;
    }
    //== Soukromé metody (instancí i třídy) ========================================

}

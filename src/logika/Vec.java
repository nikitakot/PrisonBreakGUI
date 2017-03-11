/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/**
 * *****************************************************************************
 * Instance třídy Vec představují ...
 *
 * @author Nikita Kot
 * @version ls 2016
 */
public class Vec {

    //== Datové atributy (statické i instancí)======================================
    private String nazev;
    private boolean prenositelnost;
    private String image;

    public String getImage() {
        return image;
    }

    //== Konstruktory a tovární metody =============================================

    /**
     * *************************************************************************
     * Konstruktor ....
     */
    public Vec(String nazev, boolean prenositelnost, String image) {
        this.nazev = nazev;
        this.prenositelnost = prenositelnost;
        this.image = image;
    }

    //== Nesoukromé metody (instancí i třídy) ======================================

    /**
     * Metoda getNazev
     *
     * @return Návratová hodnota
     */
    public String getNazev() {
        return nazev;
    }

    /**
     * Metoda jePrenositelna
     *
     * @return Návratová hodnota
     */
    public boolean jePrenositelna() {
        return prenositelnost;
    }

    //== Soukromé metody (instancí i třídy) ========================================
}

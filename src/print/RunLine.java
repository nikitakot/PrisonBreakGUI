/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package print;

/**
 * *****************************************************************************
 * Instance třídy RunLine představují ...
 *
 * @author Nikita Kot
 * @version ls 2016
 */
public class RunLine {

    //== Datové atributy (statické i instancí)======================================
    public static boolean zapnuto;
    //== Konstruktory a tovární metody =============================================

    /**
     * *************************************************************************
     * Konstruktor třídy
     *
     * @param zapnuto
     */
    public RunLine(boolean zapnuto) {
        RunLine.zapnuto = zapnuto;
    }

    //== Nesoukromé metody (instancí i třídy) ======================================

    /**
     * Metoda print Nahrazuje System.out.print
     *
     * @param text Parametr
     */
    public static void print(String text) {
        if (zapnuto) {
            for (char a : text.toCharArray()) {
                System.out.print(a);
                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    System.out.println("Exception " + e);
                }
            }
        } else {
            System.out.print(text);
        }
    }

    /**
     * Metoda println Nahrazuje System.out.println
     *
     * @param text Parametr
     */
    public static void println(String text) {
        if (zapnuto) {
            for (char a : text.toCharArray()) {
                System.out.print(a);
                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    System.out.println("Exception " + e);
                }
            }
            System.out.println();
        } else {
            System.out.println(text);
        }
    }

    //== Soukromé metody (instancí i třídy) ========================================
}

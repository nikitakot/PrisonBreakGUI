package logika;

/**
 * Třída PrikazNapoveda implementuje pro hru příkaz napoveda. Tato třída je
 * součástí jednoduché textové hry.
 *
 * @author Jarmila Pavlickova, Luboš Pavlíček, Nikita Kot
 * @version ls 2016
 */
class PrikazNapoveda implements IPrikaz {

    private static final String NAZEV = "help";
    private SeznamPrikazu platnePrikazy;

    /**
     * Konstruktor třídy
     *
     * @param platnePrikazy seznam příkazů, které je možné ve hře použít, aby je
     *                      nápověda mohla zobrazit uživateli.
     */
    public PrikazNapoveda(SeznamPrikazu platnePrikazy) {
        this.platnePrikazy = platnePrikazy;
    }

    /**
     * Vrací základní nápovědu po zadání příkazu "napoveda". Nyní se vypisuje
     * vcelku primitivní zpráva a seznam dostupných příkazů.
     *
     * @return napoveda ke hre
     */
    @Override
    public String proved(String... parametry) {
        return "Hurry up!!! We need to escape from here, we need to find EXIT,\n"
                + "and where is my friend John? Let's go find him! Will you help me?\n"
                + "\n"
                + "You can use this commands:\n"
                + platnePrikazy.vratNazvyPrikazu();
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

}

package logika;

/**
 * Třída PrikazJdi implementuje pro hru příkaz jdi. Tato třída je součástí
 * jednoduché textové hry.
 *
 * @author Jarmila Pavlickova, Luboš Pavlíček, Nikita Kot
 * @version ls 2016
 */
class PrikazJdi implements IPrikaz {

    private static final String NAZEV = "go";
    private HerniPlan plan;
    private Batoh batoh;
    private Hra hra;

    /**
     * Konstruktor třídy
     *
     * @param plan herní plán, ve kterém se bude ve hře "chodit"
     * @param hra  hra, pomocí které zkončíme hru v připade vstupu do "špatného"
     *             prostoru
     */
    public PrikazJdi(HerniPlan plan, Hra hra) {
        this.plan = plan;
        this.batoh = plan.getBatoh();
        this.hra = hra;
    }

    /**
     * Provádí příkaz "jdi". Zkouší se vyjít do zadaného prostoru. Pokud prostor
     * existuje, vstoupí se do nového prostoru. Pokud zadaný sousední prostor
     * (východ) není, vypíše se chybové hlášení. V metode je realizovany připady
     * vstupu do "špatného" a podmínky "špatností" prostoru +try catch pro
     * pripad že Guardian nebude v prostoru guard (NullPointerException)
     *
     * @param parametry - jako parametr obsahuje jméno prostoru (východu), do
     *                  kterého se má jít.
     * @return zpráva, kterou vypíše hra hráči
     */
    @Override
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return "Where should I go? Write a room name.";
        }

        String smer = parametry[0];

        // zkoušíme přejít do sousedního prostoru
        Prostor sousedniProstor = plan.getAktualniProstor().vratSousedniProstor(smer);

        if (sousedniProstor == null) {
            return "No way! I can't get to the " + smer + " from the " + plan.getAktualniProstor().getNazev() + "!";
        } else {
            if (sousedniProstor.jeZamceno()) {
                return "The door to the " + sousedniProstor.getNazev()
                        + " is closed...we need a " + sousedniProstor.getKlic().getNazev() + ".";
            }

            if (sousedniProstor.getNazev().equals("exit") && !plan.sJohnem()) {
                return "I can't escape without John...we need to find him at first!";
            }

            plan.setAktualniProstor(sousedniProstor);
            return sousedniProstor.dlouhyPopis();
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

}

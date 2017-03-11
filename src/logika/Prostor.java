package logika;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Trida Prostor - popisuje jednotlivé prostory (místnosti) hry
 * <p>
 * Tato třída je součástí jednoduché textové hry.
 * <p>
 * "Prostor" reprezentuje jedno místo (místnost, prostor, ..) ve scénáři hry.
 * Prostor může mít sousední prostory připojené přes východy. Pro každý východ
 * si prostor ukládá odkaz na sousedící prostor.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Nikita Kot
 * @version ls 2016
 */
public class Prostor {

    private String nazev;
    private String popis;
    private Set<Prostor> vychody;   // obsahuje sousední místnosti
    private Map<String, Vec> veci;
    private Map<String, Postava> postavy;
    private boolean zamceno = false;
    private Vec klic;
    private double top;
    private double left;

    /**
     * Vytvoření prostoru se zadaným popisem, např. "kuchyň", "hala", "trávník
     * před domem"
     *
     * @param nazev nazev prostoru, jednoznačný identifikátor, jedno slovo nebo
     *              víceslovný název bez mezer.
     * @param popis Popis prostoru.
     * @param top
     * @param left
     */
    public Prostor(String nazev, String popis, double top, double left) {
        this.nazev = nazev;
        this.popis = popis;
        vychody = new HashSet<>();
        this.veci = new HashMap<>();
        this.postavy = new HashMap<>();
        this.top = top;
        this.left = left;
    }

    public double getTop() {
        return top;
    }

    public double getLeft() {
        return left;
    }

    /**
     * Definuje východ z prostoru (sousední/vedlejsi prostor). Vzhledem k tomu,
     * že je použit Set pro uložení východů, může být sousední prostor uveden
     * pouze jednou (tj. nelze mít dvoje dveře do stejné sousední místnosti).
     * Druhé zadání stejného prostoru tiše přepíše předchozí zadání (neobjeví se
     * žádné chybové hlášení). Lze zadat též cestu ze do sebe sama.
     *
     * @param vedlejsi prostor, který sousedi s aktualnim prostorem.
     */
    public void setVychod(Prostor vedlejsi) {
        vychody.add(vedlejsi);
    }

    /**
     * Metoda equals pro porovnání dvou prostorů. Překrývá se metoda equals ze
     * třídy Object. Dva prostory jsou shodné, pokud mají stejný název. Tato
     * metoda je důležitá z hlediska správného fungování seznamu východů (Set).
     * <p>
     * Bližší popis metody equals je u třídy Object.
     *
     * @param o object, který se má porovnávat s aktuálním
     * @return hodnotu true, pokud má zadaný prostor stejný název, jinak false
     */
    @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci
        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr
        if (!(o instanceof Prostor)) {
            return false;    // pokud parametr není typu Prostor, vrátíme false
        }
        // přetypujeme parametr na typ Prostor
        Prostor druhy = (Prostor) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů.
        //Vrátí true pro stejné názvy a i v případě, že jsou oba názvy null,
        //jinak vrátí false.
        return (Objects.equals(this.nazev, druhy.nazev));
    }

    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object
     */
    @Override
    public int hashCode() {
        int vysledek = 3;
        int hashNazvu = Objects.hashCode(this.nazev);
        vysledek = 37 * vysledek + hashNazvu;
        return vysledek;
    }

    /**
     * Vrací název prostoru (byl zadán při vytváření prostoru jako parametr
     * konstruktoru)
     *
     * @return název prostoru
     */
    public String getNazev() {
        return nazev;
    }

    /**
     * Vrací "dlouhý" popis prostoru, který může vypadat následovně: Jsi v
     * mistnosti/prostoru vstupni hala budovy VSE na Jiznim meste. vychody:
     * chodba bufet ucebna položky mimo getNazev se zobrazují jenom když něco v
     * nich je
     *
     * @return Dlouhý popis prostoru
     */
    public String dlouhyPopis() {
        return "You are in the " + getNazev() + " (" + popis + ")"
                + (popisVychodu().equals("") ? "" : ("\n" + popisVychodu()))
                + (popisVeci().equals("") ? "" : ("\n" + popisVeci()))
                + (popisPostavVMistnosti().equals("") ? "" : ("\n" + popisPostavVMistnosti()));
    }

    /**
     * Vrací textový řetězec, který popisuje sousední východy, například:
     * "vychody: hala ".
     *
     * @return Popis východů - názvů sousedních prostorů
     */
    public String popisVychodu() {
        String vracenyText = "exits:";
        for (Prostor sousedni : vychody) {
            vracenyText += " " + sousedni.getNazev();
        }
        return vracenyText;
    }

    /**
     * Metoda popisVeci
     *
     * @return popis věci v prostoru
     */
    public String popisVeci() {
        String vracenyText = "objects:";
        for (String nazev : veci.keySet()) {
            vracenyText += " " + nazev;
        }
        return vracenyText.equals("objects:") ? "" : vracenyText;
    }

    /**
     * Metoda popisPostavVMistnosti
     *
     * @return popis postav v prostoru
     */
    public String popisPostavVMistnosti() {
        String vracenyText = "persons:";
        for (Map.Entry<String, Postava> pair : postavy.entrySet()) {
            if (pair.getValue().jeMrtvy()) {
                vracenyText += " " + pair.getKey() + "(is dead)";
            } else {
                vracenyText += " " + pair.getKey();
            }
        }
        return vracenyText.equals("persons:") ? "" : vracenyText;
    }

    /**
     * Vrací prostor, který sousedí s aktuálním prostorem a jehož název je zadán
     * jako parametr. Pokud prostor s udaným jménem nesousedí s aktuálním
     * prostorem, vrací se hodnota null.
     *
     * @param nazevSouseda Jméno sousedního prostoru (východu)
     * @return Prostor, který se nachází za příslušným východem, nebo hodnota
     * null, pokud prostor zadaného jména není sousedem.
     */
    public Prostor vratSousedniProstor(String nazevSouseda) {
        List<Prostor> hledaneProstory
                = vychody.stream()
                .filter(sousedni -> sousedni.getNazev().equals(nazevSouseda))
                .collect(Collectors.toList());
        if (hledaneProstory.isEmpty()) {
            return null;
        } else {
            return hledaneProstory.get(0);
        }
    }

    /**
     * Metoda vratPostavu
     *
     * @param jmeno vracené postavy
     * @return postavu
     */
    public Postava vratPostavu(String jmeno) {
        Postava hledana = null;
        for (Map.Entry<String, Postava> pair : postavy.entrySet()) {
            if (pair.getKey().equals(jmeno)) {
                hledana = pair.getValue();
                break;
            }
        }
        return hledana;
    }

    /**
     * Vrací kolekci obsahující prostory, se kterými tento prostor sousedí.
     * Takto získaný seznam sousedních prostor nelze upravovat (přidávat,
     * odebírat východy) protože z hlediska správného návrhu je to plně
     * záležitostí třídy Prostor.
     *
     * @return Nemodifikovatelná kolekce prostorů (východů), se kterými tento
     * prostor sousedí.
     */
    public Collection<Prostor> getVychody() {
        return Collections.unmodifiableCollection(vychody);
    }

    /**
     * Metoda vlozVec
     *
     * @param neco vkladaná věc
     */
    public void vlozVec(Vec neco) {
        veci.put(neco.getNazev(), neco);
    }

    /**
     * Metoda vlozPostavu
     *
     * @param postava vkladaná postava
     */
    public void vlozPostavu(Postava postava) {
        postavy.put(postava.getJmeno(), postava);
    }

    /**
     * Metoda odeberVec odebira věc z prostoru
     *
     * @param nazev věci
     * @return smazaná věc, nebo null, pokud se nic nesmazalo
     */
    public Vec odeberVec(String nazev) {
        return veci.remove(nazev);
    }

    /**
     * Metoda odeberPostavu
     *
     * @param jmeno Postavy
     * @return smazaná postava, nebo null, pokud se nic nesmazalo
     */
    public Postava odeberPostavu(String jmeno) {
        return postavy.remove(jmeno);
    }

    /**
     * Metoda zamknout zamyka a odemyka prostory
     *
     * @param zamceno Parametr
     */
    public void zamknout(boolean zamceno) {
        this.zamceno = zamceno;
    }

    /**
     * Metoda jeZamceno vrací true je-li prostor zamčený
     *
     * @return Návratová hodnota
     */
    public boolean jeZamceno() {
        return zamceno;
    }

    /**
     * Metoda nastavKlic
     *
     * @param klic Parametr
     */
    public void nastavKlic(Vec klic) {
        this.klic = klic;
    }

    /**
     * Metoda getKlic
     *
     * @return Návratová hodnota
     */
    public Vec getKlic() {
        return klic;
    }

    /**
     * Metoda getHeslo vrací heslo od prostoru
     *
     * @return Návratová hodnota
     */
    /**
     * Metoda setHeslo nastaví heslo na prostor
     *
     * @param heslo Parametr
     */
}

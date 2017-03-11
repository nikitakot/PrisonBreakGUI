package uiText;

import logika.IHra;
import print.RunLine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class TextoveRozhrani
 * <p>
 * Toto je uživatelského rozhraní aplikace Adventura Tato třída vytváří instanci
 * třídy Hra, která představuje logiku aplikace. Čte vstup zadaný uživatelem a
 * předává tento řetězec logice a vypisuje odpověď logiky na konzoli.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Nikita Kot
 * @version ls 2016
 */
public class TextoveRozhrani {

    private IHra hra;

    /**
     * Vytváří hru.
     */
    public TextoveRozhrani(IHra hra) {
        this.hra = hra;
    }

    /**
     * Hlavní metoda hry. Vypíše úvodní text a pak opakuje čtení a zpracování
     * příkazu od hráče do konce hry (dokud metoda konecHry() z logiky nevrátí
     * hodnotu true). Nakonec vypíše text epilogu.
     */
    public void hraj() {
        //Na začatku hry hrač se bude zeptan jestli chce použivat RunLine třidu jako vystup do konzole
        System.out.println("*If you want to turn-on the running-line effect type \"YES\" in \n"
                + "next console line, if no just skip it(leave it empty)*");
        RunLine.zapnuto = prectiString().equals("YES");
        RunLine.println(hra.vratUvitani());
        while (!hra.konecHry()) {
            String radek = prectiString();
            RunLine.println(hra.zpracujPrikaz(radek));

        }
        RunLine.println(hra.vratEpilog());
        System.exit(0);
    }

    public void hrajZeSouboru(String nazevSouboru) {

        // základní cyklus programu - opakovaně se čtou příkazy a poté
        // se provádějí do konce hry.
        try (BufferedReader ctecka = new BufferedReader(new FileReader(nazevSouboru))) {
            System.out.println(hra.vratUvitani());
            String radek = ctecka.readLine();

            while (!hra.konecHry() && radek != null) {
                System.out.println("*" + radek + "*");
                System.out.println(hra.zpracujPrikaz(radek));
                radek = ctecka.readLine();
            }
            System.out.println(hra.vratEpilog());
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound");
        } catch (IOException e) {
            System.out.println("Exception: " + e);
        }
    }

    /**
     * Metoda přečte příkaz z příkazového řádku
     *
     * @return Vrací přečtený příkaz jako instanci třídy String
     */
    private String prectiString() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        return scanner.nextLine();
    }

}

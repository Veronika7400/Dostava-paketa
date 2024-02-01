package pomocnici;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import adapter.Provjera2;
import builderPaketi.Paket;
import chainOfResponibilityKomande.HandlerIP;
import chainOfResponibilityKomande.HandlerKomandi;
import chainOfResponibilityKomande.HandlerOSOBE;
import chainOfResponibilityKomande.HandlerPO;
import chainOfResponibilityKomande.HandlerPP;
import chainOfResponibilityKomande.HandlerPPV;
import chainOfResponibilityKomande.HandlerPS;
import chainOfResponibilityKomande.HandlerQ;
import chainOfResponibilityKomande.HandlerSPV;
import chainOfResponibilityKomande.HandlerSV;
import chainOfResponibilityKomande.HandlerVR;
import chainOfResponibilityKomande.HandlerVS;
import chainOfResponibilityKomande.HandlerVV;
import factoryMethodUcitavanjeDatoteka.CreatorUcitavanje;
import factoryMethodUcitavanjeDatoteka.UcitavanjeMjesta;
import factoryMethodUcitavanjeDatoteka.UcitavanjeOsobe;
import factoryMethodUcitavanjeDatoteka.UcitavanjePodrucja;
import factoryMethodUcitavanjeDatoteka.UcitavanjePrijemaPaketa;
import factoryMethodUcitavanjeDatoteka.UcitavanjeUlica;
import factoryMethodUcitavanjeDatoteka.UcitavanjeVozila;
import factoryMethodUcitavanjeDatoteka.UcitavanjeVrstaPaketa;
import vrijeme.VirtualniSat;

public class Klijent {
  public static int brojPogresaka = 0;
  static Sustav sustav = Sustav.getInstance();
  public static Properties parametri;
  static CreatorUcitavanje ucitavanje;

  public static int brojAdmina = 0;

  public static void main(String[] args) {

    if (!provjeriArgumente(args)) {
      System.out.println("Broj argumenata nije valjan!");
      return;
    }

    ucitajParametre(args[0]);
    if (!ispravanBrojParametara()) {
      System.out.println("Broj parametara nije ispravan! \n" + parametri.toString());
      return;
    }
    provjeriParametre();
  }

  private static boolean ispravanBrojParametara() {
    return parametri.size() == 15 ? true : false;
  }

  private static void ucitajParametre(String naziv) {
    try {
      parametri = ucitajParametreIzDatoteke(naziv);
    } catch (Exception e) {
      System.out.println("Pogreška kod učitavanja parametara iz datoteke! " + e.getMessage());
    }
  }

  private static Properties ucitajParametreIzDatoteke(String naziv) {
    Properties properties = new Properties();
    try (FileInputStream input = new FileInputStream(naziv)) {
      properties.load(input);
    } catch (FileNotFoundException e) {
      System.out.println("Datoteka ne postoji! " + e.getMessage());
    } catch (IOException e) {
      System.out.println("Pogreška kod učitavanja postavki iz datoteke! " + e.getMessage());
    }

    return properties;
  }

  private static void provjeriParametre() {
    Provjera2 provjera = new Provjera2();
    if (provjera.provjeriParametre(parametri)) {

      ucitajVrstePaketa();
      /*
       * System.out.println("\nVRSTE PAKETA:"); List<VrstaPaketa> vrstePaketa =
       * tvrtka.dohvatiVrstePaketa(); for (VrstaPaketa v : vrstePaketa) {
       * System.out.println(v.toString()); }
       */

      ucitajUlice();
      /*
       * System.out.println("\nULICE:"); List<Ulica> ulica = tvrtka.dohvatiUlice(); for (Ulica v :
       * ulica) { System.out.println(v.toString()); }
       */

      ucitajMjesta();
      /*
       * System.out.println("\nMJESTA:"); List<Mjesto> mjesto = tvrtka.dohvatiMjesta(); for (Mjesto
       * v : mjesto) { System.out.println(v.toString()); }
       */

      ucitajOsobe();
      /*
       * System.out.println("\nOSOBE:"); List<Osobe> Osobe = tvrtka.dohvatiOsobe(); for (Osobe v :
       * Osobe) { System.out.println(v.toString()); }
       */

      ucitajPodrucja();
      /*
       * System.out.println("\nPODRUCJA:"); List<PodrucjaComposite> Podrucja =
       * tvrtka.dohvatiPodrucja(); for (PodrucjaComposite v : Podrucja) {
       * System.out.println("Podrucje: " + v.dohvatiId()); for (GradComposite g : v.dohvatiDjecu())
       * { System.out.println("\t Grad: " + g.dohvatiId()); for (UlicaLeaf u : g.dohvatiDjecu()) {
       * System.out.println("\t \tUlice: " + u.dohvatiId()); } } }
       */
      ucitajZaprimljenePakete();
      /*
       * System.out.println("\nZAPRIMLJENI PAKETI:"); List<Paket> paket =
       * tvrtka.dohvatiPaketeZaDostavu(); for (Paket v : paket) { System.out.println(v.getOznaka() +
       * " " + v.getVrijemePrijema()); }
       */
      sortirajPaketeKronoloski();

      ucitajVozila();
      postaviPocetakKrajRada();
      postaviVirtualniSat();

      pokreniIzvrsavanjeKomandi();

      /*
       * System.out.println("\nVRSTE VOZILA:"); List<Vozilo> vozila = tvrtka.dohvatiVozilo(); for
       * (Vozilo v : vozila) { System.out.println(v.toString()); }
       */
      /*
       * UredZaDostavu vozilaUDostavi = UredZaDostavu.getInstance(); vozilaUDostavi.popuniListu();
       * 
       */
    } else {
      return;
    }
  }

  private static void sortirajPaketeKronoloski() {
    List<Paket> sortiranaLista = sustav.dohvatiPaketeZaDostavu();
    sortiranaLista
        .sort((paket1, paket2) -> paket1.getVrijemePrijema().compareTo(paket2.getVrijemePrijema()));
    sustav.postaviSortiranePakete(sortiranaLista);
  }

  private static void postaviPocetakKrajRada() {
    sustav.postaviPocetakRada(parametri.getProperty("pr").trim());
    sustav.postaviKrajRada(parametri.getProperty("kr").trim());
  }

  private static boolean provjeriArgumente(String[] args) {
    return args.length == 1 ? true : false;
  }

  private static void pokreniIzvrsavanjeKomandi() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    while (true) {
      try {
        System.out.println("Unesite naredbu:");
        String input = reader.readLine();
        String[] a = input.split(" ");
        HandlerKomandi handler = new HandlerQ();

        handler.postaviSljedbenika(new HandlerIP()).postaviSljedbenika(new HandlerVR())
            .postaviSljedbenika(new HandlerSV()).postaviSljedbenika(new HandlerVV())
            .postaviSljedbenika(new HandlerVS()).postaviSljedbenika(new HandlerPP())
            .postaviSljedbenika(new HandlerPS()).postaviSljedbenika(new HandlerPO())
            .postaviSljedbenika(new HandlerSPV()).postaviSljedbenika(new HandlerPPV())
            .postaviSljedbenika(new HandlerOSOBE());

        if (!handler.izvrsiKomandu(a)) {
          System.out.println("Pogrešna naredba. ");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private static void postaviVirtualniSat() {
    VirtualniSat virtualniSat = VirtualniSat.getInstance();
    virtualniSat.postaviPocetnoVrijeme(parametri.getProperty("vs").trim());
    virtualniSat.postaviMnoziteljSekunde(Integer.parseInt(parametri.getProperty("ms").trim()));
  }

  private static void ucitajZaprimljenePakete() {
    ucitavanje = new UcitavanjePrijemaPaketa(parametri.getProperty("pp").trim(),
        Integer.parseInt(parametri.getProperty("mt").trim()));
  }

  private static void ucitajVozila() {
    ucitavanje = new UcitavanjeVozila(parametri.getProperty("pv").trim(),
        Integer.parseInt(parametri.getProperty("mt").trim()));
  }

  private static void ucitajVrstePaketa() {
    ucitavanje = new UcitavanjeVrstaPaketa(parametri.getProperty("vp").trim(),
        Integer.parseInt(parametri.getProperty("mt").trim()));
  }

  private static void ucitajUlice() {
    ucitavanje = new UcitavanjeUlica(parametri.getProperty("pu").trim(),
        Integer.parseInt(parametri.getProperty("mt").trim()));
  }

  private static void ucitajMjesta() {
    ucitavanje = new UcitavanjeMjesta(parametri.getProperty("pm").trim(),
        Integer.parseInt(parametri.getProperty("mt").trim()));
  }

  private static void ucitajOsobe() {
    ucitavanje = new UcitavanjeOsobe(parametri.getProperty("po").trim(),
        Integer.parseInt(parametri.getProperty("mt").trim()));
  }

  private static void ucitajPodrucja() {
    ucitavanje = new UcitavanjePodrucja(parametri.getProperty("pmu").trim(),
        Integer.parseInt(parametri.getProperty("mt").trim()));
  }
}

package pomocnici;

import java.util.ArrayList;
import java.util.List;
import builderPaketi.Paket;
import compositePodrucja.PodrucjaComposite;
import memento.Caretaker;
import podaci.Mjesto;
import podaci.Osobe;
import podaci.Ulica;
import podaci.VrstaPaketa;
import proxy.IOsobe;
import visitor.Vozilo;

public class Sustav implements IOsobe {
  private static Sustav instance;
  private static String pocetakRada;
  private static String krajRada;
  public static List<VrstaPaketa> vrstePaketa = new ArrayList<>();
  private static List<Paket> paketiZaDostavu = new ArrayList<>();
  private static List<Vozilo> vozniPark = new ArrayList<>();
  private static List<Ulica> ulice = new ArrayList<>();
  private static List<Mjesto> mjesta = new ArrayList<>();
  private static List<PodrucjaComposite> podrucja = new ArrayList<>();
  private static List<Osobe> osobe = new ArrayList<>();
  static int vrijemeIsporuke = 0;
  private static Caretaker caretaker = new Caretaker();

  private Sustav() {}

  public static Sustav getInstance() {
    if (instance == null) {
      instance = new Sustav();
    }
    return instance;
  }

  public Caretaker dohvatiCaretaker() {
    return caretaker;
  }

  public void dodajPaketZaDostavu(Paket paket) {
    paketiZaDostavu.add(paket);
  }

  public void dodajVozilo(Vozilo vozilo) {
    vozniPark.add(vozilo);
  }

  public void dodajVrstuPaketa(VrstaPaketa vrsta) {
    vrstePaketa.add(vrsta);
  }

  public void dodajUlicu(Ulica ulica) {
    ulice.add(ulica);
  }

  public void dodajMjesto(Mjesto mjesto) {
    mjesta.add(mjesto);
  }

  public void dodajPodrucje(PodrucjaComposite podrucje) {
    podrucja.add(podrucje);
  }

  public void dodajOsobu(Osobe osoba) {
    osobe.add(osoba);
  }

  public List<Ulica> dohvatiUlice() {
    return ulice;
  }

  public Ulica dohvatiUlicu(Integer id) {
    for (Ulica u : ulice) {
      if (u.id().equals(id)) {
        return u;
      }
    }
    return null;
  }

  public List<Mjesto> dohvatiMjesta() {
    return mjesta;
  }

  @Override
  public List<Osobe> dohvatiOsobe() {
    return osobe;
  }

  public Osobe dohvatiOsobu(String osoba) {
    for (Osobe o : osobe) {
      if (o.osoba().equals(osoba)) {
        return o;
      }
    }
    return null;

  }


  public List<PodrucjaComposite> dohvatiPodrucja() {
    return podrucja;
  }

  public List<Paket> dohvatiPaketeZaDostavu() {
    return paketiZaDostavu;
  }


  public List<VrstaPaketa> dohvatiVrstePaketa() {
    return vrstePaketa;
  }

  public List<Vozilo> dohvatiVozilo() {
    return vozniPark;
  }

  public void postaviPocetakRada(String pocetak) {
    pocetakRada = pocetak;
  }

  public void postaviKrajRada(String kraj) {
    krajRada = kraj;
  }

  public void postaviVrijemeIsporuke(int isporuka) {
    vrijemeIsporuke = isporuka;
  }

  public int dohvatiVrijemeIsporuke() {
    return vrijemeIsporuke;
  }

  public String dohvatiPocetakRada() {
    return pocetakRada;
  }

  public String dohvatiKrajRada() {
    return krajRada;
  }

  public void postaviSortiranePakete(List<Paket> sortiranaLista) {
    paketiZaDostavu = new ArrayList<>();
    paketiZaDostavu.addAll(sortiranaLista);
  }

  public Object dohvatiNazivUlice(Integer id) {
    for (Ulica u : ulice) {
      if (u.id().equals(id)) {
        return u.naziv();
      }
    }
    return null;
  }

  public Object dohvatiNazivGrada(Integer id) {
    for (Mjesto u : mjesta) {
      if (u.id().equals(id)) {
        return u.naziv();
      }
    }
    return null;
  }
}

package factoryMethodUcitavanjeDatoteka;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import compositePodrucja.PodrucjaComposite;
import pomocnici.Klijent;
import visitor.Vozilo;

public class UcitavanjeVozila extends CreatorUcitavanje {
  private List<Integer> ispravnaPodrucja;

  public UcitavanjeVozila(String naziv, Integer tezina) {
    if (!naziv.isEmpty()) {
      nazivDatoteke = naziv;
      putanja = Path.of(nazivDatoteke);
      super.postojanjeDatoteke();
      try {
        super.pokreniUcitavanje();
      } catch (IOException e) {
        System.out.println("Pogreska kod ucitavanja voznog parka!" + e.getMessage());
      }
    } else {
      System.out.println("Neispravan naziv datoteke! " + naziv);
    }
  }

  @Override
  public void stvoriProizvod(String[] stupci) {
    ProductVozila produkt = new ProductVozila();
    stupci[6] = ispravnaPodrucja.toString().replace("[", "").replace("]", "");
    Vozilo vozilo = produkt.stvoriNovi(stupci);
    sustav.dodajVozilo(vozilo);
  }

  @Override
  public boolean provjeriInformativniRedak(String red) {
    String informativniRedak =
        "Registracija;Opis;Kapacitet težine u kg;Kapacitet prostora u m3;Redoslijed;Prosječna brzina;Područja po rangu;Status";
    Pattern informativniRedakPattern = Pattern.compile(informativniRedak);
    return informativniRedakPattern.matcher(red).matches();
  }

  @Override
  public boolean provjeriStupac(String stupac, int indeks) {
    switch (indeks) {
      case 3:
        if (jeDecimalanVeciOdNula(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Kapacitet prostora u m3 nije dobro definiran! " + stupac);
          return false;
        }
        return true;
      case 2:
        if (!jeIntegerVeciOdNula(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Kapacitet težine u kg nije dobro definiran! " + stupac);
          return false;
        }
        return true;
      case 4:
        if (!jeIntegerVeciOdNula(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Redosljed nije dobro definiran! " + stupac);
          return false;
        }
        return true;
      case 6:
        ispravnaPodrucja = new ArrayList<>();
        ispravnaPodrucja = provjeriPodrucja(stupac);

        if (ispravnaPodrucja == null) {
          return false;
        }
        return true;
      case 5:
        if (!jeIntegerVeciOdNula(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Prosječna brzina vozila nije dobro definirana! " + stupac);
          return false;
        }
        return true;
      case 7:
        if (!postojiStatus(stupac.trim())) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Status vozila ne postoji! " + stupac);
          return false;
        }
        return true;
      default:
        return true;
    }
  }

  private boolean postojiStatus(String trim) {
    return trim.equals("A") || trim.equals("NA") || trim.equals("NI");
  }

  private List<Integer> provjeriPodrucja(String stupac) {
    List<PodrucjaComposite> podrucja = sustav.dohvatiPodrucja();
    List<Integer> ispravno = new ArrayList<>();
    String[] id = stupac.split(",");
    for (String idPodrucja : id) {
      if (!nijePodrucje(idPodrucja, podrucja)) {
        System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
            + " Područje ne postoji! " + idPodrucja);
      } else {
        ispravno.add(Integer.parseInt(idPodrucja.trim()));
      }
    }

    return ispravno;
  }

  private boolean nijePodrucje(String idPodrucja, List<PodrucjaComposite> podrucja) {
    for (PodrucjaComposite p : podrucja) {
      if (p.dohvatiId().equals(Integer.parseInt(idPodrucja.trim()))) {
        return true;
      }
    }
    return false;

  }

  @Override
  public boolean tocanBrojStupaca(String[] stupci) {
    return stupci.length == 8;
  }

}

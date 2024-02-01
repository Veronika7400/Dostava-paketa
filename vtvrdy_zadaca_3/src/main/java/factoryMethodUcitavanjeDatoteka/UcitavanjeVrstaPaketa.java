package factoryMethodUcitavanjeDatoteka;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Pattern;
import podaci.VrstaPaketa;
import pomocnici.Klijent;

public class UcitavanjeVrstaPaketa extends CreatorUcitavanje {

  public UcitavanjeVrstaPaketa(String naziv, Integer tezina) {
    if (!naziv.isEmpty() && !tezina.equals(0)) {
      nazivDatoteke = naziv;
      putanja = Path.of(nazivDatoteke);
      maxTezina = tezina;
      super.postojanjeDatoteke();
      try {
        super.pokreniUcitavanje();
      } catch (IOException e) {
        System.out.println("Pogreska kod ucitavanja vrste paketa!");
      }
    } else {
      System.out.println(
          "Neispravan naziv datoteke ili unos maximalne tezine! " + naziv + " " + maxTezina);
    }
  }

  @Override
  public void stvoriProizvod(String[] stupci) {
    ProductVrstaPaketa produkt = new ProductVrstaPaketa();
    produkt.dodajMaxTezinu(maxTezina);
    VrstaPaketa vrsta = produkt.stvoriNovi(stupci);
    sustav.dodajVrstuPaketa(vrsta);
  }

  @Override
  public boolean provjeriInformativniRedak(String red) {
    String informativniRedak =
        "Oznaka;Opis;Visina;Širina;Dužina;Maksimalna težina;Cijena;Cijena hitno;CijenaP;CijenaT";
    Pattern informativniRedakPattern = Pattern.compile(informativniRedak);

    return informativniRedakPattern.matcher(red).matches();
  }

  @Override
  public boolean provjeriStupac(String stupac, int indeks) {
    switch (indeks) {
      case 2:
        if (jeDecimalanVeciOdNula(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Visina nije dobro definirana! " + stupac);
          return false;
        }
        return true;
      case 3:
        if (jeDecimalanVeciOdNula(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Širina nije dobro definirana! " + stupac);
          return false;
        }
        return true;
      case 4:
        if (jeDecimalanVeciOdNula(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Dužina nije dobro definirana! " + stupac);
          return false;
        }
        return true;
      case 5:
        if (jeDecimalanVeciOdNula(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Maksimalna težina nije dobro definirana! " + stupac);
          return false;
        }
        return true;
      case 6:
        if (!jeIntegerVeciOdNula(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Cijena nije dobro definirana! " + stupac);
          return false;
        }
        return true;
      case 7:
        if (!jeIntegerVeciOdNula(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " CijenaH nije dobro definirana! " + stupac);
          return false;
        }
        return true;
      case 8:
        if (!jeIntegerVeciOdNula(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " CijenaP nije dobro definirana! " + stupac);
          return false;
        }
        return true;
      case 9:
        if (!jeIntegerVeciOdNula(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " CijenaT nije dobro definirana! " + stupac);
          return false;
        }
        return true;
      default:
        return true;
    }
  }

  @Override
  public boolean tocanBrojStupaca(String[] stupci) {
    return stupci.length == 10;
  }

}

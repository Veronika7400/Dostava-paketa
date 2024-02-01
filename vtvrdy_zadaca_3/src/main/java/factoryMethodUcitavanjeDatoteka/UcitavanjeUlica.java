package factoryMethodUcitavanjeDatoteka;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Pattern;
import podaci.Ulica;
import pomocnici.Klijent;

public class UcitavanjeUlica extends CreatorUcitavanje {

  public UcitavanjeUlica(String naziv, Integer tezina) {
    if (!naziv.isEmpty()) {
      nazivDatoteke = naziv;
      putanja = Path.of(nazivDatoteke);
      super.postojanjeDatoteke();
      try {
        super.pokreniUcitavanje();
      } catch (IOException e) {
        System.out.println("Pogreska kod ucitavanja ulica!" + e.getMessage());
      }
    } else {
      System.out.println("Neispravan naziv datoteke! " + naziv);
    }
  }


  @Override
  public void stvoriProizvod(String[] stupci) {
    ProductUlica produkt = new ProductUlica();
    Ulica ulica = produkt.stvoriNovi(stupci);
    sustav.dodajUlicu(ulica);
  }

  @Override
  public boolean provjeriInformativniRedak(String red) {
    String informativniRedak =
        "id; naziv; gps_lat_1; gps_lon_1; gps_lat_2; gps_lon_2; najveći kućni broj";
    Pattern informativniRedakPattern = Pattern.compile(informativniRedak);
    return informativniRedakPattern.matcher(red).matches();
  }

  @Override
  public boolean provjeriStupac(String stupac, int indeks) {
    switch (indeks) {
      case 0:
        if (!jeIntegerVeciOdNula(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Id ulice nije ispravno definiran! " + stupac);
          return false;
        }
        return true;

      case 2:
      case 3:
      case 4:
      case 5:
        if (!validanGpsFormat(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " GPS koordinata ulice nije ispravna! " + stupac);
          return false;
        }
        return true;
      case 6:
        if (!jeIntegerVeciOdNula(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Najveći kućni broj nije dobro definiran! " + stupac);
          return false;
        }
        return true;
      default:
        return true;
    }
  }

  private boolean validanGpsFormat(String gps) {
    final String regExGps = "[-+]?\\d{1,3}\\.\\d{6}$";
    Pattern patternRegExGps = Pattern.compile(regExGps);
    return patternRegExGps.matcher(gps.trim()).matches() ? true : false;
  }

  @Override
  public boolean tocanBrojStupaca(String[] stupci) {
    return stupci.length == 7;
  }

}

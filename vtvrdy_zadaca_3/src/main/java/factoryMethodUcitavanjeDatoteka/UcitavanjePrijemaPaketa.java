package factoryMethodUcitavanjeDatoteka;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Pattern;
import builderPaketi.Paket;
import podaci.Osobe;
import podaci.TipDostave;
import podaci.VrstaPaketa;
import pomocnici.Klijent;
import pomocnici.Sustav;

public class UcitavanjePrijemaPaketa extends CreatorUcitavanje {

  String vrstaPaketa;

  public UcitavanjePrijemaPaketa(String naziv, Integer tezina) {
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
    ProductPaket produkt = new ProductPaket();
    produkt.postaviRbroj(rbroj);
    Paket paket = produkt.stvoriNovi(stupci);
    if (paket != null) {
      sustav.dodajPaketZaDostavu(paket);
    }

  }

  @Override
  public boolean provjeriInformativniRedak(String red) {
    String regExpDatumVrijeme =
        "Oznaka;Vrijeme prijema;Pošiljatelj;Primatelj;Vrsta paketa;Visina;Širina;Dužina;Težina;Usluga dostave;Iznos pouzeća";
    Pattern patternRegExpDatumVrijeme = Pattern.compile(regExpDatumVrijeme);
    return patternRegExpDatumVrijeme.matcher(red).matches() ? true : false;
  }

  @Override
  public boolean provjeriStupac(String stupac, int indeks) {
    switch (indeks) {
      case 1:
        if (!provjeriIspravnostVremena(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Vrijeme nije dobro definirano! " + stupac);
          return false;
        }
        return true;
      case 4:
        if (!postojiVrsta(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Vrsta paketa ne postoji! " + stupac);
          return false;
        }
        vrstaPaketa = stupac;
        return true;
      case 5:
        if (jeDecimalanVeciOdNula(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Visina nije dobro definirana! " + stupac);
          return false;
        }
        return true;
      case 6:
        if (jeDecimalanVeciOdNula(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Širina nije dobro definirana! " + stupac);
          return false;
        }
        return true;
      case 7:
        if (jeDecimalanVeciOdNula(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Dužina nije dobro definirana! " + stupac);
          return false;
        }
        return true;
      case 8:
        if (jeDecimalanVeciOdNula(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Težina paketa je prevelika! " + stupac);
          return false;
        }
        if (!valjaneDimenzije(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Paket ima veću težinu od dozvoljene! " + stupac);
          return false;
        }
        return true;
      case 9:
        if (!TipDostave.postojiOznaka(stupac.trim())) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Ne postoji uneseni tip dostave! " + stupac);
          return false;
        }
        return true;
      case 3:
        if (!postojiOsoba(stupac.trim())) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Ne postoji primatelj paketa! " + stupac);
          return false;
        }
        return true;
      case 10:
        if (jeDecimalanVeciOdNula(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Iznos pouzeća nije dobro definiran! " + stupac);
          return false;
        }
        return true;
      default:
        return true;
    }
  }

  private boolean valjaneDimenzije(String stupac) {
    for (VrstaPaketa v : sustav.dohvatiVrstePaketa()) {
      if (v.oznaka().equals(vrstaPaketa)) {
        stupac = stupac.replace(",", ".");
        if (v.maxTezina() >= Float.parseFloat(stupac)) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean postojiOsoba(String ime) {
    for (Osobe osoba : sustav.dohvatiOsobe()) {
      if (osoba.osoba().equals(ime)) {
        return true;
      }
    }
    return false;
  }

  private boolean postojiVrsta(String stupac) {
    for (VrstaPaketa vrstaPaketa : Sustav.vrstePaketa) {
      if (vrstaPaketa.oznaka().equals(stupac)) {
        return true;
      }
    }
    return false;
  }

  private static boolean provjeriIspravnostVremena(String args) {
    String regExpDatumVrijeme =
        "([1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])\\.\\d{4}\\.\\s([01]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])";
    Pattern patternRegExpDatumVrijeme = Pattern.compile(regExpDatumVrijeme);
    return patternRegExpDatumVrijeme.matcher(args).matches() ? true : false;
  }


  @Override
  public boolean tocanBrojStupaca(String[] stupci) {
    return stupci.length == 11;
  }

}

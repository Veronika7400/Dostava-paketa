package factoryMethodUcitavanjeDatoteka;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import podaci.Mjesto;
import podaci.Ulica;
import pomocnici.Klijent;

public class UcitavanjeMjesta extends CreatorUcitavanje {

  private String ispravneUlice;

  public UcitavanjeMjesta(String naziv, Integer tezina) {
    if (!naziv.isEmpty()) {
      nazivDatoteke = naziv;
      putanja = Path.of(nazivDatoteke);
      super.postojanjeDatoteke();
      try {
        super.pokreniUcitavanje();
      } catch (IOException e) {
        System.out.println("Pogreska kod ucitavanja mjesta!" + e.getMessage());
      }
    } else {
      System.out.println("Neispravan naziv datoteke! " + naziv);
    }
  }

  @Override
  public void stvoriProizvod(String[] stupci) {
    ProductMjesto produkt = new ProductMjesto();
    stupci[2] = ispravneUlice;
    Mjesto mjesto = produkt.stvoriNovi(stupci);
    sustav.dodajMjesto(mjesto);
  }

  @Override
  public boolean provjeriInformativniRedak(String red) {
    String informativniRedak = "id; naziv; ulica,ulica,ulica,...";
    Pattern informativniRedakPattern = Pattern.compile(informativniRedak);
    return informativniRedakPattern.matcher(red).matches();
  }

  @Override
  public boolean provjeriStupac(String stupac, int indeks) {
    switch (indeks) {
      case 0:
        if (!jeIntegerVeciOdNula(stupac.trim())) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Id ulice nije ispravno definiran! " + stupac);
          return false;
        }
        return true;
      case 2:
        ispravneUlice = "";
        ispravneUlice = provjeriUlice(stupac);
        if (ispravneUlice == "") {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Niti jedna ulica ne postoji! " + stupac);
          return false;
        }
        return true;
      default:
        return true;
    }
  }

  private String provjeriUlice(String stupac) {
    String[] ulice = stupac.split(",");
    List<String> ispravneUlice = new ArrayList<>();
    for (var i = 0; i < ulice.length; i++) {
      if (!jeIntegerVeciOdNula(ulice[i].trim())) {
        System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
            + " Id ulice nije dobro definiran! " + stupac);
      } else {
        if (!pronadiUlicu(Integer.parseInt(ulice[i].trim()))) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Ulica s ovim id ne postoji! " + ulice[i]);
        } else {
          ispravneUlice.add(ulice[i]);
        }
      }
    }
    return ispravneUlice.toString();
  }

  private boolean pronadiUlicu(int parseInt) {
    for (Ulica u : sustav.dohvatiUlice()) {
      if (u.id().equals(parseInt)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean tocanBrojStupaca(String[] stupci) {
    return stupci.length == 3;
  }

}

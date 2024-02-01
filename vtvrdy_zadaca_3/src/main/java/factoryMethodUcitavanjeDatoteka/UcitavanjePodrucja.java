package factoryMethodUcitavanjeDatoteka;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Pattern;
import compositePodrucja.PodrucjaComposite;
import pomocnici.Klijent;

public class UcitavanjePodrucja extends CreatorUcitavanje {

  public UcitavanjePodrucja(String naziv, Integer tezina) {
    if (!naziv.isEmpty()) {
      nazivDatoteke = naziv;
      putanja = Path.of(nazivDatoteke);
      super.postojanjeDatoteke();
      try {
        super.pokreniUcitavanje();
      } catch (IOException e) {
        System.out.println("Pogreska kod ucitavanja podrucja!" + e.getMessage());
      }
    } else {
      System.out.println("Neispravan naziv datoteke! " + naziv);
    }
  }

  @Override
  public void stvoriProizvod(String[] stupci) {
    ProductPodrucja produkt = new ProductPodrucja();
    produkt.postaviRbroj(rbroj);
    PodrucjaComposite podrucja = produkt.stvoriNovi(stupci);
    sustav.dodajPodrucje(podrucja);

  }

  @Override
  public boolean provjeriInformativniRedak(String red) {
    String informativniRedak = "id;grad:ulica,grad:ulica,grad:\\*,...";
    Pattern informativniRedakPattern = Pattern.compile(informativniRedak);
    return informativniRedakPattern.matcher(red).matches();
  }

  @Override
  public boolean provjeriStupac(String stupac, int indeks) {
    switch (indeks) {
      case 0:
        if (!jeIntegerVeciOdNula(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Id podrucja nije dobro definiran! " + stupac);
          return false;
        }
        return true;
      case 1:
        if (stupac.isEmpty()) {
          return false;
        }
        return true;
      default:
        return true;
    }
  }

  @Override
  public boolean tocanBrojStupaca(String[] stupci) {
    return stupci.length == 2;
  }

}

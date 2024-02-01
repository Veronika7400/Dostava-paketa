package factoryMethodUcitavanjeDatoteka;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;
import podaci.Mjesto;
import podaci.Osobe;
import pomocnici.Klijent;

public class UcitavanjeOsobe extends CreatorUcitavanje {
  private Mjesto grad;

  public UcitavanjeOsobe(String naziv, Integer tezina) {
    if (!naziv.isEmpty() && !tezina.equals(0)) {
      nazivDatoteke = naziv;
      putanja = Path.of(nazivDatoteke);
      super.postojanjeDatoteke();
      try {
        super.pokreniUcitavanje();
      } catch (IOException e) {
        System.out.println("Pogreska kod ucitavanja osoba!");
      }
    } else {
      System.out.println("Neispravan naziv datoteke! " + naziv);
    }
  }

  @Override
  public void stvoriProizvod(String[] stupci) {
    ProductOsobe produkt = new ProductOsobe();
    Osobe osobe = produkt.stvoriNovi(stupci);
    sustav.dodajOsobu(osobe);

  }

  @Override
  public boolean provjeriInformativniRedak(String red) {
    String regExpDatumVrijeme = "osoba; grad; ulica; kbr";
    Pattern patternRegExpDatumVrijeme = Pattern.compile(regExpDatumVrijeme);
    return patternRegExpDatumVrijeme.matcher(red).matches() ? true : false;
  }

  @Override
  public boolean provjeriStupac(String stupac, int indeks) {
    switch (indeks) {
      case 0:
        if (stupac.isEmpty()) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Osoba mora imati ime i prezime! " + stupac);
          return false;
        }
        return true;
      case 1:
        grad = null;
        grad = postojiGrad(stupac);
        if (grad == null) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Grad ne postoji! " + stupac);
          return false;
        }

        return true;
      case 2:
        if (!ulicaPripadaGradu(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Ulica ne pripada gradu! " + stupac + " " + grad);
          return false;
        }
        return true;
      case 3:
        if (!jeIntegerVeciOdNula(stupac)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Kucni broj nije dobro definiran! " + stupac);
          return false;
        }
        return true;
      default:
        return true;
    }
  }

  private boolean ulicaPripadaGradu(String stupac) {
    return grad.ulice().contains(Integer.parseInt(stupac));
  }

  private Mjesto postojiGrad(String stupac) {
    List<Mjesto> mjesta = sustav.dohvatiMjesta();
    for (Mjesto mjesto : mjesta) {
      if (mjesto.id().equals(Integer.parseInt(stupac))) {
        return mjesto;
      }
    }
    return null;
  }

  @Override
  public boolean tocanBrojStupaca(String[] stupci) {
    return stupci.length == 4;
  }

}

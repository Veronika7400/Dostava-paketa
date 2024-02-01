package factoryMethodUcitavanjeDatoteka;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import pomocnici.Klijent;
import pomocnici.Sustav;

public abstract class CreatorUcitavanje {

  protected String nazivDatoteke;
  protected Integer maxTezina;
  protected Path putanja;
  protected int rbroj = 0;
  protected Sustav sustav = Sustav.getInstance();

  public void postojanjeDatoteke() {
    if (!Files.exists(putanja) || Files.isDirectory(putanja) || !Files.isReadable(putanja)) {
      System.out.println("Nema datoteke!");
    }
  }

  public void pokreniUcitavanje() throws IOException {
    var citac = Files.newBufferedReader(putanja, Charset.forName("UTF-8"));

    while (true) {
      var red = citac.readLine();
      if (red == null) {
        break;
      }
      rbroj++;
      if (isZaglavlje(rbroj)) {
        if (!provjeriInformativniRedak(red)) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Informativni redak nije ispravan! " + red);
          break;
        }
        continue;
      }

      var stupci = red.split(";");
      if (!tocanBrojStupaca(stupci)) {
        System.out.print("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
            + " Broj zapisa u redu nije valjan! ");
        for (String s : stupci)
          System.out.print(s + " ");
        System.out.print("\n");
        continue;
      } else if (!provjeriPrazneStupce(stupci)) {
        System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
            + " Postoje prazni zapisi u retku! " + red);
        continue;
      } else {
        var ispravni = true;
        for (int i = 0; i < stupci.length; i++) {
          if (!provjeriStupac(stupci[i], i)) {
            ispravni = false;
          }
        }
        if (ispravni) {
          stvoriProizvod(stupci);
        }
      }
    }
  }

  public boolean provjeriPrazneStupce(String[] stupci) {
    for (String stupac : stupci) {
      if (stupac.trim().isEmpty()) {
        return false;
      }
    }
    return true;
  }

  public static boolean jeIntegerVeciOdNula(String broj) {
    try {
      broj = broj.trim();
      broj = broj.replace(",", ".");
      double brojDouble = Double.parseDouble(broj);
      int intdio = (int) brojDouble;
      return brojDouble == intdio && intdio >= 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static boolean jeDecimalanVeciOdNula(String broj) {
    try {
      if (Float.parseFloat(broj) < 0.0000) {
        return false;
      }
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public abstract void stvoriProizvod(String[] stupci);

  public abstract boolean provjeriInformativniRedak(String red);

  public abstract boolean provjeriStupac(String stupac, int indeks);

  public abstract boolean tocanBrojStupaca(String[] stupci);

  public boolean isZaglavlje(int rbroj) {
    return rbroj == 1;
  }
}

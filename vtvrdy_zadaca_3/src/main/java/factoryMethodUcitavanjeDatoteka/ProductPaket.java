package factoryMethodUcitavanjeDatoteka;

import builderPaketi.Paket;
import builderPaketi.PaketBuildDirector;
import builderPaketi.PaketBuilder;
import builderPaketi.PaketTipskiBuilder;
import builderPaketi.PaketXBuilder;
import pomocnici.Klijent;


public class ProductPaket implements Product {

  Integer rbroj;

  @Override
  public Paket stvoriNovi(String[] stupci) {
    Paket paket = null;
    if (!stupci[9].trim().equals("P")) {
      if (jeFloatVeciOdNule(stupci[10].trim())) {
        System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
            + " Paket nije s pouzećem, a ima iznos pouzeća! " + stupci[0]);
      }
      stupci[10] = "0,00";
    } else {
      if (!jeFloatVeciOdNule(stupci[10].trim())) {
        System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
            + " Paket s pouzećem ima iznos pouzeća 0! " + stupci[0]);
        return paket;
      }
    }
    if (stupci[4].equals("X")) {
      PaketBuilder builder = new PaketXBuilder();
      PaketBuildDirector paketBuildDirector = new PaketBuildDirector(builder);

      paket = paketBuildDirector.buildPaketX(stupci[0], stupci[1], stupci[2], stupci[3], stupci[4],
          Float.parseFloat(stupci[5].replace(",", ".")),
          Float.parseFloat(stupci[6].replace(",", ".")),
          Float.parseFloat(stupci[7].replace(",", ".")),
          Float.parseFloat(stupci[8].replace(",", ".")), stupci[9],
          Float.parseFloat(stupci[10].replace(",", ".")));

    } else {
      PaketBuilder builder = new PaketTipskiBuilder();
      PaketBuildDirector paketBuildDirector = new PaketBuildDirector(builder);
      if (!stupci[9].equals("P")) {
        stupci[10] = "0,00";
      }
      paket = paketBuildDirector.buildPaketTipski(stupci[0], stupci[1], stupci[2], stupci[3],
          stupci[4], Float.parseFloat(stupci[8].replace(",", ".")), stupci[9],
          Float.parseFloat(stupci[10].replace(",", ".")));
    }
    return paket;
  }

  private boolean jeFloatVeciOdNule(String string) {
    float floatIznos;
    try {
      floatIznos = Float.parseFloat(string.replace(",", "."));
      if (floatIznos > 0) {
        return true;
      }
    } catch (NumberFormatException e) {
    }

    return false;
  }


  public void postaviRbroj(Integer b) {
    rbroj = b;
  }
}

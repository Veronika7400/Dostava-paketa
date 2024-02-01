package chainOfResponibilityKomande;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import decoratorIP.DecoratorIspis;
import decoratorIP.IspisDostavljenih;
import decoratorIP.IspisOriginal;
import decoratorIP.IspisPaketaVozilaDostave;
import decoratorIP.IspisPodatakaPaketa;
import decoratorIP.IspisPrimljenih;
import decoratorIP.ZaprimljeniPaket;
import uredi.PrijemPaketa;
import vrijeme.VirtualniSat;

public class HandlerIP extends HandlerKomandi {
  static VirtualniSat sat = VirtualniSat.getInstance();
  BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

  @Override
  public boolean izvrsiKomandu(String[] komanda) {
    if (izvrsiIP(komanda)) {
      return true;
    }
    return posaljiSljedbeniku(komanda);
  }

  private boolean izvrsiIP(String[] a) {
    if (!a[0].equals("IP")) {
      return false;
    }

    String input = "0";
    while (!input.equals("6")) {
      ispisiIzbornik();
      try {
        input = reader.readLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
      switch (input) {
        case "1":
          ispisiOriginal();
          break;
        case "2":
          ispisiInfoPaketa();
          break;
        case "3":
          ispisiDostavljene();
          break;
        case "4":
          ispisiPrimljene();
          break;
        case "5":
          ispisPaketVozilo();
          break;
        case "6":
          break;
        default:
          System.out.println("Neispravna opcija. ");
          break;
      }
    }
    return true;
  }



  private void ispisiIzbornik() {
    System.out.println("Upišite broj opcije IP komande koju želite: \n 1. Ispis zadan u zadaći \n"
        + " 2. Ispis informacija o paketima bez informacija o prijemu i isporuci \n"
        + " 3. Ispis samo dostavljenih paketa\n"
        + " 4. Ispis primljenih paketa koji nisu dostavljeni \n"
        + " 5. Ispis koje vozilo je dostavilo koji paket \n" + " 6. Kraj IP \n");
  }

  private void ispisPaketVozilo() {
    DecoratorIspis ispisPaketaVozilaDostave = new IspisPaketaVozilaDostave();
    ispisi(ispisPaketaVozilaDostave);
  }

  private void ispisiInfoPaketa() {
    DecoratorIspis ispisPodataka = new IspisPodatakaPaketa();
    ispisi(ispisPodataka);
  }

  private void ispisiPrimljene() {
    DecoratorIspis ispisPrimljenih = new IspisPrimljenih();
    ispisi(ispisPrimljenih);
  }

  private void ispisiDostavljene() {
    DecoratorIspis ispisDostavljenih = new IspisDostavljenih();
    ispisi(ispisDostavljenih);
  }

  private void ispisiOriginal() {
    DecoratorIspis ispisOriginal = new IspisOriginal();
    ispisi(ispisOriginal);
  }

  private void ispisi(DecoratorIspis ispis) {
    ispis.ispisiZaglavlje();
    for (ZaprimljeniPaket paket : PrijemPaketa.dohvatiPakete()) {
      ispis.dodjeliPaket(paket);
      ispis.ispisiSadrzaj();
    }
    ispis.ispisiPodnozje();
  }
}

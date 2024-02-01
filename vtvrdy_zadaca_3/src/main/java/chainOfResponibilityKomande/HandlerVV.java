package chainOfResponibilityKomande;

import uredi.DostavaPaketa;
import visitor.Visitor;
import visitor.Vozilo;
import visitor.VoznjeVisitor;

public class HandlerVV extends HandlerKomandi {

  @Override
  public boolean izvrsiKomandu(String[] komanda) {
    if (izvrsiVV(komanda)) {
      return true;
    }
    return posaljiSljedbeniku(komanda);
  }

  private boolean izvrsiVV(String[] a) {
    if (!a[0].equals("VV")) {
      return false;
    }

    if (a.length != 2) {
      System.out.println("Sintaksa komande je VV vozilo!");
    } else {
      izvrsiKomanduVV(a[1].trim());
    }

    return true;
  }

  private void izvrsiKomanduVV(String registracija) {
    Visitor visitor = new VoznjeVisitor();

    Vozilo vozilo = DostavaPaketa.dohvatiVozilo(registracija);
    if (vozilo != null) {
      visitor.ispisiZaglavlje();
      ((VoznjeVisitor) visitor).postaviVozilo(vozilo);
      vozilo.accept(visitor);
      visitor.ispisiPodnozje();
    } else {
      System.out.println("Vozilo ne postoji" + registracija);
    }
  }
}

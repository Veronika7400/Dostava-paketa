package chainOfResponibilityKomande;

import uredi.DostavaPaketa;
import visitor.SegmentiVisitor;
import visitor.Visitor;
import visitor.Vozilo;
import visitor.Voznja;

public class HandlerVS extends HandlerKomandi {

  @Override
  public boolean izvrsiKomandu(String[] komanda) {
    if (izvrsiVS(komanda)) {
      return true;
    }
    return posaljiSljedbeniku(komanda);
  }

  private boolean izvrsiVS(String[] a) {
    if (!a[0].equals("VS")) {
      return false;
    }

    if (a.length != 3) {
      System.out.println("Sintaksa komande je VS vozilo n!");
    } else {
      int rb = provjeriSate(a[2].trim());
      izvrsiKomanduVS(a[1].trim(), rb);
    }

    return true;
  }

  private void izvrsiKomanduVS(String registracija, Integer broj) {
    Visitor visitor = new SegmentiVisitor();
    Vozilo vozilo = DostavaPaketa.dohvatiVozilo(registracija);
    if (vozilo != null) {
      Voznja voznja = vozilo.dohvatiVoznju(broj);
      if (voznja != null) {
        visitor.ispisiZaglavlje();
        voznja.accept(visitor);
        visitor.ispisiPodnozje();
      } else {
        System.out.println("Voznja za vozilo ne postoji " + registracija + " " + broj);
      }
    } else {
      System.out.println("Vozilo ne postoji " + registracija);
    }
  }

  private static int provjeriSate(String a) {
    try {
      return Integer.parseInt(a);
    } catch (NumberFormatException e) {
      System.out.println(a + " Mora biti cijeli broj! ");
    }
    return 0;
  }



}

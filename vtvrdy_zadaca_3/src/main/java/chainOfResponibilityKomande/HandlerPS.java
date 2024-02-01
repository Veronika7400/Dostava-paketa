package chainOfResponibilityKomande;

import java.util.List;
import pomocnici.Sustav;
import visitor.Vozilo;

public class HandlerPS extends HandlerKomandi {
  static Sustav tvrtka = Sustav.getInstance();
  static List<Vozilo> vozila = tvrtka.dohvatiVozilo();
  static Vozilo trazeno = null;

  @Override
  public boolean izvrsiKomandu(String[] komanda) {
    if (izvrsiPS(komanda)) {
      return true;
    }
    return posaljiSljedbeniku(komanda);
  }

  private boolean izvrsiPS(String[] a) {
    if (!a[0].equals("PS")) {
      return false;
    }

    if (a.length != 3) {
      System.out.println("Sintaksa komande je PS vozilo [A|NI|NA]!");
    } else {
      promjeniStatus(a[1], a[2]);
    }

    return true;
  }

  private void promjeniStatus(String oznaka, String status) {
    for (Vozilo v : vozila) {
      if (v.getRegistracija().equals(oznaka)) {
        trazeno = v;
        break;
      }
    }
    if (trazeno == null) {
      System.out.println("Vozilo s unesenom registarskom oznakom ne postoji! ");
    } else {
      if (!postojiStatus(status)) {
        System.out.println("Status ne postoji! ");
      } else {
        trazeno.getStatus().promjeniStatus(trazeno, status);
      }
    }
  }

  private boolean postojiStatus(String trim) {
    return trim.equals("A") || trim.equals("NA") || trim.equals("NI");
  }

}

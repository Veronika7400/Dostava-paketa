package chainOfResponibilityKomande;

import chainOfResponibilityKomande.HandlerSPV.Memento;
import pomocnici.Sustav;
import uredi.DostavaPaketa;
import uredi.PrijemPaketa;
import vrijeme.VirtualniSat;

public class HandlerPPV extends HandlerKomandi {
  static Sustav tvrtka = Sustav.getInstance();
  static VirtualniSat sat = VirtualniSat.getInstance();

  @Override
  public boolean izvrsiKomandu(String[] komanda) {
    if (izvrsiPPV(komanda)) {
      return true;
    }
    return posaljiSljedbeniku(komanda);
  }

  private boolean izvrsiPPV(String[] a) {
    if (!a[0].equals("PPV")) {
      return false;
    }

    if (a.length < 2) {
      System.out.println("Sintaksa komande je PPV 'naziv'!");
    } else {
      String naziv = dohvatiNaziv(a);
      vratiStanje(naziv);
    }
    return true;
  }

  private String dohvatiNaziv(String[] a) {
    String ime = null;
    for (int i = 0; i < a.length; i++) {
      if (a[i].startsWith("'")) {
        if (a[i].endsWith("'")) {
          ime = a[i].replaceAll("'", "");
        } else {
          StringBuilder text = new StringBuilder(a[i].substring(1));
          while (i + 1 < a.length && !a[i + 1].endsWith("'")) {
            i++;
            text.append(" ").append(a[i]);
          }
          if (i + 1 < a.length) {
            i++;
            text.append(" ").append(a[i]);
          }
          ime = text.toString().replaceAll("'", "");
        }
      }
    }
    return ime;
  }

  private void vratiStanje(String naziv) {
    Memento trazeni = tvrtka.dohvatiCaretaker().vratiStanje(naziv);
    if (trazeni != null) {
      sat.postaviPocetnoVrijeme(trazeni.getVirtualniSat());
      PrijemPaketa.azurirajPakete(trazeni.getZaprimljeniPaketi());
      DostavaPaketa.azurirajVozila(trazeni.getListaVozila());
      System.out.println("Vraćeno je stanje pod nazivom " + naziv);
    } else {
      System.out.println("Naziv stanja ne postoji! " + naziv);
    }
  }
}

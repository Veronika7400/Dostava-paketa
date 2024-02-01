package chainOfResponibilityKomande;

import java.util.List;
import podaci.Osobe;
import pomocnici.Sustav;
import proxy.IOsobe;
import proxy.ProxyOsobe;

public class HandlerOSOBE extends HandlerKomandi {
  static Sustav sustav = Sustav.getInstance();

  @Override
  public boolean izvrsiKomandu(String[] komanda) {
    if (izvrsiOSOBE(komanda)) {
      return true;
    }
    return posaljiSljedbeniku(komanda);
  }

  private boolean izvrsiOSOBE(String[] a) {
    if (!a[0].equals("OSOBE")) {
      return false;
    }
    if (a.length < 2) {
      System.out.println("Sintaksa komande je OSOBE 'korisnik'!");
    } else {
      String korisnik = dohvatiImeKorisnika(a);
      IOsobe dohvacanje = new ProxyOsobe(korisnik);
      List<Osobe> osobe = dohvacanje.dohvatiOsobe();
      if (osobe != null) {
        ispisiPodatke(osobe);
      }
    }
    return true;
  }

  private void ispisiPodatke(List<Osobe> osobe) {
    System.out.format("| %-20s | %-20s | %-30s | %-20s | %-20s |\n", "Osoba", "Grad", "Ulica",
        "Kućni broj", "Admin");

    System.out.println("+----------------------+----------------------+"
        + "--------------------------------+----------------------+----------------------+");

    for (Osobe o : osobe) {

      System.out.format("| %-20s | %-20s | %-30s | %-20s | %-20s |\n", o.osoba(),
          sustav.dohvatiNazivGrada(o.grad()), sustav.dohvatiNazivUlice(o.ulica()), o.kbr(),
          o.admin());
    }

    System.out.println("+----------------------+----------------------+"
        + "--------------------------------+----------------------+----------------------+");
  }

  private String dohvatiImeKorisnika(String[] a) {
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
}

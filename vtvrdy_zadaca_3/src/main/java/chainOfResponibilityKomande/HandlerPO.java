package chainOfResponibilityKomande;

import decoratorIP.ZaprimljeniPaket;
import uredi.PrijemPaketa;

public class HandlerPO extends HandlerKomandi {

  String ime = null;
  String paket = null;
  String status = null;

  @Override
  public boolean izvrsiKomandu(String[] komanda) {
    if (izvrsiPO(komanda)) {
      return true;
    }
    return posaljiSljedbeniku(komanda);
  }

  private boolean izvrsiPO(String[] a) {
    if (!a[0].equals("PO")) {
      return false;
    }
    if (a.length < 4) {
      System.out.println("Sintaksa komande je PO 'primatelja/pošiljatelja' paket [D | N]!");
    } else {
      dohvatiDijelove(a);
      promjeniStatusSlanja(ime, paket, status);
    }
    return true;
  }

  private void dohvatiDijelove(String[] a) {
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
      } else if ("D".equals(a[i]) || "N".equals(a[i])) {
        status = a[i];
        paket = a[i - 1];
      }
    }
  }

  private void promjeniStatusSlanja(String ime, String paket, String status) {
    ZaprimljeniPaket zaprimljeni = PrijemPaketa.dohvatiPaket(paket);
    if (zaprimljeni == null) {
      System.out.println("Paket s danom oznakom nije zaprimljen.");
    } else {
      if (zaprimljeni.getPaket().getPosiljatelj().equals(ime)
          || zaprimljeni.getPaket().getPrimatelj().equals(ime)) {
        promjeniStatus(status, zaprimljeni, ime);
      } else {
        System.out.println("Osoba nije niti posiljatelj niti primatelj paketa. " + ime);
      }
    }

  }

  private void promjeniStatus(String status, ZaprimljeniPaket zaprimljeni, String ime) {
    if (status.equals("D") || status.equals("N")) {
      if (status.equals("D")) {
        zaprimljeni.dodajNovogPretplatnika(ime);
      } else {
        zaprimljeni.obrisiPretplatnika(ime);
      }
    } else {
      System.out.println("Status može biti D ili N.");
    }
  }
}

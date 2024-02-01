package chainOfResponibilityKomande;

import java.util.ArrayList;
import java.util.List;
import decoratorIP.ZaprimljeniPaket;
import pomocnici.Sustav;
import stateStatusVozila.Aktivno;
import stateStatusVozila.Neaktivno;
import stateStatusVozila.Neispravno;
import stateStatusVozila.Status;
import uredi.DostavaPaketa;
import uredi.PrijemPaketa;
import visitor.Vozilo;
import vrijeme.VirtualniSat;

public class HandlerSPV extends HandlerKomandi {
  static VirtualniSat sat = VirtualniSat.getInstance();
  static Sustav tvrtka = Sustav.getInstance();

  @Override
  public boolean izvrsiKomandu(String[] komanda) {
    if (izvrsiSPV(komanda)) {
      return true;
    }
    return posaljiSljedbeniku(komanda);
  }

  private boolean izvrsiSPV(String[] a) {
    if (!a[0].equals("SPV")) {
      return false;
    }
    String naziv = dohvatiNaziv(a);
    if (a.length < 2) {
      System.out.println("Sintaksa komande je SPV 'naziv'!");
    } else if (tvrtka.dohvatiCaretaker().postojiNaziv(naziv)) {
      System.out.println("Već postoji spremljeno stanje s tim nazivom! ");
    } else {
      Memento memento = spremiStanje(naziv);
      tvrtka.dohvatiCaretaker().dodajMemento(memento);
      System.out.println("Spremljeno je stanje pod nazivom " + naziv);
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

  private Memento spremiStanje(String naziv) {
    return new Memento(naziv, sat.getTrenutnoVrijeme(), PrijemPaketa.dohvatiPakete(),
        DostavaPaketa.dohvatiVozila());
  }

  public static class Memento {
    private final String virtualniSat;
    private final List<ZaprimljeniPaket> zaprimljeniPaketi = new ArrayList<>();
    private final List<Vozilo> listaVozila = new ArrayList<>();
    private final String nazivVerzije;

    private Memento(String naziv, String sat, List<ZaprimljeniPaket> paketi, List<Vozilo> vozila) {
      for (Vozilo v : vozila) {
        Vozilo vozilo = new Vozilo(v.getRegistracija(), v.getOpis(), v.getKapacitetTezine(),
            v.getKapacitetProstora(), v.getRedoslijed(), v.getProsjecnaBrzina(),
            v.getPodrucjaPoRangu());
        vozilo.setListaVoznji(v.dohvatiSveVoznje());
        vozilo.dodajPouzece(v.dohvatiPouzece());
        if (v.getStatus().toString().equals("NI")) {
          Status novi = new Neispravno();
          vozilo.setStatus(novi);
        } else if (v.getStatus().toString().equals("NA")) {
          Status novi = new Neaktivno();
          vozilo.setStatus(novi);
        } else {
          Status novi = new Aktivno();
          vozilo.setStatus(novi);
        }
        this.listaVozila.add(vozilo);
      }

      this.nazivVerzije = naziv;
      this.virtualniSat = sat;
      this.zaprimljeniPaketi.addAll(paketi);
    }

    public String getVirtualniSat() {
      return virtualniSat;
    }

    public List<ZaprimljeniPaket> getZaprimljeniPaketi() {
      return zaprimljeniPaketi;
    }

    public List<Vozilo> getListaVozila() {
      return listaVozila;
    }

    public String getNazivVerzije() {
      return nazivVerzije;
    }
  }
}

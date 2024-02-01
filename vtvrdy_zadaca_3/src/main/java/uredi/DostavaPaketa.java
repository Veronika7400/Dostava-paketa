package uredi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import compositePodrucja.GradComposite;
import compositePodrucja.PodrucjaComposite;
import compositePodrucja.UlicaLeaf;
import decoratorIP.ZaprimljeniPaket;
import podaci.Osobe;
import pomocnici.Klijent;
import pomocnici.Sustav;
import stateStatusVozila.Aktivno;
import stateStatusVozila.Neaktivno;
import stateStatusVozila.Neispravno;
import stateStatusVozila.Status;
import visitor.Vozilo;
import visitor.Voznja;
import vrijeme.VirtualniSat;

public class DostavaPaketa {
  static Sustav sustav = Sustav.getInstance();
  static VirtualniSat sat = VirtualniSat.getInstance();
  static List<Vozilo> listaVozila = sustav.dohvatiVozilo();

  public static List<Vozilo> dohvatiVozila() {
    return listaVozila;
  }

  public static void azurirajVozila(List<Vozilo> vozila) {
    listaVozila.clear();
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
      listaVozila.add(vozilo);
    }
  }

  public static void pokusajUkracatiPakete() {
    if (!PrijemPaketa.odrediHitne().isEmpty()) {
      for (ZaprimljeniPaket paket : PrijemPaketa.odrediHitne()) {
        ukrcajPaket(paket);
      }
    }
    if (!PrijemPaketa.odrediOstale().isEmpty()) {
      for (ZaprimljeniPaket paket : PrijemPaketa.odrediOstale()) {
        ukrcajPaket(paket);
      }
    }
  }

  private static void ukrcajPaket(ZaprimljeniPaket paket) {
    Vozilo vozilo = null;
    Integer podrucjePaket = odrediPodrucjePaketa(paket);
    if (podrucjePaket == null) {
      System.out.println("Pogreška broj: " + Klijent.brojPogresaka++
          + " Mjesto osobe ne pripada niti jednom području! " + paket.getPaket().getPrimatelj());
    } else {
      vozilo = pronadiVoziloPridruzenoZaPodrucje(paket, podrucjePaket);
      if (vozilo != null) {
        vozilo.getStatus().ukrcajPaket(paket, podrucjePaket, vozilo);
      } else {
        vozilo = pronadiNajviseRangiranoVozilo(paket, podrucjePaket);
        if (vozilo != null) {
          vozilo.getStatus().ukrcajPaket(paket, podrucjePaket, vozilo);
        }
      }
    }
  }

  private static Vozilo pronadiNajviseRangiranoVozilo(ZaprimljeniPaket paket,
      Integer podrucjePaket) {
    Integer rang = 10000;
    Vozilo trazeno = null;
    for (Vozilo vozilo : listaVozila) {
      if (vozilo.getStatus() instanceof Aktivno) {
        if (voziloPridruzenoPodrucjuPaketa(vozilo, podrucjePaket)) {
          if (vozilo.getRangPodrucja(podrucjePaket) < rang
              && raspolozivaTezinaProstor(paket, vozilo)) {
            trazeno = vozilo;
            rang = vozilo.getRangPodrucja(podrucjePaket);
          }
        }
      }
    }
    return trazeno;
  }

  private static boolean raspolozivaTezinaProstor(ZaprimljeniPaket paket, Vozilo vozilo) {
    return vozilo.getKapacitetProstora() >= paket.izracunajProstor()
        && vozilo.getKapacitetTezine() >= paket.getPaket().getTezina() ? true : false;
  }

  private static Vozilo pronadiVoziloPridruzenoZaPodrucje(ZaprimljeniPaket paket,
      Integer podrucjePaket) {
    for (Vozilo vozilo : listaVozila) {
      if (vozilo.getStatus() instanceof Aktivno) {
        Voznja zadnja = vozilo.dohvatiZadnjuVoznju();
        if (zadnja != null && !odvozena(zadnja)) {
          if (zadnja.getIdPodrucja().equals(podrucjePaket)
              && odgovarajucaTezinaProstor(paket, vozilo, zadnja)) {
            return vozilo;

          }
        }
      }
    }
    return null;
  }

  private static boolean odvozena(Voznja zadnja) {
    if (zadnja.dohavtiZadnjiSegment() != null) {
      return zadnja.dohavtiZadnjiSegment().getDoGPS()
          .equals(Klijent.parametri.getProperty("gps").trim());
    }
    return false;
  }

  private static boolean odgovarajucaTezinaProstor(ZaprimljeniPaket paket, Vozilo vozilo,
      Voznja zadnja) {
    return vozilo.getKapacitetProstora() >= (paket.izracunajProstor() + zadnja.getZauzeceProstora())
        && vozilo.getKapacitetTezine() >= (paket.getPaket().getTezina() + zadnja.getZauzeceTezine())
            ? true
            : false;
  }

  private static boolean voziloPridruzenoPodrucjuPaketa(Vozilo vozilo, Integer podrucjePaket) {
    return vozilo.getPodrucjePoRangu(podrucjePaket) == null ? false : true;
  }

  private static Integer odrediPodrucjePaketa(ZaprimljeniPaket paket) {
    Osobe primatelj = null;
    Integer podrucjeId = null;
    for (Osobe osoba : sustav.dohvatiOsobe()) {
      if (osoba.osoba().equals(paket.getPaket().getPrimatelj())) {
        primatelj = osoba;
        break;
      }
    }
    if (primatelj == null) {
      System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " Primatelj ne postoji! "
          + paket.getPaket().getPrimatelj());
      return null;
    } else {
      podrucjeId = pronadiPodrucje(primatelj);
    }
    return podrucjeId;
  }

  private static Integer pronadiPodrucje(Osobe primatelj) {
    for (PodrucjaComposite podrucje : sustav.dohvatiPodrucja()) {
      GradComposite grad = podrucje.dohvatiDjete(primatelj.grad());
      if (grad != null) {
        UlicaLeaf ulica = grad.dohvatiDjete(primatelj.ulica());
        if (ulica != null) {
          return podrucje.dohvatiId();
        }
      }
    }
    return null;
  }

  public static Vozilo dohvatiVozilo(String registracija) {
    for (Vozilo vozilo : listaVozila) {
      if (vozilo.getRegistracija().equals(registracija.trim())) {
        return vozilo;
      }
    }
    return null;
  }

  public static void ispisiDostavljenePakete() {
    for (ZaprimljeniPaket p : PrijemPaketa.dohvatiPaketeSortiranePoPouzecu()) {
      if (!p.getStatusIsporuke().equals("Isporucen")
          && dostavljenPaket(p.getVrijemePreuzimanja())) {
        System.out.println("Isporučen paket: " + p.getPaket().getOznaka() + " u vrijeme: "
            + p.getVrijemePreuzimanja());
        p.setStatusIsporuke("Isporucen");
        if (p.getPaket().getUslugaDostave().equals("P")) {
          voziluDodajIznosPouzeca(p);
        }
      }
    }
  }

  private static void voziluDodajIznosPouzeca(ZaprimljeniPaket p) {
    for (Vozilo vozilo : listaVozila) {
      Voznja voznja = vozilo.dohvatiZadnjuVoznju();
      if (voznja != null) {
        if (voznja.paketUVozilu(p.getPaket().getOznaka())) {
          vozilo.dodajPouzece(p.getPaket().getPouzece());
        }
      }
    }
  }

  private static boolean dostavljenPaket(String vrijemePreuzimanja) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
    LocalDateTime trenutnoVrijeme = LocalDateTime.parse(sat.getTrenutnoVrijeme(), formatter);
    LocalDateTime vrijemePrijema = LocalDateTime.parse(vrijemePreuzimanja, formatter);
    if (vrijemePrijema.isBefore(trenutnoVrijeme)) {
      return true;
    }
    return false;
  }

}

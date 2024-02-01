package uredi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import builderPaketi.Paket;
import decoratorIP.ZaprimljeniPaket;
import observerStatusPaketa.IspisPromjeneStatusa;
import observerStatusPaketa.ObserverPaket;
import pomocnici.Sustav;
import vrijeme.VirtualniSat;

public class PrijemPaketa {
  static Sustav sustav = Sustav.getInstance();
  static VirtualniSat sat = VirtualniSat.getInstance();
  static List<ZaprimljeniPaket> zaprimljeniPaketi = new ArrayList<>();

  public static List<ZaprimljeniPaket> dohvatiPakete() {
    return zaprimljeniPaketi;
  }

  public static void azurirajPakete(List<ZaprimljeniPaket> paketi) {
    zaprimljeniPaketi = new ArrayList<>();
    zaprimljeniPaketi.addAll(paketi);
  }

  public static ZaprimljeniPaket dohvatiPaket(String id) {
    for (ZaprimljeniPaket p : zaprimljeniPaketi) {
      if (p.getPaket().getOznaka().equals(id)) {
        return p;
      }
    }
    return null;
  }

  public ZaprimljeniPaket dohvatiPaket(Integer i) {
    if (i >= 0 && i < zaprimljeniPaketi.size()) {
      return zaprimljeniPaketi.get(i);
    } else {
      return null;
    }
  }

  public static List<ZaprimljeniPaket> odrediHitne() {
    List<ZaprimljeniPaket> paketiHitno = new ArrayList<>();
    for (ZaprimljeniPaket paket : zaprimljeniPaketi) {
      if (paket.getPaket().getUslugaDostave().equals("H")
          && paket.getStatusIsporuke().equals("Zaprimljen")) {
        paketiHitno.add(paket);
      }
    }
    return paketiHitno;
  }

  public static List<ZaprimljeniPaket> odrediOstale() {
    List<ZaprimljeniPaket> ostaliPaketi = new ArrayList<>();
    for (ZaprimljeniPaket paket : zaprimljeniPaketi) {
      if (!paket.getPaket().getUslugaDostave().equals("H")
          && paket.getStatusIsporuke().equals("Zaprimljen")) {
        ostaliPaketi.add(paket);
      }
    }
    return ostaliPaketi;
  }

  public static void zaprimiPakete() {
    if (zaprimljeniPaketi.isEmpty()) {
      for (Paket paket : sustav.dohvatiPaketeZaDostavu()) {
        if (paketStigao(paket, sat.getTrenutnoVrijeme())) {
          ZaprimljeniPaket zaprimi = new ZaprimljeniPaket(paket, "Zaprimljen", null);
          ObserverPaket observer = new IspisPromjeneStatusa(zaprimi);
          zaprimi.dodajNacinObavjesti(observer);
          zaprimljeniPaketi.add(zaprimi);
        } else {
          break;
        }
      }
    } else {
      for (Paket paket : sustav.dohvatiPaketeZaDostavu()) {
        if (paketStigao(paket, sat.getTrenutnoVrijeme())) {
          if (nijeNaPopisu(paket)) {
            ZaprimljeniPaket zaprimi = new ZaprimljeniPaket(paket, "Zaprimljen", null);
            ObserverPaket observer = new IspisPromjeneStatusa(zaprimi);
            zaprimi.dodajNacinObavjesti(observer);
            zaprimljeniPaketi.add(zaprimi);
          }
        } else {
          break;
        }
      }
    }
  }

  private static boolean nijeNaPopisu(Paket paket) {
    for (ZaprimljeniPaket zp : zaprimljeniPaketi) {
      if (zp.getPaket().getOznaka().equals(paket.getOznaka())) {
        return false;
      }
    }
    return true;
  }

  private static boolean paketStigao(Paket paket, String trenutnoVrijeme) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
    LocalDateTime vrijemePrijema = LocalDateTime.parse(paket.getVrijemePrijema(), formatter);
    LocalDateTime virtualnoVrijeme = LocalDateTime.parse(trenutnoVrijeme, formatter);
    if (vrijemePrijema.isBefore(virtualnoVrijeme) || vrijemePrijema.isEqual(virtualnoVrijeme)) {
      return true;
    }
    return false;
  }

  public static List<ZaprimljeniPaket> dohvatiPaketeSortiranePoPouzecu() {
    List<ZaprimljeniPaket> sortirani = new ArrayList<>();
    for (ZaprimljeniPaket z : zaprimljeniPaketi) {
      if (z.getStatusIsporuke().equals("Ukrcan") && z.getVrijemePreuzimanja() != null) {
        sortirani.add(z);
      }
    }
    sortirani.sort((paket1, paket2) -> paket1.getVrijemePreuzimanja()
        .compareTo(paket2.getVrijemePreuzimanja()));

    return sortirani;
  }


}

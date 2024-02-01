package strategyIsporukaPaketa;

import decoratorIP.ZaprimljeniPaket;
import podaci.Osobe;
import podaci.Ulica;
import pomocnici.Klijent;
import pomocnici.Sustav;
import uredi.DostavaPaketa;
import uredi.PrijemPaketa;
import visitor.SegmentVoznje;
import visitor.Vozilo;
import visitor.Voznja;
import vrijeme.VirtualniSat;

public class Isporuka1 implements IsporukaPaketa {
  static VirtualniSat sat = VirtualniSat.getInstance();
  static Sustav sustav = Sustav.getInstance();

  @Override
  public void isporuci() {
    for (Vozilo vozilo : DostavaPaketa.dohvatiVozila()) {
      Voznja zadnja = vozilo.dohvatiZadnjuVoznju();
      if (zadnja != null) {
        if (!jeIsporuceno(zadnja)) {
          isporuciPakete(zadnja, vozilo);
          izracunajPovratakUUred(vozilo);
        }
      }
    }
  }

  private void isporuciPakete(Voznja zadnja, Vozilo vozilo) {
    for (String idPaketa : zadnja.getPaketiUVozilu()) {
      ZaprimljeniPaket paket = PrijemPaketa.dohvatiPaket(idPaketa);
      SegmentVoznje segment = new SegmentVoznje();
      if (zadnja.dohavtiZadnjiSegment() == null) {
        segment.setOdGPS(Klijent.parametri.getProperty("gps").trim());
        segment.setVrijemePocetka(sat.getTrenutnoVrijeme());
      } else {
        segment.setOdGPS(zadnja.dohavtiZadnjiSegment().getDoGPS());
        String krajProslog = vozilo.dohvatiZadnjuVoznju().dohavtiZadnjiSegment().getVrijemeKraja();
        segment.setVrijemePocetka(krajProslog);
      }
      segment.setDoGPS(dohvatiGpsPaketa(paket));
      segment.setIdPaketa(paket.getPaket().getOznaka());
      segment
          .setTrajanjeIsporuke(Integer.parseInt(Klijent.parametri.getProperty("vi").trim()) * 60);
      segment.setUdaljenost(izracunajUdaljenost(segment.getOdGPS(), segment.getDoGPS()));
      segment.setTrajanjeVoznje((int) (izracinajVrijemeVoznje(segment, vozilo) * 3600));
      segment.setUkupnoTrajanje(segment.getTrajanjeIsporuke() + segment.getTrajanjeVoznje());
      double krajSekunde =
          sat.pretvoriUsekunde(segment.getVrijemePocetka()) + segment.getUkupnoTrajanje();
      segment.setVrijemeKraja(sat.pretvoriUString((long) krajSekunde));
      paket.setVrijemePreuzimanja(segment.getVrijemeKraja());
      zadnja.setVoznju(segment);
    }
  }

  private boolean jeIsporuceno(Voznja zadnja) {
    if (zadnja.dohavtiZadnjiSegment() != null) {
      return zadnja.dohavtiZadnjiSegment().getDoGPS()
          .equals(Klijent.parametri.getProperty("gps").trim());
    }
    return false;
  }

  private static void izracunajPovratakUUred(Vozilo vozilo) {
    Voznja voznja = vozilo.dohvatiZadnjuVoznju();
    if (voznja != null) {
      String gps = vozilo.dohvatiZadnjuVoznju().dohavtiZadnjiSegment().getDoGPS();
      if (!gps.equals(Klijent.parametri.getProperty("gps").trim())) {
        SegmentVoznje segment = new SegmentVoznje();
        segment.setOdGPS(gps);
        segment.setDoGPS(Klijent.parametri.getProperty("gps").trim());
        segment.setIdPaketa(null);
        segment.setUdaljenost(izracunajUdaljenost(segment.getOdGPS(), segment.getDoGPS()));
        segment.setTrajanjeIsporuke(0);
        segment.setTrajanjeVoznje((int) (izracinajVrijemeVoznje(segment, vozilo) * 3600));
        segment.setUkupnoTrajanje(segment.getTrajanjeIsporuke() + segment.getTrajanjeVoznje());
        String krajProslog = voznja.dohavtiZadnjiSegment().getVrijemeKraja();
        segment.setVrijemePocetka(krajProslog);
        double krajSekunde =
            sat.pretvoriUsekunde(segment.getVrijemePocetka()) + segment.getUkupnoTrajanje();
        segment.setVrijemeKraja(sat.pretvoriUString((long) krajSekunde));
        voznja.setVoznju(segment);
        System.out.println("Vozilo kreće u dostavu paketa! " + vozilo.getRegistracija()
            + " U vrijeme: " + sat.getTrenutnoVrijeme());
      }
    }
  }

  /**
   * Udaljenost se računa pomoću Haversine formule. Kod za izračun je preuzet sa stranice
   * https://www.baeldung.com/java-find-distance-between-points
   **/
  private static double izracunajUdaljenost(String string, String string2) {
    double EARTH_RADIUS = 6371;
    double latitude1 = Double.parseDouble(string.split(",")[0].trim());
    double longitude1 = Double.parseDouble(string.split(",")[1].trim());
    double latitude2 = Double.parseDouble(string2.split(",")[0].trim());
    double longitude2 = Double.parseDouble(string2.split(",")[1].trim());


    double dLat = Math.toRadians((latitude2 - latitude1));
    double dLong = Math.toRadians((longitude2 - longitude1));

    latitude1 = Math.toRadians(latitude1);
    latitude2 = Math.toRadians(latitude2);

    double a = haversine(dLat) + Math.cos(latitude1) * Math.cos(latitude2) * haversine(dLong);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return EARTH_RADIUS * c;
  }

  static double haversine(double val) {
    return Math.pow(Math.sin(val / 2), 2);
  }

  private static Double izracinajVrijemeVoznje(SegmentVoznje segment, Vozilo vozilo) {
    return (Double) ((segment.getUdaljenost() / vozilo.getProsjecnaBrzina()));
  }

  private static String dohvatiGpsPaketa(ZaprimljeniPaket paket) {
    for (Osobe osoba : sustav.dohvatiOsobe()) {
      if (osoba.osoba().equals(paket.getPaket().getPrimatelj())) {
        Ulica ulica = sustav.dohvatiUlicu(osoba.ulica());
        Integer kucniBroj;
        if (osoba.kbr() > ulica.maxBr()) {
          kucniBroj = ulica.maxBr();
        } else {
          kucniBroj = osoba.kbr();
        }

        double a = Math.abs(ulica.gps_lon_2() - ulica.gps_lon_1());
        double b = Math.abs(ulica.gps_lat_2() - ulica.gps_lat_1());
        double c = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        double c1 = (kucniBroj / ulica.maxBr()) * c;
        double sinAlpha = a / c;
        double a1 = sinAlpha * c1;
        double b1 = Math.sqrt(Math.pow(c1, 2) - Math.pow(a1, 2));
        double latP = ulica.gps_lat_1() + b1;
        double lonP = ulica.gps_lon_1() + a1;
        return String.format("%.6f, %.6f", latP, lonP);
      }
    }
    return null;
  }
}

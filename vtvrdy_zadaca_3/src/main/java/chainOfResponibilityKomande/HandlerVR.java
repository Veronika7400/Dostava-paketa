package chainOfResponibilityKomande;

import pomocnici.Klijent;
import pomocnici.Sustav;
import strategyIsporukaPaketa.Isporuka1;
import strategyIsporukaPaketa.Isporuka2;
import strategyIsporukaPaketa.IsporukaPaketa;
import uredi.DostavaPaketa;
import uredi.PrijemPaketa;
import vrijeme.VirtualniSat;

public class HandlerVR extends HandlerKomandi {
  static VirtualniSat sat = VirtualniSat.getInstance();
  static Sustav tvrtka = Sustav.getInstance();
  static IsporukaPaketa isporuka;

  @Override
  public boolean izvrsiKomandu(String[] komanda) {
    if (izvrsiVR(komanda)) {
      return true;
    }
    return posaljiSljedbeniku(komanda);
  }

  private boolean izvrsiVR(String[] a) {
    if (!a[0].equals("VR")) {
      return false;
    }
    if (a.length != 2) {
      System.out.println("Sintaksa komande je VR hh!");
    } else {
      if (Klijent.parametri.getProperty("isporuka").trim().equals("1")) {
        isporuka = new Isporuka1();
      } else {
        isporuka = new Isporuka2();
      }
      int sati = provjeriSate(a[1]);
      korigiraj(sati);
    }
    return true;
  }

  private static int provjeriSate(String a) {
    try {
      return Integer.parseInt(a);
    } catch (NumberFormatException e) {
      System.out.println(a + " Mora biti cijeli broj! ");
    }
    return 0;
  }

  public static void korigiraj(int sati) {

    long krajRada = pretvoriUsekundeVrijeme(tvrtka.dohvatiKrajRada());
    long pocetakRada = pretvoriUsekundeVrijeme(tvrtka.dohvatiPocetakRada());
    long zeljeniZavrsetak = sat.getTrenutnoVrijemeSekunde() + (sati * 3600);
    long trenutno = sat.pretvoriUsekunde(sat.getTrenutnoVrijeme());
    boolean krajRadnogVremena = true;

    while (trenutno < zeljeniZavrsetak) {
      odradiSpavanje();
      korigirajVirtualniSat(1);
      DostavaPaketa.ispisiDostavljenePakete();
      ispisiVrijeme();
      trenutno = sat.pretvoriUsekunde(sat.getTrenutnoVrijeme());

      if (trenutno >= pocetakRada && trenutno < krajRada) {
        if (puniSat(trenutno)) {
          PrijemPaketa.zaprimiPakete();
          DostavaPaketa.pokusajUkracatiPakete();
          isporuka.isporuci();
        }
      }

      trenutno = sat.pretvoriUsekunde(sat.getTrenutnoVrijeme());
      if (trenutno >= zeljeniZavrsetak) {
        krajRadnogVremena = false;
      }
    }
    if (krajRadnogVremena) {
      System.out.println("Prekid programa zbog kraja radnog vremena!");
    } else {
      System.out.println("Prekid programa zbog dolaska do željenog vremena!");
    }
  }

  private static boolean puniSat(long trenutno) {
    return trenutno % 3600 == 0;
  }

  private static long pretvoriUsekundeVrijeme(String dohvatiKrajRada) {
    String[] dijelovi = dohvatiKrajRada.split(":");
    int sati = Integer.parseInt(dijelovi[0]);
    int minute = Integer.parseInt(dijelovi[1]);
    return (sati * 3600) + (minute * 60);
  }

  private static void ispisiVrijeme() {
    System.out.println(sat.getTrenutnoVrijeme());

  }

  private static void korigirajVirtualniSat(int sekunde) {
    sat.korigirajVrijeme(sekunde);
  }

  private static void odradiSpavanje() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      System.out.println("Pogreška prilikom pokušaja spavanja!");
    }
  }

}

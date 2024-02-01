package vrijeme;

public class VirtualniSat {
  private static VirtualniSat instance;
  private static String virtualnoVrijeme;
  private static long virtualnoVrijemeSekunde;
  private static int mnoziteljSekunde;

  private VirtualniSat() {}

  public static VirtualniSat getInstance() {
    if (instance == null) {
      instance = new VirtualniSat();
    }
    return instance;
  }

  public void postaviPocetnoVrijeme(String vrijeme) {
    virtualnoVrijeme = vrijeme;
    virtualnoVrijemeSekunde = pretvoriUsekunde(vrijeme);

  }

  public void postaviMnoziteljSekunde(int ms) {
    mnoziteljSekunde = ms;
  }

  public void korigirajVrijeme(int s) {
    virtualnoVrijemeSekunde = korigiraj(s);
    virtualnoVrijeme = pretvoriUString(virtualnoVrijemeSekunde);
  }

  public long korigiraj(int s) {
    return virtualnoVrijemeSekunde + s * mnoziteljSekunde;
  }

  public String pretvoriUString(long virtualnoVrijemeSekunde2) {
    long sati = virtualnoVrijemeSekunde2 / 3600;
    long minute = (virtualnoVrijemeSekunde2 % 3600) / 60;
    long preostaleSekunde = virtualnoVrijemeSekunde2 % 60;

    String vremenskiString = String.format("%02d:%02d:%02d", sati, minute, preostaleSekunde);
    return virtualnoVrijeme.split(" ")[0] + " " + vremenskiString;
  }

  public String pretvoriUStringSate(long virtualnoVrijemeSekunde2) {
    long sati = virtualnoVrijemeSekunde2 / 3600;
    long minute = (virtualnoVrijemeSekunde2 % 3600) / 60;
    long preostaleSekunde = virtualnoVrijemeSekunde2 % 60;

    String vremenskiString = String.format("%02d:%02d:%02d", sati, minute, preostaleSekunde);
    return vremenskiString;
  }

  public int pretvoriUsekunde(String virtualnoVrijeme2) {
    String[] dijelovi = virtualnoVrijeme2.split(" ");
    String[] razdvoji = dijelovi[1].split(":");

    int sati = Integer.parseInt(razdvoji[0]);
    int minute = Integer.parseInt(razdvoji[1]);
    int sekunde = Integer.parseInt(razdvoji[2]);

    return sati * 3600 + minute * 60 + sekunde;
  }

  public String getTrenutnoVrijeme() {
    return virtualnoVrijeme;
  }

  public long getTrenutnoVrijemeSekunde() {
    return virtualnoVrijemeSekunde;
  }

}

package adapter;

import java.util.Properties;
import java.util.regex.Pattern;

public class Provjera2 implements ProvjeraParametara {

  Provjera1Adapter provjera1;


  public Provjera2() {
    this.provjera1 = new Provjera1Adapter();
  }

  @Override
  public boolean provjeriParametre(Properties parametri) {
    if (provjera1.provjeriParametre(parametri)) {
      String pu = parametri.getProperty("pu");
      if (pu == null || pu.trim().isEmpty()) {
        System.out.println("Nije zadan naziv datoteke ulice!");
        return false;
      }

      String pm = parametri.getProperty("pm");
      if (pm == null || pm.trim().isEmpty()) {
        System.out.println("Nije zadan naziv datoteke mjesta!");
        return false;
      }

      String pmu = parametri.getProperty("pmu");
      if (pmu == null || pmu.trim().isEmpty()) {
        System.out.println("Nije zadan naziv datoteke podrucja!");
        return false;
      }

      String vi = parametri.getProperty("vi");
      if (vi == null || vi.trim().isEmpty()) {
        System.out.println("Nije zadano vrijeme postupka isporuke!");
        return false;
      } else if (!jeInteger(vi)) {
        System.out.println("Vrijeme postupka isporuke mora biti cijeli broj!");
        return false;
      }

      String gps = parametri.getProperty("gps");
      if (gps == null || gps.trim().isEmpty()) {
        System.out.println("Nije zadan gps ureda!");
        return false;
      } else if (!validanGpsFormat(gps)) {
        System.out.println("Gps ureda nije u ispravnom formatu! " + gps);
        return false;
      }

      String isporuka = parametri.getProperty("isporuka");
      if (isporuka == null || isporuka.trim().isEmpty()) {
        System.out.println("Nije zadan parametar isporuka!");
        return false;
      } else if (!jeInteger(isporuka)) {
        System.out.println("Parametar isporuka mora biti cijeli broj!");
        return false;
      }
      return true;
    }

    return false;
  }

  private boolean jeInteger(String broj) {
    try {
      Integer.parseInt(broj);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  private boolean validanGpsFormat(String gps) {
    String gps1 = gps.split(",")[0].trim();
    String gps2 = gps.split(",")[1].trim();
    final String regExGps = "[-+]?\\d{1,3}\\.\\d{6}$";
    Pattern patternRegExGps = Pattern.compile(regExGps);
    if (!patternRegExGps.matcher(gps1.trim()).matches()
        || !patternRegExGps.matcher(gps2.trim()).matches()) {
      return false;
    }
    return true;
  }

}

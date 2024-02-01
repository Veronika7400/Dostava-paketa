package adapter;

import java.util.regex.Pattern;

public class Provjera1 {
  String regExDatumVrijeme =
      "vs=([0-2][0-9]||3[0-1]).(0[0-9]||1[0-2]).([0-9][0-9])?[0-9][0-9]. [0-2]?[0-9]:[0-9]?[0-9]:[0-5]?[0-9]";
  Pattern patternRegExpDatumVrijeme = Pattern.compile(regExDatumVrijeme);

  String regExVrijemePr = "pr=([0-1]?[0-9]|2[0-3]):[0-5][0-9]";
  Pattern patternRegExpVrijemePr = Pattern.compile(regExVrijemePr);

  String regExVrijemeKr = "kr=([0-1]?[0-9]|2[0-3]):[0-5][0-9]";
  Pattern patternRegExpVrijemeKr = Pattern.compile(regExVrijemeKr);

  String regExVP = "vp=\\S+\\.csv";
  Pattern patternRegExpVP = Pattern.compile(regExVP);

  String regExPV = "pv=\\S+\\.csv";
  Pattern patternRegExpPV = Pattern.compile(regExPV);

  String regExPP = "pp=\\S+\\.csv";
  Pattern patternRegExpPP = Pattern.compile(regExPP);

  String regExMT = "mt=\\d+$";
  Pattern patternRegExpMT = Pattern.compile(regExMT);

  String regExMS = "ms=\\d+$";
  Pattern patternRegExpMS = Pattern.compile(regExMS);

  Integer argumentiDZ1 = 0;


  public Provjera1() {}

  public boolean provjeriParametre1(String parametri) {
    String[] args = parametri.split(",");
    for (int i = 0; i < args.length; i++) {
      String arg = args[i].trim();
      provjeriParametar(arg);
    }
    if (argumentiDZ1 == 8) {
      return true;
    } else {
      System.out.println("Parametri provjeravani u prvoj zadaći nisu svi ispravni!\n");
      return false;
    }
  }

  private void provjeriParametar(String arg) {
    if (nijeU2Grupi(arg)) {
    } else if (patternRegExpDatumVrijeme.matcher(arg).matches()) {
      argumentiDZ1++;
    } else if (patternRegExpVrijemePr.matcher(arg).matches()) {
      argumentiDZ1++;
    } else if (patternRegExpVrijemeKr.matcher(arg).matches()) {
      argumentiDZ1++;
    } else if (patternRegExpVP.matcher(arg).matches()) {
      argumentiDZ1++;
    } else if (patternRegExpPV.matcher(arg).matches()) {
      argumentiDZ1++;
    } else if (patternRegExpPP.matcher(arg).matches()) {
      argumentiDZ1++;
    } else if (patternRegExpMT.matcher(arg).matches()) {
      argumentiDZ1++;
    } else if (patternRegExpMS.matcher(arg).matches()) {
      argumentiDZ1++;
    } else {
      System.out.println("Parametar nije ispravan! " + arg);
    }
  }

  private boolean nijeU2Grupi(String arg) {
    String[] elementi = {"po=", "pm=", "pu=", "pmu=", "vi=", "gps=", "isporuka="};
    for (String element : elementi) {
      if (arg.contains(element)) {
        return true;
      } else if (jeGps(arg)) {
        return true;
      }
    }
    return false;
  }

  private boolean jeGps(String arg) {
    String regExGps = "[-+]?\\d{1,3}\\.\\d{6}$";
    Pattern patternRegExGps = Pattern.compile(regExGps);
    return patternRegExGps.matcher(arg).matches() ? true : false;
  }

}

package podaci;


/**
 * Enumeracija mogućih vrsta dostave
 * 
 * @author vtvrdy
 *
 */
public enum TipDostave {
  Standardna("S"), Hitna("H"), Plaćanje_pouzećem("P"), Povratak_paketa("R");

  private String oznaka;

  private TipDostave(String oznaka) {
    this.oznaka = oznaka;
  }

  public static boolean postojiOznaka(String oznaka) {
    for (TipDostave td : TipDostave.values()) {
      if (td.oznaka.equals(oznaka)) {
        return true;
      }
    }
    return false;
  }
}


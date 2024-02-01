package observerStatusPaketa;

import java.util.List;
import decoratorIP.ZaprimljeniPaket;

public abstract class ObserverPaket {
  protected ZaprimljeniPaket zaprimljeniPaket;

  protected List<String> pretplatnici;

  public abstract void obavjesti();

  public abstract void dodajPretplatnika(String imePrezime);

  public abstract void obrisiPretplatnika(String imePrezime);

}

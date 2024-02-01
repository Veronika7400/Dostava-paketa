package decoratorIP;

import builderPaketi.Paket;

public interface IZaprimljeniPaket {

  public Paket getPaket();

  public String getVrijemePreuzimanja();

  public String getPrijevoz();

  public String getStatusIsporuke();
}

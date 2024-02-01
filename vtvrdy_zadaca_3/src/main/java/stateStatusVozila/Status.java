package stateStatusVozila;

import decoratorIP.ZaprimljeniPaket;
import visitor.Vozilo;

public interface Status {

  public void promjeniStatus(Vozilo vozilo, String noviStatus);

  public void ukrcajPaket(ZaprimljeniPaket paket, Integer podrucjePaket, Vozilo vozilo);

}

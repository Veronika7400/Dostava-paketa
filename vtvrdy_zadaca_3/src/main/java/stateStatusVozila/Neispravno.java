package stateStatusVozila;

import decoratorIP.ZaprimljeniPaket;
import visitor.Vozilo;

public class Neispravno implements Status {
  String status;

  public Neispravno() {
    this.status = "NI";
  }

  @Override
  public void promjeniStatus(Vozilo vozilo, String noviStatus) {
    System.out.println(
        "Vozilo je u neispravnom stanju i ne možete mu promjeniti status do kraja programa! ");
  }

  public String toString() {
    return status;
  }

  @Override
  public void ukrcajPaket(ZaprimljeniPaket paket, Integer podrucjePaket, Vozilo vozilo) {
    // TODO Auto-generated method stub
  }

}

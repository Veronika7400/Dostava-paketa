package stateStatusVozila;

import decoratorIP.ZaprimljeniPaket;
import visitor.Vozilo;

public class Neaktivno implements Status {
  String status;

  public Neaktivno() {
    this.status = "NA";
  }

  @Override
  public void promjeniStatus(Vozilo vozilo, String noviStatus) {
    if (noviStatus.equals("NI")) {
      Status novi = new Neispravno();
      vozilo.setStatus(novi);
      System.out.println(
          "Vozilu " + vozilo.getRegistracija() + " se postavlja status da je neispravno! ");
    } else if (noviStatus.equals("A")) {
      Status novi = new Aktivno();
      vozilo.setStatus(novi);
      System.out
          .println("Vozilu " + vozilo.getRegistracija() + " se postavlja status da je aktivno! ");
    } else if (noviStatus.equals("NA")) {
      System.out.println("Vozilo " + vozilo.getRegistracija() + " je već u tom stanju! ");
    } else {
      System.out.println("Status ne postoji! " + noviStatus);
    }
  }

  public String toString() {
    return status;
  }

  @Override
  public void ukrcajPaket(ZaprimljeniPaket paket, Integer podrucjePaket, Vozilo vozilo) {
    // TODO Auto-generated method stub
  }

}

package factoryMethodUcitavanjeDatoteka;

import podaci.Osobe;
import pomocnici.Klijent;

public class ProductOsobe implements Product {

  Boolean admin = false;

  @Override
  public Osobe stvoriNovi(String[] stupci) {
    if (Klijent.brojAdmina < 5) {
      admin = true;
      Klijent.brojAdmina++;
    }

    Osobe osobe = null;
    try {
      osobe = new Osobe(stupci[0], (int) Double.parseDouble(stupci[1].replace(",", ".")),
          (int) Double.parseDouble(stupci[2].replace(",", ".")),
          (int) Double.parseDouble(stupci[3].replace(",", ".")), admin);
    } catch (NumberFormatException e) {
      System.out.println("Greska prilikom stvaranja osobe! ");
    }
    return osobe;
  }

}

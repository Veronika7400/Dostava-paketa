
package factoryMethodUcitavanjeDatoteka;

import podaci.VrstaPaketa;

public class ProductVrstaPaketa implements Product {
  Integer maxTezina;

  @Override
  public VrstaPaketa stvoriNovi(String[] stupci) {
    VrstaPaketa paket = null;

    if (stupci[0].equals("X")) {
      paket = null;
      try {
        paket = new VrstaPaketa(stupci[0], stupci[1], Float.parseFloat(stupci[2].replace(",", ".")),
            Float.parseFloat(stupci[3].replace(",", ".")),
            Float.parseFloat(stupci[4].replace(",", ".")), maxTezina,
            Float.parseFloat(stupci[6].replace(",", ".")),
            Float.parseFloat(stupci[7].replace(",", ".")),
            Float.parseFloat(stupci[8].replace(",", ".")),
            Float.parseFloat(stupci[9].replace(",", ".")));
      } catch (NumberFormatException e) {
        System.out.println("Greska prilikom stvaranja vrste paketa! ");
      }
    } else {
      paket = new VrstaPaketa(stupci[0], stupci[1], Float.parseFloat(stupci[2].replace(",", ".")),
          Float.parseFloat(stupci[3].replace(",", ".")),
          Float.parseFloat(stupci[4].replace(",", ".")),
          Float.parseFloat(stupci[5].replace(",", ".")),
          Float.parseFloat(stupci[6].replace(",", ".")),
          Float.parseFloat(stupci[7].replace(",", ".")),
          Float.parseFloat(stupci[8].replace(",", ".")),
          Float.parseFloat(stupci[9].replace(",", ".")));
    }
    return paket;
  }

  public void dodajMaxTezinu(Integer maxTezina) {
    this.maxTezina = maxTezina;
  }

}

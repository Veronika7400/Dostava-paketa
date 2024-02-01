package factoryMethodUcitavanjeDatoteka;

import podaci.Ulica;

public class ProductUlica implements Product {

  @Override
  public Ulica stvoriNovi(String[] stupci) {
    Ulica ulica = null;
    try {
      ulica = new Ulica((int) Double.parseDouble(stupci[0].replace(",", ".")), stupci[1],
          Float.parseFloat(stupci[2].replace(",", ".")),
          Float.parseFloat(stupci[3].replace(",", ".")),
          Float.parseFloat(stupci[4].replace(",", ".")),
          Float.parseFloat(stupci[5].replace(",", ".")),
          (int) Double.parseDouble(stupci[6].replace(",", ".")));
    } catch (NumberFormatException e) {
      System.out.println("Greska prilikom stvaranja ulice! ");
    }
    return ulica;
  }

}

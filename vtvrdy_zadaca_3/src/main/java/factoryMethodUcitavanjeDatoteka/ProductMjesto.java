package factoryMethodUcitavanjeDatoteka;

import java.util.ArrayList;
import java.util.List;
import podaci.Mjesto;

public class ProductMjesto implements Product {

  @Override
  public Mjesto stvoriNovi(String[] stupci) {
    Mjesto mjesto = null;
    List<Integer> listaUlica = new ArrayList<>();
    listaUlica = dohvatiListuUlica(stupci[2]);
    try {
      mjesto =
          new Mjesto((int) Double.parseDouble(stupci[0].replace(",", ".")), stupci[1], listaUlica);
    } catch (NumberFormatException e) {
      System.out.println("Greska prilikom stvaranja mjesta! ");
    }
    return mjesto;
  }

  private List<Integer> dohvatiListuUlica(String string) {
    string = string.replace("[", "");
    string = string.replace("]", "");
    String[] uliceString = string.split(",");
    List<Integer> integeri = new ArrayList<>();
    for (String s : uliceString) {
      try {
        s = s.trim();
        integeri.add(Integer.parseInt(s));
      } catch (NumberFormatException e) {
        System.out.println("Nije moguće pretvoriti u integer u productMjesto: " + s);
      }
    }
    return integeri;
  }

}

package factoryMethodUcitavanjeDatoteka;

import java.util.ArrayList;
import java.util.List;
import stateStatusVozila.Aktivno;
import stateStatusVozila.Neaktivno;
import stateStatusVozila.Neispravno;
import stateStatusVozila.Status;
import visitor.Vozilo;

public class ProductVozila implements Product {

  @Override
  public Vozilo stvoriNovi(String[] stupci) {
    Vozilo vozilo = null;
    try {

      vozilo = new Vozilo(stupci[0], stupci[1],
          (int) Double.parseDouble(stupci[2].trim().replace(",", ".")),
          Float.parseFloat(stupci[3].trim().replace(",", ".")),
          (int) Double.parseDouble(stupci[4].trim().replace(",", ".")),
          Integer.parseInt(stupci[5].trim().replace(",", ".")),
          dohvatiPodrucjaPoRangu(stupci[6].trim()));

      postaviStatus(stupci[7].trim(), vozilo);
    } catch (NumberFormatException e) {
      System.out.println("Greska prilikom stvaranja vozila! " + e.getMessage());
    }
    return vozilo;
  }

  private void postaviStatus(String trim, Vozilo vozilo) {
    if (trim.equals("A")) {
      Status status = new Aktivno();
      vozilo.setStatus(status);
    } else if (trim.equals("NA")) {
      Status status = new Neaktivno();
      vozilo.setStatus(status);
    } else {
      Status status = new Neispravno();
      vozilo.setStatus(status);
    }
  }

  private List<Integer> dohvatiPodrucjaPoRangu(String string) {
    List<Integer> lista = new ArrayList<>();
    String[] podrucja = string.split(",");

    for (String p : podrucja) {
      lista.add(Integer.parseInt(p.trim()));
    }
    return lista;
  }
}

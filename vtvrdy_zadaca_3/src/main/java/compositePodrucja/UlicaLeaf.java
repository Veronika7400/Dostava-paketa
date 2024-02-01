package compositePodrucja;

import java.util.List;
import podaci.Ulica;
import pomocnici.Sustav;

public class UlicaLeaf implements IPodrucje {

  private Integer id;
  protected Sustav sustav = Sustav.getInstance();

  public UlicaLeaf(Integer id) {
    this.id = id;
  }

  @Override
  public boolean provjeriPostojanje(Integer id) {
    List<Ulica> ulice = sustav.dohvatiUlice();
    for (Ulica u : ulice) {
      if (u.id().equals(id)) {
        return true;
      }
    }
    return false;
  }

  public Integer dohvatiId() {
    return id;
  }

  public String dohvatiNaziv() {
    List<Ulica> ulice = sustav.dohvatiUlice();
    for (Ulica u : ulice) {
      if (u.id().equals(id)) {
        return u.naziv();
      }
    }
    return null;
  }

}

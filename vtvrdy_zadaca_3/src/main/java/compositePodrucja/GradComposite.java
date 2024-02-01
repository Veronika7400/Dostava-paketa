package compositePodrucja;

import java.util.ArrayList;
import java.util.List;
import podaci.Mjesto;
import pomocnici.Sustav;

public class GradComposite implements IPodrucje {

  Integer id = null;
  List<UlicaLeaf> ulice = new ArrayList<>();
  protected Sustav sustav = Sustav.getInstance();

  public GradComposite(Integer id) {
    this.id = id;
  }

  @Override
  public boolean provjeriPostojanje(Integer id) {
    List<Mjesto> mjesta = sustav.dohvatiMjesta();
    for (Mjesto mjesto : mjesta) {
      if (mjesto.id().equals(this.id)) {
        return mjesto.ulice().contains(id);
      }
    }
    return false;
  }

  public void dodaj(UlicaLeaf ulica) {
    ulice.add(ulica);
  }

  public void obrisi(UlicaLeaf ulica) {
    ulice.remove(ulica);
  }

  public List<UlicaLeaf> dohvatiDjecu() {
    return ulice;
  }

  public UlicaLeaf dohvatiDjete(Integer id) {
    for (UlicaLeaf ulica : ulice) {
      if (ulica.dohvatiId().equals(id)) {
        return ulica;
      }
    }
    return null;
  }

  public Integer dohvatiId() {
    return id;
  }

  public String dohvatiNaziv() {
    List<Mjesto> mjesta = sustav.dohvatiMjesta();
    for (Mjesto mjesto : mjesta) {
      if (mjesto.id().equals(this.id)) {
        return mjesto.naziv();
      }
    }
    return null;
  }
}

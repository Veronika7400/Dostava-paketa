package compositePodrucja;

import java.util.ArrayList;
import java.util.List;
import podaci.Mjesto;
import pomocnici.Sustav;

public class PodrucjaComposite implements IPodrucje {

  Integer id = null;
  List<GradComposite> obuhvacenaPodrucja = new ArrayList<>();
  protected Sustav sustav = Sustav.getInstance();

  public PodrucjaComposite(Integer id) {
    this.id = id;
  }

  @Override
  public boolean provjeriPostojanje(Integer id) {
    List<Mjesto> mjesta = sustav.dohvatiMjesta();
    for (Mjesto mjesto : mjesta) {
      if (mjesto.id().equals(id)) {
        return true;
      }
    }
    return false;
  }

  public void dodaj(GradComposite grad) {
    obuhvacenaPodrucja.add(grad);
  }

  public void obrisi(GradComposite grad) {
    obuhvacenaPodrucja.remove(grad);
  }

  public List<GradComposite> dohvatiDjecu() {
    return obuhvacenaPodrucja;
  }

  public GradComposite dohvatiDjete(Integer id) {
    for (GradComposite grad : obuhvacenaPodrucja) {
      if (grad.dohvatiId().equals(id)) {
        return grad;
      }
    }
    return null;
  }

  public Integer dohvatiId() {
    return id;
  }

}

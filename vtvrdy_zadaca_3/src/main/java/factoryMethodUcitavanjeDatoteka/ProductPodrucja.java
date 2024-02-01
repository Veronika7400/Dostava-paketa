package factoryMethodUcitavanjeDatoteka;

import java.util.ArrayList;
import java.util.List;
import compositePodrucja.GradComposite;
import compositePodrucja.PodrucjaComposite;
import compositePodrucja.UlicaLeaf;
import podaci.Mjesto;
import pomocnici.Klijent;
import pomocnici.Sustav;

public class ProductPodrucja implements Product {

  protected PodrucjaComposite podrucje;
  protected Sustav sustav = Sustav.getInstance();
  protected int rbroj;

  @Override
  public PodrucjaComposite stvoriNovi(String[] stupci) {
    podrucje = new PodrucjaComposite(Integer.parseInt(stupci[0].trim()));
    provjeriPodrucja(stupci[1]);
    return podrucje;
  }

  private void provjeriPodrucja(String stupac) {
    String[] parovi = stupac.split(",");

    for (String par : parovi) {
      String[] odvoji = par.split(":");
      if (!podrucje.provjeriPostojanje(Integer.parseInt(odvoji[0].trim()))) {
        System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
            + " Grad ne postoji! " + stupac);
      } else if (odvoji[1].trim().equals("*")) {
        GradComposite grad = new GradComposite(Integer.parseInt(odvoji[0].trim()));
        List<Integer> trazeneUlice = dohvatiUliceGrada(odvoji[0]);
        List<UlicaLeaf> uliceDodati = generirajListuUlica(trazeneUlice);
        dodajSve(uliceDodati, grad);
        podrucje.dodaj(grad);
      } else {
        GradComposite grad;
        boolean postojao = false;
        if (postojiGradUPodrucju(odvoji[0].trim())) {
          grad = dohvatiGradPodrucja(odvoji[0].trim());
          postojao = true;
        } else {
          grad = new GradComposite(Integer.parseInt(odvoji[0].trim()));
        }
        UlicaLeaf ul = new UlicaLeaf(Integer.parseInt(odvoji[1].trim()));
        if (!ul.provjeriPostojanje(ul.dohvatiId())) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Ulica ne postoji! " + stupac);
        } else if (!grad.provjeriPostojanje(ul.dohvatiId())) {
          System.out.println("Pogreška broj: " + Klijent.brojPogresaka++ + " U retku: " + rbroj
              + " Ulica " + grad.dohvatiId() + "ne postoji u gradu! " + grad.dohvatiId());
        } else {
          grad.dodaj(ul);
          if (!postojao) {
            podrucje.dodaj(grad);
          }
        }
      }
    }
  }

  private GradComposite dohvatiGradPodrucja(String id) {
    for (GradComposite g : podrucje.dohvatiDjecu()) {
      if (g.dohvatiId().equals(Integer.parseInt(id))) {
        return g;
      }
    }
    return null;
  }

  private boolean postojiGradUPodrucju(String id) {
    for (GradComposite g : podrucje.dohvatiDjecu()) {
      if (g.dohvatiId().equals(Integer.parseInt(id))) {
        return true;
      }
    }
    return false;
  }

  private void dodajSve(List<UlicaLeaf> uliceDodati, GradComposite grad) {
    for (UlicaLeaf u : uliceDodati) {
      grad.dodaj(u);
    }
  }

  private List<UlicaLeaf> generirajListuUlica(List<Integer> trazeneUlice) {
    List<UlicaLeaf> lista = new ArrayList<>();
    for (Integer u : trazeneUlice) {
      UlicaLeaf ul = new UlicaLeaf(u);
      lista.add(ul);
    }
    return lista;
  }

  private List<Integer> dohvatiUliceGrada(String idGrada) {
    List<Mjesto> mjesta = sustav.dohvatiMjesta();
    for (Mjesto mjesto : mjesta) {
      if (mjesto.id().equals(Integer.parseInt(idGrada.trim()))) {
        return mjesto.ulice();
      }
    }
    return null;
  }

  public void postaviRbroj(int rb) {
    this.rbroj = rb;
  }

}

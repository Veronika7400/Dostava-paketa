package visitor;

import java.util.ArrayList;
import java.util.List;
import stateStatusVozila.Status;

public class Vozilo extends VisitorKlijenti {
  private String registracija;
  private String opis;
  private int kapacitetTezine;
  private float kapacitetProstora;
  private int redoslijed;
  private Integer prosjecnaBrzina;
  private List<Integer> podrucjaPoRangu = new ArrayList<>();;
  private Status status;
  private List<Voznja> listaVoznji = new ArrayList<>();
  private float pouzece;


  public Vozilo(String registracija, String opis, int kapacitetTezine, float kapacitetProstora,
      int redoslijed, Integer prosjecnaBrzina, List<Integer> podrucjaPoRangu) {
    this.registracija = registracija;
    this.opis = opis;
    this.kapacitetTezine = kapacitetTezine;
    this.kapacitetProstora = kapacitetProstora;
    this.redoslijed = redoslijed;
    this.prosjecnaBrzina = prosjecnaBrzina;
    this.podrucjaPoRangu = podrucjaPoRangu;
    this.status = null;
    this.pouzece = 0;
  }

  public Voznja dohvatiZadnjuVoznju() {
    if (!listaVoznji.isEmpty()) {
      int indeksZadnjeVoznje = listaVoznji.size() - 1;
      return listaVoznji.get(indeksZadnjeVoznje);
    } else {
      return null;
    }
  }

  public void dodajPouzece(float f) {
    pouzece += f;
  }

  public float dohvatiPouzece() {
    return pouzece;
  }


  public void dodajVoznju(Voznja voznja) {
    listaVoznji.add(voznja);
  }

  public List<Voznja> dohvatiSveVoznje() {
    return listaVoznji;
  }

  public void setListaVoznji(List<Voznja> lista) {
    listaVoznji.clear();
    listaVoznji.addAll(lista);
  }

  public Voznja dohvatiVoznju(Integer i) {
    if (listaVoznji.size() == 0 || listaVoznji.size() < i) {
      return null;
    }
    return listaVoznji.get(i - 1);
  }

  public String getRegistracija() {
    return registracija;
  }

  public String getOpis() {
    return opis;
  }

  public int getKapacitetTezine() {
    return kapacitetTezine;
  }

  public float getKapacitetProstora() {
    return kapacitetProstora;
  }

  public int getRedoslijed() {
    return redoslijed;
  }

  public Integer getProsjecnaBrzina() {
    return prosjecnaBrzina;
  }

  public List<Integer> getPodrucjaPoRangu() {
    return podrucjaPoRangu;
  }

  public Integer getPodrucjePoRangu(Integer id) {
    for (Integer p : podrucjaPoRangu) {
      if (p.equals(id)) {
        return p;
      }
    }
    return null;
  }

  public Integer getRangPodrucja(Integer id) {
    for (int i = 0; i < podrucjaPoRangu.size(); i++) {
      if (podrucjaPoRangu.get(i).equals(id)) {
        return i + 1;
      }
    }
    return null;
  }

  public Status getStatus() {
    return status;
  }

  public void setRegistracija(String registracija) {
    this.registracija = registracija;
  }

  public void setOpis(String opis) {
    this.opis = opis;
  }

  public void setKapacitetTezine(int kapacitetTezine) {
    this.kapacitetTezine = kapacitetTezine;
  }

  public void setKapacitetProstora(float kapacitetProstora) {
    this.kapacitetProstora = kapacitetProstora;
  }

  public void setRedoslijed(int redoslijed) {
    this.redoslijed = redoslijed;
  }

  public void setProsjecnaBrzina(Integer prosjecnaBrzina) {
    this.prosjecnaBrzina = prosjecnaBrzina;
  }

  public void setPodrucnaPoRangu(List<Integer> podrucnaPoRangu) {
    this.podrucjaPoRangu.addAll(podrucjaPoRangu);
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  @Override
  public void accept(Visitor visitor) {
    for (Voznja v : listaVoznji) {
      v.accept(visitor);
    }
    visitor.visit(this);
  }

}

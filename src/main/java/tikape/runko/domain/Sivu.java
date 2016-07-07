package tikape.runko.domain;

import java.util.*;

public class Sivu {

    private int nro;
    private List<Sivullinen> lista;

    public Sivu(List<Sivullinen> lista) {
        this.nro = 1;
        this.lista = lista;
    }

    public List<Sivullinen> getLista() {
        return lista;
    }

    public int getNro() {
        return nro;
    }

    public void setNro(int id) {
        this.nro = id;
    }

    public void setLista(List<Sivullinen> lista) {
        this.lista = lista;
    }
    
    public String getVikaPvm() {
        Sivullinen vika = this.lista.get(this.lista.size()-1);
        return vika.getPvm();
    }

}

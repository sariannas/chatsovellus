package tikape.runko.domain;
import java.util.*;

public class Sivu<T> {
    private int nro;
    private List<T> lista;

    public Sivu(List<T> lista) {
        this.nro = 1;
        this.lista = lista;
    }

    public int getNro() {
        return nro;
    }

    public List<T> getLista() {
        return lista;
    }

    public void setNro(int id) {
        this.nro = id;
    }

}

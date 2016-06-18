package tikape.runko.domain;

import java.util.*;
public class Viesti {
    private String kirjoittaja;
    private String sisalto;
    private Date pvm;
    private int id; // tätä ei käytetä oikeastaan mihinkään
    private Avaus avaus;

    public Viesti(String kirjoittaja, String sisalto) {
        this.kirjoittaja = kirjoittaja;
        this.sisalto = sisalto;
        this.pvm = new Date();
        this.id = 1;
    }

    public int getId() {
        return id;
    }

    public String getKirjoittaja() {
        return kirjoittaja;
    }

    public Date getPvm() {
        return pvm;
    }

    public String getSisalto() {
        return sisalto;
    }

    public Avaus getAvaus() {
        return avaus;
    }
    
    

    public void setPvm(Date pvm) {
        this.pvm = pvm;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAvaus(Avaus avaus) {
        this.avaus = avaus;
    }
    
    
    
    
    
    
    
}

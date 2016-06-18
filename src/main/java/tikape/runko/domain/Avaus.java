package tikape.runko.domain;

import java.util.*;
public class Avaus {
    private int id;
    private String kirjoittaja;
    private String otsikko;
    private String sisalto;
    private Date pvm;
    private List<Viesti> viestit;
    private Alue alue;

    public Avaus(int id, String kirjoittaja, String otsikko, String sisalto) {
        this.id = 1;        
        this.kirjoittaja = kirjoittaja;
        this.otsikko = otsikko;
        this.sisalto = sisalto;
        
        this.pvm = new Date();
        this.viestit = new ArrayList();
    }

    public int getId() {
        return id;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public String getKirjoittaja() {
        return kirjoittaja;
    }

    public String getSisalto() {
        return sisalto;
    }

    public Date getPvm() {
        return pvm;
    }

    public List<Viesti> getViestit() {
        return viestit;
    }

    public Alue getAlue() {
        return alue;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setViestit(List<Viesti> viestit) {
        this.viestit = viestit;
    }

    public void setAlue(Alue alue) {
        this.alue = alue;
    }

    public void lisaaViesti(Viesti viesti) {
        this.viestit.add(viesti);
        viesti.setAvaus(this);
    }
    
    public Date viimeisin() {
        //Jos viestejä ei ole, palautetaan avauksen pvm.
        //Jos viestejä on, palautetaan viimeisenä lisätyn päivämäärä.
        if(this.viestit.isEmpty()) {
            return this.pvm;
        }
        
        Viesti vika = this.viestit.get(this.viestit.size()-1);
        return vika.getPvm();
    }
    
    
    
}

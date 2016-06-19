package tikape.runko.domain;

import java.util.*;

public class Alue {
    private int id;
    private String otsikko;
    private Date pvm;
    private List<Avaus> avaukset;

    public Alue(int id, String otsikko) {
        this.id = id;
        this.otsikko = otsikko;
        this.pvm = new Date();
        this.avaukset = new ArrayList();
    }

    public List<Avaus> getAvaukset() {
        return avaukset;
    }

    public int getId() {
        return id;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public Date getPvm() {
        return pvm;
    }
    
    public void lisaaAvaus(Avaus a) {
        this.avaukset.add(a);
        a.setAlue(this);
    }
    
   public Date getViimeisin() {
       if(this.avaukset.isEmpty()) {
           return this.pvm;
       }
       
       Avaus a = this.avaukset.get(this.avaukset.size()-1);
       return a.getViimeisin();
   }
    
    
}

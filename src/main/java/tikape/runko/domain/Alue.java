package tikape.runko.domain;

import java.util.*;

public class Alue {
    private int id;
    private String otsikko;
    private String pvm;
    private List<Avaus> avaukset;

    public Alue(int id, String otsikko) {
        this.id = id;
        this.otsikko = otsikko;
        Date nyt = new Date();
        this.pvm = nyt.toString();
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

    public String getPvm() {
        return pvm;
    }
    
   public String getViimeisin() {
       if(this.avaukset.isEmpty()) {
           return this.pvm;
       }
       
       Avaus a = this.avaukset.get(this.avaukset.size()-1);
       return a.getViimeisin();
   }
      
   public int getKoko() {
      int i = 0;
      for(Avaus a : this.avaukset) {
          i+=a.getKoko();
      }
      
      return i;
   }

    public void setAvaukset(List<Avaus> avaukset) {
        this.avaukset = avaukset;
    }
    
}

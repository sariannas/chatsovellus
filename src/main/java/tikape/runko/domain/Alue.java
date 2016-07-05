package tikape.runko.domain;

import java.util.*;

public class Alue {
    private int id;
    private String otsikko;
    private String pvm;
    private List<Sivu> sivut;

    public Alue(int id, String otsikko) {
        this.id = id;
        this.otsikko = otsikko;
        Date nyt = new Date();
        this.pvm = nyt.toString();
        this.sivut = new ArrayList();
    }

    public List<Sivu> getSivut() {
        return sivut;
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
       return this.pvm;
   }
      
   public int getKoko() {
      int i = 0;
      
      return i;
   }

    public void setSivut(List<Avaus> avaukset) {
        List<Avaus> kymmenen = new ArrayList();
        for (Avaus a : avaukset) {
            if (kymmenen.size() == 10) {
                this.sivut.add(new Sivu(kymmenen));
                kymmenen = new ArrayList();
            }
            
            kymmenen.add(a);
        }
        this.sivut.add(new Sivu(kymmenen));
        
        for(int i = 0;i<this.sivut.size();i++) {
            this.sivut.get(i).setNro(i+1);
        }
    }
    
}

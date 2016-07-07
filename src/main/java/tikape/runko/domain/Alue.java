package tikape.runko.domain;

import java.util.*;

public class Alue {

    private int id;
    private String otsikko;
    private String pvm;
    private List<Sivu> sivut;
    private int koko;

    public Alue(int id, String otsikko) {
        this.id = id;
        this.otsikko = otsikko;
        Date nyt = new Date();
        this.pvm = nyt.toString();
        this.sivut = new ArrayList();
        this.koko = 0;
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
        //Jos viestejä ei ole, palautetaan avauksen pvm.
        //Jos viestejä on, palautetaan viimeisenä lisätyn päivämäärä.
        if (this.sivut.isEmpty()) {
            return this.pvm;
        }

        Sivu vikaSivu = this.sivut.get(this.sivut.size() - 1);
        return vikaSivu.getVikaPvm();
    }

    public void setKoko(int koko) {
        this.koko = koko;
    }

    public int getKoko() {
        return this.koko;
    }

    public void setSivut(List<Avaus> avaukset) {
        List<Sivullinen> kymmenen = new ArrayList();
        for (Avaus a : avaukset) {
            if (kymmenen.size() == 10) {
                this.sivut.add(new Sivu(kymmenen));
                kymmenen = new ArrayList();
            }

            kymmenen.add(a);
        }
        this.sivut.add(new Sivu(kymmenen));

        for (int i = 0; i < this.sivut.size(); i++) {
            this.sivut.get(i).setNro(i + 1);
        }
    }

}

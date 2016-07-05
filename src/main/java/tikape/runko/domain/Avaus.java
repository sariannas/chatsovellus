package tikape.runko.domain;

import java.util.*;

public class Avaus {

    private int id;
    private String kirjoittaja;
    private String otsikko;
    private String sisalto;
    private String pvm;
    private List<Sivu> sivut;
    private Alue alue;
    private int koko;

    public Avaus(int id, String otsikko, String kirjoittaja, String sisalto, String pvm) {
        this.id = id;
        this.kirjoittaja = kirjoittaja;
        this.otsikko = otsikko;
        this.sisalto = sisalto;

        this.pvm = pvm;
        this.sivut = new ArrayList();
        this.koko = 0;
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

    public String getPvm() {
        return pvm;
    }

    public Alue getAlue() {
        return alue;
    }

    public int getAlueId() {
        return this.alue.getId();
    }

    public int getKoko() {
        return koko;
    }

    public void setKoko(int koko) {
        this.koko = koko;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAlue(Alue alue) {
        this.alue = alue;
    }

    public String getViimeisin() {
//        //Jos viestejä ei ole, palautetaan avauksen pvm.
//        //Jos viestejä on, palautetaan viimeisenä lisätyn päivämäärä.
//        if (this.viestit.isEmpty()) {
//            return this.pvm;
//        }
//
//        Viesti vika = this.viestit.get(this.viestit.size() - 1);
//        return vika.getPvm();

        return this.pvm;
    }

    public void setSivut(List<Viesti> viestit) {
        List<Viesti> kymmenen = new ArrayList();
        for (Viesti v : viestit) {
            if (kymmenen.size() == 10) {
                this.sivut.add(new Sivu(kymmenen));
                kymmenen = new ArrayList();
            }

            kymmenen.add(v);
        }
        this.sivut.add(new Sivu(kymmenen));

        for (int i = 0; i < this.sivut.size(); i++) {
            this.sivut.get(i).setNro(i + 1);
        }
    }

    public List<Sivu> getSivut() {
        return this.sivut;
    }

}

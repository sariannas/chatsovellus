package tikape.runko.domain;
import java.util.*;

public class Sivu {
    private int id;
    private Avaus avaus;
    private List<Viesti> viestit;

    public Sivu(Avaus avaus, List<Viesti> viestit) {
        this.id = 1;
        this.avaus = avaus;
        this.viestit = viestit;
    }

    public int getId() {
        return id;
    }

    public int getAvausId() {
        return this.avaus.getId();
    }
    
    public int getAlueId() {
        return this.avaus.getAlueId();
    }

    public List<Viesti> getViestit() {
        return viestit;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    
}

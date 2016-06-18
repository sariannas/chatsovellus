package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.OpiskelijaDao;
import tikape.runko.domain.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:opiskelijat.db");
        database.init();

        OpiskelijaDao opiskelijaDao = new OpiskelijaDao(database);
        
        Alue ekaAlue = new Alue(1, "Eka alue");
        Alue tokaAlue = new Alue(2, "Toka alue");
        
        Avaus ekanEka = new Avaus(1, "Joku", "Otsikko", "Sisältö");
        Avaus ekanToka = new Avaus(1, "Joku muu", "Eri otsikko", "Fiksu sisältö");
        Avaus tokanEka = new Avaus(1, "???", "Otsikko", "Blaaaaaa");
        ekaAlue.lisaaAvaus(ekanEka);
        ekaAlue.lisaaAvaus(ekanToka);
        tokaAlue.lisaaAvaus(tokanEka);
        
        Viesti eka = new Viesti("Vastaaja","Vastaus");
        ekanEka.lisaaViesti(eka);
        Viesti toka = new Viesti("Kommentoija","Solvaava kommentti");
        tokanEka.lisaaViesti(toka);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/opiskelijat", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelijat", opiskelijaDao.findAll());

            return new ModelAndView(map, "opiskelijat");
        }, new ThymeleafTemplateEngine());
        
        get("/ketju", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("avaus", ekanEka);

            return new ModelAndView(map, "avaus");
        }, new ThymeleafTemplateEngine());

        get("/opiskelijat/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "opiskelija");
        }, new ThymeleafTemplateEngine());
    }
}

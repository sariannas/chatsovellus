package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.OpiskelijaDao;
import tikape.runko.domain.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:opiskelijat.db");
        database.init();

        OpiskelijaDao opiskelijaDao = new OpiskelijaDao(database);
        
        //Testialueita ja viestejä, ilman tietokantaa
        Alue ekaAlue = new Alue(1, "Eka alue");
        Alue tokaAlue = new Alue(2, "Toka alue");
        List<Alue> alueet = new ArrayList();
        alueet.add(ekaAlue);
        alueet.add(tokaAlue);
        
        Avaus ekanEka = new Avaus(1, "Joku", "Otsikko", "Sisältö");
        Avaus ekanToka = new Avaus(2, "Joku muu", "Eri otsikko", "Fiksu sisältö");
        Avaus tokanEka = new Avaus(3, "???", "Otsikko", "Blaaaaaa");
        ekaAlue.lisaaAvaus(ekanEka);
        ekaAlue.lisaaAvaus(ekanToka);
        tokaAlue.lisaaAvaus(tokanEka);
        
        Viesti eka = new Viesti("Vastaaja","Vastaus");
        ekanEka.lisaaViesti(eka);
        Viesti toka = new Viesti("Kommentoija","Solvaava kommentti");
        tokanEka.lisaaViesti(toka);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alueet", alueet);

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/opiskelijat", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelijat", opiskelijaDao.findAll());

            return new ModelAndView(map, "opiskelijat");
        }, new ThymeleafTemplateEngine());
        
        get("/alue", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alue", ekaAlue);

            return new ModelAndView(map, "alue");
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

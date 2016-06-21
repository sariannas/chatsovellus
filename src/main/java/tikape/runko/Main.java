package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.*;
import tikape.runko.domain.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        String jdbcOsoite = "jdbc:sqlite:foorumi.db";
//        jos heroku antaa käyttöömme tietokantaosoitteen, otetaan se käyttöön

        if (System.getenv("DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("DATABASE_URL");
        }

        Database database = new Database(jdbcOsoite);
        database.init();
        
        AlueDao alueDao = new AlueDao(database);
        AvausDao avausDao = new AvausDao(database,alueDao);

        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }

        // etusivu
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Alue> alueet = alueDao.findAll();
            map.put("alueet", alueet);

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        // alueen lisäys
        post("/", (req, res) -> {
            String otsikko = req.queryParams("otsikko");
            if (otsikko.length() > 100) {
                return "Otsikko on liian pitkä.";
            }
            alueDao.uusi(otsikko);
            return "Alue lisätty.";
        });

        // alue
        get("/alue/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Alue a = alueDao.findOne(Integer.parseInt(req.params("id")));
            List<Avaus> kaikkiAvaukset = avausDao.findAll();
            a.haeAvaukset(kaikkiAvaukset);
            map.put("alue", a);

            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());

        // avauksen lisäys
        post("/alue/:id", (req, res) -> {
            String otsikko = req.queryParams("otsikko");
            String kirjoittaja = req.queryParams("kirjoittaja");
            String sisalto = req.queryParams("sisalto");
            int alueId = Integer.parseInt(req.params("id"));
            if (kirjoittaja.length() > 50 || otsikko.length() > 150 || sisalto.length() > 5000) {
                return "Otsikko, viesti tai nimimerkki on liian pitkä.";
            }
            
            avausDao.uusi(otsikko,kirjoittaja,sisalto,alueId);
            return "Keskustelunavaus lisätty.";
        });

        // avaus + viestiketju
        get("/ketju/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            //map.put("avaus", ekanEka);

            return new ModelAndView(map, "avaus");
        }, new ThymeleafTemplateEngine());

//        // pohjassa valmiina olleet sivut
//        get("/opiskelijat", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelijat", opiskelijaDao.findAll());
//
//            return new ModelAndView(map, "opiskelijat");
//        }, new ThymeleafTemplateEngine());
//
//        get("/opiskelijat/:id", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));
//
//            return new ModelAndView(map, "opiskelija");
//        }, new ThymeleafTemplateEngine());
    }
}

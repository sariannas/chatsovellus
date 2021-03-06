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
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }

        String jdbcOsoite = "jdbc:sqlite:foorumi.db";
//        jos heroku antaa käyttöömme tietokantaosoitteen, otetaan se käyttöön

        if (System.getenv("DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("DATABASE_URL");
        }

        Database database = new Database(jdbcOsoite);

        AlueDao alueDao = new AlueDao(database);
        AvausDao avausDao = new AvausDao(database, alueDao);
        ViestiDao viestiDao = new ViestiDao(database, avausDao);

        // testiviestien lisäys
//        int i = 1;
//        while(i<11) {
//            String v = "Viesti numero " + i;
//            viestiDao.uusi("nimimerkki", v, 1);
//            i++;
//        }

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
            res.redirect("/");
            return "Alue lisätty.";
        });

        // alue
        get("/alue/:id/sivu/:nro", (req, res) -> {
            HashMap map = new HashMap<>();
            Alue a = alueDao.findOne(Integer.parseInt(req.params("id")));
            List<Avaus> avaukset = avausDao.findAlueella(a);
            Collections.sort(avaukset);
            Collections.reverse(avaukset);
            a.setSivut(avaukset);
            int sivunro = Integer.parseInt(req.params("nro"));
            
            map.put("sivu", a.getSivut().get(sivunro-1));
            map.put("alue", a);

            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());

        // avauksen lisäys
        post("/alue/:id/sivu/:nro", (req, res) -> {
            String otsikko = req.queryParams("otsikko");
            String kirjoittaja = req.queryParams("kirjoittaja");
            String sisalto = req.queryParams("sisalto");
            int alueId = Integer.parseInt(req.params("id"));
            if (kirjoittaja.length() > 50 || otsikko.length() > 150 || sisalto.length() > 5000) {
                return "Otsikko, viesti tai nimimerkki on liian pitkä.";
            }

            avausDao.uusi(otsikko, kirjoittaja, sisalto, alueId);
            res.redirect("/alue/" + req.params("id") + "/sivu/" + req.params("nro"));
            return "Viesti lisätty.";
        });

//        // avaus + viestiketju ilman sivujakoa
//        get("/alue/:alueId/:id", (req, res) -> {
//            HashMap map = new HashMap<>();
//            Avaus a = avausDao.findOne(Integer.parseInt(req.params("id")));
//
//            a.setSivut(viestiDao.findAvauksella(a));
//            map.put("avaus", a);
//
//            return new ModelAndView(map, "avaus");
//        }, new ThymeleafTemplateEngine());

        // jako sivuihin
        get("alue/:alueId/:id/sivu/:nro", (req, res) -> {
            HashMap map = new HashMap<>();
            Avaus a = avausDao.findOne(Integer.parseInt(req.params("id")));
            a.setSivut(viestiDao.findAvauksella(a));
            int sivunro = Integer.parseInt(req.params("nro"));
            
            map.put("sivu", a.getSivut().get(sivunro-1));
            map.put("avaus",a);

            return new ModelAndView(map, "sivu");
        }, new ThymeleafTemplateEngine());

        // viestin lisäys
        post("/alue/:alueId/:id/sivu/:nro", (req, res) -> {
            String kirjoittaja = req.queryParams("kirjoittaja");
            String sisalto = req.queryParams("sisalto");
            int avausId = Integer.parseInt(req.params("id"));
            if (kirjoittaja.length() > 50 || sisalto.length() > 5000) {
                return "Otsikko, viesti tai nimimerkki on liian pitkä.";
            }

            viestiDao.uusi(kirjoittaja, sisalto, avausId);
            res.redirect("/alue/" + req.params("alueId") + "/" + req.params("id") + "/sivu/" + req.params("nro"));
            return "Vastaus lisätty.";
        });
    }
}

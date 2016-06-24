package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            List<String> lauseet = null;
            if (this.databaseAddress.contains("postgres")) {
                lauseet = postgreLauseet();
            } else {
                lauseet = sqliteLauseet();
            }
            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
//        lista.add("CREATE TABLE Opiskelija (id integer PRIMARY KEY, nimi varchar(255));");
//        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Platon');");
//        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Aristoteles');");
//        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Homeros');");
        return lista;
    }

    private List<String> postgreLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        // heroku käyttää SERIAL-avainsanaa uuden tunnuksen automaattiseen luomiseen
        lista.add("CREATE TABLE Alue(id integer SERIAL PRIMARY KEY,otsikko varchar(100));");
        lista.add("CREATE TABLE Avaus(id integer SERIAL PRIMARY KEY, pvm datetime,otsikko varchar(100),kirjoittaja varchar(100),sisalto TEXT,alue integer,FOREIGN KEY(alue) REFERENCES Alue(id));");
        lista.add("CREATE TABLE Viesti(id integer serial PRIMARY KEY,pvm datetime,kirjoittaja varchar(100),sisalto TEXT,avaus integer,FOREIGN KEY(avaus) REFERENCES Avaus(id));");
        lista.add("INSERT INTO Alue(otsikko) VALUES (“Otsikko 1”);");
        lista.add("INSERT INTO Avaus(pvm, otsikko, kirjoittaja, sisalto, alue) VALUES (datetime(), ”otsikko”, “nimi”, ”viestin sisältö”,1);");
        lista.add("INSERT INTO Viesti(pvm, kirjoittaja, sisalto, avaus) VALUES (datetime(), ”nimi”, ”viestin sisältö”, 1);");
        return lista;
    }
}

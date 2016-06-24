package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import tikape.runko.domain.*;

public class AvausDao implements Dao<Avaus, Integer> {

    private Database database;
    private AlueDao alueDao;

    public AvausDao(Database database, AlueDao alueDao) {
        this.database = database;
        this.alueDao = alueDao;
    }

    @Override
    public Avaus findOne(Integer key) throws SQLException {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Avaus WHERE id = ?;");
            stmt.setObject(1, key);

            ResultSet rs = stmt.executeQuery();
            boolean hasOne = rs.next();
            if (!hasOne) {
                return null;
            }

            Integer id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");
            String kirjoittaja = rs.getString("kirjoittaja");
            String sisalto = rs.getString("sisalto");
            String pvm = rs.getString("pvm");

            Avaus a = new Avaus(id, otsikko, kirjoittaja, sisalto, pvm);

            Integer alueId = rs.getInt("alue");
            Alue alue = alueDao.findOne(alueId);
            a.setAlue(alue);

            rs.close();
            stmt.close();

            return a;
        }
    }

    @Override
    public List<Avaus> findAll() throws SQLException {
        List<Avaus> avaukset;
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Avaus;");
            ResultSet rs = stmt.executeQuery();
            avaukset = new ArrayList<>();

            while (rs.next()) {
                Integer id = rs.getInt("id");
                String otsikko = rs.getString("otsikko");
                String kirjoittaja = rs.getString("kirjoittaja");
                String sisalto = rs.getString("sisalto");
                String pvm = rs.getString("pvm");

                Avaus a = new Avaus(id, otsikko, kirjoittaja, sisalto, pvm);
                avaukset.add(a);

                Integer alueId = rs.getInt("alue");
                Alue alue = alueDao.findOne(alueId);
                a.setAlue(alue);
            }

            rs.close();
            stmt.close();
        }
        return avaukset;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void uusi(String otsikko, String kirjoittaja, String sisalto, int alueId) throws SQLException {
        try (Connection connection = this.database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Avaus(pvm, otsikko, kirjoittaja, sisalto, alue) VALUES (current_timestamp, ?, ?, ?,?);");
            stmt.setString(1, otsikko);
            stmt.setString(2, kirjoittaja);
            stmt.setString(3, sisalto);
            stmt.setInt(4, alueId);
            stmt.execute();
            stmt.close();
        }
    }

    public List<Avaus> findAlueella(Alue alue) throws SQLException {
        List<Avaus> avaukset;
        try (Connection connection = database.getConnection()) {
            int alueId = alue.getId();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Avaus WHERE alue = ?;");
            stmt.setInt(1, alueId);
            ResultSet rs = stmt.executeQuery();
            avaukset = new ArrayList<>();

            while (rs.next()) {
                Integer id = rs.getInt("id");
                String otsikko = rs.getString("otsikko");
                String kirjoittaja = rs.getString("kirjoittaja");
                String sisalto = rs.getString("sisalto");
                String pvm = rs.getString("pvm");

                Avaus a = new Avaus(id, otsikko, kirjoittaja, sisalto, pvm);
                avaukset.add(a);

                a.setAlue(alue);
            }

            rs.close();
            stmt.close();
        }
        return avaukset;
    }

}

package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import tikape.runko.domain.*;

public class ViestiDao implements Dao<Viesti, Integer> {

    private Database database;
    private AvausDao avausDao;

    public ViestiDao(Database database, AvausDao avausDao) {
        this.database = database;
        this.avausDao = avausDao;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE id = ?;");
            stmt.setObject(1, key);

            ResultSet rs = stmt.executeQuery();
            boolean hasOne = rs.next();
            if (!hasOne) {
                return null;
            }

            Integer id = rs.getInt("id");
            String kirjoittaja = rs.getString("kirjoittaja");
            String sisalto = rs.getString("sisalto");
            String pvm = rs.getString("pvm");

            Viesti v = new Viesti(id, kirjoittaja, sisalto, pvm);

            Integer avausId = rs.getInt("avaus");
            Avaus avaus = avausDao.findOne(avausId);
            v.setAvaus(avaus);

            rs.close();
            stmt.close();

            return v;
        }
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        List<Viesti> viestit;
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti;");

            ResultSet rs = stmt.executeQuery();
            viestit = new ArrayList();

            while (rs.next()) {
                Integer id = rs.getInt("id");
                String kirjoittaja = rs.getString("kirjoittaja");
                String sisalto = rs.getString("sisalto");
                String pvm = rs.getString("pvm");

                Viesti v = new Viesti(id, kirjoittaja, sisalto, pvm);

                Integer avausId = rs.getInt("avaus");
                Avaus avaus = avausDao.findOne(avausId);
                v.setAvaus(avaus);

                viestit.add(v);
            }

            rs.close();
            stmt.close();
        }
        return viestit;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Viesti> findAvauksella(Avaus a) throws SQLException {
        List<Viesti> viestit;
        try (Connection connection = database.getConnection()) {
            int avausId = a.getId();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE avaus = ?;");
            stmt.setInt(1, avausId);

            ResultSet rs = stmt.executeQuery();
            viestit = new ArrayList();

            while (rs.next()) {
                Integer id = rs.getInt("id");
                String kirjoittaja = rs.getString("kirjoittaja");
                String sisalto = rs.getString("sisalto");
                String pvm = rs.getString("pvm");

                Viesti v = new Viesti(id, kirjoittaja, sisalto, pvm);

                v.setAvaus(a);

                viestit.add(v);
            }

            rs.close();
            stmt.close();
        }
        return viestit;
    }
    
    public void uusi(String kirjoittaja, String sisalto, int avausId) throws SQLException {
        try (Connection connection = this.database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Viesti(pvm, kirjoittaja, sisalto, avaus) VALUES (datetime(), ?, ?,?);");
            stmt.setString(1, kirjoittaja);
            stmt.setString(2, sisalto);
            stmt.setInt(3, avausId);
            stmt.execute();
            stmt.close();
        }
    }

}

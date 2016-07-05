package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.*;

public class AlueDao implements Dao<Alue, Integer> {

    private Database database;

    public AlueDao(Database database) {
        this.database = database;
    }

    @Override
    public Alue findOne(Integer key) throws SQLException {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE id = ?;");
            stmt.setObject(1, key);

            ResultSet rs = stmt.executeQuery();
            boolean hasOne = rs.next();
            if (!hasOne) {
                return null;
            }

            String otsikko = rs.getString("otsikko");
            Integer id = rs.getInt("id");

            Alue a = new Alue(id, otsikko);

            rs.close();
            stmt.close();
            //connection.close();

            return a;
        }
    }

    @Override
    public List<Alue> findAll() throws SQLException {
        List<Alue> alueet = new ArrayList();
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT *, count(*) AS lkm FROM Alue LEFT JOIN Avaus ON (Avaus.alue = Alue.id) LEFT JOIN Viesti ON (Viesti.avaus = Avaus.id) GROUP BY Alue.id;");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Integer luku = rs.getInt("lkm");
                String otsikko = rs.getString("otsikko");
                Integer id = rs.getInt("id");

                Alue a = new Alue(id, otsikko);
                a.setKoko(luku);
                alueet.add(a);

            }

            rs.close();
            stmt.close();
        }
        return alueet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void uusi(String otsikko) throws Exception {
        try (Connection connection = this.database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Alue(otsikko) VALUES (?);");
            stmt.setString(1, otsikko);
            stmt.execute();
            stmt.close();
        }
    }

}

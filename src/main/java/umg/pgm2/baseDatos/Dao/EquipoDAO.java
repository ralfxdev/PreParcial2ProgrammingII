package umg.pgm2.baseDatos.Dao;

import umg.pgm2.baseDatos.Model.Equipo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {
    private Connection connection;

    public EquipoDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Equipo equipo) throws SQLException {
        String sql = "INSERT INTO equipos_champions (nombre, pais, ciudad, estadio, fundacion, entrenador, web_oficial, facebook, twitter, instagram, patrocinador_principal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, equipo.getNombre());
            statement.setString(2, equipo.getPais());
            statement.setString(3, equipo.getCiudad());
            statement.setString(4, equipo.getEstadio());
            statement.setInt(5, equipo.getFundacion());
            statement.setString(6, equipo.getEntrenador());
            statement.setString(7, equipo.getWebOficial());
            statement.setString(8, equipo.getFacebook());
            statement.setString(9, equipo.getTwitter());
            statement.setString(10, equipo.getInstagram());
            statement.setString(11, equipo.getPatrocinadorPrincipal());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    equipo.setIdEquipo(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Equipo findById(int idEquipo) throws SQLException {
        String sql = "SELECT * FROM equipos_champions WHERE id_equipo = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idEquipo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Equipo(
                            resultSet.getInt("id_equipo"),
                            resultSet.getString("nombre"),
                            resultSet.getString("pais"),
                            resultSet.getString("ciudad"),
                            resultSet.getString("estadio"),
                            resultSet.getInt("fundacion"),
                            resultSet.getString("entrenador"),
                            resultSet.getString("web_oficial"),
                            resultSet.getString("facebook"),
                            resultSet.getString("twitter"),
                            resultSet.getString("instagram"),
                            resultSet.getString("patrocinador_principal"),
                            resultSet.getTimestamp("creado_en")
                    );
                }
            }
        }
        return null;
    }

    public List<Equipo> findAll() throws SQLException {
        List<Equipo> equipos = new ArrayList<>();
        String sql = "SELECT * FROM equipos_champions";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                equipos.add(new Equipo(
                        resultSet.getInt("id_equipo"),
                        resultSet.getString("nombre"),
                        resultSet.getString("pais"),
                        resultSet.getString("ciudad"),
                        resultSet.getString("estadio"),
                        resultSet.getInt("fundacion"),
                        resultSet.getString("entrenador"),
                        resultSet.getString("web_oficial"),
                        resultSet.getString("facebook"),
                        resultSet.getString("twitter"),
                        resultSet.getString("instagram"),
                        resultSet.getString("patrocinador_principal"),
                        resultSet.getTimestamp("creado_en")
                ));
            }
        }
        return equipos;
    }

    public void update(Equipo equipo) throws SQLException {
        String sql = "UPDATE equipos_champions SET nombre = ?, pais = ?, ciudad = ?, estadio = ?, fundacion = ?, entrenador = ?, web_oficial = ?, facebook = ?, twitter = ?, instagram = ?, patrocinador_principal = ? WHERE id_equipo = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, equipo.getNombre());
            statement.setString(2, equipo.getPais());
            statement.setString(3, equipo.getCiudad());
            statement.setString(4, equipo.getEstadio());
            statement.setInt(5, equipo.getFundacion());
            statement.setString(6, equipo.getEntrenador());
            statement.setString(7, equipo.getWebOficial());
            statement.setString(8, equipo.getFacebook());
            statement.setString(9, equipo.getTwitter());
            statement.setString(10, equipo.getInstagram());
            statement.setString(11, equipo.getPatrocinadorPrincipal());
            statement.setInt(12, equipo.getIdEquipo());
            statement.executeUpdate();
        }
    }

    public void delete(int idEquipo) throws SQLException {
        String sql = "DELETE FROM equipos_champions WHERE id_equipo = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idEquipo);
            statement.executeUpdate();
        }
    }
}

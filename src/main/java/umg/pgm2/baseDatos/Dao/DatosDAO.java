package umg.pgm2.baseDatos.Dao;

import umg.pgm2.baseDatos.Model.Datos;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatosDAO {
    private Connection connection;

    public DatosDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Datos datos) throws SQLException {
        String sql = "INSERT INTO tb_datos (nombre, apellido, departamento, fecha_nacimiento) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, datos.getNombre());
            statement.setString(2, datos.getApellido());
            statement.setString(3, datos.getDepartamento());
            statement.setDate(4, new java.sql.Date(datos.getFechaNacimiento().getTime()));
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    datos.setCodigo(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Datos findByCodigo(int codigo) throws SQLException {
        String sql = "SELECT * FROM tb_datos WHERE codigo = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, codigo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Datos(
                            resultSet.getInt("codigo"),
                            resultSet.getString("nombre"),
                            resultSet.getString("apellido"),
                            resultSet.getString("departamento"),
                            resultSet.getDate("fecha_nacimiento")
                    );
                }
            }
        }
        return null;
    }

    public List<Datos> findAll() throws SQLException {
        List<Datos> datosList = new ArrayList<>();
        String sql = "SELECT * FROM tb_datos";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                datosList.add(new Datos(
                        resultSet.getInt("codigo"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
                        resultSet.getString("departamento"),
                        resultSet.getDate("fecha_nacimiento")
                ));
            }
        }
        return datosList;
    }

    public void update(Datos datos) throws SQLException {
        String sql = "UPDATE tb_datos SET nombre = ?, apellido = ?, departamento = ?, fecha_nacimiento = ? WHERE codigo = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, datos.getNombre());
            statement.setString(2, datos.getApellido());
            statement.setString(3, datos.getDepartamento());
            statement.setDate(4, new java.sql.Date(datos.getFechaNacimiento().getTime()));
            statement.setInt(5, datos.getCodigo());
            statement.executeUpdate();
        }
    }

    public void delete(int codigo) throws SQLException {
        String sql = "DELETE FROM tb_datos WHERE codigo = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, codigo);
            statement.executeUpdate();
        }
    }
}
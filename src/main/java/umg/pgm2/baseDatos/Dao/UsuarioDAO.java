package umg.pgm2.baseDatos.Dao;

import umg.pgm2.baseDatos.Model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private final Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    public void crearUsuario(Usuario usuario) throws SQLException {
        // Verificar si el correo ya existe
        String checkEmailSql = "SELECT COUNT(*) FROM tb_usuarios WHERE correo = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkEmailSql)) {
            checkStmt.setString(1, usuario.getCorreo());
            ResultSet resultSet = checkStmt.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                throw new SQLException("El correo electrónico ya está registrado.");
            }
        }

        // Si el correo no existe, proceder a insertar el nuevo usuario
        String insertSql = "INSERT INTO tb_usuarios (carne, nombre, correo, seccion, telegramid, activo) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getCarne());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getCorreo());
            stmt.setString(4, usuario.getSeccion());
            stmt.setLong(5, usuario.getTelegramid());
            stmt.setString(6, usuario.getActivo());
            stmt.executeUpdate();
        }
    }

    public Usuario obtenerUsuario(int idusuario) throws SQLException {
        String sql = "SELECT * FROM tb_usuarios WHERE idusuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idusuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("idusuario"),
                        rs.getString("carne"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("seccion"),
                        rs.getLong("telegramid"),
                        rs.getString("activo")
                );
            }
        }
        return null;
    }

    public void actualizarUsuario(Usuario usuario) throws SQLException {
        // Verificar si el correo ya existe y no pertenece al mismo usuario
        String checkEmailSql = "SELECT COUNT(*) FROM tb_usuarios WHERE correo = ? AND idusuario != ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkEmailSql)) {
            checkStmt.setString(1, usuario.getCorreo());
            checkStmt.setInt(2, usuario.getIdusuario());
            ResultSet resultSet = checkStmt.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                throw new SQLException("El correo electrónico ya está registrado por otro usuario.");
            }
        }

        // Si el correo no existe o pertenece al mismo usuario, proceder a actualizar el usuario
        String sql = "UPDATE tb_usuarios SET carne = ?, nombre = ?, correo = ?, seccion = ?, telegramid = ?, activo = ? WHERE idusuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getCarne());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getCorreo());
            stmt.setString(4, usuario.getSeccion());
            stmt.setLong(5, usuario.getTelegramid());
            stmt.setString(6, usuario.getActivo());
            stmt.setInt(7, usuario.getIdusuario());
            stmt.executeUpdate();
        }
    }

    public void eliminarUsuario(int idusuario) throws SQLException {
        String sql = "DELETE FROM tb_usuarios WHERE idusuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idusuario);
            stmt.executeUpdate();
        }
    }

    public List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM tb_usuarios";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt("idusuario"),
                        rs.getString("carne"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("seccion"),
                        rs.getLong("telegramid"),
                        rs.getString("activo")
                ));
            }
        }
        return usuarios;
    }
}

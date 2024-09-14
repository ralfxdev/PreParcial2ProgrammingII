package umg.pgm2.baseDatos.Services;

import umg.pgm2.baseDatos.Dao.UsuarioDAO;
import umg.pgm2.baseDatos.Model.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UsuarioService {
    private final UsuarioDAO usuarioDAO;

    public UsuarioService(Connection connection) {
        this.usuarioDAO = new UsuarioDAO(connection);
    }

    public void crearUsuario(Usuario usuario) throws SQLException {
        usuarioDAO.crearUsuario(usuario);
    }

    public Usuario obtenerUsuario(int idusuario) throws SQLException {
        return usuarioDAO.obtenerUsuario(idusuario);
    }

    public void actualizarUsuario(Usuario usuario) throws SQLException {
        usuarioDAO.actualizarUsuario(usuario);
    }

    public void eliminarUsuario(int idusuario) throws SQLException {
        usuarioDAO.eliminarUsuario(idusuario);
    }

    public List<Usuario> listarUsuarios() throws SQLException {
        return usuarioDAO.listarUsuarios();
    }
}

package umg.pgm2.baseDatos.Services;

import umg.pgm2.baseDatos.Dao.DatosDAO;
import umg.pgm2.baseDatos.Model.Datos;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DatosService {
    private DatosDAO datosDAO;

    public DatosService(Connection connection) {
        this.datosDAO = new DatosDAO(connection);
    }

    public void crearDatos(Datos datos) throws SQLException {
        datosDAO.insert(datos);
    }

    public Datos obtenerDatos(int codigo) throws SQLException {
        return datosDAO.findByCodigo(codigo);
    }

    public List<Datos> obtenerTodosLosDatos() throws SQLException {
        return datosDAO.findAll();
    }

    public void actualizarDatos(Datos datos) throws SQLException {
        datosDAO.update(datos);
    }

    public void eliminarDatos(int codigo) throws SQLException {
        datosDAO.delete(codigo);
    }
}
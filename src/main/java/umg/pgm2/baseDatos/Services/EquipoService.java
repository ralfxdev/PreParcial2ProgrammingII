package umg.pgm2.baseDatos.Services;

import umg.pgm2.baseDatos.Dao.EquipoDAO;
import umg.pgm2.baseDatos.Model.Equipo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EquipoService {
    private EquipoDAO equipoDAO;

    public EquipoService(Connection connection) {
        this.equipoDAO = new EquipoDAO(connection);
    }

    public void crearEquipo(Equipo equipo) throws SQLException {
        equipoDAO.insert(equipo);
    }

    public Equipo obtenerEquipo(int idEquipo) throws SQLException {
        return equipoDAO.findById(idEquipo);
    }

    public List<Equipo> obtenerTodosLosEquipos() throws SQLException {
        return equipoDAO.findAll();
    }

    public void actualizarEquipo(Equipo equipo) throws SQLException {
        equipoDAO.update(equipo);
    }

    public void eliminarEquipo(int idEquipo) throws SQLException {
        equipoDAO.delete(idEquipo);
    }
}

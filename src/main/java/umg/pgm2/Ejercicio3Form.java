package umg.pgm2;

import umg.pgm2.baseDatos.Conexion.DatabaseConnection;
import umg.pgm2.baseDatos.Model.Equipo;
import umg.pgm2.baseDatos.Services.EquipoService;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class Ejercicio3Form extends JFrame {
    private JPanel Ejercicio3Form;
    private JLabel lblTitulo;
    private JLabel lblId;
    private JTextField textFieldId;
    private JLabel lblNombre;
    private JTextField textFieldNombre;
    private JLabel lblPais;
    private JTextField textFieldPais;
    private JLabel lblCiudad;
    private JTextField textFieldCiudad;
    private JLabel lblEstadio;
    private JTextField textFieldEstadio;
    private JLabel lblFundacion;
    private JTextField textFieldFundacion;
    private JLabel lblEntrenador;
    private JTextField textFieldEntrenador;
    private JLabel lblWebOficial;
    private JTextField textFieldWebOficial;
    private JLabel lblFacebook;
    private JTextField textFieldFacebook;
    private JLabel lblTwitter;
    private JTextField textFieldTwitter;
    private JLabel lblInstagram;
    private JTextField textFieldInstagram;
    private JLabel lblPatrocinador;
    private JTextField textFieldPatrocinador;
    private JButton buttonGrabar;
    private JButton buttonBuscar;
    private JButton buttonActualizar;
    private JButton buttonEliminar;
    private JButton buttonLimpiar;

    private EquipoService equipoService;

    public Ejercicio3Form() {
        setTitle("Ejercicio 3 - Gestión de Equipos");
        initComponents();
        initDatabaseConnection();
        setContentPane(Ejercicio3Form);
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        Ejercicio3Form = new JPanel();
        Ejercicio3Form.setLayout(new GridLayout(0, 2, 10, 10));

        lblTitulo = new JLabel("Gestión de Equipos");
        lblId = new JLabel("ID:");
        lblNombre = new JLabel("Nombre:");
        lblPais = new JLabel("País:");
        lblCiudad = new JLabel("Ciudad:");
        lblEstadio = new JLabel("Estadio:");
        lblFundacion = new JLabel("Año de Fundación:");
        lblEntrenador = new JLabel("Entrenador:");
        lblWebOficial = new JLabel("Web Oficial:");
        lblFacebook = new JLabel("Facebook:");
        lblTwitter = new JLabel("Twitter:");
        lblInstagram = new JLabel("Instagram:");
        lblPatrocinador = new JLabel("Patrocinador Principal:");

        textFieldId = new JTextField();
        textFieldNombre = new JTextField();
        textFieldPais = new JTextField();
        textFieldCiudad = new JTextField();
        textFieldEstadio = new JTextField();
        textFieldFundacion = new JTextField();
        textFieldEntrenador = new JTextField();
        textFieldWebOficial = new JTextField();
        textFieldFacebook = new JTextField();
        textFieldTwitter = new JTextField();
        textFieldInstagram = new JTextField();
        textFieldPatrocinador = new JTextField();

        buttonGrabar = new JButton("Grabar");
        buttonBuscar = new JButton("Buscar");
        buttonActualizar = new JButton("Actualizar");
        buttonEliminar = new JButton("Eliminar");
        buttonLimpiar = new JButton("Limpiar");

        Ejercicio3Form.add(lblTitulo);
        Ejercicio3Form.add(new JLabel());
        Ejercicio3Form.add(lblId);
        Ejercicio3Form.add(textFieldId);
        Ejercicio3Form.add(lblNombre);
        Ejercicio3Form.add(textFieldNombre);
        Ejercicio3Form.add(lblPais);
        Ejercicio3Form.add(textFieldPais);
        Ejercicio3Form.add(lblCiudad);
        Ejercicio3Form.add(textFieldCiudad);
        Ejercicio3Form.add(lblEstadio);
        Ejercicio3Form.add(textFieldEstadio);
        Ejercicio3Form.add(lblFundacion);
        Ejercicio3Form.add(textFieldFundacion);
        Ejercicio3Form.add(lblEntrenador);
        Ejercicio3Form.add(textFieldEntrenador);
        Ejercicio3Form.add(lblWebOficial);
        Ejercicio3Form.add(textFieldWebOficial);
        Ejercicio3Form.add(lblFacebook);
        Ejercicio3Form.add(textFieldFacebook);
        Ejercicio3Form.add(lblTwitter);
        Ejercicio3Form.add(textFieldTwitter);
        Ejercicio3Form.add(lblInstagram);
        Ejercicio3Form.add(textFieldInstagram);
        Ejercicio3Form.add(lblPatrocinador);
        Ejercicio3Form.add(textFieldPatrocinador);
        Ejercicio3Form.add(buttonGrabar);
        Ejercicio3Form.add(buttonBuscar);
        Ejercicio3Form.add(buttonActualizar);
        Ejercicio3Form.add(buttonEliminar);
        Ejercicio3Form.add(buttonLimpiar);

        buttonGrabar.addActionListener(e -> guardarEquipo());
        buttonBuscar.addActionListener(e -> buscarEquipo());
        buttonActualizar.addActionListener(e -> actualizarEquipo());
        buttonEliminar.addActionListener(e -> eliminarEquipo());
        buttonLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void initDatabaseConnection() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            equipoService = new EquipoService(connection);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    private void guardarEquipo() {
        try {
            Timestamp fechaRegistro = new Timestamp(new Date().getTime());

            Equipo equipo = new Equipo(
                    0, // El ID se generará automáticamente
                    textFieldNombre.getText(),
                    textFieldPais.getText(),
                    textFieldCiudad.getText(),
                    textFieldEstadio.getText(),
                    Integer.parseInt(textFieldFundacion.getText()),
                    textFieldEntrenador.getText(),
                    textFieldWebOficial.getText(),
                    textFieldFacebook.getText(),
                    textFieldTwitter.getText(),
                    textFieldInstagram.getText(),
                    textFieldPatrocinador.getText(),
                    fechaRegistro // Se agrega el Timestamp requerido
            );
            equipoService.crearEquipo(equipo);
            JOptionPane.showMessageDialog(this, "Equipo guardado con éxito. ID: " + equipo.getIdEquipo());
            limpiarCampos();
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el equipo: " + e.getMessage());
        }
    }

    private void buscarEquipo() {
        try {
            int id = Integer.parseInt(textFieldId.getText());
            Equipo equipo = equipoService.obtenerEquipo(id);
            if (equipo != null) {
                mostrarEquipo(equipo);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron equipos con ese ID.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID válido.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al buscar el equipo: " + e.getMessage());
        }
    }

    private void actualizarEquipo() {
        try {
            Timestamp fechaRegistro = new Timestamp(new Date().getTime());

            Equipo equipo = new Equipo(
                    Integer.parseInt(textFieldId.getText()),
                    textFieldNombre.getText(),
                    textFieldPais.getText(),
                    textFieldCiudad.getText(),
                    textFieldEstadio.getText(),
                    Integer.parseInt(textFieldFundacion.getText()),
                    textFieldEntrenador.getText(),
                    textFieldWebOficial.getText(),
                    textFieldFacebook.getText(),
                    textFieldTwitter.getText(),
                    textFieldInstagram.getText(),
                    textFieldPatrocinador.getText(),
                    fechaRegistro // Se agrega el Timestamp requerido
            );
            equipoService.actualizarEquipo(equipo);
            JOptionPane.showMessageDialog(this, "Equipo actualizado con éxito.");
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el equipo: " + e.getMessage());
        }
    }

    private void eliminarEquipo() {
        try {
            int id = Integer.parseInt(textFieldId.getText());
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este equipo?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                equipoService.eliminarEquipo(id);
                JOptionPane.showMessageDialog(this, "Equipo eliminado con éxito.");
                limpiarCampos();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID válido.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar el equipo: " + e.getMessage());
        }
    }

    private void mostrarEquipo(Equipo equipo) {
        textFieldId.setText(String.valueOf(equipo.getIdEquipo()));
        textFieldNombre.setText(equipo.getNombre());
        textFieldPais.setText(equipo.getPais());
        textFieldCiudad.setText(equipo.getCiudad());
        textFieldEstadio.setText(equipo.getEstadio());
        textFieldFundacion.setText(String.valueOf(equipo.getFundacion()));
        textFieldEntrenador.setText(equipo.getEntrenador());
        textFieldWebOficial.setText(equipo.getWebOficial());
        textFieldFacebook.setText(equipo.getFacebook());
        textFieldTwitter.setText(equipo.getTwitter());
        textFieldInstagram.setText(equipo.getInstagram());
        textFieldPatrocinador.setText(equipo.getPatrocinadorPrincipal());
    }

    private void limpiarCampos() {
        textFieldId.setText("");
        textFieldNombre.setText("");
        textFieldPais.setText("");
        textFieldCiudad.setText("");
        textFieldEstadio.setText("");
        textFieldFundacion.setText("");
        textFieldEntrenador.setText("");
        textFieldWebOficial.setText("");
        textFieldFacebook.setText("");
        textFieldTwitter.setText("");
        textFieldInstagram.setText("");
        textFieldPatrocinador.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Ejercicio3Form form = new Ejercicio3Form();
            form.setVisible(true);
        });
    }
}

package umg.pgm2;

import umg.pgm2.baseDatos.Conexion.DatabaseConnection;
import umg.pgm2.baseDatos.Model.Datos;
import umg.pgm2.baseDatos.Services.DatosService;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ejercicio1Form extends JFrame {
    private JPanel Ejercicio1Form;
    private JLabel lblTitulo;
    private JLabel lblCodigo;
    private JTextField textFieldCodigo;
    private JLabel lblNombre;
    private JTextField textFieldNombre;
    private JLabel lblApellido;
    private JTextField textFieldApellido;
    private JLabel lblDepartamento;
    private JTextField textFieldDepartamento;
    private JLabel lblFechaNacimiento;
    private JTextField textFieldFechaNacimiento;
    private JButton buttonGrabar;
    private JButton buttonBuscar;
    private JButton buttonActualizar;
    private JButton buttonEliminar;
    private JButton buttonLimpiar;

    private DatosService datosService;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Ejercicio1Form() {
        setTitle("Ejercicio 1 - Gestión de Datos");
        initComponents();
        initDatabaseConnection();
        setContentPane(Ejercicio1Form);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        Ejercicio1Form = new JPanel();
        Ejercicio1Form.setLayout(new GridLayout(0, 2, 10, 10));

        lblTitulo = new JLabel("Gestión de Datos");
        lblCodigo = new JLabel("Código:");
        lblNombre = new JLabel("Nombre:");
        lblApellido = new JLabel("Apellido:");
        lblDepartamento = new JLabel("Departamento:");
        lblFechaNacimiento = new JLabel("Fecha de Nacimiento (YYYY-MM-DD):");

        textFieldCodigo = new JTextField();
        textFieldNombre = new JTextField();
        textFieldApellido = new JTextField();
        textFieldDepartamento = new JTextField();
        textFieldFechaNacimiento = new JTextField();

        buttonGrabar = new JButton("Grabar");
        buttonBuscar = new JButton("Buscar");
        buttonActualizar = new JButton("Actualizar");
        buttonEliminar = new JButton("Eliminar");
        buttonLimpiar = new JButton("Limpiar");

        Ejercicio1Form.add(lblTitulo);
        Ejercicio1Form.add(new JLabel());
        Ejercicio1Form.add(lblCodigo);
        Ejercicio1Form.add(textFieldCodigo);
        Ejercicio1Form.add(lblNombre);
        Ejercicio1Form.add(textFieldNombre);
        Ejercicio1Form.add(lblApellido);
        Ejercicio1Form.add(textFieldApellido);
        Ejercicio1Form.add(lblDepartamento);
        Ejercicio1Form.add(textFieldDepartamento);
        Ejercicio1Form.add(lblFechaNacimiento);
        Ejercicio1Form.add(textFieldFechaNacimiento);
        Ejercicio1Form.add(buttonGrabar);
        Ejercicio1Form.add(buttonBuscar);
        Ejercicio1Form.add(buttonActualizar);
        Ejercicio1Form.add(buttonEliminar);
        Ejercicio1Form.add(buttonLimpiar);

        buttonGrabar.addActionListener(e -> guardarDatos());
        buttonBuscar.addActionListener(e -> buscarDatos());
        buttonActualizar.addActionListener(e -> actualizarDatos());
        buttonEliminar.addActionListener(e -> eliminarDatos());
        buttonLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void initDatabaseConnection() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            datosService = new DatosService(connection);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    private void guardarDatos() {
        try {
            Datos datos = new Datos(
                    0, // El código se generará automáticamente
                    textFieldNombre.getText(),
                    textFieldApellido.getText(),
                    textFieldDepartamento.getText(),
                    dateFormat.parse(textFieldFechaNacimiento.getText())
            );
            datosService.crearDatos(datos);
            JOptionPane.showMessageDialog(this, "Datos guardados con éxito. Código: " + datos.getCodigo());
            limpiarCampos();
        } catch (SQLException | ParseException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar los datos: " + e.getMessage());
        }
    }

    private void buscarDatos() {
        try {
            int codigo = Integer.parseInt(textFieldCodigo.getText());
            Datos datos = datosService.obtenerDatos(codigo);
            if (datos != null) {
                mostrarDatos(datos);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron datos con ese código.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un código válido.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al buscar los datos: " + e.getMessage());
        }
    }

    private void actualizarDatos() {
        try {
            Datos datos = new Datos(
                    Integer.parseInt(textFieldCodigo.getText()),
                    textFieldNombre.getText(),
                    textFieldApellido.getText(),
                    textFieldDepartamento.getText(),
                    dateFormat.parse(textFieldFechaNacimiento.getText())
            );
            datosService.actualizarDatos(datos);
            JOptionPane.showMessageDialog(this, "Datos actualizados con éxito.");
        } catch (SQLException | ParseException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar los datos: " + e.getMessage());
        }
    }

    private void eliminarDatos() {
        try {
            int codigo = Integer.parseInt(textFieldCodigo.getText());
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar estos datos?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                datosService.eliminarDatos(codigo);
                JOptionPane.showMessageDialog(this, "Datos eliminados con éxito.");
                limpiarCampos();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un código válido.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar los datos: " + e.getMessage());
        }
    }

    private void mostrarDatos(Datos datos) {
        textFieldCodigo.setText(String.valueOf(datos.getCodigo()));
        textFieldNombre.setText(datos.getNombre());
        textFieldApellido.setText(datos.getApellido());
        textFieldDepartamento.setText(datos.getDepartamento());
        textFieldFechaNacimiento.setText(dateFormat.format(datos.getFechaNacimiento()));
    }

    private void limpiarCampos() {
        textFieldCodigo.setText("");
        textFieldNombre.setText("");
        textFieldApellido.setText("");
        textFieldDepartamento.setText("");
        textFieldFechaNacimiento.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Ejercicio1Form form = new Ejercicio1Form();
            form.setVisible(true);
        });
    }
}
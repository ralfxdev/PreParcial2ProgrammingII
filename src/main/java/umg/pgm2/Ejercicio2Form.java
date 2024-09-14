package umg.pgm2;

import umg.pgm2.baseDatos.Conexion.DatabaseConnection;
import umg.pgm2.baseDatos.Model.Usuario;
import umg.pgm2.baseDatos.Services.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Ejercicio2Form extends JFrame {
    private JPanel mainPanel;
    private JTextField textFieldId;
    private JTextField textFieldCarne;
    private JTextField textFieldNombre;
    private JTextField textFieldCorreo;
    private JTextField textFieldSeccion;
    private JTextField textFieldTelegramId;
    private JTextField textFieldActivo;
    private JButton buttonGuardar;
    private JButton buttonBuscar;
    private JButton buttonActualizar;
    private JButton buttonEliminar;
    private JButton buttonLimpiar;

    private UsuarioService usuarioService;

    public Ejercicio2Form() {
        setTitle("Gestión de Usuarios - Ejercicio 2");
        initComponents();
        initDatabaseConnection();
        setContentPane(mainPanel);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2, 10, 10));

        textFieldId = new JTextField();
        textFieldCarne = new JTextField();
        textFieldNombre = new JTextField();
        textFieldCorreo = new JTextField();
        textFieldSeccion = new JTextField();
        textFieldTelegramId = new JTextField();
        textFieldActivo = new JTextField();

        buttonGuardar = new JButton("Guardar");
        buttonBuscar = new JButton("Buscar");
        buttonActualizar = new JButton("Actualizar");
        buttonEliminar = new JButton("Eliminar");
        buttonLimpiar = new JButton("Limpiar");

        mainPanel.add(new JLabel("ID:"));
        mainPanel.add(textFieldId);
        mainPanel.add(new JLabel("Carne:"));
        mainPanel.add(textFieldCarne);
        mainPanel.add(new JLabel("Nombre:"));
        mainPanel.add(textFieldNombre);
        mainPanel.add(new JLabel("Correo:"));
        mainPanel.add(textFieldCorreo);
        mainPanel.add(new JLabel("Sección:"));
        mainPanel.add(textFieldSeccion);
        mainPanel.add(new JLabel("Telegram ID:"));
        mainPanel.add(textFieldTelegramId);
        mainPanel.add(new JLabel("Activo:"));
        mainPanel.add(textFieldActivo);
        mainPanel.add(buttonGuardar);
        mainPanel.add(buttonBuscar);
        mainPanel.add(buttonActualizar);
        mainPanel.add(buttonEliminar);
        mainPanel.add(buttonLimpiar);

        buttonGuardar.addActionListener(e -> guardarUsuario());
        buttonBuscar.addActionListener(e -> buscarUsuario());
        buttonActualizar.addActionListener(e -> actualizarUsuario());
        buttonEliminar.addActionListener(e -> eliminarUsuario());
        buttonLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void initDatabaseConnection() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            usuarioService = new UsuarioService(connection);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    private void guardarUsuario() {
        try {
            Usuario usuario = new Usuario(
                    0, // El ID se generará automáticamente
                    textFieldCarne.getText(),
                    textFieldNombre.getText(),
                    textFieldCorreo.getText(),
                    textFieldSeccion.getText(),
                    Long.parseLong(textFieldTelegramId.getText()),
                    textFieldActivo.getText()
            );
            usuarioService.crearUsuario(usuario);
            JOptionPane.showMessageDialog(this, "Usuario guardado exitosamente.");
            limpiarCampos();
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar usuario: " + ex.getMessage());
        }
    }

    private void buscarUsuario() {
        try {
            int idUsuario = Integer.parseInt(textFieldId.getText());
            Usuario usuario = usuarioService.obtenerUsuario(idUsuario);
            if (usuario != null) {
                mostrarUsuario(usuario);
            } else {
                JOptionPane.showMessageDialog(this, "Usuario no encontrado.");
            }
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar usuario: " + ex.getMessage());
        }
    }

    private void actualizarUsuario() {
        try {
            Usuario usuario = new Usuario(
                    Integer.parseInt(textFieldId.getText()),
                    textFieldCarne.getText(),
                    textFieldNombre.getText(),
                    textFieldCorreo.getText(),
                    textFieldSeccion.getText(),
                    Long.parseLong(textFieldTelegramId.getText()),
                    textFieldActivo.getText()
            );
            usuarioService.actualizarUsuario(usuario);
            JOptionPane.showMessageDialog(this, "Usuario actualizado exitosamente.");
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar usuario: " + ex.getMessage());
        }
    }

    private void eliminarUsuario() {
        try {
            int idUsuario = Integer.parseInt(textFieldId.getText());
            usuarioService.eliminarUsuario(idUsuario);
            JOptionPane.showMessageDialog(this, "Usuario eliminado exitosamente.");
            limpiarCampos();
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar usuario: " + ex.getMessage());
        }
    }

    private void mostrarUsuario(Usuario usuario) {
        textFieldId.setText(String.valueOf(usuario.getIdusuario()));
        textFieldCarne.setText(usuario.getCarne());
        textFieldNombre.setText(usuario.getNombre());
        textFieldCorreo.setText(usuario.getCorreo());
        textFieldSeccion.setText(usuario.getSeccion());
        textFieldTelegramId.setText(String.valueOf(usuario.getTelegramid()));
        textFieldActivo.setText(usuario.getActivo());
    }

    private void limpiarCampos() {
        textFieldId.setText("");
        textFieldCarne.setText("");
        textFieldNombre.setText("");
        textFieldCorreo.setText("");
        textFieldSeccion.setText("");
        textFieldTelegramId.setText("");
        textFieldActivo.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Ejercicio2Form::new);
    }
}

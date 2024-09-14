package umg.pgm2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuForm {
    private JButton ejercicio1Button;
    private JButton ejercicio2Button;
    private JButton ejercicio3Button;
    private JPanel MenuForm;

    public MenuForm() {
        ejercicio1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame ejercicio1Frame = new Ejercicio1Form();
                ejercicio1Frame.setVisible(true);
            }
        });

        ejercicio2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame ejercicio2Frame = new Ejercicio2Form();
                ejercicio2Frame.setVisible(true);
            }
        });

        ejercicio3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame ejercicio3Frame = new Ejercicio3Form();
                ejercicio3Frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MenuForm");
        frame.setContentPane(new MenuForm().MenuForm);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

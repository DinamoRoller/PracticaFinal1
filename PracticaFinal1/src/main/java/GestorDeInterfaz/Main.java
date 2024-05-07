package GestorDeInterfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main {
    private Experimento experimentoActual;
    private ArrayList<PoblacionBacterias> todasLasPoblaciones; // Lista global de poblaciones

    public Main() {
        experimentoActual = new Experimento();
        todasLasPoblaciones = new ArrayList<>(); // Inicializa la lista global
    }

    public void iniciarInterfaz() {
        JFrame frame = new JFrame("Gestor de Experimentos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(4, 1));

        JButton btnAgregarPoblacion = new JButton("Agregar Población");
        btnAgregarPoblacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarPoblacion();
            }
        });

        JButton btnVisualizarPoblaciones = new JButton("Visualizar Poblaciones");
        btnVisualizarPoblaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizarPoblaciones();
            }
        });

        JButton btnBorrarPoblacion = new JButton("Borrar Población");
        btnBorrarPoblacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarPoblacion();
            }
        });

        JButton btnVerDetalles = new JButton("Ver Detalles");
        btnVerDetalles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verDetalles();
            }
        });

        panelBotones.add(btnAgregarPoblacion);
        panelBotones.add(btnVisualizarPoblaciones);
        panelBotones.add(btnBorrarPoblacion);
        panelBotones.add(btnVerDetalles);

        frame.add(panelBotones, BorderLayout.CENTER);
        frame.setVisible(true);
    }


    private void agregarPoblacion() {
        JTextField txtNombre = new JTextField(10);
        JTextField txtFechaInicio = new JTextField(10);
        JTextField txtFechaFin = new JTextField(10);
        JTextField txtNumBacterias = new JTextField(10);
        JTextField txtTemperatura = new JTextField(10);
        JTextField txtLuminosidad = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Fecha de Inicio:"));
        panel.add(txtFechaInicio);
        panel.add(new JLabel("Fecha de Fin:"));
        panel.add(txtFechaFin);
        panel.add(new JLabel("Número de Bacterias Iniciales:"));
        panel.add(txtNumBacterias);
        panel.add(new JLabel("Temperatura:"));
        panel.add(txtTemperatura);
        panel.add(new JLabel("Luminosidad:"));
        panel.add(txtLuminosidad);

        int result = JOptionPane.showConfirmDialog(null, panel, "Agregar Población", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText();
            String fechaInicio = txtFechaInicio.getText();
            String fechaFin = txtFechaFin.getText();
            int numBacterias = Integer.parseInt(txtNumBacterias.getText());
            double temperatura = Double.parseDouble(txtTemperatura.getText());
            String luminosidad = txtLuminosidad.getText();

            PoblacionBacterias poblacion = new PoblacionBacterias(nombre, fechaInicio, fechaFin, numBacterias, temperatura, luminosidad, new int[]{});
            experimentoActual.agregarPoblacion(poblacion);
            todasLasPoblaciones.add(poblacion); // Agrega la población a la lista global
            JOptionPane.showMessageDialog(null, "Población agregada exitosamente.");
        }
    }

    private void borrarPoblacion() {
        ArrayList<PoblacionBacterias> poblaciones = experimentoActual.getPoblaciones();
        if (poblaciones.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay poblaciones para borrar.");
        } else if (poblaciones.size() == 1) {
            poblaciones.remove(0);
            JOptionPane.showMessageDialog(null, "Población borrada.");
        } else {
            // Si hay más de una población, mostrar un cuadro de diálogo para que el usuario elija cuál eliminar
            String[] nombresPoblaciones = new String[poblaciones.size()];
            for (int i = 0; i < poblaciones.size(); i++) {
                nombresPoblaciones[i] = poblaciones.get(i).getNombre();
            }
            String poblacionSeleccionada = (String) JOptionPane.showInputDialog(null, "Seleccione la población a eliminar:",
                    "Eliminar Población", JOptionPane.QUESTION_MESSAGE, null, nombresPoblaciones, nombresPoblaciones[0]);
            if (poblacionSeleccionada != null) {
                for (int i = 0; i < poblaciones.size(); i++) {
                    if (poblaciones.get(i).getNombre().equals(poblacionSeleccionada)) {
                        poblaciones.remove(i);
                        JOptionPane.showMessageDialog(null, "Población '" + poblacionSeleccionada + "' borrada.");
                        break;
                    }
                }
            }
        }

        // Actualizar la visualización de poblaciones
        visualizarPoblaciones();
    }

    private void visualizarPoblaciones() {
        // Muestra un cuadro de diálogo con los nombres de todas las poblaciones
        ArrayList<PoblacionBacterias> poblaciones = experimentoActual.getPoblaciones();
        if (poblaciones.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay poblaciones registradas.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Poblaciones Registradas:\n");
            for (PoblacionBacterias poblacion : poblaciones) {
                sb.append(poblacion.getNombre()).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }


    private void verDetalles() {
        ArrayList<PoblacionBacterias> poblaciones = experimentoActual.getPoblaciones();
        if (poblaciones.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay poblaciones para ver detalles.");
        } else {
            // Si hay más de una población, mostrar un cuadro de diálogo para que el usuario elija cuál ver detalles
            String[] nombresPoblaciones = new String[poblaciones.size()];
            for (int i = 0; i < poblaciones.size(); i++) {
                nombresPoblaciones[i] = poblaciones.get(i).getNombre();
            }
            String poblacionSeleccionada = (String) JOptionPane.showInputDialog(null, "Seleccione la población de la cual quiera ver los detalles:",
                    "Ver Detalles", JOptionPane.QUESTION_MESSAGE, null, nombresPoblaciones, nombresPoblaciones[0]);
            if (poblacionSeleccionada != null) {
                for (PoblacionBacterias poblacion : poblaciones) {
                    if (poblacion.getNombre().equals(poblacionSeleccionada)) {
                        // Mostrar los detalles de la población seleccionada
                        String detalles = "Detalles de la Población:\n" +
                                "Nombre: " + poblacion.getNombre() + "\n" +
                                "Fecha Inicio: " + poblacion.getFechaInicio() + "\n" +
                                "Fecha Fin: " + poblacion.getFechaFin() + "\n" +
                                "Número de Bacterias Iniciales: " + poblacion.getNumBacteriasIniciales() + "\n" +
                                "Temperatura: " + poblacion.getTemperatura() + "\n" +
                                "Luminosidad: " + poblacion.getLuminosidad() + "\n";
                        JOptionPane.showMessageDialog(null, detalles);
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.iniciarInterfaz();
    }
}

package com.mycompany.salud3;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Salud3 {
    private JFrame frame;
    private JComboBox<String> combo;
    private DefaultListModel<String> listModel;
    private JLabel imageLabel;

    public Salud3() {
        frame = new JFrame("App de Deporte");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        combo = new JComboBox<>();
        combo.addItem("Correr");
        combo.addItem("Bicicleta");
        combo.addItem("Gimnasio");
        c.gridx = 0; c.gridy = 0; c.fill = GridBagConstraints.HORIZONTAL;
        frame.add(combo, c);

        listModel = new DefaultListModel<>();
        JList<String> lista = new JList<>(listModel);
        JScrollPane scroll = new JScrollPane(lista);
        c.gridy = 1; c.weighty = 0.6; c.fill = GridBagConstraints.BOTH;
        frame.add(scroll, c);

        JButton addBtn = new JButton("Añadir actividad");
        addBtn.addActionListener(e -> {
            String item = JOptionPane.showInputDialog(frame, "Nombre de la actividad:");
            if (item != null && !item.isBlank()) {
                combo.addItem(item);
                listModel.addElement(item);
            }
        });
        c.gridy = 2; c.weighty = 0;
        frame.add(addBtn, c);

        JButton excelBtn = new JButton("Generar Excel");
        excelBtn.addActionListener(e -> {
            try {
                ExcelEntrenos.main(null);
                JOptionPane.showMessageDialog(frame, "Excel generado correctamente.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error al generar Excel.");
            }
        });
        c.gridy = 3;
        frame.add(excelBtn, c);

        JButton graficosBtn = new JButton("Generar Gráficos");
        graficosBtn.addActionListener(e -> {
            try {
                GraficosEntrenos.main(null);
                JOptionPane.showMessageDialog(frame, "Gráficos creados correctamente.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error al crear gráficos.");
            }
        });
        c.gridy = 4;
        frame.add(graficosBtn, c);

        JButton pdfBtn = new JButton("Generar PDF");
        pdfBtn.addActionListener(e -> {
            try {
                InformePDF.main(null);
                JOptionPane.showMessageDialog(frame, "PDF generado correctamente.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error al generar PDF.");
            }
        });
        c.gridy = 5;
        frame.add(pdfBtn, c);

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        c.gridx = 1; c.gridy = 0; c.gridheight = 6; c.weightx = 0.5; c.fill = GridBagConstraints.BOTH;
        frame.add(imageLabel, c);

        loadImageFromFile("imagen_local.jpg", 300, 300);

        frame.setSize(900, 600);
        frame.setVisible(true);
    }

    private void loadImageFromFile(String path, int w, int h) {
        ImageIcon ic = new ImageIcon(path);
        setScaledIcon(ic, w, h);
    }

    private void loadImageFromURL(String urlStr, int w, int h) {
        try {
            ImageIcon ic = new ImageIcon(new URL(urlStr));
            setScaledIcon(ic, w, h);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setScaledIcon(ImageIcon ic, int w, int h) {
        Image img = ic.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(img));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Salud3::new);
    }
}
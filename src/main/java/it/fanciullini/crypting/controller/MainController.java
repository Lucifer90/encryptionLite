package it.fanciullini.crypting.controller;

import it.fanciullini.crypting.utils.Costanti;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

import static it.fanciullini.crypting.service.Cryptography.encryptAndDecrypt;
import static it.fanciullini.crypting.service.Cryptography.justDecrypt;
import static it.fanciullini.crypting.service.Cryptography.justEncrypt;

public class MainController extends JPanel {

    private JTextArea textArea;
    private String workingFilePath;

    public MainController() {
        super(new BorderLayout());
        createPanel();
    }

    private void createPanel() {
        JButton openFileChooser = new JButton("Select");
        JButton encryptChooser = new JButton("Encrypt");
        JButton decryptChooser = new JButton("Decrypt");
        JButton testChooser = new JButton("Test");
        openFileChooser.addActionListener(new OpenFileChooser());
        encryptChooser.addActionListener(new EncryptChooser());
        decryptChooser.addActionListener(new DecryptChooser());
        testChooser.addActionListener(new TestChooser());
        textArea = new JTextArea(10, 20);
        textArea.setText(Costanti.howTo);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        JPanel panelButton = new JPanel();
        panelButton.add(openFileChooser);
        panelButton.add(encryptChooser);
        panelButton.add(decryptChooser);
        panelButton.add(testChooser);
        add(panelButton, BorderLayout.SOUTH);
    }

    private class OpenFileChooser implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String toret = Costanti.unplannedError;
            try {
                JFileChooser fileChooser = new JFileChooser();
                int n = fileChooser.showOpenDialog(MainController.this);
                if (n == JFileChooser.APPROVE_OPTION) {
                    File f = fileChooser.getSelectedFile();
                    workingFilePath = f.getPath();
                    toret = String.format(Costanti.selectedFile, f.getName());
                }
            } catch (Exception ex) {
                toret = ex.getMessage();
            }
            textArea.append(toret);
        }
    }

    private class EncryptChooser implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String toret = Costanti.unplannedError;
            try {
                String keys = justEncrypt(workingFilePath).toString();
                toret = Costanti.newLine;
                toret += Costanti.encryptSuccess;
                toret += Costanti.newLine;
                toret += keys;
            } catch (Exception ex) {
                toret = ex.getMessage();
            }
            textArea.append(toret);
        }
    }

    private class DecryptChooser implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String toret = Costanti.unplannedError;
            try {
                justDecrypt(workingFilePath);
                toret = Costanti.newLine;
                toret += Costanti.decryptSuccess;
            } catch (Exception ex) {
                toret = ex.getMessage();
            }
            textArea.append(toret);
        }
    }

    private class TestChooser implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String toret = Costanti.unplannedError;
            try {
                encryptAndDecrypt(workingFilePath);
                toret = Costanti.newLine;
                toret += Costanti.testSuccess;
            } catch (Exception ex) {
                toret = ex.getMessage();
            }
            textArea.append(toret);
        }
    }

    public static void main(String[] argv) {
        try {
            UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
        } catch (UnsupportedLookAndFeelException | IllegalAccessException
                | InstantiationException | ClassNotFoundException ex) {
            return;
        }
        JFrame frame = new JFrame("EncryptionTool");
        MainController controller = new MainController();
        frame.getContentPane().add(controller);
        frame.setIconImage(controller.loadIcon().getImage());
        //frame.setSize(getMonitorResolution());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setVisible(true);
    }

    public ImageIcon loadIcon(){
        String iconName = "pika.png";
        String pathToIcon = getClass().getClassLoader().getResource(iconName).getPath();
        return new ImageIcon(pathToIcon);
    }

    public static Dimension getMonitorResolution() {
        GraphicsDevice gds = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        DisplayMode dm = gds.getDisplayMode();
        int h = dm.getHeight();
        int w = dm.getWidth();
        System.out.println(w + "x" + h);
        // or
        GraphicsConfiguration gc = gds.getDefaultConfiguration();
        Rectangle rec = gc.getBounds();
        System.out.println(rec.width + "x" + rec.height);
        int width = gds.getDisplayMode().getWidth();
        int height = gds.getDisplayMode().getHeight();
        return new Dimension(width, height);
    }

    public void test(String[] args){
        String method = args[0];
        String workingFile;
        try {
            workingFile = args[1];
            switch(method){
                case(Costanti.allMethod):
                    encryptAndDecrypt(workingFile);
                    break;
                case(Costanti.encryptMethod):
                    justEncrypt(workingFile);
                    break;
                case(Costanti.decryptMethod):
                    justDecrypt(workingFile);
                    break;
            }
        } catch (Exception ex) {
            System.out.println("Nessun file trovato: "+ex.getMessage());
            ex.printStackTrace();
        }
    }

}

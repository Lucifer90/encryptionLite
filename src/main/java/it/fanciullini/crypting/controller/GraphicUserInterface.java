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

public class GraphicUserInterface extends JPanel {

    private GridBagLayout gridBagLayout;
    private JTextArea textArea;
    private JTextField filePathString;
    private String workingFilePath;
    private JButton openFileChooser;
    private JButton encryptChooser;
    private JButton decryptChooser;
    private JButton testChooser;
    private JPanel panelBottom;
    private JPanel panelUpper;
    private JScrollPane scrollPane;

    public GraphicUserInterface() {
        super();
        gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        createPanel();
    }

    private void createPanel() {
        initButton();
        initListener();
        initBoxes();
        initContainers();
        fillContainers();
        setLayouts();
    }

    private void initButton(){
        openFileChooser = new JButton("Select");
        encryptChooser = new JButton("Encrypt");
        decryptChooser = new JButton("Decrypt");
        testChooser = new JButton("Test");
    }

    private void initListener(){
        openFileChooser.addActionListener(new OpenFileChooser());
        encryptChooser.addActionListener(new EncryptChooser());
        decryptChooser.addActionListener(new DecryptChooser());
        testChooser.addActionListener(new TestChooser());
    }

    private void initBoxes(){
        filePathString = new JTextField();
        textArea = new JTextArea(10, 20);
        textArea.setText(Costanti.howTo);

        scrollPane = new JScrollPane(textArea);
    }

    private void initContainers(){
        panelBottom = new JPanel();
        panelUpper = new JPanel();
    }

    private void fillContainers(){
        fillBottomPanel();
        fillUpperPanel();
    }

    private void fillBottomPanel(){
        GridBagLayout panelBottomLayout = new GridBagLayout();
        panelBottom.setLayout(panelBottomLayout);

        GridBagConstraints constraintsBotton = new GridBagConstraints();
        setLayout(filePathString, constraintsBotton, 0, 0, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTHWEST);
        setLayout(openFileChooser, constraintsBotton, 1, 0, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTHWEST);
        setLayout(encryptChooser, constraintsBotton, 2, 0, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTHWEST);
        setLayout(decryptChooser, constraintsBotton, 3, 0, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTHWEST);
        setLayout(testChooser, constraintsBotton, 4, 0, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTHWEST);
    }

    private void fillUpperPanel(){
        GridBagLayout panelUpperLayout = new GridBagLayout();
        panelBottom.setLayout(panelUpperLayout);

        GridBagConstraints constraintsUpper = new GridBagConstraints();
        setLayout(scrollPane, constraintsUpper, 0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
        //setLayout(openFileChooser, constraintsUpper, 1, 0, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTHWEST);

    }

    private void setLayouts(){
        GridBagConstraints constraints = new GridBagConstraints();
        //this.add(panelBottom, constraints);
        //this.add(new JScrollPane(textArea), constraints);
        setLayout(panelUpper, constraints, 0, 0, 10, 0, 10, 0, GridBagConstraints.BOTH, GridBagConstraints.NORTH);
        setLayout(panelBottom, constraints, 0, 1, 0, 0, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH);
    }

    private void setLayout(Component component, GridBagConstraints constraints,
                           int gridX, int gridY,
                           int weightX, int weightY,
                           int ipadX, int ipadY,
                           int filling, int anchor
            ){
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.weightx = weightX;
        constraints.weighty = weightY;
        constraints.ipadx = ipadX;
        constraints.ipady = ipadY;
        constraints.fill = filling;
        constraints.anchor = anchor;
        gridBagLayout.setConstraints(component, constraints);
        this.add(component);
    }

    private class OpenFileChooser implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String toret = Costanti.unplannedError;
            try {
                JFileChooser fileChooser = new JFileChooser();
                int n = fileChooser.showOpenDialog(GraphicUserInterface.this);
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
        GraphicUserInterface controller = new GraphicUserInterface();
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

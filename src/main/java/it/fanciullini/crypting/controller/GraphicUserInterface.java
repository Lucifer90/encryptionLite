package it.fanciullini.crypting.controller;

import it.fanciullini.crypting.utils.Costanti;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.*;

import static it.fanciullini.crypting.service.Cryptography.*;

public class GraphicUserInterface extends JPanel {

    private JTextArea logBox;
    private JTextField pathSelectedFile;
    private String workingFilePath;
    private String cachedJson;
    private JButton openSelectedFileChooser;
    private JButton encryptChooser;
    private JButton decryptChooser;
    private JButton testChooser;
    private JPanel panelBottom;
    private JPanel panelUpper;
    private JPanel radioContainerPanel;
    private JPanel radioPanel;
    private JButton openSelectedJsonChooser;
    private JTextField pathSelectedJson;

    private JPanel keyInputPanel;

    private JLabel labelSafeWord;
    private JLabel labelPublicKey;
    private JTextField safeWord;
    private JTextField publicKey;

    private JCheckBox jsonSelection;
    private JCheckBox manualSelection;

    public GraphicUserInterface(JFrame parent) {
        super();
        this.setLayout(new GridBagLayout());
        createPanel();
        Dimension d = getMonitorResolution();
        setDimension( parent, new Dimension(d.width/2, d.height/2));
    }

    private void createPanel() {
        initContainers();
        setUpLabels();
        initButton();
        initBoxes();
        fillContainers();
        setLayouts();
        initListener();

        initStates();
    }

    private void initStates(){
        jsonSelection.setSelected(false);
        manualSelection.setSelected(true);
        logBox.getCaret().setSelectionVisible(false);
        pathSelectedFile.getCaret().setVisible(false);
        openSelectedJsonChooser.setEnabled(false);
    }

    private void manualSelection(boolean value){
        if (value){
            jsonSelection.setSelected(false);
            manualSelection.setSelected(true);
            componentOn(safeWord);
            componentOn(publicKey);
            openSelectedJsonChooser.setEnabled(false);
            cachedJson = pathSelectedJson.getText();
            pathSelectedJson.setText("");
            pathSelectedJson.setBackground(Color.LIGHT_GRAY);
        } else {
            jsonSelection.setSelected(true);
            manualSelection.setSelected(false);
            componentOff(safeWord);
            componentOff(publicKey);
            openSelectedJsonChooser.setEnabled(true);
            pathSelectedJson.setText(cachedJson != null ? cachedJson : "");
            pathSelectedJson.setBackground(Color.WHITE);
        }
    }

    private void componentOff(JTextField jTextField){
        jTextField.setEditable(false);
        jTextField.setBackground(Color.LIGHT_GRAY);
        jTextField.getCaret().setVisible(false);
    }

    private void componentOn(JTextField jTextField){
        jTextField.setEditable(true);
        jTextField.setBackground(Color.WHITE);
        jTextField.getCaret().setVisible(true);
    }

    private void initButton(){
        openSelectedFileChooser = new JButton("Select File");
        encryptChooser = new JButton("Encrypt");
        decryptChooser = new JButton("Decrypt");
        testChooser = new JButton("Test");
        openSelectedJsonChooser = new JButton("Select JSON");
        initRadioButton();
    }

    private void initRadioButton(){
        jsonSelection = new JCheckBox("Json", true);
        manualSelection = new JCheckBox("Manuale", false);
        manualSelection.requestFocusInWindow();
        radioPanel.add(jsonSelection);
        radioPanel.add(manualSelection);

        jsonSelection.setMnemonic(KeyEvent.VK_C);
        manualSelection.setMnemonic(KeyEvent.VK_M);

    }

    private void initListener(){
        openSelectedFileChooser.addActionListener(new OpenFileChooser());
        openSelectedJsonChooser.addActionListener(new OpenFileChooser());
        encryptChooser.addActionListener(new EncryptChooser());
        decryptChooser.addActionListener(new DecryptChooser());
        testChooser.addActionListener(new TestChooser());
        manualSelection.addItemListener(new manualCheckBox());
        jsonSelection.addItemListener(new jsonCheckBox());
    }

    private void initBoxes(){
        pathSelectedFile = new JTextField();
        pathSelectedFile.setEditable(false);
        pathSelectedFile.setBackground(Color.WHITE);
        setDimension(pathSelectedFile, new Dimension(300, 10));

        pathSelectedJson = new JTextField();
        pathSelectedJson.setEditable(false);
        pathSelectedJson.setBackground(Color.WHITE);
        setDimension(pathSelectedJson, new Dimension(300, 20));

        safeWord = new JTextField();
        safeWord.setEditable(false);
        safeWord.setBackground(Color.WHITE);
        setDimension(pathSelectedFile, new Dimension(300, 10));

        publicKey = new JTextField();
        publicKey.setEditable(false);
        publicKey.setBackground(Color.WHITE);
        setDimension(pathSelectedFile, new Dimension(300, 10));

        logBox = new JTextArea(10, 20);
        logBox.setText(Costanti.howTo_ita);
        logBox.setEditable(false);
        logBox.setBackground(Color.WHITE);
    }

    private void setUpLabels(){
        labelSafeWord = new JLabel("Parola chiave criptata");
        labelPublicKey = new JLabel("Chiave pubblica");
    }

    private void setDimension(Component c, Dimension d){
        c.setMaximumSize(d);
        c.setMinimumSize(d);
        c.setPreferredSize(d);
    }

    private void initContainers(){
        panelBottom = new JPanel();
        panelUpper = new JPanel();
        keyInputPanel = new JPanel();
        radioContainerPanel = new JPanel();
        radioPanel = new JPanel();
        panelBottom.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelUpper.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        radioContainerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void fillContainers(){
        fillBottomPanel();
        fillRadioPanel();
        fillKeyInputPanel();
        fillUpperPanel();
    }

    private void fillBottomPanel(){
        GridBagLayout panelBottomLayout = new GridBagLayout();
        panelBottom.setLayout(panelBottomLayout);
        setLayout(panelBottom, pathSelectedFile, 0, 0, 0.40, 0.2, 1, 0, 2, 1, GridBagConstraints.BOTH, GridBagConstraints.SOUTH);
        setLayout(panelBottom, openSelectedFileChooser, 2, 0, 0.15, 0.2, 1, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.SOUTH);
        setLayout(panelBottom, encryptChooser, 3, 0, 0.15, 0.2, 1, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.SOUTH);
        setLayout(panelBottom, decryptChooser, 4, 0, 0.15, 0.2, 1, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.SOUTH);
        setLayout(panelBottom, testChooser, 5,0, 0.15, 0.2, 1, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.SOUTH);
    }

    private void fillRadioPanel(){
        GridBagLayout panelRadioLayout = new GridBagLayout();
        radioContainerPanel.setLayout(panelRadioLayout);
        setLayout(radioContainerPanel, radioPanel, 0, 0, 1, 1, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTH);
        setLayout(radioContainerPanel, pathSelectedJson, 0, 8, 1, 0, 0, 0, 1, 2, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH);
        setLayout(radioContainerPanel, openSelectedJsonChooser, 0, 10, 1, 0, 0, 0, 1, 2, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH);
    }

    private void fillKeyInputPanel(){
        GridBagLayout panelInputKeysLayout = new GridBagLayout();
        keyInputPanel.setLayout(panelInputKeysLayout);
        setLayout(keyInputPanel, labelSafeWord, 0, 0, 0, 0, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);
        setLayout(keyInputPanel, safeWord, 1, 0, 1, 0, 0, 0, 4, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
        setLayout(keyInputPanel, labelPublicKey, 0, 1, 0, 0, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);
        setLayout(keyInputPanel, publicKey, 1, 1, 1, 0, 0, 0, 4, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
    }

    private void fillUpperPanel(){
        GridBagLayout panelUpperLayout = new GridBagLayout();
        panelUpper.setLayout(panelUpperLayout);
        setLayout(panelUpper, logBox, 0, 0, 1, 0.5, 0, 0, 3, 1, GridBagConstraints.BOTH, GridBagConstraints.WEST);
        setLayout(panelUpper, radioContainerPanel, 3, 0, 1, 0.5, 0, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.EAST);
    }

    private void setLayouts(){
        GridBagLayout generalLayout = new GridBagLayout();
        this.setLayout(generalLayout);
        setLayout(this, panelUpper, 0, 0, 0.5, 0.87, 0, 0, 1, 12, GridBagConstraints.BOTH, GridBagConstraints.NORTH);
        setLayout(this, keyInputPanel, 0, 13, 0.5, 0.12, 0, 0, 1, 2, GridBagConstraints.BOTH, GridBagConstraints.NORTH);
        setLayout(this, panelBottom, 0, 15, 0.5, 0.01, 0, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.SOUTH);
    }

    private void setLayout(JPanel parent, JComponent child,
                           int gridX, int gridY,
                           double weightX, double weightY,
                           int ipadX, int ipadY,
                           int gridWidth, int gridHeight,
                           int filling, int anchor
            ){
        GridBagConstraints constraints = setConstaints(gridX, gridY, weightX, weightY, ipadX, ipadY,
                gridWidth, gridHeight, filling, anchor);
        try {
            parent.add(child, constraints);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private GridBagConstraints setConstaints(int gridX, int gridY,
                             double weightX, double weightY,
                             int ipadX, int ipadY,
                             int gridWidth, int gridHeight,
                             int filling, int anchor){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.weightx = weightX;
        constraints.weighty = weightY;
        constraints.ipadx = ipadX;
        constraints.ipady = ipadY;
        constraints.gridwidth = gridWidth;
        constraints.gridheight = gridHeight;
        constraints.fill = filling;
        constraints.anchor = anchor;
        return constraints;
    }

    private class OpenFileChooser implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String toret = Costanti.unplannedError;
            String tmp = "";
            try {
                JFileChooser fileChooser = new JFileChooser();
                int n = fileChooser.showOpenDialog(GraphicUserInterface.this);
                if (n == JFileChooser.APPROVE_OPTION) {
                    File f = fileChooser.getSelectedFile();
                    tmp = f.getPath();
                    toret = String.format(Costanti.selectedFile, f.getName());
                }
            } catch (Exception ex) {
                toret = ex.getMessage();
            }
            if (e.getActionCommand().equalsIgnoreCase("Select File")) {
                workingFilePath = tmp;
                pathSelectedFile.setText(workingFilePath);
            } else {
                pathSelectedJson.setText(tmp);
            }
            logBox.append(Costanti.newLine+toret);
        }
    }

    private class EncryptChooser implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String toret = Costanti.unplannedError;
            try {
                if (workingFilePath.equalsIgnoreCase("")) {
                    toret = Costanti.alertNoPath;
                    JOptionPane.showMessageDialog(null, toret, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    //separa toencrypt in path-file, replace solo file e rimergia
                    try {
                        workingFilePath = createWorkingDirectory(workingFilePath);
                    } catch (IOException ioex) {
                        return;
                    }
                    String keys = justEncrypt(workingFilePath).toString();
                    toret = Costanti.newLine;
                    toret += Costanti.encryptSuccess;
                    toret += Costanti.newLine;
                    toret += keys;
                }
            } catch (Exception ex) {
                toret = ex.getMessage();
            }
            logBox.append(toret);
        }
    }

    private class DecryptChooser implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String toret = Costanti.unplannedError;
            try {
                if (workingFilePath.equalsIgnoreCase("")) {
                    toret = Costanti.alertNoPath;
                    JOptionPane.showMessageDialog(null, toret, "Error", JOptionPane.ERROR_MESSAGE);
                } else if (jsonSelection.isSelected()) {
                    if (pathSelectedJson.getText().trim().equalsIgnoreCase("")) {
                        justDecrypt(workingFilePath);
                        toret = Costanti.newLine;
                        toret += Costanti.decryptSuccess;
                    } else {
                        /*try {
                            workingFilePath = createWorkingDirectory(workingFilePath);
                        } catch (IOException ioex) {
                            return;
                        }*/
                        justDecrypt(workingFilePath, pathSelectedJson.getText().trim());
                        toret = Costanti.newLine;
                        toret += Costanti.decryptSuccess;
                    }
                } else if(!safeWord.getText().trim().equalsIgnoreCase("")
                        && !publicKey.getText().trim().equalsIgnoreCase("")) {
                    justDecrypt(workingFilePath, safeWord.getText().trim(), publicKey.getText().trim());
                    toret = Costanti.newLine;
                    toret += Costanti.decryptSuccess;
                } else {
                    toret = Costanti.alertNoKeys;
                    JOptionPane.showMessageDialog(null, toret, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                toret = ex.getMessage();
            }
            logBox.append(toret);
        }
    }

    private class TestChooser implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String toret = Costanti.unplannedError;
            try {
                if (workingFilePath.equalsIgnoreCase("")) {
                    toret = Costanti.alertNoPath;
                    JOptionPane.showMessageDialog(null, toret, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    encryptAndDecrypt(workingFilePath);
                    toret = Costanti.newLine;
                    toret += Costanti.testSuccess;
                }
            } catch (Exception ex) {
                toret = ex.getMessage();
            }
            logBox.append(toret);
        }
    }

    private class manualCheckBox implements ItemListener {

        public void itemStateChanged(ItemEvent e){
            if (manualSelection.isSelected()){
                manualSelection(true);
            }
        }
    }

    private class jsonCheckBox implements ItemListener {

        public void itemStateChanged(ItemEvent e){
            if (jsonSelection.isSelected()) {
                manualSelection(false);
            }
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
        GraphicUserInterface controller = new GraphicUserInterface(frame);
        frame.getContentPane().add(controller);
        //frame.setIconImage(controller.loadIcon("baseicon.ico").getImage());
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ImageIcon icon = controller.createImageIcon("/baseicon.png");
        frame.setIconImage(icon.getImage());
        frame.setUndecorated(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setVisible(true);
    }

    private ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public ImageIcon loadIcon(String icoName){
        String pathToIcon = "";
        File file;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            file = new File(classLoader.getResource(icoName).getFile());
            pathToIcon = file.getPath();
        } catch (NullPointerException ex) {
            return null;
        }
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

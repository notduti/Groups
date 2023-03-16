import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import static java.util.Collections.shuffle;

public class FrmGroups extends JFrame implements ActionListener, DocumentListener, ItemListener {

    private static final int MAX_GROUPS = 20;
    private String filename = null;
    private int sizeOfGroups = 0;
    private Boolean modified = false;
    private ArrayList<Student> list = null;
    private JMenuItem mniOpen = null;
    private JMenuItem mniSave = null;
    private JMenuItem mniSaveNew = null;
    private JMenuItem mniExit = null;
    private JMenuItem mniDocumentation = null;
    private JMenuItem mniContactUs = null;
    private JComboBox cmbSize = null;
    private JTextArea txtFile = null;
    private JButton btnShuffle = null;

    public FrmGroups() {

        setTitle("Groups ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600, 500);

        initUI();
        populate();

        setVisible(true);
    }

    private void populate() {

        this.txtFile.setText("");
        if(this.list == null) return;

        if(this.sizeOfGroups == 0) {

            for(Student student: this.list) this.txtFile.append(student.showOnGUI() + "\n");
            return;
        }

        int groups = 1, i = 0;
        this.txtFile.append("Group 1\n");
        for(Student student: this.list) {

            //System.out.println(i + " == " + this.sizeOfGroups);
            if(i == this.sizeOfGroups) {
                groups++;
                this.txtFile.append("Group " + groups + "\n");
                i = 0;
            }
            else {
                this.txtFile.append("    " + student.showOnGUI() + "\n");
                i++;
            }
        }
    }

    private void initUI() {

        this.panelNorth();
        this.panelCenter();
        this.panelSouth();
    }

    private void panelNorth() {

        JPanel pnlNorth = new JPanel(new GridLayout(1, 2));

        JPanel pnlMenus = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JMenuBar mnbNorth = new JMenuBar();
        JMenu mnuFile = new JMenu("File");

        this.mniOpen = new JMenuItem("Open...");
        this.mniSave = new JMenuItem("Save");
        this.mniSaveNew = new JMenuItem("Save new...");
        this.mniExit = new JMenuItem("Exit");

        mnuFile.add(this.mniOpen);
        mnuFile.addSeparator();
        mnuFile.add(this.mniSave);
        mnuFile.add(this.mniSaveNew);
        mnuFile.addSeparator();
        mnuFile.add(this.mniExit);

        mnbNorth.add(mnuFile);

        this.mniOpen.addActionListener(this);
        this.mniSave.addActionListener(this);
        this.mniSaveNew.addActionListener(this);
        this.mniExit.addActionListener(this);


        JMenu mnuHelp = new JMenu("Help");

        this.mniDocumentation = new JMenuItem("Read the documentation...");
        this.mniContactUs = new JMenuItem("Contact us...");

        mnuHelp.add(this.mniDocumentation);
        mnuHelp.add(this.mniContactUs);

        mnbNorth.add(mnuHelp);

        pnlMenus.add(mnbNorth);


        JPanel pnlSize = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JLabel lblSize = new JLabel("Size of groups: ");
        Integer[] size = new Integer[MAX_GROUPS + 1];
        for(int i = 1; i <= MAX_GROUPS; i++) size[i] = i;
        this.cmbSize = new JComboBox(size);
        pnlSize.add(lblSize);
        pnlSize.add(this.cmbSize);

        this.cmbSize.addItemListener(this);

        pnlNorth.add(pnlMenus);
        pnlNorth.add(pnlSize);

        this.add(pnlNorth, BorderLayout.NORTH);
    }

    private void panelCenter() {

        this.txtFile = new JTextArea();
        JScrollPane sp = new JScrollPane(this.txtFile);
        this.add(sp, BorderLayout.CENTER);
    }

    private void panelSouth() {

        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.btnShuffle = new JButton("SHUFFLE");
        pnlSouth.add(this.btnShuffle);
        this.add(pnlSouth, BorderLayout.SOUTH);

        this.btnShuffle.addActionListener(this);
    }

    private void populateStudents() {

        this.list = new ArrayList<>();
        this.sizeOfGroups = 0;
        try {

            BufferedReader br = new BufferedReader(new FileReader(this.filename));
            String line = null;
            int group = 1;
            while((line = br.readLine()) != null) {

                String[] els = line.split(" ");
                if(els[0].compareTo("Group") != 0)
                    this.list.add(new Student(els[0], els[1], group));
                else {
                    group = Integer.parseInt(els[1]);
                    this.sizeOfGroups++;
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == this.btnShuffle) shuffle();
        if(e.getSource() == this.mniOpen) openFile();
        if(e.getSource() == this.mniSaveNew) saveNewFile();
        if(e.getSource() == this.mniSave) saveFile();
        if(e.getSource() == this.mniExit) exitApp();
    }

    private void openFile() {

        JFileChooser fc = new JFileChooser();
        int rc = fc.showOpenDialog(this);
        if(rc != JFileChooser.APPROVE_OPTION) return;
        this.filename = fc.getSelectedFile().getAbsolutePath();
        populateStudents();
        System.out.println(this.list);
        populate();
    }

    private void saveNewFile() {

        JFileChooser fc = new JFileChooser();
        int rc = fc.showOpenDialog(this);
        if(rc != JFileChooser.APPROVE_OPTION) return;
        this.filename = fc.getSelectedFile().getAbsolutePath();
        saveFile();
    }

    private void saveFile() {

        try {
            PrintWriter pw = new PrintWriter(new FileWriter(this.filename));
            pw.print(txtFile.getText());
            pw.close();
            modified = false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void exitApp() {


    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        this.modified = true;
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.modified = true;
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        this.modified = true;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if(e.getStateChange() == ItemEvent.SELECTED) {

            JComboBox<Integer> cb = (JComboBox<Integer>) e.getSource();
            this.sizeOfGroups = Integer.parseInt(cb.getSelectedItem() + "");
            System.out.println(this.sizeOfGroups);
        }
    }

    private void shuffle() {
        Collections.shuffle(this.list);
        populate();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmGroups());
    }

}
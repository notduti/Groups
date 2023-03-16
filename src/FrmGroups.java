import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

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

        if(this.list == null) return;
        int groups = 1;
        for(Student student: this.list) {

            this.txtFile.append("Group: " + groups + "\n");
            for(int i = 0; i < this.sizeOfGroups; i++)
                this.txtFile.append(student.toString() + "\n");
            groups++;
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == this.btnShuffle) {
            shuffle();
        }
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
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmGroups());
    }

}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Groups extends JFrame implements ActionListener {

    private JComboBox cmbSize = null;
    private JTextArea txtFile = null;
    private JButton btnShuffle = null;

    public Groups() {

        setTitle("Groups ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600, 500);

        initUI();
        populate();

        setVisible(true);
    }

    private void populate() {
    }

    private void initUI() {

        this.panelNorth();
        this.panelCenter();
        this.panelSouth();
    }

    private void panelNorth() {


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

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Groups());
    }
}
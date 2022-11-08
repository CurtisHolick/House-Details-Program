import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import java.awt.event.*;

public class JobAssignmentDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel inputPanel;
    private JComboBox<Brother> brotherCombobox;
    private JComboBox<Detail> detailCombobox;

    public JobAssignmentDialog() {
//        if(Brother.brothers.size() == 0) {
//            Brother.loadBrotherhood();
//        }
//        if(Detail.details.size() == 0) {
//            Detail.loadDetails();
//        }
        setContentPane(contentPane);
        setModal(true);
        setTitle("Assign Detail");
        Border frameBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        frameBorder = new CompoundBorder(frameBorder, BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        frameBorder = new CompoundBorder(frameBorder, BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPane.setBorder(frameBorder);

        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        brotherCombobox = new JComboBox<>();
        for(Brother brother : Brother.brothers) {
            brotherCombobox.addItem(brother);
        }
        this.inputPanel.add(brotherCombobox);

        detailCombobox = new JComboBox<>();
        for(Detail detail : Detail.details) {
            detailCombobox.addItem(detail);
        }
        this.inputPanel.add(detailCombobox);


        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void onOK() {
        Brother selectedBrother = (Brother)brotherCombobox.getSelectedItem();
        Detail selectedDetail = (Detail)detailCombobox.getSelectedItem();
        if(selectedBrother != null && selectedDetail != null) {
//            selectedBrother.setDetail(selectedDetail);
//            System.out.println(selectedBrother.name);
//            System.out.println(selectedBrother.detail.jobTitle);
//            selectedBrother.setAssignedDetail(selectedDetail);
            selectedDetail.assignBrother(selectedBrother);

        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Error assigning detail");
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        JobAssignmentDialog dialog = new JobAssignmentDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}

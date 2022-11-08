import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;

public class AddDetailDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<Integer> ratingComboBox;
    private JComboBox<Integer> numBrothersComboBox;
    private JTextField detailTitleTextField;

    public AddDetailDialog() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Add New Detail");
        getRootPane().setDefaultButton(buttonOK);

        Border frameBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        frameBorder = new CompoundBorder(frameBorder, BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        frameBorder = new CompoundBorder(frameBorder, BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPane.setBorder(frameBorder);

//        System.out.println(contentPane.getBackground().toString());

        Integer[] ratingOptions = new Integer[Detail.maxRating];
        for (int i = 0; i < Detail.maxRating; i++) {
            ratingOptions[i] = i+1;
        }
        DefaultComboBoxModel<Integer> ratingModel = new DefaultComboBoxModel<>(ratingOptions);
        ratingComboBox.setModel(ratingModel);


        Integer[] numBrothersOptions = new Integer[Detail.maxNumBrothersOnDetail];
        for (int i = 0; i < Detail.maxNumBrothersOnDetail; i++) {
            numBrothersOptions[i] = i+1;
        }

        DefaultComboBoxModel<Integer> numBrothersModel = new DefaultComboBoxModel<>(numBrothersOptions);
        numBrothersComboBox.setModel(numBrothersModel);



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
        // add your code here
        String detailTitle = detailTitleTextField.getText();
        int rating = (int)ratingComboBox.getSelectedItem();
        int numBrothers = (int)numBrothersComboBox.getSelectedItem();
        new Detail(detailTitle, rating, numBrothers, true);
        Detail.updateDetailCsv();
        Detail.reloadDetails();
        Main.updateMainSetupPanel();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        try {
            UIManager.put("control", new Color(233, 238, 242));
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        AddDetailDialog dialog = new AddDetailDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}

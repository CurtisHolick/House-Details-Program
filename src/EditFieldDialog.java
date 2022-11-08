import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import java.awt.event.*;

public class EditFieldDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton buttonDelete;
    private JTextField textInputField;
    private Object objectToEdit;
    private String newText;

    public EditFieldDialog(Object objectToEdit, String title) {
        this.setTitle(title);
        Border frameBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        frameBorder = new CompoundBorder(frameBorder, BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        frameBorder = new CompoundBorder(frameBorder, BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPane.setBorder(frameBorder);


        this.newText = "";
        if(objectToEdit.getClass().equals(Detail.class)) {
            textInputField.setText(((Detail) objectToEdit).jobTitle);
        } else if(objectToEdit.getClass().equals(Brother.class)) {
            textInputField.setText(((Brother) objectToEdit).name);
        } else {
            showErrorMessage();
        }
        this.objectToEdit = objectToEdit;

        setContentPane(contentPane);
        setModal(true);
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

        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDelete();
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
        this.newText = this.textInputField.getText();
        if(objectToEdit.getClass().equals(Detail.class)) {
            Detail detailToEdit = (Detail)objectToEdit;
            detailToEdit.setJobTitle(this.textInputField.getText());

        } else if(objectToEdit.getClass().equals(Brother.class)) {
            Brother brotherToEdit = (Brother)objectToEdit;
            brotherToEdit.setName(this.textInputField.getText());
        }

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void onDelete() {
        this.newText = null;
        if(objectToEdit.getClass().equals(Detail.class)) {
            Detail detailToEdit = (Detail)objectToEdit;
            Detail.details.remove(detailToEdit);
            Detail.updateDetailCsv();

        } else if(objectToEdit.getClass().equals(Brother.class)) {
            Brother brotherToEdit = (Brother)objectToEdit;
            Brother.brothers.remove(brotherToEdit);
            Brother.updateBrotherCsv();
        }
        dispose();
    }

    private void showErrorMessage() {
        JOptionPane.showMessageDialog(new JFrame(), String.format("Error editing object of type: %s", objectToEdit.getClass().toString()));
        dispose();
    }

    public String getNewText() {
        return this.newText;
    }

    public static void main(String[] args) {
//        EditFieldDialog dialog = new EditFieldDialog();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
    }
}

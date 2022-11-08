import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SingleDetailRow extends JPanel {
    public Detail detail;
    public JCheckBox checkbox;
    private JComboBox<Integer> ratingCombobox;
    private JComboBox<Integer> numBrothersCombobox;
    private JLabel ratingLabel, numBrothersLabel;


    //Outer list: 5 long, one list for each element
    //Inner list: # of details long, each element is a single element(ie there is a list of just checkboxes)
    public static ArrayList<ArrayList<JComponent>> allDetailRowComponents = null;
    public static ArrayList<SingleDetailRow> allDetailRows = new ArrayList<>();

    public SingleDetailRow(Detail detail) {
        super();
        if(allDetailRows == null) {
            allDetailRows = new ArrayList<>();
        }
        if(allDetailRowComponents == null) {
            allDetailRowComponents = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                allDetailRowComponents.add(new ArrayList<>());
            }
        }
        allDetailRows.add(this);

        this.detail = detail;
        this.checkbox = new JCheckBox(this.detail.jobTitle);
        this.checkbox.setSelected(this.detail.enabled);
        this.checkbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detail.setEnabled(checkbox.isSelected());
            }
        });

        SingleDetailRow selfReference = this;
        this.checkbox.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)) {
                    JFrame popupFrame = new JFrame("Edit Detail Title");


                    EditFieldDialog editFieldDialog = new EditFieldDialog(detail, "Edit Detail Title");
                    editFieldDialog.pack();
                    editFieldDialog.setVisible(true);
                    String newText = editFieldDialog.getNewText();
                    if(newText == null) {
                        //delete
                        deleteSelf();

                    } else if(!newText.equals("")) {
                        //changed
                        checkbox.setText(newText);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });


        this.ratingLabel = new JLabel(" Rating:");

        Integer[] ratingOptions = new Integer[Detail.maxRating];
        for (int i = 0; i < Detail.maxRating; i++) {
            ratingOptions[i] = i+1;
        }

        this.ratingCombobox = new JComboBox<>(ratingOptions);
        this.ratingCombobox.setSelectedItem(this.detail.rating);
        this.ratingCombobox.setToolTipText("Rating");
        this.ratingCombobox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int newRating = (int)ratingCombobox.getSelectedItem();
                detail.setRating(newRating);
            }
        });

        this.numBrothersLabel = new JLabel(" Number of Brothers:");

        Integer[] numBrothersOptions = new Integer[Detail.maxNumBrothersOnDetail];
        for (int i = 0; i < Detail.maxNumBrothersOnDetail; i++) {
            numBrothersOptions[i] = i+1;
        }

        this.numBrothersCombobox = new JComboBox<>(numBrothersOptions);
        this.numBrothersCombobox.setSelectedItem(this.detail.numBrothers);
        this.numBrothersCombobox.setToolTipText("Number of Brothers");
        this.numBrothersCombobox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int newNumBrothers = (int)numBrothersCombobox.getSelectedItem();
                detail.setNumBrothers(newNumBrothers);
            }
        });


        allDetailRowComponents.get(0).add(checkbox);
        allDetailRowComponents.get(1).add(ratingLabel);
        allDetailRowComponents.get(2).add(ratingCombobox);
        allDetailRowComponents.get(3).add(numBrothersLabel);
        allDetailRowComponents.get(4).add(numBrothersCombobox);


    }

    public void deleteSelf() {
        DetailSetupPanel.detailSetupPanel.deleteRow(this.detail.jobTitle);

//        private JCheckBox checkbox;
//        private JComboBox<Integer> ratingCombobox;
//        private JComboBox<Integer> numBrothersCombobox;
//        private JLabel ratingLabel, numBrothersLabel;
    }

    public boolean isActive() {
        return this.checkbox.isSelected();
    }

    public int getNumBrothers() {
        return (int)numBrothersCombobox.getSelectedItem();
    }

}

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class DetailListCreationPanel extends JPanel {
    //Input panel elements
    private JPanel inputPanel;
    private JTextField titleTextField;
    private JComboBox<Integer> ratingComboBox;
    private JComboBox<Integer> numBrothersComboBox;
    private JButton addDetailButton, cancelButton, saveButton;
    private JLabel titleLabel, ratingLabel, numBrothersLabel, placeHolderLabel;

    private JSeparator panelSeparator;

    //List panel elements
    private JPanel listPanel;
    private JLabel titleHeaderLabel, ratingHeaderLabel, numBrothersHeaderLabel;

    //Functional variables
//    ArrayList<Detail> newDetails = new ArrayList<>();

    public DetailListCreationPanel(String newListTitle) {
        super();
        this.setLayout(new GridBagLayout());
//        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        Border frameBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        frameBorder = new CompoundBorder(frameBorder, BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        frameBorder = new CompoundBorder(frameBorder, BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.setBorder(frameBorder);


        initListDisplayPanel();
        initInputPanel();
        Detail.detailFileName = newListTitle;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;
        this.add(listPanel, gbc);
//        listPanel.setVisible(false);


        gbc.gridy = 1;
        panelSeparator = new JSeparator(SwingConstants.HORIZONTAL);
//        panelSeparator.setVisible(false);
        this.add(panelSeparator, gbc);

        gbc.gridy = 2;
        this.add(inputPanel, gbc);

    }

    private void initListDisplayPanel() {
        listPanel = new JPanel(new GridBagLayout());
        titleHeaderLabel = new JLabel("Job", SwingConstants.CENTER);
        ratingHeaderLabel = new JLabel("Rating", SwingConstants.CENTER);
        numBrothersHeaderLabel = new JLabel("Number of Brothers", SwingConstants.CENTER);


        Font headerFont = new Font(titleHeaderLabel.getFont().getFontName(), Font.BOLD, titleHeaderLabel.getFont().getSize()+2);
        titleHeaderLabel.setFont(headerFont);
        ratingHeaderLabel.setFont(headerFont);
        numBrothersHeaderLabel.setFont(headerFont);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        listPanel.add(titleHeaderLabel, gbc);

        gbc.gridx = 1;
        listPanel.add(ratingHeaderLabel, gbc);;

        gbc.gridx = 2;
        listPanel.add(numBrothersHeaderLabel, gbc);


        JSeparator horizontalSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        listPanel.add(horizontalSeparator, gbc);


        placeHolderLabel = new JLabel("Details will appear here as they are added", SwingConstants.CENTER);
        Font placeHolderFont = new Font(placeHolderLabel.getFont().getFontName(), Font.ITALIC, placeHolderLabel.getFont().getSize());
        placeHolderLabel.setFont(placeHolderFont);
        gbc.gridy = 2;
        listPanel.add(placeHolderLabel, gbc);
    }

    //Create all input panel elements
    private void initInputPanel() {
        inputPanel = new JPanel(new GridBagLayout());

        //Key adapter to handle when enter is pressed
        KeyAdapter enterKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    addDetailFromInputs();
                }
            }
        };

        //Create title label and input
        titleLabel = new JLabel("Detail Title:");
        titleTextField = new JTextField();
        titleTextField.addKeyListener(enterKeyAdapter);

        //Setup rating dropdown (combobox) and label
        ratingLabel = new JLabel("Rating:");
        Integer[] ratingOptions = new Integer[Detail.maxRating];
        for (int i = 0; i < Detail.maxRating; i++) {
            ratingOptions[i] = i+1;
        }
        ratingComboBox = new JComboBox<>(ratingOptions);
        ratingComboBox.setSelectedItem(ratingComboBox.getItemAt(0));
        ratingComboBox.setToolTipText("Rating");
        ratingComboBox.addKeyListener(enterKeyAdapter);

        //Setup num brothers dropdown (combobox) and label
        numBrothersLabel = new JLabel("Number of Brothers:");
        Integer[] numBrothersOptions = new Integer[Detail.maxNumBrothersOnDetail];
        for (int i = 0; i < Detail.maxNumBrothersOnDetail; i++) {
            numBrothersOptions[i] = i+1;
        }
        this.numBrothersComboBox = new JComboBox<>(numBrothersOptions);
        this.numBrothersComboBox.setSelectedItem(numBrothersComboBox.getItemAt(0));
        this.numBrothersComboBox.setToolTipText("Number of Brothers");
        numBrothersComboBox.addKeyListener(enterKeyAdapter);

        //Create and setup add detail to list button
        addDetailButton = new JButton("Add Detail to List");
        addDetailButton.addActionListener(e -> addDetailFromInputs());


        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedButton = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit? \nThis list will not be saved.", "Exit Without Saving?",
                        JOptionPane.YES_NO_OPTION);
                if(selectedButton == 0) {
                    Main.displayInitialSetup();
                }
            }
        });

        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveNewDetailList());

        addElementsToInputPanel();

    }


    //Add all elements to the panel of inputs
    private void addElementsToInputPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        //Add title label and input
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 5, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(titleLabel, gbc);
        gbc.gridx = 2;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(5, 0, 5, 5);
        inputPanel.add(titleTextField, gbc);

        //Add rating label and combobox input
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        inputPanel.add(ratingLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(ratingComboBox, gbc);

        //Add num brothers label and combobox input
        gbc.gridx = 2;
        inputPanel.add(numBrothersLabel, gbc);
        gbc.gridx = 3;
        inputPanel.add(numBrothersComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        inputPanel.add(addDetailButton, gbc);


        gbc.gridy = 3;
        JSeparator horizontalSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        inputPanel.add(horizontalSeparator, gbc);

        gbc.gridwidth = 2;
        gbc.gridy = 4;
        gbc.gridx = 0;
        inputPanel.add(cancelButton, gbc);

        gbc.gridx = 2;
        inputPanel.add(saveButton, gbc);

    }

    //Listener method for add detail button
    private void addDetailFromInputs() {
        if(!titleTextField.getText().equals("")) { //Make sure job title was entered
//            listPanel.setVisible(true);
//            panelSeparator.setVisible(true);
            if(placeHolderLabel.isVisible()) {
                placeHolderLabel.setVisible(false);
            }

            String jobTitle = titleTextField.getText();
            int rating = ratingComboBox.getItemAt(ratingComboBox.getSelectedIndex());
            int numBrothers = numBrothersComboBox.getItemAt(numBrothersComboBox.getSelectedIndex());
            Detail newDetail = new Detail(jobTitle, rating, numBrothers, true);

            titleTextField.setText("");
            ratingComboBox.setSelectedItem(ratingComboBox.getItemAt(0));
            numBrothersComboBox.setSelectedItem(numBrothersComboBox.getItemAt(0));

            JLabel newTitleLabel = new JLabel(jobTitle);
            JLabel newRatingLabel = new JLabel(String.valueOf(rating));
            JLabel newNumBrothersLabel = new JLabel(String.valueOf(numBrothers));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = Detail.details.size() + 1;
            listPanel.add(newTitleLabel, gbc);
            gbc.gridx = 1;
            listPanel.add(newRatingLabel, gbc);
            gbc.gridx = 2;
            listPanel.add(newNumBrothersLabel, gbc);
            listPanel.updateUI();
            SwingUtilities.getWindowAncestor(this).pack();
        }
    }

    //Listener method for save button
    private void saveNewDetailList() {
        Detail.updateDetailCsv();
        Main.displayInitialSetup();
    }
}

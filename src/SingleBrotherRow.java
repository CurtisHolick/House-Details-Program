import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class SingleBrotherRow extends JPanel {
    private Brother brother;
    private JCheckBox checkbox;
    private JLabel rankingLabel;
    private JComboBox<Integer> rankingComboBox;
    private JTextField nicknameTextField;

    public static ArrayList<ArrayList<JComponent>> allBrotherRowComponents = null;
    public static ArrayList<SingleBrotherRow> allBrotherRows = new ArrayList<>();

    public SingleBrotherRow(Brother brother) {
        super();
        if(allBrotherRows == null) {
            allBrotherRows = new ArrayList<>();
        }
        if(allBrotherRowComponents == null) {
            allBrotherRowComponents = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                allBrotherRowComponents.add(new ArrayList<>());
            }
        }
        allBrotherRows.add(this);

        this.brother = brother;
        this.setLayout(new BorderLayout());
        this.checkbox = new JCheckBox(this.brother.name);
        this.checkbox.setSelected(this.brother.enabled);
        this.checkbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                brother.setEnabled(checkbox.isSelected());

            }
        });

        this.rankingLabel = new JLabel("Rating:");


        Integer[] rankingOptions = new Integer[Brother.maxRanking];
        for (int i = 0; i < Brother.maxRanking; i++) {
            rankingOptions[i] = i+1;
        }

        this.rankingComboBox = new JComboBox<>(rankingOptions);
        this.rankingComboBox.setSelectedItem(this.brother.ranking);
        this.rankingComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int newRanking = (int)rankingComboBox.getSelectedItem();
                brother.setRanking(newRanking);
            }
        });

        this.nicknameTextField = new JTextField(this.brother.nickname);

        this.nicknameTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                brother.setNickname(nicknameTextField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                brother.setNickname(nicknameTextField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });



        allBrotherRowComponents.get(0).add(checkbox);
        allBrotherRowComponents.get(1).add(rankingLabel);
        allBrotherRowComponents.get(2).add(rankingComboBox);
        allBrotherRowComponents.get(3).add(nicknameTextField);
    }

    public boolean isActive() {
        return this.checkbox.isSelected();
    }
}

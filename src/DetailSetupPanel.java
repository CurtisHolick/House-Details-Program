import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailSetupPanel extends JPanel {
    private JLabel detailCountLabel;
    public static DetailSetupPanel detailSetupPanel;
    public DetailSetupPanel() {
        super();
        detailSetupPanel = this;
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        ArrayList<Detail> details = Detail.details;


        SingleDetailRow.allDetailRows = null;
        //Generate all detail rows & their elements
        SingleDetailRow.allDetailRowComponents = null;
        for (Detail detail : details) {
//            System.out.println(detail.toString());
            SingleDetailRow row = new SingleDetailRow(detail);
        }


        JLabel detailsHeaderLabel = new JLabel(String.format("Details - %s", Detail.detailFileName));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;

        //Create underlined header font for header ("Details")
        Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
        fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        Font headerFont = new Font(detailsHeaderLabel.getFont().getFontName(), Font.BOLD, 15).deriveFont(fontAttributes);;
        detailsHeaderLabel.setFont(headerFont);
        gbc.insets = new Insets(10, 10, 5, 10);

        this.add(detailsHeaderLabel, gbc);

        gbc.weightx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JSeparator horizontalSeparator = new JSeparator(SwingConstants.HORIZONTAL);

        gbc.insets = new Insets(5, 0, 5, 0);
        this.add(horizontalSeparator, gbc);

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;


        gbc.insets = new Insets(0, 10, 0, 10);
        //Add all detail components
        ArrayList<ArrayList<JComponent>> allDetailRows = SingleDetailRow.allDetailRowComponents;

        for (int j = 0; j < allDetailRows.size(); j++) { //loop through the 5 different element lists
            ArrayList<JComponent> elements = allDetailRows.get(j);
            gbc.gridx = j;


            for (int i = 0; i < elements.size(); i++) {
                JComponent element = elements.get(i);
                gbc.gridy = i+2;
                gbc.anchor = GridBagConstraints.WEST;
                this.add(element, gbc);
            }
        }

        gbc.gridx = 0;
        gbc.gridy = details.size()+2;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        String labelText = String.format("%d spaces across all details", getTotalActiveDetails());
        detailCountLabel = new JLabel(labelText, SwingConstants.CENTER);

        this.add(detailCountLabel, gbc);

        JButton addDetailButton = new JButton("Add New Detail");
        gbc.gridy += 1+2;
        this.add(addDetailButton, gbc);


        addDetailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddDetailDialog addDetailDialog = new AddDetailDialog();
                addDetailDialog.pack();
                addDetailDialog.setVisible(true);
            }
        });

        for(JComponent component : SingleDetailRow.allDetailRowComponents.get(0)) {
            JCheckBox checkbox = (JCheckBox)component;
            checkbox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateDetailCountLabel();

                }
            });
        }

        for(JComponent component : SingleDetailRow.allDetailRowComponents.get(4)) {
            JComboBox<Integer> combobox = (JComboBox<Integer>) component;
            combobox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    updateDetailCountLabel();
                }
            });
        }
    }


    public void updateDetailCountLabel() {
        String labelText = String.format("%d spaces across all details", getTotalActiveDetails());
        this.detailCountLabel.setText(labelText);
    }

    public int getTotalActiveDetails() {
        int sum = 0;
        for(SingleDetailRow row: SingleDetailRow.allDetailRows) {
            if(row.isActive()) {
                sum += row.getNumBrothers();
            }
        }
        return sum;
    }

    public void deleteRow(String detailTitle) {

        ArrayList<ArrayList<JComponent>> allDetailRows = SingleDetailRow.allDetailRowComponents;
        ArrayList<JComponent> checkBoxes = allDetailRows.get(0);
        for (int i = 0; i < allDetailRows.get(0).size(); i++) {
            JCheckBox checkbox = (JCheckBox) checkBoxes.get(i);
            if(checkbox.getText().equals(detailTitle)) {
                for(ArrayList<JComponent> componentList : allDetailRows) {
                    this.remove(componentList.get(i));
                }
            }
        }
        this.revalidate();
        this.repaint();

    }

}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BrotherSetupPanel extends JPanel {
    private JButton assignDetailButton;
    private JLabel totalBrotherCountLabel;

    public BrotherSetupPanel() {
        super();
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        SingleBrotherRow.allBrotherRowComponents = null;
        SingleBrotherRow.allBrotherRows = null;
        //Generate all brother rows & their elements
        for(Brother brother : Brother.brothers) {
            SingleBrotherRow singleBrotherRow = new SingleBrotherRow(brother);
        }

        JLabel brothersHeaderLabel = new JLabel("Brothers");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;

        //Create underlined header font for header ("Brothers")
        Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
        fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        Font headerFont = new Font(brothersHeaderLabel.getFont().getFontName(), Font.BOLD, 15).deriveFont(fontAttributes);
        brothersHeaderLabel.setFont(headerFont);
        gbc.insets = new Insets(10, 10, 5, 10);

        this.add(brothersHeaderLabel, gbc);

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
        ArrayList<ArrayList<JComponent>> allBrotherRows = SingleBrotherRow.allBrotherRowComponents;
        for (int j = 0; j < allBrotherRows.size(); j++) { //loop through the 5 different element lists
            ArrayList<JComponent> elements = allBrotherRows.get(j);
            gbc.gridx = j;
            for (int i = 0; i < elements.size(); i++) {
                JComponent element = elements.get(i);
                gbc.gridy = i+2;
                if(j == 2) {
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.anchor = GridBagConstraints.EAST;
                } else {
                    gbc.anchor = GridBagConstraints.WEST;
                }
                this.add(element, gbc);
            }
        }

        gbc.gridy = allBrotherRows.get(0).size()+2;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String brotherCountText = String.format("%d Brothers Activated", getNumActiveBrothers());
        this.totalBrotherCountLabel = new JLabel(brotherCountText, SwingConstants.CENTER);
        this.add(totalBrotherCountLabel, gbc);


        this.assignDetailButton = new JButton("Assign Detail");
        gbc.gridy = allBrotherRows.get(0).size()+1+2;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        this.add(this.assignDetailButton, gbc);
        JobAssignmentDialog jobAssignmentDialog = new JobAssignmentDialog();
        //Add listener to manual detail assignment button
        this.assignDetailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                jobAssignmentDialog.pack();
                jobAssignmentDialog.setVisible(true);
            }
        });
        //Add listeners to checkboxes
        for(JComponent component : SingleBrotherRow.allBrotherRowComponents.get(0)) {
            JCheckBox checkbox = (JCheckBox)component;
            checkbox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateTotalBrotherCountLabel();

                }
            });
        }

    }

    public int getNumActiveBrothers() {
        int sum = 0;
        for(SingleBrotherRow row : SingleBrotherRow.allBrotherRows) {
            if(row.isActive()) {
                sum += 1;
            }
        }
        return sum;
    }

    public void updateTotalBrotherCountLabel() {
        String brotherCountText = String.format("%d Brothers Activated", getNumActiveBrothers());
        this.totalBrotherCountLabel.setText(brotherCountText);
    }
}

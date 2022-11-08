import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class MatchedDetailPanel extends JPanel {
    public MatchedDetailPanel(ArrayList<MatchedDetail> matchedDetails) {
        super();
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        //Add mouse listener to go back to main setup on click
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Main.displayMainSetup();
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

        gbc.insets = new Insets(3, 3, 3, 15);
        JLabel detailHeader = new JLabel("Detail", SwingConstants.CENTER);

        Font headerFont = new Font(detailHeader.getFont().getFontName(), Font.BOLD, 20);

        detailHeader.setFont(headerFont);
        this.add(detailHeader, gbc);

        gbc.gridx = 1;

        gbc.gridwidth = 8;
        JLabel brotherHeader = new JLabel("Brothers", SwingConstants.CENTER);
        brotherHeader.setFont(headerFont);
        this.add(brotherHeader, gbc);

        gbc.gridwidth = 1;

        gbc.gridy = 1;


        int mostBrothersOnDetail = MatchedDetail.getMostBrothersOnDetail(matchedDetails);

        for (int i = 0; i < matchedDetails.size(); i++) { //add each row to panel
            MatchedDetail matchedDetail = matchedDetails.get(i);

            gbc.gridx= 0;
            gbc.gridwidth = (Detail.maxNumBrothersOnDetail+1)*2;
            gbc.insets = new Insets(3, 3, 3, 3);
            //Add horizontal separator
            JSeparator horizontalSeparator = new JSeparator(SwingConstants.HORIZONTAL);
            this.add(horizontalSeparator, gbc);

            //Add detail name label
            gbc.gridy = gbc.gridy + 1;
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.insets = new Insets(3, 15, 3, 15);
            JLabel detailLabel = new JLabel(matchedDetail.detail.jobTitle);
            this.add(detailLabel, gbc);

            gbc.insets = new Insets(3, 3, 3, 15);

            //Separator between detail and brothers
            JSeparator detailVerticalSeparator = new JSeparator(SwingConstants.VERTICAL);
            gbc.gridx += 1;
            Dimension separatorDimensions = detailVerticalSeparator.getPreferredSize();
            separatorDimensions.height = detailLabel.getPreferredSize().height;
            detailVerticalSeparator.setPreferredSize(separatorDimensions);
            this.add(detailVerticalSeparator, gbc);

            ArrayList<Brother> brothers = matchedDetail.brothers;
            for (int j = 0; j < brothers.size(); j++) { //add each brother to row
                Brother brother = brothers.get(j);

                String labelText = String.format("%s - %s", brother.name, brother.nickname);
                if(brother.nickname.split("")[0].equals("&")) {
                    labelText = String.format("%s %s", brother.name, brother.nickname);
                }

                JLabel brotherLabel = new JLabel(labelText);

                gbc.gridx = gbc.gridx + 1;
                this.add(brotherLabel, gbc);
                if(j != brothers.size()-1) {
                    JSeparator brotherVerticalSeparator = new JSeparator(SwingConstants.VERTICAL);
                    gbc.gridx += 1;

                    separatorDimensions.height = brotherLabel.getPreferredSize().height;
                    brotherVerticalSeparator.setPreferredSize(separatorDimensions);
                    this.add(brotherVerticalSeparator, gbc);
                } else if(j < mostBrothersOnDetail) {
                    for (int k = j; k < mostBrothersOnDetail-1; k++) {
                        JSeparator brotherVerticalSeparator = new JSeparator(SwingConstants.VERTICAL);
                        gbc.gridx += 1;
                        separatorDimensions.height = brotherLabel.getPreferredSize().height;
                        brotherVerticalSeparator.setPreferredSize(separatorDimensions);
                        this.add(brotherVerticalSeparator, gbc);
                        gbc.gridx += 1;
                    }
                }
            }
            gbc.gridy = gbc.gridy + 1;
        }

        //empty label at bottom to add spacing
        JLabel spacerLabel = new JLabel();
        spacerLabel.setPreferredSize(new Dimension(spacerLabel.getPreferredSize().width, spacerLabel.getPreferredSize().height/2));
        this.add(spacerLabel, gbc);
    }
}

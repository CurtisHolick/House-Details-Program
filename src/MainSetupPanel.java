import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainSetupPanel extends JPanel {
    private JButton matchBrotherButton, backButton;
    private JPanel setupPanelsContainer;
    private DetailSetupPanel detailSetupPanel;
    private static MainSetupPanel mainSetupPanel;
    public MainSetupPanel(String detailFilepath) {
        super();
        mainSetupPanel = this;
        Brother.loadBrotherhood();
        Detail.loadDetails(detailFilepath, true);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        setupPanelsContainer = new JPanel();
        setupPanelsContainer.setLayout(new BoxLayout(setupPanelsContainer, BoxLayout.X_AXIS));

        BrotherSetupPanel brotherSetupPanel = new BrotherSetupPanel();

        setupPanelsContainer.add(brotherSetupPanel);

//        Component rigidArea = Box.createRigidArea(new Dimension(10, 10));
//        setupPanelsContainer.add(rigidArea);

        JSeparator verticalLine = new JSeparator(SwingConstants.VERTICAL);
//        verticalLine.setPreferredSize(new Dimension(10, verticalLine.getPreferredSize().height));
        setupPanelsContainer.add(verticalLine);

        detailSetupPanel = new DetailSetupPanel();
        setupPanelsContainer.add(detailSetupPanel);


        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
//        gbc.gridy = 1;
        this.add(setupPanelsContainer, gbc);

        this.matchBrotherButton = new JButton("Match Brothers & Details");

        gbc.gridy = 1;
//        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        matchBrotherButton.addActionListener(e -> {
            if(brotherSetupPanel.getNumActiveBrothers() == detailSetupPanel.getTotalActiveDetails()) {
                Main.displayMatches();
            } else {
                JFrame popupFrame = new JFrame("Mismatch between number of brothers and jobs");
                JOptionPane.showMessageDialog(popupFrame, "Number of activated brothers must be the same as the number of active details");
            }
        });

        gbc.insets = new Insets(10, 10, 5, 10);
        matchBrotherButton.setPreferredSize(new Dimension(matchBrotherButton.getPreferredSize().width, matchBrotherButton.getPreferredSize().height*2));
        matchBrotherButton.setFont(new Font(matchBrotherButton.getFont().getFontName(), Font.PLAIN, 15));
        this.add(matchBrotherButton, gbc);

        backButton = new JButton("Back to Initial Setup");
        backButton.addActionListener(e -> Main.displayInitialSetup());
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 10, 10);

        this.add(backButton, gbc);
    }

    public void reload() {
        setupPanelsContainer.remove(detailSetupPanel);

        setupPanelsContainer.updateUI();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        detailSetupPanel = new DetailSetupPanel();
        setupPanelsContainer.add(detailSetupPanel);
        setupPanelsContainer.updateUI();

    }


}

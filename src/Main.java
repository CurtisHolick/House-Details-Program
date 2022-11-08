import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static JFrame frame;
    private static MainSetupPanel mainSetupPanel;
    public static void main(String[] args) {

//        System.out.println(Arrays.toString(UIManager.getInstalledLookAndFeels()));

        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
//            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            UIManager.put("control", new Color(233, 238, 242));
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame = new JFrame("Initial Setup");

        displayInitialSetup();
        frame.setVisible(true);
        frame.pack();

        frame.setLocationRelativeTo(null);

    }

    public static void displayMainSetup(String detailFilepath) {
        mainSetupPanel = new MainSetupPanel(detailFilepath);
        frame.setTitle("Main Setup");
        frame.setContentPane(mainSetupPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public static void displayMainSetup() {
        displayMainSetup(Detail.detailFileName);
    }

    public static void updateMainSetupPanel() {
//        Main.mainSetupPanel = new MainSetupPanel(Detail.detailFileName.split("/")[1].split("\\.")[0]);
        Main.mainSetupPanel = new MainSetupPanel(Detail.detailFileName);
        frame.setTitle("Main Setup");
        frame.setContentPane(Main.mainSetupPanel);
        frame.pack();
    }

    public static void displayInitialSetup() {
        InitialSetupPanel initialSetupPanel = new InitialSetupPanel();
        frame.setTitle("Initial Setup");
        frame.setContentPane(initialSetupPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public static void displayMatches() {
        ArrayList<MatchedDetail> matchedDetails = MatchedDetail.matchDetails(Brother.getEnabledBrothers(), Detail.getEnabledDetails());
        MatchedDetailPanel matchedDetailPanel = new MatchedDetailPanel(matchedDetails);
        frame.setTitle("The work needs to be done");
        frame.setContentPane(matchedDetailPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public static void displayDetailListCreation() {
        String newDetailFileName = (String)JOptionPane.showInputDialog(null, "Enter a name for the new details list", "Create New Details List", JOptionPane.PLAIN_MESSAGE);
        System.out.println(newDetailFileName);
        if(newDetailFileName != null) {
            if (!newDetailFileName.equals("")) {
                DetailListCreationPanel detailListCreationPanel = new DetailListCreationPanel(newDetailFileName);
                frame.setTitle(String.format("%s - New Details", newDetailFileName));
                frame.setContentPane(detailListCreationPanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a name for the details list", "Error with list name", JOptionPane.WARNING_MESSAGE);
                displayDetailListCreation();
            }
        }
    }
}

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class InitialSetupPanel extends JPanel {
    public InitialSetupPanel() {
        super();

        String[] detailOptions = getFilesFromDir("DetailFiles");

        //Add border made of empty border for inner spacing, etched border, and empty border for outer spacing
        this.setLayout(new GridBagLayout());
        Border frameBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        frameBorder = new CompoundBorder(frameBorder, BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        frameBorder = new CompoundBorder(frameBorder, BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.setBorder(frameBorder);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        JLabel headerLabel = new JLabel("Select a list of details, or create a new one");
        Font headerFont = new Font(headerLabel.getFont().getFontName(), Font.BOLD, headerLabel.getFont().getSize()+2);
        headerLabel.setFont(headerFont);
        this.add(headerLabel, gbc);

        //Add dropdown for existing lists
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        JComboBox<String> detailListComboBox = new JComboBox<>(detailOptions);
        ((JLabel)detailListComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        this.add(detailListComboBox, gbc);

        //Add select list button
        gbc.gridx = 1;
        JButton selectDetailListButton = new JButton("Select Details List");
        this.add(selectDetailListButton, gbc);

        //Add create new detail list button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton newDetailListButton = new JButton("Create New Details List");
        newDetailListButton.setBorderPainted(false);
        this.add(newDetailListButton, gbc);

        JLabel creditsLabel = new JLabel("Created by Curtis Holick");
        Font creditsFont = new Font(creditsLabel.getFont().getFontName(), Font.ITALIC, creditsLabel.getFont().getSize());
        creditsLabel.setFont(creditsFont);
        gbc.gridy = 3;
        this.add(creditsLabel, gbc);


        //Add select list button listener
        selectDetailListButton.addActionListener(e -> {
            String detailFilepath = (String)detailListComboBox.getSelectedItem();
            Main.displayMainSetup(detailFilepath);
        });

        //Add new list button listener
        newDetailListButton.addActionListener(e -> Main.displayDetailListCreation());

        //Key adapter to handle when enter is pressed
        KeyAdapter enterKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    String detailFilepath = (String)detailListComboBox.getSelectedItem();
                    Main.displayMainSetup(detailFilepath);
                }
            }
        };
        detailListComboBox.addKeyListener(enterKeyAdapter);
    }

    public static String[] getFilesFromDir(String directoryPath) {
        File folderWithFiles = new File(directoryPath);
        File[] filesInDir = folderWithFiles.listFiles();

        ArrayList<String> brotherFileNames = new ArrayList<>();
        if (filesInDir != null) {
            for(File file : filesInDir) {
                if(file.isFile()) {
                    brotherFileNames.add(file.getName().replace(".csv", ""));
                }
            }
        } else {
            return new String[]{"No files found"};
        }

        return brotherFileNames.toArray(new String[0]);
    }
}

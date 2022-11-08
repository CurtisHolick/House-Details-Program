import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Detail {
    public String jobTitle;
    public int rating, numBrothers;
    public boolean enabled;
    public ArrayList<Brother> assignedBrothers;

    public static String detailFileName;

    public static ArrayList<Detail> details = new ArrayList<>();

    public static final int maxRating = 5;
    public static final int maxNumBrothersOnDetail = 4;

    public Detail(String jobTitle, int rating, int numBrothers, boolean enabled) {
        this.jobTitle = jobTitle;
        this.rating = rating;
        this.numBrothers = numBrothers;
        this.enabled = enabled;

        this.assignedBrothers = new ArrayList<>();
        details.add(this);
    }

    public static void loadDetails(String detailFileName) {
        loadDetails(detailFileName, false);
    }

    public static void loadDetails(String detailFileName, boolean forceUpdate) {
        if(details.size() == 0 || forceUpdate) {
            Detail.details = new ArrayList<>();
            Detail.detailFileName = detailFileName;
//            String filePath = "DetailFiles/" + detailFileName + ".csv";
            File detailSaveFile = new File(getDetailFilePath());
            try {
                Scanner inputScanner = new Scanner(detailSaveFile);
                while (inputScanner.hasNextLine()) {
                    String[] lineData = inputScanner.nextLine().split(",");
                    new Detail(lineData[0], Integer.parseInt(lineData[1]), Integer.parseInt(lineData[2]), Boolean.parseBoolean(lineData[3]));
                }
                inputScanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void reloadDetails() {
        Detail.details = new ArrayList<>();

        File detailSaveFile = new File(getDetailFilePath());
        try {
            Scanner inputScanner = new Scanner(detailSaveFile);
            while (inputScanner.hasNextLine()) {
                String[] lineData = inputScanner.nextLine().split(",");
                new Detail(lineData[0], Integer.parseInt(lineData[1]), Integer.parseInt(lineData[2]), Boolean.parseBoolean(lineData[3]));
            }
            inputScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return jobTitle;
//        return "Detail: " + jobTitle + ", rating=" + rating + ", numBrothers=" + numBrothers;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        updateDetailCsv();
    }

    public void setRating(int rating) {
        this.rating = rating;
        updateDetailCsv();
    }

    public void setNumBrothers(int numBrothers) {
        this.numBrothers = numBrothers;
        updateDetailCsv();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        updateDetailCsv();
    }

    public static void updateDetailCsv() {
//        if(Detail.details.size() == 0){
//            Detail.loadDetails();
//        }
        try {
//            String filePath = "DetailFiles/" + Detail.detailFileName + ".csv";
            FileWriter detailFileWriter = new FileWriter(getDetailFilePath());
            for(Detail detail: Detail.details) {
                String lineData = String.format("%s,%d,%d,%b\n", detail.jobTitle, detail.rating, detail.numBrothers, detail.enabled);
                System.out.print(lineData);
                detailFileWriter.append(lineData);
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~");
            detailFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Detail> getEnabledDetails() {
        ArrayList<Detail> enabledDetails = new ArrayList<>();
        for(Detail detail : details) {
            if(detail.enabled) {
                enabledDetails.add(detail);
            }
        }
        return enabledDetails;
    }

    public void assignBrother(Brother brother) {
        this.assignedBrothers.add(brother);
    }

    public boolean hasAssignedBrothers() {
        return this.assignedBrothers.size() > 0;
    }

    private static String getDetailFilePath() {
        return "DetailFiles/" + Detail.detailFileName + ".csv";
    }
}

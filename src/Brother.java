import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Brother {
    public String name;
    public int ranking;
    public String nickname;
//    public Detail detail;
    public boolean enabled;
//    public Detail assignedDetail;

    public static ArrayList<Brother> brothers = new ArrayList<>();

    public static final int maxRanking = 5;

    public Brother(String name, String nickname, int ranking, boolean enabled) {
        this.name = name;
        this.nickname = nickname;
        this.ranking = ranking;
        this.enabled = enabled;
//        this.assignedDetail = null;
        brothers.add(this);
    }


    public static void loadBrotherhood() {
        loadBrotherhood(false);
    }

    public static void loadBrotherhood(boolean forceUpdate) {
        if(brothers.size() == 0 || forceUpdate) {
            File brotherSaveFile = new File("BrotherFiles/Brothers.csv");
            try {
                Scanner inputScanner = new Scanner(brotherSaveFile);
                while (inputScanner.hasNextLine()) {
                    String[] lineData = inputScanner.nextLine().split(",");
                    new Brother(lineData[0], lineData[1], Integer.parseInt(lineData[2]), Boolean.parseBoolean(lineData[3]));
                }

                inputScanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

//    public void setDetail(Detail detail){
//        this.detail = detail;
//    }

    @Override
    public String toString() {
        return this.name;
//        return "Brother: " + name + " - " + nickname + ", " + ranking;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        updateBrotherCsv();
    }

    public void setName(String name) {
        this.name = name;
        updateBrotherCsv();
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
        updateBrotherCsv();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        updateBrotherCsv();
    }

    public static void updateBrotherCsv() {
        if(Brother.brothers.size() == 0){
            Brother.loadBrotherhood();
        }
        try {
            FileWriter brotherFileWriter = new FileWriter("BrotherFiles/Brothers.csv");
            for(Brother brother: Brother.brothers) {
                String lineData = String.format("%s,%s,%d,%b\n", brother.name, brother.nickname, brother.ranking, brother.enabled);
                brotherFileWriter.append(lineData);
            }
            brotherFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Brother> getEnabledBrothers() {
        ArrayList<Brother> enabledBrothers = new ArrayList<>();
        for(Brother brother : brothers) {
            if(brother.enabled) {
                enabledBrothers.add(brother);
            }
        }
        return enabledBrothers;
    }

//    public void setAssignedDetail(Detail detail) {
//        this.assignedDetail = detail;
//    }
//
//    public boolean hasAssignedDetail() {
//        return this.assignedDetail != null;
//    }


}

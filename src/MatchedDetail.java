import java.util.ArrayList;
import java.util.Comparator;

public class MatchedDetail {
    public ArrayList<Brother> brothers;
    public Detail detail;
    public MatchedDetail(ArrayList<Brother> brothers, Detail detail) {
        this.brothers = brothers;
        this.detail = detail;
    }
    public Brother getBrother(int index) {
        return this.brothers.get(index);
    }

    public int getNumBrothers() {
        return this.brothers.size();
    }

    public String toString() {
        String output = detail.jobTitle;
        output += " - ";
        for(Brother brother : brothers) {
            output += brother.name;
            output += ", ";
        }
        return output;

    }

    public static ArrayList<MatchedDetail> matchDetails(ArrayList<Brother> brothers, ArrayList<Detail> details) {
//        System.out.println(brothers.toString());
//        System.out.println(details.toString());

        ArrayList<MatchedDetail> matchedDetails = new ArrayList<>();


        BrotherBag bag = new BrotherBag(brothers);
        details.sort(new Comparator<Detail>() {
            @Override
            public int compare(Detail o1, Detail o2) {
                return o2.rating - o1.rating;
            }
        });


        for(Detail detail : details) {
            if(detail.hasAssignedBrothers()) {
                for(Brother brother: detail.assignedBrothers) {
                    bag.removeBrother(brother);
//                    System.out.println(bag);
                }
            }
        }

        for(Detail detail : details) {
            ArrayList<Brother> brothersOnDetail = new ArrayList<>();
            if(detail.hasAssignedBrothers()) {
                brothersOnDetail.addAll(detail.assignedBrothers);
            }
            int numBrothersToAdd = detail.numBrothers - detail.assignedBrothers.size();
            for (int i = 0; i < numBrothersToAdd; i++) {
                Brother pulledBrother = bag.pullBrother();
                System.out.println("Pulled " + pulledBrother.toString() + " for " + detail.toString());
                brothersOnDetail.add(pulledBrother);
            }
            MatchedDetail matchedDetail = new MatchedDetail(brothersOnDetail, detail);
            matchedDetails.add(matchedDetail);
        }

        return matchedDetails;
    }

    public static int getMostBrothersOnDetail(ArrayList<MatchedDetail> matchedDetails) {
        int mostBrothers = 0;
        for(MatchedDetail detail : matchedDetails) {
            if(detail.getNumBrothers() > mostBrothers) {
                mostBrothers = detail.getNumBrothers();
            }
        }
        return mostBrothers;
    }

}

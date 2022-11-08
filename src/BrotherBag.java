import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

public class BrotherBag {
    public ArrayList<Brother> bag;
    public ArrayList<Brother> remainingBrothers;
    public BrotherBag(ArrayList<Brother> brothers) {
        this.remainingBrothers = brothers;
        this.bag = generateBag();
    }

    private ArrayList<Brother> generateBag() {
        ArrayList<Brother> generatedBag = new ArrayList<>();
        for(Brother brother : remainingBrothers) {
            for (int i = 0; i < brother.ranking; i++) {
                generatedBag.add(brother);
            }
        }
        generatedBag.sort(new Comparator<Brother>() {
            @Override
            public int compare(Brother o1, Brother o2) {
                return o1.ranking - o2.ranking;
            }
        });
        return generatedBag;
    }

    public Brother pullBrother() {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, bag.size());
        Brother pulledBrother = bag.get(randomIndex);
        remainingBrothers.remove(pulledBrother);
        this.bag = generateBag();
        return pulledBrother;
    }

    public void removeBrother(Brother brotherToRemove) {
        ArrayList<Brother> newBag = new ArrayList<>();
        for(Brother brother: bag) {
            if(!brother.equals(brotherToRemove)) {
                newBag.add(brother);
            } else {
                System.out.println("Not including " + brother.toString());
                remainingBrothers.remove(brother);
            }
        }
        this.bag = newBag;
    }

    @Override
    public String toString() {
        String output = "";
        for(Brother brother : this.bag) {
            output += brother.name;
            output += " - ";
            output += brother.ranking;
            output += "\n";
        }
        return output;
    }
}

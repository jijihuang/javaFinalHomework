package sort;
import creature.Huluwa;

public class HuluwaRankSort implements Sort {
    public void sort(Huluwa[] brothers) {
        for (int i=0;i<brothers.length-1;i++) {
            for (int j = 0; j < brothers.length - i - 1; j++) {
                if (brothers[j].getRank() > brothers[j + 1].getRank()) {
                    //brothers[j].tellChange(j,j+1);
                    //queue[j + 1].tellChange(j+1,j);
                    Huluwa temp = brothers[j];
                    brothers[j] = brothers[j + 1];
                    brothers[j + 1] = temp;
                }
            }
        }
    }
}

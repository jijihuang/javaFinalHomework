import creature.Huluwa;
import org.junit.Test;
import sort.HuluwaRankSort;

import static org.junit.Assert.assertEquals;

public class HuluwaRankSortTest {

    @Test
    public void sort() {
        Huluwa[] test = new Huluwa[7];
        test[0] = new Huluwa(6);
        test[1] = new Huluwa(2);
        test[2] = new Huluwa(3);
        test[3] = new Huluwa(7);
        test[4] = new Huluwa(1);
        test[5] = new Huluwa(4);
        test[6] = new Huluwa(5);
        Huluwa[] res = new Huluwa[7];
        for (int i = 1; i <= 7; i++) {
            res[i-1] = new Huluwa(i);
        }
        new HuluwaRankSort().sort(test);
        for (int i = 0; i < 7; i++) {
            assertEquals(res[i].getRank(), test[i].getRank());
        }
    }
}
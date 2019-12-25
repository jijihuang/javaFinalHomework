import creature.Creature;
import org.junit.Test;
import space.Space;

import static org.junit.Assert.assertEquals;

public class SpaceTest {

    @Test
    public void isExceedTheBattleField() {
        Space testSpace= new Space(15,15);
        assertEquals(false,testSpace.isExceedTheBattleField(1,2));
        assertEquals(true,testSpace.isExceedTheBattleField(100,100));
    }

    @Test
    public void isTheCellEmpty() {
        Space testSpace= new Space(15,15);
        testSpace.setTheCreatureOnTheCell(1,2,new Creature());
        assertEquals(true,testSpace.isTheCellEmpty(2,2));
        assertEquals(false,testSpace.isTheCellEmpty(1,2));
    }
}
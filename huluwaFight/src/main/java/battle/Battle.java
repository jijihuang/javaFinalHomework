package battle;

import annotation.AuthorAnno;
import creature.HuluwaTeam;
import creature.SnakeTeam;
import space.*;

@AuthorAnno
public class Battle {
    public Space battleField;
    public HuluwaTeam huluwaTeam;
    public SnakeTeam snakeTeam;
    public static final int huluwaTeamWin=1;
    public static final int snakeTeamWin=2;
    public static final int gameStart=3;
    public static final int gameOver=4;
    private int battleStatus;
    //private List<Creature> creatureList;

    public Battle()throws Exception{
        snakeTeam=new SnakeTeam();
        huluwaTeam=new HuluwaTeam();

        battleField=new Space(15,12);
        battleStatus=gameOver;
    }

    public void setBattleStatus(int battleStatus) {
        this.battleStatus = battleStatus;
    }

    public int getBattleStatus() {
        return battleStatus;
    }

    public Space getBattleField() {
        return battleField;
    }

    public HuluwaTeam getHuluwaTeam() {
        return huluwaTeam;
    }

    public SnakeTeam getSnakeTeam() {
        return snakeTeam;
    }
}

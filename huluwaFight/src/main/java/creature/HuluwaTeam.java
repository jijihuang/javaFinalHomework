package creature;

import annotation.AuthorAnno;
import javafx.scene.image.Image;
import queuepattern.HuluwaTeamQueuePattern;
import sort.HuluwaRankSort;
import space.*;

@AuthorAnno
public class HuluwaTeam implements HuluwaTeamQueuePattern {
    private Huluwa brothers[];
    private Grandpa cheerleader;
    public HuluwaTeam(){
        brothers=new Huluwa[7];
        int i=0;
        /*for(Huluwa huluwa: brothers) {
            huluwa.setNumber(i);
            i++;
        }*/
        cheerleader=new Grandpa();
        cheerleader.setNumber(7);
        randomQueue();
    }

    public boolean isAllDead(){
        for (Huluwa c:brothers){
            if (c.isAlive()){
                return false;
            }
        }
        if(cheerleader.isAlive()){
            return false;
        }
        return true;
    }
    public Grandpa getCheerleader() {
        return cheerleader;
    }
    public Huluwa getTheSpecificHuluwa(int i){
        return brothers[i];
    }
    public Huluwa[] getBrothers(){
        return brothers;
    }

    public Creature getTheBrother(int i){
        if (i<7){
            return brothers[i];
        }
        else {
            return cheerleader;
        }
    }

    public void randomQueue(){
        //brothers=new Huluwa[7];
        int rank;
        String color,name,picturePath;
        int number;
        rank=0;
        int lifeValue=0;
        int attackValue=0;
        color="";
        name="";
        picturePath="";
        Image picture;
        picture=new Image("image/");
        boolean flag;
        for (int i=0;i<brothers.length;i++){
            flag=false;
            while(flag==false) {
                int rand = (int) Math.round(Math.random() * 6 + 1);
                for (int j = 0; j < i; j++) {
                    if (brothers[j].getRank() == rand) {
                        flag = true;
                        break;
                    }
                }
                if (flag==true)flag=false;
                else{
                    switch(rand) {
                        case 1:
                            rank = 1;
                            color = "红色";
                            name = "老大";
                            picture=new Image("image/Redwa.png");
                            picturePath = "image/Redwa.png";
                            lifeValue=15;
                            attackValue=5;
                            break;
                        case 2:
                            rank = 2;
                            color = "橙色";
                            name = "老二";
                            picture=new Image("image/Orangewa.png");
                            picturePath = "/image/Orangewa.png";
                            lifeValue=10;
                            attackValue=2;
                            break;
                        case 3:
                            rank = 3;
                            color = "黄色";
                            name = "老三";
                            picture=new Image("image/Yellowwa.png");
                            picturePath = "/image/Yellowwa.png";
                            lifeValue=20;
                            attackValue=3;
                            break;
                        case 4:
                            rank= 4;
                            color = "绿色";
                            name = "老四";
                            picture=new Image("image/Greenwa.png");
                            picturePath = "/image/Greenwa.png";
                            lifeValue=12;
                            attackValue=4;
                            break;
                        case 5:
                            rank = 5;
                            color = "青色";
                            name = "老五";
                            picture=new Image("image/Bluewa.png");
                            picturePath = "/image/Bluewa.png";
                            lifeValue=12;
                            attackValue=4;
                            break;
                        case 6:
                            rank = 6;
                            color = "蓝色";
                            name = "老六";
                            picture=new Image("image/Cyanwa.png");
                            picturePath = "/image/Cyanwa.png";
                            lifeValue=15;
                            attackValue=2;
                            break;
                        case 7:
                            rank = 7;
                            color = "紫色";
                            name = "老七";
                            picture=new Image("image/Purplewa.png");
                            picturePath = "/image/Purplewa.png";
                            lifeValue=10;
                            attackValue=1;
                            break;
                        default:
                            System.out.println("wrong huluwa number!");
                            break;
                    }
                    brothers[i]=new Huluwa(rank,color,name,picture,picturePath,lifeValue,attackValue);
                    brothers[i].setNumber(rand-1);
                    brothers[i].setRoleRunning(true);
                    flag=true;
                }
            }
        }
    }
    public void bubbleSort(){
        //System.out.println();
        /*for (int i=0;i<brothers.length-1;i++) {
            for (int j = 0; j < brothers.length - i - 1; j++) {
                if (brothers[j].getRank() > brothers[j + 1].getRank()) {
                    //brothers[j].tellChange(j,j+1);
                    //queue[j + 1].tellChange(j+1,j);
                    Huluwa temp = brothers[j];
                    brothers[j] = brothers[j + 1];
                    brothers[j + 1] = temp;
                }
            }
        }*/
        new HuluwaRankSort().sort(brothers);
    }

    public void generateTheCheerPattern(Space battleground, int x, int y){
        cheerleader.moveFrom(battleground);
        cheerleader.moveTo(battleground,x,y);
        cheerleader.setRoleRunning(true);
    }

    public void generateChangshePattern(Space battleground, int x, int y){
        for(int i=0;i<7;i++) {
            brothers[i].setRoleRunning(true);
        }
        for (int i=0;i<brothers.length;i++){
            brothers[i].moveFrom(battleground);
        }
        randomQueue();
        if (x==0){
            bubbleSort();
        }
        if (!battleground.isExceedTheBattleField(x,y)&&!battleground.isExceedTheBattleField(x,y+5)){
            brothers[0].moveTo(battleground, x, y);
            for (int i=1;i<brothers.length;i++){
                y += 1;
                brothers[i].moveTo(battleground, x, y);
            }
        }
        else
        {
            System.out.println("changshe pattern move wrong!!!");
        }
    }

    public void generateHeyiPattern(Space battleground,  int x, int y){
        new HuluwaRankSort().sort(brothers);
        if (!cheerleader.isAlive()) {
            for (int i = 0; i < brothers.length; i++) {
                brothers[i].moveFrom(battleground);
            }
            if (!battleground.isExceedTheBattleField(x, y) && !battleground.isExceedTheBattleField(x + 3, y + 3)) {
                brothers[0].moveTo(battleground, x, y);
                for (int i = 1; i < 4; i++) {
                    x += 1;
                    y += 1;
                    brothers[i].moveTo(battleground, x, y);
                    brothers[i].setRoleRunning(true);
                }
                for (int i = 4; i < 7; i++) {
                    x += 1;
                    y -= 1;
                    brothers[i].moveTo(battleground, x, y);
                    brothers[i].setRoleRunning(true);
                }
            } else {
                System.out.println("heyi pattern move wrong!!!");
            }
        }
        else{
            for (int i = 0; i < brothers.length; i++) {
                brothers[i].moveFrom(battleground);
            }
            cheerleader.moveFrom(battleground);
            if (!battleground.isExceedTheBattleField(x, y) && !battleground.isExceedTheBattleField(x + 4, y + 3)) {
                brothers[0].moveTo(battleground, x, y);
                for (int i = 1; i < 4; i++) {
                    x += 1;
                    y += 1;
                    brothers[i].moveTo(battleground, x, y);
                    brothers[i].setRoleRunning(true);
                }
                x+=1;
                cheerleader.moveTo(battleground,x,y);
                for (int i = 4; i < 7; i++) {
                    x += 1;
                    y -= 1;
                    brothers[i].moveTo(battleground, x, y);
                    brothers[i].setRoleRunning(true);
                }
            } else {
                System.out.println("heyi pattern move wrong!!!");
            }
        }
    }

    public void generateYanxingPattern(Space battleground,  int x, int y){
        new HuluwaRankSort().sort(brothers);
        if (!cheerleader.isAlive()) {
            for (int i = 0; i < brothers.length; i++) {
                brothers[i].moveFrom(battleground);
            }
            if (!battleground.isExceedTheBattleField(x, y) && !battleground.isExceedTheBattleField(x - 6, y + 6)) {
                brothers[0].moveTo(battleground, x, y);
                for (int i = 1; i < 7; i++) {
                    x -= 1;
                    y += 1;
                    brothers[i].moveTo(battleground, x, y);
                    brothers[i].setRoleRunning(true);
                }
            } else {
                System.out.println("yanxing pattern move wrong!!!");
            }
        }
        else{
            for (int i = 0; i < brothers.length; i++) {
                brothers[i].moveFrom(battleground);
            }
            cheerleader.moveFrom(battleground);
            if (!battleground.isExceedTheBattleField(x, y) && !battleground.isExceedTheBattleField(x - 7, y + 7)) {
                brothers[0].moveTo(battleground, x, y);
                for (int i = 1; i < 7; i++) {
                    x -= 1;
                    y += 1;
                    brothers[i].moveTo(battleground, x, y);
                    brothers[i].setRoleRunning(true);
                }
                x-=1;
                y+=1;
                cheerleader.moveTo(battleground,x,y);
            } else {
                System.out.println("yanxing pattern move wrong!!!");
            }
        }
    }

    public void generateChongePattern(Space battleground,  int x, int y){
        new HuluwaRankSort().sort(brothers);
        if (!cheerleader.isAlive()) {
            for (int i = 0; i < brothers.length; i++) {
                brothers[i].moveFrom(battleground);
            }
            if (!battleground.isExceedTheBattleField(x, y) && !battleground.isExceedTheBattleField(x - 1, y + 6)) {
                brothers[0].moveTo(battleground, x, y);
                brothers[0].setRoleRunning(true);
                for (int i = 1; i < 7; i++) {
                    brothers[i].setRoleRunning(true);
                    y += 1;
                    if (i % 2 == 1) brothers[i].moveTo(battleground, x - 1, y);
                    else brothers[i].moveTo(battleground, x, y);
                }
            } else {
                System.out.println("chonge pattern move wrong!!!");
            }
        }
        else{
            for (int i = 0; i < brothers.length; i++) {
                brothers[i].moveFrom(battleground);
            }
            cheerleader.moveFrom(battleground);
            if (!battleground.isExceedTheBattleField(x, y) && !battleground.isExceedTheBattleField(x - 1, y + 7)) {
                brothers[0].moveTo(battleground, x, y);
                brothers[0].setRoleRunning(true);
                for (int i = 1; i < 7; i++) {
                    brothers[i].setRoleRunning(true);
                    y += 1;
                    if (i % 2 == 1) brothers[i].moveTo(battleground, x - 1, y);
                    else brothers[i].moveTo(battleground, x, y);
                }
                cheerleader.moveTo(battleground,x-1,y+1);
            } else {
                System.out.println("chonge pattern move wrong!!!");
            }
        }
    }

    public void generateYulinPattern(Space battleground,  int x, int y){
        new HuluwaRankSort().sort(brothers);
        if (!cheerleader.isAlive()) {
            for (int i = 0; i < brothers.length; i++) {
                brothers[i].moveFrom(battleground);
            }
            if (!battleground.isExceedTheBattleField(x - 1, y) && !battleground.isExceedTheBattleField(x + 2, y + 3)) {
                for(int i=0;i<7;i++){
                    brothers[i].setRoleRunning(true);
                }
                brothers[0].moveTo(battleground, x, y);
                //second layer
                brothers[1].moveTo(battleground, x, y + 1);
                brothers[2].moveTo(battleground, x + 1, y + 1);
                brothers[3].moveTo(battleground, x - 1, y + 2);
                brothers[4].moveTo(battleground, x, y + 2);
                brothers[5].moveTo(battleground, x + 1, y + 2);
                brothers[6].moveTo(battleground, x, y + 3);
            } else {
                System.out.println("yulin pattern move wrong!!!");
            }
        }
        else{
            for (int i = 0; i < brothers.length; i++) {
                brothers[i].moveFrom(battleground);
            }
            cheerleader.moveFrom(battleground);
            if (!battleground.isExceedTheBattleField(x - 1, y) && !battleground.isExceedTheBattleField(x + 3, y + 4)) {
                for(int i=0;i<7;i++){
                    brothers[i].setRoleRunning(true);
                }
                brothers[0].moveTo(battleground, x, y);
                //second layer
                brothers[1].moveTo(battleground, x+1, y + 1);
                brothers[2].moveTo(battleground, x , y + 2);
                brothers[3].moveTo(battleground, x +2, y + 2);
                brothers[4].moveTo(battleground, x-1, y + 3);
                brothers[5].moveTo(battleground, x + 1, y + 3);
                brothers[6].moveTo(battleground, x+3, y + 3);
                cheerleader.moveTo(battleground,x+1,y+4);
            } else {
                System.out.println("yulin pattern move wrong!!!");
            }
        }
    }

    public void generateFangyuanPattern(Space battleground,  int x, int y){
        new HuluwaRankSort().sort(brothers);
        for (int i=0;i<brothers.length;i++){
            brothers[i].moveFrom(battleground);
        }
        cheerleader.moveFrom(battleground);
        if (!battleground.isExceedTheBattleField(x-2,y)&&!battleground.isExceedTheBattleField(x+2,y+4)){
            for(int i=0;i<7;i++){
                brothers[i].setRoleRunning(true);
            }
            brothers[0].moveTo(battleground, x, y);
            //second layer
            brothers[1].moveTo(battleground,x-1,y+1);
            brothers[2].moveTo(battleground,x+1,y+1);
            //third layer
            brothers[3].moveTo(battleground,x-2,y+2);
            brothers[4].moveTo(battleground,x+2,y+2);
            //forth layer
            brothers[5].moveTo(battleground,x-1,y+3);
            brothers[6].moveTo(battleground,x+1,y+3);
            //fifth layer
           cheerleader.moveTo(battleground,x,y+4);
           cheerleader.setRoleRunning(true);
        }
        else
        {
            System.out.println("fangyuan pattern move wrong!!!");
        }
    }

    public void generateYanyuePattern(Space battleground,  int x, int y){
        new HuluwaRankSort().sort(brothers);
        if (!cheerleader.isAlive()) {
            for (int i = 0; i < brothers.length; i++) {
                brothers[i].moveFrom(battleground);
            }
            if (!battleground.isExceedTheBattleField(x - 3, y) && !battleground.isExceedTheBattleField(x , y + 4)) {
                for(int i=0;i<7;i++){
                    brothers[i].setRoleRunning(true);
                }
                brothers[0].moveTo(battleground, x, y);
                //second layer
                brothers[1].moveTo(battleground, x-2, y + 1);
                brothers[2].moveTo(battleground, x -3, y + 2);
                brothers[3].moveTo(battleground, x - 2, y + 2);
                brothers[4].moveTo(battleground, x-1, y + 2);
                brothers[5].moveTo(battleground, x -2, y + 3);
                brothers[6].moveTo(battleground, x, y + 4);
            } else {
                System.out.println("yulin pattern move wrong!!!");
            }
        }
        else{
            for (int i = 0; i < brothers.length; i++) {
                brothers[i].moveFrom(battleground);
            }
            cheerleader.moveFrom(battleground);
            if (!battleground.isExceedTheBattleField(x - 3, y) && !battleground.isExceedTheBattleField(x , y + 4)) {
                for(int i=0;i<7;i++){
                    brothers[i].setRoleRunning(true);
                }
                brothers[0].moveTo(battleground, x, y);
                //second layer
                brothers[1].moveTo(battleground, x-2, y + 1);
                brothers[2].moveTo(battleground, x-1 , y + 1);
                brothers[3].moveTo(battleground, x -3, y + 2);
                brothers[4].moveTo(battleground, x-2, y + 2);
                brothers[5].moveTo(battleground, x -2, y + 3);
                brothers[6].moveTo(battleground, x-1, y + 3);
                cheerleader.moveTo(battleground,x,y+4);
            } else {
                System.out.println("yulin pattern move wrong!!!");
            }
        }
    }

    public void generateFengshiPattern(Space battleground,  int x, int y){
        new HuluwaRankSort().sort(brothers);
        if (!cheerleader.isAlive()) {
            for (int i = 0; i < brothers.length; i++) {
                brothers[i].moveFrom(battleground);
            }
            if (!battleground.isExceedTheBattleField(x - 2, y) && !battleground.isExceedTheBattleField(x+2 , y + 3)) {
                for(int i=0;i<7;i++){
                    brothers[i].setRoleRunning(true);
                }
                brothers[0].moveTo(battleground, x, y);
                //second layer
                brothers[1].moveTo(battleground, x-1, y + 1);
                brothers[2].moveTo(battleground, x+1, y + 1);
                brothers[3].moveTo(battleground, x - 2, y + 2);
                brothers[4].moveTo(battleground, x, y + 2);
                brothers[5].moveTo(battleground, x +2, y + 2);
                brothers[6].moveTo(battleground, x, y + 3);
            } else {
                System.out.println("yulin pattern move wrong!!!");
            }
        }
        else{
            for (int i = 0; i < brothers.length; i++) {
                brothers[i].moveFrom(battleground);
            }
            cheerleader.moveFrom(battleground);
            if (!battleground.isExceedTheBattleField(x - 2, y) && !battleground.isExceedTheBattleField(x+2 , y + 3)) {
                for(int i=0;i<7;i++){
                    brothers[i].setRoleRunning(true);
                }
                brothers[0].moveTo(battleground, x, y);
                //second layer
                brothers[1].moveTo(battleground, x-1, y + 1);
                brothers[2].moveTo(battleground, x, y + 1);
                brothers[3].moveTo(battleground, x+1, y + 1);
                brothers[4].moveTo(battleground, x - 2, y + 2);
                brothers[5].moveTo(battleground, x, y + 2);
                brothers[6].moveTo(battleground, x +2, y + 2);
                cheerleader.moveTo(battleground, x, y + 3);
            } else {
                System.out.println("yulin pattern move wrong!!!");
            }
        }
    }
}

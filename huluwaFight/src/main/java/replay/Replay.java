package replay;

import annotation.AuthorAnno;
import battle.Battle;
import creature.Creature;
import sample.Controller;
import space.Cell;

import java.io.BufferedReader;
import java.util.concurrent.TimeUnit;

@AuthorAnno
public class Replay implements Runnable{

    private Controller controller;
    private Battle battle;
    private BufferedReader bufferedReader;
    private boolean running;
    private long currentTimeStamp;
    private long oldTimeStamp;
    //private boolean isStartOver;
    private String oldLine;

    public Replay(BufferedReader bufferedReader, Controller controller, Battle battle){
        this.bufferedReader=bufferedReader;
        this.controller=controller;
        this.battle=battle;
        battle.getHuluwaTeam().bubbleSort();
        running=true;
        //sStartOver=false;
        currentTimeStamp=0;
        oldTimeStamp=0;
        oldLine="";
    }

    public void run(){
        try{
            while(bufferedReader.read()!=-1&&running){
                String readLine=bufferedReader.readLine();
                System.out.println(readLine);
                String[] readLineArray=readLine.split(" ");
                String[] oldLineArray=oldLine.split(" ");
                if (oldLineArray.length>=1){
                    if (oldLineArray[0].equals("start")&&!readLineArray[0].equals("start")){
                        controller.display();
                        TimeUnit.MILLISECONDS.sleep(150);
                    }
                }
                currentTimeStamp=Long.parseLong(readLineArray[readLineArray.length-1]);
                if (oldTimeStamp==0){
                    oldTimeStamp=currentTimeStamp;
                } else if (currentTimeStamp!=oldTimeStamp){
                    controller.display();
                    long sleepTime=currentTimeStamp-oldTimeStamp;
                    oldTimeStamp=currentTimeStamp;
                    TimeUnit.MILLISECONDS.sleep(sleepTime);
                }
                if(readLineArray[0].equals("start")) {
                    if (Integer.parseInt(readLineArray[1]) <= 7) {
                        //System.out.println("huluwa"+readLineArray[1]);
                        int number = Integer.parseInt(readLineArray[1]);
                        int CoordinateX = Integer.parseInt(readLineArray[2]);
                        int CoordinateY = Integer.parseInt(readLineArray[3]);
                        Creature c = battle.getHuluwaTeam().getTheBrother(number);
                        c.moveFrom(battle.getBattleField());
                        c.moveTo(battle.getBattleField(), CoordinateX, CoordinateY);
                    } else {
                        int number = Integer.parseInt(readLineArray[1]);
                        int CoordinateX = Integer.parseInt(readLineArray[2]);
                        int CoordinateY = Integer.parseInt(readLineArray[3]);
                        Creature c = battle.getSnakeTeam().getTheDemon(number);
                        c.moveFrom(battle.getBattleField());
                        c.moveTo(battle.getBattleField(), CoordinateX, CoordinateY);
                    }
                }
                if(readLineArray[0].equals("move")) {
                    if (Integer.parseInt(readLineArray[1]) <= 7) {
                        //System.out.println("huluwa"+readLineArray[1]);
                        int number = Integer.parseInt(readLineArray[1]);
                        int CoordinateX = Integer.parseInt(readLineArray[2]);
                        int CoordinateY = Integer.parseInt(readLineArray[3]);
                        Creature c = battle.getHuluwaTeam().getTheBrother(number);
                        c.moveFrom(battle.getBattleField());
                        c.moveTo(battle.getBattleField(), CoordinateX, CoordinateY);
                    } else {
                        int number = Integer.parseInt(readLineArray[1]);
                        int CoordinateX = Integer.parseInt(readLineArray[2]);
                        int CoordinateY = Integer.parseInt(readLineArray[3]);
                        Creature c = battle.getSnakeTeam().getTheDemon(number);
                        c.moveFrom(battle.getBattleField());
                        c.moveTo(battle.getBattleField(), CoordinateX, CoordinateY);
                    }
                }
                if (readLineArray[0].equals("attack")){
                    if (Integer.parseInt(readLineArray[1]) <= 7) {
                        //System.out.println("huluwa"+readLineArray[1]);
                        int number = Integer.parseInt(readLineArray[1]);
                       // int CoordinateX = Integer.parseInt(readLineArray[2]);
                        //int CoordinateY = Integer.parseInt(readLineArray[3]);
                        Creature c = battle.getHuluwaTeam().getTheBrother(number);
                        c.moveFrom(battle.getBattleField());
                        c.die();
                        //c.moveTo(battle.getBattleField(), CoordinateX, CoordinateY);
                    } else {
                        int number = Integer.parseInt(readLineArray[1]);
                        //int CoordinateX = Integer.parseInt(readLineArray[2]);
                        //int CoordinateY = Integer.parseInt(readLineArray[3]);
                        Creature c = battle.getSnakeTeam().getTheDemon(number);
                        c.moveFrom(battle.getBattleField());
                        c.die();
                        //c.moveTo(battle.getBattleField(), CoordinateX, CoordinateY);
                    }
                }
                if (readLineArray[0].equals("addImage")){
                    controller.addAttackImage(new Cell(Double.parseDouble(readLineArray[2]),Double.parseDouble(readLineArray[3])));
                }
                else if (readLineArray[0].equals("removeImage")){
                    controller.removeAttackImage(new Cell(Double.parseDouble(readLineArray[2]),Double.parseDouble(readLineArray[3])));
                }
                oldLine=readLine;
            }
            controller.display();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void close(){
        running=false;
    }
}

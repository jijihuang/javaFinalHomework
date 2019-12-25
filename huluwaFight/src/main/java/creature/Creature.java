package creature;

import battle.Battle;
import javafx.scene.image.Image;
import navigation.Navigation;
import replay.ReplayWriter;
import sample.Controller;
import space.*;

import java.util.Random;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class Creature implements Runnable {
    protected String name;
    protected Image picture;
    protected String picturePath;
    protected int coordinateX;
    protected int coordinateY;
    protected int number;
    protected boolean goodOrEvil;
    protected int attackValue;
    protected int lifeValue;
    private boolean roleRunning;
    private int forwardUpOrDown;
    public static Controller controller;


    public Creature(){
        coordinateX=-1;
        coordinateY=-1;
        number=-1;
        name=" ";
        goodOrEvil=false;
        //alive=true;
        attackValue=0;
        lifeValue=0;
        roleRunning=false;
        forwardUpOrDown=1;
    }
    public Creature(String name){
        coordinateX=-1;
        coordinateY=-1;
        number=-1;
        this.name=name;
        goodOrEvil=false;
        //alive=true;
        attackValue=0;
        lifeValue=0;
        roleRunning=false;
        forwardUpOrDown=-1;
    }


    /*public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }*/

    public void setRoleRunning(boolean roleRunning) {
        this.roleRunning = roleRunning;
    }

    public boolean isAlive() {
        return roleRunning;
    }
    public boolean isGoodOrEvil() {
        return goodOrEvil;
    }
    public void die(){
        roleRunning=false;
        System.out.println(this.name+" die!");
        controller.getBattle().battleField.removeTheCreature(coordinateX,coordinateY);
        this.close();
        //picturePath="";
        //picture=new Image(picturePath);
    }

    public void close(){
        roleRunning=false;
    }
    public int getAttackValue() {
        return attackValue;
    }

    public int getLifeValue() {
        return lifeValue;
    }


    public void getHurt(Creature enemy){
        lifeValue-=enemy.getAttackValue();
        System.out.println(enemy.name + " hurt " + this.name);
        if (lifeValue<0){
            die();
        }
    }

    public void hurtOthers(Creature enemy){
        enemy.getHurt(this);
        System.out.println(this.name + " hurt " + enemy.name);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void attack(Creature enemy) {
        //getHurt(enemy);
        //hurtOthers(enemy);
        Random random=new Random();
        if (random.nextBoolean()){
            try{
                long start=System.currentTimeMillis();
                synchronized (Creature.class) {
                    new ReplayWriter().writeInToFile(" attack " + enemy.number + " " + enemy.getCoordinateX() + " " + enemy.getCoordinateY() + " " + "die " + start + "\n");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            enemy.die();

            //enemy.die();
        }
    }

    public void forward(Space battleField, Battle battle){
        int directionX;
        if (isGoodOrEvil()){
            directionX=1;
        }
        else{
            directionX=-1;
        }
        synchronized (Creature.class) {
            if (isAlive()) {
                boolean hasEnemyInTheRow = false;
                int i = 0;
                for (; i < battleField.getSizeX(); i++) {
                    if (!battleField.isTheCellEmpty(i, coordinateY) && battleField.getTheCreatureOnTheCell(i, coordinateY).isGoodOrEvil() ^ this.isGoodOrEvil()) {
                        hasEnemyInTheRow = true;
                        break;
                    }
                }
                if (hasEnemyInTheRow) {
                    int direction = i - coordinateX;
                    if (direction > 0) {
                        directionX = 1;
                    } else {
                        directionX = -1;
                    }
                    if (coordinateX + directionX < battleField.getSizeX() && coordinateX + directionX >= 0) {
                        if (!battleField.isTheCellEmpty(coordinateX + directionX, coordinateY) && battleField.getTheCreatureOnTheCell(coordinateX + directionX, coordinateY).isGoodOrEvil() ^ this.isGoodOrEvil()) {
                            double xx=this.coordinateX+0.5*directionX;
                            double yy=this.coordinateY;
                            attack(battleField.getTheCreatureOnTheCell(coordinateX + directionX, coordinateY));
                            synchronized (Cell.class) {
                                controller.addAttackImage(new Cell(xx, yy));
                            }
                            try{
                                long start=System.currentTimeMillis();
                                synchronized (Creature.class) {
                                    new ReplayWriter().writeInToFile(" addImage " + " " + xx + " " + yy + " " + start + "\n");
                                }
                                TimeUnit.MILLISECONDS.sleep(120);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            synchronized (Cell.class) {
                                controller.removeAttackImage(new Cell(xx, yy ));
                            }
                            try{
                                long start=System.currentTimeMillis();
                                synchronized (Creature.class) {
                                    new ReplayWriter().writeInToFile(" removeImage " + " " + xx + " " + yy + " " + start + "\n");
                                }
                                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(20) * 5 + 300);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            //System.out.println(this.name + " hurt " + battleField.getTheCreatureOnTheCell(coordinateX + direction, coordinateY).name);
                        } else if (battleField.isTheCellEmpty(coordinateX + directionX, coordinateY) ) {
                            int tempX = coordinateX + directionX;
                            int tempY = coordinateY;
                            moveFrom(battleField);
                            //coordinateX += direction;
                            //moveFrom(battleField);
                            moveTo(battleField, tempX, tempY);
                            try{
                                long start=System.currentTimeMillis();
                                synchronized (Creature.class) {
                                    new ReplayWriter().writeInToFile(" move " + number + " " + tempX + " " + tempY + " " + start + "\n");
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            if(coordinateY==-1||coordinateX==-1){
                                System.out.println("??????");
                            }
                            System.out.println(this.name + " move to " + coordinateX + " , " + coordinateY);
                        }
                    }
                } else if (coordinateY + forwardUpOrDown < battleField.getSizeY()&&coordinateY + forwardUpOrDown>1) {
                    if (battleField.isTheCellEmpty(coordinateX, coordinateY + forwardUpOrDown)) {
                        int tempX = coordinateX;
                        int tempY = coordinateY + forwardUpOrDown;
                        moveFrom(battleField);
                        //coordinateX += direction;
                        //moveFrom(battleField);

                        moveTo(battleField, tempX, tempY);
                        try{
                            long start=System.currentTimeMillis();
                            synchronized (Creature.class) {
                                new ReplayWriter().writeInToFile(" move " + number + " " + tempX + " " + tempY + " " + start + "\n");
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        if(coordinateY==-1||coordinateX==-1){
                            System.out.println("??????");
                        }
                        System.out.println(this.name + " move to " + coordinateX + " , " + coordinateY);
                    }
                    else{
                        Random ra=new Random();
                        if (ra.nextBoolean()&&battleField.isTheCellEmpty(coordinateX + 1, coordinateY)){
                            int tempX = coordinateX + 1;
                            int tempY = coordinateY;
                            moveFrom(battleField);
                            //coordinateX += direction;
                            //moveFrom(battleField);
                            moveTo(battleField, tempX, tempY);
                            try{
                                long start=System.currentTimeMillis();
                                synchronized (Creature.class) {
                                    new ReplayWriter().writeInToFile(" move " + number + " " + tempX + " " + tempY + " " + start + "\n");
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            if(coordinateY==-1||coordinateX==-1){
                                System.out.println("??????");
                            }
                            System.out.println(this.name + " move to " + coordinateX + " , " + coordinateY);
                        }
                        else if(battleField.isTheCellEmpty(coordinateX - 1, coordinateY)) {
                            int tempX = coordinateX - 1;
                            int tempY = coordinateY;
                            moveFrom(battleField);
                            //coordinateX += direction;
                            //moveFrom(battleField);
                            moveTo(battleField, tempX, tempY);
                            try{
                                long start=System.currentTimeMillis();
                                synchronized (Creature.class) {
                                    new ReplayWriter().writeInToFile(" move " + number + " " + tempX + " " + tempY + " " + start + "\n");
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            if(coordinateY==-1||coordinateX==-1){
                                System.out.println("??????");
                            }
                            System.out.println(this.name + " move to " + coordinateX + " , " + coordinateY);
                        }
                    }
                } else if (coordinateY + 1 >= battleField.getSizeY()) {
                    forwardUpOrDown=-1;
                    if (battleField.isTheCellEmpty(coordinateX, coordinateY +forwardUpOrDown)) {
                        int tempX = coordinateX;
                        int tempY = coordinateY +forwardUpOrDown;
                        moveFrom(battleField);
                        //coordinateX += direction;
                        //moveFrom(battleField);
                        moveTo(battleField, tempX, tempY);
                        try{
                            long start=System.currentTimeMillis();
                            synchronized (Creature.class) {
                                new ReplayWriter().writeInToFile(" move " + number + " " + tempX + " " + tempY + " " + start + "\n");
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        if(coordinateY==-1||coordinateX==-1){
                            System.out.println("??????");
                        }
                        System.out.println(this.name + " move to " + coordinateX + " , " + coordinateY);
                    }
                    else{
                        Random ra=new Random();
                        if (ra.nextBoolean()&&battleField.isTheCellEmpty(coordinateX + 1, coordinateY)){
                            int tempX = coordinateX + 1;
                            int tempY = coordinateY;
                            moveFrom(battleField);
                            //coordinateX += direction;
                            //moveFrom(battleField);
                            moveTo(battleField, tempX, tempY);
                            try{
                                long start=System.currentTimeMillis();
                                synchronized (Creature.class) {
                                    new ReplayWriter().writeInToFile(" move " + number + " " + tempX + " " + tempY + " " + start + "\n");
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            if(coordinateY==-1||coordinateX==-1){
                                System.out.println("??????");
                            }
                            System.out.println(this.name + " move to " + coordinateX + " , " + coordinateY);
                        }
                        else if(battleField.isTheCellEmpty(coordinateX - 1, coordinateY)) {
                            int tempX = coordinateX - 1;
                            int tempY = coordinateY;
                            moveFrom(battleField);
                            //coordinateX += direction;
                            //moveFrom(battleField);
                            moveTo(battleField, tempX, tempY);
                            try{
                                long start=System.currentTimeMillis();
                                synchronized (Creature.class) {
                                    new ReplayWriter().writeInToFile(" move " + number + " " + tempX + " " + tempY + " " + start + "\n");
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            if(coordinateY==-1||coordinateX==-1){
                                System.out.println("??????");
                            }
                            System.out.println(this.name + " move to " + coordinateX + " , " + coordinateY);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        while(isAlive()&&coordinateX!=-1&&coordinateY!=-1){
            if (isAlive()) {
                synchronized (Creature.class) {
                    forward(controller.getBattle().getBattleField(), controller.getBattle());
                }
                try {
                    //Thread.yield();
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(20) * 5 + 400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(150);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getCoordinateX(){
        return coordinateX;
    }
    public int getCoordinateY(){
        return coordinateY;
    }
    public String getName(){
        return name;
    }
    public String getPicturePath(){
        return picturePath;
    }
    public Image getPicture(){
        return picture;
    }
    public void setCoordinateX(int x){
        coordinateX=x;
    }
    public void setCoordinateY(int y){
        coordinateY=y;
    }

    public void moveFrom(Space battleground){
        if (!battleground.isExceedTheBattleField(coordinateX,coordinateY)){
            battleground.removeTheCreature(coordinateX,coordinateY);
        }
        coordinateX=-1;
        coordinateY=-1;
    }
    public void moveTo(Space battleground, int x, int y){
        if (battleground.isTheCellEmpty(x,y)){
            coordinateX=x;
            coordinateY=y;
            battleground.setTheCreatureOnTheCell(x,y,this);
        }
        else{
            if (!battleground.isExceedTheBattleField(x,y)){
                if (battleground.getTheCreatureOnTheCell(x,y)!=this){
                    //System.out.println("Someone is still on the cell!");
                }
            }
        }
    }

    public Stack<Coordinate> moveFromTo(Space battleground,int destX,int destY)
    {
        Navigation navigation=new Navigation(battleground);
        return navigation.useTheNavigation(battleground,this,destX,destY);
    }

}

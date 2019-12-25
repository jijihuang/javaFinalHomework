package sample;

import battle.*;
import creature.*;

import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import replay.Replay;
import replay.ReplayWriter;
import space.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.*;
import javafx.fxml.*;
import javafx.event.*;
import javafx.scene.canvas.*;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.*;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Stack;


public class Controller {
    private Battle battle;
    private Image battleBackground;
    private int width;
    private int depth;
    private Creature selectedCreature;
    private Timeline timeline;
    private Timeline timelineHuluSort;
    private boolean startOrEnd;
    private boolean currentHuluwaSortDone;
    private int huluSortNum;
    private int lastRand;
    private Stack<Coordinate> path;
    private boolean isHuluwaOnTheSpace;
    private boolean gameStart;
    private Refresher refresher;
    private Replay replay;
    private ArrayList<Cell>attackImage;
    //private KeyInput keyInput;

    @FXML private Canvas canvas;
    @FXML private ImageView selectedImage;
    @FXML private Label selectedLabel;
    @FXML private Pane pane;
    @FXML private Button randomHuluTeam;
    @FXML private Button shexingHuluTeam;
    @FXML private Button heyiHuluTeam;
    @FXML private Button yanxingHuluTeam;
    @FXML private Button chongeHuluTeam;
    @FXML private Button yulinHuluTeam;
    @FXML private Button fangyuanHuluTeam;
    @FXML private Button yanyueHuluTeam;
    @FXML private Button fengshiHuluTeam;
    @FXML private Button heyiDemonTeam;
    @FXML private Button yanxingDemonTeam;
    @FXML private Button chongeDemonTeam;
    @FXML private Button changsheDemonTeam;
    @FXML private Button yulinDemonTeam;
    @FXML private Button fangyuanDemonTeam;
    @FXML private Button yanyueDemonTeam;
    @FXML private Button fengshiDemonTeam;
    @FXML private Button grandpaCheer;
    @FXML private Button snakeCheer;
    @FXML private Button randomChange;
    @FXML private Label gameLabel;


    public void shutDown(){
        // attackImage.clear();
        //display();
        refresher.close();
        if (replay!=null){
            replay.close();
        }
        for (Creature c:battle.huluwaTeam.getBrothers()){
            c.close();
        }
        battle.huluwaTeam.getCheerleader().close();
        for(Creature c:battle.snakeTeam.getDemons()){
            c.close();
        }
        battle.snakeTeam.getCheerleader().close();
    }

    public ArrayList<Cell> getAttackImage() {
        return attackImage;
    }

    @FXML private void onKeyReleased(KeyEvent event)throws Exception{
        Stage stage=(Stage)pane.getScene().getWindow();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                shutDown();
                refresher.close();
                System.out.println("监听到窗口关闭");
            }
        });
        System.out.println(event.getCode());
        if(event.getCode()==KeyCode.SPACE&&!gameStart){
            System.out.println("游戏开始");
            setGameLabel("游戏开始！");
            gameStart=true;
            Creature.controller=this;
            try{
                Date date=new Date();
                SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String str=df.format(date);
                System.out.println(str);
                new ReplayWriter().setFileWriter(new FileWriter(str));
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                for (int i=0;i<=7;i++){
                    Creature creature=battle.getHuluwaTeam().getTheBrother(i);
                    if (creature.getCoordinateY()!=-1&&creature.getCoordinateX()!=-1) {
                        long start=System.currentTimeMillis();
                        new ReplayWriter().writeInToFile(" start " + i + " " + creature.getCoordinateX() + " " + creature.getCoordinateY() + " "+start+"\n");
                    }
                }
                for (int i=8;i<=18;i++){
                    Creature creature=battle.getSnakeTeam().getTheDemon(i);
                    if (creature.getCoordinateY()!=-1&&creature.getCoordinateX()!=-1) {
                        long start=System.currentTimeMillis();
                        new ReplayWriter().writeInToFile(" start " + i + " " + creature.getCoordinateX() + " " + creature.getCoordinateY() +" "+start+ "\n");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            refresher=new Refresher(this);
            new Thread(refresher).start();
            for (Creature c:battle.huluwaTeam.getBrothers()){
                if (c.getCoordinateX()!=-1&&c.getCoordinateY()!=-1) {
                    new Thread(c).start();
                }
            }
            if(battle.huluwaTeam.getCheerleader().getCoordinateX()!=-1&&battle.huluwaTeam.getCheerleader().getCoordinateY()!=-1) {
                new Thread(battle.huluwaTeam.getCheerleader()).start();
            }
            for(Creature c:battle.snakeTeam.getDemons()){
                if (c.getCoordinateX()!=-1&&c.getCoordinateY()!=-1) {
                    new Thread(c).start();
                }
            }
            if(battle.snakeTeam.getCheerleader().getCoordinateX()!=-1&&battle.snakeTeam.getCheerleader().getCoordinateY()!=-1) {
                new Thread(battle.snakeTeam.getCheerleader()).start();
            }
        }
        else if (event.getCode()==KeyCode.R){
            shutDown();
            System.out.println("游戏重置！");
            setGameLabel("游戏重置！");
            gameStart=false;
            attackImage.clear();
            //GraphicsContext gc=canvas.getGraphicsContext2D();
            //gc.drawImage(battleBackground,0,0,width,depth);
            battle=new Battle();
            display();
        }
        else if (event.getCode()==KeyCode.L &&!gameStart){
            shutDown();
            gameStart=true;
            battle=new Battle();
            gameLabel.setText("游戏重放！");
            display();
            FileChooser fileChooser=new FileChooser();
            fileChooser.setTitle("Open Log File");
            File file=fileChooser.showOpenDialog(pane.getScene().getWindow());
            try{
                InputStreamReader inputStreamReader=new InputStreamReader(new FileInputStream(file.getPath()));
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                replay=new Replay(bufferedReader,this,this.battle);
                new Thread(replay).start();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void setGameLabel(String text){
        gameLabel.setText(text);
    }
    public void initialize()throws Exception{
        width=1080;
        depth=810;
        battle=new Battle();
        //URL url=getClass().getResource("Round4.jpg");
        battleBackground=new Image("image/round4.png");
        attackImage=new ArrayList<Cell>();
        setGameLabel("游戏准备中...");
        //Main.keyInput=keyInput;

        canvas.setFocusTraversable(true);
        randomHuluTeam.setFocusTraversable(false);
        shexingHuluTeam.setFocusTraversable(false);
        heyiHuluTeam.setFocusTraversable(false);
        yanxingHuluTeam.setFocusTraversable(false);
        chongeHuluTeam.setFocusTraversable(false);
        fangyuanHuluTeam.setFocusTraversable(false);
        fengshiHuluTeam.setFocusTraversable(false);
        yanyueHuluTeam.setFocusTraversable(false);
        yulinHuluTeam.setFocusTraversable(false);
        heyiDemonTeam.setFocusTraversable(false);
        yanxingDemonTeam.setFocusTraversable(false);
        changsheDemonTeam.setFocusTraversable(false);
        chongeDemonTeam.setFocusTraversable(false);
        fangyuanDemonTeam.setFocusTraversable(false);
        fengshiDemonTeam.setFocusTraversable(false);
        yanyueDemonTeam.setFocusTraversable(false);
        yulinDemonTeam.setFocusTraversable(false);
        grandpaCheer.setFocusTraversable(false);
        snakeCheer.setFocusTraversable(false);
        randomChange.setFocusTraversable(false);

        GraphicsContext gc=canvas.getGraphicsContext2D();
        gc.drawImage(battleBackground,100,0,width,depth);
        startOrEnd=false;
        huluSortNum=0;
        lastRand=-1;
        currentHuluwaSortDone=false;
        isHuluwaOnTheSpace=false;
        gameStart=false;


        timeline=new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int rand = (int) Math.round(Math.random() * 7 + 1);
                while(true){
                    if (rand!=lastRand){
                        break;
                    }
                    else {
                        rand = (int) Math.round(Math.random() * 7 + 1);
                    }
                }
                switch (rand) {
                    case 1:
                        battle.snakeTeam.generateHeyiPattern(battle.battleField, 8, 4);
                        display();
                        break;
                    case 2:
                        battle.snakeTeam.generateYanxingPattern(battle.battleField, 14, 3);
                        display();
                        break;
                    case 3:
                        battle.snakeTeam.generateChongePattern(battle.battleField, 14, 3);
                        display();
                        break;
                    case 4:
                        battle.snakeTeam.generateChangshePattern(battle.battleField, 14, 3);
                        display();
                        break;
                    case 5:
                        battle.snakeTeam.generateYulinPattern(battle.battleField, 11, 3);
                        display();
                        break;
                    case 6:
                        battle.snakeTeam.generateFangyuanPattern(battle.battleField, 12, 3);
                        display();
                        break;
                    case 7:
                        battle.snakeTeam.generateYanyuePattern(battle.battleField, 14, 2);
                        display();
                        break;
                    case 8:
                        battle.snakeTeam.generateFengshiPattern(battle.battleField, 11, 3);
                        display();
                        break;
                    default:
                        System.out.println("wrong in random!!!");
                }
                lastRand=rand;
            }
        });
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(keyFrame);

        timelineHuluSort=new Timeline();
        timelineHuluSort.setCycleCount(Timeline.INDEFINITE);
        timelineHuluSort.setAutoReverse(false);
        KeyFrame keyFrameHuluSort=new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (huluSortNum<7) {
                    if (!path.empty()){
                        Coordinate coordinate = new Coordinate();
                        coordinate = path.pop();
                        battle.huluwaTeam.getTheSpecificHuluwa(huluSortNum).moveFrom(battle.battleField);
                        display();
                        battle.huluwaTeam.getTheSpecificHuluwa(huluSortNum).moveTo(battle.battleField, coordinate.getX(), coordinate.getY());
                        display();
                    }
                    else {
                        huluSortNum += 1;
                        if (huluSortNum < 7) {
                            Huluwa currentHuluwa = battle.huluwaTeam.getTheSpecificHuluwa(huluSortNum);
                            int destX = 0;
                            int destY = currentHuluwa.getRank() - 1 + 3;
                            while(true) {
                                if (currentHuluwa.getCoordinateX()==destX&&currentHuluwa.getCoordinateY()==destY){
                                    huluSortNum+=1;
                                    currentHuluwa = battle.huluwaTeam.getTheSpecificHuluwa(huluSortNum);
                                    destX=0;
                                    destY=currentHuluwa.getRank() - 1 + 3;
                                }
                                else{
                                    break;
                                }
                            }
                            if (huluSortNum<7)
                                path = currentHuluwa.moveFromTo(battle.battleField, destX, destY);
                        }
                    }
                }
                else{
                    currentHuluwaSortDone=true;
                    timelineHuluSort.stop();
                }
            }
        });
        timelineHuluSort.getKeyFrames().clear();
        timelineHuluSort.getKeyFrames().add(keyFrameHuluSort);
    }

    public void setGameStart(boolean gameStart) {
        this.gameStart = gameStart;
    }

    public boolean isGameStart() {
        return gameStart;
    }

    public Battle getBattle() {
        return battle;
    }

    public void display(){

        GraphicsContext gc=canvas.getGraphicsContext2D();
        gc.drawImage(battleBackground,100,0,width,depth);
        for (int i = 0; i < battle.battleField.getSizeX(); i++)
            for (int j = 0; j < battle.battleField.getSizeY(); j++)
                if (battle.battleField.getTheCreatureOnTheCell(i, j) != null) {
                    gc.drawImage(battle.battleField.getTheCreatureOnTheCell(i, j).getPicture(), 280 + 60 * i, 60 * j, 60, 60);
                }
        for (Cell c:attackImage){
            if (c.getCoordinateXx()==0&&c.getCoordinateY()==0){
                continue;
            }
            gc.drawImage(new Image("image/attack.png"),280+60*c.getCoordinateXx(),60*c.getCoordinateYy(),60,60);
        }
    }

    public synchronized void addAttackImage(Cell c){
        attackImage.add(c);
    }

    public synchronized  void removeAttackImage(Cell c){
        Iterator<Cell> iterator=attackImage.iterator();
        while(iterator.hasNext()){
            Cell next=iterator.next();
            if (next.getCoordinateXx()==c.getCoordinateXx()&&next.getCoordinateYy()==c.getCoordinateYy()){
                iterator.remove();
                break;
            }
        }
        //attackImage.remove(c);
        //attackImage.remove(c);
    }
    @FXML private void randomHuluTeamOnAction(ActionEvent event) {
        //battle.huluwaTeam.randomQueue();
        currentHuluwaSortDone=false;
        huluSortNum=0;
        battle.huluwaTeam.generateChangshePattern(battle.battleField,3,3);
        display();
        isHuluwaOnTheSpace=true;
    }

    @FXML private void shexingHuluTeamOnAction(ActionEvent event){
        if (isHuluwaOnTheSpace==true) {
            battle.huluwaTeam.bubbleSort();
            huluSortNum=0;
            currentHuluwaSortDone=false;
            Huluwa currentHuluwa = battle.huluwaTeam.getTheSpecificHuluwa(huluSortNum);
            int destX = 0;
            int destY = currentHuluwa.getRank() - 1 + 3;
            while(true) {
                if (currentHuluwa.getCoordinateX()==destX&&currentHuluwa.getCoordinateY()==destY){
                    huluSortNum+=1;
                    currentHuluwa = battle.huluwaTeam.getTheSpecificHuluwa(huluSortNum);
                    destX=0;
                    destY=currentHuluwa.getRank() - 1 + 3;
                }
                else{
                    break;
                }
            }
            path = currentHuluwa.moveFromTo(battle.battleField, destX, destY);
            if (currentHuluwaSortDone == false) {
                timelineHuluSort.play();
            } else {
                timelineHuluSort.stop();
            }
        }
        else{
            //battle.huluwaTeam.bubbleSort();
            battle.huluwaTeam.generateChangshePattern(battle.battleField,0,3);
            display();
            isHuluwaOnTheSpace=true;
        }
    }

    @FXML private void heyiDemonTeamOnAction(ActionEvent event) {
        battle.snakeTeam.generateHeyiPattern(battle.battleField,8,4);
        display();
    }

    @FXML private void yanxingDemonTeamOnAction(ActionEvent event) {
        battle.snakeTeam.generateYanxingPattern(battle.battleField,14,3);
        display();
    }

    @FXML private void chongeDemonTeamOnAction(ActionEvent event) {
        battle.snakeTeam.generateChongePattern(battle.battleField,14,3);
        display();
    }

    @FXML private void changsheDemonTeamOnAction(ActionEvent event) {
        battle.snakeTeam.generateChangshePattern(battle.battleField,14,3);
        display();
    }

    @FXML private void yulinDemonTeamOnAction(ActionEvent event) {
        battle.snakeTeam.generateYulinPattern(battle.battleField,11,3);
        display();
    }

    @FXML private void fangyuanDemonTeamOnAction(ActionEvent event) {
        battle.snakeTeam.generateFangyuanPattern(battle.battleField,12,3);
        display();
    }

    @FXML private void yanyueDemonTeamOnAction(ActionEvent event) {
        battle.snakeTeam.generateYanyuePattern(battle.battleField,14,2);
        display();
    }

    @FXML private void fengshiDemonTeamOnAction(ActionEvent event) {
        battle.snakeTeam.generateFengshiPattern(battle.battleField,11,3);
        display();
    }

    @FXML private void heyiHuluTeamOnAction(ActionEvent event) {
        battle.huluwaTeam.generateHeyiPattern(battle.battleField,0,4);
        display();
    }

    @FXML private void yanxingHuluTeamOnAction(ActionEvent event) {
        battle.huluwaTeam.generateYanxingPattern(battle.battleField,7,3);
        display();
    }

    @FXML private void chongeHuluTeamOnAction(ActionEvent event) {
        battle.huluwaTeam.generateChongePattern(battle.battleField,1,3);
        display();
    }


    @FXML private void yulinHuluTeamOnAction(ActionEvent event) {
        battle.huluwaTeam.generateYulinPattern(battle.battleField,1,4);
        display();
    }

    @FXML private void fangyuanHuluTeamOnAction(ActionEvent event) {
        battle.huluwaTeam.generateFangyuanPattern(battle.battleField,2,4);
        display();
    }

    @FXML private void yanyueHuluTeamOnAction(ActionEvent event) {
        battle.huluwaTeam.generateYanyuePattern(battle.battleField,3,4);
        display();
    }

    @FXML private void fengshiHuluTeamOnAction(ActionEvent event) {
        battle.huluwaTeam.generateFengshiPattern(battle.battleField,2,5);
        display();
    }

    @FXML private void grandpaCheerOnAction(ActionEvent event) {
        battle.huluwaTeam.generateTheCheerPattern(battle.battleField,0,10);
        display();
    }

    @FXML private void snakeCheerOnAction(ActionEvent event) {
        battle.snakeTeam.generateTheCheerPattern(battle.battleField,14,9);
        display();
    }

    @FXML private void randomChangeOnAction(ActionEvent event) {
        startOrEnd=!startOrEnd;
        if (startOrEnd==true)
            timeline.play();
        else timeline.pause();
    }

    @FXML private void canvasOnMouseClicked(MouseEvent event) {
        canvas.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x=(int)event.getX()-280;
                int y=(int)event.getY();
                //System.out.println("x:"+x+"y:"+y);
                if (x>0&&y>0)
                {
                    x=x/60;
                    y=y/60;
                    if (battle.battleField.getTheCreatureOnTheCell(x,y)!=null){
                        selectedCreature=battle.battleField.getTheCreatureOnTheCell(x,y);
                        selectedImage.setImage(selectedCreature.getPicture());
                        selectedLabel.setText(selectedCreature.getName());
                    }
                }
            }
        });
    }

    @FXML private void canvasOnMouseDragged(MouseEvent event) {
        canvas.setOnMouseDragged(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x=(int)event.getX()-280;
                int y=(int)event.getY();
                //System.out.println("x:"+x+"y:"+y);
                if (x>0&&y>0)
                {
                    x=x/60;
                    y=y/60;
                    if (selectedCreature!=null&&battle.battleField.isTheCellEmpty(x,y)) {
                        selectedCreature.moveFrom(battle.battleField);
                        selectedCreature.moveTo(battle.battleField,x,y);
                        display();
                    }
                }
            }
        });
    }
}

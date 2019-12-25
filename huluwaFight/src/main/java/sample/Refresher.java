package sample;

import creature.Creature;
import javafx.application.Platform;
import replay.ReplayWriter;
import space.Cell;

import java.util.concurrent.TimeUnit;

public class Refresher implements Runnable{
    private Controller controller;
    private boolean gameStart;
    public Refresher(Controller controller){
        this.controller=controller;
        gameStart=true;
    }

    @Override
    public void run() {
        while(gameStart){
            if(controller.getBattle().huluwaTeam.isAllDead()){
                for(Creature c:controller.getBattle().snakeTeam.getDemons()){
                    c.close();
                }
                try{
                    TimeUnit.MILLISECONDS.sleep(300);
                }catch (Exception e){
                    e.printStackTrace();
                }
                controller.getAttackImage().clear();
                controller.getBattle().snakeTeam.getCheerleader().close();
                controller.display();
                controller.setGameStart(false);
                System.out.println("妖精胜利！");
                Platform.runLater(new Runnable() {
                    public void run() {
                        controller.setGameLabel("妖精胜利!");
                    }
                });
                try{
                    TimeUnit.MILLISECONDS.sleep(300);
                    new ReplayWriter().closeFile();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
            if(controller.getBattle().snakeTeam.isAllDead()){
                for(Creature c:controller.getBattle().huluwaTeam.getBrothers()){
                    c.close();
                }
                controller.getAttackImage().clear();
                controller.getBattle().huluwaTeam.getCheerleader().close();
                controller.display();
                controller.setGameStart(false);
                System.out.println("葫芦娃胜利！");
                Platform.runLater(new Runnable() {
                    public void run() {
                        controller.setGameLabel("葫芦娃胜利!");
                    }
                });
                try{
                    TimeUnit.MILLISECONDS.sleep(300);
                    new ReplayWriter().closeFile();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
            synchronized (Cell.class){
                controller.display();
            }
            try{
                TimeUnit.MILLISECONDS.sleep(30);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public void close(){
        gameStart=false;
    }
}

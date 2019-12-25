package creature;

public class Demon extends Creature {
    public Demon(){
        name="";
        coordinateX=-1;
        coordinateY=-1;
        goodOrEvil=false;
        lifeValue=10;
        attackValue=1;
    }
    public Demon(String name){
        coordinateX=-1;
        coordinateY=-1;
        this.name=name;
        goodOrEvil=false;
        lifeValue=10;
        attackValue=1;
    }
}

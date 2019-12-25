package creature;

import annotation.AuthorAnno;
import javafx.scene.image.Image;

@AuthorAnno
public class Huluwa extends Creature {
    private int rank;
    private String color;

    public Huluwa(int rank){
        this.rank=rank;
    }

    public Huluwa(int rank, String color, String name,Image picture, String picturePath,int lifeValue,int attackValue){
        this.rank=rank;
        this.color=color;
        this.picture=picture;
        this.picturePath=picturePath;
        this.name=name;
        this.lifeValue=lifeValue;
        this.attackValue=attackValue;
        goodOrEvil=true;
    }

    public int getRank() {
        return rank;
    }
}

package creature;

import javafx.scene.image.Image;

public class Grandpa extends Creature {
    public Grandpa(){
        name="爷爷";
        picturePath= "image/Grandfather.png";
        picture=new Image("image/Grandfather.png");
        coordinateX=-1;
        coordinateY=-1;
        goodOrEvil=true;
        lifeValue=10;
        attackValue=1;
    }
}

package creature;

import javafx.scene.image.Image;

public class Scorpion extends Demon {
    public Scorpion(){
        name="蝎子精";
        picturePath= "image/Scorpion.png";
        picture=new Image("image/Scorpion.png");
        coordinateX=-1;
        coordinateY=-1;
        lifeValue=15;
        attackValue=4;
    }
}

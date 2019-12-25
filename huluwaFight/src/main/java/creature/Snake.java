package creature;

import javafx.scene.image.Image;

public class Snake extends Demon {
    public Snake(){
        name="蛇精";
        picturePath= "image/Snake.png";
        picture=new Image("image/Snake.png");
        coordinateX=-1;
        coordinateY=-1;
        lifeValue=10;
        attackValue=1;
    }
}

package org.pAxDev.Logic;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.pAxDev.Objects.Entity;
import org.pAxDev.Util.ImgLoader;

import java.io.FileNotFoundException;

/**
 * Created by Lance on 3/3/2016.
 */
public class MainMenu {
    Entity startButton;
    Entity quitButton;
    ImgLoader il = new ImgLoader();


    public MainMenu(Vector3f pos) {

        startButton = new Entity(pos, new Vector2f(150,100),new Vector4f(1,1,1,1), null );
        quitButton = new Entity(new Vector3f(pos.x,pos.y-150,pos.z), new Vector2f(150,100),new Vector4f(1,1,1,1), null );
        Texture sbTex = il.loadTexture("src/res/start.png", "PNG");
        Texture qbTex = il.loadTexture("src/res/quit.png", "PNG");
        startButton.texture = sbTex;
        quitButton.texture = qbTex;

    }
    public void update() {
        if(isColliding(startButton)) {
            startButton.color = new Vector4f(1,1,1,1);
        } else {
            startButton.color = new Vector4f(0,1,0,.7f);
        }
        if(isColliding(quitButton)) {
            quitButton.color = new Vector4f(1,1,1,1);
        } else {
            quitButton.color = new Vector4f(1,1,0,.7f);
        }
        startButton.drawTextured();
        quitButton.drawTextured();
    }

    public boolean isColliding(Entity a) {
        if (Mouse.getX() < (a.getPositionX() + a.scale.x/2) &&
                Mouse.getX() > (a.getPositionX() - a.scale.x/2) &&
                Mouse.getY() < (a.getPositionY() + a.scale.y/2) &&
                Mouse.getY() > a.getPositionY() - a.scale.y/2) {
            return true;
        } else {
            return false;
        }
    }

    public boolean startGame() {
        if(isColliding(startButton) && Mouse.isButtonDown(0)) {
            return true;
        } else {
            return false;
        }
    }
    public boolean exitGame() {
        if(isColliding(quitButton) && Mouse.isButtonDown(0)) {
            return true;
        } else {
            return false;
        }
    }
}

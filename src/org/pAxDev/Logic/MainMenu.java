package org.pAxDev.Logic;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.pAxDev.Objects.Entity;
import org.pAxDev.Util.ImgLoader;

import java.io.FileNotFoundException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glMatrixMode;

/**
 * Created by Lance on 3/3/2016.
 */
public class MainMenu {
    Entity startButton;
    Entity quitButton;
    Entity setButton;



    public MainMenu(Vector3f pos, LoadTexture lt) {

        startButton = new Entity(pos, new Vector2f(160,110),new Vector4f(1,1,1,1), null );
        setButton = new Entity(new Vector3f(pos.x,pos.y-150,pos.z), new Vector2f(150,100),new Vector4f(1,1,1,1), null );
        quitButton = new Entity(new Vector3f(pos.x,pos.y-300,pos.z), new Vector2f(150,100),new Vector4f(1,1,1,1), null );
        Texture sbTex  = lt.getTexture(1);
        Texture stText = lt.getTexture(3);
        Texture qbTex  = lt.getTexture(2);
        startButton.texture = sbTex;
        setButton.texture   = stText;
        quitButton.texture = qbTex;

    }
    public void update() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 1366,0, 768, -5, 5);
        glMatrixMode(GL_MODELVIEW);
        if(isColliding(startButton)) {
            startButton.color = new Vector4f(1,1,1,1);
        } else {
            startButton.color = new Vector4f(0,1,0,.7f);
        }
        if(isColliding(setButton)) {
            setButton.color = new Vector4f(1,1,1,1);
        } else {
            setButton.color = new Vector4f(.5f,.5f,.8f,.7f);
        }
        if(isColliding(quitButton)) {
            quitButton.color = new Vector4f(1,1,1,1);
        } else {
            quitButton.color = new Vector4f(1,1,0,.7f);
        }
        startButton.drawTextured();
        setButton.drawTextured();
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

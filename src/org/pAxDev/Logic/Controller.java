package org.pAxDev.Logic;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.pAxDev.Objects.Entity;

import java.util.ArrayList;

/**
 * Created by Lance on 2/29/2016.
 */
public class Controller {
    public Entity player;
    Grid map;
    Sounds sound;
    public Integer score = 0;

    private boolean gameOver = false;
    private boolean allowMove = true;
    public int difficulty = 1;
    int raiseDif = 0;
    public int powerBoost = 0;
    ArrayList<GridSquare> oldTakenSquare = new ArrayList<>();
    public Controller(Entity player, Grid map, Sounds sound) {
        this.player = player;
        this.map = map;
        this.sound = sound;
    }
    public  void setGameOver(boolean b) {
        gameOver = b;
    }
    public boolean isGameOver() {
        return  gameOver;
    }

    private void input(int delta) {
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            player.setPositionY(player.getPositionY() + ((.3f * delta) / 2));
           player.inc = player.inc + .25f;
            player.rot = 180;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            player.setPositionY(player.getPositionY() - ((.3f * delta) / 2));
            player.inc = player.inc + .25f;
            player.rot = 0;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            player.setPositionX(player.getPositionX() + ((.3f * delta) / 2));
            player.inc = player.inc + .25f;
            player.rot = 90;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            player.setPositionX(player.getPositionX() - ((.3f * delta) / 2));
            player.inc = player.inc + .25f;
            player.rot = -90;
        }
    }
    public void update(int delta) {
     //check current grid type
        //get move direction requested
        // see what the next grid type is
        // allow move or not
        //move
        if (allowMove && !gameOver) {
            input(delta);
        }
        for (GridSquare[] gsa : map.getGridSquares()){
            for (GridSquare gs : gsa) {
                if (gs.collision.isColliding(player) && (gs.getGridType() != GridType.BORDER) && (gs.getGridType() != GridType.BLOCKER)) {
                   allowMove = false;
                } else {
                    allowMove = true;
                }
                if (gs.collision.isColliding(player) && (gs.getGridType() == GridType.TOKEN)) {
                    for (GridSquare os : oldTakenSquare) {

                            if (gs != os) {
                                powerBoost = powerBoost +1;
                                oldTakenSquare.add(gs);
                            }

                    }

                    gs.setGridType(GridType.TAKEN);
                    sound.playTakenSound();
                    score++;
                    gs.color = new Vector4f(0,1,0,1);
                    gs.setPosition(new Vector3f(gs.getPositionX(),gs.getPositionY(),4));
                    raiseDif++;
                    if (raiseDif == 4) {
                        difficulty++;
                        raiseDif = 0;
                    }
                }
                if ((gs.collision.isColliding(player) && (gs.getGridType() == GridType.BORDER)) || (gs.collision.isColliding(player) && (gs.getGridType() == GridType.BLOCKER))) {
                    player.color = new Vector4f(1,0,0,1);
                     player.rot = player.rot + 5;
                    player.scale = new Vector2f(player.scale.x + 0.1f,player.scale.y + 0.1f);
                    difficulty = 1;
                    if(gameOver == false) {
                        sound.playLoseSound();
                    }
                    gameOver = true;
                }
            }
        }

    }
}

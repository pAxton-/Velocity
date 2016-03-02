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

    private boolean gameOver = false;
    private boolean allowMove = true;
    public int difficulty = 1;
    public int powerBoost = 0;
    ArrayList<GridSquare> oldTakenSquare = new ArrayList<>();
    public Controller(Entity player, Grid map) {
        this.player = player;
        this.map = map;
    }
    public  void setGameOver(boolean b) {
        gameOver = b;
    }
    public boolean isGameOver() {
        return  gameOver;
    }

    private void input(int delta) {
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            player.setPositionY(player.getPositionY() + ((.1f * delta) / 2));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            player.setPositionY(player.getPositionY() - ((.1f * delta) / 2));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            player.setPositionX(player.getPositionX() + ((.1f * delta) / 2));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            player.setPositionX(player.getPositionX() - ((.1f * delta) / 2));
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
                    gs.color = new Vector4f(0,1,0,1);
                    gs.setPosition(new Vector3f(gs.getPositionX(),gs.getPositionY(),5));
                    difficulty++;
                }
                if ((gs.collision.isColliding(player) && (gs.getGridType() == GridType.BORDER)) || (gs.collision.isColliding(player) && (gs.getGridType() == GridType.BLOCKER))) {
                    player.color = new Vector4f(1,0,0,1);
                     player.rot = player.rot + 5;
                    player.scale = new Vector2f(player.scale.x + 0.1f,player.scale.y + 0.1f);
                    difficulty = 1;
                    gameOver = true;
                }
            }
        }

    }
}

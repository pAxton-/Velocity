package org.pAxDev.Logic;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.pAxDev.Objects.Entity;

/**
 * Created by Lance on 2/29/2016.
 */
public class Controller {
    public Entity player;
    Grid map;

    public Controller(Entity player, Grid map) {
        this.player = player;
        this.map = map;
    }

    public void update(int delta) {
     //check current grid type
        //get move direction requested
        // see what the next grid type is
        // allow move or not
        //move
        for (GridSquare[] gsa : map.getGridSquares()){
            for (GridSquare gs : gsa) {
                if (gs.collision.isColliding(player) && (gs.getGridType() != GridType.BORDER)) {
                    gs.color = new Vector4f(0,1,0,.5f);
                    gs.setPosition(new Vector3f(gs.getPositionX(),gs.getPositionY(),5));
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
            }
        }

    }
}

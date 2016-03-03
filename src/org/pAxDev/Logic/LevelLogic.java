package org.pAxDev.Logic;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.pAxDev.Objects.Entity;

import java.util.Random;

/**
 * Created by Lance on 3/2/2016.
 */
public class LevelLogic {
    Controller controller;
    Entity enty;
    Grid map;
    GridSquare[][] lgs;
    Random rand;
    double currentTime, currentTimeB;
    public LevelLogic(Controller controller, Entity enty, Grid map, GridSquare[][] lgs, Random rand, double currentTime, double currentTimeB) {
        this.lgs = lgs;
        this.rand = rand;
        this.currentTime = currentTime;
        this.currentTimeB = currentTimeB;
        this.controller = controller;
        this.enty = enty;
        this.map = map;
    }

    public void levelUpdate(int delta) {
        controller.update(delta);
        enty.draw();
        for (GridSquare[] gsa : map.getGridSquares()) {
            for (GridSquare gs : gsa) {
                gs.drawWire();
                // gs.collisionCheck(enty);
            }
        }
        if(getTime() > (currentTimeB + 1000/controller.difficulty) && !controller.isGameOver()){
            int  x = rand.nextInt(38) +1;
            int  y = rand.nextInt(38) +1;
            for (int i = 0; i < 8; i++) {
                x = rand.nextInt(38)+1;
                y = rand.nextInt(38)+1;
                if (lgs[x][y].getGridType() != GridType.TAKEN) {
                    lgs[x][y].color = new Vector4f(1, 0, 0, .5f);
                    lgs[x][y].setPosition(new Vector3f(lgs[x][y].getPositionX(), lgs[x][y].getPositionY(), 4));
                    lgs[x][y].setGridType(GridType.BLOCKER);
                }
            }
            for (int i = 0; i < 20; i++) {
                x = rand.nextInt(38)+1;
                y = rand.nextInt(38)+1;
                if (lgs[x][y].getGridType() != GridType.TAKEN) {
                    lgs[x][y].color = new Vector4f(1, 1, 1, .05f);
                    lgs[x][y].setPosition(new Vector3f(lgs[x][y].getPositionX(), lgs[x][y].getPositionY(), 1));
                    lgs[x][y].setGridType(GridType.EMPTY);
                }
                x = rand.nextInt(38)+1;
                y = rand.nextInt(38)+1;
                if (lgs[x][y].getGridType() != GridType.TAKEN) {
                    lgs[x][y].color = new Vector4f(1, 1, 1, .05f);
                    lgs[x][y].setPosition(new Vector3f(lgs[x][y].getPositionX(), lgs[x][y].getPositionY(), 1));
                    lgs[x][y].setGridType(GridType.EMPTY);
                }
                currentTimeB = getTime();
            }
        }
        if(getTime() > (currentTime + 1000/controller.difficulty ) && !controller.isGameOver()){
            for (int i = 0; i < 4; i++) {
                int x = rand.nextInt(38) + 1;
                int y = rand.nextInt(38) + 1;
                if (lgs[x][y].getGridType() != GridType.TAKEN) {
                    lgs[x][y].color = new Vector4f(0, 0, 1, 1);
                    lgs[x][y].setPosition(new Vector3f(lgs[x][y].getPositionX(), lgs[x][y].getPositionY(), 4));
                    lgs[x][y].setGridType(GridType.TOKEN);
                }
            }
            currentTime = getTime();
        }

        if (controller.isGameOver() && getTime() > (currentTime + 10000/controller.difficulty)  && getTime() > (currentTimeB + 10000/controller.difficulty) ) {
            for (GridSquare[] gsa : map.getGridSquares()) {
                for (GridSquare gs : gsa) {
                    if (gs.getGridType() != GridType.BORDER) {
                        gs.color = new Vector4f(1, 1, 1, .05f);
                        gs.setPosition(new Vector3f(gs.getPositionX(), gs.getPositionY(), 1));
                        gs.setGridType(GridType.EMPTY);
                        controller.player.scale = new Vector2f(10, 10);
                        controller.player.color = new Vector4f(1, 1, 1, 1);
                        controller.player.rot = 0;
                        controller.player.setPosition(new Vector3f(700,400,5));
                        controller.setGameOver(false);
                    }
                }
            }
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && controller.powerBoost > 0){
            for (int i = 0; i < 90; i++) {
                int x = rand.nextInt(38) + 1;
                int y = rand.nextInt(38) + 1;
                if (lgs[x][y].getGridType() != GridType.TAKEN) {
                    lgs[x][y].color = new Vector4f(1, 1, 1, .05f);
                    lgs[x][y].setPosition(new Vector3f(lgs[x][y].getPositionX(), lgs[x][y].getPositionY(), 1));
                    lgs[x][y].setGridType(GridType.EMPTY);
                }
            }
            controller.powerBoost = controller.powerBoost - 1;
            if (controller.powerBoost < 0) {
                controller.powerBoost = 0;
            }
        }

    }
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
}

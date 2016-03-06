package org.pAxDev.Logic;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.pAxDev.Objects.Entity;
import org.pAxDev.Util.ImgLoader;

import java.awt.Font;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glMatrixMode;

/**
 * Created by Lance on 3/2/2016.
 */
public class LevelLogic {
    private final TrueTypeFont font;
    private final boolean antiAlias = true;
    Sounds sound;
    Controller controller;
    Entity enty;
    Grid map;
    GridSquare[][] lgs;
    Random rand;
    int gridSize;
    public Integer score = 0;
    public boolean endGame = false;
    double currentTime, currentTimeB;

    Texture honeyTex;
    public LevelLogic(Sounds sound,Controller controller, Entity enty, Grid map, GridSquare[][] lgs, Random rand, double currentTime, double currentTimeB) {
        this.sound = sound;
        this.lgs = lgs;
        this.rand = rand;
        this.currentTime = currentTime;
        this.currentTimeB = currentTimeB;
        this.controller = controller;
        this.enty = enty;
        this.map = map;
        gridSize = map.getSize()-2;

        Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
        font = new TrueTypeFont(awtFont, antiAlias);
    }

    public void levelUpdate(int delta) {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 1366,0, 768, -5, 5);
        glMatrixMode(GL_MODELVIEW);
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) ) {
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, 1366,0, 768, -5, 5);
            glMatrixMode(GL_MODELVIEW);
            endGame = true;
            for (GridSquare[] gsa : map.getGridSquares()) {
                for (GridSquare gs : gsa) {
                    if (gs.getGridType() != GridType.BORDER) {
                        gs.color = new Vector4f(1, 1, 1, .05f);
                        gs.setPosition(new Vector3f(gs.getPositionX(), gs.getPositionY(), 1));
                        gs.setGridType(GridType.EMPTY);
                        controller.player.scale = new Vector2f(40, 40);
                        controller.player.color = new Vector4f(1, 1, 1, 1);
                        controller.player.rot = 0;
                        controller.player.setPosition(new Vector3f(700,400,5));
                        controller.setGameOver(false);
                    }
                }
            }
        }
        controller.update(delta);

        for (GridSquare[] gsa : map.getGridSquares()) {
            for (GridSquare gs : gsa) {
                switch (gs.getGridType()) {

                    case BLOCKER:
                        gs.drawPoly();

                        break;
                    case BORDER:
                        gs.drawPoly();

                        break;
                    case EMPTY:
                        gs.drawTri();

                        break;
                    case TOKEN:
                        gs.drawTextured();

                        break;
                    case TAKEN:
                        gs.drawPoly();

                        break;
                }
                /*
                    if (gs.getGridType() != GridType.EMPTY) {

                    gs.drawPoly();
                } else {
                    gs.drawTri();
                }
                // gs.collisionCheck(enty);
                */

            }


        }
        if(getTime() > (currentTimeB + 1000/controller.difficulty) && !controller.isGameOver()){
            sound.playSquareChangeSound();
            int  x = rand.nextInt(38) +1;
            int  y = rand.nextInt(38) +1;
            for (int i = 0; i < 8; i++) {
                x = rand.nextInt(gridSize)+1;
                y = rand.nextInt(gridSize)+1;
                if (lgs[x][y].getGridType() != GridType.TAKEN) {
                    lgs[x][y].color = new Vector4f(1, 0, 0, .5f);
                    lgs[x][y].setPosition(new Vector3f(lgs[x][y].getPositionX(), lgs[x][y].getPositionY(), 4));
                    lgs[x][y].setGridType(GridType.BLOCKER);
                }
            }
            for (int i = 0; i < 20; i++) {
                x = rand.nextInt(gridSize)+1;
                y = rand.nextInt(gridSize)+1;
                if (lgs[x][y].getGridType() != GridType.TAKEN) {
                    lgs[x][y].color = new Vector4f(1, 1, 1, .05f);
                    lgs[x][y].setPosition(new Vector3f(lgs[x][y].getPositionX(), lgs[x][y].getPositionY(), 1));
                    lgs[x][y].setGridType(GridType.EMPTY);
                }
                x = rand.nextInt(gridSize)+1;
                y = rand.nextInt(gridSize)+1;
                if (lgs[x][y].getGridType() != GridType.TAKEN) {
                    lgs[x][y].color = new Vector4f(1, 1, 1, .05f);
                    lgs[x][y].setPosition(new Vector3f(lgs[x][y].getPositionX(), lgs[x][y].getPositionY(), 1));
                    lgs[x][y].setGridType(GridType.EMPTY);
                }
                currentTimeB = getTime();
            }
        }
        if(getTime() > (currentTime + 1000/controller.difficulty ) && !controller.isGameOver()){
            for (int i = 0; i < 10; i++) {
                int x = rand.nextInt(gridSize) + 1;
                int y = rand.nextInt(gridSize) + 1;
                if (lgs[x][y].getGridType() != GridType.TAKEN) {
                    lgs[x][y].color = new Vector4f(1, 1, 1, 1);
                    lgs[x][y].setPosition(new Vector3f(lgs[x][y].getPositionX(), lgs[x][y].getPositionY(), 1));
                    lgs[x][y].setGridType(GridType.TOKEN);
                }
            }
            currentTime = getTime();
        }

        if (controller.isGameOver() && getTime() > (currentTime + 10000)  && getTime() > (currentTimeB + 10000) ) {
            for (GridSquare[] gsa : map.getGridSquares()) {
                for (GridSquare gs : gsa) {
                    if (gs.getGridType() != GridType.BORDER) {
                        gs.color = new Vector4f(1, 1, 1, .05f);
                        gs.setPosition(new Vector3f(gs.getPositionX(), gs.getPositionY(), 1));
                        gs.setGridType(GridType.EMPTY);
                        controller.player.scale = new Vector2f(60, 60);
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
                int x = rand.nextInt(gridSize) + 1;
                int y = rand.nextInt(gridSize) + 1;
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

        enty.drawSprite();
        Color.white.bind();
        score = controller.score * 10;

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 1366,768, 0, -5, 5);
        glMatrixMode(GL_MODELVIEW);
        font.drawString(1250, 700, "Score: " + score.toString(), Color.yellow);
    }
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
}

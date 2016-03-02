package org.pAxDev.Logic;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;

import org.pAxDev.Objects.Camera;
import org.pAxDev.Objects.Entity;

import org.pAxDev.Objects.Entity.Type;
import org.pAxDev.Util.ImgLoader;
import org.pAxDev.Util.Options;
import org.pAxDev.Util.Screen;

import java.util.Random;


public class Game {
	
	private static final Type PLAYER = null;
	static Game game;
	final String TITLE = "TITLE";
	final String VERSION = "0.0.1 pre-alpha";
	
	
	Options options = new Options();
	ImgLoader imgLoader = new ImgLoader();
	Screen screen;
	Camera cam1;
	
	
	Entity enty;
	Grid map;
	Controller controller;
	private long lastFrame;
		

	
	public void updateOptions(){
		
		options.readFile("options.cfg");
		
		
	}
	
	public void init(){
		
		screen = new Screen(options.screenWidth, options.screenHeight, options.frameCap, options.fullscreen, options.vSync, TITLE+" - "+VERSION);
		cam1 = new Camera(new Vector2f(0,0), new Vector2f(options.screenWidth, options.screenHeight));
		 enty =  new Entity(new Vector3f(400,300,1), new Vector2f(10,10), new Vector4f(1,1,1,1), PLAYER);
        map = new Grid(22,18.5f,options.screenWidth/5,25,40);
	    controller = new Controller(enty, map);
	
	}
	
	public void mainLoop(){

        GridSquare[][] lgs = map.getGridSquares();
        Random rand = new Random();

        double currentTime = getTime();
        double currentTimeB = getTime();
		while(!screen.isCloseRequested()){

            cam1.update();
            controller.update(getDelta());
			enty.draw();
            for (GridSquare[] gsa : map.getGridSquares()) {
                for (GridSquare gs : gsa) {
                    gs.drawWire();
                   // gs.collisionCheck(enty);
                }
            }
           if(getTime() > (currentTimeB + 1000) && !controller.isGameOver()){
                int  x = rand.nextInt(38) +1;
                int  y = rand.nextInt(38) +1;
               for (int i = 0; i < controller.difficulty; i++) {
                   x = rand.nextInt(38)+1;
                   y = rand.nextInt(38)+1;
                   if (lgs[x][y].getGridType() != GridType.TAKEN) {
                       lgs[x][y].color = new Vector4f(1, 0, 0, .5f);
                       lgs[x][y].setPosition(new Vector3f(lgs[x][y].getPositionX(), lgs[x][y].getPositionY(), 4));
                       lgs[x][y].setGridType(GridType.BLOCKER);
                   }
               }
               for (int i = 0; i < 10; i++) {
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
            if(getTime() > (currentTime + 10000 ) && !controller.isGameOver()){
               int x = rand.nextInt(38)+1;
                int y = rand.nextInt(38)+1;
                if (lgs[x][y].getGridType() != GridType.TAKEN) {
                    lgs[x][y].color = new Vector4f(0, 0, 1, 1);
                    lgs[x][y].setPosition(new Vector3f(lgs[x][y].getPositionX(), lgs[x][y].getPositionY(), 4));
                    lgs[x][y].setGridType(GridType.TOKEN);
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
                            controller.player.scale = new Vector2f(10, 10);
                            controller.player.color = new Vector4f(1, 1, 1, 1);
                            controller.player.rot = 0;
                            controller.setGameOver(false);
                        }
                    }
                }
            }
            if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
                if (controller.difficulty == 4) {
                    int x = rand.nextInt(38) + 1;
                    int y = rand.nextInt(38) + 1;
                    if (lgs[x][y].getGridType() != GridType.TAKEN) {
                        lgs[x][y].color = new Vector4f(1, 1, 1, .05f);
                        lgs[x][y].setPosition(new Vector3f(lgs[x][y].getPositionX(), lgs[x][y].getPositionY(), 1));
                        lgs[x][y].setGridType(GridType.EMPTY);
                    }
                }
            }

            if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				break;
			}
			screen.update();
		}
	}
	
	public void close(){
		screen.destroy();
	}
	
	public static void main(String[] args) {
		game = new Game();
		
		game.updateOptions();
		game.init();
		game.mainLoop();
		game.close();
	}
	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		
		return delta;
		}
		 
	
		public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
		}

}

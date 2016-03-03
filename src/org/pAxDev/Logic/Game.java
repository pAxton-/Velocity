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
	GameState gameState = GameState.MENU;
	
	Entity enty;
	Grid map;
	Controller controller;
    LevelLogic lvl1;
	private long lastFrame;
		

	
	public void updateOptions(){
		
		options.readFile("options.cfg");
		
		
	}
	
	public void init(){
		
		screen = new Screen(options.screenWidth, options.screenHeight, options.frameCap, options.fullscreen, options.vSync, TITLE+" - "+VERSION);
		cam1 = new Camera(new Vector2f(0,0), new Vector2f(options.screenWidth, options.screenHeight));
		 enty =  new Entity(new Vector3f(screen.width/2,screen.height/2,5), new Vector2f(10,10), new Vector4f(1,1,1,1), PLAYER);
        map = new Grid(22,18.5f,options.screenWidth/5,25,40);
	    controller = new Controller(enty, map);
	
	}
	
	public void mainLoop(){

        GridSquare[][] lgs = map.getGridSquares();
        Random rand = new Random();

        double currentTime = getTime();
        double currentTimeB = getTime();
        lvl1 = new LevelLogic(controller, enty, map, lgs, rand, currentTime, currentTimeB);
		while(!screen.isCloseRequested()){

            cam1.update();
            switch (gameState){

                case MENU:
                    if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
                        gameState = GameState.PLAYING;
                        break;
                    }
                    break;
                case PLAYING:
                    lvl1.levelUpdate(getDelta());
                    break;
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

package org.pAxDev.Logic;

import org.lwjgl.Sys;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;


import org.pAxDev.Objects.Camera;
import org.pAxDev.Objects.Entity;

import org.pAxDev.Objects.Entity.Type;

import org.pAxDev.Util.Options;
import org.pAxDev.Util.Screen;


import java.io.FileNotFoundException;
import java.util.Random;




public class Game {
	
	private static final Type PLAYER = null;
	static Game game;
	final String TITLE = "TITLE";
	final String VERSION = "0.0.1 pre-alpha";
	
	
	Options options = new Options();

    Sounds sound;
	Screen screen;
	Camera cam1;
	GameState gameState = GameState.MENU;
	MainMenu mMenu;
	Entity enty;
	Grid map;
	Controller controller;
    LevelLogic lvl1;
	private long lastFrame;

    boolean closeProgram = false;
	private Grid amp2;

	private Grid map2;


    public void updateOptions(){
		
		options.readFile("src/res/options.cfg");
		
		
	}
	
	public void init(){
		
		screen = new Screen(options.screenWidth, options.screenHeight, options.frameCap, options.fullscreen, options.vSync, TITLE+" - "+VERSION);
		cam1 = new Camera(new Vector2f(0,0), new Vector2f(options.screenWidth, options.screenHeight));
        LoadTexture lt = new LoadTexture();
        sound = new Sounds();
        enty =  new Entity(new Vector3f(screen.width/2,screen.height/2,5), new Vector2f(60,60), new Vector4f(1,1,1,1), PLAYER);
        enty.texture =  lt.getTexture(4);
        map = new Grid(22,18.5f,options.screenWidth/5,25,40, lt);
		map2 = new Grid(44,37f,options.screenWidth/6,25,20, lt);
		mMenu = new MainMenu(new Vector3f(screen.width/2,screen.height/2,1), lt);
	    controller = new Controller(enty, map2, sound);
        try {
            sound.init();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
	
	public void mainLoop(){

        GridSquare[][] lgs = map2.getGridSquares();
        Random rand = new Random();

        double currentTime = getTime();
        double currentTimeB = getTime();
        lvl1 = new LevelLogic(sound,controller, enty, map2, lgs, rand, currentTime, currentTimeB);
		while(!screen.isCloseRequested()){

            cam1.update();
            switch (gameState){

                case MENU:

                    mMenu.update();
                    if(mMenu.startGame()) {
                        sound.playButtonSound();
                        gameState = GameState.PLAYING;
                        lvl1.endGame = false;
                        lvl1.controller.difficulty = 1;
                    }
                        if (mMenu.exitGame()){
                            closeProgram = true;
                        }

                    break;
                case PLAYING:

                    if(!lvl1.endGame) {
                        lvl1.levelUpdate(getDelta());
                    } else {
                        gameState = GameState.MENU;
                    }
                    break;
            }


            if(closeProgram){
				break;
			}
			screen.update();
		}
	}
	
	public void close(){
		sound.destroy();
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

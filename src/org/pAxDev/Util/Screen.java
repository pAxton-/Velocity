package org.pAxDev.Util;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;


public class Screen {
	
	public int width, height, frameCap;
	boolean fullscreen, vSync;
	String title;
	DisplayMode[] modes;
	DisplayMode displayMode;
	
	public Screen(){}
	
	public Screen(int width, int height, int frameCap, boolean fullscreen, boolean vSync, String title){
		this.width = width;
		this.height = height;
		this.frameCap = frameCap;
		this.fullscreen = fullscreen;
		this.vSync = vSync;
		this.title = title;
		create(width, height, fullscreen, vSync, title);
	}
	
	public void create(int width, int height, boolean fullscreen, boolean vSync, String title){
		try {
			modes = Display.getAvailableDisplayModes();
			for(int i = 0; i < modes.length; i++){
				if(modes[i].getWidth() == width && modes[i].getHeight() == height && modes[i].isFullscreenCapable()){
					displayMode = modes[i];
				}
			}
			Display.setDisplayMode(displayMode);
			Display.setTitle(title);
			Display.setFullscreen(fullscreen);
			Display.setVSyncEnabled(vSync);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.out.println("COULD NOT CREATE WINDOW!");
		}
		System.out.println("Display initialized successfully:");
		System.out.println("	width: "+width);
		System.out.println("	height: "+height);
		System.out.println("	fullscreen: "+fullscreen);
		System.out.println("	vSync: "+vSync);
		System.out.println();
	}
	
	public void update(){
		Display.sync(frameCap);
		Display.update();
	}
	
	public void destroy(){
		Display.destroy();
	}
	
	public boolean isCloseRequested(){
		return Display.isCloseRequested();
	}
}

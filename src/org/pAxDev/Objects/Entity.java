package org.pAxDev.Objects;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

public class Entity {
	
	
	private Vector3f position;
	public Vector2f scale;
	public float rot;
	public Vector4f color;
	public Texture texture = null;
	
	
	
	float appliedForce;
	float friction;
	
	long lastFrame;
	
	int i = 0;
	
	boolean active = false;
	public float inc = 0;
	public Type type;
	private Type PLAYER;
	private Type ENEMY;
	private Type LAND;
	private Type PROJECTILE;
	private Type STATIC;
	
	
	public Entity(Vector3f position, Vector2f scale, Vector4f color, Type type){
		this.setPosition(position);
		this.scale = scale;
		this.color = color;
		this.type = type;
		
	}
	public enum Type {
		PLAYER, ENEMY, STATIC, PROJECTILE, LAND
	}
	
	boolean isMouseOver(Camera cam){
		
		
		return false;
	}
	
	boolean isOnClicked(Camera cam, int button){
		
		if(isMouseOver(cam)) {
			active = true;
			}
	
		if(Mouse.isButtonDown(button) && active){
			return true;
		}
		if(!Mouse.isButtonDown(button)){
			active = false;
		}
		return false;
	}
	
	public void draw(){
		  glDisable(GL_TEXTURE_2D);
		glPushMatrix();
			glTranslatef(position.x	, position.y, position.z);
			glRotatef(rot, 0, 0, 1);
			glScalef(scale.x, scale.y, 0);
			glColor4f(color.x, color.y, color.z, color.w);
			glBegin(GL_QUADS);
				glVertex2f(-0.5f, -0.5f);
				glVertex2f(-0.5f, 0.5f);
				glVertex2f(0.5f, 0.5f);
				glVertex2f(0.5f, -0.5f);
			glEnd();
		glPopMatrix();
		
	
	}

	public void drawWire(){
        glDisable(GL_TEXTURE_2D);
        glPushMatrix();
        glTranslatef(position.x, position.y, position.z);
        glRotatef(rot, 0, 0, 1);
        glScalef(scale.x, scale.y, 0);
        glColor4f(color.x, color.y, color.z, color.w);
        glBegin(GL_LINES);

        glVertex2f(-0.5f, -0.5f);
        glVertex2f(-0.5f, 0.5f);

        glVertex2f(-0.5f, -0.5f);
        glVertex2f(0.5f, -0.5f);

        glVertex2f(0.5f, 0.5f);
        glVertex2f(0.5f, -0.5f);

        glVertex2f(-0.5f, 0.5f);
        glVertex2f(0.5f, 0.5f);

        glEnd();
        glPopMatrix();


	}

	public void drawTri(){
		glDisable(GL_TEXTURE_2D);
		glPushMatrix();
		glTranslatef(position.x, position.y, position.z);
		glRotatef(rot, 0, 0, 1);
		glScalef(scale.x, scale.y, 0);
		glColor4f(color.x, color.y, color.z, color.w);
		glBegin(GL_POLYGON);

		glVertex2f(-0.5f, -0.5f);
	//	glVertex2f(-0.5f, 0.5f);

		glVertex2f(-0.5f, -0.5f);
	//	glVertex2f(0.5f, -0.5f);

		glVertex2f(0.5f, 0.5f);
	//	glVertex2f(0.5f, -0.5f);

		glVertex2f(-0.5f, 0.5f);
	//	glVertex2f(0.5f, 0.5f);

		glEnd();
		glPopMatrix();


	}

	public void drawPoly(){
		glDisable(GL_TEXTURE_2D);
		glPushMatrix();
		glTranslatef(position.x, position.y, position.z);
		glRotatef(rot, 0, 0, 1);
		glScalef(scale.x, scale.y, 0);
		glColor4f(color.x, color.y, color.z, color.w);

		glBegin(GL_POLYGON);

		glVertex2f(-0.5f, -0.5f);

		glVertex2f(0.5f, -0.5f);

		glVertex2f(0.5f, 0.5f);

		glVertex2f(-0.5f, 0.5f);


		glEnd();
		glPopMatrix();


	}


	public void drawTextured(){
		glPushMatrix();
		glTranslatef(getPositionX(), getPositionY(), getPositionZ());
		glRotatef(rot, 0, 0, 1);
		glScalef(scale.x, scale.y, 0);
		glColor4f(color.x, color.y, color.z, color.w);

		texture.bind();
		
		glBegin(GL_POLYGON);
			glTexCoord2f(0, 0);
			//glVertex3f(-0.5f, -0.5f, 0);
        glVertex3f(-.5f, .5f, 0);
			glTexCoord2f(1, 0);
			//glVertex3f(-0.5f, 0.5f, 0);
        glVertex3f(.5f, .5f, 0);
			glTexCoord2f(1, 1);
			//glVertex3f(0.5f, 0.5f, 0);
        glVertex3f(.5f, -.5f, 0);
			glTexCoord2f(0, 1);
			//glVertex3f(0.5f, -0.5f, 0);
        glVertex3f(-.5f, -.5f, 0);
		glEnd();
		
		
	glPopMatrix();
	}
	public void drawSprite(){
		
	     
		
		glPushMatrix();
	
		glTranslatef(getPositionX(), getPositionY(), getPositionZ());
		glRotatef(rot, 0, 0, 1);
		glScalef(scale.x, scale.y, 0);
		glColor4f(color.x, color.y, color.z, color.w);
		
		
		texture.bind();
		
		
		glBegin(GL_QUADS);
			
			
			
			glTexCoord2f(inc, 0);
			glVertex2f(0.25f, 0);
			
			glTexCoord2f(inc,0.25f);
			glVertex2f(0.25f, 0.25f);
			
			glTexCoord2f(inc +0.25f, 0.25f);
			glVertex2f(0.25f+0.25f,0.25f);
			
			glTexCoord2f(inc+0.25f, 0);
			glVertex2f(0.25f+0.25f, 0);
		glEnd();
		
		
	glPopMatrix();
	
	}
	
	

	public void input(int delta){
		int dt = delta;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			
				push(dt);
		}
		
		if (!Keyboard.isKeyDown(Keyboard.KEY_D)) { 
			
			
			
			
		}
		
	}
	
	public void update(int delta){
		int dt = delta;
		
		//input(dt);
		if(texture == null){
			draw();
		}else{
			drawTextured();
		}
	}
	
	
	public void push(int delta) {
		long dt = delta ;
		float go = (float) (dt^2);
		position.x = getPositionX() + .01f * go++;
	
		
	}

	public int getType() {
		if(type == PLAYER){
			return 1;
		}
		if(type == ENEMY){
			return 2;
		}
		if(type == LAND){
			return 3;
		} 
		if(type == PROJECTILE){
			return 4;
		} 
		if(type == STATIC){
			return 5;
		} else {
		return 0;
		}
		
	}
	public void setPosition(Vector3f position){
		this.position = position;
	}

	public float getPositionX() {
		return position.x;
	}

	public void setPositionX(float x) {
		this.position.x = x;
	}
	public void setPositionY(float y) {
		this.position.y = y;
	}
	public float getPositionZ() {
		return position.z;
	}

	public float getPositionY() {
		return position.y;
	}

}

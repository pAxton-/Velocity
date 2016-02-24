package org.pAxDev.Logic;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.pAxDev.Objects.Entity;

/**
 * Created by Lance Paxton on 2/23/16.
 */
public class GridSquare extends Entity {
    Collision collision = new Collision();
    public GridSquare(Vector3f position, Vector2f scale, Vector4f color, Type type) {
        super(position, scale, color, type);
    }

    public void collisionCheck(Entity ent) {
        if (collision.isColliding(ent)) {
            super.color = new Vector4f(1,1,1,1);
            setPosition(new Vector3f(getPositionX(),getPositionY(),5));
        } else {
            super.color = new Vector4f(0,0,1,1);
            setPosition(new Vector3f(getPositionX(),getPositionY(),0));
        }
    }
     class Collision {

        public boolean isColliding(Entity a) {
            if (a.getPositionX() < (getPositionX() + scale.x/2) &&
                        a.getPositionX() > (getPositionX() - scale.x/2) &&
                        a.getPositionY() < (getPositionY() + scale.y/2) &&
                        a.getPositionY() > getPositionY() - scale.y/2) {
                return true;
            } else {
                return false;
            }
        }
    }
}

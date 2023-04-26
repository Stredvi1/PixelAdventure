package entity;

import map.MapBuilder;
import map.Position;

import static org.lwjgl.opengl.GL11.*;

public class Building extends Entity{

    private float setHigher = 0;

    public Building(Position leftCornerPos, String textureName, int parcelWidth, float setHigher) {
        this.pos = leftCornerPos;
        init("Buildings/" + textureName);
        this.size = parcelWidth * MapBuilder.MAP_SIZE + 1;
        this.setHigher = setHigher;
    }

    public void render() {

        texture.bind();

        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();

        glTranslatef(pos.toMap()[0] - 1, pos.toMap()[1] + setHigher, 1);


        glBegin(GL_QUADS);

        float height = size / 25 * 16;

        glTexCoord2f(0, 0);
        glVertex2f(0, 0);

        glTexCoord2f(1, 0);
        glVertex2f(size, 0);

        glTexCoord2f(1, 1);
        glVertex2f(size, height);

        glTexCoord2f(0, 1);
        glVertex2f(0, height);

        glEnd();
        glPopMatrix();

    }
}

package entity;

import lwjglutils.OGLTexture2D;
import map.MapBuilder;
import map.Position;

import static org.lwjgl.opengl.GL11.*;

public class Item {

    private Position pos;
    private OGLTexture2D texture;
    private float size;
    private boolean isPickedUp = false;


    public Item(String file, float size) {
        this.size = size;
        pos = new Position(4, 4, MapBuilder.MAP_SIZE);

        init(file);
    }

    private void init(String file) {
        try {
            texture = new OGLTexture2D(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void render() {

        if(!isPickedUp) {

            texture.bind();

            glMatrixMode(GL_MODELVIEW);
            glPushMatrix();
            glLoadIdentity();

            glTranslatef(pos.toMap()[0] + 1, pos.toMap()[1] + 1, 1);


            glBegin(GL_QUADS);

            glTexCoord2f(0, 0);
            glVertex2f(0, 0);

            glTexCoord2f(1, 0);
            glVertex2f(size, 0);

            glTexCoord2f(1, 1);
            glVertex2f(size, size);

            glTexCoord2f(0, 1);
            glVertex2f(0, size);

            glEnd();
            glPopMatrix();
        }
    }


    public void setPosition(Position pos) {
        this.pos = pos;
    }

    public void pickUp(Position pos) {
        System.out.println(this.pos.equals(pos));

        if (this.pos.equals(pos)) {
            this.isPickedUp = true;
        }
    }
}

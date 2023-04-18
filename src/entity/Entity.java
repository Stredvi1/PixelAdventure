package entity;

import lwjglutils.OGLTexture2D;
import map.Position;

import static org.lwjgl.opengl.GL11.*;

public abstract class Entity {

    protected OGLTexture2D texture;
    protected float size = 5;
    protected float scale = 0.6f;
    protected Position pos;


    protected void init(String textureName) {

        try {
            texture = new OGLTexture2D("textures/" + textureName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void render() {

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

    public void setPosition(Position pos) {
        this.pos = pos;
    }

}

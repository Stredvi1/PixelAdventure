package entity;

import lwjglutils.OGLTexture2D;
import map.Position;


import static lwjglutils.GlutUtils.glutSolidCube;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;


public class Bob {

    private OGLTexture2D texture;
    private float size = 5;
    private float scale = 0.6f;
    private Position pos;

    public Bob(Position pos) {
        this.pos = pos;

        init();
    }

    public Bob(Position pos, int mapSize) {
        this.pos = pos;
        this.size = mapSize * scale;

        init();
    }


    private void init() {

        try {
            texture = new OGLTexture2D("textures/bob.png");
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

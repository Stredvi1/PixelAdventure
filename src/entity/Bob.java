package entity;

import lwjglutils.OGLTexture2D;


import static lwjglutils.GlutUtils.glutSolidCube;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;


public class Bob {

    private float[] position = new float[2];
    private OGLTexture2D texture;
    private int size = 5;

    public Bob(float x, float y) {
        this.position[0] = x * 5;
        this.position[1] = y * 5;

        init();
    }

    public Bob(float x, float y, int MapSize) {
        this.position[0] = x * 10;
        this.position[1] = y * 10;
        this.size = MapSize - 2;

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

        glTranslatef(position[0], position[1], 1);
        glRotatef(-90, 0, 0, 1);



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

    public void setPosition(float[] pos) {
        this.position = pos;
    }
}

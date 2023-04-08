package entity;

import lwjglutils.OGLTexture2D;


import static lwjglutils.GlutUtils.glutSolidCube;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;


public class Bob {

    private float[] position = new float[3];
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

//
//        glMatrixMode(GL_MODELVIEW);
//        glPushMatrix();
//        glLoadIdentity();
//        glEnable(GL_TEXTURE_2D);
//
//        glEnable(GL_BLEND);
//        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//        glBegin(GL_QUADS);
//
//        float startX = position[0] - size / 2f;
//        float startY = position[1] - size / 2f;
//
//        float endX = position[0] + size / 2f;
//        float endY = position[1] + size / 2f;
//
//        texture.bind();
//
//        int paramTexApp = GL_REPLACE;
//        int paramTex = GL_CLAMP_TO_EDGE;
//
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, paramTex);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, paramTex);
//        glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, paramTexApp);
////        glTranslatef(startX, startY, 9);
////        glutSolidCube(size);
//
//
//        double p = 10;
//
//        glTexCoord2d(0, 0);
//        glVertex2d(0, 0);
//
//        glTexCoord2d(0, 1);
//        glVertex2d(0, 1);
//
//        glTexCoord2d(1, 1);
//        glVertex2d(1,1);
//
//        glTexCoord2d(1, 0);
//        glVertex2d(1,0);
//
//        glEnd();
//
//        glPopMatrix();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        texture.bind();

        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();

        glTranslatef(position[0], position[1], 1);

        glBegin(GL_QUADS);

        glTexCoord2f(1, 1);
        glVertex2f(0, 1);

        glTexCoord2f(1, 0);
        glVertex2f(0, -1);

        glTexCoord2f(0, 0);
        glVertex2f(1, 0);

        glTexCoord2f(0, 1);
        glVertex2f(1, 1);

        glEnd();

        glPopMatrix();
    }
}

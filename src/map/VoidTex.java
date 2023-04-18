package map;

import lwjglutils.OGLTexture2D;

import static org.lwjgl.opengl.GL11.*;

public class VoidTex {

    private Position pos;
    private OGLTexture2D texture;
    private int width, height;


    public VoidTex(Position pos) {
        this.pos = pos;

        width = 20 * MapBuilder.MAP_SIZE;
        height = 20 * MapBuilder.MAP_SIZE;
        init();
    }

    private void init() {
        try {
            texture = new OGLTexture2D("textures/void.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void render() {

        texture.bind();

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);

        glMatrixMode(GL_TEXTURE);
        glPushMatrix();
        glLoadIdentity();
        glScalef(8f, 8f, -1);

        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();


        glTranslatef(pos.toMap()[0], pos.toMap()[1], -1);


        glBegin(GL_QUADS);

        glTexCoord2f(-1, -1);
        glVertex2f(-(width/2f), -(height/2f));

        glTexCoord2f(1, -1);
        glVertex2f((width/2f), -(height/2f));

        glTexCoord2f(1, 1);
        glVertex2f((width/2f), (height/2f));

        glTexCoord2f(-1, 1);
        glVertex2f(-(width/2f), (height/2f));

        glEnd();
        glPopMatrix();

        glMatrixMode(GL_TEXTURE);
        glPopMatrix();

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
    }

    public void setPosition(Position position) {
        this.pos = position;
    }
}

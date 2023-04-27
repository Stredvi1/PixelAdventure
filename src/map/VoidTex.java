package map;

import lwjglutils.OGLTexture2D;

import static org.lwjgl.opengl.GL11.*;

public class VoidTex {

    private Position pos;
    private OGLTexture2D texture;
    private int width, height;


    public VoidTex(Position pos, int mapWidth, int mapHeight) {
        this(pos, "textures/sky.png", mapWidth, mapHeight);
    }

    public VoidTex(Position pos, String fileName, int mapWidth, int mapHeight) {
        this.pos = pos;

        width = mapWidth * 2 * MapBuilder.MAP_SIZE;
        height = mapHeight * 2 * MapBuilder.MAP_SIZE;
        init(fileName);
    }

    private void init(String fileName) {
        try {
            texture = new OGLTexture2D(fileName);
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
        glScalef(8f, 8f, 8f);

        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();


//        glTranslatef(pos.toMap()[0], pos.toMap()[1], -20);
        glTranslatef(40 + pos.toMap()[0] / 10, -pos.toMap()[1] / 10 - 40, 0);


        glBegin(GL_QUADS);

        int x = width / 2;

        glTexCoord2f(-1, -1);
        glVertex2f(-x, -x);

        glTexCoord2f(1, -1);
        glVertex2f(x, -x);

        glTexCoord2f(1, 1);
        glVertex2f(x, x);

        glTexCoord2f(-1, 1);
        glVertex2f(-x, x);

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

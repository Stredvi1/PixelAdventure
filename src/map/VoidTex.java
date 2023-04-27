package map;

import lwjglutils.OGLTexture2D;

import static org.lwjgl.opengl.GL11.*;

public class VoidTex {

    private Position pos;
    private OGLTexture2D texture;
    private int width, height;
    private int mapWidth, mapHeight;


    public VoidTex(Position pos, int mapWidth, int mapHeight) {
        this(pos, "textures/sky.png", mapWidth, mapHeight);
    }

    public VoidTex(Position pos, String fileName, int mapWidth, int mapHeight) {
        this.pos = pos;

        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
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



        if(mapWidth < 10) {
            mapWidth = 6;
        }

        glTranslatef(mapWidth * 2 + pos.toMap()[0] / 10, -pos.toMap()[1] / 10 - mapWidth * 2, 0);


        glBegin(GL_QUADS);
        int x;
        if( mapWidth < 10) {
            x = width;
        } else {
            x = width / 2;
        }

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

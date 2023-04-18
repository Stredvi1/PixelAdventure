package entity;

import lwjglutils.OGLTextRenderer;
import lwjglutils.OGLTexture2D;
import map.MapBuilder;
import map.Position;
import window.Window;

import java.awt.*;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class Message {

    private String text;
    private Position pos;
    private float size = MapBuilder.MAP_SIZE * 16 / 2f;
    private float height = MapBuilder.MAP_SIZE * 2;
    private float padding = 1.2f;
    private OGLTextRenderer textRenderer;

    public Message(String text) {
        this.text = text;
        init();
    }

    private void init() {
        textRenderer = new OGLTextRenderer(Window.WIDTH, Window.HEIGHT);
        textRenderer.setColor(Color.black);
        textRenderer.setBackgroundColor(Color.white);
    }

    public void show() {



        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();

//        float x = pos.toMap()[0] + size / 2f;
//        float y = pos.toMap()[1] + MapBuilder.MAP_SIZE * 1.2f;

        glTranslatef(size, -2*height, 2);

        glDisable(GL_TEXTURE_2D);
        glColor3f(0.2f, 0.2f, 0.2f);
        glBegin(GL_QUADS);

        glTexCoord2f(0, 0);
        glVertex2f(-size, 0);

        glTexCoord2f(1, 0);
        glVertex2f(size, 0);

        glTexCoord2f(1, 1);
        glVertex2f(size, height);

        glTexCoord2f(0, 1);
        glVertex2f(-size, height);

        glEnd();
        glPopMatrix();

        glEnable(GL_TEXTURE_2D);
        textRenderer.addStr2D(Window.WIDTH/2, Window.HEIGHT/2, text);
    }
}

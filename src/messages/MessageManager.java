package messages;

import lwjglutils.OGLTextRenderer;
import map.MapBuilder;
import map.Position;
import render.Renderer;
import window.Window;

import java.awt.*;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

public class MessageManager {

    private final OGLTextRenderer textRenderer;
    private ArrayList<Message> messages;
    private int parcelRadius = 1;
    private Color bgColor = new Color(0xe4ad00);

    private float size = MapBuilder.MAP_SIZE * 16 / 2f;
    private float height = MapBuilder.MAP_SIZE * 2;

    private int currentIndex = 0;


    public MessageManager(OGLTextRenderer textRenderer) {
        this.textRenderer = textRenderer;
        messages = new ArrayList<>();

        init();
    }

    private void init() {
        textRenderer.setBackgroundColor(bgColor);
        textRenderer.setColor(Color.white);
    }

    public void next(Position pos) {
        if (!messages.get(currentIndex).shouldCheckPos()) {
            show(pos);
        } else {
            if (messages.get(currentIndex).getPos().withinRadius(pos, parcelRadius)) {
                show(pos);
            }
        }
        increaseIndex();
    }

    public void showMessage(Position pos) {
        if (!messages.get(currentIndex).shouldCheckPos()) {
            show(pos);
        } else {
            if (messages.get(currentIndex).getPos().withinRadius(pos, parcelRadius)) {
                show(pos);
            }
        }
    }

    public void addMessage(String author, String text, Position pos) {
        messages.add(new Message(author, text, pos));
    }

    public void addBobMessage(String text, Position pos) {
        messages.add(new Message(text, pos));
    }

    public void addBobMessage(String text) {
        messages.add(new Message(text));
    }

    private void increaseIndex() {
        if (currentIndex < messages.size() - 1) {
            currentIndex++;
        }
    }

    private void show(Position pos) {

        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();

        glTranslatef(pos.toMap()[0], pos.toMap()[1] - height * 2, 2);
        glDisable(GL_TEXTURE_2D);
        glColor3f(bgColor.getRed() / 255f, bgColor.getGreen() / 255f, bgColor.getBlue() / 255f);
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
        textRenderer.addStr2D(Renderer.WIDTH / 20, (int) (Renderer.HEIGHT - Renderer.HEIGHT * 0.1), messages.get(currentIndex).toString());
    }


    public void setRadius(int parcelRadius) {
        this.parcelRadius = parcelRadius;
    }
}

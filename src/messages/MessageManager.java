package messages;

import gameStuff.Sound;
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
    private Color bgColor = Renderer.mainColor;

    private float size = MapBuilder.MAP_SIZE * 16 / 2f;
    private float height = MapBuilder.MAP_SIZE * 2;

    private int currentIndex = 0;
    private Sound bip = new Sound("audio/sounds/message.ogg", false, true);
    private boolean isLastMessage = false;

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
            bip.play();
        } else {
            isLastMessage = true;
        }
    }

    private void show(Position pos) {

        if(!isLastMessage) {

            glDisable(GL_TEXTURE_2D);
            glMatrixMode(GL_MODELVIEW);
            glPushMatrix();
            glLoadIdentity();

            glTranslatef(pos.toMap()[0], pos.toMap()[1] - height * 2, 2);

            glColor3f(bgColor.getRed() / 255f, bgColor.getGreen() / 255f, bgColor.getBlue() / 255f);
            glBegin(GL_QUADS);

            glVertex2f(-size, 0);
            glVertex2f(size, 0);
            glVertex2f(size, height);
            glVertex2f(-size, height);

            glEnd();


            glEnable(GL_TEXTURE_2D);
            glPopMatrix();
            textRenderer.addStr2D(Renderer.WIDTH / 20, (int) (Renderer.HEIGHT - Renderer.HEIGHT * 0.1), messages.get(currentIndex).toString());
        }
    }


    public void setRadius(int parcelRadius) {
        this.parcelRadius = parcelRadius;
    }

    public boolean isLastMessagege() {
        return isLastMessage;
    }
}

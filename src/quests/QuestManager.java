package quests;

import gameStuff.Sound;
import lwjglutils.OGLTextRenderer;
import map.MapBuilder;
import map.Position;
import render.Renderer;

import java.awt.*;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glLoadIdentity;

public class QuestManager {

    private ArrayList<Quest> quests;
    private OGLTextRenderer textRenderer;

    private float windowWidth = MapBuilder.MAP_SIZE * 10;
    private float windowHeight = MapBuilder.MAP_SIZE * 5;
    private Color bgColor = Renderer.mainColor;

    private int halfWidth = Renderer.WIDTH / 2;
    private int textX;
    private int textY = (int) (Renderer.HEIGHT * 0.1);
    private int spacing = Renderer.HEIGHT / 10;
    private boolean toggle = false;

    private boolean addedRequest = false;
    private int counter = 0;
    private int displayTime = 300;

    private float letterSize = Renderer.WIDTH * 0.012f;

    private Sound finished;

    public QuestManager(OGLTextRenderer textRenderer) {
        this.textRenderer = textRenderer;
        this.quests = new ArrayList<>();
        finished = new Sound("audio/sounds/finished.ogg", false);
    }

    public void toggle() {
        toggle = !toggle;
    }

    public void addQuest(Quest quest) {
        addedRequest = true;
        quests.add(quest);
    }

    public void finishQuest(int questID) {
        Quest questToRemove = null;
        for (Quest q : quests) {
            if (q.getID() == questID) {
                questToRemove = q;
                break;
            }
        }
        if (questToRemove != null) {
            quests.remove(questToRemove);
            System.out.println("FINISHED QUEST");
            finished.play();
        } else {
            assert false : "Quest s ID " + questID + " neexistuje";
        }
    }

    public void render(Position pos) {
        glDisable(GL_TEXTURE_2D);
        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();

        if (toggle) {
            addedRequest = false;
            renderBg(pos);

            renderQuests(pos);


        } else if (addedRequest) {
            renderIcon(pos);
        }
        glPopMatrix();
        glEnable(GL_TEXTURE_2D);
    }

    private void renderIcon(Position pos) {
        if (counter < displayTime) {
            counter++;
        } else {
            addedRequest = false;
            counter = 0;
            return;
        }

        glPushMatrix();
        glLoadIdentity();


        glEnable(GL_TEXTURE_2D);

        int x = (int)(Renderer.WIDTH - 3 * letterSize - Renderer.WIDTH * 0.067);
        int y = (int) (Renderer.HEIGHT - Renderer.HEIGHT * 0.093);

        textRenderer.setColor(Renderer.mainColor);
        textRenderer.setBackgroundColor(Color.white);

        textRenderer.addStr2D(x, y, "TAB");

        textRenderer.setColor(Color.white);
        textRenderer.setBackgroundColor(Renderer.mainColor);

        glPopMatrix();
        glDisable(GL_TEXTURE_2D);
    }

    private void renderQuests(Position pos) {
        glEnable(GL_TEXTURE_2D);

        textX = (int) (halfWidth - 5.5 * letterSize);
        textRenderer.addStr2D(textX, textY, "Q U E S T Y");

        int i = 1;
        for(Quest q : quests) {
            textX = (int) (halfWidth - (q.get().length() / 2 * letterSize));
            textRenderer.addStr2D(textX, textY + spacing * i++, q.get());

        }
        glDisable(GL_TEXTURE_2D);

    }

    private void renderBg(Position pos) {
        glPushMatrix();

        glColor4f(bgColor.getRed() / 255f, bgColor.getGreen() / 255f, bgColor.getBlue() / 255f, 0.8f);

        glTranslatef(pos.toMap()[0] + windowWidth / 4, pos.toMap()[1] + windowHeight / 4, 0);
        glBegin(GL_QUADS);
        glVertex3f(-windowWidth, -windowHeight, 5);
        glVertex3f(windowWidth, -windowHeight, 5);
        glVertex3f(windowWidth, windowHeight, 5);
        glVertex3f(-windowWidth, windowHeight, 5);
        glEnd();

        glPopMatrix();
    }

    public void setLetterSize(float scale) {
        this.letterSize = scale;
    }

    public void resize(int width, int height) {
        this.halfWidth = width / 2;
        this.textY = (int) (height * 0.1);
        letterSize = width * 0.012f;
        spacing = height / 10;
    }

    public boolean hasQuest(int questID) {
        for(Quest q : quests) {
            if(q.getID() == questID){
                return true;
            }
        }
        return false;
    }


}

package gameStuff;

import map.MapBuilder;
import map.Position;
import render.Renderer;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.glfwSetTime;
import static org.lwjgl.opengl.GL11.*;


public class DamageBar {

    private final int barWidth = 10;
    private final int barHeight = 1;
    private final int barX = (800 - barWidth) / 2;
    private final int barY = 550;
    private final int barBorderWidth = 2;
    private final int barBorderHeight = 2;

    private Color bossHealthCol = new Color(0xdb1b04);
    private Color bossHealthOutlineCol = new Color(0x871b0e);
    private int bossHealth = 100;
    private int playerHealth = 100;

    private boolean hit = false;


    private float dmgBarWidth = MapBuilder.MAP_SIZE * 5;
    private float dmgBarHeight = MapBuilder.MAP_SIZE * 0.26f;
    private Color bgColor = new Color(0x333131);

    private Color middleColor = new Color(0xffffff);
    private float middleHeight = dmgBarHeight * 1.2f;
    private float middleWidth = dmgBarWidth * 0.02f;

    private Color outline = new Color(0x4f4b4a);
    private Color cursor = Renderer.mainColor;

    private float cursorPos = 0;
    private float cursorSpeed = 0.6f;
    private boolean right = true;



    public void renderHealthBars(Position pos) {
//        int playerBarWidth = (int) ((double) playerHealth / 100 * barWidth);
        float bossBarWidth = bossHealth / 100f * barWidth;

        glPushMatrix();
        glLoadIdentity();

        glTranslatef(pos.toMap()[0] + 4 * MapBuilder.MAP_SIZE, pos.toMap()[1] + 3 * MapBuilder.MAP_SIZE, 3);


        glColor3f(bossHealthCol.getRed() / 255f, bossHealthCol.getGreen() / 255f, bossHealthCol.getBlue() / 255f);
        glBegin(GL_QUADS);
        glVertex3f(0,0, 2);
        glVertex3f(bossBarWidth, 0, 2);
        glVertex3f(bossBarWidth, barHeight, 2);
        glVertex3f(0, barHeight, 2);

        glEnd();

        glColor3f(bossHealthOutlineCol.getRed() / 255f, bossHealthOutlineCol.getGreen() / 255f, bossHealthOutlineCol.getBlue() / 255f);
        glBegin(GL_LINE_LOOP);
        glVertex3f(0,0, 2);
        glVertex3f(barWidth, 0, 2);
        glVertex3f(barWidth, barHeight, 2);
        glVertex3f(0, barHeight, 2);

        glEnd();


        glPopMatrix();

    }

    public void renderDamageBar(Position pos) {
        glDisable(GL_TEXTURE_2D);
        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();

        renderHealthBars(pos);

        renderBar(pos);

        cursorAnimation(pos);

        renderMiddle(pos);

        glPopMatrix();
        glEnable(GL_TEXTURE_2D);
    }

    private void renderMiddle(Position pos) {
        glColor3f(middleColor.getRed() / 255f, middleColor.getGreen() / 255f, middleColor.getBlue() / 255f);

        glBegin(GL_LINE_LOOP);

        glVertex2f(-middleWidth, middleHeight);
        glVertex2f(-middleWidth, -middleHeight);
        glVertex2f(middleWidth, -middleHeight);
        glVertex2f(middleWidth, middleHeight);

        glEnd();
    }

    private void renderBar(Position pos) {

        glTranslatef(pos.toMap()[0], pos.toMap()[1] - dmgBarHeight * 4, 3);

        if(hit) {
            glColor3f(bossHealthOutlineCol.getRed() / 255f, bossHealthOutlineCol.getGreen() / 255f, bossHealthOutlineCol.getBlue() / 255f);
            hit = false;
        } else {
            glColor3f(bgColor.getRed() / 255f, bgColor.getGreen() / 255f, bgColor.getBlue() / 255f);
        }

        glBegin(GL_QUADS);

        glVertex2f(-dmgBarWidth, -dmgBarHeight);
        glVertex2f(dmgBarWidth, -dmgBarHeight);
        glVertex2f(dmgBarWidth, dmgBarHeight);
        glVertex2f(-dmgBarWidth, dmgBarHeight);

        glEnd();

        glBegin(GL_LINE_LOOP);
        glColor3f(outline.getRed() / 255f, outline.getGreen() / 255f, outline.getBlue() / 255f);

        glVertex2f(-dmgBarWidth, -dmgBarHeight);
        glVertex2f(dmgBarWidth, -dmgBarHeight);
        glVertex2f(dmgBarWidth, dmgBarHeight);
        glVertex2f(-dmgBarWidth, dmgBarHeight);

        glEnd();
    }

    private void cursorAnimation(Position pos) {

        glPushMatrix();
        glLoadIdentity();

        float barSize = dmgBarWidth - 0.4f;

        if(cursorPos >= barSize) {
            right = false;
        } else if (cursorPos <= -barSize) {
            right = true;
        }

        if(right) {
            cursorPos += cursorSpeed;
        } else {
            cursorPos -= cursorSpeed;
        }


        glTranslatef(cursorPos, 0,0);
        glTranslatef(pos.toMap()[0], pos.toMap()[1] - dmgBarHeight * 4, 2);



        glColor3f(cursor.getRed() / 255f, cursor.getGreen() / 255f, cursor.getBlue() / 255f);
        glBegin(GL_QUADS);

        float cursorHeight = dmgBarHeight * 0.9f;
        float cursorWidth = dmgBarWidth * 0.01f;

        glVertex2f(-cursorWidth, cursorHeight);
        glVertex2f(-cursorWidth, -cursorHeight);
        glVertex2f(cursorWidth, -cursorHeight);
        glVertex2f(cursorWidth, cursorHeight);

        glEnd();
        glPopMatrix();

    }

    public boolean hit() {
        if (cursorPos >= -middleWidth * 2.5f && cursorPos <= middleWidth * 2.5f) {
            if(bossHealth > 0) {
                bossHealth -= 10;
                hit = true;
                return false;
            } else {
                bossHealth = 0;
            }
        }
        return false;
    }
}

package gameStuff;

import items.Inventory;
import lwjglutils.OGLTextRenderer;
import map.MapBuilder;
import map.Position;
import render.Renderer;

import java.awt.*;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;


public class DamageBar {

    private final int barMaxWidth = 10;
    private final int barHeight = 1;

    private Color bossHealthCol = new Color(0xdb1b04);
    private Color bobHealthCol = Renderer.mainColor;
    private int bossHealth = 100;
    private int bobHealth = 100;

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

    private Random rnd = new Random();

    public boolean isBobDead = false;
    public boolean isBossDead = false;

    private final Sound hitSound;

    private OGLTextRenderer textRenderer;

    public DamageBar(OGLTextRenderer textRenderer) {
        this.textRenderer = textRenderer;
        hitSound = new Sound("audio/sounds/hit.ogg", false, true);
    }

    public void init() {
        isBobDead = false;
        isBossDead = false;
        bobHealth = 100;
        bossHealth = 100;
    }

    public void renderHealthBars(Position pos, boolean isBob) {
        float barWidth;
        if (isBob) {
            barWidth = bobHealth / 100f * barMaxWidth;
        } else {
            barWidth = bossHealth / 100f * barMaxWidth;
        }

        glPushMatrix();
        glLoadIdentity();

        if (isBob) {
            glTranslatef(pos.toMap()[0] - 4.6f * MapBuilder.MAP_SIZE, pos.toMap()[1] - 1.6f * MapBuilder.MAP_SIZE, 3);
            glColor3f(bobHealthCol.getRed() / 255f, bobHealthCol.getGreen() / 255f, bobHealthCol.getBlue() / 255f);
        } else {
            glTranslatef(pos.toMap()[0] + 2.8f * MapBuilder.MAP_SIZE, pos.toMap()[1] - 1.6f * MapBuilder.MAP_SIZE, 3);
            glColor3f(bossHealthCol.getRed() / 255f, bossHealthCol.getGreen() / 255f, bossHealthCol.getBlue() / 255f);
        }

        glBegin(GL_QUADS);
        glVertex3f(0, 0, 2);
        glVertex3f(barWidth, 0, 2);
        glVertex3f(barWidth, barHeight, 2);
        glVertex3f(0, barHeight, 2);

        glEnd();

        glColor3f(1, 1, 1);
        glBegin(GL_LINE_LOOP);
        glVertex3f(0, 0, 2);
        glVertex3f(barWidth, 0, 2);
        glVertex3f(barWidth, barHeight, 2);
        glVertex3f(0, barHeight, 2);

        glEnd();
        glPopMatrix();
    }

    public void renderDamageBar(Position pos) {
        if (!isBossDead && !isBobDead) {
            glDisable(GL_TEXTURE_2D);
            glMatrixMode(GL_MODELVIEW);
            glPushMatrix();
            glLoadIdentity();

            renderHealthBars(pos, true);
            renderHealthBars(pos, false);

            renderBar(pos);

            cursorAnimation(pos);

            renderMiddle(pos);

            if(bobHealth < 50 && Inventory.BAGET > 0) {
                textRenderer.addStr2D((int) ((Renderer.WIDTH / 2) - (Renderer.WIDTH * 0.015f * 8)), (int) (Renderer.HEIGHT / 2 + Renderer.HEIGHT * 0.24), "M - sníst bagetu");
            }

            glPopMatrix();
            glEnable(GL_TEXTURE_2D);
        }
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

        if (hit) {
            glColor3f(bossHealthCol.getRed() / 255f, bossHealthCol.getGreen() / 255f, bossHealthCol.getBlue() / 255f);
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

        if (cursorPos >= barSize) {
            right = false;
        } else if (cursorPos <= -barSize) {
            right = true;
        }

        if (right) {
            cursorPos += cursorSpeed;
        } else {
            cursorPos -= cursorSpeed;
        }


        glTranslatef(cursorPos, 0, 0);
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
        bossHit();
        hitSound.play();
        if (cursorPos >= -middleWidth * 2.5f && cursorPos <= middleWidth * 2.5f) {
            if (bossHealth > 0) {
                bossHealth -= 10;
                hit = true;
                return false;
            } else {
                bossHealth = 0;
                isBossDead = true;
            }
        }
        return false;
    }

    private void bossHit() {

        if (bobHealth > 0) {
            int hit = (5 + rnd.nextInt(5));
            int healthAfterHit = bobHealth - hit;
            if(healthAfterHit < 0) {
                bobHealth = 0;
                isBobDead = true;
            } else {
                bobHealth = healthAfterHit;
            }
        }
    }

    public void healBob() {
        if(Inventory.BAGET > 0 && bobHealth < 100) {
            Inventory.BAGET--;
            if(bobHealth + 20 > 100) {
                bobHealth = 100;
            } else {
                bobHealth += 20;
            }
        }
    }
}

package items;

import gameStuff.Sound;
import lwjglutils.OGLTexture2D;
import map.MapBuilder;
import map.Position;
import render.Renderer;

import java.util.ArrayList;

import static items.Inventory.*;
import static org.lwjgl.opengl.GL11.*;

public class ItemManager {
    protected float scale = 0.6f;
    protected float itemSize = MapBuilder.MAP_SIZE * scale;


    private ArrayList<Item> items;
    private Sound pickup;

    private OGLTexture2D baget, glasses, magic_wand, roll, bread, goulash, dumpling, cake;

    public ItemManager() {
        init();
    }

    private void init() {
        items = new ArrayList<>();
        try {
            baget = new OGLTexture2D("textures/bb.png");
            glasses = new OGLTexture2D("textures/glasses.png");
            magic_wand = new OGLTexture2D("textures/magic_wand.png");
            roll = new OGLTexture2D("textures/roll.png");
            bread = new OGLTexture2D("textures/bread.png");
            goulash = new OGLTexture2D("textures/goulash.png");
            dumpling = new OGLTexture2D("textures/dumpling.png");
            cake = new OGLTexture2D("textures/cake.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        pickup = new Sound("audio/sounds/pickup.ogg", false);
    }

    public void renderItems(Position currentPos) {
        Item itemToRemove = null;
        for(Item item : items) {

            if(item.getPos().equals(currentPos)) {
                switch(item.getType()) {
                    case BAGET -> BAGET++;
                    case GLASSES -> GLASSES++;
                    case MAGIC_WAND -> MAGIC_WAND++;
                    case ROLL -> ROLL++;
                    case BREAD -> BREAD++;
                    case GOULASH -> GOULASH++;
                    case DUMPLING -> DUMPLING++;
                    case CAKE -> CAKE++;
                }
                itemToRemove = item;
                pickup.play();
            } else {
                render(item);
            }
        }
        if(itemToRemove != null) {
            items.remove(itemToRemove);
        }
    }

    public void addItem(Item item) {
        if(item.getType() == ItemType.BAGET) {
            Renderer.AllBagetCount++;
        }
        items.add(item);
    }

    private void render(Item item) {

        switch (item.getType()) {
            case BAGET -> baget.bind();
            case GLASSES -> glasses.bind();
            case MAGIC_WAND -> magic_wand.bind();
            case ROLL -> roll.bind();
            case BREAD -> bread.bind();
            case GOULASH -> goulash.bind();
            case DUMPLING -> dumpling.bind();
            case CAKE -> cake.bind();
        }

        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();

        glTranslatef(item.getPos().toMap()[0] + 0.9f, item.getPos().toMap()[1] + 0.9f, 1);


        glBegin(GL_QUADS);

        glTexCoord2f(0, 0);
        glVertex2f(0, 0);

        glTexCoord2f(1, 0);
        glVertex2f(itemSize, 0);

        glTexCoord2f(1, 1);
        glVertex2f(itemSize, itemSize);

        glTexCoord2f(0, 1);
        glVertex2f(0, itemSize);

        glEnd();
        glPopMatrix();

    }




}

package scenes;

import entity.Bob;
import entity.Entity;
import gameStuff.DamageBar;
import items.Inventory;
import gameStuff.Sound;
import items.ItemManager;
import lwjglutils.OGLTextRenderer;
import map.*;
import messages.MessageManager;


public abstract class Scene {

    protected Inventory items;
    protected Bob bob;
    protected Map map;
    protected int[][] mapDesign;
    protected MapBuilder mapBuilder;
    protected MapChecker mapChecker;
    protected VoidTex voidTex;
    protected Position playerPos;
    protected int sceneID = 0;
    protected MessageManager messageManager;
    public boolean hasFight = false;
    protected DamageBar bar;
    protected Sound bgMusic;
    public boolean hasOwnMusic = false;

    protected ItemManager itemManager;


    public boolean isInit = false;



    public Scene(MapBuilder builder, OGLTextRenderer textRenderer) {
        this.mapBuilder = builder;
        messageManager = new MessageManager(textRenderer);
    }

    public void init() {
        isInit = true;
        map = new Map(mapDesign);
        mapChecker = new MapChecker(map);
        itemManager = new ItemManager();
    }

    public void render() {

    }

    public Position getPlayerPos() {
        return playerPos;
    }

    public void up() {
        int newY = playerPos.getY() - 1;

        if(mapChecker.checkMove(playerPos.getX(), newY)) {
            this.playerPos.y(newY);
        }
    }

    public void down() {
        int newY = playerPos.getY() + 1;

        if(mapChecker.checkMove(playerPos.getX(), newY)) {
            this.playerPos.y(newY);
        }
    }
    public void left() {
        int newX = playerPos.getX() - 1;

        if(mapChecker.checkMove(newX, playerPos.getY())) {
            this.playerPos.x(newX);
        }
    }
    public void right() {
        int newX = playerPos.getX() + 1;

        if(mapChecker.checkMove(newX, playerPos.getY())) {
            this.playerPos.x(newX);
        }
    }
    public int checkCurrentPos() {
        return mapChecker.checkCurrentPos(playerPos);
    }

    public int getSceneID() {
        return sceneID;
    }

    public void nextMessage() {
        messageManager.next(playerPos);
    }


    public void stopMusic() {
        if(bgMusic != null) {
            bgMusic.stop();
        }
    }

    public void playMusic() {
        if(bgMusic != null) {
            bgMusic.play();
        }
    }

    public void setPlayerPos(Position pos) {
        playerPos = pos;
        bob.setPosition(pos);
    }

    public Position getTeleportPos() {
        return mapChecker.getTeleportTo(playerPos);
    }

    public void hit() {
        if(hasFight) {
            bar.hit();
        }
    }

    public void heal() {
        if(hasFight) {
            bar.healBob();
        }
    }
}

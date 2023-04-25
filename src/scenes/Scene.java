package scenes;

import entity.Bob;
import entity.Entity;
import entity.Inventory;
import gameStuff.Sound;
import items.ItemManager;
import lwjglutils.OGLTextRenderer;
import map.*;
import messages.MessageManager;

import java.util.ArrayList;

public abstract class Scene {

    protected ArrayList<Entity> entityList;
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
    protected Sound bgMusic;
    protected Sound footstep;

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
        footstep = new Sound("audio/sounds/footstep.ogg", false, true);
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
            footstep.play();
        }
    }

    public void down() {
        int newY = playerPos.getY() + 1;

        if(mapChecker.checkMove(playerPos.getX(), newY)) {
            this.playerPos.y(newY);
            footstep.play();
        }
    }
    public void left() {
        int newX = playerPos.getX() - 1;

        if(mapChecker.checkMove(newX, playerPos.getY())) {
            this.playerPos.x(newX);
            footstep.play();
        }
    }
    public void right() {
        int newX = playerPos.getX() + 1;

        if(mapChecker.checkMove(newX, playerPos.getY())) {
            this.playerPos.x(newX);
            footstep.play();
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

    public void hit(){

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
}

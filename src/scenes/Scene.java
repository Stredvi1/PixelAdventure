package scenes;

import entity.Bob;
import entity.Entity;
import entity.ItemManager;
import map.*;

import java.util.ArrayList;

public abstract class Scene {

    protected ArrayList<Entity> entityList;
    protected ItemManager items;
    protected Bob bob;
    protected Map map;
    protected int[][] mapDesign;
    protected MapBuilder mapBuilder;
    protected MapChecker mapChecker;
    protected VoidTex voidTex;
    public Position playerPos;
    protected int sceneID = 0;


    public Scene(MapBuilder builder) {
        this.mapBuilder = builder;
        init();
    }

    protected void init() {
        mapChecker = new MapChecker(map);
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
}

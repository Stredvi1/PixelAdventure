package scenes;

import entity.Bob;
import entity.Entity;
import entity.ItemManager;
import map.Map;
import map.Position;

import java.util.ArrayList;

public abstract class Scene {

    protected ArrayList<Entity> entityList;
    protected ItemManager items;
    protected Bob bob;
    protected Map map;
    public static Position playerPos;
    protected boolean isActive = false;


    protected void init() {

    }

    public void active() {
        isActive = true;
    }

    public void start() {
        if(isActive) {

        }
    }

    public void end() {
        isActive = false;
    }
}

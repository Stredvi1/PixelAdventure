package entity;

import map.Position;
import java.util.ArrayList;

public class NPC extends Entity{

    private String name;

    public NPC(Position pos, String name, String textureName) {
        this.pos = pos;
        this.name = name;
        init(textureName);
    }

    protected void init(String textureName) {
        super.init(textureName);

    }

    public String getName() {
        return name;
    }




}

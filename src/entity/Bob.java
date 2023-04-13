package entity;

import lwjglutils.OGLTexture2D;
import map.MapBuilder;
import map.Position;

public class Bob extends Entity{

    public Bob(Position pos) {
        this.pos = pos;
        this.size = MapBuilder.MAP_SIZE * scale;

        init();
    }


    private void init() {

        try {
            texture = new OGLTexture2D("textures/bob.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

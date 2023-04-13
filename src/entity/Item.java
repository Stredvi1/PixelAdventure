package entity;

import lwjglutils.OGLTexture2D;
import map.MapBuilder;
import map.Position;

public class Item extends Entity{

    private boolean isPickedUp = false;

    public Item(String fileName, float size) {
        this.size = size;
        pos = new Position(4, 4, MapBuilder.MAP_SIZE);

        super.init(fileName);
    }

    public void render() {
        if(!isPickedUp) {
            super.render();
        }
    }

    public void pickUp(Position pos) {
        if (this.pos.equals(pos)) {
            this.isPickedUp = true;
        }
    }
}

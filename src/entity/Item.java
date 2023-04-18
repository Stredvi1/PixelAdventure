package entity;

import map.MapBuilder;
import map.Position;

public class Item extends Entity{

    private boolean isPickedUp = false;

    public Item(String fileName, float size) {
        this.size = size;
        pos = new Position(9, 10);

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

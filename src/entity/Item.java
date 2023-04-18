package entity;

import controller.BagetCounter;
import map.MapBuilder;
import map.Position;

public class Item extends Entity {

    private boolean isPickedUp = false;

    public Item(Position pos, String fileName) {
        this.size = MapBuilder.MAP_SIZE * scale;
        this.pos = pos;

        super.init(fileName);
    }

    public void render() {
        if (!isPickedUp) {
            super.render();
        }
    }

    public void pickUp(Position pos) {
        if (!isPickedUp) {
            if (this.pos.equals(pos)) {
                this.isPickedUp = true;
                BagetCounter.count += 1;
            }
        }

    }
}

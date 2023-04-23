package entity;

import map.MapBuilder;
import map.Position;

public class Item extends Entity {


    private boolean isPickedUp = false;
    private Inventory.ItemType type;

    public Item(Position pos, String fileName, Inventory.ItemType type) {
        this.size = MapBuilder.MAP_SIZE * scale;
        this.pos = pos;
        this.type = type;

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

                switch (type) {
                    case BAGET -> Inventory.BAGET++;
                    case GLASSES -> Inventory.GLASSES++;

                }
            }
        }

    }
}

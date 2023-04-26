package items;

import map.Position;

public class Item {

    private Inventory.ItemType type;
    private Position pos;

    public Item(Inventory.ItemType type, Position pos) {
        this.type = type;
        this.pos = pos;
    }

    public Position getPos() {
        return pos;
    }

    public Inventory.ItemType getType() {
        return type;
    }
}

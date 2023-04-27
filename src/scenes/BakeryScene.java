package scenes;

import entity.Bob;
import entity.NPC;
import gameStuff.Sound;
import items.Inventory;
import items.Item;
import lwjglutils.OGLTextRenderer;
import map.MapBuilder;
import map.Position;
import map.VoidTex;
import quests.Quest;
import render.Renderer;

public class BakeryScene extends Scene{

    private boolean added = false;

    public BakeryScene(MapBuilder builder, OGLTextRenderer textRenderer) {
        super(builder, textRenderer);
        sceneID = 7;
    }

    @Override
    public void init() {

        mapDesign = new int[][] {
                {0,0,0,0,0,0,0,0,0,0,0,0,2,3,2,0,0,0,},
                {0,0,0,0,0,0,0,0,0,0,0,3,2,2,2,2,0,0,},
                {0,0,0,2,4,4,2,2,2,2,2,2,2,2,4,2,2,0,},
                {0,2,2,2,2,4,2,2,2,2,4,2,2,4,2,2,2,0,},
                {2,2,3,2,4,2,4,3,2,4,1,2,2,3,2,2,2,4,},
                {4,2,2,2,4,2,1,2,2,2,2,2,2,4,2,2,2,1,},
                {0,2,4,2,2,2,2,1,2,7,2,2,2,4,2,4,4,0,},
                {0,0,4,4,2,2,2,2,2,2,2,2,2,2,1,2,0,0,},
                {0,0,0,0,2,2,2,1,2,2,2,2,2,2,0,0,0,0,}

        };

        super.init();

        playerPos = new Position(12,0);
        voidTex = new VoidTex(playerPos, map.getHighestWidth(), map.getHeight());
        bob = new Bob(playerPos);

        itemManager.addItem(new Item(Inventory.ItemType.ROLL, new Position(17,0)));
        itemManager.addItem(new Item(Inventory.ItemType.BAGET, new Position(4,9)));
        itemManager.addItem(new Item(Inventory.ItemType.BAGET, new Position(17,15)));

    }

    public void render() {
        voidTex.render();
        mapBuilder.renderMap(map);
        bob.render();
        itemManager.renderItems(playerPos);



    }

}

package scenes;

import entity.Bob;
import entity.Building;
import entity.NPC;
import gameStuff.Sound;
import items.Inventory;
import items.Item;
import lwjglutils.OGLTextRenderer;
import map.MapBuilder;
import map.Position;
import map.VoidTex;
import org.lwjgl.system.CallbackI;
import quests.Quest;
import render.Renderer;

public class BakeryScene extends Scene{

    private Building bakery;
    private boolean added = false;

    public BakeryScene(MapBuilder builder, OGLTextRenderer textRenderer) {
        super(builder, textRenderer);
        sceneID = 7;
    }

    @Override
    public void init() {

        mapDesign = new int[][] {
                {0,0,0,0,0,0,0,0,0,0,0,0,4,5,4,0,0,0,},
                {0,0,0,0,0,0,0,0,0,0,0,3,2,5,2,2,0,0,},
                {0,0,0,4,5,5,5,4,2,3,2,3,2,2,3,2,2,0,},
                {0,2,2,4,5,5,5,4,3,2,3,2,3,3,2,2,2,0,},
                {2,2,2,4,4,4,4,4,3,3,3,2,2,3,2,2,2,3,},
                {7,2,2,2,3,2,3,2,2,3,2,2,2,2,2,2,2,3,},
                {0,7,7,2,2,2,2,3,2,0,2,2,2,3,2,3,3,0,},
                {0,0,7,7,7,7,7,7,0,0,0,2,2,2,3,2,0,0,},
                {0,0,0,0,7,7,7,0,0,2,2,2,2,2,0,0,0,0,}

        };

        super.init();

        playerPos = new Position(13,0);
        voidTex = new VoidTex(playerPos, map.getHighestWidth(), map.getHeight());
        bob = new Bob(playerPos);

        bakery = new Building(new Position(4,2), "bakery.png", 3, 0.5f);

        itemManager.addItem(new Item(Inventory.ItemType.BAGET, new Position(16,6)));

        mapChecker.addTeleportPad(new Position(5,2), 8);
        mapChecker.addTeleportPad(new Position(13,0), 6);

    }

    public void render() {
        voidTex.render();
        mapBuilder.renderMap(map);
        bakery.render();
        bob.render();
        itemManager.renderItems(playerPos);
    }

}

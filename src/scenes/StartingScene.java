package scenes;

import entity.Building;
import entity.Bob;
import items.Inventory;
import gameStuff.Sound;
import items.Item;
import lwjglutils.OGLTextRenderer;
import map.*;
import quests.Quest;
import render.Renderer;

public class StartingScene extends Scene {

    private Building bbShop;
    public static boolean showItems = false;
    private boolean added = false;
    private boolean start = true;

    public StartingScene(MapBuilder builder, OGLTextRenderer textRenderer) {
        super(builder, textRenderer);
        sceneID = 1;
        hasOwnMusic = true;
    }

    public void init() {

        bgMusic = new Sound("audio/music/welcome.ogg", true);
        mapDesign = new int[][]{
                {0,2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 5, 5, 5, 4, 2},
                {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 4, 4, 2},
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 2, 2},
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 2, 2},
                {4, 4, 4, 4, 4, 4, 3, 3, 4, 4, 4, 4, 5, 5, 4, 2, 2, 2},
                {2, 2, 2, 2, 3, 3, 3, 3, 2, 2, 2, 4, 5, 5, 4, 2, 2, 2},
                {0, 0, 0, 2, 3, 3, 2, 2, 2, 2, 2, 4, 5, 5, 4, 2, 2},
                {0, 0, 0, 0, 3, 3, 2, 0, 0, 0, 2, 4, 5, 5, 4, 2, 2},
                {0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 2, 4, 5, 5, 4, 2},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 4, 5, 5, 4, 2},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 4, 5, 5, 4, 2, 2},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 4, 5, 5, 4, 2, 2},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 4, 5, 5, 4, 2, 2},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 4, 5, 5, 4, 2},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 4, 5, 5, 4, 2},


        };

        super.init();
        playerPos = new Position(1,1);

        voidTex = new VoidTex(playerPos, map.getHighestWidth(), map.getHeight());
        bob = new Bob(playerPos);

        bbShop = new Building(new Position(12, 0),"bb_shop.png", 3, 0);

        itemManager.addItem(new Item(Inventory.ItemType.ROLL, new Position(5, 8)));
        itemManager.addItem(new Item(Inventory.ItemType.BAGET, new Position(12, 6)));
        itemManager.addItem(new Item(Inventory.ItemType.BAGET,new Position(16, 0)));

        mapChecker.addTeleportPad(new Position(0, 1), 9);

        initMessages();
    }

    public void render() {
        voidTex.render();
        mapBuilder.renderMap(this.map);
        bbShop.render();

        if(showItems) {
            if(start) {
                start = false;
                Inventory.SHOW_INVENTORY = true;
                mapChecker.addTeleportPad(new Position(13, 14), 3);
                mapChecker.addTeleportPad(new Position(12, 14), 3);
                bgMusic.delete();
                bgMusic = new Sound("audio/music/challengeAccepted.ogg", true);
                bgMusic.play();
            }
            itemManager.renderItems(playerPos);
        }

        bob.render();

        messageManager.showMessage(playerPos);

        if(messageManager.isLastMessage() && !added) {
            added = true;
            Renderer.questManager.addQuest(new Quest("VITO", "Kup si bagetu", 1));
            mapChecker.addTeleportPad(new Position(13, 0), 2);
        }

    }

    private void initMessages() {
        messageManager.addBobMessage("Hmm, mám chuť na bagetu...");
        messageManager.addBobMessage("Ještě že je kousek odsud Bageterka.");

    }
}

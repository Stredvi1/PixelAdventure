package scenes;

import entity.Building;
import entity.Bob;
import entity.Inventory;
import entity.Item;
import gameStuff.Sound;
import lwjglutils.OGLTextRenderer;
import map.*;
import quests.Quest;
import render.Renderer;

public class StartingScene extends Scene {

    private Building bbShop;
    public static boolean showSecretBaget = false;
    private Item baget, baget1, baget2;
    private boolean added = false;

    public StartingScene(MapBuilder builder, OGLTextRenderer textRenderer) {
        super(builder, textRenderer);
        sceneID = 1;
        init();
    }

    protected void init() {

        bgMusic = new Sound("audio/music/welcome.ogg", true);
        mapDesign = new int[][]{
                {-1,2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 5, 5, 5, 4, 2},
                {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 4, 4, 2},
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 2, 2},
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 2, 2},
                {4, 4, 4, 4, 4, 4, 3, 3, 4, 4, 4, 4, 5, 5, 4, 2, 2, 2},
                {2, 2, 2, 2, 3, 3, 3, 3, 2, 2, 2, 4, 5, 5, 4, 2, 2, 2},
                {-1,-1,-1,2, 3, 3, 2, 2, 2, 2, 2, 4, 5, 5, 4, 2, 2},
                {-1,-1,-1,-1,3, 3, 2,-1,-1,-1, 2, 4, 5, 5, 4, 2, 2},
                {-1,-1,-1,-1,-1,3, -1,-1,-1,-1, 2, 4, 5, 5, 4, 2},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 2, 4, 5, 5, 4, 2},

        };

        map = new Map(mapDesign);

        super.init();
        playerPos = new Position(1,1);

        voidTex = new VoidTex(playerPos, true);
        bob = new Bob(playerPos);

        bbShop = new Building(new Position(12, 0),"bb_shop.png", 3);
        baget = new Item(new Position(5, 8), "bb.png", Inventory.ItemType.BAGET);
        baget1 = new Item(new Position(12, 6), "bb.png", Inventory.ItemType.BAGET);
        baget2 = new Item(new Position(14, 8), "bb.png", Inventory.ItemType.BAGET);

        mapChecker.addTeleportPos(new Position(13, 0), 2);
        mapChecker.addTeleportPos(new Position(0, 1), 3);

        bgMusic.play();

        initMessages();
    }

    public void render() {
        voidTex.render();
        mapBuilder.renderMap(this.map);
        bbShop.render();



        if(showSecretBaget) {
            Inventory.SHOW_INVENTORY = true;
            baget.render();
            baget1.render();
            baget2.render();

            baget.pickUp(playerPos);
            baget1.pickUp(playerPos);
            baget2.pickUp(playerPos);

        }

        bob.render();

        messageManager.showMessage(playerPos);

        if(messageManager.isLastMessagege() && !added) {
            System.out.println("LAST MESSAGE");
            added = true;
            Renderer.questManager.addQuest(new Quest("VITO", "Kup si bagetu", 1));
        }
    }

    private void initMessages() {
        messageManager.addBobMessage("Hmm, mám chuť na bagetu...");
        messageManager.addBobMessage("Ještě že je kousek odsud Bageterka.");

    }
}

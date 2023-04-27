package scenes;

import entity.Bob;
import entity.Building;
import entity.NPC;
import gameStuff.DamageBar;
import gameStuff.Sound;
import items.Inventory;
import items.Item;
import lwjglutils.OGLTextRenderer;
import map.Map;
import map.MapBuilder;
import map.Position;
import map.VoidTex;
import quests.Quest;
import render.Renderer;

public class BakeryPorchScene extends Scene{

    private NPC snejks;
    private Building hut;
    private Position snejksPos;

    private boolean added = false;
    private boolean startFight = false;
    private boolean finished = false;
    private boolean dumpling = false;

    public BakeryPorchScene(MapBuilder builder, OGLTextRenderer textRenderer) {
        super(builder, textRenderer);
        sceneID = 9;
        hasOwnMusic = true;
        hasFight = true;
        bar = new DamageBar(textRenderer);
        bgMusic = new Sound("audio/music/snejks.ogg", true);
    }

    @Override
    public void init() {

        mapDesign = new int[][] {
                {2,2,2,2,3,2,2,3,2},
                {3,3,3,2,2,3,2,2,2},
                {7,3,2,2,3,2,2,2,2},
                {7,7,2,3,2,2,3,2,2},
                {7,3,2,2,2,3,3,2,2},
                {2,2,2,2,3,2,2,6,2}
        };

        map = new Map(mapDesign);

        super.init();

        playerPos = new Position(7, 5);
        bob = new Bob(playerPos);

        snejksPos = new Position(6,2);

        snejks = new NPC(snejksPos, "Snejks", "snejks.png");
        voidTex = new VoidTex(playerPos, map.getHighestWidth(), map.getHeight());

        hut = new Building(new Position(3, 1), "chickens.png", 3, 2f);

        initMessages();
    }

    private void initMessages() {
        messageManager.addBobMessage("HEJ, pomalu Snejksi, pomalu!", snejksPos);
        messageManager.addMessage(snejks.getName(), "Jsem otevřel tajemnou komnatu!", snejksPos);
    }

    public void render() {

        voidTex.render();
        mapBuilder.renderMap(this.map);
        if(!bar.isBossDead) {
            snejks.render();
        }
        hut.render();
        bob.render();

        itemManager.renderItems(playerPos);

        if(startFight && playerPos.withinRadius(snejksPos, 1)) {
            bar.renderDamageBar(playerPos);
        }

        messageManager.showMessage(playerPos);

        if(messageManager.isLastMessage()) {
            startFight = true;
        }

        if(bar.isBobDead) {
            startFight = false;
            bar.init();
            this.init();
        }

        if(bar.isBossDead && !finished) {
            finished = true;
            bgMusic.delete();
            bgMusic = new Sound("audio/music/snejksded.ogg", true);
            bgMusic.play();
            Renderer.questManager.finishQuest(6);
            mapChecker.addTeleportPad(new Position(7,5), 8);
            itemManager.addItem(new Item(Inventory.ItemType.MAGIC_WAND, snejksPos));
        }

        if(Renderer.questManager.hasQuest(7) && !dumpling) {
            dumpling = true;
            itemManager.addItem(new Item(Inventory.ItemType.DUMPLING, new Position(0,0)));
            itemManager.addItem(new Item(Inventory.ItemType.DUMPLING, new Position(5,0)));
            itemManager.addItem(new Item(Inventory.ItemType.DUMPLING, new Position(4,3)));
            itemManager.addItem(new Item(Inventory.ItemType.DUMPLING, new Position(8,2)));
        }
    }

    public void hit() {
        if(playerPos.withinRadius(snejksPos, 1) && messageManager.isLastMessage()) {
            super.hit();
        }
    }


}

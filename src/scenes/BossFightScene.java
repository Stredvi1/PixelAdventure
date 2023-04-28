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
import render.Renderer;

public class BossFightScene extends Scene{

    private NPC boss;
    private Position bossPos;

    private boolean startFight = false;
    private boolean finished = false;
    private boolean noWand = false;
    private boolean added = false;
    private boolean mapChanged = false;

    public BossFightScene(MapBuilder builder, OGLTextRenderer textRenderer) {
        super(builder, textRenderer);
        sceneID = 12;
        hasOwnMusic = true;
        hasFight = true;
        bar = new DamageBar(textRenderer);
        bgMusic = new Sound("audio/music/boss.ogg", true);
    }

    @Override
    public void init() {

        mapDesign = new int[][] {
                {0,5,5,0,0,0,5,5,0,0,},
                {5,0,0,0,0,5,5,5,5,5,},
                {5,0,5,3,5,3,5,0,0,0,},
                {3,5,5,0,5,0,0,0,3,0,},
                {0,0,0,5,0,5,3,0,0,5,},
                {0,5,0,5,3,0,5,5,3,5,},
                {3,0,0,5,0,0,3,3,0,0,},
                {0,0,0,0,0,5,5,0,0,5,},
                {0,5,5,0,5,5,5,0,3,5,},
                {0,0,0,0,5,0,0,0,0,0,},
        };

        map = new Map(mapDesign);

        super.init();

        playerPos = new Position(4, 9);
        bob = new Bob(playerPos);

        bossPos = new Position(6,2);

        boss = new NPC(bossPos, "Boss", "redboi.png");
        voidTex = new VoidTex(playerPos,"textures/lava.png", map.getHighestWidth(), map.getHeight());
        mapChecker.addTeleportPad(new Position(4,9), 11);

        messageManager.setRadius(2);

        bar.setCursorSpeed(1.2f);
        bar.setMaxBossDamage(15);
        initMessages();
    }

    private void initMessages() {
        messageManager.addMessage(boss.getName(), "Tak už jsi konečně tady!", bossPos);
        messageManager.addBobMessage("Jo, to jsem.", bossPos);
        messageManager.addBobMessage("Měl by sis opravit příjezdovou cestu kámo", bossPos);
        messageManager.addBobMessage("Je to fakt děs", bossPos);
    }

    public void render() {

        voidTex.render();
        mapBuilder.renderMap(this.map);
        mapChecker.renderTeleports();

        if(!bar.isBossDead) {
            boss.render();
        }
        bob.render();

        itemManager.renderItems(playerPos);

        if(startFight && playerPos.withinRadius(bossPos, 2)) {
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
            Renderer.stopAllMusic();
            bgMusic = new Sound("audio/music/winner.ogg", true);
            Renderer.FINAL_MUSIC = true;
            bgMusic.play();

            itemManager.addItem(new Item(Inventory.ItemType.RECIPE, bossPos));
        }

        if (Inventory.MAGIC_WAND > 0 && bar.isBossDead && messageManager.isLastMessage() && !added) {
            added = true;
            Inventory.MAGIC_WAND = 0;
            messageManager.addBobMessage("Ha, ještě že mám hůlku, jinak bych se tam nedostal");
            messageManager.addBobMessage("To by byl trapas...");
        }

        if(added && !mapChanged && messageManager.isLastMessage()) {
            mapChanged = true;
            map.setParcel(3,5, 9);

        }

        if (Inventory.MAGIC_WAND == 0 && bar.isBossDead && messageManager.isLastMessage() && !noWand && !added) {
            noWand = true;
            messageManager.addBobMessage("Teď by se mi fakt hodila Snejksova hůlka...");
            messageManager.addBobMessage("Teď se tam nedostanu");
        }
    }

    public void hit() {
        if(playerPos.withinRadius(bossPos, 2) && messageManager.isLastMessage()) {
            super.hit();
        }
    }


}

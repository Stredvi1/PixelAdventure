package scenes;

import entity.Bob;
import entity.Inventory;
import entity.NPC;
import gameStuff.Sound;
import items.ItemManager;
import items.Item;
import lwjglutils.OGLTextRenderer;
import map.MapBuilder;
import map.Position;
import map.VoidTex;
import quests.Quest;
import render.Renderer;

public class LostGrandmaScene extends Scene{

    private NPC granny;
    private Position grannyPos = new Position(3, 6);
    private boolean added = false;

    public LostGrandmaScene(MapBuilder builder, OGLTextRenderer textRenderer) {
        super(builder, textRenderer);
        sceneID = 3;
    }

    public void init() {

        bgMusic = new Sound("audio/music/challengeAccepted.ogg", true);

        mapDesign = new int[][] {
                {2, 4, 5, 5, 4, 2},
                {2, 4, 5, 5, 4, 2},
                {2, 4, 5, 5, 4, 2, -1},
                {2, 4, 5, 5, 4, 4, 4, 4,4,4},
                {2, 4, 5, 5, 5, 5, 5, 5,5,5},
                {2, 4, 5, 5, 5, 5, 5 ,5,5,5},
                {2, 4, 4, 4, 4, 4, 4 ,4,4,4},
                {2, 2, 2, 2, 2, 2, 2, 2,2,2}
        };

        super.init();
        playerPos = new Position(2,0);

        voidTex = new VoidTex(playerPos);
        bob = new Bob(playerPos);

        granny = new NPC(grannyPos, "Stařenka","granny.png");

        itemManager.addItem(new Item(Inventory.ItemType.BAGET, new Position(4, 6)));

        mapChecker.addTeleportPos(new Position(2, 0), 1);
        mapChecker.addTeleportPos(new Position(3, 0), 1);

        initMessages();
    }

    public void render() {
        voidTex.render();
        mapBuilder.renderMap(this.map);

        itemManager.renderItems(playerPos);

        granny.render();

        bob.render();
        messageManager.showMessage(playerPos);

        if(messageManager.isLastMessagege() && !added) {
            added = true;
            Renderer.questManager.addQuest(new Quest(granny.getName(), "Najdi mi moje brýle", 3));
        }
    }

    private void initMessages() {
        messageManager.addMessage(granny.getName(), "Dob.. dobrý ý dd den", grannyPos);
        messageManager.addBobMessage("Zdravíčko stařenko, co se vám stalo...", grannyPos);
        messageManager.addBobMessage("Že tu tak sedíte na zemi úplně ztracená?", grannyPos);
        messageManager.addMessage(granny.getName(), "T-to byste ne-nevěřřil, ...", grannyPos);
        messageManager.addMessage(granny.getName(), "c-co jsou dnes lidi za impo- im.. impotenty!", grannyPos);
        messageManager.addBobMessage("Pročpak stařenko?", grannyPos);
        messageManager.addMessage(granny.getName(), "Nějaký pablb m-mě málem sej-jmul dodávkou!", grannyPos);
        messageManager.addBobMessage("*Zděšení*", grannyPos);
        messageManager.addMessage(granny.getName(), "A jak prosvištěl, tak mi někam od-dletěly brejle", grannyPos);
        messageManager.addBobMessage("Vydržte tu, já se vám po nich podívám", grannyPos);


    }
}

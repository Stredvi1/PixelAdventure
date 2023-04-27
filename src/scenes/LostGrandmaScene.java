package scenes;

import entity.Bob;
import items.Inventory;
import entity.NPC;
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
    private boolean goulash = false;
    private boolean itemGoulash = false;


    public LostGrandmaScene(MapBuilder builder, OGLTextRenderer textRenderer) {
        super(builder, textRenderer);
        sceneID = 3;
    }

    public void init() {

        mapDesign = new int[][] {
                {2,4,5,5,4,2},
                {2,4,5,5,4,2},
                {2,4,5,5,4,2,2,2,2,2,2,2,2},
                {2,4,5,5,4,4,4,4,4,4,4,4,4},
                {2,4,5,5,5,5,5,5,5,5,5,5,5},
                {2,4,5,5,5,5,5,5,5,5,5,5,5},
                {2,4,4,4,4,4,4,4,4,4,4,4,4},
                {2,2,2,2,3,2,2,2,2,2,2,2,2},
                {0,0,0,2,2,3,3,2},
                {},
                {},
                {0,0,0,0,0,0,0,1,1,1,7,7,7},
                {0,0,0,0,0,0,1,1,7,7,7,7,7},
                {0,0,0,0,0,3,2,2,3,7,7,7,7},
                {0,0,0,0,0,0,2,2,2,1,1,1,1},
                {0,0,0,0,0,0,0,0,2,2,2},


        };

        super.init();
        playerPos = new Position(2,0);

        voidTex = new VoidTex(playerPos, map.getHighestWidth(), map.getHeight());
        bob = new Bob(playerPos);

        granny = new NPC(grannyPos, "Stařenka","granny.png");

        itemManager.addItem(new Item(Inventory.ItemType.BAGET, new Position(4, 6)));
        itemManager.addItem(new Item(Inventory.ItemType.GLASSES, new Position(7, 12)));


        mapChecker.addTeleportPad(new Position(2, 0), 1);
        mapChecker.addTeleportPad(new Position(3, 0), 1);

        initMessages();
    }

    public void render() {
        voidTex.render();
        mapBuilder.renderMap(this.map);

        itemManager.renderItems(playerPos);

        granny.render();

        bob.render();
        messageManager.showMessage(playerPos);

        if(messageManager.isLastMessage() && !added) {
            added = true;
            Renderer.questManager.addQuest(new Quest(granny.getName(), "Najdi mi moje brýle", 3));
            mapChecker.addTeleportPad(new Position(12,4), 4);
            mapChecker.addTeleportPad(new Position(12,5), 4);
            mapChecker.addTeleportPad(new Position(12,14), 4,new Position(0,12));
        }

        if(playerPos.withinRadius(grannyPos, 1) && Inventory.GLASSES > 0) {
            Inventory.GLASSES = 0;
            Renderer.questManager.finishQuest(3);
            granny.setTexture("grannyglasses.png");
            messageManager.addMessage(granny.getName(), "Moc ti děkuju mladíku!", grannyPos);
            messageManager.addBobMessage("Maličkost", grannyPos);
            messageManager.addBobMessage("Mluvila jste o dodávce?", grannyPos);
            messageManager.addMessage(granny.getName(), "Joo, ten chuligán co tu projel!", grannyPos);
            messageManager.addBobMessage("Kam jel?", grannyPos);
            messageManager.addMessage(granny.getName(), "Pokračoval dál touhle cestou.", grannyPos);
            messageManager.addBobMessage("Díkec, jdu si pro něj.", grannyPos);
            messageManager.addMessage(granny.getName(), "A dejte mu za mě pěstí!", grannyPos);
        }

        if(Renderer.questManager.hasQuest(7) && !goulash) {
            goulash = true;
            messageManager.addMessage(granny.getName(), "Copak ty tu zase?", grannyPos);
            messageManager.addBobMessage("Byl jsem za vámi vyslán, že máte dobrý segedín", grannyPos);
            messageManager.addMessage(granny.getName(), "Jojo mládenče, ten nejlepší! Tumáš.", grannyPos);
        }

        if (goulash && messageManager.isLastMessage() && !itemGoulash) {
            itemGoulash = true;
            itemManager.addItem(new Item(Inventory.ItemType.GOULASH, new Position(2,6)));
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

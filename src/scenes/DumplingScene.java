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
import quests.Quest;
import render.Renderer;

public class DumplingScene extends Scene{

    private NPC guy;
    private Position guyPos;
    private Sound guySong;

    private boolean added = false;
    private boolean isQuestDone = false;
    private boolean addedBossQuestion = false;

    public DumplingScene(MapBuilder builder, OGLTextRenderer textRenderer) {
        super(builder, textRenderer);
        sceneID = 10;
    }

    @Override
    public void init() {

        guySong = new Sound("audio/sounds/guySings.ogg", false);

        mapDesign = new int[][] {
                {0,0,0,0,0,0,0,0,0,0,0,3,2,0,0,0,0,0,},
                {0,0,0,4,4,4,4,4,4,3,2,2,0,0,0,0,0,0,},
                {4,4,4,4,5,5,5,5,3,2,5,2,2,2,2,2,4,4,4,4},
                {5,5,5,5,5,5,5,5,5,3,2,3,2,4,2,1,3,5,5,5,},
                {4,4,5,5,5,5,5,5,2,3,3,5,1,2,3,5,1,5,5,5,5},
                {0,4,4,4,4,4,4,4,4,5,4,2,4,3,2,4,4,4,4,4,},
                {0,0,0,0,0,0,0,0,0,0,0,6,9,9,9,6,0,0,},
                {0,0,0,0,0,0,0,0,0,0,0,6,9,9,9,6,0,0,},
                {0,0,0,0,0,0,0,0,0,0,0,6,6,6,6,6,0,0,},

        };

        super.init();

        playerPos = new Position(0,3);
        voidTex = new VoidTex(playerPos, map.getHighestWidth(), map.getHeight());
        bob = new Bob(playerPos);
        guyPos = new Position(5,3);
        guy = new NPC(guyPos, "Týpek", "pastryguy.png");


        mapChecker.addTeleportPad(new Position(0,3), 6);

        initMessages();
    }

    public void render() {
        voidTex.render();
        mapBuilder.renderMap(map);
        mapChecker.renderTeleports();
        guy.render();
        bob.render();

        itemManager.renderItems(playerPos);

        messageManager.showMessage(playerPos);

        if(messageManager.isLastMessage() && !added) {
            added = true;
            Renderer.questManager.addQuest(new Quest(guy.getName(), "Přines 4 rohlíky, chleba, guláš, 4 knedlíky", 7));

        }

        if(playerPos.withinRadius(guyPos, 1)
                && Inventory.BREAD > 0
                && Inventory.DUMPLING >= 4
                && Inventory.GOULASH > 0
                && Inventory.ROLL >= 4) {
            isQuestDone = true;
            Renderer.questManager.finishQuest(7);
            Inventory.BREAD = 0;
            Inventory.DUMPLING = 0;
            Inventory.GOULASH = 0;
            Inventory.ROLL = 0;

            Renderer.stopAllMusic();
            guySong.play();

            itemManager.addItem(new Item(Inventory.ItemType.BAGET, new Position(14,7)));
            itemManager.addItem(new Item(Inventory.ItemType.BAGET, new Position(14,6)));
            itemManager.addItem(new Item(Inventory.ItemType.BAGET, new Position(13,6)));
            itemManager.addItem(new Item(Inventory.ItemType.BAGET, new Position(13,7)));
            itemManager.addItem(new Item(Inventory.ItemType.BAGET, new Position(12,6)));
            itemManager.addItem(new Item(Inventory.ItemType.BAGET, new Position(12,7)));
        }

        if(isQuestDone && !guySong.isPlaying()) {
            isQuestDone = false;
            messageManager.addBobMessage("Tak tohle bylo fakt o ničem");
            messageManager.addBobMessage("Ještě že to nemusím programovat");
            messageManager.addBobMessage("Nebo...?");
            messageManager.addBobMessage("No nic, jdu si pro recept.");

        }

        if (!isQuestDone && !addedBossQuestion && playerPos.withinRadius(new Position(13,4),3)) {
            addedBossQuestion = true;
            messageManager.addBobMessage("Ehh.. tohle vypadá na težkej fight.");
            messageManager.addBobMessage("Asi by nebylo od věci si nabrat zásoby.");

            mapChecker.addTeleportPad(new Position(20,4),11);
            mapChecker.addTeleportPad(new Position(19,3),11);

        }
    }

    private void initMessages() {
        messageManager.addMessage(guy.getName(), "Ahoj brachu, neměl bys nějaký drobný?", guyPos);
        messageManager.addBobMessage("Promiň, ale drobný nenosím.", guyPos);
        messageManager.addMessage(guy.getName(), "Alespoň nějaký jídlo? třeba rohlíky nebo tak..", guyPos);
        messageManager.addBobMessage("Ten by se možná našel...", guyPos);
        messageManager.addMessage(guy.getName(), "A za chleba...", guyPos);
        messageManager.addMessage(guy.getName(), "... 4 rohlíky...", guyPos);
        messageManager.addMessage(guy.getName(), "... segedínský guláš ...", guyPos);
        messageManager.addMessage(guy.getName(), "... a 4 knedlíky ... ti složím písničku", guyPos);
        messageManager.addBobMessage("Cože, proč bych to v životě chtěl?", guyPos);
        messageManager.addBobMessage("Jo jasný... Já tady nerozhoduju...", guyPos);
        messageManager.addBobMessage("Dobře teda.. máš to mít.", guyPos);
        messageManager.addMessage(guy.getName(), "Skvělej gulášek umí stařenka, určitě jsi ji potkal už", guyPos);
        messageManager.addBobMessage("Hmmm... jen se vypovídej, já stejně nikam nemůžu.", guyPos);
        messageManager.addMessage(guy.getName(), "... a chleba s knedlíkem budou v pekárně.", guyPos);
        messageManager.addBobMessage("Mh.. jasný...", guyPos);
    }

}

package scenes;

import entity.Bob;
import entity.Building;
import entity.NPC;
import items.Inventory;
import items.Item;
import lwjglutils.OGLTextRenderer;
import map.MapBuilder;
import map.Position;
import map.VoidTex;
import quests.Quest;
import render.Renderer;

public class SvartaScene extends Scene{


    private Building garage, matrace;
    private Position svartaPos;
    private NPC svarta;

    private boolean added = false;
    private boolean renderMatrace = false;

    private boolean startCounter = false;
    private int counter = 0;


    public SvartaScene(MapBuilder builder, OGLTextRenderer textRenderer) {
        super(builder, textRenderer);
        sceneID = 4;
    }

    public void init() {

        mapDesign = new int[][] {
                {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
                {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,2,4,2,2},
                {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,3,5,3,2},
                {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,3,5,2,2,3,3},
                {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,2,3,4},
                {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
                {0,0,0,2,2,2,2,2,2,2},
                {0,0,0,0,0,2,2,2,2,2,2},
                {0,0,0,0,2,2,2,2,2,2},
                {7,1,1,1,2,2,2,2,2,2},
                {7,7,7,1,1,2,2,2,2,3},
                {7,7,7,7,1,1,2,2},
                {1,1,1,2,2,2},
        };

        super.init();
        playerPos = new Position(0,2);
        voidTex = new VoidTex(playerPos, map.getHighestWidth(), map.getHeight());
        bob = new Bob(playerPos);

        mapChecker.addTeleportPad(new Position(0, 2), 3, new Position(12,4));
        mapChecker.addTeleportPad(new Position(0, 3), 3, new Position(12,5));

        mapChecker.addTeleportPad(new Position(0,12), 3, new Position(12,14));

        garage = new Building(new Position(16, 1), "garage.png", 3, 0);
        matrace = new Building(new Position(17, 2), "matrace.png", 2,0);
        svartaPos = new Position(17, 0);
        svarta = new NPC(svartaPos, "Svarta", "svarta.png");

        itemManager.addItem(new Item(Inventory.ItemType.BAGET, new Position(16,0)));


        initMessages();
    }

    public void render() {
        voidTex.render();
        mapBuilder.renderMap(this.map);
        mapChecker.renderTeleports();
        garage.render();
        if(svartaPos.withinRadius(playerPos, 2) && Inventory.MATRACE > 0 && Renderer.questManager.hasQuest(4)) {
            Inventory.MATRACE = 0;
            Renderer.questManager.finishQuest(4);
            renderMatrace = true;

            startCounter = true;
        }

        if(startCounter) {
            if (counter <= 200) {
                counter += 5;
            }
            if(counter == 100) {
                svartaPos = new Position(svartaPos.getX(), svartaPos.getY() + 1);
                svarta.setPosition(svartaPos);
            }else if (counter == 200) {
                svartaPos = new Position(svartaPos.getX(), svartaPos.getY() + 1);
                svarta.setPosition(svartaPos);
                messageManager.addMessage(svarta.getName(), "Tak kámo, to byla vejška jako PRASE!", svartaPos);
                messageManager.addBobMessage("Není ti nic?", svartaPos);
                messageManager.addMessage(svarta.getName(), "Nic mi není, díky kámo!", svartaPos);
                messageManager.addBobMessage("Jak ses tam nahoru vůbec dostal?", svartaPos);
                messageManager.addMessage(svarta.getName(), "Svištěl tady nějakej vocas jak kdyby něco ukradl", svartaPos);
                messageManager.addMessage(svarta.getName(), "A jel přímo na mě, tak jsem skočil na garáž", svartaPos);
                messageManager.addBobMessage("Hustý kámo. A kam jel?", svartaPos);
                messageManager.addMessage(svarta.getName(), "Pokračoval dál doprava", svartaPos);

                itemManager.addItem(new Item(Inventory.ItemType.ROLL, new Position(8,9)));

                mapChecker.addTeleportPad(new Position(24,3), 6);
            }
        }

        if(renderMatrace) {
            matrace.render();
        }
        svarta.render();
        bob.render();
        itemManager.renderItems(playerPos);

        messageManager.showMessage(playerPos);

        if (messageManager.isLastMessage() && !added) {
            added = true;
            Renderer.questManager.addQuest(new Quest(svarta.getName(), "Přines mi matrac, ať můžu jumpovat", 4));
        }
    }

    private void initMessages() {
        messageManager.setRadius(2);
        messageManager.addMessage(svarta.getName(), "Já to budu jumpovat!", svartaPos);
        messageManager.addMessage(svarta.getName(), "a bude mi HODNĚ", svartaPos);
        messageManager.addBobMessage("To ti bude teda, to nedělej!", svartaPos);
        messageManager.addMessage(svarta.getName(), "Zavolej sanitku!", svartaPos);
        messageManager.addBobMessage("Nezavolám, matraci ti donesu", svartaPos);
    }
}

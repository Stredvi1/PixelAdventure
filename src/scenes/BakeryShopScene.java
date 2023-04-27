package scenes;

import entity.Bob;
import entity.Building;
import entity.NPC;
import gameStuff.Sound;
import items.Inventory;
import items.Item;
import lwjglutils.OGLTextRenderer;
import map.Map;
import map.MapBuilder;
import map.Position;
import map.VoidTex;
import org.lwjgl.system.CallbackI;
import quests.Quest;
import render.Renderer;

public class BakeryShopScene extends Scene{

    private NPC baker;
    private Building bbOrders;
    private Position bakerPos;

    private boolean added = false;
    private boolean addCake = false;
    private boolean addMessages = false;
    private boolean bread = false;
    private boolean itemBread = false;



    public BakeryShopScene(MapBuilder builder, OGLTextRenderer textRenderer) {
        super(builder, textRenderer);
        sceneID = 8;
        hasOwnMusic = false;
    }

    @Override
    public void init() {

        mapDesign = new int[][] {
                {6,6,6,0,0,0,0,6,0},
                {6,6,6,8,8,8,8,8,8},
                {8,8,8,8,8,8,8,8,8},
                {8,8,8,8,8,8,8,8,8},
                {8,8,8,8,8,8,8,8,8},
                {8,8,8,8,6,8,8,8,8}
        };

        map = new Map(mapDesign);

        super.init();

        playerPos = new Position(4, 5);
        bob = new Bob(playerPos);

        bakerPos = new Position(1,0);

        baker = new NPC(bakerPos, "Pekař", "baker.png");
        voidTex = new VoidTex(playerPos, "textures/void.png", map.getHighestWidth(), map.getHeight());

        bbOrders = new Building(new Position(0, 1), "bakery_orders.png", 3, 2f);

        initMessages();
    }

    private void initMessages() {
        messageManager.addMessage(baker.getName(), "Dobrý den, co to dneska bude?", bakerPos);
        messageManager.addBobMessage("Zdravíčko, neměli byste nějakou suprovou bábovku?", bakerPos);
        messageManager.addMessage(baker.getName(), "To se omlouvám, ale před chvilkou jsme prodali poslední", bakerPos);
        messageManager.addBobMessage("To snad neee...", bakerPos);
        messageManager.addBobMessage("Dneska prostě nic nejde podle plánu.", bakerPos);
        messageManager.addBobMessage("Ostatně jako vždycky...", bakerPos);
        messageManager.addMessage(baker.getName(), "Co povídáte?", bakerPos);
        messageManager.addBobMessage("Ehh.. co musím udělat?", bakerPos);
        messageManager.addMessage(baker.getName(), "Asi nerozumím..", bakerPos);
        messageManager.addBobMessage("Co musím udělat, abyste mi udělal další?", bakerPos);
        messageManager.addMessage(baker.getName(), "Nemůžeme se dostat k naším vajíčkům,", bakerPos);
        messageManager.addMessage(baker.getName(), "protože nás nějaký podivný muž nechce pustit", bakerPos);
        messageManager.addMessage(baker.getName(), "a stále opakuje, že otevřel tajemnou komnatu či co", bakerPos);
        messageManager.addBobMessage("Kudy?", bakerPos);
        messageManager.addMessage(baker.getName(), "Tam tou chodbou se dostanete na dvůr", bakerPos);
    }

    public void render() {

        voidTex.render();
        mapBuilder.renderMap(this.map);
        mapChecker.renderTeleports();
        baker.render();
        bbOrders.render();
        itemManager.renderItems(playerPos);
        bob.render();

        messageManager.showMessage(playerPos);


        if(messageManager.isLastMessage() && !added) {
            added = true;
            mapChecker.addTeleportPad(new Position(7,0), 9);
            Renderer.questManager.addQuest(new Quest(baker.getName(), "Zbav nás toho divného muže", 6));
        }

        if (!Renderer.questManager.hasQuest(6) && added && !addMessages) {
            addMessages = true;
            messageManager.addMessage(baker.getName(), "Ty jsi to dokázal! :O", bakerPos);
            messageManager.addBobMessage("Můžu dostat svoji bábovku?", bakerPos);
            messageManager.addMessage(baker.getName(), "Jasně, tady je!", bakerPos);
            mapChecker.addTeleportPad(new Position(4,5),7);
        }

        if (!Renderer.questManager.hasQuest(6) && messageManager.isLastMessage() && !addCake) {
            addCake = true;
            itemManager.addItem(new Item(Inventory.ItemType.CAKE, new Position(2,1)));
        }

        if(Renderer.questManager.hasQuest(7) && !bread) {
            bread = true;
            messageManager.addMessage(baker.getName(), "Zdravím", bakerPos);
            messageManager.addBobMessage("Máte tu chleba a knedlíky prosím?", bakerPos);
            messageManager.addMessage(baker.getName(), "Díky vaší pomoci už jo, koukejte, dám vám ho...", bakerPos);
            messageManager.addMessage(baker.getName(), "za to žeee....", bakerPos);
            messageManager.addMessage(baker.getName(), "....", bakerPos);
            messageManager.addMessage(baker.getName(), "jste mi pomohl :).", bakerPos);
        }

        if(Renderer.questManager.hasQuest(7) && messageManager.isLastMessage() && !itemBread) {
            itemBread = true;
            itemManager.addItem(new Item(Inventory.ItemType.BREAD, new Position(2,1)));
            messageManager.addMessage(baker.getName(), "A knedlíky jsou vzadu u slepic", bakerPos);
            messageManager.addMessage(baker.getName(), "Neptejte se proč...", bakerPos);
        }

    }
}

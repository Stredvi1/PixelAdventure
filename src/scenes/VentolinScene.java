package scenes;

import entity.Bob;
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

public class VentolinScene extends Scene{

    private Position ventolinPos;
    private NPC ventolin;
    private boolean added = false;
    private Sound tmp;
    private boolean isDiscoScience = false;
    private boolean initDisco = false;


    public VentolinScene(MapBuilder builder, OGLTextRenderer textRenderer) {
        super(builder, textRenderer);
        sceneID = 6;
        hasOwnMusic = true;
    }

    @Override
    public void init() {
        bgMusic = new Sound("audio/music/ventomech.ogg", true);

        mapDesign = new int[][] {
                {4,3,2,2,2,3,2,4,2,2,0,0,0,0,0,1,1,1,7,0,0,},
                {0,0,5,3,5,2,3,2,2,2,2,2,0,2,1,1,7,7,7,7,0,},
                {0,3,2,3,3,2,3,2,2,2,3,2,2,2,1,1,1,1,7,7,0,},
                {0,0,4,4,2,3,3,3,2,4,2,2,2,2,2,1,1,1,7,7,0,},
                {0,0,0,0,0,4,2,2,3,2,3,2,2,2,2,2,2,1,1,1,0,},
                {0,0,0,0,0,2,2,2,3,3,2,2,2,3,2,2,2,2,2,1,1,},
                {0,0,0,0,0,2,2,2,2,3,4,4,4,4,2,2,2,2,2,4,4,},
                {0,0,0,0,2,2,4,2,2,3,6,6,6,4,2,2,4,4,2,2,4,},
                {0,0,0,0,2,2,2,2,2,4,6,6,6,3,3,2,2,3,2,2,2,},
                {0,0,0,2,2,3,2,2,4,4,6,6,6,4,2,3,3,2,2,4,4,},
                {0,0,0,2,2,2,2,2,2,4,4,4,4,4,2,4,2,2,2,3,4,},
                {0,0,0,2,2,2,2,2,4,3,2,3,2,2,2,2,4,2,2,2,2,},
                {0,0,0,0,2,2,2,2,2,2,2,4,2,2,2,2,2,2,2,2,0,},
                {0,0,0,0,0,3,2,2,2,2,2,3,3,2,2,2,2,2,4,2,0,},
                {0,0,0,0,0,0,2,4,3,2,2,2,3,2,2,4,2,4,2,0,0,},
                {0,0,0,0,0,0,0,0,2,2,2,2,2,3,2,2,2,2,0,0,0,},
                {0,0,0,0,0,0,0,0,0,0,0,0,2,4,4,2,4,0,0,0,0,},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,5,4,0,0,0,0,}

        };

        super.init();

        playerPos = new Position(2,1);
        voidTex = new VoidTex(playerPos, map.getHighestWidth(), map.getHeight());
        bob = new Bob(playerPos);

        ventolinPos = new Position(11,8);
        ventolin = new NPC(ventolinPos, "Ventolin", "ventolin.png");

        itemManager.addItem(new Item(Inventory.ItemType.ROLL, new Position(17,0)));
        itemManager.addItem(new Item(Inventory.ItemType.BAGET, new Position(4,9)));
        itemManager.addItem(new Item(Inventory.ItemType.BAGET, new Position(17,15)));

        mapChecker.addTeleportPad(new Position(2,1), 4);

        initMessages();
    }

    public void render() {
        voidTex.render();
        mapBuilder.renderMap(map);
        ventolin.render();
        bob.render();
        itemManager.renderItems(playerPos);

        messageManager.showMessage(playerPos);

        if (messageManager.isLastMessage() && !added) {
            added = true;
            Renderer.questManager.addQuest(new Quest(ventolin.getName(), "Sežeň mi bábovku", 5));
            mapChecker.addTeleportPad(new Position(15, 17), 7);
        }

        if (playerPos.withinRadius(ventolinPos, 1) && Inventory.CAKE == 1) {
            messageManager.addMessage(ventolin.getName(), "Tak tohle byla ta nejlepší bábovka co jsem kdy měl!", ventolinPos);
            messageManager.addMessage(ventolin.getName(), "Tahle písnička je pro tebe!", ventolinPos);
            Inventory.CAKE = 0;
            Renderer.questManager.finishQuest(5);
            isDiscoScience = true;
        }

        if (messageManager.isLastMessage() && isDiscoScience && !initDisco) {
            initDisco = true;
            bgMusic.stop();
            Sound bgMusic2 = new Sound("audio/music/discoscience.ogg", false);
            tmp = bgMusic;
            bgMusic = bgMusic2;
            bgMusic.play();
        }

        if (isDiscoScience && !bgMusic.isPlaying()) {
            isDiscoScience = false;
            bgMusic.delete();
            bgMusic = tmp;
            bgMusic.play();
        }
    }


    private void initMessages() {
        messageManager.addBobMessage("Zdravíčko, vy jste určitě Ventolin!", ventolinPos);
        messageManager.addMessage(ventolin.getName(), "To si piš kluku, to jsem já", ventolinPos);
        messageManager.addBobMessage("Tak to je úžasný! Teď hrajete Mech, žejo?",ventolinPos);
        messageManager.addMessage(ventolin.getName(), "Přesně tak", ventolinPos);
        messageManager.addBobMessage("To je jedna z mých oblíbených :)", ventolinPos);
        messageManager.addBobMessage("Můžete zahrát i Disco Science? Prosíííííím.",ventolinPos);
        messageManager.addMessage(ventolin.getName(), "To bych moc rád, ale nemůžu.", ventolinPos);
        messageManager.addMessage(ventolin.getName(), "K tak ikonický sonze potřebuju moji bábovku", ventolinPos);
        messageManager.addBobMessage("Krucipísek, tak to fakt nevím, kde seženu", ventolinPos);
        messageManager.addMessage(ventolin.getName(), "Vlastně kousek odsud jsem viděl pekárnu", ventolinPos);
        messageManager.addBobMessage("Aaaaa, tak tu chvilku vydžte a hned ji budete mít!", ventolinPos);
    }
}

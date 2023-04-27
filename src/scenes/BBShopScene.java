package scenes;

import entity.Building;
import entity.Bob;
import entity.NPC;
import gameStuff.Sound;
import lwjglutils.OGLTextRenderer;
import map.*;
import quests.Quest;
import render.Renderer;

public class BBShopScene extends Scene{

    private NPC bbMaestro;
    private boolean firstRender = true;
    private Building bbOrders;
    private Position maestroPos;

    private boolean added = false;
    private boolean addedMatrace = false;

    public BBShopScene(MapBuilder builder, OGLTextRenderer textRenderer) {
        super(builder, textRenderer);
        sceneID = 2;
        hasOwnMusic = true;
    }

    @Override
    public void init() {

        bgMusic = new Sound("audio/music/bageterka.ogg", true);

        mapDesign = new int[][] {
                {0,0,0,6,6,6,0,0,0},
                {0,0,6,6,6,6,6,0,0},
                {0,0,6,6,6,6,6,0,0},
                {0,0,6,6,6,6,6,0,0},
                {0,0,6,6,6,6,6,0,0},
                {0,0,6,6,5,6,6,0,0}
        };

        map = new Map(mapDesign);

        super.init();

        playerPos = new Position(4, 5);
        bob = new Bob(playerPos);

        maestroPos = new Position(4,0);

        bbMaestro = new NPC(maestroPos, "BB Maestro", "bb_maestro.png");
        voidTex = new VoidTex(playerPos, "textures/void.png", map.getHighestWidth(), map.getHeight());

        bbOrders = new Building(new Position(3, 1), "bb_orders.png", 3, 2f);

        initMessages(maestroPos);
    }

    private void initMessages(Position maestroPos) {


        messageManager.addBobMessage("Zdravím, můžu poprosit moji oblíbenou bruselskou v meníčku?.", maestroPos);
        messageManager.addMessage("BB Maestro", "...", maestroPos);
        messageManager.addBobMessage("Eehh.", maestroPos);
        messageManager.addMessage("BB Maestro", "...", maestroPos);
        messageManager.addMessage("BB Maestro", "Obávám se, že to nepůjde, omlouvám se.", maestroPos);
        messageManager.addBobMessage("Tak já si obj... Vy brečíte?.", maestroPos);
        messageManager.addMessage("BB Maestro", "...", maestroPos);
        messageManager.addMessage("BB Maestro", "Dnes měla přijít dodávka baget, ale někdo ji přepadl.", maestroPos);
        messageManager.addMessage("BB Maestro", "... Byl v ní i tajný recept na bruselskou bagetu", maestroPos);
        messageManager.addBobMessage("To jakože... No tak to vůbec!", maestroPos);
        messageManager.addBobMessage("Je tohle nějakej prenk? Kde jsou kamery?", maestroPos);
        messageManager.addMessage("BB Maestro", "... Kéžby ...", maestroPos);
        messageManager.addMessage("BB Maestro", "Omluvám se, ale budeme zavírat... nejspíš navždy", maestroPos);
        messageManager.addBobMessage("To NEEEEEE! Já toho padoucha dopadnu a získám recept zpět!", maestroPos);
        messageManager.addMessage("BB Maestro", "Pokud se vám to podaří, máte doživotní zásobu baget jistou :).", maestroPos);
    }

    public void render() {
        if(firstRender) {
            firstRender = false;
            StartingScene.showItems = true;
            Renderer.questManager.finishQuest(1);
        }
        voidTex.render();
        mapBuilder.renderMap(this.map);
        mapChecker.renderTeleports();
        bbMaestro.render();
        bbOrders.render();
        bob.render();

        messageManager.showMessage(playerPos);


        if(Renderer.questManager.hasQuest(4) &&!addedMatrace) {
            addedMatrace = true;
            mapChecker.addTeleportPad(new Position(4, 0), 5);
            messageManager.addBobMessage("Nemáte náhodou ve skladu matraci?", maestroPos);
            messageManager.addMessage(bbMaestro.getName(), "Já už myslel, že jdeš s receptem.", maestroPos);
            messageManager.addMessage(bbMaestro.getName(), "Jedna tam bude, na který spěj brigádníci.", maestroPos);
            messageManager.addMessage(bbMaestro.getName(), "Jen ji musíš najít ve skladu", maestroPos);
            messageManager.addBobMessage("Aaa ten bych našel kde přesně?", maestroPos);
            messageManager.addMessage(bbMaestro.getName(), "Stačí projít dveřmi za mnou.", maestroPos);
            messageManager.addMessage(bbMaestro.getName(), "Ale neztrať se tam!", maestroPos);
        }


        if(messageManager.isLastMessage() && !added) {
            added = true;
            mapChecker.addTeleportPad(new Position(4, 5), 1);
            Renderer.questManager.addQuest(new Quest("BB Maestro", "Získej zpět tajný recept", 2));
        }
    }


}

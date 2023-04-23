package scenes;

import entity.Building;
import entity.Bob;
import entity.NPC;
import lwjglutils.OGLTextRenderer;
import map.*;

public class BBShopScene extends Scene{

    private NPC bbMaestro;
    private boolean firstRender = true;
    private Building bbOrders;

    public BBShopScene(MapBuilder builder, OGLTextRenderer textRenderer) {
        super(builder, textRenderer);
        sceneID = 2;
        init();

    }

    @Override
    protected void init() {


        mapDesign = new int[][] {
                {-1,-1,-1, 6, 6, 6,-1,-1,-1},
                {-1,-1, 6, 6, 6, 6, 6,-1,-1},
                {-1,-1, 6, 6, 6, 6, 6,-1,-1},
                {-1,-1, 6, 6, 6, 6, 6,-1,-1},
                {-1,-1, 6, 6, 6, 6, 6,-1,-1},
                {-1,-1, 6, 6, 5, 6, 6,-1,-1}
        };

        map = new Map(mapDesign);

        super.init();

        playerPos = new Position(4, 5);
        bob = new Bob(playerPos);

        Position maestroPos = new Position(4,0);

        bbMaestro = new NPC(maestroPos, "BB Maestro", "bb_maestro.png");
        voidTex = new VoidTex(playerPos, false);

        bbOrders = new Building(new Position(3, 1), "bb_orders.png", 3);

        mapChecker.addTeleportPos(new Position(4, 5), 1);

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
            StartingScene.showSecretBaget = true;
        }
        voidTex.render();
        mapBuilder.renderMap(this.map);
        bbMaestro.render();
        bbOrders.render();
        bob.render();

        messageManager.showMessage(playerPos);
    }


}

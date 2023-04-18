package scenes;

import entity.BBShop;
import entity.Bob;
import entity.NPC;
import map.*;

public class BBShopScene extends Scene{

    private NPC bbMaestro;
    private boolean firstRender = true;
    private BBShop bbOrders;

    public BBShopScene(MapBuilder builder) {
        super(builder);
        sceneID = 2;
        init();
    }

    @Override
    protected void init() {
        super.init();

        mapDesign = new int[][] {
                {-1,-1,-1, 6, 6, 6,-1,-1,-1},
                {-1,-1, 6, 6, 6, 6, 6,-1,-1},
                {-1,-1, 6, 6, 6, 6, 6,-1,-1},
                {-1,-1, 6, 6, 6, 6, 6,-1,-1},
                {-1,-1, 6, 6, 6, 6, 6,-1,-1},
                {-1,-1, 6, 6, 5, 6, 6,-1,-1}
        };

        map = new Map(mapDesign);

        playerPos = new Position(4, 5);
        bob = new Bob(playerPos);

        bbMaestro = new NPC(new Position(4,0), "bb_maestro.png");
        voidTex = new VoidTex(playerPos, false);

        bbOrders = new BBShop(new Position(3, 1), "bb_orders.png", 3);

        mapChecker.addSpecialPos(new Position(4, 5), 1);


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
    }
}

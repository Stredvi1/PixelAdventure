package scenes;

import entity.BBShop;
import entity.Bob;
import entity.Item;
import map.*;

public class StartingScene extends Scene {

    private BBShop bbShop;
    public static boolean showSecretBaget = false;
    private Item baget, baget1, baget2;

    public StartingScene(MapBuilder builder) {
        super(builder);
        sceneID = 1;
        init();
    }

    protected void init() {
        super.init();
        mapDesign = new int[][]{
                {-1,2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 5, 5, 5, 4, 2},
                {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 4, 4, 2},
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 2, 2},
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 2, 2},
                {4, 4, 4, 4, 4, 4, 3, 3, 4, 4, 4, 4, 5, 5, 4, 2, 2, 2},
                {2, 2, 2, 2, 3, 3, 3, 3, 2, 2, 2, 4, 5, 5, 4, 2, 2, 2},
                {-1,-1,-1,2, 3, 3, 2, 2, 2, 2, 2, 4, 5, 5, 4, 2, 2},
                {-1,-1,-1,-1,3, 3, 2,-1,-1,-1, 2, 4, 5, 5, 4, 2, 2},
                {-1,-1,-1,-1,-1,3, -1,-1,-1,-1, 2, 4, 5, 5, 4, 2},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 2, 4, 5, 5, 4, 2},

        };

        map = new Map(mapDesign);

        playerPos = new Position(1,1);

        voidTex = new VoidTex(playerPos, true);
        bob = new Bob(playerPos);

        bbShop = new BBShop(new Position(12, 0),"bb_shop.png", 3);
        baget = new Item(new Position(5, 8), "bb.png");
        baget1 = new Item(new Position(12, 6), "bb.png");
        baget2 = new Item(new Position(14, 8), "bb.png");


        mapChecker.addSpecialPos(new Position(13, 0), 2);

    }

    public void render() {
        voidTex.render();
        mapBuilder.renderMap(this.map);
        bbShop.render();

        if(showSecretBaget) {
            baget.render();
            baget1.render();
            baget2.render();

            baget.pickUp(playerPos);
            baget1.pickUp(playerPos);
            baget2.pickUp(playerPos);
        }

        bob.render();
    }
}

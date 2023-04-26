package scenes;

import entity.Bob;
import entity.Building;
import items.Inventory;
import lwjglutils.OGLTextRenderer;
import map.MapBuilder;
import map.Position;
import map.VoidTex;
import render.Renderer;

public class BBShopBasementScene extends Scene{

    private Building matrace;
    private Position matracePos;

    private boolean added = false;


    public BBShopBasementScene(MapBuilder builder, OGLTextRenderer textRenderer) {
        super(builder, textRenderer);
        sceneID = 5;
    }

    public void init() {

        mapDesign = new int[][]{
                {6,6,6,6,6,0,0,0,0,0,0,0,0,0,0,0,6,6,6},
                {0,0,0,6,6,0,0,0,0,0,0,0,0,0,0,0,6,6,6},
                {0,0,0,6,6,6,6,6,6,6,6,6,6,6,0,0,0,0,6},
                {0,0,0,6,6,0,0,0,0,0,0,6,0,6,0,0,0,0,6},
                {0,0,0,6,6,0,0,0,0,0,0,6,0,0,0,0,0,0,6},
                {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
                {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
                {0,0,0,0,6,6,0,0,0,0,0,0,0,0,6,6},
                {0,0,0,0,5,5,0,0,0,0,0,0,0,0,6,6},
                {0,0,0,0,0,0,0,6,6,6,6,6,0,0,6,6},
                {0,0,0,0,0,0,0,6,0,0,0,0,0,0,6,6},
                {0,0,0,0,0,0,0,6,6,6,6,6,6,6,6,6},
                {0,0,0,0,0,0,0,0,0,6,0,0,0,0,0,6,6,6,6},
                {6,6,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,6},
                {6,6,6,6,6,6,6,6,6,6,0,0,0,0,0,0,0,0,6},
                {0,0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,6},
                {0,0,0,0,0,0,0,0,0,6,6,6,6,6,6,6,6,6,6}
        };

        super.init();

        playerPos = new Position(4,8);
        voidTex = new VoidTex(playerPos, "textures/void.png");
        bob = new Bob(playerPos);

        matracePos = new Position(0,14);
        matrace = new Building(matracePos, "matrace.png", 2, 0.3f);

        mapChecker.addTeleportPad(new Position(4,8),2);
        mapChecker.addTeleportPad(new Position(5,8),2);
//TODO: přidat bagety
    }

    public void render() {
        Renderer.setCameraHeight(30);

        voidTex.render();
        mapBuilder.renderMap(map);

        if(!added) {;
            matrace.render();
        }
        if (playerPos.withinRadius(matracePos, 1) && !added) {
            added = true;
            Inventory.MATRACE = 1;
        }

        bob.render();
    }
}

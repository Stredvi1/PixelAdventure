package scenes;

import entity.Bob;
import entity.Building;
import entity.PortPad;
import gameStuff.Sound;
import items.Inventory;
import items.Item;
import lwjglutils.OGLTextRenderer;
import map.MapBuilder;
import map.Position;
import map.VoidTex;
import org.lwjgl.system.CallbackI;

public class InfinityRoadScene extends Scene {

    private final Position endPos1 = new Position(21, 1);
    private final Position endPos2 = new Position(22, 2);

    private final Position startPos1 = new Position(0, 1);
    private final Position startPos2 = new Position(1, 2);


    public InfinityRoadScene(MapBuilder builder, OGLTextRenderer textRenderer) {
        super(builder, textRenderer);
        sceneID = 11;
        hasOwnMusic = true;
    }

    @Override
    public void init() {

        bgMusic = new Sound("audio/music/badboi.ogg", true);

        mapDesign = new int[][]{
                {4, 4, 4, 4, 4, 0, 4, 4, 4, 0, 4, 4, 4, 4, 4, 2, 4, 4, 0, 0, 3, 4},
                {5, 5, 3, 5, 5, 0, 0, 5, 5, 5, 5, 0, 5, 5, 2, 3, 5, 5, 5, 0, 5, 5},
                {0, 5, 5, 5, 5, 5, 0, 5, 5, 5, 5, 5, 2, 5, 0, 5, 5, 5, 5, 2, 5, 5, 5},
                {4, 4, 4, 0, 4, 4, 2, 4, 4, 4, 4, 0, 0, 0, 4, 4, 4, 2, 3, 4, 4, 4},
        };

        super.init();


        playerPos = new Position(1, 1);
        voidTex = new VoidTex(playerPos, "textures/cloud_dark.png", map.getHighestWidth(), map.getHeight());
        bob = new Bob(playerPos);

        itemManager.addItem(new Item(Inventory.ItemType.BAGET, new Position(16, 2)));


        mapChecker.addTeleportPad(startPos2, 10);
        mapChecker.addTeleportPad(startPos1, 10);
        mapChecker.addTeleportPad(endPos1, 12);
        mapChecker.addTeleportPad(endPos2, 12);

    }


    public void render() {
        voidTex.render();
        mapBuilder.renderMap(map);
        mapChecker.renderTeleports();


        bob.render();

        itemManager.renderItems(playerPos);
    }

}

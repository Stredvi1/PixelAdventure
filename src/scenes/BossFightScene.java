package scenes;

import entity.Bob;
import gameStuff.DamageBar;
import gameStuff.Sound;
import lwjglutils.OGLTextRenderer;
import map.Map;
import map.MapBuilder;
import map.Position;
import map.VoidTex;

public class BossFightScene extends Scene{


    public BossFightScene(MapBuilder builder, OGLTextRenderer textRenderer) {
        super(builder, textRenderer);
        sceneID = 99;
        hasFight = true;
        hasOwnMusic = true;
        bar = new DamageBar(textRenderer);
    }

    public void init() {

        bgMusic = new Sound("audio/music/boss.ogg", true);

        mapDesign = new int[][] {
                {0,0,3, 5, 5, 5, 5, 5,0},
                {0,5, 3, 0, 5, 5, 3, 5,0},
                {0,3, 5, 5, 0, 3, 5,0,0},
                {0,5, 5, 5, 0, 0, 5, 5,0},
                {0,0, 3, 5, 5, 0, 5,0,0},
                {0,0, 5, 5, 3, 5, 0,0,0}
        };
        map = new Map(mapDesign);

        super.init();
        playerPos = new Position(4, 5);
        bob = new Bob(playerPos);
        voidTex = new VoidTex(playerPos, "textures/lava.png", map.getHighestWidth(), map.getHeight());
        mapChecker.addTeleportPad(new Position(4, 5), 1);

    }

    private void initMessages() {

    }

    public void render() {
        voidTex.render();
        mapBuilder.renderMap(this.map);
        bob.render();
        bar.renderDamageBar(playerPos);
    }



}

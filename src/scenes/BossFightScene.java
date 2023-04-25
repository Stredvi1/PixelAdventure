package scenes;

import entity.Bob;
import gameStuff.DamageBar;
import gameStuff.Sound;
import lwjglutils.OGLTextRenderer;
import map.Map;
import map.MapBuilder;
import map.Position;
import map.VoidTex;
import render.Renderer;

public class BossFightScene extends Scene{

    private final DamageBar bar;

    public BossFightScene(MapBuilder builder, OGLTextRenderer textRenderer) {
        super(builder, textRenderer);
        sceneID = 9;
        hasFight = true;
        bar = new DamageBar();
    }

    public void init() {

        bgMusic = new Sound("audio/music/boss.ogg", true);

        mapDesign = new int[][] {
                {-1,-1,3, 5, 5, 5, 5, 5,-1},
                {-1,5, 3, -1, 5, 5, 3, 5,-1},
                {-1,3, 5, 5, -1, 3, 5,-1,-1},
                {-1,5, 5, 5, -1, -1, 5, 5,-1},
                {-1,-1, 3, 5, 5, -1, 5,-1,-1},
                {-1,-1, 5, 5, 3, 5, -1,-1,-1}
        };
        map = new Map(mapDesign);

        super.init();
        playerPos = new Position(4, 5);
        bob = new Bob(playerPos);
        voidTex = new VoidTex(playerPos, "textures/lava.png");
        mapChecker.addTeleportPos(new Position(4, 5), 1);

    }

    private void initMessages() {

    }

    public void render() {
        voidTex.render();
        mapBuilder.renderMap(this.map);
        bob.render();
        bar.renderDamageBar(playerPos);
    }


    public void hit() {
        bar.hit();
    }
}

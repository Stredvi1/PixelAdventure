package scenes;

import entity.Bob;
import entity.NPC;
import gameStuff.Sound;
import lwjglutils.OGLTextRenderer;
import map.MapBuilder;
import map.Position;
import map.VoidTex;

public class VentolinScene extends Scene{

    private Position ventolinPos;
    private NPC ventolin;


    public VentolinScene(MapBuilder builder, OGLTextRenderer textRenderer) {
        super(builder, textRenderer);
        sceneID = 5;
        hasOwnMusic = true;
    }

    @Override
    public void init() {
        bgMusic = new Sound("audio/music/ventomech.ogg", true);

        mapDesign = new int[][] {
                {4,3,2,2,2,3,2,4,2,2,0,0,0,0,0,0,2,2,2,2,2,},
                {0,0,5,3,5,2,3,2,2,2,2,2,0,0,4,2,2,2,2,2,2,},
                {0,5,2,3,3,2,3,2,2,2,3,2,2,2,2,7,4,2,4,2,2,},
                {0,0,4,4,2,3,3,3,2,4,2,2,2,2,2,3,2,4,2,2,2,},
                {0,0,0,0,0,4,2,2,3,2,3,2,2,2,2,2,4,2,2,2,2,},
                {0,0,0,0,0,2,2,2,3,3,2,2,2,3,2,2,7,2,2,2,2,},
                {0,0,0,0,0,2,2,2,2,3,4,4,4,4,2,2,2,2,2,4,4,},
                {0,0,0,0,2,2,4,2,2,3,6,6,6,4,2,2,4,4,2,2,4,},
                {0,0,0,0,2,2,2,2,2,4,6,6,6,3,3,2,2,3,2,2,2,},
                {0,0,0,2,2,7,2,2,4,4,6,6,6,4,2,3,3,2,2,4,4,},
                {0,0,0,2,2,2,2,2,2,4,4,4,4,4,2,4,2,2,2,3,4,},
                {0,0,0,2,2,2,2,2,4,3,2,3,2,2,2,2,4,2,2,2,2,},
                {0,0,0,0,2,2,2,2,2,2,2,4,2,2,2,2,2,2,2,2,2,},
                {0,0,0,0,0,3,2,2,2,2,2,3,3,2,2,2,2,2,4,2,2,},
                {0,0,0,0,0,2,2,4,3,2,2,2,3,2,2,4,2,4,2,2,2,},
                {0,0,0,0,0,2,4,2,2,2,2,2,2,3,2,2,2,2,2,4,2,}
        };

        super.init();

        playerPos = new Position(2,1);
        voidTex = new VoidTex(playerPos);
        bob = new Bob(playerPos);

        ventolinPos = new Position(11,8);
        ventolin = new NPC(ventolinPos, "Ventolin", "ventolin.png");

        //initMessages();
    }

    public void render() {
        voidTex.render();
        mapBuilder.renderMap(map);
        ventolin.render();
        bob.render();

        ventolin.render();

    }
}

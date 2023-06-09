package map;


import lwjglutils.OGLTexture2D;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class MapBuilder {

    private OGLTexture2D water, grass, dirt, stone, sand, asphalt, tile, wood, disco;
    private Map map;
    public static int MAP_SIZE = 5;

    public MapBuilder() {
        init();
    }

    public MapBuilder(Map map, int mapSize) {
        this.map = map;
        this.MAP_SIZE = mapSize;
        init();
    }

    private void init() {
        try {
            grass = new OGLTexture2D("textures/grass.png");
            stone = new OGLTexture2D("textures/stone.png");
            dirt = new OGLTexture2D("textures/dirt.png");
            water = new OGLTexture2D("textures/water.png");
            sand = new OGLTexture2D("textures/sand.png");
            asphalt = new OGLTexture2D("textures/asphalt.png");
            tile = new OGLTexture2D("textures/tile.png");
            wood = new OGLTexture2D("textures/wood.png");
            disco = new OGLTexture2D("textures/disco.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void renderMap(Map map) {

        for (int i = 0; i < map.getHeight(); i++) {
            //Loop for rows
            for (int j = 0; j < map.getRowLength(i); j++) {
                //Loop for parcels

                int parcel = map.getParcel(i,j);

                switch (parcel) {
                    case Map.WATER -> water.bind();
                    case Map.GRASS -> grass.bind();
                    case Map.DIRT -> dirt.bind();
                    case Map.STONE -> stone.bind();
                    case Map.SAND -> sand.bind();
                    case Map.ASPHALT -> asphalt.bind();
                    case Map.TILE -> tile.bind();
                    case Map.WOOD -> wood.bind();
                    case Map.DISCO -> disco.bind();

                    case Map.VOID -> {
                        continue;
                    }
                }

                glMatrixMode(GL_MODELVIEW);
                glPushMatrix();
                glLoadIdentity();

                glTranslatef((float) ((j) * MAP_SIZE), (float) (-(i) * MAP_SIZE), 0);

                float end;
                end = MAP_SIZE;


                GL11.glBegin(GL_QUADS);
                glColor3f(1f, 1f, 1f);

                glTexCoord2f(0, 0);
                glVertex2f(0, 0);

                glTexCoord2f(1, 0);
                glVertex2f(end, 0);

                glTexCoord2f(1, 1);
                glVertex2f(end, end);

                glTexCoord2f(0, 1);
                glVertex2f(0, end);

                glEnd();
                glPopMatrix();
            }
        }
    }
}

package map;


import lwjglutils.OGLTexture2D;
import org.lwjgl.opengl.GL11;

import static lwjglutils.GlutUtils.glutSolidCube;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjglx.debug.GLmetadata.glBegin;

public class MapBuilder {

    private OGLTexture2D water, grass, dirt, stone, sand;
    private Map map;
    private int mapSize = 5;

    public MapBuilder(Map map) {
        this.map = map;
        init();
    }

    public MapBuilder(Map map, int mapSize) {
        this.map = map;
        this.mapSize = mapSize;
        init();
    }

    private void init() {
        try {
            grass = new OGLTexture2D("textures/grass.png");
            stone = new OGLTexture2D("textures/stone.png");
            dirt = new OGLTexture2D("textures/dirt.png");
            water = new OGLTexture2D("textures/water.gif");
            sand = new OGLTexture2D("textures/sand.jpg");
            //bob = new OGLTexture2D("textures/bob.png");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void renderMap() {

        for (int i = 0; i < map.getHeight(); i++) {
            //Loop for rows

            for (int j = 0; j < map.getRowLength(i); j++) {
                //Loop for parcels

                int parcel = map.getParcel(i, j);

                switch (parcel) {
                    case Map.WATER -> water.bind();
                    case Map.GRASS -> grass.bind();
                    case Map.DIRT -> dirt.bind();
                    case Map.STONE -> stone.bind();
                    case Map.SAND -> sand.bind();
                }


                glMatrixMode(GL_MODELVIEW);
                glPushMatrix();
                glLoadIdentity();

//                glScalef(0.1f, 0.1f, 1);
                glTranslatef((float) ((j) * mapSize), (float) (-(i) * mapSize), 0);

                float end;
                end = mapSize;


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

    public int getMapSize() {
        return mapSize;
    }

    public Position getCenter() {
        Position pos = new Position(map.getWidth() / 2, map.getHeight() / 2, mapSize);
        System.out.println(pos.toParcel()[0] + "  " + pos.toParcel()[1]);
        return pos;
    }
}

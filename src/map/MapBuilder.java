package map;


import lwjglutils.OGLTexture2D;

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
            water = new OGLTexture2D("textures/water.png");
            sand = new OGLTexture2D("textures/sand.jpg");
            //bob = new OGLTexture2D("textures/bob.png");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void renderMap() {

        glMatrixMode(GL_MODELVIEW);



        for(int i = 0; i < map.getHeight(); i++) {
            for(int j = 0; j < map.getWidth(); j++) {

                int parcel = map.getParcel(i, j);

                switch (parcel) {
                    case Map.WATER -> water.bind();
                    case Map.GRASS -> grass.bind();
                    case Map.DIRT -> dirt.bind();
                    case Map.STONE -> stone.bind();
                    case Map.SAND -> sand.bind();


                }
                glLoadIdentity();
                glPushMatrix();
                glTranslatef(j*mapSize, i*mapSize, 0);
                glutSolidCube(10);
                glPopMatrix();
            }
        }

    }
}

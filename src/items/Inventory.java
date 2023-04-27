package items;

import lwjglutils.OGLTextRenderer;
import render.Renderer;

public class Inventory {

   public enum ItemType {
       BAGET, GLASSES, MAGIC_WAND, ROLL, BREAD, GOULASH, DUMPLING, CAKE
   }

    public static int BAGET, MAGIC_WAND, GLASSES, MATRACE, ROLL, BREAD, GOULASH, DUMPLING, CAKE;



   public static boolean SHOW_INVENTORY = false;
    private OGLTextRenderer textRenderer;
    private int startingPixel = (int) (Renderer.HEIGHT  *  0.1);
    private float spacing = startingPixel * 0.6f;


    public Inventory(OGLTextRenderer textRenderer) {
    this.textRenderer = textRenderer;
    }

    public void showInventory() {

        if(!SHOW_INVENTORY) {
            return;
        }

        int move = (int)(startingPixel - spacing);

        if(BAGET != 0) {
            textRenderer.addStr2D(startingPixel, move += spacing, "Bagety: " + BAGET + "/" + Renderer.AllBagetCount);
        }
        if(MAGIC_WAND != 0) {
            textRenderer.addStr2D(startingPixel, (move += spacing), "Hůlka: " + MAGIC_WAND);
        }
        if(GLASSES != 0) {
            textRenderer.addStr2D(startingPixel, (move += spacing), "Brýle: " + GLASSES);
        }
        if(MATRACE != 0) {
            textRenderer.addStr2D(startingPixel, (move += spacing), "Matrace: " + MATRACE);
        }
        if(ROLL != 0) {
            textRenderer.addStr2D(startingPixel, (move += spacing), "Rohlíky: " + ROLL);
        }
        if(BREAD != 0) {
            textRenderer.addStr2D(startingPixel, (move += spacing), "Chleba: " + BREAD);
        }
        if(GOULASH != 0) {
            textRenderer.addStr2D(startingPixel, (move += spacing), "Guláš: " + GOULASH);
        }
        if(DUMPLING != 0) {
            textRenderer.addStr2D(startingPixel, (move += spacing), "Knedlíky: " + DUMPLING);
        }
        if(CAKE != 0) {
            textRenderer.addStr2D(startingPixel, (move += spacing), "Bábovka: " + CAKE);
        }

    }

    public void resize() {
        startingPixel = (int) (Renderer.HEIGHT  *  0.1);
        spacing = startingPixel * 0.6f;
    }

}

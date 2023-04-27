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

    public Inventory(OGLTextRenderer textRenderer) {
    this.textRenderer = textRenderer;
    }

    public void showInventory() {

        if(!SHOW_INVENTORY) {
            return;
        }

        if(BAGET != 0) {
            textRenderer.addStr2D(startingPixel, startingPixel, "Bagety: " + BAGET);
        }
        if(MAGIC_WAND != 0) {
            textRenderer.addStr2D(startingPixel, (startingPixel *= 1.5), "Hůlka: " + MAGIC_WAND);
        }
        if(GLASSES != 0) {
            textRenderer.addStr2D(startingPixel, (startingPixel *= 1.5), "Brýle: " + GLASSES);
        }
        if(MATRACE != 0) {
            textRenderer.addStr2D(startingPixel, (startingPixel *= 1.5), "Matrace: " + MATRACE);
        }
        if(ROLL != 0) {
            textRenderer.addStr2D(startingPixel, (startingPixel *= 1.5), "Rohlíky: " + ROLL);
        }
        if(BREAD != 0) {
            textRenderer.addStr2D(startingPixel, (startingPixel *= 1.5), "Chleba: " + BREAD);
        }
        if(GOULASH != 0) {
            textRenderer.addStr2D(startingPixel, (startingPixel *= 1.5), "Guláš: " + GOULASH);
        }
        if(DUMPLING != 0) {
            textRenderer.addStr2D(startingPixel, (startingPixel *= 1.5), "Knedlíky: " + DUMPLING);
        }
        if(CAKE != 0) {
            textRenderer.addStr2D(startingPixel, (startingPixel *= 1.5), "Bábovka: " + CAKE);
        }

    }

    public void resize() {
        startingPixel = (int) (Renderer.HEIGHT  *  0.1);
    }

}

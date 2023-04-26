package items;

import lwjglutils.OGLTextRenderer;
import render.Renderer;

public class Inventory {

   public enum ItemType {
       BAGET, GLASSES, MAGIC_WAND
   }

    public static int BAGET, GLASSES, MATRACE;

   public static boolean SHOW_INVENTORY = false;
    private OGLTextRenderer textRenderer;

    private int startingPixel = (int) (Renderer.HEIGHT  *  0.1);

    public Inventory(OGLTextRenderer textRenderer) {
    this.textRenderer = textRenderer;
    }

    public int getBaget() {
        return BAGET;
    }

    public void showInventory() {

        if(!SHOW_INVENTORY) {
            return;
        }

        if(BAGET != 0) {
            textRenderer.addStr2D(startingPixel, startingPixel, "Bagety: " + BAGET);
        }
        if(GLASSES != 0) {
            textRenderer.addStr2D(startingPixel, (int) (startingPixel * 1.5), "Brýle: " + GLASSES);
        }
        if(MATRACE != 0) {
            textRenderer.addStr2D(startingPixel, (startingPixel * 3), "Matrace: " + MATRACE);
        }
    }

    public void resize() {
        startingPixel = (int) (Renderer.HEIGHT  *  0.1);
    }

}

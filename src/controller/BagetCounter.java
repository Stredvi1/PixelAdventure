package controller;

import lwjglutils.OGLTextRenderer;

import window.Window;
import java.awt.Color;



public class BagetCounter {

    private final OGLTextRenderer textRenderer;
    public static int count = 0;
    private String text;

    public BagetCounter(OGLTextRenderer textRenderer) {
        this.textRenderer = textRenderer;

        textRenderer.setBackgroundColor(new Color(0xe4ad00));
        textRenderer.setColor(Color.white);
        textRenderer.setScale(3);
    }

    public void showCount() {
        text = "Bagety: " + count;
        textRenderer.addStr2D(40, 80, text);
    }
}

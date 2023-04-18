package controller;

import lwjglutils.OGLTextRenderer;

import java.awt.*;

public class BagetCounter {

    private final OGLTextRenderer textRenderer;
    public static int count = 0;
    private String text;

    public BagetCounter(OGLTextRenderer textRenderer) {
        this.textRenderer = textRenderer;

        textRenderer.setBackgroundColor(Color.BLACK);
        textRenderer.setColor(new Color(0xe4ad00));
        textRenderer.setScale(5);
    }

    public void showCount() {
        text = "Počet baget: " + count;
        textRenderer.addStr2D(40, 80, text);
    }


}

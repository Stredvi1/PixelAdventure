package render;

import entity.*;
import map.*;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import scenes.BBShopScene;
import scenes.Scene;
import scenes.StartingScene;
import window.Window;


import java.util.ArrayList;
import java.util.Arrays;

import static lwjglutils.GluUtils.gluLookAt;
import static lwjglutils.GluUtils.gluPerspective;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Shows using 3D transformation and interaction in a scene
 *
 * @author PGRF FIM UHK
 * @version 3.1
 * @since 2020-01-20
 */
public class Renderer extends AbstractRenderer {

    public static int WIDTH = Window.WIDTH;
    public static int HEIGHT = Window.HEIGHT;

    private float[] modelMatrix = new float[16];

    private MapBuilder mapBuilder;

    private Scene ACTIVE, starting, bbShop;
    private ArrayList<Scene> scenes;

    public Inventory inventory;

    public Renderer() {
        super();


        glfwKeyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                    // We will detect this in our rendering loop
                    glfwSetWindowShouldClose(window, true);
                if (action == GLFW_RELEASE) {
                    //do nothing
                }
                if (action == GLFW_PRESS) {

                    int teleportID;

                    switch (key) {
                        case GLFW_KEY_W:
                            ACTIVE.up();
                            break;
                        case GLFW_KEY_S:
                            ACTIVE.down();
                            break;
                        case GLFW_KEY_A:
                            ACTIVE.left();
                            break;
                        case GLFW_KEY_D:
                            ACTIVE.right();
                            break;
                        case GLFW_KEY_ENTER:
                            ACTIVE.nextMessage();
                    }
                    teleportID = ACTIVE.checkCurrentPos();
                    if (teleportID != 0 && teleportID != ACTIVE.getSceneID()) {
                        System.out.println("HUH? UPDATE");
                        updateActiveScene(teleportID);

                    }
                }
            }
        };
    }



    @Override
    public void init() {
        super.init();
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        glFrontFace(GL_CCW);
        glPolygonMode(GL_FRONT, GL_FILL);
        glPolygonMode(GL_BACK, GL_LINE);
        glDisable(GL_CULL_FACE);
        glDisable(GL_LIGHTING);
        glMatrixMode(GL_MODELVIEW);

        glLoadIdentity();
        glGetFloatv(GL_MODELVIEW_MATRIX, modelMatrix);

        textRenderer.setScale(2.5);

        mapBuilder = new MapBuilder();
        inventory = new Inventory(textRenderer);

        starting = new StartingScene(mapBuilder, textRenderer);
        bbShop = new BBShopScene(mapBuilder, textRenderer);

        scenes = new ArrayList<>();
        scenes.addAll(Arrays.asList(starting, bbShop));

        ACTIVE = starting;


        glEnable(GL_TEXTURE_2D);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
        glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
    }

    @Override
    public void display() {
        glViewport(0, 0, width, height);

        clearBuffers();


        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(45, width / (float) height, 0.1f, 100.0f);


        gluLookAt(ACTIVE.getPlayerPos().toMap()[0], ACTIVE.getPlayerPos().toMap()[1], 50, ACTIVE.getPlayerPos().toMap()[0], ACTIVE.getPlayerPos().toMap()[1], 0, 0, 1, 0);


        glMatrixMode(GL_MODELVIEW);

        glLoadIdentity();

        ACTIVE.render();

        inventory.showInventory();


    }

    private void clearBuffers() {
        glClearColor(0f, 0f, 0f, 1f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    }

    private void updateActiveScene(int teleportID) {

        for(Scene scene : scenes) {
            if(scene.getSceneID() == teleportID) {
                ACTIVE = scene;
            }
        }
    }

    protected GLFWWindowSizeCallback glfwWindowSizeCallback = new GLFWWindowSizeCallback() {
        @Override
        public void invoke(long window, int w, int h) {
            if (w > 0 && h > 0) {
                width = WIDTH = w;
                height = HEIGHT = h;
                System.out.println("Windows resize to [" + w + ", " + h + "]");
                if (textRenderer != null) {
                    textRenderer.resize(width, height);

                    if(width >= 3000) {
                        System.out.println("scale 6");
                        textRenderer.setScale(6);
                    } else if (width >= 1000) {
                        System.out.println("scale 2.5");
                        textRenderer.setScale(2.5);
                    } else {
                        System.out.println("scale 2");
                        textRenderer.setScale(2);
                    }
                }
            }
        }
    };

    public GLFWWindowSizeCallback getGlfwWindowSizeCallback() {
        return glfwWindowSizeCallback;
    }

}

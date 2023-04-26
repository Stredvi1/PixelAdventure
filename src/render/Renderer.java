package render;

import gameStuff.Sound;
import items.Inventory;
import map.*;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import quests.QuestManager;
import scenes.*;
import window.Window;


import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static lwjglutils.GluUtils.gluLookAt;
import static lwjglutils.GluUtils.gluPerspective;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;


public class Renderer extends AbstractRenderer {

    public static int WIDTH = Window.WIDTH;
    public static int HEIGHT = Window.HEIGHT;

    private float[] modelMatrix = new float[16];

    private MapBuilder mapBuilder;

    public static Scene ACTIVE;
    private Scene starting, bbShop, lostGranny, svarta, basement, bossFight;
    private ArrayList<Scene> scenes;

    public Inventory inventory;
    public static QuestManager questManager;

    public static Color mainColor = new Color(0xe4ad00);

    private double initScaleText = 1.5;

    protected Sound footstep;

    public static final int normalCameraHeight = 50;
    private static int cameraHeight = normalCameraHeight;



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
                            break;
                        case GLFW_KEY_SPACE:
                            if (ACTIVE.hasFight) {
                                ACTIVE.hit();
                            }
                            break;
                        case GLFW_KEY_TAB:
                            questManager.toggle();
                            break;
                    }
                    footstep.play();

                    teleportID = ACTIVE.checkCurrentPos();
                    if (teleportID != 0 && teleportID != ACTIVE.getSceneID()) {
                        updateActiveScene(teleportID);
                    }
                }
            }
        };
    }

    public static void setCameraHeight(int height) {
        cameraHeight = height;
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
        glLineWidth(4);

        textRenderer.setScale(initScaleText);
        footstep = new Sound("audio/sounds/footstep.ogg", false, true);


        mapBuilder = new MapBuilder();
        inventory = new Inventory(textRenderer);
        questManager = new QuestManager(textRenderer);

        starting = new StartingScene(mapBuilder, textRenderer);
        bbShop = new BBShopScene(mapBuilder, textRenderer);
        lostGranny = new LostGrandmaScene(mapBuilder, textRenderer);
        svarta = new SvartaScene(mapBuilder, textRenderer);
        basement = new BBShopBasementScene(mapBuilder, textRenderer);
        bossFight = new BossFightScene(mapBuilder,textRenderer);

        scenes = new ArrayList<>();
        scenes.addAll(Arrays.asList(starting, bbShop, lostGranny, svarta, basement, bossFight));


        ACTIVE = starting;
        ACTIVE.init();
        ACTIVE.playMusic();


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


        gluLookAt(ACTIVE.getPlayerPos().toMap()[0], ACTIVE.getPlayerPos().toMap()[1], cameraHeight, ACTIVE.getPlayerPos().toMap()[0], ACTIVE.getPlayerPos().toMap()[1], 0, 0, 1, 0);


        glMatrixMode(GL_MODELVIEW);

        glLoadIdentity();

        ACTIVE.render();

        inventory.showInventory();

        questManager.render(ACTIVE.getPlayerPos());

    }

    private void clearBuffers() {
        glClearColor(0f, 0f, 0f, 1f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    }

    private void updateActiveScene(int teleportID) {

        for(Scene scene : scenes) {
            if(scene.getSceneID() == teleportID) {
                Position teleportTo = ACTIVE.getTeleportPos();

                if(scene.hasOwnMusic) {
                    ACTIVE.stopMusic();
                }
                ACTIVE = scene;
                if(!ACTIVE.isInit) {
                    ACTIVE.init();
                }
                ACTIVE.playMusic();
                setCameraHeight(normalCameraHeight);

                if(teleportTo != null) {
                    ACTIVE.setPlayerPos(teleportTo);
                }
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
                    questManager.resize(width, height);
                    inventory.resize();

                    if(width >= 3000) {
                        textRenderer.setScale(4);
                        glLineWidth(12);
                    } else if (width >= 1000) {
                        textRenderer.setScale(initScaleText);
                        glLineWidth(4);
                    } else {
                        textRenderer.setScale(1);
                        glLineWidth(3);
                    }
                }
            }
        }
    };

    public GLFWWindowSizeCallback getGlfwWindowSizeCallback() {
        return glfwWindowSizeCallback;
    }

}

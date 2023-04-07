package render;

import window.AbstractRenderer;
import lwjglutils.OGLTexture2D;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import java.nio.DoubleBuffer;

import static lwjglutils.GluUtils.gluLookAt;
import static lwjglutils.GluUtils.gluPerspective;
import static lwjglutils.GlutUtils.glutSolidCube;
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

    private double dx, dy;
    private double ox, oy;

    private float[] modelMatrix = new float[16];

    private boolean per = false, depth = true;
    private boolean mouseButton1 = false;

    private OGLTexture2D grass, stone;

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
                    switch (key) {
                        case GLFW_KEY_P:
                            per = !per;
                            break;
                        case GLFW_KEY_D:
                            depth = !depth;
                            break;
                        case GLFW_KEY_M:
                            break;
                    }
                }
            }
        };

        glfwMouseButtonCallback = new GLFWMouseButtonCallback() {

            @Override
            public void invoke(long window, int button, int action, int mods) {
                DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
                DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
                glfwGetCursorPos(window, xBuffer, yBuffer);
                double x = xBuffer.get(0);
                double y = yBuffer.get(0);

                mouseButton1 = glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS;

                if (button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS) {
                    ox = x;
                    oy = y;
                }
            }

        };

        glfwCursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double x, double y) {
                if (mouseButton1) {
                    dx = x - ox;
                    dy = y - oy;
                    ox = x;
                    oy = y;
                }
            }
        };

        glfwScrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double dx, double dy) {
                //do nothing
            }
        };
    }

    @Override
    public void init() {
        super.init();
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        glEnable(GL_DEPTH_TEST);

        glFrontFace(GL_CCW);
        glPolygonMode(GL_FRONT, GL_FILL);
        glPolygonMode(GL_BACK, GL_LINE);
        glDisable(GL_CULL_FACE);
        glDisable(GL_LIGHTING);
        glMatrixMode(GL_MODELVIEW);

        glLoadIdentity();
        glGetFloatv(GL_MODELVIEW_MATRIX, modelMatrix);

        try {
            grass = new OGLTexture2D("textures/grass.png");
            stone = new OGLTexture2D("textures/stone.png");


        } catch (Exception e) {
            e.printStackTrace();
        }

        glEnable(GL_TEXTURE_2D);
    }

    @Override
    public void display() {
        glViewport(0, 0, width, height);
        // zapnuti nebo vypnuti viditelnosti
        if (depth)
            glEnable(GL_DEPTH_TEST);
        else
            glDisable(GL_DEPTH_TEST);

        // mazeme image buffer i z-buffer
        glClearColor(0f, 0f, 0f, 1f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer



        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(45, width / (float) height, 0.1f, 100.0f);

        // divame se do sceny z kladne osy x, osa z je svisla
        gluLookAt(50, 20, 15, 0, 0, 0, 0, 0, 1);


        glMatrixMode(GL_MODELVIEW);

        glLoadIdentity();
        glPushMatrix();

        grass.bind();
        glutSolidCube(5);

        stone.bind();

        glTranslatef(20, 0, 0);
        glutSolidCube(4);

        glPopMatrix();

        glBegin(GL_LINES);
        glColor3f(1f, 0f, 0f);
        glVertex3f(0f, 0f, 0f);
        glVertex3f(100f, 0f, 0f);
        glColor3f(0f, 1f, 0f);
        glVertex3f(0f, 0f, 0f);
        glVertex3f(0f, 100f, 0f);
        glColor3f(0f, 0f, 1f);
        glVertex3f(0f, 0f, 0f);
        glVertex3f(0f, 0f, 100f);
        glEnd();

        float[] color = {1.0f, 1.0f, 1.0f};
        glColor3fv(color);
        glDisable(GL_DEPTH_TEST);

    }

}
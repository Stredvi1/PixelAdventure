package window;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.MemoryStack;
import render.AbstractRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import static org.lwjgl.BufferUtils.createByteBuffer;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    public static int WIDTH = 1280;
    public static int HEIGHT = 720;

    // The window handle
    private long window;
    private AbstractRenderer renderer;

    //Audio
    private long audioContext;
    private long audioDevice;

    private static boolean DEBUG = false;

    static {
        if (DEBUG) {
            System.setProperty("org.lwjgl.util.Debug", "true");
            System.setProperty("org.lwjgl.util.NoChecks", "false");
            System.setProperty("org.lwjgl.util.DebugLoader", "true");
            System.setProperty("org.lwjgl.util.DebugAllocator", "true");
            System.setProperty("org.lwjgl.util.DebugStack", "true");
            Configuration.DEBUG_MEMORY_ALLOCATOR.set(true);
        }
    }

    public Window(AbstractRenderer renderer) {
        this(WIDTH, HEIGHT, renderer, false);
    }

    public Window(AbstractRenderer renderer, boolean debug) {
        this(WIDTH, HEIGHT, renderer, debug);
    }

    public Window(int width, int height, AbstractRenderer renderer, boolean debug) {
        this.renderer = renderer;
        DEBUG = debug;
        WIDTH = width;
        HEIGHT = height;
        if (DEBUG)
            System.err.println("Run in debugging mode");
        run();
    }

    public void run() {
        init();

        loop();

        renderer.dispose();

        // Destroy audio context
        alcDestroyContext(audioContext);
        alcCloseDevice(audioDevice);

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);


        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable


        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "VITO Baget Adventure", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Load window icon
        try {
            InputStream is = this.getClass().getResourceAsStream("/textures/bob.png");
            assert is != null;
            BufferedImage bi = ImageIO.read(is);
            byte[] iconData = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
            ByteBuffer ib = createByteBuffer(iconData.length);
            ib.put(iconData);
            ib.flip();
            GLFWImage.Buffer gb = GLFWImage.create(1);
            GLFWImage iconGI = GLFWImage.create().set(339, 339, ib);
            gb.put(0, iconGI);
            glfwSetWindowIcon(window, gb);
        }
        catch (Exception e){
            System.out.println("Couldn't open icon image..." + e.toString());
        }

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, renderer.getGlfwKeyCallback());
        glfwSetWindowSizeCallback(window, renderer.getGlfwWindowSizeCallback());
        glfwSetMouseButtonCallback(window, renderer.getGlfwMouseButtonCallback());
        glfwSetCursorPosCallback(window, renderer.getGlfwCursorPosCallback());
        glfwSetScrollCallback(window, renderer.getGlfwScrollCallback());

        if (DEBUG)
            glfwSetErrorCallback(new GLFWErrorCallback() {
                GLFWErrorCallback delegate = GLFWErrorCallback.createPrint(System.err);

                @Override
                public void invoke(int error, long description) {
                    if (error == GLFW_VERSION_UNAVAILABLE)
                        System.err.println("GLFW_VERSION_UNAVAILABLE: This demo requires OpenGL 2.0 or higher.");
                    if (error == GLFW_NOT_INITIALIZED)
                        System.err.println();
                    if (error == GLFW_NO_CURRENT_CONTEXT)
                        System.err.println("GLFW_NO_CURRENT_CONTEXT");
                    if (error == GLFW_INVALID_ENUM)
                        System.err.println("GLFW_INVALID_ENUM");
                    if (error == GLFW_INVALID_VALUE)
                        System.err.println("GLFW_INVALID_VALUE");
                    if (error == GLFW_OUT_OF_MEMORY)
                        System.err.println("GLFW_OUT_OF_MEMORY");
                    if (error == GLFW_API_UNAVAILABLE)
                        System.err.println("GLFW_API_UNAVAILABLE");
                    if (error == GLFW_VERSION_UNAVAILABLE)
                        System.err.println("GLFW_VERSION_UNAVAILABLE");
                    if (error == GLFW_PLATFORM_ERROR)
                        System.err.println("GLFW_PLATFORM_ERROR");
                    if (error == GLFW_FORMAT_UNAVAILABLE)
                        System.err.println("GLFW_FORMAT_UNAVAILABLE");
                    if (error == GLFW_FORMAT_UNAVAILABLE)
                        System.err.println("GLFW_FORMAT_UNAVAILABLE");

                    delegate.invoke(error, description);
                }

                @Override
                public void free() {
                    delegate.free();
                }
            });
        //TODO
		/*other debug modes, some may have a very negative impact on performance
		 * see https://github.com/LWJGL/lwjgl3-wiki/wiki/2.5.-Troubleshooting

		Configuration.DEBUG.set(true);
		Configuration.DEBUG_LOADER.set(true);
		Configuration.DEBUG_MEMORY_ALLOCATOR.set(true);
		Configuration.DEBUG_STACK.set(true);
		Configuration.DEBUG_STREAM.set(true);
		*/

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        //Audio init
        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        audioDevice = alcOpenDevice(defaultDeviceName);

        int[] attributes = {0};
        audioContext = alcCreateContext(audioDevice, attributes);
        alcMakeContextCurrent(audioContext);

        ALCCapabilities alcCapabilities = ALC.createCapabilities(audioDevice);
        ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

        if(!alCapabilities.OpenAL10) {
            assert false: "Audio library not supported.";
        }
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        if (DEBUG)
            GLUtil.setupDebugMessageCallback();

        renderer.init();

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {

            renderer.display();

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

}
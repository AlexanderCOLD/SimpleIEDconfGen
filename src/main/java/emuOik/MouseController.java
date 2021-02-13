package emuOik;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.logging.Logger;


/**
 * @author Александр Холодов
 * @created 12.2020
 * @project SimpleIEDconfGen
 * @description
 */
public class MouseController {

    static Robot robot;
    static {
        try { robot = new Robot(); } catch (AWTException e) { e.printStackTrace(); }

        Logger log = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        log.setUseParentHandlers(false);

        try { GlobalScreen.registerNativeHook(); } catch (NativeHookException e) { e.printStackTrace(); }

        GlobalScreen.addNativeMouseMotionListener(new NativeMouseMotionListener() {
            public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {
                System.out.println("Mouse position: " + nativeMouseEvent.getX() + "   "  + nativeMouseEvent.getY());
            }
            public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) { }
        });
    }


    public static void main(String[] args) throws InterruptedException {
        Robot r = KeyboardController.robot;
        Thread.sleep(10000);
    }



    public static void click(int x, int y) throws InterruptedException {
        robot.mouseMove(x, y);
        Thread.sleep(100);

        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(100);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public static void doubleClick(int x, int y) throws InterruptedException {
        robot.mouseMove(x, y);
        Thread.sleep(100);

        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(100);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(100);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(100);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }


}

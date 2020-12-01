package emuOik;

import javafx.scene.input.KeyCode;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

/**
 * @author Александр Холодов
 * @created 11.2020
 * @project SimpleIEDconfGen
 * @description Скрипт клавиатуры
 */
public class KeyboardController {

    static Robot robot;
    static {
        try { robot = new Robot(); } catch (AWTException e) { e.printStackTrace(); }

        Logger log = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        log.setUseParentHandlers(false);

        try { GlobalScreen.registerNativeHook(); } catch (NativeHookException e) { e.printStackTrace(); }

        /* Завершение скрипта при нажатии Esc */
        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) { }
            public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) { }
            public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) { if(nativeKeyEvent.getKeyCode()==1) { System.out.println("DONE"); System.exit(1); } }
        });
    }


    /* Нажатие клавиши */
    public static void press(int keyCode) throws InterruptedException {
        System.out.println("KEYBOARD PRESS:   " + KeyEvent.getKeyText(keyCode));
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
        Thread.sleep(70);
    }

    /* Нажатие клавиши */
    public static void pressHigh(int keyCode) throws InterruptedException {
        System.out.println("KEYBOARD PRESS:   " + KeyEvent.getKeyText(keyCode));

        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        Thread.sleep(150);
    }



    /* Набор номера */
    public static void pressNumber(int number) throws InterruptedException {
        String num = String.valueOf(number);

        for(int i=0; i<num.length(); i++){
            char c = num.charAt(i);
            switch (c) {
                case '0': press(KeyEvent.VK_0); break;
                case '1': press(KeyEvent.VK_1); break;
                case '2': press(KeyEvent.VK_2); break;
                case '3': press(KeyEvent.VK_3); break;
                case '4': press(KeyEvent.VK_4); break;
                case '5': press(KeyEvent.VK_5); break;
                case '6': press(KeyEvent.VK_6); break;
                case '7': press(KeyEvent.VK_7); break;
                case '8': press(KeyEvent.VK_8); break;
                case '9': press(KeyEvent.VK_9); break;
                default: press(KeyEvent.VK_ESCAPE); break;
            }
        }
    }

    /* Набор номера */
    public static void pressWord(String word) throws InterruptedException {
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
            switch (c) {
                case 'a': press(KeyEvent.VK_A); break;
                case 'b': press(KeyEvent.VK_B); break;
                case 'c': press(KeyEvent.VK_C); break;
                case 'd': press(KeyEvent.VK_D); break;
                case 'e': press(KeyEvent.VK_E); break;
                case 'f': press(KeyEvent.VK_F); break;
                case 'g': press(KeyEvent.VK_G); break;
                case 'h': press(KeyEvent.VK_H); break;
                case 'i': press(KeyEvent.VK_I); break;
                case 'j': press(KeyEvent.VK_J); break;
                case 'k': press(KeyEvent.VK_K); break;
                case 'l': press(KeyEvent.VK_L); break;
                case 'm': press(KeyEvent.VK_M); break;
                case 'n': press(KeyEvent.VK_N); break;
                case 'o': press(KeyEvent.VK_O); break;
                case 'p': press(KeyEvent.VK_P); break;
                case 'q': press(KeyEvent.VK_Q); break;
                case 'r': press(KeyEvent.VK_R); break;
                case 's': press(KeyEvent.VK_S); break;
                case 't': press(KeyEvent.VK_T); break;
                case 'u': press(KeyEvent.VK_U); break;
                case 'v': press(KeyEvent.VK_V); break;
                case 'w': press(KeyEvent.VK_W); break;
                case 'x': press(KeyEvent.VK_X); break;
                case 'y': press(KeyEvent.VK_Y); break;
                case 'z': press(KeyEvent.VK_Z); break;

                case 'A': pressHigh(KeyEvent.VK_A); break;
                case 'B': pressHigh(KeyEvent.VK_B); break;
                case 'C': pressHigh(KeyEvent.VK_C); break;
                case 'D': pressHigh(KeyEvent.VK_D); break;
                case 'E': pressHigh(KeyEvent.VK_E); break;
                case 'F': pressHigh(KeyEvent.VK_F); break;
                case 'G': pressHigh(KeyEvent.VK_G); break;
                case 'H': pressHigh(KeyEvent.VK_H); break;
                case 'I': pressHigh(KeyEvent.VK_I); break;
                case 'J': pressHigh(KeyEvent.VK_J); break;
                case 'K': pressHigh(KeyEvent.VK_K); break;
                case 'L': pressHigh(KeyEvent.VK_L); break;
                case 'M': pressHigh(KeyEvent.VK_M); break;
                case 'N': pressHigh(KeyEvent.VK_N); break;
                case 'O': pressHigh(KeyEvent.VK_O); break;
                case 'P': pressHigh(KeyEvent.VK_P); break;
                case 'Q': pressHigh(KeyEvent.VK_Q); break;
                case 'R': pressHigh(KeyEvent.VK_R); break;
                case 'S': pressHigh(KeyEvent.VK_S); break;
                case 'T': pressHigh(KeyEvent.VK_T); break;
                case 'U': pressHigh(KeyEvent.VK_U); break;
                case 'V': pressHigh(KeyEvent.VK_V); break;
                case 'W': pressHigh(KeyEvent.VK_W); break;
                case 'X': pressHigh(KeyEvent.VK_X); break;
                case 'Y': pressHigh(KeyEvent.VK_Y); break;
                case 'Z': pressHigh(KeyEvent.VK_Z); break;

                default: System.err.println("Bad symbol " + c); press(KeyEvent.VK_ESCAPE); break;
            }
        }
    }



}

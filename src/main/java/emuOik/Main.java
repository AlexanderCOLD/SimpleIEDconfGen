package emuOik;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.event.KeyEvent;
import java.util.logging.Logger;

/**
 * @author Александр Холодов
 * @created 12.2020
 * @project SimpleIEDconfGen
 * @description
 */
public class Main {


    private static final long start = System.currentTimeMillis();

    public static void main(String[] args) throws Exception {

        System.out.println("Keyboard writer timer start");
        Thread.sleep(10000);
        System.out.println("Keyboard writer started");
        writeObjects();


        System.out.println((System.currentTimeMillis() - start)/1000 + "    Keyboard works done, sec");

        System.exit(1);
    }


    private static int number = 0;
    private static int iedSigNum = 0;
    private static int iedNum = 1;

    /** Заполнить номера сигналов  */
    private static void writeObjects() throws Exception {
        while (number<400){

            number++;                                                       // Сплошной номер 104 протокола
            iedSigNum++; if(iedSigNum == 51) { iedSigNum = 1; iedNum++; }   // Номер от 0 до 50

            System.out.println("СИГНАЛ:   " + number);

            for(int i=0; i<5; i++) KeyboardController.press(KeyEvent.VK_TAB);  // Переход в правую таблицу
            KeyboardController.pressNumber(number);       // Номер сигнала 104
            KeyboardController.press(KeyEvent.VK_DOWN);   // Переход ниже
            KeyboardController.pressNumber(1);            // Номер канала (1)
            KeyboardController.press(KeyEvent.VK_DOWN);   // Переход ниже
            KeyboardController.pressNumber(iedNum);       // Номер КП
            KeyboardController.press(KeyEvent.VK_DOWN);   // Переход ниже
            KeyboardController.pressNumber(number);       // Номер объекта = номеру сигнала

            for(int i=0; i<2; i++) KeyboardController.press(KeyEvent.VK_TAB);    // Переход в список слева
            KeyboardController.press(KeyEvent.VK_DOWN);                          // Переход ниже
            for(int i=0; i<2; i++) KeyboardController.press(KeyEvent.VK_RIGHT);  // 2 раза вправо (на случай если вкладка)
        }
    }



}

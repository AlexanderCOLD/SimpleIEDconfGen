package emuOik;

import java.awt.event.KeyEvent;

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
        Thread.sleep(5000);
        System.out.println("Keyboard writer started");

//        writeScale();
//        writeControllers();
//        writeObjects();
        writeDB();

        System.out.println((System.currentTimeMillis() - start)/1000 + "    Keyboard works done, sec");

        System.exit(1);
    }


    private static int number1 = 0;
    private static int iedADD = 0;

    private static void writeControllers() throws Exception{

        while (number1 < 8) {

            number1++; // Сплошной номер 104 протокола

            if(number1 == 2) iedADD = 50;
            if(number1 == 3) iedADD = 100;
            if(number1 == 4) iedADD = 150;
            if(number1 == 5) iedADD = 200;
            if(number1 == 6) iedADD = 250;
            if(number1 == 7) iedADD = 300;
            if(number1 == 8) iedADD = 350;


            System.out.println("СИГНАЛ:   " + number1);

            int[] arr = new int[] { 1, 4, 7, 10, 14, 18, 22, 26, 27, 28, 29, 30, 31, 32, 33 };

            for(int s: arr) setSig(s + iedADD);
        }

    }


    private static void setSig(int s) throws Exception {
        System.out.println("Setting signal:   " + s);
        for(int i=0; i<5; i++) KeyboardController.press(KeyEvent.VK_TAB);  // Переход в правую таблицу
        KeyboardController.pressNumber(s);

        for(int i=0; i<2; i++) KeyboardController.press(KeyEvent.VK_TAB);    // Переход в список слева
        KeyboardController.press(KeyEvent.VK_DOWN);                          // Переход ниже
        for(int i=0; i<3; i++) KeyboardController.press(KeyEvent.VK_RIGHT);  // 3 раза вправо (на случай если вкладка)
    }





    private static int number = 0;
    private static int iedSigNum = 0;
    private static int iedNum = 1;



    private static int sig = 0;


    /** Заполнить номера сигналов  */
    private static void writeScale() throws Exception {

        while (number<400){

            number++;                                                       // Сплошной номер 104 протокола
            sig++;
            iedSigNum++; if(iedSigNum == 51) { iedSigNum = 1; iedNum++; sig = 1; }   // Номер от 0 до 50

            System.out.println("СИГНАЛ:   " + number);

            /* Включение масштабирования */
            if(sig <= 26){
                System.out.println("CLICK");
                MouseController.doubleClick(1829,265);
                Thread.sleep(200);
                MouseController.doubleClick(1318,302);
                for(int i=0; i<2; i++) KeyboardController.press(KeyEvent.VK_TAB);  // Возвращаем в левую таблицу
            }

            KeyboardController.press(KeyEvent.VK_DOWN);                          // Переход ниже
            for(int i=0; i<2; i++) KeyboardController.press(KeyEvent.VK_RIGHT);  // 2 раза вправо (на случай если вкладка)
        }
    }

    /** Заполнить номера сигналов  */
    private static void writeDB() throws Exception {

        while (number<7){

            number++;                                                       // Сплошной номер 104 протокола

            System.out.println("СИГНАЛ:   " + number);

            /* Включение масштабирования */
            System.out.println("CLICK");
            MouseController.doubleClick(1668,347);
            Thread.sleep(200);
            MouseController.doubleClick(1378,364);
            for(int i=0; i<2; i++) KeyboardController.press(KeyEvent.VK_TAB);  // Возвращаем в левую таблицу

            KeyboardController.press(KeyEvent.VK_DOWN);                          // Переход ниже
            for(int i=0; i<2; i++) KeyboardController.press(KeyEvent.VK_RIGHT);  // 2 раза вправо (на случай если вкладка)
        }
    }


    /** Заполнить номера сигналов  */
    private static void writeObjects() throws Exception {
        while (number<400){

            number++;                                                       // Сплошной номер 104 протокола
            iedSigNum++; if(iedSigNum == 51) { iedSigNum = 1; iedNum++; }   // Номер от 0 до 50

            System.out.println("СИГНАЛ:   " + number);

            for(int i=0; i<5; i++) KeyboardController.press(KeyEvent.VK_TAB);  // Переход в правую таблицу
            KeyboardController.pressNumber(number);       // Номер сигнала 104
            KeyboardController.press(KeyEvent.VK_DOWN);   // Переход ниже
            KeyboardController.pressNumber(2);            // Номер канала (1)
            KeyboardController.press(KeyEvent.VK_DOWN);   // Переход ниже
            KeyboardController.pressNumber(iedNum);       // Номер КП
            KeyboardController.press(KeyEvent.VK_DOWN);   // Переход ниже
            KeyboardController.pressNumber(number);       // Номер объекта = номеру сигнала

            /* Включение масштабирования */
            KeyboardController.press(KeyEvent.VK_DOWN);   // Переход ниже
            MouseController.click(1783,297);
//            MouseController.click(1784,298);
            MouseController.click(1272,333);


            for(int i=0; i<2; i++) KeyboardController.press(KeyEvent.VK_TAB);    // Переход в список слева
            KeyboardController.press(KeyEvent.VK_DOWN);                          // Переход ниже
            for(int i=0; i<2; i++) KeyboardController.press(KeyEvent.VK_RIGHT);  // 2 раза вправо (на случай если вкладка)
        }
    }



}

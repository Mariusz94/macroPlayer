package programOne;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.event.InputEvent;

public class Main {

    private static Logger logMain = LogManager.getLogger(Main.class);
    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private static Color pointerColor = new Color(5, 140, 98);

    private static Color SUPER_HARD = new Color(232, 2, 1);
    private static Color VERY_HARD = new Color(254, 59, 0);
    private static Color HARD = new Color(255, 118, 1);
    private static Color NORMAL = new Color(135, 153, 41);

    private static int X_POINT_POINTER = 1401;
    private static int Y_POINT_POINTER = 254;
    private static int X_POINT_SUPER_HARD = 1402;
    private static int Y_POINT_SUPER_HARD = 350;
    private static int X_POINT_VERY_HARD = 1402;
    private static int Y_POINT_VERY_HARD = 517;
    private static int X_POINT_HARD = 1402;
    private static int Y_POINT_HARD = 686;
    private static int X_POINT_NORMAL = 1402;
    private static int Y_POINT_NORMAL = 858;
    private static int X_POINT_GO = 1674;
    private static int Y_POINT_GO = 985;
    private static int X_POINT_END_GAME = 1635;
    private static int Y_POINT_END_GAME = 871;
    private static int X_POINT_OK = 985;
    private static int Y_POINT_OK = 200;
    private static int X_POINT_CLOSE = 1633;
    private static int Y_POINT_CLOSE = 775;

    private static int TIME_CHOICE_LVL = 200; //MS
    private static int TIME_AFTER_CLICK =2000; //MS
    private static int TIME_WAIT_AFTER_DETECTION = 200; //MS
    private static int TIME_WAIT_AFTER_DETECTION_END_GAME = 3000; //MS

    private static int TIME_STOP_GO= 200; //MS
    private static int TIME_STOP_END_GAME= 200; //MS
    private static int TIME_STOP_OK= 200; //MS
    private static int TIME_STOP_CLOSE= 200; //MS
    private static int TIME_AFTER_STOP_LOOK_FOR_CHOICE_LVL = 200; //MS

    private static int MARGIN_PIXEL_R= 10;
    private static int MARGIN_PIXEL_G= 10;
    private static int MARGIN_PIXEL_B= 10;

    private static EnumLvl lvl = EnumLvl.HARD;


    public static void main(String[] args) throws AWTException {
        //findStartPosition();

        while(true) {
            choiceLvl(lvl);
            choicePicture("Go!",new Color(238,146,21),X_POINT_GO,Y_POINT_GO,TIME_WAIT_AFTER_DETECTION,TIME_STOP_GO);
            choicePicture("End Game",new Color(31,70,62),X_POINT_END_GAME,Y_POINT_END_GAME,TIME_WAIT_AFTER_DETECTION_END_GAME,TIME_STOP_END_GAME);
            choicePicture("Ok",new Color(238,146,21),X_POINT_OK,Y_POINT_OK,TIME_WAIT_AFTER_DETECTION,TIME_STOP_OK);
            choicePicture("Close",new Color(101,149,107),X_POINT_CLOSE,Y_POINT_CLOSE,TIME_WAIT_AFTER_DETECTION,TIME_STOP_CLOSE);
        }
    }

    public static void findStartPosition() {
        Color pointer;
        while (true) {
            pointer = robot.getPixelColor(X_POINT_POINTER, Y_POINT_POINTER);
           printInfoColor("strzalka",pointer,pointerColor);
            if (pointer.equals(pointerColor)) {
                logMain.info("---------------------------");
                logMain.info("Jestem na dobrej pozycji");
                logMain.info("---------------------------");

                break;
            }
        }
    }

    public static void choiceLvl(EnumLvl enumLvl) {
        switch (enumLvl){
            case SUPER_HARD:
                choicePicture("super hard",SUPER_HARD,X_POINT_SUPER_HARD,Y_POINT_SUPER_HARD,TIME_CHOICE_LVL,TIME_AFTER_STOP_LOOK_FOR_CHOICE_LVL);
                break;
            case VERY_HARD:
                choicePicture("very hard",VERY_HARD,X_POINT_VERY_HARD,Y_POINT_VERY_HARD,TIME_CHOICE_LVL, TIME_AFTER_STOP_LOOK_FOR_CHOICE_LVL);
                break;
            case HARD:
                choicePicture("hard",HARD,X_POINT_HARD,Y_POINT_HARD,TIME_CHOICE_LVL, TIME_AFTER_STOP_LOOK_FOR_CHOICE_LVL);
                break;
            case NORMAL:
                choicePicture("normal",NORMAL,X_POINT_NORMAL,Y_POINT_NORMAL,TIME_CHOICE_LVL,TIME_AFTER_STOP_LOOK_FOR_CHOICE_LVL);
                break;
        }
    }

    public static void choicePicture(String name, Color colorWanted, int xPoint, int yPoint, int xTimeWaitMS, long stopTime) {
        Color colorPicker;
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < stopTime) {

            colorPicker = robot.getPixelColor(xPoint, yPoint);
            robot.delay(100);
            printInfoColor(name, colorPicker, colorWanted);
            colorComparison(colorPicker,colorWanted);
            if (colorComparison(colorPicker,colorWanted)) {
                robot.delay(xTimeWaitMS);
                robot.mouseMove(xPoint, yPoint);
                logMain.info("Znalazłem kolor " + name);
                clickMouse("left",robot,1);
                robot.delay(TIME_AFTER_CLICK);
                break;
            }
        }
    }

    public static boolean colorComparison (Color colorPicker, Color colorWanted){
        logMain.info("Różnica kolorów: r=" + Math.abs(colorPicker.getRed()-colorWanted.getRed()) +
                " g=" + Math.abs(colorPicker.getGreen()-colorWanted.getGreen()) +
                " b=" + Math.abs(colorPicker.getBlue()-colorWanted.getBlue()));

        if (Math.abs(colorPicker.getRed()-colorWanted.getRed()) <= MARGIN_PIXEL_R &&
                Math.abs(colorPicker.getGreen()-colorWanted.getGreen()) <= MARGIN_PIXEL_G &&
                Math.abs(colorPicker.getBlue()-colorWanted.getBlue()) <= MARGIN_PIXEL_B ){

            return true;
        }

        return false;
    }

    public static void printInfoColor(String name, Color colorPicker, Color colorWanted) {
        logMain.info("Sprawdzam kolor : " + name + " r=" + colorPicker.getRed()
                + " g=" + colorPicker.getGreen() + " b=" + colorPicker.getBlue() +
                " chcę : r=" + colorWanted.getRed() + " g=" + colorWanted.getGreen() +
                " b=" + colorWanted.getBlue());
    }

    public static void clickMouse(String button, Robot r, int number) {
        int mouse;
        switch (button) {
            case "left":
                mouse = InputEvent.BUTTON1_MASK;
                break;
            case "right":
                mouse = InputEvent.BUTTON3_MASK;
                break;
            case "middle":
                mouse = InputEvent.BUTTON2_MASK;
                break;
            default:
                mouse = InputEvent.BUTTON1_MASK;
                break;
        }
        for (int i = 0; i < number; i++) {
            r.mousePress(mouse);
            r.delay(200);
            r.mouseRelease(mouse);
            r.delay(200);
            logMain.info("click");
        }
    }
}
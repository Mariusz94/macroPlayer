package programTwo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import simulatorPlay.Player;

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

    private static int TIME_CHOICE_LVL = 200; //MS
    private static int TIME_AFTER_CLICK = 2000; //MS
    private static int TIME_WAIT_AFTER_DETECTION = 200; //MS
    private static int TIME_WAIT_AFTER_DETECTION_END_GAME = 3000; //MS

    private static int TIME_STOP_GO = 200; //MS
    private static int TIME_STOP_END_GAME = 200; //MS
    private static int TIME_STOP_BATTLE_AGAIN = 200; //MS
    private static int TIME_STOP_FINISH = 200; //MS
    private static int TIME_AFTER_STOP_LOOK_FOR_CHOICE_LVL = 200; //MS

    private static int MARGIN_PIXEL_R = 22;
    private static int MARGIN_PIXEL_G = 10;
    private static int MARGIN_PIXEL_B = 10;

    private static EnumLvl lvl = EnumLvl.ULTRA_HARD;


    public static void main(String[] args) throws AWTException {

        while (true) {
            choiceLvl(lvl);
            choicePicture(EnumButton.GO, TIME_WAIT_AFTER_DETECTION, TIME_STOP_GO);
            choicePicture(EnumButton.END_GAME, TIME_WAIT_AFTER_DETECTION_END_GAME, TIME_STOP_END_GAME);
            choicePicture(EnumButton.BATTLE_AGAIN,TIME_WAIT_AFTER_DETECTION,TIME_STOP_BATTLE_AGAIN);
            //choicePicture(EnumButton.FINISH, TIME_WAIT_AFTER_DETECTION, TIME_STOP_FINISH);
        }
    }

    private static void choiceLvl(EnumLvl enumLvl) {
        switch (enumLvl) {
            case ULTRA_HARD:
                choicePicture(EnumLvl.ULTRA_HARD, TIME_CHOICE_LVL, TIME_AFTER_STOP_LOOK_FOR_CHOICE_LVL);
                break;
            case SUPER_HARD:
                choicePicture(EnumLvl.SUPER_HARD, TIME_CHOICE_LVL, TIME_AFTER_STOP_LOOK_FOR_CHOICE_LVL);
                break;
            case VERY_HARD:
                choicePicture(EnumLvl.VERY_HARD, TIME_CHOICE_LVL, TIME_AFTER_STOP_LOOK_FOR_CHOICE_LVL);
                break;
            case HARD:
                choicePicture(EnumLvl.HARD, TIME_CHOICE_LVL, TIME_AFTER_STOP_LOOK_FOR_CHOICE_LVL);
                break;
            case NORMAL:
                choicePicture(EnumLvl.NORMAL, TIME_CHOICE_LVL, TIME_AFTER_STOP_LOOK_FOR_CHOICE_LVL);
                break;
        }
    }

    private static void choicePicture(ChoicePicture choicePicture, int xTimeWaitMS, long stopTime) {
        Color colorPicker;
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < stopTime) {
            Color colorWanted = new Color(choicePicture.getR_COLOR(), choicePicture.getG_COLOR(), choicePicture.getB_COLOR());
            colorPicker = robot.getPixelColor(choicePicture.getX_POINT_POINTER(), choicePicture.getY_POINT_POINTER());

            printInfoColor(choicePicture.getNAME(), colorPicker, colorWanted);
            if (colorComparison(colorPicker, colorWanted)) {
                robot.delay(xTimeWaitMS);
                double x = MouseInfo.getPointerInfo().getLocation().getX();
                double y = MouseInfo.getPointerInfo().getLocation().getY();

                robot.mouseMove(choicePicture.getX_POINT_POINTER(), choicePicture.getY_POINT_POINTER());
                logMain.info("Znalazłem kolor " + choicePicture.getNAME());
                clickMouse(choicePicture, "left", robot, 1);
                goToCords((int) x, (int) y, robot);
                robot.delay(TIME_AFTER_CLICK);
                if (choicePicture.getNAME().equals("go") || choicePicture.getNAME().equals("battle again")){
                    try {
                        Player.main();
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }

    private static boolean colorComparison(Color colorPicker, Color colorWanted) {
        logMain.info("Różnica kolorów: r=" + Math.abs(colorPicker.getRed() - colorWanted.getRed()) +
                " g=" + Math.abs(colorPicker.getGreen() - colorWanted.getGreen()) +
                " b=" + Math.abs(colorPicker.getBlue() - colorWanted.getBlue()));

        if (Math.abs(colorPicker.getRed() - colorWanted.getRed()) <= MARGIN_PIXEL_R &&
                Math.abs(colorPicker.getGreen() - colorWanted.getGreen()) <= MARGIN_PIXEL_G &&
                Math.abs(colorPicker.getBlue() - colorWanted.getBlue()) <= MARGIN_PIXEL_B) {

            return true;
        }

        return false;
    }

    private static void printInfoColor(String name, Color colorPicker, Color colorWanted) {
        logMain.info("Sprawdzam kolor : " + name + " r=" + colorPicker.getRed()
                + " g=" + colorPicker.getGreen() + " b=" + colorPicker.getBlue() +
                " chcę : r=" + colorWanted.getRed() + " g=" + colorWanted.getGreen() +
                " b=" + colorWanted.getBlue());
    }

    private static void clickMouse(ChoicePicture choicePicture, String button, Robot r, int number) {
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
                mouse = InputEvent.BUTTON2_DOWN_MASK;
                break;
        }
        for (int i = 0; i < number; i++) {
            goToCords(choicePicture, r);

            r.mousePress(mouse);
            //r.delay(200);
            r.mouseRelease(mouse);
            //r.delay(200);
            r.waitForIdle();
            logMain.info("click");
        }
    }

    private static void goToCords(ChoicePicture choicePicture, Robot r) {
        double actualMousePointX = 1000000;
        double actualMousePointY = 1000000;
        while (actualMousePointX != choicePicture.getX_POINT_POINTER() && actualMousePointY != choicePicture.getY_POINT_POINTER()) {
            r.mouseMove(choicePicture.getX_POINT_POINTER(), choicePicture.getY_POINT_POINTER());
            r.waitForIdle();
            actualMousePointX = MouseInfo.getPointerInfo().getLocation().getX();
            actualMousePointY = MouseInfo.getPointerInfo().getLocation().getY();
        }
    }

    private static void goToCords(int x, int y, Robot r) {
        double actualMousePointX = 1000000;
        double actualMousePointY = 1000000;
        while (actualMousePointX != x && actualMousePointY != y) {
            r.mouseMove(x, y);
            r.waitForIdle();
            actualMousePointX = MouseInfo.getPointerInfo().getLocation().getX();
            actualMousePointY = MouseInfo.getPointerInfo().getLocation().getY();
        }
    }
}
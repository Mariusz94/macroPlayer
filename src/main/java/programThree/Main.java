package programThree;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import simulatorPlay.Player;

import java.awt.*;
import java.awt.event.InputEvent;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

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
    private static int MARGIN_PIXEL_G = 20;
    private static int MARGIN_PIXEL_B = 20;

    /*private static int MARGIN_PIXEL_R = 22;
    private static int MARGIN_PIXEL_G = 10;
    private static int MARGIN_PIXEL_B = 10;*/

    private static EnumLvl lvl = EnumLvl.ULTRA_HARD;
    private static boolean initPlayer = true;
    private static int countBattleWin = 1;
    private static long startTime = System.currentTimeMillis();
    private static int countBattle = 1;
    private static boolean countFlag = true;


    public static void main(String[] args) throws AWTException {

        while (true) {
            choiceLvl(lvl);
            choicePicture(EnumButton.GO, TIME_WAIT_AFTER_DETECTION, TIME_STOP_GO);
            choicePicture(EnumButton.END_GAME, TIME_WAIT_AFTER_DETECTION_END_GAME, TIME_STOP_END_GAME);
            choicePicture(EnumButton.BATTLE_AGAIN, TIME_WAIT_AFTER_DETECTION, TIME_STOP_BATTLE_AGAIN);
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
            //////////////////////
            if (choicePicture.getNAME().equals("end game")){
                Color colorWanted2 = new Color(EnumButton.END_GAME2.getR_COLOR(), EnumButton.END_GAME2.getG_COLOR(), EnumButton.END_GAME2.getB_COLOR());
                if (!colorComparison(robot.getPixelColor(EnumButton.END_GAME2.getX_POINT_POINTER(), EnumButton.END_GAME2.getY_POINT_POINTER()), colorWanted)){
                    break;
                }
            }
            /////////////////////
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

                doStatistic(choicePicture, robot);

               /* if (initPlayer && (choicePicture.getNAME().equals("go") || choicePicture.getNAME().equals("battle again"))){
                    try {
                        PlayerThree.main();
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }
                }*/
                break;
            }
        }
    }

    private static void doStatistic(ChoicePicture choicePicture, Robot r) {

        Timestamp timestamp = new Timestamp(startTime);
        long stopTime = System.currentTimeMillis();
        long difference = (stopTime - startTime) / 1000;

        long differenceSec = difference;
        long differenceMin = differenceSec / 60;
        long differenceHours = differenceMin / 60;


        int hours = (int) differenceHours;
        int min = (int) (differenceMin - (hours * 60));
        int sec = (int) (differenceSec - (min * 60 + hours * 60 * 60));
        if (min == 60) min = 0;
        if (sec == 60) min = 0;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy MM dd  HH:mm:ss");
        System.out.println("-----------------------------------------");
        System.out.println("Czas rozpoczęcia gry : " + timestamp.toLocalDateTime().format(dateTimeFormatter));
        System.out.println("Stoczonych walk : " + countBattle);
        System.out.println("Wygranych walk  : " + countBattleWin);
        System.out.println("Wygranych       : " + (int) (countBattleWin * 100 / countBattle) + " %");
        System.out.println("Jedna runda trwa: " + (int) (difference / countBattle) + " s");
        System.out.print("Uruchomiona od  : ");
        System.out.print(hours + ":");
        System.out.print(min>=10 ? min + ":" : "0" + min + ":");
        System.out.println(sec>=10 ? sec : "0"+sec);
        System.out.println("-----------------------------------------");

        if (choicePicture.getNAME().equals("battle again")) {
            countBattleWin++;
            countBattle++;
        }
        if (choicePicture.getNAME().equals("go")) countBattle++;
        r.delay(200);
        if (countFlag && choicePicture.getNAME().equals("go")) {
            countBattle--;
            countFlag = false;
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
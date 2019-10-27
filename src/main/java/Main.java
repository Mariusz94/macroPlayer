import java.awt.*;
import java.awt.event.InputEvent;

public class Main {

    public static void main(String[] args) throws AWTException {
        findStartPosition();
        int counter = 0;
        int stopCounting = Integer.MAX_VALUE;
        while(counter < stopCounting) {
            choiceLvl(EnumLvl.SUPER_HARD);
            choicePicture("Go!",new Color(238,146,21),1674,985,200,200);
            choicePicture("End Game",new Color(40,70,62),1635,871,3000,2000);
            choicePicture("Ok",new Color(238,146,21),1674,985,200,200);
            choicePicture("Close",new Color(101,149,107),1633,775,200,200);
            counter++;
        }
    }

    public static void findStartPosition() throws AWTException {
        Robot robot = new Robot();
        Color strzalka;
        Color colorStrzalki = new Color(5, 140, 98);
        while (true) {
            //kolor strzałki
            strzalka = robot.getPixelColor(1401, 254);

            if (strzalka.equals(colorStrzalki)) {
                System.out.println("---------------------------");
                System.out.println("Jestem na dobrej pozycji");
                System.out.println("---------------------------");
                break;
            }
        }
    }

    public static void choiceLvl(EnumLvl enumLvl) throws AWTException {
        switch (enumLvl){
            case SUPER_HARD:
                choicePicture("super hard",new Color(232, 2, 1),1402,350,200,2000);
                break;
            case VERY_HARD:
                choicePicture("very hard",new Color(254, 59, 0),1402,517,200, 2000);
                break;
            case HARD:
                choicePicture("hard",new Color(255, 118, 1),1402,686,200, 2000);
                break;
            case NORMAL:
                choicePicture("normal",new Color(135, 153, 41),1402,858,200,2000);
                break;
        }
    }

    public static void choicePicture(String name, Color colorWanted, int xPoint, int yPoint, int xTimeWaitMS, long stopTime) throws AWTException {
        Robot robot = new Robot();
        Color colorPicker;
        long startTime = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - startTime > stopTime){
                System.out.println("Przerwalem poszukiwania " + name);
                break;
            }

            colorPicker = robot.getPixelColor(xPoint, yPoint);
            robot.delay(200);
            printInfoColor(name, colorPicker, colorWanted);
            robot.delay(200);
            if (colorComparison(colorPicker,colorWanted)) {
                robot.delay(xTimeWaitMS);
                robot.mouseMove(xPoint, yPoint);
                System.out.println("Znalazłem kolor " + name);
                clickMouse("left",robot,1);
                robot.delay(1000);
                break;
            }
        }
    }

    public static boolean colorComparison (Color colorPicker, Color colorWanted){
        if (Math.abs(colorPicker.getRed()-colorWanted.getRed()) < 10 &&
                Math.abs(colorPicker.getGreen()-colorWanted.getGreen()) < 5 &&
                Math.abs(colorPicker.getBlue()-colorWanted.getBlue()) < 5 ){
            return true;
        }

        return false;
    }

    public static void printInfoColor(String name, Color colorPicker, Color colorWanted) {
        System.out.println("Sprawdzam kolor : " + name + " r=" + colorPicker.getRed()
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
        }
    }
}
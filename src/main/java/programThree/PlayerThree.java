package programThree;

import java.awt.*;
import java.awt.event.InputEvent;

public class PlayerThree {
    private static int MARGIN_PIXEL_R = 2;
    private static int MARGIN_PIXEL_G = 2;
    private static int MARGIN_PIXEL_B = 2;
    private static Point leftUpperCorner = new Point(1574, 773);
    private static Point rightUpperCorner = new Point(1703, 773);
    private static Point leftBottomCorner = new Point(1574, 838);
    private static Point rightBottomCorner = new Point(1703, 838);

    private static Point auto = new Point(1824, 224);
    private static Color autoColor = new Color(189, 239, 106);
    private static Point auto2 = new Point(1784, 232);
    private static Color autoColor2 = new Color(205, 237, 126);

    private static Point sync = new Point(1641, 827);
    private static Color syncColor = new Color(255, 192, 137);

    private static Color colorGame = new Color(129, 160, 240);

    private static Point cordsToCheck = new Point(1638, 725);

    public static void main() throws AWTException {
        Robot r = new Robot();
        int count = 1;
        int countTwo = 0;
        while (count == 1) {
            Color downloadedColor = r.getPixelColor((int) cordsToCheck.getX(), (int) cordsToCheck.getY());
            if (colorComparison(r.getPixelColor((int) auto.getX(), (int) auto.getY()), autoColor)) {
                spamClickPoint(auto, r, 100);
            }
            r.delay(100);
            while (colorComparison(downloadedColor, colorGame) && countTwo == 0) {
                spamClickPoint(rightUpperCorner, r, 7000);
                spamClickPoint(rightBottomCorner, r, 10000);
                if (!(//colorComparison(r.getPixelColor((int) auto.getX(), (int) auto.getY()), autoColor)) ||
                        colorComparison(r.getPixelColor((int) auto2.getX(), (int) auto2.getY()), autoColor2))) {
                    spamClickPoint(auto, r, 100);
                }
                spamClickPoint(leftBottomCorner, r, 38000);
                //spamClickPoint(sync, r, 6000);
                //spamClickPoint(leftBottomCorner, r, 15000);

                countTwo++;
            }
            if (countTwo == 1) {
                count--;
            }
        }
    }

    private static void spamClickPoint(Point point, Robot r, long stopTime) {
        Color downloadedColor = r.getPixelColor((int) cordsToCheck.getX(), (int) cordsToCheck.getY());
        /*while (colorComparison(downloadedColor, colorGame)) {
            r.delay(1000);
        }*/
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < (stopTime)) {
            goToCords(point, r);
            r.mousePress(InputEvent.BUTTON1_MASK);
            r.mouseRelease(InputEvent.BUTTON1_MASK);
            r.delay(200);
            System.out.println(" Left Time : " + Math.abs(System.currentTimeMillis() - startTime - stopTime));
        }
    }

    private static boolean colorComparison(Color colorPicker, Color colorWanted) {

        if (Math.abs(colorPicker.getRed() - colorWanted.getRed()) <= MARGIN_PIXEL_R &&
                Math.abs(colorPicker.getGreen() - colorWanted.getGreen()) <= MARGIN_PIXEL_G &&
                Math.abs(colorPicker.getBlue() - colorWanted.getBlue()) <= MARGIN_PIXEL_B) {

            return true;
        }
        return false;
    }

    private static void goToCords(Point point, Robot r) {
        double actualMousePointX = 1000000;
        double actualMousePointY = 1000000;
        while (actualMousePointX != point.getX() && actualMousePointY != point.getY()) {
            r.mouseMove((int) point.getX(), (int) point.getY());
            r.waitForIdle();
            actualMousePointX = MouseInfo.getPointerInfo().getLocation().getX();
            actualMousePointY = MouseInfo.getPointerInfo().getLocation().getY();
        }
    }
}

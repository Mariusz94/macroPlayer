import java.awt.*;

public class CordMouseCheck {

    public static void main(String[] args) {
        double x = 0; double y = 0;
        while(true) {
            Point mouse = MouseInfo.getPointerInfo().getLocation();
            if (mouse.getX() != x || mouse.getY() != y) {
                x = mouse.getX();
                y = mouse.getY();
                printCords(x, y);
            }
        }
    }

    public static void printCords(double x, double y){
        System.out.println("x : " + x + "   y : " + y);
    }
}

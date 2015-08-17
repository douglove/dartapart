package net.magicstudios.jdart.data;


import net.magicstudios.jdart.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Nick Cramer (d3k199)
 * @version 1.0
 */
public class ZoneConverter {

    private int [] slices = new int[] {6, 13, 4, 18, 1, 20, 5, 12, 9, 14, 11, 8, 16, 7, 19, 3, 17, 2, 15, 10, 6};


    public ZoneConverter() {
    }

    public Zone zone(double x, double y) {

        Zone zone = new Zone();

        double h = Math.sqrt((x*x) + (y*y));
        double angle;

        if(x == 0){
            if(y < 0){
                angle = 270;
            }
            else{
                angle = 90;
            }
        }
        else{
            angle = Math.atan(y / x);

            angle = Math.toDegrees(angle);

            if (x > 0 && y > 0) { // quad 1

            } else if (x < 0 && y > 0) { // quad 2
                angle = 180.0 + angle;
            } else if (x < 0 && y < 0) { // quad 3
                angle = 180.0 + angle;
            } else if (x > 0 && y < 0) { // quad 4
                angle = 360.0 + angle;
            }
        }


        int sliceIndex = (int) ((angle + 9.0) / 18.0);
        zone.setSlice(slices[sliceIndex]);

        if (h < DartBoardDimensions.MM_BULLSEYE_INSIDE) {
            // double bullseye
            zone.setRing(Zone.RING_DOUBLE_BULLSEYE);
            zone.setSlice(0);
        } else if (h < DartBoardDimensions.MM_BULLSEYE_OUTSIDE) {
            // single bullseye
            zone.setRing(Zone.RING_SINGLE_BULLSEYE);
            zone.setSlice(0);
        } else if (h < DartBoardDimensions.MM_TRIPLE_INSIDE) {
            // single
            zone.setRing(Zone.RING_SINGLE);
        } else if (h < DartBoardDimensions.MM_TRIPLE_OUTSIDE) {
            // triple
            zone.setRing(Zone.RING_TRIPLE);
        } else if (h < DartBoardDimensions.MM_DOUBLE_INSIDE) {
            // single
            zone.setRing(Zone.RING_SINGLE);
        } else if (h < DartBoardDimensions.MM_DOUBLE_OUTSIDE) {
            // double
            zone.setRing(Zone.RING_DOUBLE);
        } else {
            zone.setRing(Zone.RING_OUTSIDE);
        }
//        System.out.print("h: " + h + " x: " + x + " y: " + y + " slice: " + zone.getSlice() + " ring: " + zone.getRing());

        zone.setX(x);
        zone.setY(y);

        return zone;
    }
}

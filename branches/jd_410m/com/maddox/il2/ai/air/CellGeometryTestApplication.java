package com.maddox.il2.ai.air;

import com.maddox.JGP.Point3d;
import java.io.PrintStream;
import java.util.ArrayList;

public class CellGeometryTestApplication
{
  private static ArrayList points = new ArrayList();
  public static CellAirField fieldC;
  public static final int AIR_FIELDC_X_SIZE = 21;
  public static final int AIR_FIELDC_Y_SIZE = 21;
  public static final double AIR_FIELDC_CELL_SIZE = 5.0D;
  public static CellObject[][] cellsC = new CellObject[21][21];
  public static CellAirField field;
  public static final int AIR_FIELD_X_SIZE = 20;
  public static final int AIR_FIELD_Y_SIZE = 17;
  public static final double AIR_FIELD_CELL_SIZE = 0.5D;
  public static CellObject[][] cells = new CellObject[20][17];

  public static CellAirPlane[] plane = new CellAirPlane[5];
  public static final int AIR_PLANE_X_SIZE = 5;
  public static final int AIR_PLANE_Y_SIZE = 4;
  public static final double AIR_PLANE_CELL_SIZE = 0.5D;
  public static CellObject[][] pcells = new CellObject[5][4];
  public static boolean[][] mask = { { false, false, true, false, false }, { true, true, true, true, true }, { false, false, true, false, false }, { false, true, true, true, false } };

  public static CellAirPlane[] plane2 = new CellAirPlane[5];
  public static final int AIR_PLANE_X_SIZE2 = 7;
  public static final int AIR_PLANE_Y_SIZE2 = 5;
  public static final double AIR_PLANE_CELL_SIZE2 = 0.5D;
  public static CellObject[][] pcells2 = new CellObject[7][5];
  public static boolean[][] mask2 = { { false, false, true, false, true, false, false }, { true, true, true, true, true, true, true }, { false, false, true, false, true, false, false }, { false, false, true, false, true, false, false }, { false, true, true, true, true, true, false } };

  public static void createComplexSiluetAirField()
  {
    for (int i = 0; i < 21; i++) {
      for (int j = 0; j < 21; j++) {
        cellsC[i][j] = null;
      }
    }
    fieldC = new CellAirField(cellsC);
    points.clear();
    points.add(new Point3d(30.0D, 0.0D, 0.0D));
    points.add(new Point3d(110.0D, 30.0D, 0.0D));
    points.add(new Point3d(100.0D, 90.0D, 0.0D));
    points.add(new Point3d(50.0D, 110.0D, 0.0D));
    points.add(new Point3d(0.0D, 50.0D, 0.0D));
    fieldC.fieldInitWithComplexPoly(points, 5.0D);
    fieldC.debugPrint();
  }

  public static void createCellAirField()
  {
    for (int i = 0; i < 20; i++) {
      for (int j = 0; j < 17; j++) {
        cells[i][j] = null;
      }
    }
    field = new CellAirField(cells);
  }

  public static void createCellAirPlane()
  {
    for (int i = 0; i < 5; i++) {
      plane[i] = new CellAirPlane(pcells);
      for (int j = 0; j < 5; j++) {
        for (int k = 0; k < 4; k++) {
          if (mask[k][j] != 0) plane[i].getCells()[j][k] = plane[i]; else {
            plane[i].getCells()[j][k] = null;
          }
        }
      }
    }
    plane[0].debugPrint();
  }

  public static void createCellAirPlane2()
  {
    for (int i = 0; i < 5; i++) {
      plane2[i] = new CellAirPlane(pcells2);
      for (int j = 0; j < 7; j++) {
        for (int k = 0; k < 5; k++) {
          if (mask2[k][j] != 0) plane2[i].getCells()[j][k] = plane2[i]; else {
            plane2[i].getCells()[j][k] = null;
          }
        }
      }
    }
    plane2[0].debugPrint();
  }

  public static void main(String[] paramArrayOfString)
  {
    System.out.println();
    System.out.println("CellGeometryTestApplication v1.0 by KiRshe");
    System.out.println("used for testing cell geometry operations and plane-airfield relations");
    System.out.println();

    System.out.println("Blank test AirField:");
    createCellAirField();
    System.out.println("Test AirPlane:");
    createCellAirPlane();
    createCellAirPlane2();
    System.out.println("Placing Test AirPlane in the middle of Test AirField:");
    field.placeAirPlane(plane[0], 5, 1);
    field.debugPrint();
    System.out.println("Placing Next Test AirPlane in the top of Test AirField:");
    field.placeAirPlane(plane[0], 0, 0);
    field.debugPrint();
    System.out.println("Replacing Next Test AirPlane from the middle of Test AirField:");
    field.replaceAirPlane(plane[0], 5, 1);
    field.debugPrint();
    System.out.println("Trying to place 10 planes on blank Test AirField:");
    field.clearCells();
    for (int i = 0; i < 5; i++)
      if (field.findPlaceForAirPlane(plane2[i])) {
        System.out.println("found, x=" + field.resX() + ", y=" + field.resY());
        field.placeAirPlaneId(plane2[i], field.resX(), field.resY(), "" + i); } else {
        System.out.println("cannot find place for plane2_" + i);
      }
    for (i = 0; i < 5; i++)
      if (field.findPlaceForAirPlane(plane[i])) {
        System.out.println("found, x=" + field.resX() + ", y=" + field.resY());
        field.placeAirPlaneId(plane[i], field.resX(), field.resY(), "" + (i + 5)); } else {
        System.out.println("cannot find place for plane1_" + (i + 5));
      }
    field.debugPrint();

    System.out.println("Blank Complex-Siluet AirField:");
    createComplexSiluetAirField();

    for (i = 0; i < 5; i++) {
      if (fieldC.findPlaceForAirPlane(plane2[i])) {
        System.out.println("found, x=" + fieldC.resX() + ", y=" + fieldC.resY());
        fieldC.placeAirPlaneId(plane2[i], fieldC.resX(), fieldC.resY(), "" + i); } else {
        System.out.println("cannot find place for plane2_" + i);
      }

    }

    for (i = 0; i < 5; i++)
      if (fieldC.findPlaceForAirPlane(plane[i])) {
        System.out.println("found, x=" + fieldC.resX() + ", y=" + fieldC.resY());
        fieldC.placeAirPlaneId(plane[i], fieldC.resX(), fieldC.resY(), "" + (i + 5)); } else {
        System.out.println("cannot find place for plane1_" + (i + 5));
      }
    fieldC.debugPrint();
  }
}
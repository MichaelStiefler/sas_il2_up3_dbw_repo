// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CellGeometryTestApplication.java

package com.maddox.il2.ai.air;

import com.maddox.JGP.Point3d;
import java.io.PrintStream;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.ai.air:
//            CellAirField, CellAirPlane, CellObject

public class CellGeometryTestApplication
{

    public CellGeometryTestApplication()
    {
    }

    public static void createComplexSiluetAirField()
    {
        for(int i = 0; i < 21; i++)
        {
            for(int j = 0; j < 21; j++)
                cellsC[i][j] = null;

        }

        fieldC = new CellAirField(cellsC);
        points.clear();
        points.add(new Point3d(30D, 0.0D, 0.0D));
        points.add(new Point3d(110D, 30D, 0.0D));
        points.add(new Point3d(100D, 90D, 0.0D));
        points.add(new Point3d(50D, 110D, 0.0D));
        points.add(new Point3d(0.0D, 50D, 0.0D));
        fieldC.fieldInitWithComplexPoly(points, 5D);
        fieldC.debugPrint();
    }

    public static void createCellAirField()
    {
        for(int i = 0; i < 20; i++)
        {
            for(int j = 0; j < 17; j++)
                cells[i][j] = null;

        }

        field = new CellAirField(cells);
    }

    public static void createCellAirPlane()
    {
        for(int i = 0; i < 5; i++)
        {
            plane[i] = new CellAirPlane(pcells);
            for(int j = 0; j < 5; j++)
            {
                for(int k = 0; k < 4; k++)
                    if(mask[k][j])
                        plane[i].getCells()[j][k] = plane[i];
                    else
                        plane[i].getCells()[j][k] = null;

            }

        }

        plane[0].debugPrint();
    }

    public static void createCellAirPlane2()
    {
        for(int i = 0; i < 5; i++)
        {
            plane2[i] = new CellAirPlane(pcells2);
            for(int j = 0; j < 7; j++)
            {
                for(int k = 0; k < 5; k++)
                    if(mask2[k][j])
                        plane2[i].getCells()[j][k] = plane2[i];
                    else
                        plane2[i].getCells()[j][k] = null;

            }

        }

        plane2[0].debugPrint();
    }

    public static void main(java.lang.String args[])
    {
        java.lang.System.out.println();
        java.lang.System.out.println("CellGeometryTestApplication v1.0 by KiRshe");
        java.lang.System.out.println("used for testing cell geometry operations and plane-airfield relations");
        java.lang.System.out.println();
        java.lang.System.out.println("Blank test AirField:");
        com.maddox.il2.ai.air.CellGeometryTestApplication.createCellAirField();
        java.lang.System.out.println("Test AirPlane:");
        com.maddox.il2.ai.air.CellGeometryTestApplication.createCellAirPlane();
        com.maddox.il2.ai.air.CellGeometryTestApplication.createCellAirPlane2();
        java.lang.System.out.println("Placing Test AirPlane in the middle of Test AirField:");
        field.placeAirPlane(plane[0], 5, 1);
        field.debugPrint();
        java.lang.System.out.println("Placing Next Test AirPlane in the top of Test AirField:");
        field.placeAirPlane(plane[0], 0, 0);
        field.debugPrint();
        java.lang.System.out.println("Replacing Next Test AirPlane from the middle of Test AirField:");
        field.replaceAirPlane(plane[0], 5, 1);
        field.debugPrint();
        java.lang.System.out.println("Trying to place 10 planes on blank Test AirField:");
        field.clearCells();
        for(int i = 0; i < 5; i++)
            if(field.findPlaceForAirPlane(plane2[i]))
            {
                java.lang.System.out.println("found, x=" + field.resX() + ", y=" + field.resY());
                field.placeAirPlaneId(plane2[i], field.resX(), field.resY(), "" + i);
            } else
            {
                java.lang.System.out.println("cannot find place for plane2_" + i);
            }

        for(int j = 0; j < 5; j++)
            if(field.findPlaceForAirPlane(plane[j]))
            {
                java.lang.System.out.println("found, x=" + field.resX() + ", y=" + field.resY());
                field.placeAirPlaneId(plane[j], field.resX(), field.resY(), "" + (j + 5));
            } else
            {
                java.lang.System.out.println("cannot find place for plane1_" + (j + 5));
            }

        field.debugPrint();
        java.lang.System.out.println("Blank Complex-Siluet AirField:");
        com.maddox.il2.ai.air.CellGeometryTestApplication.createComplexSiluetAirField();
        for(int k = 0; k < 5; k++)
            if(fieldC.findPlaceForAirPlane(plane2[k]))
            {
                java.lang.System.out.println("found, x=" + fieldC.resX() + ", y=" + fieldC.resY());
                fieldC.placeAirPlaneId(plane2[k], fieldC.resX(), fieldC.resY(), "" + k);
            } else
            {
                java.lang.System.out.println("cannot find place for plane2_" + k);
            }

        for(int l = 0; l < 5; l++)
            if(fieldC.findPlaceForAirPlane(plane[l]))
            {
                java.lang.System.out.println("found, x=" + fieldC.resX() + ", y=" + fieldC.resY());
                fieldC.placeAirPlaneId(plane[l], fieldC.resX(), fieldC.resY(), "" + (l + 5));
            } else
            {
                java.lang.System.out.println("cannot find place for plane1_" + (l + 5));
            }

        fieldC.debugPrint();
    }

    private static java.util.ArrayList points = new ArrayList();
    public static com.maddox.il2.ai.air.CellAirField fieldC;
    public static final int AIR_FIELDC_X_SIZE = 21;
    public static final int AIR_FIELDC_Y_SIZE = 21;
    public static final double AIR_FIELDC_CELL_SIZE = 5D;
    public static com.maddox.il2.ai.air.CellObject cellsC[][] = new com.maddox.il2.ai.air.CellObject[21][21];
    public static com.maddox.il2.ai.air.CellAirField field;
    public static final int AIR_FIELD_X_SIZE = 20;
    public static final int AIR_FIELD_Y_SIZE = 17;
    public static final double AIR_FIELD_CELL_SIZE = 0.5D;
    public static com.maddox.il2.ai.air.CellObject cells[][] = new com.maddox.il2.ai.air.CellObject[20][17];
    public static com.maddox.il2.ai.air.CellAirPlane plane[] = new com.maddox.il2.ai.air.CellAirPlane[5];
    public static final int AIR_PLANE_X_SIZE = 5;
    public static final int AIR_PLANE_Y_SIZE = 4;
    public static final double AIR_PLANE_CELL_SIZE = 0.5D;
    public static com.maddox.il2.ai.air.CellObject pcells[][] = new com.maddox.il2.ai.air.CellObject[5][4];
    public static boolean mask[][] = {
        {
            false, false, true, false, false
        }, {
            true, true, true, true, true
        }, {
            false, false, true, false, false
        }, {
            false, true, true, true, false
        }
    };
    public static com.maddox.il2.ai.air.CellAirPlane plane2[] = new com.maddox.il2.ai.air.CellAirPlane[5];
    public static final int AIR_PLANE_X_SIZE2 = 7;
    public static final int AIR_PLANE_Y_SIZE2 = 5;
    public static final double AIR_PLANE_CELL_SIZE2 = 0.5D;
    public static com.maddox.il2.ai.air.CellObject pcells2[][] = new com.maddox.il2.ai.air.CellObject[7][5];
    public static boolean mask2[][] = {
        {
            false, false, true, false, true, false, false
        }, {
            true, true, true, true, true, true, true
        }, {
            false, false, true, false, true, false, false
        }, {
            false, false, true, false, true, false, false
        }, {
            false, true, true, true, true, true, false
        }
    };

}

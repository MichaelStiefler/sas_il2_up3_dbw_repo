// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CellAirField.java

package com.maddox.il2.ai.air;

import com.maddox.JGP.Point3d;
import java.io.Serializable;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.ai.air:
//            CellObject, CellAirPlane, CellTools

public class CellAirField extends com.maddox.il2.ai.air.CellObject
    implements java.io.Serializable
{

    public com.maddox.JGP.Point3d leftUpperCorner()
    {
        return leftUpperCorner;
    }

    public void setLeftUpperCorner(com.maddox.JGP.Point3d point3d)
    {
        leftUpperCorner.set(point3d);
    }

    public boolean findPlaceForAirPlane(com.maddox.il2.ai.air.CellAirPlane cellairplane)
    {
        if(!cellairplane.checkAirFieldSize(this))
            return false;
        if(!cellairplane.checkAirFieldCapacity(this))
            return false;
        for(int i = 0; i <= getHeight() - cellairplane.getHeight(); i++)
        {
            for(int j = 0; j <= getWidth() - cellairplane.getWidth(); j++)
                if(isThereFreePlace(cellairplane, j, i))
                {
                    resX = j;
                    resY = i;
                    return true;
                }

        }

        return false;
    }

    public int resX()
    {
        return resX;
    }

    public int resY()
    {
        return resY;
    }

    public boolean isThereFreePlace(com.maddox.il2.ai.air.CellAirPlane cellairplane, int i, int j)
    {
        if(getCells() == null)
            return false;
        for(int k = j; k < j + cellairplane.getHeight(); k++)
        {
label0:
            for(int l = i; l < i + cellairplane.getWidth(); l++)
            {
                if(getCells()[l][k] != null && cellairplane.getCells()[l - i][k - j] != null)
                    return false;
                if(cellairplane.getCells()[l - i][k - j] == null)
                    continue;
                int i1 = k;
                do
                {
                    if(i1 >= getHeight())
                        continue label0;
                    if(getCells()[l][i1] != null && getCells()[l][i1] != this)
                        return false;
                    i1++;
                } while(true);
            }

        }

        return true;
    }

    public void placeAirPlane(com.maddox.il2.ai.air.CellAirPlane cellairplane, int i, int j)
    {
        if(getCells() == null)
            return;
        cellairplane.setWorldCoordinates(getXCoordinate() + (double)i * getCellSize(), getYCoordinate() + (double)j * getCellSize());
        for(int k = 0; k < cellairplane.getHeight(); k++)
        {
            for(int l = 0; l < cellairplane.getWidth(); l++)
                if(cellairplane.getCells()[l][k] != null)
                    getCells()[i + l][j + k] = cellairplane;

        }

    }

    public void placeAirPlaneId(com.maddox.il2.ai.air.CellAirPlane cellairplane, int i, int j, java.lang.String s)
    {
        if(getCells() == null)
            return;
        cellairplane.setWorldCoordinates(getXCoordinate() + (double)i * getCellSize(), getYCoordinate() + (double)j * getCellSize());
        for(int k = 0; k < cellairplane.getHeight(); k++)
        {
            for(int l = 0; l < cellairplane.getWidth(); l++)
                if(cellairplane.getCells()[l][k] != null)
                {
                    getCells()[i + l][j + k] = cellairplane;
                    getCells()[i + l][j + k].setId(s);
                }

        }

    }

    public boolean removeAirPlane(com.maddox.il2.ai.air.CellAirPlane cellairplane)
    {
        if(getCells() == null)
            return false;
        boolean flag = false;
        for(int i = 0; i < getCells().length; i++)
        {
            for(int j = 0; j < getCells()[0].length; j++)
                if(getCells()[i][j] == cellairplane)
                {
                    getCells()[i][j] = null;
                    flag = true;
                }

        }

        return flag;
    }

    public void replaceAirPlane(com.maddox.il2.ai.air.CellAirPlane cellairplane, int i, int j)
    {
        if(getCells() == null)
            return;
        for(int k = 0; k < cellairplane.getWidth(); k++)
        {
            for(int l = 0; l < cellairplane.getHeight(); l++)
                if(getCells()[k][l] == cellairplane)
                    getCells()[i + k][j + l] = null;

        }

    }

    public void freeCells()
    {
        if(getCells() == null)
            return;
        for(int i = 0; i < getCells().length; i++)
        {
            for(int j = 0; j < getCells()[0].length; j++)
                if(getCells()[i][j] != this)
                    getCells()[i][j] = null;

        }

    }

    public com.maddox.JGP.Point3d getLeftUpperPoint(java.util.ArrayList arraylist)
    {
        com.maddox.JGP.Point3d point3d = new Point3d();
        double d = 1000D;
        double d1 = -1000D;
        for(int i = 0; i < arraylist.size(); i++)
        {
            com.maddox.JGP.Point3d point3d1 = (com.maddox.JGP.Point3d)arraylist.get(i);
            if(point3d1.x < d)
                d = point3d1.x;
            if(point3d1.y > d1)
                d1 = point3d1.y;
        }

        point3d.set(d, d1, 0.0D);
        return point3d;
    }

    public com.maddox.JGP.Point3d getRightDownPoint(java.util.ArrayList arraylist)
    {
        com.maddox.JGP.Point3d point3d = new Point3d();
        double d = -1000D;
        double d1 = 1000D;
        for(int i = 0; i < arraylist.size(); i++)
        {
            com.maddox.JGP.Point3d point3d1 = (com.maddox.JGP.Point3d)arraylist.get(i);
            if(point3d1.x > d)
                d = point3d1.x;
            if(point3d1.y < d1)
                d1 = point3d1.y;
        }

        point3d.set(d, d1, 0.0D);
        return point3d;
    }

    public void fieldInitWithComplexPoly(java.util.ArrayList arraylist, double d)
    {
        com.maddox.JGP.Point3d point3d = new Point3d();
        com.maddox.JGP.Point3d point3d1 = new Point3d();
        com.maddox.JGP.Point3d point3d2 = new Point3d();
        com.maddox.JGP.Point3d point3d3 = new Point3d();
        setCellSize(d);
        com.maddox.JGP.Point3d point3d4 = getLeftUpperPoint(arraylist);
        setLeftUpperCorner(point3d4);
        com.maddox.JGP.Point3d point3d5 = getRightDownPoint(arraylist);
        int i = (int)(java.lang.Math.abs(point3d5.x - point3d4.x) / d);
        int j = (int)(java.lang.Math.abs(point3d5.y - point3d4.y) / d);
        com.maddox.il2.ai.air.CellObject acellobject[][] = new com.maddox.il2.ai.air.CellObject[i][j];
        setCells(acellobject);
        clearCells();
        for(int k = 0; k < getWidth(); k++)
        {
            for(int l = 0; l < getHeight(); l++)
            {
                point3d.x = point3d4.x + (double)k * d;
                point3d.y = point3d4.y - (double)l * d;
                point3d1.x = point3d4.x + (double)k * d + d;
                point3d1.y = point3d4.y - (double)l * d;
                point3d2.x = point3d4.x + (double)k * d;
                point3d2.y = (point3d4.y - (double)l * d) + d;
                point3d3.x = point3d4.x + (double)k * d + d;
                point3d3.y = (point3d4.y - (double)l * d) + d;
                if(com.maddox.il2.ai.air.CellTools.belongsToComplex(arraylist, point3d) && com.maddox.il2.ai.air.CellTools.belongsToComplex(arraylist, point3d1) && com.maddox.il2.ai.air.CellTools.belongsToComplex(arraylist, point3d2) && com.maddox.il2.ai.air.CellTools.belongsToComplex(arraylist, point3d3))
                    getCells()[k][l] = null;
                else
                    getCells()[k][l] = this;
            }

        }

    }

    public com.maddox.il2.ai.air.CellObject getClone()
    {
        if(getCells() == null)
            return null;
        com.maddox.il2.ai.air.CellObject acellobject[][] = new com.maddox.il2.ai.air.CellObject[getWidth()][getHeight()];
        for(int i = 0; i < getCells().length; i++)
        {
            for(int j = 0; j < getCells()[0].length; j++)
                acellobject[i][j] = getCells()[i][j];

        }

        com.maddox.il2.ai.air.CellAirField cellairfield = new CellAirField(acellobject, leftUpperCorner());
        cellairfield.setCellSize(getCellSize());
        return cellairfield;
    }

    public CellAirField(com.maddox.il2.ai.air.CellObject acellobject[][])
    {
        super(acellobject);
        leftUpperCorner = new Point3d();
        resX = 0;
        resY = 0;
    }

    public CellAirField(com.maddox.il2.ai.air.CellObject acellobject[][], com.maddox.JGP.Point3d point3d)
    {
        super(acellobject);
        leftUpperCorner = new Point3d();
        resX = 0;
        resY = 0;
        setLeftUpperCorner(point3d);
    }

    public CellAirField(com.maddox.il2.ai.air.CellObject acellobject[][], java.util.ArrayList arraylist, double d)
    {
        super(acellobject);
        leftUpperCorner = new Point3d();
        resX = 0;
        resY = 0;
        fieldInitWithComplexPoly(arraylist, d);
    }

    private com.maddox.JGP.Point3d leftUpperCorner;
    private int resX;
    private int resY;
}

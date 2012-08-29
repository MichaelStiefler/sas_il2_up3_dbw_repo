// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CellObject.java

package com.maddox.il2.ai.air;

import java.io.PrintStream;
import java.io.Serializable;

public class CellObject
    implements java.io.Serializable
{

    public void setCells(com.maddox.il2.ai.air.CellObject acellobject[][])
    {
        cells = acellobject;
    }

    public com.maddox.il2.ai.air.CellObject[][] getCells()
    {
        return cells;
    }

    public java.lang.String getId()
    {
        return id;
    }

    public void setId(java.lang.String s)
    {
        id = s;
    }

    public void setCellX(double d)
    {
        cellX = d;
    }

    public double getCellX()
    {
        return cellX;
    }

    public void setCellY(double d)
    {
        cellY = d;
    }

    public double getCellY()
    {
        return cellY;
    }

    public void setCoordinates(double d, double d1)
    {
        setCellX(d);
        setCellY(d1);
    }

    public int getWidth()
    {
        if(cells == null)
            return 0;
        else
            return cells.length;
    }

    public int getHeight()
    {
        if(cells == null)
            return 0;
        if(cells[0] == null)
            return 0;
        else
            return cells[0].length;
    }

    public double getXCoordinate()
    {
        if(cells == null)
            return 0.0D;
        if(cells[0][0] == null)
            return 0.0D;
        else
            return cells[0][0].getCellX();
    }

    public double getYCoordinate()
    {
        if(cells == null)
            return 0.0D;
        if(cells[0][0] == null)
            return 0.0D;
        else
            return cells[0][0].getCellY();
    }

    public double getCellSize()
    {
        return cellSize;
    }

    public void setCellSize(double d)
    {
        cellSize = d;
    }

    public void setWorldCoordinates(double d, double d1)
    {
        if(cells == null)
            return;
        if(cells[0][0] == null)
            return;
        double d2 = cells[0][0].getCellSize();
        for(int i = 0; i < cells.length; i++)
        {
            for(int j = 0; j < cells[0].length; j++)
                if(cells[i][j] != null)
                    cells[i][j].setCoordinates(d + (double)i * d2, d1 + (double)j * d2);

        }

    }

    public com.maddox.il2.ai.air.CellObject getClone()
    {
        if(cells == null)
            return null;
        com.maddox.il2.ai.air.CellObject acellobject[][] = new com.maddox.il2.ai.air.CellObject[getWidth()][getHeight()];
        for(int i = 0; i < cells.length; i++)
        {
            for(int j = 0; j < cells[0].length; j++)
                if(cells[i][j] != null)
                    acellobject[i][j] = cells[i][j];

        }

        return new CellObject(acellobject);
    }

    public void clearCells()
    {
        if(cells == null)
            return;
        for(int i = 0; i < cells.length; i++)
        {
            for(int j = 0; j < cells[0].length; j++)
                cells[i][j] = null;

        }

    }

    public int calcFilledCells()
    {
        int i = 0;
        if(cells == null)
            return 0;
        for(int j = 0; j < cells.length; j++)
        {
            for(int k = 0; k < cells[0].length; k++)
                if(cells[j][k] != null)
                    i++;

        }

        return i;
    }

    public int calcFreeCells()
    {
        int i = 0;
        if(cells == null)
            return 0;
        if(cells[0][0] == null)
            return 0;
        for(int j = 0; j < cells.length; j++)
        {
            for(int k = 0; k < cells[0].length; k++)
                if(cells[j][k] == null)
                    i++;

        }

        return i;
    }

    public void debugPrint()
    {
        if(cells == null)
            return;
        java.lang.System.out.println();
        for(int i = 0; i < cells[0].length; i++)
        {
            for(int j = 0; j < cells.length; j++)
                if(cells[j][i] != null)
                    java.lang.System.out.print(cells[j][i].getId());
                else
                    java.lang.System.out.print("_");

            java.lang.System.out.println();
        }

        java.lang.System.out.println();
    }

    public void reInitReferences()
    {
        if(cells == null)
            return;
        for(int i = 0; i < cells.length; i++)
        {
            for(int j = 0; j < cells[0].length; j++)
                if(cells[i][j] != null)
                    cells[i][j] = this;

        }

    }

    public CellObject(com.maddox.il2.ai.air.CellObject acellobject[][])
    {
        id = "*";
        cellSize = 0.5D;
        cells = acellobject;
        reInitReferences();
    }

    public CellObject()
    {
        id = "*";
        cellSize = 0.5D;
        cells = new com.maddox.il2.ai.air.CellObject[1][1];
        cells[0][0] = this;
    }

    private com.maddox.il2.ai.air.CellObject cells[][];
    private java.lang.String id;
    private double cellX;
    private double cellY;
    private double cellSize;
}

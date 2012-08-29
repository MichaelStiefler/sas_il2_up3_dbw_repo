// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GRegion.java

package com.maddox.gwindow;

import com.maddox.rts.ObjIO;

// Referenced classes of package com.maddox.gwindow:
//            GPoint, GSize

public class GRegion
{

    public void get(com.maddox.gwindow.GPoint gpoint)
    {
        gpoint.x = x;
        gpoint.y = y;
    }

    public void get(com.maddox.gwindow.GSize gsize)
    {
        gsize.dx = dx;
        gsize.dy = dy;
    }

    public void get(com.maddox.gwindow.GPoint gpoint, com.maddox.gwindow.GSize gsize)
    {
        gpoint.x = x;
        gpoint.y = y;
        gsize.dx = dx;
        gsize.dy = dy;
    }

    public void set(float f, float f1, float f2, float f3)
    {
        x = f;
        y = f1;
        dx = f2;
        dy = f3;
    }

    public void set(com.maddox.gwindow.GPoint gpoint, com.maddox.gwindow.GSize gsize)
    {
        x = gpoint.x;
        y = gpoint.y;
        dx = gsize.dx;
        dy = gsize.dy;
    }

    public void set(com.maddox.gwindow.GRegion gregion)
    {
        x = gregion.x;
        y = gregion.y;
        dx = gregion.dx;
        dy = gregion.dy;
    }

    public GRegion()
    {
        x = 0.0F;
        y = 0.0F;
        dx = 0.0F;
        dy = 0.0F;
    }

    public GRegion(float f, float f1, float f2, float f3)
    {
        x = 0.0F;
        y = 0.0F;
        dx = 0.0F;
        dy = 0.0F;
        set(f, f1, f2, f3);
    }

    public GRegion(com.maddox.gwindow.GPoint gpoint, com.maddox.gwindow.GSize gsize)
    {
        x = 0.0F;
        y = 0.0F;
        dx = 0.0F;
        dy = 0.0F;
        set(gpoint, gsize);
    }

    public GRegion(com.maddox.gwindow.GRegion gregion)
    {
        x = 0.0F;
        y = 0.0F;
        dx = 0.0F;
        dy = 0.0F;
        set(gregion);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public float x;
    public float y;
    public float dx;
    public float dy;

    static 
    {
        com.maddox.rts.ObjIO.fields(com.maddox.gwindow.GRegion.class, new java.lang.String[] {
            "x", "y", "dx", "dy"
        });
    }
}

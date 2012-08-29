// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GBevel.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GRegion

public class GBevel
{

    public GBevel()
    {
        TL = new GRegion();
        T = new GRegion();
        TR = new GRegion();
        L = new GRegion();
        R = new GRegion();
        BL = new GRegion();
        B = new GRegion();
        BR = new GRegion();
        Area = new GRegion();
    }

    public void set(com.maddox.gwindow.GRegion gregion, com.maddox.gwindow.GRegion gregion1)
    {
        TL.x = gregion.x;
        TL.y = gregion.y;
        TL.dx = gregion1.x - gregion.x;
        TL.dy = gregion1.y - gregion.y;
        T.x = gregion.x + TL.dx;
        T.y = gregion.y;
        T.dx = gregion1.dx;
        T.dy = TL.dy;
        TR.x = gregion1.x + gregion1.dx;
        TR.y = gregion.y;
        TR.dx = gregion.dx - gregion1.dx - TL.dx;
        TR.dy = TL.dy;
        L.x = gregion.x;
        L.y = gregion.y + TL.dy;
        L.dx = TL.dx;
        L.dy = gregion1.dy;
        R.x = TR.x;
        R.y = gregion.y + TR.dy;
        R.dx = TR.dx;
        R.dy = gregion1.dy;
        BL.x = gregion.x;
        BL.y = gregion1.y + gregion1.dy;
        BL.dx = TL.dx;
        BL.dy = gregion.dy - gregion1.dy - TL.dy;
        B.x = gregion.x + BL.dx;
        B.y = BL.y;
        B.dx = gregion1.dx;
        B.dy = BL.dy;
        BR.x = TR.x;
        BR.y = BL.y;
        BR.dx = TR.dx;
        BR.dy = BL.dy;
        Area.set(gregion1);
    }

    public com.maddox.gwindow.GRegion TL;
    public com.maddox.gwindow.GRegion T;
    public com.maddox.gwindow.GRegion TR;
    public com.maddox.gwindow.GRegion L;
    public com.maddox.gwindow.GRegion R;
    public com.maddox.gwindow.GRegion BL;
    public com.maddox.gwindow.GRegion B;
    public com.maddox.gwindow.GRegion BR;
    public com.maddox.gwindow.GRegion Area;
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GTreeModelDir95.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GTreeModelDir, GTexRegion, GTreePath

public class GTreeModelDir95 extends com.maddox.gwindow.GTreeModelDir
{

    public com.maddox.gwindow.GTexRegion getIcon(com.maddox.gwindow.GTreePath gtreepath, boolean flag, boolean flag1)
    {
        if(flag)
        {
            if(iconSelect == null)
                iconSelect = new GTexRegion("GUI/win95/cursorss.mat", 32F, 96F, 32F, 32F);
            return iconSelect;
        }
        if(iconNormal == null)
            iconNormal = new GTexRegion("GUI/win95/cursorss.mat", 64F, 96F, 32F, 32F);
        return iconNormal;
    }

    public GTreeModelDir95(java.lang.String s)
    {
        super(s);
    }

    private com.maddox.gwindow.GTexRegion iconNormal;
    private com.maddox.gwindow.GTexRegion iconSelect;
}

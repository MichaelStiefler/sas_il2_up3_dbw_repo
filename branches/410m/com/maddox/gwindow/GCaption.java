// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GCaption.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindow, GWindowRoot, GCanvas, GFont, 
//            GSize

public class GCaption
{

    public void draw(com.maddox.gwindow.GWindow gwindow, float f, float f1, com.maddox.gwindow.GFont gfont)
    {
        draw(gwindow, f, f1, gfont, true);
    }

    public void draw(com.maddox.gwindow.GWindow gwindow, float f, float f1, com.maddox.gwindow.GFont gfont, boolean flag)
    {
        gwindow.root.C.font = gfont;
        gwindow.draw(f, f1, caption);
        if(hotKey != 0 && flag)
            gwindow.draw(f + (float)offsetHotKey(gfont), f1, "_");
    }

    public int offsetHotKey(com.maddox.gwindow.GFont gfont)
    {
        if(offsetHotKey < 0)
            offsetHotKey = (int)gfont.size(caption, 0, iHotKey).dx;
        return offsetHotKey;
    }

    public void set(java.lang.String s)
    {
        int i = s.indexOf('&');
        if(i == 0)
        {
            caption = s.substring(1);
            iHotKey = 0;
            hotKey = s.charAt(1);
        } else
        if(i > 0)
        {
            caption = s.substring(0, i) + s.substring(i + 1);
            iHotKey = i;
            hotKey = s.charAt(i + 1);
            offsetHotKey = -1;
        } else
        {
            caption = s;
            iHotKey = 0;
            hotKey = '\0';
            offsetHotKey = 0;
        }
    }

    public GCaption(java.lang.String s)
    {
        set(s);
    }

    public java.lang.String caption;
    public int iHotKey;
    public int offsetHotKey;
    public char hotKey;
}

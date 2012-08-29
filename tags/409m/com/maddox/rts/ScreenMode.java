// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ScreenMode.java

package com.maddox.rts;

import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.GObj;
import java.util.Vector;

public class ScreenMode
{

    public int width()
    {
        return cx;
    }

    public int height()
    {
        return cy;
    }

    public int colourBits()
    {
        return bpp;
    }

    public static com.maddox.rts.ScreenMode[] all()
    {
        java.util.Vector vector = new Vector();
        for(int j = 0; com.maddox.rts.ScreenMode.EGet(j, n); j++)
        {
            int i;
            for(i = 0; i < vector.size(); i++)
            {
                com.maddox.rts.ScreenMode screenmode = (com.maddox.rts.ScreenMode)vector.elementAt(i);
                if(screenmode.cx == n[0] && screenmode.cy == n[1] && screenmode.bpp == n[2])
                    break;
            }

            if(i == vector.size())
            {
                com.maddox.rts.ScreenMode screenmode1 = new ScreenMode(n[0], n[1], n[2]);
                vector.addElement(screenmode1);
            }
        }

        com.maddox.rts.ScreenMode ascreenmode[] = new com.maddox.rts.ScreenMode[vector.size()];
        vector.copyInto(ascreenmode);
        return ascreenmode;
    }

    public static com.maddox.rts.ScreenMode startup()
    {
        com.maddox.rts.ScreenMode.init();
        return def;
    }

    public static com.maddox.rts.ScreenMode current()
    {
        com.maddox.rts.ScreenMode.init();
        return cur;
    }

    public static boolean set(int i, int j, int k)
    {
        com.maddox.rts.ScreenMode.init();
        if(i == cur.cx && j == cur.cy && k == cur.bpp)
            return true;
        if(i == def.cx && j == def.cy && k == def.bpp)
        {
            com.maddox.rts.ScreenMode.restore();
            return true;
        }
        n[0] = i;
        n[1] = j;
        n[2] = k;
        if(com.maddox.rts.ScreenMode.ESet(n))
        {
            com.maddox.rts.ScreenMode.EGetCurrent(n);
            cur = new ScreenMode(n[0], n[1], n[2]);
            return true;
        } else
        {
            return false;
        }
    }

    public static com.maddox.rts.ScreenMode readCurrent()
    {
        com.maddox.rts.ScreenMode.EGetCurrent(n);
        return new ScreenMode(n[0], n[1], n[2]);
    }

    public static boolean set(com.maddox.rts.ScreenMode screenmode)
    {
        return com.maddox.rts.ScreenMode.set(screenmode.width(), screenmode.height(), screenmode.colourBits());
    }

    public static void restore()
    {
        if(cur != def)
        {
            cur = def;
            com.maddox.rts.ScreenMode.ESet(null);
        }
    }

    public ScreenMode(com.maddox.rts.ScreenMode screenmode)
    {
        ext = null;
        cx = screenmode.cx;
        cy = screenmode.cy;
        bpp = screenmode.bpp;
    }

    private static void init()
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(def != null)
                return;
            com.maddox.rts.ScreenMode.EGetCurrent(n);
            def = new ScreenMode(n[0], n[1], n[2]);
            cur = def;
        }
    }

    private static final native boolean EGet(int i, int ai[]);

    private static final native boolean ESet(int ai[]);

    private static final native void EGetCurrent(int ai[]);

    private ScreenMode(int i, int j, int k)
    {
        ext = null;
        cx = i;
        cy = j;
        bpp = k;
    }

    public java.lang.Object ext;
    private static com.maddox.rts.ScreenMode def;
    private static com.maddox.rts.ScreenMode cur;
    private static int n[] = new int[3];
    private int cx;
    private int cy;
    private int bpp;

    static 
    {
        com.maddox.il2.engine.GObj.loadNative();
    }
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIFont.java

package com.maddox.il2.engine;

import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GSize;

// Referenced classes of package com.maddox.il2.engine:
//            TTFont

public class GUIFont extends com.maddox.gwindow.GFont
{
    public static class _Loader extends com.maddox.gwindow.GFont.Loader
    {

        public com.maddox.gwindow.GFont load(java.lang.String s)
        {
            com.maddox.il2.engine.GUIFont guifont = new GUIFont();
            guifont.fnt = com.maddox.il2.engine.TTFont.get(s);
            guifont.resolutionChanged();
            return guifont;
        }

        public _Loader()
        {
        }
    }


    public void size(java.lang.String s, com.maddox.gwindow.GSize gsize)
    {
        if(fnt == null)
        {
            gsize.dx = 0.0F;
            gsize.dy = 1.0F;
            return;
        }
        if(s == null || s.length() == 0)
        {
            gsize.dx = 0.0F;
            gsize.dy = height;
            return;
        } else
        {
            gsize.dx = fnt.width(s);
            gsize.dy = height;
            return;
        }
    }

    public void size(java.lang.String s, int i, int j, com.maddox.gwindow.GSize gsize)
    {
        if(fnt == null)
        {
            gsize.dx = 0.0F;
            gsize.dy = 1.0F;
            return;
        }
        if(s == null || s.length() == 0)
        {
            gsize.dx = 0.0F;
            gsize.dy = height;
            return;
        } else
        {
            gsize.dx = fnt.width(s, i, j);
            gsize.dy = height;
            return;
        }
    }

    public void size(char ac[], int i, int j, com.maddox.gwindow.GSize gsize)
    {
        if(fnt == null)
        {
            gsize.dx = 0.0F;
            gsize.dy = 1.0F;
            return;
        }
        if(ac == null || ac.length == 0)
        {
            gsize.dx = 0.0F;
            gsize.dy = height;
            return;
        } else
        {
            gsize.dx = fnt.width(ac, i, j);
            gsize.dy = height;
            return;
        }
    }

    public int len(java.lang.String s, float f, boolean flag, boolean flag1)
    {
        if(fnt == null || s == null || s.length() == 0)
            return 0;
        if(flag)
            return fnt.len(s, f, flag1);
        else
            return fnt.lenEnd(s, f, flag1);
    }

    public int len(java.lang.String s, int i, int j, float f, boolean flag, boolean flag1)
    {
        if(fnt == null || s == null || s.length() == 0)
            return 0;
        if(flag)
            return fnt.len(s, i, j, f, flag1);
        else
            return fnt.lenEnd(s, i, j, f, flag1);
    }

    public int len(char ac[], int i, int j, float f, boolean flag, boolean flag1)
    {
        if(fnt == null || ac == null || ac.length == 0)
            return 0;
        if(flag)
            return fnt.len(ac, i, j, f, flag1);
        else
            return fnt.lenEnd(ac, i, j, f, flag1);
    }

    public void resolutionChanged()
    {
        if(fnt != null)
        {
            height = fnt.height();
            descender = fnt.descender();
        }
    }

    private GUIFont()
    {
        fnt = null;
    }


    protected com.maddox.il2.engine.TTFont fnt;
}

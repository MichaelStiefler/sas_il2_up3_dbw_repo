// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TextScr.java

package com.maddox.il2.engine;

import com.maddox.JGP.Color4f;
import com.maddox.opengl.Provider;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;

// Referenced classes of package com.maddox.il2.engine:
//            Render, TextScrItem, CameraOrtho2D, TTFont

public class TextScr extends com.maddox.il2.engine.Render
{

    public void preRender()
    {
    }

    public void render()
    {
        com.maddox.opengl.Provider.setPauseProfile(true);
        for(com.maddox.util.HashMapIntEntry hashmapintentry = context.nextEntry(null); hashmapintentry != null; hashmapintentry = context.nextEntry(hashmapintentry))
        {
            int i = hashmapintentry.getKey();
            com.maddox.il2.engine.TextScrItem textscritem = (com.maddox.il2.engine.TextScrItem)hashmapintentry.getValue();
            textscritem.font.output(((int)(textscritem.color.w * 255F) & 0xff) << 24 | ((int)(textscritem.color.x * 255F) & 0xff) << 0 | ((int)(textscritem.color.y * 255F) & 0xff) << 8 | ((int)(textscritem.color.z * 255F) & 0xff) << 16, i >> 16, i & 0xffff, 0.0F, textscritem.str);
        }

        context.clear();
        com.maddox.opengl.Provider.setPauseProfile(false);
    }

    public static void output(int i, int j, java.lang.String s)
    {
        if(s == null || "".equals(s))
            scr.context.remove((i & 0xffff) << 16 | j & 0xffff);
        else
            scr.context.put((i & 0xffff) << 16 | j & 0xffff, new TextScrItem(scr.color, scr.font, s));
    }

    public static void output(float f, float f1, java.lang.String s)
    {
        if(s == null || "".equals(s))
            scr.context.remove(((int)f & 0xffff) << 16 | (int)f1 & 0xffff);
        else
            scr.context.put(((int)f & 0xffff) << 16 | (int)f1 & 0xffff, new TextScrItem(scr.color, scr.font, s));
    }

    public static com.maddox.il2.engine.TTFont font()
    {
        if(scr == null)
        {
            scr = new TextScr();
            com.maddox.il2.engine.CameraOrtho2D cameraortho2d = new CameraOrtho2D();
            cameraortho2d.set(0.0F, scr.getViewPortWidth(), 0.0F, scr.getViewPortHeight());
            scr.setCamera(cameraortho2d);
            scr.setName("renderTextScr");
        }
        return scr.font;
    }

    public static void setFont(com.maddox.il2.engine.TTFont ttfont)
    {
        scr.font = ttfont;
    }

    public static void setFont(java.lang.String s)
    {
        scr.font = com.maddox.il2.engine.TTFont.get(s);
    }

    public static void setColor(com.maddox.JGP.Color4f color4f)
    {
        scr.color = new Color4f(color4f);
    }

    public static com.maddox.JGP.Color4f color()
    {
        return new Color4f(scr.color);
    }

    private TextScr()
    {
        super(-1F);
        context = new HashMapInt();
        font = com.maddox.il2.engine.TTFont.font[1];
        color = new Color4f(0.0F, 0.0F, 0.0F, 1.0F);
        useClearDepth(false);
        useClearColor(false);
    }

    protected void contextResize(int i, int j)
    {
        super.contextResize(i, j);
        context.clear();
    }

    public static com.maddox.il2.engine.TextScr This()
    {
        return scr;
    }

    private com.maddox.util.HashMapInt context;
    private com.maddox.il2.engine.TTFont font;
    private com.maddox.JGP.Color4f color;
    private static com.maddox.il2.engine.TextScr scr;
}

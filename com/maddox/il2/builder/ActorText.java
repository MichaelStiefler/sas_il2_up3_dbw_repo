// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorText.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GColor;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.rts.Message;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, Builder

public class ActorText extends com.maddox.il2.engine.Actor
    implements com.maddox.il2.objects.ActorAlign
{

    public static void setupFonts()
    {
        tfont[0] = com.maddox.il2.engine.TTFont.font[0];
        tfont[1] = com.maddox.il2.engine.TTFont.font[1];
        tfont[2] = com.maddox.il2.engine.TTFont.font[2];
    }

    public static void setRenderLevel(int i)
    {
        renderLevel = i;
    }

    public static void setRenderClip(double d, double d1, double d2, double d3)
    {
        x0 = d;
        y0 = d1;
        x1 = d + d2;
        y1 = d1 + d3;
    }

    private boolean isVisible()
    {
        if(p2d.x + (double)w < x0)
            return false;
        if(p2d.x > x1)
            return false;
        if(p2d.y + (double)h < y0)
            return false;
        return p2d.y <= y1;
    }

    public void render2d()
    {
        if(w == 0.0F)
            return;
        if(!bLevel[renderLevel])
            return;
        p2d.x = (pos.getAbsPoint().x - com.maddox.il2.builder.Plugin.builder.camera2D.worldXOffset) * com.maddox.il2.builder.Plugin.builder.camera2D.worldScale;
        p2d.y = (pos.getAbsPoint().y - com.maddox.il2.builder.Plugin.builder.camera2D.worldYOffset) * com.maddox.il2.builder.Plugin.builder.camera2D.worldScale;
        switch(align)
        {
        case 1: // '\001'
            p2d.x -= w / 2.0F;
            break;

        case 2: // '\002'
            p2d.x -= w;
            break;
        }
        if(isVisible())
            tfont[font].output(0xff000000 | gcolor[color].color, (float)p2d.x, (float)p2d.y, 0.0F, text);
    }

    private void computeSizes()
    {
        if(text == null || text.length() == 0)
            w = 0.0F;
        else
            w = tfont[font].width(text);
        h = tfont[font].height();
    }

    public void setText(java.lang.String s)
    {
        text = s;
        computeSizes();
    }

    public java.lang.String getText()
    {
        return text;
    }

    public void setFont(int i)
    {
        font = i;
        computeSizes();
    }

    public int getFont()
    {
        return font;
    }

    public void checkLevels(int i)
    {
        for(int j = 0; j < 3; j++)
            if(bLevel[j])
                return;

        bLevel[i] = true;
    }

    public void saveAsDef()
    {
        def_text = text;
        def_font = font;
        def_align = align;
        def_color = color;
        def_bLevel[0] = bLevel[0];
        def_bLevel[1] = bLevel[1];
        def_bLevel[2] = bLevel[2];
    }

    public void align()
    {
        alignPosToLand(0.0D, true);
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public ActorText(com.maddox.JGP.Point3d point3d)
    {
        text = def_text;
        font = def_font;
        align = def_align;
        color = def_color;
        bLevel = new boolean[3];
        w = 0.0F;
        flags |= 0x2000;
        pos = new ActorPosMove(this);
        com.maddox.il2.engine.IconDraw.create(this);
        if(point3d != null)
        {
            pos.setAbs(point3d);
            align();
        }
        drawing(true);
        setFont(def_font);
        setText(def_text);
        bLevel[0] = def_bLevel[0];
        bLevel[1] = def_bLevel[1];
        bLevel[2] = def_bLevel[2];
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public static java.lang.String def_text = "";
    public static int def_font = 1;
    public static int def_align = 1;
    public static int def_color = 0;
    public static boolean def_bLevel[];
    public static com.maddox.gwindow.GColor gcolor[];
    private java.lang.String text;
    private int font;
    public int align;
    public int color;
    public boolean bLevel[];
    public static com.maddox.il2.engine.TTFont tfont[] = new com.maddox.il2.engine.TTFont[3];
    private float w;
    private float h;
    private static int renderLevel = 0;
    private static double x0;
    private static double y0;
    private static double x1;
    private static double y1;
    private static com.maddox.JGP.Point2d p2d = new Point2d();

    static 
    {
        def_bLevel = new boolean[3];
        gcolor = new com.maddox.gwindow.GColor[20];
        def_bLevel[0] = def_bLevel[1] = def_bLevel[2] = true;
        gcolor[0] = new GColor(0, 0, 0);
        gcolor[1] = new GColor(128, 0, 0);
        gcolor[2] = new GColor(0, 128, 0);
        gcolor[3] = new GColor(128, 128, 0);
        gcolor[4] = new GColor(0, 0, 128);
        gcolor[5] = new GColor(128, 0, 128);
        gcolor[6] = new GColor(0, 128, 128);
        gcolor[7] = new GColor(192, 192, 192);
        gcolor[8] = new GColor(192, 220, 192);
        gcolor[9] = new GColor(166, 202, 240);
        gcolor[10] = new GColor(255, 251, 240);
        gcolor[11] = new GColor(160, 160, 164);
        gcolor[12] = new GColor(128, 128, 128);
        gcolor[13] = new GColor(255, 0, 0);
        gcolor[14] = new GColor(0, 255, 0);
        gcolor[15] = new GColor(255, 255, 0);
        gcolor[16] = new GColor(0, 0, 255);
        gcolor[17] = new GColor(255, 0, 255);
        gcolor[18] = new GColor(0, 255, 255);
        gcolor[19] = new GColor(255, 255, 255);
        com.maddox.rts.Property.set(com.maddox.il2.builder.ActorText.class, "iconName", "icons/SelectIcon.mat");
    }
}

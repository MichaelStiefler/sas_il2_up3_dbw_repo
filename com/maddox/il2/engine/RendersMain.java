// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RendersMain.java

package com.maddox.il2.engine;

import com.maddox.opengl.GLContext;

// Referenced classes of package com.maddox.il2.engine:
//            Engine, Renders, Render

public class RendersMain
{

    public static com.maddox.il2.engine.Render get(int i)
    {
        return com.maddox.il2.engine.Engine.rendersMain().get(i);
    }

    public static boolean isShow()
    {
        return com.maddox.il2.engine.Engine.rendersMain().isShow();
    }

    public static void setShow(boolean flag)
    {
        com.maddox.il2.engine.Engine.rendersMain().setShow(flag);
    }

    public static int frame()
    {
        return com.maddox.il2.engine.Engine.rendersMain().frame();
    }

    public static int width()
    {
        return com.maddox.il2.engine.Engine.rendersMain().width();
    }

    public static int height()
    {
        return com.maddox.il2.engine.Engine.rendersMain().height();
    }

    public static boolean isSaveAspect()
    {
        return com.maddox.il2.engine.Engine.rendersMain().isSaveAspect();
    }

    public static void setSaveAspect(boolean flag)
    {
        com.maddox.il2.engine.Engine.rendersMain().setSaveAspect(flag);
    }

    public static void getAspectViewPort(float af[])
    {
        com.maddox.il2.engine.Engine.rendersMain().getAspectViewPort(af);
    }

    public static void getAspectViewPort(int ai[])
    {
        com.maddox.il2.engine.Engine.rendersMain().getAspectViewPort(ai);
    }

    public static int getAspectViewPortWidth()
    {
        return com.maddox.il2.engine.Engine.rendersMain().getAspectViewPortWidth();
    }

    public static int getAspectViewPortHeight()
    {
        return com.maddox.il2.engine.Engine.rendersMain().getAspectViewPortHeight();
    }

    public static void getViewPort(float af[])
    {
        com.maddox.il2.engine.Engine.rendersMain().getViewPort(af);
    }

    public static void getViewPort(int ai[])
    {
        com.maddox.il2.engine.Engine.rendersMain().getViewPort(ai);
    }

    public static int getViewPortWidth()
    {
        return com.maddox.il2.engine.Engine.rendersMain().getViewPortWidth();
    }

    public static int getViewPortHeight()
    {
        return com.maddox.il2.engine.Engine.rendersMain().getViewPortHeight();
    }

    public static void setRenderFocus(com.maddox.il2.engine.Render render)
    {
        com.maddox.il2.engine.Engine.rendersMain().setRenderFocus(render);
    }

    public static com.maddox.il2.engine.Render getRenderFocus()
    {
        return com.maddox.il2.engine.Engine.rendersMain().getRenderFocus();
    }

    public static com.maddox.opengl.GLContext glContext()
    {
        return com.maddox.il2.engine.Engine.rendersMain().glContext();
    }

    public static void setGlContext(com.maddox.opengl.GLContext glcontext)
    {
        com.maddox.il2.engine.Engine.rendersMain().setGlContext(glcontext);
    }

    public static boolean isTickPainting()
    {
        return com.maddox.il2.engine.Engine.rendersMain().isTickPainting();
    }

    public static void setTickPainting(boolean flag)
    {
        com.maddox.il2.engine.Engine.rendersMain().setTickPainting(flag);
    }

    private RendersMain()
    {
    }

    public static boolean bSwapBuffersResult = true;

}

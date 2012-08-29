// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ConsoleGL0.java

package com.maddox.il2.engine;

import com.maddox.rts.CmdEnv;
import com.maddox.rts.Console;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Referenced classes of package com.maddox.il2.engine:
//            ConsoleGL0Listener, CameraOrtho2D, ConsoleGL0Render, Mat, 
//            Config, TTFont, RendersMain

public class ConsoleGL0
{

    public ConsoleGL0()
    {
    }

    public static void exclusiveDraw(boolean flag)
    {
        if(flag)
            consoleListener = new ConsoleGL0Listener(render);
        else
            consoleListener = null;
        com.maddox.rts.RTSConf.cur.console.setListenerChanges(consoleListener);
    }

    public static void exclusiveDraw(java.lang.String s, long l)
    {
        try
        {
            backgroundMat = com.maddox.il2.engine.Mat.New(s);
            if(backgroundMat != null)
            {
                render.exlusiveDraw();
                long l1 = com.maddox.rts.Time.real() + l;
                long l2;
                do
                    l2 = com.maddox.rts.Time.real();
                while(l2 < l1);
            }
        }
        catch(java.lang.Exception exception) { }
    }

    public static void exclusiveDraw(java.lang.String s)
    {
        try
        {
            java.lang.String s1 = com.maddox.rts.RTSConf.cur.locale.getLanguage();
            if(!"us".equalsIgnoreCase(s1) && !"en".equalsIgnoreCase(s1))
            {
                java.lang.String s2 = s.substring(0, s.length() - ".mat".length()) + "_" + s1 + ".mat";
                backgroundMat = com.maddox.il2.engine.Mat.New(s2);
                if(backgroundMat != null)
                {
                    render.exlusiveDraw();
                    return;
                }
            }
        }
        catch(java.lang.Exception exception) { }
        try
        {
            backgroundMat = com.maddox.il2.engine.Mat.New(s);
            render.exlusiveDraw();
        }
        catch(java.lang.Exception exception1) { }
    }

    public static void exclusiveDrawStep(java.lang.String s, int i)
    {
        if(backgroundMat == null)
            return;
        if(s == null)
        {
            backgroundMat = null;
            render.sstep = null;
        } else
        {
            render.exlusiveDrawStep(s, i);
        }
    }

    public static boolean isActive()
    {
        return bActive;
    }

    public static void activate(boolean flag)
    {
        if(bActive != flag)
        {
            com.maddox.rts.RTSConf.cur.console.activate(flag);
            render.setShow(flag);
            bActive = flag;
            if(bActive)
            {
                java.util.List list = com.maddox.rts.HotKeyEnv.allEnv();
                int j = list.size();
                for(int l = 0; l < j; l++)
                {
                    com.maddox.rts.HotKeyEnv hotkeyenv1 = (com.maddox.rts.HotKeyEnv)list.get(l);
                    if(hotkeyenv1.isEnabled() && !envName.equals(hotkeyenv1.name()))
                    {
                        hotkeyenv1.enable(false);
                        pausedEnv.add(hotkeyenv1);
                    }
                }

            } else
            {
                int i = pausedEnv.size();
                for(int k = 0; k < i; k++)
                {
                    com.maddox.rts.HotKeyEnv hotkeyenv = (com.maddox.rts.HotKeyEnv)pausedEnv.get(k);
                    hotkeyenv.enable(true);
                }

                pausedEnv.clear();
            }
        }
    }

    private static void initHotKeys()
    {
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(true, "Activate") {

            public void end()
            {
                com.maddox.il2.engine.ConsoleGL0.activate(!com.maddox.il2.engine.ConsoleGL0.bActive);
            }

        }
);
    }

    public static void init(java.lang.String s, int i)
    {
        if(!bInit)
        {
            com.maddox.rts.CmdEnv cmdenv = com.maddox.rts.RTSConf.cur.console.getEnv();
            cmdenv.setLevelAccess(i);
            envName = s;
            com.maddox.rts.HotKeyEnv.fromIni(envName, com.maddox.il2.engine.Config.cur.ini, "HotKey " + envName);
            font = com.maddox.il2.engine.TTFont.font[3];
            com.maddox.il2.engine.CameraOrtho2D cameraortho2d = new CameraOrtho2D();
            cameraortho2d.setName("cameraConsoleGL0");
            cameraortho2d.set(0.0F, com.maddox.il2.engine.RendersMain.getViewPortWidth(), 0.0F, com.maddox.il2.engine.RendersMain.getViewPortHeight());
            render = new ConsoleGL0Render(0.0F);
            render.setName("renderConsoleGL0");
            render.setCamera(cameraortho2d);
            render.setShow(false);
            com.maddox.il2.engine.ConsoleGL0.initHotKeys();
            bInit = true;
        }
    }

    public static float typeOffset = 5F;
    protected static com.maddox.il2.engine.ConsoleGL0Listener consoleListener;
    protected static com.maddox.il2.engine.Mat backgroundMat;
    protected static boolean bActive = false;
    protected static com.maddox.il2.engine.TTFont font;
    private static java.util.ArrayList pausedEnv = new ArrayList();
    private static java.lang.String envName;
    private static com.maddox.il2.engine.ConsoleGL0Render render;
    private static boolean bInit = false;

}

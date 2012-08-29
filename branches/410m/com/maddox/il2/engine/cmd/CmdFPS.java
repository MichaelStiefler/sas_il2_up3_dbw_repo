// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdFPS.java

package com.maddox.il2.engine.cmd;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.RendersMain;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.engine.TextScr;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.MsgTimeOut;
import com.maddox.rts.MsgTimeOutListener;
import com.maddox.rts.Time;
import com.maddox.sound.AudioDevice;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdFPS extends com.maddox.rts.Cmd
    implements com.maddox.rts.MsgTimeOutListener
{

    public void msgTimeOut(java.lang.Object obj)
    {
        msg.post();
        if(!bGo)
            return;
        long l = com.maddox.rts.Time.real();
        int i = com.maddox.il2.engine.RendersMain.frame();
        if(l >= timePrev + 250L)
        {
            fpsCur = (1000D * (double)(i - framePrev)) / (double)(l - timePrev);
            if(fpsMin > fpsCur)
                fpsMin = fpsCur;
            if(fpsMax < fpsCur)
                fpsMax = fpsCur;
            framePrev = i;
            timePrev = l;
        }
        if(framePrev == frameStart)
            return;
        if(bShow)
        {
            com.maddox.il2.engine.Render render = (com.maddox.il2.engine.Render)com.maddox.il2.engine.Actor.getByName("renderTextScr");
            if(render == null)
                return;
            com.maddox.il2.engine.TTFont ttfont = com.maddox.il2.engine.TextScr.font();
            int j = render.getViewPortWidth();
            int k = render.getViewPortHeight();
            java.lang.String s = "fps:" + fpsInfo();
            int i1 = (int)ttfont.width(s);
            int j1 = k - ttfont.height() - 5;
            int k1 = (j - i1) / 2;
            com.maddox.il2.engine.TextScr.output(k1, j1, s);
        }
        if(logPeriod > 0L && l >= logPrintTime + logPeriod)
        {
            java.lang.System.out.println("fps:" + fpsInfo());
            logPrintTime = l;
        }
    }

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        boolean flag = false;
        checkMsg();
        if(map.containsKey("SHOW"))
        {
            bShow = true;
            flag = true;
        } else
        if(map.containsKey("HIDE"))
        {
            bShow = false;
            flag = true;
        }
        if(map.containsKey("LOG"))
        {
            int i = com.maddox.il2.engine.cmd.CmdFPS.arg(map, "LOG", 0, 5);
            if(i < 0)
                i = 0;
            logPeriod = (long)i * 1000L;
            flag = true;
        }
        if(map.containsKey("PERF"))
        {
            com.maddox.sound.AudioDevice.setPerformInfo(true);
            flag = true;
        }
        if(map.containsKey("START"))
        {
            if(bGo && timeStart != timePrev && logPeriod == 0L)
                INFO_HARD("fps:" + fpsInfo());
            timeStart = com.maddox.rts.Time.real();
            frameStart = com.maddox.il2.engine.RendersMain.frame();
            fpsMin = 1000000D;
            fpsMax = 0.0D;
            fpsCur = 0.0D;
            timePrev = timeStart;
            framePrev = frameStart;
            logPrintTime = timeStart;
            bGo = true;
            flag = true;
        } else
        if(map.containsKey("STOP"))
        {
            if(bGo && timeStart != timePrev && logPeriod == 0L)
                INFO_HARD("fps:" + fpsInfo());
            bGo = false;
            flag = true;
        }
        if(!flag)
        {
            INFO_HARD("  LOG  " + logPeriod / 1000L);
            if(bShow)
                INFO_HARD("  SHOW");
            else
                INFO_HARD("  HIDE");
            if(bGo)
            {
                if(timeStart != timePrev)
                    INFO_HARD("  " + fpsInfo());
            } else
            {
                INFO_HARD("  STOPPED");
            }
        }
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    private java.lang.String fpsInfo()
    {
        return "" + (int)java.lang.Math.floor(fpsCur) + " avg:" + (int)java.lang.Math.floor((1000D * (double)(framePrev - frameStart)) / (double)(timePrev - timeStart)) + " max:" + (int)java.lang.Math.floor(fpsMax) + " min:" + (int)java.lang.Math.floor(fpsMin) + " #fr:" + (framePrev - frameStart);
    }

    private void checkMsg()
    {
        if(msg == null)
        {
            msg = new MsgTimeOut();
            msg.setListener(this);
            msg.setNotCleanAfterSend();
            msg.setFlags(72);
        }
        if(!msg.busy())
            msg.post();
    }

    public CmdFPS()
    {
        bGo = false;
        bShow = false;
        logPeriod = 5000L;
        logPrintTime = -1L;
        param.put("LOG", null);
        param.put("START", null);
        param.put("STOP", null);
        param.put("SHOW", null);
        param.put("HIDE", null);
        param.put("PERF", null);
        _properties.put("NAME", "fps");
        _levelAccess = 1;
    }

    private boolean bGo;
    private boolean bShow;
    private long timeStart;
    private int frameStart;
    private double fpsMin;
    private double fpsMax;
    private double fpsCur;
    private long timePrev;
    private int framePrev;
    private long logPeriod;
    private long logPrintTime;
    public static final java.lang.String LOG = "LOG";
    public static final java.lang.String START = "START";
    public static final java.lang.String STOP = "STOP";
    public static final java.lang.String SHOW = "SHOW";
    public static final java.lang.String HIDE = "HIDE";
    public static final java.lang.String PERF = "PERF";
    private com.maddox.rts.MsgTimeOut msg;
}

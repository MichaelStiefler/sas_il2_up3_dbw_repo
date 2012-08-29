// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MainWin3D.java

package com.maddox.il2.game;

import com.maddox.il2.ai.EventLog;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.ConsoleGL0;
import com.maddox.il2.engine.RendersMain;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.opengl.GLContext;
import com.maddox.rts.BackgroundLoop;
import com.maddox.rts.BackgroundTask;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Console;
import com.maddox.rts.IniFile;
import com.maddox.rts.MainWin32;
import com.maddox.rts.MainWindow;
import com.maddox.rts.RTSConf;
import com.maddox.rts.RTSConfWin;
import com.maddox.rts.ScreenMode;
import com.maddox.rts.Time;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.game:
//            Main3D, Main

public class MainWin3D extends com.maddox.il2.game.Main3D
{
    private class Background extends com.maddox.rts.BackgroundLoop
    {

        protected void step()
        {
            checkFocus();
            com.maddox.rts.RTSConf.cur.loopMsgs();
            try
            {
                java.lang.Thread.sleep(1L);
            }
            catch(java.lang.Exception exception) { }
            com.maddox.rts.RTSConf.cur.loopMsgs();
        }

        public Background()
        {
            setThisAsCurrent();
        }
    }

    class ConsoleExec extends com.maddox.rts.Console.Exec
    {

        public void doExec(java.lang.String s)
        {
            com.maddox.rts.RTSConf.cur.console.getEnv().exec(s);
            if(consoleServer != null)
                consoleServer.typeNum();
        }

        public java.lang.String getPrompt()
        {
            return com.maddox.rts.RTSConf.cur.console._getPrompt();
        }

        ConsoleExec()
        {
        }
    }


    public MainWin3D()
    {
        bChangedScreenMode = false;
        bTryChangedScreenMode = false;
        bChangeTimerPause = false;
    }

    public static com.maddox.il2.game.MainWin3D curWin3D()
    {
        return (com.maddox.il2.game.MainWin3D)com.maddox.il2.game.MainWin3D.cur();
    }

    private void checkFocus()
    {
        if(bDrawIfNotFocused || ((com.maddox.rts.MainWin32)com.maddox.rts.RTSConf.cur.mainWindow).IsFocused())
        {
            if(!com.maddox.il2.engine.RendersMain.isShow())
            {
                if(com.maddox.rts.Time.isEnableChangePause() && bChangeTimerPause)
                {
                    com.maddox.rts.Time.setPause(false);
                    bChangeTimerPause = false;
                }
                if(bChangedScreenMode)
                {
                    ((com.maddox.rts.MainWin32)com.maddox.rts.MainWindow.adapter()).loopMsgs();
                    com.maddox.rts.MainWindow.adapter().setMessagesEnable(false);
                    com.maddox.rts.ScreenMode.set(saveMode);
                    if(com.maddox.rts.MainWindow.adapter().isIconic())
                        com.maddox.rts.MainWindow.adapter().showNormal();
                    ((com.maddox.rts.MainWin32)com.maddox.rts.MainWindow.adapter()).loopMsgs();
                    com.maddox.rts.MainWindow.adapter().setFocus();
                    ((com.maddox.rts.MainWin32)com.maddox.rts.MainWindow.adapter()).loopMsgs();
                    com.maddox.rts.ScreenMode screenmode = com.maddox.rts.ScreenMode.readCurrent();
                    boolean flag = saveMode.width() != screenmode.width() || saveMode.height() != screenmode.height();
                    if(flag)
                        com.maddox.rts.MainWindow.adapter().setMessagesEnable(true);
                    com.maddox.rts.MainWindow.adapter().setPosSize(0, 0, screenmode.width(), screenmode.height());
                    ((com.maddox.rts.MainWin32)com.maddox.rts.MainWindow.adapter()).loopMsgs();
                    if(!flag)
                        com.maddox.rts.MainWindow.adapter().setMessagesEnable(true);
                    com.maddox.rts.RTSConf.cur.setUseMouse(saveMouseMode);
                    bChangedScreenMode = false;
                }
                com.maddox.il2.engine.RendersMain.setShow(true);
                com.maddox.il2.engine.RendersMain.bSwapBuffersResult = true;
                bTryChangedScreenMode = false;
            } else
            if(!com.maddox.il2.engine.RendersMain.bSwapBuffersResult && !com.maddox.rts.RTSConf.isRequestExitApp() && com.maddox.il2.engine.Config.cur.windowChangeScreenRes && com.maddox.il2.engine.Config.cur.windowFullScreen)
                if(bTryChangedScreenMode)
                {
                    com.maddox.il2.game.Main.doGameExit();
                } else
                {
                    bTryChangedScreenMode = true;
                    com.maddox.rts.CmdEnv.top().exec("window " + com.maddox.il2.engine.Config.cur.windowWidth + " " + com.maddox.il2.engine.Config.cur.windowHeight + " " + com.maddox.il2.engine.Config.cur.windowColourBits + " " + com.maddox.il2.engine.Config.cur.windowDepthBits + " " + com.maddox.il2.engine.Config.cur.windowStencilBits + " PROVIDER " + com.maddox.il2.engine.Config.cur.glLib + " FULL");
                    com.maddox.il2.engine.RendersMain.bSwapBuffersResult = true;
                }
        } else
        if(com.maddox.il2.engine.RendersMain.isShow())
        {
            if(com.maddox.rts.Time.isEnableChangePause() && !com.maddox.rts.Time.isPaused())
            {
                com.maddox.rts.Time.setPause(true);
                bChangeTimerPause = true;
            }
            com.maddox.il2.engine.RendersMain.setShow(false);
            if(!bChangedScreenMode && com.maddox.il2.engine.Config.cur.windowChangeScreenRes)
            {
                saveMouseMode = com.maddox.rts.RTSConf.cur.getUseMouse();
                com.maddox.rts.RTSConf.cur.setUseMouse(0);
                saveMode = com.maddox.rts.ScreenMode.readCurrent();
                if(!com.maddox.rts.MainWindow.adapter().isIconic())
                    com.maddox.rts.MainWindow.adapter().showIconic();
                com.maddox.rts.ScreenMode.restore();
                bChangedScreenMode = true;
            }
            com.maddox.il2.engine.RendersMain.bSwapBuffersResult = true;
            bTryChangedScreenMode = false;
        }
    }

    public void loopApp()
    {
        if(bUseStartLog)
            com.maddox.il2.engine.ConsoleGL0.exclusiveDraw(false);
        while(!com.maddox.rts.RTSConf.isRequestExitApp()) 
            synchronized(com.maddox.rts.RTSConf.lockObject())
            {
                if(com.maddox.rts.BackgroundTask.isExecuted())
                {
                    com.maddox.rts.BackgroundTask.doRun();
                } else
                {
                    checkFocus();
                    boolean flag;
                    java.lang.String s;
                    synchronized(oCommandSync)
                    {
                        flag = bCommand;
                        s = sCommand;
                        bCommand = false;
                    }
                    if(flag)
                    {
                        if(consoleServer != null)
                            consoleServer.bEnableType = false;
                        java.lang.System.out.println(com.maddox.rts.RTSConf.cur.console._getPrompt() + s);
                        if(consoleServer != null)
                            consoleServer.bEnableType = true;
                        com.maddox.rts.RTSConf.cur.console.getEnv().exec(s);
                        if(consoleServer != null)
                            consoleServer.typeNum();
                    }
                    com.maddox.rts.RTSConf.cur.loopMsgs();
                }
            }
    }

    public void endApp()
    {
        if(com.maddox.il2.engine.Config.cur != null)
        {
            viewSet_Save();
            com.maddox.il2.engine.Config.cur.save();
        }
        if(com.maddox.il2.engine.Config.cur != null)
            com.maddox.il2.engine.Config.cur.endSound();
        com.maddox.il2.objects.effects.ForceFeedback.stop();
        com.maddox.opengl.GLContext glcontext = com.maddox.il2.engine.RendersMain.glContext();
        if(com.maddox.opengl.GLContext.isValid(glcontext))
            glcontext.destroy();
        if(com.maddox.rts.ScreenMode.current() != com.maddox.rts.ScreenMode.startup())
            com.maddox.rts.ScreenMode.restore();
        if(com.maddox.rts.RTSConf.cur != null)
        {
            com.maddox.rts.RTSConf.cur.stop();
            if(com.maddox.rts.RTSConf.cur.mainWindow.isCreated() && (com.maddox.rts.RTSConf.cur instanceof com.maddox.rts.RTSConfWin))
                ((com.maddox.rts.MainWin32)com.maddox.rts.RTSConf.cur.mainWindow).destroy();
        }
        com.maddox.il2.ai.EventLog.close();
    }

    public boolean beginApp(java.lang.String s, java.lang.String s1, int i)
    {
        com.maddox.rts.IniFile inifile = new IniFile(s);
        com.maddox.rts.RTSConf.cur = new RTSConfWin(inifile, "rts", i);
        com.maddox.rts.RTSConf.cur.console.exec = new ConsoleExec();
        com.maddox.il2.engine.Config.cur = new Config(inifile, true);
        new Background();
        if("RU".equals(com.maddox.il2.engine.Config.LOCALE))
            com.maddox.rts.MainWin32.GetAppPath();
        if(!super.beginApp(s, s1, i))
        {
            return false;
        } else
        {
            com.maddox.il2.objects.effects.ForceFeedback.start();
            return true;
        }
    }

    private com.maddox.rts.ScreenMode saveMode;
    private int saveMouseMode;
    private boolean bChangedScreenMode;
    private boolean bTryChangedScreenMode;
    private boolean bChangeTimerPause;

}

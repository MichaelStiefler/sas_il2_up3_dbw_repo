// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RTSConfWin.java

package com.maddox.rts;

import java.io.PrintStream;

// Referenced classes of package com.maddox.rts:
//            RTSConf, MainWin32, MouseDX, MouseWin, 
//            KeyboardDX, KeyboardWin, JoyDX, JoyWin, 
//            TrackIRWin, Mouse, MainWindow, Joy, 
//            Keyboard, TrackIR, HotKeyEnvs, IniFile

public class RTSConfWin extends com.maddox.rts.RTSConf
{

    private int doUseMouse(int i)
    {
        mouse.setMouseCursorAdapter(null);
        if(i == 0)
        {
            mouseDX.destroy();
            mouseWin.destroy();
            mouse.setComputePos(false, false);
        } else
        if(i == 1)
        {
            if(mainWindow.hWnd() != 0)
            {
                mouseDX.destroy();
                mouseWin.create();
                mouse.setComputePos(false, true);
                mouse.setMouseCursorAdapter(mouseWin);
            }
        } else
        if(mainWindow.hWnd() != 0)
        {
            com.maddox.rts.MouseDX _tmp = mouseDX;
            mouseDX.create(2);
            mouseWin.destroy();
            mouse.setComputePos(true, false);
        }
        mouse._clear();
        return i;
    }

    public void setUseMouse(int i)
    {
        if(useMouse == i)
        {
            return;
        } else
        {
            super.setUseMouse(doUseMouse(i));
            return;
        }
    }

    private boolean doUseJoy(boolean flag)
    {
        if(flag)
        {
            if(mainWindow.hWnd() != 0)
                try
                {
                    joyDX.create();
                }
                catch(java.lang.Exception exception)
                {
                    java.lang.System.out.println("DirectX Joystick NOT created: " + exception.getMessage());
                    try
                    {
                        joyWin.create();
                    }
                    catch(java.lang.Exception exception1) { }
                }
            joy._clear();
            return true;
        } else
        {
            joyDX.destroy();
            joyWin.destroy();
            joy._clear();
            return false;
        }
    }

    public void useJoy(boolean flag)
    {
        if(bUseJoy == flag)
        {
            return;
        } else
        {
            super.useJoy(doUseJoy(flag));
            return;
        }
    }

    public void start()
    {
        if(!bStarted)
        {
            useMouse = doUseMouse(useMouse);
            if(mainWindow.hWnd() != 0)
            {
                keyboardDX.create();
                keyboardWin.create();
            }
            keyboard._clear();
            bUseJoy = doUseJoy(bUseJoy);
            if(bUseTrackIR)
            {
                trackIRWin.create();
                trackIR.setExist(trackIRWin.isCreated());
            }
            cur.hotKeyEnvs.resetGameCreate();
            super.start();
        }
    }

    public void stop()
    {
        if(bStarted)
        {
            doUseMouse(0);
            keyboardDX.destroy();
            keyboardWin.destroy();
            keyboard._clear();
            doUseJoy(false);
            if(bUseTrackIR)
            {
                trackIRWin.destroy();
                trackIR.setExist(false);
            }
            cur.hotKeyEnvs.resetGameClear();
            super.stop();
        }
    }

    public RTSConfWin()
    {
        super(null);
        mainWindow = new MainWin32(-10005, true);
        mouseDX = new MouseDX(-10004, 2, false);
        mouseWin = new MouseWin(-10003, false);
        keyboardDX = new KeyboardDX(-10002, 1, false);
        keyboardWin = new KeyboardWin(-10001, false);
        keyboardWin.setOnlyChars(true);
        joyDX = new JoyDX(100L, 3, false);
        joyWin = new JoyWin(100L, false);
        trackIRWin = new TrackIRWin(-10010, false);
    }

    public RTSConfWin(com.maddox.rts.IniFile inifile, java.lang.String s, int i)
    {
        super(null, inifile, s, i);
        mainWindow = new MainWin32(-10005, true);
        useMouse = inifile.get(s, "mouseUse", useMouse, 0, 2);
        mouseDX = new MouseDX(-10004, 2, false);
        mouseWin = new MouseWin(-10003, false);
        keyboardDX = new KeyboardDX(-10002, 1, false);
        keyboardWin = new KeyboardWin(-10001, false);
        keyboardWin.setOnlyChars(true);
        bUseJoy = inifile.get(s, "joyUse", bUseJoy);
        joyDX = new JoyDX(100L, 3, false);
        joyWin = new JoyWin(100L, false);
        bUseTrackIR = inifile.get(s, "trackIRUse", bUseTrackIR);
        trackIRWin = new TrackIRWin(-10010, false);
    }

    public com.maddox.rts.MouseDX mouseDX;
    public com.maddox.rts.MouseWin mouseWin;
    public com.maddox.rts.KeyboardDX keyboardDX;
    public com.maddox.rts.KeyboardWin keyboardWin;
    public com.maddox.rts.JoyDX joyDX;
    public com.maddox.rts.JoyWin joyWin;
    public com.maddox.rts.TrackIRWin trackIRWin;
}

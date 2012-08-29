// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MainWin32.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            MainWindow, MsgTimeOut, MsgTimeOutListener, RTSConf, 
//            MessageQueue, RTS

public class MainWin32 extends com.maddox.rts.MainWindow
    implements com.maddox.rts.MsgTimeOutListener
{

    public boolean isLoop()
    {
        return bLoop;
    }

    public void loop(boolean flag)
    {
        if(flag != bLoop)
            if(flag)
            {
                ticker.post();
                bLoop = true;
            } else
            {
                com.maddox.rts.RTSConf.cur.queueRealTime.remove(ticker);
                com.maddox.rts.RTSConf.cur.queueRealTimeNextTick.remove(ticker);
                bLoop = false;
            }
    }

    public void msgTimeOut(java.lang.Object obj)
    {
        if(bLoop)
        {
            ticker.post();
            loopMsgs();
        }
    }

    public void loopMsgs()
    {
        int i = LoopMsgs();
        if(i != 0)
        {
            if((i & 2) != 0 && hWnd != 0)
            {
                hWnd = 0;
                bCreated = false;
                SendAction(2);
            }
            if((i & 4) != 0)
            {
                cx = Width();
                cy = Height();
                cxFull = WidthFull();
                cyFull = HeightFull();
                SendAction(4);
            }
            if((i & 8) != 0)
            {
                posX = PosX();
                posY = PosY();
                SendAction(8);
            }
            if((i & 0x10) != 0)
            {
                bFocused = IsFocused();
                SendAction(16);
            }
        }
    }

    public boolean create(java.lang.String s, int i, int j)
    {
        if(hWnd != 0)
            destroy();
        hWnd = Create(s, i, j);
        if(hWnd == 0)
        {
            return false;
        } else
        {
            bCreated = true;
            bFullScreen = true;
            cx = Width();
            cy = Height();
            cxFull = WidthFull();
            cyFull = HeightFull();
            posX = PosX();
            posY = PosY();
            bFocused = IsFocused();
            SendAction(1);
            return true;
        }
    }

    public boolean create(java.lang.String s, boolean flag, boolean flag1, int i, int j)
    {
        if(hWnd != 0)
            destroy();
        hWnd = Create(s, flag, flag1, i, j);
        if(hWnd == 0)
        {
            return false;
        } else
        {
            bCreated = true;
            bFullScreen = false;
            cx = Width();
            cy = Height();
            cxFull = WidthFull();
            cyFull = HeightFull();
            posX = PosX();
            posY = PosY();
            bFocused = IsFocused();
            SendAction(1);
            return true;
        }
    }

    public void destroy()
    {
        if(hWnd == 0)
        {
            return;
        } else
        {
            Destroy();
            hWnd = 0;
            bCreated = false;
            SendAction(2);
            return;
        }
    }

    public void setTitle(java.lang.String s)
    {
        if(hWnd == 0)
        {
            return;
        } else
        {
            SetTitle(s);
            return;
        }
    }

    public native void setFocus();

    public native void setSize(int i, int j);

    public native void setPosSize(int i, int j, int k, int l);

    public native void SetTitle(java.lang.String s);

    public boolean isIconic()
    {
        return IsIconic();
    }

    public void showIconic()
    {
        ShowIconic();
    }

    public void showNormal()
    {
        ShowNormal();
    }

    private native int Create(java.lang.String s, int i, int j);

    private native int Create(java.lang.String s, boolean flag, boolean flag1, int i, int j);

    private native void Destroy();

    private native int LoopMsgs();

    public native int Width();

    public native int Height();

    private native int WidthFull();

    private native int HeightFull();

    private native int PosX();

    private native int PosY();

    public native boolean IsFocused();

    private native boolean IsIconic();

    private native void ShowIconic();

    private native void ShowNormal();

    public static native java.lang.String RegistryGetAppPath(java.lang.String s);

    public static native java.lang.String RegistryGetStringLM(java.lang.String s, java.lang.String s1);

    public static native java.lang.String GetAppPath();

    public static native java.lang.String GetCDDrive(java.lang.String s);

    public static native void SetAppPath(java.lang.String s);

    public static native void ImmDisableIME();

    protected MainWin32(int i, boolean flag)
    {
        bLoop = false;
        ticker = new MsgTimeOut(null);
        ticker.setTickPos(i);
        ticker.setNotCleanAfterSend();
        ticker.setFlags(88);
        ticker.setListener(this);
        loop(flag);
    }

    private com.maddox.rts.MsgTimeOut ticker;
    private boolean bLoop;

    static 
    {
        com.maddox.rts.RTS.loadNative();
    }
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MouseDX.java

package com.maddox.rts;

import com.maddox.il2.engine.Config;

// Referenced classes of package com.maddox.rts:
//            MouseDXException, MsgTimeOut, MsgTimeOutListener, RTSConf, 
//            MainWindow, MessageQueue, Time, Mouse, 
//            RTS

public final class MouseDX
    implements com.maddox.rts.MsgTimeOutListener
{

    public final boolean isCreated()
    {
        return bCreated;
    }

    public final void create(int i)
        throws com.maddox.rts.MouseDXException
    {
        if(bCreated)
        {
            setCooperativeLevel(i);
            return;
        }
        com.maddox.rts.MouseDX.checkCoopLevel(i);
        if(com.maddox.rts.RTSConf.cur.mainWindow.hWnd() == 0)
        {
            throw new MouseDXException("DirectX mouse driver: window not present");
        } else
        {
            com.maddox.rts.MouseDX.nCreate(i);
            level = i;
            ticker.post();
            bCreated = true;
            return;
        }
    }

    public final void create()
        throws com.maddox.rts.MouseDXException
    {
        create(level);
    }

    public final int cooperativeLevel()
    {
        return level;
    }

    public final void setCooperativeLevel(int i)
        throws com.maddox.rts.MouseDXException
    {
        com.maddox.rts.MouseDX.checkCoopLevel(i);
        com.maddox.rts.MouseDX.nSetCoopLevel(i);
        level = i;
    }

    public final void destroy()
    {
        if(bCreated)
        {
            com.maddox.rts.MouseDX.nDestroy();
            com.maddox.rts.RTSConf.cur.queueRealTime.remove(ticker);
            com.maddox.rts.RTSConf.cur.queueRealTimeNextTick.remove(ticker);
            bCreated = false;
        }
    }

    public void msgTimeOut(java.lang.Object obj)
    {
        if(bCreated)
        {
            if(bR_RU)
                param[0] = param[1] = 0;
            while(com.maddox.rts.MouseDX.nGetMsg(param)) 
            {
                long l = com.maddox.rts.Time.realFromRawClamp(param[2]);
                switch(param[0])
                {
                case 0: // '\0'
                case 1: // '\001'
                case 2: // '\002'
                case 3: // '\003'
                    if(param[1] == 1)
                        com.maddox.rts.RTSConf.cur.mouse.setPress(l, param[0] - 0);
                    else
                        com.maddox.rts.RTSConf.cur.mouse.setRelease(l, param[0] - 0);
                    break;

                case 4: // '\004'
                    com.maddox.rts.RTSConf.cur.mouse.setMove(l, param[1], 0, 0);
                    break;

                case 5: // '\005'
                    com.maddox.rts.RTSConf.cur.mouse.setMove(l, 0, -param[1], 0);
                    break;

                case 6: // '\006'
                    com.maddox.rts.RTSConf.cur.mouse.setMove(l, 0, 0, param[1]);
                    break;
                }
                if(bR_RU)
                    param[0] = param[1] = 0;
            }
            com.maddox.rts.RTSConf.cur.mouse.flushMove();
            ticker.post();
        }
    }

    private static final void checkCoopLevel(int i)
        throws com.maddox.rts.MouseDXException
    {
        if(i < 0 || i > 2)
            throw new MouseDXException("DirectX mouse driver: unknown cooperative level = " + i);
        else
            return;
    }

    protected MouseDX(int i, int j, boolean flag)
    {
        param = new int[3];
        bCreated = false;
        com.maddox.rts.MouseDX.checkCoopLevel(j);
        level = j;
        ticker = new MsgTimeOut(null);
        ticker.setTickPos(i);
        ticker.setNotCleanAfterSend();
        ticker.setFlags(88);
        ticker.setListener(this);
        if(flag)
            create();
    }

    private static final native void nCreate(int i)
        throws com.maddox.rts.MouseDXException;

    private static final native void nDestroy();

    private static final native void nSetCoopLevel(int i);

    private static final native boolean nGetMsg(int ai[]);

    public static final int NONEXCLUSIVE_BACKGROUND = 0;
    public static final int NONEXCLUSIVE_FOREGROUND = 1;
    public static final int EXCLUSIVE_FOREGROUND = 2;
    private static final int BUTTON0 = 0;
    private static final int BUTTON1 = 1;
    private static final int BUTTON2 = 2;
    private static final int BUTTON3 = 3;
    private static final int MOVE_X = 4;
    private static final int MOVE_Y = 5;
    private static final int MOVE_Z = 6;
    private static final boolean bR_I18N = false;
    private static boolean bR_RU;
    private boolean bCreated;
    private int level;
    private int param[];
    private com.maddox.rts.MsgTimeOut ticker;

    static 
    {
        bR_RU = "RU".equals(com.maddox.il2.engine.Config.LOCALE);
        com.maddox.rts.RTS.loadNative();
    }
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   JoyDX.java

package com.maddox.rts;

import com.maddox.il2.engine.Config;

// Referenced classes of package com.maddox.rts:
//            JoyDXException, MsgTimeOut, MsgTimeOutListener, RTSConf, 
//            MainWindow, Time, Joy, MessageQueue, 
//            RTS

public final class JoyDX
    implements com.maddox.rts.MsgTimeOutListener
{

    public final boolean isCreated()
    {
        return bCreated;
    }

    public final void create(int i)
        throws com.maddox.rts.JoyDXException
    {
        if(bCreated)
        {
            setCooperativeLevel(i);
            return;
        }
        com.maddox.rts.JoyDX.checkCoopLevel(i);
        if(com.maddox.rts.RTSConf.cur.mainWindow.hWnd() == 0)
            throw new JoyDXException("DirectX joystick driver: window not present");
        amount = com.maddox.rts.JoyDX.nCreate(i);
        level = i;
        ticker.setTime(com.maddox.rts.Time.currentReal() + timePool);
        ticker.post();
        bCreated = true;
        com.maddox.rts.RTSConf.cur.joy.setAttached(true);
        com.maddox.rts.RTSConf.cur.joy.setAmount(amount);
        int ai[] = new int[4];
        for(int j = 0; j < amount; j++)
        {
            com.maddox.rts.JoyDX.nCaps(j, ai);
            com.maddox.rts.RTSConf.cur.joy.setCaps(j, ai[0], ai[1], ai[2], ai[3]);
        }

    }

    public final void create()
        throws com.maddox.rts.JoyDXException
    {
        create(level);
    }

    public final int cooperativeLevel()
    {
        return level;
    }

    public final void setCooperativeLevel(int i)
        throws com.maddox.rts.JoyDXException
    {
        com.maddox.rts.JoyDX.checkCoopLevel(i);
        com.maddox.rts.JoyDX.nSetCoopLevel(i);
        level = i;
    }

    public final void destroy()
    {
        if(bCreated)
        {
            com.maddox.rts.JoyDX.nDestroy();
            com.maddox.rts.RTSConf.cur.queueRealTime.remove(ticker);
            com.maddox.rts.RTSConf.cur.queueRealTimeNextTick.remove(ticker);
            bCreated = false;
            amount = 0;
            com.maddox.rts.RTSConf.cur.joy.setAttached(false);
        }
    }

    public void msgTimeOut(java.lang.Object obj)
    {
        if(bCreated)
        {
            long l = com.maddox.rts.Time.currentReal();
            for(int i = 0; i < amount; i++)
            {
                if(bR_RU)
                    param[0] = param[1] = 0;
                while(com.maddox.rts.JoyDX.nGetMsg(i, param)) 
                {
                    if(param[0] >= 0 && param[0] <= 31)
                    {
                        if(param[1] == 1)
                            com.maddox.rts.RTSConf.cur.joy.setPress(l, i, param[0] - 0);
                        else
                            com.maddox.rts.RTSConf.cur.joy.setRelease(l, i, param[0] - 0);
                    } else
                    {
                        if(bR_RU)
                            param[0] -= 6;
                        switch(param[0])
                        {
                        case 32: // ' '
                            com.maddox.rts.RTSConf.cur.joy.setMove(l, i, 0, param[1]);
                            break;

                        case 33: // '!'
                            com.maddox.rts.RTSConf.cur.joy.setMove(l, i, 1, param[1]);
                            break;

                        case 34: // '"'
                            com.maddox.rts.RTSConf.cur.joy.setMove(l, i, 2, param[1]);
                            break;

                        case 35: // '#'
                            com.maddox.rts.RTSConf.cur.joy.setMove(l, i, 3, param[1]);
                            break;

                        case 36: // '$'
                            com.maddox.rts.RTSConf.cur.joy.setMove(l, i, 4, param[1]);
                            break;

                        case 37: // '%'
                            com.maddox.rts.RTSConf.cur.joy.setMove(l, i, 5, param[1]);
                            break;

                        case 38: // '&'
                            com.maddox.rts.RTSConf.cur.joy.setMove(l, i, 6, param[1]);
                            break;

                        case 39: // '\''
                            com.maddox.rts.RTSConf.cur.joy.setMove(l, i, 7, param[1]);
                            break;

                        case 40: // '('
                            com.maddox.rts.RTSConf.cur.joy.setPov(l, i, 0, param[1]);
                            break;

                        case 41: // ')'
                            com.maddox.rts.RTSConf.cur.joy.setPov(l, i, 1, param[1]);
                            break;

                        case 42: // '*'
                            com.maddox.rts.RTSConf.cur.joy.setPov(l, i, 2, param[1]);
                            break;

                        case 43: // '+'
                            com.maddox.rts.RTSConf.cur.joy.setPov(l, i, 3, param[1]);
                            break;
                        }
                    }
                    if(bR_RU)
                        param[0] = param[1] = 0;
                }
            }

            com.maddox.rts.RTSConf.cur.joy.poll(l);
            ticker.setTime(com.maddox.rts.Time.currentReal() + timePool);
            ticker.post();
        }
    }

    private static final void checkCoopLevel(int i)
        throws com.maddox.rts.JoyDXException
    {
        if(i < 0 || i > 3)
            throw new JoyDXException("DirectX joystick driver: unknown cooperative level = " + i);
        else
            return;
    }

    protected JoyDX(long l, int i, boolean flag)
    {
        timePool = 100L;
        param = new int[3];
        bCreated = false;
        amount = 0;
        com.maddox.rts.JoyDX.checkCoopLevel(i);
        level = i;
        ticker = new MsgTimeOut(null);
        timePool = l;
        ticker.setTime(com.maddox.rts.Time.currentReal() + l);
        ticker.setNotCleanAfterSend();
        ticker.setFlags(64);
        ticker.setListener(this);
        if(flag)
            create();
    }

    public static final native void doControlPanel();

    private static final native int nCreate(int i)
        throws com.maddox.rts.JoyDXException;

    private static final native void nDestroy();

    private static final native void nSetCoopLevel(int i);

    private static final native boolean nGetMsg(int i, int ai[]);

    private static final native boolean nCaps(int i, int ai[]);

    public static final int NONEXCLUSIVE_BACKGROUND = 0;
    public static final int NONEXCLUSIVE_FOREGROUND = 1;
    public static final int EXCLUSIVE_BACKGROUND = 2;
    public static final int EXCLUSIVE_FOREGROUND = 3;
    private static final int BUTTON0 = 0;
    private static final int BUTTON31 = 31;
    private static final int MOVE_X = 32;
    private static final int MOVE_Y = 33;
    private static final int MOVE_Z = 34;
    private static final int MOVE_RX = 35;
    private static final int MOVE_RY = 36;
    private static final int MOVE_RZ = 37;
    private static final int MOVE_U = 38;
    private static final int MOVE_V = 39;
    private static final int POV0 = 40;
    private static final int POV1 = 41;
    private static final int POV2 = 42;
    private static final int POV3 = 43;
    private static final boolean bR_I18N = false;
    private static boolean bR_RU;
    private boolean bCreated;
    private int amount;
    private int level;
    private int param[];
    private com.maddox.rts.MsgTimeOut ticker;
    private long timePool;

    static 
    {
        bR_RU = "RU".equals(com.maddox.il2.engine.Config.LOCALE);
        com.maddox.rts.RTS.loadNative();
    }
}

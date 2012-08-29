// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   JoyWin.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            JoyWinException, MsgTimeOut, MsgTimeOutListener, RTSConf, 
//            MainWindow, Time, Joy, MessageQueue, 
//            RTS

public final class JoyWin
    implements com.maddox.rts.MsgTimeOutListener
{

    public final boolean isCreated()
    {
        return bCreated;
    }

    public final void create()
        throws com.maddox.rts.JoyWinException
    {
        if(com.maddox.rts.RTSConf.cur.mainWindow.hWnd() == 0)
            throw new JoyWinException("Windows joystick driver: window not present");
        amount = com.maddox.rts.JoyWin.nCreate();
        if(amount == 0)
            throw new JoyWinException("Windows joystick not found");
        ticker.setTime(com.maddox.rts.Time.currentReal() + timePool);
        ticker.post();
        bCreated = true;
        com.maddox.rts.RTSConf.cur.joy.setAttached(true);
        for(int i = 0; i < amount; i++)
            com.maddox.rts.RTSConf.cur.joy.setCaps(i, 4, 2, 0, 3);

    }

    public final void destroy()
    {
        if(bCreated)
        {
            com.maddox.rts.RTSConf.cur.queueRealTime.remove(ticker);
            com.maddox.rts.RTSConf.cur.queueRealTimeNextTick.remove(ticker);
            bCreated = false;
            amount = 0;
            com.maddox.rts.RTSConf.cur.joy.setAttached(false);
        }
    }

    private void checkButton(long l, int i, int j, boolean flag)
    {
        if(bButtons[i][j] != flag)
        {
            bButtons[i][j] = flag;
            if(flag)
                com.maddox.rts.RTSConf.cur.joy.setPress(l, i, j);
            else
                com.maddox.rts.RTSConf.cur.joy.setRelease(l, i, j);
        }
    }

    public void msgTimeOut(java.lang.Object obj)
    {
        if(bCreated)
        {
            long l = com.maddox.rts.Time.currentReal();
            for(int i = 0; i < amount; i++)
            {
                if(!com.maddox.rts.JoyWin.nGetMsg(i, param))
                    continue;
                checkButton(l, i, 0, (param[0] & 1) != 0);
                checkButton(l, i, 1, (param[0] & 2) != 0);
                checkButton(l, i, 2, (param[0] & 4) != 0);
                checkButton(l, i, 3, (param[0] & 8) != 0);
                if((param[0] & 0x10) != 0)
                    com.maddox.rts.RTSConf.cur.joy.setMove(l, i, 0, param[1]);
                if((param[0] & 0x20) != 0)
                    com.maddox.rts.RTSConf.cur.joy.setMove(l, i, 1, param[2]);
            }

            com.maddox.rts.RTSConf.cur.joy.poll(l);
            ticker.setTime(com.maddox.rts.Time.currentReal() + timePool);
            ticker.post();
        }
    }

    protected JoyWin(long l, boolean flag)
    {
        timePool = 100L;
        param = new int[3];
        bCreated = false;
        amount = 0;
        ticker = new MsgTimeOut(null);
        timePool = l;
        ticker.setTime(com.maddox.rts.Time.currentReal() + l);
        ticker.setNotCleanAfterSend();
        ticker.setFlags(64);
        ticker.setListener(this);
        if(flag)
            create();
    }

    private static final native int nCreate();

    private static final native boolean nGetMsg(int i, int ai[]);

    private static final int BUTTON0 = 1;
    private static final int BUTTON1 = 2;
    private static final int BUTTON2 = 4;
    private static final int BUTTON3 = 8;
    private static final int MOVE_X = 16;
    private static final int MOVE_Y = 32;
    private static boolean bButtons[][] = new boolean[2][4];
    private boolean bCreated;
    private int amount;
    private int param[];
    private com.maddox.rts.MsgTimeOut ticker;
    private long timePool;

    static 
    {
        com.maddox.rts.RTS.loadNative();
    }
}

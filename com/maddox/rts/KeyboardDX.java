// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   KeyboardDX.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            KeyboardDXException, MsgTimeOut, MsgTimeOutListener, RTSConf, 
//            MainWindow, MessageQueue, Time, Keyboard, 
//            RTS

public final class KeyboardDX
    implements com.maddox.rts.MsgTimeOutListener
{

    public final boolean isCreated()
    {
        return bCreated;
    }

    public final void create(int i)
        throws com.maddox.rts.KeyboardDXException
    {
        if(bCreated)
        {
            setCooperativeLevel(i);
            return;
        }
        com.maddox.rts.KeyboardDX.checkCoopLevel(i);
        if(com.maddox.rts.RTSConf.cur.mainWindow.hWnd() == 0)
        {
            throw new KeyboardDXException("Keyboard DirectX driver: main window not present");
        } else
        {
            com.maddox.rts.KeyboardDX.nCreate(i);
            level = i;
            ticker.post();
            bCreated = true;
            return;
        }
    }

    public final void create()
        throws com.maddox.rts.KeyboardDXException
    {
        create(level);
    }

    public final void setCooperativeLevel(int i)
        throws com.maddox.rts.KeyboardDXException
    {
        com.maddox.rts.KeyboardDX.checkCoopLevel(i);
        if(bCreated)
            com.maddox.rts.KeyboardDX.nSetCoopLevel(i);
        level = i;
    }

    public final void destroy()
    {
        if(bCreated)
        {
            com.maddox.rts.KeyboardDX.nDestroy();
            com.maddox.rts.RTSConf.cur.queueRealTime.remove(ticker);
            com.maddox.rts.RTSConf.cur.queueRealTimeNextTick.remove(ticker);
            bCreated = false;
        }
    }

    public void msgTimeOut(java.lang.Object obj)
    {
        if(bCreated)
        {
            while(com.maddox.rts.KeyboardDX.nGetMsg(param)) 
            {
                long l = com.maddox.rts.Time.realFromRawClamp(param[2]);
                switch(param[0])
                {
                case 0: // '\0'
                    com.maddox.rts.RTSConf.cur.keyboard.setPress(l, param[1]);
                    break;

                case 1: // '\001'
                    com.maddox.rts.RTSConf.cur.keyboard.setRelease(l, param[1]);
                    break;
                }
            }
            ticker.post();
        }
    }

    private static final void checkCoopLevel(int i)
        throws com.maddox.rts.KeyboardDXException
    {
        if(i < 0 || i > 1)
            throw new KeyboardDXException("Keyboard DirectX driver: unknown cooperative level = " + i);
        else
            return;
    }

    protected KeyboardDX(int i, int j, boolean flag)
    {
        param = new int[3];
        bCreated = false;
        com.maddox.rts.KeyboardDX.checkCoopLevel(j);
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
        throws com.maddox.rts.KeyboardDXException;

    private static final native void nDestroy();

    private static final native void nSetCoopLevel(int i);

    private static final native boolean nGetMsg(int ai[]);

    public static final int NONEXCLUSIVE_BACKGROUND = 0;
    public static final int NONEXCLUSIVE_FOREGROUND = 1;
    private static final int PRESS = 0;
    private static final int RELEASE = 1;
    private boolean bCreated;
    private int level;
    private int param[];
    private com.maddox.rts.MsgTimeOut ticker;

    static 
    {
        com.maddox.rts.RTS.loadNative();
    }
}

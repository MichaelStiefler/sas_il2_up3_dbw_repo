// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   KeyboardWin.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            KeyboardWinException, MsgTimeOut, MsgTimeOutListener, RTSConf, 
//            MainWindow, MessageQueue, Time, Keyboard, 
//            RTS

public final class KeyboardWin
    implements com.maddox.rts.MsgTimeOutListener
{

    public final boolean isCreated()
    {
        return bCreated;
    }

    public final boolean isOnlyChars()
    {
        return bOnlyChars;
    }

    public final void setOnlyChars(boolean flag)
    {
        bOnlyChars = flag;
    }

    public final void create()
        throws com.maddox.rts.KeyboardWinException
    {
        if(bCreated)
            return;
        if(com.maddox.rts.RTSConf.cur.mainWindow.hWnd() == 0)
        {
            throw new KeyboardWinException("Keyboard windows driver: main window not present");
        } else
        {
            com.maddox.rts.KeyboardWin.nCreate();
            ticker.post();
            bCreated = true;
            return;
        }
    }

    public final void destroy()
    {
        if(bCreated)
        {
            com.maddox.rts.KeyboardWin.nDestroy();
            com.maddox.rts.RTSConf.cur.queueRealTime.remove(ticker);
            com.maddox.rts.RTSConf.cur.queueRealTimeNextTick.remove(ticker);
            bCreated = false;
        }
    }

    public void msgTimeOut(java.lang.Object obj)
    {
        if(bCreated)
        {
            do
            {
                if(!com.maddox.rts.KeyboardWin.nGetMsg(param))
                    break;
                long l = com.maddox.rts.Time.realFromRawClamp(param[2]);
                switch(param[0])
                {
                case 0: // '\0'
                    if(!bOnlyChars)
                        com.maddox.rts.RTSConf.cur.keyboard.setPress(l, param[1]);
                    break;

                case 1: // '\001'
                    if(!bOnlyChars)
                        com.maddox.rts.RTSConf.cur.keyboard.setRelease(l, param[1]);
                    break;

                case 2: // '\002'
                    com.maddox.rts.RTSConf.cur.keyboard.setChar(l, param[1]);
                    break;
                }
            } while(true);
            ticker.post();
        }
    }

    protected KeyboardWin(int i, boolean flag)
    {
        bOnlyChars = false;
        param = new int[3];
        bCreated = false;
        ticker = new MsgTimeOut(null);
        ticker.setTickPos(i);
        ticker.setNotCleanAfterSend();
        ticker.setFlags(88);
        ticker.setListener(this);
        if(flag)
            create();
    }

    private static final native void nCreate();

    private static final native void nDestroy();

    private static final native boolean nGetMsg(int ai[]);

    private static final int PRESS = 0;
    private static final int RELEASE = 1;
    private static final int CHAR = 2;
    private boolean bOnlyChars;
    private boolean bCreated;
    private int param[];
    private com.maddox.rts.MsgTimeOut ticker;

    static 
    {
        com.maddox.rts.RTS.loadNative();
    }
}

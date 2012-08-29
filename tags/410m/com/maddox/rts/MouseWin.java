// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MouseWin.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            MouseWinException, MsgTimeOut, MsgTimeOutListener, MouseCursor, 
//            RTSConf, MainWindow, MessageQueue, Time, 
//            Mouse, RTS

public final class MouseWin
    implements com.maddox.rts.MsgTimeOutListener, com.maddox.rts.MouseCursor
{

    public final boolean isCreated()
    {
        return bCreated;
    }

    public final void create()
        throws com.maddox.rts.MouseWinException
    {
        if(bCreated)
            return;
        if(com.maddox.rts.RTSConf.cur.mainWindow.hWnd() == 0)
        {
            throw new MouseWinException("Mouse windows driver: main window not present");
        } else
        {
            com.maddox.rts.MouseWin.nCreate();
            ticker.post();
            bCreated = true;
            return;
        }
    }

    public final void destroy()
    {
        if(bCreated)
        {
            com.maddox.rts.MouseWin.nDestroy();
            com.maddox.rts.RTSConf.cur.queueRealTime.remove(ticker);
            com.maddox.rts.RTSConf.cur.queueRealTimeNextTick.remove(ticker);
            bCreated = false;
        }
    }

    public void setCursor(int i)
    {
        com.maddox.rts.MouseWin.SetCursor(i);
    }

    public void msgTimeOut(java.lang.Object obj)
    {
        long l;
        int i;
        int j;
        int k;
        if(bCreated)
            do
            {
                if(com.maddox.rts.MouseWin.nGetMsg(param))
                {
                    l = com.maddox.rts.Time.realFromRawClamp(param[1]);
                    switch(param[0])
                    {
                    case 0: // '\0'
                        com.maddox.rts.RTSConf.cur.mouse.setPress(l, param[2]);
                        break;

                    case 1: // '\001'
                        com.maddox.rts.RTSConf.cur.mouse.setRelease(l, param[2]);
                        break;

                    case 2: // '\002'
                        i = param[2];
                        j = com.maddox.rts.RTSConf.cur.mainWindow.height() - param[3] - 1;
                        k = param[4];
                        com.maddox.rts.RTSConf.cur.mouse.setAbsMove(l, i, j, k);
                        break;
                    }
                    continue;
                }
                ticker.post();
                break;
            } while(true);
    }

    protected MouseWin(int i, boolean flag)
    {
        bOnlyAbsMove = false;
        param = new int[5];
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

    private static final native void SetCursor(int i);

    private static final int PRESS = 0;
    private static final int RELEASE = 1;
    private static final int ABSMOVE = 2;
    private boolean bOnlyAbsMove;
    private boolean bCreated;
    private int param[];
    private com.maddox.rts.MsgTimeOut ticker;

    static 
    {
        com.maddox.rts.RTS.loadNative();
    }
}

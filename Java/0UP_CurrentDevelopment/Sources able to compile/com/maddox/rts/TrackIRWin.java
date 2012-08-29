package com.maddox.rts;


public final class TrackIRWin
    implements com.maddox.rts.MsgTimeOutListener
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
            throw new RuntimeException("TrackIR windows driver: main window not present");
        if(!com.maddox.rts.TrackIRWin.nCreate(ProgramID))
        {
            return;
        } else
        {
            ticker.post();
            bCreated = true;
            return;
        }
    }

    public final void destroy()
    {
        if(bCreated)
        {
            com.maddox.rts.TrackIRWin.nDestroy();
            com.maddox.rts.RTSConf.cur.queueRealTime.remove(ticker);
            com.maddox.rts.RTSConf.cur.queueRealTimeNextTick.remove(ticker);
            bCreated = false;
        }
    }

    public void msgTimeOut(java.lang.Object obj)
    {
        if(bCreated)
        {
            if(com.maddox.rts.TrackIRWin.nGetAngles(angles))
                com.maddox.rts.TrackIRWin.nGetAngles(angles);
            com.maddox.rts.RTSConf.cur.trackIR.setAngles(angles[0], angles[1], angles[2], angles[3], angles[4], angles[5]);
            ticker.post();
        }
    }

    protected TrackIRWin(int i, boolean flag)
    {
        angles = new float[7];
        bCreated = false;
        ticker = new MsgTimeOut(null);
        ticker.setTickPos(i);
        ticker.setNotCleanAfterSend();
        ticker.setFlags(88);
        ticker.setListener(this);
        if(flag)
            create();
    }

    private static final native boolean nCreate(int i);

    private static final native void nDestroy();

    private static final native boolean nGetAngles(float af[]);

    private static int ProgramID = 1001;
    private boolean bCreated;
    private static float angles[] = new float[7];
    private com.maddox.rts.MsgTimeOut ticker;

    static 
    {
        com.maddox.rts.RTS.loadNative();
    }
}

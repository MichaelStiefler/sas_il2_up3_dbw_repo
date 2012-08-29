package com.maddox.rts;


public class TrackIR
    implements com.maddox.rts.MsgAddListenerListener, com.maddox.rts.MsgRemoveListenerListener
{

    public static com.maddox.rts.TrackIR adapter()
    {
        return com.maddox.rts.RTSConf.cur.trackIR;
    }

    public boolean isExist()
    {
        return bExist;
    }

    public void getAngles(float af[])
    {
        af[0] = yaw;
        af[1] = pitch;
        af[2] = roll;
        af[3] = x;
        af[4] = y;
        af[5] = z;
    }

    public java.lang.Object[] getListeners()
    {
        return listeners.get();
    }

    public java.lang.Object[] getRealListeners()
    {
        return realListeners.get();
    }

    public void msgAddListener(java.lang.Object obj, java.lang.Object obj1)
    {
        if(com.maddox.rts.Message.current().isRealTime())
            realListeners.insListener(obj);
        else
            listeners.insListener(obj);
    }

    public void msgRemoveListener(java.lang.Object obj, java.lang.Object obj1)
    {
        if(com.maddox.rts.Message.current().isRealTime())
            realListeners.removeListener(obj);
        else
            listeners.removeListener(obj);
    }

    protected void setExist(boolean flag)
    {
        bExist = flag;
    }

    protected void clear()
    {
        yaw = pitch = roll = x = y = z = 0.0F;
    }

    protected void setAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        if(yaw == f && pitch == f1 && roll == f2 && x == f3 && y == f4 && z == f5)
            return;
        yaw = f;
        pitch = f1;
        roll = f2;
        x = f3;
        y = f4;
        z = f5;
        java.lang.Object aobj[] = realListeners.get();
        if(aobj != null)
        {
            com.maddox.rts.MsgTrackIR msgtrackir = (com.maddox.rts.MsgTrackIR)cache.get();
            msgtrackir.id = 0;
            msgtrackir.yaw = yaw;
            msgtrackir.pitch = pitch;
            msgtrackir.roll = roll;
            msgtrackir.post(64, ((java.lang.Object) (aobj)), com.maddox.rts.Time.currentReal(), this);
        }
        if(!com.maddox.rts.Time.isPaused())
        {
            java.lang.Object aobj1[] = listeners.get();
            if(aobj1 != null)
            {
                com.maddox.rts.MsgTrackIR msgtrackir1 = (com.maddox.rts.MsgTrackIR)cache.get();
                msgtrackir1.id = 0;
                msgtrackir1.yaw = yaw;
                msgtrackir1.pitch = pitch;
                msgtrackir1.roll = roll;
                msgtrackir1.post(0, ((java.lang.Object) (aobj1)), com.maddox.rts.Time.current(), this);
            }
        }
    }

    protected TrackIR(com.maddox.rts.IniFile inifile, java.lang.String s)
    {
        bExist = false;
        listeners = new Listeners();
        realListeners = new Listeners();
        cache = new MessageCache(com.maddox.rts.MsgTrackIR.class);
        clear();
    }

    public static final int ANGLES = 0;
    public static final int UNKNOWN = -1;
    private boolean bExist;
    private com.maddox.rts.Listeners listeners;
    private com.maddox.rts.Listeners realListeners;
    private com.maddox.rts.MessageCache cache;
    private float yaw;
    private float pitch;
    private float roll;
    private float x;
    private float y;
    private float z;
}

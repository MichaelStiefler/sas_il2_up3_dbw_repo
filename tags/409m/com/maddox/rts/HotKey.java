// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HotKey.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            MsgTimeOut, MsgKeyboardListener, MsgMouseListener, MsgJoyListener, 
//            MsgTrackIRListener, MsgTimeOutListener, RTSConf, MsgAddListener, 
//            HotKeyEnv, Message

public class HotKey
    implements com.maddox.rts.MsgKeyboardListener, com.maddox.rts.MsgMouseListener, com.maddox.rts.MsgJoyListener, com.maddox.rts.MsgTrackIRListener, com.maddox.rts.MsgTimeOutListener
{

    protected HotKey()
    {
        povState = new int[16];
        msgTimeOut = new MsgTimeOut();
        msgTimeOut.setListener(this);
        msgTimeOut.setTickPos(-1);
        msgTimeOut.setNotCleanAfterSend();
        msgTimeOut.setFlags(8);
        msgTimeOut.post();
        msgRealTimeOut = new MsgTimeOut();
        msgRealTimeOut.setListener(this);
        msgRealTimeOut.setNotCleanAfterSend();
        msgRealTimeOut.setFlags(72);
        msgRealTimeOut.post();
        com.maddox.rts.MsgAddListener.post(0, com.maddox.rts.RTSConf.cur.keyboard, this, null);
        com.maddox.rts.MsgAddListener.post(64, com.maddox.rts.RTSConf.cur.keyboard, this, null);
        com.maddox.rts.MsgAddListener.post(0, com.maddox.rts.RTSConf.cur.mouse, this, null);
        com.maddox.rts.MsgAddListener.post(64, com.maddox.rts.RTSConf.cur.mouse, this, null);
        com.maddox.rts.MsgAddListener.post(0, com.maddox.rts.RTSConf.cur.joy, this, null);
        com.maddox.rts.MsgAddListener.post(64, com.maddox.rts.RTSConf.cur.joy, this, null);
        com.maddox.rts.MsgAddListener.post(0, com.maddox.rts.RTSConf.cur.trackIR, this, null);
        com.maddox.rts.MsgAddListener.post(64, com.maddox.rts.RTSConf.cur.trackIR, this, null);
    }

    protected void resetGameClear()
    {
        for(int i = 0; i < povState.length; i++)
            povState[i] = 0;

    }

    protected void resetGameCreate()
    {
        if(!msgTimeOut.busy())
            msgTimeOut.post();
    }

    public void msgUserKey(boolean flag, int i, boolean flag1)
    {
        com.maddox.rts.HotKeyEnv.keyPress(flag, 601 + i & 0xffff, flag1);
    }

    public void msgJoyButton(int i, int j, boolean flag)
    {
        com.maddox.rts.HotKeyEnv.keyPress(com.maddox.rts.Message.current().isRealTime(), i, j, flag);
    }

    public void msgJoyMove(int i, int j, int k)
    {
        com.maddox.rts.HotKeyEnv.joyMove(com.maddox.rts.Message.current().isRealTime(), i, j, k);
    }

    public void msgJoyPov(int i, int j)
    {
        boolean flag = com.maddox.rts.Message.current().isRealTime();
        int k = povState[i - 584];
        if(k == j)
            return;
        if(k != 0)
        {
            povState[i - 584] = 0;
            com.maddox.rts.HotKeyEnv.keyPress(flag, i, k, false);
        }
        if(j != 571)
        {
            povState[i - 584] = j;
            com.maddox.rts.HotKeyEnv.keyPress(flag, i, j, true);
        }
    }

    public void msgJoyPoll()
    {
    }

    public void msgMouseButton(int i, boolean flag)
    {
        com.maddox.rts.HotKeyEnv.keyPress(com.maddox.rts.Message.current().isRealTime(), 524 + i, flag);
    }

    public void msgMouseMove(int i, int j, int k)
    {
        com.maddox.rts.HotKeyEnv.mouseMove(com.maddox.rts.Message.current().isRealTime(), i, j, k);
    }

    public void msgMouseAbsMove(int i, int j, int k)
    {
        com.maddox.rts.HotKeyEnv.mouseAbsMove(com.maddox.rts.Message.current().isRealTime(), i, j, k);
    }

    public void msgKeyboardKey(int i, boolean flag)
    {
        com.maddox.rts.HotKeyEnv.keyPress(com.maddox.rts.Message.current().isRealTime(), 0 + i, flag);
    }

    public void msgKeyboardChar(char c)
    {
    }

    public void msgTrackIRAngles(float f, float f1, float f2)
    {
        com.maddox.rts.HotKeyEnv.trackIRAngles(com.maddox.rts.Message.current().isRealTime(), f, f1, f2);
    }

    public void msgTimeOut(java.lang.Object obj)
    {
        if(com.maddox.rts.Message.current().isRealTime())
        {
            com.maddox.rts.HotKeyEnv.tick(true);
            msgRealTimeOut.post();
        } else
        {
            com.maddox.rts.HotKeyEnv.tick(false);
            msgTimeOut.post();
        }
    }

    private int povState[];
    private com.maddox.rts.MsgTimeOut msgTimeOut;
    private com.maddox.rts.MsgTimeOut msgRealTimeOut;
}

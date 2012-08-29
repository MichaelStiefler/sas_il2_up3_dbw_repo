package com.maddox.rts;

public class HotKey
  implements MsgKeyboardListener, MsgMouseListener, MsgJoyListener, MsgTrackIRListener, MsgTimeOutListener
{
  private int[] povState = new int[16];
  private MsgTimeOut msgTimeOut;
  private MsgTimeOut msgRealTimeOut;

  protected HotKey()
  {
    this.msgTimeOut = new MsgTimeOut();
    this.msgTimeOut.setListener(this);
    this.msgTimeOut.setTickPos(-1);
    this.msgTimeOut.setNotCleanAfterSend();
    this.msgTimeOut.setFlags(8);
    this.msgTimeOut.post();
    this.msgRealTimeOut = new MsgTimeOut();
    this.msgRealTimeOut.setListener(this);
    this.msgRealTimeOut.setNotCleanAfterSend();
    this.msgRealTimeOut.setFlags(72);
    this.msgRealTimeOut.post();
    MsgAddListener.post(0, RTSConf.cur.keyboard, this, null);
    MsgAddListener.post(64, RTSConf.cur.keyboard, this, null);
    MsgAddListener.post(0, RTSConf.cur.mouse, this, null);
    MsgAddListener.post(64, RTSConf.cur.mouse, this, null);
    MsgAddListener.post(0, RTSConf.cur.joy, this, null);
    MsgAddListener.post(64, RTSConf.cur.joy, this, null);
    MsgAddListener.post(0, RTSConf.cur.trackIR, this, null);
    MsgAddListener.post(64, RTSConf.cur.trackIR, this, null);
  }

  protected void resetGameClear() {
    for (int i = 0; i < this.povState.length; i++)
      this.povState[i] = 0; 
  }

  protected void resetGameCreate() {
    if (!this.msgTimeOut.busy())
      this.msgTimeOut.post();
  }

  public void msgUserKey(boolean paramBoolean1, int paramInt, boolean paramBoolean2)
  {
    HotKeyEnv.keyPress(paramBoolean1, 601 + paramInt & 0xFFFF, paramBoolean2);
  }

  public void msgJoyButton(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    HotKeyEnv.keyPress(Message.current().isRealTime(), paramInt1, paramInt2, paramBoolean);
  }
  public void msgJoyMove(int paramInt1, int paramInt2, int paramInt3) {
    HotKeyEnv.joyMove(Message.current().isRealTime(), paramInt1, paramInt2, paramInt3);
  }
  public void msgJoyPov(int paramInt1, int paramInt2) {
    boolean bool = Message.current().isRealTime();
    int i = this.povState[(paramInt1 - 584)];
    if (i == paramInt2) return;
    if (i != 0) {
      this.povState[(paramInt1 - 584)] = 0;
      HotKeyEnv.keyPress(bool, paramInt1, i, false);
    }
    if (paramInt2 != 571) {
      this.povState[(paramInt1 - 584)] = paramInt2;
      HotKeyEnv.keyPress(bool, paramInt1, paramInt2, true);
    }
  }

  public void msgJoyPoll() {
  }

  public void msgMouseButton(int paramInt, boolean paramBoolean) {
    HotKeyEnv.keyPress(Message.current().isRealTime(), 524 + paramInt, paramBoolean);
  }
  public void msgMouseMove(int paramInt1, int paramInt2, int paramInt3) {
    HotKeyEnv.mouseMove(Message.current().isRealTime(), paramInt1, paramInt2, paramInt3);
  }
  public void msgMouseAbsMove(int paramInt1, int paramInt2, int paramInt3) {
    HotKeyEnv.mouseAbsMove(Message.current().isRealTime(), paramInt1, paramInt2, paramInt3);
  }

  public void msgKeyboardKey(int paramInt, boolean paramBoolean)
  {
    HotKeyEnv.keyPress(Message.current().isRealTime(), 0 + paramInt, paramBoolean);
  }
  public void msgKeyboardChar(char paramChar) {
  }
  public void msgTrackIRAngles(float paramFloat1, float paramFloat2, float paramFloat3) {
    HotKeyEnv.trackIRAngles(Message.current().isRealTime(), paramFloat1, paramFloat2, paramFloat3);
  }

  public void msgTimeOut(Object paramObject)
  {
    if (Message.current().isRealTime()) {
      HotKeyEnv.tick(true);
      this.msgRealTimeOut.post();
    } else {
      HotKeyEnv.tick(false);
      this.msgTimeOut.post();
    }
  }
}
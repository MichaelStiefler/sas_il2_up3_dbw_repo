package com.maddox.rts;

public class Keyboard
  implements MsgAddListenerListener, MsgRemoveListenerListener
{
  public static final int PRESS = 0;
  public static final int RELEASE = 1;
  public static final int CHAR = 2;
  public static final int UNKNOWN = -1;
  public static final int BUTTONS = 524;
  private int buttonEnable = -1;
  private boolean bEnabled;
  private Listeners listeners;
  private Listeners realListeners;
  private Object focus;
  private MessageCache cache;
  private boolean[] buttons;

  public static Keyboard adapter()
  {
    return RTSConf.cur.keyboard;
  }

  public boolean isPressed(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= 524))
      return false;
    return this.buttons[paramInt];
  }

  public Object[] getListeners() {
    return this.listeners.get();
  }
  public Object[] getRealListeners() {
    return this.realListeners.get();
  }
  public void msgAddListener(Object paramObject1, Object paramObject2) {
    if (paramObject2 != null) {
      if (Message.current().isRealTime()) this.realListeners.insListener(paramObject1); else
        this.listeners.insListener(paramObject1);
    }
    else if (Message.current().isRealTime()) this.realListeners.addListener(paramObject1); else
      this.listeners.addListener(paramObject1);
  }

  public void msgRemoveListener(Object paramObject1, Object paramObject2)
  {
    if (Message.current().isRealTime()) this.realListeners.removeListener(paramObject1); else
      this.listeners.removeListener(paramObject1); 
  }

  public Object focus() {
    return this.focus; } 
  public void setFocus(Object paramObject) { this.focus = paramObject; } 
  public void setEnable(boolean paramBoolean) {
    this.bEnabled = paramBoolean; } 
  public boolean isEnable() { return this.bEnabled; } 
  public void setKeyEnable(int paramInt) { this.buttonEnable = paramInt;
  }

  public void setPress(long paramLong, int paramInt)
  {
    if ((paramInt == this.buttonEnable) || (this.bEnabled))
      _setPress(true, paramLong, paramInt); 
  }

  protected void _setPress(boolean paramBoolean, long paramLong, int paramInt) {
    if ((paramInt < 0) || (paramInt >= 524))
      return;
    if (this.buttons[paramInt] != 0)
      return;
    this.buttons[paramInt] = true;
    post(paramBoolean, paramLong, -1, paramInt, true);
  }

  public void setRelease(long paramLong, int paramInt)
  {
    if ((paramInt == this.buttonEnable) || (this.bEnabled))
      _setRelease(true, paramLong, paramInt); 
  }

  protected void _setRelease(boolean paramBoolean, long paramLong, int paramInt) {
    if ((paramInt < 0) || (paramInt >= 524))
      return;
    if (this.buttons[paramInt] == 0)
      return;
    this.buttons[paramInt] = false;
    post(paramBoolean, paramLong, -1, paramInt, false);
  }

  public void setChar(long paramLong, int paramInt)
  {
    if (!this.bEnabled) return;
    _setChar(true, paramLong, paramInt);
  }
  public void _setChar(boolean paramBoolean, long paramLong, int paramInt) {
    post(paramBoolean, paramLong, paramInt, 0, false);
  }

  private void msgSet(MsgKeyboard paramMsgKeyboard, boolean paramBoolean1, boolean paramBoolean2, long paramLong, int paramInt1, int paramInt2, boolean paramBoolean3) {
    paramMsgKeyboard.setTickPos(2147483646);
    if (paramBoolean2) {
      paramMsgKeyboard.setFlags(64);
      if (!paramBoolean1) paramLong = Time.toReal(paramLong);
      paramMsgKeyboard.setTime(paramLong);
    } else {
      paramMsgKeyboard.setFlags(0);
      if (paramBoolean1) paramLong = Time.current();
      paramMsgKeyboard.setTime(paramLong);
    }
    if (paramInt1 >= 0) {
      paramMsgKeyboard.setChar(paramInt1);
    }
    else if (paramBoolean3) paramMsgKeyboard.setPress(paramInt2); else
      paramMsgKeyboard.setRelease(paramInt2);
  }

  private void post(boolean paramBoolean1, long paramLong, int paramInt1, int paramInt2, boolean paramBoolean2)
  {
    if (this.focus != null) {
      localObject = (MsgKeyboard)this.cache.get();
      msgSet((MsgKeyboard)localObject, paramBoolean1, true, paramLong, paramInt1, paramInt2, paramBoolean2);
      ((MsgKeyboard)localObject).post(this.focus);
      return;
    }
    Object localObject = this.realListeners.get();
    MsgKeyboard localMsgKeyboard;
    if (localObject != null) {
      localMsgKeyboard = (MsgKeyboard)this.cache.get();
      msgSet(localMsgKeyboard, paramBoolean1, true, paramLong, paramInt1, paramInt2, paramBoolean2);
      localMsgKeyboard.post(localObject);
    }
    if (!Time.isPaused()) {
      localObject = this.listeners.get();
      if (localObject != null) {
        localMsgKeyboard = (MsgKeyboard)this.cache.get();
        msgSet(localMsgKeyboard, paramBoolean1, false, paramLong, paramInt1, paramInt2, paramBoolean2);
        localMsgKeyboard.post(localObject);
      }
    }
  }

  public void clear()
  {
    if (!this.bEnabled) return;
    _clear();
  }
  protected void _clear() {
    long l = Time.currentReal();
    for (int i = 0; i < 524; i++)
      if (this.buttons[i] != 0)
        _setRelease(true, l, i);
  }

  protected Keyboard() {
    this.listeners = new Listeners();
    this.realListeners = new Listeners();
    this.cache = new MessageCache(MsgKeyboard.class);
    this.buttons = new boolean[524];
    for (int i = 0; i < 524; i++)
      this.buttons[i] = false;
    this.bEnabled = true;
  }
}
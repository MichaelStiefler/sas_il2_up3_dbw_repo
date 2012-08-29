package com.maddox.rts;

public class Mouse
  implements MsgAddListenerListener, MsgRemoveListenerListener
{
  public static final int PRESS = 0;
  public static final int RELEASE = 1;
  public static final int MOVE = 2;
  public static final int UNKNOWN = -1;
  public static final int BUTTONS = 7;
  private MouseCursor mouseCursor;
  private boolean bEnabled;
  private Listeners listeners;
  private Listeners realListeners;
  private Object focus;
  private long lastTimeMove;
  private boolean bLastRealTimeMove;
  private int dx;
  private int dy;
  private int dz;
  private int X;
  private int Y;
  private int Z;
  private int prevX;
  private int prevY;
  private boolean bComputeAbsPos = false;
  private boolean bComputeRelPos = false;
  private boolean bResetComputeRelPos = true;
  private float[] sensitivity;
  private boolean[] buttons;
  private boolean invert = false;
  private MessageCache cache;

  public static Mouse adapter()
  {
    return RTSConf.cur.mouse;
  }

  public void setMouseCursorAdapter(MouseCursor paramMouseCursor)
  {
    this.mouseCursor = paramMouseCursor;
  }

  public boolean isExistMouseCursorAdapter() {
    return this.mouseCursor != null;
  }

  public void setMouseCursor(int paramInt) {
    if (this.mouseCursor != null)
      this.mouseCursor.setCursor(paramInt);
  }

  public boolean isPressed(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= 7))
      return false;
    return this.buttons[paramInt];
  }

  public float[] getSensitivity()
  {
    return this.sensitivity;
  }

  public Object[] getListeners()
  {
    return this.listeners.get();
  }
  public boolean isInvert() { return this.invert; } 
  public void setInvert(boolean paramBoolean) {
    this.invert = paramBoolean;
  }

  public Object[] getRealListeners()
  {
    return this.realListeners.get();
  }
  public void getPos(int[] paramArrayOfInt) {
    paramArrayOfInt[0] = this.X;
    paramArrayOfInt[1] = this.Y;
    paramArrayOfInt[2] = this.Z;
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

  private void msgSet(MsgMouse paramMsgMouse, boolean paramBoolean1, boolean paramBoolean2, long paramLong, boolean paramBoolean3, int paramInt1, boolean paramBoolean4, boolean paramBoolean5, int paramInt2, int paramInt3, int paramInt4) {
    paramMsgMouse.setTickPos(2147483646);
    if (paramBoolean2) {
      paramMsgMouse.setFlags(64);
      if (!paramBoolean1) paramLong = Time.toReal(paramLong);
      paramMsgMouse.setTime(paramLong);
    } else {
      paramMsgMouse.setFlags(0);
      if (paramBoolean1) paramLong = Time.current();
      paramMsgMouse.setTime(paramLong);
    }
    if (paramBoolean3) {
      if (paramBoolean4) paramMsgMouse.setPress(paramInt1); else
        paramMsgMouse.setRelease(paramInt1);
    }
    else if (paramBoolean5) paramMsgMouse.setAbsMove(paramInt2, paramInt3, paramInt4); else
      paramMsgMouse.setMove(paramInt2, paramInt3, paramInt4);
  }

  private void post(boolean paramBoolean1, long paramLong, boolean paramBoolean2, int paramInt1, boolean paramBoolean3, boolean paramBoolean4, int paramInt2, int paramInt3, int paramInt4)
  {
    MsgMouse localMsgMouse;
    if (this.focus != null) {
      localMsgMouse = (MsgMouse)this.cache.get();
      msgSet(localMsgMouse, paramBoolean1, true, paramLong, paramBoolean2, paramInt1, paramBoolean3, paramBoolean4, paramInt2, paramInt3, paramInt4);
      localMsgMouse.post(this.focus);
      return;
    }
    Object[] arrayOfObject = this.realListeners.get();
    if (arrayOfObject != null) {
      localMsgMouse = (MsgMouse)this.cache.get();
      msgSet(localMsgMouse, paramBoolean1, true, paramLong, paramBoolean2, paramInt1, paramBoolean3, paramBoolean4, paramInt2, paramInt3, paramInt4);
      localMsgMouse.post(arrayOfObject);
    }

    if (!Time.isPaused()) {
      arrayOfObject = this.listeners.get();
      if (arrayOfObject != null) {
        localMsgMouse = (MsgMouse)this.cache.get();
        msgSet(localMsgMouse, paramBoolean1, false, paramLong, paramBoolean2, paramInt1, paramBoolean3, paramBoolean4, paramInt2, paramInt3, paramInt4);
        localMsgMouse.post(arrayOfObject);
      }
    }
  }

  public Object focus() {
    return this.focus; } 
  public void setFocus(Object paramObject) { this.focus = paramObject; } 
  public void setEnable(boolean paramBoolean) {
    this.bEnabled = paramBoolean; } 
  public boolean isEnable() { return this.bEnabled; }

  public void setPress(long paramLong, int paramInt)
  {
    if (!this.bEnabled) return;
    _setPress(true, paramLong, paramInt);
  }
  protected void _setPress(boolean paramBoolean, long paramLong, int paramInt) {
    flushMove();
    if ((paramInt < 0) || (paramInt >= 7))
      return;
    if (this.buttons[paramInt] != 0)
      return;
    this.buttons[paramInt] = true;
    post(paramBoolean, paramLong, true, paramInt, true, false, 0, 0, 0);
  }

  public void setRelease(long paramLong, int paramInt)
  {
    if (!this.bEnabled) return;
    _setRelease(true, paramLong, paramInt);
  }
  protected void _setRelease(boolean paramBoolean, long paramLong, int paramInt) {
    flushMove();
    if ((paramInt < 0) || (paramInt >= 7))
      return;
    if (this.buttons[paramInt] == 0)
      return;
    this.buttons[paramInt] = false;
    post(paramBoolean, paramLong, true, paramInt, false, false, 0, 0, 0);
  }

  public void clear()
  {
    if (!this.bEnabled) return;
    _clear();
  }
  protected void _clear() {
    for (int i = 0; i < 7; i++)
      if (this.buttons[i] != 0)
        _setRelease(true, Time.currentReal(), i);
    this.bResetComputeRelPos = true;
    clearMove();
  }

  public void setMove(long paramLong, int paramInt1, int paramInt2, int paramInt3)
  {
    if (!this.bEnabled) return;
    _setMove(true, paramLong, paramInt1, paramInt2, paramInt3);
  }
  protected void _setMove(boolean paramBoolean, long paramLong, int paramInt1, int paramInt2, int paramInt3) {
    if ((paramLong != this.lastTimeMove) || (paramBoolean != this.bLastRealTimeMove))
      _flushMove();
    paramInt3 = paramInt3 * 5 / 120;
    this.lastTimeMove = paramLong;
    this.bLastRealTimeMove = paramBoolean;
    this.dx += paramInt1; this.dy += paramInt2; this.dz += paramInt3;
  }

  public void setAbsMove(long paramLong, int paramInt1, int paramInt2, int paramInt3)
  {
    if (!this.bEnabled) return;
    _setAbsMove(true, paramLong, paramInt1, paramInt2, paramInt3);
  }
  protected void _setAbsMove(boolean paramBoolean, long paramLong, int paramInt1, int paramInt2, int paramInt3) {
    this.X = paramInt1; this.Y = paramInt2;
    this.Z += paramInt3 * 5 / 120;
    if (this.Z < -125) this.Z = -125;
    if (this.Z > 125) this.Z = 125;
    post(paramBoolean, paramLong, false, 0, false, true, this.X, this.Y, this.Z);
    if (this.bComputeRelPos) {
      if (!this.bResetComputeRelPos) {
        paramInt1 = this.X - this.prevX; paramInt2 = this.Y - this.prevY;
        _setMove(paramBoolean, paramLong, paramInt1, paramInt2, paramInt3);
      } else {
        this.bResetComputeRelPos = false;
      }
      this.prevX = this.X; this.prevY = this.Y;
    }
  }

  public void flushMove()
  {
    if (!this.bEnabled) return;
    _flushMove();
  }
  protected void _flushMove() {
    if (this.lastTimeMove != -1L) {
      if ((this.dx != 0) || (this.dy != 0) || (this.dz != 0)) {
        int i = this.dx >= 0 ? (int)(this.dx * this.sensitivity[0] + 0.5F) : (int)(this.dx * this.sensitivity[0] - 0.5F);
        int j = this.dy >= 0 ? (int)(this.dy * this.sensitivity[1] + 0.5F) : (int)(this.dy * this.sensitivity[1] - 0.5F);
        int k = this.dz >= 0 ? (int)(this.dz * this.sensitivity[2] + 0.5F) : (int)(this.dz * this.sensitivity[2] - 0.5F);
        post(this.bLastRealTimeMove, this.lastTimeMove, false, 0, false, false, i, j, k);
        if (this.bComputeAbsPos) {
          int m = RTSConf.cur.mainWindow.width();
          int n = RTSConf.cur.mainWindow.height();
          this.X += i; if (this.X < 0) this.X = 0; if (this.X >= m) this.X = (m - 1);
          this.Y += j; if (this.Y < 0) this.Y = 0; if (this.Y >= n) this.Y = (n - 1);
          this.Z += k; if (this.Z < -125) this.Z = -125;
          if (this.Z > 125) this.Z = 125;
          post(this.bLastRealTimeMove, this.lastTimeMove, false, 0, false, true, this.X, this.Y, this.Z);
        }
      }
      clearMove();
    }
  }

  private void clearMove() {
    this.lastTimeMove = -1L;
    this.bLastRealTimeMove = true;
    this.dx = 0; this.dy = 0; this.dz = 0;
  }

  protected void setComputePos(boolean paramBoolean1, boolean paramBoolean2) {
    this.bComputeAbsPos = paramBoolean1;
    this.bComputeRelPos = paramBoolean2;
    this.prevX = (this.X = RTSConf.cur.mainWindow.width() / 2);
    this.prevY = (this.Y = RTSConf.cur.mainWindow.height() / 2);
    this.Z = 0;
    this.dx = 0; this.dy = 0; this.dz = 0;
  }
  protected boolean isComputeRelPos() {
    return this.bComputeRelPos; } 
  protected boolean isComputeAbsPos() { return this.bComputeAbsPos; }

  public void saveConfig(IniFile paramIniFile, String paramString) {
    if ((paramIniFile == null) || (paramString == null)) return;
    paramIniFile.set(paramString, "SensitivityX", this.sensitivity[0]);
    paramIniFile.set(paramString, "SensitivityY", this.sensitivity[1]);
    paramIniFile.set(paramString, "SensitivityZ", this.sensitivity[2]);
    paramIniFile.set(paramString, "Invert", this.invert);
  }
  public void loadConfig(IniFile paramIniFile, String paramString) {
    if ((paramIniFile == null) || (paramString == null)) return;
    this.sensitivity[0] = paramIniFile.get(paramString, "SensitivityX", 1.0F);
    this.sensitivity[1] = paramIniFile.get(paramString, "SensitivityY", 1.0F);
    this.sensitivity[2] = paramIniFile.get(paramString, "SensitivityZ", 1.0F);
    this.invert = paramIniFile.get(paramString, "Invert", this.invert);
  }

  protected Mouse(IniFile paramIniFile, String paramString) {
    this.listeners = new Listeners();
    this.realListeners = new Listeners();
    this.cache = new MessageCache(MsgMouse.class);
    clearMove();
    this.buttons = new boolean[7];
    for (int i = 0; i < 7; i++)
      this.buttons[i] = false;
    this.sensitivity = new float[3];
    for (int j = 0; j < 3; j++)
      this.sensitivity[j] = 1.0F;
    this.X = (this.Y = this.Z = 0);
    this.bEnabled = true;
    loadConfig(paramIniFile, paramString);
  }
}
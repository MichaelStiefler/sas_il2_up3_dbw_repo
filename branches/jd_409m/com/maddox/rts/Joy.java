package com.maddox.rts;

import com.maddox.util.NumberTokenizer;

public class Joy
  implements MsgAddListenerListener, MsgRemoveListenerListener
{
  public static final int AMOUNTJOY = 4;
  public static final int AMOUNTAXE = 8;
  public static final int AMOUNTPOV = 4;
  public static final int AXE_NOT_PRESENT = -1;
  public static final int AXE_MAX_MOVE = 125;
  public static final int AXE_MIN_MOVE = -125;
  public static final float NORMAL_KOOF = 0.008F;
  public static final int AXE_X = 0;
  public static final int AXE_Y = 1;
  public static final int AXE_Z = 2;
  public static final int AXE_RX = 3;
  public static final int AXE_RY = 4;
  public static final int AXE_RZ = 5;
  public static final int AXE_U = 6;
  public static final int AXE_V = 7;
  public static final int POV_0 = 8;
  public static final int POV_1 = 9;
  public static final int POV_2 = 10;
  public static final int POV_3 = 11;
  public static final int PRESS = 0;
  public static final int RELEASE = 1;
  public static final int MOVE = 2;
  public static final int POV = 3;
  public static final int UNKNOWN = -1;
  public static final int BUTTONS = 70;
  private boolean bEnabled;
  private Listeners listeners;
  private Listeners realListeners;
  private Object focus;
  private int amount;
  private int[][] mov;
  private int[][] filter;
  private boolean[][] filterUpdated;
  private int[][] cur_mov;
  private int[][] pov;
  private boolean[][] buttons;
  private int[][][] koof;
  private MessageCache cache;
  private boolean bAttached = false;
  private int[] countButtons;
  private int[] countAxes;
  private int[] countPOVs;
  private int[] caps;

  public static float normal(int paramInt)
  {
    return paramInt * 0.008F;
  }

  public static Joy adapter()
  {
    return RTSConf.cur.joy;
  }

  public boolean isAttached()
  {
    return this.bAttached;
  }
  public int buttons(int paramInt) {
    return this.countButtons[paramInt]; } 
  public int axes(int paramInt) { return this.countAxes[paramInt]; } 
  public int POVs(int paramInt) { return this.countPOVs[paramInt]; } 
  public int caps(int paramInt) { return this.caps[paramInt]; }

  public boolean isExistAxe(int paramInt1, int paramInt2)
  {
    if (paramInt2 < 0) return false;
    if (paramInt2 > 7) return false;
    return (this.caps[paramInt1] & 1 << paramInt2) != 0;
  }

  public boolean isExistPov(int paramInt1, int paramInt2)
  {
    if (paramInt2 < 0) return false;
    if (paramInt2 >= 4) return false;
    return (this.caps[paramInt1] & 1 << paramInt2 + 8) != 0;
  }

  public static String axeName(int paramInt)
  {
    switch (paramInt) { case 0:
      return "X";
    case 1:
      return "Y";
    case 2:
      return "Z";
    case 3:
      return "RX";
    case 4:
      return "RY";
    case 5:
      return "RZ";
    case 6:
      return "U";
    case 7:
      return "V"; }
    return null;
  }

  public boolean isPressed(int paramInt1, int paramInt2)
  {
    if ((paramInt2 < 0) || (paramInt2 >= 70))
      return false;
    return this.buttons[paramInt1][paramInt2];
  }

  public Object[] getListeners()
  {
    return this.listeners.get();
  }

  public Object[] getRealListeners()
  {
    return this.realListeners.get();
  }
  public void getPos(int paramInt, int[] paramArrayOfInt) {
    for (int i = 0; i < 8; i++) {
      int j = this.filterUpdated[paramInt][i];
      this.filterUpdated[paramInt][i] = 1;
      updateMove(paramInt, i);
      paramArrayOfInt[i] = this.cur_mov[paramInt][i];
      this.filterUpdated[paramInt][i] = j;
    }
  }

  public void getNotFilteredPos(int paramInt, int[] paramArrayOfInt) {
    for (int i = 0; i < 8; i++)
      paramArrayOfInt[i] = this.mov[paramInt][i];
  }

  public void getPov(int paramInt, int[] paramArrayOfInt) {
    for (int i = 0; i < 4; i++)
      paramArrayOfInt[i] = this.pov[paramInt][i];
  }

  public void getSensitivity(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    for (int i = 0; i < 12; i++)
      paramArrayOfInt[i] = this.koof[paramInt1][paramInt2][i];
  }

  public void setSensitivity(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    for (int i = 0; i < 12; i++)
      this.koof[paramInt1][paramInt2][i] = paramArrayOfInt[i];
  }

  public void msgAddListener(Object paramObject1, Object paramObject2) {
    if (Message.current().isRealTime()) this.realListeners.addListener(paramObject1); else
      this.listeners.addListener(paramObject1);
  }

  public void msgRemoveListener(Object paramObject1, Object paramObject2) {
    if (Message.current().isRealTime()) this.realListeners.removeListener(paramObject1); else
      this.listeners.removeListener(paramObject1);
  }

  private MsgJoy msgNext(boolean paramBoolean, long paramLong) {
    MsgJoy localMsgJoy = (MsgJoy)this.cache.get();
    localMsgJoy.setTickPos(2147483646);
    if (paramBoolean) {
      localMsgJoy.setFlags(64);
      localMsgJoy.setTime(paramLong);
    } else {
      localMsgJoy.setFlags(0);
      localMsgJoy.setTime(Time.current());
    }
    return localMsgJoy;
  }

  private void postButton(long paramLong, int paramInt1, int paramInt2, boolean paramBoolean) {
    if (this.focus != null) {
      localObject = msgNext(true, paramLong);
      ((MsgJoy)localObject).setButton(paramInt1, paramInt2, paramBoolean);
      ((MsgJoy)localObject).post(this.focus);
      return;
    }
    Object localObject = this.realListeners.get();
    MsgJoy localMsgJoy;
    if (localObject != null) {
      localMsgJoy = msgNext(true, paramLong);
      localMsgJoy.setButton(paramInt1, paramInt2, paramBoolean);
      localMsgJoy.post(localObject);
    }
    if (!Time.isPaused()) {
      localObject = this.listeners.get();
      if (localObject != null) {
        localMsgJoy = msgNext(false, paramLong);
        localMsgJoy.setButton(paramInt1, paramInt2, paramBoolean);
        localMsgJoy.post(localObject);
      }
    }
  }

  private void postMove(long paramLong, int paramInt1, int paramInt2, int paramInt3) {
    postMove(true, paramLong, paramInt1, paramInt2, paramInt3);
  }
  private void postMove(boolean paramBoolean, long paramLong, int paramInt1, int paramInt2, int paramInt3) {
    if (this.focus != null) {
      localObject = msgNext(true, paramLong);
      ((MsgJoy)localObject).setMove(paramInt1, paramInt2, paramInt3);
      ((MsgJoy)localObject).post(this.focus);
      return;
    }
    Object localObject = this.realListeners.get();
    MsgJoy localMsgJoy;
    if (localObject != null) {
      localMsgJoy = msgNext(true, paramLong);
      localMsgJoy.setMove(paramInt1, paramInt2, paramInt3);
      localMsgJoy.post(localObject);
    }
    if ((!paramBoolean) || (!Time.isPaused())) {
      localObject = this.listeners.get();
      if (localObject != null) {
        localMsgJoy = msgNext(false, paramLong);
        localMsgJoy.setMove(paramInt1, paramInt2, paramInt3);
        localMsgJoy.post(localObject);
      }
    }
  }

  private int idPov(int paramInt) {
    if (paramInt == -1) return 571;
    if (paramInt > 337) return 572;
    if (paramInt > 292) return 579;
    if (paramInt > 247) return 578;
    if (paramInt > 202) return 577;
    if (paramInt > 157) return 576;
    if (paramInt > 112) return 575;
    if (paramInt > 67) return 574;
    if (paramInt > 22) return 573;
    return 572;
  }
  private void postPov(long paramLong, int paramInt1, int paramInt2, int paramInt3) {
    int i = paramInt1 * 4 + paramInt2;
    int j = idPov(paramInt3);
    if (this.focus != null) {
      localObject = msgNext(true, paramLong);
      ((MsgJoy)localObject).setPov(i, j);
      ((MsgJoy)localObject).post(this.focus);
      return;
    }
    Object localObject = this.realListeners.get();
    MsgJoy localMsgJoy;
    if (localObject != null) {
      localMsgJoy = msgNext(true, paramLong);
      localMsgJoy.setPov(i, j);
      localMsgJoy.post(localObject);
    }
    if (!Time.isPaused()) {
      localObject = this.listeners.get();
      if (localObject != null) {
        localMsgJoy = msgNext(false, paramLong);
        localMsgJoy.setPov(i, j);
        localMsgJoy.post(localObject);
      }
    }
  }

  private void postPoll(long paramLong) {
    if (this.focus != null) {
      localObject = msgNext(true, paramLong);
      ((MsgJoy)localObject).setPoll();
      ((MsgJoy)localObject).post(this.focus);
      return;
    }
    Object localObject = this.realListeners.get();
    MsgJoy localMsgJoy;
    if (localObject != null) {
      localMsgJoy = msgNext(true, paramLong);
      localMsgJoy.setPoll();
      localMsgJoy.post(localObject);
    }
    if (!Time.isPaused()) {
      localObject = this.listeners.get();
      if (localObject != null) {
        localMsgJoy = msgNext(false, paramLong);
        localMsgJoy.setPoll();
        localMsgJoy.post(localObject);
      }
    }
  }

  public void setAttached(boolean paramBoolean)
  {
    this.bAttached = paramBoolean;
  }

  public void setAmount(int paramInt)
  {
    this.amount = paramInt;
  }

  public void setCaps(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    this.countButtons[paramInt1] = paramInt2;
    this.countAxes[paramInt1] = paramInt3;
    this.countPOVs[paramInt1] = paramInt4;
    this.caps[paramInt1] = paramInt5;
  }
  public Object focus() {
    return this.focus; } 
  public void setFocus(Object paramObject) { this.focus = paramObject; } 
  public void setEnable(boolean paramBoolean) {
    this.bEnabled = paramBoolean; } 
  public boolean isEnable() { return this.bEnabled; }

  public void setPress(long paramLong, int paramInt1, int paramInt2)
  {
    if (!this.bEnabled) return;
    if ((paramInt2 < 0) || (paramInt2 >= 70))
      return;
    if (this.buttons[paramInt1][paramInt2] != 0)
      return;
    this.buttons[paramInt1][paramInt2] = 1;
    postButton(paramLong, paramInt1, paramInt2, true);
  }

  public void setRelease(long paramLong, int paramInt1, int paramInt2)
  {
    if (!this.bEnabled) return;
    if ((paramInt2 < 0) || (paramInt2 >= 70))
      return;
    if (this.buttons[paramInt1][paramInt2] == 0)
      return;
    this.buttons[paramInt1][paramInt2] = 0;
    postButton(paramLong, paramInt1, paramInt2, false);
  }

  public void clear()
  {
    if (!this.bEnabled) return;
    _clear();
  }
  protected void _clear() {
    for (int i = 0; i < 4; i++)
      for (int j = 0; j < 70; j++)
        if (this.buttons[i][j] != 0)
          setRelease(Time.currentReal(), i, j);
    for (int k = 0; k < 4; k++)
    {
      for (int m = 0; m < 4; m++)
        this.pov[k][m] = -1;
    }
  }

  public void rePostMoves() {
    long l = Time.currentReal();
    for (int i = 0; i < 4; i++)
      for (int j = 0; j < 8; j++)
        if (this.cur_mov[i][j] != -1)
          postMove(false, l, i, j, this.cur_mov[i][j]);
  }

  public void setMove(long paramLong, int paramInt1, int paramInt2, int paramInt3)
  {
    if (!this.bEnabled) return;
    this.mov[paramInt1][paramInt2] = paramInt3;
    if (updateMove(paramInt1, paramInt2))
      postMove(paramLong, paramInt1, paramInt2, this.cur_mov[paramInt1][paramInt2]);
  }

  public void setPov(long paramLong, int paramInt1, int paramInt2, int paramInt3)
  {
    if (!this.bEnabled) return;
    this.pov[paramInt1][paramInt2] = paramInt3;
    postPov(paramLong, paramInt1, paramInt2, paramInt3);
  }

  public void poll(long paramLong)
  {
    if (!this.bEnabled) return;
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 8; j++) {
        if ((filterUpdate(i, j)) && (updateMove(i, j)))
          postMove(paramLong, i, j, this.cur_mov[i][j]);
        this.filterUpdated[i][j] = 0;
      }
    }
    postPoll(paramLong);
  }

  private boolean filterUpdate(int paramInt1, int paramInt2) {
    if ((this.filterUpdated[paramInt1][paramInt2] != 0) || (this.koof[paramInt1][paramInt2][11] == 0)) return false;
    this.filterUpdated[paramInt1][paramInt2] = 1;
    int i = this.koof[paramInt1][paramInt2][11];
    if (this.mov[paramInt1][paramInt2] == -1) return false;
    if (this.mov[paramInt1][paramInt2] < -125) this.mov[paramInt1][paramInt2] = -125;
    if (this.mov[paramInt1][paramInt2] > 125) this.mov[paramInt1][paramInt2] = 125;
    float f = 0.8F * i / 100.0F;
    int j = Math.round(this.mov[paramInt1][paramInt2] * (1.0F - f) + this.filter[paramInt1][paramInt2] * f);
    if (j == -1)
      j++;
    int k = this.filter[paramInt1][paramInt2] != j ? 1 : 0;
    this.filter[paramInt1][paramInt2] = j;
    return k;
  }

  private boolean updateMove(int paramInt1, int paramInt2) {
    int i = 0;
    if (this.mov[paramInt1][paramInt2] == -1) {
      i = -1;
    }
    else {
      if (this.koof[paramInt1][paramInt2][11] == 0) {
        if (this.mov[paramInt1][paramInt2] < -125) this.mov[paramInt1][paramInt2] = -125;
        if (this.mov[paramInt1][paramInt2] > 125) this.mov[paramInt1][paramInt2] = 125;
        j = this.mov[paramInt1][paramInt2];
      } else {
        filterUpdate(paramInt1, paramInt2);
        if (this.filter[paramInt1][paramInt2] < -125) this.filter[paramInt1][paramInt2] = -125;
        if (this.filter[paramInt1][paramInt2] > 125) this.filter[paramInt1][paramInt2] = 125;
        j = this.filter[paramInt1][paramInt2];
      }
      if (Math.abs(j) < this.koof[paramInt1][paramInt2][0]) {
        this.cur_mov[paramInt1][paramInt2] = 0;
      } else {
        int k = j < 0 ? 1 : 0;
        float f1 = 0.0F;
        if (k != 0) j = -j;
        float f2 = 12.5F;
        int m = (int)(j / f2);
        if (m > 9) m = 9;
        if (m == 0)
          f1 = interpolate(0, 0, this.koof[paramInt1][paramInt2][1], j / f2);
        else {
          f1 = interpolate(m, this.koof[paramInt1][paramInt2][m], this.koof[paramInt1][paramInt2][(m + 1)], j / f2 - m);
        }
        int n = Math.round(f1 / 100.0F * 125.0F);
        if (n > 125) n = 125;
        if (k != 0) n = -n;
        if (n == -1)
          n++;
        i = n;
      }
    }
    int j = this.cur_mov[paramInt1][paramInt2] != i ? 1 : 0;
    this.cur_mov[paramInt1][paramInt2] = i;
    return j;
  }

  private float interpolate(int paramInt1, int paramInt2, int paramInt3, float paramFloat) {
    float f1 = paramInt2 * paramInt1 / 10.0F;
    float f2 = paramInt3 * (paramInt1 + 1) / 10.0F;
    if (paramFloat <= 0.0F) return f1;
    if (paramFloat >= 1.0F) return f2;
    return f1 + (f2 - f1) * paramFloat;
  }

  private void writeConfig(IniFile paramIniFile, String paramString1, int paramInt1, int paramInt2, String paramString2) {
    if ((this.caps[paramInt1] & 1 << paramInt2) == 0) return;
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < 12; i++) {
      if (i != 0) localStringBuffer.append(' ');
      localStringBuffer.append(this.koof[paramInt1][paramInt2][i]);
    }
    paramIniFile.set(paramString1, paramString2, localStringBuffer.toString());
  }

  public void saveConfig(IniFile paramIniFile, String paramString) {
    if ((paramIniFile == null) || (paramString == null)) return;
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 8; j++) {
        String str = "1" + (i == 0 ? axeName(j) : new StringBuffer().append(axeName(j)).append(i).toString());
        writeConfig(paramIniFile, paramString, i, j, str);
      }
    }
    paramIniFile.set(paramString, "FF", JoyFF.isEnable() ? "1" : "0");
  }

  private boolean readConfig(IniFile paramIniFile, String paramString1, int paramInt1, int paramInt2, String paramString2, boolean paramBoolean) {
    String str = paramIniFile.get(paramString1, paramString2, (String)null);
    if (str == null) return false;
    NumberTokenizer localNumberTokenizer = new NumberTokenizer(str);
    this.koof[paramInt1][paramInt2][0] = localNumberTokenizer.next(0);
    for (int i = 1; i < 11; i++)
      if (paramBoolean) this.koof[paramInt1][paramInt2][i] = localNumberTokenizer.next(100); else
        this.koof[paramInt1][paramInt2][i] = localNumberTokenizer.next(i * 10);
    this.koof[paramInt1][paramInt2][11] = localNumberTokenizer.next(0);
    return true;
  }

  public void loadConfig(IniFile paramIniFile, String paramString) {
    if ((paramIniFile == null) || (paramString == null)) return;
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 8; j++) {
        String str = axeName(j) + i;
        if (!readConfig(paramIniFile, paramString, i, j, "1" + str, true)) {
          readConfig(paramIniFile, paramString, i, j, str, false);
          this.koof[i][j][0] = 0;
          for (int k = 1; k < 11; k++) {
            if (this.koof[i][j][k] < 0)
              this.koof[i][j][k] = 0;
            else if (this.koof[i][j][k] >= 10 * k)
              this.koof[i][j][k] = 100;
            else {
              this.koof[i][j][k] = (this.koof[i][j][k] * 10 / k);
            }
          }
        }
      }
    }
    JoyFF.setEnable(paramIniFile.get(paramString, "FF", JoyFF.isEnable()));
  }

  protected Joy(IniFile paramIniFile, String paramString) {
    this.listeners = new Listeners();
    this.realListeners = new Listeners();
    this.cache = new MessageCache(MsgJoy.class);
    this.amount = 0;
    this.buttons = new boolean[4][70];
    for (int i = 0; i < 4; i++)
      for (int j = 0; j < 70; j++)
        this.buttons[i][j] = 0;
    this.mov = new int[4][8];
    this.filter = new int[4][8];
    this.filterUpdated = new boolean[4][8];
    this.cur_mov = new int[4][8];
    for (int k = 0; k < 4; k++)
      for (int m = 0; m < 8; m++)
      {
        byte tmp204_203 = (this.mov[k][m] = -1); this.cur_mov[k][m] = tmp204_203; this.filter[k][m] = tmp204_203;
        this.filterUpdated[k][m] = 0;
      }
    this.pov = new int[4][4];
    for (int n = 0; n < 4; n++)
      for (int i1 = 0; i1 < 4; i1++)
        this.pov[n][i1] = -1;
    this.bEnabled = true;
    this.koof = new int[4][8][12];
    this.countButtons = new int[4];
    this.countAxes = new int[4];
    this.countPOVs = new int[4];
    this.caps = new int[4];
    for (int i2 = 0; i2 < 4; i2++) {
      for (int i3 = 0; i3 < 8; i3++) {
        this.koof[i2][i3][0] = 0;
        for (int i4 = 1; i4 < 11; i4++)
          this.koof[i2][i3][i4] = 100;
        this.koof[i2][i3][11] = 0;
      }
      this.countButtons[i2] = 0;
      this.countAxes[i2] = 0;
      this.countPOVs[i2] = 0;
      this.caps[i2] = 0;
    }
    loadConfig(paramIniFile, paramString);
  }
}
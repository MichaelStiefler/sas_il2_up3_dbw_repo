package com.maddox.rts;

public class MsgJoy extends Message
{
  public static final int PRESS = 0;
  public static final int RELEASE = 1;
  public static final int MOVE = 2;
  public static final int POV = 3;
  public static final int POLL = 4;
  public static final int UNKNOWN = -1;
  protected int id = -1;
  protected int idDev;
  protected int data0;
  protected int data1;

  public void setButton(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    this.id = (paramBoolean ? 0 : 1);
    this.idDev = (580 + paramInt1);
    this.data0 = (531 + paramInt2);
  }

  public void setMove(int paramInt1, int paramInt2, int paramInt3) {
    this.id = 2;
    this.idDev = (580 + paramInt1);
    this.data0 = (563 + paramInt2);
    this.data1 = paramInt3;
  }

  public void setPov(int paramInt1, int paramInt2) {
    this.id = 3;
    this.idDev = (584 + paramInt1);
    this.data0 = paramInt2;
  }

  public void setPoll() {
    this.id = 4;
  }

  public boolean invokeListener(Object paramObject)
  {
    if ((paramObject instanceof MsgJoyListener)) {
      switch (this.id) {
      case 0:
      case 1:
        ((MsgJoyListener)paramObject).msgJoyButton(this.idDev, this.data0, this.id == 0);
        return true;
      case 2:
        ((MsgJoyListener)paramObject).msgJoyMove(this.idDev, this.data0, this.data1);
        return true;
      case 3:
        ((MsgJoyListener)paramObject).msgJoyPov(this.idDev, this.data0);
        return true;
      case 4:
        ((MsgJoyListener)paramObject).msgJoyPoll();
        return true;
      }
      return true;
    }

    return false;
  }
}
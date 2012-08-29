package com.maddox.rts;

public class MsgMouse extends Message
{
  public static final int PRESS = 0;
  public static final int RELEASE = 1;
  public static final int MOVE = 2;
  public static final int ABSMOVE = 3;
  public static final int UNKNOWN = -1;
  protected int id = -1; protected int data0 = -1;
  protected int data1;
  protected int data2;

  public int id()
  {
    return this.id;
  }

  public int button()
  {
    return this.id != 2 ? this.data0 : -1;
  }

  public int dx()
  {
    return this.id == 2 ? this.data0 : 0;
  }

  public int dy()
  {
    return this.id == 2 ? this.data1 : 0;
  }

  public int dz()
  {
    return this.id == 2 ? this.data2 : 0;
  }

  public int x() {
    return this.id == 3 ? this.data0 : 0;
  }

  public int y()
  {
    return this.id == 3 ? this.data1 : 0;
  }

  public int z()
  {
    return this.id == 3 ? this.data2 : 0;
  }

  public void setPress(int paramInt)
  {
    this.id = 0; this.data0 = paramInt;
  }

  public void setRelease(int paramInt)
  {
    this.id = 1; this.data0 = paramInt;
  }

  public void setMove(int paramInt1, int paramInt2, int paramInt3)
  {
    this.id = 2; this.data0 = paramInt1; this.data1 = paramInt2; this.data2 = paramInt3;
  }

  public void setAbsMove(int paramInt1, int paramInt2, int paramInt3)
  {
    this.id = 3; this.data0 = paramInt1; this.data1 = paramInt2; this.data2 = paramInt3;
  }

  public boolean invokeListener(Object paramObject)
  {
    if ((paramObject instanceof MsgMouseListener)) {
      switch (this.id) {
      case 0:
      case 1:
        ((MsgMouseListener)paramObject).msgMouseButton(this.data0, this.id == 0);
        return true;
      case 2:
        ((MsgMouseListener)paramObject).msgMouseMove(this.data0, this.data1, this.data2);
        return true;
      case 3:
        ((MsgMouseListener)paramObject).msgMouseAbsMove(this.data0, this.data1, this.data2);
        return true;
      }
      return true;
    }

    return false;
  }
}
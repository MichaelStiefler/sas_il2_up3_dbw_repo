package com.maddox.rts;

public class MsgKeyboard extends Message
{
  public static final int PRESS = 0;
  public static final int RELEASE = 1;
  public static final int CHAR = 2;
  public static final int UNKNOWN = -1;
  protected int id = -1; protected int key = -1;

  public int id()
  {
    return this.id;
  }

  public int button()
  {
    return this.key;
  }

  public int key()
  {
    return this.key;
  }

  public int getchar()
  {
    return this.key;
  }

  public void setPress(int paramInt)
  {
    this.id = 0; this.key = paramInt;
  }

  public void setRelease(int paramInt)
  {
    this.id = 1; this.key = paramInt;
  }

  public void setChar(int paramInt)
  {
    this.id = 2; this.key = paramInt;
  }

  public boolean invokeListener(Object paramObject)
  {
    if ((paramObject instanceof MsgKeyboardListener)) {
      switch (this.id) {
      case 0:
      case 1:
        ((MsgKeyboardListener)paramObject).msgKeyboardKey(this.key, this.id == 0);
        return true;
      case 2:
        ((MsgKeyboardListener)paramObject).msgKeyboardChar((char)this.key);
        return true;
      }
      return true;
    }

    return false;
  }
}
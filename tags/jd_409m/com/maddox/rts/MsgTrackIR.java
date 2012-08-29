package com.maddox.rts;

public class MsgTrackIR extends Message
{
  public static final int ANGLES = 0;
  public static final int UNKNOWN = -1;
  protected int id = -1;
  protected float yaw;
  protected float pitch;
  protected float roll;

  public int id()
  {
    return this.id;
  }
  public float yaw() { return this.yaw; } 
  public float pitch() { return this.pitch; } 
  public float roll() { return this.roll;
  }

  public boolean invokeListener(Object paramObject)
  {
    if ((paramObject instanceof MsgMouseListener)) {
      switch (this.id) {
      case 0:
        ((MsgTrackIRListener)paramObject).msgTrackIRAngles(this.yaw, this.pitch, this.roll);
        return true;
      }
    }
    return false;
  }
}
package com.maddox.sound;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;

public class SoundListener
{
  protected static Acoustics acc = null;
  protected static Point3d pos = new Point3d();

  public static void setAcoustics(Acoustics paramAcoustics)
  {
    if (acc != paramAcoustics) {
      acc = paramAcoustics;
      jniSetAcoustics(acc.handle);
    }
  }

  protected static void flush()
  {
    if (acc != null) {
      double d = pos.z - Engine.land().HQ(pos.x, pos.y);
      acc.flush((float)d);
    }
  }

  public static void setPosition(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    pos.set(paramDouble1, paramDouble2, paramDouble3);
    jniSetPosition(paramDouble1, paramDouble2, paramDouble3);
  }

  public static void setVelocity(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    jniSetVelocity(paramFloat1, paramFloat2, paramFloat3);
  }

  public static void setOrientation(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    jniSetOrientation(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }

  protected static native void jniSetPosition(double paramDouble1, double paramDouble2, double paramDouble3);

  protected static native void jniSetVelocity(float paramFloat1, float paramFloat2, float paramFloat3);

  protected static native void jniSetOrientation(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);

  protected static native void jniSetAcoustics(int paramInt);
}
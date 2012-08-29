package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.rts.Destroy;
import com.maddox.sound.SoundFX;

public class EffClouds extends GObj
  implements Destroy
{
  private boolean bShow;
  private int type;
  private float height;
  protected SoundFX sound;
  protected float vmax;
  private static float[] farr3 = new float[3];

  static
  {
    GObj.loadNative();
  }

  public boolean isShow()
  {
    return this.bShow;
  }

  public void setShow(boolean flag)
  {
    this.bShow = flag;
  }

  public int type()
  {
    return this.type;
  }

  public float height()
  {
    return this.height;
  }

  public void soundUpdate(double d)
  {
    if (this.sound != null)
    {
      float f = (float)d;
      float f1 = 1.0F;
      float f2 = 1100.0F;
      if (f > this.height)
      {
        f -= this.height;
        if (f < f2)
        {
          f1 = 1.0F - f / f2;
        }
        else {
          f1 = 0.0F;
        }
      }
      this.sound.setVolume(f1 * this.vmax);
    }
  }

  protected void setRainSound(int i)
  {
    int j = Engine.land().config.month;
    boolean flag = ((j <= 10) && (j >= 4) && (i >= 5) && (i <= 6)) || (i > 7);
    if (flag)
    {
      if (this.sound == null)
      {
        this.sound = new SoundFX("objects.rain");
      }
      if (this.sound != null)
      {
        this.sound.setUsrFlag(i - 5);
        this.sound.play();
      }
    }
    else if (this.sound != null)
    {
      this.sound.clear();
      this.sound.cancel();
      this.sound = null;
    }
  }

  public void setType(int i)
  {
    if (this.cppObj == 0)
    {
      setRainSound(0);
      return;
    }
    this.type = i;
    if (i == 8)
    {
      i = 4;
    }
    SetType(this.cppObj, i);
    setRainSound(i);
  }

  public void setHeight(float f)
  {
    if (this.cppObj == 0)
    {
      return;
    }

    this.height = f;
    SetHeight(this.cppObj, f);
  }

  public boolean getRandomCloudPos(Point3d point3d)
  {
    if (this.cppObj == 0)
    {
      return false;
    }
    boolean flag = GetRandomCloudPos(this.cppObj, farr3);
    if (!flag)
    {
      return false;
    }

    point3d.x = farr3[0];
    point3d.y = farr3[1];
    point3d.z = farr3[2];
    return true;
  }

  public float getVisibility(Point3d point3d, Point3d point3d1)
  {
    return GetVisibility(this.cppObj, (float)point3d.x, (float)point3d.y, (float)point3d.z, (float)point3d1.x, (float)point3d1.y, (float)point3d1.z);
  }

  public int preRender()
  {
    if ((!this.bShow) || (this.cppObj == 0))
    {
      return 0;
    }

    return PreRender(this.cppObj);
  }

  public void render()
  {
    if ((!this.bShow) || (this.cppObj == 0))
    {
      return;
    }

    Render(this.cppObj);
  }

  public void destroy()
  {
    setRainSound(0);
    if (!isDestroyed())
    {
      Destroy(this.cppObj);
      this.cppObj = 0;
    }
  }

  public boolean isDestroyed()
  {
    return this.cppObj == 0;
  }

  public EffClouds(boolean flag, int i, float f)
  {
    super(0);

    this.bShow = true;
    this.sound = null;
    this.vmax = 1.0F;
    this.height = f;
    this.type = i;
    int j = i;
    if (flag)
    {
      j |= 16;
    }
    this.cppObj = Load(j, f);
    if (this.cppObj == 0)
    {
      throw new GObjException("EffClouds not created");
    }

    setRainSound(i);
  }

  public EffClouds(int i)
  {
    super(i);
    this.bShow = true;
    this.sound = null;
    this.vmax = 1.0F;
  }

  private native int Load(int paramInt, float paramFloat);

  private native void Destroy(int paramInt);

  private native void SetType(int paramInt1, int paramInt2);

  private native void SetHeight(int paramInt, float paramFloat);

  private native boolean GetRandomCloudPos(int paramInt, float[] paramArrayOfFloat);

  private native float GetVisibility(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);

  private native int PreRender(int paramInt);

  private native void Render(int paramInt);
}
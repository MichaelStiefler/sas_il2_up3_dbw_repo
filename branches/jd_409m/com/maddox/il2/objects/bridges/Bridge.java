package com.maddox.il2.objects.bridges;

import com.maddox.il2.engine.IconDraw;
import com.maddox.rts.Property;

public class Bridge extends LongBridge
{
  public int __indx;
  public int __type;
  public int __x1;
  public int __y1;
  public int __x2;
  public int __y2;
  public float __offsetK;

  public Bridge(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat)
  {
    super(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramFloat);
    IconDraw.create(this);
    this.__indx = paramInt1;
    this.__type = paramInt2;
    this.__x1 = paramInt3;
    this.__y1 = paramInt4;
    this.__x2 = paramInt5;
    this.__y2 = paramInt6;
    this.__offsetK = paramFloat;
  }

  static
  {
    Property.set(Bridge.class, "iconName", "icons/Bridge.mat");
  }
}
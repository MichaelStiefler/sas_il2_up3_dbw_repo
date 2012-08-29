package com.maddox.rts;

import java.lang.ref.WeakReference;

class PropertyListener extends WeakReference
{
  private static final int REAL_TIME = 1;
  private static final int SEND = 2;
  private int flags;

  public boolean isRealTime()
  {
    return (this.flags & 0x1) != 0; } 
  public boolean isSend() { return (this.flags & 0x2) != 0; } 
  public Object listener() { return get(); }

  public PropertyListener(Object paramObject, boolean paramBoolean1, boolean paramBoolean2) {
    super(paramObject);
    this.flags = ((paramBoolean1 ? 1 : 0) | (paramBoolean2 ? 2 : 0));
  }
}
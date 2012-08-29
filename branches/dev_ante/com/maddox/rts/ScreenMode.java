package com.maddox.rts;

import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.GObj;
import java.util.Vector;

public class ScreenMode
{
  public Object ext = null;
  private static ScreenMode def;
  private static ScreenMode cur;
  private static int[] n = new int[3];
  private int cx;
  private int cy;
  private int bpp;

  public int width()
  {
    return this.cx;
  }
  public int height() {
    return this.cy;
  }
  public int colourBits() {
    return this.bpp;
  }

  public static ScreenMode[] all()
  {
    Vector localVector = new Vector();
    int j = 0;

    while (EGet(j, n))
    {
      for (int i = 0; i < localVector.size(); i++) {
        localObject = (ScreenMode)localVector.elementAt(i);
        if ((((ScreenMode)localObject).cx == n[0]) && (((ScreenMode)localObject).cy == n[1]) && (((ScreenMode)localObject).bpp == n[2]))
          break;
      }
      if (i == localVector.size()) {
        localObject = new ScreenMode(n[0], n[1], n[2]);
        localVector.addElement(localObject);
      }
      j++;
    }
    Object localObject = new ScreenMode[localVector.size()];
    localVector.copyInto(localObject);
    return (ScreenMode)localObject;
  }

  public static ScreenMode startup()
  {
    init();
    return def;
  }

  public static ScreenMode current()
  {
    init();
    return cur;
  }

  public static boolean set(int paramInt1, int paramInt2, int paramInt3)
  {
    init();
    if ((paramInt1 == cur.cx) && (paramInt2 == cur.cy) && (paramInt3 == cur.bpp))
      return true;
    if ((paramInt1 == def.cx) && (paramInt2 == def.cy) && (paramInt3 == def.bpp)) {
      restore();
      return true;
    }
    n[0] = paramInt1; n[1] = paramInt2; n[2] = paramInt3;
    if (ESet(n)) {
      EGetCurrent(n);
      cur = new ScreenMode(n[0], n[1], n[2]);
      return true;
    }
    return false;
  }

  public static ScreenMode readCurrent() {
    EGetCurrent(n);
    return new ScreenMode(n[0], n[1], n[2]);
  }

  public static boolean set(ScreenMode paramScreenMode)
  {
    return set(paramScreenMode.width(), paramScreenMode.height(), paramScreenMode.colourBits());
  }

  public static void restore()
  {
    if (cur != def) {
      cur = def;
      ESet(null);
    }
  }

  public ScreenMode(ScreenMode paramScreenMode) {
    this.cx = paramScreenMode.cx;
    this.cy = paramScreenMode.cy;
    this.bpp = paramScreenMode.bpp;
  }

  private static void init()
  {
    if (Config.isUSE_RENDER()) {
      if (def != null)
        return;
      EGetCurrent(n);
      def = new ScreenMode(n[0], n[1], n[2]);
      cur = def;
    }
  }

  private static final native boolean EGet(int paramInt, int[] paramArrayOfInt);

  private static final native boolean ESet(int[] paramArrayOfInt);

  private static final native void EGetCurrent(int[] paramArrayOfInt);

  private ScreenMode(int paramInt1, int paramInt2, int paramInt3) {
    this.cx = paramInt1; this.cy = paramInt2; this.bpp = paramInt3;
  }

  static
  {
    GObj.loadNative();
  }
}
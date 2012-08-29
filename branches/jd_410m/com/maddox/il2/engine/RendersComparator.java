package com.maddox.il2.engine;

import java.util.Comparator;

class RendersComparator
  implements Comparator
{
  public int compare(Object paramObject1, Object paramObject2)
  {
    Render localRender1 = (Render)paramObject1;
    Render localRender2 = (Render)paramObject2;
    if (localRender1 == localRender2) return 0;
    if (localRender2.zOrder - localRender1.zOrder < 0.0F) return -1;
    return 1;
  }
}
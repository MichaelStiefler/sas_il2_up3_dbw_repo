package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.HierMesh;

public class PaintSchemeFMPar00du extends PaintSchemeFMPar00
{
  public void prepareNum(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    super.prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3);

    int i = paramRegiment.gruppeNumber() - 1;

    if (paramRegiment.country() == PaintScheme.countryNetherlands)
    {
      float f = 1.0F;

      changeMat(paramHierMesh, "Overlay6", "null", "null.tga", 1.0F, 1.0F, 1.0F);

      int j = 344 + (paramInt3 - 1) % 20;

      if ((j >= 344) && (j <= 363)) {
        changeMat(paramHierMesh, "Overlay1", "psBM00DUTCNUM" + i + paramInt1 + paramInt3, "Dutch/CW-" + j + ".tga", "null.tga", f, f, f, f, f, f);
      }

      return;
    }
  }
}
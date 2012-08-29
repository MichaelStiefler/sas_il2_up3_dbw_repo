package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

public class PaintSchemeFCSPar08 extends PaintSchemeFCSPar01
{
  public void prepareNumOff(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramRegiment.country() == PaintScheme.countryRussia)
      changeMat(paramHierMesh, "Overlay1", "null", "null.tga", 1.0F, 1.0F, 1.0F);
    changeMat(paramHierMesh, "Overlay4", "null", "null.tga", 1.0F, 1.0F, 1.0F);
    changeMat(paramHierMesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
    changeMat(paramHierMesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
  }

  public void prepareNum(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramRegiment.gruppeNumber() - 1;
    if (paramRegiment.country() == PaintScheme.countryRussia)
    {
      changeMat(paramHierMesh, "Overlay1", "null", "null.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay4", "null", "null.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);

      int k = World.cur().camouflage;
      float f;
      if (k == 1)
        f = 0.1F;
      else {
        f = 0.9F;
      }
      if (paramInt3 < 10) {
        changeMat(paramHierMesh, "Overlay8", "psFCS01RUSCNUM" + i + paramInt1 + "0" + paramInt3 + k, "Russian/0" + paramInt3 + ".tga", f, f, f);
      }
      else
      {
        changeMat(paramHierMesh, "Overlay8", "psFCS01RUSCNUM" + i + paramInt1 + paramInt3 + k, "Russian/0" + paramInt3 / 10 + ".tga", "Russian/0" + paramInt3 % 10 + ".tga", f, f, f, f, f, f);
      }

    }
    else if (paramRegiment.country() == PaintScheme.countryFinland)
    {
      super.prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3);

      int j = paramInt3;
      if (j > 5)
      {
        j = 1;
      }
      String str = "VH-" + j;
      changeMat(paramHierMesh, "Overlay2", "psFM01FINACID" + str, "Finnish/" + str + ".tga", 0.0F, 0.0F, 0.0F);

      changeMat(paramHierMesh, "Overlay3", "psFM01FINACID" + str, "Finnish/" + str + ".tga", 0.0F, 0.0F, 0.0F);

      changeMat(paramHierMesh, "Overlay8", "null", "null.tga", 1.0F, 1.0F, 1.0F);
    }
    else {
      super.prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3);
    }
  }
}
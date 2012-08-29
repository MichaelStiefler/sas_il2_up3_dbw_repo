package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

public class PaintSchemeBMPar00s extends PaintSchemeBMPar00
{
  public void prepareNum(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    super.prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3);

    int i = paramRegiment.gruppeNumber() - 1;

    if (paramRegiment.country() == PaintScheme.countrySlovakia)
    {
      int j = World.cur().camouflage;

      float f = 0.85F;
      changeMat(paramHierMesh, "Overlay1", "psBM00SLVKLNUM" + i + paramInt1 + paramInt3 + j, "Slovakian2/S.tga", "Slovakian2/-.tga", f, f, f, f, f, f);

      if (paramInt3 > 9) {
        changeMat(paramHierMesh, "Overlay2", "psBM00SLVKRNUM" + i + paramInt1 + paramInt3 + j, "Slovakian2/" + paramInt3 / 10 + ".tga", "Slovakian2/" + paramInt3 % 10 + ".tga", f, f, f, f, f, f);
      }
      else
      {
        changeMat(paramHierMesh, "Overlay2", "psBM00SLVKRNUM" + i + paramInt1 + paramInt3 + j, "Slovakian2/0.tga", "Slovakian2/" + paramInt3 % 10 + ".tga", f, f, f, f, f, f);
      }

      changeMat(paramClass, paramHierMesh, "Overlay7", "null", "null.tga", 1.0F, 1.0F, 1.0F);

      return;
    }

    if (paramRegiment.country() == PaintScheme.countryGermany)
    {
      changeMat(paramClass, paramHierMesh, "Overlay6", "null", "null.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "null", "null.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay8", "haken1", "German/" + (World.cur().isHakenAllowed() ? "haken1.tga" : "hakenfake.tga"), 1.0F, 1.0F, 1.0F);
      return;
    }
  }
}
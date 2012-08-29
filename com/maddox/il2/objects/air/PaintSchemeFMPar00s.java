package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

public class PaintSchemeFMPar00s extends PaintSchemeFMPar00
{
  public void prepareNum(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    super.prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3);

    int i = paramRegiment.gruppeNumber() - 1;

    if (paramRegiment.country() == PaintScheme.countrySlovakia)
    {
      float f = 0.85F;
      changeMat(paramHierMesh, "Overlay1", "psBM00SLVKLNUM" + i + paramInt1 + paramInt3, "Slovakian2/S.tga", "Slovakian2/-.tga", f, f, f, f, f, f);

      if (paramInt3 > 9) {
        changeMat(paramHierMesh, "Overlay2", "psBM00SLVKRNUM" + i + paramInt1 + paramInt3, "Slovakian2/" + paramInt3 / 10 + ".tga", "Slovakian2/" + paramInt3 % 10 + ".tga", f, f, f, f, f, f);
      }
      else
      {
        changeMat(paramHierMesh, "Overlay2", "psBM00SLVKRNUM" + i + paramInt1 + paramInt3, "Slovakian2/0.tga", "Slovakian2/" + paramInt3 % 10 + ".tga", f, f, f, f, f, f);
      }

      changeMat(paramClass, paramHierMesh, "Overlay7", "null", "null.tga", 1.0F, 1.0F, 1.0F);

      return;
    }

    if (paramRegiment.country() == PaintScheme.countryBritain)
    {
      if ((!"ra".equals(paramRegiment.branch())) && (!"rz".equals(paramRegiment.branch())) && (!"rn".equals(paramRegiment.branch())))
      {
        changeMat(paramClass, paramHierMesh, "Overlay6", "britishroundel4c", "British/roundel4c.tga", 1.0F, 1.0F, 1.0F);

        changeMat(paramClass, paramHierMesh, "Overlay7", "britishroundel3c", "British/roundel3c.tga", 1.0F, 1.0F, 1.0F);
      }

      changeMat(paramClass, paramHierMesh, "Overlay1", "null", "null.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
    }
    else if (paramRegiment.country() == PaintScheme.countryNoName)
    {
      changeMat(paramClass, paramHierMesh, "Overlay1", "null", "null.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
    }
    else
    {
      int j = paramInt1 + 1;

      if (j == 4) {
        j = 0;
      }
      changeMat(paramClass, paramHierMesh, "Overlay1", "null", "null.tga", 1.0F, 1.0F, 1.0F);

      if (paramInt3 >= 20) {
        paramInt3 %= 20;
      }
      if (paramInt3 < 10) {
        changeMat(paramHierMesh, "Overlay2", "psFM00GERRNUM" + i + paramInt1 + paramInt3, "null.tga", "German/0" + j + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay2", "psFM00GERRNUM" + i + paramInt1 + paramInt3, "German/0" + j + paramInt3 / 10 + ".tga", "German/0" + j + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      if (paramRegiment.country() == PaintScheme.countryGermany) {
        changeMat(paramClass, paramHierMesh, "Overlay8", "haken1", "German/" + (World.cur().isHakenAllowed() ? "haken1.tga" : "hakenfake.tga"), 1.0F, 1.0F, 1.0F);
      }
      return;
    }
  }
}
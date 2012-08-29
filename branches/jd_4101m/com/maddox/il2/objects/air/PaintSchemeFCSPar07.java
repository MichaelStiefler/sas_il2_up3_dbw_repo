package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

public class PaintSchemeFCSPar07 extends PaintSchemeFCSPar01
{
  public void prepareNum(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramRegiment.gruppeNumber() - 1;
    int j;
    float f;
    if (paramRegiment.country() == PaintScheme.countryRussia)
    {
      j = World.cur().camouflage;
      if (j == 1)
        f = 0.1F;
      else {
        f = 0.9F;
      }
      if (paramInt3 < 10) {
        changeMat(paramClass, paramHierMesh, "Overlay1", "psFCS01RUSCNUM" + i + paramInt1 + "0" + paramInt3 + j, "Russian/0" + paramInt3 + ".tga", f, f, f);
      }
      else
      {
        changeMat(paramHierMesh, "Overlay1", "psFCS01RUSCNUM" + i + paramInt1 + paramInt3 + j, "Russian/0" + paramInt3 / 10 + ".tga", "Russian/0" + paramInt3 % 10 + ".tga", f, f, f, f, f, f);
      }

      changeMat(paramClass, paramHierMesh, "Overlay6", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay7", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay8", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay2", "psAVGRUSMARKcolor" + paramInt1, "mark.tga", PaintScheme.psRussianBomberColor[paramInt1][0], PaintScheme.psRussianBomberColor[paramInt1][1], PaintScheme.psRussianBomberColor[paramInt1][2]);
    }
    else if (paramRegiment.country() != PaintScheme.countryNoName)
    {
      super.prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3);

      j = World.cur().camouflage;
      if (j == 1)
        f = 0.1F;
      else
        f = 0.9F;
      if (paramInt3 < 10) {
        changeMat(paramClass, paramHierMesh, "Overlay8", "psFCS01RUSCNUM" + i + paramInt1 + "0" + paramInt3 + j, "Russian/0" + paramInt3 + ".tga", f, f, f);
      }
      else
      {
        changeMat(paramHierMesh, "Overlay8", "psFCS01RUSCNUM" + i + paramInt1 + paramInt3 + j, "Russian/0" + paramInt3 / 10 + ".tga", "Russian/0" + paramInt3 % 10 + ".tga", f, f, f, f, f, f);
      }
    }
  }
}
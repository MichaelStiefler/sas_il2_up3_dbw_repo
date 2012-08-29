package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.HierMesh;

public class PaintSchemeFCSPar06 extends PaintSchemeFMPar05
{
  public String typedNameNum(Class paramClass, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramRegiment.gruppeNumber() - 1;

    if (paramRegiment.country() == countryRussia) {
      return "" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString());
    }

    return super.typedNameNum(paramClass, paramRegiment, paramInt1, paramInt2, paramInt3);
  }

  public void prepareNum(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    super.prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3);
    int i = paramRegiment.gruppeNumber() - 1;

    if (paramRegiment.country() == countryRussia) {
      changeMat(paramHierMesh, "Overlay1", "psFCS06RUSCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/3" + paramInt3 / 10 + ".tga", "Russian/3" + paramInt3 % 10 + ".tga", 0.72549F, 0.17647F, 0.10196F, 0.72549F, 0.17647F, 0.10196F);

      changeMat(paramHierMesh, "Overlay4", "psFCS06RUSCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/3" + paramInt3 / 10 + ".tga", "Russian/3" + paramInt3 % 10 + ".tga", 0.72549F, 0.17647F, 0.10196F, 0.72549F, 0.17647F, 0.10196F);

      changeMat(paramClass, paramHierMesh, "Overlay7", "redstar4", "Russian/redstar4.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay8", "redstar4", "Russian/redstar4.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
  }
}
package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.HierMesh;

public class PaintSchemeFMPar00DXXI extends PaintSchemeFMPar01
{
  protected String getFAFACCode(Class paramClass, int paramInt)
  {
    if (paramClass.isAssignableFrom(DXXI_SARJA3_EARLY.class))
      return "FR-" + (paramInt > 1 ? "9" : "8");
    if (paramClass.isAssignableFrom(DXXI_SARJA3_LATE.class))
      return "FR-" + (paramInt > 1 ? "10" : "9");
    if (paramClass.isAssignableFrom(DXXI_SARJA4.class)) {
      return "FR-" + (paramInt > 1 ? "14" : "13");
    }
    return super.getFAFACCode(paramClass, paramInt);
  }

  public void prepareNum(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    super.prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3);

    int i = paramRegiment.gruppeNumber() - 1;
    float f;
    int j;
    if (paramClass.isAssignableFrom(DXXI_DU.class))
    {
      if (paramRegiment.country() == PaintScheme.countryNetherlands)
      {
        f = 1.0F;

        j = 240;

        if (paramInt3 > 20)
        {
          j = 220 + paramInt3 / 20;
        }
        else
        {
          j = 220 + paramInt3;
        }

        if ((j >= 221) && (j <= 240)) {
          changeMat(paramHierMesh, "Overlay1", "psBM00DUTCNUM" + i + paramInt1 + paramInt3, "Dutch/" + j + ".tga", "null.tga", f, f, f, f, f, f);
        }

        return;
      }

      changeMat(paramClass, paramHierMesh, "Overlay1", "null", "null.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramClass.isAssignableFrom(DXXI_DK.class))
    {
      f = 0.0F;

      j = 52;

      if (paramInt3 > 10)
      {
        j = 42 + paramInt3 % 10;
      }
      else
      {
        j = 42 + paramInt3;
      }

      if ((j >= 43) && (j <= 52)) {
        changeMat(paramHierMesh, "Overlay1", "psBM00DUTCNUM" + i + paramInt1 + paramInt3, "Danish/J-" + j + ".tga", "null.tga", f, f, f, f, f, f);
      }
      else
      {
        changeMat(paramClass, paramHierMesh, "Overlay1", "null", "null.tga", 1.0F, 1.0F, 1.0F);
      }
    }
    else if (paramClass.isAssignableFrom(DXXI_SARJA3_SARVANTO.class))
    {
      changeMat(paramClass, paramHierMesh, "Overlay8", "null", "null.tga", 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay2", "psFM01FINACID97", "Finnish/FR-9.tga", "Finnish/sn7.tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

      changeMat(paramHierMesh, "Overlay3", "psFM01FINACID97", "Finnish/FR-9.tga", "Finnish/sn7.tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    }
    else if ((paramRegiment.country() != PaintScheme.countryFinland) && (paramRegiment.country() != PaintScheme.countryNoName))
    {
      if (paramInt1 == 3) {
        changeMat(paramClass, paramHierMesh, "Overlay8", "psFM01FINCNUM" + i + paramInt1 + "_" + (char)(65 + (paramInt3 % 10 - 1)), "Finnish/" + (char)(65 + (paramInt3 % 10 - 1)) + ".tga", 1.0F, 1.0F, 1.0F);
      }
      else if (paramInt3 < 10) {
        changeMat(paramClass, paramHierMesh, "Overlay8", "psFM01FINCNUM" + i + paramInt1 + "0" + paramInt3, "Finnish/0" + paramInt3 + ".tga", 1.0F, 1.0F, 1.0F);
      }
      else
      {
        changeMat(paramHierMesh, "Overlay8", "psFM01FINCNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
    }
  }
}
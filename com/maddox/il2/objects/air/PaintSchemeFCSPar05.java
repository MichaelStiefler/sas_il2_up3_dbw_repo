package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.HierMesh;

public class PaintSchemeFCSPar05 extends PaintSchemeFMPar05
{
  public String typedNameNum(Class paramClass, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramRegiment.gruppeNumber() - 1;

    if (paramRegiment.country() == PaintScheme.countryFrance) {
      if (paramRegiment.fileName().equals("PaintSchemes/Red/NN")) {
        return "" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()) + " *";
      }
      return super.typedNameNum(paramClass, paramRegiment, paramInt1, paramInt2, paramInt3);
    }

    if (paramRegiment.country() == PaintScheme.countryRussia) {
      return "" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString());
    }

    if (paramRegiment.country() == PaintScheme.countryUSA) {
      paramInt3 = clampToLiteral(paramInt3);
      return "" + paramRegiment.id() + " - " + (char)(65 + (paramInt3 - 1));
    }

    return super.typedNameNum(paramClass, paramRegiment, paramInt1, paramInt2, paramInt3);
  }

  public void prepareNum(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    super.prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3);
    int i = paramRegiment.gruppeNumber() - 1;

    if (paramRegiment.country() == PaintScheme.countryFrance) {
      if (paramRegiment.fileName().equals("PaintSchemes/Red/NN")) {
        changeMat(paramHierMesh, "Overlay1", "psFCS05FRACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay4", "psFCS05FRACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay6", "null", "null.tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay7", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay8", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
      }
      else
      {
        if ((paramClass.isAssignableFrom(YAK_1B.class)) || (paramClass.isAssignableFrom(YAK_3.class)) || (paramClass.isAssignableFrom(YAK_9T.class)))
        {
          changeMat(paramHierMesh, "Overlay1", "psFCS05FRACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        return;
      }

      return;
    }

    if (paramRegiment.country() == PaintScheme.countryJapan) {
      changeMat(paramHierMesh, "Overlay1", "psFCS05JAPCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay4", "psFCS05JAPCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay6", "JAR2", "Japanese/JAR2.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == PaintScheme.countryRussia) {
      changeMat(paramHierMesh, "Overlay1", "psFCS05RUSCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/2" + paramInt3 / 10 + ".tga", "Russian/2" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay4", "psFCS05RUSCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/2" + paramInt3 / 10 + ".tga", "Russian/2" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay7", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay8", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == PaintScheme.countryUSA) {
      paramInt3 = clampToLiteral(paramInt3);
      changeMat(paramHierMesh, "Overlay1", "psFCS05USAREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", 0.21F * PaintScheme.psBritishSkyColor[0], 0.21F * PaintScheme.psBritishSkyColor[1], 0.21F * PaintScheme.psBritishSkyColor[2], 0.21F * PaintScheme.psBritishSkyColor[0], 0.21F * PaintScheme.psBritishSkyColor[1], 0.21F * PaintScheme.psBritishSkyColor[2]);

      changeMat(paramHierMesh, "Overlay4", "psFCS05USAREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", 0.21F * PaintScheme.psBritishSkyColor[0], 0.21F * PaintScheme.psBritishSkyColor[1], 0.21F * PaintScheme.psBritishSkyColor[2], 0.21F * PaintScheme.psBritishSkyColor[0], 0.21F * PaintScheme.psBritishSkyColor[1], 0.21F * PaintScheme.psBritishSkyColor[2]);

      changeMat(paramHierMesh, "Overlay2", "psFCS05USALNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "British/" + (char)(65 + paramInt3 - 1) + ".tga", "null.tga", 0.21F * PaintScheme.psBritishSkyColor[0], 0.21F * PaintScheme.psBritishSkyColor[1], 0.21F * PaintScheme.psBritishSkyColor[2], 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay3", "psFCS05USARNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "null.tga", "British/" + (char)(65 + paramInt3 - 1) + ".tga", 1.0F, 1.0F, 1.0F, 0.21F * PaintScheme.psBritishSkyColor[0], 0.21F * PaintScheme.psBritishSkyColor[1], 0.21F * PaintScheme.psBritishSkyColor[2]);
    }
  }
}
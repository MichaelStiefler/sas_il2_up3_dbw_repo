package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.HierMesh;

public class PaintSchemeSpecial extends PaintSchemeFMPar04
{
  public String typedNameNum(Class paramClass, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramClass.isAssignableFrom(BF_109G6GRAF.class)) {
      return "+ 1";
    }
    if (paramClass.isAssignableFrom(BF_109G6HARTMANN.class)) {
      return "1 + -";
    }
    if (paramClass.isAssignableFrom(BF_109G6HEPPES.class)) {
      return "V8 + 10";
    }
    if (paramClass.isAssignableFrom(BF_109G6KOVACS.class)) {
      return "V-3 + 74";
    }
    if (paramClass.isAssignableFrom(BF_109G6MOLNAR.class)) {
      return "+ 66";
    }
    if (paramClass.isAssignableFrom(BF_109G10FABIAN.class)) {
      return "5 +";
    }
    if (paramClass.isAssignableFrom(I_16TYPE24SAFONOV.class)) {
      return "* 11";
    }
    if (paramClass.isAssignableFrom(JU_87G2RUDEL.class)) {
      return "< + -";
    }
    if (paramClass.isAssignableFrom(LA_7KOJEDUB.class)) {
      return "27";
    }
    if (paramClass.isAssignableFrom(ME_262A1ANOWOTNY.class)) {
      return "8 +";
    }
    if (paramClass.isAssignableFrom(MIG_3POKRYSHKIN.class)) {
      return "* 5";
    }
    if (paramClass.isAssignableFrom(MXY_7.class)) {
      return "o";
    }
    if (paramClass.isAssignableFrom(P_39NPOKRYSHKIN.class)) {
      return "* 100";
    }
    if (paramClass.isAssignableFrom(P_39Q15RECHKALOV.class)) {
      return "*";
    }
    if (paramClass.isAssignableFrom(YAK_9TALBERT.class)) {
      return "* 42";
    }

    return super.typedNameNum(paramClass, paramRegiment, paramInt1, paramInt2, paramInt3);
  }

  public void prepareNum(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    super.prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3);
    int i = paramRegiment.gruppeNumber() - 1;

    if ((paramClass.isAssignableFrom(P_39NPOKRYSHKIN.class)) || (paramClass.isAssignableFrom(P_39Q15RECHKALOV.class))) {
      changeMat(paramHierMesh, "Overlay6", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
    }

    if (paramClass.isAssignableFrom(LA_7KOJEDUB.class)) {
      changeMat(paramHierMesh, "Overlay7", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay8", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
    }
  }
}
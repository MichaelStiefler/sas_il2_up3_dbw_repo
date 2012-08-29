package com.maddox.il2.ai;

import com.maddox.rts.SectFile;
import java.io.PrintStream;

public final class StrengthProperties
{
  public float SHOT_MIN_ENERGY = -1.0F;
  public float SHOT_MAX_ENERGY = -1.0F;

  public float EXPLHIT_MIN_TNT = -1.0F;
  public float EXPLHIT_MAX_TNT = -1.0F;

  public float EXPLNEAR_MIN_TNT = -1.0F;
  public float EXPLNEAR_MAX_TNT = -1.0F;

  private static float[] tmpf6 = new float[6];

  private static float[] caliber2energy = { 0.004F, 511.22504F, 0.00762F, 2453.8801F, 0.0127F, 10140.0F, 0.02F, 23400.0F, 0.037F, 131400.0F, 0.045F, 252000.0F, 0.05F, 369000.0F, 0.075F, 1224000.0F, 0.1F, 3295500.0F, 0.203F, 5120000.0F };

  private static float getF(String paramString1, SectFile paramSectFile, String paramString2, String paramString3, float paramFloat1, float paramFloat2)
  {
    float f = paramSectFile.get(paramString2, paramString3, -9865.3447F);
    if ((f == -9865.3447F) || (f < paramFloat1) || (f > paramFloat2)) {
      if (f == -9865.3447F) {
        System.out.println(paramString1 + ": Value of [" + paramString2 + "]:<" + paramString3 + "> " + "not found");
      }
      else {
        System.out.println(paramString1 + ": Value of [" + paramString2 + "]:<" + paramString3 + "> (" + f + ")" + " is out of range (" + paramFloat1 + ";" + paramFloat2 + ")");
      }

      throw new RuntimeException("Can't set property");
    }
    return f;
  }

  private static void tryToReadStrengthProperties(String paramString1, SectFile paramSectFile, String paramString2, float[] paramArrayOfFloat)
  {
    if (paramSectFile.exist(paramString2, "MinShotCaliber")) {
      paramArrayOfFloat[0] = getF(paramString1, paramSectFile, paramString2, "MinShotCaliber", 1.0E-004F, 1.0F);
    }
    if (paramSectFile.exist(paramString2, "NumShots")) {
      paramArrayOfFloat[1] = getF(paramString1, paramSectFile, paramString2, "NumShots", 1.0F, 1000.0F);
    }

    if (paramSectFile.exist(paramString2, "MinHitExplTNT")) {
      paramArrayOfFloat[2] = getF(paramString1, paramSectFile, paramString2, "MinHitExplTNT", 0.01F, 1000.0F);
    }
    if (paramSectFile.exist(paramString2, "NumHitExpl")) {
      paramArrayOfFloat[3] = getF(paramString1, paramSectFile, paramString2, "NumHitExpl", 1.0F, 1000.0F);
    }

    if (paramSectFile.exist(paramString2, "MinNearExplTNT")) {
      paramArrayOfFloat[4] = getF(paramString1, paramSectFile, paramString2, "MinNearExplTNT", 0.01F, 1000.0F);
    }
    if (paramSectFile.exist(paramString2, "NumNearExpl"))
      paramArrayOfFloat[5] = getF(paramString1, paramSectFile, paramString2, "NumNearExpl", 1.0F, 1000.0F);
  }

  public boolean read(String paramString1, SectFile paramSectFile, String paramString2, String paramString3)
  {
    for (int i = 0; i < 6; i++) {
      tmpf6[i] = -1.0F;
    }

    if (paramString2 != null) {
      tryToReadStrengthProperties(paramString1, paramSectFile, paramString2, tmpf6);
    }
    tryToReadStrengthProperties(paramString1, paramSectFile, paramString3, tmpf6);

    for (i = 0; i < 6; i++) {
      if (tmpf6[i] < 0.0F) {
        System.out.println(paramString1 + ": " + "Not enough strength data  in '" + paramString3 + "'");

        return false;
      }
    }

    this.SHOT_MIN_ENERGY = -1.0F;
    for (i = 0; i < caliber2energy.length; i += 2) {
      if (caliber2energy[i] == tmpf6[0]) {
        this.SHOT_MIN_ENERGY = caliber2energy[(i + 1)];
      }
    }
    if (this.SHOT_MIN_ENERGY <= 0.0F) {
      System.out.println(paramString1 + ": " + "Unknown shot caliber for '" + paramString3 + "'");

      return false;
    }
    this.SHOT_MAX_ENERGY = this.SHOT_MIN_ENERGY;
    this.SHOT_MAX_ENERGY *= tmpf6[1];

    this.EXPLHIT_MIN_TNT = tmpf6[2];
    this.EXPLHIT_MAX_TNT = this.EXPLHIT_MIN_TNT;
    this.EXPLHIT_MAX_TNT *= tmpf6[3];

    this.EXPLNEAR_MIN_TNT = tmpf6[4];
    this.EXPLNEAR_MAX_TNT = this.EXPLNEAR_MIN_TNT;
    this.EXPLNEAR_MAX_TNT *= tmpf6[5];

    return true;
  }
}
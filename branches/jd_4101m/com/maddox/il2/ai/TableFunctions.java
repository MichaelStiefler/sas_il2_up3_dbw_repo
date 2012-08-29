package com.maddox.il2.ai;

import com.maddox.il2.objects.Statics;
import com.maddox.rts.SectFile;
import com.maddox.util.TableFunction2;

public class TableFunctions
{
  private static boolean bInited = false;

  public static TableFunction2 GetFunc2(String paramString)
  {
    init();
    return TableFunction2.Get(paramString);
  }

  private static void init()
  {
    if (bInited)
      return;
    SectFile localSectFile = Statics.getTechnicsFile();
    TableFunction2.Load("ArtilleryShotPanzer", localSectFile, "_ArtilleryShotPanzer_", -1, 1);
    TableFunction2.Load("TankShotPanzer", localSectFile, "_TankShotPanzer_", -1, 1);
    TableFunction2.Load("CarShotPanzer", localSectFile, "_CarShotPanzer_", -1, 1);
    TableFunction2.Load("PlaneShotPanzer", localSectFile, "_PlaneShotPanzer_", -1, 1);

    TableFunction2.Load("ArtilleryExplodePanzer", localSectFile, "_ArtilleryExplodePanzer_", -1, 1);
    TableFunction2.Load("TankExplodePanzer", localSectFile, "_TankExplodePanzer_", -1, 1);
    TableFunction2.Load("CarExplodePanzer", localSectFile, "_CarExplodePanzer_", -1, 1);
    TableFunction2.Load("PlaneExplodePanzer", localSectFile, "_PlaneExplodePanzer_", -1, 1);
    bInited = true;
  }
}
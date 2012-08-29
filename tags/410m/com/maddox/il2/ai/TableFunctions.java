// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TableFunctions.java

package com.maddox.il2.ai;

import com.maddox.il2.objects.Statics;
import com.maddox.util.TableFunction2;

public class TableFunctions
{

    public TableFunctions()
    {
    }

    public static com.maddox.util.TableFunction2 GetFunc2(java.lang.String s)
    {
        com.maddox.il2.ai.TableFunctions.init();
        return com.maddox.util.TableFunction2.Get(s);
    }

    private static void init()
    {
        if(bInited)
        {
            return;
        } else
        {
            com.maddox.rts.SectFile sectfile = com.maddox.il2.objects.Statics.getTechnicsFile();
            com.maddox.util.TableFunction2.Load("ArtilleryShotPanzer", sectfile, "_ArtilleryShotPanzer_", -1, 1);
            com.maddox.util.TableFunction2.Load("TankShotPanzer", sectfile, "_TankShotPanzer_", -1, 1);
            com.maddox.util.TableFunction2.Load("CarShotPanzer", sectfile, "_CarShotPanzer_", -1, 1);
            com.maddox.util.TableFunction2.Load("PlaneShotPanzer", sectfile, "_PlaneShotPanzer_", -1, 1);
            com.maddox.util.TableFunction2.Load("ArtilleryExplodePanzer", sectfile, "_ArtilleryExplodePanzer_", -1, 1);
            com.maddox.util.TableFunction2.Load("TankExplodePanzer", sectfile, "_TankExplodePanzer_", -1, 1);
            com.maddox.util.TableFunction2.Load("CarExplodePanzer", sectfile, "_CarExplodePanzer_", -1, 1);
            com.maddox.util.TableFunction2.Load("PlaneExplodePanzer", sectfile, "_PlaneExplodePanzer_", -1, 1);
            bInited = true;
            return;
        }
    }

    private static boolean bInited = false;

}

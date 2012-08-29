// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ToKGUtils.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            BombGunNull

public class ToKGUtils
{

    public ToKGUtils()
    {
    }

    public static void setTorpedoGyroAngle(com.maddox.il2.fm.FlightModel flightmodel, float f, float f1)
    {
        double d = 0.017453292519943278D;
        float f2 = 0.0F;
        double d1 = 80D;
        double d2 = 40D;
        double d3 = 21D;
        double d4 = 2000D;
        double d5 = (double)f * d;
        double d6 = (double)f1 * 0.51444400000000001D;
        if(flightmodel.CT.Weapons[3] != null)
        {
            int i = 0;
            do
            {
                if(i >= flightmodel.CT.Weapons[3].length)
                    break;
                if(flightmodel.CT.Weapons[3][i] != null && !(flightmodel.CT.Weapons[3][i] instanceof com.maddox.il2.objects.weapons.BombGunNull) && flightmodel.CT.Weapons[3][i].countBullets() != 0)
                {
                    java.lang.Class class1 = flightmodel.CT.Weapons[3][0].getClass();
                    java.lang.Class class2 = (java.lang.Class)com.maddox.rts.Property.value(class1, "bulletClass", null);
                    d3 = com.maddox.rts.Property.floatValue(class2, "velocity", 21F);
                    d1 = (double)com.maddox.rts.Property.floatValue(class2, "dropSpeed", 200F) / 3.6000000000000001D;
                    d2 = com.maddox.rts.Property.floatValue(class2, "dropAltitude", 40F);
                    break;
                }
                i++;
            } while(true);
        }
        double d7 = java.lang.Math.sqrt((2D * d2) / (double)com.maddox.il2.fm.Atmosphere.g());
        double d8 = java.lang.Math.atan2(d6 * d7 * java.lang.Math.sin(d5), d4 - (d1 + d6 * java.lang.Math.cos(d5)) * d7);
        double d9 = d8 + d5;
        double d10 = java.lang.Math.asin((d6 / d3) * java.lang.Math.sin(d9));
        f2 = (float)((d10 + d8) / d);
        flightmodel.AS.setGyroAngle(f2);
    }

    private static void mydebug(java.lang.String s)
    {
    }
}

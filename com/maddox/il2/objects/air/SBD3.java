// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SBD3.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            SBD, PaintSchemeFMPar01, TypeStormovik, TypeDiveBomber, 
//            Aircraft, NetAircraft

public class SBD3 extends com.maddox.il2.objects.air.SBD
    implements com.maddox.il2.objects.air.TypeStormovik, com.maddox.il2.objects.air.TypeDiveBomber
{

    public SBD3()
    {
        flapps = 0.0F;
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        switch(i)
        {
        case 0: // '\0'
            if(f < -135F)
                f = -135F;
            if(f > 135F)
                f = 135F;
            if(f1 < -69F)
            {
                f1 = -69F;
                flag = false;
            }
            if(f1 > 45F)
            {
                f1 = 45F;
                flag = false;
            }
            float f2;
            for(f2 = java.lang.Math.abs(f); f2 > 180F; f2 -= 180F);
            if(f1 < -com.maddox.il2.objects.air.SBD3.floatindex(com.maddox.il2.objects.air.Aircraft.cvt(f2, 0.0F, 180F, 0.0F, 36F), af))
                f1 = -com.maddox.il2.objects.air.SBD3.floatindex(com.maddox.il2.objects.air.Aircraft.cvt(f2, 0.0F, 180F, 0.0F, 36F), af);
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    private static final float floatindex(float f, float af[])
    {
        int i = (int)f;
        if(i >= af.length - 1)
            return af[af.length - 1];
        if(i < 0)
            return af[0];
        if(i == 0)
        {
            if(f > 0.0F)
                return af[0] + f * (af[1] - af[0]);
            else
                return af[0];
        } else
        {
            return af[i] + (f % (float)i) * (af[i + 1] - af[i]);
        }
    }

    public boolean typeDiveBomberToggleAutomation()
    {
        return false;
    }

    public void typeDiveBomberAdjAltitudeReset()
    {
    }

    public void typeDiveBomberAdjAltitudePlus()
    {
    }

    public void typeDiveBomberAdjAltitudeMinus()
    {
    }

    public void typeDiveBomberAdjVelocityReset()
    {
    }

    public void typeDiveBomberAdjVelocityPlus()
    {
    }

    public void typeDiveBomberAdjVelocityMinus()
    {
    }

    public void typeDiveBomberAdjDiveAngleReset()
    {
    }

    public void typeDiveBomberAdjDiveAnglePlus()
    {
    }

    public void typeDiveBomberAdjDiveAngleMinus()
    {
    }

    public void typeDiveBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
    }

    public void typeDiveBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
    }

    public void update(float f)
    {
        super.update(f);
        float f1 = FM.EI.engines[0].getControlRadiator();
        if(java.lang.Math.abs(flapps - f1) > 0.01F)
        {
            flapps = f1;
            hierMesh().chunkSetAngles("Oil_D0", 0.0F, -22F * f1, 0.0F);
            for(int i = 1; i < 7; i++)
                hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, -22F * f1, 0.0F);

        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private static final float angles[] = {
        4F, 5.5F, 5.5F, 7F, 10.5F, 15.5F, 24F, 33F, 40F, 46F, 
        52.5F, 59F, 64.5F, 69F, 69F, 69F, 69F, 69F, 69F, 69F, 
        69F, 69F, 69F, 66.5F, 62.5F, 55F, 49.5F, -40F, -74.5F, -77F, 
        -77F, -77F, -77F, -77F, -77F, -77F, -77F
    };
    private float flapps;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.SBD3.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "SBD");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/SBD-3(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/SBD-3(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1946.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/SBD-3.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitSBD3.class, com.maddox.il2.objects.air.CockpitSBD3_TGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.1058F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 10, 10, 3, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb01"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50s 350", "MGunBrowning50s 350", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x250", new java.lang.String[] {
            "MGunBrowning50s 350", "MGunBrowning50s 350", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun250lbs"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunBrowning50s 350", "MGunBrowning50s 350", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun250lbs", "BombGun250lbs", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3x250", new java.lang.String[] {
            "MGunBrowning50s 350", "MGunBrowning50s 350", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun250lbs", "BombGun250lbs", "BombGun250lbs"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x500", new java.lang.String[] {
            "MGunBrowning50s 350", "MGunBrowning50s 350", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun500lbs"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning50s 350", "MGunBrowning50s 350", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun500lbs", "BombGun500lbs", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3x500", new java.lang.String[] {
            "MGunBrowning50s 350", "MGunBrowning50s 350", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun500lbs", "BombGun500lbs", "BombGun500lbs"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x1000", new java.lang.String[] {
            "MGunBrowning50s 350", "MGunBrowning50s 350", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun1000lbs"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x1600", new java.lang.String[] {
            "MGunBrowning50s 350", "MGunBrowning50s 350", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun1600lbs"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null
        });
    }
}

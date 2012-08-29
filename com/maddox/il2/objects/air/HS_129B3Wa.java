// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HS_129B3Wa.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            HS_129, PaintSchemeBMPar02, Aircraft, NetAircraft

public class HS_129B3Wa extends com.maddox.il2.objects.air.HS_129
{

    public HS_129B3Wa()
    {
        phase = 0;
        disp = 0.0F;
        oldbullets = -1;
        g1 = null;
    }

    public void onAircraftLoaded()
    {
        if(FM.CT.Weapons[1] != null)
            g1 = FM.CT.Weapons[1][0];
    }

    public void update(float f)
    {
        if(g1 != null)
            switch(phase)
            {
            default:
                break;

            case 0: // '\0'
                if(g1.isShots() && oldbullets != g1.countBullets())
                {
                    oldbullets = g1.countBullets();
                    phase++;
                    hierMesh().chunkVisible("Shell_D0", true);
                    disp = 0.0F;
                }
                break;

            case 1: // '\001'
                disp += 4.5F * f;
                resetYPRmodifier();
                com.maddox.il2.objects.air.Aircraft.xyz[0] = disp;
                hierMesh().chunkSetLocate("Barrel_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                hierMesh().chunkSetLocate("Shell_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                hierMesh().chunkSetLocate("Sled_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                if(disp >= 0.75F)
                    phase++;
                break;

            case 2: // '\002'
                com.maddox.il2.objects.Wreckage wreckage = new Wreckage(this, hierMesh().chunkFind("Shell_D0"));
                com.maddox.il2.engine.Eff3DActor.New(wreckage, null, null, 1.0F, com.maddox.il2.objects.Wreckage.SMOKE, 3F);
                com.maddox.JGP.Vector3d vector3d = new Vector3d();
                vector3d.set(FM.Vwld);
                wreckage.setSpeed(vector3d);
                hierMesh().chunkVisible("Shell_D0", false);
                phase++;
                break;

            case 3: // '\003'
                disp -= 0.375F * f;
                resetYPRmodifier();
                com.maddox.il2.objects.air.Aircraft.xyz[0] = disp;
                hierMesh().chunkSetLocate("Barrel_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                hierMesh().chunkSetLocate("Shell_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                hierMesh().chunkSetLocate("Sled_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                if(disp <= 0.0F)
                {
                    disp = 0.0F;
                    com.maddox.il2.objects.air.Aircraft.xyz[0] = disp;
                    hierMesh().chunkSetLocate("Barrel_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                    hierMesh().chunkSetLocate("Shell_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                    hierMesh().chunkSetLocate("Sled_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                    phase++;
                }
                break;

            case 4: // '\004'
                phase = 0;
                break;
            }
        super.update(f);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private int phase;
    private float disp;
    private int oldbullets;
    private com.maddox.il2.ai.BulletEmitter g1;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.HS_129B3Wa.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Hs-129");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Hs-129B-3Wa/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1943.9F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Hs-129B-2.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_HEAVYCANNON01"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG15120s 125", "MGunMG15120s 125", "MGunPaK40 12"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null
        });
    }
}

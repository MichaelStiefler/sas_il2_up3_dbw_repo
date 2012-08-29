// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 7/28/2011 8:17:46 AM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: fullnames 
// Source File Name:   BF_109D1.java

package com.maddox.il2.objects.air;

import com.maddox.il2.fm.FlightModelMain;
import java.util.Random;

// Referenced classes of package com.maddox.il2.objects.air:
//            BF_109, CockpitBF_109Bx, PaintSchemeFMPar01, Aircraft, 
//            Cockpit, NetAircraft

public class BF_109D1 extends com.maddox.il2.objects.air.BF_109B1
{
    public BF_109D1()
    {
        burst_fire = new int[2][2];
    }

    public void update(float f)
    {
    	if(getGunByHookName("_CANNON03") instanceof com.maddox.il2.objects.weapons.MGunMGFFki)
        {
            hierMesh().chunkVisible("NoseCannon1_D0", true);
            if(((FlightModelMain) (super.FM)).EI.engines[0].tOilOut > 70F || ((FlightModelMain) (super.FM)).EI.engines[0].getControlThrottle() > 0.8F)
            {
                Random random = new Random();
                int i = 1;
                if(FM.CT.WeaponControl[i])
                {
                    for(int j = 0; j < 1; j++)
                    {
                        int l = FM.CT.Weapons[i][j].countBullets();
                        if(l < burst_fire[j][1])
                        {
                            burst_fire[j][0]++;
                            burst_fire[j][1] = l;
                            int i1 = Math.abs(random.nextInt()) % 100;
                            float f1 = (float)burst_fire[j][0] * 1.0F;
                            if((float)i1 < f1)
                                FM.AS.setJamBullets(i, j);
                        }
                    }

                } else
                {
                    for(int k = 0; k < 1; k++)
                    {
                        burst_fire[k][0] = 0;
                        burst_fire[k][1] = FM.CT.Weapons[i][k].countBullets();
                    }

                }
            }
        }
        super.update(f);
    }
    
    private int burst_fire[][];

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.BF_109D1.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Bf109");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/BF_109D1/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        
        com.maddox.rts.Property.set(class1, "meshName_de", "3DO/Plane/BF_109D1/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_de", new PaintSchemeFCSPar01());
        
        
        com.maddox.rts.Property.set(class1, "yearService", 1938F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1941F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Bf-109D-1SAS.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitBF_109Bx.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.74985F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 1
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON03", "_CANNON01", "_CANNON02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, "MGunMG17k 420", "MGunMG17k 420"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "C2", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMGFFki 60", "MGunMG17k 420", "MGunMG17k 420"
        });    
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null
        });
    }
}
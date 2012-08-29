package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;
import java.util.Random;

public class SPITFIRE1B extends SPITFIRE
{

    public SPITFIRE1B()
    {
        burst_fire = new int[2][2];
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(super.FM.isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        if(super.FM.isPlayers())
            bChangedPit = true;
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        Aircraft.xyz[1] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.55F);
        hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
        float f1 = (float)java.lang.Math.sin(Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 3.141593F));
        hierMesh().chunkSetAngles("Pilot1_D0", 0.0F, 0.0F, 9F * f1);
        hierMesh().chunkSetAngles("Head1_D0", 12F * f1, 0.0F, 0.0F);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public void update(float f)
    {
        java.util.Random random = new Random();
        int i = 1;
        if(super.FM.getOverload() > 2.0F || super.FM.getOverload() < 0.0F || super.FM.getAltitude() > 5000F)
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.WeaponControl[i])
            {
                for(int j = 0; j < 2; j++)
                {
                    int l = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons[i][j].countBullets();
                    if(l < burst_fire[j][1])
                    {
                        burst_fire[j][0]++;
                        burst_fire[j][1] = l;
                        int i1 = java.lang.Math.abs(random.nextInt()) % 100;
                        float f1 = (float)burst_fire[j][0] * 1.0F;
                        if((float)i1 < f1)
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(i, j);
                    }
                }

            } else
            {
                for(int k = 0; k < 2; k++)
                {
                    burst_fire[k][0] = 0;
                    burst_fire[k][1] = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons[i][k].countBullets();
                }

            }
        super.update(f);
    }

    public static boolean bChangedPit = false;
    private int burst_fire[][];

    static 
    {
        java.lang.Class class1 = SPITFIRE1B.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Spit");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/SpitfireMk1b(Multi1)/Spitfire1b.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar03());
        com.maddox.rts.Property.set(class1, "meshName_gb", "3DO/Plane/SpitfireMk1b(Multi1)/Spitfire1b.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_gb", new PaintSchemeSPIT1());
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1946.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/SpitfireIb.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitSpit1.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.5926F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 60", "MGunHispanoMkIki 60"
        });
        Aircraft.weaponsRegister(class1, "NoMG", new java.lang.String[] {
            null, null, null, null, "MGunHispanoMkIki 60", "MGunHispanoMkIki 60"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}

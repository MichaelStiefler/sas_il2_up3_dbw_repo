// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   A_20G.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.weapons.BombGunTorpMk13;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            A_20, PaintSchemeBMPar05, PaintSchemeBMPar03, TypeStormovik, 
//            Cockpit, NetAircraft

public class A_20G extends com.maddox.il2.objects.air.A_20
    implements com.maddox.il2.objects.air.TypeStormovik
{

    public A_20G()
    {
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int i;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                i = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte0 = 2;
                i = s.charAt(6) - 49;
            } else
            {
                i = s.charAt(5) - 49;
            }
            if(i == 2)
                i = 1;
            hitFlesh(i, shot, byte0);
            return;
        } else
        {
            super.hitBone(s, shot, point3d);
            return;
        }
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            if(f1 > 89F)
            {
                f1 = 89F;
                flag = false;
            }
            float f2 = java.lang.Math.abs(f);
            if(f1 < com.maddox.il2.objects.air.A_20G.cvt(f2, 140F, 180F, -1F, 25F))
                f1 = com.maddox.il2.objects.air.A_20G.cvt(f2, 140F, 180F, -1F, 25F);
            break;

        case 1: // '\001'
            if(f < -30F)
            {
                f = -30F;
                flag = false;
            }
            if(f > 30F)
            {
                f = 30F;
                flag = false;
            }
            if(f1 < -45F)
            {
                f1 = -45F;
                flag = false;
            }
            if(f1 > 15F)
            {
                f1 = 15F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    public void doWoundPilot(int i, float f)
    {
        switch(i)
        {
        case 1: // '\001'
            FM.turret[0].setHealth(f);
            break;

        case 2: // '\002'
            FM.turret[1].setHealth(f);
            break;
        }
    }

    public void moveCockpitDoor(float f)
    {
        hierMesh().chunkSetAngles("Blister1_D0", 0.0F, com.maddox.il2.objects.air.A_20G.cvt(f, 0.01F, 0.99F, 0.0F, -120F), 0.0F);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag)
        {
            if(FM.AS.astateEngineStates[0] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.0023F)
                FM.AS.hitTank(this, 0, 1);
            if(FM.AS.astateEngineStates[1] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.0023F)
                FM.AS.hitTank(this, 1, 1);
            if(FM.AS.astateEngineStates[2] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.0023F)
                FM.AS.hitTank(this, 2, 1);
            if(FM.AS.astateEngineStates[3] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.0023F)
                FM.AS.hitTank(this, 3, 1);
        }
        for(int i = 1; i < 4; i++)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask" + i + "_D0", false);
            else
                hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));

    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(FM.CT.Weapons[3] != null && (FM.CT.Weapons[3][0] instanceof com.maddox.il2.objects.weapons.BombGunTorpMk13))
        {
            hierMesh().chunkVisible("Bay1_D0", false);
            hierMesh().chunkVisible("Bay2_D0", false);
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "A-20");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/A-20G(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar05());
        com.maddox.rts.Property.set(class1, "meshName_gb", "3DO/Plane/A-20G(AU)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_gb", new PaintSchemeBMPar05());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/A-20G(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeBMPar03());
        com.maddox.rts.Property.set(class1, "noseart", 1);
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1965.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/A-20G.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitA_20G.class, com.maddox.il2.objects.air.CockpitA_20G_TGunner.class, com.maddox.il2.objects.air.CockpitA_20G_BGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.92575F);
        com.maddox.il2.objects.air.A_20G.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 10, 10, 11, 3, 
            3, 3, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.A_20G.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_MGUN09", "_ExternalBomb01", 
            "_ExternalBomb02", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05"
        });
        com.maddox.il2.objects.air.A_20G.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.A_20G.weaponsRegister(class1, "40xParaF", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, 
            null, "BombGunParafrag8 20", "BombGunParafrag8 20", null, null, null
        });
        com.maddox.il2.objects.air.A_20G.weaponsRegister(class1, "2x100", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, 
            null, "BombGunFAB50", "BombGunFAB50", null, null, null
        });
        com.maddox.il2.objects.air.A_20G.weaponsRegister(class1, "2x1008x100", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, 
            null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50 4", "BombGunFAB50 4", null
        });
        com.maddox.il2.objects.air.A_20G.weaponsRegister(class1, "2x1008x1002x100", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", "BombGunFAB50", 
            "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50 4", "BombGunFAB50 4", null
        });
        com.maddox.il2.objects.air.A_20G.weaponsRegister(class1, "2x3004x300", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, 
            null, "BombGun300lbs", "BombGun300lbs", "BombGun300lbs 2", "BombGun300lbs 2", null
        });
        com.maddox.il2.objects.air.A_20G.weaponsRegister(class1, "2x3004x3002x100", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", "BombGunFAB50", 
            "BombGunFAB50", "BombGun300lbs", "BombGun300lbs", "BombGun300lbs 2", "BombGun300lbs 2", null
        });
        com.maddox.il2.objects.air.A_20G.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, 
            null, "BombGun500lbs", "BombGun500lbs", null, null, null
        });
        com.maddox.il2.objects.air.A_20G.weaponsRegister(class1, "2x5008x100", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, 
            null, "BombGun500lbs", "BombGun500lbs", "BombGunFAB50 4", "BombGunFAB50 4", null
        });
        com.maddox.il2.objects.air.A_20G.weaponsRegister(class1, "2x5004x300", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, 
            null, "BombGun500lbs", "BombGun500lbs", "BombGun300lbs 2", "BombGun300lbs 2", null
        });
        com.maddox.il2.objects.air.A_20G.weaponsRegister(class1, "2x5002x500", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", "BombGun500lbs", 
            "BombGun500lbs", "BombGun500lbs", "BombGun500lbs", null, null, null
        });
        com.maddox.il2.objects.air.A_20G.weaponsRegister(class1, "1x1000", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, 
            null, null, null, null, null, "BombGun1000lbs"
        });
        com.maddox.il2.objects.air.A_20G.weaponsRegister(class1, "1x10008x100", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, 
            null, null, null, "BombGunFAB50 4", "BombGunFAB50 4", "BombGun1000lbs"
        });
        com.maddox.il2.objects.air.A_20G.weaponsRegister(class1, "1x10004x300", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, 
            null, null, null, "BombGun300lbs 2", "BombGun300lbs 2", "BombGun1000lbs"
        });
        com.maddox.il2.objects.air.A_20G.weaponsRegister(class1, "1x10002x500", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", "BombGun500lbs", 
            "BombGun500lbs", null, null, null, null, "BombGun1000lbs"
        });
        com.maddox.il2.objects.air.A_20G.weaponsRegister(class1, "1xmk13", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, 
            null, null, null, null, null, "BombGunTorpMk13"
        });
        com.maddox.il2.objects.air.A_20G.weaponsRegister(class1, "1x53mmCirc", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, 
            null, null, null, null, null, "BombGunTorp45_36AV_A"
        });
        com.maddox.il2.objects.air.A_20G.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
    }
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   DO_335V13.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            DO_335, PaintSchemeFMPar05, NetAircraft

public class DO_335V13 extends com.maddox.il2.objects.air.DO_335
{

    public DO_335V13()
    {
        bKeelUp = true;
    }

    public void update(float f)
    {
        hierMesh().chunkSetAngles("Water1_D0", 0.0F, -20F * FM.EI.engines[1].getControlRadiator(), 0.0F);
        hierMesh().chunkSetAngles("Water2_D0", 0.0F, -10F * FM.EI.engines[1].getControlRadiator(), 0.0F);
        hierMesh().chunkSetAngles("Water3_D0", 0.0F, -10F * FM.EI.engines[1].getControlRadiator(), 0.0F);
        for(int i = 2; i < 8; i++)
            hierMesh().chunkSetAngles("Cowflap" + i + "_D0", 0.0F, -30F * FM.EI.engines[0].getControlRadiator(), 0.0F);

        super.update(f);
        if(FM.AS.isMaster() && bKeelUp && FM.AS.astateBailoutStep == 3)
        {
            FM.AS.setInternalDamage(this, 5);
            FM.AS.setInternalDamage(this, 4);
            bKeelUp = false;
        }
    }

    public final void doKeelShutoff()
    {
        nextDMGLevels(4, 2, "Keel1_D" + chunkDamageVisible("Keel1"), this);
        oldProp[1] = 99;
        com.maddox.il2.objects.Wreckage wreckage;
        if(hierMesh().isChunkVisible("Prop2_D1"))
            wreckage = new Wreckage(this, hierMesh().chunkFind("Prop2_D1"));
        else
            wreckage = new Wreckage(this, hierMesh().chunkFind("Prop2_D0"));
        com.maddox.il2.engine.Eff3DActor.New(wreckage, null, null, 1.0F, com.maddox.il2.objects.Wreckage.SMOKE, 3F);
        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        vector3d.set(FM.Vwld);
        wreckage.setSpeed(vector3d);
        hierMesh().chunkVisible("Prop2_D0", false);
        hierMesh().chunkVisible("Prop2_D1", false);
        hierMesh().chunkVisible("PropRot2_D0", false);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xxcannon04"))
        {
            debuggunnery("Armament System: Left Wing Cannon: Disabled..");
            FM.AS.setJamBullets(1, 1);
            getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(6.98F, 24.35F), shot);
            return;
        }
        if(s.startsWith("xxcannon05"))
        {
            debuggunnery("Armament System: Right Wing Cannon: Disabled..");
            FM.AS.setJamBullets(1, 2);
            getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(6.98F, 24.35F), shot);
            return;
        } else
        {
            super.hitBone(s, shot, point3d);
            return;
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private boolean bKeelUp;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Do-335");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Do-335V-13/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1946F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Do-335V-13.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitDO_335V13.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.00705F);
        com.maddox.il2.objects.air.DO_335V13.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 1, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.DO_335V13.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN02", "_MGUN03", "_MGUN01", "_MGUN04", "_MGUN05", "_BombSpawn01", "_BombSpawn02", "_BombSpawn01", "_BombSpawn03", "_BombSpawn01", 
            "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_BombSpawn08", "_BombSpawn09", "_BombSpawn10", "_BombSpawn11"
        });
        com.maddox.il2.objects.air.DO_335V13.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "MGunMK103k 70", "MGunMK103k 70", null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.DO_335V13.weaponsRegister(class1, "8sc50", new java.lang.String[] {
            "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "MGunMK103k 70", "MGunMK103k 70", null, null, null, null, null, 
            "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50"
        });
        com.maddox.il2.objects.air.DO_335V13.weaponsRegister(class1, "8sc70", new java.lang.String[] {
            "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "MGunMK103k 70", "MGunMK103k 70", null, null, null, null, null, 
            "BombGunSC70", "BombGunSC70", "BombGunSC70", "BombGunSC70", "BombGunSC70", "BombGunSC70", "BombGunSC70", "BombGunSC70"
        });
        com.maddox.il2.objects.air.DO_335V13.weaponsRegister(class1, "2sc250", new java.lang.String[] {
            "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "MGunMK103k 70", "MGunMK103k 70", null, "BombGunSC250", "BombGunNull", "BombGunSC250", "BombGunNull", 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.DO_335V13.weaponsRegister(class1, "2xab250", new java.lang.String[] {
            "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "MGunMK103k 70", "MGunMK103k 70", null, "BombGunAB250", "BombGunNull", "BombGunAB250", "BombGunNull", 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.DO_335V13.weaponsRegister(class1, "1sc500", new java.lang.String[] {
            "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "MGunMK103k 70", "MGunMK103k 70", "BombGunSC500", null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.DO_335V13.weaponsRegister(class1, "1sd500", new java.lang.String[] {
            "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "MGunMK103k 70", "MGunMK103k 70", "BombGunSD500", null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.DO_335V13.weaponsRegister(class1, "1ab500", new java.lang.String[] {
            "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "MGunMK103k 70", "MGunMK103k 70", "BombGunAB500", null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.DO_335V13.weaponsRegister(class1, "1sc1000", new java.lang.String[] {
            "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "MGunMK103k 70", "MGunMK103k 70", "BombGunSC1000", null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.DO_335V13.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
    }
}

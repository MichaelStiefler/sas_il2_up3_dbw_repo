// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SEAFIRE3F.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            SPITFIRE5, PaintSchemeFMPar04, Cockpit, NetAircraft

public class SEAFIRE3F extends com.maddox.il2.objects.air.SPITFIRE5
{

    public SEAFIRE3F()
    {
        arrestor = 0.0F;
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if(isNet() && isNetMirror())
            return;
        if(s.startsWith("Hook"))
        {
            return;
        } else
        {
            super.msgCollision(actor, s, s1);
            return;
        }
    }

    protected void moveFlap(float f)
    {
        float f1 = -85F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap04_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap05_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap06_D0", 0.0F, f1, 0.0F);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    }

    public void moveArrestorHook(float f)
    {
        hierMesh().chunkSetAngles("Hook1_D0", 0.0F, -57F * f, 0.0F);
        hierMesh().chunkSetAngles("Hook2_D0", 0.0F, -12F * f, 0.0F);
        resetYPRmodifier();
        xyz[2] = 0.1385F * f;
        hierMesh().chunkSetLocate("Hook3_D0", xyz, ypr);
        arrestor = f;
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        xyz[1] = com.maddox.il2.objects.air.SEAFIRE3F.cvt(f, 0.01F, 0.99F, 0.0F, 0.55F);
        hierMesh().chunkSetLocate("Blister1_D0", xyz, ypr);
        float f1 = (float)java.lang.Math.sin(com.maddox.il2.objects.air.SEAFIRE3F.cvt(f, 0.01F, 0.99F, 0.0F, 3.141593F));
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
        super.update(f);
        if(FM.CT.getArrestor() > 0.2F)
            if(FM.Gears.arrestorVAngle != 0.0F)
            {
                float f1 = com.maddox.il2.objects.air.SEAFIRE3F.cvt(FM.Gears.arrestorVAngle, -50F, 7F, 1.0F, 0.0F);
                arrestor = 0.8F * arrestor + 0.2F * f1;
                moveArrestorHook(arrestor);
            } else
            {
                float f2 = (-33F * FM.Gears.arrestorVSink) / 57F;
                if(f2 < 0.0F && FM.getSpeedKMH() > 60F)
                    com.maddox.il2.engine.Eff3DActor.New(this, FM.Gears.arrestorHook, null, 1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
                if(f2 > 0.0F && FM.CT.getArrestor() < 0.95F)
                    f2 = 0.0F;
                if(f2 > 0.2F)
                    f2 = 0.2F;
                if(f2 > 0.0F)
                    arrestor = 0.7F * arrestor + 0.3F * (arrestor + f2);
                else
                    arrestor = 0.3F * arrestor + 0.7F * (arrestor + f2);
                if(arrestor < 0.0F)
                    arrestor = 0.0F;
                else
                if(arrestor > 1.0F)
                    arrestor = 1.0F;
                moveArrestorHook(arrestor);
            }
    }

    protected void moveWingFold(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("WingLMid_D0", 0.0F, -112F * f, 0.0F);
        hiermesh.chunkSetAngles("WingLOut_D0", 0.0F, -112F * f, 0.0F);
        hiermesh.chunkSetAngles("WingRMid_D0", 0.0F, -112F * f, 0.0F);
        hiermesh.chunkSetAngles("WingROut_D0", 0.0F, -112F * f, 0.0F);
    }

    public void moveWingFold(float f)
    {
        moveWingFold(hierMesh(), f);
        if(f < 0.001F)
        {
            setGunPodsOn(true);
            hideWingWeapons(false);
        } else
        {
            setGunPodsOn(false);
            FM.CT.WeaponControl[0] = false;
            hideWingWeapons(true);
        }
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 19: // '\023'
            FM.CT.bHasArrestorControl = false;
            break;
        }
        return super.cutFM(i, j, actor);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private float arrestor;
    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.SEAFIRE3F.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Seafire");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/SeafireMkIII(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1946.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Seafire-F-MkIII.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitSea3.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.06985F);
        com.maddox.il2.objects.air.SEAFIRE3F.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 9, 3, 9, 9, 
            3, 3, 9, 9, 9, 9, 2, 2, 2, 2, 
            9
        });
        com.maddox.il2.objects.air.SEAFIRE3F.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev02", "_ExternalDev03", 
            "_ExternalBomb02", "_ExternalBomb03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", 
            "_ExternalDev08"
        });
        com.maddox.il2.objects.air.SEAFIRE3F.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunHispanoMkIkWF 120", "MGunHispanoMkIkWF 120", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.SEAFIRE3F.weaponsRegister(class1, "1x30dt", new java.lang.String[] {
            "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunHispanoMkIkWF 120", "MGunHispanoMkIkWF 120", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "FuelTankGun_TankSpit30"
        });
        com.maddox.il2.objects.air.SEAFIRE3F.weaponsRegister(class1, "1x45dt", new java.lang.String[] {
            "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunHispanoMkIkWF 120", "MGunHispanoMkIkWF 120", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "FuelTankGun_TankSpit45"
        });
        com.maddox.il2.objects.air.SEAFIRE3F.weaponsRegister(class1, "1x90dt", new java.lang.String[] {
            "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunHispanoMkIkWF 120", "MGunHispanoMkIkWF 120", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "FuelTankGun_TankSpit90"
        });
        com.maddox.il2.objects.air.SEAFIRE3F.weaponsRegister(class1, "1x500", new java.lang.String[] {
            "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunHispanoMkIkWF 120", "MGunHispanoMkIkWF 120", "PylonSpitC", "BombGun500lbsE 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.SEAFIRE3F.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunHispanoMkIkWF 120", "MGunHispanoMkIkWF 120", null, null, "PylonSpitL", "PylonSpitR", 
            "BombGun250lbsE 1", "BombGun250lbsE 1", null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.SEAFIRE3F.weaponsRegister(class1, "4x60hvar", new java.lang.String[] {
            "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunHispanoMkIkWF 120", "MGunHispanoMkIkWF 120", null, null, null, null, 
            null, null, "PylonSpitROCK", "PylonSpitROCK", "PylonSpitROCK", "PylonSpitROCK", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", 
            null
        });
        com.maddox.il2.objects.air.SEAFIRE3F.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
    }
}

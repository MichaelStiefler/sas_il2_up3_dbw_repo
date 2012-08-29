// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   ME_262A1A.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;

// Referenced classes of package com.maddox.il2.objects.air:
//            ME_262, PaintSchemeFMPar05, NetAircraft, Aircraft

public class ME_262C1A extends com.maddox.il2.objects.air.ME_262
{

    public ME_262C1A()
    {
        flame = null;
        dust = null;
        trail = null;
        sprite = null;
        turboexhaust = null;
        bHasEngine = true;
    }

    public void destroy()
    {
        if(Actor.isValid(flame))
            flame.destroy();
        if(Actor.isValid(dust))
            dust.destroy();
        if(Actor.isValid(trail))
            trail.destroy();
        if(Actor.isValid(sprite))
            sprite.destroy();
        if(Actor.isValid(turboexhaust))
            turboexhaust.destroy();
        super.destroy();
    }
    
    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if((getBulletEmitterByHookName("_ExternalBomb01") instanceof com.maddox.il2.objects.weapons.GunEmpty) && (getBulletEmitterByHookName("_ExternalDev03") instanceof com.maddox.il2.objects.weapons.GunEmpty) && (getBulletEmitterByHookName("_ExternalDev05") instanceof com.maddox.il2.objects.weapons.GunEmpty))
            hierMesh().chunkVisible("Pylon_D0", false);
        if(getBulletEmitterByHookName("_CANNON07") instanceof com.maddox.il2.objects.weapons.GunEmpty)
            hierMesh().chunkVisible("MK103", false);
        else
            hierMesh().chunkVisible("MK103", true);
        if(getBulletEmitterByHookName("_CANNON09") instanceof com.maddox.il2.objects.weapons.GunEmpty)
            hierMesh().chunkVisible("MK108", false);
        else
            hierMesh().chunkVisible("MK108", true);
        super.onAircraftLoaded();
        super.FM.isPlayers();
        if(Config.isUSE_RENDER())
        {
            flame = Eff3DActor.New(this, findHook("_Engine3EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboHWK109F.eff", -1F);
            dust = Eff3DActor.New(this, findHook("_Engine3EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboHWK109D.eff", -1F);
            trail = Eff3DActor.New(this, findHook("_Engine3EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboHWK109T.eff", -1F);
            sprite = Eff3DActor.New(this, findHook("_Engine3EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboHWK109S.eff", -1F);
            turboexhaust = Eff3DActor.New(this, findHook("_Engine3ES_02"), null, 1.0F, "3DO/Effects/Aircraft/WhiteOxySmallGND.eff", -1F);
            Eff3DActor.setIntesity(flame, 0.0F);
            Eff3DActor.setIntesity(dust, 0.0F);
            Eff3DActor.setIntesity(trail, 0.0F);
            Eff3DActor.setIntesity(sprite, 0.0F);
            Eff3DActor.setIntesity(turboexhaust, 1.0F);
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(Config.isUSE_RENDER())
        {
            if(oldVwld < 20F && FM.getSpeed() > 20F)
            {
                Eff3DActor.finish(turboexhaust);
                turboexhaust = Eff3DActor.New(this, findHook("_Engine3ES_02"), null, 1.0F, "3DO/Effects/Aircraft/WhiteOxySmallTSPD.eff", -1F);
            }
            if(oldVwld > 20F && FM.getSpeed() < 20F)
            {
                Eff3DActor.finish(turboexhaust);
                turboexhaust = Eff3DActor.New(this, findHook("_Engine3ES_02"), null, 1.0F, "3DO/Effects/Aircraft/WhiteOxySmallGND.eff", -1F);
            }
            oldVwld = FM.getSpeed();
        }
    }
    
    protected boolean cutFM(int i, int j, Actor actor)
    {
        switch(i)
        {
        case 19: // '\023'
            bHasEngine = false;
            FM.AS.setEngineDies(this, 2);
            return cut(Aircraft.partNames()[i]);

        case 3: // '\003'
        case 4: // '\004'
            return false;
        }
        return super.cutFM(i, j, actor);
    }    
    
    public void update(float f)
    {
        super.update(f);
        if(bHasEngine && FM.M.nitro > 0F && FM.EI.engines[2].getStage() > 0)
        {
            //FM.EI.engines[2].setControlThrottle(1.0F);
            FM.CT.setPowerControl(1.0F, 2);
            Eff3DActor.setIntesity(flame, 1.0F);
            Eff3DActor.setIntesity(dust, 1.0F);
            Eff3DActor.setIntesity(trail, 1.0F);
            Eff3DActor.setIntesity(sprite, 1.0F);
        }
        else if(!bHasEngine || FM.M.nitro == 0F || FM.EI.engines[2].getStage() == 0)
        {
            FM.EI.engines[2].setControlThrottle(0.0F);
            FM.CT.setPowerControl(0.0F, 2);
            Eff3DActor.setIntesity(flame, 0.0F);
            Eff3DActor.setIntesity(dust, 0.0F);
            Eff3DActor.setIntesity(trail, 0.0F);
            Eff3DActor.setIntesity(sprite, 0.0F);	
        }
    }
    
    private Eff3DActor flame;
    private Eff3DActor dust;
    private Eff3DActor trail;
    private Eff3DActor sprite;
    private Eff3DActor turboexhaust;
    private float oldVwld;
    private boolean bHasEngine;
    
    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.ME_262C1A.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Me 262");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/Me-262C-1a/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar05())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1944F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Me-262C-1a.fmd:Me-262C");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitME_262.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.74615F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 9, 9, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 9, 9, 3, 3, 0, 0, 1, 1, 
            1, 1, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev01", "_ExternalDev02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", 
            "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", 
            "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", "_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalRock23", "_ExternalRock24", 
            "_ExternalRock25", "_ExternalRock26", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb01", "_ExternalBomb02", "_CANNON05", "_CANNON06", "_CANNON07", "_CANNON08", 
            "_CANNON09", "_CANNON10", "_ExternalDev05", "_ExternalDev06"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "MGunMK108k 80", "MGunMK108k 80", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xTypeD", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "MGunMK108k 80", "MGunMK108k 80", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "FuelTankGun_Type_D", "FuelTankGun_Type_D"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xSC250", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "MGunMK108k 80", "MGunMK108k 80", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "BombGunSC250", "BombGunSC250", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xAB250", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "MGunMK108k 80", "MGunMK108k 80", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "BombGunAB250", "BombGunAB250", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWfrGr21", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "MGunMK108k 80", "MGunMK108k 80", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "RocketGunWfrGr21 1", "RocketGunWfrGr21 1", "PylonRO_WfrGr21_262", "PylonRO_WfrGr21_262", null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "U1", new java.lang.String[] {
            "PylonMG15120Internal", "PylonMG15120Internal", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "MGunMG15120MGki 146", "MGunMG15120MGki 146", "MGunMK103k 72", "MGunMK103k 72", 
            "MGunMK108k 66", "MGunMK108k 66", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "U1_2xTypeD", new java.lang.String[] {
            "PylonMG15120Internal", "PylonMG15120Internal", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "MGunMG15120MGki 146", "MGunMG15120MGki 146", "MGunMK103k 72", "MGunMK103k 72", 
            "MGunMK108k 66", "MGunMK108k 66", "FuelTankGun_Type_D", "FuelTankGun_Type_D"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "U5", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "MGunMK108k 80", "MGunMK108k 80", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "MGunMK108k 66", "MGunMK108k 66", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "U5_2xTypeD", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "MGunMK108k 80", "MGunMK108k 80", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "MGunMK108k 66", "MGunMK108k 66", "FuelTankGun_Type_D", "FuelTankGun_Type_D"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "24r4m", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "MGunMK108k 80", "MGunMK108k 80", "PylonMe262_R4M_Left", "PylonMe262_R4M_Right", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", 
            "RocketGunR4Ms 1", "RocketGunR4Ms 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4Ms 1", "RocketGunR4Ms 1", "RocketGunR4M 1", "RocketGunR4M 1", 
            "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4Ms 1", "RocketGunR4Ms 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4Ms 1", "RocketGunR4Ms 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "24r4m_2xTypeD", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "MGunMK108k 80", "MGunMK108k 80", "PylonMe262_R4M_Left", "PylonMe262_R4M_Right", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", 
            "RocketGunR4Ms 1", "RocketGunR4Ms 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4Ms 1", "RocketGunR4Ms 1", "RocketGunR4M 1", "RocketGunR4M 1", 
            "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4Ms 1", "RocketGunR4Ms 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4Ms 1", "RocketGunR4Ms 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "FuelTankGun_Type_D", "FuelTankGun_Type_D"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
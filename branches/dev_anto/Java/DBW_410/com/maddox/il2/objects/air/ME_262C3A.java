// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 8.6.2010 22:58:10
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ME_262C3A.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.*;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.*;
import com.maddox.il2.fm.*;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.*;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.objects.air:
//            ME_262, TypeStormovik, TypeX4Carrier, TypeBNZFighter, 
//            PaintSchemeFMPar05, Aircraft, CockpitME_262, Cockpit, 
//            NetAircraft

public class ME_262C3A extends ME_262
    implements TypeStormovik, TypeX4Carrier, TypeBNZFighter
{

    public ME_262C3A()
    {
        bToFire = false;
        tX4Prev = 0L;
        deltaAzimuth = 0.0F;
        deltaTangage = 0.0F;
        cockpitDoor_ = 0.0F;
        fMaxKMHSpeedForOpenCanopy = 200F;
        bHasBlister = true;
        flame = null;
        dust = null;
        trail = null;
        sprite = null;
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
        super.destroy();
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if((super.FM instanceof RealFlightModel) && ((RealFlightModel)super.FM).isRealMode() || !flag || !(super.FM instanceof Pilot))
            return;
        Pilot pilot = (Pilot)super.FM;
        if(pilot.get_maneuver() == 63 && ((Maneuver) (pilot)).target != null)
        {
            Point3d point3d = new Point3d(((FlightModelMain) (((Maneuver) (pilot)).target)).Loc);
            point3d.sub(((FlightModelMain) (super.FM)).Loc);
            ((FlightModelMain) (super.FM)).Or.transformInv(point3d);
            if((((Tuple3d) (point3d)).x > 4000D && ((Tuple3d) (point3d)).x < 5500D || ((Tuple3d) (point3d)).x > 100D && ((Tuple3d) (point3d)).x < 5000D && World.Rnd().nextFloat() < 0.33F) && Time.current() > tX4Prev + 10000L)
            {
                bToFire = true;
                tX4Prev = Time.current();
            }
        }
    }

    public void typeX4CAdjSidePlus()
    {
        deltaAzimuth = 1.0F;
    }

    public void typeX4CAdjSideMinus()
    {
        deltaAzimuth = -1F;
    }

    public void typeX4CAdjAttitudePlus()
    {
        deltaTangage = 1.0F;
    }

    public void typeX4CAdjAttitudeMinus()
    {
        deltaTangage = -1F;
    }

    public void typeX4CResetControls()
    {
        deltaAzimuth = deltaTangage = 0.0F;
    }

    public float typeX4CgetdeltaAzimuth()
    {
        return deltaAzimuth;
    }

    public float typeX4CgetdeltaTangage()
    {
        return deltaTangage;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(Config.isUSE_RENDER())
        {
            flame = Eff3DActor.New(this, findHook("_Engine3EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboHWK109F.eff", -1F);
            dust = Eff3DActor.New(this, findHook("_Engine3EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboHWK109D.eff", -1F);
            trail = Eff3DActor.New(this, findHook("_Engine3EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboHWK109T.eff", -1F);
            sprite = Eff3DActor.New(this, findHook("_Engine3EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboHWK109S.eff", -1F);
            Eff3DActor.setIntesity(flame, 0.0F);
            Eff3DActor.setIntesity(dust, 0.0F);
            Eff3DActor.setIntesity(trail, 0.0F);
            Eff3DActor.setIntesity(sprite, 0.0F);
        }
        super.onAircraftLoaded();
        if(getBulletEmitterByHookName("_CANNON07") instanceof GunEmpty)
            hierMesh().chunkVisible("MK103", false);
        else
            hierMesh().chunkVisible("MK103", true);
        if(getBulletEmitterByHookName("_CANNON09") instanceof GunEmpty)
            hierMesh().chunkVisible("MK108", false);
        else
            hierMesh().chunkVisible("MK108", true);
        super.onAircraftLoaded();
        if(super.FM.isPlayers())
        {
            ((FlightModelMain) (super.FM)).CT.bHasCockpitDoorControl = true;
            ((FlightModelMain) (super.FM)).CT.dvCockpitDoor = 1.0F;
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
       if((double)((FlightModelMain) (super.FM)).CT.getCockpitDoor() > 0.20000000000000001D && bHasBlister && super.FM.getSpeedKMH() > fMaxKMHSpeedForOpenCanopy && hierMesh().chunkFindCheck("Blister1_D0") != -1)
        {
            try
            {
                if(this == World.getPlayerAircraft())
                    ((CockpitME_262)Main3D.cur3D().cockpitCur).removeCanopy();
            }
            catch(Exception exception) { }
            hierMesh().hideSubTrees("Blister1_D0");
            Wreckage wreckage = new Wreckage(this, hierMesh().chunkFind("Blister1_D0"));
            wreckage.collide(true);
            Vector3d vector3d = new Vector3d();
            vector3d.set(((FlightModelMain) (super.FM)).Vwld);
            wreckage.setSpeed(vector3d);
            bHasBlister = false;
            ((FlightModelMain) (super.FM)).CT.bHasCockpitDoorControl = false;
            super.FM.setGCenter(-0.5F);
        }
                
        if(isNetMirror())
            return;    
            
        bPowR = this == World.getPlayerAircraft();
        if(FM.M.fuel > 0.0F)
        {
            if((bHasEngine || FM.EI.engines[0].getControlThrottle() > 0.5F || FM.EI.engines[1].getControlThrottle() > 0.5F) && (FM.EI.engines[2].getStage() == 0 && FM.M.nitro > 0.0F))
            {
                FM.EI.engines[2].setStage(this, 6);
                Eff3DActor.setIntesity(flame, 1.0F);
                Eff3DActor.setIntesity(dust, 1.0F);
                Eff3DActor.setIntesity(trail, 1.0F);
                Eff3DActor.setIntesity(sprite, 1.0F);
            }
            if((FM.EI.engines[0].getControlThrottle() < 0.5F || FM.EI.engines[1].getControlThrottle() < 0.5F) && FM.EI.engines[2].getStage() > 0)
            {
                FM.EI.engines[2].setEngineStops(this);
                Eff3DActor.setIntesity(flame, 0.0F);
                Eff3DActor.setIntesity(dust, 0.0F);
                Eff3DActor.setIntesity(trail, 0.0F);
                Eff3DActor.setIntesity(sprite, 0.0F);	
            }
            if((FM.EI.engines[0].getStage() == 0) || (FM.EI.engines[1].getStage() == 0))
            {
                FM.EI.engines[2].setEngineStops(this);
                Eff3DActor.setIntesity(flame, 0.0F);
                Eff3DActor.setIntesity(dust, 0.0F);
                Eff3DActor.setIntesity(trail, 0.0F);
                Eff3DActor.setIntesity(sprite, 0.0F);	
            }
        }        
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 100F * f, 0.0F);
        if(Config.isUSE_RENDER())
        {
            if(Main3D.cur3D().cockpits != null && Main3D.cur3D().cockpits[0] != null)
                Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public boolean bToFire;
    private long tX4Prev;
    private float deltaAzimuth;
    private float deltaTangage;
    public float cockpitDoor_;
    private float fMaxKMHSpeedForOpenCanopy;
    public boolean bHasBlister;
    private boolean bHasEngine;
    private Eff3DActor flame;
    private Eff3DActor dust;
    private Eff3DActor trail;
    private Eff3DActor sprite;
    private boolean bPowR;

    static 
    {
        Class class1 = com.maddox.il2.objects.air.ME_262C3A.class;
        new NetAircraft.SPAWN(class1);
        Property.set(class1, "iconFar_shortClassName", "Me 262");
        Property.set(class1, "meshName", "3DO/Plane/Me-262A-1a/hier_262A.him");
        Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        Property.set(class1, "yearService", 1944.1F);
        Property.set(class1, "yearExpired", 1945.5F);
        Property.set(class1, "FlightModel", "FlightModels/Me-262C-3a.fmd");
        Property.set(class1, "cockpitClass", new Class[] {
            com.maddox.il2.objects.air.CockpitME_262.class
        });
        Property.set(class1, "LOSElevation", 0.74615F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 3, 3, 2, 2, 9, 9, 
            9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 0, 0, 1, 1, 
            1, 1, 2, 2, 2, 2, 9, 9, 9, 9
        });
        Aircraft.weaponHooksRegister(class1, new String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalRock27", "_ExternalRock28", "_ExternalDev03", "_ExternalDev04", 
            "_ExternalDev01", "_ExternalDev02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", 
            "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", 
            "_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalRock23", "_ExternalRock24", "_CANNON05", "_CANNON06", "_CANNON07", "_CANNON08", 
            "_CANNON09", "_CANNON10", "_ExternalRock25", "_ExternalRock25", "_ExternalRock26", "_ExternalRock26", "_ExternalDev07", "_ExternalDev08", "_ExternalDev05", "_ExternalDev06"
        });
        try
        {
            ArrayList arraylist = new ArrayList();
            Property.set(class1, "weaponsList", arraylist);
            HashMapInt hashmapint = new HashMapInt();
            Property.set(class1, "weaponsMap", hashmapint);
            byte byte0 = 50;
            String s = "default";
            Aircraft._WeaponSlot a_lweaponslot[] = new Aircraft._WeaponSlot[byte0];
            /*a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(1, "MGunMK108k", 80);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(1, "MGunMK108k", 80);*/
            for(int i = 4; i < 48; i++)
                a_lweaponslot[i] = null;

            a_lweaponslot[48] = new Aircraft._WeaponSlot(9, "FuelTankGun_TStoff", 1);
            a_lweaponslot[49] = new Aircraft._WeaponSlot(9, "FuelTankGun_TStoff", 1);
            for(int a = 50; a < byte0; a++)
                a_lweaponslot[a] = null;
                
            arraylist.add(s);
            hashmapint.put(Finger.Int(s), a_lweaponslot);
            s = "U1";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(9, "PylonMG15120Internal", 1);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(9, "PylonMG15120Internal", 1);
            for(int j = 2; j < 36; j++)
                a_lweaponslot[j] = null;

            a_lweaponslot[36] = new Aircraft._WeaponSlot(0, "MGunMG15120MGki", 146);
            a_lweaponslot[37] = new Aircraft._WeaponSlot(0, "MGunMG15120MGki", 146);
            a_lweaponslot[38] = new Aircraft._WeaponSlot(1, "MGunMK103k", 72);
            a_lweaponslot[39] = new Aircraft._WeaponSlot(1, "MGunMK103k", 72);
            a_lweaponslot[40] = new Aircraft._WeaponSlot(1, "MGunMK108k", 66);
            a_lweaponslot[41] = new Aircraft._WeaponSlot(1, "MGunMK108k", 66);
            for(int j = 42; j < 48; j++)
                a_lweaponslot[j] = null;

            a_lweaponslot[48] = new Aircraft._WeaponSlot(9, "FuelTankGun_TStoff", 1);
            a_lweaponslot[49] = new Aircraft._WeaponSlot(9, "FuelTankGun_TStoff", 1);
            for(int b = 50; b < byte0; b++)
                a_lweaponslot[b] = null;
                
            arraylist.add(s);
            hashmapint.put(Finger.Int(s), a_lweaponslot);
            s = "U5";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(1, "MGunMK108k", 80);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(1, "MGunMK108k", 80);
            for(int k = 4; k < 40; k++)
                a_lweaponslot[k] = null;

            a_lweaponslot[40] = new Aircraft._WeaponSlot(1, "MGunMK108k", 66);
            a_lweaponslot[41] = new Aircraft._WeaponSlot(1, "MGunMK108k", 66);
            for(int k = 42; k < 48; k++)
                a_lweaponslot[k] = null;

            a_lweaponslot[48] = new Aircraft._WeaponSlot(9, "FuelTankGun_TStoff", 1);
            a_lweaponslot[49] = new Aircraft._WeaponSlot(9, "FuelTankGun_TStoff", 1);
            for(int c = 50; c < byte0; c++)
                a_lweaponslot[c] = null;
                
            arraylist.add(s);
            hashmapint.put(Finger.Int(s), a_lweaponslot);
            s = "2xX4";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(1, "MGunMK108k", 80);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(1, "MGunMK108k", 80);
            for(int l = 4; l < 42; l++)
                a_lweaponslot[l] = null;

            a_lweaponslot[42] = new Aircraft._WeaponSlot(2, "RocketGunX4", 1);
            a_lweaponslot[43] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
            a_lweaponslot[44] = new Aircraft._WeaponSlot(2, "RocketGunX4", 1);
            a_lweaponslot[45] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
            a_lweaponslot[46] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
            a_lweaponslot[47] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
            a_lweaponslot[48] = new Aircraft._WeaponSlot(9, "FuelTankGun_TStoff", 1);
            a_lweaponslot[49] = new Aircraft._WeaponSlot(9, "FuelTankGun_TStoff", 1);
            for(int e = 50; e < byte0; e++)
                a_lweaponslot[e] = null;
                
            arraylist.add(s);
            hashmapint.put(Finger.Int(s), a_lweaponslot);
            s = "24r4m";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(1, "MGunMK108k", 80);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(1, "MGunMK108k", 80);
            for(int i1 = 4; i1 < 10; i1++)
                a_lweaponslot[i1] = null;

            a_lweaponslot[10] = new Aircraft._WeaponSlot(9, "PylonMe262_R4M_Left", 1);
            a_lweaponslot[11] = new Aircraft._WeaponSlot(9, "PylonMe262_R4M_Right", 1);
            a_lweaponslot[12] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[13] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[14] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[15] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[16] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[17] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[18] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[19] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[20] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[21] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[22] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[23] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[24] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[25] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[26] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[27] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[28] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[29] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[30] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[31] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[32] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[33] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[34] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            a_lweaponslot[35] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
            for(int i1 = 36; i1 < 48; i1++)
                a_lweaponslot[i1] = null;

            a_lweaponslot[48] = new Aircraft._WeaponSlot(9, "FuelTankGun_TStoff", 1);
            a_lweaponslot[49] = new Aircraft._WeaponSlot(9, "FuelTankGun_TStoff", 1);
            for(int a1 = 50; a1 < byte0; a1++)
                a_lweaponslot[a1] = null;
                
            arraylist.add(s);
            hashmapint.put(Finger.Int(s), a_lweaponslot);
            s = "none";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            for(int j1 = 0; j1 < byte0; j1++)
                a_lweaponslot[j1] = null;

            arraylist.add(s);
            hashmapint.put(Finger.Int(s), a_lweaponslot);
        }
        catch(Exception exception) { }
    }
}
// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 15/06/2011 8:00:37 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   F_80A.java
package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            X_80, PaintSchemeFMPar06, Aircraft, NetAircraft
public class T_33 extends P_80 {

    public T_33() {
        overrideBailout = false;
        ejectComplete = false;
        lTimeNextEject = 0;
        emergencyEject = false;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(super.thisWeaponsName.startsWith("50cal"))
        {
            hierMesh().chunkVisible("NoseGun_D0", true);
        }
        else
            hierMesh().chunkVisible("NoseGun_D0", false);
    }
    
    public void doMurderPilot(int i) {
        switch (i) {
            default:
                break;

            case 0: // '\0'
                hierMesh().chunkVisible("Pilot1_D0", false);
                hierMesh().chunkVisible("Head1_D0", false);
                hierMesh().chunkVisible("Pilot1_D1", true);
                break;

            case 1: // '\001'
                hierMesh().chunkVisible("Pilot2_D0", false);
                hierMesh().chunkVisible("Head2_D0", false);
                hierMesh().chunkVisible("Pilot2_D1", true);
                break;
        }
    }

    protected boolean cutFM(int i, int j, Actor actor) {
        switch (i) {
            case 33: // '!'
                return super.cutFM(34, j, actor);

            case 36: // '$'
                return super.cutFM(37, j, actor);

            case 11: // '\013'
                cutFM(17, j, actor);
                super.FM.cut(17, j, actor);
                cutFM(18, j, actor);
                super.FM.cut(18, j, actor);
                return super.cutFM(i, j, actor);
        }
        return super.cutFM(i, j, actor);
    }

    public void rareAction(float f, boolean flag) {
        super.rareAction(f, flag);
        if ((FM.Gears.nearGround() || FM.Gears.onGround())
                && FM.CT.getCockpitDoor() == 1.0F) {
            hierMesh().chunkVisible("HMask2_D0", false);
        } else {
            hierMesh().chunkVisible("HMask2_D0", hierMesh().isChunkVisible("Head2_D0"));
        }
    }

    public void doEjectCatapultStudent() {
        new MsgAction(false, this) {

            public void doAction(Object paramObject) {
                Aircraft localAircraft = (Aircraft) paramObject;
                if (Actor.isValid(localAircraft)) {
                    Loc localLoc1 = new Loc();
                    Loc localLoc2 = new Loc();
                    Vector3d localVector3d = new Vector3d(0.0, 0.0, 10.0);
                    HookNamed localHookNamed = new HookNamed(localAircraft,
                            "_ExternalSeat01");
                    localAircraft.pos.getAbs(localLoc2);
                    localHookNamed.computePos(localAircraft, localLoc2,
                            localLoc1);
                    localLoc1.transform(localVector3d);
                    localVector3d.x += localAircraft.FM.Vwld.x;
                    localVector3d.y += localAircraft.FM.Vwld.y;
                    localVector3d.z += localAircraft.FM.Vwld.z;
                    new EjectionSeat(1, localLoc1, localVector3d, localAircraft);
                }
            }
        };
        this.hierMesh().chunkVisible("Seat2_D0", false);
    }

    public void doEjectCatapultInstructor() {
        new MsgAction(false, this) {

            public void doAction(Object paramObject) {
                Aircraft localAircraft = (Aircraft) paramObject;
                if (Actor.isValid(localAircraft)) {
                    Loc localLoc1 = new Loc();
                    Loc localLoc2 = new Loc();
                    Vector3d localVector3d = new Vector3d(0.0, 0.0, 10.0);
                    HookNamed localHookNamed = new HookNamed(localAircraft,
                            "_ExternalSeat02");
                    localAircraft.pos.getAbs(localLoc2);
                    localHookNamed.computePos(localAircraft, localLoc2,
                            localLoc1);
                    localLoc1.transform(localVector3d);
                    localVector3d.x += localAircraft.FM.Vwld.x;
                    localVector3d.y += localAircraft.FM.Vwld.y;
                    localVector3d.z += localAircraft.FM.Vwld.z;
                    new EjectionSeat(1, localLoc1, localVector3d, localAircraft);
                }
            }
        };
        this.hierMesh().chunkVisible("Seat1_D0", false);
    }

    private void bailout() {
        if (overrideBailout) {
            if (FM.AS.astateBailoutStep >= 0 && FM.AS.astateBailoutStep < 2) {
                if (FM.CT.cockpitDoorControl > 0.5F
                        && FM.CT.getCockpitDoor() > 0.5F) {
                    FM.AS.astateBailoutStep = (byte) 11;
                } else {
                    FM.AS.astateBailoutStep = (byte) 2;
                }
            } else if (FM.AS.astateBailoutStep >= 2
                    && FM.AS.astateBailoutStep <= 3) {
                switch (FM.AS.astateBailoutStep) {
                    case 2:
                        if (FM.CT.cockpitDoorControl >= 0.5F) {
                            break;
                        }
                        if(FM.AS.getPilotHealth(0) < 0.5F || FM.AS.getPilotHealth(1) < 0.5F || FM.EI.engines[0].getReadyness() < 0.7F || !FM.CT.bHasCockpitDoorControl || FM.getSpeedKMH() < 250F || World.Rnd().nextFloat() < 0.20F){
                        	emergencyEject = true;
                        	breakBlister();
                        }
                        else
                        	doRemoveBlister1();
                        break;
                    case 3:
                    	if(!emergencyEject){
                    		lTimeNextEject = Time.current() + 1000;
                    	}
                        break;
                }
                if (FM.AS.isMaster()) {
                    FM.AS.netToMirrors(20, FM.AS.astateBailoutStep, 1, null);
                }
                FM.AS.astateBailoutStep = (byte) (FM.AS.astateBailoutStep + 1);
                if (FM.AS.astateBailoutStep == 4) {
                    FM.AS.astateBailoutStep = (byte) 11;
                }
            } else if (FM.AS.astateBailoutStep >= 11 && FM.AS.astateBailoutStep <= 19) {
                int i = FM.AS.astateBailoutStep;
                if (FM.AS.isMaster()) {
                    FM.AS.netToMirrors(20, FM.AS.astateBailoutStep, 1, null);
                }
                FM.AS.astateBailoutStep = (byte) (FM.AS.astateBailoutStep + 1);
                if (FM instanceof Maneuver && ((Maneuver) FM).get_maneuver() != 44) {
                    World.cur();
                    if (FM.AS.actor != World.getPlayerAircraft()) {
                        ((Maneuver) FM).set_maneuver(44);
                    }
                }
                
                if (FM.AS.astatePilotStates[i - 11] < 99) {
                    if (i == 11) {
                        this.doRemoveBodyFromPlane(2);
                        doEjectCatapultStudent();
                        lTimeNextEject = Time.current() + 1000;
                    } else if (i == 12) {
                        this.doRemoveBodyFromPlane(1);
                        doEjectCatapultInstructor();
                        FM.AS.astateBailoutStep = (byte) 51;
                        FM.setTakenMortalDamage(true, null);
                        FM.CT.WeaponControl[0] = false;
                        FM.CT.WeaponControl[1] = false;
                        FM.AS.astateBailoutStep = (byte) -1;
                        overrideBailout = false;
                        FM.AS.bIsAboutToBailout = true;
                        ejectComplete = true;
                    }
                    FM.AS.astatePilotStates[(i - 11)] = 99;
                } else {
                    EventLog.type("astatePilotStates[" + (i-11) + "]=" + FM.AS.astatePilotStates[i - 11]);
                }                    
            }
        }
    }

    private final void breakBlister(){
        if (this.hierMesh().chunkFindCheck("Blister1_D0") != -1
                && FM.AS.getPilotHealth(0) > 0.0F) {
            this.hierMesh().hideSubTrees("Blister1_D0");
            this.hierMesh().chunkVisible("BlisterBroken_D0", true);
            Wreckage localWreckage1 = new Wreckage(this, this.hierMesh().chunkFind("BlisterPiece1_D0"));
            localWreckage1.collide(false);
            Vector3d localVector3d1 = new Vector3d();
            localVector3d1.set(FM.Vwld);
            localWreckage1.setSpeed(localVector3d1);
            Wreckage localWreckage2 = new Wreckage(this, this.hierMesh().chunkFind("BlisterPiece2_D0"));
            localWreckage2.collide(false);
            Vector3d localVector3d2 = new Vector3d();
            localVector3d2.set(FM.Vwld);
            localWreckage2.setSpeed(localVector3d2);
            Wreckage localWreckage3 = new Wreckage(this, this.hierMesh().chunkFind("BlisterPiece3_D0"));
            localWreckage3.collide(false);
            Vector3d localVector3d3 = new Vector3d();
            localVector3d3.set(FM.Vwld);
            localWreckage3.setSpeed(localVector3d3);

        }
    }
    
    private final void doRemoveBlister1() {
        if (this.hierMesh().chunkFindCheck("Blister1_D0") != -1
                && FM.AS.getPilotHealth(0) > 0.0F) {
            this.hierMesh().hideSubTrees("Blister1_D0");
            Wreckage localWreckage = new Wreckage(this, this.hierMesh().chunkFind("Blister1_D0"));
            localWreckage.collide(false);
            Vector3d localVector3d = new Vector3d();
            localVector3d.set(FM.Vwld);
            localWreckage.setSpeed(localVector3d);
        }
    }

    public void moveCockpitDoor(float paramFloat) {
        this.resetYPRmodifier();
        this.hierMesh().chunkSetAngles("BlisterMove", 0.0F, 0.0F, 25.0F * paramFloat);
        Aircraft.xyz[2] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.7F);
        this.hierMesh().chunkSetLocate("Cylinder_D0", Aircraft.xyz,
                Aircraft.ypr);
        if (Config.isUSE_RENDER()) {
            if (Main3D.cur3D().cockpits != null
                    && Main3D.cur3D().cockpits[0] != null) {
                Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
            }
            this.setDoorSnd(paramFloat);
        }
    }

    public void update(float f) {
        if ((FM.AS.bIsAboutToBailout || overrideBailout) && !ejectComplete
                && FM.getSpeedKMH() > 15.0F) {
            overrideBailout = true;
            FM.AS.bIsAboutToBailout = false;
            if (Time.current() > lTimeNextEject) bailout();
        }
        super.update(f);
    }
    public static boolean bChangedPit = false;
    private boolean overrideBailout;
    private boolean ejectComplete;
    private long lTimeNextEject = 0;
    private boolean emergencyEject;

    static {
        Class class1 = com.maddox.il2.objects.air.T_33.class;
        new NetAircraft.SPAWN(class1);
        Property.set(class1, "iconFar_shortClassName", "F-80A");
        Property.set(class1, "meshName", "3DO/Plane/T-33/hier.him");
        Property.set(class1, "PaintScheme", new PaintSchemeFMPar06());
        Property.set(class1, "yearService", 1946.9F);
        Property.set(class1, "yearExpired", 1955.3F);
        Property.set(class1, "FlightModel", "FlightModels/T-33.fmd");
        Property.set(class1, "cockpitClass", new Class[]{
                    com.maddox.il2.objects.air.CockpitT_33c.class,
                    com.maddox.il2.objects.air.CockpitT_33i.class
                });
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[]{
                    0, 0, 9, 9, 9, 9, 3, 3, 9, 9,
                    9, 9, 9, 9, 9, 9, 2, 2, 2, 2,
                    2, 2, 2, 2
                });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[]{
                    "_MGUN01", "_MGUN02", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev05", "_ExternalDev06",
                    "_ExternalDev07", "_ExternalDev08", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04",
                    "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08"
                });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[]{
                    null, null, null, null, null, null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null, null,
                    null, null, null, null
                });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250lb", new java.lang.String[]{
                    null, null, null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", "BombGun250lbs 1", "BombGun250lbs 1", null, null,
                    null, null, null, null, null, null, null, null, null, null,
                    null, null, null, null
                });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500lb", new java.lang.String[]{
                    null, null, null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", "BombGun500lbs 1", "BombGun500lbs 1", null, null,
                    null, null, null, null, null, null, null, null, null, null,
                    null, null, null, null
                });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x1000lb", new java.lang.String[]{
                    null, null, null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", "BombGun1000lbs 1", "BombGun1000lbs 1", null, null,
                    null, null, null, null, null, null, null, null, null, null,
                    null, null, null, null
                });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8xHVAR", new java.lang.String[]{
                    null, null, null, null, null, null, null, null, "PylonF4FPLN2 1", "PylonF4FPLN2 1",
                    "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1",
                    "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1"
                });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8xHVAR+2x250lb", new java.lang.String[]{
                    null, null, null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", "BombGun250lbs 1", "BombGun250lbs 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1",
                    "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1",
                    "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1"
                });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "50cal", new java.lang.String[]{
                    "MGunBrowning50kAPIT 350", "MGunBrowning50kAPIT 350", null, null, null, null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null, null,
                    null, null, null, null
                });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "50cal_2x250lb", new java.lang.String[]{
                    "MGunBrowning50kAPIT 350", "MGunBrowning50kAPIT 350", null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", "BombGun250lbs 1", "BombGun250lbs 1", null, null,
                    null, null, null, null, null, null, null, null, null, null,
                    null, null, null, null
                });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "50cal_2x500lb", new java.lang.String[]{
                    "MGunBrowning50kAPIT 350", "MGunBrowning50kAPIT 350", null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", "BombGun500lbs 1", "BombGun500lbs 1", null, null,
                    null, null, null, null, null, null, null, null, null, null,
                    null, null, null, null
                });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "50cal_2x1000lb", new java.lang.String[]{
                    "MGunBrowning50kAPIT 350", "MGunBrowning50kAPIT 350", null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", "BombGun1000lbs 1", "BombGun1000lbs 1", null, null,
                    null, null, null, null, null, null, null, null, null, null,
                    null, null, null, null
                });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "50cal_8xHVAR", new java.lang.String[]{
                    "MGunBrowning50kAPIT 350", "MGunBrowning50kAPIT 350", null, null, null, null, null, null, "PylonF4FPLN2 1", "PylonF4FPLN2 1",
                    "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1",
                    "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1"
                });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "50cal_8xHVAR+2x250lb", new java.lang.String[]{
                    "MGunBrowning50kAPIT 350", "MGunBrowning50kAPIT 350", null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", "BombGun250lbs 1", "BombGun250lbs 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1",
                    "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1",
                    "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1"
                });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[]{
                    null, null, null, null, null, null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null, null,
                    null, null, null, null
                });
    }
}
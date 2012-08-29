// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 21/07/2011 10:37:56 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   F_80N.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.*;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_80, TypeFighter, TypeBNZFighter, TypeFighterAceMaker, 
//            TypeStormovik, PaintSchemeFMPar05, Aircraft, Cockpit, 
//            NetAircraft

public class F_80N extends P_80
    implements TypeFighter, TypeBNZFighter, TypeFighterAceMaker, TypeStormovik
{

    public F_80N()
    {
    	overrideBailout = false;
        ejectComplete = false;
        arrestor = 0.0F;
    }

    public void moveArrestorHook(float f)
    {
        hierMesh().chunkSetAngles("Hook1_D0", 0.0F, -60F * f, 0.0F);
        arrestor = f;
    }

    protected void moveWingFold(HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("WingLOutFold_D0", 0.0F, 130F * f, 0.0F);
        hiermesh.chunkSetAngles("WingROutFold_D0", 0.0F, -130F * f, 0.0F);
    }

    public void moveWingFold(float f)
    {
        if(f < 0.001F)
        {
            setGunPodsOn(true);
            hideWingWeapons(false);
        } else
        {
            setGunPodsOn(true);
            ((FlightModelMain) (super.FM)).CT.WeaponControl[0] = false;
            hideWingWeapons(true);
        }
        moveWingFold(hierMesh(), f);
    }  
    
    public void doEjectCatapult() {
        new MsgAction(false, this) {

          public void doAction(Object paramObject) {
            Aircraft localAircraft = (Aircraft) paramObject;
            if (Actor.isValid(localAircraft)) {
              Loc localLoc1 = new Loc();
              Loc localLoc2 = new Loc();
              Vector3d localVector3d = new Vector3d(0.0, 0.0, 10.0);
              HookNamed localHookNamed = new HookNamed(localAircraft, "_ExternalSeat01");
              localAircraft.pos.getAbs(localLoc2);
              localHookNamed.computePos(localAircraft, localLoc2,
                      localLoc1);
              localLoc1.transform(localVector3d);
              localVector3d.x += localAircraft.FM.Vwld.x;
              localVector3d.y += localAircraft.FM.Vwld.y;
              localVector3d.z += localAircraft.FM.Vwld.z;
              new EjectionSeat(1, localLoc1, localVector3d,
                      localAircraft);
            }
          }
        };
        this.hierMesh().chunkVisible("Seat_D0", false);
        FM.setTakenMortalDamage(true, null);
        FM.CT.WeaponControl[0] = false;
        FM.CT.WeaponControl[1] = false;
        FM.CT.bHasAileronControl = false;
        FM.CT.bHasRudderControl = false;
        FM.CT.bHasElevatorControl = false;
      }

    private void bailout()
    {
        if(overrideBailout)
            if(((FlightModelMain) (super.FM)).AS.astateBailoutStep >= 0 && ((FlightModelMain) (super.FM)).AS.astateBailoutStep < 2)
            {
                if(((FlightModelMain) (super.FM)).CT.cockpitDoorControl > 0.5F && ((FlightModelMain) (super.FM)).CT.getCockpitDoor() > 0.5F)
                {
                    ((FlightModelMain) (super.FM)).AS.astateBailoutStep = 11;
                    doRemoveBlisters();
                } else
                {
                    ((FlightModelMain) (super.FM)).AS.astateBailoutStep = 2;
                }
            } else
            if(((FlightModelMain) (super.FM)).AS.astateBailoutStep >= 2 && ((FlightModelMain) (super.FM)).AS.astateBailoutStep <= 3)
            {
                switch(((FlightModelMain) (super.FM)).AS.astateBailoutStep)
                {
                case 2: // '\002'
                    if(((FlightModelMain) (super.FM)).CT.cockpitDoorControl < 0.5F)
                        doRemoveBlister1();
                    break;

                case 3: // '\003'
                    doRemoveBlisters();
                    break;
                }
                if(((FlightModelMain) (super.FM)).AS.isMaster())
                    ((FlightModelMain) (super.FM)).AS.netToMirrors(20, ((FlightModelMain) (super.FM)).AS.astateBailoutStep, 1, null);
                AircraftState tmp178_177 = ((FlightModelMain) (super.FM)).AS;
                tmp178_177.astateBailoutStep = (byte)(tmp178_177.astateBailoutStep + 1);
                if(((FlightModelMain) (super.FM)).AS.astateBailoutStep == 4)
                    ((FlightModelMain) (super.FM)).AS.astateBailoutStep = 11;
            } else
            if(((FlightModelMain) (super.FM)).AS.astateBailoutStep >= 11 && ((FlightModelMain) (super.FM)).AS.astateBailoutStep <= 19)
            {
                int i = ((FlightModelMain) (super.FM)).AS.astateBailoutStep;
                if(((FlightModelMain) (super.FM)).AS.isMaster())
                    ((FlightModelMain) (super.FM)).AS.netToMirrors(20, ((FlightModelMain) (super.FM)).AS.astateBailoutStep, 1, null);
                AircraftState tmp383_382 = ((FlightModelMain) (super.FM)).AS;
                tmp383_382.astateBailoutStep = (byte)(tmp383_382.astateBailoutStep + 1);
                if(i == 11)
                {
                    super.FM.setTakenMortalDamage(true, null);
                    if((super.FM instanceof Maneuver) && ((Maneuver)super.FM).get_maneuver() != 44)
                    {
                        World.cur();
                        if(((FlightModelMain) (super.FM)).AS.actor != World.getPlayerAircraft())
                            ((Maneuver)super.FM).set_maneuver(44);
                    }
                }
                if(((FlightModelMain) (super.FM)).AS.astatePilotStates[i - 11] < 99)
                {
                    doRemoveBodyFromPlane(i - 10);
                    if(i == 11)
                    {
                        doEjectCatapult();
                        super.FM.setTakenMortalDamage(true, null);
                        ((FlightModelMain) (super.FM)).AS.astateBailoutStep = -1;
                        overrideBailout = false;
                        ((FlightModelMain) (super.FM)).AS.bIsAboutToBailout = true;
                        ejectComplete = true;
                        return;
                    }
                }
            }
    }

    private final void doRemoveBlister1()
    {
        if(hierMesh().chunkFindCheck("Blister1_D0") != -1 && ((FlightModelMain) (super.FM)).AS.getPilotHealth(0) > 0.0F)
        {
            hierMesh().hideSubTrees("Blister1_D0");
            Wreckage localWreckage = new Wreckage(this, hierMesh().chunkFind("Blister1_D0"));
            localWreckage.collide(false);
            Vector3d localVector3d = new Vector3d();
            localVector3d.set(((FlightModelMain) (super.FM)).Vwld);
            localWreckage.setSpeed(localVector3d);
        }
    }

    private final void doRemoveBlisters()
    {
        for(int i = 2; i < 10; i++)
            if(hierMesh().chunkFindCheck("Blister" + i + "_D0") != -1 && ((FlightModelMain) (super.FM)).AS.getPilotHealth(i - 1) > 0.0F)
            {
                hierMesh().hideSubTrees("Blister" + i + "_D0");
                Wreckage localWreckage = new Wreckage(this, hierMesh().chunkFind("Blister" + i + "_D0"));
                localWreckage.collide(false);
                Vector3d localVector3d = new Vector3d();
                localVector3d.set(((FlightModelMain) (super.FM)).Vwld);
                localWreckage.setSpeed(localVector3d);
            }

    }
    
    public void setOnGround(Point3d point3d, Orient orient, Vector3d vector3d)
    {
        super.setOnGround(point3d, orient, vector3d);
        if(super.FM.isPlayers())
            ((FlightModelMain) (super.FM)).CT.cockpitDoorControl = 1.0F;
    }

    public void update(float f){
     	 super.update(f);
     	    if ((FM.AS.bIsAboutToBailout || overrideBailout) && !ejectComplete
     	            && FM.getSpeedKMH() > 15.0F) {
     	      overrideBailout = true;
     	      FM.AS.bIsAboutToBailout = false;
     	      bailout();
     	    }
       }
    
    private boolean overrideBailout;
    private boolean ejectComplete;
    protected float arrestor;
    public static boolean bChangedPit = false;

    static 
    {
        Class var_class = com.maddox.il2.objects.air.F_80N.class;
        new NetAircraft.SPAWN(var_class);
        Property.set(var_class, "iconFar_shortClassName", "F-80N");
        Property.set(var_class, "meshName", "3DO/Plane/F-80N/hier.him");
        Property.set(var_class, "PaintScheme", new PaintSchemeFMPar05());
        Property.set(var_class, "yearService", 1946.9F);
        Property.set(var_class, "yearExpired", 1955.3F);
        Property.set(var_class, "FlightModel", "FlightModels/F-80N.fmd:F80_FM");
        Property.set(var_class, "cockpitClass", new Class[] {
            com.maddox.il2.objects.air.CockpitF_80N.class
        });
        Aircraft.weaponTriggersRegister(var_class, new int[] {
            0, 0, 0, 0, 0, 0, 9, 9, 9, 9, 
            3, 3, 9, 9, 9, 9, 9, 9, 9, 9, 
            2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 9, 9, 9, 9, 0, 0, 0, 0, 
            0, 0, 0, 0, 3, 3
        });
        Aircraft.weaponHooksRegister(var_class, new String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", 
            "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", 
            "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalBomb03", "_ExternalBomb04", 
            "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11", "_ExternalBomb12", "_ExternalBomb13", "_ExternalBomb14", 
            "_ExternalBomb15", "_ExternalBomb16", "_ExternalBomb17", "_ExternalBomb18", "_ExternalBomb19", "_ExternalBomb20", "_ExternalBomb21", "_ExternalBomb22", "_ExternalBomb23", "_ExternalBomb24", 
            "_ExternalBomb25", "_ExternalBomb26", "_ExternalBomb27", "_ExternalBomb28", "_ExternalBomb29", "_ExternalBomb30", "_ExternalBomb31", "_ExternalBomb32", "_ExternalBomb33", "_ExternalBomb34", 
            "_ExternalBomb35", "_ExternalBomb36", "_ExternalBomb37", "_ExternalBomb38", "_ExternalBomb39", "_ExternalBomb40", "_ExternalBomb41", "_ExternalBomb42", "_ExternalBomb43", "_ExternalBomb44", 
            "_ExternalBomb45", "_ExternalBomb46", "_ExternalBomb47", "_ExternalBomb48", "_ExternalBomb49", "_ExternalBomb50", "_ExternalBomb51", "_ExternalBomb52", "_ExternalBomb53", "_ExternalBomb54", 
            "_ExternalBomb55", "_ExternalBomb56", "_ExternalBomb57", "_ExternalBomb58", "_ExternalBomb59", "_ExternalBomb60", "_ExternalBomb61", "_ExternalBomb62", "_ExternalBomb63", "_ExternalBomb64", 
            "_ExternalBomb65", "_ExternalBomb66", "_ExternalBomb67", "_ExternalBomb68", "_ExternalBomb69", "_ExternalBomb70", "_ExternalBomb71", "_ExternalBomb72", "_ExternalBomb73", "_ExternalBomb74", 
            "_ExternalBomb75", "_ExternalBomb76", "_ExternalDev13", "_ExternalDev14", "_ExternalDev15", "_ExternalDev16", "_MGUN07", "_MGUN08", "_MGUN09", "_MGUN10", 
            "_MGUN11", "_MGUN12", "_MGUN13", "_MGUN14", "_ExternalBomb77", "_ExternalBomb78"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "default", new java.lang.String[]{
        		"MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2x500lb", new java.lang.String[]{
        		"MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300", null, null,"PylonP51PLN2 1","PylonP51PLN2 1",
        		"BombGun500lbs 1","BombGun500lbs 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2x1000lb", new java.lang.String[]{
        		"MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300", null, null,"PylonP51PLN2 1","PylonP51PLN2 1",
        		"BombGun1000lbs 1","BombGun1000lbs 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "8xHVAR", new java.lang.String[]{
        		"MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300", null, null, null, null, 
        		null, null,"PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1",
        		"RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1", null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "8xHVAR_2x500lb", new java.lang.String[]{
        		"MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300", null, null,"PylonP51PLN2 1","PylonP51PLN2 1",
        		"BombGun500lbs 1","BombGun500lbs 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1",
        		"RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1", null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR_LongRange", new java.lang.String[]{
        		"MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1", null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR-2x500lb", new java.lang.String[]{
        		"MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1", 
        		"BombGun500lbs 1","BombGun500lbs 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR-8xHVAR", new java.lang.String[]{
        		"MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1", null, null, 
        		null, null,"PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1",
        		"RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1", null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR-8xHVAR+2x500lb_RATO", new java.lang.String[]{
        		"MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		"BombGun500lbs 1","BombGun500lbs 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1",
        		"RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1", null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "ExtraLongRange_RATO", new java.lang.String[]{
        		"MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1", 
        		"FuelTankGun_Tank75gal2 1","FuelTankGun_Tank75gal2 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR-2x1000lb_RATO", new java.lang.String[]{
        		"MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1", 
        		"BombGun1000lbs 1","BombGun1000lbs 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4x1000lb_RATO", new java.lang.String[]{
        		"MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300", null, null,"PylonP51PLN2 1","PylonP51PLN2 1",
        		"BombGun1000lbs 1","BombGun1000lbs 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null,"BombGun1000lbs 1","BombGun1000lbs 1"
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR+8xHVAR+2x1000lb+2xHispano_RATO", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		"BombGun1000lbs 1","BombGun1000lbs 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1",
        		"RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1", null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "ELR+4xHispano_RATO", new java.lang.String[]{
        		"MGunHispanoMkIk 300","MGunHispanoMkIk 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunHispanoMkIk 300","MGunHispanoMkIk 300","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		"FuelTankGun_Tank75gal2 1","FuelTankGun_Tank75gal2 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "ELR+mixed_belt_RATO", new java.lang.String[]{
        		"MGunBrowning50MIX 300","MGunBrowning50MIX 300","MGunBrowning50MIX 300","MGunBrowning50MIX 300","MGunBrowning50MIX 300","MGunBrowning50MIX 300","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		"FuelTankGun_Tank75gal2 1","FuelTankGun_Tank75gal2 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR+6xHispano", new java.lang.String[]{
        		"MGunHispanoMkIk 220","MGunHispanoMkIk 220","MGunHispanoMkIk 220","MGunHispanoMkIk 220","MGunHispanoMkIk 220","MGunHispanoMkIk 220","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1", null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4x1000lbs+6xHispano_RATO", new java.lang.String[]{
        		"MGunHispanoMkIk 220","MGunHispanoMkIk 220","MGunHispanoMkIk 220","MGunHispanoMkIk 220","MGunHispanoMkIk 220","MGunHispanoMkIk 220", null, null,"PylonP51PLN2 1","PylonP51PLN2 1",
        		"BombGun1000lbs 1","BombGun1000lbs 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null,"BombGun1000lbs 1","BombGun1000lbs 1"
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR+8xHVAR+2xNapalm175+6xHispano_RATO", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		"BombGun175Napalm 1","BombGun175Napalm 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1",
        		"RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1", null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });   
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR+2xTinyTim+6xHispano", new java.lang.String[]{
        		"MGunHispanoMkIk 220","MGunHispanoMkIk 220","MGunHispanoMkIk 220","MGunHispanoMkIk 220","MGunHispanoMkIk 220","MGunHispanoMkIk 220","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		"RocketGunTinyTim 1","RocketGunTinyTim 1", null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null
              }); 
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR+6xDepthCharges", new java.lang.String[]{
        		"MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		"BombGunMk53Charge 1","BombGunMk53Charge 1","PylonF4FPLN2 1", null, null,"PylonF4FPLN2 1","PylonF4FPLN2 1", null, null,"PylonF4FPLN2 1", 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null,"BombGunMk53Charge 1","BombGunMk53Charge 1", null, null,"BombGunMk53Charge 1","BombGunMk53Charge 1",
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });  
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR+2xTorpMk13+6xHispano_RATO", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		"BombGunTorpMk13 1","BombGunTorpMk13 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null
              });  
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xTorpMk34", new java.lang.String[]{
        		"MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300", null, null,"PylonP51PLN2 1","PylonP51PLN2 1",
        		"BombGunTorpMk34 1","BombGunTorpMk34 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null
              });  
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR+2xTorpMk34+6xHispano_RATO", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		"BombGunTorpMk34 1","BombGunTorpMk34 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null
              });  
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR+64xParafrag+2xNapalm154+6xHispano_RATO", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		"BombGun154Napalm 1","BombGun154Napalm 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1", 
        		null, null, null, null, null, null, null, null,"BombGunParafrag8 1","BombGunParafrag8 1",
        		"BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1",
        		"BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1",
        		"BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1",
        		"BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1",
        		"BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1",
        		"BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1","BombGunParafrag8 1",
        		"BombGunParafrag8 1","BombGunParafrag8 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });  
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR-2x2000lb_RATO", new java.lang.String[]{
        		"MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1", 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null,"BombGun2000lbs 1","BombGun2000lbs 1", null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });  
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR+2x2000lbs+6xHispano_RATO", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null,"BombGun2000lbs 1","BombGun2000lbs 1", null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });  
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR+8x250lbs+2x500+6xHispano_RATO", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		"BombGun500lbs 1","BombGun500lbs 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1", 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null,"BombGun250lbs 1","BombGun250lbs 1","BombGun250lbs 1","BombGun250lbs 1","BombGun250lbs 1","BombGun250lbs 1",
        		"BombGun250lbs 1","BombGun250lbs 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });  
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR+8x250lbs+2x1000lbs+6xHispano_RATO", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		"BombGun1000lbs 1","BombGun1000lbs 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1", 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null,"BombGun250lbs 1","BombGun250lbs 1","BombGun250lbs 1","BombGun250lbs 1","BombGun250lbs 1","BombGun250lbs 1",
        		"BombGun250lbs 1","BombGun250lbs 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });  
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR+32xHVAR+2xNapalm154+6xHispano_RATO", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		"BombGun154Napalm 1","BombGun154Napalm 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1", 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null,
        		"RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1",
        		"RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1",
        		"RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1",
        		"RocketGunHVAR5 1","RocketGunHVAR5 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });  
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR+32xHVAR+2x1000lbs+6xHispano_RATO", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		"BombGun1000lbs 1","BombGun1000lbs 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1", 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null,
        		"RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1",
        		"RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1",
        		"RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1",
        		"RocketGunHVAR5 1","RocketGunHVAR5 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });   
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "32xHVAR+2xTinyTim+6xHispano_RATO", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200", null, null,"PylonP51PLN2 1","PylonP51PLN2 1",
        		"RocketGunTinyTim 1","RocketGunTinyTim 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1", 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null,
        		"RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1",
        		"RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1",
        		"RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1",
        		"RocketGunHVAR5 1","RocketGunHVAR5 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              }); 
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR+gunpods+2x500lbs+6xHispano_RATO", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		"BombGun500lbs 1","BombGun500lbs 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null,"PylonP38GUNPOD 1","PylonP38GUNPOD 1","PylonP38GUNPOD 1","PylonP38GUNPOD 1","MGunBrowning50kAPIT 350","MGunBrowning50kAPIT 350","MGunBrowning50kAPIT 350","MGunBrowning50kAPIT 350",
        		"MGunBrowning50kAPIT 350","MGunBrowning50kAPIT 350","MGunBrowning50kAPIT 350","MGunBrowning50kAPIT 350", null, null
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR+gunpods+2x500lbs_RATO", new java.lang.String[]{
        		"MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		"BombGun500lbs 1","BombGun500lbs 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null,"PylonP38GUNPOD 1","PylonP38GUNPOD 1","PylonP38GUNPOD 1","PylonP38GUNPOD 1","MGunBrowning50kAPIT 350","MGunBrowning50kAPIT 350","MGunBrowning50kAPIT 350","MGunBrowning50kAPIT 350",
        		"MGunBrowning50kAPIT 350","MGunBrowning50kAPIT 350","MGunBrowning50kAPIT 350","MGunBrowning50kAPIT 350", null, null
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "ELR+gunpods_RATO", new java.lang.String[]{
        		"MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		"FuelTankGun_Tank75gal2 1","FuelTankGun_Tank75gal2 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null,"PylonP38GUNPOD 1","PylonP38GUNPOD 1","PylonP38GUNPOD 1","PylonP38GUNPOD 1","MGunBrowning50kAPIT 350","MGunBrowning50kAPIT 350","MGunBrowning50kAPIT 350","MGunBrowning50kAPIT 350",
        		"MGunBrowning50kAPIT 350","MGunBrowning50kAPIT 350","MGunBrowning50kAPIT 350","MGunBrowning50kAPIT 350", null, null
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "ELR+gunpods+API_RATO", new java.lang.String[]{
        		"MGunBrowning50kAPI 300","MGunBrowning50kAPI 300","MGunBrowning50kAPI 300","MGunBrowning50kAPI 300","MGunBrowning50kAPI 300","MGunBrowning50kAPI 300","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		"FuelTankGun_Tank75gal2 1","FuelTankGun_Tank75gal2 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null,"PylonP38GUNPOD 1","PylonP38GUNPOD 1","PylonP38GUNPOD 1","PylonP38GUNPOD 1","MGunBrowning50kAPI 350","MGunBrowning50kAPI 350","MGunBrowning50kAPI 350","MGunBrowning50kAPI 350",
        		"MGunBrowning50kAPI 350","MGunBrowning50kAPI 350","MGunBrowning50kAPI 350","MGunBrowning50kAPI 350", null, null
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "ELR+gunpods+2x500lb+APImixed_RATO", new java.lang.String[]{
        		"MGunBrowning50kiAPI 300","MGunBrowning50kiAPI 300","MGunBrowning50ki 300","MGunBrowning50ki 300","MGunBrowning50kiAPI 300","MGunBrowning50kiAPI 300","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		"BombGun500lbs 1","BombGun500lbs 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null,"PylonP38GUNPOD 1","PylonP38GUNPOD 1","PylonP38GUNPOD 1","PylonP38GUNPOD 1","MGunBrowning50kiAPI 350","MGunBrowning50kiAPI 350","MGunBrowning50ki 350","MGunBrowning50ki 350",
        		"MGunBrowning50kiAPI 350","MGunBrowning50kiAPI 350","MGunBrowning50ki 350","MGunBrowning50ki 350", null, null
              });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR+4xM9cannon+2xTinyTim", new java.lang.String[]{
        		"MGunM9ki 60","MGunM9ki 60","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunM9ki 60","MGunM9ki 60","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		"RocketGunTinyTim 1","RocketGunTinyTim 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });     
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR+4xM9cannon+2x1000lbs+32xHVAR_RATO", new java.lang.String[]{
        		"MGunM9ki 60","MGunM9ki 60","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunM9ki 60","MGunM9ki 60","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1",
        		"BombGun1000lbs 1","BombGun1000lbs 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1","PylonF4FPLN2 1", 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null,
        		"RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1",
        		"RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1",
        		"RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1","RocketGunHVAR5 1",
        		"RocketGunHVAR5 1","RocketGunHVAR5 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });   
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "LR+4x750lbs+2x1600lbs+6xHispano_RATO", new java.lang.String[]{
        		"MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","MGunBrowning50kAPIT 300","FuelTankGun_Tank154gal 1","FuelTankGun_Tank154gal 1","PylonP51PLN2 1","PylonP51PLN2 1", 
        		"BombGun 1600lbs 1", "BombGun1600lbs 1", null,"PylonF4FPLN2 1","PylonF4FPLN2 1", null, null,"PylonF4FPLN2 1","PylonF4FPLN2 1", null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null,"BombGun750lbs 1", "BombGun750lbs 1", null, null, 
        		"BombGun750lbs 1", "BombGun750lbs 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });  
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "none", new java.lang.String[]{
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null
              });  
    }
}
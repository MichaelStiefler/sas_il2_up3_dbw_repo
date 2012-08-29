// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   F_80C.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            X_80, PaintSchemeFMPar05, NetAircraft, Aircraft

public class F_80C extends com.maddox.il2.objects.air.P_80
{
	private boolean overrideBailout;
    private boolean ejectComplete;

    public F_80C()
    {
    	overrideBailout = false;
        ejectComplete = false;
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

    private void bailout() {
      if (overrideBailout) {
        if (FM.AS.astateBailoutStep >= 0 && FM.AS.astateBailoutStep < 2) {
          if (FM.CT.cockpitDoorControl > 0.5F
                  && FM.CT.getCockpitDoor() > 0.5F) {
            FM.AS.astateBailoutStep = (byte) 11;
            doRemoveBlisters();
          } else {
            FM.AS.astateBailoutStep = (byte) 2;
          }
        } else if (FM.AS.astateBailoutStep >= 2
                && FM.AS.astateBailoutStep <= 3) {
          switch (FM.AS.astateBailoutStep) {
            case 2:
              if (FM.CT.cockpitDoorControl < 0.5F) {
                doRemoveBlister1();
              }
              break;
            case 3:
              doRemoveBlisters();
              break;
          }
          if (FM.AS.isMaster()) {
            FM.AS.netToMirrors(20, FM.AS.astateBailoutStep, 1, null);
          }
          AircraftState tmp178_177 = FM.AS;
          tmp178_177.astateBailoutStep = (byte) (tmp178_177.astateBailoutStep + 1);
          if (FM.AS.astateBailoutStep == 4) {
            FM.AS.astateBailoutStep = (byte) 11;
          }
        } else if (FM.AS.astateBailoutStep >= 11
                && FM.AS.astateBailoutStep <= 19) {
          int i = FM.AS.astateBailoutStep;
          if (FM.AS.isMaster()) {
            FM.AS.netToMirrors(20, FM.AS.astateBailoutStep, 1, null);
          }
          AircraftState tmp383_382 = FM.AS;
          tmp383_382.astateBailoutStep = (byte) (tmp383_382.astateBailoutStep + 1);
          if (i == 11) {
            FM.setTakenMortalDamage(true, null);
            if (FM instanceof Maneuver
                    && ((Maneuver) FM).get_maneuver() != 44) {
              World.cur();
              if (FM.AS.actor != World.getPlayerAircraft()) {
                ((Maneuver) FM).set_maneuver(44);
              }
            }
          }
          if (FM.AS.astatePilotStates[i - 11] < 99) {
            this.doRemoveBodyFromPlane(i - 10);
            if (i == 11) {
              doEjectCatapult();
              FM.setTakenMortalDamage(true, null);
              FM.CT.WeaponControl[0] = false;
              FM.CT.WeaponControl[1] = false;
              FM.AS.astateBailoutStep = (byte) -1;
              overrideBailout = false;
              FM.AS.bIsAboutToBailout = true;
              ejectComplete = true;
            }
          }
        }
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

    private final void doRemoveBlisters() {
      for (int i = 2; i < 10; i++) {
        if (this.hierMesh().chunkFindCheck("Blister" + i + "_D0") != -1
                && FM.AS.getPilotHealth(i - 1) > 0.0F) {
          this.hierMesh().hideSubTrees("Blister" + i + "_D0");
          Wreckage localWreckage = new Wreckage(this,
                  this.hierMesh().chunkFind("Blister" + i + "_D0"));
          localWreckage.collide(false);
          Vector3d localVector3d = new Vector3d();
          localVector3d.set(FM.Vwld);
          localWreckage.setSpeed(localVector3d);
        }
      }
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
        
    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.F_80C.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "P-80C");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/P-80C/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar05())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1946.9F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1955.3F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/P-80C.fmd");
		Property.set(class1, "cockpitClass", new Class[]{
            com.maddox.il2.objects.air.CockpitF_80C.class
        });
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 9, 9, 9, 9, 
            3, 3, 9, 9, 9, 9, 9, 9, 9, 9, 
            2, 2, 2, 2, 2, 2, 2, 2, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", 
            "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", 
            "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalBomb03", "_ExternalBomb04"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500lb", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x1000lb", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", 
            "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", null, null, null, null, 
            null, null, "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8xHVAR+2x250lb", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "LR-LongRange", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "FuelTankGun_Tank154gal 1", "FuelTankGun_Tank154gal 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "LR-2x500lb", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "FuelTankGun_Tank154gal 1", "FuelTankGun_Tank154gal 1", "PylonP51PLN2 1", "PylonP51PLN2 1", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "LR-2x1000lb", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "FuelTankGun_Tank154gal 1", "FuelTankGun_Tank154gal 1", "PylonP51PLN2 1", "PylonP51PLN2 1", 
            "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4x1000lb_RATO", new java.lang.String[] {
                "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", 
                "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null, null, null, null, null, 
                null, null, null, null, null, null, null, null, "BombGun1000lbs 1", "BombGun1000lbs 1"
            });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "LR-8xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "FuelTankGun_Tank154gal 1", "FuelTankGun_Tank154gal 1", null, null, 
            null, null, "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "LR-8xHVAR+2x250lb", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "FuelTankGun_Tank154gal 1", "FuelTankGun_Tank154gal 1", "PylonP51PLN2 1", "PylonP51PLN2 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "ExtraLongRange", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "FuelTankGun_Tank154gal 1", "FuelTankGun_Tank154gal 1", "PylonP51PLN2 1", "PylonP51PLN2 1", 
            "FuelTankGun_Tank75gal2 1", "FuelTankGun_Tank75gal2 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
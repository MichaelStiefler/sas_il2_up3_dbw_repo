// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 11/02/2011 12:28:28 AM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   MIG_21PFM.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.*;
import com.maddox.il2.ai.*;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.*;
import com.maddox.il2.fm.*;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.weapons.GuidedMissileUtils;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.objects.air:
//            MIG_21, Aircraft, PaintSchemeFMParMiG21, TypeAIM9Carrier, 
//            Cockpit, NetAircraft

public class F4D extends F4_Phantom
implements TypeGuidedMissileCarrier,TypeCountermeasure,
TypeThreatDetector {

	  private GuidedMissileUtils guidedMissileUtils = null;
	  private float trgtPk = 0.0F;
	  private Actor trgtAI = null;

    public F4D()
    {
    	rocketsList = new ArrayList();
        bToFire = false;
        tX4Prev = 0L;
        guidedMissileUtils = new GuidedMissileUtils(this);
    }

   // <editor-fold defaultstate="collapsed" desc="Countermeasures">
    private boolean hasChaff = false;     // Aircraft is equipped with Chaffs yes/no
    private boolean hasFlare = false;     // Aircraft is equipped with Flares yes/no
    private long lastChaffDeployed = 0L;  // Last Time when Chaffs have been deployed
    private long lastFlareDeployed = 0L;  // Last Time when Flares have been deployed

    public long getChaffDeployed() {
      if (this.hasChaff) {
        return this.lastChaffDeployed;
      }
      return 0L;
    }

    public long getFlareDeployed() {
      if (this.hasFlare) {
        return this.lastFlareDeployed;
      }
      return 0L;
    }// </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Threat Detection">
    private long lastCommonThreatActive = 0L;         // Last Time when a common threat was reported
    private long intervalCommonThreat = 1000L;        // Interval (milliseconds) at which common threats should be dealt with (i.e. duration of warning sound / light)
    private long lastRadarLockThreatActive = 0L;      // Last Time when a radar lock threat was reported
    private long intervalRadarLockThreat = 1000L;     // Interval (milliseconds) at which radar lock threats should be dealt with (i.e. duration of warning sound / light)
    private long lastMissileLaunchThreatActive = 0L;  // Last Time when a missile launch threat was reported
    private long intervalMissileLaunchThreat = 1000L; // Interval (milliseconds) at which missile launch threats should be dealt with (i.e. duration of warning sound / light)

    public void setCommonThreatActive() {
      long curTime = Time.current();
      if ((curTime - this.lastCommonThreatActive) > this.intervalCommonThreat) {
        this.lastCommonThreatActive = curTime;
        this.doDealCommonThreat();
      }
    }

    public void setRadarLockThreatActive() {
      long curTime = Time.current();
      if ((curTime - this.lastRadarLockThreatActive) > this.intervalRadarLockThreat) {
        this.lastRadarLockThreatActive = curTime;
        this.doDealRadarLockThreat();
      }
    }

    public void setMissileLaunchThreatActive() {
      long curTime = Time.current();
      if ((curTime - this.lastMissileLaunchThreatActive) > this.intervalMissileLaunchThreat) {
        this.lastMissileLaunchThreatActive = curTime;
        this.doDealMissileLaunchThreat();
      }
    }

    private void doDealCommonThreat() {       // Must be filled with life for A/Cs capable of dealing with common Threats
    }

    private void doDealRadarLockThreat() {    // Must be filled with life for A/Cs capable of dealing with radar lock Threats
    }

    private void doDealMissileLaunchThreat() {// Must be filled with life for A/Cs capable of dealing with missile launch Threats
    }// </editor-fold>

    public Actor getMissileTarget() {
        return this.guidedMissileUtils.getMissileTarget();
      }

      public Point3f getMissileTargetOffset() {
        return this.guidedMissileUtils.getSelectedActorOffset();
      }

      public boolean hasMissiles() {
        if (!this.rocketsList.isEmpty()) {
          return true;
        }
        return false;
      }
      
      public void shotMissile() {
        if (this.hasMissiles()) {
          rocketsList.remove(0);
        }
      }

      public int getMissileLockState() {
        return this.guidedMissileUtils.getMissileLockState();
      }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="AI related Missile Functions">
      private float getMissilePk() {
        float thePk = 0.0F;
        this.guidedMissileUtils.setMissileTarget(guidedMissileUtils.lookForGuidedMissileTarget(FM.actor, guidedMissileUtils.getMaxPOVfrom(), guidedMissileUtils.getMaxPOVto(), guidedMissileUtils.getPkMaxDist()));
        if (Actor.isValid(this.guidedMissileUtils.getMissileTarget())) {
          thePk = guidedMissileUtils.Pk(FM.actor, this.guidedMissileUtils.getMissileTarget());
          //HUD.training("P[k]="+thePk);
        } else {
          //HUD.training("no Target");
        }
        return thePk;
      }

      private void checkAIlaunchMissile() {
        if ((FM instanceof RealFlightModel) && ((RealFlightModel) FM).isRealMode() /*|| !flag*/ || !(FM instanceof Pilot)) {
          return;
        }
        if (this.rocketsList.isEmpty()) {
          return;
        }
        Pilot pilot = (Pilot) FM;
        if (((pilot.get_maneuver() == 27) || (pilot.get_maneuver() == 62) || (pilot.get_maneuver() == 63)) && (pilot.target != null)) { // 27 = ATTACK, 62 = ENERGY_ATTACK, 63 = ATTACK_BOMBER
          trgtAI = pilot.target.actor;
          if (!(Actor.isValid(trgtAI)) || !(trgtAI instanceof Aircraft)) {
            return;
          }
          bToFire = false;
          //HUD.training("Pk=" + trgtPk);
          if ((trgtPk > 25.0F) && (Actor.isValid(this.guidedMissileUtils.getMissileTarget())) && (this.guidedMissileUtils.getMissileTarget() instanceof Aircraft)) {
            if ((this.guidedMissileUtils.getMissileTarget().getArmy() != FM.actor.getArmy()) && (Time.current() > (tX4Prev + 10000L))) {
              //HUD.log("AI launches Sidewinder!");
              bToFire = true;
              tX4Prev = Time.current();
              this.shootRocket();
              bToFire = false;
            }
          }
        } else {
          //HUD.training("No Attack!");
        }
      }

      public void shootRocket() {
        if (rocketsList.isEmpty()) {
          return;
        } else {
          ((RocketGun) rocketsList.get(0)).shots(1);
          return;
        }
      }
    // </editor-fold>
      
    private void gearlimit()
    {
        float f = super.FM.getSpeedKMH() - 550F;
        if(f < 0.0F)
            f = 0.0F;
        ((FlightModelMain) (super.FM)).CT.dvGear = 0.2F - f / 500F;
        if(((FlightModelMain) (super.FM)).CT.dvGear < 0.0F)
            ((FlightModelMain) (super.FM)).CT.dvGear = 0.0F;
    }

    public void onAircraftLoaded() {
        super.onAircraftLoaded();
        rocketsList.clear();
        this.guidedMissileUtils.createMissileList(rocketsList);
      }

      //Radar-ranging gunsite code
      public void typeFighterAceMakerRangeFinder() {
        if (k14Mode == 0) {
          return;
        }
        hunted = Main3D.cur3D().getViewPadlockEnemy();
        if (hunted == null) {
          hunted = War.GetNearestEnemyAircraft(FM.actor, 2000F, 9);
        }
        if (hunted != null) {
          k14Distance = (float) FM.actor.pos.getAbsPoint().distance(hunted.pos.getAbsPoint());
          if (k14Distance > 1700F) {
            k14Distance = 1700F;
          } else if (k14Distance < 200F) {
            k14Distance = 200F;
          }
        }
      }
    public void update(float f)
    {
        super.update(f);
        gearlimit();
        typeFighterAceMakerRangeFinder();
        trgtPk = this.getMissilePk();
        this.guidedMissileUtils.checkLockStatus();
        this.checkAIlaunchMissile();
        resetYPRmodifier();
        float f1 = super.FM.getSpeedKMH() - 1000F;
        if(f1 < 0.0F)
            f1 = 0.0F;
        float f2 = f1 / 4944F;
        if(f2 > 0.25F)
            f2 = 0.25F;
        Aircraft.xyz[0] = Aircraft.cvt(f2, 0.0F, 0.25F, 0.0F, 0.25F);
        hierMesh().chunkSetLocate("Cone", Aircraft.xyz, Aircraft.ypr);
        resetYPRmodifier();
        float f3 = ((FlightModelMain) (super.FM)).CT.getPowerControl() * 1.5F;
        Aircraft.xyz[0] = Aircraft.cvt(f3, 0.0F, 1.5F, 0.0F, 1.5F);
        hierMesh().chunkSetLocate("EffectBox", Aircraft.xyz, Aircraft.ypr);
        for(int i = 1; i < 19; i++)
            hierMesh().chunkSetAngles("EngineExhaustFlap" + i, 0.0F, -38F * ((FlightModelMain) (super.FM)).CT.getPowerControl(), 0.0F);

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
    
    public void doEjectCatapult() {
        new MsgAction(false, this)  {

          public void doAction(Object paramObject) {
            Aircraft localAircraft = (Aircraft) paramObject;
            if (Actor.isValid(localAircraft)) {
              Loc localLoc1 = new Loc();
              Loc localLoc2 = new Loc();
              Vector3d localVector3d = new Vector3d(0.0, 0.0, 40.0);
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
    
    private ArrayList rocketsList;
    public boolean bToFire;
    private long tX4Prev;
    private static Actor hunted = null;

    static 
    {
        Class class1 = com.maddox.il2.objects.air.F4D.class;
        new NetAircraft.SPAWN(class1);
        Property.set(class1, "iconFar_shortClassName", "F-4D");
        Property.set(class1, "meshName", "3DO/Plane/F-4C/hier.him");
        Property.set(class1, "PaintScheme", new PaintSchemeFMParMiG21());
        Property.set(class1, "noseart", 1);
        Property.set(class1, "yearService", 1944.9F);
        Property.set(class1, "yearExpired", 1948.3F);
        Property.set(class1, "FlightModel", "FlightModels/F-4C.fmd:Phantoms");
        Property.set(class1, "cockpitClass", new Class[] {
            com.maddox.il2.objects.air.CockpitF4.class
        });
        Property.set(class1, "LOSElevation", 0.965F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 9, 9, 9, 9, 9, 2, 2, 2, 
            2, 2, 2, 2, 2, 9, 9, 3, 3, 9,
            9, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2
        });
        Aircraft.weaponHooksRegister(class1, new String[] {
            "_CANNON01", "_CANNON02", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalRock01", "_ExternalRock01", "_ExternalRock02", 
            "_ExternalRock02", "_ExternalRock03", "_ExternalRock03", "_ExternalRock04", "_ExternalRock04", "_ExternalDev06", "_ExternalDev07", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev08",
            "_ExternalDev09", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13",
            "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", "_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalRock23",
            "_ExternalRock24", "_ExternalRock25", "_ExternalRock26", "_ExternalRock27", "_ExternalRock28", "_ExternalRock29", "_ExternalRock30", "_ExternalRock31", "_ExternalRock32", "_ExternalRock33",
            "_ExternalRock34", "_ExternalRock35", "_ExternalRock36"
        });
    }
}
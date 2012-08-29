// Source File Name: CAC_Sabre_Mk32.java
// Author:           
// Last Modified by: Storebror 2011-06-03
package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3f;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.ai.World;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.objects.weapons.GuidedMissileUtils;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.rts.Time;
import java.util.ArrayList;

public class CAC_Sabre_Mk32 extends F_86F
        implements TypeGuidedMissileCarrier,
        TypeCountermeasure,
        TypeThreatDetector,
        TypeGSuit {

  private GuidedMissileUtils guidedMissileUtils = null;
  private float trgtPk = 0.0F;
  private Actor trgtAI = null;

  public CAC_Sabre_Mk32() {
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
// <editor-fold defaultstate="collapsed" desc="G Forces">
  /**
   * G-Force Resistance, Tolerance and Recovery parmeters.
   * See TypeGSuit.GFactors Private fields implementation
   * for further details.
   */
  private static final float NEG_G_TOLERANCE_FACTOR = 1.5F;
  private static final float NEG_G_TIME_FACTOR = 1.5F;
  private static final float NEG_G_RECOVERY_FACTOR = 1.0F;
  private static final float POS_G_TOLERANCE_FACTOR = 2.0F;
  private static final float POS_G_TIME_FACTOR = 2.0F;
  private static final float POS_G_RECOVERY_FACTOR = 2.0F;

  public void getGFactors(GFactors theGFactors) {
    theGFactors.setGFactors(
            NEG_G_TOLERANCE_FACTOR,
            NEG_G_TIME_FACTOR,
            NEG_G_RECOVERY_FACTOR,
            POS_G_TOLERANCE_FACTOR,
            POS_G_TIME_FACTOR,
            POS_G_RECOVERY_FACTOR);
  }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="TypeGuidedMissileCarrier Implementation">

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
      if (NetMissionTrack.isPlaying()) {
        if (!(FM instanceof RealFlightModel) || !((RealFlightModel) FM).isRealMode()) {
          ((RocketGun) rocketsList.get(0)).loadBullets(0);
        }
      }
      if (World.cur().diffCur.Limited_Ammo || (this != World.getPlayerAircraft())) {
        rocketsList.remove(0);
      }
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

  public void onAircraftLoaded() {
    super.onAircraftLoaded();
    if (super.FM.isPlayers()) {
      ((FlightModelMain) (super.FM)).CT.bHasCockpitDoorControl = true;
      ((FlightModelMain) (super.FM)).CT.dvCockpitDoor = 0.5F;
    }
    rocketsList.clear();
    this.guidedMissileUtils.createMissileList(rocketsList);
  }

  public void update(float f) {
    if (FM.CT.WeaponControl[1] && FM.EI.engines[0].getStage() == 6 && FM.CT.Weapons[0][0].countBullets() != 0) {
      origThrl = ((FlightModelMain) (super.FM)).EI.engines[0].getControlThrottle();
      FM.EI.engines[0].setControlThrottle(origThrl - 0.05F);
      surgeControl = 1;
    } else if (!FM.CT.WeaponControl[1] && FM.EI.engines[0].getStage() == 6 && surgeControl == 1) {
      FM.EI.engines[0].setControlThrottle(origThrl);
      surgeControl = 0;
    }
    super.update(f);
    trgtPk = this.getMissilePk();
    this.guidedMissileUtils.checkLockStatus();
    this.checkAIlaunchMissile();
  }
  private ArrayList rocketsList;
  public boolean bToFire;
  private long tX4Prev;
  private float origThrl;
  private int surgeControl;

  static {
    java.lang.Class localClass = com.maddox.il2.objects.air.CAC_Sabre_Mk32.class;
    new NetAircraft.SPAWN(localClass);
    com.maddox.rts.Property.set(localClass, "iconFar_shortClassName", "CAC_Sabre");
    com.maddox.rts.Property.set(localClass, "meshName_gb", "3DO/Plane/CAC_Sabre_Mk32(Multi1)/hier.him");
    com.maddox.rts.Property.set(localClass, "PaintScheme_gb", new PaintSchemeFMPar1956());
    com.maddox.rts.Property.set(localClass, "meshName", "3DO/Plane/CAC_Sabre_Mk32(Multi1)/hier.him");
    com.maddox.rts.Property.set(localClass, "PaintScheme", new PaintSchemeFMPar06());
    com.maddox.rts.Property.set(localClass, "yearService", 1949.9F);
    com.maddox.rts.Property.set(localClass, "yearExpired", 1960.3F);
    com.maddox.rts.Property.set(localClass, "FlightModel", "FlightModels/CAC_Sabre_Mk32.fmd");
    try {
      com.maddox.rts.Property.set(localClass, "cockpitClass", new java.lang.Class[]{
                java.lang.Class.forName("com.maddox.il2.objects.air.CockpitF_86Flate")
              });
    } catch (java.lang.Exception exception) {
      com.maddox.il2.ai.EventLog.type("Exception in CAC Mk32 Cockpit init, " + exception.getMessage());
    }
    com.maddox.rts.Property.set(localClass, "LOSElevation", 0.725F);
    com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(localClass, new int[]{
              0, 0, 9, 9, 9, 9, 9, 3, 3, 9,
              3, 3, 9, 2, 2, 9, 2, 2, 9, 9,
              9, 9, 9, 3, 3, 9, 3, 3, 2, 2,
              2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
              2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
              2, 2, 9, 9, 9, 9, 9, 9, 9, 9,
              2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
              2, 2, 2, 2, 2, 2, 9, 9, 9, 9,
              9, 9, 9, 9
            });
    com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(localClass, new java.lang.String[]{
              "_MGUN01", "_MGUN02", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalBomb01", "_ExternalBomb01", "_ExternalDev06",
              "_ExternalBomb02", "_ExternalBomb02", "_ExternalDev07", "_ExternalRock01", "_ExternalRock01", "_ExternalDev08", "_ExternalRock02", "_ExternalRock02", "_ExternalDev09", "_ExternalDev10",
              "_ExternalDev11", "_ExternalDev12", "_ExternalDev13", "_ExternalBomb03", "_ExternalBomb03", "_ExternalDev14", "_ExternalBomb04", "_ExternalBomb04", "_ExternalRock03", "_ExternalRock04",
              "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14",
              "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", "_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalRock23", "_ExternalRock24",
              "_ExternalRock25", "_ExternalRock26", "_ExternalDev17", "_ExternalDev18", "_ExternalDev19", "_ExternalDev20", "_ExternalDev21", "_ExternalDev22", "_ExternalDev23", "_ExternalDev24",
              "_ExternalRock27", "_ExternalRock28", "_ExternalRock29", "_ExternalRock30", "_ExternalRock31", "_ExternalRock32", "_ExternalRock33", "_ExternalRock34", "_ExternalRock35", "_ExternalRock36",
              "_ExternalRock37", "_ExternalRock38", "_ExternalRock39", "_ExternalRock40", "_ExternalRock41", "_ExternalRock42", "_ExternalDev25", "_ExternalDev26", "_ExternalDev27", "_ExternalDev28",
              "_ExternalDev29", "_ExternalDev30", "_ExternalDev31", "_ExternalDev32"
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "default", new java.lang.String[]{
              "MGunADEN30ki 150", "MGunADEN30ki 150", null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "2x200dt", new java.lang.String[]{
              "MGunADEN30ki 150", "MGunADEN30ki 150", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "4x200dt", new java.lang.String[]{
              "MGunADEN30ki 150", "MGunADEN30ki 150", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, null, null,
              null, null, null, null, null, null, null, null, "PylonF86_Outboard 1", "PylonF86_Outboard 1",
              "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "2x75nap", new java.lang.String[]{
              "MGunADEN30ki 150", "MGunADEN30ki 150", null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, "PylonF86_Outboard 1", "BombGun75Napalm 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun75Napalm 1", "BombGunNull 1", null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "2x75nap+2x200dt", new java.lang.String[]{
              "MGunADEN30ki 150", "MGunADEN30ki 150", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, "PylonF86_Outboard 1", "BombGun75Napalm 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun75Napalm 1", "BombGunNull 1", null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "2x500", new java.lang.String[]{
              "MGunADEN30ki 150", "MGunADEN30ki 150", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, "PylonF86_Outboard 1", "BombGun500lbsMC 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun500lbsMC 1", "BombGunNull 1", null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "4x500", new java.lang.String[]{
              "MGunADEN30ki 150", "MGunADEN30ki 150", "PylonF86_Outboard 1", "PylonF86_Outboard 1", null, null, null, "BombGun500lbsMC 1", "BombGunNull 1", null,
              "BombGun500lbsMC 1", "BombGunNull 1", null, null, null, null, null, null, null, null,
              null, null, "PylonF86_Outboard 1", "BombGun500lbsMC 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun500lbsMC 1", "BombGunNull 1", null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "2x500+2x1000", new java.lang.String[]{
              "MGunADEN30ki 150", "MGunADEN30ki 150", "PylonF86_Outboard 1", "PylonF86_Outboard 1", null, null, null, "BombGun500lbsMC 1", "BombGunNull 1", null,
              "BombGun500lbsMC 1", "BombGunNull 1", null, null, null, null, null, null, null, null,
              null, null, "PylonF86_Outboard 1", "BombGun1000lbsMC 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun1000lbsMC 1", "BombGunNull 1", null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "2x1000", new java.lang.String[]{
              "MGunADEN30ki 150", "MGunADEN30ki 150", null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, "PylonF86_Outboard 1", "BombGun1000lbsMC 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun1000lbsMC 1", "BombGunNull 1", null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "2x1000+2x200dt", new java.lang.String[]{
              "MGunADEN30ki 150", "MGunADEN30ki 150", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, "PylonF86_Outboard 1", "BombGun1000lbsMC 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun1000lbsMC 1", "BombGunNull 1", null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "16xRP3", new java.lang.String[]{
              "MGunADEN30ki 150", "MGunADEN30ki 150", null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1",
              "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "PylonSpitROCK 1", "PylonSpitROCK 1", "PylonSpitROCK 1", "PylonSpitROCK 1",
              "PylonSpitROCK 1", "PylonSpitROCK 1", "PylonSpitROCK 1", "PylonSpitROCK 1"
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "8xRP3+2x200dt", new java.lang.String[]{
              "MGunADEN30ki 150", "MGunADEN30ki 150", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1",
              "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", null, null, null, null,
              "PylonSpitROCK 1", "PylonSpitROCK 1", "PylonSpitROCK 1", "PylonSpitROCK 1"
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "24xSURA_D_HE", new java.lang.String[]{
              "MGunADEN30ki 150", "MGunADEN30ki 150", null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, "RocketGunSURA_HE 1", "RocketGunSURA_HE 1",
              "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1",
              "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1",
              "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1",
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "24xSURA_D_AP", new java.lang.String[]{
              "MGunADEN30ki 150", "MGunADEN30ki 150", null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, "RocketGunSURA_AP 1", "RocketGunSURA_AP 1",
              "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1",
              "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1",
              "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1",
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "2xAIM9B", new java.lang.String[]{
              "MGunADEN30ki 150", "MGunADEN30ki 150", null, null, null, null, null, null, null, null,
              null, null, "PylonF86_Sidewinder 1", "RocketGunAIM9B 1", "RocketGunNull 1", "PylonF86_Sidewinder 1", "RocketGunAIM9B 1", "RocketGunNull 1", null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "2xAIM9B+2x200dt", new java.lang.String[]{
              "MGunADEN30ki 150", "MGunADEN30ki 150", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, null, null,
              null, null, "PylonF86_Sidewinder 1", "RocketGunAIM9B 1", "RocketGunNull 1", "PylonF86_Sidewinder 1", "RocketGunAIM9B 1", "RocketGunNull 1", null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "none", new java.lang.String[]{
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null
            });
  }
}
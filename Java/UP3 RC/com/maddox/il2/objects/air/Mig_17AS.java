// Source File Name: Mig_17AS.java
// Author:           
// Last Modified by: Storebror 2011-06-03
package com.maddox.il2.objects.air;

import java.util.ArrayList;

import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.JGP.Point3f;
import com.maddox.il2.ai.World;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.il2.objects.weapons.GuidedMissileUtils;

public class Mig_17AS extends Mig_17
        implements TypeGuidedMissileCarrier,
        TypeCountermeasure,
        TypeThreatDetector,
        TypeGSuit {

  private GuidedMissileUtils guidedMissileUtils = null;
  private float trgtPk = 0.0F;
  private Actor trgtAI = null;

  public Mig_17AS() {
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
  private static final float NEG_G_TOLERANCE_FACTOR = 1.0F;
  private static final float NEG_G_TIME_FACTOR = 1.0F;
  private static final float NEG_G_RECOVERY_FACTOR = 1.0F;
  private static final float POS_G_TOLERANCE_FACTOR = 1.8F;
  private static final float POS_G_TIME_FACTOR = 1.5F;
  private static final float POS_G_RECOVERY_FACTOR = 1.0F;

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

  public void update(float f1) {
    super.update(f1);
    typeFighterAceMakerRangeFinder();
    trgtPk = this.getMissilePk();
    this.guidedMissileUtils.checkLockStatus();
    this.checkAIlaunchMissile();
    if (FM.AS.isMaster() && Config.isUSE_RENDER()) {
      if (FM.EI.engines[0].getPowerOutput() > 0.50F
              && FM.EI.engines[0].getStage() == 6) {
        if (FM.EI.engines[0].getPowerOutput() > 0.50F) {
          if (FM.EI.engines[0].getPowerOutput() > 1.001F) {
            FM.AS.setSootState(this, 0, 5);
          } else {
            FM.AS.setSootState(this, 0, 3);
          }
        }
      } else {
        FM.AS.setSootState(this, 0, 0);
      }
    }
  }
  private ArrayList rocketsList;
  public boolean bToFire;
  private long tX4Prev;

  static {
    Class var_class = com.maddox.il2.objects.air.Mig_17AS.class;
    new NetAircraft.SPAWN(var_class);
    Property.set(var_class, "iconFar_shortClassName", "MiG-17AS");
    Property.set(var_class, "meshName_ru", "3DO/Plane/MiG-17F(Multi1)/hier.him");
    Property.set(var_class, "PaintScheme_ru", new PaintSchemeFCSPar1956());
    Property.set(var_class, "meshName_sk", "3DO/Plane/MiG-17F(Multi1)/hier.him");
    Property.set(var_class, "PaintScheme_sk", new PaintSchemeFMPar1956());
    Property.set(var_class, "meshName_ro", "3DO/Plane/MiG-17F(Multi1)/hier.him");
    Property.set(var_class, "PaintScheme_ro", new PaintSchemeFMPar1956());
    Property.set(var_class, "meshName_hu", "3DO/Plane/MiG-17F(Multi1)/hier.him");
    Property.set(var_class, "PaintScheme_hu", new PaintSchemeFMPar1956());
    Property.set(var_class, "meshName", "3DO/Plane/MiG-17F(Multi1)/hier.him");
    Property.set(var_class, "PaintScheme", new PaintSchemeFMPar06());
    Property.set(var_class, "yearService", 1952.11F);
    Property.set(var_class, "yearExpired", 1960.3F);
    Property.set(var_class, "FlightModel", "FlightModels/MiG-17F.fmd");
    Property.set(var_class, "cockpitClass", new Class[]{
              com.maddox.il2.objects.air.CockpitMig_17.class
            });
    Property.set(var_class, "LOSElevation", 0.725F);
    com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(var_class, new int[]{
              1, 0, 0, 9, 9, 9, 9, 9, 9, 2,
              2, 2, 2, 2, 2, 2, 2, 9, 9, 2,
              2, 2, 2, 9, 9, 9, 9, 2, 2, 2,
              2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
              2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
              2, 2, 2, 2, 2, 2, 2, 2, 2, 9,
              9, 2, 2, 2, 2, 2, 2, 2, 2, 2,
              2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
              2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
              2, 2, 2, 9, 9, 3, 3, 3, 3, 3,
              3, 3, 3
            });
    com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(var_class, new java.lang.String[]{
              "_CANNON01", "_CANNON02", "_CANNON03", "_ExternalDev03", "_ExternalDev04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev05", "_ExternalDev06", "_ExternalRock01",
              "_ExternalRock01", "_ExternalRock02", "_ExternalRock02", "_ExternalRock03", "_ExternalRock03", "_ExternalRock04", "_ExternalRock04", "_ExternalDev07", "_ExternalDev08", "_ExternalRock05",
              "_ExternalRock05", "_ExternalRock06", "_ExternalRock06", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09",
              "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", "_ExternalRock19",
              "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalRock23", "_ExternalRock24", "_ExternalRock25", "_ExternalRock26", "_ExternalRock27", "_ExternalRock28", "_ExternalRock29",
              "_ExternalRock30", "_ExternalRock31", "_ExternalRock32", "_ExternalRock33", "_ExternalRock34", "_ExternalRock35", "_ExternalRock36", "_ExternalRock37", "_ExternalRock38", "_ExternalDev13",
              "_ExternalDev14", "_ExternalRock39", "_ExternalRock40", "_ExternalRock41", "_ExternalRock42", "_ExternalRock43", "_ExternalRock44", "_ExternalRock45", "_ExternalRock46", "_ExternalRock47",
              "_ExternalRock48", "_ExternalRock49", "_ExternalRock50", "_ExternalRock51", "_ExternalRock52", "_ExternalRock53", "_ExternalRock54", "_ExternalRock55", "_ExternalRock56", "_ExternalRock57",
              "_ExternalRock58", "_ExternalRock59", "_ExternalRock60", "_ExternalRock61", "_ExternalRock62", "_ExternalRock63", "_ExternalRock64", "_ExternalRock65", "_ExternalRock66", "_ExternalRock67",
              "_ExternalRock68", "_ExternalRock69", "_ExternalRock70", "_ExternalDev15", "_ExternalDev16", "_ExternalBomb01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb02", "_ExternalBomb03",
              "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb04"
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "default", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xdrops", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2x100", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, "PylonMiG17Inner 1",
              "PylonMiG17Inner 1", null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, "BombGunFAB100 1",
              "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1"
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2x100+2xdrops", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, "PylonMiG17Inner 1",
              "PylonMiG17Inner 1", null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, "BombGunFAB100 1",
              "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1"
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2x100+2x250m46", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, "PylonMiG17Inner 1",
              "PylonMiG17Inner 1", null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, "PylonMiG15 1", "PylonMiG15 1", "BombGunFAB250m46 1", "BombGunNull 1", "BombGunFAB250m46 1", "BombGunNull 1", "BombGunFAB100 1",
              "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1"
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4x100", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, "PylonMiG17Inner 1",
              "PylonMiG17Inner 1", null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, "PylonMiG15 1", "PylonMiG15 1", "BombGunFAB100 1", "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1", "BombGunFAB100 1",
              "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1"
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2x250m46", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, "PylonMiG15 1", "PylonMiG15 1", "BombGunFAB250m46 1", "BombGunNull 1", "BombGunFAB250m46 1", "BombGunNull 1", null,
              null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xMARS2", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, "PylonMARS2 1",
              "PylonMARS2 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
              "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
              "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
              "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", null, null, null, null, null, null, null,
              null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xMARS2+2xdrops", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, "PylonMARS2 1",
              "PylonMARS2 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
              "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
              "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
              "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", null, null, null, null, null, null, null,
              null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xORO57", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, "PylonORO57 1", "PylonORO57 1", null, null, "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
              "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
              "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xORO57+2xdrops", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, "PylonORO57 1", "PylonORO57 1", null, null, "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
              "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
              "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4xORO57", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, "PylonORO57 1", "PylonORO57 1", "PylonORO57 1", "PylonORO57 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
              "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
              "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
              "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4xORO57+2xdrops", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, "PylonORO57 1", "PylonORO57 1", "PylonORO57 1", "PylonORO57 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
              "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
              "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
              "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xK13A", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, "PylonK13A 1", "PylonK13A 1", "RocketGunK13A 1",
              "RocketGunNull 1", "RocketGunK13A 1", "RocketGunNull 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xK13A+2xdrops", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", null, null, null, null, null,
              null, null, null, null, null, null, null, "PylonK13A 1", "PylonK13A 1", "RocketGunK13A 1",
              "RocketGunNull 1", "RocketGunK13A 1", "RocketGunNull 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null
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
              null, null, null
            });
  }
}
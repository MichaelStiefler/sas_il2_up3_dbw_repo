// Source File Name: MissileGun.java
// Author:           Storebror
package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.NetSafeLog;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeGuidedMissileCarrier;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class MissileGun extends RocketGun {
  private boolean engineWarmupRunning = false;
  private float engineWarmupTime = 0.0F;
  private int shotsAfterEngineWarmup = 0;
  public static int hudMissileGunId = 0;
  private String theMissileName = null;
  
  public float bulletMassa() {
    return bulletMassa / 10F;
  }
  
  public Missile getMissile() {
    return (Missile)this.rocket;
  }
  
  public void checkPendingWeaponRelease()
  {
//    com.maddox.il2.game.HUD.log("checkPendingWeaponRelease");
    if (this.engineWarmupRunning) {
      Missile theMissile = (Missile)this.rocket;
      if (theMissile == null) {
        this.engineWarmupRunning = false;
        return;
      }
      if (Time.current() > theMissile.getStartTime() + (long)this.engineWarmupTime) {
        this.shots(this.shotsAfterEngineWarmup);
      } else {
        theMissile.runupEngine();
      }
    }
    return;
  }

  public void shots(int paramInt) {
    if (paramInt == 0) return;
    if (hudMissileGunId == 0) hudMissileGunId = HUD.makeIdLog();
    Missile theMissile = (Missile) this.rocket;
    if (theMissileName == null) {
      if (theMissile != null) {
        theMissileName = Property.stringValue(theMissile.getClass(), "friendlyName", "Missile");
      }
    }
    EventLog.type("MissileGun shots 1");
    try {
      if (Actor.isValid(super.actor) && (super.actor instanceof Aircraft) && (super.actor instanceof TypeGuidedMissileCarrier) && (Aircraft) super.actor == World.getPlayerAircraft() && ((RealFlightModel) ((SndAircraft) ((Aircraft) super.actor)).FM).isRealMode() && ((TypeGuidedMissileCarrier) super.actor).hasMissiles() && ((TypeGuidedMissileCarrier) super.actor).getMissileLockState() == 0) {
        NetSafeLog.log(super.actor, hudMissileGunId, theMissileName + " launch cancelled (disengaged)");
        return;
      }
    } catch (Exception exception) {
      NetSafeLog.log(super.actor, hudMissileGunId, theMissileName + " launch cancelled (system error)");
      return;
    }
    if (theMissile == null) {
      EventLog.type("MissileGun shots 0");
      this.engineWarmupRunning = false;
      return;
    }
    if (this.engineWarmupRunning) {
      if (Time.current() < theMissile.getStartTime() + (long)this.engineWarmupTime) {
    Exception testException = new Exception();
    testException.printStackTrace();
        
        NetSafeLog.log(super.actor, hudMissileGunId, theMissileName + " launch cancelled (engine warmup running)");
        return;
      }
    }
    this.engineWarmupTime = Property.floatValue(theMissile.getClass(), "warmupTime", 0.0F);
    if (this.engineWarmupTime > 0) {
      EventLog.type("MissileGun shots 2");
      if (!this.engineWarmupRunning) {
        EventLog.type("MissileGun shots 3");
        theMissile.startEngine();
        theMissile.setStartTime();
        NetSafeLog.log(super.actor, hudMissileGunId, theMissileName + " engine starting");
        this.engineWarmupRunning = true;
        this.shotsAfterEngineWarmup = paramInt;
        return;
      }
    }
    EventLog.type("MissileGun shots 4");
    this.engineWarmupRunning = false;
    super.shots(paramInt);
    NetSafeLog.log(super.actor, hudMissileGunId, theMissileName + " released");
    if (!(NetMissionTrack.isPlaying() || Mission.isNet())) {
      if ( (paramInt > 0 )
              && Actor.isValid(super.actor)
              && (super.actor instanceof TypeGuidedMissileCarrier)
              && (World.cur().diffCur.Limited_Ammo
              || (super.actor != World.getPlayerAircraft()))) {
        ((TypeGuidedMissileCarrier)super.actor).shotMissile();
      }
    }
  }

}

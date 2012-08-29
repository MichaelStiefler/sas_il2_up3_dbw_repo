package com.maddox.il2.objects.weapons;

//import com.maddox.il2.ai.World;
//import com.maddox.il2.engine.Actor;
//import com.maddox.il2.fm.RealFlightModel;
//import com.maddox.il2.game.Mission;
//import com.maddox.il2.game.NetSafeLog;
//import com.maddox.il2.net.NetMissionTrack;
//import com.maddox.il2.objects.air.Aircraft;
//import com.maddox.il2.objects.air.TypeGuidedMissileCarrier;
//import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Property;
//import com.maddox.rts.Time;

public class RocketGunKS1 extends MissileGun implements RocketGunWithDelay
{
  static
  {
    Class class1 = RocketGunKS1.class;
    Property.set(class1, "bulletClass", (Object)MissileKS1.class);
    Property.set(class1, "bullets", 1);
    Property.set(class1, "shotFreq", 2.0F);
    Property.set(class1, "sound", "weapon.bombgun");
    Property.set(class1, "dateOfUse", 19450401);
  }
}

//
//public class RocketGunKS1 extends RocketGun implements RocketGunWithDelay
//{
//  private boolean engineWarmupRunning = false;
//  private float engineWarmupTime = 0.0F;
//  private int shotsAfterEngineWarmup = 0;
//  public static int hudLogRocketGunKS1Id = -1001;
//  static
//  {
//    Class class1 = RocketGunKS1.class;
//    Property.set(class1, "bulletClass", (Object)MissileKS1.class);
//    Property.set(class1, "bullets", 1);
//    Property.set(class1, "shotFreq", 2.0F);
//    Property.set(class1, "sound", "weapon.bombgun");
//    Property.set(class1, "dateOfUse", 19450401);
//  }
//  
//  public float bulletMassa() {
//    return bulletMassa / 10F;
//  }
//  
//  public Missile getMissile() {
//    return (Missile)this.rocket;
//  }
//  
////  public boolean isPause() { return isPaused; } 
////  public void setPause(boolean paramBoolean) {
////    isPaused = paramBoolean;
////  }
//  
//  public void checkPendingWeaponRelease()
//  {
////    com.maddox.il2.game.HUD.log("checkPendingWeaponRelease");
//    if (this.engineWarmupRunning) {
//      Missile theMissile = (Missile)this.rocket;
//      if (theMissile == null) {
//        this.engineWarmupRunning = false;
//        return;
//      }
//      if (Time.current() > theMissile.getStartTime() + (long)this.engineWarmupTime) {
//        this.shots(this.shotsAfterEngineWarmup);
//      } else {
//        theMissile.runupEngine();
//      }
//    }
//    return;
//  }
//
//  public void shots(int paramInt) {
//    try {
//      if (Actor.isValid(super.actor) && (super.actor instanceof Aircraft) && (super.actor instanceof TypeGuidedMissileCarrier) && (Aircraft) super.actor == World.getPlayerAircraft() && ((RealFlightModel) ((SndAircraft) ((Aircraft) super.actor)).FM).isRealMode() && ((TypeGuidedMissileCarrier) super.actor).hasMissiles() && ((TypeGuidedMissileCarrier) super.actor).getMissileLockState() == 0) {
//        NetSafeLog.log(super.actor, hudLogRocketGunKS1Id, "KS-1 launch cancelled (disengaged)");
//        return;
//      }
//    } catch (Exception exception) {
//      NetSafeLog.log(super.actor, hudLogRocketGunKS1Id, "KS-1 launch cancelled (system error)");
//      return;
//    }
//    Missile theMissile = (Missile)this.rocket;
//    if (theMissile == null) {
//      this.engineWarmupRunning = false;
//      return;
//    }
//    if (this.engineWarmupRunning) {
//      if (Time.current() < theMissile.getStartTime() + (long)this.engineWarmupTime) {
//        NetSafeLog.log(super.actor, hudLogRocketGunKS1Id, "KS-1 launch cancelled (engine warmup running)");
//        return;
//      }
//    }
//    this.engineWarmupTime = Property.floatValue(theMissile.getClass(), "warmupTime", 0.0F);
//    if (this.engineWarmupTime > 0) {
//      if (!this.engineWarmupRunning) {
//        theMissile.startEngine();
//        theMissile.setStartTime();
//        NetSafeLog.log(super.actor, hudLogRocketGunKS1Id, "KS-1 engine starting");
//        this.engineWarmupRunning = true;
//        this.shotsAfterEngineWarmup = paramInt;
//        return;
//      }
//    }
//    this.engineWarmupRunning = false;
//    super.shots(paramInt);
//    NetSafeLog.log(super.actor, hudLogRocketGunKS1Id, "KS-1 released");
//    if (!(NetMissionTrack.isPlaying() || Mission.isNet())) {
//      if ( (paramInt > 0 )
//              && Actor.isValid(super.actor)
//              && (super.actor instanceof TypeGuidedMissileCarrier)
//              && (World.cur().diffCur.Limited_Ammo
//              || (super.actor != World.getPlayerAircraft()))) {
//        ((TypeGuidedMissileCarrier)super.actor).shotMissile();
//      }
//    }
//  }
//
//}
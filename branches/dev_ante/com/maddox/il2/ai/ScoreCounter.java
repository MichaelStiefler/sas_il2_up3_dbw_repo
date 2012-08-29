package com.maddox.il2.ai;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.Scheme1;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.util.ArrayList;

public class ScoreCounter
{
  public ArrayList enemyItems;
  public ArrayList friendItems;
  public ArrayList targetOnItems;
  public ArrayList targetOffItems;
  public int bulletsFire;
  public int bulletsHit;
  public int bulletsHitAir;
  public int rocketsFire;
  public int rocketsHit;
  public int bombFire;
  public int bombHit;
  public static final int PLAYER_IS_AIR_SINGLE = 0;
  public static final int PLAYER_IS_AIR_MULTI = 1;
  public static final int PLAYER_IS_AIR_GUNNER = 2;
  public boolean bPlayerDead;
  public boolean bPlayerParatrooper;
  public boolean bLandedFarAirdrome;
  public boolean bPlayerCaptured;
  public int nPlayerTakeoffs;
  public int nPlayerLandings;
  public int nPlayerDitches;
  public boolean bLanded;
  public boolean bPlayerStateUnknown;
  public long timeStart;
  public int player_is;
  public float todStart;
  public boolean bCrossCountry;

  public ScoreCounter()
  {
    this.enemyItems = new ArrayList();
    this.friendItems = new ArrayList();
    this.targetOnItems = new ArrayList();
    this.targetOffItems = new ArrayList();

    this.bPlayerDead = false;
    this.bPlayerParatrooper = false;
    this.bLandedFarAirdrome = false;
    this.bPlayerCaptured = false;

    this.bLanded = false;
    this.bPlayerStateUnknown = false;

    this.timeStart = -1L;
    this.player_is = 0;

    this.bCrossCountry = false;
  }
  public void playerDead() {
    this.bPlayerDead = true;
  }
  public void playerParatrooper() {
    this.bPlayerParatrooper = true;
  }
  public void playerTakeoff() {
    this.nPlayerTakeoffs += 1;
    this.bLandedFarAirdrome = false;
    this.bLanded = false;
  }
  public void playerLanding(boolean paramBoolean) {
    this.bLanded = true;
    if (paramBoolean) {
      this.nPlayerDitches += 1;
      this.bLandedFarAirdrome = true;
    } else {
      this.nPlayerLandings += 1;
      this.bLandedFarAirdrome = false;
    }
  }

  public void playerCaptured() {
    this.bPlayerCaptured = true;
  }

  public void playerStartAir(Aircraft paramAircraft) {
    this.timeStart = Time.currentReal();
    this.todStart = World.getTimeofDay();
    if ((paramAircraft instanceof Scheme1))
      this.player_is = 0;
    else
      this.player_is = 1; 
  }

  public void playerStartGunner() {
    this.timeStart = Time.currentReal();
    this.todStart = World.getTimeofDay();
    this.player_is = 2;
  }
  public void playerDoCrossCountry() {
    this.bCrossCountry = true;
  }
  public void playerUpdateState() {
    this.bPlayerStateUnknown = false;
    if ((!this.bPlayerDead) && (!this.bPlayerParatrooper)) {
      FlightModel localFlightModel = World.getPlayerFM();
      if (localFlightModel != null)
        this.bLanded = localFlightModel.isStationedOnGround();
      else
        this.bPlayerStateUnknown = true;
    }
  }

  public void enemyDestroyed(Actor paramActor)
  {
    Register localRegister = getRegistered(paramActor);
    if (localRegister == null) return;
    this.enemyItems.add(new ScoreItem(localRegister.type, localRegister.enemy));
    switch (localRegister.type) { case 0:
      HUD.log("EnemyAircraftDestroyed"); break;
    case 1:
      HUD.log("EnemyTankDestroyed"); break;
    case 2:
      HUD.log("EnemyCarDestroyed"); break;
    case 3:
      HUD.log("EnemyArtilleryDestroyed"); break;
    case 4:
      HUD.log("EnemyAAADestroyed"); break;
    case 5:
      HUD.log("EnemyBridgeDestroyed"); break;
    case 6:
      HUD.log("EnemyWagonDestroyed"); break;
    case 7:
      HUD.log("EnemyShipDestroyed"); break;
    case 8:
      HUD.log("EnemyStaticAircraftDestroyed"); }
  }

  public void friendDestroyed(Actor paramActor) {
    Register localRegister = getRegistered(paramActor);
    if (localRegister == null) return;
    this.friendItems.add(new ScoreItem(localRegister.type, localRegister.friend));
    switch (localRegister.type) { case 0:
      HUD.log("FriendAircraftDestroyed"); break;
    case 1:
      HUD.log("FriendTankDestroyed"); break;
    case 2:
      HUD.log("FriendCarDestroyed"); break;
    case 3:
      HUD.log("FriendArtilleryDestroyed"); break;
    case 4:
      HUD.log("FriendAAADestroyed"); break;
    case 5:
      HUD.log("FriendBridgeDestroyed"); break;
    case 6:
      HUD.log("FriendWagonDestroyed"); break;
    case 7:
      HUD.log("FriendShipDestroyed"); break;
    case 8:
      HUD.log("FriendStaticAircraftDestroyed"); }
  }

  public int getRegisteredType(Actor paramActor)
  {
    if (paramActor == null) return -1;
    Register localRegister = (Register)Property.value(paramActor.getClass(), "scoreDefine", null);
    if (localRegister == null)
      return -1;
    return localRegister.type;
  }

  private Register getRegistered(Actor paramActor) {
    if (paramActor == null) return null;
    Register localRegister = (Register)Property.value(paramActor.getClass(), "scoreDefine", null);

    if ((localRegister == null) && (World.cur().isDebugFM()))
      System.out.println("Class '" + paramActor.getClass().getName() + "' NOT registered in score database");
    return localRegister;
  }

  public void targetOn(Target paramTarget, boolean paramBoolean)
  {
    double d;
    int i;
    if (paramBoolean) {
      d = 0.0D;
      i = 0;
      switch (paramTarget.importance()) { case 0:
        i = 100; d = 50.0D;
        HUD.log("PrimaryTargetComplete");
        break;
      case 1:
        i = 101; d = 50.0D;
        HUD.log("SecondaryTargetComplete");
        break;
      case 2:
        i = 102; d = 100.0D;
        HUD.log("SecretTargetComplete");
      }

      if (Mission.isNet()) d = 0.0D;
      this.targetOnItems.add(new ScoreItem(i, d));
    } else {
      d = 0.0D;
      i = 0;
      switch (paramTarget.importance()) { case 0:
        i = 100; d = 0.0D;
        HUD.log("PrimaryTargetFailed");
        break;
      case 1:
        i = 101; d = 0.0D;
        HUD.log("SecondaryTargetFailed");
        break;
      case 2:
        i = 102; d = 0.0D;
      }

      if (Mission.isNet()) d = 0.0D;
      this.targetOffItems.add(new ScoreItem(i, d));
    }
  }

  public void resetGame() {
    this.enemyItems.clear();
    this.friendItems.clear();
    this.targetOnItems.clear();
    this.targetOffItems.clear();
    this.bulletsFire = 0;
    this.bulletsHit = 0;
    this.bulletsHitAir = 0;
    this.rocketsFire = 0;
    this.rocketsHit = 0;
    this.bombFire = 0;
    this.bombHit = 0;

    this.bPlayerDead = false;
    this.bPlayerParatrooper = false;
    this.bLandedFarAirdrome = false;
    this.bPlayerCaptured = false;
    this.nPlayerTakeoffs = 0;
    this.nPlayerLandings = 0;
    this.nPlayerDitches = 0;
    this.timeStart = -1L;
    this.player_is = 0;
    this.bCrossCountry = false;
    this.bLanded = false;
  }

  public static void register(Class paramClass, int paramInt, double paramDouble1, double paramDouble2) {
    Property.set(paramClass, "scoreDefine", new Register(paramInt, paramDouble1, paramDouble2));
  }
  static class Register { public int type;
    public double enemy;
    public double friend;

    public Register(int paramInt, double paramDouble1, double paramDouble2) { this.type = paramInt; this.enemy = paramDouble1; this.friend = paramDouble2;
    }
  }
}
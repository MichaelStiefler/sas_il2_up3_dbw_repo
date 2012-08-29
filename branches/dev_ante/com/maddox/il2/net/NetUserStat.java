package com.maddox.il2.net;

import com.maddox.il2.ai.ScoreCounter;
import com.maddox.il2.ai.ScoreItem;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.EffClouds;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgOutput;
import com.maddox.rts.Time;
import java.io.IOException;
import java.util.ArrayList;

public class NetUserStat
{
  public int nMissions;
  public int nSorties;
  public int nTakeoffs;
  public int nLandings;
  public int nDitches;
  public int nBails;
  public int nDeaths;
  public int nCaptures;
  public float tTotal;
  public float tSingle;
  public float tMulti;
  public float tGunner;
  public float tNight;
  public float tIns;
  public float tCCountry;
  public float rating;
  public double score = 0.0D;
  public int[] enemyKill = new int[9];
  public int[] friendKill = new int[9];
  public int bulletsFire;
  public int bulletsHit;
  public int bulletsHitAir;
  public int rocketsFire;
  public int rocketsHit;
  public int bombFire;
  public int bombHit;
  public int curPlayerState;
  public static final int PLAYER_STATE_DEAD = 1;
  public static final int PLAYER_STATE_PARATROOPER = 2;
  public static final int PLAYER_STATE_LANDED = 4;
  public static final int PLAYER_STATE_LANDEDFARAIRDROME = 8;
  public static final int PLAYER_STATE_CAPTURED = 16;
  public static final int PLAYER_STATE_UNKNOWN = 32;

  public void read(NetMsgInput paramNetMsgInput)
    throws IOException
  {
    this.nMissions = paramNetMsgInput.readInt();
    this.nSorties = paramNetMsgInput.readInt();
    this.nTakeoffs = paramNetMsgInput.readInt();
    this.nLandings = paramNetMsgInput.readInt();
    this.nDitches = paramNetMsgInput.readInt();
    this.nBails = paramNetMsgInput.readInt();
    this.nDeaths = paramNetMsgInput.readInt();
    this.nCaptures = paramNetMsgInput.readInt();
    this.tTotal = paramNetMsgInput.readFloat();
    this.tSingle = paramNetMsgInput.readFloat();
    this.tMulti = paramNetMsgInput.readFloat();
    this.tGunner = paramNetMsgInput.readFloat();
    this.tNight = paramNetMsgInput.readFloat();
    this.tIns = paramNetMsgInput.readFloat();
    this.tCCountry = paramNetMsgInput.readFloat();
    this.rating = paramNetMsgInput.readFloat();
    this.score = paramNetMsgInput.readFloat();
    for (int i = 0; i < 9; i++) this.enemyKill[i] = paramNetMsgInput.readInt();
    for (int j = 0; j < 9; j++) this.friendKill[j] = paramNetMsgInput.readInt();
    this.bulletsFire = paramNetMsgInput.readInt();
    this.bulletsHit = paramNetMsgInput.readInt();
    this.bulletsHitAir = paramNetMsgInput.readInt();
    this.rocketsFire = paramNetMsgInput.readInt();
    this.rocketsHit = paramNetMsgInput.readInt();
    this.bombFire = paramNetMsgInput.readInt();
    this.bombHit = paramNetMsgInput.readInt();

    this.curPlayerState = paramNetMsgInput.readByte();
  }
  public void write(NetMsgOutput paramNetMsgOutput) throws IOException {
    paramNetMsgOutput.writeInt(this.nMissions);
    paramNetMsgOutput.writeInt(this.nSorties);
    paramNetMsgOutput.writeInt(this.nTakeoffs);
    paramNetMsgOutput.writeInt(this.nLandings);
    paramNetMsgOutput.writeInt(this.nDitches);
    paramNetMsgOutput.writeInt(this.nBails);
    paramNetMsgOutput.writeInt(this.nDeaths);
    paramNetMsgOutput.writeInt(this.nCaptures);
    paramNetMsgOutput.writeFloat(this.tTotal);
    paramNetMsgOutput.writeFloat(this.tSingle);
    paramNetMsgOutput.writeFloat(this.tMulti);
    paramNetMsgOutput.writeFloat(this.tGunner);
    paramNetMsgOutput.writeFloat(this.tNight);
    paramNetMsgOutput.writeFloat(this.tIns);
    paramNetMsgOutput.writeFloat(this.tCCountry);
    paramNetMsgOutput.writeFloat(this.rating);
    paramNetMsgOutput.writeFloat((float)this.score);
    for (int i = 0; i < 9; i++) paramNetMsgOutput.writeInt(this.enemyKill[i]);
    for (int j = 0; j < 9; j++) paramNetMsgOutput.writeInt(this.friendKill[j]);
    paramNetMsgOutput.writeInt(this.bulletsFire);
    paramNetMsgOutput.writeInt(this.bulletsHit);
    paramNetMsgOutput.writeInt(this.bulletsHitAir);
    paramNetMsgOutput.writeInt(this.rocketsFire);
    paramNetMsgOutput.writeInt(this.rocketsHit);
    paramNetMsgOutput.writeInt(this.bombFire);
    paramNetMsgOutput.writeInt(this.bombHit);

    paramNetMsgOutput.writeByte(this.curPlayerState);
  }

  public boolean isEmpty() {
    if (this.nMissions != 0) return false;
    if (this.nSorties != 0) return false;
    if (this.nTakeoffs != 0) return false;
    if (this.nLandings != 0) return false;
    if (this.nDitches != 0) return false;
    if (this.nBails != 0) return false;
    if (this.nDeaths != 0) return false;
    if (this.nCaptures != 0) return false;
    if (this.tTotal != 0.0F) return false;
    if (this.tSingle != 0.0F) return false;
    if (this.tMulti != 0.0F) return false;
    if (this.tGunner != 0.0F) return false;
    if (this.tNight != 0.0F) return false;
    if (this.tIns != 0.0F) return false;
    if (this.tCCountry != 0.0F) return false;
    if (this.rating != 0.0F) return false;
    if (this.score != 0.0D) return false;
    for (int i = 0; i < 9; i++) if (this.enemyKill[i] != 0) return false;
    for (int j = 0; j < 9; j++) if (this.friendKill[j] != 0) return false;
    if (this.bulletsFire != 0) return false;
    if (this.bulletsHit != 0) return false;
    if (this.bulletsHitAir != 0) return false;
    if (this.rocketsFire != 0) return false;
    if (this.rocketsHit != 0) return false;
    if (this.bombFire != 0) return false;
    return this.bombHit == 0;
  }

  public boolean isEqualsCurrent(NetUserStat paramNetUserStat)
  {
    if (this.score != paramNetUserStat.score) return false;
    if (this.curPlayerState != paramNetUserStat.curPlayerState) return false;
    for (int i = 0; i < 9; i++) if (this.enemyKill[i] != paramNetUserStat.enemyKill[i]) return false;
    for (int j = 0; j < 9; j++) if (this.friendKill[j] != paramNetUserStat.friendKill[j]) return false;
    if (this.bulletsFire != paramNetUserStat.bulletsFire) return false;
    if (this.bulletsHit != paramNetUserStat.bulletsHit) return false;
    if (this.bulletsHitAir != paramNetUserStat.bulletsHitAir) return false;
    if (this.rocketsFire != paramNetUserStat.rocketsFire) return false;
    if (this.rocketsHit != paramNetUserStat.rocketsHit) return false;
    if (this.bombFire != paramNetUserStat.bombFire) return false;
    return this.bombHit == paramNetUserStat.bombHit;
  }

  public void clear()
  {
    this.nMissions = 0;
    this.nSorties = 0;
    this.nTakeoffs = 0;
    this.nLandings = 0;
    this.nDitches = 0;
    this.nBails = 0;
    this.nDeaths = 0;
    this.nCaptures = 0;
    this.tTotal = 0.0F;
    this.tSingle = 0.0F;
    this.tMulti = 0.0F;
    this.tGunner = 0.0F;
    this.tNight = 0.0F;
    this.tIns = 0.0F;
    this.tCCountry = 0.0F;
    this.rating = 0.0F;
    this.score = 0.0D;
    for (int i = 0; i < 9; i++) this.enemyKill[i] = 0;
    for (int j = 0; j < 9; j++) this.friendKill[j] = 0;
    this.bulletsFire = 0;
    this.bulletsHit = 0;
    this.bulletsHitAir = 0;
    this.rocketsFire = 0;
    this.rocketsHit = 0;
    this.bombFire = 0;
    this.bombHit = 0;

    this.curPlayerState = 4;
  }

  public void set(NetUserStat paramNetUserStat) {
    this.nMissions = paramNetUserStat.nMissions;
    this.nSorties = paramNetUserStat.nSorties;
    this.nTakeoffs = paramNetUserStat.nTakeoffs;
    this.nLandings = paramNetUserStat.nLandings;
    this.nDitches = paramNetUserStat.nDitches;
    this.nBails = paramNetUserStat.nBails;
    this.nDeaths = paramNetUserStat.nDeaths;
    this.nCaptures = paramNetUserStat.nCaptures;
    this.tTotal = paramNetUserStat.tTotal;
    this.tSingle = paramNetUserStat.tSingle;
    this.tMulti = paramNetUserStat.tMulti;
    this.tGunner = paramNetUserStat.tGunner;
    this.tNight = paramNetUserStat.tNight;
    this.tIns = paramNetUserStat.tIns;
    this.tCCountry = paramNetUserStat.tCCountry;
    this.rating = paramNetUserStat.rating;
    this.score = paramNetUserStat.score;
    for (int i = 0; i < 9; i++) this.enemyKill[i] = paramNetUserStat.enemyKill[i];
    for (int j = 0; j < 9; j++) this.friendKill[j] = paramNetUserStat.friendKill[j];
    this.bulletsFire = paramNetUserStat.bulletsFire;
    this.bulletsHit = paramNetUserStat.bulletsHit;
    this.bulletsHitAir = paramNetUserStat.bulletsHitAir;
    this.rocketsFire = paramNetUserStat.rocketsFire;
    this.rocketsHit = paramNetUserStat.rocketsHit;
    this.bombFire = paramNetUserStat.bombFire;
    this.bombHit = paramNetUserStat.bombHit;

    this.curPlayerState = paramNetUserStat.curPlayerState;
  }

  public void inc(NetUserStat paramNetUserStat) {
    this.nMissions += paramNetUserStat.nMissions;
    this.nSorties += paramNetUserStat.nSorties;
    this.nTakeoffs += paramNetUserStat.nTakeoffs;
    this.nLandings += paramNetUserStat.nLandings;
    this.nDitches += paramNetUserStat.nDitches;
    this.nBails += paramNetUserStat.nBails;
    this.nDeaths += paramNetUserStat.nDeaths;
    this.nCaptures += paramNetUserStat.nCaptures;
    this.tTotal += paramNetUserStat.tTotal;
    this.tSingle += paramNetUserStat.tSingle;
    this.tMulti += paramNetUserStat.tMulti;
    this.tGunner += paramNetUserStat.tGunner;
    this.tNight += paramNetUserStat.tNight;
    this.tIns += paramNetUserStat.tIns;
    this.tCCountry += paramNetUserStat.tCCountry;
    this.rating += paramNetUserStat.rating;
    this.score += paramNetUserStat.score;
    for (int i = 0; i < 9; i++) this.enemyKill[i] += paramNetUserStat.enemyKill[i];
    for (int j = 0; j < 9; j++) this.friendKill[j] += paramNetUserStat.friendKill[j];
    this.bulletsFire += paramNetUserStat.bulletsFire;
    this.bulletsHit += paramNetUserStat.bulletsHit;
    this.bulletsHitAir += paramNetUserStat.bulletsHitAir;
    this.rocketsFire += paramNetUserStat.rocketsFire;
    this.rocketsHit += paramNetUserStat.rocketsHit;
    this.bombFire += paramNetUserStat.bombFire;
    this.bombHit += paramNetUserStat.bombHit;

    this.curPlayerState = paramNetUserStat.curPlayerState;
  }

  public void fillFromScoreCounter(boolean paramBoolean) {
    ScoreCounter localScoreCounter = World.cur().scoreCounter;
    double d1 = 0.0D;
    ArrayList localArrayList = localScoreCounter.enemyItems;
    for (int i = 0; i < localArrayList.size(); i++) {
      ScoreItem localScoreItem = (ScoreItem)localArrayList.get(i);
      this.enemyKill[localScoreItem.type] += 1;
      d1 += localScoreItem.score;
    }
    double d2 = 0.0D;
    localArrayList = localScoreCounter.friendItems;
    Object localObject;
    for (int j = 0; j < localArrayList.size(); j++) {
      localObject = (ScoreItem)localArrayList.get(j);
      this.friendKill[((ScoreItem)localObject).type] += 1;
      d2 += ((ScoreItem)localObject).score;
    }

    if (localScoreCounter.bPlayerDead)
      d1 /= 10.0D;
    else if (localScoreCounter.bPlayerCaptured)
      d1 = d1 * 2.0D / 10.0D;
    else if (localScoreCounter.bLandedFarAirdrome)
      d1 = d1 * 7.0D / 10.0D;
    else if (localScoreCounter.bPlayerParatrooper) {
      d1 /= 2.0D;
    }

    this.score += d1 - d2;

    this.bulletsFire = localScoreCounter.bulletsFire;
    this.bulletsHit = localScoreCounter.bulletsHit;
    this.bulletsHitAir = localScoreCounter.bulletsHitAir;
    this.rocketsFire = localScoreCounter.rocketsFire;
    this.rocketsHit = localScoreCounter.rocketsHit;
    this.bombFire = localScoreCounter.bombFire;
    this.bombHit = localScoreCounter.bombHit;

    if (Mission.cur() != null) { Mission.cur(); if (!Mission.isPlaying())
        this.nMissions = 1; }
    this.nSorties = 1;
    this.nTakeoffs = localScoreCounter.nPlayerTakeoffs;
    this.nLandings = localScoreCounter.nPlayerLandings;
    this.nDitches = localScoreCounter.nPlayerDitches;

    if (World.isPlayerParatrooper()) {
      this.nBails = 1;
    }
    if (localScoreCounter.bPlayerDead) {
      this.nDeaths = 1;
    }
    if (localScoreCounter.bPlayerCaptured) {
      this.nCaptures = 1;
    }
    if (localScoreCounter.timeStart != -1L) {
      this.tTotal = ((float)(Time.currentReal() - localScoreCounter.timeStart) * 0.001F);
      switch (localScoreCounter.player_is) { case 0:
        this.tSingle = this.tTotal; break;
      case 1:
        this.tMulti = this.tTotal; break;
      case 2:
        this.tGunner = this.tTotal;
      }
      if (World.land() != null)
        this.tNight = World.land().nightTime(localScoreCounter.todStart, (int)this.tTotal);
      if (Config.isUSE_RENDER()) {
        localObject = Main3D.cur3D();
        if ((((Main3D)localObject).clouds != null) && (((Main3D)localObject).clouds.type() > 2))
          this.tIns = this.tTotal;
      }
      if (localScoreCounter.bCrossCountry) {
        this.tCCountry = this.tTotal;
      }
    }
    localScoreCounter.playerUpdateState();

    this.curPlayerState = 0;
    if (localScoreCounter.bPlayerDead) this.curPlayerState |= 1;
    if (localScoreCounter.bPlayerParatrooper) this.curPlayerState |= 2;
    if (localScoreCounter.bLanded) this.curPlayerState |= 4;
    if (localScoreCounter.bLandedFarAirdrome) this.curPlayerState |= 8;
    if (localScoreCounter.bPlayerCaptured) this.curPlayerState |= 16;
    if (localScoreCounter.bPlayerStateUnknown) this.curPlayerState |= 32;

    if (paramBoolean)
      localScoreCounter.resetGame();
  }
}
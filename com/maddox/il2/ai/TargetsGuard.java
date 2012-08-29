package com.maddox.il2.ai;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.MsgTimeOut;
import com.maddox.rts.MsgTimeOutListener;
import com.maddox.rts.NetEnv;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.util.ArrayList;

public class TargetsGuard
  implements MsgTimeOutListener
{
  public static final long TIME_STEP = 1000L;
  private boolean bActive = false;
  private MsgTimeOut ticker;
  private boolean bDead = true;
  private boolean bTaskComplete = false;
  private int checkType;
  private ArrayList targets = new ArrayList();

  public boolean isAlive()
  {
    return !this.bDead; } 
  public boolean isTaskComplete() { return this.bTaskComplete;
  }

  private void checkTask()
  {
    if (this.bDead) return;
    int i = 1;
    if (this.checkType == 2)
      i = 0;
    int j = 0;
    int k = 1;
    int m = this.targets.size();
    for (int n = 0; n < m; n++) {
      Target localTarget = (Target)this.targets.get(n);
      switch (this.checkType) {
      case 0:
        if ((localTarget.importance() != 0) || 
          (localTarget.isTaskComplete())) continue;
        if (!localTarget.isAlive()) {
          j = 1;
        }
        i = 0; break;
      case 1:
        if ((localTarget.importance() != 1) || 
          (localTarget.isTaskComplete())) continue;
        if (!localTarget.isAlive()) {
          j = 1;
        }
        i = 0; break;
      case 2:
        if (localTarget.importance() != 2) continue;
        if (localTarget.isAlive()) {
          k = 0;
        }
        else if (localTarget.isTaskComplete())
          i = 1;
        else {
          j = 1;
        }

      }

    }

    if (this.checkType == 2) {
      if (i != 0)
        j = 0;
      else if (k == 0) {
        return;
      }
    }
    if (j != 0) {
      this.bTaskComplete = false;
      this.bDead = true;
      doMissionComplete();
      return;
    }
    if (i != 0) {
      this.bTaskComplete = true;
      this.bDead = true;
      doMissionComplete();
    }
  }

  private void doScores(Target paramTarget, int paramInt) {
    World.cur().scoreCounter.targetOn(paramTarget, paramTarget.isTaskComplete());
    EventLog.type("Target " + paramInt + (paramTarget.isTaskComplete() ? " Complete" : " Failed"));
  }

  public void msgTimeOut(Object paramObject) {
    if (!this.bActive) return;
    if (!Mission.isPlaying()) return;
    this.ticker.setTime(Time.current() + 1000L);
    this.ticker.post();
    long l = Time.current();
    int i = 0;
    int j = this.targets.size();
    for (int k = 0; k < j; k++) {
      Target localTarget = (Target)this.targets.get(k);
      if (localTarget.isAlive()) {
        if (localTarget.checkPeriodic()) {
          i = 1;
          doScores(localTarget, k);
        }
        if ((localTarget.timeout > 0L) && (l > localTarget.timeout)) {
          localTarget.timeout = (-localTarget.timeout);
          if (localTarget.checkTimeoutOff()) {
            doScores(localTarget, k);
            i = 1;
          }
        }
      }
    }
    if (i != 0)
      checkTask();
  }

  protected void checkActorDied(Actor paramActor) {
    if (!this.bActive) return;
    if (!Mission.isPlaying()) return;
    int i = 0;
    int j = this.targets.size();
    for (int k = 0; k < j; k++) {
      Target localTarget = (Target)this.targets.get(k);
      if ((!localTarget.isAlive()) || 
        (!localTarget.checkActorDied(paramActor))) continue;
      i = 1;
      doScores(localTarget, k);
    }

    if ((paramActor == World.getPlayerAircraft()) && (!World.isPlayerRemoved()) && (!this.bDead) && (Mission.isSingle())) {
      this.bTaskComplete = false;
      this.bDead = true;
      doMissionComplete();
    } else if (i != 0) {
      checkTask();
    }
  }

  protected void checkTaskComplete(Actor paramActor) {
    if (!this.bActive) return;
    if (!Mission.isPlaying()) return;
    int i = 0;
    int j = this.targets.size();
    for (int k = 0; k < j; k++) {
      Target localTarget = (Target)this.targets.get(k);
      if ((!localTarget.isAlive()) || 
        (!localTarget.checkTaskComplete(paramActor))) continue;
      i = 1;
      doScores(localTarget, k);
    }

    if (i != 0)
      checkTask();
  }

  protected void addTarget(Target paramTarget) {
    this.targets.add(paramTarget);
  }

  public void doMissionComplete() {
    if (Mission.isNet()) {
      if (Main.cur().netServerParams.isCoop()) {
        if (Main.cur().netServerParams.isMaster())
          ((NetUser)NetEnv.host()).coopMissionComplete(this.bTaskComplete);
        if (NetUser.getArmyCoopWinner() == 1) {
          HUD.logCenter("RedWon");
          System.out.println("-------------------------------- RED WON ---------------------");
          EventLog.type(true, "Mission: RED WON");
        } else {
          HUD.logCenter("BlueWon");
          System.out.println("-------------------------------- BLUE WON ---------------------");
          EventLog.type(true, "Mission: BLUE WON");
        }
      }
    }
    else if (this.bTaskComplete) {
      HUD.logCenter("MissionComplete");
      System.out.println("-------------------------------- MISSION COMPLETE ---------------------");
      EventLog.type(true, "Mission: COMPLETE");
    } else {
      HUD.logCenter("MissionFailed");
      System.out.println("-------------------------------- MISSION FAILED ---------------------");
      EventLog.type(true, "Mission: FAILED");
    }
  }

  public void activate()
  {
    if (this.bActive) return;
    this.bActive = true;
    this.bDead = false;
    this.bTaskComplete = false;
    if (this.ticker.busy())
      this.ticker.remove();
    if (this.targets.size() == 0)
      return;
    this.checkType = 2;
    int i = this.targets.size();
    for (int j = 0; j < i; j++) {
      Target localTarget = (Target)this.targets.get(j);
      if (localTarget.importance() < this.checkType) {
        this.checkType = localTarget.importance();
      }
    }
    this.ticker.setTime(Time.current() + 1000L);
    this.ticker.post();
  }

  public void resetGame() {
    this.bActive = false;
    this.bDead = true;
    this.bTaskComplete = false;
    int i = this.targets.size();
    for (int j = 0; j < i; j++) {
      Target localTarget = (Target)this.targets.get(j);
      if (Actor.isValid(localTarget))
        localTarget.destroy();
    }
    this.targets.clear();
  }

  protected TargetsGuard() {
    this.ticker = new MsgTimeOut(null);
    this.ticker.setNotCleanAfterSend();
    this.ticker.setListener(this);
  }
}
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TargetsGuard.java

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

// Referenced classes of package com.maddox.il2.ai:
//            Target, World, ScoreCounter, EventLog

public class TargetsGuard
    implements com.maddox.rts.MsgTimeOutListener
{

    public boolean isAlive()
    {
        return !bDead;
    }

    public boolean isTaskComplete()
    {
        return bTaskComplete;
    }

    private void checkTask()
    {
        if(bDead)
            return;
        boolean flag = true;
        if(checkType == 2)
            flag = false;
        boolean flag1 = false;
        boolean flag2 = true;
        int i = targets.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.Target target = (com.maddox.il2.ai.Target)targets.get(j);
            switch(checkType)
            {
            default:
                break;

            case 0: // '\0'
                if(target.importance() != 0 || target.isTaskComplete())
                    break;
                if(!target.isAlive())
                    flag1 = true;
                flag = false;
                break;

            case 1: // '\001'
                if(target.importance() != 1 || target.isTaskComplete())
                    break;
                if(!target.isAlive())
                    flag1 = true;
                flag = false;
                break;

            case 2: // '\002'
                if(target.importance() != 2)
                    break;
                if(target.isAlive())
                {
                    flag2 = false;
                    break;
                }
                if(target.isTaskComplete())
                    flag = true;
                else
                    flag1 = true;
                break;
            }
        }

        if(checkType == 2)
            if(flag)
                flag1 = false;
            else
            if(!flag2)
                return;
        if(flag1)
        {
            bTaskComplete = false;
            bDead = true;
            doMissionComplete();
            return;
        }
        if(flag)
        {
            bTaskComplete = true;
            bDead = true;
            doMissionComplete();
        }
    }

    private void doScores(com.maddox.il2.ai.Target target, int i)
    {
        com.maddox.il2.ai.World.cur().scoreCounter.targetOn(target, target.isTaskComplete());
        com.maddox.il2.ai.EventLog.type("Target " + i + (target.isTaskComplete() ? " Complete" : " Failed"));
    }

    public void msgTimeOut(java.lang.Object obj)
    {
        if(!bActive)
            return;
        if(!com.maddox.il2.game.Mission.isPlaying())
            return;
        ticker.setTime(com.maddox.rts.Time.current() + 1000L);
        ticker.post();
        long l = com.maddox.rts.Time.current();
        boolean flag = false;
        int i = targets.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.Target target = (com.maddox.il2.ai.Target)targets.get(j);
            if(target.isAlive())
            {
                if(target.checkPeriodic())
                {
                    flag = true;
                    doScores(target, j);
                }
                if(target.timeout > 0L && l > target.timeout)
                {
                    target.timeout = -target.timeout;
                    if(target.checkTimeoutOff())
                    {
                        doScores(target, j);
                        flag = true;
                    }
                }
            }
        }

        if(flag)
            checkTask();
    }

    protected void checkActorDied(com.maddox.il2.engine.Actor actor)
    {
        if(!bActive)
            return;
        if(!com.maddox.il2.game.Mission.isPlaying())
            return;
        boolean flag = false;
        int i = targets.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.Target target = (com.maddox.il2.ai.Target)targets.get(j);
            if(target.isAlive() && target.checkActorDied(actor))
            {
                flag = true;
                doScores(target, j);
            }
        }

        if(actor == com.maddox.il2.ai.World.getPlayerAircraft() && !com.maddox.il2.ai.World.isPlayerRemoved() && !bDead && com.maddox.il2.game.Mission.isSingle())
        {
            bTaskComplete = false;
            bDead = true;
            doMissionComplete();
        } else
        if(flag)
            checkTask();
    }

    protected void checkTaskComplete(com.maddox.il2.engine.Actor actor)
    {
        if(!bActive)
            return;
        if(!com.maddox.il2.game.Mission.isPlaying())
            return;
        boolean flag = false;
        int i = targets.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.Target target = (com.maddox.il2.ai.Target)targets.get(j);
            if(target.isAlive() && target.checkTaskComplete(actor))
            {
                flag = true;
                doScores(target, j);
            }
        }

        if(flag)
            checkTask();
    }

    protected void addTarget(com.maddox.il2.ai.Target target)
    {
        targets.add(target);
    }

    public void doMissionComplete()
    {
        if(com.maddox.il2.game.Mission.isNet())
        {
            if(com.maddox.il2.game.Main.cur().netServerParams.isCoop())
            {
                if(com.maddox.il2.game.Main.cur().netServerParams.isMaster())
                    ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).coopMissionComplete(bTaskComplete);
                if(com.maddox.il2.net.NetUser.getArmyCoopWinner() == 1)
                {
                    com.maddox.il2.game.HUD.logCenter("RedWon");
                    java.lang.System.out.println("-------------------------------- RED WON ---------------------");
                    com.maddox.il2.ai.EventLog.type(true, "Mission: RED WON");
                } else
                {
                    com.maddox.il2.game.HUD.logCenter("BlueWon");
                    java.lang.System.out.println("-------------------------------- BLUE WON ---------------------");
                    com.maddox.il2.ai.EventLog.type(true, "Mission: BLUE WON");
                }
            }
        } else
        if(bTaskComplete)
        {
            com.maddox.il2.game.HUD.logCenter("MissionComplete");
            java.lang.System.out.println("-------------------------------- MISSION COMPLETE ---------------------");
            com.maddox.il2.ai.EventLog.type(true, "Mission: COMPLETE");
        } else
        {
            com.maddox.il2.game.HUD.logCenter("MissionFailed");
            java.lang.System.out.println("-------------------------------- MISSION FAILED ---------------------");
            com.maddox.il2.ai.EventLog.type(true, "Mission: FAILED");
        }
    }

    public void activate()
    {
        if(bActive)
            return;
        bActive = true;
        bDead = false;
        bTaskComplete = false;
        if(ticker.busy())
            ticker.remove();
        if(targets.size() == 0)
            return;
        checkType = 2;
        int i = targets.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.Target target = (com.maddox.il2.ai.Target)targets.get(j);
            if(target.importance() < checkType)
                checkType = target.importance();
        }

        ticker.setTime(com.maddox.rts.Time.current() + 1000L);
        ticker.post();
    }

    public void resetGame()
    {
        bActive = false;
        bDead = true;
        bTaskComplete = false;
        int i = targets.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.Target target = (com.maddox.il2.ai.Target)targets.get(j);
            if(com.maddox.il2.engine.Actor.isValid(target))
                target.destroy();
        }

        targets.clear();
    }

    protected TargetsGuard()
    {
        bActive = false;
        bDead = true;
        bTaskComplete = false;
        targets = new ArrayList();
        ticker = new MsgTimeOut(null);
        ticker.setNotCleanAfterSend();
        ticker.setListener(this);
    }

    public static final long TIME_STEP = 1000L;
    private boolean bActive;
    private com.maddox.rts.MsgTimeOut ticker;
    private boolean bDead;
    private boolean bTaskComplete;
    private int checkType;
    private java.util.ArrayList targets;
}

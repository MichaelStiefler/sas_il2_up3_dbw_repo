// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ScoreCounter.java

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

// Referenced classes of package com.maddox.il2.ai:
//            ScoreItem, World, Target

public class ScoreCounter
{
    static class Register
    {

        public int type;
        public double enemy;
        public double friend;

        public Register(int i, double d, double d1)
        {
            type = i;
            enemy = d;
            friend = d1;
        }
    }


    public ScoreCounter()
    {
        enemyItems = new ArrayList();
        friendItems = new ArrayList();
        targetOnItems = new ArrayList();
        targetOffItems = new ArrayList();
        bPlayerDead = false;
        bPlayerParatrooper = false;
        bLandedFarAirdrome = false;
        bPlayerCaptured = false;
        bLanded = false;
        bPlayerStateUnknown = false;
        bPlayerDroppedExternalStores = false;
        externalStoresValue = 0;
        timeStart = -1L;
        player_is = 0;
        bCrossCountry = false;
    }

    public void playerDead()
    {
        bPlayerDead = true;
    }

    public void playerParatrooper()
    {
        bPlayerParatrooper = true;
    }

    public void playerDroppedExternalStores(int i)
    {
        bPlayerDroppedExternalStores = true;
        externalStoresValue = i;
    }

    public void playerTakeoff()
    {
        nPlayerTakeoffs++;
        bLandedFarAirdrome = false;
        bLanded = false;
    }

    public void playerLanding(boolean flag)
    {
        bLanded = true;
        if(flag)
        {
            nPlayerDitches++;
            bLandedFarAirdrome = true;
        } else
        {
            nPlayerLandings++;
            bLandedFarAirdrome = false;
        }
    }

    public void playerCaptured()
    {
        bPlayerCaptured = true;
    }

    public void playerStartAir(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        timeStart = com.maddox.rts.Time.currentReal();
        todStart = com.maddox.il2.ai.World.getTimeofDay();
        if(aircraft instanceof com.maddox.il2.objects.air.Scheme1)
            player_is = 0;
        else
            player_is = 1;
    }

    public void playerStartGunner()
    {
        timeStart = com.maddox.rts.Time.currentReal();
        todStart = com.maddox.il2.ai.World.getTimeofDay();
        player_is = 2;
    }

    public void playerDoCrossCountry()
    {
        bCrossCountry = true;
    }

    public void playerUpdateState()
    {
        bPlayerStateUnknown = false;
        if(!bPlayerDead && !bPlayerParatrooper)
        {
            com.maddox.il2.fm.FlightModel flightmodel = com.maddox.il2.ai.World.getPlayerFM();
            if(flightmodel != null)
                bLanded = flightmodel.isStationedOnGround();
            else
                bPlayerStateUnknown = true;
        }
    }

    public void enemyDestroyed(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.ai.Register register1 = getRegistered(actor);
        if(register1 == null)
            return;
        enemyItems.add(new ScoreItem(register1.type, register1.enemy));
        switch(register1.type)
        {
        case 0: // '\0'
            com.maddox.il2.game.HUD.log("EnemyAircraftDestroyed");
            break;

        case 1: // '\001'
            com.maddox.il2.game.HUD.log("EnemyTankDestroyed");
            break;

        case 2: // '\002'
            com.maddox.il2.game.HUD.log("EnemyCarDestroyed");
            break;

        case 3: // '\003'
            com.maddox.il2.game.HUD.log("EnemyArtilleryDestroyed");
            break;

        case 4: // '\004'
            com.maddox.il2.game.HUD.log("EnemyAAADestroyed");
            break;

        case 5: // '\005'
            com.maddox.il2.game.HUD.log("EnemyBridgeDestroyed");
            break;

        case 6: // '\006'
            com.maddox.il2.game.HUD.log("EnemyWagonDestroyed");
            break;

        case 7: // '\007'
            com.maddox.il2.game.HUD.log("EnemyShipDestroyed");
            break;

        case 8: // '\b'
            com.maddox.il2.game.HUD.log("EnemyStaticAircraftDestroyed");
            break;

        case 9: // '\t'
            com.maddox.il2.game.HUD.log("EnemyRadioDestroyed");
            break;
        }
    }

    public void friendDestroyed(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.ai.Register register1 = getRegistered(actor);
        if(register1 == null)
            return;
        friendItems.add(new ScoreItem(register1.type, register1.friend));
        switch(register1.type)
        {
        case 0: // '\0'
            com.maddox.il2.game.HUD.log("FriendAircraftDestroyed");
            break;

        case 1: // '\001'
            com.maddox.il2.game.HUD.log("FriendTankDestroyed");
            break;

        case 2: // '\002'
            com.maddox.il2.game.HUD.log("FriendCarDestroyed");
            break;

        case 3: // '\003'
            com.maddox.il2.game.HUD.log("FriendArtilleryDestroyed");
            break;

        case 4: // '\004'
            com.maddox.il2.game.HUD.log("FriendAAADestroyed");
            break;

        case 5: // '\005'
            com.maddox.il2.game.HUD.log("FriendBridgeDestroyed");
            break;

        case 6: // '\006'
            com.maddox.il2.game.HUD.log("FriendWagonDestroyed");
            break;

        case 7: // '\007'
            com.maddox.il2.game.HUD.log("FriendShipDestroyed");
            break;

        case 8: // '\b'
            com.maddox.il2.game.HUD.log("FriendStaticAircraftDestroyed");
            break;

        case 9: // '\t'
            com.maddox.il2.game.HUD.log("FriendRadioDestroyed");
            break;
        }
    }

    public int getRegisteredType(com.maddox.il2.engine.Actor actor)
    {
        if(actor == null)
            return -1;
        com.maddox.il2.ai.Register register1 = (com.maddox.il2.ai.Register)com.maddox.rts.Property.value(actor.getClass(), "scoreDefine", null);
        if(register1 == null)
            return -1;
        else
            return register1.type;
    }

    private com.maddox.il2.ai.Register getRegistered(com.maddox.il2.engine.Actor actor)
    {
        if(actor == null)
            return null;
        com.maddox.il2.ai.Register register1 = (com.maddox.il2.ai.Register)com.maddox.rts.Property.value(actor.getClass(), "scoreDefine", null);
        if(register1 == null && com.maddox.il2.ai.World.cur().isDebugFM())
            java.lang.System.out.println("Class '" + actor.getClass().getName() + "' NOT registered in score database");
        return register1;
    }

    public void targetOn(com.maddox.il2.ai.Target target, boolean flag)
    {
        if(flag)
        {
            double d = 0.0D;
            byte byte0 = 0;
            switch(target.importance())
            {
            case 0: // '\0'
                byte0 = 100;
                d = 50D;
                com.maddox.il2.game.HUD.log("PrimaryTargetComplete");
                break;

            case 1: // '\001'
                byte0 = 101;
                d = 50D;
                com.maddox.il2.game.HUD.log("SecondaryTargetComplete");
                break;

            case 2: // '\002'
                byte0 = 102;
                d = 100D;
                com.maddox.il2.game.HUD.log("SecretTargetComplete");
                break;
            }
            if(com.maddox.il2.game.Mission.isNet())
                d = 0.0D;
            targetOnItems.add(new ScoreItem(byte0, d));
        } else
        {
            double d1 = 0.0D;
            byte byte1 = 0;
            switch(target.importance())
            {
            case 0: // '\0'
                byte1 = 100;
                d1 = 0.0D;
                com.maddox.il2.game.HUD.log("PrimaryTargetFailed");
                break;

            case 1: // '\001'
                byte1 = 101;
                d1 = 0.0D;
                com.maddox.il2.game.HUD.log("SecondaryTargetFailed");
                break;

            case 2: // '\002'
                byte1 = 102;
                d1 = 0.0D;
                break;
            }
            if(com.maddox.il2.game.Mission.isNet())
                d1 = 0.0D;
            targetOffItems.add(new ScoreItem(byte1, d1));
        }
    }

    public void resetGame()
    {
        enemyItems.clear();
        friendItems.clear();
        targetOnItems.clear();
        targetOffItems.clear();
        bulletsFire = 0;
        bulletsHit = 0;
        bulletsHitAir = 0;
        rocketsFire = 0;
        rocketsHit = 0;
        bombFire = 0;
        bombHit = 0;
        bPlayerDead = false;
        bPlayerParatrooper = false;
        bLandedFarAirdrome = false;
        bPlayerCaptured = false;
        nPlayerTakeoffs = 0;
        nPlayerLandings = 0;
        nPlayerDitches = 0;
        timeStart = -1L;
        player_is = 0;
        bCrossCountry = false;
        bLanded = false;
        bPlayerDroppedExternalStores = false;
        externalStoresValue = 0;
    }

    public static void register(java.lang.Class class1, int i, double d, double d1)
    {
        com.maddox.rts.Property.set(class1, "scoreDefine", new Register(i, d, d1));
    }

    public java.util.ArrayList enemyItems;
    public java.util.ArrayList friendItems;
    public java.util.ArrayList targetOnItems;
    public java.util.ArrayList targetOffItems;
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
    public boolean bPlayerDroppedExternalStores;
    public int externalStoresValue;
    public long timeStart;
    public int player_is;
    public float todStart;
    public boolean bCrossCountry;
}

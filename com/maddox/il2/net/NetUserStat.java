// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetUserStat.java

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

    public NetUserStat()
    {
        score = 0.0D;
        enemyKill = new int[9];
        friendKill = new int[9];
    }

    public void read(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        nMissions = netmsginput.readInt();
        nSorties = netmsginput.readInt();
        nTakeoffs = netmsginput.readInt();
        nLandings = netmsginput.readInt();
        nDitches = netmsginput.readInt();
        nBails = netmsginput.readInt();
        nDeaths = netmsginput.readInt();
        nCaptures = netmsginput.readInt();
        tTotal = netmsginput.readFloat();
        tSingle = netmsginput.readFloat();
        tMulti = netmsginput.readFloat();
        tGunner = netmsginput.readFloat();
        tNight = netmsginput.readFloat();
        tIns = netmsginput.readFloat();
        tCCountry = netmsginput.readFloat();
        rating = netmsginput.readFloat();
        score = netmsginput.readFloat();
        for(int i = 0; i < 9; i++)
            enemyKill[i] = netmsginput.readInt();

        for(int j = 0; j < 9; j++)
            friendKill[j] = netmsginput.readInt();

        bulletsFire = netmsginput.readInt();
        bulletsHit = netmsginput.readInt();
        bulletsHitAir = netmsginput.readInt();
        rocketsFire = netmsginput.readInt();
        rocketsHit = netmsginput.readInt();
        bombFire = netmsginput.readInt();
        bombHit = netmsginput.readInt();
        curPlayerState = netmsginput.readByte();
    }

    public void write(com.maddox.rts.NetMsgOutput netmsgoutput)
        throws java.io.IOException
    {
        netmsgoutput.writeInt(nMissions);
        netmsgoutput.writeInt(nSorties);
        netmsgoutput.writeInt(nTakeoffs);
        netmsgoutput.writeInt(nLandings);
        netmsgoutput.writeInt(nDitches);
        netmsgoutput.writeInt(nBails);
        netmsgoutput.writeInt(nDeaths);
        netmsgoutput.writeInt(nCaptures);
        netmsgoutput.writeFloat(tTotal);
        netmsgoutput.writeFloat(tSingle);
        netmsgoutput.writeFloat(tMulti);
        netmsgoutput.writeFloat(tGunner);
        netmsgoutput.writeFloat(tNight);
        netmsgoutput.writeFloat(tIns);
        netmsgoutput.writeFloat(tCCountry);
        netmsgoutput.writeFloat(rating);
        netmsgoutput.writeFloat((float)score);
        for(int i = 0; i < 9; i++)
            netmsgoutput.writeInt(enemyKill[i]);

        for(int j = 0; j < 9; j++)
            netmsgoutput.writeInt(friendKill[j]);

        netmsgoutput.writeInt(bulletsFire);
        netmsgoutput.writeInt(bulletsHit);
        netmsgoutput.writeInt(bulletsHitAir);
        netmsgoutput.writeInt(rocketsFire);
        netmsgoutput.writeInt(rocketsHit);
        netmsgoutput.writeInt(bombFire);
        netmsgoutput.writeInt(bombHit);
        netmsgoutput.writeByte(curPlayerState);
    }

    public boolean isEmpty()
    {
        if(nMissions != 0)
            return false;
        if(nSorties != 0)
            return false;
        if(nTakeoffs != 0)
            return false;
        if(nLandings != 0)
            return false;
        if(nDitches != 0)
            return false;
        if(nBails != 0)
            return false;
        if(nDeaths != 0)
            return false;
        if(nCaptures != 0)
            return false;
        if(tTotal != 0.0F)
            return false;
        if(tSingle != 0.0F)
            return false;
        if(tMulti != 0.0F)
            return false;
        if(tGunner != 0.0F)
            return false;
        if(tNight != 0.0F)
            return false;
        if(tIns != 0.0F)
            return false;
        if(tCCountry != 0.0F)
            return false;
        if(rating != 0.0F)
            return false;
        if(score != 0.0D)
            return false;
        for(int i = 0; i < 9; i++)
            if(enemyKill[i] != 0)
                return false;

        for(int j = 0; j < 9; j++)
            if(friendKill[j] != 0)
                return false;

        if(bulletsFire != 0)
            return false;
        if(bulletsHit != 0)
            return false;
        if(bulletsHitAir != 0)
            return false;
        if(rocketsFire != 0)
            return false;
        if(rocketsHit != 0)
            return false;
        if(bombFire != 0)
            return false;
        return bombHit == 0;
    }

    public boolean isEqualsCurrent(com.maddox.il2.net.NetUserStat netuserstat)
    {
        if(score != netuserstat.score)
            return false;
        if(curPlayerState != netuserstat.curPlayerState)
            return false;
        for(int i = 0; i < 9; i++)
            if(enemyKill[i] != netuserstat.enemyKill[i])
                return false;

        for(int j = 0; j < 9; j++)
            if(friendKill[j] != netuserstat.friendKill[j])
                return false;

        if(bulletsFire != netuserstat.bulletsFire)
            return false;
        if(bulletsHit != netuserstat.bulletsHit)
            return false;
        if(bulletsHitAir != netuserstat.bulletsHitAir)
            return false;
        if(rocketsFire != netuserstat.rocketsFire)
            return false;
        if(rocketsHit != netuserstat.rocketsHit)
            return false;
        if(bombFire != netuserstat.bombFire)
            return false;
        return bombHit == netuserstat.bombHit;
    }

    public void clear()
    {
        nMissions = 0;
        nSorties = 0;
        nTakeoffs = 0;
        nLandings = 0;
        nDitches = 0;
        nBails = 0;
        nDeaths = 0;
        nCaptures = 0;
        tTotal = 0.0F;
        tSingle = 0.0F;
        tMulti = 0.0F;
        tGunner = 0.0F;
        tNight = 0.0F;
        tIns = 0.0F;
        tCCountry = 0.0F;
        rating = 0.0F;
        score = 0.0D;
        for(int i = 0; i < 9; i++)
            enemyKill[i] = 0;

        for(int j = 0; j < 9; j++)
            friendKill[j] = 0;

        bulletsFire = 0;
        bulletsHit = 0;
        bulletsHitAir = 0;
        rocketsFire = 0;
        rocketsHit = 0;
        bombFire = 0;
        bombHit = 0;
        curPlayerState = 4;
    }

    public void set(com.maddox.il2.net.NetUserStat netuserstat)
    {
        nMissions = netuserstat.nMissions;
        nSorties = netuserstat.nSorties;
        nTakeoffs = netuserstat.nTakeoffs;
        nLandings = netuserstat.nLandings;
        nDitches = netuserstat.nDitches;
        nBails = netuserstat.nBails;
        nDeaths = netuserstat.nDeaths;
        nCaptures = netuserstat.nCaptures;
        tTotal = netuserstat.tTotal;
        tSingle = netuserstat.tSingle;
        tMulti = netuserstat.tMulti;
        tGunner = netuserstat.tGunner;
        tNight = netuserstat.tNight;
        tIns = netuserstat.tIns;
        tCCountry = netuserstat.tCCountry;
        rating = netuserstat.rating;
        score = netuserstat.score;
        for(int i = 0; i < 9; i++)
            enemyKill[i] = netuserstat.enemyKill[i];

        for(int j = 0; j < 9; j++)
            friendKill[j] = netuserstat.friendKill[j];

        bulletsFire = netuserstat.bulletsFire;
        bulletsHit = netuserstat.bulletsHit;
        bulletsHitAir = netuserstat.bulletsHitAir;
        rocketsFire = netuserstat.rocketsFire;
        rocketsHit = netuserstat.rocketsHit;
        bombFire = netuserstat.bombFire;
        bombHit = netuserstat.bombHit;
        curPlayerState = netuserstat.curPlayerState;
    }

    public void inc(com.maddox.il2.net.NetUserStat netuserstat)
    {
        nMissions += netuserstat.nMissions;
        nSorties += netuserstat.nSorties;
        nTakeoffs += netuserstat.nTakeoffs;
        nLandings += netuserstat.nLandings;
        nDitches += netuserstat.nDitches;
        nBails += netuserstat.nBails;
        nDeaths += netuserstat.nDeaths;
        nCaptures += netuserstat.nCaptures;
        tTotal += netuserstat.tTotal;
        tSingle += netuserstat.tSingle;
        tMulti += netuserstat.tMulti;
        tGunner += netuserstat.tGunner;
        tNight += netuserstat.tNight;
        tIns += netuserstat.tIns;
        tCCountry += netuserstat.tCCountry;
        rating += netuserstat.rating;
        score += netuserstat.score;
        for(int i = 0; i < 9; i++)
            enemyKill[i] += netuserstat.enemyKill[i];

        for(int j = 0; j < 9; j++)
            friendKill[j] += netuserstat.friendKill[j];

        bulletsFire += netuserstat.bulletsFire;
        bulletsHit += netuserstat.bulletsHit;
        bulletsHitAir += netuserstat.bulletsHitAir;
        rocketsFire += netuserstat.rocketsFire;
        rocketsHit += netuserstat.rocketsHit;
        bombFire += netuserstat.bombFire;
        bombHit += netuserstat.bombHit;
        curPlayerState = netuserstat.curPlayerState;
    }

    public void fillFromScoreCounter(boolean flag)
    {
        com.maddox.il2.ai.ScoreCounter scorecounter = com.maddox.il2.ai.World.cur().scoreCounter;
        double d = 0.0D;
        java.util.ArrayList arraylist = scorecounter.enemyItems;
        for(int i = 0; i < arraylist.size(); i++)
        {
            com.maddox.il2.ai.ScoreItem scoreitem = (com.maddox.il2.ai.ScoreItem)arraylist.get(i);
            enemyKill[scoreitem.type]++;
            d += scoreitem.score;
        }

        double d1 = 0.0D;
        arraylist = scorecounter.friendItems;
        for(int j = 0; j < arraylist.size(); j++)
        {
            com.maddox.il2.ai.ScoreItem scoreitem1 = (com.maddox.il2.ai.ScoreItem)arraylist.get(j);
            friendKill[scoreitem1.type]++;
            d1 += scoreitem1.score;
        }

        if(scorecounter.bPlayerDead)
            d /= 10D;
        else
        if(scorecounter.bPlayerCaptured)
            d = (d * 2D) / 10D;
        else
        if(scorecounter.bLandedFarAirdrome)
            d = (d * 7D) / 10D;
        else
        if(scorecounter.bPlayerParatrooper)
            d /= 2D;
        score += d - d1;
        bulletsFire = scorecounter.bulletsFire;
        bulletsHit = scorecounter.bulletsHit;
        bulletsHitAir = scorecounter.bulletsHitAir;
        rocketsFire = scorecounter.rocketsFire;
        rocketsHit = scorecounter.rocketsHit;
        bombFire = scorecounter.bombFire;
        bombHit = scorecounter.bombHit;
        if(com.maddox.il2.game.Mission.cur() != null)
        {
            com.maddox.il2.game.Mission.cur();
            if(!com.maddox.il2.game.Mission.isPlaying())
                nMissions = 1;
        }
        nSorties = 1;
        nTakeoffs = scorecounter.nPlayerTakeoffs;
        nLandings = scorecounter.nPlayerLandings;
        nDitches = scorecounter.nPlayerDitches;
        if(com.maddox.il2.ai.World.isPlayerParatrooper())
            nBails = 1;
        if(scorecounter.bPlayerDead)
            nDeaths = 1;
        if(scorecounter.bPlayerCaptured)
            nCaptures = 1;
        if(scorecounter.timeStart != -1L)
        {
            tTotal = (float)(com.maddox.rts.Time.currentReal() - scorecounter.timeStart) * 0.001F;
            switch(scorecounter.player_is)
            {
            case 0: // '\0'
                tSingle = tTotal;
                break;

            case 1: // '\001'
                tMulti = tTotal;
                break;

            case 2: // '\002'
                tGunner = tTotal;
                break;
            }
            if(com.maddox.il2.ai.World.land() != null)
                tNight = com.maddox.il2.ai.World.land().nightTime(scorecounter.todStart, (int)tTotal);
            if(com.maddox.il2.engine.Config.isUSE_RENDER())
            {
                com.maddox.il2.game.Main3D main3d = com.maddox.il2.game.Main3D.cur3D();
                if(main3d.clouds != null && main3d.clouds.type() > 2)
                    tIns = tTotal;
            }
            if(scorecounter.bCrossCountry)
                tCCountry = tTotal;
        }
        scorecounter.playerUpdateState();
        curPlayerState = 0;
        if(scorecounter.bPlayerDead)
            curPlayerState |= 1;
        if(scorecounter.bPlayerParatrooper)
            curPlayerState |= 2;
        if(scorecounter.bLanded)
            curPlayerState |= 4;
        if(scorecounter.bLandedFarAirdrome)
            curPlayerState |= 8;
        if(scorecounter.bPlayerCaptured)
            curPlayerState |= 0x10;
        if(scorecounter.bPlayerStateUnknown)
            curPlayerState |= 0x20;
        if(flag)
            scorecounter.resetGame();
    }

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
    public double score;
    public int enemyKill[];
    public int friendKill[];
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
}

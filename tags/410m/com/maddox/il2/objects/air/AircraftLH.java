// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AircraftLH.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.objects.air:
//            Aircraft

public abstract class AircraftLH extends com.maddox.il2.objects.air.Aircraft
{

    public AircraftLH()
    {
        bWantBeaconKeys = false;
    }

    public void beaconPlus()
    {
        if(!bWantBeaconKeys || com.maddox.il2.game.Main.cur().mission.getBeacons(getArmy()) != null && com.maddox.il2.game.Main.cur().mission.getBeacons(getArmy()).size() == 0)
        {
            return;
        } else
        {
            FM.AS.beaconPlus();
            return;
        }
    }

    public void beaconMinus()
    {
        if(!bWantBeaconKeys || com.maddox.il2.game.Main.cur().mission.getBeacons(getArmy()) != null && com.maddox.il2.game.Main.cur().mission.getBeacons(getArmy()).size() == 0)
        {
            return;
        } else
        {
            FM.AS.beaconMinus();
            return;
        }
    }

    public void beaconSet(int i)
    {
        if(!bWantBeaconKeys || com.maddox.il2.game.Main.cur().mission.getBeacons(getArmy()) != null && com.maddox.il2.game.Main.cur().mission.getBeacons(getArmy()).size() == 0)
        {
            return;
        } else
        {
            FM.AS.setBeacon(i);
            return;
        }
    }

    public void auxPlus(int i)
    {
        switch(i)
        {
        case 1: // '\001'
            headingBug = headingBug + 1.0F;
            if(headingBug >= 360F)
                headingBug = 0.0F;
            if(printCompassHeading && com.maddox.il2.ai.World.cur().diffCur.RealisticNavigationInstruments && bWantBeaconKeys)
                com.maddox.il2.game.HUD.log(hudLogCompassId, "CompassHeading", new java.lang.Object[] {
                    "" + (int)headingBug
                });
            break;
        }
    }

    public void auxMinus(int i)
    {
        switch(i)
        {
        case 1: // '\001'
            headingBug = headingBug - 1.0F;
            if(headingBug < 0.0F)
                headingBug = 359F;
            if(printCompassHeading && com.maddox.il2.ai.World.cur().diffCur.RealisticNavigationInstruments && bWantBeaconKeys)
                com.maddox.il2.game.HUD.log(hudLogCompassId, "CompassHeading", new java.lang.Object[] {
                    "" + (int)headingBug
                });
            break;
        }
    }

    public void auxPressed(int i)
    {
        if(i == 1)
            FM.CT.dropExternalStores(true);
    }

    protected void hitFlesh(int i, com.maddox.il2.ai.Shot shot, int j)
    {
        int k = 0;
        int l = (int)(shot.power * 0.0035F * com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 1.5F));
        switch(j)
        {
        default:
            break;

        case 0: // '\0'
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                return;
            if(shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
            l *= 30;
            break;

        case 1: // '\001'
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.08F)
            {
                l *= 2;
                boolean flag = true;
                k = com.maddox.il2.ai.World.Rnd().nextInt(1, 15) * 8000;
                break;
            }
            boolean flag1 = com.maddox.il2.ai.World.Rnd().nextInt(0, 100 - l) <= 20;
            if(flag1)
                k = l / com.maddox.il2.ai.World.Rnd().nextInt(1, 10);
            break;

        case 2: // '\002'
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.015F)
            {
                boolean flag2 = true;
                k = com.maddox.il2.ai.World.Rnd().nextInt(1, 15) * 1000;
            } else
            {
                boolean flag3 = com.maddox.il2.ai.World.Rnd().nextInt(0, 100 - l) <= 10;
                if(flag3)
                    k = l / com.maddox.il2.ai.World.Rnd().nextInt(1, 15);
            }
            l = (int)((float)l / 1.5F);
            break;
        }
        debuggunnery("*** Pilot " + i + " hit for " + l + "% (" + (int)shot.power + " J)");
        FM.AS.hitPilot(shot.initiator, i, l);
        if(com.maddox.il2.ai.World.cur().diffCur.RealisticPilotVulnerability)
        {
            if(k > 0)
                FM.AS.setBleedingPilot(shot.initiator, i, k);
            if(i == 0 && j > 0)
                FM.AS.woundedPilot(shot.initiator, j, l);
        }
        if(FM.AS.astatePilotStates[i] > 95 && j == 0)
            debuggunnery("*** Headshot!.");
    }

    public static int hudLogCompassId = com.maddox.il2.game.HUD.makeIdLog();
    public static boolean printCompassHeading = false;
    public boolean bWantBeaconKeys;

}

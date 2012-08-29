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

public abstract class AircraftLH extends Aircraft
{
  public static int hudLogCompassId = HUD.makeIdLog();
  public static boolean printCompassHeading = false;

  public boolean bWantBeaconKeys = false;

  public void beaconPlus()
  {
    if ((!this.bWantBeaconKeys) || ((Main.cur().mission.getBeacons(getArmy()) != null) && (Main.cur().mission.getBeacons(getArmy()).size() == 0))) {
      return;
    }
    this.FM.AS.beaconPlus();
  }

  public void beaconMinus()
  {
    if ((!this.bWantBeaconKeys) || ((Main.cur().mission.getBeacons(getArmy()) != null) && (Main.cur().mission.getBeacons(getArmy()).size() == 0))) {
      return;
    }
    this.FM.AS.beaconMinus();
  }

  public void beaconSet(int paramInt)
  {
    if ((!this.bWantBeaconKeys) || ((Main.cur().mission.getBeacons(getArmy()) != null) && (Main.cur().mission.getBeacons(getArmy()).size() == 0))) {
      return;
    }
    this.FM.AS.setBeacon(paramInt);
  }

  public void auxPlus(int paramInt)
  {
    switch (paramInt)
    {
    case 1:
      this.headingBug += 1.0F;
      if (this.headingBug >= 360.0F)
        this.headingBug = 0.0F;
      if ((!printCompassHeading) || (!World.cur().diffCur.RealisticNavigationInstruments) || (!this.bWantBeaconKeys)) break;
      HUD.log(hudLogCompassId, "CompassHeading", new Object[] { "" + (int)this.headingBug });
    }
  }

  public void auxMinus(int paramInt)
  {
    switch (paramInt)
    {
    case 1:
      this.headingBug -= 1.0F;
      if (this.headingBug < 0.0F)
        this.headingBug = 359.0F;
      if ((!printCompassHeading) || (!World.cur().diffCur.RealisticNavigationInstruments) || (!this.bWantBeaconKeys)) break;
      HUD.log(hudLogCompassId, "CompassHeading", new Object[] { "" + (int)this.headingBug });
    }
  }

  public void auxPressed(int paramInt)
  {
    if (paramInt == 1)
    {
      this.FM.CT.dropExternalStores(true);
    }
  }

  protected void hitFlesh(int paramInt1, Shot paramShot, int paramInt2)
  {
    int i = 0;

    int k = (int)(paramShot.power * 0.0035F * World.Rnd().nextFloat(0.5F, 1.5F));
    int j;
    switch (paramInt2) {
    case 0:
      if (World.Rnd().nextFloat() < 0.05F)
        return;
      if ((paramShot.initiator == World.getPlayerAircraft()) && (World.cur().isArcade()))
        HUD.logCenter("H E A D S H O T");
      k *= 30;
      break;
    case 1:
      if (World.Rnd().nextFloat() < 0.08F) {
        k *= 2;
        j = 1;
        i = World.Rnd().nextInt(1, 15) * 8000;
      } else {
        j = World.Rnd().nextInt(0, 100 - k) <= 20 ? 1 : 0;
        if (j == 0) break;
        i = k / World.Rnd().nextInt(1, 10); } break;
    case 2:
      if (World.Rnd().nextFloat() < 0.015F) {
        j = 1;
        i = World.Rnd().nextInt(1, 15) * 1000;
      } else {
        j = World.Rnd().nextInt(0, 100 - k) <= 10 ? 1 : 0;
        if (j != 0)
          i = k / World.Rnd().nextInt(1, 15);
      }
      k = (int)(k / 1.5F);
    }

    debuggunnery("*** Pilot " + paramInt1 + " hit for " + k + "% (" + (int)paramShot.power + " J)");
    this.FM.AS.hitPilot(paramShot.initiator, paramInt1, k);

    if (World.cur().diffCur.RealisticPilotVulnerability) {
      if (i > 0) {
        this.FM.AS.setBleedingPilot(paramShot.initiator, paramInt1, i);
      }
      if ((paramInt1 == 0) && (paramInt2 > 0)) {
        this.FM.AS.woundedPilot(paramShot.initiator, paramInt2, k);
      }
    }
    if ((this.FM.AS.astatePilotStates[paramInt1] > 95) && (paramInt2 == 0))
      debuggunnery("*** Headshot!.");
  }
}
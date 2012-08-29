package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Property;

public abstract class P_39 extends Scheme1
  implements TypeFighter
{
  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, 87.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -87.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", 15.5F * paramFloat, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 15.5F * paramFloat, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC6_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC9_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 100.0F * paramFloat, 0.0F);
    float f = Math.max(-paramFloat * 1500.0F, -90.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, f, 0.0F);

    paramHierMesh.chunkSetAngles("LanLight_D0", 0.0F, 0.0F, 92.5F * paramFloat);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat);
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -31.0F * paramFloat, 0.0F);
    if (this.FM.CT.getGear() == 1.0F)
      hierMesh().chunkSetAngles("GearC2_D0", -40.0F * paramFloat, 0.0F, 0.0F);
  }

  protected void moveFlap(float paramFloat)
  {
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -45.0F * paramFloat, 0.0F);
  }

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);

    if ((paramShot.chunkName.startsWith("WingLIn")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.2F)) {
      this.FM.AS.hitTank(paramShot.initiator, 0, 1);
    }
    if ((paramShot.chunkName.startsWith("WingRIn")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.2F)) {
      this.FM.AS.hitTank(paramShot.initiator, 1, 1);
    }
    if (paramShot.chunkName.startsWith("CF")) {
      if (World.Rnd().nextFloat(0.0F, 1.0F) < 0.3F) {
        this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
      }

      if (World.Rnd().nextFloat() < 0.03F) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
      }
      if (World.Rnd().nextFloat() < 0.03F) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
      }
      if (World.Rnd().nextFloat() < 0.03F) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
      }
      if (World.Rnd().nextFloat() < 0.03F) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
      }
      if (World.Rnd().nextFloat() < 0.03F) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
      }
      if (World.Rnd().nextFloat() < 0.03F) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
      }
      if (World.Rnd().nextFloat() < 0.03F) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
      }

    }

    if ((paramShot.chunkName.startsWith("Engine1")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.06F) && (Pd.z > 0.4799999892711639D)) {
      this.FM.AS.setJamBullets(0, 0);
      this.FM.AS.setJamBullets(0, 1);
    }

    if ((paramShot.chunkName.startsWith("Oil")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.5F)) {
      this.FM.AS.hitOil(paramShot.initiator, 0);
    }
    if (paramShot.chunkName.startsWith("Pilot")) {
      if (paramShot.power * Math.abs(v1.x) > 12070.0D) {
        this.FM.AS.hitPilot(paramShot.initiator, 0, (int)(paramShot.mass * 1000.0F * 0.5F));
      }
      if (Pd.z > 1.011749982833862D) {
        killPilot(paramShot.initiator, 0);
        if ((paramShot.initiator == World.getPlayerAircraft()) && (World.cur().isArcade())) HUD.logCenter("H E A D S H O T");
      }
      return;
    }

    super.msgShot(paramShot);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) { case 33:
      return super.cutFM(34, paramInt2, paramActor);
    case 36:
      return super.cutFM(37, paramInt2, paramActor);
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  static
  {
    Class localClass = P_39.class;
    Property.set(localClass, "originCountry", PaintScheme.countryUSA);
  }
}
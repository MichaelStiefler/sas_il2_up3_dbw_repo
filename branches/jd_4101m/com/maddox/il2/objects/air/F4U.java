package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public abstract class F4U extends Scheme1
  implements TypeFighter, TypeDiveBomber
{
  private float arrestorPos = 0.0F;
  private float arrestorVel = 0.0F;
  private float prevGear = 0.0F;
  private float prevGear2 = 0.0F;
  private static boolean bGearExtending = false;
  private static boolean bGearExtending2 = false;
  private float prevWing = 1.0F;

  private float cGearPos = 0.0F;
  private float cGear = 0.0F;
  private boolean bNeedSetup = true;

  private float flapps = 0.0F;

  public boolean typeDiveBomberToggleAutomation()
  {
    return false;
  }

  public void typeDiveBomberAdjAltitudeReset()
  {
  }

  public void typeDiveBomberAdjAltitudePlus()
  {
  }

  public void typeDiveBomberAdjAltitudeMinus()
  {
  }

  public void typeDiveBomberAdjVelocityReset() {
  }

  public void typeDiveBomberAdjVelocityPlus() {
  }

  public void typeDiveBomberAdjVelocityMinus() {
  }

  public void typeDiveBomberAdjDiveAngleReset() {
  }

  public void typeDiveBomberAdjDiveAnglePlus() {
  }

  public void typeDiveBomberAdjDiveAngleMinus() {
  }

  public void typeDiveBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
  }

  public void typeDiveBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
  }

  protected void moveAileron(float paramFloat) {
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, 30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, 30.0F * paramFloat, 0.0F);
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat, FlightModel paramFlightModel)
  {
    float f3 = 10.0F * paramFloat;
    if (paramFlightModel != null)
      paramFloat = 10.0F * Math.max(paramFloat, paramFlightModel.CT.getAirBrake());
    else {
      paramFloat = f3;
    }

    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
    float f1;
    if (bGearExtending)
    {
      paramHierMesh.chunkSetAngles("GearL9_D0", 0.0F, cvt(paramFloat, 0.0F, 3.0F, 0.0F, 95.0F), 0.0F);
      paramHierMesh.chunkSetAngles("GearL10_D0", 0.0F, cvt(paramFloat, 0.0F, 3.0F, 0.0F, 60.0F), 0.0F);
      paramHierMesh.chunkSetAngles("GearR9_D0", 0.0F, cvt(paramFloat, 0.0F, 3.0F, 0.0F, 95.0F), 0.0F);
      paramHierMesh.chunkSetAngles("GearR10_D0", 0.0F, cvt(paramFloat, 0.0F, 3.0F, 0.0F, 60.0F), 0.0F);

      if (paramFlightModel == null) {
        f1 = cvt(f3, 0.0F, 1.0F, 0.0F, 0.7071068F);
        f1 = 2.0F * f1 * f1;
        paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 41.0F * f1, 0.0F);
        paramHierMesh.chunkSetAngles("Hook1_D0", 0.0F, cvt(f1, 0.0F, 1.0F, 0.0F, -64.5F), 0.0F);
        f1 = cvt(f3, 0.0F, 0.25F, 0.0F, 1.0F);
        paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, 140.0F * f1, 0.0F);
        paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, 140.0F * f1, 0.0F);
      }
      float f2;
      if (paramFloat < 4.0F) {
        f1 = f2 = cvt(paramFloat, 3.0F, 4.0F, 0.0F, 0.4F);
      } else {
        f1 = cvt(paramFloat, 4.0F, 8.0F, 0.75F, 2.0F);
        f1 = (float)Math.sqrt(f1);
        f1 = cvt(f1, (float)Math.sqrt(0.75D), (float)Math.sqrt(2.0D), 0.4F, 1.0F);
        f2 = cvt(paramFloat, 4.0F, 8.5F, 0.75F, 2.0F);
        f2 = (float)Math.sqrt(f2);
        f2 = cvt(f2, (float)Math.sqrt(0.75D), (float)Math.sqrt(2.0D), 0.4F, 1.0F);
      }
      paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 81.0F * f1, 0.0F);
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 84.0F * f1, 0.0F);
      paramHierMesh.chunkSetAngles("GearL8_D0", 0.0F, 83.0F * f1, 0.0F);
      paramHierMesh.chunkSetAngles("GearL33_D0", 0.0F, -104.0F * f1, 0.0F);
      paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, 40.0F * f1, 0.0F);
      paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -168.0F * f1, 0.0F);
      paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -90.0F * f1, 0.0F);
      paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 81.0F * f2, 0.0F);
      paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 84.0F * f2, 0.0F);
      paramHierMesh.chunkSetAngles("GearR8_D0", 0.0F, 83.0F * f2, 0.0F);
      paramHierMesh.chunkSetAngles("GearR33_D0", 0.0F, -104.0F * f2, 0.0F);
      paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, 40.0F * f2, 0.0F);
      paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -168.0F * f2, 0.0F);
      paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, -90.0F * f2, 0.0F);
    }
    else
    {
      if (paramFlightModel == null) {
        f1 = cvt(f3, 8.5F, 10.0F, 0.0F, 1.0F);
        paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 41.0F * f1, 0.0F);
        f1 = cvt(f3, 8.5F, 8.75F, 0.0F, 1.0F);
        paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, 140.0F * f1, 0.0F);
        paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, 140.0F * f1, 0.0F);
        paramHierMesh.chunkSetAngles("Hook1_D0", 0.0F, cvt(f3, 8.5F, 10.0F, 0.0F, -64.5F), 0.0F);
      }

      if (paramFloat > 7.5F)
        f1 = cvt(paramFloat, 7.5F, 8.5F, 0.9F, 1.0F);
      else {
        f1 = cvt(paramFloat, 3.0F, 7.5F, 0.0F, 0.9F);
      }
      paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 81.0F * f1, 0.0F);
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 84.0F * f1, 0.0F);
      paramHierMesh.chunkSetAngles("GearL8_D0", 0.0F, 83.0F * f1, 0.0F);
      paramHierMesh.chunkSetAngles("GearL33_D0", 0.0F, -104.0F * f1, 0.0F);
      paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, 40.0F * f1, 0.0F);
      paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -168.0F * f1, 0.0F);
      paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -90.0F * f1, 0.0F);
      paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 81.0F * f1, 0.0F);
      paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 84.0F * f1, 0.0F);
      paramHierMesh.chunkSetAngles("GearR8_D0", 0.0F, 83.0F * f1, 0.0F);
      paramHierMesh.chunkSetAngles("GearR33_D0", 0.0F, -104.0F * f1, 0.0F);
      paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, 40.0F * f1, 0.0F);
      paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -168.0F * f1, 0.0F);
      paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, -90.0F * f1, 0.0F);

      paramHierMesh.chunkSetAngles("GearL9_D0", 0.0F, cvt(paramFloat, 1.5F, 3.7F, 0.0F, 95.0F), 0.0F);
      paramHierMesh.chunkSetAngles("GearL10_D0", 0.0F, cvt(paramFloat, 1.5F, 3.7F, 0.0F, 60.0F), 0.0F);
      paramHierMesh.chunkSetAngles("GearR9_D0", 0.0F, cvt(paramFloat, 0.01F, 3.7F, 0.0F, 95.0F), 0.0F);
      paramHierMesh.chunkSetAngles("GearR10_D0", 0.0F, cvt(paramFloat, 0.01F, 3.7F, 0.0F, 60.0F), 0.0F);
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat) {
    moveGear(paramHierMesh, paramFloat, null);
  }

  protected void moveGear(float paramFloat) {
    if (this.prevGear > paramFloat)
      bGearExtending = false;
    else {
      bGearExtending = true;
    }
    this.prevGear = paramFloat;
    moveGear(hierMesh(), paramFloat, this.FM);

    paramFloat *= 10.0F;
    if (bGearExtending) {
      float f = cvt(paramFloat, 0.0F, 1.0F, 0.0F, 0.7071068F);
      this.cGearPos = (2.0F * f * f);
    }
    else
    {
      this.cGearPos = cvt(paramFloat, 8.5F, 10.0F, 0.0F, 1.0F);
    }
  }

  public void moveSteering(float paramFloat)
  {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }

  public void moveArrestorHook(float paramFloat) {
    hierMesh().chunkSetAngles("Hook1_D0", 0.0F, paramFloat, 0.0F);
  }
  public void moveWheelSink() {
    resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.231F, 0.0F, 0.231F);
    hierMesh().chunkSetLocate("GearL7_D0", xyz, ypr);
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.231F, 0.0F, -0.231F);
    hierMesh().chunkSetLocate("GearR7_D0", xyz, ypr);
  }

  protected void moveAirBrake(float paramFloat)
  {
    moveGear(this.FM.CT.getGear());

    moveArrestorHook(this.arrestorPos);
  }

  protected void moveWingFold(HierMesh paramHierMesh, float paramFloat)
  {
    paramFloat *= 18.0F;
    if (bGearExtending) {
      if (paramFloat < 1.5F) {
        paramHierMesh.chunkSetAngles("WingLMid_D0", 0.0F, cvt(paramFloat, 0.0F, 1.5F, 0.0F, 2.6F), 0.0F);
        paramHierMesh.chunkSetAngles("WingRMid_D0", 0.0F, cvt(paramFloat, 0.0F, 1.5F, 0.0F, 2.6F), 0.0F);
      } else if (paramFloat < 2.5F) {
        paramHierMesh.chunkSetAngles("WingLMid_D0", 0.0F, cvt(paramFloat, 1.5F, 2.5F, 2.6F, 5.1F), 0.0F);
        paramHierMesh.chunkSetAngles("WingRMid_D0", 0.0F, cvt(paramFloat, 1.5F, 2.5F, 2.6F, 5.1F), 0.0F);
      } else {
        paramHierMesh.chunkSetAngles("WingLMid_D0", 0.0F, cvt(paramFloat, 2.5F, 17.9F, 5.1F, 105.0F), 0.0F);
        paramHierMesh.chunkSetAngles("WingRMid_D0", 0.0F, cvt(paramFloat, 2.5F, 16.0F, 5.1F, 105.0F), 0.0F);
      }
    }
    else if (paramFloat < 9.0F) {
      if (paramFloat < 6.8F)
        paramHierMesh.chunkSetAngles("WingRMid_D0", 0.0F, cvt(paramFloat, 0.01F, 6.8F, 0.0F, 45.0F), 0.0F);
      else {
        paramHierMesh.chunkSetAngles("WingRMid_D0", 0.0F, cvt(paramFloat, 6.8F, 9.0F, 45.0F, 50.0F), 0.0F);
      }
      if (paramFloat < 7.5F)
        paramHierMesh.chunkSetAngles("WingLMid_D0", 0.0F, cvt(paramFloat, 0.75F, 7.5F, 0.0F, 45.0F), 0.0F);
      else
        paramHierMesh.chunkSetAngles("WingLMid_D0", 0.0F, cvt(paramFloat, 7.5F, 9.0F, 45.0F, 50.0F), 0.0F);
    }
    else if (paramFloat < 11.0F) {
      paramHierMesh.chunkSetAngles("WingLMid_D0", 0.0F, cvt(paramFloat, 9.0F, 11.0F, 50.0F, 60.0F), 0.0F);
      paramHierMesh.chunkSetAngles("WingRMid_D0", 0.0F, cvt(paramFloat, 9.0F, 11.0F, 50.0F, 60.0F), 0.0F);
    } else {
      paramHierMesh.chunkSetAngles("WingLMid_D0", 0.0F, cvt(paramFloat, 11.0F, 15.75F, 60.0F, 105.0F), 0.0F);
      paramHierMesh.chunkSetAngles("WingRMid_D0", 0.0F, cvt(paramFloat, 11.0F, 15.75F, 60.0F, 105.0F), 0.0F);
    }
  }

  public void moveWingFold(float paramFloat) {
    if (this.prevWing > paramFloat)
      bGearExtending = false;
    else {
      bGearExtending = true;
    }
    this.prevWing = paramFloat;

    if (paramFloat < 0.001F) {
      setGunPodsOn(true);
      hideWingWeapons(false);
    } else {
      setGunPodsOn(false);
      this.FM.CT.WeaponControl[0] = false;
      hideWingWeapons(true);
    }
    moveWingFold(hierMesh(), paramFloat);
  }

  public void moveCockpitDoor(float paramFloat)
  {
    resetYPRmodifier();
    xyz[1] = cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.625F);
    xyz[2] = cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.06845F);
    ypr[2] = cvt(paramFloat, 0.01F, 0.99F, 0.0F, 1.0F);
    hierMesh().chunkSetLocate("Blister1_D0", xyz, ypr);
    resetYPRmodifier();
    xyz[2] = cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.13F);
    ypr[2] = cvt(paramFloat, 0.01F, 0.99F, 0.0F, -8.0F);
    hierMesh().chunkSetLocate("Pilot1_D0", xyz, ypr);
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null)) Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      setDoorSnd(paramFloat);
    }
  }

  protected void moveFlap(float paramFloat)
  {
    float f = 50.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap04_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap05_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap06_D0", 0.0F, f, 0.0F);
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);

    if (this.bNeedSetup) {
      this.cGear = this.FM.CT.GearControl;
      this.bNeedSetup = false;
    }

    this.cGear = filter(paramFloat, this.FM.CT.GearControl, this.cGear, 999.90002F, this.FM.CT.dvGear);

    if (this.prevGear2 > this.cGear)
      bGearExtending2 = false;
    else {
      bGearExtending2 = true;
    }
    this.prevGear2 = this.cGear;
    float f2 = 10.0F * this.cGear;
    if (bGearExtending2) {
      f1 = cvt(f2, 0.0F, 1.0F, 0.0F, 0.7071068F);
      this.cGearPos = (2.0F * f1 * f1);

      hierMesh().chunkSetAngles("GearC2_D0", 0.0F, 41.0F * this.cGearPos, 0.0F);

      f1 = cvt(f2, 0.0F, 0.25F, 0.0F, 1.0F);
      hierMesh().chunkSetAngles("GearC4_D0", 0.0F, 140.0F * f1, 0.0F);
      hierMesh().chunkSetAngles("GearC5_D0", 0.0F, 140.0F * f1, 0.0F);
    }
    else
    {
      this.cGearPos = cvt(f2, 8.5F, 10.0F, 0.0F, 1.0F);

      hierMesh().chunkSetAngles("GearC2_D0", 0.0F, 41.0F * this.cGearPos, 0.0F);
      f1 = cvt(f2, 8.5F, 8.75F, 0.0F, 1.0F);
      hierMesh().chunkSetAngles("GearC4_D0", 0.0F, 140.0F * f1, 0.0F);
      hierMesh().chunkSetAngles("GearC5_D0", 0.0F, 140.0F * f1, 0.0F);
    }

    if (this.FM.Gears.arrestorVAngle != 0.0F) {
      f1 = cvt(this.FM.Gears.arrestorVAngle, -43.0F, 21.5F, 0.0F, -64.5F);
      if (f1 < -64.5F * this.cGearPos) {
        f1 = -64.5F * this.cGearPos;
      }
      this.arrestorPos = (0.5F * this.arrestorPos + 0.5F * f1);

      this.arrestorVel = 0.0F;
    } else {
      if (this.arrestorVel >= -0.1F) {
        if (Engine.cur.land.isWater(this.FM.Loc.x, this.FM.Loc.y))
          f1 = 0.0F;
        else {
          f1 = cvt(this.FM.getSpeedKMH(), 0.0F, 250.0F, 0.0F, 0.75F);
        }
        f1 = -47.5F * World.Rnd().nextFloat(1.0F - f1, 1.0F + f1 + f1) * this.FM.Gears.arrestorVSink;
      }
      else {
        f1 = 0.0F;
      }

      if ((f1 < 0.0F) && (this.FM.getSpeedKMH() > 60.0F)) {
        Eff3DActor.New(this, this.FM.Gears.arrestorHook, null, 1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
      }
      if (f1 < -47.200001F) {
        f1 = -47.200001F;
      }

      if (f1 < 0.0F)
        this.arrestorVel += f1;
      else {
        this.arrestorVel += 0.5F;
      }

      this.arrestorPos += this.arrestorVel;

      if (this.arrestorPos < -64.5F * this.cGearPos) {
        this.arrestorPos = (-64.5F * this.cGearPos);
        this.arrestorVel = 0.0F;
      }

      if (this.arrestorPos > -64.5F * this.cGearPos * (1.0F - this.FM.CT.getArrestor()))
      {
        this.arrestorPos = (-64.5F * this.cGearPos * (1.0F - this.FM.CT.getArrestor()));

        this.arrestorVel = 0.0F;
      }

    }

    moveArrestorHook(this.arrestorPos);

    float f1 = Math.min(0.98F, this.FM.CT.getAirBrake());
    f1 = Math.max(f1, this.FM.CT.getGear());
    this.FM.CT.setGear(f1);

    f1 = this.FM.EI.engines[0].getControlRadiator();
    if (Math.abs(this.flapps - f1) > 0.01F) {
      this.flapps = f1;
      for (int i = 1; i < 22; i++) {
        hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, -23.700001F * f1, 0.0F);
      }
      hierMesh().chunkSetAngles("Water19_D0", 0.0F, -16.0F * f1, 0.0F);
    }
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("HMask1_D0", false);
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        debuggunnery("Armor: Hit..");
        if (paramString.endsWith("f1")) {
          getEnergyPastArmor(World.Rnd().nextFloat(8.0F, 12.0F) / (Math.abs(v1.z) + 9.999999747378752E-005D), paramShot);
        } else if (paramString.endsWith("p1")) {
          getEnergyPastArmor(World.Rnd().nextFloat(16.0F, 36.0F) / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
          if (paramShot.power <= 0.0F)
            doRicochetBack(paramShot);
        }
        else if (paramString.endsWith("p2")) {
          getEnergyPastArmor(11.0D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        } else if (paramString.endsWith("p3")) {
          getEnergyPastArmor(11.5D / (Math.abs(v1.x) + 9.999999747378752E-005D), paramShot);
        }
        return;
      }
      if ((paramString.startsWith("xxcmglammo")) && 
        (World.Rnd().nextFloat(0.0F, 20000.0F) < paramShot.power)) {
        i = 0 + 2 * World.Rnd().nextInt(0, 2);
        this.FM.AS.setJamBullets(0, i);
      }

      if ((paramString.startsWith("xxcmgrammo")) && 
        (World.Rnd().nextFloat(0.0F, 20000.0F) < paramShot.power)) {
        i = 1 + 2 * World.Rnd().nextInt(0, 2);
        this.FM.AS.setJamBullets(0, i);
      }

      if (paramString.startsWith("xxcontrols")) {
        debuggunnery("Controls: Hit..");
        i = paramString.charAt(10) - '0';
        switch (i) {
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
          if ((World.Rnd().nextFloat() >= 0.5F) || (getEnergyPastArmor(0.1F, paramShot) <= 0.0F)) break;
          debuggunnery("Controls: Ailerones Controls: Disabled..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 7:
        case 8:
          if ((World.Rnd().nextFloat() >= 0.08F) || (getEnergyPastArmor(0.1F, paramShot) <= 0.0F)) break;
          debuggunnery("Controls: Elevator Controls: Disabled..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 1); break;
        case 9:
          if ((World.Rnd().nextFloat() >= 0.95F) || (getEnergyPastArmor(1.27F, paramShot) <= 0.0F)) break;
          debuggunnery("Controls: Rudder Controls: Disabled..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
        }

        return;
      }
      if (paramString.startsWith("xxeng1")) {
        debuggunnery("Engine Module: Hit..");
        if (paramString.endsWith("case")) {
          if (getEnergyPastArmor(World.Rnd().nextFloat(0.2F, 0.55F), paramShot) > 0.0F) {
            if (World.Rnd().nextFloat() < paramShot.power / 280000.0F) {
              debuggunnery("Engine Module: Engine Crank Case Hit - Engine Stucks..");
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 100000.0F) {
              debuggunnery("Engine Module: Engine Crank Case Hit - Engine Damaged..");
              this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
            }
          }
          getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 24.0F), paramShot);
        }
        if (paramString.endsWith("cyls")) {
          if ((getEnergyPastArmor(0.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 0.66F)) {
            this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 32200.0F)));
            debuggunnery("Engine Module: Cylinders Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 1000000.0F) {
              this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
              debuggunnery("Engine Module: Cylinders Hit - Engine Fires..");
            }
          }
          getEnergyPastArmor(25.0F, paramShot);
        }
        if (paramString.endsWith("eqpt")) {
          if (getEnergyPastArmor(0.5F, paramShot) > 0.0F) {
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 4);
            }
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 0);
            }
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
            }
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power) {
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
            }
          }
          getEnergyPastArmor(2.0F, paramShot);
        }
        if (paramString.endsWith("gear")) {
          if ((getEnergyPastArmor(4.6F, paramShot) > 0.0F) && 
            (World.Rnd().nextFloat() < 0.5F)) {
            debuggunnery("Engine Module: Bullet Jams Reductor Gear..");
            this.FM.EI.engines[0].setEngineStuck(paramShot.initiator);
          }

          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 12.445654F), paramShot);
        }
        if (paramString.startsWith("xxeng1mag")) {
          i = paramString.charAt(9) - '1';
          debuggunnery("Engine Module: Magneto " + i + " Destroyed..");
          this.FM.EI.engines[0].setMagnetoKnockOut(paramShot.initiator, i);
        }
        if (paramString.endsWith("oil1")) {
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.25F, paramShot) > 0.0F))
            debuggunnery("Engine Module: Oil Radiator Hit..");
          this.FM.AS.hitOil(paramShot.initiator, 0);
        }
        if ((paramString.endsWith("prop")) && 
          (getEnergyPastArmor(0.42F, paramShot) > 0.0F)) {
          this.FM.EI.engines[0].setKillPropAngleDevice(paramShot.initiator);
        }

        if ((paramString.startsWith("xxeng1typ")) && 
          (getEnergyPastArmor(0.42F, paramShot) > 0.0F)) {
          this.FM.EI.engines[0].setKillPropAngleDeviceSpeeds(paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxhyd")) {
        if (getEnergyPastArmor(World.Rnd().nextFloat(0.25F, 12.39F), paramShot) > 0.0F) {
          debuggunnery("Hydro System: Disabled..");
          this.FM.AS.setInternalDamage(paramShot.initiator, 0);
        }
        return;
      }
      if (paramString.startsWith("xxlock")) {
        debuggunnery("Lock Construction: Hit..");
        if ((paramString.startsWith("xxlockr")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvl")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: VatorL Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockvr")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: VatorR Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockal")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: AroneL Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), paramShot.initiator);
        }

        if ((paramString.startsWith("xxlockar")) && 
          (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debuggunnery("Lock Construction: AroneR Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxmgun0")) {
        i = paramString.charAt(7) - '1';
        if (getEnergyPastArmor(0.5F, paramShot) > 0.0F) {
          debuggunnery("Armament: Machine Gun (" + i + ") Disabled..");
          this.FM.AS.setJamBullets(0, i);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxoil")) {
        if ((getEnergyPastArmor(0.25F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F)) {
          this.FM.AS.hitOil(paramShot.initiator, 0);
          getEnergyPastArmor(0.22F, paramShot);
          debuggunnery("Engine Module: Oil Tank Pierced..");
        }
        return;
      }
      if (paramString.startsWith("xxradio")) {
        getEnergyPastArmor(25.532F, paramShot);
        return;
      }
      if (paramString.startsWith("xxspar")) {
        debugprintln(this, "*** Spar Construction: Hit..");
        if ((paramString.startsWith("xxsparli")) && 
          (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(12.7F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparri")) && 
          (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(12.7F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlm")) && 
          (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(10.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparrm")) && 
          (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(10.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlo")) && 
          (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 1.8F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparro")) && 
          (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 1.8F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxspart")) && 
          (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(13.8F * World.Rnd().nextFloat(0.99F, 1.8F), paramShot) > 0.0F)) {
          debugprintln(this, "*** Tail1 Spars Damaged..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxsupc")) {
        if (getEnergyPastArmor(World.Rnd().nextFloat(0.2F, 12.0F), paramShot) > 0.0F) {
          debuggunnery("Engine Module: Turbine Disabled..");
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 0);
        }
        return;
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if (getEnergyPastArmor(1.0F, paramShot) > 0.0F) {
          if (this.FM.AS.astateTankStates[i] == 0) {
            debuggunnery("Fuel Tank (" + i + "): Pierced..");
            this.FM.AS.hitTank(paramShot.initiator, i, 1);
            this.FM.AS.doSetTankState(paramShot.initiator, i, 1);
          }
          if ((World.Rnd().nextFloat() < 0.07F) || ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.8F))) {
            this.FM.AS.hitTank(paramShot.initiator, i, 2);
            debuggunnery("Fuel Tank (" + i + "): Hit..");
          }
        }
        return;
      }
      return;
    }

    if ((paramString.startsWith("xcf")) || (paramString.startsWith("xblister"))) {
      hitChunk("CF", paramShot);
      if (paramString.startsWith("xcf2")) {
        if ((paramPoint3d.x > -2.313D) && (paramPoint3d.x < -1.455D) && (paramPoint3d.z > 0.669D)) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
        }
        if (paramPoint3d.z > 1.125D) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
        }
      }
      else if ((paramPoint3d.x > -1.489D) && (paramPoint3d.x < -1.2D) && (paramPoint3d.z > 0.34D)) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
      }

      if (World.Rnd().nextFloat() < 0.054F) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
      }
      if (World.Rnd().nextFloat() < 0.054F) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
      }
      return;
    }if (paramString.startsWith("xeng")) {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel")) {
      if (chunkDamageVisible("Keel1") < 2) {
        hitChunk("Keel1", paramShot);
      }

    }
    else if (paramString.startsWith("xstab")) {
      if ((paramString.startsWith("xstabl")) && 
        (chunkDamageVisible("StabL") < 2)) {
        hitChunk("StabL", paramShot);
      }

      if ((paramString.startsWith("xstabr")) && 
        (chunkDamageVisible("StabR") < 1)) {
        hitChunk("StabR", paramShot);
      }

    }
    else if (paramString.startsWith("xwing")) {
      if ((paramString.startsWith("xwinglin")) && 
        (chunkDamageVisible("WingLIn") < 3)) {
        hitChunk("WingLIn", paramShot);
      }

      if ((paramString.startsWith("xwingrin")) && 
        (chunkDamageVisible("WingRIn") < 3)) {
        hitChunk("WingRIn", paramShot);
      }

      if ((paramString.startsWith("xwinglmid")) && 
        (chunkDamageVisible("WingLMid") < 3)) {
        hitChunk("WingLMid", paramShot);
      }

      if ((paramString.startsWith("xwingrmid")) && 
        (chunkDamageVisible("WingRMid") < 3)) {
        hitChunk("WingRMid", paramShot);
      }

      if ((paramString.startsWith("xwinglout")) && 
        (chunkDamageVisible("WingLOut") < 3)) {
        hitChunk("WingLOut", paramShot);
      }

      if ((paramString.startsWith("xwingrout")) && 
        (chunkDamageVisible("WingROut") < 3)) {
        hitChunk("WingROut", paramShot);
      }

    }
    else if (paramString.startsWith("xgear")) {
      if ((paramString.endsWith("1")) && 
        (World.Rnd().nextFloat() < 0.05F)) {
        debuggunnery("Hydro System: Disabled..");
        this.FM.AS.setInternalDamage(paramShot.initiator, 0);
      }
    }
    else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
      i = 0;
      int j;
      if (paramString.endsWith("a")) {
        i = 1;
        j = paramString.charAt(6) - '1';
      } else if (paramString.endsWith("b")) {
        i = 2;
        j = paramString.charAt(6) - '1';
      } else {
        j = paramString.charAt(5) - '1';
      }
      hitFlesh(j, paramShot, i);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
  }

  private static final float filter(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    float f1 = (float)Math.exp(-paramFloat1 / paramFloat4);

    float f2 = paramFloat2 + (paramFloat3 - paramFloat2) * f1;
    if (f2 < paramFloat2) { f2 += paramFloat5 * paramFloat1; if (f2 > paramFloat2) f2 = paramFloat2; 
    }
    else if (f2 > paramFloat2) { f2 -= paramFloat5 * paramFloat1; if (f2 < paramFloat2) f2 = paramFloat2; 
    }
    return f2;
  }

  static
  {
    Class localClass = F4U.class;
    Property.set(localClass, "originCountry", PaintScheme.countryUSA);
  }
}
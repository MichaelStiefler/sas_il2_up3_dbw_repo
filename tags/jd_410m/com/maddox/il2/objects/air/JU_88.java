package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public abstract class JU_88 extends Scheme2
{
  float suspR = 0.0F;
  float suspL = 0.0F;

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = Math.max(-paramFloat * 1600.0F, -80.0F);
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
    if (f > -2.5F)
      f = 0.0F;
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    f = paramFloat < 0.5F ? Math.abs(Math.min(paramFloat, 0.1F)) : Math.abs(Math.min(1.0F - paramFloat, 0.1F));
    if (f < 0.002F)
      f = 0.0F;
    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, -450.0F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, 450.0F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, 1200.0F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, -1200.0F * f, 0.0F);
    f = cvt(paramFloat, 0.0F, 0.5F, 0.0F, 0.1F);
    if (f < 0.002F)
      f = 0.0F;
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, 900.0F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -900.0F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -900.0F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, 900.0F * f, 0.0F);

    paramHierMesh.chunkSetAngles("GearR8_D0", 0.0F, 0.0F, 93.0F * paramFloat);
    paramHierMesh.chunkSetAngles("GearL8_D0", 0.0F, 0.0F, 93.0F * paramFloat);

    paramHierMesh.chunkSetAngles("GearR3_D0", 85.0F * paramFloat, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", -85.0F * paramFloat, 0.0F, 0.0F);

    paramHierMesh.chunkSetAngles("GearR9_D0", 0.0F, 0.0F, -116.0F * paramFloat);
    paramHierMesh.chunkSetAngles("GearL9_D0", 0.0F, 0.0F, -116.0F * paramFloat);

    paramHierMesh.chunkSetAngles("GearR10_D0", 0.0F, 0.0F, 126.0F * paramFloat);
    paramHierMesh.chunkSetAngles("GearL10_D0", 0.0F, 0.0F, 126.0F * paramFloat);
  }

  public void moveWheelSink()
  {
    this.suspL = (0.9F * this.suspL + 0.1F * this.FM.Gears.gWheelSinking[0]);
    this.suspR = (0.9F * this.suspR + 0.1F * this.FM.Gears.gWheelSinking[1]);

    if (this.suspL > 0.035F)
      this.suspL = 0.035F;
    if (this.suspR > 0.035F)
      this.suspR = 0.035F;
    if (this.suspL < 0.0F)
      this.suspL = 0.0F;
    if (this.suspR < 0.0F) {
      this.suspR = 0.0F;
    }
    Aircraft.xyz[0] = 0.0F;
    Aircraft.ypr[0] = 0.0F;
    Aircraft.xyz[1] = 0.0F;
    Aircraft.ypr[1] = 0.0F;
    Aircraft.xyz[2] = 0.0F;
    Aircraft.ypr[2] = 0.0F;
    float f = 588.0F;

    Aircraft.xyz[2] = (this.suspL * 6.0F);
    hierMesh().chunkSetLocate("GearL2_D0", Aircraft.xyz, Aircraft.ypr);
    hierMesh().chunkSetAngles("GearL11_D0", 0.0F, 0.0F, this.suspL * f);
    hierMesh().chunkSetAngles("GearL12_D0", 0.0F, 0.0F, -this.suspL * f);

    Aircraft.xyz[2] = (this.suspR * 6.0F);
    hierMesh().chunkSetLocate("GearR2_D0", Aircraft.xyz, Aircraft.ypr);
    hierMesh().chunkSetAngles("GearR11_D0", 0.0F, 0.0F, this.suspR * f);
    hierMesh().chunkSetAngles("GearR12_D0", 0.0F, 0.0F, -this.suspR * f);
  }
  protected void moveGear(float paramFloat) {
    moveGear(hierMesh(), paramFloat);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -70.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
  }

  public void update(float paramFloat)
  {
    for (int i = 1; i < 11; i++)
    {
      hierMesh().chunkSetAngles("Radl" + i + "_D0", -30.0F * this.FM.EI.engines[0].getControlRadiator(), 0.0F, 0.0F);

      hierMesh().chunkSetAngles("Radr" + i + "_D0", -30.0F * this.FM.EI.engines[1].getControlRadiator(), 0.0F, 0.0F);
    }

    super.update(paramFloat);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -30.0F) { f1 = -30.0F; bool = false; }
      if (f1 > 35.0F) { f1 = 35.0F; bool = false; }
      if (f2 < -10.0F) { f2 = -10.0F; bool = false; }
      if (f2 <= 35.0F) break; f2 = 35.0F; bool = false; break;
    case 1:
      f1 = 0.0F;
      f2 = 0.0F;
      bool = false;
      break;
    case 2:
      if (f1 < -45.0F) { f1 = -45.0F; bool = false; }
      if (f1 > 25.0F) { f1 = 25.0F; bool = false; }
      if (f2 < -10.0F) { f2 = -10.0F; bool = false; }
      if (f2 > 60.0F) { f2 = 60.0F; bool = false;
      }
      if (f1 < -2.0F) {
        if (f2 >= cvt(f1, -6.8F, -2.0F, -10.0F, -2.99F)) break;
        f2 = cvt(f1, -6.8F, -2.0F, -10.0F, -2.99F);
      }
      else if (f1 < 0.5F) {
        if (f2 >= cvt(f1, -2.0F, 0.5F, -2.99F, -2.3F)) break;
        f2 = cvt(f1, -2.0F, 0.5F, -2.99F, -2.3F);
      }
      else if (f1 < 5.3F) {
        if (f2 >= cvt(f1, 0.5F, 5.3F, -2.3F, -2.3F)) break;
        f2 = cvt(f1, 0.5F, 5.3F, -2.3F, -2.3F);
      }
      else {
        if (f2 >= cvt(f1, 5.3F, 25.0F, -2.3F, -7.2F)) break;
        f2 = cvt(f1, 5.3F, 25.0F, -2.3F, -7.2F); } break;
    case 3:
      if (f1 < -35.0F) { f1 = -35.0F; bool = false; }
      if (f1 > 35.0F) { f1 = 35.0F; bool = false; }
      if (f2 < -35.0F) { f2 = -35.0F; bool = false; }
      if (f2 <= -0.48F) break; f2 = -0.48F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1)
    {
    case 13:
      return false;
    case 33:
      return super.cutFM(34, paramInt2, paramActor);
    case 36:
      return super.cutFM(37, paramInt2, paramActor);
    case 3:
      this.FM.AS.hitEngine(this, 0, 99);
      break;
    case 4:
      this.FM.AS.hitEngine(this, 1, 99);
      break;
    case 19:
      this.FM.Gears.hitCentreGear();
      break;
    case 37:
      this.FM.Gears.hitRightGear();
      break;
    case 34:
      this.FM.Gears.hitLeftGear();
      break;
    case 10:
      doWreck("GearR8_D0");
      this.FM.Gears.hitRightGear();
      break;
    case 9:
      doWreck("GearL8_D0");
      this.FM.Gears.hitLeftGear();
    case 5:
    case 6:
    case 7:
    case 8:
    case 11:
    case 12:
    case 14:
    case 15:
    case 16:
    case 17:
    case 18:
    case 20:
    case 21:
    case 22:
    case 23:
    case 24:
    case 25:
    case 26:
    case 27:
    case 28:
    case 29:
    case 30:
    case 31:
    case 32:
    case 35: } return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  private void doWreck(String paramString)
  {
    if (hierMesh().chunkFindCheck(paramString) != -1)
    {
      hierMesh().hideSubTrees(paramString);
      Wreckage localWreckage = new Wreckage(this, hierMesh().chunkFind(paramString));
      localWreckage.collide(true);
      Vector3d localVector3d = new Vector3d();
      localVector3d.set(this.FM.Vwld);
      localWreckage.setSpeed(localVector3d);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if ((paramBoolean) && 
      (World.Rnd().nextFloat() < 0.2F)) {
      if (this.FM.AS.astateEngineStates[0] > 3) {
        if (World.Rnd().nextFloat() < 0.25F) this.FM.AS.hitTank(this, 0, 3);
        if (World.Rnd().nextFloat() < 0.12F) this.FM.AS.hitTank(this, 1, 3);
      }
      if (this.FM.AS.astateEngineStates[1] > 3) {
        if (World.Rnd().nextFloat() < 0.12F) this.FM.AS.hitTank(this, 2, 3);
        if (World.Rnd().nextFloat() < 0.25F) this.FM.AS.hitTank(this, 3, 3);
      }
      if ((this.FM.AS.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.11F)) nextDMGLevel(this.FM.AS.astateEffectChunks[0] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.11F)) nextDMGLevel(this.FM.AS.astateEffectChunks[1] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.11F)) this.FM.AS.hitTank(this, 2, 3);
      if ((this.FM.AS.astateTankStates[2] > 4) && (World.Rnd().nextFloat() < 0.11F)) this.FM.AS.hitTank(this, 1, 3);
      if ((this.FM.AS.astateTankStates[2] > 4) && (World.Rnd().nextFloat() < 0.11F)) nextDMGLevel(this.FM.AS.astateEffectChunks[2] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[3] > 4) && (World.Rnd().nextFloat() < 0.11F)) nextDMGLevel(this.FM.AS.astateEffectChunks[3] + "0", 0, this);

    }

    if (!(this instanceof JU_88MSTL))
      for (int i = 1; i < 4; i++)
        if (this.FM.getAltitude() < 3000.0F)
          hierMesh().chunkVisible("HMask" + i + "_D0", false);
        else
          hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

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

  public void typeDiveBomberAdjVelocityReset()
  {
  }

  public void typeDiveBomberAdjVelocityPlus()
  {
  }

  public void typeDiveBomberAdjVelocityMinus()
  {
  }

  public void typeDiveBomberAdjDiveAngleReset()
  {
  }

  public void typeDiveBomberAdjDiveAnglePlus()
  {
  }

  public void typeDiveBomberAdjDiveAngleMinus()
  {
  }

  public void typeDiveBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
  }

  public void typeDiveBomberReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException
  {
  }

  static
  {
    Class localClass = JU_88.class;
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
  }
}
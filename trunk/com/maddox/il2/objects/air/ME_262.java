package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.rts.Property;

public class ME_262 extends Scheme2
  implements TypeFighter, TypeBNZFighter
{
  private float[] oldctl = { -1.0F, -1.0F };
  private float[] curctl = { -1.0F, -1.0F };

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      if (this.FM.AS.bIsAboutToBailout) break;
      if (hierMesh().isChunkVisible("Blister1_D0")) {
        hierMesh().chunkVisible("Gore1_D0", true);
      }
      hierMesh().chunkVisible("Gore2_D0", true);
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 111.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC21_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, cvt(paramFloat, 0.01F, 0.11F, 0.0F, 90.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 73.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 73.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, 88.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, 88.0F * paramFloat, 0.0F);

    float f = Math.max(-paramFloat * 1500.0F, -90.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, f, 0.0F);
  }
  protected void moveGear(float paramFloat) {
    moveGear(hierMesh(), paramFloat);
  }
  public void moveWheelSink() { resetYPRmodifier();
    float f = this.FM.Gears.gWheelSinking[2];
    xyz[1] = cvt(f, 0.0F, 0.19F, 0.0F, 0.19F);
    hierMesh().chunkSetLocate("GearC22_D0", xyz, ypr);
    f = cvt(f, 0.0F, 19.0F, 0.0F, 30.0F);
    hierMesh().chunkSetAngles("GearC7_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("GearC8_D0", 0.0F, 2.0F * f, 0.0F);
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    if (this.FM.CT.getGear() > 0.75F)
      hierMesh().chunkSetAngles("GearC21_D0", 0.0F, 40.0F * paramFloat, 0.0F);
  }

  protected void moveFan(float paramFloat)
  {
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    if (paramString.startsWith("xcf")) {
      if (chunkDamageVisible("CF") < 3) {
        hitChunk("CF", paramShot);
      }
      if (paramPoint3d.x > 1.7D) {
        if (World.Rnd().nextFloat() < 0.07F) {
          this.FM.AS.setJamBullets(0, 0);
        }
        if (World.Rnd().nextFloat() < 0.07F) {
          this.FM.AS.setJamBullets(0, 1);
        }
        if (World.Rnd().nextFloat() < 0.12F) {
          this.FM.AS.setJamBullets(1, 0);
        }
        if (World.Rnd().nextFloat() < 0.12F) {
          this.FM.AS.setJamBullets(1, 1);
        }
      }
      if ((paramPoint3d.x > -0.999D) && (paramPoint3d.x < 0.535D) && (paramPoint3d.z > -0.224D)) {
        if (World.Rnd().nextFloat() < 0.1F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
        }
        if (World.Rnd().nextFloat() < 0.1F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
        }
        if (World.Rnd().nextFloat() < 0.1F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
        }
        if (World.Rnd().nextFloat() < 0.1F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
        }
        if (World.Rnd().nextFloat() < 0.1F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
        }
        if (World.Rnd().nextFloat() < 0.1F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
        }
        if (World.Rnd().nextFloat() < 0.1F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
        }
      }
      if ((paramPoint3d.x > 0.8D) && (paramPoint3d.x < 1.58D) && 
        (World.Rnd().nextFloat() < 0.25F) && (
        ((paramShot.powerType == 3) && (getEnergyPastArmor(0.4F, paramShot) > 0.0F)) || (paramShot.powerType == 0))) {
        this.FM.AS.hitTank(paramShot.initiator, 0, World.Rnd().nextInt(1, (int)(paramShot.power / 4000.0F)));
      }

      if ((paramPoint3d.x > -2.485D) && (paramPoint3d.x < -1.6D) && 
        (World.Rnd().nextFloat() < 0.25F) && (
        ((paramShot.powerType == 3) && (getEnergyPastArmor(0.4F, paramShot) > 0.0F)) || (paramShot.powerType == 0))) {
        this.FM.AS.hitTank(paramShot.initiator, 1, World.Rnd().nextInt(1, (int)(paramShot.power / 4000.0F)));
      }
    }
    else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel")) {
      hitChunk("Keel1", paramShot);
    } else if (paramString.startsWith("xstabl")) {
      hitChunk("StabL", paramShot);
    } else if (paramString.startsWith("xstabr")) {
      hitChunk("StabR", paramShot);
    } else if (paramString.startsWith("xwing")) {
      if ((paramString.endsWith("lin")) && 
        (chunkDamageVisible("WingLIn") < 3)) {
        hitChunk("WingLIn", paramShot);
      }

      if ((paramString.endsWith("rin")) && 
        (chunkDamageVisible("WingRIn") < 3)) {
        hitChunk("WingRIn", paramShot);
      }

      if ((paramString.endsWith("lmid")) && 
        (chunkDamageVisible("WingLMid") < 3)) {
        hitChunk("WingLMid", paramShot);
      }

      if ((paramString.endsWith("rmid")) && 
        (chunkDamageVisible("WingRMid") < 3)) {
        hitChunk("WingRMid", paramShot);
      }

      if ((paramString.endsWith("lout")) && 
        (chunkDamageVisible("WingLOut") < 3)) {
        hitChunk("WingLOut", paramShot);
      }

      if ((paramString.endsWith("rout")) && 
        (chunkDamageVisible("WingROut") < 3))
        hitChunk("WingROut", paramShot);
    }
    else
    {
      int i;
      if (paramString.startsWith("xengine")) {
        i = paramString.charAt(7) - '1';
        if ((paramPoint3d.x > 0.0D) && (paramPoint3d.x < 0.697D)) {
          this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, 6));
        }
        if (World.Rnd().nextFloat(0.009F, 0.1357F) < paramShot.mass)
          this.FM.AS.hitEngine(paramShot.initiator, i, 5);
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
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 33:
      return super.cutFM(34, paramInt2, paramActor);
    case 36:
      return super.cutFM(37, paramInt2, paramActor);
    case 11:
      cutFM(17, paramInt2, paramActor); this.FM.cut(17, paramInt2, paramActor);
      cutFM(18, paramInt2, paramActor); this.FM.cut(18, paramInt2, paramActor);
      return super.cutFM(paramInt1, paramInt2, paramActor);
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if ((paramBoolean) && 
      (World.Rnd().nextFloat() < 0.2F)) {
      if ((this.FM.AS.astateEngineStates[0] > 3) && 
        (World.Rnd().nextFloat() < 0.12F)) {
        this.FM.AS.explodeEngine(this, 0);
        msgCollision(this, "WingLIn_D0", "WingLIn_D0");
        if (World.Rnd().nextBoolean()) this.FM.AS.hitTank(this, 0, World.Rnd().nextInt(1, 8)); else {
          this.FM.AS.hitTank(this, 1, World.Rnd().nextInt(1, 8));
        }
      }
      if ((this.FM.AS.astateEngineStates[1] > 3) && 
        (World.Rnd().nextFloat() < 0.12F)) {
        this.FM.AS.explodeEngine(this, 1);
        msgCollision(this, "WingRIn_D0", "WingRIn_D0");
        if (World.Rnd().nextBoolean()) this.FM.AS.hitTank(this, 0, World.Rnd().nextInt(1, 8)); else {
          this.FM.AS.hitTank(this, 1, World.Rnd().nextInt(1, 8));
        }
      }

    }

    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
  }

  public void update(float paramFloat)
  {
    if (this.FM.getSpeed() > 5.0F) {
      hierMesh().chunkSetAngles("SlatL0_D0", 0.0F, cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, -1.0F), 0.0F);
      hierMesh().chunkSetAngles("SlatL1_D0", 0.0F, cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, -1.0F), 0.0F);
      hierMesh().chunkSetAngles("SlatR0_D0", 0.0F, cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, -1.0F), 0.0F);
      hierMesh().chunkSetAngles("SlatR1_D0", 0.0F, cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, -1.0F), 0.0F);
      if (!(this instanceof ME_262HGII)) {
        hierMesh().chunkSetAngles("SlatL2_D0", 0.0F, cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, -1.0F), 0.0F);
        hierMesh().chunkSetAngles("SlatR2_D0", 0.0F, cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, -1.0F), 0.0F);
      }
    }
    if (this.FM.AS.isMaster()) {
      for (int i = 0; i < 2; i++) {
        if (this.curctl[i] == -1.0F)
        {
          float tmp245_242 = this.FM.EI.engines[i].getControlThrottle(); this.oldctl[i] = tmp245_242; this.curctl[i] = tmp245_242;
        } else {
          this.curctl[i] = this.FM.EI.engines[i].getControlThrottle();
          if (((this.curctl[i] - this.oldctl[i]) / paramFloat > 3.0F) && (this.FM.EI.engines[i].getRPM() < 2400.0F) && (this.FM.EI.engines[i].getStage() == 6) && (World.Rnd().nextFloat() < 0.25F)) {
            this.FM.AS.hitEngine(this, i, 100);
          }
          if (((this.curctl[i] - this.oldctl[i]) / paramFloat < -3.0F) && (this.FM.EI.engines[i].getRPM() < 2400.0F) && (this.FM.EI.engines[i].getStage() == 6)) {
            if ((World.Rnd().nextFloat() < 0.25F) && ((this.FM instanceof RealFlightModel)) && (((RealFlightModel)this.FM).isRealMode())) {
              this.FM.EI.engines[i].setEngineStops(this);
            }
            if ((World.Rnd().nextFloat() < 0.75F) && ((this.FM instanceof RealFlightModel)) && (((RealFlightModel)this.FM).isRealMode())) {
              this.FM.EI.engines[i].setKillCompressor(this);
            }
          }
          this.oldctl[i] = this.curctl[i];
        }
      }

      if (Config.isUSE_RENDER()) {
        if ((this.FM.EI.engines[0].getPowerOutput() > 0.8F) && (this.FM.EI.engines[0].getStage() == 6)) {
          if (this.FM.EI.engines[0].getPowerOutput() > 0.95F)
            this.FM.AS.setSootState(this, 0, 3);
          else
            this.FM.AS.setSootState(this, 0, 2);
        }
        else {
          this.FM.AS.setSootState(this, 0, 0);
        }
        if ((this.FM.EI.engines[1].getPowerOutput() > 0.8F) && (this.FM.EI.engines[1].getStage() == 6)) {
          if (this.FM.EI.engines[1].getPowerOutput() > 0.95F)
            this.FM.AS.setSootState(this, 1, 3);
          else
            this.FM.AS.setSootState(this, 1, 2);
        }
        else {
          this.FM.AS.setSootState(this, 1, 0);
        }
      }
    }
    super.update(paramFloat);
  }

  static
  {
    Class localClass = ME_262.class;
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
  }
}
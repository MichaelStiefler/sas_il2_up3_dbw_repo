package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Property;

public abstract class GO_229 extends Scheme2
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
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, -85.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 100.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, cvt(paramFloat, 0.0F, 0.078F, 0.0F, -70.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, cvt(paramFloat, 0.0F, 0.078F, 0.0F, -70.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC7_D0", 0.0F, 0.0F, 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, cvt(paramFloat, 0.0F, 0.078F, 0.0F, 90.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, cvt(paramFloat, 0.0F, 0.078F, 0.0F, 90.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveWheelSink() {
    if (this.FM.CT.getGear() < 0.8F) {
      return;
    }
    resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.425F, 0.0F, 0.425F);
    hierMesh().chunkSetLocate("GearC6_D0", xyz, ypr);
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.425F, 100.0F, 83.5F), 0.0F);
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
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
  }

  protected void moveRudder(float paramFloat)
  {
    if (this.FM.CT.getGear() > 0.99F) {
      hierMesh().chunkSetAngles("GearC7_D0", 0.0F, 20.0F * paramFloat, 0.0F);
    }
    resetYPRmodifier();
    xyz[2] = cvt(paramFloat, -1.0F, 0.0F, 0.16F, 0.0F);
    hierMesh().chunkSetLocate("Rudder1_D0", xyz, ypr);
    resetYPRmodifier();
    xyz[1] = cvt(paramFloat, -1.0F, 0.0F, 0.0975F, 0.0F);
    hierMesh().chunkSetLocate("Rudder2_D0", xyz, ypr);
    resetYPRmodifier();
    xyz[2] = cvt(paramFloat, 0.0F, 1.0F, 0.0F, 0.16F);
    hierMesh().chunkSetLocate("Rudder3_D0", xyz, ypr);
    resetYPRmodifier();
    xyz[1] = cvt(paramFloat, 0.0F, 1.0F, 0.0F, 0.0975F);
    hierMesh().chunkSetLocate("Rudder4_D0", xyz, ypr);
  }

  protected void moveElevator(float paramFloat)
  {
    hierMesh().chunkSetAngles("VatorL_D0", 0.0F, 15.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -15.0F * paramFloat, 0.0F);
  }

  protected void moveAileron(float paramFloat)
  {
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -15.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -15.0F * paramFloat, 0.0F);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 0:
      this.FM.cut(0, paramInt2, paramActor);
      this.FM.cut(31, paramInt2, paramActor);
      break;
    case 1:
      this.FM.cut(1, paramInt2, paramActor);
      this.FM.cut(32, paramInt2, paramActor);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void moveAirBrake(float paramFloat)
  {
    resetYPRmodifier();
    xyz[1] = (0.1137F * paramFloat);
    this.FM.setGCenter(0.05F + 0.12F * paramFloat);
    hierMesh().chunkSetLocate("Brake01_D0", xyz, ypr);
  }

  public void doEjectCatapult()
  {
    new MsgAction(false, this) {
      public void doAction(Object paramObject) { Aircraft localAircraft = (Aircraft)paramObject;
        if (!Actor.isValid(localAircraft)) return;
        Loc localLoc1 = new Loc();
        Loc localLoc2 = new Loc();
        Vector3d localVector3d = new Vector3d(0.0D, 0.0D, 10.0D);
        HookNamed localHookNamed = new HookNamed(localAircraft, "_ExternalSeat01");
        localAircraft.pos.getAbs(localLoc2);
        localHookNamed.computePos(localAircraft, localLoc2, localLoc1);
        localLoc1.transform(localVector3d);
        localVector3d.x += localAircraft.FM.Vwld.x;
        localVector3d.y += localAircraft.FM.Vwld.y;
        localVector3d.z += localAircraft.FM.Vwld.z;
        new EjectionSeat(1, localLoc1, localVector3d, localAircraft);
      }
    };
    hierMesh().chunkVisible("Seat_D0", false);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        if (paramString.endsWith("p1"))
          getEnergyPastArmor(7.71999979019165D / v1.x * World.Rnd().nextFloat(1.0F, 1.2F), paramShot);
        else if (paramString.endsWith("g1")) {
          getEnergyPastArmor(9.810000419616699D / v1.x * World.Rnd().nextFloat(1.0F, 1.2F), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcontrols")) {
        if (getEnergyPastArmor(1.0F, paramShot) > 0.0F) {
          if (World.Rnd().nextFloat() < 0.12F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
            this.FM.AS.setControlsDamage(paramShot.initiator, 0);
            debuggunnery("E/A Controls Out..");
            return;
          }
          if (World.Rnd().nextFloat() < 0.12F) {
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);
            debuggunnery("Rudder Controls Out..");
            return;
          }
        }
        return;
      }
      if (paramString.startsWith("xxspar")) {
        if ((paramString.startsWith("xxsparli")) && 
          (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparri")) && 
          (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlm")) && 
          (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparrm")) && 
          (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparlo")) && 
          (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }

        if ((paramString.startsWith("xxsparro")) && 
          (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(12.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F)) {
          debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }

        return;
      }
      if (paramString.startsWith("xxeng")) {
        i = paramString.charAt(5) - '1';
        if (getEnergyPastArmor(3.4F, paramShot) > 0.0F) {
          this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, 6));
        }
        if (World.Rnd().nextFloat(0.009F, 0.1357F) < paramShot.mass) {
          this.FM.AS.hitEngine(paramShot.initiator, i, 5);
        }
        return;
      }
      if (paramString.startsWith("xxtank")) {
        i = paramString.charAt(6) - '1';
        if (getEnergyPastArmor(0.2F, paramShot) > 0.0F) {
          if (paramShot.power < 14100.0F) {
            if (this.FM.AS.astateTankStates[i] == 0) {
              this.FM.AS.hitTank(paramShot.initiator, i, 1);
              this.FM.AS.doSetTankState(paramShot.initiator, i, 1);
            }
            if ((this.FM.AS.astateTankStates[i] < 4) && (World.Rnd().nextFloat() < 0.03F)) {
              this.FM.AS.hitTank(paramShot.initiator, i, 1);
            }
            if ((paramShot.powerType == 3) && (this.FM.AS.astateTankStates[i] > 2) && (World.Rnd().nextFloat() < 0.015F))
              this.FM.AS.hitTank(paramShot.initiator, i, 10);
          }
          else {
            this.FM.AS.hitTank(paramShot.initiator, i, World.Rnd().nextInt(0, (int)(paramShot.power / 56000.0F)));
          }
        }
        return;
      }
      return;
    }

    if (paramString.startsWith("xcf")) {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
    }
    else if (paramString.startsWith("xwinglin")) {
      if (chunkDamageVisible("WingLIn") < 3)
        hitChunk("WingLIn", paramShot);
    }
    else if (paramString.startsWith("xwingrin")) {
      if (chunkDamageVisible("WingRIn") < 3)
        hitChunk("WingRIn", paramShot);
    }
    else if (paramString.startsWith("xwinglmid")) {
      if (chunkDamageVisible("WingLMid") < 3)
        hitChunk("WingLMid", paramShot);
    }
    else if (paramString.startsWith("xwingrmid")) {
      if (chunkDamageVisible("WingRMid") < 3)
        hitChunk("WingRMid", paramShot);
    }
    else if (paramString.startsWith("xwinglout")) {
      if (chunkDamageVisible("WingLOut") < 3)
        hitChunk("WingLOut", paramShot);
    }
    else if (paramString.startsWith("xwingrout")) {
      if (chunkDamageVisible("WingROut") < 3)
        hitChunk("WingROut", paramShot);
    }
    else if (paramString.startsWith("xaronel"))
      hitChunk("AroneL", paramShot);
    else if (paramString.startsWith("xaroner"))
      hitChunk("AroneR", paramShot);
    else if (!paramString.startsWith("xengine1"))
    {
      if (!paramString.startsWith("xengine2"))
      {
        if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
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
  }

  public void update(float paramFloat)
  {
    if (this.FM.AS.isMaster()) {
      for (int i = 0; i < 2; i++) {
        if (this.curctl[i] == -1.0F)
        {
          float tmp57_54 = this.FM.EI.engines[i].getControlThrottle(); this.oldctl[i] = tmp57_54; this.curctl[i] = tmp57_54;
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
    Class localClass = GO_229.class;
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
  }
}
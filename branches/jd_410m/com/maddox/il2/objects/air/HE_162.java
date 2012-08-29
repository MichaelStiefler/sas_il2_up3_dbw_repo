package com.maddox.il2.objects.air;

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
import com.maddox.rts.CLASS;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Property;

public abstract class HE_162 extends Scheme1
  implements TypeFighter
{
  private float oldctl = -1.0F;
  private float curctl = -1.0F;

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

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean) {
      if ((this.FM.AS.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.07F)) nextDMGLevel(this.FM.AS.astateEffectChunks[0] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.14F)) this.FM.AS.hitTank(this, 1, 1);
      if ((this.FM.AS.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.14F)) this.FM.AS.hitTank(this, 2, 1);
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.07F)) nextDMGLevel(this.FM.AS.astateEffectChunks[1] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.14F)) this.FM.AS.hitTank(this, 0, 1);
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.07F)) nextDMGLevel(this.FM.AS.astateEffectChunks[2] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.14F)) this.FM.AS.hitTank(this, 0, 1);
    }

    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = Math.max(-paramFloat * 1500.0F, -110.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, f, 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 115.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, 110.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 115.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, -110.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 120.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearC25_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC27_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC28_D0", 0.0F, 0.0F, 0.0F);
    float tmp160_159 = (xyz[2] = ypr[0] = ypr[1] = ypr[2] = 0.0F); xyz[1] = tmp160_159; xyz[0] = tmp160_159;
    xyz[2] = cvt(paramFloat, 0.0F, 1.0F, -0.0833F, 0.0F);
    paramHierMesh.chunkSetLocate("GearC64_D0", xyz, ypr);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveWheelSink() {
    resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.0632F, 0.0F, 0.0632F);
    if (this.FM.CT.getGear() > 0.99F) {
      ypr[1] = (40.0F * this.FM.CT.getRudder());
    }
    hierMesh().chunkSetLocate("GearC25_D0", xyz, ypr);
    hierMesh().chunkSetAngles("GearC27_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.0632F, 0.0F, -15.0F), 0.0F);
    hierMesh().chunkSetAngles("GearC28_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.0632F, 0.0F, 30.0F), 0.0F);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -50.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
  }

  protected void moveFan(float paramFloat)
  {
  }

  public void update(float paramFloat)
  {
    if (this.FM.AS.isMaster()) {
      if (this.curctl == -1.0F) {
        this.curctl = (this.oldctl = this.FM.EI.engines[0].getControlThrottle());
      } else {
        this.curctl = this.FM.EI.engines[0].getControlThrottle();
        if (((this.curctl - this.oldctl) / paramFloat > 3.0F) && (this.FM.EI.engines[0].getRPM() < 2400.0F) && (this.FM.EI.engines[0].getStage() == 6) && (World.Rnd().nextFloat() < 0.25F)) {
          this.FM.AS.hitEngine(this, 0, 100);
        }
        if (((this.curctl - this.oldctl) / paramFloat < -3.0F) && (this.FM.EI.engines[0].getRPM() < 2400.0F) && (this.FM.EI.engines[0].getStage() == 6)) {
          if ((World.Rnd().nextFloat() < 0.25F) && ((this.FM instanceof RealFlightModel)) && (((RealFlightModel)this.FM).isRealMode())) {
            this.FM.EI.engines[0].setEngineStops(this);
          }
          if ((World.Rnd().nextFloat() < 0.75F) && ((this.FM instanceof RealFlightModel)) && (((RealFlightModel)this.FM).isRealMode())) {
            this.FM.EI.engines[0].setKillCompressor(this);
          }
        }
        this.oldctl = this.curctl;
      }
    }
    if ((Config.isUSE_RENDER()) && 
      (this.FM.AS.isMaster())) {
      if ((this.FM.EI.engines[0].getPowerOutput() > 0.8F) && (this.FM.EI.engines[0].getStage() == 6)) {
        if (this.FM.EI.engines[0].getPowerOutput() > 0.95F)
          this.FM.AS.setSootState(this, 0, 3);
        else
          this.FM.AS.setSootState(this, 0, 2);
      }
      else {
        this.FM.AS.setSootState(this, 0, 0);
      }
    }

    super.update(paramFloat);
  }

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);

    if (paramShot.chunkName.startsWith("Pilot")) {
      killPilot(paramShot.initiator, 0);
    }

    if (paramShot.chunkName.startsWith("Engine")) {
      if (World.Rnd().nextFloat() < 0.05F) {
        this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, 6));
      }
      if (World.Rnd().nextFloat(0.009F, 0.1357F) < paramShot.mass) {
        this.FM.AS.hitEngine(paramShot.initiator, 0, 5);
      }
    }

    if (paramShot.chunkName.startsWith("CF")) {
      if (World.Rnd().nextFloat() < 0.1F) {
        this.FM.AS.hitTank(paramShot.initiator, 0, 1);
      }
      if (World.Rnd().nextFloat() < 0.1F) {
        if (World.Rnd().nextFloat() < 0.25F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
        }
        if (World.Rnd().nextFloat() < 0.25F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
        }
        if (World.Rnd().nextFloat() < 0.25F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
        }
        if (World.Rnd().nextFloat() < 0.25F) {
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
        }
      }
    }

    super.msgShot(paramShot);
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
  }
}
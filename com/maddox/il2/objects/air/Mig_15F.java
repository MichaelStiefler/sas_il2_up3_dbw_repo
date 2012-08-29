package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Property;
import java.util.Random;

public class Mig_15F extends Scheme1
  implements TypeFighter, TypeBNZFighter
{
  protected boolean curst;
  private boolean ts;
  private float oldctl;
  private float curctl;
  private boolean overrideBailout;
  private boolean ejectComplete;
  public static boolean bChangedPit = false;
  private float mn;
  private static float uteb = 1.25F;
  private static float lteb = 0.9F;
  private static float mteb = 1.0F;
  private boolean ictl;
  private final .0 $1;
  private float actl;
  private float rctl;
  private float ectl;

  public Mig_15F()
  {
    this.mn = 0.0F;
    this.ts = false;
    this.curst = false;
    this.oldctl = -1.0F;
    this.curctl = -1.0F;
    this.ictl = false;
    this.overrideBailout = false;
    this.ejectComplete = false;

    this.$1 = new Object(0.0F, 10000.0F, 0.65F, 1.0F, 0.05F, 1.0F, 0.4F, 1.0F, 0.29F, 0.34F, 0.8F, null)
    {
      private float lal;
      private float tal;
      private float bef;
      private float tef;
      private float bhef;
      private float thef;
      private float phef;
      private float mef;
      private float wef;
      private float lef;
      private float ftl;
      private final Mig_15F this$0;

      public void rs(int ii)
      {
        if ((ii == 0) || (ii == 1)) Mig_15F.access$234(this.this$0, 0.6800000000000001D);
        if ((ii == 31) || (ii == 32)) Mig_15F.access$334(this.this$0, 0.6800000000000001D);
        if ((ii == 15) || (ii == 16)) Mig_15F.access$434(this.this$0, 0.6800000000000001D); 
      }

      private void $1()
      {
        if (this.this$0.ts) {
          float f1 = Aircraft.cvt(this.this$0.FM.getAltitude(), this.lal, this.tal, this.bef, this.tef);
          float f2 = Aircraft.cvt(this.this$0.mn, this.this$0.mn < Mig_15F.mteb ? Mig_15F.lteb : Mig_15F.uteb, this.this$0.mn < Mig_15F.mteb ? Mig_15F.uteb : Mig_15F.lteb, this.this$0.mn < Mig_15F.mteb ? this.bhef : this.thef, this.this$0.mn < Mig_15F.mteb ? this.thef : this.phef);

          float f3 = Aircraft.cvt(this.this$0.mn, this.this$0.mn < Mig_15F.mteb ? Mig_15F.lteb : Mig_15F.uteb, this.this$0.mn < Mig_15F.mteb ? Mig_15F.uteb : Mig_15F.lteb, this.this$0.mn < Mig_15F.mteb ? this.mef : this.wef / f1, this.this$0.mn < Mig_15F.mteb ? this.wef / f1 : this.lef / f1);

          ((RealFlightModel)this.this$0.FM).producedShakeLevel += 0.1125F * f2;
          if (World.Rnd().nextFloat() > 0.76F) this.this$0.FM.SensPitch = (this.this$0.ectl * f3 * f3); else
            this.this$0.FM.SensPitch = (this.this$0.ectl * f3);
          this.this$0.FM.SensRoll = (this.this$0.actl * f3 * f3);
          this.this$0.FM.SensYaw = (this.this$0.rctl * f3);

          if (f2 > 0.6F) Mig_15F.access$1002(this.this$0, true); else {
            Mig_15F.access$1002(this.this$0, false);
          }
          if (this.ftl > 0.0F) {
            if (World.Rnd().nextFloat() > 0.6F)
              if (this.this$0.FM.CT.RudderControl > 0.0F) this.this$0.FM.CT.RudderControl -= this.ftl * f2;
              else if (this.this$0.FM.CT.RudderControl < 0.0F) this.this$0.FM.CT.RudderControl += this.ftl * f2; else
                this.this$0.FM.CT.RudderControl += (World.Rnd().nextFloat() > 0.5F ? this.ftl * f2 : -this.ftl * f2);
            if (this.this$0.FM.CT.RudderControl > 1.0F)
              this.this$0.FM.CT.RudderControl = 1.0F;
            if (this.this$0.FM.CT.RudderControl < -1.0F)
              this.this$0.FM.CT.RudderControl = -1.0F; 
          }
        } else {
          this.this$0.FM.SensPitch = this.this$0.ectl; this.this$0.FM.SensRoll = this.this$0.actl; this.this$0.FM.SensYaw = this.this$0.rctl;
        }
      }
    };
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.actl = this.FM.SensRoll;
    this.ectl = this.FM.SensPitch;
    this.rctl = this.FM.SensYaw;
    this.FM.Gears.tailStiffness = 0.4F;
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (((this.FM.Gears.nearGround()) || (this.FM.Gears.onGround())) && (this.FM.CT.getCockpitDoor() == 1.0F))
      hierMesh().chunkVisible("HMask1_D0", false);
    else {
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
    }
    if ((this.FM.AS.isMaster()) && (Config.isUSE_RENDER()))
    {
      if ((this.FM.getSpeed() > 14.0D) && 
        (this.FM.CT.getCockpitDoor() > 0.0F)) {
        this.FM.CT.cockpitDoorControl = 0.0F;
      }

    }

    if ((!this.FM.isPlayers()) && 
      ((this.FM instanceof Maneuver)))
    {
      if (((this.FM.AP.way.isLanding()) || (this.FM.AP.way.isLandingOnShip())) && (this.FM.AP.getWayPointDistance() < 2500.0F))
      {
        if (this.FM.getSpeedKMH() < this.FM.VmaxFLAPS * 0.4D)
          this.FM.CT.AirBrakeControl = 0.0F;
        else this.FM.CT.AirBrakeControl = 1.0F;
      }
      else if (((Maneuver)this.FM).get_maneuver() == 66) {
        this.FM.CT.AirBrakeControl = 0.0F;
      }
      else if (((Maneuver)this.FM).get_maneuver() == 7) {
        this.FM.CT.AirBrakeControl = 1.0F;
      }
      else
        this.FM.CT.AirBrakeControl = 0.0F;
    }
  }

  public void doEjectCatapult()
  {
    new MsgAction(false, this)
    {
      public void doAction(Object paramObject)
      {
        Aircraft localAircraft = (Aircraft)paramObject;
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

  public void doMurderPilot(int paramInt) {
    switch (paramInt)
    {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
    }
  }

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers()) bChangedPit = true; 
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers()) bChangedPit = true; 
  }

  public void moveCockpitDoor(float paramFloat)
  {
    resetYPRmodifier();
    if (paramFloat < 0.1F)
      Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.01F, 0.08F, 0.0F, 0.1F);
    else {
      Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.17F, 0.99F, 0.1F, 0.4F);
    }
    hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null)) Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      setDoorSnd(paramFloat);
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat) {
    float f = Math.max(-paramFloat * 1500.0F, -90.0F);

    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, -127.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, -95.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, -95.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", -90.0F * paramFloat, -38.0F * paramFloat, 90.0F * paramFloat);
    paramHierMesh.chunkSetAngles("GearR2_D0", 90.0F * paramFloat, -38.0F * paramFloat, -90.0F * paramFloat);

    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -115.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -115.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, f, 0.0F);
  }

  protected void moveGear(float paramFloat) {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveWheelSink()
  {
    float f = Aircraft.cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.2F, 0.0F, 1.0F);

    resetYPRmodifier();
    Aircraft.xyz[2] = (-0.2F * f);
    hierMesh().chunkSetLocate("GearC6_D0", Aircraft.xyz, Aircraft.ypr);
    hierMesh().chunkSetAngles("GearC8_D0", 0.0F, -15.0F * f, 0.0F);
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    if (this.FM.CT.GearControl > 0.5F)
      hierMesh().chunkSetAngles("GearC7_D0", 0.0F, 40.0F * paramFloat, 0.0F);
  }

  protected void moveFlap(float paramFloat) {
    float f = -45.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
  }

  protected void moveFan(float paramFloat)
  {
  }

  protected void moveAirBrake(float paramFloat)
  {
    resetYPRmodifier();
    hierMesh().chunkSetAngles("Brake01_D0", 0.0F, -70.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Brake02_D0", 0.0F, -70.0F * paramFloat, 0.0F);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int ii = part(paramString);
    this.$1.rs(ii);
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxammo")) {
        if ((World.Rnd().nextFloat(0.0F, 20000.0F) < paramShot.power) && (World.Rnd().nextFloat() < 0.25F)) {
          this.FM.AS.setJamBullets(0, World.Rnd().nextInt(0, 2));
        }
        getEnergyPastArmor(11.4F, paramShot);
        return;
      }
      if (paramString.startsWith("xxarmor")) {
        debuggunnery("Armor: Hit..");
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(13.350000381469727D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot);

          if (paramShot.power <= 0.0F)
            doRicochetBack(paramShot);
        } else if (paramString.endsWith("p2")) {
          getEnergyPastArmor(8.770001F, paramShot);
        } else if (paramString.endsWith("P3")) {
          getEnergyPastArmor(8.770001F, paramShot);
        } else if (paramString.endsWith("g1")) {
          getEnergyPastArmor(World.Rnd().nextFloat(40.0F, 60.0F) / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot);

          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);

          if (paramShot.power <= 0.0F)
            doRicochetBack(paramShot);
        }
      } else if (paramString.startsWith("xxcontrols")) {
        debuggunnery("Controls: Hit..");
        int i = paramString.charAt(10) - '0';
        switch (i)
        {
        case 1:
        case 2:
          if ((World.Rnd().nextFloat() >= 0.5F) || (getEnergyPastArmor(1.1F, paramShot) <= 0.0F))
            break;
          debuggunnery("Controls: Ailerones Controls: Out..");
          this.FM.AS.setControlsDamage(paramShot.initiator, 0); break;
        case 3:
        case 4:
          if ((getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 2.93F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F))
          {
            debuggunnery("Controls: Elevator Controls: Disabled / Strings Broken..");

            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          }
          if ((getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 2.93F), paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.25F))
            break;
          debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");

          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
        }

      }
      else if (paramString.startsWith("xxeng1")) {
        debuggunnery("Engine Module: Hit..");
        if (paramString.endsWith("bloc")) {
          getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 60.0F) / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot);
        }

        if ((paramString.endsWith("cams")) && (getEnergyPastArmor(0.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 20.0F))
        {
          this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));

          debuggunnery("Engine Module: Engine Cams Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");

          if (World.Rnd().nextFloat() < paramShot.power / 24000.0F) {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
            debuggunnery("Engine Module: Engine Cams Hit - Engine Fires..");
          }

          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.75F))
          {
            this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
            debuggunnery("Engine Module: Engine Cams Hit (2) - Engine Fires..");
          }
        }

        if ((paramString.endsWith("eqpt")) && (World.Rnd().nextFloat() < paramShot.power / 24000.0F))
        {
          this.FM.AS.hitEngine(paramShot.initiator, 0, 3);
          debuggunnery("Engine Module: Hit - Engine Fires..");
        }

        if (paramString.endsWith("exht"));
      }
      else if (paramString.startsWith("xxcannon0")) {
        int i = paramString.charAt(9) - '1';
        if (getEnergyPastArmor(1.5F, paramShot) > 0.0F) {
          debuggunnery("Armament: Cannon (" + i + ") Disabled..");

          this.FM.AS.setJamBullets(0, i);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
      }
      else if (paramString.startsWith("xxtank")) {
        int i = paramString.charAt(6) - '1';
        if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F))
        {
          if (this.FM.AS.astateTankStates[i] == 0) {
            debuggunnery("Fuel Tank (" + i + "): Pierced..");
            this.FM.AS.hitTank(paramShot.initiator, i, 1);
            this.FM.AS.doSetTankState(paramShot.initiator, i, 1);
          }
          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.075F))
          {
            this.FM.AS.hitTank(paramShot.initiator, i, 2);
            debuggunnery("Fuel Tank (" + i + "): Hit..");
          }
        }
      } else if (paramString.startsWith("xxspar")) {
        debuggunnery("Spar Construction: Hit..");
        if ((paramString.startsWith("xxsparlm")) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          debuggunnery("Spar Construction: WingLMid Spars Damaged..");

          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparrm")) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          debuggunnery("Spar Construction: WingRMid Spars Damaged..");

          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparlo")) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          debuggunnery("Spar Construction: WingLOut Spars Damaged..");

          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparro")) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
        {
          debuggunnery("Spar Construction: WingROut Spars Damaged..");

          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }
      } else if (paramString.startsWith("xxhyd")) {
        this.FM.AS.setInternalDamage(paramShot.initiator, 3);
      } else if (paramString.startsWith("xxpnm")) {
        this.FM.AS.setInternalDamage(paramShot.initiator, 1);
      }
    } else {
      if (paramString.startsWith("xblister")) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);

        getEnergyPastArmor(0.05F, paramShot);
      }
      if (paramString.startsWith("xcf")) {
        hitChunk("CF", paramShot);
      } else if (paramString.startsWith("xnose"))
      {
        hitChunk("Nose", paramShot);
      }
      else if (paramString.startsWith("xTail")) {
        if (chunkDamageVisible("Tail1") < 3)
          hitChunk("Tail1", paramShot);
      } else if (paramString.startsWith("xkeel")) {
        if (chunkDamageVisible("Keel1") < 2)
          hitChunk("Keel1", paramShot);
      } else if (paramString.startsWith("xrudder")) {
        hitChunk("Rudder1", paramShot);
      } else if (paramString.startsWith("xstab")) {
        if ((paramString.startsWith("xstabl")) && (chunkDamageVisible("StabL") < 2))
        {
          hitChunk("StabL", paramShot);
        }if ((paramString.startsWith("xstabr")) && (chunkDamageVisible("StabR") < 1))
        {
          hitChunk("StabR", paramShot);
        }
      } else if (paramString.startsWith("xvator")) {
        if (paramString.startsWith("xvatorl"))
          hitChunk("VatorL", paramShot);
        if (paramString.startsWith("xvatorr"))
          hitChunk("VatorR", paramShot);
      } else if (paramString.startsWith("xwing")) {
        if ((paramString.startsWith("xwinglin")) && (chunkDamageVisible("WingLIn") < 3))
        {
          hitChunk("WingLIn", paramShot);
        }if ((paramString.startsWith("xwingrin")) && (chunkDamageVisible("WingRIn") < 3))
        {
          hitChunk("WingRIn", paramShot);
        }if ((paramString.startsWith("xwinglmid")) && (chunkDamageVisible("WingLMid") < 3))
        {
          hitChunk("WingLMid", paramShot);
        }if ((paramString.startsWith("xwingrmid")) && (chunkDamageVisible("WingRMid") < 3))
        {
          hitChunk("WingRMid", paramShot);
        }if ((paramString.startsWith("xwinglout")) && (chunkDamageVisible("WingLOut") < 3))
        {
          hitChunk("WingLOut", paramShot);
        }if ((paramString.startsWith("xwingrout")) && (chunkDamageVisible("WingROut") < 3))
        {
          hitChunk("WingROut", paramShot);
        }
      } else if (paramString.startsWith("xarone")) {
        if (paramString.startsWith("xaronel"))
          hitChunk("AroneL", paramShot);
        if (paramString.startsWith("xaroner"))
          hitChunk("AroneR", paramShot);
      } else if (paramString.startsWith("xgear")) {
        if ((paramString.endsWith("1")) && (World.Rnd().nextFloat() < 0.05F)) {
          debuggunnery("Hydro System: Disabled..");
          this.FM.AS.setInternalDamage(paramShot.initiator, 0);
        }
        if ((paramString.endsWith("2")) && (World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 3.435F), paramShot) > 0.0F))
        {
          debuggunnery("Undercarriage: Stuck..");
          this.FM.AS.setInternalDamage(paramShot.initiator, 3);
        }
      } else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead")))
      {
        int i = 0;
        int j;
        int j;
        if (paramString.endsWith("a")) {
          i = 1;
          j = paramString.charAt(6) - '1';
        }
        else
        {
          int j;
          if (paramString.endsWith("b")) {
            i = 2;
            j = paramString.charAt(6) - '1';
          } else {
            j = paramString.charAt(5) - '1';
          }
        }hitFlesh(j, paramShot, i);
      }
    }
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor) {
    switch (paramInt1)
    {
    case 13:
      this.FM.Gears.cgear = false;
      float t = World.Rnd().nextFloat();
      if (t < 0.1F) {
        this.FM.AS.hitEngine(this, 0, 100);
        if (World.Rnd().nextFloat() >= 0.49D) break;
        this.FM.EI.engines[0].setEngineDies(paramActor);
      } else {
        if (t <= 0.55D) break; this.FM.EI.engines[0].setEngineDies(paramActor); } break;
    case 34:
      this.FM.Gears.lgear = false;
      break;
    case 37:
      this.FM.Gears.rgear = false;
      break;
    case 19:
      this.FM.CT.bHasAirBrakeControl = false;
      this.FM.EI.engines[0].setEngineDies(paramActor);
      break;
    case 11:
      this.FM.CT.bHasElevatorControl = false;
      this.FM.CT.bHasRudderControl = false;
      this.FM.CT.bHasRudderTrim = false;
      this.FM.CT.bHasElevatorTrim = false;
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void update(float paramFloat)
  {
    if (this.FM.AS.isMaster())
    {
      if ((this.FM instanceof RealFlightModel)) {
        umnr();
        this.$1.$1();
      }
      if (this.curctl == -1.0F) {
        this.curctl = (this.oldctl = this.FM.CT.getAirBrake());
      } else {
        this.curctl = this.FM.CT.getAirBrake();
        if (this.curctl > 0.05F)
        {
          if (this.curctl - this.oldctl > 0.0F)
            this.curst = true;
          else if ((this.curctl - this.oldctl == 0.0F) && (this.oldctl == 1.0F))
            this.curst = true;
          else this.curst = false; 
        }
      }
      this.oldctl = this.curctl;
    }

    if (((this.FM.AS.bIsAboutToBailout) || (this.overrideBailout)) && (!this.ejectComplete) && 
      (this.FM.getSpeedKMH() > 15.0F))
    {
      this.overrideBailout = true;
      this.FM.AS.bIsAboutToBailout = false;
      bailout();
    }
    super.update(paramFloat);
    if ((this.FM.AS.isMaster()) && (Config.isUSE_RENDER()))
    {
      if ((this.FM.EI.engines[0].getPowerOutput() > 0.5F) && (this.FM.EI.engines[0].getStage() == 6))
        this.FM.AS.setSootState(this, 0, 2);
      else this.FM.AS.setSootState(this, 0, 0);
      setExhaustFlame(Math.round(Aircraft.cvt(this.FM.EI.engines[0].getThrustOutput(), 0.7F, 0.87F, 0.0F, 12.0F)), 0);
    }
  }

  public void setExhaustFlame(int stage, int i)
  {
    if (i == 0)
      switch (stage)
      {
      case 0:
        hierMesh().chunkVisible("Exhaust1", false);
        hierMesh().chunkVisible("Exhaust2", false);
        hierMesh().chunkVisible("Exhaust3", false);
        hierMesh().chunkVisible("Exhaust4", false);
        hierMesh().chunkVisible("Exhaust5", false);
        break;
      case 1:
        hierMesh().chunkVisible("Exhaust1", true);
        hierMesh().chunkVisible("Exhaust2", false);
        hierMesh().chunkVisible("Exhaust3", false);
        hierMesh().chunkVisible("Exhaust4", false);
        hierMesh().chunkVisible("Exhaust5", false);
        break;
      case 2:
        hierMesh().chunkVisible("Exhaust1", false);
        hierMesh().chunkVisible("Exhaust2", true);
        hierMesh().chunkVisible("Exhaust3", false);
        hierMesh().chunkVisible("Exhaust4", false);
        hierMesh().chunkVisible("Exhaust5", false);
        break;
      case 3:
        hierMesh().chunkVisible("Exhaust1", true);
        hierMesh().chunkVisible("Exhaust2", true);
        hierMesh().chunkVisible("Exhaust3", false);
        hierMesh().chunkVisible("Exhaust4", false);
        hierMesh().chunkVisible("Exhaust5", false);
      case 4:
        hierMesh().chunkVisible("Exhaust1", false);
        hierMesh().chunkVisible("Exhaust2", false);
        hierMesh().chunkVisible("Exhaust3", true);
        hierMesh().chunkVisible("Exhaust4", false);
        hierMesh().chunkVisible("Exhaust5", false);
        break;
      case 5:
        hierMesh().chunkVisible("Exhaust1", true);
        hierMesh().chunkVisible("Exhaust2", false);
        hierMesh().chunkVisible("Exhaust3", true);
        hierMesh().chunkVisible("Exhaust4", false);
        hierMesh().chunkVisible("Exhaust5", false);
        break;
      case 6:
        hierMesh().chunkVisible("Exhaust1", false);
        hierMesh().chunkVisible("Exhaust2", true);
        hierMesh().chunkVisible("Exhaust3", true);
        hierMesh().chunkVisible("Exhaust4", false);
        hierMesh().chunkVisible("Exhaust5", false);
        break;
      case 7:
        hierMesh().chunkVisible("Exhaust1", true);
        hierMesh().chunkVisible("Exhaust2", false);
        hierMesh().chunkVisible("Exhaust3", false);
        hierMesh().chunkVisible("Exhaust4", true);
        hierMesh().chunkVisible("Exhaust5", false);
        break;
      case 8:
        hierMesh().chunkVisible("Exhaust1", false);
        hierMesh().chunkVisible("Exhaust2", true);
        hierMesh().chunkVisible("Exhaust3", false);
        hierMesh().chunkVisible("Exhaust4", true);
        hierMesh().chunkVisible("Exhaust5", false);
        break;
      case 9:
        hierMesh().chunkVisible("Exhaust1", false);
        hierMesh().chunkVisible("Exhaust2", false);
        hierMesh().chunkVisible("Exhaust3", true);
        hierMesh().chunkVisible("Exhaust4", true);
        hierMesh().chunkVisible("Exhaust5", false);
        break;
      case 10:
        hierMesh().chunkVisible("Exhaust1", true);
        hierMesh().chunkVisible("Exhaust2", false);
        hierMesh().chunkVisible("Exhaust3", false);
        hierMesh().chunkVisible("Exhaust4", false);
        hierMesh().chunkVisible("Exhaust5", true);
        break;
      case 11:
        hierMesh().chunkVisible("Exhaust1", false);
        hierMesh().chunkVisible("Exhaust2", true);
        hierMesh().chunkVisible("Exhaust3", false);
        hierMesh().chunkVisible("Exhaust4", false);
        hierMesh().chunkVisible("Exhaust5", true);
        break;
      case 12:
        hierMesh().chunkVisible("Exhaust1", false);
        hierMesh().chunkVisible("Exhaust2", false);
        hierMesh().chunkVisible("Exhaust3", true);
        hierMesh().chunkVisible("Exhaust4", false);
        hierMesh().chunkVisible("Exhaust5", true);

        break;
      default:
        hierMesh().chunkVisible("Exhaust1", false);
        hierMesh().chunkVisible("Exhaust2", false);
        hierMesh().chunkVisible("Exhaust3", false);
        hierMesh().chunkVisible("Exhaust4", false);
        hierMesh().chunkVisible("Exhaust5", false);
      }
  }

  private void bailout()
  {
    if (this.overrideBailout)
      if ((this.FM.AS.astateBailoutStep >= 0) && (this.FM.AS.astateBailoutStep < 2))
      {
        if ((this.FM.CT.cockpitDoorControl > 0.5F) && (this.FM.CT.getCockpitDoor() > 0.5F))
        {
          this.FM.AS.astateBailoutStep = 11;
          doRemoveBlisters(); } else {
          this.FM.AS.astateBailoutStep = 2;
        }
      } else if ((this.FM.AS.astateBailoutStep >= 2) && (this.FM.AS.astateBailoutStep <= 3))
      {
        switch (this.FM.AS.astateBailoutStep)
        {
        case 2:
          if (this.FM.CT.cockpitDoorControl >= 0.5F) break;
          doRemoveBlister1(); break;
        case 3:
          doRemoveBlisters();
        }
        if (this.FM.AS.isMaster())
          this.FM.AS.netToMirrors(20, this.FM.AS.astateBailoutStep, 1, null);
        AircraftState tmp178_177 = this.FM.AS; tmp178_177.astateBailoutStep = (byte)(tmp178_177.astateBailoutStep + 1);
        if (this.FM.AS.astateBailoutStep == 4) {
          this.FM.AS.astateBailoutStep = 11;
        }
      }
      else if ((this.FM.AS.astateBailoutStep >= 11) && (this.FM.AS.astateBailoutStep <= 19))
      {
        int i = this.FM.AS.astateBailoutStep;
        if (this.FM.AS.isMaster())
          this.FM.AS.netToMirrors(20, this.FM.AS.astateBailoutStep, 1, null);
        AircraftState tmp383_382 = this.FM.AS; tmp383_382.astateBailoutStep = (byte)(tmp383_382.astateBailoutStep + 1);
        if (i == 11) {
          this.FM.setTakenMortalDamage(true, null);
          if (((this.FM instanceof Maneuver)) && (((Maneuver)this.FM).get_maneuver() != 44)) {
            World.cur(); if (this.FM.AS.actor != World.getPlayerAircraft())
            {
              ((Maneuver)this.FM).set_maneuver(44);
            }
          }
        }
        if (this.FM.AS.astatePilotStates[(i - 11)] < 99)
        {
          doRemoveBodyFromPlane(i - 10);
          if (i == 11)
          {
            doEjectCatapult();

            this.FM.setTakenMortalDamage(true, null);
            this.FM.CT.WeaponControl[0] = false;

            this.FM.AS.astateBailoutStep = -1;
            this.overrideBailout = false;
            this.FM.AS.bIsAboutToBailout = true;
            this.ejectComplete = true;
            return;
          }
        }
      }
  }

  private final void doRemoveBlister1()
  {
    if ((hierMesh().chunkFindCheck("Blister1_D0") != -1) && (this.FM.AS.getPilotHealth(0) > 0.0F)) {
      hierMesh().hideSubTrees("Blister1_D0");
      Wreckage localWreckage = new Wreckage(this, hierMesh().chunkFind("Blister1_D0"));
      localWreckage.collide(false);
      Vector3d localVector3d = new Vector3d();
      localVector3d.set(this.FM.Vwld); localWreckage.setSpeed(localVector3d);
    }
  }
  private final void doRemoveBlisters() {
    for (int i = 2; i < 10; i++)
      if ((hierMesh().chunkFindCheck("Blister" + i + "_D0") != -1) && (this.FM.AS.getPilotHealth(i - 1) > 0.0F)) {
        hierMesh().hideSubTrees("Blister" + i + "_D0");
        Wreckage localWreckage = new Wreckage(this, hierMesh().chunkFind("Blister" + i + "_D0"));
        localWreckage.collide(false);
        Vector3d localVector3d = new Vector3d();
        localVector3d.set(this.FM.Vwld); localWreckage.setSpeed(localVector3d);
      }
  }

  public boolean ist()
  {
    return this.ts;
  }

  public float gmnr()
  {
    return this.mn;
  }

  public boolean inr()
  {
    return this.ictl;
  }

  private final void umnr() {
    Vector3d vf1 = this.FM.getVflow();
    this.mn = (float)vf1.lengthSquared();
    this.mn = (float)Math.sqrt(this.mn);
    this.mn /= Atmosphere.sonicSpeed((float)this.FM.Loc.z);
    if (this.mn >= lteb)
      this.ts = true;
    else this.ts = false;
  }

  static
  {
    Class localClass = Mig_15F.class;

    Property.set(localClass, "originCountry", PaintScheme.countryRussia);
  }
}
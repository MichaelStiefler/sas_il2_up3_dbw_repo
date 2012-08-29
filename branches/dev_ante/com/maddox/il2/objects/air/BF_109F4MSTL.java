package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Finger;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class BF_109F4MSTL extends BF_109
  implements TypeDockable
{
  private float kangle;
  private boolean bNeedSetup;
  private long dtime;
  private Actor target_;
  private Actor queen_;
  private int dockport_;

  public BF_109F4MSTL()
  {
    this.kangle = 0.0F;
    this.bNeedSetup = true;
    this.dtime = -1L;
    this.target_ = null;
    this.queen_ = null;
  }

  protected void moveFan(float paramFloat)
  {
    int i = 0;
    for (int j = 0; j < 1; j++)
    {
      if (this.jdField_oldProp_of_type_ArrayOfInt[j] < 2)
      {
        i = Math.abs((int)(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getw() * 0.12F * 1.5F));
        if (i >= 1)
          i = 1;
        if (i != this.jdField_oldProp_of_type_ArrayOfInt[j])
        {
          hierMesh().chunkVisible(Aircraft.Props[j][this.jdField_oldProp_of_type_ArrayOfInt[j]], false);
          this.jdField_oldProp_of_type_ArrayOfInt[j] = i;
          hierMesh().chunkVisible(Aircraft.Props[j][i], true);
        }
      }
      if (i == 0)
      {
        this.jdField_propPos_of_type_ArrayOfFloat[j] = ((this.jdField_propPos_of_type_ArrayOfFloat[j] + 57.299999F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getw() * paramFloat) % 360.0F);
      }
      else {
        float f = 57.299999F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getw();
        f %= 2880.0F;
        f /= 2880.0F;
        if (f <= 0.5F)
          f *= 2.0F;
        else
          f = f * 2.0F - 2.0F;
        f *= 1200.0F;
        this.jdField_propPos_of_type_ArrayOfFloat[j] = ((this.jdField_propPos_of_type_ArrayOfFloat[j] + f * paramFloat) % 360.0F);
      }
      hierMesh().chunkSetAngles(Aircraft.Props[j][0], 0.0F, -this.jdField_propPos_of_type_ArrayOfFloat[j], 0.0F);
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f1 = 0.8F;
    float f2 = -0.5F * (float)Math.cos(paramFloat / f1 * 3.141592653589793D) + 0.5F;
    if ((paramFloat <= f1) || (paramFloat == 1.0F))
    {
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f2, 0.0F);
      paramHierMesh.chunkSetAngles("GearL2_D0", -33.5F * f2, 0.0F, 0.0F);
    }
    f2 = -0.5F * (float)Math.cos((paramFloat - (1.0F - f1)) / f1 * 3.141592653589793D) + 0.5F;
    if (paramFloat >= 1.0F - f1)
    {
      paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F * f2, 0.0F);
      paramHierMesh.chunkSetAngles("GearR2_D0", 33.5F * f2, 0.0F, 0.0F);
    }
    paramHierMesh.chunkSetAngles("GearC3_D0", 70.0F * paramFloat, 0.0F, 0.0F);
    if (paramFloat > 0.99F)
    {
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F, 0.0F);
      paramHierMesh.chunkSetAngles("GearL2_D0", -33.5F, 0.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR2_D0", 33.5F, 0.0F, 0.0F);
    }
    if (paramFloat < 0.01F)
    {
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 0.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 0.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 0.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 0.0F, 0.0F);
    }
  }

  protected void moveGear(float paramFloat)
  {
    if (typeDockableIsDocked())
      moveGear(hierMesh(), 0.0F);
    else
      moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat)
  {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() >= 0.98F)
      hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
  }

  public void update(float paramFloat)
  {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed() > 5.0F)
    {
      hierMesh().chunkSetAngles("SlatL_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAOA(), 6.8F, 11.0F, 0.0F, 1.5F), 0.0F);
      hierMesh().chunkSetAngles("SlatR_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAOA(), 6.8F, 11.0F, 0.0F, 1.5F), 0.0F);
    }
    hierMesh().chunkSetAngles("Flap01L_D0", 0.0F, -16.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap01U_D0", 0.0F, 16.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap02L_D0", 0.0F, -16.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap02U_D0", 0.0F, 16.0F * this.kangle, 0.0F);
    this.kangle = (0.95F * this.kangle + 0.05F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator());
    if (this.kangle > 1.0F)
      this.kangle = 1.0F;
    super.update(paramFloat);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      if (!Main3D.cur3D().isViewOutside())
        hierMesh().chunkVisible("CF_D0", false);
      else
        hierMesh().chunkVisible("CF_D0", true);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
    {
      if (!Main3D.cur3D().isViewOutside())
        hierMesh().chunkVisible("CF_D1", false);
      hierMesh().chunkVisible("CF_D2", false);
      hierMesh().chunkVisible("CF_D3", false);
    }
    Object localObject;
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
    {
      if (!Main3D.cur3D().isViewOutside())
        hierMesh().chunkVisible("Blister1_D0", false);
      else
        hierMesh().chunkVisible("Blister1_D0", true);
      localObject = World.getPlayerAircraft().pos.getAbsPoint();
      if (((Point3d)localObject).z - World.land().HQ(((Point3d)localObject).x, ((Point3d)localObject).y) < 0.009999999776482582D)
        hierMesh().chunkVisible("CF_D0", true);
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsAboutToBailout)
        hierMesh().chunkVisible("Blister1_D0", false);
    }
    if (this.bNeedSetup)
      checkAsDrone();
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Maneuver))
      if (typeDockableIsDocked())
      {
        if ((!(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof RealFlightModel)) || (!((RealFlightModel)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).isRealMode()))
        {
          ((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).set_maneuver(48);
          ((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.setCur(((Aircraft)this.queen_).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur());
          ((Pilot)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).setDumbTime(3000L);
        }
      }
      else if ((!(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof RealFlightModel)) || (!((RealFlightModel)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).isRealMode()))
        if (this.dtime > 0L)
        {
          ((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).set_maneuver(22);
          ((Pilot)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).setDumbTime(3000L);
          if (Time.current() > this.dtime + 3000L)
          {
            this.dtime = -1L;
            ((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).clear_stack();
            ((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).pop();
            ((Pilot)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).setDumbTime(0L);
          }
        }
        else if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action == 3) && (((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).get_maneuver() == 24))
        {
          ((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).set_maneuver(21);
          ((Pilot)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).setDumbTime(30000L);
        }
    if (typeDockableIsDocked())
    {
      localObject = (Aircraft)typeDockableGetQueen();
      ((Aircraft)localObject).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl;
      ((Aircraft)localObject).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl;
      ((Aircraft)localObject).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl;
      ((Aircraft)localObject).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl;
    }
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.saveWeaponControl[3] != 0)
      typeDockableAttemptDetach();
    super.update(paramFloat);
  }

  public void rareAction(float paramFloat, boolean paramBoolean) {
    super.rareAction(paramFloat, paramBoolean);
    if (!paramBoolean);
  }

  public void onAircraftLoaded() {
    super.onAircraftLoaded();
    if ((Mission.isCoop()) && (!Mission.isServer()) && (!isSpawnFromMission()) && (this.net.isMaster()))
      new MsgAction(64, 0.0D, this)
      {
        public void doAction()
        {
          BF_109F4MSTL.this.onCoopMasterSpawned();
        }
      };
  }

  private void onCoopMasterSpawned()
  {
    Actor localActor = null;
    Object localObject = null;
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getTargetName() == null)
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
    String str1 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getTargetName();
    if (str1 != null)
      localActor = Actor.getByName(str1);
    if ((Actor.isValid(localActor)) && ((localActor instanceof Wing)) && (localActor.getOwnerAttachedCount() > 0))
      localActor = (Actor)localActor.getOwnerAttached(0);
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.setCur(0);
    if ((Actor.isValid(localActor)) && ((localActor instanceof JU_88MSTL)))
      try
      {
        Aircraft localAircraft = (Aircraft)localActor;
        float f = 100.0F;
        if (localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.maxFuel > 0.0F)
          f = localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel / localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.maxFuel * 100.0F;
        String str2 = "spawn " + localActor.getClass().getName() + " NAME net" + localActor.name() + " FUEL " + f + " WEAPONS " + localAircraft.thisWeaponsName + (localAircraft.bPaintShemeNumberOn ? "" : " NUMBEROFF") + " OVR";
        CmdEnv.top().exec(str2);
      }
      catch (Exception localException)
      {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
      }
  }

  public void missionStarting()
  {
    checkAsDrone();
  }

  private void checkAsDrone()
  {
    if (this.target_ == null)
    {
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getTargetActorRandom() == null)
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getTargetActorRandom();
      this.target_ = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getTargetActorRandom();
      if (Actor.isValid(this.target_))
        this.target_ = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getTargetActorRandom();
    }
    if ((Actor.isValid(this.target_)) && ((this.target_ instanceof JU_88MSTL)) && (isNetMaster()))
      ((TypeDockable)this.target_).typeDockableRequestAttach(this, 0, true);
    this.bNeedSetup = false;
    this.target_ = null;
  }

  public int typeDockableGetDockport()
  {
    if (typeDockableIsDocked()) {
      return this.dockport_;
    }
    return -1;
  }

  public Actor typeDockableGetQueen()
  {
    return this.queen_;
  }

  public boolean typeDockableIsDocked()
  {
    return Actor.isValid(this.queen_);
  }

  public void typeDockableAttemptAttach()
  {
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isMaster()) && (!typeDockableIsDocked()))
    {
      Aircraft localAircraft = War.getNearestFriend(this);
      if ((localAircraft instanceof JU_88MSTL))
        ((TypeDockable)localAircraft).typeDockableRequestAttach(this);
    }
  }

  public void typeDockableAttemptDetach()
  {
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isMaster()) && (typeDockableIsDocked()) && (Actor.isValid(this.queen_)))
      ((TypeDockable)this.queen_).typeDockableRequestDetach(this);
  }

  public void typeDockableRequestAttach(Actor paramActor)
  {
  }

  public void typeDockableRequestDetach(Actor paramActor)
  {
  }

  public void typeDockableRequestAttach(Actor paramActor, int paramInt, boolean paramBoolean)
  {
  }

  public void typeDockableRequestDetach(Actor paramActor, int paramInt, boolean paramBoolean)
  {
  }

  public void typeDockableDoAttachToDrone(Actor paramActor, int paramInt)
  {
  }

  public void typeDockableDoDetachFromDrone(int paramInt)
  {
  }

  public void typeDockableDoAttachToQueen(Actor paramActor, int paramInt)
  {
    this.queen_ = paramActor;
    this.dockport_ = paramInt;
    Object localObject2;
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum() == 1)
    {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Scheme_of_type_Int = 2;
      localObject1 = (Aircraft)paramActor;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setNum(3);
      localObject2 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0];
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines = new Motor[3];
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0] = localObject2;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1] = localObject1.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0];
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[2] = localObject1.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1];
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.bCurControl = new boolean[] { true, true, true };

      localObject1.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.bCurControl[0] = false;
      localObject1.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.bCurControl[1] = false;
    }
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setEngineRunning();
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.setGearAirborne();
    moveGear(0.0F);
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl = ((Aircraft)paramActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl;
    Object localObject1 = ((Aircraft)this.queen_).jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
    if (((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Maneuver)) && ((localObject1 instanceof Maneuver)))
    {
      localObject2 = (Maneuver)localObject1;
      Maneuver localManeuver = (Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      if ((((Maneuver)localObject2).Group != null) && (localManeuver.Group != null) && (localManeuver.Group.numInGroup(this) == localManeuver.Group.nOfAirc - 1))
      {
        AirGroup localAirGroup = new AirGroup(localManeuver.Group);
        localManeuver.Group.delAircraft(this);
        localAirGroup.addAircraft(this);
        localAirGroup.attachGroup(((Maneuver)localObject2).Group);
        localAirGroup.rejoinGroup = null;
      }
    }
  }

  public void typeDockableDoDetachFromQueen(int paramInt)
  {
    if (this.dockport_ == paramInt)
    {
      this.queen_ = null;
      this.dockport_ = 0;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.setTrimElevatorControl(0.51F);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.trimElevator = 0.51F;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.setGearAirborne();
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum() == 3)
      {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Scheme_of_type_Int = 1;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setNum(1);
        Motor localMotor = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0];
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines = new Motor[1];
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0] = localMotor;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.bCurControl = new boolean[] { true };

        for (int i = 1; i < 3; i++)
        {
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.clpEngineEff[i][0] != null)
          {
            Eff3DActor.finish(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.clpEngineEff[i][0]);
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.clpEngineEff[i][0] = null;
          }
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.clpEngineEff[i][1] == null)
            continue;
          Eff3DActor.finish(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.clpEngineEff[i][1]);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.clpEngineEff[i][1] = null;
        }
      }
    }
  }

  public void typeDockableReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
    if (typeDockableIsDocked())
    {
      paramNetMsgGuaranted.writeByte(1);
      ActorNet localActorNet = null;
      if (Actor.isValid(this.queen_))
      {
        localActorNet = this.queen_.net;
        if (localActorNet.countNoMirrors() > 0)
          localActorNet = null;
      }
      paramNetMsgGuaranted.writeByte(this.dockport_);
      paramNetMsgGuaranted.writeNetObj(localActorNet);
    }
    else {
      paramNetMsgGuaranted.writeByte(0);
    }
  }

  public void typeDockableReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException
  {
    if (paramNetMsgInput.readByte() == 1)
    {
      this.dockport_ = paramNetMsgInput.readByte();
      NetObj localNetObj = paramNetMsgInput.readNetObj();
      if (localNetObj != null)
      {
        Actor localActor = (Actor)localNetObj.superObj();
        ((TypeDockable)localActor).typeDockableDoAttachToDrone(this, this.dockport_);
      }
    }
  }

  static
  {
    Class localClass = BF_109F4MSTL.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Bf109");
    Property.set(localClass, "meshName", "3DO/Plane/Bf-109F-4/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar03());
    Property.set(localClass, "meshName_sk", "3DO/Plane/Bf-109F-4(sk)/hier.him");
    Property.set(localClass, "PaintScheme_sk", new PaintSchemeFMPar03());
    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1944.5F);
    Property.set(localClass, "FlightModel", "FlightModels/Bf-109F-4.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitBF_109F2.class });

    Property.set(localClass, "LOSElevation", 0.74205F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 3;
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      String str = "default";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG17si", 500);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17si", 500);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 200);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}
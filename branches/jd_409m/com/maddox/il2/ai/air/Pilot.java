package com.maddox.il2.ai.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.ai.ground.TgtShip;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.KI_46_OTSUHEI;
import com.maddox.il2.objects.air.ME_163B1A;
import com.maddox.il2.objects.air.NetAircraft.Mirror;
import com.maddox.il2.objects.air.TypeBomber;
import com.maddox.il2.objects.air.TypeDiveBomber;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.air.TypeGlider;
import com.maddox.il2.objects.air.TypeStormovik;
import com.maddox.il2.objects.air.TypeTransport;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.weapons.BombGunPara;
import com.maddox.il2.objects.weapons.TorpedoGun;
import com.maddox.rts.Time;

public class Pilot extends Maneuver
{
  public static final int GTARGET_ALL = 0;
  public static final int GTARGET_TANKS = 1;
  public static final int GTARGET_FLAK = 2;
  public static final int GTARGET_VEHICLES = 3;
  public static final int GTARGET_TRAIN = 4;
  public static final int GTARGET_BRIDGE = 5;
  public static final int GTARGET_SHIPS = 6;
  public static final int ATARGET_FIGHTERS = 7;
  public static final int ATARGET_BOMBERS = 8;
  public static final int ATARGET_ALL = 9;
  public static final int TARGET_FIGHTERS = 7;
  public static final int TARGET_BOMBERS = 8;
  public static final int TARGET_ALL = 9;
  private int airTargetType = 9;
  private int groundTargetType = 0;

  private long dumbOffTime = 0L;

  private int oldTask = 3;
  private FlightModel oldTaskObject = null;
  private Actor oldGTarget = null;
  private boolean continueManeuver = false;

  private static final Vector3d MainLook = new Vector3d(0.34202014D, 0.0D, 0.9396926D);

  private static Vector3d VDanger = new Vector3d();
  private static Vector3d OnMe = new Vector3d();
  private static Vector3d diffV = new Vector3d();
  private static double diffVLength = 0.0D;
  private static Vector3f tmpV = new Vector3f();
  private static Point3d p1 = new Point3d(); private static Point3d p2 = new Point3d();
  private static Point3f p1f = new Point3f(); private static Point3f p2f = new Point3f();
  private boolean Visible;
  private boolean Near;
  private boolean OnBack;
  private boolean Looks;
  private boolean Higher;
  private boolean Faster;
  private boolean Energed = false;
  private float dist = 0.0F; private float dE = 0.0F;
  private Actor act;
  private Actor actg;

  public void targetAll()
  {
    this.airTargetType = 9; } 
  public void targetFighters() { this.airTargetType = 7; } 
  public void targetBombers() { this.airTargetType = 8; } 
  public void attackGround(int paramInt) { this.groundTargetType = paramInt; }

  public Pilot(String paramString) {
    super(paramString);
  }

  private boolean killed(Actor paramActor) {
    if (paramActor == null) return true;
    if ((paramActor instanceof BridgeSegment)) paramActor = paramActor.getOwner();
    if (Actor.isValid(paramActor)) return !paramActor.isAlive();
    return true;
  }

  public boolean killed(FlightModel paramFlightModel) {
    if (paramFlightModel == null) return true;
    if (paramFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astatePilotStates[0] == 100) return true;
    if (Actor.isValid(paramFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor)) return killed(paramFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor);
    return paramFlightModel.isTakenMortalDamage();
  }

  private boolean detectable(Actor paramActor) {
    if (!(paramActor instanceof Aircraft)) return false;
    if (this.jdField_Skill_of_type_Int >= 2) return true;
    VDanger.set(((Aircraft)paramActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    VDanger.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    OnMe.scale(-1.0D, VDanger);
    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(VDanger);
    return VDanger.jdField_x_of_type_Double >= 0.0D;
  }

  public boolean isDumb() {
    return Time.current() < this.dumbOffTime;
  }
  public void setDumbTime(long paramLong) {
    this.dumbOffTime = (Time.current() + paramLong);
  }
  public void addDumbTime(long paramLong) {
    if (isDumb())
      this.dumbOffTime += paramLong;
    else
      setDumbTime(paramLong);
  }

  public void super_update(float paramFloat)
  {
    super.update(paramFloat);
  }

  public void update(float paramFloat)
  {
    if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor.net != null) && (this.jdField_actor_of_type_ComMaddoxIl2EngineActor.net.isMirror())) {
      ((NetAircraft.Mirror)this.jdField_actor_of_type_ComMaddoxIl2EngineActor.net).fmUpdate(paramFloat);
      return;
    }

    moveCarrier();

    if (this.TaxiMode) {
      World.cur().airdrome.update(this, paramFloat);
      return;
    }

    if ((isTick(8, 0)) || (get_maneuver() == 0)) {
      setPriorities();
      setTaskAndManeuver();
    }
    super.update(paramFloat);
  }

  protected void setPriorities()
  {
    if (killed(this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel)) this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel = null;
    if (killed(this.jdField_target_of_type_ComMaddoxIl2FmFlightModel)) this.jdField_target_of_type_ComMaddoxIl2FmFlightModel = null;
    if (killed(this.jdField_airClient_of_type_ComMaddoxIl2FmFlightModel)) this.jdField_airClient_of_type_ComMaddoxIl2FmFlightModel = null;
    if (killed(this.jdField_target_ground_of_type_ComMaddoxIl2EngineActor)) this.jdField_target_ground_of_type_ComMaddoxIl2EngineActor = null;

    setBusy(false);

    if (this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isPilotDead(0)) {
      setBusy(true);
      set_maneuver(44);
      if (this.crew > 1) ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).hitDaSilk();
      set_task(0);
      return;
    }
    if (get_maneuver() == 46) {
      setBusy(true);
      this.jdField_dontSwitch_of_type_Boolean = true;
      return;
    }

    float f1 = 0.0F;
    int i = this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum();
    int j;
    if (i != 0) {
      for (j = 0; j < i; j++) {
        f1 += this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getReadyness() / i;
      }
      if (f1 < 0.7F) setReadyToReturn(true);
      if (f1 < 0.3F) setReadyToDie(true);

    }

    if (this.jdField_M_of_type_ComMaddoxIl2FmMass.fuel < 0.3F * this.jdField_M_of_type_ComMaddoxIl2FmMass.maxFuel) {
      j = this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur();
      this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.last();
      float f2 = this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.getWayPointDistance();
      this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.setCur(j);
      if (this.jdField_M_of_type_ComMaddoxIl2FmMass.maxFuel < 0.001F) this.jdField_M_of_type_ComMaddoxIl2FmMass.maxFuel = 0.001F;
      float f3 = 1000.0F * this.Range * this.jdField_M_of_type_ComMaddoxIl2FmMass.fuel / this.jdField_M_of_type_ComMaddoxIl2FmMass.maxFuel;
      if ((f3 < 2.0F * f2) && (!(this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeGlider))) setReadyToReturn(true);
    }
    if ((this.jdField_M_of_type_ComMaddoxIl2FmMass.fuel < 0.01F) && (!(this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeGlider))) setReadyToDie(true);

    if ((isTakenMortalDamage()) || (!isCapableOfBMP())) {
      setBusy(true);
      ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).hitDaSilk();
      set_task(0);
      if (this.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != null) this.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.delAircraft((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
    }

    if (isReadyToDie()) {
      this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.setCur(1);
      this.jdField_bombsOut_of_type_Boolean = true;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.dropFuelTanks();
      set_task(0);
      if ((get_maneuver() != 49) && (get_maneuver() != 12) && (get_maneuver() != 54))
      {
        clear_stack();
        set_maneuver(49);
      }
      setBusy(true);
      return;
    }

    if ((get_maneuver() == 44) || (get_maneuver() == 25) || (get_maneuver() == 49) || (get_maneuver() == 26) || (get_maneuver() == 64) || (get_maneuver() == 2) || (get_maneuver() == 57) || (get_maneuver() == 60) || (get_maneuver() == 61))
    {
      setBusy(true);
      this.jdField_dontSwitch_of_type_Boolean = true;
      return;
    }
    Maneuver localManeuver;
    if ((getDangerAggressiveness() > 0.88F) && 
      (this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel != null) && (((this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeFighter)) || ((this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeStormovik))) && (((Maneuver)this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel).isOk()))
    {
      if (get_task() != 4) {
        set_task(4);
        clear_stack();
        set_maneuver(0);
        if (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeStormovik)) && (this.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != null)) {
          j = this.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.numInGroup((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
          if (this.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.nOfAirc >= j + 2) {
            localManeuver = (Maneuver)this.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.airc[(j + 1)].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
            Voice.speakCheckYour6((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor, (Aircraft)this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor);
            Voice.speakHelpFromAir((Aircraft)localManeuver.jdField_actor_of_type_ComMaddoxIl2EngineActor, 1);
            localManeuver.jdField_airClient_of_type_ComMaddoxIl2FmFlightModel = this;
            set_task(6);
            clear_stack();
            localManeuver.jdField_target_of_type_ComMaddoxIl2FmFlightModel = this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel;
            set_maneuver(27);
            setBusy(true);
            return;
          }
        }

      }

      Voice.speakClearTail((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
      setBusy(true);
      return;
    }

    if ((isReadyToReturn()) && (!this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLanding())) {
      if ((this.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != null) && (this.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.grTask != 1)) {
        AirGroup localAirGroup = new AirGroup(this.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup);
        localAirGroup.rejoinGroup = null;
        this.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.delAircraft((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
        localAirGroup.addAircraft((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
        localAirGroup.w.last();
        localAirGroup.w.prev();
        localAirGroup.w.curr().setTimeout(3);
        localAirGroup.timeOutForTaskSwitch = 10000;
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.last();
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.prev();
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getP(p1f);
        p1f.z = (-10.0F + (float)this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double);
      }
      this.jdField_bombsOut_of_type_Boolean = true;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.dropFuelTanks();
      return;
    }

    if (get_task() == 6)
    {
      if ((this.jdField_target_of_type_ComMaddoxIl2FmFlightModel != null) && (this.jdField_airClient_of_type_ComMaddoxIl2FmFlightModel != null) && (this.jdField_target_of_type_ComMaddoxIl2FmFlightModel == ((Maneuver)this.jdField_airClient_of_type_ComMaddoxIl2FmFlightModel).jdField_danger_of_type_ComMaddoxIl2FmFlightModel)) {
        if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeStormovik)) {
          if (((Maneuver)this.jdField_airClient_of_type_ComMaddoxIl2FmFlightModel).getDangerAggressiveness() > 0.0F) {
            setBusy(true);
            return;
          }
          this.jdField_airClient_of_type_ComMaddoxIl2FmFlightModel = null;
        }

        setBusy(true);
        return;
      }

      if (((((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).aircIndex() & 0x1) == 0) && (this.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != null)) {
        int k = this.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.numInGroup((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
        if ((this.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.nOfAirc >= k + 2) && ((this.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.airc[(k + 1)].aircIndex() & 0x1) != 0)) {
          localManeuver = (Maneuver)this.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.airc[(k + 1)].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
          if ((localManeuver.jdField_airClient_of_type_ComMaddoxIl2FmFlightModel == this) && (localManeuver.getDangerAggressiveness() > 0.5F) && (localManeuver.jdField_danger_of_type_ComMaddoxIl2FmFlightModel != null) && (((Maneuver)localManeuver.jdField_danger_of_type_ComMaddoxIl2FmFlightModel).isOk()))
          {
            Voice.speakCheckYour6((Aircraft)localManeuver.jdField_actor_of_type_ComMaddoxIl2EngineActor, (Aircraft)localManeuver.jdField_danger_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor);
            Voice.speakHelpFromAir((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 1);
            set_task(6);
            clear_stack();
            this.jdField_target_of_type_ComMaddoxIl2FmFlightModel = localManeuver.jdField_danger_of_type_ComMaddoxIl2FmFlightModel;
            set_maneuver(27);
            setBusy(true);
            return;
          }
        }

      }

      if ((this.jdField_target_of_type_ComMaddoxIl2FmFlightModel != null) && (((Maneuver)this.jdField_target_of_type_ComMaddoxIl2FmFlightModel).getDangerAggressiveness() > 0.5F) && (((Maneuver)this.jdField_target_of_type_ComMaddoxIl2FmFlightModel).jdField_danger_of_type_ComMaddoxIl2FmFlightModel == this) && (((Maneuver)this.jdField_target_of_type_ComMaddoxIl2FmFlightModel).isOk()))
      {
        setBusy(true);
        return;
      }
    }

    if (isDumb()) {
      setBusy(true);
      return;
    }

    if ((get_task() == 7) && (this.jdField_target_ground_of_type_ComMaddoxIl2EngineActor != null) && (get_maneuver() != 0)) {
      setBusy(true);
      return;
    }

    if ((get_task() == 3) && (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLanding())) {
      setBusy(true);
      return;
    }
  }

  private void setTaskAndManeuver()
  {
    if (this.jdField_dontSwitch_of_type_Boolean) { this.jdField_dontSwitch_of_type_Boolean = false; return; }
    if (!isBusy()) {
      if (((this.wasBusy) || (get_maneuver() == 0)) && (this.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != null)) {
        clear_stack();
        this.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setTaskAndManeuver(this.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.numInGroup((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor));
      }
    }
    else if (get_maneuver() == 0) {
      clear_stack();
      setManeuverByTask();
    }
  }

  public void setManeuverByTask()
  {
    clear_stack();
    int k;
    float f1;
    int j;
    switch (get_task())
    {
    case 2:
      if ((this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel != null) && (Actor.isValid(this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor)) && (!this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.isReadyToReturn()) && (!this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.isReadyToDie()) && (this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.getSpeed() > 35.0F))
      {
        set_maneuver(24);
      }
      else
        set_task(3);
      break;
    case 3:
      set_maneuver(21);

      if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLanding()) break;
      wingman(true);
      if (this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel != null) {
        break;
      }
      k = 0;
      int i;
      if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur() < this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.size() - 1) {
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
        i = this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action;
        k = this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getTarget() != null ? 1 : 0;
        f1 = this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.getWayPointDistance();
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.prev();
      }
      else {
        i = this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action;
        f1 = this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.getWayPointDistance();
      }
      Pilot localPilot = (Pilot)((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      float f2 = this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.getWayPointDistance();

      while (localPilot.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel != null) {
        localPilot = (Pilot)localPilot.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel;
        localPilot.wingman(true);
        localPilot.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.setCur(this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur());
        if ((!localPilot.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLanding()) && (localPilot.get_task() == 3))
          localPilot.set_task(2);
      }
      if ((((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).aircIndex() != 0) || (this.jdField_Speak5minutes_of_type_Int != 0) || (i != 3) || (f1 >= 30000.0D)) break;
      Voice.speak5minutes((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
      this.jdField_Speak5minutes_of_type_Int = 1; break;
    case 4:
      if (get_maneuver() == 0) set_maneuver(21);
      if (this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel == null) {
        set_task(3);
      }
      else {
        if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeFighter)) {
          this.jdField_bombsOut_of_type_Boolean = true;
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.dropFuelTanks();
        }

        this.dist = (float)this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.distance(this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        VDanger.sub(this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        OnMe.scale(-1.0D, VDanger);
        Maneuver.tmpOr.setYPR(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getYaw(), 0.0F, 0.0F);
        Maneuver.tmpOr.transformInv(VDanger);
        diffV.sub(this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel.Vwld, this.Vwld);
        Maneuver.tmpOr.transformInv(diffV);
        diffVLength = diffV.length();

        Maneuver.tmpOr.setYPR(this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getYaw(), 0.0F, 0.0F);
        this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(OnMe);

        VDanger.normalize();
        OnMe.normalize();

        this.dE = ((this.jdField_Energy_of_type_Float - this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel.jdField_Energy_of_type_Float) * 0.1019F);

        this.Energed = (this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel.jdField_Energy_of_type_Float > this.jdField_Energy_of_type_Float);
        this.Faster = (this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel.getSpeed() > getSpeed());
        this.Higher = (this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double > this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double);
        this.Near = (this.dist < 300.0F);
        this.OnBack = ((VDanger.jdField_x_of_type_Double < 0.0D) && (this.dist < 2000.0F));
        this.Visible = (VDanger.dot(MainLook) > 0.0D);
        this.Looks = (OnMe.jdField_x_of_type_Double > 0.0D);
        VDanger.normalize();

        if ((this.OnBack) && (this.Near) && ((this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel instanceof TypeFighter)) && (
          ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeTransport)) || (this.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel == null) || (killed(this.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel)) || (((Pilot)this.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel).jdField_target_of_type_ComMaddoxIl2FmFlightModel != this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel)))
        {
          Aircraft localAircraft;
          if (isLeader()) {
            if (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeFighter)) || (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeStormovik)) && (this.jdField_Skill_of_type_Int > 1) && (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action == 0)))
            {
              if ((this.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel != null) && (!killed(this.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel)) && 
                (!((Pilot)this.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel).requestCoverFor(this))) {
                if ((this.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel != null) && (!killed(this.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel))) {
                  ((Pilot)this.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel).requestCoverFor(this);
                }
                else if (this.jdField_Skill_of_type_Int >= 2) {
                  localAircraft = War.getNearestFriendlyFighter((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 8000.0F);
                  if ((localAircraft != null) && ((localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot))) ((Pilot)localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).requestCoverFor(this);
                }
              }

            }

          }
          else if (this.jdField_Skill_of_type_Int >= 2) {
            localAircraft = War.getNearestFriendlyFighter((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 8000.0F);
            if (((localAircraft instanceof TypeFighter)) && ((localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot))) ((Pilot)localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).requestCoverFor(this);

          }

        }

        if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeFighter))
          fighterDefence();
        else if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeStormovik))
          stormovikDefence();
        else {
          transportDefence();
        }
      }
      break;
    case 5:
      if ((this.jdField_airClient_of_type_ComMaddoxIl2FmFlightModel != null) && (!killed(this.jdField_airClient_of_type_ComMaddoxIl2FmFlightModel))) {
        this.followOffset.set(100.0D, 0.0D, 20.0D);
        set_maneuver(65);
      }
      else {
        this.jdField_airClient_of_type_ComMaddoxIl2FmFlightModel = null;
        if ((this.jdField_target_of_type_ComMaddoxIl2FmFlightModel != null) && (!killed(this.jdField_target_of_type_ComMaddoxIl2FmFlightModel))) {
          set_task(6);
          set_maneuver(0);
        } else {
          set_task(3);
          set_maneuver(21);
        }
      }
      break;
    case 6:
      this.WeWereInAttack = true;
      if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeFighter)) {
        this.jdField_bombsOut_of_type_Boolean = true;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.dropFuelTanks();
      }
      if ((this.jdField_target_of_type_ComMaddoxIl2FmFlightModel == null) || (!hasCourseWeaponBullets())) {
        if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action == 3) {
          set_task(7);
          set_maneuver(0);
        } else {
          set_task(3);
          if (this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel != null) set_maneuver(24); else
            set_maneuver(21);
        }
      }
      else {
        j = ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).aircIndex();
        if ((this.jdField_target_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeBomber)) { attackBombers();
        } else if ((this.jdField_target_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeStormovik)) { attackStormoviks();
        } else {
          if ((j == 0) || (j == 2)) {
            set_maneuver(27);
          }
          if ((j != 1) && (j != 3)) break;
          if ((this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel != null) && (!killed(this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel))) {
            this.jdField_airClient_of_type_ComMaddoxIl2FmFlightModel = this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel;
            set_task(5);
            set_maneuver(0);
          } else {
            set_maneuver(27); } 
        }
      }break;
    case 7:
      if (!this.jdField_WeWereInGAttack_of_type_Boolean)
      {
        this.jdField_WeWereInGAttack_of_type_Boolean = true;
      }
      if (!Actor.isAlive(this.jdField_target_ground_of_type_ComMaddoxIl2EngineActor)) {
        set_task(2);
        set_maneuver(0);
      }
      else {
        j = 1;
        if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0][0] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0][0].bulletMassa() > 0.05F) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0][0].countBullets() > 0))
          j = 0;
        if (((j != 0) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.getWeaponMass() < 15.0F)) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.getWeaponMass() < 1.0F)) {
          Voice.speakEndOfAmmo((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
          set_task(2);
          set_maneuver(0);
          if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action != 3) this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
          this.jdField_target_ground_of_type_ComMaddoxIl2EngineActor = null;
        }

        if (((this.jdField_target_ground_of_type_ComMaddoxIl2EngineActor instanceof Prey)) && ((((Prey)this.jdField_target_ground_of_type_ComMaddoxIl2EngineActor).HitbyMask() & 0x1) == 0))
        {
          f1 = 0.0F;
          for (k = 0; k < 4; k++) {
            if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[k] == null) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[k][0] == null) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[k][0].countBullets() == 0)) {
              continue;
            }
            if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[k][0].bulletMassa() > f1)
              f1 = this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[k][0].bulletMassa();
          }
          if ((f1 < 0.08F) || (((this.jdField_target_ground_of_type_ComMaddoxIl2EngineActor instanceof TgtShip)) && (f1 < 0.55F)))
          {
            set_task(2);
            set_maneuver(0);
            if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action != 3) this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
            this.jdField_target_ground_of_type_ComMaddoxIl2EngineActor = null;
          }
        }

        if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0].countBullets() != 0)) {
          if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0] instanceof TorpedoGun)) {
            if ((this.jdField_target_ground_of_type_ComMaddoxIl2EngineActor instanceof TgtShip))
              set_maneuver(51);
            else {
              set_maneuver(43);
            }
          }
          else if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0] instanceof BombGunPara)) {
            this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().setTarget(null);
            this.jdField_target_ground_of_type_ComMaddoxIl2EngineActor = null;
            set_maneuver(21);
          }
          else if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0].bulletMassa() < 10.0F) {
            set_maneuver(52);
          }
          else if (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeDiveBomber)) && (this.jdField_Alt_of_type_Float > 1200.0F)) {
            set_maneuver(50); } else {
            set_maneuver(43);
          }
        } else {
          if ((this.jdField_target_ground_of_type_ComMaddoxIl2EngineActor instanceof BridgeSegment)) {
            set_task(2);
            set_maneuver(0);
            if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action != 3) this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
            this.jdField_target_ground_of_type_ComMaddoxIl2EngineActor = null;
          }
          if (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeFighter)) || ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeStormovik)))
          {
            set_maneuver(43);
          }
          else {
            set_task(2);
            set_maneuver(0);
            if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action != 3) this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
            this.jdField_target_ground_of_type_ComMaddoxIl2EngineActor = null;
          }
        }
      }
      break;
    case 0:
      if (!isReadyToDie()) break; set_maneuver(49); break;
    case 1:
      set_maneuver(45);
    }
  }

  public boolean requestCoverFor(FlightModel paramFlightModel)
  {
    if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeTransport)) {
      Voice.speakHelpFromAir((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 0);
      return false;
    }
    if ((this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel == null) || (((Pilot)this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel).jdField_target_of_type_ComMaddoxIl2FmFlightModel != this) || (this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.distance(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d) > 600.0D + 200.0D * this.jdField_Skill_of_type_Int) || ((this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeStormovik)) || ((this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeBomber)))
    {
      if ((((Pilot)paramFlightModel).jdField_danger_of_type_ComMaddoxIl2FmFlightModel == null) || (killed(((Pilot)paramFlightModel).jdField_danger_of_type_ComMaddoxIl2FmFlightModel)) || ((((Pilot)paramFlightModel).jdField_danger_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeTransport)) || (((Pilot)paramFlightModel).jdField_danger_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.distance(paramFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d) > 3000.0D))
      {
        Voice.speakHelpFromAir((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 2);
        return true;
      }
      set_task(6);
      set_maneuver(27);
      this.jdField_target_of_type_ComMaddoxIl2FmFlightModel = ((Pilot)paramFlightModel).jdField_danger_of_type_ComMaddoxIl2FmFlightModel;
      if (World.Rnd().nextBoolean())
        Voice.speakCoverProvided((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor, (Aircraft)paramFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor);
      else
        Voice.speakHelpFromAir((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 1);
      return true;
    }

    Voice.speakHelpFromAir((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 0);
    return false;
  }

  public void setAsDanger(Actor paramActor)
  {
    if (get_maneuver() == 44) return;
    if (get_maneuver() == 26) return;
    if ((isDumb()) && 
      (!isReadyToReturn())) return;

    if (paramActor.getArmy() == this.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy()) {
      set_maneuver(8);
      setDumbTime(5000L);
      if ((paramActor instanceof Aircraft))
        Voice.speakCheckFire((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor, (Aircraft)paramActor);
      return;
    }

    if (!Actor.isValid(this.jdField_actor_of_type_ComMaddoxIl2EngineActor)) {
      if (World.cur().isArcade()) {
        Aircraft.debugprintln(paramActor, "Jeopardizing invalid actor (one being destroyed)..");
        Explosions.generateComicBulb(paramActor, "Sucker", 5.0F);
        if (((paramActor instanceof TypeFighter)) && ((((Aircraft)paramActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot))) {
          ((Pilot)((Aircraft)paramActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel).set_maneuver(35);
        }
      }
      Voice.speakNiceKill((Aircraft)paramActor);
      return;
    }
    Object localObject;
    switch (this.jdField_Skill_of_type_Int) {
    case 0:
      if (World.Rnd().nextInt(0, 99) < 98) return;
      if (!(paramActor instanceof Aircraft)) break;
      localObject = new Vector3d();
      ((Vector3d)localObject).sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d, ((Aircraft)paramActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      ((Aircraft)paramActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv((Tuple3d)localObject);
      if (((Vector3d)localObject).jdField_z_of_type_Double <= 0.0D) break; return;
    case 1:
      if (!detectable(paramActor)) return;
      if (World.Rnd().nextInt(0, 99) >= 97) break; return;
    case 2:
      if (getMnTime() >= 1.0F) break; return;
    case 3:
      if (getMnTime() >= 1.0F) break; return;
    }

    if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeTransport)) {
      if ((this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action != 3) && ((get_maneuver() == 24) || (get_maneuver() == 21))) {
        set_task(4);
        set_maneuver(0);
      }
      return;
    }

    if (get_task() == 2) {
      set_task(3);
      set_maneuver(0);
    }

    if ((paramActor instanceof Aircraft)) {
      if ((paramActor instanceof TypeFighter)) {
        if ((this.jdField_turret_of_type_ArrayOfComMaddoxIl2FmTurret.length > 0) && (this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astatePilotStates[this.jdField_turret_of_type_ArrayOfComMaddoxIl2FmTurret.length] < 90)) Voice.speakDanger((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 4);
        Voice.speakDanger((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 0);
      }

      Aircraft localAircraft = (Aircraft)paramActor;
      localObject = this;
      ((Pilot)localObject).jdField_danger_of_type_ComMaddoxIl2FmFlightModel = localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      if (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeFighter)) && (get_task() != 4)) {
        this.jdField_target_of_type_ComMaddoxIl2FmFlightModel = localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
        set_task(4);
        set_maneuver(0);
        clear_stack();
        return;
      }
    }
  }

  private void transportDefence()
  {
    switch (this.jdField_Skill_of_type_Int) {
    case 0:
      set_maneuver(21);
      break;
    case 1:
      if (!isLonely(30.0F, true)) break;
      set_maneuver(38); break;
    case 2:
    case 3:
      if (!isLonely(40.0F, false)) break;
      switch (World.Rnd().nextInt(0, 3)) {
      case 0:
        set_maneuver(39);
        break;
      case 1:
        set_maneuver(38);
        break;
      case 2:
        set_maneuver(15);
        break;
      case 3:
        set_maneuver(14);
      }
    }
  }

  private void stormovikDefence()
  {
    if (this.dist > 400.0F) {
      set_maneuver(3);
      return;
    }

    switch (this.jdField_Skill_of_type_Int) {
    case 0:
      if (World.Rnd().nextBoolean())
        set_maneuver(14);
      else
        set_maneuver(56);
      break;
    case 1:
      if (this.Visible)
        set_maneuver(14);
      else {
        switch (World.Rnd().nextInt(0, 7)) {
        case 0:
          set_maneuver(29);
          break;
        case 1:
          set_maneuver(35);
          break;
        case 2:
        case 3:
        case 4:
        case 5:
          set_maneuver(14);
          break;
        case 6:
        case 7:
          set_maneuver(39);
        }
      }

      break;
    case 2:
    case 3:
      if (this.Visible) {
        if (VDanger.jdField_x_of_type_Double > 0.9399999976158142D) {
          switch (World.Rnd().nextInt(0, 13)) { case 0:
          case 1:
          case 2:
            set_maneuver(38);
            break;
          case 3:
          case 4:
            set_maneuver(27);
            this.jdField_target_of_type_ComMaddoxIl2FmFlightModel = this.jdField_danger_of_type_ComMaddoxIl2FmFlightModel;
            break;
          case 5:
          case 6:
            set_maneuver(39);
            break;
          case 7:
          case 8:
          case 9:
            set_maneuver(35);
            break;
          case 10:
          case 11:
            set_maneuver(6);
            break;
          case 12:
            set_maneuver(56);
            break;
          case 13:
            set_maneuver(14);
          }
        }
        else if (Math.abs(VDanger.jdField_x_of_type_Double) < 0.2750000059604645D) {
          switch (World.Rnd().nextInt(0, 7)) {
          case 0:
            set_maneuver(16);
            break;
          case 1:
            set_maneuver(17);
            break;
          case 2:
          case 3:
          case 4:
            set_maneuver(28);
            break;
          case 5:
          case 6:
            set_maneuver(6);
            break;
          case 7:
            set_maneuver(13);
          }
        }
        else {
          switch (World.Rnd().nextInt(0, 3)) { case 0:
          case 1:
            set_maneuver(14);
            break;
          case 2:
            set_maneuver(29);
            break;
          case 3:
            set_maneuver(39);
          }
        }
      }
      else
        switch (World.Rnd().nextInt(0, 17)) { case 0:
        case 1:
        case 2:
        case 3:
        case 4:
          set_maneuver(29);
          break;
        case 5:
        case 6:
          set_maneuver(35);
          break;
        case 7:
        case 8:
          set_maneuver(28);
          break;
        case 9:
        case 10:
          set_maneuver(39);
          break;
        case 11:
          set_maneuver(38);
          break;
        case 12:
        case 13:
          set_maneuver(32);
          break;
        case 14:
          set_maneuver(7);
          break;
        case 15:
        case 16:
        case 17:
          set_maneuver(15);
        }
    }
  }

  private void fighterDefence()
  {
    switch (this.jdField_Skill_of_type_Int) {
    case 0:
      if (VDanger.jdField_x_of_type_Double > 0.9D) {
        set_maneuver(27);
      }
      else
        switch (World.Rnd().nextInt(0, 2)) { case 0:
        case 1:
          set_maneuver(14);
          break;
        default:
          set_maneuver(28); }
      break;
    case 1:
      if (this.dE > 500.0D) {
        if (VDanger.jdField_x_of_type_Double > 0.9D)
          set_maneuver(27);
        else {
          switch (World.Rnd().nextInt(0, 3)) { case 1:
          case 2:
            set_maneuver(22);
            break;
          default:
            set_maneuver(28); break;
          }
        }
      }
      else if (this.dE > 200.0D) {
        if (VDanger.jdField_x_of_type_Double > 0.9D)
          set_maneuver(27);
        else {
          switch (World.Rnd().nextInt(0, 5)) {
          case 1:
          case 2:
          case 3:
            set_maneuver(22);
            break;
          case 4:
            set_maneuver(35);
            break;
          default:
            set_maneuver(39); break;
          }
        }
      }
      else if (this.dE > -200.0D) {
        if (VDanger.jdField_x_of_type_Double > 0.8D) {
          set_maneuver(27);
        } else {
          if ((diffVLength < 50.0D) && (OnMe.jdField_x_of_type_Double > 0.8D)) {
            if (this.dist < 200.0D) {
              if (this.jdField_Alt_of_type_Float > 500.0D) {
                switch (World.Rnd().nextInt(0, 6)) {
                case 0:
                  set_maneuver(35);
                  break;
                case 1:
                  set_maneuver(19);
                  break;
                case 2:
                  set_maneuver(33);
                  break;
                case 3:
                  set_maneuver(34);
                  break;
                case 4:
                  set_maneuver(7);
                  break;
                case 5:
                  set_maneuver(32);
                  break;
                default:
                  set_maneuver(29); break;
                }
              }
              if (this.jdField_Alt_of_type_Float > 150.0F) {
                switch (World.Rnd().nextInt(0, 3)) {
                case 0:
                  set_maneuver(35);
                  break;
                case 1:
                  set_maneuver(7);
                  break;
                case 2:
                  set_maneuver(32);
                  break;
                default:
                  set_maneuver(29); break;
                }
              }
              set_maneuver(29); break;
            }
          }
          switch (World.Rnd().nextInt(0, 4)) {
          case 0:
            set_maneuver(7);
            break;
          case 1:
            set_maneuver(19);
            break;
          case 2:
            set_maneuver(33);
            break;
          case 3:
            set_maneuver(34);
            break;
          default:
            set_maneuver(35); break;

            if (OnMe.jdField_x_of_type_Double > 0.9D) {
              switch (World.Rnd().nextInt(0, 2)) {
              case 0:
                set_maneuver(35);
                break;
              case 1:
                set_maneuver(34);
                break;
              default:
                set_maneuver(22); break;
              }
            }

            set_maneuver(22); break;
          }
        }
      } else switch (World.Rnd().nextInt(0, 4)) {
        case 0:
          set_maneuver(27);
          break;
        case 1:
          set_maneuver(35);
          break;
        case 2:
          set_maneuver(19);
          break;
        case 3:
          set_maneuver(33);
          break;
        default:
          set_maneuver(34);
        }

      break;
    case 2:
      if (this.dE > 500.0D) {
        if (VDanger.jdField_x_of_type_Double > 0.9D) {
          set_maneuver(27);
        }
        else if ((OnMe.jdField_x_of_type_Double > 0.9D) && (this.dist < 200.0D))
          set_maneuver(35);
        else {
          switch (World.Rnd().nextInt(0, 6)) { case 1:
          case 2:
          case 3:
            set_maneuver(67);
            break;
          case 4:
            set_maneuver(28);
            break;
          default:
            set_maneuver(16); break;
          }
        }
      }
      else if (this.dE > 200.0D) {
        if (VDanger.jdField_x_of_type_Double > 0.9D) {
          set_maneuver(27);
        }
        else if ((OnMe.jdField_x_of_type_Double > 0.9D) && (this.dist > 200.0D))
          set_maneuver(35);
        else {
          switch (World.Rnd().nextInt(0, 5)) {
          case 1:
          case 2:
          case 3:
            set_maneuver(67);
            break;
          case 4:
            set_maneuver(35);
            break;
          default:
            set_maneuver(39); break;
          }
        }
      }
      else if (this.dE > -200.0D) {
        if (VDanger.jdField_x_of_type_Double > 0.8D) {
          set_maneuver(27);
        } else {
          if ((diffVLength < 50.0D) && (OnMe.jdField_x_of_type_Double > 0.8D)) {
            if (this.dist < 200.0D) {
              if (this.jdField_Alt_of_type_Float > 500.0D) {
                switch (World.Rnd().nextInt(0, 10)) {
                case 0:
                  set_maneuver(35);
                  break;
                case 1:
                  set_maneuver(19);
                  break;
                case 2:
                  set_maneuver(33);
                  break;
                case 3:
                  set_maneuver(34);
                  break;
                case 4:
                  set_maneuver(7);
                  break;
                case 5:
                  set_maneuver(32);
                  break;
                default:
                  set_maneuver(29); break;
                }
              }
              if (this.jdField_Alt_of_type_Float > 150.0F) {
                switch (World.Rnd().nextInt(0, 8)) {
                case 0:
                  set_maneuver(35);
                  break;
                case 1:
                  set_maneuver(7);
                  break;
                case 2:
                  set_maneuver(32);
                  break;
                default:
                  set_maneuver(29); break;
                }
              }
              set_maneuver(29); break;
            }
          }
          switch (World.Rnd().nextInt(0, 4)) {
          case 0:
            set_maneuver(7);
            break;
          case 1:
            set_maneuver(19);
            break;
          case 2:
            set_maneuver(33);
            break;
          case 3:
            set_maneuver(34);
            break;
          default:
            set_maneuver(35); break;

            if (OnMe.jdField_x_of_type_Double > 0.9D) {
              switch (World.Rnd().nextInt(0, 2)) {
              case 0:
                set_maneuver(35);
                break;
              case 1:
                set_maneuver(34);
                break;
              default:
                set_maneuver(67); break;
              }
            }

            set_maneuver(67); break;
          }
        }
      } else switch (World.Rnd().nextInt(0, 4)) {
        case 0:
          set_maneuver(27);
          break;
        case 1:
          set_maneuver(35);
          break;
        case 2:
          set_maneuver(19);
          break;
        case 3:
          set_maneuver(33);
          break;
        default:
          set_maneuver(34);
        }

      break;
    case 3:
      if (this.dE > 500.0D) {
        if (VDanger.jdField_x_of_type_Double > 0.9D) {
          set_maneuver(27);
        }
        else if ((OnMe.jdField_x_of_type_Double > 0.9D) && (this.dist < 200.0D))
          set_maneuver(35);
        else {
          switch (World.Rnd().nextInt(0, 6)) { case 1:
          case 2:
          case 3:
            set_maneuver(67);
            break;
          case 4:
            set_maneuver(28);
            break;
          default:
            set_maneuver(16); break;
          }
        }
      }
      else if (this.dE > 200.0D) {
        if (VDanger.jdField_x_of_type_Double > 0.9D) {
          set_maneuver(27);
        }
        else if ((OnMe.jdField_x_of_type_Double > 0.9D) && (this.dist > 200.0D))
          set_maneuver(35);
        else {
          switch (World.Rnd().nextInt(0, 5)) {
          case 1:
          case 2:
          case 3:
            set_maneuver(67);
            break;
          case 4:
            set_maneuver(35);
            break;
          default:
            set_maneuver(39); break;
          }
        }
      }
      else if (this.dE > -200.0D) {
        if (VDanger.jdField_x_of_type_Double > 0.8D) {
          set_maneuver(27);
        } else {
          if ((diffVLength < 50.0D) && (OnMe.jdField_x_of_type_Double > 0.8D)) {
            if (this.dist < 200.0D) {
              if (this.jdField_Alt_of_type_Float > 500.0D) {
                switch (World.Rnd().nextInt(0, 14)) {
                case 0:
                  set_maneuver(35);
                  break;
                case 1:
                  set_maneuver(19);
                  break;
                case 2:
                  set_maneuver(33);
                  break;
                case 3:
                  set_maneuver(34);
                  break;
                case 4:
                  set_maneuver(7);
                  break;
                case 5:
                  set_maneuver(32);
                  break;
                default:
                  set_maneuver(29); break;
                }
              }
              if (this.jdField_Alt_of_type_Float > 150.0F) {
                switch (World.Rnd().nextInt(0, 10)) {
                case 0:
                  set_maneuver(35);
                  break;
                case 1:
                  set_maneuver(7);
                  break;
                case 2:
                  set_maneuver(32);
                  break;
                default:
                  set_maneuver(29); break;
                }
              }
              set_maneuver(29); break;
            }
          }
          switch (World.Rnd().nextInt(0, 4)) {
          case 0:
            set_maneuver(7);
            break;
          case 1:
            set_maneuver(19);
            break;
          case 2:
            set_maneuver(33);
            break;
          case 3:
            set_maneuver(34);
            break;
          default:
            set_maneuver(35); break;

            if (OnMe.jdField_x_of_type_Double > 0.9D) {
              switch (World.Rnd().nextInt(0, 2)) {
              case 0:
                set_maneuver(35);
                break;
              case 1:
                set_maneuver(34);
                break;
              default:
                set_maneuver(67); break;
              }
            }

            set_maneuver(67); break;
          }
        }
      } else switch (World.Rnd().nextInt(0, 4)) {
        case 0:
          set_maneuver(27);
          break;
        case 1:
          set_maneuver(35);
          break;
        case 2:
          set_maneuver(19);
          break;
        case 3:
          set_maneuver(33);
          break;
        default:
          set_maneuver(34);
        }
    }
  }

  public void attackBombers()
  {
    float f = 0.0F;
    if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1] != null) && (((GunGeneric)this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1][0]).countBullets() > 0)) {
      f = ((GunGeneric)this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1][0]).bulletMassa();
    }

    if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof ME_163B1A)) {
      switch (World.Rnd().nextInt(0, 2)) {
      case 0:
        setBomberAttackType(7);
        break;
      default:
        setBomberAttackType(2); break;
      }
    }
    else if (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof KI_46_OTSUHEI)) && (((GunGeneric)this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0][0]).countBullets() > 0))
    {
      setBomberAttackType(10);
    }
    else switch (this.jdField_Skill_of_type_Int) {
      case 0:
        switch (World.Rnd().nextInt(0, 5)) {
        case 0:
          setBomberAttackType(7);
          break;
        case 1:
          setBomberAttackType(2);
          break;
        default:
          setBomberAttackType(5);
        }
        break;
      case 1:
        if (f > 0.12F) {
          switch (World.Rnd().nextInt(0, 3)) {
          case 0:
            setBomberAttackType(2);
            break;
          case 1:
            setBomberAttackType(1);
            break;
          default:
            setBomberAttackType(0); break;
          }
        }
        else {
          switch (World.Rnd().nextInt(0, 6)) {
          case 0:
            setBomberAttackType(1);
            break;
          case 1:
            setBomberAttackType(7);
            break;
          default:
            setBomberAttackType(2);
          }
        }
        break;
      case 2:
        if (f > 0.12F) {
          switch (World.Rnd().nextInt(0, 6)) {
          case 0:
            setBomberAttackType(2);
            break;
          case 1:
            setBomberAttackType(1);
            break;
          default:
            setBomberAttackType(0); break;
          }
        }
        else if (f > 0.05F) {
          switch (World.Rnd().nextInt(0, 10)) { case 0:
          case 1:
          case 2:
            setBomberAttackType(1);
            break;
          case 3:
            setBomberAttackType(7);
            break;
          case 4:
            setBomberAttackType(6);
            break;
          default:
            setBomberAttackType(2); break;
          }
        }
        else {
          switch (World.Rnd().nextInt(0, 6)) {
          case 0:
            setBomberAttackType(1);
            break;
          case 1:
            setBomberAttackType(7);
            break;
          case 2:
            setBomberAttackType(3);
            break;
          default:
            setBomberAttackType(2);
          }
        }
        break;
      case 3:
        if (f > 0.12F) {
          switch (World.Rnd().nextInt(0, 7)) {
          case 0:
            setBomberAttackType(2);
            break;
          case 1:
            setBomberAttackType(1);
            break;
          case 2:
            setBomberAttackType(6);
            break;
          default:
            setBomberAttackType(0); break;
          }
        }
        else if (f > 0.05F) {
          switch (World.Rnd().nextInt(0, 10)) { case 0:
          case 1:
          case 2:
            setBomberAttackType(1);
            break;
          case 3:
            setBomberAttackType(7);
            break;
          case 4:
            setBomberAttackType(3);
            break;
          case 5:
            setBomberAttackType(6);
            break;
          default:
            setBomberAttackType(2); break;
          }
        }
        else {
          switch (World.Rnd().nextInt(0, 4)) {
          case 0:
            setBomberAttackType(1);
            break;
          case 1:
            setBomberAttackType(7);
            break;
          case 2:
            setBomberAttackType(3);
            break;
          default:
            setBomberAttackType(2);
          }
        }
      }


    set_maneuver(63);
  }

  public void attackStormoviks()
  {
    switch (this.jdField_Skill_of_type_Int) {
    case 0:
      switch (World.Rnd().nextInt(0, 5)) {
      case 0:
        setBomberAttackType(8);
        break;
      case 1:
        setBomberAttackType(9);
        break;
      default:
        setBomberAttackType(5);
      }
      break;
    case 1:
    case 2:
    case 3:
      if (this.jdField_target_of_type_ComMaddoxIl2FmFlightModel.crew > 1)
        switch (World.Rnd().nextInt(0, 6)) {
        case 0:
          setBomberAttackType(9);
          break;
        case 1:
        case 2:
          setBomberAttackType(0);
          break;
        default:
          setBomberAttackType(4); break;
        }
      else {
        switch (World.Rnd().nextInt(0, 3)) {
        case 0:
          setBomberAttackType(9);
          break;
        default:
          setBomberAttackType(8);
        }
      }
    }

    set_maneuver(63);
  }

  private void assignManeuverToWingmen(int paramInt)
  {
    Pilot localPilot = this;
    while (localPilot.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel != null) {
      localPilot = (Pilot)localPilot.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel;
      localPilot.set_maneuver(paramInt);
    }
  }

  private void assignTaskToWingmen(int paramInt)
  {
    Pilot localPilot = this;
    while (localPilot.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel != null) {
      localPilot = (Pilot)localPilot.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel;
      localPilot.set_task(paramInt);
    }
  }

  public boolean isLeader()
  {
    if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeFighter)) return ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).aircIndex() % 2 == 0;
    return this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel == null;
  }

  public boolean isLonely(float paramFloat, boolean paramBoolean)
  {
    if (paramBoolean) {
      if ((this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel == null) && (this.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel == null)) return true;
      double d = 0.0D;
      if (this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel != null) d = this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.distance(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      if (this.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel != null) d = Math.min(this.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.distance(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d), d);
      return d > paramFloat;
    }
    Actor localActor = NearestTargets.getEnemy(9, -1, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d, paramFloat, 0);
    if (Actor.isValid(localActor)) return localActor.pos.getAbsPoint().distance(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d) > paramFloat;
    return true;
  }
}
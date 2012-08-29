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
import com.maddox.il2.objects.air.TypeHasToKG;
import com.maddox.il2.objects.air.TypeStormovik;
import com.maddox.il2.objects.air.TypeTransport;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.weapons.BombGunPara;
import com.maddox.il2.objects.weapons.ParaTorpedoGun;
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
    if (paramFlightModel.AS.astatePilotStates[0] == 100) return true;
    if (Actor.isValid(paramFlightModel.actor)) return killed(paramFlightModel.actor);
    return paramFlightModel.isTakenMortalDamage();
  }

  private boolean detectable(Actor paramActor) {
    if (!(paramActor instanceof Aircraft)) return false;
    if (this.Skill >= 2) return true;
    VDanger.set(((Aircraft)paramActor).FM.Loc);
    VDanger.sub(this.Loc);
    OnMe.scale(-1.0D, VDanger);
    this.Or.transformInv(VDanger);
    return VDanger.x >= 0.0D;
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
    if ((this.actor.net != null) && (this.actor.net.isMirror())) {
      ((NetAircraft.Mirror)this.actor.net).fmUpdate(paramFloat);
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
    if (killed(this.danger)) this.danger = null;
    if (killed(this.target)) this.target = null;
    if (killed(this.airClient)) this.airClient = null;
    if (killed(this.target_ground)) this.target_ground = null;

    setBusy(false);

    if (this.AS.isPilotDead(0)) {
      setBusy(true);
      set_maneuver(44);
      if (this.crew > 1) ((Aircraft)this.actor).hitDaSilk();
      set_task(0);
      return;
    }
    if (get_maneuver() == 46) {
      setBusy(true);
      this.dontSwitch = true;
      return;
    }

    float f1 = 0.0F;
    int i = this.EI.getNum();
    int j;
    if (i != 0) {
      for (j = 0; j < i; j++) {
        f1 += this.EI.engines[j].getReadyness() / i;
      }
      if (f1 < 0.7F) setReadyToReturn(true);
      if (f1 < 0.3F) setReadyToDie(true);

    }

    if (this.M.fuel < 0.3F * this.M.maxFuel) {
      j = this.AP.way.Cur();
      this.AP.way.last();
      float f2 = this.AP.getWayPointDistance();
      this.AP.way.setCur(j);
      if (this.M.maxFuel < 0.001F) this.M.maxFuel = 0.001F;
      float f3 = 1000.0F * this.Range * this.M.fuel / this.M.maxFuel;
      if ((f3 < 2.0F * f2) && (!(this.actor instanceof TypeGlider))) setReadyToReturn(true);
    }
    if ((this.M.fuel < 0.01F) && (!(this.actor instanceof TypeGlider))) setReadyToDie(true);

    if ((isTakenMortalDamage()) || (!isCapableOfBMP())) {
      setBusy(true);
      ((Aircraft)this.actor).hitDaSilk();
      set_task(0);
      if (this.Group != null) this.Group.delAircraft((Aircraft)this.actor);
    }

    if (isReadyToDie()) {
      this.AP.way.setCur(1);
      this.bombsOut = true;
      this.CT.dropFuelTanks();
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
      this.dontSwitch = true;
      return;
    }
    Maneuver localManeuver;
    if ((getDangerAggressiveness() > 0.88F) && 
      (this.danger != null) && (((this.danger.actor instanceof TypeFighter)) || ((this.danger.actor instanceof TypeStormovik))) && (((Maneuver)this.danger).isOk()))
    {
      if (get_task() != 4) {
        set_task(4);
        clear_stack();
        set_maneuver(0);
        if (((this.actor instanceof TypeStormovik)) && (this.Group != null)) {
          j = this.Group.numInGroup((Aircraft)this.actor);
          if (this.Group.nOfAirc >= j + 2) {
            localManeuver = (Maneuver)this.Group.airc[(j + 1)].FM;
            Voice.speakCheckYour6((Aircraft)this.actor, (Aircraft)this.danger.actor);
            Voice.speakHelpFromAir((Aircraft)localManeuver.actor, 1);
            localManeuver.airClient = this;
            set_task(6);
            clear_stack();
            localManeuver.target = this.danger;
            set_maneuver(27);
            setBusy(true);
            return;
          }
        }

      }

      Voice.speakClearTail((Aircraft)this.actor);
      setBusy(true);
      return;
    }

    if ((isReadyToReturn()) && (!this.AP.way.isLanding())) {
      if ((this.Group != null) && (this.Group.grTask != 1)) {
        AirGroup localAirGroup = new AirGroup(this.Group);
        localAirGroup.rejoinGroup = null;
        this.Group.delAircraft((Aircraft)this.actor);
        localAirGroup.addAircraft((Aircraft)this.actor);
        localAirGroup.w.last();
        localAirGroup.w.prev();
        localAirGroup.w.curr().setTimeout(3);
        localAirGroup.timeOutForTaskSwitch = 10000;
        this.AP.way.last();
        this.AP.way.prev();
        this.AP.way.curr().getP(p1f);
        p1f.z = (-10.0F + (float)this.Loc.z);
      }
      this.bombsOut = true;
      this.CT.dropFuelTanks();
      return;
    }

    if (get_task() == 6)
    {
      if ((this.target != null) && (this.airClient != null) && (this.target == ((Maneuver)this.airClient).danger)) {
        if ((this.actor instanceof TypeStormovik)) {
          if (((Maneuver)this.airClient).getDangerAggressiveness() > 0.0F) {
            setBusy(true);
            return;
          }
          this.airClient = null;
        }

        setBusy(true);
        return;
      }

      if (((((Aircraft)this.actor).aircIndex() & 0x1) == 0) && (this.Group != null)) {
        int k = this.Group.numInGroup((Aircraft)this.actor);
        if ((this.Group.nOfAirc >= k + 2) && ((this.Group.airc[(k + 1)].aircIndex() & 0x1) != 0)) {
          localManeuver = (Maneuver)this.Group.airc[(k + 1)].FM;
          if ((localManeuver.airClient == this) && (localManeuver.getDangerAggressiveness() > 0.5F) && (localManeuver.danger != null) && (((Maneuver)localManeuver.danger).isOk()))
          {
            Voice.speakCheckYour6((Aircraft)localManeuver.actor, (Aircraft)localManeuver.danger.actor);
            Voice.speakHelpFromAir((Aircraft)this.actor, 1);
            set_task(6);
            clear_stack();
            this.target = localManeuver.danger;
            set_maneuver(27);
            setBusy(true);
            return;
          }
        }
      }

      if ((this.target != null) && (((Maneuver)this.target).getDangerAggressiveness() > 0.5F) && (((Maneuver)this.target).danger == this) && (((Maneuver)this.target).isOk()))
      {
        setBusy(true);
        return;
      }
    }

    if (isDumb()) {
      setBusy(true);
      return;
    }

    if ((get_task() == 7) && (this.target_ground != null) && (get_maneuver() != 0)) {
      setBusy(true);
      return;
    }

    if ((get_task() == 3) && (this.AP.way.isLanding())) {
      setBusy(true);
      return;
    }
  }

  private void setTaskAndManeuver()
  {
    if (this.dontSwitch) { this.dontSwitch = false; return; }
    if (!isBusy()) {
      if (((this.wasBusy) || (get_maneuver() == 0)) && (this.Group != null)) {
        clear_stack();
        this.Group.setTaskAndManeuver(this.Group.numInGroup((Aircraft)this.actor));
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
      if ((this.Leader != null) && (Actor.isValid(this.Leader.actor)) && (!this.Leader.isReadyToReturn()) && (!this.Leader.isReadyToDie()) && (this.Leader.getSpeed() > 35.0F))
      {
        set_maneuver(24);
      }
      else
        set_task(3);
      break;
    case 3:
      set_maneuver(21);

      if (this.AP.way.isLanding()) break;
      wingman(true);
      if (this.Leader != null) {
        break;
      }
      k = 0;
      int i;
      if (this.AP.way.Cur() < this.AP.way.size() - 1) {
        this.AP.way.next();
        i = this.AP.way.curr().Action;
        k = this.AP.way.curr().getTarget() != null ? 1 : 0;
        f1 = this.AP.getWayPointDistance();
        this.AP.way.prev();
      }
      else {
        i = this.AP.way.curr().Action;
        f1 = this.AP.getWayPointDistance();
      }
      Pilot localPilot = (Pilot)(Pilot)((Aircraft)this.actor).FM;
      float f2 = this.AP.getWayPointDistance();

      while (localPilot.Wingman != null) {
        localPilot = (Pilot)localPilot.Wingman;
        localPilot.wingman(true);
        localPilot.AP.way.setCur(this.AP.way.Cur());
        if ((!localPilot.AP.way.isLanding()) && (localPilot.get_task() == 3))
          localPilot.set_task(2);
      }
      if ((((Aircraft)this.actor).aircIndex() == 0) && (this.Speak5minutes == 0) && (i == 3) && (f1 < 30000.0D)) {
        Voice.speak5minutes((Aircraft)this.actor);
        this.Speak5minutes = 1;
      }
      break;
    case 4:
      if (get_maneuver() == 0) set_maneuver(21);
      if (this.danger == null) {
        set_task(3);
      }
      else {
        if ((this.actor instanceof TypeFighter)) {
          this.bombsOut = true;
          this.CT.dropFuelTanks();
        }

        this.dist = (float)this.Loc.distance(this.danger.Loc);
        VDanger.sub(this.danger.Loc, this.Loc);
        OnMe.scale(-1.0D, VDanger);
        tmpOr.setYPR(this.Or.getYaw(), 0.0F, 0.0F);
        tmpOr.transformInv(VDanger);
        diffV.sub(this.danger.Vwld, this.Vwld);
        tmpOr.transformInv(diffV);
        diffVLength = diffV.length();

        tmpOr.setYPR(this.danger.Or.getYaw(), 0.0F, 0.0F);
        this.danger.Or.transformInv(OnMe);

        VDanger.normalize();
        OnMe.normalize();

        this.dE = ((this.Energy - this.danger.Energy) * 0.1019F);

        this.Energed = (this.danger.Energy > this.Energy);
        this.Faster = (this.danger.getSpeed() > getSpeed());
        this.Higher = (this.danger.Loc.z > this.Loc.z);
        this.Near = (this.dist < 300.0F);
        this.OnBack = ((VDanger.x < 0.0D) && (this.dist < 2000.0F));
        this.Visible = (VDanger.dot(MainLook) > 0.0D);
        this.Looks = (OnMe.x > 0.0D);
        VDanger.normalize();

        if ((this.OnBack) && (this.Near) && ((this.danger instanceof TypeFighter)) && (
          ((this.actor instanceof TypeTransport)) || (this.Wingman == null) || (killed(this.Wingman)) || (((Pilot)this.Wingman).target != this.danger)))
        {
          Aircraft localAircraft;
          if (isLeader()) {
            if (((this.actor instanceof TypeFighter)) || (((this.actor instanceof TypeStormovik)) && (this.Skill > 1) && (this.AP.way.curr().Action == 0)))
            {
              if ((this.Wingman != null) && (!killed(this.Wingman)) && 
                (!((Pilot)this.Wingman).requestCoverFor(this))) {
                if ((this.Wingman.Wingman != null) && (!killed(this.Wingman.Wingman))) {
                  ((Pilot)this.Wingman.Wingman).requestCoverFor(this);
                }
                else if (this.Skill >= 2) {
                  localAircraft = War.getNearestFriendlyFighter((Aircraft)this.actor, 8000.0F);
                  if ((localAircraft != null) && ((localAircraft.FM instanceof Pilot))) ((Pilot)localAircraft.FM).requestCoverFor(this);
                }
              }

            }

          }
          else if (this.Skill >= 2) {
            localAircraft = War.getNearestFriendlyFighter((Aircraft)this.actor, 8000.0F);
            if (((localAircraft instanceof TypeFighter)) && ((localAircraft.FM instanceof Pilot))) ((Pilot)localAircraft.FM).requestCoverFor(this);

          }

        }

        if ((this.actor instanceof TypeFighter))
          fighterDefence();
        else if ((this.actor instanceof TypeStormovik))
          stormovikDefence();
        else {
          transportDefence();
        }
      }
      break;
    case 5:
      if ((this.airClient != null) && (!killed(this.airClient))) {
        this.followOffset.set(100.0D, 0.0D, 20.0D);
        set_maneuver(65);
      }
      else {
        this.airClient = null;
        if ((this.target != null) && (!killed(this.target))) {
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
      if ((this.actor instanceof TypeFighter)) {
        this.bombsOut = true;
        this.CT.dropFuelTanks();
      }
      if ((this.target == null) || (!hasCourseWeaponBullets())) {
        if (this.AP.way.curr().Action == 3) {
          set_task(7);
          set_maneuver(0);
        } else {
          set_task(3);
          if (this.Leader != null) set_maneuver(24); else
            set_maneuver(21);
        }
      }
      else {
        j = ((Aircraft)this.actor).aircIndex();
        if ((this.target.actor instanceof TypeBomber)) { attackBombers();
        } else if ((this.target.actor instanceof TypeStormovik)) { attackStormoviks();
        } else {
          if ((j == 0) || (j == 2)) {
            set_maneuver(27);
          }
          if ((j == 1) || (j == 3)) {
            if ((this.Leader != null) && (!killed(this.Leader))) {
              this.airClient = this.Leader;
              set_task(5);
              set_maneuver(0);
            } else {
              set_maneuver(27);
            }
          }
        }
      }
      break;
    case 7:
      if (!this.WeWereInGAttack)
      {
        this.WeWereInGAttack = true;
      }
      if (!Actor.isAlive(this.target_ground)) {
        set_task(2);
        set_maneuver(0);
      }
      else {
        j = 1;
        if ((this.CT.Weapons[0] != null) && (this.CT.Weapons[0][0] != null) && (this.CT.Weapons[0][0].bulletMassa() > 0.05F) && (this.CT.Weapons[0][0].countBullets() > 0))
          j = 0;
        if (((j != 0) && (this.CT.getWeaponMass() < 15.0F)) || (this.CT.getWeaponMass() < 1.0F)) {
          Voice.speakEndOfAmmo((Aircraft)this.actor);
          set_task(2);
          set_maneuver(0);
          if (this.AP.way.curr().Action != 3) this.AP.way.next();
          this.target_ground = null;
        }

        if (((this.target_ground instanceof Prey)) && ((((Prey)this.target_ground).HitbyMask() & 0x1) == 0))
        {
          f1 = 0.0F;
          for (k = 0; k < 4; k++) {
            if ((this.CT.Weapons[k] == null) || (this.CT.Weapons[k][0] == null) || (this.CT.Weapons[k][0].countBullets() == 0)) {
              continue;
            }
            if (this.CT.Weapons[k][0].bulletMassa() > f1)
              f1 = this.CT.Weapons[k][0].bulletMassa();
          }
          if ((f1 < 0.08F) || (((this.target_ground instanceof TgtShip)) && (f1 < 0.55F)))
          {
            set_task(2);
            set_maneuver(0);
            if (this.AP.way.curr().Action != 3) this.AP.way.next();
            this.target_ground = null;
          }
        }

        if ((this.CT.Weapons[3] != null) && (this.CT.Weapons[3][0] != null) && (this.CT.Weapons[3][0].countBullets() != 0))
        {
          if ((this.CT.Weapons[3][0] instanceof ParaTorpedoGun))
          {
            set_maneuver(43);
          }
          else if ((this.CT.Weapons[3][0] instanceof TorpedoGun)) {
            if ((this.target_ground instanceof TgtShip))
            {
              if (((this instanceof TypeHasToKG)) && (this.Skill >= 2))
              {
                set_maneuver(81);
              }
              else
              {
                set_maneuver(51);
              }
            }
            else {
              set_maneuver(43);
            }
          }
          else if ((this.CT.Weapons[3][0] instanceof BombGunPara)) {
            this.AP.way.curr().setTarget(null);
            this.target_ground = null;
            set_maneuver(21);
          }
          else if (this.CT.Weapons[3][0].bulletMassa() < 10.0F) {
            set_maneuver(52);
          }
          else if (((this.actor instanceof TypeDiveBomber)) && (this.Alt > 1200.0F)) {
            set_maneuver(50); } else {
            set_maneuver(43);
          }
        } else {
          if ((this.target_ground instanceof BridgeSegment)) {
            set_task(2);
            set_maneuver(0);
            if (this.AP.way.curr().Action != 3) this.AP.way.next();
            this.target_ground = null;
          }
          if (((this.actor instanceof TypeFighter)) || ((this.actor instanceof TypeStormovik)))
          {
            set_maneuver(43);
          }
          else {
            set_task(2);
            set_maneuver(0);
            if (this.AP.way.curr().Action != 3) this.AP.way.next();
            this.target_ground = null;
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
    if ((this.actor instanceof TypeTransport)) {
      Voice.speakHelpFromAir((Aircraft)this.actor, 0);
      return false;
    }
    if ((this.danger == null) || (((Pilot)this.danger).target != this) || (this.danger.Loc.distance(this.Loc) > 600.0D + 200.0D * this.Skill) || ((this.danger.actor instanceof TypeStormovik)) || ((this.danger.actor instanceof TypeBomber)))
    {
      if ((((Pilot)paramFlightModel).danger == null) || (killed(((Pilot)paramFlightModel).danger)) || ((((Pilot)paramFlightModel).danger.actor instanceof TypeTransport)) || (((Pilot)paramFlightModel).danger.Loc.distance(paramFlightModel.Loc) > 3000.0D))
      {
        Voice.speakHelpFromAir((Aircraft)this.actor, 2);
        return true;
      }
      set_task(6);
      set_maneuver(27);
      this.target = ((Pilot)paramFlightModel).danger;
      if (World.Rnd().nextBoolean())
        Voice.speakCoverProvided((Aircraft)this.actor, (Aircraft)paramFlightModel.actor);
      else
        Voice.speakHelpFromAir((Aircraft)this.actor, 1);
      return true;
    }

    Voice.speakHelpFromAir((Aircraft)this.actor, 0);
    return false;
  }

  public void setAsDanger(Actor paramActor)
  {
    if (get_maneuver() == 44) return;
    if (get_maneuver() == 26) return;
    if ((isDumb()) && 
      (!isReadyToReturn())) return;

    if (paramActor.getArmy() == this.actor.getArmy()) {
      set_maneuver(8);
      setDumbTime(5000L);
      if ((paramActor instanceof Aircraft))
        Voice.speakCheckFire((Aircraft)this.actor, (Aircraft)paramActor);
      return;
    }

    if (!Actor.isValid(this.actor)) {
      if (World.cur().isArcade()) {
        Aircraft.debugprintln(paramActor, "Jeopardizing invalid actor (one being destroyed)..");
        Explosions.generateComicBulb(paramActor, "Sucker", 5.0F);
        if (((paramActor instanceof TypeFighter)) && ((((Aircraft)paramActor).FM instanceof Pilot))) {
          ((Pilot)((Aircraft)paramActor).FM).set_maneuver(35);
        }
      }
      Voice.speakNiceKill((Aircraft)paramActor);
      return;
    }
    Object localObject;
    switch (this.Skill) {
    case 0:
      if (World.Rnd().nextInt(0, 99) < 98) return;
      if (!(paramActor instanceof Aircraft)) break;
      localObject = new Vector3d();
      ((Vector3d)localObject).sub(this.Loc, ((Aircraft)paramActor).FM.Loc);
      ((Aircraft)paramActor).FM.Or.transformInv((Tuple3d)localObject);
      if (((Vector3d)localObject).z > 0.0D) return;
      break;
    case 1:
      if (!detectable(paramActor)) return;
      if (World.Rnd().nextInt(0, 99) >= 97) break; return;
    case 2:
      if (getMnTime() >= 1.0F) break; return;
    case 3:
      if (getMnTime() >= 1.0F) break; return;
    }

    if ((this.actor instanceof TypeTransport)) {
      if ((this.AP.way.curr().Action != 3) && ((get_maneuver() == 24) || (get_maneuver() == 21))) {
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
        if ((this.turret.length > 0) && (this.AS.astatePilotStates[this.turret.length] < 90)) Voice.speakDanger((Aircraft)this.actor, 4);
        Voice.speakDanger((Aircraft)this.actor, 0);
      }

      Aircraft localAircraft = (Aircraft)paramActor;
      localObject = this;
      ((Pilot)localObject).danger = localAircraft.FM;
      if (((this.actor instanceof TypeFighter)) && (get_task() != 4)) {
        this.target = localAircraft.FM;
        set_task(4);
        set_maneuver(0);
        clear_stack();
        return;
      }
    }
  }

  private void transportDefence()
  {
    switch (this.Skill) {
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

    switch (this.Skill) {
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
        if (VDanger.x > 0.9399999976158142D) {
          switch (World.Rnd().nextInt(0, 13)) { case 0:
          case 1:
          case 2:
            set_maneuver(38);
            break;
          case 3:
          case 4:
            set_maneuver(27);
            this.target = this.danger;
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
        else if (Math.abs(VDanger.x) < 0.2750000059604645D) {
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
    switch (this.Skill) {
    case 0:
      if (VDanger.x > 0.9D) {
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
        if (VDanger.x > 0.9D)
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
        if (VDanger.x > 0.9D)
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
        if (VDanger.x > 0.8D) {
          set_maneuver(27);
        } else {
          if ((diffVLength < 50.0D) && (OnMe.x > 0.8D)) {
            if (this.dist < 200.0D) {
              if (this.Alt > 500.0D) {
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
              if (this.Alt > 150.0F) {
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

            if (OnMe.x > 0.9D) {
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
        if (VDanger.x > 0.9D) {
          set_maneuver(27);
        }
        else if ((OnMe.x > 0.9D) && (this.dist < 200.0D))
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
        if (VDanger.x > 0.9D) {
          set_maneuver(27);
        }
        else if ((OnMe.x > 0.9D) && (this.dist > 200.0D))
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
        if (VDanger.x > 0.8D) {
          set_maneuver(27);
        } else {
          if ((diffVLength < 50.0D) && (OnMe.x > 0.8D)) {
            if (this.dist < 200.0D) {
              if (this.Alt > 500.0D) {
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
              if (this.Alt > 150.0F) {
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

            if (OnMe.x > 0.9D) {
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
        if (VDanger.x > 0.9D) {
          set_maneuver(27);
        }
        else if ((OnMe.x > 0.9D) && (this.dist < 200.0D))
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
        if (VDanger.x > 0.9D) {
          set_maneuver(27);
        }
        else if ((OnMe.x > 0.9D) && (this.dist > 200.0D))
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
        if (VDanger.x > 0.8D) {
          set_maneuver(27);
        } else {
          if ((diffVLength < 50.0D) && (OnMe.x > 0.8D)) {
            if (this.dist < 200.0D) {
              if (this.Alt > 500.0D) {
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
              if (this.Alt > 150.0F) {
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

            if (OnMe.x > 0.9D) {
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
    if ((this.CT.Weapons[1] != null) && (((GunGeneric)this.CT.Weapons[1][0]).countBullets() > 0)) {
      f = ((GunGeneric)this.CT.Weapons[1][0]).bulletMassa();
    }

    if ((this.actor instanceof ME_163B1A)) {
      switch (World.Rnd().nextInt(0, 2)) {
      case 0:
        setBomberAttackType(7);
        break;
      default:
        setBomberAttackType(2); break;
      }
    }
    else if (((this.actor instanceof KI_46_OTSUHEI)) && (((GunGeneric)this.CT.Weapons[0][0]).countBullets() > 0))
    {
      setBomberAttackType(10);
    }
    else switch (this.Skill) {
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
    switch (this.Skill) {
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
      if (this.target.crew > 1)
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
    while (localPilot.Wingman != null) {
      localPilot = (Pilot)localPilot.Wingman;
      localPilot.set_maneuver(paramInt);
    }
  }

  private void assignTaskToWingmen(int paramInt)
  {
    Pilot localPilot = this;
    while (localPilot.Wingman != null) {
      localPilot = (Pilot)localPilot.Wingman;
      localPilot.set_task(paramInt);
    }
  }

  public boolean isLeader()
  {
    if ((this.actor instanceof TypeFighter)) return ((Aircraft)this.actor).aircIndex() % 2 == 0;
    return this.Leader == null;
  }

  public boolean isLonely(float paramFloat, boolean paramBoolean)
  {
    if (paramBoolean) {
      if ((this.Leader == null) && (this.Wingman == null)) return true;
      double d = 0.0D;
      if (this.Leader != null) d = this.Leader.Loc.distance(this.Loc);
      if (this.Wingman != null) d = Math.min(this.Wingman.Loc.distance(this.Loc), d);
      return d > paramFloat;
    }
    Actor localActor = NearestTargets.getEnemy(9, -1, this.Loc, paramFloat, 0);
    if (Actor.isValid(localActor)) return localActor.pos.getAbsPoint().distance(this.Loc) > paramFloat;
    return true;
  }
}
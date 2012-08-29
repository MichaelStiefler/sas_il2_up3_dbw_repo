package com.maddox.il2.ai.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FMMath;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeBomber;
import com.maddox.il2.objects.sounds.Voice;

public class AutopilotAI extends Autopilotage
{
  public boolean bWayPoint;
  public boolean bStabAltitude;
  public boolean bStabSpeed;
  public boolean bStabDirection;
  protected double StabAltitude;
  protected double StabSpeed;
  protected double StabDirection;
  protected Pilot FM;
  protected WayPoint WWPoint;
  protected Point3d WPoint = new Point3d();

  private static Point3d P = new Point3d();
  private static Point3d PlLoc = new Point3d();
  private static Orientation O = new Orientation();
  private float Ail;
  private float Pw;
  private float Ru;
  private float Ev;
  private float SA;
  private Vector3d Ve = new Vector3d();

  public AutopilotAI(FlightModel paramFlightModel)
  {
    this.FM = ((Pilot)paramFlightModel);
  }
  public boolean getWayPoint() {
    return this.bWayPoint; } 
  public boolean getStabAltitude() { return this.bStabAltitude; } 
  public boolean getStabSpeed() { return this.bStabSpeed; } 
  public boolean getStabDirection() { return this.bStabDirection;
  }

  public void setWayPoint(boolean paramBoolean)
  {
    this.bWayPoint = paramBoolean;
    if (!paramBoolean) return;
    this.bStabSpeed = false;
    this.bStabAltitude = false;
    this.bStabDirection = false;

    if (this.WWPoint != null) {
      this.WWPoint.getP(this.WPoint);
      this.StabSpeed = this.WWPoint.Speed;
      this.StabAltitude = this.WPoint.jdField_z_of_type_Double;
    } else {
      this.StabAltitude = 1000.0D;
      this.StabSpeed = 80.0D;
    }
    this.StabDirection = O.getAzimut();
  }

  public void setStabAltitude(boolean paramBoolean) {
    this.bStabAltitude = paramBoolean;
    if (!paramBoolean) return;
    this.bWayPoint = false;
    this.FM.getLoc(P);
    this.StabAltitude = P.jdField_z_of_type_Double;

    if (!this.bStabSpeed) this.StabSpeed = this.FM.getSpeed();

    this.Pw = this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl;
  }

  public void setStabAltitude(float paramFloat)
  {
    this.bStabAltitude = true;
    this.bWayPoint = false;
    this.FM.getLoc(P);
    this.StabAltitude = paramFloat;

    if (!this.bStabSpeed) this.StabSpeed = this.FM.getSpeed();

    this.Pw = this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl;
  }

  public void setStabSpeed(boolean paramBoolean)
  {
    this.bStabSpeed = paramBoolean;
    if (!paramBoolean) return;
    this.bWayPoint = false;
    this.StabSpeed = this.FM.getSpeed();
  }

  public void setStabSpeed(float paramFloat)
  {
    this.bStabSpeed = true;
    this.bWayPoint = false;
    this.StabSpeed = (paramFloat / 3.6F);
  }

  public void setStabDirection(boolean paramBoolean)
  {
    this.bStabDirection = paramBoolean;
    if (!paramBoolean) return;
    this.bWayPoint = false;
    O.set(this.FM.jdField_Or_of_type_ComMaddoxIl2EngineOrientation);
    this.StabDirection = O.getAzimut();
    this.Ail = this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl;
  }

  public void setStabDirection(float paramFloat) {
    this.bStabDirection = true;
    this.bWayPoint = false;
    this.StabDirection = ((paramFloat + 3600.0F) % 360.0F);
    this.Ail = this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl;
  }

  public void setStabAll(boolean paramBoolean) {
    this.bWayPoint = false;
    setStabDirection(paramBoolean);
    setStabAltitude(paramBoolean);
    setStabSpeed(paramBoolean);
    setStabDirection(paramBoolean);
  }

  public float getWayPointDistance()
  {
    if (this.WPoint == null) return 1000000.0F;
    this.jdField_way_of_type_ComMaddoxIl2AiWay.curr().getP(P); P.sub(this.FM.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    return (float)Math.sqrt(P.jdField_x_of_type_Double * P.jdField_x_of_type_Double + P.jdField_y_of_type_Double * P.jdField_y_of_type_Double);
  }

  private void voiceCommand(Point3d paramPoint3d1, Point3d paramPoint3d2) {
    this.Ve.sub(paramPoint3d2, paramPoint3d1);
    float f = 57.324841F * (float)Math.atan2(this.Ve.jdField_x_of_type_Double, this.Ve.jdField_y_of_type_Double);
    int i = (int)f;
    i = (i + 180) % 360;
    Voice.speakHeading((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor, i);
    Voice.speakAltitude((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor, (int)paramPoint3d1.jdField_z_of_type_Double);
  }

  public void update(float paramFloat) {
    this.FM.getLoc(PlLoc);
    this.SA = (float)Math.max(this.StabAltitude, Engine.land().HQ_Air(PlLoc.jdField_x_of_type_Double, PlLoc.jdField_y_of_type_Double) + 5.0D);

    if (this.bWayPoint) {
      if ((this.WWPoint != this.jdField_way_of_type_ComMaddoxIl2AiWay.auto(PlLoc)) || (this.jdField_way_of_type_ComMaddoxIl2AiWay.isReached(PlLoc))) {
        this.WWPoint = this.jdField_way_of_type_ComMaddoxIl2AiWay.auto(PlLoc);
        this.WWPoint.getP(this.WPoint);
        if ((((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor).aircIndex() == 0) && (!this.jdField_way_of_type_ComMaddoxIl2AiWay.isLanding())) voiceCommand(this.WPoint, PlLoc);

        this.StabSpeed = (this.WWPoint.Speed - 2.0F * ((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor).aircIndex());
        this.StabAltitude = this.WPoint.jdField_z_of_type_Double;
        Object localObject;
        if (this.WWPoint.Action == 3) {
          localObject = this.WWPoint.getTarget();
          if (localObject != null) {
            this.FM.target_ground = null;
          }
          else if ((((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeBomber)) && (this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0] != null) && (this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][(this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3].length - 1)].haveBullets()))
          {
            this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl = 1.0F;
            Pilot localPilot = this.FM;
            while (localPilot.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel != null) {
              localPilot = (Pilot)localPilot.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel;
              localPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl = 1.0F;
            }
          }
        }
        else {
          if (((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeBomber)) {
            this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl = 0.0F;
            localObject = this.FM;
            while (((Pilot)localObject).jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel != null) {
              localObject = (Pilot)((Pilot)localObject).jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel;
              ((Pilot)localObject).jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl = 0.0F;
            }
          }
          localObject = this.WWPoint.getTarget();
          if ((localObject instanceof Aircraft)) {
            if (((Actor)localObject).getArmy() == this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy()) this.FM.airClient = ((Maneuver)((Aircraft)localObject).jdField_FM_of_type_ComMaddoxIl2FmFlightModel); else {
              this.FM.target = ((Aircraft)localObject).jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
            }
          }
        }

        if (this.jdField_way_of_type_ComMaddoxIl2AiWay.isLanding()) {
          this.FM.getLoc(P);
          if ((this.jdField_way_of_type_ComMaddoxIl2AiWay.Cur() > 3) && (P.jdField_z_of_type_Double > this.WPoint.jdField_z_of_type_Double + 500.0D)) this.jdField_way_of_type_ComMaddoxIl2AiWay.setCur(1);
          if (this.jdField_way_of_type_ComMaddoxIl2AiWay.Cur() == 5) {
            Voice.speakLanding((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor);
          }
          if ((this.jdField_way_of_type_ComMaddoxIl2AiWay.Cur() == 6) || (this.jdField_way_of_type_ComMaddoxIl2AiWay.Cur() == 7)) {
            int i = 0;
            if (Actor.isAlive(this.jdField_way_of_type_ComMaddoxIl2AiWay.landingAirport)) {
              i = this.jdField_way_of_type_ComMaddoxIl2AiWay.landingAirport.landingFeedback(this.WPoint, (Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor);
            }
            if (i == 0) {
              Voice.speakLandingPermited((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor);
            }
            if (i == 1) {
              Voice.speakLandingDenied((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor);
              this.jdField_way_of_type_ComMaddoxIl2AiWay.first();
              this.FM.push(2);
              this.FM.push(2);
              this.FM.push(2);
              this.FM.push(2);
              this.FM.pop();
              Voice.speakGoAround((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor);
              this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 0.4F;
              this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl = 0.0F;
              return;
            }
            if (i == 2) {
              Voice.speakWaveOff((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor);
              if (this.FM.isReadyToReturn()) {
                Voice.speakGoingIn((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor);
                this.FM.AS.setCockpitDoor(this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor, 1);
                this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl = 1.0F;
                return;
              }
              this.jdField_way_of_type_ComMaddoxIl2AiWay.first();
              this.FM.push(2);
              this.FM.push(2);
              this.FM.push(2);
              this.FM.push(2);
              this.FM.pop();
              this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 0.4F;
              this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl = 0.0F;
              Aircraft.debugprintln(this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor, "Going around!.");
              return;
            }
            this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl = 1.0F;
          }
        }
      }

      if ((this.jdField_way_of_type_ComMaddoxIl2AiWay.isLanding()) && (this.jdField_way_of_type_ComMaddoxIl2AiWay.Cur() < 6) && (this.jdField_way_of_type_ComMaddoxIl2AiWay.getCurDist() < 800.0D)) this.jdField_way_of_type_ComMaddoxIl2AiWay.next();

      if (((this.jdField_way_of_type_ComMaddoxIl2AiWay.Cur() == this.jdField_way_of_type_ComMaddoxIl2AiWay.size() - 1) && (getWayPointDistance() < 2000.0F) && (this.jdField_way_of_type_ComMaddoxIl2AiWay.curr().getTarget() == null) && (this.FM.jdField_M_of_type_ComMaddoxIl2FmMass.fuel < 0.2F * this.FM.jdField_M_of_type_ComMaddoxIl2FmMass.maxFuel)) || ((this.jdField_way_of_type_ComMaddoxIl2AiWay.curr().Action == 2) && (!this.jdField_way_of_type_ComMaddoxIl2AiWay.isLanding())))
      {
        Airport localAirport = Airport.makeLandWay(this.FM);
        if (localAirport != null) {
          this.WWPoint = null;
          this.jdField_way_of_type_ComMaddoxIl2AiWay.first();
          update(paramFloat);
          return;
        }
        this.FM.set_task(3);
        this.FM.set_maneuver(49);
        this.FM.setBusy(true);
      }

      this.StabDirection = (-FMMath.RAD2DEG((float)Math.atan2(this.WPoint.jdField_y_of_type_Double - PlLoc.jdField_y_of_type_Double, this.WPoint.jdField_x_of_type_Double - PlLoc.jdField_x_of_type_Double)));
    }

    if ((this.bStabSpeed) || (this.bWayPoint)) {
      this.Pw = (0.3F - 0.04F * (this.FM.getSpeed() - (float)this.StabSpeed));
      if (this.Pw > 1.0F) this.Pw = 1.0F; else if (this.Pw < 0.0F) this.Pw = 0.0F;

    }

    if ((this.bStabAltitude) || (this.bWayPoint)) {
      this.Ev = this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl;

      double d1 = this.SA - this.FM.getAltitude();
      double d2 = 0.0D;
      double d3 = 0.0D;
      float f4;
      if (d1 > -50.0D)
      {
        f4 = 5.0F + 0.00025F * this.FM.getAltitude();
        f4 = (float)(f4 + 0.02D * (250.0D - this.FM.Vmax));
        if (f4 > 14.0F) f4 = 14.0F;
        d2 = Math.min(this.FM.getAOA() - f4, this.FM.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() - 1.0F) * 1.0F * paramFloat + 0.5F * this.FM.getForwAccel();
      }

      if (d1 < 50.0D)
      {
        f4 = -15.0F + this.FM.jdField_M_of_type_ComMaddoxIl2FmMass.mass * 0.00033F;
        if (f4 < -4.0F) f4 = -4.0F;
        d3 = (this.FM.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() - f4) * 0.8F * paramFloat;
      }
      double d4 = 0.01D * (d1 + 50.0D);
      if (d4 > 1.0D) d4 = 1.0D;
      if (d4 < 0.0D) d4 = 0.0D;
      this.Ev = (float)(this.Ev - (d4 * d2 + (1.0D - d4) * d3));

      this.Ev = (float)(this.Ev + (1.0D * this.FM.getW().jdField_y_of_type_Double + 0.5D * this.FM.getAW().jdField_y_of_type_Double));
      if (this.FM.getSpeed() < 1.3F * this.FM.jdField_VminFLAPS_of_type_Float) this.Ev -= 0.004F * paramFloat;

      float f5 = 9.0F * this.FM.getSpeed() / this.FM.jdField_VminFLAPS_of_type_Float;
      if (this.FM.jdField_VminFLAPS_of_type_Float < 28.0F) f5 = 10.0F;
      if (f5 > 25.0F) f5 = 25.0F;
      float f6 = (f5 - this.FM.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage()) * 0.1F;
      float f7 = -15.0F + this.FM.jdField_M_of_type_ComMaddoxIl2FmMass.mass * 0.00033F;
      if (f7 < -4.0F) f7 = -4.0F;
      float f8 = (f7 - this.FM.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage()) * 0.2F;

      if (this.Ev > f6) this.Ev = f6;
      if (this.Ev < f8) this.Ev = f8;

      this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (0.8F * this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl + 0.2F * this.Ev);
    }

    float f1 = 0.0F;

    if ((this.bStabDirection) || (this.bWayPoint))
    {
      f1 = this.FM.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getAzimut();
      float f2 = this.FM.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren();
      f1 = (float)(f1 - this.StabDirection);

      f1 = (f1 + 3600.0F) % 360.0F;
      f2 = (f2 + 3600.0F) % 360.0F;
      if (f1 > 180.0F) f1 -= 360.0F;
      if (f2 > 180.0F) f2 -= 360.0F;

      float f3 = ((this.FM.getSpeed() - this.FM.jdField_VminFLAPS_of_type_Float) * 3.6F + this.FM.getVertSpeed() * 40.0F) * 0.25F;
      if (this.jdField_way_of_type_ComMaddoxIl2AiWay.isLanding()) f3 = 65.0F;
      if (f3 < 15.0F) f3 = 15.0F; else if (f3 > 65.0F) f3 = 65.0F;
      if (f1 < -f3) f1 = -f3; else if (f1 > f3) f1 = f3;

      this.Ail = (-0.01F * (f1 + f2 + 3.0F * (float)this.FM.getW().jdField_x_of_type_Double + 0.5F * (float)this.FM.getAW().jdField_x_of_type_Double));
      if (this.Ail > 1.0F) this.Ail = 1.0F; else if (this.Ail < -1.0F) this.Ail = -1.0F;
      this.WPoint.get(this.Ve);
      this.Ve.sub(this.FM.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      this.FM.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(this.Ve);
      if ((Math.abs(this.Ve.jdField_y_of_type_Double) < 25.0D) && (Math.abs(this.Ve.jdField_x_of_type_Double) < 150.0D))
        this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.01F * this.FM.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
      else
        this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = this.Ail;
      this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += Math.abs(f2) * 0.004F * paramFloat;

      this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl -= this.FM.getAOS() * 0.04F * paramFloat;
    }

    if ((this.bWayPoint) && (this.jdField_way_of_type_ComMaddoxIl2AiWay.isLanding())) {
      if (World.Rnd().nextFloat() < 0.01F) this.FM.doDumpBombsPassively();
      if (this.jdField_way_of_type_ComMaddoxIl2AiWay.Cur() > 5) this.FM.set_maneuver(25);
      this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl -= f1 * 0.04F * paramFloat;
      landUpdate(paramFloat);
    }
  }

  private void landUpdate(float paramFloat)
  {
    if (this.FM.getAltitude() - 10.0F + this.FM.getVertSpeed() * 5.0F - this.SA > 0.0F) {
      if (this.FM.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double > -10.0D) this.FM.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double -= 1.0F * paramFloat;
    }
    else if (this.FM.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double < 10.0D) this.FM.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double += 1.0F * paramFloat;

    if ((this.FM.getAOA() > 11.0F) && (this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > -0.3F))
      this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.3F * paramFloat;
  }
}
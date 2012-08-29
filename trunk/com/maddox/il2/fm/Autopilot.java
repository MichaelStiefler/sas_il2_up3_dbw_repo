package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.AutopilotAI;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Orientation;

public class Autopilot extends AutopilotAI
{
  private static Point3d P = new Point3d();
  private static Orientation O = new Orientation();
  private Stabilizer Hstab = new Stabilizer();
  private Stabilizer Hvstab = new Stabilizer();
  private Stabilizer Vstab = new Stabilizer();
  private Stabilizer Sstab = new Stabilizer();
  private float Ail;
  private float Pw;
  private float Ru;

  Autopilot(FlightModel paramFlightModel)
  {
    super(paramFlightModel);
  }
  public boolean getWayPoint() {
    return this.bWayPoint; } 
  public boolean getStabAltitude() { return this.bStabAltitude; } 
  public boolean getStabSpeed() { return this.bStabSpeed; } 
  public boolean getStabDirection() { return this.bStabDirection;
  }

  public void setWayPoint(boolean paramBoolean)
  {
    super.setWayPoint(paramBoolean);
    this.bWayPoint = paramBoolean;
    if (!paramBoolean) return;
    this.bStabSpeed = false;
    this.bStabAltitude = false;
    this.bStabDirection = false;

    if (this.WWPoint != null) {
      this.WWPoint.getP(this.WPoint);
      this.StabSpeed = this.WWPoint.Speed;
      this.StabAltitude = this.WPoint.z;
    } else {
      this.StabAltitude = 1000.0D;
      this.StabSpeed = 80.0D;
    }
    this.StabDirection = O.getAzimut();
    this.Vstab.set((float)this.StabSpeed, -this.FM.CT.ElevatorControl);
    this.Hvstab.set((float)this.StabAltitude, this.FM.CT.PowerControl);
  }

  public void setStabAltitude(boolean paramBoolean) {
    super.setStabAltitude(paramBoolean);
    this.bStabAltitude = paramBoolean;
    if (!paramBoolean) return;
    this.bWayPoint = false;
    this.FM.getLoc(P);
    this.StabAltitude = P.z;

    if (!this.bStabSpeed) this.StabSpeed = this.FM.getSpeed();

    this.Pw = this.FM.CT.PowerControl;
    this.Hstab.set((float)this.StabAltitude, this.FM.CT.ElevatorControl);
    this.Hvstab.set((float)this.StabAltitude, this.FM.CT.PowerControl);
  }
  public void setStabAltitudeSimple(float paramFloat) {
    super.setStabAltitude(paramFloat);
  }

  public void setStabSpeed(boolean paramBoolean)
  {
    super.setStabSpeed(paramBoolean);
    this.bStabSpeed = paramBoolean;
    if (!paramBoolean) return;
    this.bWayPoint = false;
    this.StabSpeed = this.FM.getSpeed();
    this.Vstab.set((float)this.StabSpeed, -this.FM.CT.ElevatorControl);
  }

  public void setStabSpeed(float paramFloat)
  {
    super.setStabSpeed(paramFloat);
    this.bStabSpeed = true;
    this.bWayPoint = false;
    this.StabSpeed = (paramFloat / 3.6F);
    this.Vstab.set((float)this.StabSpeed, -this.FM.CT.ElevatorControl);
  }

  public void setStabDirection(boolean paramBoolean)
  {
    super.setStabDirection(paramBoolean);
    this.bStabDirection = paramBoolean;
    if (!paramBoolean) return;
    this.bWayPoint = false;
    O.set(this.FM.Or);
    this.StabDirection = O.getAzimut();
    this.Ail = this.FM.CT.AileronControl;
    this.Sstab.set(0.0F, 0.0F);
  }

  public void setStabAll(boolean paramBoolean) {
    super.setStabAll(paramBoolean);
    this.bWayPoint = false;
    setStabDirection(paramBoolean);
    setStabAltitude(paramBoolean);
    setStabSpeed(paramBoolean);
    setStabDirection(paramBoolean);
  }

  public float getWayPointDistance()
  {
    return super.getWayPointDistance();
  }

  public void update(float paramFloat) {
    if (!((RealFlightModel)this.FM).isRealMode()) {
      super.update(paramFloat);
      return;
    }
    this.FM.getLoc(P);
    if (this.bWayPoint) {
      if ((this.WWPoint != this.way.auto(P)) || (this.way.isReached(P))) {
        this.WWPoint = this.way.auto(P);
        this.WWPoint.getP(this.WPoint);
        this.StabSpeed = this.WWPoint.Speed;
        this.StabAltitude = this.WPoint.z;
        this.Vstab.set((float)this.StabSpeed, -this.FM.CT.ElevatorControl);
        this.Hvstab.set((float)this.StabAltitude, this.FM.CT.PowerControl);
      }

      if (World.cur().diffCur.Wind_N_Turbulence) { World.cur(); if ((!World.wind().noWind) && (this.FM.Skill > 0))
        {
          World.cur(); World.wind().getVectorAI(this.WPoint, this.windV);
          this.windV.scale(-1.0D);

          if (this.FM.Skill == 1) {
            this.windV.scale(0.75D);
          }
          this.courseV.set(this.WPoint.x - P.x, this.WPoint.y - P.y, 0.0D);
          this.courseV.normalize();
          this.courseV.scale(this.FM.getSpeed());
          this.courseV.add(this.windV);
          this.StabDirection = (-FMMath.RAD2DEG((float)Math.atan2(this.courseV.y, this.courseV.x))); break label398;
        }
      }
      this.StabDirection = (-FMMath.RAD2DEG((float)Math.atan2(this.WPoint.y - P.y, this.WPoint.x - P.x)));
    } else {
      this.way.auto(P);
    }
    label398: if ((this.FM.isTick(256, 0)) && (!this.FM.actor.isTaskComplete()) && (((this.way.isLast()) && (getWayPointDistance() < 1500.0F)) || (this.way.isLanding())))
    {
      World.onTaskComplete(this.FM.actor);
    }
    if ((this.bStabSpeed) || (this.bWayPoint)) {
      this.FM.CT.ElevatorControl = (-this.Vstab.getOutput(this.FM.getSpeed()));
      if ((this.FM.Or.getTangage() > 10.0F) && (this.FM.CT.ElevatorControl > 0.0F)) this.FM.CT.ElevatorControl = 0.0F;
      if ((this.FM.Or.getTangage() < -10.0F) && (this.FM.CT.ElevatorControl < -0.0F)) this.FM.CT.ElevatorControl = 0.0F;

      if ((this.bStabAltitude) || (this.bWayPoint)) {
        this.Pw = this.Hvstab.getOutput(this.FM.getAltitude());
        if (this.Pw < 0.0F) this.Pw = 0.0F;
        this.FM.CT.setPowerControl(this.Pw);
      }
    } else if (this.bStabAltitude) {
      this.FM.CT.ElevatorControl = this.Hstab.getOutput(this.FM.getAltitude());
    }

    if ((this.bStabDirection) || (this.bWayPoint))
    {
      float f2 = this.FM.Or.getAzimut();
      float f1 = this.FM.Or.getKren();
      f2 = (float)(f2 - this.StabDirection);

      f2 = (f2 + 3600.0F) % 360.0F;
      f1 = (f1 + 3600.0F) % 360.0F;
      if (f2 > 180.0F) f2 -= 360.0F;
      if (f1 > 180.0F) f1 -= 360.0F;

      float f3 = this.FM.getSpeedKMH() * 0.15F + this.FM.getVertSpeed();
      if (f3 > 70.0F) f3 = 70.0F;
      if (f2 < -f3) f2 = -f3;
      else if (f2 > f3) f2 = f3;

      this.Ail = (-0.05F * (f2 + f1));
      if (this.Ail > 1.0F) this.Ail = 1.0F; else if (this.Ail < -1.0F) this.Ail = -1.0F;
      this.FM.CT.AileronControl = this.Ail;

      this.Ru = (-this.FM.getAOS() * 0.2F);
      if (this.Ru > 1.0F) this.Ru = 1.0F; else if (this.Ru < -1.0F) this.Ru = -1.0F;
      this.FM.CT.RudderControl += this.Ru * 0.0003F;
    }
  }
}
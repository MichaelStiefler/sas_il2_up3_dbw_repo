package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
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
    return this.jdField_bWayPoint_of_type_Boolean; } 
  public boolean getStabAltitude() { return this.jdField_bStabAltitude_of_type_Boolean; } 
  public boolean getStabSpeed() { return this.jdField_bStabSpeed_of_type_Boolean; } 
  public boolean getStabDirection() { return this.jdField_bStabDirection_of_type_Boolean;
  }

  public void setWayPoint(boolean paramBoolean)
  {
    super.setWayPoint(paramBoolean);
    this.jdField_bWayPoint_of_type_Boolean = paramBoolean;
    if (!paramBoolean) return;
    this.jdField_bStabSpeed_of_type_Boolean = false;
    this.jdField_bStabAltitude_of_type_Boolean = false;
    this.jdField_bStabDirection_of_type_Boolean = false;

    if (this.jdField_WWPoint_of_type_ComMaddoxIl2AiWayPoint != null) {
      this.jdField_WWPoint_of_type_ComMaddoxIl2AiWayPoint.getP(this.jdField_WPoint_of_type_ComMaddoxJGPPoint3d);
      this.jdField_StabSpeed_of_type_Double = this.jdField_WWPoint_of_type_ComMaddoxIl2AiWayPoint.Speed;
      this.jdField_StabAltitude_of_type_Double = this.jdField_WPoint_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double;
    } else {
      this.jdField_StabAltitude_of_type_Double = 1000.0D;
      this.jdField_StabSpeed_of_type_Double = 80.0D;
    }
    this.jdField_StabDirection_of_type_Double = O.getAzimut();
    this.Vstab.set((float)this.jdField_StabSpeed_of_type_Double, -this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl);
    this.Hvstab.set((float)this.jdField_StabAltitude_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl);
  }

  public void setStabAltitude(boolean paramBoolean) {
    super.setStabAltitude(paramBoolean);
    this.jdField_bStabAltitude_of_type_Boolean = paramBoolean;
    if (!paramBoolean) return;
    this.jdField_bWayPoint_of_type_Boolean = false;
    this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.getLoc(P);
    this.jdField_StabAltitude_of_type_Double = P.jdField_z_of_type_Double;

    if (!this.jdField_bStabSpeed_of_type_Boolean) this.jdField_StabSpeed_of_type_Double = this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.getSpeed();

    this.Pw = this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl;
    this.Hstab.set((float)this.jdField_StabAltitude_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl);
    this.Hvstab.set((float)this.jdField_StabAltitude_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl);
  }
  public void setStabAltitudeSimple(float paramFloat) {
    super.setStabAltitude(paramFloat);
  }

  public void setStabSpeed(boolean paramBoolean)
  {
    super.setStabSpeed(paramBoolean);
    this.jdField_bStabSpeed_of_type_Boolean = paramBoolean;
    if (!paramBoolean) return;
    this.jdField_bWayPoint_of_type_Boolean = false;
    this.jdField_StabSpeed_of_type_Double = this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.getSpeed();
    this.Vstab.set((float)this.jdField_StabSpeed_of_type_Double, -this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl);
  }

  public void setStabSpeed(float paramFloat)
  {
    super.setStabSpeed(paramFloat);
    this.jdField_bStabSpeed_of_type_Boolean = true;
    this.jdField_bWayPoint_of_type_Boolean = false;
    this.jdField_StabSpeed_of_type_Double = (paramFloat / 3.6F);
    this.Vstab.set((float)this.jdField_StabSpeed_of_type_Double, -this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl);
  }

  public void setStabDirection(boolean paramBoolean)
  {
    super.setStabDirection(paramBoolean);
    this.jdField_bStabDirection_of_type_Boolean = paramBoolean;
    if (!paramBoolean) return;
    this.jdField_bWayPoint_of_type_Boolean = false;
    O.set(this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_Or_of_type_ComMaddoxIl2EngineOrientation);
    this.jdField_StabDirection_of_type_Double = O.getAzimut();
    this.Ail = this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl;
    this.Sstab.set(0.0F, 0.0F);
  }

  public void setStabAll(boolean paramBoolean) {
    super.setStabAll(paramBoolean);
    this.jdField_bWayPoint_of_type_Boolean = false;
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
    if (!((RealFlightModel)this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot).isRealMode()) {
      super.update(paramFloat);
      return;
    }
    this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.getLoc(P);
    if (this.jdField_bWayPoint_of_type_Boolean) {
      if ((this.jdField_WWPoint_of_type_ComMaddoxIl2AiWayPoint != this.jdField_way_of_type_ComMaddoxIl2AiWay.auto(P)) || (this.jdField_way_of_type_ComMaddoxIl2AiWay.isReached(P))) {
        this.jdField_WWPoint_of_type_ComMaddoxIl2AiWayPoint = this.jdField_way_of_type_ComMaddoxIl2AiWay.auto(P);
        this.jdField_WWPoint_of_type_ComMaddoxIl2AiWayPoint.getP(this.jdField_WPoint_of_type_ComMaddoxJGPPoint3d);
        this.jdField_StabSpeed_of_type_Double = this.jdField_WWPoint_of_type_ComMaddoxIl2AiWayPoint.Speed;
        this.jdField_StabAltitude_of_type_Double = this.jdField_WPoint_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double;
        this.Vstab.set((float)this.jdField_StabSpeed_of_type_Double, -this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl);
        this.Hvstab.set((float)this.jdField_StabAltitude_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl);
      }
      this.jdField_StabDirection_of_type_Double = (-FMMath.RAD2DEG((float)Math.atan2(this.jdField_WPoint_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double - P.jdField_y_of_type_Double, this.jdField_WPoint_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double - P.jdField_x_of_type_Double)));
    }
    else {
      this.jdField_way_of_type_ComMaddoxIl2AiWay.auto(P);
    }
    if ((this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.isTick(256, 0)) && (!this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_actor_of_type_ComMaddoxIl2EngineActor.isTaskComplete()) && (((this.jdField_way_of_type_ComMaddoxIl2AiWay.isLast()) && (getWayPointDistance() < 1500.0F)) || (this.jdField_way_of_type_ComMaddoxIl2AiWay.isLanding())))
    {
      World.onTaskComplete(this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_actor_of_type_ComMaddoxIl2EngineActor);
    }
    if ((this.jdField_bStabSpeed_of_type_Boolean) || (this.jdField_bWayPoint_of_type_Boolean)) {
      this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (-this.Vstab.getOutput(this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.getSpeed()));
      if ((this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() > 10.0F) && (this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > 0.0F)) this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 0.0F;
      if ((this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() < -10.0F) && (this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl < -0.0F)) this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 0.0F;

      if ((this.jdField_bStabAltitude_of_type_Boolean) || (this.jdField_bWayPoint_of_type_Boolean)) {
        this.Pw = this.Hvstab.getOutput(this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.getAltitude());
        if (this.Pw < 0.0F) this.Pw = 0.0F;
        this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl = this.Pw;
      }
    } else if (this.jdField_bStabAltitude_of_type_Boolean) {
      this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = this.Hstab.getOutput(this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.getAltitude());
    }

    if ((this.jdField_bStabDirection_of_type_Boolean) || (this.jdField_bWayPoint_of_type_Boolean))
    {
      float f2 = this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getAzimut();
      float f1 = this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren();
      f2 = (float)(f2 - this.jdField_StabDirection_of_type_Double);

      f2 = (f2 + 3600.0F) % 360.0F;
      f1 = (f1 + 3600.0F) % 360.0F;
      if (f2 > 180.0F) f2 -= 360.0F;
      if (f1 > 180.0F) f1 -= 360.0F;

      float f3 = this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.getSpeedKMH() * 0.15F + this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.getVertSpeed();
      if (f3 > 70.0F) f3 = 70.0F;
      if (f2 < -f3) f2 = -f3;
      else if (f2 > f3) f2 = f3;

      this.Ail = (-0.05F * (f2 + f1));
      if (this.Ail > 1.0F) this.Ail = 1.0F; else if (this.Ail < -1.0F) this.Ail = -1.0F;
      this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = this.Ail;

      this.Ru = (-this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.getAOS() * 0.2F);
      if (this.Ru > 1.0F) this.Ru = 1.0F; else if (this.Ru < -1.0F) this.Ru = -1.0F;
      this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (this.jdField_FM_of_type_ComMaddoxIl2AiAirPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl + this.Ru * 0.0003F);
    }
  }
}
package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Main3D.RenderCockpit;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.effects.OverLoad;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import java.io.PrintStream;

public class CockpitPilot extends Cockpit
{
  private String[] hotKeyEnvs = { "pilot", "move" };
  protected float stepAzimut;
  protected float stepTangage;
  protected float minMaxAzimut;
  protected float maxTangage;
  protected float minTangage;
  protected Point3d cameraCenter;
  protected Point3d cameraAim;
  protected Point3d cameraUp;
  private double pictBall;
  private long oldBallTime;
  private float saveZN;
  protected float normZN;
  protected float gsZN;

  public boolean isEnableHotKeysOnOutsideView()
  {
    return true;
  }

  public String[] getHotKeyEnvs()
  {
    return this.hotKeyEnvs;
  }

  protected boolean doFocusEnter()
  {
    HookPilot localHookPilot = HookPilot.current;
    Aircraft localAircraft = aircraft();
    Main3D localMain3D = Main3D.cur3D();
    localHookPilot.setCenter(this.cameraCenter);
    localHookPilot.setAim(this.cameraAim);
    localHookPilot.setUp(this.cameraUp);
    if ((!NetMissionTrack.isPlaying()) || (NetMissionTrack.playingOriginalVersion() > 101))
    {
      localHookPilot.setSteps(this.stepAzimut, this.stepTangage);
      localHookPilot.setMinMax(this.minMaxAzimut, this.minTangage, this.maxTangage);
    }
    else {
      localHookPilot.setSteps(45.0F, 30.0F);
      localHookPilot.setMinMax(135.0F, -60.0F, 90.0F);
    }
    localHookPilot.reset();
    localHookPilot.use(true);
    localAircraft.setAcoustics(this.acoustics);
    if (this.acoustics != null)
    {
      localAircraft.enableDoorSnd(true);
      if (this.acoustics.getEnvNum() == 2)
        localAircraft.setDoorSnd(1.0F);
    }
    localMain3D.camera3D.pos.setRel(new Point3d(), new Orient());
    localMain3D.camera3D.pos.setBase(localAircraft, localHookPilot, false);
    localMain3D.camera3D.pos.resetAsBase();
    this.pos.resetAsBase();
    aircraft().setMotorPos(localMain3D.camera3D.pos.getAbsPoint());
    localMain3D.cameraCockpit.pos.setRel(new Point3d(), new Orient());
    localMain3D.cameraCockpit.pos.setBase(this, localHookPilot, false);
    localMain3D.cameraCockpit.pos.resetAsBase();
    localMain3D.overLoad.setShow(true);
    localMain3D.renderCockpit.setShow(true);
    localAircraft.drawing(!isNullShow());

    this.saveZN = ZNear(HookPilot.current.isAim() ? this.gsZN : this.normZN);
    return true;
  }

  protected void doFocusLeave()
  {
    this.saveZN = ZNear(this.saveZN);

    HookPilot localHookPilot = HookPilot.current;
    Aircraft localAircraft = aircraft();
    Main3D localMain3D = Main3D.cur3D();
    localHookPilot.use(false);
    localMain3D.camera3D.pos.setRel(new Point3d(), new Orient());
    localMain3D.camera3D.pos.setBase(null, null, false);
    localMain3D.cameraCockpit.pos.setRel(new Point3d(), new Orient());
    localMain3D.cameraCockpit.pos.setBase(null, null, false);
    localMain3D.overLoad.setShow(false);
    localMain3D.renderCockpit.setShow(false);
    if (Actor.isValid(localAircraft))
      localAircraft.drawing(true);
    if (localAircraft != null)
      localAircraft.setAcoustics(null);
    localAircraft.enableDoorSnd(false);
    aircraft().setMotorPos(null);
  }

  public boolean existPadlock()
  {
    return true;
  }

  public boolean isPadlock()
  {
    if (!isFocused())
    {
      return false;
    }

    HookPilot localHookPilot = HookPilot.current;
    return localHookPilot.isPadlock();
  }

  public Actor getPadlockEnemy()
  {
    if (!isFocused())
    {
      return null;
    }

    HookPilot localHookPilot = HookPilot.current;
    return localHookPilot.isPadlock() ? localHookPilot.getEnemy() : null;
  }

  public boolean startPadlock(Actor paramActor)
  {
    if (!isFocused())
    {
      return false;
    }

    HookPilot localHookPilot = HookPilot.current;
    return localHookPilot.startPadlock(paramActor);
  }

  public void stopPadlock()
  {
    if (!isFocused())
    {
      return;
    }

    HookPilot localHookPilot = HookPilot.current;
    localHookPilot.stopPadlock();
  }

  public void endPadlock()
  {
    if (!isFocused())
    {
      return;
    }

    HookPilot localHookPilot = HookPilot.current;
    localHookPilot.endPadlock();
  }

  public void setPadlockForward(boolean paramBoolean)
  {
    if (!isFocused())
    {
      return;
    }

    HookPilot localHookPilot = HookPilot.current;
    localHookPilot.setForward(paramBoolean);
  }

  public boolean isToggleAim()
  {
    if (!isFocused())
    {
      return false;
    }

    HookPilot localHookPilot = HookPilot.current;
    return localHookPilot.isAim();
  }

  public void doToggleAim(boolean paramBoolean)
  {
    if (!isFocused())
    {
      return;
    }

    HookPilot localHookPilot = HookPilot.current;
    localHookPilot.doAim(paramBoolean);

    if (paramBoolean)
      ZNear(this.gsZN);
    else
      ZNear(this.normZN);
  }

  public boolean isToggleUp()
  {
    if (!isFocused())
    {
      return false;
    }

    HookPilot localHookPilot = HookPilot.current;
    return localHookPilot.isUp();
  }

  public void doToggleUp(boolean paramBoolean)
  {
    if (!isFocused())
      return;
    if ((paramBoolean) && (this.cameraUp == null))
    {
      return;
    }

    HookPilot localHookPilot = HookPilot.current;
    localHookPilot.doUp(paramBoolean);
  }

  public CockpitPilot(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
    this.stepAzimut = 45.0F;
    this.stepTangage = 30.0F;
    this.minMaxAzimut = 145.0F;
    this.maxTangage = 90.0F;
    this.minTangage = -60.0F;
    this.cameraCenter = new Point3d();
    this.pictBall = 0.0D;
    this.oldBallTime = 0L;
    HookNamed localHookNamed1 = new HookNamed(this.mesh, "CAMERA");
    Loc localLoc = new Loc();
    localHookNamed1.computePos(this, this.pos.getAbs(), localLoc);
    localLoc.get(this.cameraCenter);
    try
    {
      HookNamed localHookNamed2 = new HookNamed(this.mesh, "CAMERAAIM");
      localLoc.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
      localHookNamed2.computePos(this, this.pos.getAbs(), localLoc);
      this.cameraAim = new Point3d();
      localLoc.get(this.cameraAim);
    }
    catch (Exception localException1)
    {
      System.out.println(localException1.getMessage());
      localException1.printStackTrace();
    }
    try
    {
      HookNamed localHookNamed3 = new HookNamed(this.mesh, "CAMERAUP");
      localLoc.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
      localHookNamed3.computePos(this, this.pos.getAbs(), localLoc);
      this.cameraUp = new Point3d();
      localLoc.get(this.cameraUp);
    } catch (Exception localException2) {
    }
    this.pos.setBase(aircraft(), new Cockpit.HookOnlyOrient(), false);
    interpPut(new Interpolater(), "CockpitPilot", Time.current(), null);
    if (HookPilot.current != null) {
      HookPilot.current.doUp(false);
    }

    this.normZN = Property.floatValue(getClass(), "normZN", -1.0F);
    this.gsZN = Property.floatValue(getClass(), "gsZN", -1.0F);
  }

  protected float getBall(double paramDouble)
  {
    double d1 = 0.0D;
    long l1 = Time.current();
    long l2 = l1 - this.oldBallTime;
    this.oldBallTime = l1;
    if (l2 > 200L)
      l2 = 200L;
    double d2 = 0.00038D * l2;
    double d3;
    if (-this.fm.getBallAccel().z > 0.001D)
    {
      d3 = Math.toDegrees(Math.atan2(this.fm.getBallAccel().y, -this.fm.getBallAccel().z));
      if (d3 > 20.0D) {
        d3 = 20.0D;
      }
      else if (d3 < -20.0D)
        d3 = -20.0D;
      this.pictBall = ((1.0D - d2) * this.pictBall + d2 * d3);
    }
    else
    {
      if (this.pictBall > 0.0D)
        d3 = 20.0D;
      else
        d3 = -20.0D;
      this.pictBall = ((1.0D - d2) * this.pictBall + d2 * d3);
    }
    if (this.pictBall > paramDouble) {
      this.pictBall = paramDouble;
    }
    else if (this.pictBall < -paramDouble)
      this.pictBall = (-paramDouble);
    return (float)this.pictBall;
  }

  private float ZNear(float paramFloat)
  {
    if (paramFloat < 0.0F) {
      return -1.0F;
    }
    Camera3D localCamera3D = (Camera3D)Actor.getByName("camera");
    float f = localCamera3D.ZNear;

    localCamera3D.ZNear = paramFloat;
    return f;
  }

  static
  {
    Property.set(CockpitPilot.class, "astatePilotIndx", 0);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitPilot.this.isPadlock())
        HookPilot.current.checkPadlockState();
      return true;
    }
  }
}
package com.maddox.il2.engine.hotkey;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.HookRender;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.VisibilityChecker;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.effects.SpritesFog;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Time;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class HookPilot extends HookRender
{
  private float stepAzimut = 45.0F;
  private float stepTangage = 30.0F;
  private float maxAzimut = 155.0F;
  private float maxTangage = 89.0F;
  private float minTangage = -60.0F;
  private float Azimut = 0.0F;
  private float Tangage = 0.0F;
  private float _Azimut = 0.0F;
  private float _Tangage = 0.0F;
  private long rprevTime = 0L;
  private float Px;
  private float Py;
  private float Pz;
  private float azimPadlock;
  private float tangPadlock;
  private float timeKoof = 1.0F;
  private Orient o = new Orient();
  private Orient op = new Orient();
  private Loc le = new Loc();
  private Point3d pe = new Point3d();
  private Point3d pEnemyAbs = new Point3d();
  private Vector3d Ve = new Vector3d();
  private Point3d pAbs = new Point3d();
  private Orient oAbs = new Orient();
  private Actor target = null;
  private Actor target2 = null;
  private Actor enemy = null;
  private long stamp = -1L;
  private long prevTime = -1L;
  private long roolTime = -1L;
  private boolean bUse = false;
  private boolean bPadlock = true;
  private long tPadlockEnd = -1L;
  private long tPadlockEndLen = 0L;
  private boolean bPadlockEnd = false;
  private boolean bForward = false;
  private boolean bAim = false;
  private boolean bUp = false;
  private boolean bVisibleEnemy = true;
  private boolean bSimpleUse = false;

  private Point3d pCenter = new Point3d();
  private Point3d pAim = new Point3d();
  private Point3d pUp = new Point3d();

  private static RangeRandom rnd = new RangeRandom();

  private static Point3d P = new Point3d();
  private static Vector3d tmpA = new Vector3d();
  private static Vector3d tmpB = new Vector3d();
  private static Vector3d headShift = new Vector3d();
  private static Vector3d counterForce = new Vector3d();
  private static long oldHeadTime = 0L;
  private static double oldWx = 0.0D;
  private static double oldWy = 0.0D;

  private boolean bUseMouse = true;
  private long timeViewSet = -2000L;
  public static HookPilot current;
  private static Orient oTmp = new Orient();
  private static Orient oTmp2 = new Orient();
  private static float shakeLVL;

  public void resetGame()
  {
    this.enemy = null;
    this.bUp = false;
  }

  private Point3d pCamera()
  {
    if (this.bAim) return this.pAim;
    if (this.bUp) return this.pUp;
    return this.pCenter;
  }

  public void setSimpleUse(boolean paramBoolean) {
    this.bSimpleUse = paramBoolean;
  }
  public void setSimpleAimOrient(float paramFloat1, float paramFloat2, float paramFloat3) {
    this.o.set(paramFloat1, paramFloat2, paramFloat3);
    this.le.set(pCamera(), this.o);
  }

  public void setCenter(Point3d paramPoint3d) {
    this.pCenter.set(paramPoint3d);
  }
  public void setAim(Point3d paramPoint3d) {
    this.pAim.set(this.pCenter);
    if (paramPoint3d != null)
      this.pAim.set(paramPoint3d);
    setUp(paramPoint3d);
  }
  public void setUp(Point3d paramPoint3d) {
    this.pUp.set(this.pCenter);
    if (paramPoint3d != null)
      this.pUp.set(paramPoint3d); 
  }

  public void setSteps(float paramFloat1, float paramFloat2) {
    this.stepAzimut = paramFloat1;
    this.stepTangage = paramFloat2;
  }
  public void setMinMax(float paramFloat1, float paramFloat2, float paramFloat3) {
    this.maxAzimut = paramFloat1;
    this.minTangage = paramFloat2;
    this.maxTangage = paramFloat3;
  }

  public void setForward(boolean paramBoolean) {
    this.bForward = paramBoolean;
  }
  public void endPadlock() {
    this.bPadlockEnd = true;
  }

  private void _reset() {
    if (!AircraftHotKeys.bFirstHotCmd) {
      this._Azimut = (this.Azimut = 0.0F);
      this._Tangage = (this.Tangage = 0.0F);
      this.o.set(0.0F, 0.0F, 0.0F);
      this.le.set(pCamera(), this.o);
    }
    this.Px = (this.Py = this.Pz = 0.0F);

    this.azimPadlock = 0.0F;
    this.tangPadlock = 0.0F;
    this.timeKoof = 1.0F;
    this.prevTime = -1L;
    this.roolTime = -1L;

    this.enemy = null;

    this.bPadlock = false;
    this.tPadlockEnd = -1L;
    this.tPadlockEndLen = 0L;
    this.bPadlockEnd = false;
    this.bForward = false;
    if (!Main3D.cur3D().isDemoPlaying())
      new MsgAction(64, 0.0D) {
        public void doAction() { HotKeyCmd.exec("misc", "target_"); } } ;
    this.timeViewSet = -2000L;

    headShift.set(0.0D, 0.0D, 0.0D);
    counterForce.set(0.0D, 0.0D, 0.0D);
    oldHeadTime = -1L;
    oldWx = 0.0D;
    oldWy = 0.0D;
  }

  public void saveRecordedStates(PrintWriter paramPrintWriter) throws Exception {
    paramPrintWriter.println(this.Azimut);
    paramPrintWriter.println(this._Azimut);
    paramPrintWriter.println(this.Tangage);
    paramPrintWriter.println(this._Tangage);
    paramPrintWriter.println(this.o.azimut());
    paramPrintWriter.println(this.o.tangage());
  }
  public void loadRecordedStates(BufferedReader paramBufferedReader) throws Exception {
    this.Azimut = Float.parseFloat(paramBufferedReader.readLine());
    this._Azimut = Float.parseFloat(paramBufferedReader.readLine());
    this.Tangage = Float.parseFloat(paramBufferedReader.readLine());
    this._Tangage = Float.parseFloat(paramBufferedReader.readLine());
    this.o.set(Float.parseFloat(paramBufferedReader.readLine()), Float.parseFloat(paramBufferedReader.readLine()), 0.0F);
    this.le.set(pCamera(), this.o);
  }

  public void reset() {
    this.stamp = -1L;

    _reset();
  }

  private void setTimeKoof() {
    long l = Time.current();
    if (this.prevTime == -1L)
      this.timeKoof = 1.0F;
    else
      this.timeKoof = ((float)(l - this.prevTime) / 30.0F);
    this.prevTime = l;
  }

  private void headRoll(Aircraft paramAircraft)
  {
    long l1 = this.roolTime - this.stamp;
    if ((l1 >= 0L) && (l1 < 50L))
      return;
    this.roolTime = this.stamp;

    shakeLVL = ((RealFlightModel)paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).shakeLevel;
    float f2;
    float f1;
    float f3;
    if (World.cur().diffCur.Head_Shake) {
      long l2 = Time.current();
      if (oldHeadTime == -1L) {
        oldHeadTime = Time.current();
        oldWx = paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getW().jdField_x_of_type_Double;
        oldWy = paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getW().jdField_y_of_type_Double;
      }
      long l3 = l2 - oldHeadTime;
      oldHeadTime = l2;
      if (l3 > 200L) l3 = 200L;
      double d2 = 0.003D * l3;
      double d3 = paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getW().jdField_x_of_type_Double - oldWx;
      double d4 = paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getW().jdField_y_of_type_Double - oldWy;
      oldWx = paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getW().jdField_x_of_type_Double;
      if (d2 < 0.001D) d3 = 0.0D; else
        d3 /= d2;
      oldWy = paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getW().jdField_y_of_type_Double;
      if (d2 < 0.001D) d4 = 0.0D; else {
        d4 /= d2;
      }
      if (paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Gears.onGround()) {
        tmpA.set(0.0D, 0.0D, 0.0D);

        headShift.scale(1.0D - d2);
        tmpA.scale(d2);
        headShift.add(tmpA);

        f2 = (float)headShift.jdField_y_of_type_Double;
        f1 = (float)(headShift.jdField_x_of_type_Double + 0.03F * shakeLVL * (0.5F - rnd.nextFloat()));
        f3 = (float)(headShift.jdField_z_of_type_Double + 1.2F * shakeLVL * (0.5F - rnd.nextFloat()));
      }
      else {
        tmpB.set(0.0D, 0.0D, 0.0D);
        tmpA.set(paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAccel());
        paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Or.transformInv(tmpA);
        tmpA.scale(-0.6D);
        if (tmpA.jdField_z_of_type_Double > 0.0D) tmpA.jdField_z_of_type_Double *= 0.8D;
        tmpB.add(tmpA);

        counterForce.scale(1.0D - 0.2D * d2);
        tmpA.scale(0.2D * d2);
        counterForce.add(tmpA);

        tmpB.sub(counterForce);
        counterForce.scale(1.0D - 0.05D * d2);
        if (counterForce.jdField_z_of_type_Double > 0.0D) counterForce.jdField_z_of_type_Double *= (1.0D - 0.08D * d2);

        tmpB.scale(0.08D);

        tmpA.set(-0.7D * d4, d3, 0.0D);
        tmpA.add(tmpB);

        headShift.scale(1.0D - d2);
        tmpA.scale(d2);
        headShift.add(tmpA);

        f2 = (float)headShift.jdField_y_of_type_Double;
        f1 = (float)(headShift.jdField_x_of_type_Double + 0.3F * shakeLVL * (0.5F - rnd.nextFloat()));
        f3 = (float)(headShift.jdField_z_of_type_Double + 0.4F * shakeLVL * (0.5F - rnd.nextFloat()));
      }
    } else {
      f2 = 0.0F;
      f1 = 0.0F;
      f3 = 0.0F;
    }
    if (World.cur().diffCur.Wind_N_Turbulence) {
      float f4 = SpritesFog.dynamicFogAlpha;
      double d1 = paramAircraft.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().jdField_z_of_type_Double;
      if ((f4 > 0.01F) && (d1 > 300.0D) && (d1 < 2500.0D)) {
        float f5 = paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed();
        if (f5 > 138.88889F) f5 = 138.88889F;
        f5 -= 55.555557F;
        if (f5 < 0.0F) f5 = 0.0F;
        f5 /= 83.333336F;
        f2 += f5 * 0.05F * f4 * (0.5F - rnd.nextFloat());
        f3 += f5 * 0.3F * f4 * (0.5F - rnd.nextFloat());
      }
    }
    if ((f1 >= 1.0F) || (f1 <= -1.0F))
    {
      if (f1 < -1.0F) f1 = -1.0F; else if (f1 > 1.0F) f1 = 1.0F; else
        f1 = 0.0F;
    }
    if ((f2 >= 1.0F) || (f2 <= -1.0F))
    {
      if (f2 < -1.0F) f2 = -1.0F; else if (f2 > 1.0F) f2 = 1.0F; else
        f2 = 0.0F;
    }
    if ((f3 >= 1.0F) || (f3 <= -1.0F))
    {
      if (f3 < -1.0F) f3 = -1.0F; else if (f3 > 1.0F) f3 = 1.0F; else
        f3 = 0.0F;
    }
    P.set(this.Px += (f1 * (this.bAim ? 0.01F : 0.03F) - this.Px) * 0.4F, this.Py += (f2 * (this.bAim ? 0.01F : 0.03F) - this.Py) * 0.4F, this.Pz += (f3 * (this.bAim ? 0.01F : 0.03F) - this.Pz) * 0.4F);

    oTmp.set((float)(6.0D * P.jdField_y_of_type_Double), (float)(6.0D * P.jdField_z_of_type_Double), (float)(60.0D * P.jdField_y_of_type_Double));
    oTmp.increment(0.31F * rnd.nextFloat(-shakeLVL, shakeLVL), 0.31F * rnd.nextFloat(-shakeLVL, shakeLVL), 0.54F * rnd.nextFloat(-shakeLVL, shakeLVL));
  }

  public boolean isPadlock()
  {
    return this.bPadlock;
  }
  public Actor getEnemy() {
    return this.enemy;
  }
  public void stopPadlock() {
    if (!this.bPadlock) return;
    this.stamp = -1L;
    _reset();
  }
  public boolean startPadlock(Actor paramActor) {
    if ((!this.bUse) || (this.bSimpleUse)) return false;
    if (!Actor.isValid(paramActor)) {
      this.bPadlock = false;
      return false;
    }
    Aircraft localAircraft = World.getPlayerAircraft();
    if (!Actor.isValid(localAircraft)) {
      this.bPadlock = false;
      return false;
    }

    this.enemy = paramActor;

    this.Azimut = this._Azimut;
    this.Tangage = this._Tangage;
    this.bPadlock = true;
    this.bPadlockEnd = false;
    this.bVisibleEnemy = true;

    localAircraft.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this.pAbs, this.oAbs);
    Camera3D localCamera3D = (Camera3D)this.target2;
    localCamera3D.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this.o);
    this.o.sub(this.oAbs);
    this.azimPadlock = this.o.getAzimut();
    this.tangPadlock = this.o.getTangage();
    this.azimPadlock = ((this.azimPadlock + 3600.0F) % 360.0F);
    if (this.azimPadlock > 180.0F) this.azimPadlock -= 360.0F;
    this.stamp = -1L;

    if (!Main3D.cur3D().isDemoPlaying())
      new MsgAction(64, 0.0D) {
        public void doAction() { HotKeyCmd.exec("misc", "target_"); } } ;
    return true;
  }
  public boolean isAim() {
    return this.bAim;
  }
  public void doAim(boolean paramBoolean) {
    if (this.bAim == paramBoolean) return;
    this.bAim = paramBoolean;
  }

  public boolean isUp()
  {
    return this.bUp;
  }
  public void doUp(boolean paramBoolean) {
    if (this.bUp == paramBoolean) return;
    this.bUp = paramBoolean;
  }

  private float bvalue(float paramFloat1, float paramFloat2, long paramLong)
  {
    float f = HookView.koofSpeed * (float)paramLong / 30.0F;
    if (paramFloat1 == paramFloat2) return paramFloat1;
    if (paramFloat1 > paramFloat2) {
      if (paramFloat1 < paramFloat2 + f) return paramFloat1;
      return paramFloat2 + f;
    }
    if (paramFloat1 > paramFloat2 - f) return paramFloat1;
    return paramFloat2 - f;
  }

  public boolean computeRenderPos(Actor paramActor, Loc paramLoc1, Loc paramLoc2) {
    if (this.bUse) {
      if (this.bPadlock) {
        Aircraft localAircraft1 = World.getPlayerAircraft();
        if (!Actor.isValid(localAircraft1)) {
          reset();
          paramLoc2.add(this.le, paramLoc1);
          return true;
        }
        long l2 = Time.current();
        if ((l2 != this.stamp) && (this.enemy.jdField_pos_of_type_ComMaddoxIl2EngineActorPos != null) && (localAircraft1.jdField_pos_of_type_ComMaddoxIl2EngineActorPos != null)) {
          this.stamp = l2;
          setTimeKoof();
          this.enemy.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRender(this.pe);
          this.pEnemyAbs.set(this.pe);
          localAircraft1.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRender(this.pAbs, this.oAbs);
          this.Ve.sub(this.pe, this.pAbs);
          this.o.setAT0(this.Ve);
          if ((World.cur().diffCur.Head_Shake) || (World.cur().diffCur.Wind_N_Turbulence)) {
            headRoll(localAircraft1);
            this.pe.add(pCamera(), P);
            this.le.set(this.pe);
          } else {
            this.le.set(pCamera());
          }
          this.o.sub(this.oAbs);
          padlockSet(this.o);
          this.op.set(this.o);
          this.op.add(this.oAbs);
        }
        paramLoc2.add(this.le, paramLoc1);
        paramLoc2.set(this.op);
        return true;
      }
      long l1 = Time.currentReal();
      if ((l1 != this.rprevTime) && (!this.bSimpleUse)) {
        long l3 = l1 - this.rprevTime;
        this.rprevTime = l1;
        if ((this._Azimut != this.Azimut) || (this._Tangage != this.Tangage)) {
          this.Azimut = bvalue(this._Azimut, this.Azimut, l3);
          this.Tangage = bvalue(this._Tangage, this.Tangage, l3);
          this.o.set(this.Azimut, this.Tangage, 0.0F);
        }
      }

      if (((World.cur().diffCur.Head_Shake) || (World.cur().diffCur.Wind_N_Turbulence)) && (!this.bSimpleUse))
      {
        Aircraft localAircraft2 = World.getPlayerAircraft();
        if (Actor.isValid(localAircraft2)) {
          l1 = Time.current();
          if (l1 != this.stamp) {
            this.stamp = l1;
            headRoll(localAircraft2);
          }
        }
        this.pe.add(pCamera(), P);

        oTmp2.set(this.o);
        oTmp2.increment(oTmp);
        this.le.set(this.pe, oTmp2);
      }
      else {
        this.le.set(pCamera(), this.o);
      }
      paramLoc2.add(this.le, paramLoc1);
    } else {
      paramLoc2.set(paramLoc1);
    }
    return true;
  }

  public void computePos(Actor paramActor, Loc paramLoc1, Loc paramLoc2) {
    if (this.bUse) {
      if ((Time.isPaused()) && (!this.bPadlock)) {
        if (World.cur().diffCur.Head_Shake) {
          this.pe.add(pCamera(), P);
          this.le.set(this.pe, this.o);
        } else {
          this.le.set(pCamera(), this.o);
        }
        paramLoc2.add(this.le, paramLoc1);
        return;
      }
      paramLoc2.add(this.le, paramLoc1);
      if (this.bPadlock)
        paramLoc2.set(this.op);
    } else {
      paramLoc2.set(paramLoc1);
    }
  }

  private float avalue(float paramFloat1, float paramFloat2) {
    if (paramFloat1 >= 0.0F) {
      if (paramFloat1 <= paramFloat2) return 0.0F;
      return paramFloat1 - paramFloat2;
    }
    if (paramFloat1 >= -paramFloat2) return 0.0F;
    return paramFloat1 + paramFloat2;
  }

  private float bvalue(float paramFloat1, float paramFloat2) {
    float f = HookView.koofSpeed * 4.0F / 6.0F * this.timeKoof;
    if (paramFloat1 > paramFloat2) {
      if (paramFloat1 < paramFloat2 + f) return paramFloat1;
      return paramFloat2 + f;
    }
    if (paramFloat1 > paramFloat2 - f) return paramFloat1;
    return paramFloat2 - f;
  }

  private void padlockSet(Orient paramOrient) {
    float f1 = paramOrient.getAzimut();
    float f2 = paramOrient.getTangage();
    if ((this.bPadlockEnd) || (this.bForward)) {
      f1 = f2 = 0.0F;
      this.tPadlockEnd = -1L;
    } else {
      Camera3D localCamera3D = (Camera3D)this.target2;

      float f3 = localCamera3D.FOV() * 0.3F;
      float f4 = f3 / localCamera3D.aspect();
      f1 = (f1 + 3600.0F) % 360.0F;
      if (f1 > 180.0F) f1 -= 360.0F;

      f1 = avalue(f1, f3);
      f2 = avalue(f2, f4);
      int i = 0;
      if (f1 < -this.maxAzimut) { f1 = -this.maxAzimut; i = 1; }
      if (f1 > this.maxAzimut) { f1 = this.maxAzimut; i = 1; }
      if (f2 < this.minTangage) { f2 = this.minTangage; i = 1;
      }
      if ((i != 0) || (!this.bVisibleEnemy) || (!Actor.isAlive(this.enemy))) {
        if (this.tPadlockEnd != -1L) {
          this.tPadlockEndLen += Time.current() - this.tPadlockEnd;
        }
        this.tPadlockEnd = Time.current();
        if (this.tPadlockEndLen > 4000L) {
          this.bPadlockEnd = true;
          this.tPadlockEnd = -1L;
          this.tPadlockEndLen = 0L;
        }
      } else {
        this.tPadlockEnd = -1L;
        this.tPadlockEndLen = 0L;
      }
    }

    f1 = bvalue(f1, this.azimPadlock);
    f2 = bvalue(f2, this.tangPadlock);
    paramOrient.set(f1, f2, 0.0F);
    this.azimPadlock = f1;
    this.tangPadlock = f2;

    if ((this.bPadlockEnd) && 
      (-1.0F < this.azimPadlock) && (this.azimPadlock < 1.0F) && (-1.0F < this.tangPadlock) && (this.tangPadlock < 1.0F))
    {
      this.stamp = -1L;
      _reset();
    }
  }

  public void checkPadlockState()
  {
    if (!this.bPadlock) return;
    if (!Actor.isAlive(this.enemy)) return;

    VisibilityChecker.checkLandObstacle = true;
    VisibilityChecker.checkCabinObstacle = true;
    VisibilityChecker.checkPlaneObstacle = true;
    VisibilityChecker.checkObjObstacle = true;
    this.bVisibleEnemy = (VisibilityChecker.computeVisibility(null, this.enemy) > 0.0F);
  }

  public void setTarget(Actor paramActor)
  {
    this.target = paramActor; } 
  public void setTarget2(Actor paramActor) { this.target2 = paramActor; } 
  public boolean use(boolean paramBoolean) {
    boolean bool = this.bUse;
    this.bUse = paramBoolean;
    if (Actor.isValid(this.target))
      this.target.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.inValidate(true);
    if (Actor.isValid(this.target2))
      this.target2.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.inValidate(true);
    return bool;
  }
  public boolean useMouse(boolean paramBoolean) {
    boolean bool = this.bUseMouse;
    this.bUseMouse = paramBoolean;
    return bool;
  }

  public void mouseMove(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((!this.bUse) || (this.bPadlock) || (this.bSimpleUse)) return;
    if ((this.bUseMouse) && (Time.real() > this.timeViewSet + 1000L)) {
      float f1 = (this.o.azimut() + paramInt1 * HookView.koofAzimut) % 360.0F;
      if (f1 > 180.0F) f1 -= 360.0F;
      else if (f1 < -180.0F) f1 += 360.0F;
      if (f1 < -this.maxAzimut) {
        if (paramInt1 <= 0) f1 = -this.maxAzimut; else
          f1 = this.maxAzimut;
      } else if (f1 > this.maxAzimut) {
        if (paramInt1 >= 0) f1 = this.maxAzimut; else
          f1 = -this.maxAzimut;
      }
      float f2 = (this.o.tangage() + paramInt2 * HookView.koofTangage) % 360.0F;
      if (f2 > 180.0F) f2 -= 360.0F;
      else if (f2 < -180.0F) f2 += 360.0F;
      if (f2 < this.minTangage) {
        if (paramInt2 <= 0) f2 = this.minTangage; else
          f2 = this.maxTangage;
      } else if (f2 > this.maxTangage) {
        if (paramInt2 >= 0) f2 = this.maxTangage; else
          f2 = this.minTangage;
      }
      this.o.set(f1, f2, 0.0F);

      if (Actor.isValid(this.target))
        this.target.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.inValidate(true);
      if (Actor.isValid(this.target2))
        this.target2.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.inValidate(true);
      this.Azimut = this._Azimut;
      this.Tangage = this._Tangage;
    }
  }

  public void viewSet(float paramFloat1, float paramFloat2) {
    if ((!this.bUse) || (this.bPadlock) || (this.bSimpleUse)) return;
    if (this.bUseMouse) {
      this.timeViewSet = Time.real();

      paramFloat1 %= 360.0F;
      if (paramFloat1 > 180.0F) paramFloat1 -= 360.0F;
      else if (paramFloat1 < -180.0F) paramFloat1 += 360.0F;
      paramFloat2 %= 360.0F;
      if (paramFloat2 > 180.0F) paramFloat2 -= 360.0F;
      else if (paramFloat2 < -180.0F) paramFloat2 += 360.0F;

      if (paramFloat1 < -this.maxAzimut) paramFloat1 = -this.maxAzimut;
      else if (paramFloat1 > this.maxAzimut) paramFloat1 = this.maxAzimut;
      if (paramFloat2 > this.maxTangage) paramFloat2 = this.maxTangage;
      else if (paramFloat2 < this.minTangage) paramFloat2 = this.minTangage;

      this._Azimut = (this.Azimut = paramFloat1);
      this._Tangage = (this.Tangage = paramFloat2);
      this.o.set(paramFloat1, paramFloat2, 0.0F);

      if (Actor.isValid(this.target))
        this.target.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.inValidate(true);
      if (Actor.isValid(this.target2))
        this.target2.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.inValidate(true);
    }
  }

  public void snapSet(float paramFloat1, float paramFloat2)
  {
    if ((!this.bUse) || (this.bPadlock) || (this.bSimpleUse)) return;
    this._Azimut = (45.0F * paramFloat1);
    this._Tangage = (44.0F * paramFloat2);

    this.Azimut = (this.o.azimut() % 360.0F);
    if (this.Azimut > 180.0F) this.Azimut -= 360.0F;
    else if (this.Azimut < -180.0F) this.Azimut += 360.0F;
    this.Tangage = (this.o.tangage() % 360.0F);
    if (this.Tangage > 180.0F) this.Tangage -= 360.0F;
    else if (this.Tangage < -180.0F) this.Tangage += 360.0F;

    if (Actor.isValid(this.target))
      this.target.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.inValidate(true);
    if (Actor.isValid(this.target2))
      this.target2.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.inValidate(true);
  }

  public void panSet(int paramInt1, int paramInt2) {
    if ((!this.bUse) || (this.bPadlock) || (this.bSimpleUse)) return;
    if ((paramInt1 == 0) && (paramInt2 == 0)) {
      this._Azimut = 0.0F;
      this._Tangage = 0.0F;
    }
    int i;
    if (this._Azimut == -this.maxAzimut) {
      i = (int)(this._Azimut / this.stepAzimut);
      if (-this._Azimut % this.stepAzimut > 0.01F * this.stepAzimut) i--;
      this._Azimut = (i * this.stepAzimut);
    } else if (this._Azimut == this.maxAzimut) {
      i = (int)(this._Azimut / this.stepAzimut);
      if (this._Azimut % this.stepAzimut > 0.01F * this.stepAzimut) i++;
      this._Azimut = (i * this.stepAzimut);
    }
    this._Azimut = (paramInt1 * this.stepAzimut + this._Azimut);
    if (this._Azimut < -this.maxAzimut) this._Azimut = (-this.maxAzimut);
    if (this._Azimut > this.maxAzimut) this._Azimut = this.maxAzimut;

    this._Tangage = (paramInt2 * this.stepTangage + this._Tangage);
    if (this._Tangage < this.minTangage) this._Tangage = this.minTangage;
    if (this._Tangage > this.maxTangage) this._Tangage = this.maxTangage;

    this.Azimut = (this.o.azimut() % 360.0F);
    if (this.Azimut > 180.0F) this.Azimut -= 360.0F;
    else if (this.Azimut < -180.0F) this.Azimut += 360.0F;
    this.Tangage = (this.o.tangage() % 360.0F);
    if (this.Tangage > 180.0F) this.Tangage -= 360.0F;
    else if (this.Tangage < -180.0F) this.Tangage += 360.0F;

    if (Actor.isValid(this.target))
      this.target.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.inValidate(true);
    if (Actor.isValid(this.target2))
      this.target2.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.inValidate(true);
  }

  public static HookPilot New()
  {
    if (current == null)
      current = new HookPilot();
    return current;
  }
  public static HookPilot cur() {
    return New();
  }
}
package com.maddox.il2.engine.hotkey;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookRender;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Time;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class HookGunner extends HookRender
{
  private float stepAzimut = 45.0F;
  private float stepTangage = 30.0F;
  private float maxAzimut = 135.0F;
  private float maxTangage = 89.0F;
  private float minTangage = -60.0F;
  private float Azimut = 0.0F;
  private float Tangage = 0.0F;
  private float _Azimut = 0.0F;
  private float _Tangage = 0.0F;
  private long prevTime = 0L;
  private float Px;
  private float Py;
  private float Pz;
  private long tstamp = -1L;
  private long roolTime = -1L;
  private Move mover;
  private Actor target;
  private Actor target2;
  private boolean bUse = false;
  private Orient oGunMove = new Orient();

  private Loc L = new Loc();

  private Orient o = new Orient();

  private static float save_Azimut = 0.0F;
  private static float save_Tangage = 0.0F;
  private static float save__Azimut = 0.0F;
  private static float save__Tangage = 0.0F;
  private static HookGunner current;
  private static ArrayList all = new ArrayList();

  public static void resetGame()
  {
    for (int i = 0; i < all.size(); i++) {
      HookGunner localHookGunner = (HookGunner)all.get(i);
      localHookGunner.mover = null;
      localHookGunner.target = null;
      localHookGunner.target2 = null;
      localHookGunner.bUse = false;
      localHookGunner.oGunMove.set(0.0F, 0.0F, 0.0F);
    }
    all.clear();
    current = null;
  }

  public Orient getGunMove()
  {
    return this.oGunMove;
  }
  public void resetMove(float paramFloat1, float paramFloat2) {
    this.oGunMove.set(paramFloat1, paramFloat2, 0.0F);
    if (this.mover != null) {
      this.mover.clipAnglesGun(this.oGunMove);
      this.mover.moveGun(this.oGunMove);
    }
  }

  public void setMover(Move paramMove) {
    this.mover = paramMove;
  }
  private void _reset() {
    if (!AircraftHotKeys.bFirstHotCmd) {
      this._Azimut = (this.Azimut = 0.0F);
      this._Tangage = (this.Tangage = 0.0F);
    }
    this.Px = (this.Py = this.Pz = 0.0F);
    this.L.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    this.tstamp = -1L;
    this.roolTime = -1L;
  }
  public void reset() {
    _reset();
  }

  private void headRoll(Aircraft paramAircraft) {
    long l = this.roolTime - this.tstamp;
    if ((l >= 0L) && (l < 50L))
      return;
    this.roolTime = this.tstamp;
    float f1 = (float)(-(paramAircraft.FM.getAccel().y + paramAircraft.FM.getRollAcceleration()) * 0.0500000007450581D);
    float f2 = (float)(-paramAircraft.FM.getAccel().x * 0.1000000014901161D);
    float f3 = (float)(-paramAircraft.FM.getAccel().z * 0.01999999955296516D);
    if ((f2 >= 1.0F) || (f2 <= -1.0F))
    {
      if (f2 < -1.0F) f2 = -1.0F; else if (f2 > 1.0F) f2 = 1.0F; else
        f2 = 0.0F;
    }
    if ((f1 >= 1.0F) || (f1 <= -1.0F))
    {
      if (f1 < -1.0F) f1 = -1.0F; else if (f1 > 1.0F) f1 = 1.0F; else
        f1 = 0.0F;
    }
    if ((f3 >= 1.0F) || (f3 <= -1.0F))
    {
      if (f3 < -1.0F) f3 = -1.0F; else if (f3 > 1.0F) f3 = 1.0F; else {
        f3 = 0.0F;
      }
    }
    this.L.set(this.Px += (f2 * 0.015F - this.Px) * 0.4F, this.Py += (f1 * 0.015F - this.Py) * 0.4F, this.Pz += (f3 * 0.015F - this.Pz) * 0.4F, 0.0F, 0.0F, 0.0F);
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
    long l1 = Time.currentReal();
    if (l1 != this.prevTime) {
      long l2 = l1 - this.prevTime;
      this.prevTime = l1;
      if ((this._Azimut != this.Azimut) || (this._Tangage != this.Tangage)) {
        this.Azimut = bvalue(this._Azimut, this.Azimut, l2);
        this.Tangage = bvalue(this._Tangage, this.Tangage, l2);
      }
    }
    computePos(paramActor, paramLoc1, paramLoc2);
    return true;
  }
  public void computePos(Actor paramActor, Loc paramLoc1, Loc paramLoc2) {
    if ((this.bUse) && (this.mover != null)) {
      if (World.cur().diffCur.Head_Shake) {
        Aircraft localAircraft = World.getPlayerAircraft();
        if (Actor.isValid(localAircraft)) {
          long l = Time.current();
          if ((l != this.tstamp) && (!localAircraft.FM.Gears.onGround())) {
            this.tstamp = l;
            headRoll(localAircraft);
          }
        }
      }
      paramLoc2.add(this.L, paramLoc2);
      this.o.set(this.Azimut, this.Tangage, 0.0F);
      paramLoc2.getOrient().add(this.o);
      this.mover.getHookCameraGun().computePos(paramActor, paramLoc1, paramLoc2);
    } else {
      paramLoc2.set(paramLoc1);
    }
  }

  public void setTarget(Actor paramActor) {
    this.target = paramActor; } 
  public void setTarget2(Actor paramActor) { this.target2 = paramActor; } 
  public boolean use(boolean paramBoolean) {
    boolean bool = this.bUse;
    this.bUse = paramBoolean;
    if (Actor.isValid(this.target))
      this.target.pos.inValidate(true);
    if (Actor.isValid(this.target2))
      this.target2.pos.inValidate(true);
    if (this.bUse) current = this; else
      current = null;
    return bool;
  }

  public void gunFire(boolean paramBoolean) {
    if (this.mover == null) return;
    this.mover.doGunFire(paramBoolean);
  }

  public void mouseMove(int paramInt1, int paramInt2, int paramInt3) {
    if (this.mover == null) return;
    this.oGunMove.set(this.oGunMove.azimut() - paramInt1 * HookView.koofAzimut, this.oGunMove.tangage() + paramInt2 * HookView.koofTangage, 0.0F);
    this.oGunMove.wrap();
    this.mover.clipAnglesGun(this.oGunMove);
    this.mover.moveGun(this.oGunMove);
    if (Actor.isValid(this.target))
      this.target.pos.inValidate(true);
    if (Actor.isValid(this.target2))
      this.target2.pos.inValidate(true);
  }

  public static void doSnapSet(float paramFloat1, float paramFloat2) {
    for (int i = 0; i < all.size(); i++) {
      HookGunner localHookGunner = (HookGunner)all.get(i);
      localHookGunner.snapSet(paramFloat1, paramFloat2);
    }
  }

  public void snapSet(float paramFloat1, float paramFloat2) {
    if (!this.bUse) return;
    this._Azimut = (45.0F * paramFloat1);
    this._Tangage = (44.0F * paramFloat2);
    if (Actor.isValid(this.target))
      this.target.pos.inValidate(true);
    if (Actor.isValid(this.target2))
      this.target2.pos.inValidate(true);
  }

  public static void doPanSet(int paramInt1, int paramInt2) {
    for (int i = 0; i < all.size(); i++) {
      HookGunner localHookGunner = (HookGunner)all.get(i);
      localHookGunner.panSet(paramInt1, paramInt2);
    }
  }

  public void panSet(int paramInt1, int paramInt2) {
    if (!this.bUse) return;
    if ((paramInt1 == 0) && (paramInt2 == 0)) {
      this._Azimut = 0.0F;
      this._Tangage = 0.0F;
    }
    this._Azimut = (paramInt1 * this.stepAzimut + this._Azimut);
    if (this._Azimut < -this.maxAzimut) this._Azimut = (-this.maxAzimut);
    if (this._Azimut > this.maxAzimut) this._Azimut = this.maxAzimut;
    this._Tangage = (paramInt2 * this.stepTangage + this._Tangage);
    if (this._Tangage < this.minTangage) this._Tangage = this.minTangage;
    if (this._Tangage > this.maxTangage) this._Tangage = this.maxTangage;
    if (Actor.isValid(this.target))
      this.target.pos.inValidate(true);
    if (Actor.isValid(this.target2))
      this.target2.pos.inValidate(true);
  }

  public void viewSet(float paramFloat1, float paramFloat2) {
    if (!this.bUse) return;

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
    if (Actor.isValid(this.target))
      this.target.pos.inValidate(true);
    if (Actor.isValid(this.target2))
      this.target2.pos.inValidate(true);
  }

  public static void saveRecordedStates(PrintWriter paramPrintWriter) throws Exception {
    if (current == null) {
      paramPrintWriter.println(0);
      paramPrintWriter.println(0);
      paramPrintWriter.println(0);
      paramPrintWriter.println(0);
    } else {
      paramPrintWriter.println(current.Azimut);
      paramPrintWriter.println(current._Azimut);
      paramPrintWriter.println(current.Tangage);
      paramPrintWriter.println(current._Tangage);
    }
  }

  public static void loadRecordedStates(BufferedReader paramBufferedReader) throws Exception {
    save_Azimut = Float.parseFloat(paramBufferedReader.readLine());
    save__Azimut = Float.parseFloat(paramBufferedReader.readLine());
    save_Tangage = Float.parseFloat(paramBufferedReader.readLine());
    save__Tangage = Float.parseFloat(paramBufferedReader.readLine());
  }

  public HookGunner(Actor paramActor1, Actor paramActor2)
  {
    this.Azimut = save_Azimut;
    this.Tangage = save_Tangage;
    this._Azimut = save__Azimut;
    this._Tangage = save__Tangage;
    setTarget(paramActor1);
    setTarget2(paramActor2);
    all.add(this);
  }

  public static HookGunner current()
  {
    return current;
  }

  public static abstract interface Move
  {
    public abstract void moveGun(Orient paramOrient);

    public abstract void clipAnglesGun(Orient paramOrient);

    public abstract Hook getHookCameraGun();

    public abstract void doGunFire(boolean paramBoolean);
  }
}
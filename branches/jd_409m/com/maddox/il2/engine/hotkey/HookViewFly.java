package com.maddox.il2.engine.hotkey;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.Render;
import com.maddox.rts.IniFile;

public class HookViewFly extends Hook
{
  private float timeFirstStep = 2.0F;
  private float deltaZ = 10.0F;
  private double maxLen;
  private Point3d p0 = new Point3d();
  private Point3d pAbs = new Point3d();
  private Orient oAbs = new Orient();
  private Vector3d vect = new Vector3d();
  private boolean bUse = false;
  private boolean bView = false;
  private Actor camera;
  private Orient o = new Orientation();
  private Vector3d v = new Vector3d();

  public static HookViewFly current = null;

  private boolean computePos(Actor paramActor)
  {
    double d1 = paramActor.getSpeed(this.vect);
    if (d1 < 0.009999999776482582D) {
      if (paramActor.pos == null) return false;
      Loc localLoc = paramActor.pos.getAbs();
      this.vect.set(1.0D, 0.0D, 0.0D);
      localLoc.transform(this.vect);
      d1 = 20.0D;
    }
    this.vect.normalize();
    this.vect.scaleAdd(d1 * this.timeFirstStep, this.pAbs);
    this.p0.set(this.vect);

    float f = this.oAbs.getTangage();
    if (f > 0.0F) this.p0.jdField_z_of_type_Double -= this.deltaZ; else
      this.p0.jdField_z_of_type_Double += this.deltaZ;
    if (Math.abs(this.p0.jdField_x_of_type_Double - this.pAbs.jdField_x_of_type_Double) > Math.abs(this.p0.jdField_y_of_type_Double - this.pAbs.jdField_y_of_type_Double)) {
      this.p0.jdField_y_of_type_Double += this.deltaZ; this.p0.jdField_x_of_type_Double += this.deltaZ / 4.0F;
    } else {
      this.p0.jdField_x_of_type_Double += this.deltaZ; this.p0.jdField_y_of_type_Double += this.deltaZ / 4.0F;
    }
    double d2 = Engine.land().HQ_Air(this.p0.jdField_x_of_type_Double, this.p0.jdField_y_of_type_Double) + 25.0D;
    if (this.p0.jdField_z_of_type_Double < d2) this.p0.jdField_z_of_type_Double = d2;
    double d3 = this.pAbs.distance(this.p0);
    this.maxLen = (2.0D * d3);
    if (d3 < 10.0D)
      this.maxLen = 20.0D;
    if (Actor.isValid(this.camera)) {
      this.camera.pos.inValidate(true);
      this.camera.pos.resetAsBase();
    }
    return true;
  }

  public void computePos(Actor paramActor, Loc paramLoc1, Loc paramLoc2) {
    if (this.bUse) {
      paramLoc1.get(this.pAbs, this.oAbs);
      if (!this.bView) {
        this.bView = computePos(paramActor);
      }
      if ((this.pAbs.distance(this.p0) > this.maxLen) && (Render.current() == null))
        this.bView = computePos(paramActor);
      if (!this.bView) {
        return;
      }

      this.v.set(this.pAbs);
      this.v.sub(this.p0);
      this.o.setAT0(this.v);
      paramLoc2.set(this.p0, this.o);
    }
  }

  public boolean use(boolean paramBoolean)
  {
    boolean bool = this.bUse;
    this.bUse = paramBoolean;
    this.bView = false;
    return bool;
  }

  public void reset() {
    this.bView = false;
  }
  public void setCamera(Actor paramActor) {
    this.camera = paramActor;
  }

  public HookViewFly(String paramString) {
    String str = paramString + " Config";
    this.timeFirstStep = Config.cur.ini.get(str, "timeFirstStep", this.timeFirstStep);
    this.deltaZ = Config.cur.ini.get(str, "deltaZ", this.deltaZ);
    current = this;
  }
}
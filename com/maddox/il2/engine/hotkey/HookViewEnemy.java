package com.maddox.il2.engine.hotkey;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorViewPoint;

public class HookViewEnemy extends HookView
{
  private static Actor enemy = null;
  private static Actor Aenemy = null;
  private static Actor Genemy = null;
  private static boolean bGround;
  protected static final int ENEMY = 2;
  public static HookViewEnemy current = null;
  private static Vector3d Ve = new Vector3d();
  private static Vector3d Vt = new Vector3d();
  private static Point3d Pt = new Point3d();

  public void resetGame()
  {
    lastBaseActor = null;
    enemy = null;
    Aenemy = null;
    Genemy = null;
  }

  public Actor enemy()
  {
    return enemy;
  }
  public void reset(float paramFloat1, float paramFloat2, float paramFloat3) {
    len = paramFloat1;
    if (this.camera != null) {
      Actor localActor = this.camera.pos.base();
      if (this.bUse)
        this.camera.pos.inValidate(true); 
    }
  }

  public boolean computeRenderPos(Actor paramActor, Loc paramLoc1, Loc paramLoc2) {
    computePos(paramActor, paramLoc1, paramLoc2, true);
    return true;
  }

  public void computePos(Actor paramActor, Loc paramLoc1, Loc paramLoc2) {
    computePos(paramActor, paramLoc1, paramLoc2, false);
  }

  public void computePos(Actor paramActor, Loc paramLoc1, Loc paramLoc2, boolean paramBoolean) {
    paramLoc1.get(this.pAbs, this.oAbs);
    if (this.bUse) {
      if (lastBaseActor != paramActor) {
        lastBaseActor = paramActor;
        _visibleR = -1.0F;
        if ((Actor.isValid(paramActor)) && ((paramActor instanceof ActorMesh)))
          _visibleR = ((ActorMesh)paramActor).mesh().visibilityR();
      }
      boolean bool = paramActor instanceof ActorViewPoint;
      if (paramBoolean) enemy.pos.getRender(this.p); else
        enemy.pos.getAbs(this.p);
      Ve.sub(this.p, this.pAbs);
      float f = (float)Ve.length();
      Ve.normalize();
      if (!bool) Ve.z -= pullVector(paramActor, this.pAbs);
      this.o.setAT0(Ve);
      if ((len < 0.0F) && (paramBoolean) && 
        (-len + defaultLen() > f)) {
        len = -f + defaultLen();
      }
      Ve.scale(-len);
      this.pAbs.add(Ve);
      if (!bool) {
        this.pAbs.z += 0.2D * len;
      }
      double d = World.land().HQ_Air(this.pAbs.x, this.pAbs.y) + 2.0D;
      if (this.pAbs.z < d)
        this.pAbs.z = d;
      paramLoc2.set(this.pAbs, this.o);
    } else {
      paramLoc2.set(this.pAbs, this.oAbs);
    }
  }

  private float pullVector(Actor paramActor, Point3d paramPoint3d)
  {
    float f2 = (float)(paramPoint3d.z - World.land().HQ(paramPoint3d.x, paramPoint3d.y));
    paramActor.getSpeed(Vt);
    float f1 = 10.0F;

    f1 = Math.min(f2 * 0.1F, f1);
    f1 = Math.min(Math.max(1.0F, f1), 10.0F);
    return (10.0F - f1) * 0.1F;
  }

  public boolean start(Actor paramActor1, Actor paramActor2, boolean paramBoolean1, boolean paramBoolean2) {
    bGround = paramBoolean1;
    _visibleR = -1.0F;
    if (Actor.isValid(paramActor1)) {
      if ((paramActor1 instanceof ActorMesh))
        _visibleR = ((ActorMesh)paramActor1).mesh().visibilityR();
      paramActor1.pos.inValidate(true);
    } else {
      stop();
      return false;
    }
    paramActor1.pos.getAbs(this.p);
    enemy = paramActor2;
    if (enemy == null) {
      if (bGround) enemy = Genemy; else enemy = Aenemy;
      if (Actor.isValid(enemy)) {
        if (paramActor1 != enemy) { if (paramBoolean2 != (paramActor1.getArmy() == enemy.getArmy())); } else {
          stop();
          return false;
        }
      }
    }
    this.bUse = ((this.camera != null) && (Actor.isValid(enemy)));
    useCommon(2, this.bUse);
    if (bGround) Genemy = enemy; else Aenemy = enemy;
    clipLen(paramActor1);
    return this.bUse;
  }

  public void stop() {
    useCommon(2, false);
    this.bUse = false;
  }

  public boolean isRun() {
    return this.bUse;
  }

  public HookViewEnemy() {
    current = this;
  }
}
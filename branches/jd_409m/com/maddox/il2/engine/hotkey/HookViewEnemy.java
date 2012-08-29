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
    HookView.lastBaseActor = null;
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
    if (this.jdField_camera_of_type_ComMaddoxIl2EngineActor != null) {
      Actor localActor = this.jdField_camera_of_type_ComMaddoxIl2EngineActor.pos.base();
      if (this.jdField_bUse_of_type_Boolean)
        this.jdField_camera_of_type_ComMaddoxIl2EngineActor.pos.inValidate(true); 
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
    paramLoc1.get(this.jdField_pAbs_of_type_ComMaddoxJGPPoint3d, this.jdField_oAbs_of_type_ComMaddoxIl2EngineOrient);
    if (this.jdField_bUse_of_type_Boolean) {
      if (HookView.lastBaseActor != paramActor) {
        HookView.lastBaseActor = paramActor;
        HookView._visibleR = -1.0F;
        if ((Actor.isValid(paramActor)) && ((paramActor instanceof ActorMesh)))
          HookView._visibleR = ((ActorMesh)paramActor).mesh().visibilityR();
      }
      boolean bool = paramActor instanceof ActorViewPoint;
      if (paramBoolean) enemy.pos.getRender(this.jdField_p_of_type_ComMaddoxJGPPoint3d); else
        enemy.pos.getAbs(this.jdField_p_of_type_ComMaddoxJGPPoint3d);
      Ve.sub(this.jdField_p_of_type_ComMaddoxJGPPoint3d, this.jdField_pAbs_of_type_ComMaddoxJGPPoint3d);
      float f = (float)Ve.length();
      Ve.normalize();
      if (!bool) Ve.jdField_z_of_type_Double -= pullVector(paramActor, this.jdField_pAbs_of_type_ComMaddoxJGPPoint3d);
      this.jdField_o_of_type_ComMaddoxIl2EngineOrient.setAT0(Ve);
      if ((HookView.len < 0.0F) && (paramBoolean) && 
        (-HookView.len + HookView.defaultLen() > f)) {
        HookView.len = -f + HookView.defaultLen();
      }
      Ve.scale(-HookView.len);
      this.jdField_pAbs_of_type_ComMaddoxJGPPoint3d.add(Ve);
      if (!bool) {
        this.jdField_pAbs_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double += 0.2D * HookView.len;
      }
      double d = World.land().HQ_Air(this.jdField_pAbs_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_pAbs_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double) + 2.0D;
      if (this.jdField_pAbs_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double < d)
        this.jdField_pAbs_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double = d;
      paramLoc2.set(this.jdField_pAbs_of_type_ComMaddoxJGPPoint3d, this.jdField_o_of_type_ComMaddoxIl2EngineOrient);
    } else {
      paramLoc2.set(this.jdField_pAbs_of_type_ComMaddoxJGPPoint3d, this.jdField_oAbs_of_type_ComMaddoxIl2EngineOrient);
    }
  }

  private float pullVector(Actor paramActor, Point3d paramPoint3d)
  {
    float f2 = (float)(paramPoint3d.jdField_z_of_type_Double - World.land().HQ(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double));
    paramActor.getSpeed(Vt);
    float f1 = 10.0F;

    f1 = Math.min(f2 * 0.1F, f1);
    f1 = Math.min(Math.max(1.0F, f1), 10.0F);
    return (10.0F - f1) * 0.1F;
  }

  public boolean start(Actor paramActor1, Actor paramActor2, boolean paramBoolean1, boolean paramBoolean2) {
    bGround = paramBoolean1;
    HookView._visibleR = -1.0F;
    if (Actor.isValid(paramActor1)) {
      if ((paramActor1 instanceof ActorMesh))
        HookView._visibleR = ((ActorMesh)paramActor1).mesh().visibilityR();
      paramActor1.pos.inValidate(true);
    } else {
      stop();
      return false;
    }
    paramActor1.pos.getAbs(this.jdField_p_of_type_ComMaddoxJGPPoint3d);
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
    this.jdField_bUse_of_type_Boolean = ((this.jdField_camera_of_type_ComMaddoxIl2EngineActor != null) && (Actor.isValid(enemy)));
    useCommon(2, this.jdField_bUse_of_type_Boolean);
    if (bGround) Genemy = enemy; else Aenemy = enemy;
    clipLen(paramActor1);
    return this.jdField_bUse_of_type_Boolean;
  }

  public void stop() {
    useCommon(2, false);
    this.jdField_bUse_of_type_Boolean = false;
  }

  public boolean isRun() {
    return this.jdField_bUse_of_type_Boolean;
  }

  public HookViewEnemy() {
    current = this;
  }
}
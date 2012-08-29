package com.maddox.il2.game;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.EffClouds;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.objects.air.Cockpit;

public class VisibilityChecker
{
  public static boolean checkLandObstacle;
  public static boolean checkCabinObstacle;
  public static boolean checkPlaneObstacle;
  public static boolean checkObjObstacle;
  public static Point3d targetPosInput;
  public static Point3d nosePos = new Point3d();

  private static Vector3d noseDir = new Vector3d();
  private static Vector3d targRayDir = new Vector3d();

  private static Point3d targetPos = new Point3d();

  private static Loc Cam2WorldLoc = new Loc();

  private static Loc Plane2WorldLoc = new Loc();
  private static Matrix4d Plane2WorldTM = new Matrix4d();

  private static Point3d tmpp0 = new Point3d();
  private static Point3d tmpp1 = new Point3d();
  private static Point3d tmpp2 = new Point3d();
  private static Vector3d tmpv0 = new Vector3d();
  public static float resultAng;

  public static float computeVisibility(Vector3d paramVector3d, Actor paramActor)
  {
    if (paramVector3d == null) {
      if (paramActor == null) {
        targetPosInput = null;
        return -100.0F;
      }
    } else if (paramActor != null) {
      targetPosInput = null;
      return -100.0F;
    }
    int i;
    if (paramVector3d != null) {
      i = 1;

      Main3D.cur3D()._camera3D[Main3D.cur3D().getRenderIndx()].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRender(Cam2WorldLoc);

      Cam2WorldLoc.get(nosePos);
    } else {
      i = 0;

      Main3D.cur3D()._camera3D[Main3D.cur3D().getRenderIndx()].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(Cam2WorldLoc);

      Cam2WorldLoc.get(nosePos);

      if (targetPosInput == null)
        paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(targetPos);
      else {
        targetPos.set(targetPosInput);
      }

      targRayDir.sub(targetPos, nosePos);
      d1 = targRayDir.length();
      if (d1 < 0.001D) {
        return -1.0F;
      }
      targRayDir.scale(1.0D / d1);
      paramVector3d = targRayDir;
    }
    targetPosInput = null;

    noseDir.set(1.0D, 0.0D, 0.0D);
    Cam2WorldLoc.transform(noseDir);

    double d1 = noseDir.dot(paramVector3d);
    if (d1 < 0.01D) {
      return -1.0F;
    }
    resultAng = Geom.RAD2DEG((float)Math.acos(d1));

    float f1 = 1.0F;

    if (i != 0)
    {
      if (paramVector3d.jdField_z_of_type_Double < 0.4199999868869782D) {
        if (paramVector3d.jdField_z_of_type_Double <= 0.0D) {
          return 0.0F;
        }
        f1 = (float)(f1 * (paramVector3d.jdField_z_of_type_Double / 0.4199999868869782D));
      }

      if ((Main3D.cur3D().clouds != null) && 
        (nosePos.jdField_z_of_type_Double < Main3D.cur3D().clouds.height() + 150.0F)) {
        if (Main3D.cur3D().clouds.type() >= 5) {
          return 0.0F;
        }
        if (Main3D.cur3D().clouds.type() >= 4) {
          f1 *= 0.5F;
        }

      }

    }

    if ((Main3D.cur3D().clouds != null) && (Main3D.cur3D().bDrawClouds))
    {
      float f2;
      if (i != 0)
      {
        if (nosePos.jdField_z_of_type_Double < Main3D.cur3D().clouds.height() + 150.0F) {
          tmpp0.set(nosePos);
          tmpp1.set(paramVector3d);
          tmpp1.scale(30000.0D);
          tmpp1.add(nosePos);
          f2 = Main3D.cur3D().clouds.getVisibility(tmpp0, tmpp1);
          if (f2 <= 0.0F) {
            return 0.0F;
          }
          f1 *= f2;
        }
      }
      else {
        f2 = Main3D.cur3D().clouds.getVisibility(nosePos, targetPos);
        if (f2 <= 0.0F) {
          return 0.0F;
        }
      }

    }

    if (checkLandObstacle) {
      if (i != 0) {
        tmpp1.set(paramVector3d);
        tmpp1.scale(30000.0D);
        tmpp1.add(nosePos);
        Engine.land(); if (Landscape.rayHitHQ(nosePos, tmpp1, tmpp2))
          return 0.0F;
      }
      else {
        Engine.land(); if (Landscape.rayHitHQ(nosePos, targetPos, tmpp2)) {
          return 0.0F;
        }
      }
      checkLandObstacle = false;
    }

    int j = (!Main3D.cur3D().isViewOutside()) && (Main3D.cur3D().isViewInsideShow()) ? 1 : 0;
    int k;
    if (j != 0) {
      k = 1;
    }
    else if (Main3D.cur3D().isViewOutside())
      k = 2;
    else {
      k = 0;
    }

    HierMesh localHierMesh = null;
    if (k == 1) {
      localObject1 = World.getPlayerAircraft();
      if (!Actor.isAlive((Actor)localObject1)) {
        return 0.0F;
      }
      if (!(localObject1 instanceof ActorHMesh)) {
        return 0.0F;
      }
      localHierMesh = ((ActorHMesh)localObject1).hierMesh();
      if (localHierMesh == null) {
        return 0.0F;
      }
      if (i != 0)
        ((Actor)localObject1).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRender(Plane2WorldLoc);
      else {
        ((Actor)localObject1).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(Plane2WorldLoc);
      }
      Plane2WorldLoc.getMatrix(Plane2WorldTM);
    }

    Object localObject1 = null;
    Object localObject2;
    if (j != 0)
    {
      localObject2 = Main3D.cur3D().cockpitCur;
      if (localObject2 != null) {
        localObject1 = ((Cockpit)localObject2).mesh;
      }

    }

    if ((localObject1 != null) && 
      (checkCabinObstacle)) {
      if (((HierMesh)localObject1).isVisualRayHit(nosePos, paramVector3d, 0.0D, 999999.98999999999D, Plane2WorldTM))
      {
        return 0.0F;
      }
      checkCabinObstacle = false;
    }

    if ((localHierMesh != null) && 
      (checkPlaneObstacle)) {
      if (localHierMesh.isVisualRayHit(nosePos, paramVector3d, 1.200000047683716D, 999999.98999999999D, Plane2WorldTM))
      {
        return 0.0F;
      }
      checkPlaneObstacle = false;
    }

    if (checkObjObstacle) {
      localObject2 = World.getPlayerAircraft();
      if (k == 2) {
        localObject2 = null;
      }
      Point3d localPoint3d = Main3D.cur3D()._camera3D[Main3D.cur3D().getRenderIndx()].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
      tmpp0.set(localPoint3d);
      if (i != 0) {
        tmpp1.set(paramVector3d);
        tmpp1.scale(4000.0D);
        tmpp1.add(localPoint3d);
      } else {
        tmpp1.set(targetPos);
        tmpv0.sub(tmpp1, tmpp0);
        double d2 = tmpv0.length();
        if (d2 > 4000.0D) {
          tmpv0.scale(4000.0D / d2);
          tmpp1.set(tmpp0);
          tmpp1.add(tmpv0);
        }
      }
      Actor localActor = Engine.collideEnv().getLine(tmpp0, tmpp1, false, (Actor)localObject2, null);
      if (localActor != null) {
        if (i != 0)
        {
          return 0.0F;
        }
        if (localActor != paramActor) {
          return 0.0F;
        }
      }

      checkObjObstacle = false;
    }

    return f1;
  }
}
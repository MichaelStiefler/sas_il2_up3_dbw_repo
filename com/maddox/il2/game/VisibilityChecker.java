// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   VisibilityChecker.java

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

// Referenced classes of package com.maddox.il2.game:
//            Main3D

public class VisibilityChecker
{

    public VisibilityChecker()
    {
    }

    public static float computeVisibility(com.maddox.JGP.Vector3d vector3d, com.maddox.il2.engine.Actor actor)
    {
        if(vector3d == null)
        {
            if(actor == null)
            {
                targetPosInput = null;
                return -100F;
            }
        } else
        if(actor != null)
        {
            targetPosInput = null;
            return -100F;
        }
        boolean flag;
        if(vector3d != null)
        {
            flag = true;
            com.maddox.il2.game.Main3D.cur3D()._camera3D[com.maddox.il2.game.Main3D.cur3D().getRenderIndx()].pos.getRender(Cam2WorldLoc);
            Cam2WorldLoc.get(nosePos);
        } else
        {
            flag = false;
            com.maddox.il2.game.Main3D.cur3D()._camera3D[com.maddox.il2.game.Main3D.cur3D().getRenderIndx()].pos.getAbs(Cam2WorldLoc);
            Cam2WorldLoc.get(nosePos);
            if(targetPosInput == null)
                actor.pos.getAbs(targetPos);
            else
                targetPos.set(targetPosInput);
            targRayDir.sub(targetPos, nosePos);
            double d = targRayDir.length();
            if(d < 0.001D)
                return -1F;
            targRayDir.scale(1.0D / d);
            vector3d = targRayDir;
        }
        targetPosInput = null;
        noseDir.set(1.0D, 0.0D, 0.0D);
        Cam2WorldLoc.transform(noseDir);
        double d1 = noseDir.dot(vector3d);
        if(d1 < 0.01D)
            return -1F;
        resultAng = com.maddox.JGP.Geom.RAD2DEG((float)java.lang.Math.acos(d1));
        d1 = 1.0F;
        if(flag)
        {
            if(vector3d.z < 0.41999998688697815D)
            {
                if(vector3d.z <= 0.0D)
                    return 0.0F;
                d1 = (float)((double)d1 * (vector3d.z / 0.41999998688697815D));
            }
            if(com.maddox.il2.game.Main3D.cur3D().clouds != null && nosePos.z < (double)(com.maddox.il2.game.Main3D.cur3D().clouds.height() + 150F))
            {
                if(com.maddox.il2.game.Main3D.cur3D().clouds.type() >= 5)
                    return 0.0F;
                if(com.maddox.il2.game.Main3D.cur3D().clouds.type() >= 4)
                    d1 *= 0.5F;
            }
        }
        if(com.maddox.il2.game.Main3D.cur3D().clouds != null && com.maddox.il2.game.Main3D.cur3D().bDrawClouds)
            if(flag)
            {
                if(nosePos.z < (double)(com.maddox.il2.game.Main3D.cur3D().clouds.height() + 150F))
                {
                    tmpp0.set(nosePos);
                    tmpp1.set(vector3d);
                    tmpp1.scale(30000D);
                    tmpp1.add(nosePos);
                    float f = com.maddox.il2.game.Main3D.cur3D().clouds.getVisibility(tmpp0, tmpp1);
                    if(f <= 0.0F)
                        return 0.0F;
                    d1 *= f;
                }
            } else
            {
                float f1 = com.maddox.il2.game.Main3D.cur3D().clouds.getVisibility(nosePos, targetPos);
                if(f1 <= 0.0F)
                    return 0.0F;
            }
        if(checkLandObstacle)
        {
            if(flag)
            {
                tmpp1.set(vector3d);
                tmpp1.scale(30000D);
                tmpp1.add(nosePos);
                com.maddox.il2.engine.Engine.land();
                if(com.maddox.il2.engine.Landscape.rayHitHQ(nosePos, tmpp1, tmpp2))
                    return 0.0F;
            } else
            {
                com.maddox.il2.engine.Engine.land();
                if(com.maddox.il2.engine.Landscape.rayHitHQ(nosePos, targetPos, tmpp2))
                    return 0.0F;
            }
            checkLandObstacle = false;
        }
        boolean flag1 = !com.maddox.il2.game.Main3D.cur3D().isViewOutside() && com.maddox.il2.game.Main3D.cur3D().isViewInsideShow();
        byte byte0;
        if(flag1)
            byte0 = 1;
        else
        if(com.maddox.il2.game.Main3D.cur3D().isViewOutside())
            byte0 = 2;
        else
            byte0 = 0;
        com.maddox.il2.engine.HierMesh hiermesh = null;
        if(byte0 == 1)
        {
            com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
            if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
                return 0.0F;
            if(!(aircraft instanceof com.maddox.il2.engine.ActorHMesh))
                return 0.0F;
            hiermesh = ((com.maddox.il2.engine.ActorHMesh)aircraft).hierMesh();
            if(hiermesh == null)
                return 0.0F;
            if(flag)
                ((com.maddox.il2.engine.Actor) (aircraft)).pos.getRender(Plane2WorldLoc);
            else
                ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbs(Plane2WorldLoc);
            Plane2WorldLoc.getMatrix(Plane2WorldTM);
        }
        com.maddox.il2.engine.HierMesh hiermesh1 = null;
        if(flag1)
        {
            com.maddox.il2.objects.air.Cockpit cockpit = com.maddox.il2.game.Main3D.cur3D().cockpitCur;
            if(cockpit != null)
                hiermesh1 = cockpit.mesh;
        }
        if(hiermesh1 != null && checkCabinObstacle)
        {
            if(hiermesh1.isVisualRayHit(nosePos, vector3d, 0.0D, 999999.98999999999D, Plane2WorldTM))
                return 0.0F;
            checkCabinObstacle = false;
        }
        if(hiermesh != null && checkPlaneObstacle)
        {
            if(hiermesh.isVisualRayHit(nosePos, vector3d, 1.2000000476837158D, 999999.98999999999D, Plane2WorldTM))
                return 0.0F;
            checkPlaneObstacle = false;
        }
        if(checkObjObstacle)
        {
            com.maddox.il2.objects.air.Aircraft aircraft1 = com.maddox.il2.ai.World.getPlayerAircraft();
            if(byte0 == 2)
                aircraft1 = null;
            com.maddox.JGP.Point3d point3d = com.maddox.il2.game.Main3D.cur3D()._camera3D[com.maddox.il2.game.Main3D.cur3D().getRenderIndx()].pos.getAbsPoint();
            tmpp0.set(point3d);
            if(flag)
            {
                tmpp1.set(vector3d);
                tmpp1.scale(4000D);
                tmpp1.add(point3d);
            } else
            {
                tmpp1.set(targetPos);
                tmpv0.sub(tmpp1, tmpp0);
                double d2 = tmpv0.length();
                if(d2 > 4000D)
                {
                    tmpv0.scale(4000D / d2);
                    tmpp1.set(tmpp0);
                    tmpp1.add(tmpv0);
                }
            }
            com.maddox.il2.engine.Actor actor1 = com.maddox.il2.engine.Engine.collideEnv().getLine(tmpp0, tmpp1, false, aircraft1, null);
            if(actor1 != null)
            {
                if(flag)
                    return 0.0F;
                if(actor1 != actor)
                    return 0.0F;
            }
            checkObjObstacle = false;
        }
        return d1;
    }

    public static boolean checkLandObstacle;
    public static boolean checkCabinObstacle;
    public static boolean checkPlaneObstacle;
    public static boolean checkObjObstacle;
    public static com.maddox.JGP.Point3d targetPosInput;
    public static com.maddox.JGP.Point3d nosePos = new Point3d();
    private static com.maddox.JGP.Vector3d noseDir = new Vector3d();
    private static com.maddox.JGP.Vector3d targRayDir = new Vector3d();
    private static com.maddox.JGP.Point3d targetPos = new Point3d();
    private static com.maddox.il2.engine.Loc Cam2WorldLoc = new Loc();
    private static com.maddox.il2.engine.Loc Plane2WorldLoc = new Loc();
    private static com.maddox.JGP.Matrix4d Plane2WorldTM = new Matrix4d();
    private static com.maddox.JGP.Point3d tmpp0 = new Point3d();
    private static com.maddox.JGP.Point3d tmpp1 = new Point3d();
    private static com.maddox.JGP.Point3d tmpp2 = new Point3d();
    private static com.maddox.JGP.Vector3d tmpv0 = new Vector3d();
    public static float resultAng;

}

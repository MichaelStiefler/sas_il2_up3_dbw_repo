// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Rope.java

package com.maddox.il2.objects.vehicles.aeronautics;

import com.maddox.JGP.Plane3d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.Camera;
import com.maddox.il2.engine.CollideEnvXY;
import com.maddox.il2.engine.CollisionInterface;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.LightEnv;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollision;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Message;
import com.maddox.rts.Time;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.vehicles.aeronautics:
//            AeroanchoredGeneric

public class Rope extends com.maddox.il2.engine.Actor
    implements com.maddox.il2.engine.CollisionInterface, com.maddox.il2.engine.MsgCollisionListener
{
    class MyDrawer extends com.maddox.il2.engine.ActorDraw
    {

        public int preRender(com.maddox.il2.engine.Actor actor)
        {
            if(st == 2)
                return 0;
            pos.getRender(com.maddox.il2.objects.vehicles.aeronautics.Rope.p0);
            double d = com.maddox.il2.objects.vehicles.aeronautics.Rope.p0.z - height_down - 0.01D;
            if(d <= 0.0D)
                return 0;
            com.maddox.il2.objects.vehicles.aeronautics.Rope.p1.x = com.maddox.il2.objects.vehicles.aeronautics.Rope.p0.x;
            com.maddox.il2.objects.vehicles.aeronautics.Rope.p1.y = com.maddox.il2.objects.vehicles.aeronautics.Rope.p0.y;
            com.maddox.il2.objects.vehicles.aeronautics.Rope.p1.z = com.maddox.il2.objects.vehicles.aeronautics.Rope.p0.z - d * 0.5D;
            if(!com.maddox.il2.engine.Render.currentCamera().isSphereVisible(com.maddox.il2.objects.vehicles.aeronautics.Rope.p1, (float)(d * 0.5D)))
                return 0;
            int i = 1 + (int)(d / (double)ropeSegLen);
            com.maddox.il2.objects.vehicles.aeronautics.Rope.p2.x = com.maddox.il2.objects.vehicles.aeronautics.Rope.p0.x;
            com.maddox.il2.objects.vehicles.aeronautics.Rope.p2.y = com.maddox.il2.objects.vehicles.aeronautics.Rope.p0.y;
            double d1 = (double)ropeSegLen * 0.5D;
            int j = 0;
            for(int k = 0; k < i; k++)
            {
                com.maddox.il2.objects.vehicles.aeronautics.Rope.p2.z = com.maddox.il2.objects.vehicles.aeronautics.Rope.p0.z - (double)((float)k * ropeSegLen) - d1;
                com.maddox.il2.objects.vehicles.aeronautics.Rope.locStraightUp.set(com.maddox.il2.objects.vehicles.aeronautics.Rope.p2);
                ropeMesh.setPos(com.maddox.il2.objects.vehicles.aeronautics.Rope.locStraightUp);
                lightUpdate(com.maddox.il2.objects.vehicles.aeronautics.Rope.locStraightUp, true);
                soundUpdate(actor, com.maddox.il2.objects.vehicles.aeronautics.Rope.locStraightUp);
                j |= ropeMesh.preRender();
            }

            return j;
        }

        public void render(com.maddox.il2.engine.Actor actor)
        {
            super.render(actor);
            pos.getRender(com.maddox.il2.objects.vehicles.aeronautics.Rope.p0);
            double d = com.maddox.il2.objects.vehicles.aeronautics.Rope.p0.z - height_down - 0.01D;
            if(d <= 0.0D)
                return;
            com.maddox.il2.objects.vehicles.aeronautics.Rope.p1.x = com.maddox.il2.objects.vehicles.aeronautics.Rope.p0.x;
            com.maddox.il2.objects.vehicles.aeronautics.Rope.p1.y = com.maddox.il2.objects.vehicles.aeronautics.Rope.p0.y;
            com.maddox.il2.objects.vehicles.aeronautics.Rope.p1.z = com.maddox.il2.objects.vehicles.aeronautics.Rope.p0.z - d * 0.5D;
            if(!com.maddox.il2.engine.Render.currentCamera().isSphereVisible(com.maddox.il2.objects.vehicles.aeronautics.Rope.p1, (float)(d * 0.5D)))
                return;
            com.maddox.il2.engine.Render.currentCamera().pos.getAbs(com.maddox.il2.objects.vehicles.aeronautics.Rope.p3);
            int i = 1 + (int)(d / (double)ropeSegLen);
            com.maddox.il2.objects.vehicles.aeronautics.Rope.p2.x = com.maddox.il2.objects.vehicles.aeronautics.Rope.p0.x;
            com.maddox.il2.objects.vehicles.aeronautics.Rope.p2.y = com.maddox.il2.objects.vehicles.aeronautics.Rope.p0.y;
            double d1 = (double)ropeSegLen * 0.5D;
            for(int j = 0; j < i; j++)
            {
                com.maddox.il2.objects.vehicles.aeronautics.Rope.p2.z = com.maddox.il2.objects.vehicles.aeronautics.Rope.p0.z - (double)((float)j * ropeSegLen) - d1;
                if(!com.maddox.il2.engine.Render.currentCamera().isSphereVisible(com.maddox.il2.objects.vehicles.aeronautics.Rope.p2, (float)d1))
                    continue;
                double d2 = com.maddox.il2.objects.vehicles.aeronautics.Rope.p3.distanceSquared(com.maddox.il2.objects.vehicles.aeronautics.Rope.p2);
                boolean flag;
                if(d2 < 2500D)
                {
                    flag = false;
                } else
                {
                    if(d2 >= 562500D)
                        continue;
                    flag = true;
                }
                if(!flag)
                {
                    com.maddox.il2.objects.vehicles.aeronautics.Rope.locStraightUp.set(com.maddox.il2.objects.vehicles.aeronautics.Rope.p2);
                    ropeMesh.setPos(com.maddox.il2.objects.vehicles.aeronautics.Rope.locStraightUp);
                    lightUpdate(com.maddox.il2.objects.vehicles.aeronautics.Rope.locStraightUp, false);
                    com.maddox.il2.engine.Render.currentLightEnv().prepareForRender(com.maddox.il2.objects.vehicles.aeronautics.Rope.locStraightUp.getPoint(), (float)d1);
                    ropeMesh.render();
                } else
                {
                    d2 = java.lang.Math.sqrt(d2);
                    int k;
                    if(d2 > 100D)
                    {
                        float f = ((float)d2 - 100F) / 650F;
                        k = (int)(129F * (1.0F - f) + 0.0F * f);
                    } else
                    {
                        float f1 = ((float)d2 - 50F) / 50F;
                        k = (int)(255F * (1.0F - f1) + 129F * f1);
                    }
                    if(k > 0)
                    {
                        if(k > 255)
                            k = 255;
                        com.maddox.il2.engine.Render.drawBeginLines(0);
                        com.maddox.il2.objects.vehicles.aeronautics.Rope.lineXYZ[0] = com.maddox.il2.objects.vehicles.aeronautics.Rope.lineXYZ[3] = (float)com.maddox.il2.objects.vehicles.aeronautics.Rope.p2.x;
                        com.maddox.il2.objects.vehicles.aeronautics.Rope.lineXYZ[1] = com.maddox.il2.objects.vehicles.aeronautics.Rope.lineXYZ[4] = (float)com.maddox.il2.objects.vehicles.aeronautics.Rope.p2.y;
                        com.maddox.il2.objects.vehicles.aeronautics.Rope.lineXYZ[2] = (float)(com.maddox.il2.objects.vehicles.aeronautics.Rope.p2.z - (double)(ropeSegLen * 0.5F));
                        com.maddox.il2.objects.vehicles.aeronautics.Rope.lineXYZ[5] = (float)(com.maddox.il2.objects.vehicles.aeronautics.Rope.p2.z + (double)(ropeSegLen * 0.5F));
                        com.maddox.il2.engine.Render.drawLines(com.maddox.il2.objects.vehicles.aeronautics.Rope.lineXYZ, 2, 1.0F, (k << 24) + 0x50505, com.maddox.il2.engine.Render.LineFlags, 0);
                        com.maddox.il2.engine.Render.drawEnd();
                    }
                }
            }

        }

        MyDrawer()
        {
        }
    }

    class Move extends com.maddox.il2.engine.Interpolate
    {

        public boolean tick()
        {
            if(st == 1)
            {
                curPos.z -= speedOfFall;
                if(curPos.z <= height_down)
                {
                    anchor.ropeDisappeared();
                    anchor = null;
                    st = 2;
                    drawing(false);
                    postDestroy();
                    return false;
                } else
                {
                    pos.setAbs(curPos);
                    return true;
                }
            }
            if(st == 0)
            {
                pos.setAbs(curPos);
                return true;
            } else
            {
                java.lang.System.out.println("***anchor: unexpected dead");
                return true;
            }
        }

        Move()
        {
        }
    }


    public boolean collision_isEnabled()
    {
        return st == 0;
    }

    public double collision_getCylinderR()
    {
        return 32D;
    }

    public void collision_processing(com.maddox.il2.engine.Actor actor)
    {
        if(!(actor instanceof com.maddox.il2.objects.air.Aircraft))
            return;
        if(!(actor instanceof com.maddox.il2.engine.ActorHMesh))
            return;
        com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
        float f = actor.collisionR();
        double d = collision_getCylinderR() + (double)f;
        if(point3d.z - d > height_stay || point3d.z + d < height_down)
            return;
        v.sub(point3d, actor.pos.getCurrentPoint());
        if(v.lengthSquared() <= 0.00040000000000000002D)
            return;
        p3.set(curPos);
        p2.set(curPos);
        p3.z = height_stay;
        p2.z = height_down;
        p0.set(p3);
        p1.set(p2);
        p0.add(v);
        p1.add(v);
        if(v.x * v.x + v.y * v.y <= 0.00040000000000000002D)
        {
            if(v.z >= 0.0D)
            {
                p1.set(p2);
            } else
            {
                p0.set(p1);
                p1.set(p3);
            }
            if(com.maddox.il2.engine.CollideEnvXY.intersectLineSphere(p0.x, p0.y, p0.z, p1.x, p1.y, p1.z, point3d.x, point3d.y, point3d.z, f) < 0.0D)
                return;
            com.maddox.il2.engine.Loc loc = actor.pos.getAbs();
            com.maddox.il2.engine.Mesh mesh = ((com.maddox.il2.engine.ActorMesh)actor).mesh();
            double d1 = mesh.detectCollisionLine(actor.pos.getAbs(), p0, p1);
            if(d1 >= 0.0D && d1 <= 1.0D)
            {
                long l = com.maddox.rts.Time.tick() + (long)(d1 * (double)com.maddox.rts.Time.tickLenFms());
                if(l >= com.maddox.rts.Time.tickNext())
                    l = com.maddox.rts.Time.tickNext() - 1L;
                com.maddox.il2.engine.MsgCollision.post2(l, this, actor, "<edge>", com.maddox.il2.engine.Mesh.collisionChunk(0));
            }
            return;
        }
        e1.sub(p2, p1);
        e2.sub(p0, p1);
        norm.cross(e1, e2);
        loc = norm.length();
        if(loc < 0.001D)
        {
            java.lang.System.out.println("***rope: normal");
            return;
        }
        norm.scale(1.0D / loc);
        qplane.set(norm, p1);
        loc = qplane.deviation(point3d);
        if(loc >= (double)f || loc <= (double)(-f))
            return;
        e2.sub(p1, p0);
        bplane.N.cross(e2, qplane.N);
        bplane.N.normalize();
        bplane.D = -bplane.N.dot(p0);
        if(bplane.deviation(point3d) > (double)f)
            return;
        e2.sub(p3, p2);
        bplane.N.cross(e2, qplane.N);
        bplane.N.normalize();
        bplane.D = -bplane.N.dot(p2);
        if(bplane.deviation(point3d) > (double)f)
            return;
        com.maddox.il2.engine.Loc loc1 = actor.pos.getAbs();
        com.maddox.il2.engine.HierMesh hiermesh = ((com.maddox.il2.engine.ActorHMesh)actor).hierMesh();
        int i = hiermesh.detectCollision_Quad_Multi(actor.pos.getAbs(), p0, p1, p2, p3);
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.HierMesh _tmp = hiermesh;
            double d2 = com.maddox.il2.engine.HierMesh.collisionDistMulti(j);
            if(d2 >= 0.0D && d2 <= 1.0D)
            {
                com.maddox.il2.engine.HierMesh _tmp1 = hiermesh;
                java.lang.String s = com.maddox.il2.engine.HierMesh.collisionNameMulti(j, 0);
                long l1 = com.maddox.rts.Time.tick() + (long)(d2 * (double)com.maddox.rts.Time.tickLenFms());
                if(l1 >= com.maddox.rts.Time.tickNext())
                    l1 = com.maddox.rts.Time.tickNext() - 1L;
                com.maddox.il2.engine.MsgCollision.post2(l1, this, actor, "<edge>", s);
            }
        }

    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if(st == 0 && anchor != null)
            anchor.ropeCollision(actor);
    }

    void somebodyKilled(int i)
    {
        if(st != 0)
        {
            return;
        } else
        {
            st = 1;
            return;
        }
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public Rope(com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric aeroanchoredgeneric, float f, float f1, java.lang.String s)
    {
        anchor = null;
        st = 0;
        ropeMesh = null;
        ropeSegLen = 0.0F;
        curPos = new Point3d();
        lastColliedChunkName = null;
        ropeMesh = new Mesh(s);
        ropeSegLen = f1;
        anchor = aeroanchoredgeneric;
        anchor.pos.getAbs(curPos);
        height_down = com.maddox.il2.engine.Engine.land().HQ(curPos.x, curPos.y);
        height_stay = height_down + (double)f;
        st = 0;
        curPos.z = height_stay;
        o.setYPR(anchor.pos.getAbsOrient().getYaw(), 0.0F, 0.0F);
        pos = new ActorPosMove(this);
        pos.setAbs(curPos, o);
        pos.reset();
        locStraightUp.set(curPos, o);
        setArmy(0);
        collide(false);
        drawing(true);
        draw = new MyDrawer();
        speedOfFall = 25D * (double)com.maddox.rts.Time.tickLenFs();
        if(interpGet("move") == null)
            interpPut(new Move(), "move", com.maddox.rts.Time.current(), null);
    }

    private com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric anchor;
    private static final int ST_STAY = 0;
    private static final int ST_FALL = 1;
    private static final int ST_DEAD = 2;
    private int st;
    private com.maddox.il2.engine.Mesh ropeMesh;
    private float ropeSegLen;
    private com.maddox.JGP.Point3d curPos;
    private double height_stay;
    private double height_down;
    private double speedOfFall;
    private java.lang.String lastColliedChunkName;
    private static com.maddox.il2.engine.Loc locStraightUp = new Loc();
    private static com.maddox.il2.engine.Orient o = new Orient();
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.JGP.Point3d p0 = new Point3d();
    private static com.maddox.JGP.Point3d p1 = new Point3d();
    private static com.maddox.JGP.Point3d p2 = new Point3d();
    private static com.maddox.JGP.Point3d p3 = new Point3d();
    private static com.maddox.JGP.Vector3d v = new Vector3d();
    private static com.maddox.JGP.Vector3d norm = new Vector3d();
    private static com.maddox.JGP.Vector3d e1 = new Vector3d();
    private static com.maddox.JGP.Vector3d e2 = new Vector3d();
    private static com.maddox.JGP.Plane3d qplane = new Plane3d();
    private static com.maddox.JGP.Plane3d bplane = new Plane3d();
    private static float lineXYZ[] = new float[6];
















}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Wreckage.java

package com.maddox.il2.objects;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.ShipGeneric;
import com.maddox.rts.Message;
import com.maddox.rts.MsgDestroy;
import com.maddox.rts.NetObj;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects:
//            Wreck

public class Wreckage extends com.maddox.il2.engine.ActorMesh
{
    static class ClipFilter
        implements com.maddox.il2.engine.ActorFilter
    {

        public boolean isUse(com.maddox.il2.engine.Actor actor, double d)
        {
            return (actor instanceof com.maddox.il2.objects.ships.BigshipGeneric) || (actor instanceof com.maddox.il2.objects.ships.ShipGeneric);
        }

        ClipFilter()
        {
        }
    }

    class Interpolater extends com.maddox.il2.engine.Interpolate
    {

        private double deceleron(double d, float f)
        {
            if(d > 0.0D)
                d -= (double)f * d * d;
            else
                d += (double)f * d * d;
            return d;
        }

        public boolean tick()
        {
            float f = com.maddox.rts.Time.tickLenFs();
            float f2 = (float)v.length();
            float f1 = 10F / (f2 + 0.1F);
            if(f1 > 1.0F)
                f1 = 1.0F;
            f1 *= com.maddox.il2.objects.Wreckage.dv[lr] * f;
            pos.getAbs(com.maddox.il2.objects.Wreckage.p, com.maddox.il2.objects.Wreckage.o);
            com.maddox.il2.objects.Wreckage.o.increment(W.z * f, W.y * f, -W.x * f);
            com.maddox.il2.objects.Wreckage.oh.set(f1, 0.0F, 0.0F);
            com.maddox.il2.objects.Wreckage.oh.transform(v);
            com.maddox.il2.objects.Wreckage.o.setYaw(com.maddox.il2.objects.Wreckage.o.getYaw() - f1);
            float f4 = A * f;
            v.x = deceleron(v.x, f4);
            v.y = deceleron(v.y, f4);
            v.z = deceleron(v.z, f4);
            v.z -= com.maddox.il2.ai.World.g() * f;
            com.maddox.il2.objects.Wreckage.p.scaleAdd(f, v, com.maddox.il2.objects.Wreckage.p);
            double d = com.maddox.il2.ai.World.land().HQ(com.maddox.il2.objects.Wreckage.p.x, com.maddox.il2.objects.Wreckage.p.y);
            if(com.maddox.il2.objects.Wreckage.p.z <= d)
            {
                com.maddox.il2.ai.World.land().N(com.maddox.il2.objects.Wreckage.p.x, com.maddox.il2.objects.Wreckage.p.y, com.maddox.il2.objects.Wreckage.Nf);
                com.maddox.il2.objects.Wreckage.N.set(com.maddox.il2.objects.Wreckage.Nf);
                float f3;
                if((f3 = (float)v.dot(com.maddox.il2.objects.Wreckage.N)) < 0.0F)
                {
                    if(f3 < -40F)
                        f3 = -40F;
                    com.maddox.il2.objects.Wreckage.N.scale(2.0F * f3);
                    v.sub(com.maddox.il2.objects.Wreckage.N);
                    v.scale(0.5D);
                    if(com.maddox.il2.ai.World.land().isWater(com.maddox.il2.objects.Wreckage.p.x, com.maddox.il2.objects.Wreckage.p.y))
                    {
                        com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current(), actor);
                        com.maddox.il2.objects.Wreckage.WreckageONLYDrop_Water(com.maddox.il2.objects.Wreckage.p);
                        return false;
                    }
                    if(!bBoundToBeDestroyed && java.lang.Math.abs(v.z) < 1.0D)
                    {
                        com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current() + 0x13880L, actor);
                        bBoundToBeDestroyed = true;
                        return false;
                    }
                }
                com.maddox.il2.objects.Wreckage.p.z = d;
            }
            pos.setAbs(com.maddox.il2.objects.Wreckage.p, com.maddox.il2.objects.Wreckage.o);
            return true;
        }

        Interpolater()
        {
        }
    }


    public void setSpeed(com.maddox.JGP.Vector3d vector3d)
    {
        v.set(vector3d);
        v.z += 5D;
        if(v.z > 50D)
            v.z = 50D;
    }

    private void construct(float f)
    {
        collide(true);
        drawing(true);
        getDimensions(sz);
        M = f;
        float f1 = sz.x * sz.y + sz.x * sz.z + sz.z * sz.y;
        if(f1 < 0.01F)
            f1 = 0.01F;
        if(M < 0.001F)
        {
            float f2 = sz.x * sz.y * sz.z;
            if(f2 < 0.01F)
                f2 = 0.01F;
            float f4 = f1 * 2.0F * 0.02F;
            M = 500F * java.lang.Math.min(f2, f4);
        }
        float f3 = sz.x;
        float f5 = sz.y;
        float f6 = sz.z;
        int i = 0;
        int j = 1;
        int k = 2;
        if(f3 > f5)
        {
            float f7 = f3;
            f3 = f5;
            f5 = f7;
            int l = i;
            i = j;
            j = l;
        }
        if(f5 > f6)
        {
            float f8 = f5;
            f5 = f6;
            f6 = f8;
            int i1 = j;
            j = k;
            k = i1;
            if(f3 > f5)
            {
                float f9 = f3;
                f3 = f5;
                f5 = f9;
                int j1 = i;
                i = j;
                j = j1;
            }
        }
        if(f5 * 8F < f6)
        {
            switch(i)
            {
            case 0: // '\0'
                W.set(1.0F, 0.0F, 0.0F);
                break;

            case 1: // '\001'
                W.set(0.0F, 1.0F, 0.0F);
                break;

            case 2: // '\002'
                W.set(0.0F, 0.0F, 1.0F);
                break;
            }
            wn = k;
        } else
        if(f3 * 3F < f5)
        {
            switch(k)
            {
            case 0: // '\0'
                W.set(1.0F, 0.0F, 0.0F);
                break;

            case 1: // '\001'
                W.set(0.0F, 1.0F, 0.0F);
                break;

            case 2: // '\002'
                W.set(0.0F, 0.0F, 1.0F);
                break;
            }
            wn = j;
        } else
        {
            W.set(sz);
            if(W.x < 1E-010F)
                W.x += 1E-010F;
            W.normalize();
            wn = k;
        }
        wn = 60F + 80F / (wn + 0.2F);
        W.scale(wn);
        lr = com.maddox.il2.ai.World.Rnd().nextInt(0, 8);
        A = 0.02F / M;
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current() + 0x1d4c0L, this);
    }

    float DEG2RAD(float f)
    {
        return f * 0.01745329F;
    }

    float RAD2DEG(float f)
    {
        return f * 57.29578F;
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public Wreckage(com.maddox.il2.engine.ActorHMesh actorhmesh, int i)
    {
        super(actorhmesh, i);
        v = new Vector3d();
        W = new Vector3f();
        bBoundToBeDestroyed = false;
        construct(actorhmesh.getChunkMass());
        actorhmesh.hierMesh().setCurChunk(i);
        if(actorhmesh instanceof com.maddox.il2.objects.air.Aircraft)
        {
            setOwner(actorhmesh, false, false, false);
            netOwner = ((com.maddox.il2.objects.air.Aircraft)actorhmesh).netUser();
        }
    }

    private static void WreckageONLYDrop_Water(com.maddox.JGP.Point3d point3d)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        long l = com.maddox.rts.Time.current();
        if(l == timeLastWreckageDrop_Water)
        {
            double d = posLastWreckageDrop_Water.x - point3d.x;
            double d1 = posLastWreckageDrop_Water.y - point3d.y;
            if(d * d + d1 * d1 < 100D)
                return;
        }
        pClipZ1.set(p);
        pClipZ2.set(p);
        pClipZ1.z -= 2D;
        pClipZ2.z += 42D;
        com.maddox.il2.engine.Actor actor = com.maddox.il2.engine.Engine.collideEnv().getLine(pClipZ2, pClipZ1, false, clipFilter, pClipRes);
        if(com.maddox.il2.engine.Actor.isValid(actor))
        {
            return;
        } else
        {
            timeLastWreckageDrop_Water = l;
            posLastWreckageDrop_Water.set(point3d);
            com.maddox.il2.objects.effects.Explosions.WreckageDrop_Water(point3d);
            return;
        }
    }

    public static com.maddox.il2.objects.Wreck makeWreck(com.maddox.il2.engine.ActorHMesh actorhmesh, int i)
    {
        com.maddox.il2.engine.HierMesh hiermesh = actorhmesh.hierMesh();
        hiermesh.setCurChunk(i);
        int ai[] = hiermesh.getSubTrees(hiermesh.chunkName());
        if(ai == null || ai.length < 1)
            return null;
        double d = 0.0D;
        com.maddox.JGP.Point3d point3d = new Point3d(0.0D, 0.0D, 0.0D);
        com.maddox.JGP.Point3f point3f = new Point3f();
        com.maddox.JGP.Point3f point3f1 = new Point3f();
        com.maddox.JGP.Point3d point3d1 = new Point3d();
        for(int j = 0; j < ai.length; j++)
        {
            hiermesh.setCurChunk(ai[j]);
            if(hiermesh.isChunkVisible())
            {
                hiermesh.getChunkCurVisBoundBox(point3f, point3f1);
                point3d1.set(point3f);
                point3d1.add(point3f1.x, point3f1.y, point3f1.z);
                point3d1.scale(0.5D);
                hiermesh.getChunkLocObj(LO);
                LO.transform(point3d1);
                point3f1.sub(point3f);
                double d1 = point3f1.x * point3f1.y * point3f1.z;
                double d2 = 500D;
                double d3 = d1 * d2;
                d += d3;
                point3d1.scale(d3);
                point3d.add(point3d1);
            }
        }

        if(d > 0.0001D)
        {
            point3d.scale(1.0D / d);
        } else
        {
            hiermesh.setCurChunk(i);
            hiermesh.getChunkLocObj(LO);
            point3d.set(LO.getPoint());
        }
        hiermesh.setCurChunk(i);
        hiermesh.getChunkLocObj(LO);
        LO.getPoint().set(point3d);
        com.maddox.il2.engine.HierMesh hiermesh1 = new HierMesh(hiermesh, i, LO);
        com.maddox.il2.engine.Loc loc = new Loc();
        actorhmesh.pos.getAbs(loc);
        LO.add(loc);
        return new Wreck(hiermesh1, LO);
    }

    public static java.lang.String SMOKE = "EFFECTS/Smokes/SmokeBlack_Wreckage.eff";
    public static java.lang.String SMOKE_EXPLODE = "EFFECTS/Smokes/SmokeBlack_Wreckage_Explode.eff";
    public static java.lang.String FIRE = "EFFECTS/Explodes/Wreckage_Burn.eff";
    private float M;
    private float A;
    private int lr;
    private com.maddox.JGP.Vector3d v;
    private com.maddox.JGP.Vector3f W;
    private static com.maddox.JGP.Vector3d N = new Vector3d();
    private static com.maddox.JGP.Vector3f Nf = new Vector3f();
    private static com.maddox.JGP.Vector3f sz = new Vector3f();
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();
    private static com.maddox.il2.engine.Orient oh = new Orient();
    private static float wn;
    private static float dv[] = {
        -80F, -60F, -40F, -20F, 20F, 40F, 60F, 80F, 0.0F
    };
    private boolean bBoundToBeDestroyed;
    public com.maddox.rts.NetObj netOwner;
    private static long timeLastWreckageDrop_Water = 0L;
    private static com.maddox.JGP.Point3d posLastWreckageDrop_Water = new Point3d();
    private static com.maddox.JGP.Point3d pClipZ1 = new Point3d();
    private static com.maddox.JGP.Point3d pClipZ2 = new Point3d();
    private static com.maddox.JGP.Point3d pClipRes = new Point3d();
    static com.maddox.il2.objects.ClipFilter clipFilter = new ClipFilter();
    private static com.maddox.il2.engine.Loc LO = new Loc();














}

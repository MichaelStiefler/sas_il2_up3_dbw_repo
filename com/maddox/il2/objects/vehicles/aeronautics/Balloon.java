// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Balloon.java

package com.maddox.il2.objects.vehicles.aeronautics;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.ai.ground.TgtFlak;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.vehicles.artillery.AAA;
import com.maddox.rts.Message;
import com.maddox.rts.Time;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.vehicles.aeronautics:
//            AeroanchoredGeneric

public class Balloon extends com.maddox.il2.engine.ActorMesh
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.engine.MsgCollisionListener, com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener, com.maddox.il2.ai.ground.Prey, com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
{
    class Move extends com.maddox.il2.engine.Interpolate
    {

        public boolean tick()
        {
            if(st == 1)
            {
                curPos.z += speedOfGoUp;
                if(curPos.z >= height_top)
                {
                    curPos.z = height_top;
                    return false;
                } else
                {
                    pos.setAbs(curPos);
                    return true;
                }
            }
            if(st == 2)
                if(com.maddox.rts.Time.current() < disappearTime)
                {
                    return true;
                } else
                {
                    anchor.balloonDisappeared();
                    anchor = null;
                    st = 3;
                    collide(false);
                    drawing(false);
                    postDestroy();
                    return false;
                }
            if(st == 0)
            {
                java.lang.System.out.println("***balloon: unexpected stay");
                return true;
            } else
            {
                java.lang.System.out.println("***balloon: unexpected dead");
                return true;
            }
        }

        Move()
        {
        }
    }


    public void msgCollisionRequest(com.maddox.il2.engine.Actor actor, boolean aflag[])
    {
        if(st == 3 || anchor == null)
            aflag[0] = false;
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if(st != 3 && st != 2 && anchor != null)
            anchor.balloonCollision(actor);
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        shot.bodyMaterial = 3;
        if(st == 2 || st == 3)
            return;
        if(shot.power <= 0.0F)
            return;
        if(shot.powerType == 1)
        {
            if(anchor != null)
                anchor.balloonShot(shot.initiator);
            return;
        }
        if(shot.v.length() < 40D)
            return;
        if(anchor != null)
            anchor.balloonShot(shot.initiator);
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        if(st == 2 || st == 3)
            return;
        float f = 0.01F;
        float f1 = 0.09F;
        com.maddox.il2.ai.Explosion _tmp = explosion;
        if(com.maddox.il2.ai.Explosion.killable(this, explosion.receivedTNT_1meter(this), f, f1, 0.0F) && anchor != null)
            anchor.balloonExplosion(explosion.initiator);
    }

    public int HitbyMask()
    {
        return -1;
    }

    public int chooseBulletType(com.maddox.il2.engine.BulletProperties abulletproperties[])
    {
        if(st == 2 || st == 3)
            return -1;
        if(abulletproperties.length == 1)
            return 0;
        if(abulletproperties.length <= 0)
            return -1;
        if(abulletproperties[0].power <= 0.0F)
            return 1;
        if(abulletproperties[0].powerType == 1)
            return 0;
        if(abulletproperties[1].powerType == 1)
            return 1;
        if(abulletproperties[0].cumulativePower > 0.0F)
            return 1;
        return abulletproperties[0].powerType != 2 ? 0 : 1;
    }

    public int chooseShotpoint(com.maddox.il2.engine.BulletProperties bulletproperties)
    {
        return st != 2 && st != 3 ? 0 : -1;
    }

    public boolean getShotpointOffset(int i, com.maddox.JGP.Point3d point3d)
    {
        if(st == 2 || st == 3)
            return false;
        if(i != 0)
            return false;
        if(point3d != null)
            point3d.set(0.0D, 0.0D, 0.0D);
        return true;
    }

    void somebodyKilled(int i)
    {
        if(st == 2 || st == 3)
            return;
        if(i == 98)
        {
            st = 2;
            com.maddox.il2.engine.Loc loc = new Loc();
            loc.set(pos.getAbs());
            loc.getPoint().z += balloonCenterOffset;
            com.maddox.il2.objects.effects.Explosions.HydrogenBalloonExplosion(loc, null);
            disappearTime = com.maddox.rts.Time.current() + 800L;
            if(interpGet("move") == null)
                interpPut(new Move(), "move", com.maddox.rts.Time.current(), null);
            return;
        }
        if(st == 0)
        {
            st = 1;
            if(interpGet("move") == null)
                interpPut(new Move(), "move", com.maddox.rts.Time.current(), null);
            return;
        } else
        {
            return;
        }
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public Balloon(com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric aeroanchoredgeneric, float f, float f1, boolean flag)
    {
        super(aeroanchoredgeneric.prop.meshBName);
        anchor = null;
        st = 0;
        curPos = new Point3d();
        balloonCenterOffset = 0.0F;
        anchor = aeroanchoredgeneric;
        anchor.pos.getAbs(curPos);
        height_stay = com.maddox.il2.engine.Engine.land().HQ(curPos.x, curPos.y) + (double)f;
        height_top = f1;
        if(flag)
        {
            st = 1;
            curPos.z = height_top;
        } else
        {
            st = 0;
            curPos.z = height_stay;
        }
        o.setYPR(anchor.pos.getAbsOrient().getYaw(), 0.0F, 0.0F);
        pos.setAbs(curPos, o);
        pos.reset();
        setArmy(anchor.getArmy());
        collide(true);
        drawing(true);
        speedOfGoUp = 5D * (double)com.maddox.rts.Time.tickLenFs();
        balloonCenterOffset = 0.0F;
        int i = mesh().hookFind("corner0");
        int j = mesh().hookFind("corner1");
        if(i != -1 && j != -1)
        {
            com.maddox.JGP.Matrix4d matrix4d = new Matrix4d();
            mesh().hookMatrix(i, matrix4d);
            balloonCenterOffset = (float)matrix4d.m23;
            mesh().hookMatrix(j, matrix4d);
            balloonCenterOffset += (float)matrix4d.m23;
            balloonCenterOffset *= 0.5F;
        }
    }

    private com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric anchor;
    private static final int ST_STAY = 0;
    private static final int ST_GOUP = 1;
    private static final int ST_BURN = 2;
    private static final int ST_DEAD = 3;
    private int st;
    private com.maddox.JGP.Point3d curPos;
    private double height_stay;
    private double height_top;
    private double speedOfGoUp;
    private long disappearTime;
    private float balloonCenterOffset;
    private static com.maddox.il2.engine.Orient o = new Orient();









}

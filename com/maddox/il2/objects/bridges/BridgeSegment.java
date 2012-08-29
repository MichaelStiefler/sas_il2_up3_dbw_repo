// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BridgeSegment.java

package com.maddox.il2.objects.bridges;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector2d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.ChiefGround;
import com.maddox.il2.ai.ground.TgtShip;
import com.maddox.il2.ai.ground.UnitInterface;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.Statics;
import com.maddox.rts.Message;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.bridges:
//            LongBridge

public class BridgeSegment extends com.maddox.il2.engine.ActorMesh
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.engine.MsgCollisionListener, com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener
{

    public boolean isStaticPos()
    {
        return true;
    }

    public int Code()
    {
        return (brID << 16) + ID;
    }

    public boolean IsDamaged()
    {
        return life[0] <= 0.0F || life[1] <= 0.0F;
    }

    private boolean IsDead()
    {
        return life[0] <= 0.0F && life[1] <= 0.0F;
    }

    boolean IsDead(int i)
    {
        return life[i] <= 0.0F;
    }

    private static java.lang.String NameByIdx(int i, int j)
    {
        return new String(" Bridge" + i + "Seg" + j);
    }

    public static com.maddox.il2.objects.bridges.BridgeSegment getByIdx(int i, int j)
    {
        return (com.maddox.il2.objects.bridges.BridgeSegment)com.maddox.il2.engine.Actor.getByName(com.maddox.il2.objects.bridges.BridgeSegment.NameByIdx(i, j));
    }

    public static boolean isEncodedSegmentDamaged(int i)
    {
        return com.maddox.il2.objects.bridges.BridgeSegment.getByIdx(i >> 16, i & 0x7fff).IsDamaged();
    }

    private double getNearestDistToSegment(com.maddox.JGP.Point3d point3d)
    {
        com.maddox.JGP.Point3d point3d1 = new Point3d();
        com.maddox.JGP.Point3d point3d2 = new Point3d();
        com.maddox.JGP.Point3d point3d3 = new Point3d();
        com.maddox.JGP.Point3d point3d4 = new Point3d();
        ((com.maddox.il2.objects.bridges.LongBridge)getOwner()).ComputeSegmentKeyPoints(ID, point3d1, point3d2, point3d3, point3d4);
        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        vector3d.sub(point3d, point3d2);
        com.maddox.JGP.Vector3d vector3d1 = new Vector3d();
        vector3d1.sub(point3d4, point3d2);
        double d = vector3d.dot(vector3d1);
        if(d <= 0.0D)
            return point3d2.distance(point3d);
        double d1 = vector3d1.lengthSquared();
        if(d >= d1)
            return point3d4.distance(point3d);
        d = vector3d.length() - (d * d) / d1;
        if(d <= 0.0D)
            return 0.0D;
        else
            return java.lang.Math.sqrt(d);
    }

    public void msgCollisionRequest(com.maddox.il2.engine.Actor actor, boolean aflag[])
    {
        if(actor instanceof com.maddox.il2.ai.ground.UnitInterface)
        {
            com.maddox.il2.engine.Actor actor1 = actor.getOwner();
            if(actor1 != null && (actor1 instanceof com.maddox.il2.ai.ground.ChiefGround))
                aflag[0] = false;
        }
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if(!(actor instanceof com.maddox.il2.ai.ground.TgtShip))
            return;
        if(IsDead())
            return;
        if(isNetMirror())
        {
            return;
        } else
        {
            damagedByTNT(actor.pos.getAbsPoint(), maxLife + 1.0F, actor);
            return;
        }
    }

    private void damagedByTNT(com.maddox.JGP.Point3d point3d, float f, com.maddox.il2.engine.Actor actor)
    {
        if(f <= ignoreTNT)
            return;
        if(f < 2.0F * ignoreTNT && ignoreTNT > 1E-005F)
        {
            f = (f - ignoreTNT) / ignoreTNT;
            f *= 2.0F * ignoreTNT;
        }
        com.maddox.JGP.Point3d point3d1 = pos.getAbsPoint();
        int i = dir2d.x * (point3d.x - point3d1.x) + dir2d.y * (point3d.y - point3d1.y) >= 0.0D ? 1 : 0;
        if(life[i] <= 0.0F)
        {
            return;
        } else
        {
            LifeChanged(i, life[i] - f, actor, true);
            return;
        }
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        shot.bodyMaterial = ((com.maddox.il2.objects.bridges.LongBridge)getOwner()).bodyMaterial;
        if(IsDead())
            return;
        if(shot.power <= 0.0F)
            return;
        if(isNetMirror())
            return;
        if(shot.powerType == 1)
        {
            return;
        } else
        {
            damagedByTNT(shot.p, shot.powerToTNT(), shot.initiator);
            return;
        }
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        if(IsDead())
            return;
        if(isNetMirror())
            return;
        float f = (float)getNearestDistToSegment(explosion.p);
        f -= ((com.maddox.il2.objects.bridges.LongBridge)getOwner()).getWidth();
        if(f <= 0.0F)
            f = 0.0F;
        damagedByTNT(explosion.p, explosion.receivedTNT_1meter(f), explosion.initiator);
    }

    void ForcePartState(int i, boolean flag)
    {
        life[i] = flag ? maxLife : 0.0F;
        com.maddox.il2.objects.bridges.LongBridge longbridge = (com.maddox.il2.objects.bridges.LongBridge)getOwner();
        longbridge.SetSegmentDamageState(false, this, ID, life[0], life[1], null);
    }

    void ForcePartDestroing(int i, com.maddox.il2.engine.Actor actor)
    {
        if(life[i] <= 0.0F)
        {
            return;
        } else
        {
            LifeChanged(i, 0.0F, actor, false);
            return;
        }
    }

    public void netForcePartDestroing(int i, com.maddox.il2.engine.Actor actor)
    {
        if(life[i] <= 0.0F)
        {
            return;
        } else
        {
            LifeChanged(i, 0.0F, actor, com.maddox.il2.game.Mission.isServer());
            return;
        }
    }

    private void LifeChanged(int i, float f, com.maddox.il2.engine.Actor actor, boolean flag)
    {
        if(f <= 0.0F)
            f = 0.0F;
        if(i < 0)
        {
            life[0] = life[1] = f;
            com.maddox.il2.objects.bridges.LongBridge longbridge = (com.maddox.il2.objects.bridges.LongBridge)getOwner();
            longbridge.SetSegmentDamageState(false, this, ID, life[0], life[1], actor);
        } else
        {
            if(f <= 0.0F && flag && !com.maddox.il2.ai.World.cur().statics.onBridgeDied(brID, ID, i, actor))
                return;
            boolean flag1 = IsDead(i);
            life[i] = f;
            if(IsDead(i) != flag1)
            {
                com.maddox.il2.objects.bridges.LongBridge longbridge1 = (com.maddox.il2.objects.bridges.LongBridge)getOwner();
                if(IsDead(i))
                    longbridge1.ShowSegmentExplosion(this, ID, i);
                longbridge1.SetSegmentDamageState(true, this, ID, life[0], life[1], actor);
            }
        }
    }

    public void destroy()
    {
        super.destroy();
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public BridgeSegment(com.maddox.il2.objects.bridges.LongBridge longbridge, int i, int j, float f, float f1, com.maddox.JGP.Point3d point3d, int k)
    {
        life = new float[2];
        ignoreTNT = 0.01F;
        setOwner(longbridge);
        if(f <= 0.0F)
        {
            java.lang.System.out.println("*** Internal error in BridgeSegment");
            float f2 = (1.0F / 0.0F);
        }
        brID = i;
        ID = j;
        setName(com.maddox.il2.objects.bridges.BridgeSegment.NameByIdx(brID, ID));
        setArmy(0);
        pos.setAbs(point3d);
        pos.reset();
        dirOct = k;
        switch(dirOct)
        {
        case 0: // '\0'
            dir2d = new Vector2d(1.0D, 0.0D);
            break;

        case 1: // '\001'
            dir2d = new Vector2d(1.0D, 1.0D);
            break;

        case 2: // '\002'
            dir2d = new Vector2d(0.0D, 1.0D);
            break;

        case 3: // '\003'
            dir2d = new Vector2d(-1D, 1.0D);
            break;

        case 4: // '\004'
            dir2d = new Vector2d(-1D, 0.0D);
            break;

        case 5: // '\005'
            dir2d = new Vector2d(-1D, -1D);
            break;

        case 6: // '\006'
            dir2d = new Vector2d(0.0D, -1D);
            break;

        case 7: // '\007'
            dir2d = new Vector2d(1.0D, -1D);
            break;

        default:
            throw new RuntimeException("Bad bridge's direction");
        }
        ignoreTNT = f1 > 0.0F ? f1 : 0.0F;
        maxLife = f;
        LifeChanged(-1, maxLife, null, false);
    }

    private int ID;
    private int brID;
    private int dirOct;
    private com.maddox.JGP.Vector2d dir2d;
    private float life[];
    private float maxLife;
    private float ignoreTNT;
}

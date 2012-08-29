// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PNodes.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorException;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Mat;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.builder:
//            PPoint, Path, PathChief, PathFind

public class PNodes extends com.maddox.il2.builder.PPoint
{

    public double len()
    {
        if(posXYZ == null)
            return 0.0D;
        if(posXYZ.length / 4 <= 1)
            return 0.0D;
        double d = posXYZ[0];
        double d2 = posXYZ[1];
        double d4 = posXYZ[2];
        _prevP.set(d, d2, d4);
        double d6 = 0.0D;
        for(int i = 1; i < posXYZ.length / 4; i++)
        {
            double d1 = posXYZ[i * 4 + 0];
            double d3 = posXYZ[i * 4 + 1];
            double d5 = posXYZ[i * 4 + 2];
            _curP.set(d1, d3, d5);
            d6 += _prevP.distance(_curP);
            _prevP.set(_curP);
        }

        return d6;
    }

    public double computeTime(com.maddox.il2.builder.PNodes pnodes)
    {
        double d = len();
        if(d == 0.0D)
            return 0.0D;
        double d1 = 0.0D;
        int i = posXYZ.length / 4;
        double d2 = posXYZ[0];
        double d4 = posXYZ[1];
        double d6 = posXYZ[2];
        _prevP.set(d2, d4, d6);
        double d8 = 0.0D;
        for(int j = 1; j < i; j++)
        {
            double d3 = posXYZ[j * 4 + 0];
            double d5 = posXYZ[j * 4 + 1];
            double d7 = posXYZ[j * 4 + 2];
            _curP.set(d3, d5, d7);
            double d9 = _prevP.distance(_curP);
            double d10 = speed * (d8 / d) + pnodes.speed * ((d - d8) / d);
            d8 += d9;
            double d11 = speed * (d8 / d) + pnodes.speed * ((d - d8) / d);
            double d12 = (d10 + d11) / 2D;
            if(j == 1)
                if(timeoutMin != 0.0D)
                    d10 = 0.0D;
                else
                    d10 = speed;
            if(j == i - 1)
                if(pnodes.timeoutMin != 0.0D)
                    d11 = 0.0D;
                else
                    d11 = pnodes.speed;
            if(d10 == 0.0D && d11 == 0.0D)
                d12 /= 2D;
            else
                d12 = (d10 + d11) / 2D;
            d1 += d9 / d12;
            _prevP.set(_curP);
        }

        return d1;
    }

    private void syncPathPos()
    {
        com.maddox.il2.builder.Path path = (com.maddox.il2.builder.Path)getOwner();
        if(path.points() <= 1)
            return;
        com.maddox.il2.builder.PNodes pnodes = (com.maddox.il2.builder.PNodes)path.point(0);
        _curP.set(pnodes.posXYZ[0], pnodes.posXYZ[1], pnodes.posXYZ[2]);
        com.maddox.JGP.Point3d point3d = pnodes.pos.getAbsPoint();
        if(!point3d.equals(_curP))
        {
            pnodes.pos.setAbs(_curP);
            pnodes.pos.reset();
            _curP.set(pnodes.posXYZ[4], pnodes.posXYZ[5], pnodes.posXYZ[6]);
            ((com.maddox.il2.builder.PathChief)path).placeUnits(pnodes.pos.getAbsPoint(), _curP);
        }
        for(int i = 1; i < path.points(); i++)
        {
            com.maddox.il2.builder.PNodes pnodes1 = (com.maddox.il2.builder.PNodes)path.point(i - 1);
            int j = pnodes1.posXYZ.length / 4 - 1;
            _curP.set(pnodes1.posXYZ[j * 4 + 0], pnodes1.posXYZ[j * 4 + 1], pnodes1.posXYZ[j * 4 + 2]);
            path.point(i).pos.setAbs(_curP);
            path.point(i).pos.reset();
        }

    }

    public void moveTo(com.maddox.JGP.Point3d point3d)
    {
        com.maddox.il2.builder.Path path = (com.maddox.il2.builder.Path)getOwner();
        int i = path.moveType;
        if(i >= 0)
            com.maddox.il2.builder.PathFind.setMoverType(i);
        pos.getAbs(_movePrevP);
        pos.setAbs(point3d);
        align();
        int j = path.points();
        if(j == 1)
        {
            if(!com.maddox.il2.builder.PathFind.setStartPoint(0, this))
            {
                pos.setAbs(_movePrevP);
                throw new ActorException("PathPoint not Reacheable");
            }
        } else
        {
            int k = path.pointIndx(this);
            if(k == j - 1)
            {
                com.maddox.il2.builder.PNodes pnodes = (com.maddox.il2.builder.PNodes)path.point(k - 1);
                if(!com.maddox.il2.builder.PathFind.setStartPoint(0, pnodes) || !com.maddox.il2.builder.PathFind.isPointReacheable(0, this))
                {
                    pos.setAbs(_movePrevP);
                    throw new ActorException("PathPoint not Reacheable");
                }
                pnodes.posXYZ = com.maddox.il2.builder.PathFind.buildPath(0, this);
            } else
            if(k == 0)
            {
                com.maddox.il2.builder.PNodes pnodes1 = (com.maddox.il2.builder.PNodes)path.point(k + 1);
                if(!com.maddox.il2.builder.PathFind.setStartPoint(0, pnodes1) || !com.maddox.il2.builder.PathFind.isPointReacheable(0, this))
                {
                    pos.setAbs(_movePrevP);
                    throw new ActorException("PathPoint not Reacheable");
                }
                posXYZ = com.maddox.il2.builder.PathFind.buildPath(0, this);
                reversePathXY(posXYZ);
            } else
            {
                com.maddox.il2.builder.PNodes pnodes2 = (com.maddox.il2.builder.PNodes)path.point(k - 1);
                com.maddox.il2.builder.PNodes pnodes3 = (com.maddox.il2.builder.PNodes)path.point(k + 1);
                if(!com.maddox.il2.builder.PathFind.setStartPoint(0, pnodes2) || !com.maddox.il2.builder.PathFind.setStartPoint(1, pnodes3) || !com.maddox.il2.builder.PathFind.isPointReacheable(0, this) || !com.maddox.il2.builder.PathFind.isPointReacheable(1, this))
                {
                    pos.setAbs(_movePrevP);
                    throw new ActorException("PathPoint not Reacheable");
                }
                pnodes2.posXYZ = com.maddox.il2.builder.PathFind.buildPath(0, this);
                posXYZ = com.maddox.il2.builder.PathFind.buildPath(1, this);
                reversePathXY(posXYZ);
            }
        }
        pos.reset();
        syncPathPos();
    }

    public void destroy()
    {
        if(isDestroyed())
            return;
        com.maddox.il2.builder.Path path = (com.maddox.il2.builder.Path)getOwner();
        if(!com.maddox.il2.engine.Actor.isValid(path))
        {
            super.destroy();
            return;
        }
        int i = path.points();
        if(i > 1)
        {
            int j = path.pointIndx(this);
            if(j == i - 1)
            {
                com.maddox.il2.builder.PNodes pnodes = (com.maddox.il2.builder.PNodes)path.point(j - 1);
                pnodes.posXYZ = null;
            } else
            if(j != 0)
            {
                com.maddox.il2.builder.PNodes pnodes1 = (com.maddox.il2.builder.PNodes)path.point(j - 1);
                com.maddox.il2.builder.PNodes pnodes2 = (com.maddox.il2.builder.PNodes)path.point(j + 1);
                if(!com.maddox.il2.builder.PathFind.setStartPoint(0, pnodes1) || !com.maddox.il2.builder.PathFind.isPointReacheable(0, pnodes2))
                    throw new ActorException("PathPoint not Reacheable");
                pnodes1.posXYZ = com.maddox.il2.builder.PathFind.buildPath(0, pnodes2);
            }
        }
        super.destroy();
        path.computeTimes();
        com.maddox.il2.builder.PathChief pathchief = (com.maddox.il2.builder.PathChief)path;
        pathchief.updateUnitsPos();
    }

    private void reversePathXY(float af[])
    {
        int i = af.length / 4;
        int j = 0;
        for(int k = i - 1; j < k; k--)
        {
            float f = af[j * 4 + 0];
            float f1 = af[j * 4 + 1];
            float f2 = af[j * 4 + 2];
            float f3 = af[j * 4 + 3];
            af[j * 4 + 0] = af[k * 4 + 0];
            af[j * 4 + 1] = af[k * 4 + 1];
            af[j * 4 + 2] = af[k * 4 + 2];
            af[j * 4 + 3] = af[k * 4 + 3];
            af[k * 4 + 0] = f;
            af[k * 4 + 1] = f1;
            af[k * 4 + 2] = f2;
            af[k * 4 + 3] = f3;
            j++;
        }

    }

    public PNodes(com.maddox.il2.builder.Path path, com.maddox.il2.builder.PPoint ppoint, com.maddox.il2.engine.Mat mat, com.maddox.JGP.Point3d point3d)
    {
        super(path, ppoint, mat, point3d);
        timeoutMin = 0.0D;
        if(point3d == null)
            return;
        int i = path.moveType;
        if(i >= 0)
            com.maddox.il2.builder.PathFind.setMoverType(i);
        int j = path.points();
        if(j == 1)
        {
            if(!com.maddox.il2.builder.PathFind.setStartPoint(0, this))
            {
                super.destroy();
                throw new ActorException("PathPoint not Reacheable");
            }
        } else
        {
            int k = path.pointIndx(this);
            if(k == j - 1)
            {
                com.maddox.il2.builder.PNodes pnodes = (com.maddox.il2.builder.PNodes)path.point(k - 1);
                if(!com.maddox.il2.builder.PathFind.setStartPoint(0, pnodes) || !com.maddox.il2.builder.PathFind.isPointReacheable(0, this))
                {
                    super.destroy();
                    throw new ActorException("PathPoint not Reacheable");
                }
                pnodes.posXYZ = com.maddox.il2.builder.PathFind.buildPath(0, this);
            } else
            if(k == 0)
            {
                com.maddox.il2.builder.PNodes pnodes1 = (com.maddox.il2.builder.PNodes)path.point(k + 1);
                if(!com.maddox.il2.builder.PathFind.setStartPoint(0, this) || !com.maddox.il2.builder.PathFind.isPointReacheable(0, pnodes1))
                {
                    super.destroy();
                    throw new ActorException("PathPoint not Reacheable");
                }
                posXYZ = com.maddox.il2.builder.PathFind.buildPath(0, pnodes1);
            } else
            {
                com.maddox.il2.builder.PNodes pnodes2 = (com.maddox.il2.builder.PNodes)path.point(k - 1);
                com.maddox.il2.builder.PNodes pnodes3 = (com.maddox.il2.builder.PNodes)path.point(k + 1);
                if(!com.maddox.il2.builder.PathFind.setStartPoint(0, pnodes2) || !com.maddox.il2.builder.PathFind.setStartPoint(1, pnodes3) || !com.maddox.il2.builder.PathFind.isPointReacheable(0, this) || !com.maddox.il2.builder.PathFind.isPointReacheable(1, this))
                {
                    super.destroy();
                    throw new ActorException("PathPoint not Reacheable");
                }
                pnodes2.posXYZ = com.maddox.il2.builder.PathFind.buildPath(0, this);
                posXYZ = com.maddox.il2.builder.PathFind.buildPath(1, this);
                reversePathXY(posXYZ);
            }
        }
        if((path instanceof com.maddox.il2.builder.PathChief) && com.maddox.il2.engine.Actor.isValid(path))
        {
            com.maddox.il2.builder.PathChief pathchief = (com.maddox.il2.builder.PathChief)path;
            pathchief.updateUnitsPos();
        }
        syncPathPos();
        path.computeTimes();
    }

    public PNodes(com.maddox.il2.builder.Path path, com.maddox.il2.builder.PPoint ppoint, com.maddox.JGP.Point3d point3d)
    {
        this(path, ppoint, (com.maddox.il2.engine.Mat)null, point3d);
        if(ppoint != null)
            icon = ppoint.icon;
    }

    public PNodes(com.maddox.il2.builder.Path path, com.maddox.il2.builder.PPoint ppoint, java.lang.String s, com.maddox.JGP.Point3d point3d)
    {
        this(path, ppoint, com.maddox.il2.engine.IconDraw.get(s), point3d);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public double timeoutMin;
    public double speed;
    public float posXYZ[];
    private static com.maddox.JGP.Point3d _prevP = new Point3d();
    private static com.maddox.JGP.Point3d _curP = new Point3d();
    private static com.maddox.JGP.Point3d _movePrevP = new Point3d();

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.builder.PNodes.class, "iconName", "icons/tank.mat");
    }
}

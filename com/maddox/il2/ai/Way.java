// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Way.java

package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Landscape;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.ai:
//            WayPoint, World, Airport

public class Way
{

    public int Cur()
    {
        return Cur;
    }

    public Way()
    {
        WList = new ArrayList();
        prevdist2 = 100000000D;
        prevdistToNextWP2 = 3.4028234663852886E+038D;
        WList.clear();
        Cur = 0;
        landing = false;
        landingOnShip = false;
        landingAirport = null;
        takeoffAirport = null;
    }

    public Way(com.maddox.il2.ai.Way way)
    {
        WList = new ArrayList();
        prevdist2 = 100000000D;
        prevdistToNextWP2 = 3.4028234663852886E+038D;
        set(way);
    }

    public void set(com.maddox.il2.ai.Way way)
    {
        WList.clear();
        Cur = 0;
        for(int i = 0; i < way.WList.size(); i++)
        {
            com.maddox.il2.ai.WayPoint waypoint = new WayPoint();
            waypoint.set(way.get(i));
            WList.add(waypoint);
            if(waypoint.Action == 2 && waypoint.sTarget != null)
                landingOnShip = true;
        }

        landing = way.landing;
        landingAirport = way.landingAirport;
        if(takeoffAirport == null)
            takeoffAirport = way.takeoffAirport;
    }

    public com.maddox.il2.ai.WayPoint first()
    {
        Cur = 0;
        return curr();
    }

    public com.maddox.il2.ai.WayPoint last()
    {
        Cur = java.lang.Math.max(0, WList.size() - 1);
        return curr();
    }

    public com.maddox.il2.ai.WayPoint next()
    {
        int i = WList.size();
        Cur++;
        if(Cur >= i)
        {
            Cur = java.lang.Math.max(0, i - 1);
            com.maddox.il2.ai.WayPoint waypoint = curr();
            return waypoint;
        } else
        {
            return curr();
        }
    }

    public com.maddox.il2.ai.WayPoint look_at_point(int i)
    {
        int j = WList.size();
        if(j == 0)
            return defaultWP;
        if(i < 0)
            i = 0;
        if(i > j - 1)
            i = j - 1;
        return (com.maddox.il2.ai.WayPoint)WList.get(i);
    }

    public void setCur(int i)
    {
        if(i >= WList.size() || i < 0)
        {
            return;
        } else
        {
            Cur = i;
            return;
        }
    }

    public com.maddox.il2.ai.WayPoint prev()
    {
        Cur--;
        if(Cur < 0)
            Cur = 0;
        return curr();
    }

    public com.maddox.il2.ai.WayPoint curr()
    {
        if(WList.size() == 0)
            return defaultWP;
        else
            return (com.maddox.il2.ai.WayPoint)WList.get(Cur);
    }

    public com.maddox.il2.ai.WayPoint auto(com.maddox.JGP.Point3d point3d)
    {
        if(Cur == 0 || isReached(point3d))
            return next();
        else
            return curr();
    }

    public double getCurDist()
    {
        return java.lang.Math.sqrt(curDist);
    }

    public boolean isReached(com.maddox.JGP.Point3d point3d)
    {
        curr().getP(P);
        V.sub(point3d, P);
        if(curr().timeout == -1 && !isLast())
        {
            ((com.maddox.il2.ai.WayPoint)WList.get(Cur + 1)).getP(tmpP);
            V.sub(point3d, tmpP);
            curDist = V.x * V.x + V.y * V.y;
            if(curDist < 100000000D && curDist > prevdistToNextWP2)
            {
                curr().setTimeout(0);
                prevdistToNextWP2 = 3.4028234663852886E+038D;
                return true;
            }
            prevdistToNextWP2 = curDist;
        } else
        {
            curDist = V.x * V.x + V.y * V.y;
            if(curDist < 1000000D && curDist > prevdist2)
            {
                prevdist2 = 100000000D;
                return true;
            }
            prevdist2 = curDist;
        }
        return false;
    }

    public boolean isLanding()
    {
        return landing;
    }

    public boolean isLandingOnShip()
    {
        return landingOnShip;
    }

    public boolean isLast()
    {
        return Cur == WList.size() - 1;
    }

    public void setLanding(boolean flag)
    {
        landing = flag;
    }

    public void add(com.maddox.il2.ai.WayPoint waypoint)
    {
        WList.add(waypoint);
        if(waypoint.Action == 2 && waypoint.sTarget != null)
            landingOnShip = true;
    }

    public com.maddox.il2.ai.WayPoint get(int i)
    {
        if(i < 0 || i >= WList.size())
            return null;
        else
            return (com.maddox.il2.ai.WayPoint)WList.get(i);
    }

    public void insert(int i, com.maddox.il2.ai.WayPoint waypoint)
    {
        if(i < 0)
            i = 0;
        else
        if(i > WList.size())
        {
            add(waypoint);
            return;
        }
        WList.add(i, waypoint);
    }

    public int size()
    {
        return WList.size();
    }

    public void load(com.maddox.rts.SectFile sectfile, java.lang.String s)
        throws java.lang.Exception
    {
        int i = sectfile.sectionIndex(s);
        int j = sectfile.vars(i);
        for(int k = 0; k < j; k++)
        {
            java.lang.String s1 = sectfile.var(i, k);
            com.maddox.il2.ai.WayPoint waypoint = new WayPoint();
            if(s1.equalsIgnoreCase("TAKEOFF"))
                waypoint.Action = 1;
            else
            if(s1.equalsIgnoreCase("LANDING"))
                waypoint.Action = 2;
            else
            if(s1.equalsIgnoreCase("GATTACK"))
                waypoint.Action = 3;
            else
                waypoint.Action = 0;
            com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.value(i, k));
            P.x = numbertokenizer.next(0.0F, -1000000F, 1000000F);
            P.y = numbertokenizer.next(0.0F, -1000000F, 1000000F);
            P.z = (double)numbertokenizer.next(0.0F, -1000000F, 1000000F) + com.maddox.il2.ai.World.land().HQ(P.x, P.y);
            float f = numbertokenizer.next(0.0F, 0.0F, 2800F);
            waypoint.set(P);
            waypoint.set(f / 3.6F);
            java.lang.String s2 = numbertokenizer.next(null);
            if(s2 != null)
                if(s2.equals("&0"))
                {
                    waypoint.bRadioSilence = false;
                    s2 = null;
                } else
                if(s2.equals("&1"))
                {
                    waypoint.bRadioSilence = true;
                    s2 = null;
                } else
                {
                    numbertokenizer.next(0);
                    java.lang.String s3 = numbertokenizer.next(null);
                    if(s3 != null && s3.equals("&1"))
                        waypoint.bRadioSilence = true;
                }
            if(s2 != null && s2.startsWith("Bridge"))
                s2 = " " + s2;
            waypoint.setTarget(s2);
            add(waypoint);
        }

    }

    private java.util.ArrayList WList;
    private int Cur;
    private boolean landing;
    private boolean landingOnShip;
    public com.maddox.il2.ai.Airport landingAirport;
    public com.maddox.il2.ai.Airport takeoffAirport;
    private double prevdist2;
    private double prevdistToNextWP2;
    private double curDist;
    private static com.maddox.JGP.Vector3d V = new Vector3d();
    private static com.maddox.JGP.Point3d P = new Point3d();
    private static com.maddox.JGP.Point3d tmpP = new Point3d();
    private static com.maddox.il2.ai.WayPoint defaultWP = new WayPoint();

}

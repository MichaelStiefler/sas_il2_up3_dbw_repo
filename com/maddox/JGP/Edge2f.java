// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Edge2f.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Line2f, JGPException, Point2f, Vector2f, 
//            NSolvef

public class Edge2f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Edge2f()
    {
        LA = new Line2f();
        LN = new Line2f();
        LN2 = new Line2f();
    }

    public Edge2f(com.maddox.JGP.Point2f point2f, com.maddox.JGP.Point2f point2f1)
    {
        LA = new Line2f();
        LN = new Line2f();
        LN2 = new Line2f();
        set(point2f, point2f1);
    }

    public final void set(com.maddox.JGP.Point2f point2f, com.maddox.JGP.Point2f point2f1)
    {
        LA.set(point2f, point2f1);
        LN.N.x = point2f1.x - point2f.x;
        LN.N.y = point2f1.y - point2f.y;
        LN.N.normalize();
        LN.C = -LN.N.dot(point2f);
        LN2.N.x = -LN.N.x;
        LN2.N.y = -LN.N.y;
        LN2.C = -LN.N.dot(point2f1);
    }

    public final float deviation(com.maddox.JGP.Point2f point2f)
    {
        return LA.deviation(point2f);
    }

    public final boolean Projected(com.maddox.JGP.Point2f point2f)
    {
        return LN.deviation(point2f) >= 0.0F && LN2.deviation(point2f) >= 0.0F;
    }

    public final com.maddox.JGP.Point2f cross(com.maddox.JGP.Edge2f edge2f)
        throws com.maddox.JGP.JGPException
    {
        float af[] = {
            LA.N.x, LA.N.y, -LA.C, edge2f.LA.N.x, edge2f.LA.N.y, -edge2f.LA.C
        };
        com.maddox.JGP.Point2f point2f = com.maddox.JGP.NSolvef.Solve2f(af);
        if(Projected(point2f) && edge2f.Projected(point2f))
            return point2f;
        else
            throw new JGPException("Edges not crossed");
    }

    public final com.maddox.JGP.Point2f cross(com.maddox.JGP.Line2f line2f)
        throws com.maddox.JGP.JGPException
    {
        float af[] = {
            LA.N.x, LA.N.y, -LA.C, line2f.N.x, line2f.N.y, -line2f.C
        };
        com.maddox.JGP.Point2f point2f = com.maddox.JGP.NSolvef.Solve2f(af);
        if(Projected(point2f))
            return point2f;
        else
            throw new JGPException("Edge and Line not crossed");
    }

    public java.lang.String toString()
    {
        return "( " + LN.N.x + "," + LN.N.y + "," + LA.cross(LN) + "," + LA.cross(LN2) + " )";
        java.lang.Exception exception;
        exception;
        return exception.toString();
    }

    public com.maddox.JGP.Line2f LA;
    public com.maddox.JGP.Line2f LN;
    public com.maddox.JGP.Line2f LN2;
}

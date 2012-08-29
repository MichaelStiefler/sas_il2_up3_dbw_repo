// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorLandMesh.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            ActorMesh, LandPlate, Mesh, Loc, 
//            ActorPos

public abstract class ActorLandMesh extends com.maddox.il2.engine.ActorMesh
    implements com.maddox.il2.engine.LandPlate
{

    protected float cHQ(double d, double d1)
    {
        return mesh().heightMapMeshGetHeight(d, d1);
    }

    protected boolean cNormal(double d, double d1, float af[])
    {
        return mesh().heightMapMeshGetNormal(d, d1, af);
    }

    protected boolean cPlane(double d, double d1, double ad[])
    {
        return mesh().heightMapMeshGetPlane(d, d1, ad);
    }

    protected float cRayHit(double ad[])
    {
        return mesh().heightMapMeshGetRayHit(ad);
    }

    public boolean isStaticPos()
    {
        return true;
    }

    protected ActorLandMesh()
    {
    }

    protected ActorLandMesh(com.maddox.il2.engine.Loc loc)
    {
        super(loc);
    }

    protected ActorLandMesh(com.maddox.il2.engine.ActorPos actorpos)
    {
        super(actorpos);
    }

    public ActorLandMesh(java.lang.String s)
    {
        super(s);
    }

    public ActorLandMesh(java.lang.String s, com.maddox.il2.engine.Loc loc)
    {
        super(s, loc);
    }

    public ActorLandMesh(java.lang.String s, com.maddox.il2.engine.ActorPos actorpos)
    {
        super(s, actorpos);
    }
}

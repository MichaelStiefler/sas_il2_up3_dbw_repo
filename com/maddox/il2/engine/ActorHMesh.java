// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorHMesh.java

package com.maddox.il2.engine;

import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.engine:
//            ActorMesh, Actor, HierMesh, Loc, 
//            Mesh, ActorPos

public abstract class ActorHMesh extends com.maddox.il2.engine.ActorMesh
{

    public com.maddox.il2.engine.HierMesh hierMesh()
    {
        return hmesh;
    }

    public com.maddox.il2.engine.Mesh mesh()
    {
        return ((com.maddox.il2.engine.Mesh) (hmesh != null ? hmesh : super.mesh()));
    }

    public float collisionR()
    {
        return mesh().collisionR();
    }

    public int[] hideSubTrees(java.lang.String s)
    {
        return hmesh.hideSubTrees(s);
    }

    public void destroyChildFiltered(java.lang.Class class1)
    {
        java.lang.Object aobj[] = getOwnerAttached();
        for(int i = 0; i < aobj.length; i++)
            if(aobj[i] != null && class1.isInstance(aobj[i]))
                ((com.maddox.il2.engine.Actor)aobj[i]).destroy();

    }

    public void getChunkLoc(com.maddox.il2.engine.Loc loc)
    {
        hmesh.getChunkLocObj(loc);
    }

    public void getChunkLocAbs(com.maddox.il2.engine.Loc loc)
    {
        hmesh.getChunkLocObj(loc);
        loc.add(pos.getAbs());
    }

    public void getChunkLocTimeAbs(com.maddox.il2.engine.Loc loc)
    {
        hmesh.getChunkLocObj(loc);
        pos.getTime(com.maddox.rts.Time.current(), _L);
        loc.add(_L);
    }

    public float getChunkMass()
    {
        return hmesh.getChunkMass();
    }

    public void destroy()
    {
        if(isDestroyed())
            return;
        super.destroy();
        if(hmesh != null)
        {
            hmesh.destroy();
            hmesh = null;
        }
    }

    protected ActorHMesh()
    {
        hmesh = null;
    }

    protected ActorHMesh(com.maddox.il2.engine.Loc loc)
    {
        super(loc);
        hmesh = null;
    }

    protected ActorHMesh(com.maddox.il2.engine.ActorPos actorpos)
    {
        super(actorpos);
        hmesh = null;
    }

    public void setMesh(java.lang.String s)
    {
        boolean flag = mesh() != null && pos != null;
        if(s.endsWith(".sim"))
        {
            hmesh = null;
            super.setMesh(s);
        } else
        {
            mesh = null;
            hmesh = new HierMesh(s);
        }
        if(flag)
            pos.actorChanged();
    }

    public void setMesh(com.maddox.il2.engine.Mesh mesh1)
    {
        boolean flag = mesh() != null && pos != null;
        mesh = mesh1;
        hmesh = null;
        if(flag)
            pos.actorChanged();
    }

    protected void setMesh(com.maddox.il2.engine.HierMesh hiermesh)
    {
        boolean flag = mesh() != null && pos != null;
        hmesh = hiermesh;
        mesh = null;
        if(flag)
            pos.actorChanged();
    }

    public ActorHMesh(java.lang.String s)
    {
        hmesh = null;
        try
        {
            setMesh(s);
        }
        catch(java.lang.RuntimeException runtimeexception)
        {
            super.destroy();
            throw runtimeexception;
        }
    }

    public ActorHMesh(java.lang.String s, com.maddox.il2.engine.Loc loc)
    {
        super(loc);
        hmesh = null;
        try
        {
            setMesh(s);
        }
        catch(java.lang.RuntimeException runtimeexception)
        {
            super.destroy();
            throw runtimeexception;
        }
    }

    public ActorHMesh(java.lang.String s, com.maddox.il2.engine.ActorPos actorpos)
    {
        super(actorpos);
        hmesh = null;
        try
        {
            setMesh(s);
        }
        catch(java.lang.RuntimeException runtimeexception)
        {
            super.destroy();
            throw runtimeexception;
        }
    }

    public ActorHMesh(com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.engine.Loc loc)
    {
        super(loc);
        hmesh = null;
        try
        {
            setMesh(hiermesh);
        }
        catch(java.lang.RuntimeException runtimeexception)
        {
            super.destroy();
            throw runtimeexception;
        }
    }

    private com.maddox.il2.engine.HierMesh hmesh;
    private static com.maddox.il2.engine.Loc _L = new Loc();

}

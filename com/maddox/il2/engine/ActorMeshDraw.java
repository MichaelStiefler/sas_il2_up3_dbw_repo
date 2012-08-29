// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorMeshDraw.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;

// Referenced classes of package com.maddox.il2.engine:
//            ActorDraw, ActorMesh, MeshShared, Loc, 
//            ActorPos, Mesh, Actor, Render, 
//            LightEnv

public class ActorMeshDraw extends com.maddox.il2.engine.ActorDraw
{

    public int preRender(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.engine.ActorMesh actormesh = (com.maddox.il2.engine.ActorMesh)actor;
        com.maddox.il2.engine.Mesh mesh = actormesh.mesh();
        if(mesh == null)
            return 0;
        if(mesh instanceof com.maddox.il2.engine.MeshShared)
        {
            if(lightMap == null && sounds == null)
            {
                actormesh.pos.getRender(p);
                return mesh.preRender(p);
            } else
            {
                actormesh.pos.getRender(l);
                lightUpdate(l, true);
                soundUpdate(actor, l);
                return mesh.preRender(l.getPoint());
            }
        } else
        {
            actormesh.pos.getRender(l);
            mesh.setPos(l);
            lightUpdate(l, true);
            soundUpdate(actor, l);
            return mesh.preRender();
        }
    }

    public void render(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.engine.Mesh mesh = ((com.maddox.il2.engine.ActorMesh)actor).mesh();
        actor.pos.getRender(l);
        if(mesh instanceof com.maddox.il2.engine.MeshShared)
        {
            if(lightMap == null)
                lightUpdate(l, false);
            if(com.maddox.il2.engine.Render.currentLightEnv().prepareForRender(l.getPoint(), mesh.visibilityR()) == 0)
            {
                if(!((com.maddox.il2.engine.MeshShared)mesh).putToRenderArray(l))
                {
                    mesh.setPos(l);
                    mesh.render();
                }
            } else
            {
                mesh.setPos(l);
                mesh.render();
            }
        } else
        {
            lightUpdate(l, false);
            com.maddox.il2.engine.Render.currentLightEnv().prepareForRender(l.getPoint(), mesh.visibilityR());
            mesh.render();
        }
    }

    public void renderShadowProjective(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.engine.Mesh mesh = ((com.maddox.il2.engine.ActorMesh)actor).mesh();
        if(mesh instanceof com.maddox.il2.engine.MeshShared)
        {
            actor.pos.getRender(l);
            if(!((com.maddox.il2.engine.MeshShared)mesh).putToRenderArray(l))
            {
                mesh.setPos(l);
                mesh.renderShadowProjective();
            }
        } else
        {
            mesh.renderShadowProjective();
        }
    }

    public void renderShadowVolume(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.engine.Mesh mesh = ((com.maddox.il2.engine.ActorMesh)actor).mesh();
        if(mesh instanceof com.maddox.il2.engine.MeshShared)
        {
            actor.pos.getRender(l);
            if(!((com.maddox.il2.engine.MeshShared)mesh).putToRenderArray(l))
            {
                mesh.setPos(l);
                mesh.renderShadowVolume();
            }
        } else
        {
            mesh.renderShadowVolume();
        }
    }

    public void renderShadowVolumeHQ(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.engine.Mesh mesh = ((com.maddox.il2.engine.ActorMesh)actor).mesh();
        if(mesh instanceof com.maddox.il2.engine.MeshShared)
        {
            actor.pos.getRender(l);
            if(!((com.maddox.il2.engine.MeshShared)mesh).putToRenderArray(l))
            {
                mesh.setPos(l);
                mesh.renderShadowVolumeHQ();
            }
        } else
        {
            mesh.renderShadowVolumeHQ();
        }
    }

    public ActorMeshDraw(com.maddox.il2.engine.ActorDraw actordraw)
    {
        super(actordraw);
    }

    public ActorMeshDraw()
    {
    }

    private static com.maddox.il2.engine.Loc l = new Loc();
    private static com.maddox.JGP.Point3d p = new Point3d();

}

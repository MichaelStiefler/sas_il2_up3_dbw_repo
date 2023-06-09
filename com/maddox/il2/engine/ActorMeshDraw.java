package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;

public class ActorMeshDraw extends ActorDraw
{
  private static Loc l = new Loc();
  private static Point3d p = new Point3d();

  public int preRender(Actor paramActor)
  {
    ActorMesh localActorMesh = (ActorMesh)paramActor;
    Mesh localMesh = localActorMesh.mesh();
    if (localMesh == null) return 0;
    if ((localMesh instanceof MeshShared)) {
      if ((this.lightMap == null) && (this.sounds == null)) {
        localActorMesh.pos.getRender(p);
        return localMesh.preRender(p);
      }
      localActorMesh.pos.getRender(l);
      lightUpdate(l, true);
      soundUpdate(paramActor, l);
      return localMesh.preRender(l.getPoint());
    }

    localActorMesh.pos.getRender(l);
    localMesh.setPos(l);
    lightUpdate(l, true);
    soundUpdate(paramActor, l);
    return localMesh.preRender();
  }

  public void render(Actor paramActor)
  {
    Mesh localMesh = ((ActorMesh)paramActor).mesh();
    paramActor.pos.getRender(l);
    if ((localMesh instanceof MeshShared)) {
      if (this.lightMap == null)
        lightUpdate(l, false);
      if (Render.currentLightEnv().prepareForRender(l.getPoint(), localMesh.visibilityR()) == 0) {
        if (!((MeshShared)localMesh).putToRenderArray(l)) {
          localMesh.setPos(l);
          localMesh.render();
        }
      } else {
        localMesh.setPos(l);
        localMesh.render();
      }
    } else {
      lightUpdate(l, false);
      Render.currentLightEnv().prepareForRender(l.getPoint(), localMesh.visibilityR());
      localMesh.render();
    }
  }

  public void renderShadowProjective(Actor paramActor) {
    Mesh localMesh = ((ActorMesh)paramActor).mesh();
    if ((localMesh instanceof MeshShared)) {
      paramActor.pos.getRender(l);
      if (!((MeshShared)localMesh).putToRenderArray(l)) {
        localMesh.setPos(l);
        localMesh.renderShadowProjective();
      }
    } else {
      localMesh.renderShadowProjective();
    }
  }

  public void renderShadowVolume(Actor paramActor) {
    Mesh localMesh = ((ActorMesh)paramActor).mesh();
    if ((localMesh instanceof MeshShared)) {
      paramActor.pos.getRender(l);
      if (!((MeshShared)localMesh).putToRenderArray(l)) {
        localMesh.setPos(l);
        localMesh.renderShadowVolume();
      }
    } else {
      localMesh.renderShadowVolume();
    }
  }

  public void renderShadowVolumeHQ(Actor paramActor) {
    Mesh localMesh = ((ActorMesh)paramActor).mesh();
    if ((localMesh instanceof MeshShared)) {
      paramActor.pos.getRender(l);
      if (!((MeshShared)localMesh).putToRenderArray(l)) {
        localMesh.setPos(l);
        localMesh.renderShadowVolumeHQ();
      }
    } else {
      localMesh.renderShadowVolumeHQ();
    }
  }

  public ActorMeshDraw(ActorDraw paramActorDraw) {
    super(paramActorDraw);
  }

  public ActorMeshDraw()
  {
  }
}
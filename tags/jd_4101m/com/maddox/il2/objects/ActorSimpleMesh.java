package com.maddox.il2.objects;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Loc;
import com.maddox.rts.Message;
import com.maddox.rts.Spawn;

public class ActorSimpleMesh extends ActorMesh
{
  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }
  public ActorSimpleMesh(String paramString, Loc paramLoc) {
    super(paramString, paramLoc);
    drawing(true);
  }

  public ActorSimpleMesh(String paramString, ActorPos paramActorPos) {
    super(paramString, paramActorPos);
    drawing(true);
  }

  public ActorSimpleMesh(String paramString) {
    super(paramString);
    drawing(true);
  }
  protected void createActorHashCode() {
    makeActorRealHashCode();
  }

  static
  {
    Spawn.add(ActorSimpleMesh.class, new SPAWN());
  }

  public static class SPAWN
    implements ActorSpawn
  {
    public Actor actorSpawn(ActorSpawnArg paramActorSpawnArg)
    {
      if (paramActorSpawnArg.isNoExistHARD(paramActorSpawnArg.meshName, "mesh name not present")) return null;
      return paramActorSpawnArg.set(new ActorSimpleMesh(paramActorSpawnArg.meshName));
    }
  }
}
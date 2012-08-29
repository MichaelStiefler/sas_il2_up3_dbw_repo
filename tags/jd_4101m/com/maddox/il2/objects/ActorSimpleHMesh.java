package com.maddox.il2.objects;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Loc;
import com.maddox.rts.Message;
import com.maddox.rts.Spawn;

public class ActorSimpleHMesh extends ActorHMesh
{
  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }
  public ActorSimpleHMesh(String paramString, Loc paramLoc) {
    super(paramString, paramLoc);
    drawing(true);
  }
  public ActorSimpleHMesh(String paramString, ActorPos paramActorPos) {
    super(paramString, paramActorPos);
    drawing(true);
  }
  public ActorSimpleHMesh(String paramString) {
    super(paramString);
    drawing(true);
  }
  protected void createActorHashCode() {
    makeActorRealHashCode();
  }

  static
  {
    Spawn.add(ActorSimpleHMesh.class, new SPAWN());
  }

  public static class SPAWN
    implements ActorSpawn
  {
    public Actor actorSpawn(ActorSpawnArg paramActorSpawnArg)
    {
      if (paramActorSpawnArg.isNoExistHARD(paramActorSpawnArg.meshName, "mesh name not present")) return null;
      return paramActorSpawnArg.set(new ActorSimpleHMesh(paramActorSpawnArg.meshName));
    }
  }
}
package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.rts.Property;
import com.maddox.rts.Spawn;
import com.maddox.util.NumberTokenizer;

public class PathAirdrome extends Path
{
  public int _iType;

  public PathAirdrome(Pathes paramPathes, int paramInt)
  {
    super(paramPathes);
    this._iType = paramInt;
  }
  public static void configure() {
  }

  public static String toSpawnString(PathAirdrome paramPathAirdrome) {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("spawn ");
    localStringBuffer.append(paramPathAirdrome.getClass().getName());
    localStringBuffer.append(" RAWDATA ");
    localStringBuffer.append(paramPathAirdrome._iType); localStringBuffer.append(" ");
    int i = paramPathAirdrome.points();
    localStringBuffer.append(i);
    for (int j = 0; j < i; j++) {
      PPoint localPPoint = paramPathAirdrome.point(j);
      localStringBuffer.append(" "); localStringBuffer.append(localPPoint.pos.getAbsPoint().x);
      localStringBuffer.append(" "); localStringBuffer.append(localPPoint.pos.getAbsPoint().y);
    }
    return localStringBuffer.toString();
  }

  static
  {
    Spawn.add(PathAirdrome.class, new SPAWN());
  }

  public static class SPAWN
    implements ActorSpawn
  {
    public Actor actorSpawn(ActorSpawnArg paramActorSpawnArg)
    {
      if (paramActorSpawnArg.isNoExistHARD(paramActorSpawnArg.rawData, "data not present")) return null;
      PlMapAirdrome localPlMapAirdrome = (PlMapAirdrome)Plugin.getPlugin("MapAirdrome");
      NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramActorSpawnArg.rawData, " ");
      int i = localNumberTokenizer.next(0, 0, 2);
      int j = localNumberTokenizer.next(0);
      PathAirdrome localPathAirdrome = new PathAirdrome(Plugin.builder.pathes, i);
      Property.set(localPathAirdrome, "builderPlugin", localPlMapAirdrome);
      localPathAirdrome.drawing(localPlMapAirdrome.mView.bChecked);
      Object localObject = null;
      Point3d localPoint3d = new Point3d();
      for (int k = 0; k < j; k++) {
        localPoint3d.set(localNumberTokenizer.next(0.0F), localNumberTokenizer.next(0.0F), 0.0D);
        localPoint3d.z = (Engine.land().HQ(localPoint3d.x, localPoint3d.y) + 0.2D);
        localObject = new PAirdrome(localPathAirdrome, (PPoint)localObject, localPoint3d, i);
        Property.set(localObject, "builderPlugin", localPlMapAirdrome);
        Property.set(localObject, "builderSpawn", "");
      }
      return (Actor)localPathAirdrome;
    }
  }
}
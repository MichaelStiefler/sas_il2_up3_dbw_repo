package com.maddox.il2.ai.ground;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.rts.Finger;
import com.maddox.rts.Message;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ChiefStaticGround extends Chief
{
  public ArrayList unitsPacked;
  public long waitTime;
  public int behaviour;

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  public ChiefStaticGround() {
    this.waitTime = -1L;
    this.behaviour = 0;

    this.unitsPacked = new ArrayList();

    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosMove(this);
  }

  public ChiefStaticGround(String paramString, Point3d paramPoint3d)
  {
    this();
    if (paramPoint3d != null) {
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(paramPoint3d);
    }

    SectFile localSectFile = new SectFile(paramString);

    int i = localSectFile.sectionIndex("StUnits");
    int j = localSectFile.vars(i);
    if (j <= 0) j /= 0;
    for (int k = 0; k < j; k++) {
      StringTokenizer localStringTokenizer = new StringTokenizer(localSectFile.line(i, k));
      String str = localStringTokenizer.nextToken();
      float f1 = Float.valueOf(localStringTokenizer.nextToken()).floatValue();
      float f2 = Float.valueOf(localStringTokenizer.nextToken()).floatValue();
      float f3 = Float.valueOf(localStringTokenizer.nextToken()).floatValue();

      Object localObject = Spawn.get(str);
      if (localObject == null) {
        System.out.println("No such type of object: " + str);
        System.exit(0);
      }

      int m = Finger.Int(str);
      int n = 0;
      this.unitsPacked.add(new StaticUnitInPackedForm(k, m, n, f1, f2, f3));
    }

    unpackUnits();
  }

  public void packUnits()
  {
    if (this.unitsPacked.size() > 0) return;
    Object[] arrayOfObject = getOwnerAttached();
    if (arrayOfObject == null) return;
    int i = arrayOfObject.length;
    for (int j = 0; j < i; j++) {
      this.unitsPacked.add(((StaticUnitInterface)arrayOfObject[j]).Pack());
      ((Actor)arrayOfObject[j]).destroy();
    }
  }

  public void unpackUnits()
  {
    int i = this.unitsPacked.size();
    if (i <= 0) return;

    for (int j = 0; j < i; j++) {
      StaticUnitInPackedForm localStaticUnitInPackedForm = (StaticUnitInPackedForm)this.unitsPacked.get(j);
      Object localObject = Spawn.get(localStaticUnitInPackedForm.CodeType());

      ((StaticUnitSpawn)localObject).unitSpawn(localStaticUnitInPackedForm.CodeName(), localStaticUnitInPackedForm.State(), this, localStaticUnitInPackedForm.X(), localStaticUnitInPackedForm.Y(), localStaticUnitInPackedForm.Yaw());
    }

    this.unitsPacked.clear();
  }

  static
  {
    Spawn.add(ChiefStaticGround.class, new SPAWN());
  }

  public static class SPAWN
    implements ActorSpawn
  {
    public Actor actorSpawn(ActorSpawnArg paramActorSpawnArg)
    {
      return paramActorSpawnArg.set(new ChiefStaticGround(paramActorSpawnArg.paramFileName, paramActorSpawnArg.point));
    }
  }
}
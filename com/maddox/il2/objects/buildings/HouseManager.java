package com.maddox.il2.objects.buildings;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.util.NumberTokenizer;
import java.io.IOException;
import java.io.PrintStream;

public class HouseManager extends Actor
{
  private int houses = 0;
  private House[] house = null;
  private long[] houseInitState = null;

  public void destroy() {
    if (isDestroyed()) return;
    for (int i = 0; i < this.houses; i++) {
      if (Actor.isValid(this.house[i]))
        this.house[i].destroy();
      this.house[i] = null;
    }
    this.house = null;
    this.houseInitState = null;
    super.destroy();
  }

  public void fullUpdateChannel(NetChannel paramNetChannel) {
    ((HouseNet)this.jdField_net_of_type_ComMaddoxIl2EngineActorNet).fullUpdateChannel(paramNetChannel);
  }
  public void onHouseDie(House paramHouse, Actor paramActor) {
    for (int i = 0; i < this.houses; i++)
      if (this.house[i] == paramHouse) {
        ((HouseNet)this.jdField_net_of_type_ComMaddoxIl2EngineActorNet).postDie(i, paramActor, null);
        return;
      }
  }

  private void createNetObj(NetChannel paramNetChannel, int paramInt)
  {
    if (paramNetChannel == null) this.jdField_net_of_type_ComMaddoxIl2EngineActorNet = new HouseNet(this); else
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet = new HouseNet(this, paramNetChannel, paramInt);
  }

  public HouseManager(SectFile paramSectFile, String paramString, NetChannel paramNetChannel, int paramInt) {
    int i = paramSectFile.sectionIndex(paramString);
    if (i < 0)
      return;
    int j = paramSectFile.vars(i);
    this.houses = j;
    this.house = new House[j];
    this.houseInitState = new long[j / 64 + 1];
    Point3d localPoint3d = new Point3d();
    Orient localOrient = new Orient();
    ActorSpawnArg localActorSpawnArg = new ActorSpawnArg();
    for (int k = 0; k < j; k++) {
      NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, k));
      localNumberTokenizer.next("");
      String str = "com.maddox.il2.objects.buildings." + localNumberTokenizer.next("");
      int m = localNumberTokenizer.next(1) == 1 ? 1 : 0;
      double d1 = localNumberTokenizer.next(0.0D);
      double d2 = localNumberTokenizer.next(0.0D);
      float f = localNumberTokenizer.next(0.0F);
      ActorSpawn localActorSpawn = (ActorSpawn)Spawn.get_WithSoftClass(str, false);
      if (localActorSpawn == null)
        continue;
      localPoint3d.set(d1, d2, 0.0D);
      localActorSpawnArg.point = localPoint3d;
      localOrient.set(f, 0.0F, 0.0F);
      localActorSpawnArg.orient = localOrient;
      try {
        House localHouse = (House)localActorSpawn.actorSpawn(localActorSpawnArg);
        if (m == 0) {
          localHouse.setDiedFlag(true);
        } else {
          int n = k / 64;
          int i1 = k % 64;
          this.houseInitState[n] |= 1L << i1;
        }
        this.house[k] = localHouse;
        localHouse.setOwner(this);
      } catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
      }
    }
    createNetObj(paramNetChannel, paramInt);

    if (Actor.isValid(World.cur().houseManager))
      World.cur().houseManager.destroy();
    World.cur().houseManager = this;
  }

  class HouseNet extends ActorNet
  {
    public void fullUpdateChannel(NetChannel paramNetChannel)
    {
      int i = HouseManager.this.houses / 64 + 1;
      try {
        for (int j = 0; j < i; j++) {
          long l = 0L;
          for (int k = 0; k < 64; k++) {
            int m = j * 64 + k;
            if (m >= HouseManager.this.houses) break;
            if (Actor.isAlive(HouseManager.this.house[m]))
              l |= 1L << k;
          }
          if (l == HouseManager.this.houseInitState[j])
            continue;
          NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
          localNetMsgGuaranted.writeShort(j);
          localNetMsgGuaranted.writeLong(l);
          postTo(paramNetChannel, localNetMsgGuaranted);
        }
      } catch (Exception localException) {
        NetObj.printDebug(localException);
      }
    }

    public boolean netInput(NetMsgInput paramNetMsgInput)
      throws IOException
    {
      int i;
      if (paramNetMsgInput.available() > 4 + NetMsgInput.netObjReferenceLen()) {
        i = paramNetMsgInput.readUnsignedShort();
        long l = paramNetMsgInput.readLong();
        for (int j = 0; j < 64; j++) {
          int k = i * 64 + j;
          if (k >= HouseManager.this.houses) break;
          House localHouse2 = HouseManager.this.house[k];
          if (!Actor.isValid(localHouse2))
            continue;
          int m = (l & 1L << j) != 0L ? 1 : 0;
          localHouse2.setDiedFlag(m == 0);
        }
      }
      else {
        i = paramNetMsgInput.readInt();
        if (i >= HouseManager.this.houses)
          return true;
        House localHouse1 = HouseManager.this.house[i];
        if (!Actor.isAlive(localHouse1))
          return true;
        NetObj localNetObj = paramNetMsgInput.readNetObj();
        if (localNetObj == null)
          return true;
        Actor localActor = (Actor)localNetObj.superObj();

        localHouse1.doDieShow();
        World.onActorDied(localHouse1, localActor);

        postDie(i, localActor, paramNetMsgInput.channel());
      }
      return true;
    }

    private void postDie(int paramInt, Actor paramActor, NetChannel paramNetChannel) {
      int i = countMirrors();
      if (isMirror()) i++;
      if (paramNetChannel != null) i--;
      if (i <= 0) return; try
      {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeInt(paramInt);
        localNetMsgGuaranted.writeNetObj(paramActor.net);
        postExclude(paramNetChannel, localNetMsgGuaranted); } catch (Exception localException) {
        NetObj.printDebug(localException);
      }
    }

    public HouseNet(Actor arg2) {
      super();
    }
    public HouseNet(Actor paramNetChannel, NetChannel paramInt, int arg4) {
      super(paramInt, i);
    }
  }
}
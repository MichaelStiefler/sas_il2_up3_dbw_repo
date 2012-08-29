package com.maddox.il2.objects;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.AirportStatic;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Airdrome;
import com.maddox.il2.ai.air.Point_Runaway;
import com.maddox.il2.ai.air.Point_Stay;
import com.maddox.il2.ai.air.Point_Taxi;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorLandMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.DrawEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgDreamGlobalListener;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.objects.bridges.Bridge;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.bridges.LongBridge;
import com.maddox.il2.objects.buildings.House;
import com.maddox.il2.objects.buildings.Plate;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetHost;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.util.AsciiBitSet;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class Statics extends Actor
  implements MsgDreamGlobalListener
{
  private static SectFile ships = null;
  private static SectFile technics = null;
  private static SectFile buildings = null;

  public ArrayList bridges = new ArrayList();
  private ActorSpawn[] spawns;
  private boolean[] spawnIsPlate;
  private float[] uniformMaxDist;
  private ArrayList[] cacheActors;
  private HashMapInt allBlocks;
  private HashMapInt allBridge0;
  private HashMapInt allStates0;
  private int[] _updateX = new int[1];
  private int[] _updateY = new int[1];

  private Queue wQueue = new Queue();
  private Queue sQueue = new Queue();

  private boolean bCheckPlate = true;

  Loc _loc = new Loc();
  ActorSpawnArg _spawnArg = new ActorSpawnArg();

  public static SectFile getShipsFile()
  {
    if (ships == null) {
      ships = new SectFile("com/maddox/il2/objects/ships.ini");
      ships.createIndexes();
    }
    return ships;
  }

  public static SectFile getTechnicsFile() {
    if (technics == null) {
      technics = new SectFile("com/maddox/il2/objects/technics.ini");
      technics.createIndexes();
    }
    return technics;
  }

  public static SectFile getBuildingsFile() {
    if (buildings == null) {
      buildings = new SectFile("com/maddox/il2/objects/static.ini");
      buildings.createIndexes();
    }
    return buildings;
  }

  public static int[] readBridgesEndPoints(String paramString) {
    int[] arrayOfInt = null;
    try {
      DataInputStream localDataInputStream = new DataInputStream(new SFSInputStream(paramString));

      int i = localDataInputStream.readInt();

      if (i == -65535) {
        i = localDataInputStream.readInt();
      }

      arrayOfInt = new int[i * 4];
      for (int j = 0; j < i; j++) {
        int k = localDataInputStream.readInt();
        int m = localDataInputStream.readInt();
        int n = localDataInputStream.readInt();
        int i1 = localDataInputStream.readInt();
        int i2 = localDataInputStream.readInt();
        float f = localDataInputStream.readFloat();

        arrayOfInt[(j * 4 + 0)] = k;
        arrayOfInt[(j * 4 + 1)] = m;
        arrayOfInt[(j * 4 + 2)] = n;
        arrayOfInt[(j * 4 + 3)] = i1;
      }
      localDataInputStream.close();
    } catch (Exception localException) {
      arrayOfInt = null;
      String str = "Bridges data in '" + paramString + "' DAMAGED: " + localException.getMessage();
      System.out.println(str);
    }
    return arrayOfInt;
  }

  public static void load(String paramString, List paramList)
  {
    World.cur().statics._load(paramString, paramList);
  }

  private void _load(String paramString, List paramList) {
    try {
      DataInputStream localDataInputStream = new DataInputStream(new SFSInputStream(paramString));

      int i = localDataInputStream.readInt();
      if (i != -65535) {
        throw new Exception("Not supported sersion");
      }

      System.out.println("Load bridges");
      int j = localDataInputStream.readInt();
      int i2;
      float f3;
      Object localObject;
      for (int k = 0; k < j; k++) {
        m = localDataInputStream.readInt();
        int n = localDataInputStream.readInt();
        i2 = localDataInputStream.readInt();
        int i4 = localDataInputStream.readInt();
        int i6 = localDataInputStream.readInt();
        f3 = localDataInputStream.readFloat();
        localObject = new Bridge(k, i6, m, n, i2, i4, f3);
        if (paramList != null) {
          paramList.add(localObject);
        }
      }
      System.out.println("Load static objects");

      ArrayList localArrayList = new ArrayList();

      j = localDataInputStream.readInt();
      this.spawns = null;
      if (j > 0) {
        m = -1;
        ActorSpawnArg localActorSpawnArg = new ActorSpawnArg();
        this.spawns = new ActorSpawn[j];
        for (i2 = 0; i2 < j; i2++) {
          String str3 = localDataInputStream.readUTF();
          if ("com.maddox.il2.objects.air.Runaway".equals(str3))
            m = i2;
          this.spawns[i2] = ((ActorSpawn)Spawn.get_WithSoftClass(str3));
        }
        j = localDataInputStream.readInt();
        while (j-- > 0) {
          i2 = localDataInputStream.readInt();
          float f1 = localDataInputStream.readFloat();
          float f2 = localDataInputStream.readFloat();
          f3 = localDataInputStream.readFloat();
          this._loc.set(f1, f2, 0.0D, f3, 0.0F, 0.0F);
          if (m == i2) {
            localObject = new Loc(this._loc);
            localArrayList.add(localObject);
          }
          if ((i2 < this.spawns.length) && (this.spawns[i2] != null)) {
            localActorSpawnArg.clear();
            localActorSpawnArg.point = this._loc.getPoint();
            localActorSpawnArg.orient = this._loc.getOrient();
            try {
              localObject = this.spawns[i2].actorSpawn(localActorSpawnArg);
              if ((localObject instanceof ActorLandMesh)) {
                ActorLandMesh localActorLandMesh = (ActorLandMesh)localObject;
                localActorLandMesh.mesh().setPos(localActorLandMesh.pos.getAbs());
                Landscape.meshAdd(localActorLandMesh);
              }
            } catch (Exception localException3) {
              System.out.println(localException3.getMessage());
              localException3.printStackTrace();
            }
          }
        }

      }

      j = localDataInputStream.readInt();
      this.spawns = null;
      int m = -1;
      int i3;
      int i5;
      int i8;
      if (j > 0) {
        this.spawns = new ActorSpawn[j];
        this.spawnIsPlate = new boolean[j];
        this.uniformMaxDist = new float[j];
        this.cacheActors = new ArrayList[j];
        this._loc.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        for (int i1 = 0; i1 < j; i1++) {
          String str2 = localDataInputStream.readUTF();
          if (str2.indexOf("TreeLine") >= 0)
            m = i1;
          this.spawns[i1] = ((ActorSpawn)Spawn.get_WithSoftClass(str2));
          this.cacheActors[i1] = new ArrayList();
          try
          {
            this._spawnArg.point = this._loc.getPoint();
            this._spawnArg.orient = this._loc.getOrient();
            Actor localActor = this.spawns[i1].actorSpawn(this._spawnArg);
            cachePut(i1, localActor);
            this.spawnIsPlate[i1] = (localActor instanceof Plate);
            if ((localActor instanceof ActorMesh)) {
              this.uniformMaxDist[i1] = ((ActorMesh)localActor).mesh().getUniformMaxDist();
              localActor.draw.uniformMaxDist = this.uniformMaxDist[i1];
            } else {
              this.uniformMaxDist[i1] = 0.0F;
            }
          } catch (Exception localException2) {
            System.out.println(localException2.getMessage());
            localException2.printStackTrace();
          }
        }

        i1 = localDataInputStream.readInt();
        this.allBlocks = new HashMapInt((int)(i1 / 0.75F));
        int i9;
        int i12;
        while (i1-- > 0) {
          i3 = localDataInputStream.readInt();
          i5 = i3 & 0xFFFF;
          int i7 = i3 >> 16 & 0xFFFF;
          i9 = localDataInputStream.readInt();
          Block localBlock1 = new Block();
          localBlock1.code = new int[i9 * 2];
          float f4 = 0.0F;
          for (i12 = 0; i12 < i9; i12++) {
            int i13 = localDataInputStream.readInt();
            if ((i13 & 0x7FFF) >= this.spawns.length)
              i13 = 0;
            localBlock1.code[(i12 * 2 + 0)] = i13;
            localBlock1.code[(i12 * 2 + 1)] = localDataInputStream.readInt();
            if (this.spawnIsPlate[(i13 & 0x7FFF)] != 0)
              localBlock1.bExistPlate = true;
            float f5 = this.uniformMaxDist[(i13 & 0x7FFF)];
            if (f4 < f5)
              f4 = f5;
          }
          this.allBlocks.put(key(i7, i5), localBlock1);
          Engine.drawEnv().setUniformMaxDist(i5, i7, f4);
          if (localBlock1.bExistPlate) {
            this.bCheckPlate = false;
            this._updateX[0] = i5;
            this._updateY[0] = i7;
            _msgDreamGlobal(true, 1, 0, this._updateX, this._updateY);
            this.bCheckPlate = true;
          }
        }
        if (Config.isUSE_RENDER())
        {
          i3 = Landscape.getSizeXpix();
          i5 = Landscape.getSizeYpix();
          int[] arrayOfInt = new int[i3 + 31 >> 5];
          int i10;
          for (i9 = 0; i9 < i5; i9++) {
            for (i10 = 0; i10 < arrayOfInt.length; i10++) arrayOfInt[i10] = 0;
            i10 = 0;
            for (int i11 = 0; i11 < i3; i10++) {
              if (this.allBlocks.containsKey(key(i9, i11)))
                arrayOfInt[(i10 >> 5)] |= 1 << (i10 & 0x1F);
              i11++;
            }

            Landscape.MarkStaticActorsCells(0, i9, i3, 1, 200, arrayOfInt);
          }

          i3 = Landscape.getSizeXpix();
          i5 = Landscape.getSizeYpix();
          i8 = 0;
          for (i9 = 0; i9 < i5; i9++) {
            for (i10 = 0; i10 < i3; i10++) {
              if (this.allBlocks.containsKey(key(i9, i10))) {
                Block localBlock2 = (Block)this.allBlocks.get(key(i9, i10));
                Landscape.MarkActorCellWithTrees(m, i10, i9, localBlock2.code, localBlock2.code.length);
                for (i12 = 0; i12 < localBlock2.code.length; i12 += 2) {
                  if ((localBlock2.code[i12] & 0x7FFF) == m) {
                    i8++;
                  }
                }
              }
            }
          }
        }
      }
      Airdrome localAirdrome = new Airdrome();
      if (localDataInputStream.available() > 0)
      {
        j = localDataInputStream.readInt();
        localAirdrome.runw = new Point_Runaway[j][];
        for (i3 = 0; i3 < j; i3++) {
          i5 = localDataInputStream.readInt();
          localAirdrome.runw[i3] = new Point_Runaway[i5];
          for (i8 = 0; i8 < i5; i8++) {
            localAirdrome.runw[i3][i8] = new Point_Runaway(localDataInputStream.readFloat(), localDataInputStream.readFloat());
          }
        }
        j = localDataInputStream.readInt();
        localAirdrome.taxi = new Point_Taxi[j][];
        for (i3 = 0; i3 < j; i3++) {
          i5 = localDataInputStream.readInt();
          localAirdrome.taxi[i3] = new Point_Taxi[i5];
          for (i8 = 0; i8 < i5; i8++) {
            localAirdrome.taxi[i3][i8] = new Point_Taxi(localDataInputStream.readFloat(), localDataInputStream.readFloat());
          }
        }
        j = localDataInputStream.readInt();
        localAirdrome.stay = new Point_Stay[j][];
        for (i3 = 0; i3 < j; i3++) {
          i5 = localDataInputStream.readInt();
          localAirdrome.stay[i3] = new Point_Stay[i5];
          for (i8 = 0; i8 < i5; i8++)
            localAirdrome.stay[i3][i8] = new Point_Stay(localDataInputStream.readFloat(), localDataInputStream.readFloat());
        }
        localAirdrome.stayHold = new boolean[localAirdrome.stay.length];
      }
      World.cur().airdrome = localAirdrome;
      AirportStatic.make(localArrayList, localAirdrome.runw, localAirdrome.taxi, localAirdrome.stay);

      localDataInputStream.close();
    } catch (Exception localException1) {
      String str1 = "Actors load from '" + paramString + "' FAILED: " + localException1.getMessage();
      System.out.println(str1);
      localException1.printStackTrace();
    }
  }

  public void restoreAllBridges() {
    int i = 0;
    while (true) {
      LongBridge localLongBridge = LongBridge.getByIdx(i);
      if (localLongBridge == null) break;
      if (!localLongBridge.isAlive())
        localLongBridge.BeLive();
      i++;
    }
  }

  public void saveStateBridges(SectFile paramSectFile, int paramInt) {
    int i = 0;
    while (true) {
      LongBridge localLongBridge = LongBridge.getByIdx(i);
      if (localLongBridge == null) break;
      if (!localLongBridge.isAlive()) {
        int j = localLongBridge.NumStateBits();
        BitSet localBitSet = localLongBridge.GetStateOfSegments();
        paramSectFile.lineAdd(paramInt, AsciiBitSet.save(i), AsciiBitSet.save(localBitSet, j));
      }
      i++;
    }
  }

  public void loadStateBridges(SectFile paramSectFile, boolean paramBoolean) {
    int i = paramSectFile.sectionIndex(paramBoolean ? "AddBridge" : "Bridge");
    if (i < 0) return;
    loadStateBridges(paramSectFile, i, paramBoolean);
  }
  public void loadStateBridges(SectFile paramSectFile, int paramInt, boolean paramBoolean) {
    int i = 0;
    if ((!paramBoolean) && (Mission.isDogfight())) {
      i = 1;
      this.allBridge0 = new HashMapInt();
    }
    int j = paramSectFile.vars(paramInt);
    BitSet localBitSet = new BitSet();
    for (int k = 0; k < j; k++) {
      String str1 = paramSectFile.var(paramInt, k);
      String str2 = paramSectFile.value(paramInt, k);
      int m = AsciiBitSet.load(str1);
      LongBridge localLongBridge = LongBridge.getByIdx(m);
      if (localLongBridge != null) {
        AsciiBitSet.load(str2, localBitSet, localLongBridge.NumStateBits());
        if (paramBoolean)
          localBitSet.or(localLongBridge.GetStateOfSegments());
        localLongBridge.SetStateOfSegments(localBitSet);
        if (i != 0)
          this.allBridge0.put(m, localBitSet.clone()); 
      }
    }
  }

  public void restoreAllHouses() {
    HashMapIntEntry localHashMapIntEntry = this.allBlocks.nextEntry(null);
    while (localHashMapIntEntry != null) {
      Block localBlock = (Block)localHashMapIntEntry.getValue();
      if (localBlock.isDestructed())
        localBlock.restoreAll();
      localHashMapIntEntry = this.allBlocks.nextEntry(localHashMapIntEntry);
    }
  }

  public void saveStateHouses(SectFile paramSectFile, int paramInt) {
    HashMapIntEntry localHashMapIntEntry = this.allBlocks.nextEntry(null);
    while (localHashMapIntEntry != null) {
      Block localBlock = (Block)localHashMapIntEntry.getValue();
      if (localBlock.isDestructed()) {
        int i = localHashMapIntEntry.getKey();
        paramSectFile.lineAdd(paramInt, AsciiBitSet.save(i), AsciiBitSet.save(localBlock.getDestruction(null), localBlock.amountObjects()));
      }
      localHashMapIntEntry = this.allBlocks.nextEntry(localHashMapIntEntry);
    }
  }

  public void loadStateHouses(SectFile paramSectFile, boolean paramBoolean) {
    int i = paramSectFile.sectionIndex(paramBoolean ? "AddHouse" : "House");
    if (i < 0) return;
    loadStateHouses(paramSectFile, i, paramBoolean);
  }
  public void loadStateHouses(SectFile paramSectFile, int paramInt, boolean paramBoolean) {
    int i = 0;
    if ((!paramBoolean) && (Mission.isDogfight())) {
      i = 1;
      this.allStates0 = new HashMapInt();
    }
    int j = paramSectFile.vars(paramInt);
    byte[] arrayOfByte1 = new byte[32];
    for (int k = 0; k < j; k++) {
      String str1 = paramSectFile.var(paramInt, k);
      String str2 = paramSectFile.value(paramInt, k);
      int m = AsciiBitSet.load(str1);
      Block localBlock = (Block)this.allBlocks.get(m);
      if (localBlock != null) {
        arrayOfByte1 = AsciiBitSet.load(str2, arrayOfByte1, localBlock.amountObjects());
        if (paramBoolean) {
          localBlock.addDestruction(arrayOfByte1);
        } else {
          localBlock.setDestruction(arrayOfByte1);
          if (i != 0) {
            int n = (localBlock.amountObjects() + 7) / 8;
            byte[] arrayOfByte2 = new byte[n];
            System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, n);
            this.allStates0.put(m, arrayOfByte2);
          }
        }
      }
    }
  }

  public void restoreAllHouses(float paramFloat1, float paramFloat2, float paramFloat3) {
    int i = (int)((paramFloat1 - paramFloat3) / 200.0F) - 1;
    int j = (int)((paramFloat1 + paramFloat3) / 200.0F) + 2;
    int k = (int)((paramFloat2 - paramFloat3) / 200.0F) - 1;
    int m = (int)((paramFloat2 + paramFloat3) / 200.0F) + 2;
    HashMapIntEntry localHashMapIntEntry = this.allBlocks.nextEntry(null);
    float f = paramFloat3 * paramFloat3;
    while (localHashMapIntEntry != null) {
      Block localBlock = (Block)localHashMapIntEntry.getValue();
      int n = localHashMapIntEntry.getKey();
      int i1 = key2x(n);
      int i2 = key2y(n);
      if ((i1 >= i) && (i1 <= j) && (i2 >= k) && (i2 <= m))
        localBlock.restoreAll(i1 * 200.0F, i2 * 200.0F, paramFloat1, paramFloat2, f);
      localHashMapIntEntry = this.allBlocks.nextEntry(localHashMapIntEntry);
    }
  }

  public void netBridgeSync(NetChannel paramNetChannel)
  {
    int i = 0;
    while (true) {
      LongBridge localLongBridge = LongBridge.getByIdx(i);
      if (localLongBridge == null) break;
      if (!localLongBridge.isAlive()) {
        int j = localLongBridge.NumStateBits();
        BitSet localBitSet1 = localLongBridge.GetStateOfSegments();
        BitSet localBitSet2 = null;
        if (this.allBridge0 != null)
          localBitSet2 = (BitSet)this.allBridge0.get(i);
        if ((localBitSet2 == null) || (!localBitSet2.equals(localBitSet1)))
          try
          {
            NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
            localNetMsgGuaranted.writeByte(28);
            localNetMsgGuaranted.writeShort(i);
            int k = (j + 7) / 8;
            byte[] arrayOfByte = new byte[k];
            for (int m = 0; m < j; m++) {
              if (localBitSet1.get(m)) {
                int n = m / 8;
                int i1 = m % 8;
                int tmp150_148 = n;
                byte[] tmp150_146 = arrayOfByte; tmp150_146[tmp150_148] = (byte)(tmp150_146[tmp150_148] | 1 << i1);
              }
            }
            localNetMsgGuaranted.write(arrayOfByte);
            NetEnv.host().postTo(paramNetChannel, localNetMsgGuaranted);
          } catch (Exception localException) {
          }
      }
      i++;
    }
  }

  public void netMsgBridgeSync(NetMsgInput paramNetMsgInput) throws IOException {
    int i = paramNetMsgInput.readUnsignedShort();
    LongBridge localLongBridge = LongBridge.getByIdx(i);
    if (localLongBridge == null) return;
    byte[] arrayOfByte = new byte[paramNetMsgInput.available()];
    paramNetMsgInput.read(arrayOfByte);
    int j = localLongBridge.NumStateBits();
    BitSet localBitSet = localLongBridge.GetStateOfSegments();
    for (int k = 0; k < j; k++) {
      int m = k / 8;
      int n = k % 8;
      if ((arrayOfByte[m] & 1 << n) != 0) localBitSet.set(k); else
        localBitSet.clear(k);
    }
    localLongBridge.SetStateOfSegments(localBitSet);
  }

  public boolean onBridgeDied(int paramInt1, int paramInt2, int paramInt3, Actor paramActor)
  {
    ActorNet localActorNet;
    if (Mission.isServer()) {
      try {
        NetMsgGuaranted localNetMsgGuaranted1 = new NetMsgGuaranted();
        localNetMsgGuaranted1.writeByte(27);
        localNetMsgGuaranted1.writeShort(paramInt1);
        localNetMsgGuaranted1.writeShort(paramInt2);
        localNetMsgGuaranted1.writeByte(paramInt3);
        localActorNet = null;
        if (Actor.isValid(paramActor))
          localActorNet = paramActor.net;
        localNetMsgGuaranted1.writeNetObj(localActorNet);
        NetEnv.host().post(localNetMsgGuaranted1); } catch (Exception localException1) {
      }
      return true;
    }
    try {
      NetMsgGuaranted localNetMsgGuaranted2 = new NetMsgGuaranted();
      localNetMsgGuaranted2.writeByte(26);
      localNetMsgGuaranted2.writeShort(paramInt1);
      localNetMsgGuaranted2.writeShort(paramInt2);
      localNetMsgGuaranted2.writeByte(paramInt3);
      localActorNet = null;
      if (Actor.isValid(paramActor))
        localActorNet = paramActor.net;
      localNetMsgGuaranted2.writeNetObj(localActorNet);
      NetEnv.host().postTo(Main.cur().netServerParams.masterChannel(), localNetMsgGuaranted2); } catch (Exception localException2) {
    }
    return false;
  }

  public void netMsgBridgeRDie(NetMsgInput paramNetMsgInput) throws IOException
  {
    int i = paramNetMsgInput.readUnsignedShort();
    int j = paramNetMsgInput.readUnsignedShort();
    int k = paramNetMsgInput.readByte();
    Actor localActor = null;
    NetObj localNetObj = paramNetMsgInput.readNetObj();
    if (localNetObj != null)
      localActor = (Actor)localNetObj.superObj();
    if (Mission.isServer()) {
      BridgeSegment localBridgeSegment = BridgeSegment.getByIdx(i, j);
      localBridgeSegment.netForcePartDestroing(k, localActor);
    } else {
      onBridgeDied(i, j, k, localActor);
    }
  }

  public void netMsgBridgeDie(NetObj paramNetObj, NetMsgInput paramNetMsgInput) throws IOException {
    int i = paramNetMsgInput.readUnsignedShort();
    int j = paramNetMsgInput.readUnsignedShort();
    int k = paramNetMsgInput.readByte();
    Actor localActor = null;
    NetObj localNetObj = paramNetMsgInput.readNetObj();
    if (localNetObj != null)
      localActor = (Actor)localNetObj.superObj();
    try {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(27);
      localNetMsgGuaranted.writeShort(i);
      localNetMsgGuaranted.writeShort(j);
      localNetMsgGuaranted.writeByte(k);
      ActorNet localActorNet = null;
      if (Actor.isValid(localActor))
        localActorNet = localActor.net;
      localNetMsgGuaranted.writeNetObj(localActorNet);
      paramNetObj.post(localNetMsgGuaranted); } catch (Exception localException) {
    }
    BridgeSegment localBridgeSegment = BridgeSegment.getByIdx(i, j);
    localBridgeSegment.netForcePartDestroing(k, localActor);
  }

  public void netHouseSync(NetChannel paramNetChannel)
  {
    HashMapIntEntry localHashMapIntEntry = this.allBlocks.nextEntry(null);
    while (localHashMapIntEntry != null) {
      Block localBlock = (Block)localHashMapIntEntry.getValue();
      int i = localHashMapIntEntry.getKey();
      byte[] arrayOfByte = null;
      if (this.allStates0 != null)
        arrayOfByte = (byte[])(byte[])this.allStates0.get(i);
      if (arrayOfByte == null) {
        if (localBlock.isDestructed())
          putMsgHouseSync(paramNetChannel, i, localBlock);
      }
      else if (!localBlock.isEquals(arrayOfByte)) {
        putMsgHouseSync(paramNetChannel, i, localBlock);
      }
      localHashMapIntEntry = this.allBlocks.nextEntry(localHashMapIntEntry);
    }
  }

  private void putMsgHouseSync(NetChannel paramNetChannel, int paramInt, Block paramBlock) {
    try {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(25);
      localNetMsgGuaranted.writeInt(paramInt);
      localNetMsgGuaranted.write(paramBlock.getDestruction(null));
      NetEnv.host().postTo(paramNetChannel, localNetMsgGuaranted); } catch (Exception localException) {
    }
  }

  public void netMsgHouseSync(NetMsgInput paramNetMsgInput) throws IOException {
    int i = paramNetMsgInput.readInt();
    byte[] arrayOfByte = new byte[paramNetMsgInput.available()];
    paramNetMsgInput.read(arrayOfByte);
    Block localBlock = (Block)this.allBlocks.get(i);
    if (localBlock == null)
      return;
    localBlock.setDestruction(arrayOfByte);
  }

  public void netMsgHouseDie(NetObj paramNetObj, NetMsgInput paramNetMsgInput) throws IOException {
    Actor localActor = null;
    NetObj localNetObj = paramNetMsgInput.readNetObj();
    if (localNetObj != null)
      localActor = (Actor)localNetObj.superObj();
    int i = paramNetMsgInput.readInt();
    int j = paramNetMsgInput.readUnsignedShort();
    Block localBlock = (Block)this.allBlocks.get(i);
    if (localBlock == null)
      return;
    if (j >= localBlock.code.length / 2)
      return;
    if ((localBlock.actor != null) && (localBlock.actor[0] != null)) {
      if ((Actor.isAlive(localBlock.actor[j])) && 
        ((localBlock.actor[j] instanceof House))) {
        ((House)localBlock.actor[j]).doDieShow();
        World.onActorDied(localBlock.actor[j], localActor);
        replicateHouseDie(paramNetObj, paramNetMsgInput.channel(), localNetObj, i, j);
      }
    }
    else if ((localBlock.code[(j * 2)] & 0x8000) == 0) {
      localBlock.code[(j * 2)] |= 32768;
      replicateHouseDie(paramNetObj, paramNetMsgInput.channel(), localNetObj, i, j);
    }
  }

  public void onHouseDied(House paramHouse, Actor paramActor)
  {
    Point3d localPoint3d = paramHouse.pos.getAbsPoint();
    int i = (int)(localPoint3d.y / 200.0D);
    int j = (int)(localPoint3d.x / 200.0D);
    int k = key(i, j);
    Block localBlock = (Block)this.allBlocks.get(k);
    if (localBlock == null)
      return;
    if (localBlock.actor == null)
      return;
    int m = 0;
    int n = localBlock.actor.length;
    while ((m < n) && 
      (localBlock.actor[m] != paramHouse)) {
      m++;
    }

    if (m >= n)
      return;
    ActorNet localActorNet = null;
    if (Actor.isValid(paramActor))
      localActorNet = paramActor.net;
    try {
      replicateHouseDie(NetEnv.host(), null, localActorNet, k, m);
    } catch (Exception localException) {
    }
  }

  private void replicateHouseDie(NetObj paramNetObj1, NetChannel paramNetChannel, NetObj paramNetObj2, int paramInt1, int paramInt2) throws IOException {
    int i = paramNetObj1.countMirrors();
    if (paramNetObj1.isMirror()) i++;
    if (paramNetChannel != null) i--;
    if (i <= 0) return;
    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    localNetMsgGuaranted.writeByte(24);
    localNetMsgGuaranted.writeNetObj(paramNetObj2);
    localNetMsgGuaranted.writeInt(paramInt1);
    localNetMsgGuaranted.writeShort(paramInt2);
    paramNetObj1.postExclude(paramNetChannel, localNetMsgGuaranted);
  }

  public HashMapInt allBlocks()
  {
    return this.allBlocks;
  }
  public int key(int paramInt1, int paramInt2) {
    return paramInt2 & 0xFFFF | paramInt1 << 16;
  }
  public int key2x(int paramInt) {
    return paramInt & 0xFFFF;
  }
  public int key2y(int paramInt) {
    return paramInt >> 16 & 0xFFFF;
  }

  public void updateBlock(int paramInt1, int paramInt2) {
    Block localBlock = (Block)this.allBlocks.get(key(paramInt2, paramInt1));
    if ((localBlock != null) && 
      (localBlock.actor != null)) {
      this._updateX[0] = paramInt1;
      this._updateY[0] = paramInt2;
      _msgDreamGlobal(false, 1, 0, this._updateX, this._updateY);
      _msgDreamGlobal(true, 1, 0, this._updateX, this._updateY);
    }
  }

  private Actor cacheGet(int paramInt)
  {
    ArrayList localArrayList = this.cacheActors[paramInt];
    int i = localArrayList.size();
    if (i == 0) return null;
    Actor localActor = (Actor)localArrayList.get(i - 1);
    localArrayList.remove(i - 1);
    return localActor;
  }

  private void cachePut(int paramInt, Actor paramActor) {
    ArrayList localArrayList = this.cacheActors[paramInt];

    localArrayList.add(paramActor);
    paramActor.drawing(false);
    paramActor.collide(false);
  }

  public static void trim()
  {
  }

  public void msgDreamGlobalTick(int paramInt1, int paramInt2)
  {
    _msgDreamGlobalTick(false, paramInt1, paramInt2 - 1);
    _msgDreamGlobalTick(true, paramInt1, paramInt2 - 1);
  }

  public void _msgDreamGlobalTick(boolean paramBoolean, int paramInt1, int paramInt2) {
    Queue localQueue = paramBoolean ? this.wQueue : this.sQueue;
    if (localQueue.ofs < localQueue.len) {
      int i = paramInt1 - paramInt2;
      int j = (localQueue.len * i + paramInt1 / 2) / paramInt1;
      if (j > localQueue.len)
        j = localQueue.len;
      int k = j - localQueue.ofs;
      if (k > 0) {
        _msgDreamGlobal(paramBoolean, k, localQueue.ofs, localQueue.X, localQueue.Y);
        localQueue.ofs = j;
        if (localQueue.ofs == localQueue.len)
          localQueue.clear();
      }
    }
  }

  public void msgDreamGlobal(boolean paramBoolean, int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    if (this.allBlocks == null) return;
    _msgDreamGlobalTick(paramBoolean, 1, 0);
    Queue localQueue = paramBoolean ? this.wQueue : this.sQueue;
    for (int i = 0; i < paramInt; i++) {
      int j = paramArrayOfInt1[i];
      int k = paramArrayOfInt2[i];
      Block localBlock = (Block)this.allBlocks.get(key(k, j));
      if (localBlock != null)
        localQueue.add(j, k);
    }
  }

  private void _msgDreamGlobal(boolean paramBoolean, int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    if (this.allBlocks == null) return;
    int i;
    int j;
    int k;
    Block localBlock;
    float f1;
    float f2;
    int m;
    int n;
    if (paramBoolean) {
      for (i = 0; i < paramInt1; i++) {
        j = paramArrayOfInt1[(i + paramInt2)];
        k = paramArrayOfInt2[(i + paramInt2)];
        localBlock = (Block)this.allBlocks.get(key(k, j));
        if ((localBlock == null) || (
          (this.bCheckPlate) && (localBlock.bExistPlate))) {
          continue;
        }
        f1 = j * 200.0F;
        f2 = k * 200.0F;
        m = localBlock.code.length / 2;

        localBlock.actor = new Actor[m];
        for (n = 0; n < m; n++) {
          int i1 = localBlock.code[(n * 2 + 0)];
          int i2 = localBlock.code[(n * 2 + 1)];
          int i3 = i1 & 0x7FFF;
          int i4 = (short)(i1 >> 16);
          int i5 = (short)(i2 & 0xFFFF);
          int i6 = (short)(i2 >> 16 & 0xFFFF);
          float f3 = i4 * 360.0F / 32000.0F;
          float f4 = i5 * 200.0F / 32000.0F + f1;
          float f5 = i6 * 200.0F / 32000.0F + f2;
          this._loc.set(f4, f5, 0.0D, -f3, 0.0F, 0.0F);
          Actor localActor2 = cacheGet(i3);
          if (localActor2 != null) {
            localActor2.pos.setAbs(this._loc);
            localActor2.setDiedFlag((i1 & 0x8000) != 0);
          } else {
            this._spawnArg.point = this._loc.getPoint();
            this._spawnArg.orient = this._loc.getOrient();
            try {
              localActor2 = this.spawns[i3].actorSpawn(this._spawnArg);
              if ((i1 & 0x8000) != 0)
                localActor2.setDiedFlag(true);
              if ((localActor2 instanceof ActorMesh))
                localActor2.draw.uniformMaxDist = this.uniformMaxDist[i3];
            }
            catch (Exception localException) {
              System.out.println(localException.getMessage());
              localException.printStackTrace();
            }
          }
          localBlock.actor[n] = localActor2;
        }
      }
    }
    else
      for (i = 0; i < paramInt1; i++) {
        j = paramArrayOfInt1[(i + paramInt2)];
        k = paramArrayOfInt2[(i + paramInt2)];
        localBlock = (Block)this.allBlocks.get(key(k, j));

        if ((localBlock == null) || (localBlock.actor == null) || (
          (this.bCheckPlate) && (localBlock.bExistPlate)))
          continue;
        f1 = j * 200.0F;
        f2 = k * 200.0F;
        m = localBlock.code.length / 2;

        for (n = 0; n < m; n++) {
          Actor localActor1 = localBlock.actor[n];
          localBlock.actor[n] = null;
          if (localActor1 != null) {
            if (localActor1.getDiedFlag())
              localBlock.code[(n * 2 + 0)] |= 32768;
            cachePut(localBlock.code[(n * 2 + 0)] & 0x7FFF, localActor1);
          }
        }
        localBlock.actor = null;
      }
  }

  public void resetGame()
  {
    this.wQueue.clear();
    this.sQueue.clear();
    if (this.allBlocks == null) return;
    ArrayList localArrayList1 = new ArrayList();

    HashMapIntEntry localHashMapIntEntry = this.allBlocks.nextEntry(null);
    while (localHashMapIntEntry != null) {
      Block localBlock = (Block)localHashMapIntEntry.getValue();
      if (localBlock.actor != null) {
        for (int j = 0; j < localBlock.actor.length; j++) {
          localArrayList1.add(localBlock.actor[j]);
          localBlock.actor[j] = null;
        }
      }
      localHashMapIntEntry = this.allBlocks.nextEntry(localHashMapIntEntry);
    }
    Engine.destroyListGameActors(localArrayList1);

    for (int i = 0; i < this.cacheActors.length; i++) {
      ArrayList localArrayList2 = this.cacheActors[i];
      Engine.destroyListGameActors(localArrayList2);
    }
    this.allBlocks = null;
    this.allStates0 = null;
    this.allBridge0 = null;
    this.cacheActors = null;
    this.spawns = null;
    this.bridges.clear();
  }
  public Object getSwitchListener(Message paramMessage) {
    return this;
  }
  public Statics() {
    this.flags |= 16384;
  }
  protected void createActorHashCode() {
    makeActorRealHashCode();
  }

  static class Queue
  {
    int ofs = 0;
    int len = 0;
    int[] X = new int[256];
    int[] Y = new int[256];

    void clear() { this.ofs = (this.len = 0); } 
    void add(int paramInt1, int paramInt2) {
      if (this.X.length <= this.len) {
        int[] arrayOfInt1 = new int[this.len * 2];
        int[] arrayOfInt2 = new int[this.len * 2];
        for (int i = this.ofs; i < this.len; i++) {
          arrayOfInt1[i] = this.X[i]; arrayOfInt2[i] = this.Y[i];
        }
        this.X = arrayOfInt1; this.Y = arrayOfInt2;
      }
      this.X[this.len] = paramInt1;
      this.Y[this.len] = paramInt2;
      this.len += 1;
    }
  }

  public static class Block
  {
    boolean bExistPlate = false;
    int[] code;
    public Actor[] actor;

    public int amountObjects()
    {
      return this.code.length / 2;
    }
    public boolean isEquals(byte[] paramArrayOfByte) {
      int i = this.code.length / 2;
      int j;
      int k;
      int m;
      if ((this.actor != null) && (this.actor[0] != null))
        for (j = 0; j < i; j++) {
          k = j >> 8;
          m = 1 << (j & 0xFF);
          if (!Actor.isAlive(this.actor[j])) {
            if ((paramArrayOfByte[k] & m) == 0) return false;

            if ((paramArrayOfByte[k] & m) != 0) return false; 
          }
        }
      else
        for (j = 0; j < i; j++) {
          k = j >> 8;
          m = 1 << (j & 0xFF);
          if ((this.code[(j * 2)] & 0x8000) != 0) {
            if ((paramArrayOfByte[k] & m) == 0) return false;

            if ((paramArrayOfByte[k] & m) != 0) return false;
          }
        }
      return true;
    }
    public byte[] getDestruction(byte[] paramArrayOfByte) {
      int i = this.code.length / 2;
      int j = (i + 7) / 8;
      if ((paramArrayOfByte == null) || (paramArrayOfByte.length < j))
        paramArrayOfByte = new byte[j];
      int k;
      int m;
      int n;
      if ((this.actor != null) && (this.actor[0] != null))
        for (k = 0; k < i; k++) {
          m = k >> 8;
          n = 1 << (k & 0xFF);
          if (!Actor.isAlive(this.actor[k]))
          {
            int tmp88_86 = m;
            byte[] tmp88_85 = paramArrayOfByte; tmp88_85[tmp88_86] = (byte)(tmp88_85[tmp88_86] | n);
          }
          else
          {
            int tmp101_99 = m;
            byte[] tmp101_98 = paramArrayOfByte; tmp101_98[tmp101_99] = (byte)(tmp101_98[tmp101_99] & (n ^ 0xFFFFFFFF));
          }
        }
      else for (k = 0; k < i; k++) {
          m = k >> 8;
          n = 1 << (k & 0xFF);
          if ((this.code[(k * 2)] & 0x8000) != 0)
          {
            int tmp163_161 = m;
            byte[] tmp163_160 = paramArrayOfByte; tmp163_160[tmp163_161] = (byte)(tmp163_160[tmp163_161] | n);
          }
          else
          {
            int tmp176_174 = m;
            byte[] tmp176_173 = paramArrayOfByte; tmp176_173[tmp176_174] = (byte)(tmp176_173[tmp176_174] & (n ^ 0xFFFFFFFF));
          }
        }
      return paramArrayOfByte;
    }
    public void setDestruction(byte[] paramArrayOfByte) {
      int i = this.code.length / 2;
      int j;
      int k;
      int m;
      if ((this.actor != null) && (this.actor[0] != null))
        for (j = 0; j < i; j++) {
          k = j >> 8;
          m = 1 << (j & 0xFF);
          this.actor[j].setDiedFlag((paramArrayOfByte[k] & m) != 0);
        }
      else
        for (j = 0; j < i; j++) {
          k = j >> 8;
          m = 1 << (j & 0xFF);
          if ((paramArrayOfByte[k] & m) != 0)
            this.code[(j * 2)] |= 32768;
          else
            this.code[(j * 2)] &= -32769;
        }
    }

    public void addDestruction(byte[] paramArrayOfByte) {
      int i = this.code.length / 2;
      int j;
      int k;
      int m;
      if ((this.actor != null) && (this.actor[0] != null))
        for (j = 0; j < i; j++) {
          k = j >> 8;
          m = 1 << (j & 0xFF);
          if ((paramArrayOfByte[k] & m) != 0)
            this.actor[j].setDiedFlag(true);
        }
      else
        for (j = 0; j < i; j++) {
          k = j >> 8;
          m = 1 << (j & 0xFF);
          if ((paramArrayOfByte[k] & m) != 0)
            this.code[(j * 2)] |= 32768;
        }
    }

    public float getDestruction() {
      int i = this.code.length / 2;
      int j = 0;
      int k;
      if ((this.actor != null) && (this.actor[0] != null))
        for (k = 0; k < i; k++)
          if (!Actor.isAlive(this.actor[k]))
            j++;
      else {
        for (k = 0; k < i; k++)
          if ((this.code[(k * 2)] & 0x8000) != 0)
            j++;
      }
      return j / i;
    }
    public void setDestruction(float paramFloat) {
      int i = this.code.length / 2;
      int j = (int)(paramFloat * i + 0.5F);
      if (j > i) j = i;
      int k;
      if ((this.actor != null) && (this.actor[0] != null)) {
        for (k = 0; k < i; k++)
          if (j > 0) {
            if (Actor.isAlive(this.actor[k]))
              this.actor[k].setDiedFlag(true);
            j--;
          }
          else if (!Actor.isAlive(this.actor[k])) {
            this.actor[k].setDiedFlag(false);
          }
      }
      else
        for (k = 0; k < i; k++)
          if (j > 0) {
            this.code[(k * 2)] |= 32768;
            j--;
          } else {
            this.code[(k * 2)] &= -32769;
          }
    }

    public boolean isDestructed()
    {
      int i = this.code.length / 2;
      int j;
      if ((this.actor != null) && (this.actor[0] != null))
        for (j = 0; j < i; j++)
          if (!this.actor[j].isAlive())
            return true;
      else {
        for (j = 0; j < i; j++)
          if ((this.code[(j * 2)] & 0x8000) != 0)
            return true;
      }
      return false;
    }
    public void restoreAll() {
      int i = this.code.length / 2;
      int j;
      if ((this.actor != null) && (this.actor[0] != null))
        for (j = 0; j < i; j++)
          if (!this.actor[j].isAlive())
            this.actor[j].setDiedFlag(false);
      else
        for (j = 0; j < i; j++)
          this.code[(j * 2)] &= -32769;
    }

    public void restoreAll(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
      int i = this.code.length / 2;
      int j;
      if ((this.actor != null) && (this.actor[0] != null)) {
        for (j = 0; j < i; j++)
          if (!this.actor[j].isAlive()) {
            Point3d localPoint3d = this.actor[j].pos.getAbsPoint();
            if ((localPoint3d.x - paramFloat3) * (localPoint3d.x - paramFloat3) + (localPoint3d.y - paramFloat4) * (localPoint3d.y - paramFloat4) <= paramFloat5)
              this.actor[j].setDiedFlag(false);
          }
      }
      else
        for (j = 0; j < i; j++)
          if ((this.code[(j * 2)] & 0x8000) != 0) {
            int k = this.code[(j * 2 + 0)];
            int m = this.code[(j * 2 + 1)];
            int n = (short)(m & 0xFFFF);
            int i1 = (short)(m >> 16 & 0xFFFF);
            float f1 = n * 200.0F / 32000.0F + paramFloat1;
            float f2 = i1 * 200.0F / 32000.0F + paramFloat2;
            if ((f1 - paramFloat3) * (f1 - paramFloat3) + (f2 - paramFloat4) * (f2 - paramFloat4) <= paramFloat5)
              this.code[(j * 2)] &= -32769;
          }
    }
  }
}
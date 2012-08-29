package com.maddox.il2.objects;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
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
import com.maddox.il2.objects.bridges.Bridge;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.bridges.LongBridge;
import com.maddox.il2.objects.buildings.House;
import com.maddox.il2.objects.buildings.Plate;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgOutput;
import com.maddox.rts.NetObj;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.util.AsciiBitSet;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import java.io.DataInputStream;
import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

  private static SectFile chiefs = null;
  private static SectFile air = null;
  private static SectFile stationary = null;
  private static SectFile rockets = null;
  private static SectFile vehicle = null;
  private static SectFile chiefs1 = null;
  private static SectFile air1 = null;
  private static SectFile stationary1 = null;
  private static SectFile rockets1 = null;
  private static SectFile vehicle1 = null;
  private static SectFile ships1 = null;
  private static SectFile technics1 = null;
  private static SectFile buildings1 = null;
  private static SectFile ships2 = null;
  private static SectFile technics2 = null;
  private static SectFile buildings2 = null;

  public static int[] readBridgesEndPoints(String paramString) {
    Object localObject = null;
    int[] arrayOfInt;
    try {
      DataInputStream localDataInputStream = new DataInputStream(new SFSInputStream(paramString));
      int i = localDataInputStream.readInt();
      if (i == -65535) i = localDataInputStream.readInt();
      arrayOfInt = new int[i * 4];
      for (int j = 0; j < i; j++)
      {
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
    }
    catch (Exception localException)
    {
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

  private void _load(String paramString, List paramList)
  {
    try
    {
      DataInputStream localDataInputStream = new DataInputStream(new SFSInputStream(paramString));
      int i = localDataInputStream.readInt();
      if (i != -65535) throw new Exception("Not supported sersion");
      System.out.println("Load bridges");
      int j = localDataInputStream.readInt();
      int i3;
      float f1;
      for (int k = 0; k < j; k++)
      {
        int m = localDataInputStream.readInt();
        n = localDataInputStream.readInt();
        int i1 = localDataInputStream.readInt();
        i3 = localDataInputStream.readInt();
        int i5 = localDataInputStream.readInt();
        f1 = localDataInputStream.readFloat();
        Bridge localBridge = new Bridge(k, i5, m, n, i1, i3, f1);
        if (paramList == null) continue; paramList.add(localBridge);
      }
      System.out.println("Load static objects");
      ArrayList localArrayList = new ArrayList();
      j = localDataInputStream.readInt();
      this.spawns = null;
      if (j > 0)
      {
        n = -1;
        ActorSpawnArg localActorSpawnArg = new ActorSpawnArg();
        this.spawns = new ActorSpawn[j];
        for (i3 = 0; i3 < j; i3++)
        {
          String str3 = localDataInputStream.readUTF();
          if ("com.maddox.il2.objects.air.Runaway".equals(str3)) n = i3;
          this.spawns[i3] = ((ActorSpawn)Spawn.get_WithSoftClass(str3));
        }
        j = localDataInputStream.readInt();
        while (j-- > 0)
        {
          int i6 = localDataInputStream.readInt();
          f1 = localDataInputStream.readFloat();
          float f2 = localDataInputStream.readFloat();
          float f3 = localDataInputStream.readFloat();
          this._loc.set(f1, f2, 0.0D, f3, 0.0F, 0.0F);
          Object localObject;
          if (n == i6)
          {
            localObject = new Loc(this._loc);
            localArrayList.add(localObject);
          }
          if ((i6 >= this.spawns.length) || (this.spawns[i6] == null))
            continue;
          localActorSpawnArg.clear();
          localActorSpawnArg.point = this._loc.getPoint();
          localActorSpawnArg.orient = this._loc.getOrient();
          try
          {
            localObject = this.spawns[i6].actorSpawn(localActorSpawnArg);
            if ((localObject instanceof ActorLandMesh))
            {
              ActorLandMesh localActorLandMesh = (ActorLandMesh)localObject;
              localActorLandMesh.mesh().setPos(localActorLandMesh.pos.getAbs());
              Landscape.meshAdd(localActorLandMesh);
            }
          }
          catch (Exception localException3)
          {
            System.out.println(localException3.getMessage());
            localException3.printStackTrace();
          }
        }
      }

      j = localDataInputStream.readInt();
      this.spawns = null;
      int n = -1;
      int i4;
      int i7;
      int i8;
      int i11;
      if (j > 0)
      {
        this.spawns = new ActorSpawn[j];
        this.spawnIsPlate = new boolean[j];
        this.uniformMaxDist = new float[j];
        this.cacheActors = new ArrayList[j];
        this._loc.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        for (int i2 = 0; i2 < j; i2++)
        {
          String str2 = localDataInputStream.readUTF();
          if (str2.indexOf("TreeLine") >= 0) n = i2;
          this.spawns[i2] = ((ActorSpawn)Spawn.get_WithSoftClass(str2));
          this.cacheActors[i2] = new ArrayList();
          try
          {
            this._spawnArg.point = this._loc.getPoint();
            this._spawnArg.orient = this._loc.getOrient();
            Actor localActor = this.spawns[i2].actorSpawn(this._spawnArg);
            cachePut(i2, localActor);
            this.spawnIsPlate[i2] = (localActor instanceof Plate);
            if ((localActor instanceof ActorMesh))
            {
              this.uniformMaxDist[i2] = ((ActorMesh)localActor).mesh().getUniformMaxDist();
              localActor.draw.uniformMaxDist = this.uniformMaxDist[i2];
            }
            else {
              this.uniformMaxDist[i2] = 0.0F;
            }
          }
          catch (Exception localException2) {
            System.out.println(localException2.getMessage());
            localException2.printStackTrace();
          }
        }
        i4 = localDataInputStream.readInt();
        this.allBlocks = new HashMapInt((int)(i4 / 0.75F));
        int i14;
        while (i4-- > 0)
        {
          i7 = localDataInputStream.readInt();
          i8 = i7 & 0xFFFF;
          int i9 = i7 >> 16 & 0xFFFF;
          i11 = localDataInputStream.readInt();
          Block localBlock1 = new Block();
          localBlock1.code = new int[i11 * 2];
          float f4 = 0.0F;
          for (i14 = 0; i14 < i11; i14++)
          {
            int i15 = localDataInputStream.readInt();
            if ((i15 & 0x7FFF) >= this.spawns.length) i15 = 0;
            localBlock1.code[(i14 * 2 + 0)] = i15;
            localBlock1.code[(i14 * 2 + 1)] = localDataInputStream.readInt();
            if (this.spawnIsPlate[(i15 & 0x7FFF)] != 0) localBlock1.bExistPlate = true;
            float f5 = this.uniformMaxDist[(i15 & 0x7FFF)];
            if (f4 >= f5) continue; f4 = f5;
          }
          this.allBlocks.put(key(i9, i8), localBlock1);
          Engine.drawEnv().setUniformMaxDist(i8, i9, f4);
          if (!localBlock1.bExistPlate)
            continue;
          this.bCheckPlate = false;
          this._updateX[0] = i8;
          this._updateY[0] = i9;
          _msgDreamGlobal(true, 1, 0, this._updateX, this._updateY);
          this.bCheckPlate = true;
        }

        if (Config.isUSE_RENDER())
        {
          i7 = Landscape.getSizeXpix();
          i8 = Landscape.getSizeYpix();
          int[] arrayOfInt = new int[i7 + 31 >> 5];
          for (i11 = 0; i11 < i8; i11++)
          {
            for (i12 = 0; i12 < arrayOfInt.length; i12++)
              arrayOfInt[i12] = 0;
            i13 = 0;
            i14 = 0;
            while (i14 < i7)
            {
              if (this.allBlocks.containsKey(key(i11, i14))) arrayOfInt[(i13 >> 5)] |= 1 << (i13 & 0x1F);
              i14++;
              i13++;
            }
            Landscape.MarkStaticActorsCells(0, i11, i7, 1, 200, arrayOfInt);
          }
          i7 = Landscape.getSizeXpix();
          i8 = Landscape.getSizeYpix();
          int i12 = 0;
          for (int i13 = 0; i13 < i8; i13++)
          {
            for (i14 = 0; i14 < i7; i14++)
            {
              if (!this.allBlocks.containsKey(key(i13, i14)))
                continue;
              Block localBlock2 = (Block)this.allBlocks.get(key(i13, i14));
              Landscape.MarkActorCellWithTrees(n, i14, i13, localBlock2.code, localBlock2.code.length);
              for (int i16 = 0; i16 < localBlock2.code.length; i16 += 2)
              {
                if ((localBlock2.code[i16] & 0x7FFF) != n) continue; i12++;
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
        for (i4 = 0; i4 < j; i4++)
        {
          i7 = localDataInputStream.readInt();
          localAirdrome.runw[i4] = new Point_Runaway[i7];
          for (i8 = 0; i8 < i7; i8++)
            localAirdrome.runw[i4][i8] = new Point_Runaway(localDataInputStream.readFloat(), localDataInputStream.readFloat());
        }
        j = localDataInputStream.readInt();
        localAirdrome.taxi = new Point_Taxi[j][];
        int i10;
        for (i7 = 0; i7 < j; i7++)
        {
          i8 = localDataInputStream.readInt();
          localAirdrome.taxi[i7] = new Point_Taxi[i8];
          for (i10 = 0; i10 < i8; i10++)
            localAirdrome.taxi[i7][i10] = new Point_Taxi(localDataInputStream.readFloat(), localDataInputStream.readFloat());
        }
        j = localDataInputStream.readInt();
        localAirdrome.stay = new Point_Stay[j][];
        for (i8 = 0; i8 < j; i8++)
        {
          i10 = localDataInputStream.readInt();
          localAirdrome.stay[i8] = new Point_Stay[i10];
          for (i11 = 0; i11 < i10; i11++)
            localAirdrome.stay[i8][i11] = new Point_Stay(localDataInputStream.readFloat(), localDataInputStream.readFloat());
        }
        localAirdrome.stayHold = new boolean[localAirdrome.stay.length];
      }
      World.cur().airdrome = localAirdrome;
      AirportStatic.make(localArrayList, localAirdrome.runw, localAirdrome.taxi, localAirdrome.stay);
      localDataInputStream.close();
    }
    catch (Exception localException1)
    {
      String str1 = "Actors load from '" + paramString + "' FAILED: " + localException1.getMessage();
      System.out.println(str1);
      localException1.printStackTrace();
    }
  }

  public void restoreAllBridges()
  {
    int i = 0;
    while (true)
    {
      LongBridge localLongBridge = LongBridge.getByIdx(i);
      if (localLongBridge == null) break;
      if (!localLongBridge.isAlive()) localLongBridge.BeLive();
      i++;
    }
  }

  public void saveStateBridges(SectFile paramSectFile, int paramInt)
  {
    int i = 0;
    while (true)
    {
      LongBridge localLongBridge = LongBridge.getByIdx(i);
      if (localLongBridge == null) break;
      if (!localLongBridge.isAlive())
      {
        int j = localLongBridge.NumStateBits();
        BitSet localBitSet = localLongBridge.GetStateOfSegments();
        paramSectFile.lineAdd(paramInt, AsciiBitSet.save(i), AsciiBitSet.save(localBitSet, j));
      }
      i++;
    }
  }

  public void loadStateBridges(SectFile paramSectFile, boolean paramBoolean)
  {
    int i = paramSectFile.sectionIndex(paramBoolean ? "AddBridge" : "Bridge");
    if (i >= 0) loadStateBridges(paramSectFile, i, paramBoolean);
  }

  public void loadStateBridges(SectFile paramSectFile, int paramInt, boolean paramBoolean)
  {
    int i = 0;
    if ((!paramBoolean) && (Mission.isDogfight()))
    {
      i = 1;
      this.allBridge0 = new HashMapInt();
    }
    int j = paramSectFile.vars(paramInt);
    BitSet localBitSet = new BitSet();
    for (int k = 0; k < j; k++)
    {
      String str1 = paramSectFile.var(paramInt, k);
      String str2 = paramSectFile.value(paramInt, k);
      int m = AsciiBitSet.load(str1);
      LongBridge localLongBridge = LongBridge.getByIdx(m);
      if (localLongBridge == null)
        continue;
      AsciiBitSet.load(str2, localBitSet, localLongBridge.NumStateBits());
      if (paramBoolean) localBitSet.or(localLongBridge.GetStateOfSegments());
      localLongBridge.SetStateOfSegments(localBitSet);
      if (i == 0) continue; this.allBridge0.put(m, localBitSet.clone());
    }
  }

  public void restoreAllHouses()
  {
    for (HashMapIntEntry localHashMapIntEntry = this.allBlocks.nextEntry(null); localHashMapIntEntry != null; localHashMapIntEntry = this.allBlocks.nextEntry(localHashMapIntEntry))
    {
      Block localBlock = (Block)localHashMapIntEntry.getValue();
      if (!localBlock.isDestructed()) continue; localBlock.restoreAll();
    }
  }

  public void saveStateHouses(SectFile paramSectFile, int paramInt)
  {
    for (HashMapIntEntry localHashMapIntEntry = this.allBlocks.nextEntry(null); localHashMapIntEntry != null; localHashMapIntEntry = this.allBlocks.nextEntry(localHashMapIntEntry))
    {
      Block localBlock = (Block)localHashMapIntEntry.getValue();
      if (!localBlock.isDestructed())
        continue;
      int i = localHashMapIntEntry.getKey();
      paramSectFile.lineAdd(paramInt, AsciiBitSet.save(i), AsciiBitSet.save(localBlock.getDestruction(null), localBlock.amountObjects()));
    }
  }

  public void loadStateHouses(SectFile paramSectFile, boolean paramBoolean)
  {
    int i = paramSectFile.sectionIndex(paramBoolean ? "AddHouse" : "House");
    if (i >= 0) loadStateHouses(paramSectFile, i, paramBoolean);
  }

  public void loadStateHouses(SectFile paramSectFile, int paramInt, boolean paramBoolean)
  {
    int i = 0;
    if ((!paramBoolean) && (Mission.isDogfight()))
    {
      i = 1;
      this.allStates0 = new HashMapInt();
    }
    int j = paramSectFile.vars(paramInt);
    byte[] arrayOfByte1 = new byte[32];
    for (int k = 0; k < j; k++)
    {
      String str1 = paramSectFile.var(paramInt, k);
      String str2 = paramSectFile.value(paramInt, k);
      int m = AsciiBitSet.load(str1);
      Block localBlock = (Block)this.allBlocks.get(m);
      if (localBlock == null)
        continue;
      arrayOfByte1 = AsciiBitSet.load(str2, arrayOfByte1, localBlock.amountObjects());
      if (paramBoolean) {
        localBlock.addDestruction(arrayOfByte1);
      }
      else {
        localBlock.setDestruction(arrayOfByte1);
        if (i == 0)
          continue;
        int n = (localBlock.amountObjects() + 7) / 8;
        byte[] arrayOfByte2 = new byte[n];
        System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, n);
        this.allStates0.put(m, arrayOfByte2);
      }
    }
  }

  public void restoreAllHouses(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    int i = (int)((paramFloat1 - paramFloat3) / 200.0F) - 1;
    int j = (int)((paramFloat1 + paramFloat3) / 200.0F) + 2;
    int k = (int)((paramFloat2 - paramFloat3) / 200.0F) - 1;
    int m = (int)((paramFloat2 + paramFloat3) / 200.0F) + 2;
    HashMapIntEntry localHashMapIntEntry = this.allBlocks.nextEntry(null);
    float f = paramFloat3 * paramFloat3;
    for (; localHashMapIntEntry != null; localHashMapIntEntry = this.allBlocks.nextEntry(localHashMapIntEntry))
    {
      Block localBlock = (Block)localHashMapIntEntry.getValue();
      int n = localHashMapIntEntry.getKey();
      int i1 = key2x(n);
      int i2 = key2y(n);
      if ((i1 < i) || (i1 > j) || (i2 < k) || (i2 > m)) continue; localBlock.restoreAll(i1 * 200.0F, i2 * 200.0F, paramFloat1, paramFloat2, f);
    }
  }

  public void netBridgeSync(NetChannel paramNetChannel)
  {
    int i = 0;
    while (true)
    {
      LongBridge localLongBridge = LongBridge.getByIdx(i);
      if (localLongBridge == null) break;
      if (!localLongBridge.isAlive())
      {
        int j = localLongBridge.NumStateBits();
        BitSet localBitSet1 = localLongBridge.GetStateOfSegments();
        BitSet localBitSet2 = null;
        if (this.allBridge0 != null) localBitSet2 = (BitSet)this.allBridge0.get(i);
        if ((localBitSet2 == null) || (!localBitSet2.equals(localBitSet1)))
        {
          try
          {
            NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
            localNetMsgGuaranted.writeByte(28);
            localNetMsgGuaranted.writeShort(i);
            int k = (j + 7) / 8;
            byte[] arrayOfByte = new byte[k];
            for (int m = 0; m < j; m++)
            {
              if (!localBitSet1.get(m))
                continue;
              int n = m / 8;
              int i1 = m % 8;
              int tmp146_144 = n;
              byte[] tmp146_142 = arrayOfByte; tmp146_142[tmp146_144] = (byte)(tmp146_142[tmp146_144] | 1 << i1);
            }

            localNetMsgGuaranted.write(arrayOfByte);
            NetEnv.host().postTo(paramNetChannel, localNetMsgGuaranted);
          }
          catch (Exception localException)
          {
          }
        }
      }

      i++;
    }
  }

  public void netMsgBridgeSync(NetMsgInput paramNetMsgInput) throws IOException
  {
    int i = paramNetMsgInput.readUnsignedShort();
    LongBridge localLongBridge = LongBridge.getByIdx(i);
    if (localLongBridge != null)
    {
      byte[] arrayOfByte = new byte[paramNetMsgInput.available()];
      paramNetMsgInput.read(arrayOfByte);
      int j = localLongBridge.NumStateBits();
      BitSet localBitSet = localLongBridge.GetStateOfSegments();
      for (int k = 0; k < j; k++)
      {
        int m = k / 8;
        int n = k % 8;
        if ((arrayOfByte[m] & 1 << n) != 0)
          localBitSet.set(k);
        else
          localBitSet.clear(k);
      }
      localLongBridge.SetStateOfSegments(localBitSet);
    }
  }

  public boolean onBridgeDied(int paramInt1, int paramInt2, int paramInt3, Actor paramActor)
  {
    ActorNet localActorNet;
    if (Mission.isServer())
    {
      try
      {
        NetMsgGuaranted localNetMsgGuaranted1 = new NetMsgGuaranted();
        localNetMsgGuaranted1.writeByte(27);
        localNetMsgGuaranted1.writeShort(paramInt1);
        localNetMsgGuaranted1.writeShort(paramInt2);
        localNetMsgGuaranted1.writeByte(paramInt3);
        localActorNet = null;
        if (Actor.isValid(paramActor)) localActorNet = paramActor.net;
        localNetMsgGuaranted1.writeNetObj(localActorNet);
        NetEnv.host().post(localNetMsgGuaranted1);
      }
      catch (Exception localException1)
      {
      }

      return true;
    }
    try
    {
      NetMsgGuaranted localNetMsgGuaranted2 = new NetMsgGuaranted();
      localNetMsgGuaranted2.writeByte(26);
      localNetMsgGuaranted2.writeShort(paramInt1);
      localNetMsgGuaranted2.writeShort(paramInt2);
      localNetMsgGuaranted2.writeByte(paramInt3);
      localActorNet = null;
      if (Actor.isValid(paramActor)) localActorNet = paramActor.net;
      localNetMsgGuaranted2.writeNetObj(localActorNet);
      NetEnv.host().postTo(Main.cur().netServerParams.masterChannel(), localNetMsgGuaranted2);
    }
    catch (Exception localException2)
    {
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
    if (localNetObj != null) localActor = (Actor)localNetObj.superObj();
    if (Mission.isServer())
    {
      BridgeSegment localBridgeSegment = BridgeSegment.getByIdx(i, j);
      localBridgeSegment.netForcePartDestroing(k, localActor);
    }
    else {
      onBridgeDied(i, j, k, localActor);
    }
  }

  public void netMsgBridgeDie(NetObj paramNetObj, NetMsgInput paramNetMsgInput) throws IOException {
    int i = paramNetMsgInput.readUnsignedShort();
    int j = paramNetMsgInput.readUnsignedShort();
    int k = paramNetMsgInput.readByte();
    Actor localActor = null;
    NetObj localNetObj = paramNetMsgInput.readNetObj();
    if (localNetObj != null) localActor = (Actor)localNetObj.superObj();
    try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(27);
      localNetMsgGuaranted.writeShort(i);
      localNetMsgGuaranted.writeShort(j);
      localNetMsgGuaranted.writeByte(k);
      ActorNet localActorNet = null;
      if (Actor.isValid(localActor)) localActorNet = localActor.net;
      localNetMsgGuaranted.writeNetObj(localActorNet);
      paramNetObj.post(localNetMsgGuaranted);
    }
    catch (Exception localException)
    {
    }

    BridgeSegment localBridgeSegment = BridgeSegment.getByIdx(i, j);
    localBridgeSegment.netForcePartDestroing(k, localActor);
  }

  public void netHouseSync(NetChannel paramNetChannel)
  {
    for (HashMapIntEntry localHashMapIntEntry = this.allBlocks.nextEntry(null); localHashMapIntEntry != null; localHashMapIntEntry = this.allBlocks.nextEntry(localHashMapIntEntry))
    {
      Block localBlock = (Block)localHashMapIntEntry.getValue();
      int i = localHashMapIntEntry.getKey();
      byte[] arrayOfByte = null;
      if (this.allStates0 != null) arrayOfByte = (byte[])this.allStates0.get(i);
      if (arrayOfByte == null)
      {
        if (!localBlock.isDestructed()) continue; putMsgHouseSync(paramNetChannel, i, localBlock);
      } else {
        if (localBlock.isEquals(arrayOfByte)) continue; putMsgHouseSync(paramNetChannel, i, localBlock);
      }
    }
  }

  private void putMsgHouseSync(NetChannel paramNetChannel, int paramInt, Block paramBlock)
  {
    try {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(25);
      localNetMsgGuaranted.writeInt(paramInt);
      localNetMsgGuaranted.write(paramBlock.getDestruction(null));
      NetEnv.host().postTo(paramNetChannel, localNetMsgGuaranted);
    }
    catch (Exception localException)
    {
    }
  }

  public void netMsgHouseSync(NetMsgInput paramNetMsgInput)
    throws IOException
  {
    int i = paramNetMsgInput.readInt();
    byte[] arrayOfByte = new byte[paramNetMsgInput.available()];
    paramNetMsgInput.read(arrayOfByte);
    Block localBlock = (Block)this.allBlocks.get(i);
    if (localBlock != null) localBlock.setDestruction(arrayOfByte); 
  }

  public void netMsgHouseDie(NetObj paramNetObj, NetMsgInput paramNetMsgInput)
    throws IOException
  {
    Actor localActor = null;
    NetObj localNetObj = paramNetMsgInput.readNetObj();
    if (localNetObj != null) localActor = (Actor)localNetObj.superObj();
    int i = paramNetMsgInput.readInt();
    int j = paramNetMsgInput.readUnsignedShort();
    Block localBlock = (Block)this.allBlocks.get(i);
    if ((localBlock != null) && (j < localBlock.code.length / 2))
    {
      if ((localBlock.actor != null) && (localBlock.actor[0] != null))
      {
        if ((Actor.isAlive(localBlock.actor[j])) && ((localBlock.actor[j] instanceof House)))
        {
          ((House)localBlock.actor[j]).doDieShow();
          World.onActorDied(localBlock.actor[j], localActor);
          replicateHouseDie(paramNetObj, paramNetMsgInput.channel(), localNetObj, i, j);
        }
      }
      else if ((localBlock.code[(j * 2)] & 0x8000) == 0)
      {
        localBlock.code[(j * 2)] |= 32768;
        replicateHouseDie(paramNetObj, paramNetMsgInput.channel(), localNetObj, i, j);
      }
    }
  }

  public void onHouseDied(House paramHouse, Actor paramActor)
  {
    Point3d localPoint3d = paramHouse.pos.getAbsPoint();
    int i = (int)(localPoint3d.y / 200.0D);
    int j = (int)(localPoint3d.x / 200.0D);
    int k = key(i, j);
    Block localBlock = (Block)this.allBlocks.get(k);
    if ((localBlock != null) && (localBlock.actor != null))
    {
      int m = 0;

      for (int n = localBlock.actor.length; m < n; m++)
      {
        if (localBlock.actor[m] == paramHouse) break;
      }
      if (m < n)
      {
        ActorNet localActorNet = null;
        if (Actor.isValid(paramActor)) localActorNet = paramActor.net;
        try
        {
          replicateHouseDie(NetEnv.host(), null, localActorNet, k, m);
        }
        catch (Exception localException)
        {
        }
      }
    }
  }

  private void replicateHouseDie(NetObj paramNetObj1, NetChannel paramNetChannel, NetObj paramNetObj2, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = paramNetObj1.countMirrors();
    if (paramNetObj1.isMirror()) i++;
    if (paramNetChannel != null) i--;
    if (i > 0)
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(24);
      localNetMsgGuaranted.writeNetObj(paramNetObj2);
      localNetMsgGuaranted.writeInt(paramInt1);
      localNetMsgGuaranted.writeShort(paramInt2);
      paramNetObj1.postExclude(paramNetChannel, localNetMsgGuaranted);
    }
  }

  public HashMapInt allBlocks()
  {
    return this.allBlocks;
  }

  public int key(int paramInt1, int paramInt2)
  {
    return paramInt2 & 0xFFFF | paramInt1 << 16;
  }

  public int key2x(int paramInt)
  {
    return paramInt & 0xFFFF;
  }

  public int key2y(int paramInt)
  {
    return paramInt >> 16 & 0xFFFF;
  }

  public void updateBlock(int paramInt1, int paramInt2)
  {
    Block localBlock = (Block)this.allBlocks.get(key(paramInt2, paramInt1));
    if ((localBlock != null) && (localBlock.actor != null))
    {
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

  private void cachePut(int paramInt, Actor paramActor)
  {
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

  public void _msgDreamGlobalTick(boolean paramBoolean, int paramInt1, int paramInt2)
  {
    Queue localQueue = paramBoolean ? this.wQueue : this.sQueue;
    if (localQueue.ofs < localQueue.len)
    {
      int i = paramInt1 - paramInt2;
      int j = (localQueue.len * i + paramInt1 / 2) / paramInt1;
      if (j > localQueue.len) j = localQueue.len;
      int k = j - localQueue.ofs;
      if (k > 0)
      {
        _msgDreamGlobal(paramBoolean, k, localQueue.ofs, localQueue.X, localQueue.Y);
        localQueue.ofs = j;
        if (localQueue.ofs == localQueue.len) localQueue.clear();
      }
    }
  }

  public void msgDreamGlobal(boolean paramBoolean, int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    if (this.allBlocks != null)
    {
      _msgDreamGlobalTick(paramBoolean, 1, 0);
      Queue localQueue = paramBoolean ? this.wQueue : this.sQueue;
      for (int i = 0; i < paramInt; i++)
      {
        int j = paramArrayOfInt1[i];
        int k = paramArrayOfInt2[i];
        Block localBlock = (Block)this.allBlocks.get(key(k, j));
        if (localBlock == null) continue; localQueue.add(j, k);
      }
    }
  }

  private void _msgDreamGlobal(boolean paramBoolean, int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    if (this.allBlocks != null)
    {
      int i;
      int j;
      int k;
      Block localBlock;
      float f1;
      float f2;
      int m;
      int n;
      if (paramBoolean)
      {
        for (i = 0; i < paramInt1; i++)
        {
          j = paramArrayOfInt1[(i + paramInt2)];
          k = paramArrayOfInt2[(i + paramInt2)];
          localBlock = (Block)this.allBlocks.get(key(k, j));
          if ((localBlock == null) || ((this.bCheckPlate) && (localBlock.bExistPlate)))
            continue;
          f1 = j * 200.0F;
          f2 = k * 200.0F;
          m = localBlock.code.length / 2;
          localBlock.actor = new Actor[m];
          for (n = 0; n < m; n++)
          {
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
            if (localActor2 != null)
            {
              localActor2.pos.setAbs(this._loc);
              localActor2.setDiedFlag((i1 & 0x8000) != 0);
            }
            else
            {
              this._spawnArg.point = this._loc.getPoint();
              this._spawnArg.orient = this._loc.getOrient();
              try
              {
                localActor2 = this.spawns[i3].actorSpawn(this._spawnArg);
                if ((i1 & 0x8000) != 0) localActor2.setDiedFlag(true);
                if ((localActor2 instanceof ActorMesh)) localActor2.draw.uniformMaxDist = this.uniformMaxDist[i3];
              }
              catch (Exception localException)
              {
                System.out.println(localException.getMessage());
                localException.printStackTrace();
              }
            }
            localBlock.actor[n] = localActor2;
          }
        }

      }
      else
      {
        for (i = 0; i < paramInt1; i++)
        {
          j = paramArrayOfInt1[(i + paramInt2)];
          k = paramArrayOfInt2[(i + paramInt2)];
          localBlock = (Block)this.allBlocks.get(key(k, j));
          if ((localBlock == null) || (localBlock.actor == null) || ((this.bCheckPlate) && (localBlock.bExistPlate)))
            continue;
          f1 = j * 200.0F;
          f2 = k * 200.0F;
          m = localBlock.code.length / 2;
          for (n = 0; n < m; n++)
          {
            Actor localActor1 = localBlock.actor[n];
            localBlock.actor[n] = null;
            if (localActor1 == null)
              continue;
            if (localActor1.getDiedFlag()) localBlock.code[(n * 2 + 0)] |= 32768;
            cachePut(localBlock.code[(n * 2 + 0)] & 0x7FFF, localActor1);
          }

          localBlock.actor = null;
        }
      }
    }
  }

  public void resetGame()
  {
    this.wQueue.clear();
    this.sQueue.clear();
    if (this.allBlocks != null)
    {
      ArrayList localArrayList1 = new ArrayList();
      for (HashMapIntEntry localHashMapIntEntry = this.allBlocks.nextEntry(null); localHashMapIntEntry != null; localHashMapIntEntry = this.allBlocks.nextEntry(localHashMapIntEntry))
      {
        Block localBlock = (Block)localHashMapIntEntry.getValue();
        if (localBlock.actor == null)
          continue;
        for (int j = 0; j < localBlock.actor.length; j++)
        {
          localArrayList1.add(localBlock.actor[j]);
          localBlock.actor[j] = null;
        }
      }

      Engine.destroyListGameActors(localArrayList1);
      for (int i = 0; i < this.cacheActors.length; i++)
      {
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
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  public Statics()
  {
    this.flags |= 16384;
  }

  protected void createActorHashCode()
  {
    makeActorRealHashCode();
  }

  public static SectFile getShipsFile()
  {
    if (ships == null)
    {
      ships = new SectFile("com/maddox/il2/objects/ships.ini");
      ships.createIndexes();
    }
    return ships;
  }

  public static SectFile getTechnicsFile()
  {
    if (technics == null)
    {
      technics = new SectFile("com/maddox/il2/objects/technics.ini");
      technics.createIndexes();
    }
    return technics;
  }

  public static SectFile getBuildingsFile()
  {
    if (buildings == null)
    {
      buildings = new SectFile("com/maddox/il2/objects/static.ini");
      buildings.createIndexes();
    }
    return buildings;
  }

  public static SectFile getChiefsFile() {
    if (!new File("files/com/maddox/il2/objects/chief.in").exists()) {
      return null;
    }
    try
    {
      if (chiefs == null)
      {
        chiefs = new SectFile("files/com/maddox/il2/objects/chief.ini");
        chiefs.createIndexes();
      }
      return chiefs; } catch (Exception localException) {
    }
    return null;
  }

  public static SectFile getAirFile() {
    if (!new File("files/com/maddox/il2/objects/air.ini").exists()) {
      return null;
    }
    try
    {
      if (air == null)
      {
        air = new SectFile("files/com/maddox/il2/objects/air.ini");
        air.createIndexes();
      }
      return air; } catch (Exception localException) {
    }
    return null;
  }

  public static SectFile getStationaryFile() {
    if (!new File("files/com/maddox/il2/objects/stationary.ini").exists()) {
      return null;
    }
    try
    {
      if (stationary == null)
      {
        stationary = new SectFile("files/com/maddox/il2/objects/stationary.ini");
        stationary.createIndexes();
      }
      return stationary; } catch (Exception localException) {
    }
    return null;
  }

  public static SectFile getRocketsFile() {
    if (!new File("files/com/maddox/il2/objects/rockets.ini").exists()) {
      return null;
    }
    try
    {
      if (rockets == null)
      {
        rockets = new SectFile("files/com/maddox/il2/objects/rockets.ini");
        rockets.createIndexes();
      }
      return rockets; } catch (Exception localException) {
    }
    return null;
  }

  public static SectFile getVehiclesFile() {
    if (!new File("files/com/maddox/il2/objects/vehicle.ini").exists()) {
      return null;
    }
    try
    {
      if (vehicle == null)
      {
        vehicle = new SectFile("files/com/maddox/il2/objects/vehicle.ini");
        vehicle.createIndexes();
      }
      return vehicle; } catch (Exception localException) {
    }
    return null;
  }

  public static SectFile getShips1File() {
    if (!new File("mods/std/com/maddox/il2/objects/ships.ini").exists()) {
      return null;
    }
    try
    {
      if (ships1 == null)
      {
        ships1 = new SectFile("mods/std/com/maddox/il2/objects/ships.ini");
        ships1.createIndexes();
      }
      return ships1; } catch (Exception localException) {
    }
    return null;
  }

  public static SectFile getTechnics1File() {
    if (!new File("mods/std/com/maddox/il2/objects/technics.ini").exists()) {
      return null;
    }
    try
    {
      if (technics1 == null)
      {
        technics1 = new SectFile("mods/std/com/maddox/il2/objects/technics.ini");
        technics1.createIndexes();
      }
      return technics1; } catch (Exception localException) {
    }
    return null;
  }

  public static SectFile getBuildings1File() {
    if (!new File("mods/std/com/maddox/il2/objects/static.ini").exists()) {
      return null;
    }
    try
    {
      if (buildings1 == null)
      {
        buildings1 = new SectFile("mods/std/com/maddox/il2/objects/static.ini");
        buildings1.createIndexes();
      }
      return buildings1; } catch (Exception localException) {
    }
    return null;
  }

  public static SectFile getChiefs1File() {
    if (!new File("mods/std/com/maddox/il2/objects/chief.ini").exists()) {
      return null;
    }
    try
    {
      if (chiefs1 == null)
      {
        chiefs1 = new SectFile("mods/std/com/maddox/il2/objects/chief.ini");
        chiefs1.createIndexes();
      }
      return chiefs1; } catch (Exception localException) {
    }
    return null;
  }

  public static SectFile getAir1File() {
    if (!new File("mods/std/com/maddox/il2/objects/air.ini").exists()) {
      return null;
    }
    try
    {
      if (air1 == null)
      {
        air1 = new SectFile("mods/std/com/maddox/il2/objects/air.ini");
        air1.createIndexes();
      }
      return air1; } catch (Exception localException) {
    }
    return null;
  }

  public static SectFile getStationary1File() {
    if (!new File("mods/std/com/maddox/il2/objects/stationary.ini").exists()) {
      return null;
    }
    try
    {
      if (stationary1 == null)
      {
        stationary1 = new SectFile("mods/std/com/maddox/il2/objects/stationary.ini");
        stationary1.createIndexes();
      }
      return stationary1; } catch (Exception localException) {
    }
    return null;
  }

  public static SectFile getRockets1File() {
    if (!new File("mods/std/com/maddox/il2/objects/rockets.ini").exists()) {
      return null;
    }
    try
    {
      if (rockets1 == null)
      {
        rockets1 = new SectFile("mods/std/com/maddox/il2/objects/rockets.ini");
        rockets1.createIndexes();
      }
      return rockets1; } catch (Exception localException) {
    }
    return null;
  }

  public static SectFile getVehicles1File() {
    if (!new File("mods/std/com/maddox/il2/objects/vehicle.ini").exists()) {
      return null;
    }
    try
    {
      if (vehicle1 == null)
      {
        vehicle1 = new SectFile("mods/std/com/maddox/il2/objects/vehicle.ini");
        vehicle1.createIndexes();
      }
      return vehicle1; } catch (Exception localException) {
    }
    return null;
  }

  public static SectFile getShips2File() {
    if (!new File("files/com/maddox/il2/objects/ships.ini").exists()) {
      return null;
    }
    try
    {
      if (ships2 == null)
      {
        ships2 = new SectFile("files/com/maddox/il2/objects/ships.ini");
        ships2.createIndexes();
      }
      return ships2; } catch (Exception localException) {
    }
    return null;
  }

  public static SectFile getTechnics2File() {
    if (!new File("files/com/maddox/il2/objects/technics.ini").exists()) {
      return null;
    }
    try
    {
      if (technics2 == null)
      {
        technics2 = new SectFile("files/com/maddox/il2/objects/technics.ini");
        technics2.createIndexes();
      }
      return technics2; } catch (Exception localException) {
    }
    return null;
  }

  public static SectFile getBuildings2File() {
    if (!new File("files/com/maddox/il2/objects/static.ini").exists()) {
      return null;
    }
    try
    {
      if (buildings2 == null)
      {
        buildings2 = new SectFile("files/com/maddox/il2/objects/static.ini");
        buildings2.createIndexes();
      }
      return buildings2; } catch (Exception localException) {
    }
    return null;
  }

  public static long getSize(File paramFile, long paramLong1, long paramLong2) {
    long l = 0L;
    if (paramFile.isFile())
    {
      l = paramFile.length();
      paramLong1 += 1L;
    }
    else
    {
      File[] arrayOfFile = paramFile.listFiles();

      for (int i = 0; i < arrayOfFile.length; i++)
      {
        File localFile = arrayOfFile[i];

        if ((localFile.getName().equals("5D18E55E5DF1D418")) || (localFile.getName().equals(".rcs")) || (localFile.getName().equals(".rc"))) {
          continue;
        }
        if (localFile.isFile())
        {
          l += localFile.length();
          paramLong1 += 1L;
        }
        else
        {
          l += getSize(localFile, paramLong1, paramLong2);
        }
      }

      paramLong2 += 1L;
    }
    return l + paramLong1 + paramLong2;
  }

  public static class Block
  {
    boolean bExistPlate = false;
    int[] code;
    Actor[] actor;

    public int amountObjects()
    {
      return this.code.length / 2;
    }

    public boolean isEquals(byte[] paramArrayOfByte)
    {
      int i = this.code.length / 2;
      int j;
      int k;
      int m;
      if ((this.actor != null) && (this.actor[0] != null))
      {
        for (j = 0; j < i; j++)
        {
          k = j >> 8;
          m = 1 << (j & 0xFF);
          if (Actor.isAlive(this.actor[j]))
            continue;
          if ((paramArrayOfByte[k] & m) == 0) return false;
          if ((paramArrayOfByte[k] & m) != 0) return false;
        }

      }
      else
      {
        for (j = 0; j < i; j++)
        {
          k = j >> 8;
          m = 1 << (j & 0xFF);
          if ((this.code[(j * 2)] & 0x8000) == 0)
            continue;
          if ((paramArrayOfByte[k] & m) == 0) return false;
          if ((paramArrayOfByte[k] & m) != 0) return false;
        }
      }

      return true;
    }

    public byte[] getDestruction(byte[] paramArrayOfByte)
    {
      int i = this.code.length / 2;
      int j = (i + 7) / 8;
      if ((paramArrayOfByte == null) || (paramArrayOfByte.length < j)) paramArrayOfByte = new byte[j];
      int k;
      int m;
      int n;
      if ((this.actor != null) && (this.actor[0] != null))
      {
        for (k = 0; k < i; k++)
        {
          m = k >> 8;
          n = 1 << (k & 0xFF);
          if (!Actor.isAlive(this.actor[k]))
          {
            int tmp85_83 = m;
            byte[] tmp85_82 = paramArrayOfByte; tmp85_82[tmp85_83] = (byte)(tmp85_82[tmp85_83] | n);
          }
          else
          {
            int tmp98_96 = m;
            byte[] tmp98_95 = paramArrayOfByte; tmp98_95[tmp98_96] = (byte)(tmp98_95[tmp98_96] & (n ^ 0xFFFFFFFF));
          }
        }
      }
      else
        for (k = 0; k < i; k++)
        {
          m = k >> 8;
          n = 1 << (k & 0xFF);
          if ((this.code[(k * 2)] & 0x8000) != 0)
          {
            int tmp160_158 = m;
            byte[] tmp160_157 = paramArrayOfByte; tmp160_157[tmp160_158] = (byte)(tmp160_157[tmp160_158] | n);
          }
          else
          {
            int tmp173_171 = m;
            byte[] tmp173_170 = paramArrayOfByte; tmp173_170[tmp173_171] = (byte)(tmp173_170[tmp173_171] & (n ^ 0xFFFFFFFF));
          }
        }
      return paramArrayOfByte;
    }

    public void setDestruction(byte[] paramArrayOfByte)
    {
      int i = this.code.length / 2;
      int j;
      int k;
      int m;
      if ((this.actor != null) && (this.actor[0] != null))
      {
        for (j = 0; j < i; j++)
        {
          k = j >> 8;
          m = 1 << (j & 0xFF);
          this.actor[j].setDiedFlag((paramArrayOfByte[k] & m) != 0);
        }
      }
      else
      {
        for (j = 0; j < i; j++)
        {
          k = j >> 8;
          m = 1 << (j & 0xFF);
          if ((paramArrayOfByte[k] & m) != 0)
            this.code[(j * 2)] |= 32768;
          else
            this.code[(j * 2)] &= -32769;
        }
      }
    }

    public void addDestruction(byte[] paramArrayOfByte)
    {
      int i = this.code.length / 2;
      int j;
      int k;
      int m;
      if ((this.actor != null) && (this.actor[0] != null))
      {
        for (j = 0; j < i; j++)
        {
          k = j >> 8;
          m = 1 << (j & 0xFF);
          if ((paramArrayOfByte[k] & m) == 0) continue; this.actor[j].setDiedFlag(true);
        }
      }
      else
      {
        for (j = 0; j < i; j++)
        {
          k = j >> 8;
          m = 1 << (j & 0xFF);
          if ((paramArrayOfByte[k] & m) == 0) continue; this.code[(j * 2)] |= 32768;
        }
      }
    }

    public float getDestruction()
    {
      int i = this.code.length / 2;
      int j = 0;
      int k;
      if ((this.actor != null) && (this.actor[0] != null))
      {
        for (k = 0; k < i; k++)
        {
          if (Actor.isAlive(this.actor[k])) continue; j++;
        }
      }
      else
      {
        for (k = 0; k < i; k++)
        {
          if ((this.code[(k * 2)] & 0x8000) == 0) continue; j++;
        }
      }
      return j / i;
    }

    public void setDestruction(float paramFloat)
    {
      int i = this.code.length / 2;
      int j = (int)(paramFloat * i + 0.5F);
      if (j > i) j = i;
      int k;
      if ((this.actor != null) && (this.actor[0] != null))
      {
        for (k = 0; k < i; k++)
        {
          if (j > 0)
          {
            if (Actor.isAlive(this.actor[k])) this.actor[k].setDiedFlag(true);
            j--;
          } else {
            if (Actor.isAlive(this.actor[k])) continue; this.actor[k].setDiedFlag(false);
          }
        }
      }
      else
        for (k = 0; k < i; k++)
        {
          if (j > 0)
          {
            this.code[(k * 2)] |= 32768;
            j--;
          }
          else {
            this.code[(k * 2)] &= -32769;
          }
        }
    }

    public boolean isDestructed()
    {
      int i = this.code.length / 2;
      int j;
      if ((this.actor != null) && (this.actor[0] != null))
      {
        for (j = 0; j < i; j++)
        {
          if (!this.actor[j].isAlive()) return true;
        }
      }
      else
      {
        for (j = 0; j < i; j++)
        {
          if ((this.code[(j * 2)] & 0x8000) != 0) return true;
        }
      }
      return false;
    }

    public void restoreAll()
    {
      int i = this.code.length / 2;
      int j;
      if ((this.actor != null) && (this.actor[0] != null))
      {
        for (j = 0; j < i; j++)
        {
          if (this.actor[j].isAlive()) continue; this.actor[j].setDiedFlag(false);
        }
      }
      else
      {
        for (j = 0; j < i; j++)
          this.code[(j * 2)] &= -32769;
      }
    }

    public void restoreAll(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
    {
      int i = this.code.length / 2;
      int j;
      if ((this.actor != null) && (this.actor[0] != null))
      {
        for (j = 0; j < i; j++)
        {
          if (this.actor[j].isAlive())
            continue;
          Point3d localPoint3d = this.actor[j].pos.getAbsPoint();
          if ((localPoint3d.x - paramFloat3) * (localPoint3d.x - paramFloat3) + (localPoint3d.y - paramFloat4) * (localPoint3d.y - paramFloat4) > paramFloat5) continue; this.actor[j].setDiedFlag(false);
        }

      }
      else
      {
        for (j = 0; j < i; j++)
        {
          if ((this.code[(j * 2)] & 0x8000) == 0)
            continue;
          int k = this.code[(j * 2 + 0)];
          int m = this.code[(j * 2 + 1)];
          int n = (short)(m & 0xFFFF);
          int i1 = (short)(m >> 16 & 0xFFFF);
          float f1 = n * 200.0F / 32000.0F + paramFloat1;
          float f2 = i1 * 200.0F / 32000.0F + paramFloat2;
          if ((f1 - paramFloat3) * (f1 - paramFloat3) + (f2 - paramFloat4) * (f2 - paramFloat4) > paramFloat5) continue; this.code[(j * 2)] &= -32769;
        }
      }
    }
  }

  static class Queue
  {
    int ofs = 0;
    int len = 0;
    int[] X = new int[256];
    int[] Y = new int[256];

    void clear()
    {
      this.ofs = (this.len = 0);
    }

    void add(int paramInt1, int paramInt2)
    {
      if (this.X.length <= this.len)
      {
        int[] arrayOfInt1 = new int[this.len * 2];
        int[] arrayOfInt2 = new int[this.len * 2];
        for (int i = this.ofs; i < this.len; i++)
        {
          arrayOfInt1[i] = this.X[i];
          arrayOfInt2[i] = this.Y[i];
        }
        this.X = arrayOfInt1;
        this.Y = arrayOfInt2;
      }
      this.X[this.len] = paramInt1;
      this.Y[this.len] = paramInt2;
      this.len += 1;
    }
  }
}
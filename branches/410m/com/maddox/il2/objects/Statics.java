// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Statics.java

package com.maddox.il2.objects;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.AirportStatic;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Airdrome;
import com.maddox.il2.ai.air.Point_Runaway;
import com.maddox.il2.ai.air.Point_Stay;
import com.maddox.il2.ai.air.Point_Taxi;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorLandMesh;
import com.maddox.il2.engine.ActorMesh;
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

public class Statics extends com.maddox.il2.engine.Actor
    implements com.maddox.il2.engine.MsgDreamGlobalListener
{
    static class Queue
    {

        void clear()
        {
            ofs = len = 0;
        }

        void add(int i, int j)
        {
            if(X.length <= len)
            {
                int ai[] = new int[len * 2];
                int ai1[] = new int[len * 2];
                for(int k = ofs; k < len; k++)
                {
                    ai[k] = X[k];
                    ai1[k] = Y[k];
                }

                X = ai;
                Y = ai1;
            }
            X[len] = i;
            Y[len] = j;
            len++;
        }

        int ofs;
        int len;
        int X[];
        int Y[];

        Queue()
        {
            ofs = 0;
            len = 0;
            X = new int[256];
            Y = new int[256];
        }
    }

    public static class Block
    {

        public int amountObjects()
        {
            return code.length / 2;
        }

        public boolean isEquals(byte abyte0[])
        {
            int i = code.length / 2;
            if(actor != null && actor[0] != null)
            {
                for(int j = 0; j < i; j++)
                {
                    int l = j >> 8;
                    int j1 = 1 << (j & 0xff);
                    if(!com.maddox.il2.engine.Actor.isAlive(actor[j]))
                    {
                        if((abyte0[l] & j1) == 0)
                            return false;
                        if((abyte0[l] & j1) != 0)
                            return false;
                    }
                }

            } else
            {
                for(int k = 0; k < i; k++)
                {
                    int i1 = k >> 8;
                    int k1 = 1 << (k & 0xff);
                    if((code[k * 2] & 0x8000) == 0)
                        continue;
                    if((abyte0[i1] & k1) == 0)
                        return false;
                    if((abyte0[i1] & k1) != 0)
                        return false;
                }

            }
            return true;
        }

        public byte[] getDestruction(byte abyte0[])
        {
            int i = code.length / 2;
            int j = (i + 7) / 8;
            if(abyte0 == null || abyte0.length < j)
                abyte0 = new byte[j];
            if(actor != null && actor[0] != null)
            {
                for(int k = 0; k < i; k++)
                {
                    int i1 = k >> 8;
                    int k1 = 1 << (k & 0xff);
                    if(!com.maddox.il2.engine.Actor.isAlive(actor[k]))
                        abyte0[i1] |= k1;
                    else
                        abyte0[i1] &= ~k1;
                }

            } else
            {
                for(int l = 0; l < i; l++)
                {
                    int j1 = l >> 8;
                    int l1 = 1 << (l & 0xff);
                    if((code[l * 2] & 0x8000) != 0)
                        abyte0[j1] |= l1;
                    else
                        abyte0[j1] &= ~l1;
                }

            }
            return abyte0;
        }

        public void setDestruction(byte abyte0[])
        {
            int i = code.length / 2;
            if(actor != null && actor[0] != null)
            {
                for(int j = 0; j < i; j++)
                {
                    int l = j >> 8;
                    int j1 = 1 << (j & 0xff);
                    actor[j].setDiedFlag((abyte0[l] & j1) != 0);
                }

            } else
            {
                for(int k = 0; k < i; k++)
                {
                    int i1 = k >> 8;
                    int k1 = 1 << (k & 0xff);
                    if((abyte0[i1] & k1) != 0)
                        code[k * 2] |= 0x8000;
                    else
                        code[k * 2] &= 0xffff7fff;
                }

            }
        }

        public void addDestruction(byte abyte0[])
        {
            int i = code.length / 2;
            if(actor != null && actor[0] != null)
            {
                for(int j = 0; j < i; j++)
                {
                    int l = j >> 8;
                    int j1 = 1 << (j & 0xff);
                    if((abyte0[l] & j1) != 0)
                        actor[j].setDiedFlag(true);
                }

            } else
            {
                for(int k = 0; k < i; k++)
                {
                    int i1 = k >> 8;
                    int k1 = 1 << (k & 0xff);
                    if((abyte0[i1] & k1) != 0)
                        code[k * 2] |= 0x8000;
                }

            }
        }

        public float getDestruction()
        {
            int i = code.length / 2;
            int j = 0;
            if(actor != null && actor[0] != null)
            {
                for(int k = 0; k < i; k++)
                    if(!com.maddox.il2.engine.Actor.isAlive(actor[k]))
                        j++;

            } else
            {
                for(int l = 0; l < i; l++)
                    if((code[l * 2] & 0x8000) != 0)
                        j++;

            }
            return (float)j / (float)i;
        }

        public void setDestruction(float f)
        {
            int i = code.length / 2;
            int j = (int)(f * (float)i + 0.5F);
            if(j > i)
                j = i;
            if(actor != null && actor[0] != null)
            {
                for(int k = 0; k < i; k++)
                    if(j > 0)
                    {
                        if(com.maddox.il2.engine.Actor.isAlive(actor[k]))
                            actor[k].setDiedFlag(true);
                        j--;
                    } else
                    if(!com.maddox.il2.engine.Actor.isAlive(actor[k]))
                        actor[k].setDiedFlag(false);

            } else
            {
                for(int l = 0; l < i; l++)
                    if(j > 0)
                    {
                        code[l * 2] |= 0x8000;
                        j--;
                    } else
                    {
                        code[l * 2] &= 0xffff7fff;
                    }

            }
        }

        public boolean isDestructed()
        {
            int i = code.length / 2;
            if(actor != null && actor[0] != null)
            {
                for(int j = 0; j < i; j++)
                    if(!actor[j].isAlive())
                        return true;

            } else
            {
                for(int k = 0; k < i; k++)
                    if((code[k * 2] & 0x8000) != 0)
                        return true;

            }
            return false;
        }

        public void restoreAll()
        {
            int i = code.length / 2;
            if(actor != null && actor[0] != null)
            {
                for(int j = 0; j < i; j++)
                    if(!actor[j].isAlive())
                        actor[j].setDiedFlag(false);

            } else
            {
                for(int k = 0; k < i; k++)
                    code[k * 2] &= 0xffff7fff;

            }
        }

        public void restoreAll(float f, float f1, float f2, float f3, float f4)
        {
            int i = code.length / 2;
            if(actor != null && actor[0] != null)
            {
                for(int j = 0; j < i; j++)
                    if(!actor[j].isAlive())
                    {
                        com.maddox.JGP.Point3d point3d = actor[j].pos.getAbsPoint();
                        if((point3d.x - (double)f2) * (point3d.x - (double)f2) + (point3d.y - (double)f3) * (point3d.y - (double)f3) <= (double)f4)
                            actor[j].setDiedFlag(false);
                    }

            } else
            {
                for(int k = 0; k < i; k++)
                {
                    if((code[k * 2] & 0x8000) == 0)
                        continue;
                    int l = code[k * 2 + 0];
                    int i1 = code[k * 2 + 1];
                    int j1 = (short)(i1 & 0xffff);
                    int k1 = (short)(i1 >> 16 & 0xffff);
                    float f5 = ((float)j1 * 200F) / 32000F + f;
                    float f6 = ((float)k1 * 200F) / 32000F + f1;
                    if((f5 - f2) * (f5 - f2) + (f6 - f3) * (f6 - f3) <= f4)
                        code[k * 2] &= 0xffff7fff;
                }

            }
        }

        boolean bExistPlate;
        int code[];
        public com.maddox.il2.engine.Actor actor[];

        public Block()
        {
            bExistPlate = false;
        }
    }


    public static com.maddox.rts.SectFile getShipsFile()
    {
        if(ships == null)
        {
            ships = new SectFile("com/maddox/il2/objects/ships.ini");
            ships.createIndexes();
        }
        return ships;
    }

    public static com.maddox.rts.SectFile getTechnicsFile()
    {
        if(technics == null)
        {
            technics = new SectFile("com/maddox/il2/objects/technics.ini");
            technics.createIndexes();
        }
        return technics;
    }

    public static com.maddox.rts.SectFile getBuildingsFile()
    {
        if(buildings == null)
        {
            buildings = new SectFile("com/maddox/il2/objects/static.ini");
            buildings.createIndexes();
        }
        return buildings;
    }

    public static int[] readBridgesEndPoints(java.lang.String s)
    {
        int ai[] = null;
        try
        {
            java.io.DataInputStream datainputstream = new DataInputStream(new SFSInputStream(s));
            int i = datainputstream.readInt();
            if(i == -65535)
                i = datainputstream.readInt();
            ai = new int[i * 4];
            for(int j = 0; j < i; j++)
            {
                int k = datainputstream.readInt();
                int l = datainputstream.readInt();
                int i1 = datainputstream.readInt();
                int j1 = datainputstream.readInt();
                int k1 = datainputstream.readInt();
                float f = datainputstream.readFloat();
                ai[j * 4 + 0] = k;
                ai[j * 4 + 1] = l;
                ai[j * 4 + 2] = i1;
                ai[j * 4 + 3] = j1;
            }

            datainputstream.close();
        }
        catch(java.lang.Exception exception)
        {
            ai = null;
            java.lang.String s1 = "Bridges data in '" + s + "' DAMAGED: " + exception.getMessage();
            java.lang.System.out.println(s1);
        }
        return ai;
    }

    public static void load(java.lang.String s, java.util.List list)
    {
        com.maddox.il2.ai.World.cur().statics._load(s, list);
    }

    private void _load(java.lang.String s, java.util.List list)
    {
        try
        {
            java.io.DataInputStream datainputstream = new DataInputStream(new SFSInputStream(s));
            int i = datainputstream.readInt();
            if(i != -65535)
                throw new Exception("Not supported sersion");
            java.lang.System.out.println("Load bridges");
            int j = datainputstream.readInt();
            for(int l = 0; l < j; l++)
            {
                int i1 = datainputstream.readInt();
                int l1 = datainputstream.readInt();
                int k2 = datainputstream.readInt();
                int k4 = datainputstream.readInt();
                int i6 = datainputstream.readInt();
                float f2 = datainputstream.readFloat();
                com.maddox.il2.objects.bridges.Bridge bridge = new Bridge(l, i6, i1, l1, k2, k4, f2);
                if(list != null)
                    list.add(bridge);
            }

            java.lang.System.out.println("Load static objects");
            java.util.ArrayList arraylist = new ArrayList();
            j = datainputstream.readInt();
            spawns = null;
            if(j > 0)
            {
                int j1 = -1;
                com.maddox.il2.engine.ActorSpawnArg actorspawnarg = new ActorSpawnArg();
                spawns = new com.maddox.il2.engine.ActorSpawn[j];
                for(int l2 = 0; l2 < j; l2++)
                {
                    java.lang.String s3 = datainputstream.readUTF();
                    if("com.maddox.il2.objects.air.Runaway".equals(s3))
                        j1 = l2;
                    spawns[l2] = (com.maddox.il2.engine.ActorSpawn)com.maddox.rts.Spawn.get_WithSoftClass(s3);
                }

                j = datainputstream.readInt();
                do
                {
                    if(j-- <= 0)
                        break;
                    int i3 = datainputstream.readInt();
                    float f = datainputstream.readFloat();
                    float f1 = datainputstream.readFloat();
                    float f3 = datainputstream.readFloat();
                    _loc.set(f, f1, 0.0D, f3, 0.0F, 0.0F);
                    if(j1 == i3)
                    {
                        com.maddox.il2.engine.Loc loc = new Loc(_loc);
                        arraylist.add(loc);
                    }
                    if(i3 < spawns.length && spawns[i3] != null)
                    {
                        actorspawnarg.clear();
                        actorspawnarg.point = _loc.getPoint();
                        actorspawnarg.orient = _loc.getOrient();
                        try
                        {
                            com.maddox.il2.engine.Actor actor1 = spawns[i3].actorSpawn(actorspawnarg);
                            if(actor1 instanceof com.maddox.il2.engine.ActorLandMesh)
                            {
                                com.maddox.il2.engine.ActorLandMesh actorlandmesh = (com.maddox.il2.engine.ActorLandMesh)actor1;
                                actorlandmesh.mesh().setPos(actorlandmesh.pos.getAbs());
                                com.maddox.il2.engine.Landscape.meshAdd(actorlandmesh);
                            }
                        }
                        catch(java.lang.Exception exception2)
                        {
                            java.lang.System.out.println(exception2.getMessage());
                            exception2.printStackTrace();
                        }
                    }
                } while(true);
            }
            j = datainputstream.readInt();
            spawns = null;
            int k1 = -1;
            if(j > 0)
            {
                spawns = new com.maddox.il2.engine.ActorSpawn[j];
                spawnIsPlate = new boolean[j];
                uniformMaxDist = new float[j];
                cacheActors = new java.util.ArrayList[j];
                _loc.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
                for(int i2 = 0; i2 < j;)
                {
                    java.lang.String s2 = datainputstream.readUTF();
                    if(s2.indexOf("TreeLine") >= 0)
                        k1 = i2;
                    spawns[i2] = (com.maddox.il2.engine.ActorSpawn)com.maddox.rts.Spawn.get_WithSoftClass(s2);
                    cacheActors[i2] = new ArrayList();
                    try
                    {
                        _spawnArg.point = _loc.getPoint();
                        _spawnArg.orient = _loc.getOrient();
                        com.maddox.il2.engine.Actor actor = spawns[i2].actorSpawn(_spawnArg);
                        cachePut(i2, actor);
                        spawnIsPlate[i2] = actor instanceof com.maddox.il2.objects.buildings.Plate;
                        if(actor instanceof com.maddox.il2.engine.ActorMesh)
                        {
                            uniformMaxDist[i2] = ((com.maddox.il2.engine.ActorMesh)actor).mesh().getUniformMaxDist();
                            actor.draw.uniformMaxDist = uniformMaxDist[i2];
                        } else
                        {
                            uniformMaxDist[i2] = 0.0F;
                        }
                        continue;
                    }
                    catch(java.lang.Exception exception1)
                    {
                        java.lang.System.out.println(exception1.getMessage());
                        exception1.printStackTrace();
                        i2++;
                    }
                }

                int j2 = datainputstream.readInt();
                allBlocks = new HashMapInt((int)((float)j2 / 0.75F));
                do
                {
                    if(j2-- <= 0)
                        break;
                    int j3 = datainputstream.readInt();
                    int l4 = j3 & 0xffff;
                    int j6 = j3 >> 16 & 0xffff;
                    int k7 = datainputstream.readInt();
                    com.maddox.il2.objects.Block block = new Block();
                    block.code = new int[k7 * 2];
                    float f4 = 0.0F;
                    for(int j9 = 0; j9 < k7; j9++)
                    {
                        int l9 = datainputstream.readInt();
                        if((l9 & 0x7fff) >= spawns.length)
                            l9 = 0;
                        block.code[j9 * 2 + 0] = l9;
                        block.code[j9 * 2 + 1] = datainputstream.readInt();
                        if(spawnIsPlate[l9 & 0x7fff])
                            block.bExistPlate = true;
                        float f5 = uniformMaxDist[l9 & 0x7fff];
                        if(f4 < f5)
                            f4 = f5;
                    }

                    allBlocks.put(key(j6, l4), block);
                    com.maddox.il2.engine.Engine.drawEnv().setUniformMaxDist(l4, j6, f4);
                    if(block.bExistPlate)
                    {
                        bCheckPlate = false;
                        _updateX[0] = l4;
                        _updateY[0] = j6;
                        _msgDreamGlobal(true, 1, 0, _updateX, _updateY);
                        bCheckPlate = true;
                    }
                } while(true);
                if(com.maddox.il2.engine.Config.isUSE_RENDER())
                {
                    int k3 = com.maddox.il2.engine.Landscape.getSizeXpix();
                    int i5 = com.maddox.il2.engine.Landscape.getSizeYpix();
                    int ai[] = new int[k3 + 31 >> 5];
                    for(int l7 = 0; l7 < i5; l7++)
                    {
                        for(int j8 = 0; j8 < ai.length; j8++)
                            ai[j8] = 0;

                        int k8 = 0;
                        for(int i9 = 0; i9 < k3;)
                        {
                            if(allBlocks.containsKey(key(l7, i9)))
                                ai[k8 >> 5] |= 1 << (k8 & 0x1f);
                            i9++;
                            k8++;
                        }

                        com.maddox.il2.engine.Landscape.MarkStaticActorsCells(0, l7, k3, 1, 200, ai);
                    }

                    k3 = com.maddox.il2.engine.Landscape.getSizeXpix();
                    i5 = com.maddox.il2.engine.Landscape.getSizeYpix();
                    int k6 = 0;
                    for(int i8 = 0; i8 < i5; i8++)
                    {
                        for(int l8 = 0; l8 < k3; l8++)
                        {
                            if(!allBlocks.containsKey(key(i8, l8)))
                                continue;
                            com.maddox.il2.objects.Block block1 = (com.maddox.il2.objects.Block)allBlocks.get(key(i8, l8));
                            com.maddox.il2.engine.Landscape.MarkActorCellWithTrees(k1, l8, i8, block1.code, block1.code.length);
                            for(int k9 = 0; k9 < block1.code.length; k9 += 2)
                                if((block1.code[k9] & 0x7fff) == k1)
                                    k6++;

                        }

                    }

                }
            }
            com.maddox.il2.ai.air.Airdrome airdrome = new Airdrome();
            if(datainputstream.available() > 0)
            {
                int k = datainputstream.readInt();
                airdrome.runw = new com.maddox.il2.ai.air.Point_Runaway[k][];
                for(int l3 = 0; l3 < k; l3++)
                {
                    int j5 = datainputstream.readInt();
                    airdrome.runw[l3] = new com.maddox.il2.ai.air.Point_Runaway[j5];
                    for(int l6 = 0; l6 < j5; l6++)
                        airdrome.runw[l3][l6] = new Point_Runaway(datainputstream.readFloat(), datainputstream.readFloat());

                }

                k = datainputstream.readInt();
                airdrome.taxi = new com.maddox.il2.ai.air.Point_Taxi[k][];
                for(int i4 = 0; i4 < k; i4++)
                {
                    int k5 = datainputstream.readInt();
                    airdrome.taxi[i4] = new com.maddox.il2.ai.air.Point_Taxi[k5];
                    for(int i7 = 0; i7 < k5; i7++)
                        airdrome.taxi[i4][i7] = new Point_Taxi(datainputstream.readFloat(), datainputstream.readFloat());

                }

                k = datainputstream.readInt();
                airdrome.stay = new com.maddox.il2.ai.air.Point_Stay[k][];
                for(int j4 = 0; j4 < k; j4++)
                {
                    int l5 = datainputstream.readInt();
                    airdrome.stay[j4] = new com.maddox.il2.ai.air.Point_Stay[l5];
                    for(int j7 = 0; j7 < l5; j7++)
                        airdrome.stay[j4][j7] = new Point_Stay(datainputstream.readFloat(), datainputstream.readFloat());

                }

                airdrome.stayHold = new boolean[airdrome.stay.length];
            }
            com.maddox.il2.ai.World.cur().airdrome = airdrome;
            com.maddox.il2.ai.AirportStatic.make(arraylist, airdrome.runw, airdrome.taxi, airdrome.stay);
            datainputstream.close();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.String s1 = "Actors load from '" + s + "' FAILED: " + exception.getMessage();
            java.lang.System.out.println(s1);
            exception.printStackTrace();
        }
    }

    public void restoreAllBridges()
    {
        int i = 0;
        do
        {
            com.maddox.il2.objects.bridges.LongBridge longbridge = com.maddox.il2.objects.bridges.LongBridge.getByIdx(i);
            if(longbridge != null)
            {
                if(!longbridge.isAlive())
                    longbridge.BeLive();
                i++;
            } else
            {
                return;
            }
        } while(true);
    }

    public void saveStateBridges(com.maddox.rts.SectFile sectfile, int i)
    {
        int j = 0;
        do
        {
            com.maddox.il2.objects.bridges.LongBridge longbridge = com.maddox.il2.objects.bridges.LongBridge.getByIdx(j);
            if(longbridge != null)
            {
                if(!longbridge.isAlive())
                {
                    int k = longbridge.NumStateBits();
                    java.util.BitSet bitset = longbridge.GetStateOfSegments();
                    sectfile.lineAdd(i, com.maddox.util.AsciiBitSet.save(j), com.maddox.util.AsciiBitSet.save(bitset, k));
                }
                j++;
            } else
            {
                return;
            }
        } while(true);
    }

    public void loadStateBridges(com.maddox.rts.SectFile sectfile, boolean flag)
    {
        int i = sectfile.sectionIndex(flag ? "AddBridge" : "Bridge");
        if(i < 0)
        {
            return;
        } else
        {
            loadStateBridges(sectfile, i, flag);
            return;
        }
    }

    public void loadStateBridges(com.maddox.rts.SectFile sectfile, int i, boolean flag)
    {
        boolean flag1 = false;
        if(!flag && com.maddox.il2.game.Mission.isDogfight())
        {
            flag1 = true;
            allBridge0 = new HashMapInt();
        }
        int j = sectfile.vars(i);
        java.util.BitSet bitset = new BitSet();
        for(int k = 0; k < j; k++)
        {
            java.lang.String s = sectfile.var(i, k);
            java.lang.String s1 = sectfile.value(i, k);
            int l = com.maddox.util.AsciiBitSet.load(s);
            com.maddox.il2.objects.bridges.LongBridge longbridge = com.maddox.il2.objects.bridges.LongBridge.getByIdx(l);
            if(longbridge == null)
                continue;
            com.maddox.util.AsciiBitSet.load(s1, bitset, longbridge.NumStateBits());
            if(flag)
                bitset.or(longbridge.GetStateOfSegments());
            longbridge.SetStateOfSegments(bitset);
            if(flag1)
                allBridge0.put(l, bitset.clone());
        }

    }

    public void restoreAllHouses()
    {
        for(com.maddox.util.HashMapIntEntry hashmapintentry = allBlocks.nextEntry(null); hashmapintentry != null; hashmapintentry = allBlocks.nextEntry(hashmapintentry))
        {
            com.maddox.il2.objects.Block block = (com.maddox.il2.objects.Block)hashmapintentry.getValue();
            if(block.isDestructed())
                block.restoreAll();
        }

    }

    public void saveStateHouses(com.maddox.rts.SectFile sectfile, int i)
    {
        for(com.maddox.util.HashMapIntEntry hashmapintentry = allBlocks.nextEntry(null); hashmapintentry != null; hashmapintentry = allBlocks.nextEntry(hashmapintentry))
        {
            com.maddox.il2.objects.Block block = (com.maddox.il2.objects.Block)hashmapintentry.getValue();
            if(block.isDestructed())
            {
                int j = hashmapintentry.getKey();
                sectfile.lineAdd(i, com.maddox.util.AsciiBitSet.save(j), com.maddox.util.AsciiBitSet.save(block.getDestruction(null), block.amountObjects()));
            }
        }

    }

    public void loadStateHouses(com.maddox.rts.SectFile sectfile, boolean flag)
    {
        int i = sectfile.sectionIndex(flag ? "AddHouse" : "House");
        if(i < 0)
        {
            return;
        } else
        {
            loadStateHouses(sectfile, i, flag);
            return;
        }
    }

    public void loadStateHouses(com.maddox.rts.SectFile sectfile, int i, boolean flag)
    {
        boolean flag1 = false;
        if(!flag && com.maddox.il2.game.Mission.isDogfight())
        {
            flag1 = true;
            allStates0 = new HashMapInt();
        }
        int j = sectfile.vars(i);
        byte abyte0[] = new byte[32];
        for(int k = 0; k < j; k++)
        {
            java.lang.String s = sectfile.var(i, k);
            java.lang.String s1 = sectfile.value(i, k);
            int l = com.maddox.util.AsciiBitSet.load(s);
            com.maddox.il2.objects.Block block = (com.maddox.il2.objects.Block)allBlocks.get(l);
            if(block == null)
                continue;
            abyte0 = com.maddox.util.AsciiBitSet.load(s1, abyte0, block.amountObjects());
            if(flag)
            {
                block.addDestruction(abyte0);
                continue;
            }
            block.setDestruction(abyte0);
            if(flag1)
            {
                int i1 = (block.amountObjects() + 7) / 8;
                byte abyte1[] = new byte[i1];
                java.lang.System.arraycopy(abyte0, 0, abyte1, 0, i1);
                allStates0.put(l, abyte1);
            }
        }

    }

    public void restoreAllHouses(float f, float f1, float f2)
    {
        int i = (int)((f - f2) / 200F) - 1;
        int j = (int)((f + f2) / 200F) + 2;
        int k = (int)((f1 - f2) / 200F) - 1;
        int l = (int)((f1 + f2) / 200F) + 2;
        com.maddox.util.HashMapIntEntry hashmapintentry = allBlocks.nextEntry(null);
        float f3 = f2 * f2;
        for(; hashmapintentry != null; hashmapintentry = allBlocks.nextEntry(hashmapintentry))
        {
            com.maddox.il2.objects.Block block = (com.maddox.il2.objects.Block)hashmapintentry.getValue();
            int i1 = hashmapintentry.getKey();
            int j1 = key2x(i1);
            int k1 = key2y(i1);
            if(j1 >= i && j1 <= j && k1 >= k && k1 <= l)
                block.restoreAll((float)j1 * 200F, (float)k1 * 200F, f, f1, f3);
        }

    }

    public void netBridgeSync(com.maddox.rts.NetChannel netchannel)
    {
        int i = 0;
        do
        {
            com.maddox.il2.objects.bridges.LongBridge longbridge = com.maddox.il2.objects.bridges.LongBridge.getByIdx(i);
            if(longbridge != null)
            {
                if(!longbridge.isAlive())
                {
                    int j = longbridge.NumStateBits();
                    java.util.BitSet bitset = longbridge.GetStateOfSegments();
                    java.util.BitSet bitset1 = null;
                    if(allBridge0 != null)
                        bitset1 = (java.util.BitSet)allBridge0.get(i);
                    if(bitset1 == null || !bitset1.equals(bitset))
                        try
                        {
                            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                            netmsgguaranted.writeByte(28);
                            netmsgguaranted.writeShort(i);
                            int k = (j + 7) / 8;
                            byte abyte0[] = new byte[k];
                            for(int l = 0; l < j; l++)
                                if(bitset.get(l))
                                {
                                    int i1 = l / 8;
                                    int j1 = l % 8;
                                    abyte0[i1] |= 1 << j1;
                                }

                            netmsgguaranted.write(abyte0);
                            com.maddox.rts.NetEnv.host().postTo(netchannel, netmsgguaranted);
                        }
                        catch(java.lang.Exception exception) { }
                }
                i++;
            } else
            {
                return;
            }
        } while(true);
    }

    public void netMsgBridgeSync(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        int i = netmsginput.readUnsignedShort();
        com.maddox.il2.objects.bridges.LongBridge longbridge = com.maddox.il2.objects.bridges.LongBridge.getByIdx(i);
        if(longbridge == null)
            return;
        byte abyte0[] = new byte[netmsginput.available()];
        netmsginput.read(abyte0);
        int j = longbridge.NumStateBits();
        java.util.BitSet bitset = longbridge.GetStateOfSegments();
        for(int k = 0; k < j; k++)
        {
            int l = k / 8;
            int i1 = k % 8;
            if((abyte0[l] & 1 << i1) != 0)
                bitset.set(k);
            else
                bitset.clear(k);
        }

        longbridge.SetStateOfSegments(bitset);
    }

    public boolean onBridgeDied(int i, int j, int k, com.maddox.il2.engine.Actor actor)
    {
        if(com.maddox.il2.game.Mission.isServer())
        {
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(27);
                netmsgguaranted.writeShort(i);
                netmsgguaranted.writeShort(j);
                netmsgguaranted.writeByte(k);
                com.maddox.il2.engine.ActorNet actornet = null;
                if(com.maddox.il2.engine.Actor.isValid(actor))
                    actornet = actor.net;
                netmsgguaranted.writeNetObj(actornet);
                com.maddox.rts.NetEnv.host().post(netmsgguaranted);
            }
            catch(java.lang.Exception exception) { }
            return true;
        }
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted();
            netmsgguaranted1.writeByte(26);
            netmsgguaranted1.writeShort(i);
            netmsgguaranted1.writeShort(j);
            netmsgguaranted1.writeByte(k);
            com.maddox.il2.engine.ActorNet actornet1 = null;
            if(com.maddox.il2.engine.Actor.isValid(actor))
                actornet1 = actor.net;
            netmsgguaranted1.writeNetObj(actornet1);
            com.maddox.rts.NetEnv.host().postTo(com.maddox.il2.game.Main.cur().netServerParams.masterChannel(), netmsgguaranted1);
        }
        catch(java.lang.Exception exception1) { }
        return false;
    }

    public void netMsgBridgeRDie(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        int i = netmsginput.readUnsignedShort();
        int j = netmsginput.readUnsignedShort();
        byte byte0 = netmsginput.readByte();
        com.maddox.il2.engine.Actor actor = null;
        com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
        if(netobj != null)
            actor = (com.maddox.il2.engine.Actor)netobj.superObj();
        if(com.maddox.il2.game.Mission.isServer())
        {
            com.maddox.il2.objects.bridges.BridgeSegment bridgesegment = com.maddox.il2.objects.bridges.BridgeSegment.getByIdx(i, j);
            bridgesegment.netForcePartDestroing(byte0, actor);
        } else
        {
            onBridgeDied(i, j, byte0, actor);
        }
    }

    public void netMsgBridgeDie(com.maddox.rts.NetObj netobj, com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        int i = netmsginput.readUnsignedShort();
        int j = netmsginput.readUnsignedShort();
        byte byte0 = netmsginput.readByte();
        com.maddox.il2.engine.Actor actor = null;
        com.maddox.rts.NetObj netobj1 = netmsginput.readNetObj();
        if(netobj1 != null)
            actor = (com.maddox.il2.engine.Actor)netobj1.superObj();
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(27);
            netmsgguaranted.writeShort(i);
            netmsgguaranted.writeShort(j);
            netmsgguaranted.writeByte(byte0);
            com.maddox.il2.engine.ActorNet actornet = null;
            if(com.maddox.il2.engine.Actor.isValid(actor))
                actornet = actor.net;
            netmsgguaranted.writeNetObj(actornet);
            netobj.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception) { }
        com.maddox.il2.objects.bridges.BridgeSegment bridgesegment = com.maddox.il2.objects.bridges.BridgeSegment.getByIdx(i, j);
        bridgesegment.netForcePartDestroing(byte0, actor);
    }

    public void netHouseSync(com.maddox.rts.NetChannel netchannel)
    {
        for(com.maddox.util.HashMapIntEntry hashmapintentry = allBlocks.nextEntry(null); hashmapintentry != null; hashmapintentry = allBlocks.nextEntry(hashmapintentry))
        {
            com.maddox.il2.objects.Block block = (com.maddox.il2.objects.Block)hashmapintentry.getValue();
            int i = hashmapintentry.getKey();
            byte abyte0[] = null;
            if(allStates0 != null)
                abyte0 = (byte[])(byte[])allStates0.get(i);
            if(abyte0 == null)
            {
                if(block.isDestructed())
                    putMsgHouseSync(netchannel, i, block);
                continue;
            }
            if(!block.isEquals(abyte0))
                putMsgHouseSync(netchannel, i, block);
        }

    }

    private void putMsgHouseSync(com.maddox.rts.NetChannel netchannel, int i, com.maddox.il2.objects.Block block)
    {
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(25);
            netmsgguaranted.writeInt(i);
            netmsgguaranted.write(block.getDestruction(null));
            com.maddox.rts.NetEnv.host().postTo(netchannel, netmsgguaranted);
        }
        catch(java.lang.Exception exception) { }
    }

    public void netMsgHouseSync(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        int i = netmsginput.readInt();
        byte abyte0[] = new byte[netmsginput.available()];
        netmsginput.read(abyte0);
        com.maddox.il2.objects.Block block = (com.maddox.il2.objects.Block)allBlocks.get(i);
        if(block == null)
        {
            return;
        } else
        {
            block.setDestruction(abyte0);
            return;
        }
    }

    public void netMsgHouseDie(com.maddox.rts.NetObj netobj, com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        com.maddox.il2.engine.Actor actor = null;
        com.maddox.rts.NetObj netobj1 = netmsginput.readNetObj();
        if(netobj1 != null)
            actor = (com.maddox.il2.engine.Actor)netobj1.superObj();
        int i = netmsginput.readInt();
        int j = netmsginput.readUnsignedShort();
        com.maddox.il2.objects.Block block = (com.maddox.il2.objects.Block)allBlocks.get(i);
        if(block == null)
            return;
        if(j >= block.code.length / 2)
            return;
        if(block.actor != null && block.actor[0] != null)
        {
            if(com.maddox.il2.engine.Actor.isAlive(block.actor[j]) && (block.actor[j] instanceof com.maddox.il2.objects.buildings.House))
            {
                ((com.maddox.il2.objects.buildings.House)block.actor[j]).doDieShow();
                com.maddox.il2.ai.World.onActorDied(block.actor[j], actor);
                replicateHouseDie(netobj, netmsginput.channel(), netobj1, i, j);
            }
        } else
        if((block.code[j * 2] & 0x8000) == 0)
        {
            block.code[j * 2] |= 0x8000;
            replicateHouseDie(netobj, netmsginput.channel(), netobj1, i, j);
        }
    }

    public void onHouseDied(com.maddox.il2.objects.buildings.House house, com.maddox.il2.engine.Actor actor)
    {
        com.maddox.JGP.Point3d point3d = house.pos.getAbsPoint();
        int i = (int)(point3d.y / 200D);
        int j = (int)(point3d.x / 200D);
        int k = key(i, j);
        com.maddox.il2.objects.Block block = (com.maddox.il2.objects.Block)allBlocks.get(k);
        if(block == null)
            return;
        if(block.actor == null)
            return;
        int l = 0;
        int i1;
        for(i1 = block.actor.length; l < i1 && block.actor[l] != house; l++);
        if(l >= i1)
            return;
        com.maddox.il2.engine.ActorNet actornet = null;
        if(com.maddox.il2.engine.Actor.isValid(actor))
            actornet = actor.net;
        try
        {
            replicateHouseDie(com.maddox.rts.NetEnv.host(), null, actornet, k, l);
        }
        catch(java.lang.Exception exception) { }
    }

    private void replicateHouseDie(com.maddox.rts.NetObj netobj, com.maddox.rts.NetChannel netchannel, com.maddox.rts.NetObj netobj1, int i, int j)
        throws java.io.IOException
    {
        int k = netobj.countMirrors();
        if(netobj.isMirror())
            k++;
        if(netchannel != null)
            k--;
        if(k <= 0)
        {
            return;
        } else
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(24);
            netmsgguaranted.writeNetObj(netobj1);
            netmsgguaranted.writeInt(i);
            netmsgguaranted.writeShort(j);
            netobj.postExclude(netchannel, netmsgguaranted);
            return;
        }
    }

    public com.maddox.util.HashMapInt allBlocks()
    {
        return allBlocks;
    }

    public int key(int i, int j)
    {
        return j & 0xffff | i << 16;
    }

    public int key2x(int i)
    {
        return i & 0xffff;
    }

    public int key2y(int i)
    {
        return i >> 16 & 0xffff;
    }

    public void updateBlock(int i, int j)
    {
        com.maddox.il2.objects.Block block = (com.maddox.il2.objects.Block)allBlocks.get(key(j, i));
        if(block != null && block.actor != null)
        {
            _updateX[0] = i;
            _updateY[0] = j;
            _msgDreamGlobal(false, 1, 0, _updateX, _updateY);
            _msgDreamGlobal(true, 1, 0, _updateX, _updateY);
        }
    }

    private com.maddox.il2.engine.Actor cacheGet(int i)
    {
        java.util.ArrayList arraylist = cacheActors[i];
        int j = arraylist.size();
        if(j == 0)
        {
            return null;
        } else
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)arraylist.get(j - 1);
            arraylist.remove(j - 1);
            return actor;
        }
    }

    private void cachePut(int i, com.maddox.il2.engine.Actor actor)
    {
        java.util.ArrayList arraylist = cacheActors[i];
        arraylist.add(actor);
        actor.drawing(false);
        actor.collide(false);
    }

    public static void trim()
    {
    }

    public void msgDreamGlobalTick(int i, int j)
    {
        _msgDreamGlobalTick(false, i, j - 1);
        _msgDreamGlobalTick(true, i, j - 1);
    }

    public void _msgDreamGlobalTick(boolean flag, int i, int j)
    {
        com.maddox.il2.objects.Queue queue = flag ? wQueue : sQueue;
        if(queue.ofs < queue.len)
        {
            int k = i - j;
            int l = (queue.len * k + i / 2) / i;
            if(l > queue.len)
                l = queue.len;
            int i1 = l - queue.ofs;
            if(i1 > 0)
            {
                _msgDreamGlobal(flag, i1, queue.ofs, queue.X, queue.Y);
                queue.ofs = l;
                if(queue.ofs == queue.len)
                    queue.clear();
            }
        }
    }

    public void msgDreamGlobal(boolean flag, int i, int ai[], int ai1[])
    {
        if(allBlocks == null)
            return;
        _msgDreamGlobalTick(flag, 1, 0);
        com.maddox.il2.objects.Queue queue = flag ? wQueue : sQueue;
        for(int j = 0; j < i; j++)
        {
            int k = ai[j];
            int l = ai1[j];
            com.maddox.il2.objects.Block block = (com.maddox.il2.objects.Block)allBlocks.get(key(l, k));
            if(block != null)
                queue.add(k, l);
        }

    }

    private void _msgDreamGlobal(boolean flag, int i, int j, int ai[], int ai1[])
    {
        if(allBlocks == null)
            return;
        if(flag)
        {
            for(int k = 0; k < i; k++)
            {
                int i1 = ai[k + j];
                int k1 = ai1[k + j];
                com.maddox.il2.objects.Block block = (com.maddox.il2.objects.Block)allBlocks.get(key(k1, i1));
                if(block != null && (!bCheckPlate || !block.bExistPlate))
                {
                    float f = (float)i1 * 200F;
                    float f2 = (float)k1 * 200F;
                    int i2 = block.code.length / 2;
                    block.actor = new com.maddox.il2.engine.Actor[i2];
                    for(int k2 = 0; k2 < i2; k2++)
                    {
                        int i3 = block.code[k2 * 2 + 0];
                        int j3 = block.code[k2 * 2 + 1];
                        int k3 = i3 & 0x7fff;
                        int l3 = (short)(i3 >> 16);
                        int i4 = (short)(j3 & 0xffff);
                        int j4 = (short)(j3 >> 16 & 0xffff);
                        float f4 = ((float)l3 * 360F) / 32000F;
                        float f5 = ((float)i4 * 200F) / 32000F + f;
                        float f6 = ((float)j4 * 200F) / 32000F + f2;
                        _loc.set(f5, f6, 0.0D, -f4, 0.0F, 0.0F);
                        com.maddox.il2.engine.Actor actor1 = cacheGet(k3);
                        if(actor1 != null)
                        {
                            actor1.pos.setAbs(_loc);
                            actor1.setDiedFlag((i3 & 0x8000) != 0);
                        } else
                        {
                            _spawnArg.point = _loc.getPoint();
                            _spawnArg.orient = _loc.getOrient();
                            try
                            {
                                actor1 = spawns[k3].actorSpawn(_spawnArg);
                                if((i3 & 0x8000) != 0)
                                    actor1.setDiedFlag(true);
                                if(actor1 instanceof com.maddox.il2.engine.ActorMesh)
                                    actor1.draw.uniformMaxDist = uniformMaxDist[k3];
                            }
                            catch(java.lang.Exception exception)
                            {
                                java.lang.System.out.println(exception.getMessage());
                                exception.printStackTrace();
                            }
                        }
                        block.actor[k2] = actor1;
                    }

                }
            }

        } else
        {
            for(int l = 0; l < i; l++)
            {
                int j1 = ai[l + j];
                int l1 = ai1[l + j];
                com.maddox.il2.objects.Block block1 = (com.maddox.il2.objects.Block)allBlocks.get(key(l1, j1));
                if(block1 == null || block1.actor == null || bCheckPlate && block1.bExistPlate)
                    continue;
                float f1 = (float)j1 * 200F;
                float f3 = (float)l1 * 200F;
                int j2 = block1.code.length / 2;
                for(int l2 = 0; l2 < j2; l2++)
                {
                    com.maddox.il2.engine.Actor actor = block1.actor[l2];
                    block1.actor[l2] = null;
                    if(actor == null)
                        continue;
                    if(actor.getDiedFlag())
                        block1.code[l2 * 2 + 0] |= 0x8000;
                    cachePut(block1.code[l2 * 2 + 0] & 0x7fff, actor);
                }

                block1.actor = null;
            }

        }
    }

    public void resetGame()
    {
        wQueue.clear();
        sQueue.clear();
        if(allBlocks == null)
            return;
        java.util.ArrayList arraylist = new ArrayList();
        for(com.maddox.util.HashMapIntEntry hashmapintentry = allBlocks.nextEntry(null); hashmapintentry != null; hashmapintentry = allBlocks.nextEntry(hashmapintentry))
        {
            com.maddox.il2.objects.Block block = (com.maddox.il2.objects.Block)hashmapintentry.getValue();
            if(block.actor == null)
                continue;
            for(int j = 0; j < block.actor.length; j++)
            {
                arraylist.add(block.actor[j]);
                block.actor[j] = null;
            }

        }

        com.maddox.il2.engine.Engine.destroyListGameActors(arraylist);
        for(int i = 0; i < cacheActors.length; i++)
        {
            java.util.ArrayList arraylist1 = cacheActors[i];
            com.maddox.il2.engine.Engine.destroyListGameActors(arraylist1);
        }

        allBlocks = null;
        allStates0 = null;
        allBridge0 = null;
        cacheActors = null;
        spawns = null;
        bridges.clear();
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public Statics()
    {
        bridges = new ArrayList();
        _updateX = new int[1];
        _updateY = new int[1];
        wQueue = new Queue();
        sQueue = new Queue();
        bCheckPlate = true;
        _loc = new Loc();
        _spawnArg = new ActorSpawnArg();
        flags |= 0x4000;
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    private static com.maddox.rts.SectFile ships = null;
    private static com.maddox.rts.SectFile technics = null;
    private static com.maddox.rts.SectFile buildings = null;
    public java.util.ArrayList bridges;
    private com.maddox.il2.engine.ActorSpawn spawns[];
    private boolean spawnIsPlate[];
    private float uniformMaxDist[];
    private java.util.ArrayList cacheActors[];
    private com.maddox.util.HashMapInt allBlocks;
    private com.maddox.util.HashMapInt allBridge0;
    private com.maddox.util.HashMapInt allStates0;
    private int _updateX[];
    private int _updateY[];
    private com.maddox.il2.objects.Queue wQueue;
    private com.maddox.il2.objects.Queue sQueue;
    private boolean bCheckPlate;
    com.maddox.il2.engine.Loc _loc;
    com.maddox.il2.engine.ActorSpawnArg _spawnArg;

}

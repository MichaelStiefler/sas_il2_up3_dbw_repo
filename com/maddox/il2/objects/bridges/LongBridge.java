// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LongBridge.java

package com.maddox.il2.objects.bridges;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.ChiefGround;
import com.maddox.il2.ai.ground.RoadSegment;
import com.maddox.il2.ai.ground.UnitInterface;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosStatic;
import com.maddox.il2.engine.Camera;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.LandConf;
import com.maddox.il2.engine.LandPlate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MeshShared;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.trains.Train;
import com.maddox.rts.Message;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.BitSet;

// Referenced classes of package com.maddox.il2.objects.bridges:
//            BridgeSegment, BridgeRoad

public class LongBridge extends com.maddox.il2.engine.Actor
    implements com.maddox.il2.engine.LandPlate
{
    private class LongBridgeDraw extends com.maddox.il2.engine.ActorDraw
    {

        public int preRender(com.maddox.il2.engine.Actor actor)
        {
            com.maddox.JGP.Point3d point3d = new Point3d();
            pos.getRender(point3d);
            float f = (float)java.lang.Math.sqrt((float)((endX - begX) * (endX - begX) + (endY - begY) * (endY - begY)));
            f *= 0.5F;
            f += 1.988F;
            f *= 200F;
            f += 500F;
            if(!com.maddox.il2.engine.Render.currentCamera().isSphereVisible(point3d, f))
                return 0;
            else
                return mat.preRender();
        }

        public void render(com.maddox.il2.engine.Actor actor)
        {
            char c;
            switch(type)
            {
            case 0: // '\0'
                c = '\200';
                break;

            case 1: // '\001'
                c = ' ';
                break;

            default:
                c = '@';
                break;
            }
            com.maddox.JGP.Point3d point3d = ComputeSegmentPos3d(0);
            com.maddox.JGP.Point3d point3d1 = ComputeSegmentPos3d(1 + 2 * nMidCells);
            float f = 284F;
            float f1 = f * 0.25F + f * 1.4F;
            f1 += 500F;
            if(com.maddox.il2.engine.Render.currentCamera().isSphereVisible(point3d, f1))
            {
                com.maddox.il2.ai.World.cur();
                com.maddox.il2.ai.World.land().renderBridgeRoad(mat, c, begX, begY, incX, incY, offsetKoef);
            }
            if(com.maddox.il2.engine.Render.currentCamera().isSphereVisible(point3d1, f1))
            {
                com.maddox.il2.ai.World.cur();
                com.maddox.il2.ai.World.land().renderBridgeRoad(mat, c, endX, endY, -incX, -incY, -offsetKoef);
            }
        }

        private LongBridgeDraw()
        {
        }

    }


    public int type()
    {
        return type;
    }

    public boolean isStaticPos()
    {
        return true;
    }

    public int bridgeIdx()
    {
        return bridgeIdx;
    }

    private static java.lang.String nameOfBridgeByIdx(int i)
    {
        return " Bridge" + i;
    }

    public static com.maddox.il2.objects.bridges.LongBridge getByIdx(int i)
    {
        return (com.maddox.il2.objects.bridges.LongBridge)com.maddox.il2.engine.Actor.getByName(com.maddox.il2.objects.bridges.LongBridge.nameOfBridgeByIdx(i));
    }

    public boolean isUsableFor(com.maddox.il2.engine.Actor actor)
    {
        return supervisor == null || supervisor == actor;
    }

    public void setSupervisor(com.maddox.il2.engine.Actor actor)
    {
        supervisor = actor;
    }

    public void resetSupervisor(com.maddox.il2.engine.Actor actor)
    {
        if(supervisor == actor)
            supervisor = null;
    }

    public float getWidth()
    {
        return width;
    }

    private java.lang.String SegmentMeshName(boolean flag, int i)
    {
        return new String("3do/bridges/" + (type != 0 ? type != 1 ? "rail/" : "country/" : "highway/") + ((dirOct & 1) != 0 ? "long/" : "short/") + (flag ? "mid/" : "end/") + (i != 0 ? i != 3 ? "mono2" : "mono3" : "mono1") + ".sim");
    }

    private static int ComputeOctDirection(int i, int j)
    {
        byte byte0;
        if(i > 0)
            byte0 = j <= 0 ? ((byte) (((byte)(j >= 0 ? 0 : 1)))) : 7;
        else
        if(i < 0)
            byte0 = j <= 0 ? ((byte)(j >= 0 ? 4 : 3)) : 5;
        else
            byte0 = ((byte)(j <= 0 ? 2 : 6));
        return byte0;
    }

    private com.maddox.il2.engine.Orient ComputeSegmentOrient(boolean flag)
    {
        float f = (float)dirOct * 45F;
        if(!flag)
        {
            f += 180F;
            if(f >= 360F)
                f -= 360F;
        }
        com.maddox.il2.engine.Orient orient = new Orient();
        orient.setYPR(f, 0.0F, 0.0F);
        return orient;
    }

    private com.maddox.JGP.Point3d ComputeSegmentPos3d(int i)
    {
        float f;
        float f1;
        boolean flag;
        if(i == 0)
        {
            f = begX;
            f1 = begY;
            flag = true;
        } else
        if(i == 1 + 2 * nMidCells)
        {
            f = begX + incX * (1 + nMidCells);
            f1 = begY + incY * (1 + nMidCells);
            flag = false;
        } else
        {
            f = begX + incX * (i + 1 >> 1);
            f1 = begY + incY * (i + 1 >> 1);
            flag = (i & 1) == 0;
        }
        float f2 = offsetKoef + (flag ? 0.25F : -0.25F);
        float f3 = f + 0.5F + (float)incX * f2;
        float f4 = f1 + 0.5F + (float)incY * f2;
        com.maddox.JGP.Point3d point3d = new Point3d();
        point3d.x = com.maddox.il2.ai.World.land().PIX2WORLDX(f3);
        point3d.y = com.maddox.il2.ai.World.land().PIX2WORLDY(f4);
        point3d.z = 0.0D;
        return point3d;
    }

    private float SegmentLength()
    {
        return 200F * ((dirOct & 1) != 0 ? 0.7071F : 0.5F);
    }

    void ComputeSegmentKeyPoints(int i, com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, com.maddox.JGP.Point3d point3d2, com.maddox.JGP.Point3d point3d3)
    {
        boolean flag = i == 0;
        boolean flag1 = i == 1 + 2 * nMidCells;
        boolean flag2 = !flag && !flag1;
        point3d1.set(ComputeSegmentPos3d(0));
        point3d3.set(ComputeSegmentPos3d(1));
        float f = (float)(point3d3.x - point3d1.x);
        float f1 = (float)(point3d3.y - point3d1.y);
        float f2 = 1.0F / (float)java.lang.Math.sqrt(f * f + f1 * f1);
        point3d.set(ComputeSegmentPos3d(i));
        point3d1.set(point3d);
        point3d3.set(point3d);
        point3d1.sub(0.5D * (double)f, 0.5D * (double)f1, 0.0D);
        point3d3.add(0.5D * (double)f, 0.5D * (double)f1, 0.0D);
        if(flag)
        {
            point3d2.set(point3d1);
            point3d2.add(f * f2 * lengthEnd, f1 * f2 * lengthEnd, 0.0D);
            point3d1.z = 0.0D;
            point3d2.z = heightEnd;
            point3d3.z = height;
        } else
        if(flag1)
        {
            point3d2.set(point3d3);
            point3d2.sub(f * f2 * lengthEnd, f1 * f2 * lengthEnd, 0.0D);
            point3d1.z = height;
            point3d2.z = heightEnd;
            point3d3.z = 0.0D;
        } else
        {
            point3d2.set(point3d);
            point3d1.z = height;
            point3d2.z = height;
            point3d3.z = height;
        }
    }

    void ShowSegmentExplosion(com.maddox.il2.objects.bridges.BridgeSegment bridgesegment, int i, int j)
    {
        com.maddox.JGP.Point3d point3d = new Point3d();
        com.maddox.JGP.Point3d point3d1 = new Point3d();
        com.maddox.JGP.Point3d point3d2 = new Point3d();
        com.maddox.JGP.Point3d point3d3 = new Point3d();
        ComputeSegmentKeyPoints(i, point3d, point3d1, point3d2, point3d3);
        com.maddox.JGP.Point3d point3d4;
        com.maddox.JGP.Point3d point3d5;
        if(j == 0)
        {
            point3d4 = point3d1;
            point3d5 = point3d2;
        } else
        {
            point3d4 = point3d2;
            point3d5 = point3d3;
        }
        com.maddox.il2.objects.effects.Explosions.ExplodeBridge(point3d4, point3d5, width);
    }

    public int NumStateBits()
    {
        return 2 * (1 + nMidCells + 1);
    }

    public java.util.BitSet GetStateOfSegments()
    {
        java.lang.Object aobj[] = getOwnerAttached();
        java.util.BitSet bitset = new BitSet(2 * aobj.length);
        for(int i = 0; i < aobj.length; i++)
        {
            boolean flag = ((com.maddox.il2.objects.bridges.BridgeSegment)aobj[i]).IsDead(0);
            boolean flag1 = ((com.maddox.il2.objects.bridges.BridgeSegment)aobj[i]).IsDead(1);
            if(flag)
                bitset.set(i * 2 + 0);
            if(flag1)
                bitset.set(i * 2 + 1);
        }

        return bitset;
    }

    public void SetStateOfSegments(java.util.BitSet bitset)
    {
        java.lang.Object aobj[] = getOwnerAttached();
        for(int i = 0; i < aobj.length; i++)
        {
            boolean flag = bitset.get(i * 2 + 0);
            boolean flag2 = bitset.get(i * 2 + 1);
            if(flag && i > 0)
                bitset.set((i - 1) * 2 + 1);
            if(flag2 && i < aobj.length - 1)
                bitset.set((i + 1) * 2 + 0);
        }

        boolean flag1 = false;
        for(int j = 0; j < aobj.length; j++)
        {
            boolean flag3 = bitset.get(j * 2 + 0);
            boolean flag4 = bitset.get(j * 2 + 1);
            if(flag3 || flag4)
                flag1 = true;
            ((com.maddox.il2.objects.bridges.BridgeSegment)aobj[j]).ForcePartState(0, !flag3);
            ((com.maddox.il2.objects.bridges.BridgeSegment)aobj[j]).ForcePartState(1, !flag4);
        }

        setDiedFlag(flag1);
    }

    public void BeLive()
    {
        java.util.BitSet bitset = new BitSet(NumStateBits());
        SetStateOfSegments(bitset);
    }

    void SetSegmentDamageState(boolean flag, com.maddox.il2.objects.bridges.BridgeSegment bridgesegment, int i, float f, float f1, com.maddox.il2.engine.Actor actor)
    {
        int j = (f1 > 0.0F ? 0 : 2) + (f > 0.0F ? 0 : 1);
        boolean flag1 = i == 0;
        boolean flag2 = i == 1 + 2 * nMidCells;
        boolean flag3 = !flag1 && !flag2;
        boolean flag4 = flag2;
        if(flag3 && j == 2)
            flag4 = true;
        com.maddox.il2.engine.Orient orient = ComputeSegmentOrient(flag4);
        bridgesegment.pos.setAbs(orient);
        bridgesegment.pos.reset();
        bridgesegment.setMesh(com.maddox.il2.engine.MeshShared.get(SegmentMeshName(flag3, j)));
        bridgesegment.collide(true);
        bridgesegment.drawing(true);
        if(j != 0 && isAlive())
            com.maddox.il2.ai.World.onActorDied(this, actor);
        if(j != 0 && travellers.size() > 0)
        {
            java.lang.Object aobj[] = travellers.toArray();
            for(int k = 0; k < aobj.length; k++)
                if(aobj[k] instanceof com.maddox.il2.ai.ground.ChiefGround)
                    ((com.maddox.il2.ai.ground.ChiefGround)aobj[k]).BridgeSegmentDestroyed(bridgeIdx, i, actor);
                else
                if(aobj[k] instanceof com.maddox.il2.objects.trains.Train)
                    ((com.maddox.il2.objects.trains.Train)aobj[k]).BridgeSegmentDestroyed(bridgeIdx, i, actor);
                else
                    ((com.maddox.il2.ai.ground.UnitInterface)aobj[k]).absoluteDeath(actor);

        }
        if(!flag)
            return;
        if(j == 0)
            return;
        java.lang.Object aobj1[] = getOwnerAttached();
        int l = 1 + 2 * nMidCells + 1;
        com.maddox.il2.engine.Actor actor1 = actor;
        if((j & 1) != 0 && i > 0)
            ((com.maddox.il2.objects.bridges.BridgeSegment)aobj1[i - 1]).ForcePartDestroing(1, actor1);
        if((j & 2) != 0 && i < l - 1)
            ((com.maddox.il2.objects.bridges.BridgeSegment)aobj1[i + 1]).ForcePartDestroing(0, actor1);
        if(flag1 && (j & 1) != 0)
            ((com.maddox.il2.objects.bridges.BridgeSegment)aobj1[i]).ForcePartDestroing(1, actor1);
        if(flag2 && (j & 2) != 0)
            ((com.maddox.il2.objects.bridges.BridgeSegment)aobj1[i]).ForcePartDestroing(0, actor1);
    }

    public void destroy()
    {
        java.lang.Object aobj[] = getOwnerAttached();
        for(int i = 0; i < aobj.length; i++)
            ((com.maddox.il2.objects.bridges.BridgeSegment)aobj[i]).destroy();

        for(int j = 0; j < 2; j++)
            if(com.maddox.il2.engine.Actor.isValid(bridgeRoad[j]))
            {
                bridgeRoad[j].destroy();
                bridgeRoad[j] = null;
            }

        super.destroy();
    }

    public static void AddTraveller(int i, com.maddox.il2.engine.Actor actor)
    {
        if(!(actor instanceof com.maddox.il2.ai.ground.UnitInterface) && !(actor instanceof com.maddox.il2.ai.ground.ChiefGround) && !(actor instanceof com.maddox.il2.objects.trains.Train))
        {
            java.lang.System.out.println("Error: traveller type");
            return;
        }
        com.maddox.il2.objects.bridges.LongBridge longbridge = com.maddox.il2.objects.bridges.LongBridge.getByIdx(i >> 16);
        if(longbridge == null)
            return;
        if(longbridge.travellers.indexOf(actor) < 0)
            longbridge.travellers.add(actor);
    }

    public static void DelTraveller(int i, com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.objects.bridges.LongBridge longbridge = com.maddox.il2.objects.bridges.LongBridge.getByIdx(i >> 16);
        if(longbridge == null)
            return;
        int j = longbridge.travellers.indexOf(actor);
        if(j >= 0)
            longbridge.travellers.remove(j);
    }

    public static void AddSegmentsToRoadPath(int i, java.util.ArrayList arraylist, float f, float f1)
    {
        com.maddox.il2.objects.bridges.LongBridge.getByIdx(i).AddSegmentsToRoadPath(arraylist, f, f1);
    }

    private void AddSegmentsToRoadPath(java.util.ArrayList arraylist, float f, float f1)
    {
        com.maddox.JGP.Point3d point3d = new Point3d();
        com.maddox.JGP.Point3d point3d1 = new Point3d();
        com.maddox.JGP.Point3d point3d2 = new Point3d();
        com.maddox.JGP.Point3d point3d3 = new Point3d();
        int i = 1 + 2 * nMidCells;
        int j = arraylist.size();
        ComputeSegmentKeyPoints(0, point3d, point3d1, point3d2, point3d3);
        arraylist.add(new RoadSegment((float)point3d1.x, (float)point3d1.y, (float)point3d1.z, width * 0.5F, 0.0D, bridgeIdx, 0));
        arraylist.add(new RoadSegment((float)point3d2.x, (float)point3d2.y, (float)point3d2.z, width * 0.5F, 0.0D, bridgeIdx, 0));
        for(int k = 1; k < 1 + 2 * nMidCells; k++)
        {
            ComputeSegmentKeyPoints(k, point3d, point3d1, point3d2, point3d3);
            arraylist.add(new RoadSegment((float)point3d1.x, (float)point3d1.y, (float)point3d1.z, width * 0.5F, 0.0D, bridgeIdx, k));
        }

        ComputeSegmentKeyPoints(i, point3d, point3d1, point3d2, point3d3);
        arraylist.add(new RoadSegment((float)point3d1.x, (float)point3d1.y, (float)point3d1.z, width * 0.5F, 0.0D, bridgeIdx, i));
        arraylist.add(new RoadSegment((float)point3d2.x, (float)point3d2.y, (float)point3d2.z, width * 0.5F, 0.0D, bridgeIdx, i));
        arraylist.add(new RoadSegment((float)point3d3.x, (float)point3d3.y, (float)point3d3.z, width * 0.5F, 0.0D, -1, -1));
        com.maddox.JGP.Point3d point3d4 = new Point3d(f, f1, 0.0D);
        ComputeSegmentKeyPoints(i, point3d, point3d1, point3d2, point3d3);
        java.lang.Object obj = new Point3d(point3d3);
        obj.z = 0.0D;
        ComputeSegmentKeyPoints(0, point3d, point3d1, point3d2, point3d3);
        point3d1.z = 0.0D;
        boolean flag = point3d4.distanceSquared(((com.maddox.JGP.Point3d) (obj))) < point3d4.distanceSquared(point3d1);
        if(flag)
        {
            int l = 2 + nMidCells * 2 + 1 + 2;
            for(int i1 = 0; i1 < l / 2; i1++)
            {
                java.lang.Object obj1 = arraylist.get(j + i1);
                java.lang.Object obj2 = arraylist.get((j + l) - 1 - i1);
                arraylist.set(j + i1, obj2);
                arraylist.set((j + l) - 1 - i1, obj1);
            }

            for(int j1 = 0; j1 < l - 1; j1++)
            {
                com.maddox.il2.ai.ground.RoadSegment roadsegment = (com.maddox.il2.ai.ground.RoadSegment)arraylist.get(j + j1);
                com.maddox.il2.ai.ground.RoadSegment roadsegment1 = (com.maddox.il2.ai.ground.RoadSegment)arraylist.get(j + j1 + 1);
                roadsegment.br = roadsegment1.br;
                roadsegment.brSg = roadsegment1.brSg;
                if(j1 == l - 2)
                {
                    roadsegment1.br = null;
                    roadsegment1.brSg = null;
                }
            }

        }
        l = ((int) (arraylist.get(arraylist.size() - 1)));
        ((com.maddox.il2.ai.ground.RoadSegment)l).setZ(-1D);
        arraylist.set(arraylist.size() - 1, l);
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public LongBridge(int i, int j, int k, int l, int i1, int j1, float f)
    {
        bridgeRoad = new com.maddox.il2.objects.bridges.BridgeRoad[2];
        bridgeIdx = i;
        setName(com.maddox.il2.objects.bridges.LongBridge.nameOfBridgeByIdx(bridgeIdx));
        supervisor = null;
        if(java.lang.Math.abs(f) > 0.4F)
        {
            java.lang.System.out.println("LongBridge: too big offset");
            f = 0.0F;
        }
        offsetKoef = f;
        switch(j)
        {
        case 128: 
            type = 0;
            bodyMaterial = 0;
            break;

        case 32: // ' '
            type = 1;
            bodyMaterial = 3;
            break;

        case 64: // '@'
            type = 2;
            bodyMaterial = 2;
            break;

        default:
            java.lang.System.out.println("LongBridge: wrong type " + type);
            break;
        }
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            com.maddox.il2.ai.World.cur();
            com.maddox.il2.engine.LandConf landconf = com.maddox.il2.ai.World.land().config;
            mat = com.maddox.il2.engine.Mat.New("maps/_Tex/" + (type != 0 ? type != 1 ? landconf.rail : landconf.road : landconf.highway) + ".mat");
        }
        begX = k;
        begY = l;
        endX = i1;
        endY = j1;
        int k1 = endX - begX;
        int l1 = endY - begY;
        if(l1 == 0)
        {
            nMidCells = java.lang.Math.abs(k1) - 1;
            incY = 0;
            incX = k1 <= 0 ? -1 : 1;
        } else
        if(k1 == 0)
        {
            nMidCells = java.lang.Math.abs(l1) - 1;
            incX = 0;
            incY = l1 <= 0 ? -1 : 1;
        } else
        {
            if(java.lang.Math.abs(k1) != java.lang.Math.abs(l1))
                java.lang.System.out.println("LongBridge: wrong direction");
            nMidCells = java.lang.Math.abs(l1) - 1;
            incX = k1 <= 0 ? -1 : 1;
            incY = l1 <= 0 ? -1 : 1;
        }
        dirOct = com.maddox.il2.objects.bridges.LongBridge.ComputeOctDirection(incX, incY);
        if(nMidCells < 0)
            java.lang.System.out.println("LongBridge: zero length");
        pos = new ActorPosStatic(this);
        java.lang.Object obj = ComputeSegmentOrient(false);
        com.maddox.JGP.Point3d point3d = ComputeSegmentPos3d(0);
        com.maddox.JGP.Point3d point3d1 = ComputeSegmentPos3d(1 + 2 * nMidCells);
        point3d.add(point3d1);
        point3d.scale(0.5D);
        pos.setAbs(point3d, ((com.maddox.il2.engine.Orient) (obj)));
        pos.reset();
        draw = new LongBridgeDraw();
        drawing(false);
        float f1;
        float f2;
        if(type != 1)
        {
            f1 = 24F;
            f2 = 0.05F;
        } else
        {
            f1 = 2.1F;
            f2 = 0.033F;
        }
        int i2 = 0;
        com.maddox.JGP.Point3d point3d2 = ComputeSegmentPos3d(i2);
        obj = new BridgeSegment(this, bridgeIdx, i2++, f1, f2, point3d2, dirOct);
        for(int j2 = 0; j2 < nMidCells; j2++)
        {
            point3d2 = ComputeSegmentPos3d(i2);
            new BridgeSegment(this, bridgeIdx, i2++, f1, f2, point3d2, dirOct);
            point3d2 = ComputeSegmentPos3d(i2);
            new BridgeSegment(this, bridgeIdx, i2++, f1, f2, point3d2, dirOct);
        }

        point3d2 = ComputeSegmentPos3d(i2);
        new BridgeSegment(this, bridgeIdx, i2++, f1, f2, point3d2, dirOct);
        java.lang.Object obj1 = ((com.maddox.il2.objects.bridges.BridgeSegment) (obj)).mesh();
        java.lang.Object obj2 = new Matrix4d();
        int k2 = ((com.maddox.il2.engine.Mesh) (obj1)).hookFind("Top");
        ((com.maddox.il2.engine.Mesh) (obj1)).hookMatrix(k2, ((com.maddox.JGP.Matrix4d) (obj2)));
        height = (float)((com.maddox.JGP.Matrix4d) (obj2)).m23;
        width = 2.0F * java.lang.Math.abs((float)((com.maddox.JGP.Matrix4d) (obj2)).m13);
        k2 = ((com.maddox.il2.engine.Mesh) (obj1)).hookFind("Mid");
        ((com.maddox.il2.engine.Mesh) (obj1)).hookMatrix(k2, ((com.maddox.JGP.Matrix4d) (obj2)));
        heightEnd = (float)((com.maddox.JGP.Matrix4d) (obj2)).m23;
        lengthEnd = -(float)((com.maddox.JGP.Matrix4d) (obj2)).m03;
        lengthEnd += 0.5F * SegmentLength();
        travellers = new ArrayList();
        obj1 = ComputeSegmentPos3d(0);
        com.maddox.JGP.Point3d point3d3 = ComputeSegmentPos3d(1);
        obj2 = new Vector3d(point3d3);
        ((com.maddox.JGP.Vector3d) (obj2)).sub(((com.maddox.JGP.Tuple3d) (obj1)));
        point3d3 = ComputeSegmentPos3d(1 + 2 * nMidCells);
        float f3 = 2.0F * (float)((com.maddox.JGP.Vector3d) (obj2)).length();
        float f4 = f3 * 0.25F;
        f3 *= 1.4F;
        f3 *= 0.5F;
        ((com.maddox.JGP.Vector3d) (obj2)).normalize();
        ((com.maddox.JGP.Vector3d) (obj2)).scale(f3);
        ((com.maddox.JGP.Point3d) (obj1)).sub(((com.maddox.JGP.Tuple3d) (obj2)));
        point3d3.add(((com.maddox.JGP.Tuple3d) (obj2)));
        float f5 = (float)java.lang.Math.sqrt(f3 * f3 + f4 * f4);
        bridgeRoad[0] = new BridgeRoad(((com.maddox.JGP.Point3d) (obj1)), f5, mat, type, begX, begY, incX, incY, offsetKoef);
        bridgeRoad[1] = new BridgeRoad(point3d3, f5, mat, type, endX, endY, -incX, -incY, -offsetKoef);
    }

    public static final int BRIDGE_HIGHWAY = 0;
    public static final int BRIDGE_COUNTRY = 1;
    public static final int BRIDGE_RAIL = 2;
    private static final int N_BRIDGE_TYPES = 3;
    private static final float BRIDGE_SEGM_MAX_LIFE = 24F;
    private static final float BRIDGE_WOODSEGM_MAX_LIFE = 2.1F;
    private static final float BRIDGE_SEGM_IGN_TNT = 0.05F;
    private static final float BRIDGE_WOODSEGM_IGN_TNT = 0.033F;
    private int type;
    public int bodyMaterial;
    private int bridgeIdx;
    private int begX;
    private int begY;
    private int endX;
    private int endY;
    private int nMidCells;
    private float offsetKoef;
    private int incX;
    private int incY;
    private int dirOct;
    private float width;
    private float height;
    private float heightEnd;
    private float lengthEnd;
    private com.maddox.il2.engine.Mat mat;
    private com.maddox.il2.objects.bridges.BridgeRoad bridgeRoad[];
    private java.util.ArrayList travellers;
    private com.maddox.il2.engine.Actor supervisor;











}

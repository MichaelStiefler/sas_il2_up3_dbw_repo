// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PlMapActors.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.TexImage.TexImage;
import com.maddox.gwindow.GFileFilter;
import com.maddox.gwindow.GFileFilterName;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowFileOpen;
import com.maddox.gwindow.GWindowFileSaveAs;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuBarItem;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowMenuPopUp;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.DrawEnv;
import com.maddox.il2.engine.DreamEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.LandConf;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Runaway;
import com.maddox.il2.objects.bridges.Bridge;
import com.maddox.il2.objects.buildings.Mountain;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Finger;
import com.maddox.rts.HomePath;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.MainWindow;
import com.maddox.rts.MsgAddListener;
import com.maddox.rts.MsgMouseListener;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SFSReader;
import com.maddox.rts.SectFile;
import com.maddox.rts.SoftClass;
import com.maddox.rts.Spawn;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapXY16List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, PlMapAirdrome, PathAirdrome, PAirdrome, 
//            PlMapLoad, Builder, Pathes, PPoint, 
//            WSelect, BldConfig

public class PlMapActors extends com.maddox.il2.builder.Plugin
    implements com.maddox.rts.MsgMouseListener
{
    static class ItemPattern
    {

        public float getAzimut()
        {
            return (float)(flags >> 16) + (float)(flags >> 8 & 0xff) / 256F;
        }

        public int finger;
        public int flags;
        public float x;
        public float y;
        public com.maddox.il2.engine.ActorSpawn spawn;

        public ItemPattern(int i, float f, float f1, float f2)
        {
            finger = i;
            x = f;
            y = f1;
            flags = (int)f2 << 16 | ((int)(f2 * 256F) & 0xff) << 8;
            spawn = (com.maddox.il2.engine.ActorSpawn)com.maddox.rts.Spawn.get(i);
        }
    }

    private static class ClearRoadFilter
        implements com.maddox.il2.engine.ActorFilter
    {

        public boolean isUse(com.maddox.il2.engine.Actor actor, double d)
        {
            return actor instanceof com.maddox.il2.engine.ActorMesh;
        }

        private ClearRoadFilter()
        {
        }

    }

    class ViewItem extends com.maddox.gwindow.GWindowMenuItem
    {

        public void execute()
        {
            bChecked = !bChecked;
            viewType(indx);
        }

        int indx;

        public ViewItem(int i, com.maddox.gwindow.GWindowMenu gwindowmenu, java.lang.String s, java.lang.String s1)
        {
            super(gwindowmenu, s, s1);
            indx = i;
        }
    }

    static class Type
    {

        public java.lang.String name;
        public com.maddox.il2.builder.Item item[];

        public Type(java.lang.String s, com.maddox.il2.builder.Item aitem[])
        {
            name = s;
            item = aitem;
        }
    }

    static class Item
    {

        public java.lang.String name;
        com.maddox.il2.engine.ActorSpawn spawn_;
        java.lang.String fullClassName;
        int fingerOfFullClassName;

        public Item(java.lang.String s, java.lang.String s1)
        {
            name = s;
            fullClassName = s1;
            fingerOfFullClassName = com.maddox.rts.Finger.Int(fullClassName);
            spawn_ = (com.maddox.il2.engine.ActorSpawn)com.maddox.rts.Spawn.get_WithSoftClass(fullClassName);
        }

        public Item(java.lang.String s)
        {
            name = s;
            fullClassName = null;
            spawn_ = null;
            fingerOfFullClassName = com.maddox.rts.Finger.Int("* an impossible name *");
        }
    }


    public PlMapActors()
    {
        allActors = new HashMapExt();
        matLabel = null;
        _saveCls0Map = new HashMapExt();
        _saveClsMap = new HashMapExt();
        p2d = new Point2d();
        spawnArg = new ActorSpawnArg();
        fillHeight = 0;
        _startFill = new Point3d();
        _endFill = new Point3d();
        _stepFill = new Point3d();
        squareTile = 1;
        _clipSpawns = new ArrayList();
        _clipAirdroms = new ArrayList();
        _clipLoc = new ArrayList();
        _clipP0 = new Point3d();
        viewClassFingers = new HashMap();
        newIndxBridge = 10000;
        _clearRoadP0 = new Point3d();
        _clearRoadP1 = new Point3d();
        _clearRoadArray = new ArrayList();
        _clearRoadBound = new float[6];
        _clearRoadFilter = new ClearRoadFilter();
        PIXEL = 200D;
        PIXELD2 = 100D;
        TILE = 8;
        TILEMASK = 7;
        QUAD = PIXEL * (double)TILE;
        _changeOffsetXY = new Point3f();
        _changeLoc = new Loc();
    }

    private static final java.lang.String getFullClassName(com.maddox.il2.engine.Actor actor)
    {
        return (actor instanceof com.maddox.rts.SoftClass) ? ((com.maddox.rts.SoftClass)actor).fullClassName() : actor.getClass().getName();
    }

    private static final int getFingerOfFullClassName(com.maddox.il2.engine.Actor actor)
    {
        return com.maddox.rts.Finger.Int((actor instanceof com.maddox.rts.SoftClass) ? ((com.maddox.rts.SoftClass)actor).fullClassName() : actor.getClass().getName());
    }

    private java.lang.String staticFileName()
    {
        java.lang.String s = "maps/" + com.maddox.il2.builder.PlMapLoad.mapFileName();
        com.maddox.rts.SectFile sectfile = new SectFile(s);
        int i = sectfile.sectionIndex("static");
        if(i >= 0 && sectfile.vars(i) > 0)
        {
            java.lang.String s1 = sectfile.var(i, 0);
            return com.maddox.rts.HomePath.concatNames(s, s1);
        } else
        {
            return null;
        }
    }

    public void mapLoaded()
    {
        deleteAll();
        com.maddox.il2.builder.Plugin.builder.deleteAll();
        com.maddox.il2.engine.Engine.drawEnv().resetGameClear();
        com.maddox.il2.engine.Engine.drawEnv().resetGameCreate();
        if(com.maddox.il2.ai.World.cur().statics != null)
            com.maddox.il2.ai.World.cur().statics.resetGame();
        if(!com.maddox.il2.builder.Plugin.builder.isLoadedLandscape())
        {
            return;
        } else
        {
            load(staticFileName());
            com.maddox.il2.objects.Statics.trim();
            com.maddox.il2.engine.Engine.dreamEnv().resetGlobalListener(com.maddox.il2.ai.World.cur().statics);
            return;
        }
    }

    public void deleteAll()
    {
        for(java.util.Map.Entry entry = allActors.nextEntry(null); entry != null; entry = allActors.nextEntry(entry))
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getKey();
            if(com.maddox.il2.engine.Actor.isValid(actor))
                actor.destroy();
        }

        allActors.clear();
    }

    public void loadAs(java.lang.String s)
    {
        load(s, false, false);
    }

    public void load(java.lang.String s)
    {
        load(s, true, false);
    }

    public void load(java.lang.String s, boolean flag, boolean flag1)
    {
        int i = 0;
        if(flag1)
        {
            _clipSpawns.clear();
            _clipAirdroms.clear();
            _clipLoc.clear();
            _clipP0.x = _clipP0.y = _clipP0.z = 0.0D;
        } else
        {
            landMapT = new TexImage();
            landMapH = new TexImage();
            try
            {
                landMapT.LoadTGA("maps/" + com.maddox.il2.builder.PlMapLoad.mapDirName() + "/" + com.maddox.il2.ai.World.land().config.typeMap);
                landMapH.LoadTGA("maps/" + com.maddox.il2.builder.PlMapLoad.mapDirName() + "/" + com.maddox.il2.ai.World.land().config.heightMap);
            }
            catch(java.lang.Exception exception) { }
            bTMapChanged = false;
            bBridgeChanged = false;
            if(s == null)
            {
                if(!"Test".equals(com.maddox.il2.builder.PlMapLoad.mapKeyName()) && !flag)
                    com.maddox.il2.builder.PlMapLoad.bridgesCreate(landMapT);
                return;
            }
        }
        com.maddox.il2.engine.Loc loc = new Loc();
        try
        {
            java.io.DataInputStream datainputstream = new DataInputStream(new SFSInputStream(s));
            boolean flag2 = true;
            int j = datainputstream.readInt();
            if(j == -65535)
            {
                flag2 = false;
                j = datainputstream.readInt();
            }
            int k1 = 0;
            for(int l1 = 0; l1 < j; l1++)
            {
                int i2 = datainputstream.readInt();
                int k2 = datainputstream.readInt();
                int j3 = datainputstream.readInt();
                int j4 = datainputstream.readInt();
                int l4 = datainputstream.readInt();
                float f7 = datainputstream.readFloat();
                if(flag && !flag1)
                {
                    com.maddox.il2.objects.bridges.Bridge bridge = new Bridge(k1++, l4, i2, k2, j3, j4, f7);
                    com.maddox.rts.Property.set(bridge, "builderSpawn", "");
                    com.maddox.il2.builder.PlMapLoad.bridgeActors.add(bridge);
                }
            }

            if(flag2)
            {
                int k = datainputstream.readInt();
                if(k > 0)
                {
                    while(k-- > 0) 
                    {
                        java.lang.String s2 = datainputstream.readUTF();
                        com.maddox.rts.Spawn.get_WithSoftClass(s2);
                    }
                    for(int l = datainputstream.readInt(); l-- > 0;)
                    {
                        int j2 = datainputstream.readInt();
                        float f = datainputstream.readFloat();
                        float f1 = datainputstream.readFloat();
                        float f2 = datainputstream.readFloat();
                        float f4 = datainputstream.readFloat();
                        float f8 = datainputstream.readFloat();
                        float f11 = datainputstream.readFloat();
                        loc.set(f, f1, f2, f4, f8, f11);
                        com.maddox.il2.engine.ActorSpawn actorspawn = (com.maddox.il2.engine.ActorSpawn)com.maddox.rts.Spawn.get(j2);
                        if(actorspawn != null)
                            if(flag1)
                            {
                                com.maddox.il2.engine.Loc loc2 = new Loc(loc);
                                _clipSpawns.add(actorspawn);
                                _clipLoc.add(loc2);
                                _clipP0.add(loc2.getPoint());
                                i++;
                            } else
                            {
                                insert(actorspawn, loc, false);
                            }
                    }

                }
            } else
            {
                int i1 = datainputstream.readInt();
                Object obj = null;
                if(i1 > 0)
                {
                    com.maddox.il2.engine.ActorSpawn aactorspawn[] = new com.maddox.il2.engine.ActorSpawn[i1];
                    for(int l2 = 0; l2 < i1; l2++)
                    {
                        java.lang.String s3 = datainputstream.readUTF();
                        aactorspawn[l2] = (com.maddox.il2.engine.ActorSpawn)com.maddox.rts.Spawn.get_WithSoftClass(s3);
                    }

                    for(i1 = datainputstream.readInt(); i1-- > 0;)
                    {
                        int k3 = datainputstream.readInt();
                        float f3 = datainputstream.readFloat();
                        float f5 = datainputstream.readFloat();
                        float f9 = datainputstream.readFloat();
                        loc.set(f3, f5, 0.0D, f9, 0.0F, 0.0F);
                        if(k3 < aactorspawn.length && aactorspawn[k3] != null)
                            if(flag1)
                            {
                                com.maddox.il2.engine.Loc loc1 = new Loc(loc);
                                _clipSpawns.add(aactorspawn[k3]);
                                _clipLoc.add(loc1);
                                _clipP0.add(loc1.getPoint());
                                i++;
                            } else
                            {
                                insert(aactorspawn[k3], loc, false);
                            }
                    }

                }
                i1 = datainputstream.readInt();
                if(i1 > 0)
                {
                    com.maddox.il2.engine.ActorSpawn aactorspawn1[] = new com.maddox.il2.engine.ActorSpawn[i1];
                    for(int i3 = 0; i3 < i1; i3++)
                    {
                        java.lang.String s4 = datainputstream.readUTF();
                        aactorspawn1[i3] = (com.maddox.il2.engine.ActorSpawn)com.maddox.rts.Spawn.get_WithSoftClass(s4);
                    }

                    for(int l3 = datainputstream.readInt(); l3-- > 0;)
                    {
                        int k4 = datainputstream.readInt();
                        float f6 = (float)(k4 & 0xffff) * 200F;
                        float f10 = (float)(k4 >> 16 & 0xffff) * 200F;
                        for(int j5 = datainputstream.readInt(); j5-- > 0;)
                        {
                            int l5 = datainputstream.readInt();
                            int i6 = datainputstream.readInt();
                            int j6 = l5 & 0x7fff;
                            if(j6 < aactorspawn1.length && aactorspawn1[j6] != null)
                            {
                                int k6 = (short)(l5 >> 16);
                                int l6 = (short)(i6 & 0xffff);
                                int i7 = (short)(i6 >> 16 & 0xffff);
                                float f12 = ((float)k6 * 360F) / 32000F;
                                float f13 = ((float)l6 * 200F) / 32000F + f6;
                                float f14 = ((float)i7 * 200F) / 32000F + f10;
                                loc.set(f13, f14, 0.0D, -f12, 0.0F, 0.0F);
                                if(flag1)
                                {
                                    com.maddox.il2.engine.Loc loc3 = new Loc(loc);
                                    _clipSpawns.add(aactorspawn1[j6]);
                                    _clipLoc.add(loc3);
                                    _clipP0.add(loc3.getPoint());
                                    i++;
                                } else
                                {
                                    insert(aactorspawn1[j6], loc, false);
                                }
                            }
                        }

                    }

                }
            }
            if(datainputstream.available() > 0)
            {
                com.maddox.il2.builder.PlMapAirdrome plmapairdrome = (com.maddox.il2.builder.PlMapAirdrome)com.maddox.il2.builder.Plugin.getPlugin("MapAirdrome");
                com.maddox.JGP.Point3d point3d = new Point3d();
                for(int i4 = 0; i4 < 3; i4++)
                {
                    for(int j1 = datainputstream.readInt(); j1-- > 0;)
                    {
                        com.maddox.il2.builder.PathAirdrome pathairdrome = new PathAirdrome(com.maddox.il2.builder.Plugin.builder.pathes, i4);
                        com.maddox.rts.Property.set(pathairdrome, "builderPlugin", plmapairdrome);
                        pathairdrome.drawing(plmapairdrome.mView.bChecked);
                        java.lang.Object obj1 = null;
                        int i5 = datainputstream.readInt();
                        for(int k5 = 0; k5 < i5; k5++)
                        {
                            point3d.set(datainputstream.readFloat(), datainputstream.readFloat(), 0.0D);
                            point3d.z = com.maddox.il2.engine.Engine.land().HQ(point3d.x, point3d.y) + 0.20000000000000001D;
                            obj1 = new PAirdrome(pathairdrome, ((com.maddox.il2.builder.PPoint) (obj1)), point3d, i4);
                            com.maddox.rts.Property.set(obj1, "builderPlugin", plmapairdrome);
                            com.maddox.rts.Property.set(obj1, "builderSpawn", "");
                            if(flag1)
                            {
                                _clipP0.add(point3d);
                                i++;
                            }
                        }

                        if(flag1)
                        {
                            _clipAirdroms.add(com.maddox.il2.builder.PathAirdrome.toSpawnString(pathairdrome));
                            pathairdrome.destroy();
                        }
                    }

                }

            }
            datainputstream.close();
            if(!flag1)
            {
                viewUpdate();
                com.maddox.il2.engine.Engine.drawEnv().staticTrimToSize();
            }
        }
        catch(java.lang.Exception exception1)
        {
            java.lang.String s1 = "Actors load from '" + s + "' FAILED: " + exception1.getMessage();
            java.lang.System.out.println(s1);
            exception1.printStackTrace();
            com.maddox.il2.builder.Plugin.builder.tip(s1);
        }
        if(flag1 && i > 0)
        {
            _clipP0.x /= i;
            _clipP0.y /= i;
            _clipP0.z /= i;
            paste();
        }
    }

    public void save()
    {
        save(staticFileName());
        if(bTMapChanged || bBridgeChanged)
        {
            int i = com.maddox.il2.builder.PlMapLoad.bridgeActors.size();
            com.maddox.TexImage.TexImage teximage = landMapT;
            for(int j = 0; j < i; j++)
            {
                com.maddox.il2.objects.bridges.Bridge bridge = (com.maddox.il2.objects.bridges.Bridge)com.maddox.il2.builder.PlMapLoad.bridgeActors.get(j);
                int k = bridge.__x1;
                int l = bridge.__y1;
                int i1 = bridge.__x2;
                int j1 = bridge.__y2;
                int k1 = i1 - k;
                if(k1 < 0)
                    k1 = -1;
                if(k1 > 0)
                    k1 = 1;
                int l1 = j1 - l;
                if(l1 < 0)
                    l1 = -1;
                if(l1 > 0)
                    l1 = 1;
                do
                {
                    teximage.I(k, l, teximage.intI(k, l) & 0xffffff1f);
                    if(k == i1 && l == j1)
                        break;
                    k += k1;
                    l += l1;
                } while(true);
            }

            java.lang.String s2 = "maps/" + com.maddox.il2.builder.PlMapLoad.mapDirName() + "/" + com.maddox.il2.ai.World.land().config.typeMap;
            try
            {
                teximage.SaveTGA(s2);
                bTMapChanged = false;
            }
            catch(java.lang.Exception exception1)
            {
                java.lang.String s3 = "Type map '" + s2 + "' save FAILED: " + exception1.getMessage();
                java.lang.System.out.println(s3);
                com.maddox.il2.builder.Plugin.builder.tip(s3);
            }
        }
        if(bHMapChanged)
        {
            java.lang.String s = "maps/" + com.maddox.il2.builder.PlMapLoad.mapDirName() + "/" + com.maddox.il2.ai.World.land().config.heightMap;
            try
            {
                landMapH.SaveTGA(s);
                bHMapChanged = false;
            }
            catch(java.lang.Exception exception)
            {
                java.lang.String s1 = "Height map '" + s + "' save FAILED: " + exception.getMessage();
                java.lang.System.out.println(s1);
                com.maddox.il2.builder.Plugin.builder.tip(s1);
            }
        }
        com.maddox.il2.builder.Plugin.doSave((com.maddox.rts.SectFile)null);
    }

    public void saveAs(java.lang.String s)
    {
        save(s, false);
    }

    public void save(java.lang.String s)
    {
        save(s, true);
    }

    public void save(java.lang.String s, boolean flag)
    {
        if(s == null)
            return;
        try
        {
            java.io.DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(s));
            dataoutputstream.writeInt(-65535);
            int i = com.maddox.il2.builder.PlMapLoad.bridgeActors.size();
            if(i == 0 || !flag)
            {
                dataoutputstream.writeInt(0);
            } else
            {
                dataoutputstream.writeInt(i);
                for(int j = 0; j < i; j++)
                {
                    com.maddox.il2.objects.bridges.Bridge bridge = (com.maddox.il2.objects.bridges.Bridge)com.maddox.il2.builder.PlMapLoad.bridgeActors.get(j);
                    dataoutputstream.writeInt(bridge.__x1);
                    dataoutputstream.writeInt(bridge.__y1);
                    dataoutputstream.writeInt(bridge.__x2);
                    dataoutputstream.writeInt(bridge.__y2);
                    dataoutputstream.writeInt(bridge.__type);
                    dataoutputstream.writeFloat(bridge.__offsetK);
                }

            }
            _saveCls0Map.clear();
            _saveClsMap.clear();
            com.maddox.util.HashMapXY16List hashmapxy16list = new HashMapXY16List(7);
            int k = 0;
            int l = 0;
            int i1 = 0;
            for(java.util.Map.Entry entry = allActors.nextEntry(null); entry != null; entry = allActors.nextEntry(entry))
            {
                com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getKey();
                if(com.maddox.il2.engine.Actor.isValid(actor))
                {
                    java.lang.String s2 = com.maddox.il2.builder.PlMapActors.getFullClassName(actor);
                    if((actor instanceof com.maddox.il2.objects.air.Runaway) || (actor instanceof com.maddox.il2.objects.buildings.Mountain))
                    {
                        if(!_saveCls0Map.containsKey(s2))
                        {
                            _saveCls0Map.put(s2, new Integer(k));
                            k++;
                        }
                        l++;
                    } else
                    {
                        if(!_saveClsMap.containsKey(s2))
                        {
                            _saveClsMap.put(s2, new Integer(i1));
                            i1++;
                        }
                        com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
                        hashmapxy16list.put((int)(point3d.y / 200D), (int)(point3d.x / 200D), actor);
                    }
                }
            }

            if(k > 0)
            {
                java.lang.String as[] = new java.lang.String[k];
                for(java.util.Map.Entry entry1 = _saveCls0Map.nextEntry(null); entry1 != null; entry1 = _saveCls0Map.nextEntry(entry1))
                {
                    java.lang.String s3 = (java.lang.String)entry1.getKey();
                    java.lang.Integer integer = (java.lang.Integer)entry1.getValue();
                    as[integer.intValue()] = s3;
                }

                dataoutputstream.writeInt(k);
                for(int j1 = 0; j1 < k; j1++)
                    dataoutputstream.writeUTF(as[j1]);

                dataoutputstream.writeInt(l);
                for(java.util.Map.Entry entry2 = allActors.nextEntry(null); entry2 != null; entry2 = allActors.nextEntry(entry2))
                {
                    com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)entry2.getKey();
                    if(com.maddox.il2.engine.Actor.isValid(actor1))
                    {
                        java.lang.String s5 = com.maddox.il2.builder.PlMapActors.getFullClassName(actor1);
                        if(_saveCls0Map.containsKey(s5))
                        {
                            int k2 = ((java.lang.Integer)_saveCls0Map.get(s5)).intValue();
                            com.maddox.JGP.Point3d point3d1 = actor1.pos.getAbsPoint();
                            com.maddox.il2.engine.Orient orient = actor1.pos.getAbsOrient();
                            dataoutputstream.writeInt(k2);
                            dataoutputstream.writeFloat((float)point3d1.x);
                            dataoutputstream.writeFloat((float)point3d1.y);
                            dataoutputstream.writeFloat(orient.azimut());
                        }
                    }
                }

            } else
            {
                dataoutputstream.writeInt(0);
            }
            if(i1 > 0)
            {
                java.lang.String as1[] = new java.lang.String[i1];
                for(java.util.Map.Entry entry3 = _saveClsMap.nextEntry(null); entry3 != null; entry3 = _saveClsMap.nextEntry(entry3))
                {
                    java.lang.String s4 = (java.lang.String)entry3.getKey();
                    java.lang.Integer integer1 = (java.lang.Integer)entry3.getValue();
                    as1[integer1.intValue()] = s4;
                }

                dataoutputstream.writeInt(i1);
                for(int k1 = 0; k1 < i1; k1++)
                    dataoutputstream.writeUTF(as1[k1]);

                int l1 = 0;
                int ai1[] = hashmapxy16list.allKeys();
                dataoutputstream.writeInt(ai1.length);
                for(int l2 = 0; l2 < ai1.length; l2++)
                {
                    int j3 = hashmapxy16list.key2x(ai1[l2]);
                    int k3 = hashmapxy16list.key2y(ai1[l2]);
                    float f = (float)j3 * 200F;
                    float f1 = (float)k3 * 200F;
                    dataoutputstream.writeInt(k3 << 16 | j3);
                    java.util.List list = hashmapxy16list.get(k3, j3);
                    int j4 = list.size();
                    l1 += j4;
                    dataoutputstream.writeInt(j4);
                    for(int k4 = 0; k4 < j4; k4++)
                    {
                        com.maddox.il2.engine.Actor actor4 = (com.maddox.il2.engine.Actor)list.get(k4);
                        java.lang.String s6 = com.maddox.il2.builder.PlMapActors.getFullClassName(actor4);
                        int l4 = ((java.lang.Integer)_saveClsMap.get(s6)).intValue();
                        com.maddox.JGP.Point3d point3d3 = actor4.pos.getAbsPoint();
                        com.maddox.il2.engine.Orient orient1 = actor4.pos.getAbsOrient();
                        int i5 = l4 & 0x7fff;
                        float f2 = orient1.getYaw() % 360F;
                        i5 |= (int)((f2 * 32000F) / 360F) << 16;
                        int j5 = (int)(((point3d3.x - (double)f) * 32000D) / 200D) & 0xffff | (int)(((point3d3.y - (double)f1) * 32000D) / 200D) << 16;
                        dataoutputstream.writeInt(i5);
                        dataoutputstream.writeInt(j5);
                    }

                }

                java.lang.System.out.println("Saved actors: " + l1 + " blocks: " + ai1.length);
            } else
            {
                dataoutputstream.writeInt(0);
            }
            hashmapxy16list.clear();
            _saveCls0Map.clear();
            _saveClsMap.clear();
            int ai[] = {
                0, 0, 0
            };
            java.lang.Object aobj[] = com.maddox.il2.builder.Plugin.builder.pathes.getOwnerAttached();
            for(int i2 = 0; i2 < aobj.length; i2++)
            {
                com.maddox.il2.engine.Actor actor2 = (com.maddox.il2.engine.Actor)aobj[i2];
                if(actor2 == null)
                    break;
                if(actor2 instanceof com.maddox.il2.builder.PathAirdrome)
                {
                    com.maddox.il2.builder.PathAirdrome pathairdrome = (com.maddox.il2.builder.PathAirdrome)actor2;
                    ai[pathairdrome._iType]++;
                }
            }

            for(int j2 = 0; j2 < 3; j2++)
            {
                dataoutputstream.writeInt(ai[j2]);
                java.lang.Object aobj1[] = com.maddox.il2.builder.Plugin.builder.pathes.getOwnerAttached();
                for(int i3 = 0; i3 < aobj1.length; i3++)
                {
                    com.maddox.il2.engine.Actor actor3 = (com.maddox.il2.engine.Actor)aobj1[i3];
                    if(actor3 == null)
                        break;
                    if(actor3 instanceof com.maddox.il2.builder.PathAirdrome)
                    {
                        com.maddox.il2.builder.PathAirdrome pathairdrome1 = (com.maddox.il2.builder.PathAirdrome)actor3;
                        if(pathairdrome1._iType == j2)
                        {
                            int l3 = pathairdrome1.points();
                            dataoutputstream.writeInt(l3);
                            for(int i4 = 0; i4 < l3; i4++)
                            {
                                com.maddox.il2.builder.PPoint ppoint = pathairdrome1.point(i4);
                                com.maddox.JGP.Point3d point3d2 = ppoint.pos.getAbsPoint();
                                dataoutputstream.writeFloat((float)point3d2.x);
                                dataoutputstream.writeFloat((float)point3d2.y);
                            }

                        }
                    }
                }

            }

            dataoutputstream.close();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.String s1 = "Actors save to '" + s + "' FAILED: " + exception.getMessage();
            java.lang.System.out.println(s1);
            com.maddox.il2.builder.Plugin.builder.tip(s1);
        }
    }

    public void loadSpawn(java.lang.String s)
    {
        loadSpawnFrom(s);
        viewUpdate();
        com.maddox.il2.engine.Engine.drawEnv().staticTrimToSize();
    }

    public void loadSpawnFrom(com.maddox.rts.SectFile sectfile, java.lang.String s)
    {
        int i = sectfile.sectionIndex(s);
        if(i < 0)
            return;
        int j = sectfile.vars(i);
        for(int k = 0; k < j; k++)
        {
            java.lang.String s1 = sectfile.line(i, k);
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)com.maddox.rts.CmdEnv.top().exec(s1);
            if(com.maddox.il2.engine.Actor.isValid(actor))
            {
                com.maddox.il2.engine.IconDraw.create(actor);
                if(actor.icon == null)
                    actor.icon = com.maddox.il2.engine.IconDraw.get("icons/unknown.mat");
                com.maddox.rts.Property.set(actor, "builderSpawn", "");
                com.maddox.rts.Property.set(actor, "builderPlugin", this);
                allActors.put(actor, null);
            }
        }

    }

    public void loadSpawnFrom(java.lang.String s)
    {
        try
        {
            java.io.BufferedReader bufferedreader = new BufferedReader(new SFSReader(s));
            java.lang.String s1 = "";
            do
            {
                java.lang.String s2 = bufferedreader.readLine();
                if(s2 == null)
                    break;
                com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)com.maddox.rts.CmdEnv.top().exec(s2);
                if(com.maddox.il2.engine.Actor.isValid(actor) && !(actor instanceof com.maddox.il2.builder.PathAirdrome))
                {
                    com.maddox.il2.engine.IconDraw.create(actor);
                    if(actor.icon == null)
                        actor.icon = com.maddox.il2.engine.IconDraw.get("icons/unknown.mat");
                    com.maddox.rts.Property.set(actor, "builderSpawn", "");
                    com.maddox.rts.Property.set(actor, "builderPlugin", this);
                    allActors.put(actor, null);
                }
            } while(true);
            bufferedreader.close();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("Load '" + s + "'failed: " + exception.getMessage());
        }
    }

    public void saveSpawn(java.lang.String s)
    {
        saveSpawnTo(s);
    }

    public void saveSpawnTo(com.maddox.rts.SectFile sectfile, java.lang.String s)
    {
        int i = sectfile.sectionIndex(s);
        if(i < 0)
            i = sectfile.sectionAdd(s);
        else
            sectfile.sectionClear(i);
        com.maddox.il2.builder.Item aitem[] = null;
        for(int j = 0; j < type.length; j++)
        {
            if(!s.equals(type[j].name))
                continue;
            aitem = type[j].item;
            break;
        }

        if(aitem == null)
            return;
        for(java.util.Map.Entry entry = allActors.nextEntry(null); entry != null; entry = allActors.nextEntry(entry))
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getKey();
            if(com.maddox.il2.engine.Actor.isValid(actor))
            {
                int k = com.maddox.il2.builder.PlMapActors.getFingerOfFullClassName(actor);
                for(int l = 0; l < aitem.length; l++)
                {
                    if(k != aitem[l].fingerOfFullClassName)
                        continue;
                    com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
                    com.maddox.il2.engine.Orient orient = actor.pos.getAbsOrient();
                    sectfile.lineAdd(i, "spawn", aitem[l].fullClassName + " POSP " + (float)point3d.x + " " + (float)point3d.y + " " + (float)point3d.z + " POSO " + orient.azimut() + " " + orient.tangage() + " " + orient.kren());
                    break;
                }

            }
        }

    }

    public void saveSpawnTo(java.lang.String s)
    {
        try
        {
            java.io.PrintWriter printwriter = new PrintWriter(new BufferedWriter(new FileWriter(s)));
            for(java.util.Map.Entry entry = allActors.nextEntry(null); entry != null; entry = allActors.nextEntry(entry))
            {
                com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getKey();
                if(com.maddox.il2.engine.Actor.isValid(actor))
                {
                    com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
                    com.maddox.il2.engine.Orient orient = actor.pos.getAbsOrient();
                    printwriter.println("spawn " + com.maddox.il2.builder.PlMapActors.getFullClassName(actor) + " POSP " + (float)point3d.x + " " + (float)point3d.y + " " + (float)point3d.z + " POSO " + orient.azimut() + " " + orient.tangage() + " " + orient.kren());
                }
            }

            java.lang.Object aobj[] = com.maddox.il2.builder.Plugin.builder.pathes.getOwnerAttached();
            for(int i = 0; i < aobj.length; i++)
            {
                com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)aobj[i];
                if(actor1 == null)
                    break;
                if(actor1 instanceof com.maddox.il2.builder.PathAirdrome)
                {
                    com.maddox.il2.builder.PathAirdrome pathairdrome = (com.maddox.il2.builder.PathAirdrome)actor1;
                    printwriter.println(com.maddox.il2.builder.PathAirdrome.toSpawnString(pathairdrome));
                }
            }

            printwriter.close();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.String s1 = "Actors save as spawns to '" + s + "' FAILED: " + exception.getMessage();
            java.lang.System.out.println(s1);
            com.maddox.il2.builder.Plugin.builder.tip(s1);
        }
    }

    public void renderMap2D()
    {
        if(!com.maddox.il2.builder.Plugin.builder.isFreeView())
        {
            com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
            com.maddox.il2.engine.Actor aactor[] = com.maddox.il2.builder.Plugin.builder.selectedActors();
            com.maddox.il2.engine.IconDraw.setColor(255, 0, 0, 255);
            for(int k = 0; k < aactor.length; k++)
            {
                com.maddox.il2.engine.Actor actor1 = aactor[k];
                if(actor1 == null)
                    break;
                if(com.maddox.il2.builder.Plugin.builder.project2d(actor1.pos.getAbsPoint(), p2d))
                    if(actor1 == actor)
                    {
                        com.maddox.il2.engine.IconDraw.setColor(255, 255, 0, 255);
                        com.maddox.il2.engine.IconDraw.render(actor1, p2d.x, p2d.y);
                        com.maddox.il2.engine.IconDraw.setColor(255, 0, 0, 255);
                    } else
                    {
                        com.maddox.il2.engine.IconDraw.render(actor1, p2d.x, p2d.y);
                    }
            }

        }
        int i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
        int j = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
        if(i < 0 || i >= type.length || j < 0 || j >= type[i].item.length)
            return;
        if(i == type.length - 2 && matLabel != null)
        {
            com.maddox.JGP.Point3d point3d = null;
            if(com.maddox.il2.builder.Plugin.builder.isFreeView())
            {
                com.maddox.il2.engine.Actor actor2 = com.maddox.il2.builder.Plugin.builder.selectedActor();
                if(!com.maddox.il2.engine.Actor.isValid(actor2))
                    return;
                point3d = actor2.pos.getAbsPoint();
            } else
            {
                point3d = com.maddox.il2.builder.Plugin.builder.posScreenToLand(com.maddox.il2.builder.Plugin.builder.mousePosX, com.maddox.il2.builder.Plugin.builder.mousePosY, 0.0D, 0.10000000000000001D);
            }
            int l = com.maddox.il2.ai.World.land().WORLD2PIXX(point3d.x);
            int i1 = com.maddox.il2.ai.World.land().WORLD2PIXY(point3d.y);
            int j1 = squareTile;
            j1 |= 1;
            int k1 = l - j1 / 2;
            int l1 = i1 - j1 / 2;
            com.maddox.il2.ai.World.land();
            int i2 = com.maddox.il2.engine.Landscape.getSizeXpix();
            com.maddox.il2.ai.World.land();
            int j2 = com.maddox.il2.engine.Landscape.getSizeYpix();
            com.maddox.il2.engine.IconDraw.setColor(255, 255, 255, 255);
            for(int k2 = l1; k2 < l1 + j1; k2++)
            {
                for(int l2 = k1; l2 < k1 + j1; l2++)
                    if(k2 >= 0 && k2 < j2 && l2 >= 0 && l2 < i2)
                    {
                        double d = com.maddox.il2.ai.World.land().PIX2WORLDX(l2);
                        double d1 = com.maddox.il2.ai.World.land().PIX2WORLDY(k2);
                        double d2 = com.maddox.il2.ai.World.land().HQ(d, d1);
                        com.maddox.il2.builder.Plugin.builder.project2d(d, d1, d2, p2d);
                        com.maddox.il2.engine.IconDraw.render(matLabel, p2d.x, p2d.y);
                    }

            }

        }
    }

    public void changeType()
    {
        int i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
        int j = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
        if(i == type.length - 4)
        {
            newCurType = i;
            if(!setCurBridge())
                return;
            com.maddox.il2.objects.bridges.Bridge bridge = curBridge;
            char c = '\0';
            if(j == 0)
                c = '@';
            if(j == 1)
                c = ' ';
            if(j == 2)
                c = '\200';
            changeCurBridge(bridge.__indx, c, bridge.__x1, bridge.__y1, bridge.__x2, bridge.__y2, bridge.__offsetK);
            newCurType = i;
            newCurItem = j;
            return;
        }
        if(i < 0 || i >= type.length - 4)
            return;
        com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
        com.maddox.il2.engine.Loc loc = actor.pos.getAbs();
        insert(type[i].item[j].spawn_, loc, true);
        if(com.maddox.il2.builder.Plugin.builder.selectedActor() != actor)
        {
            allActors.remove(actor);
            actor.destroy();
        }
        newCurType = i;
        newCurItem = j;
    }

    public void changeType(boolean flag, boolean flag1)
    {
        int i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
        int j = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
        if(i == type.length - 4)
        {
            newCurType = i;
            if(flag1)
                bridgeType(flag);
            else
                bridgeLen(flag);
            return;
        }
        int k = com.maddox.il2.builder.PlMapActors.getFingerOfFullClassName(com.maddox.il2.builder.Plugin.builder.selectedActor());
        if(i < 0 || i >= type.length - 4)
            i = 0;
        if(j < 0 || j >= type[i].item.length || type[i].item[j].fingerOfFullClassName != k)
        {
            int i1 = 0;
            int l;
            for(l = 0; l < type.length; l++)
            {
                for(i1 = 0; i1 < type[l].item.length; i1++)
                    if(type[l].item[i1].fingerOfFullClassName == k)
                        break;

                if(i1 != type[l].item.length)
                    break;
            }

            if(l != type.length)
            {
                i = l;
                j = i1;
            } else
            {
                return;
            }
        }
        if(flag1)
        {
            if(flag)
            {
                if(--i < 0)
                    i = 0;
            } else
            if(++i == type.length - 4)
                i = type.length - 1 - 4;
            j = 0;
        } else
        if(flag)
        {
            if(--j < 0)
                j = 0;
        } else
        if(++j == type[i].item.length)
            j = type[i].item.length - 1;
        com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
        com.maddox.il2.engine.Loc loc = actor.pos.getAbs();
        insert(type[i].item[j].spawn_, loc, true);
        if(com.maddox.il2.builder.Plugin.builder.selectedActor() != actor)
            actor.destroy();
        newCurType = i;
        newCurItem = j;
        fillComboBox2(newCurType, newCurItem);
    }

    private com.maddox.il2.engine.Actor insert(com.maddox.il2.engine.ActorSpawn actorspawn, com.maddox.il2.engine.Loc loc, boolean flag)
    {
        spawnArg.clear();
        spawnArg.point = loc.getPoint();
        spawnArg.orient = loc.getOrient();
        com.maddox.il2.engine.Actor actor;
        actor = actorspawn.actorSpawn(spawnArg);
        com.maddox.il2.engine.IconDraw.create(actor);
        if(actor.icon == null)
            actor.icon = com.maddox.il2.engine.IconDraw.get("icons/unknown.mat");
        com.maddox.il2.builder.Plugin.builder.align(actor);
        com.maddox.rts.Property.set(actor, "builderSpawn", "");
        com.maddox.rts.Property.set(actor, "builderPlugin", this);
        allActors.put(actor, null);
        if(flag)
            com.maddox.il2.builder.Plugin.builder.setSelected(actor);
        return actor;
        java.lang.Exception exception;
        exception;
        java.lang.System.out.println(exception.getMessage());
        exception.printStackTrace();
        return null;
    }

    public void delete(com.maddox.il2.engine.Actor actor)
    {
        allActors.remove(actor);
        actor.destroy();
    }

    public void insert(com.maddox.il2.engine.Loc loc, boolean flag)
    {
        int i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
        int j = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
        if(i < 0 || i >= type.length || j < 0 || j >= type[i].item.length)
            return;
        if(i == type.length - 4)
            bridgeCreate(loc);
        else
        if(i == type.length - 3)
        {
            bTMapChanged = true;
            int k = com.maddox.il2.ai.World.land().WORLD2PIXX(loc.getPoint().x);
            int l = com.maddox.il2.ai.World.land().WORLD2PIXY(loc.getPoint().y);
            char c = '\0';
            if(j == 0)
                c = '@';
            if(j == 1)
                c = ' ';
            if(j == 2)
                c = '\200';
            int i1 = landMapT.intI(k, l) | c;
            landMapT.I(k, l, i1);
            com.maddox.il2.ai.World.land();
            com.maddox.il2.engine.Landscape.setPixelMapT(k, l, i1);
        } else
        if(i == type.length - 2)
        {
            if(j == 0)
                peekHeight(loc.getPoint());
            else
            if(j == 1)
                setHeight(loc.getPoint());
            else
            if(j == 2)
                blurHeight5(loc.getPoint(), 1);
        } else
        if(i == type.length - 1)
        {
            changeTile(loc.getPoint(), j);
        } else
        {
            com.maddox.il2.engine.ActorSpawn actorspawn = type[i].item[j].spawn_;
            insert(actorspawn, loc, flag);
        }
    }

    private void peekHeight(com.maddox.JGP.Point3d point3d)
    {
        int i = com.maddox.il2.ai.World.land().WORLD2PIXX(point3d.x);
        int j = com.maddox.il2.ai.World.land().WORLD2PIXY(point3d.y);
        fillHeight = landMapH.intI(i, j);
        com.maddox.il2.builder.Plugin.builder.tip("Peek Height code = " + fillHeight);
    }

    private void fillHeight(com.maddox.JGP.Point3d point3d, int i)
    {
        int j = com.maddox.il2.ai.World.land().WORLD2PIXX(point3d.x);
        int k = com.maddox.il2.ai.World.land().WORLD2PIXY(point3d.y);
        i |= 1;
        int l = j - i / 2;
        int i1 = k - i / 2;
        com.maddox.il2.ai.World.land();
        int j1 = com.maddox.il2.engine.Landscape.getSizeXpix();
        com.maddox.il2.ai.World.land();
        int k1 = com.maddox.il2.engine.Landscape.getSizeYpix();
        for(int l1 = i1; l1 < i1 + i; l1++)
        {
            for(int i2 = l; i2 < l + i; i2++)
                if(l1 >= 0 && l1 < k1 && i2 >= 0 && i2 < j1)
                    setHeight(i2, l1);

        }

    }

    private void setHeight(int i, int j)
    {
        int k = landMapT.intI(i, j);
        bHMapChanged = true;
        landMapH.I(i, j, fillHeight);
        com.maddox.il2.ai.World.land();
        com.maddox.il2.engine.Landscape.setPixelMapH(i, j, fillHeight);
    }

    private void setHeight(com.maddox.JGP.Point3d point3d)
    {
        int i = com.maddox.il2.ai.World.land().WORLD2PIXX(point3d.x);
        int j = com.maddox.il2.ai.World.land().WORLD2PIXY(point3d.y);
        setHeight(i, j);
    }

    private void incHeight(int i, int j, int k)
    {
        int l = landMapT.intI(i, j);
        bHMapChanged = true;
        int i1 = landMapH.intI(i, j) + k;
        if(i1 < 0)
            i1 = 0;
        if(i1 > 255)
            i1 = 255;
        landMapH.I(i, j, i1);
        com.maddox.il2.ai.World.land();
        com.maddox.il2.engine.Landscape.setPixelMapH(i, j, i1);
    }

    private void incHeight(com.maddox.JGP.Point3d point3d, int i)
    {
        int j = com.maddox.il2.ai.World.land().WORLD2PIXX(point3d.x);
        int k = com.maddox.il2.ai.World.land().WORLD2PIXY(point3d.y);
        incHeight(j, k, i);
    }

    private void incHeight(com.maddox.JGP.Point3d point3d, int i, int j)
    {
        int k = com.maddox.il2.ai.World.land().WORLD2PIXX(point3d.x);
        int l = com.maddox.il2.ai.World.land().WORLD2PIXY(point3d.y);
        i |= 1;
        int i1 = k - i / 2;
        int j1 = l - i / 2;
        com.maddox.il2.ai.World.land();
        int k1 = com.maddox.il2.engine.Landscape.getSizeXpix();
        com.maddox.il2.ai.World.land();
        int l1 = com.maddox.il2.engine.Landscape.getSizeYpix();
        for(int i2 = j1; i2 < j1 + i; i2++)
        {
            for(int j2 = i1; j2 < i1 + i; j2++)
                if(i2 >= 0 && i2 < l1 && j2 >= 0 && j2 < k1)
                    incHeight(j2, i2, j);

        }

    }

    public void msgMouseMove(int i, int j, int k)
    {
        int l = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
        int i1 = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
        if(l < 0 || l >= type.length || i1 < 0 || i1 >= type[l].item.length)
            return;
        if(l == type.length - 2)
        {
            if(k == 0)
            {
                if(com.maddox.rts.HotKeyCmdEnv.env(com.maddox.il2.builder.Builder.envName).get("fill").isActive())
                {
                    com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
                    if(com.maddox.il2.engine.Actor.isValid(actor))
                    {
                        com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
                        peekHeight(point3d);
                        fillHeight(point3d, squareTile);
                    }
                }
                return;
            }
            if(com.maddox.il2.builder.Plugin.builder.isFreeView())
            {
                com.maddox.il2.engine.Actor actor1 = com.maddox.il2.builder.Plugin.builder.selectedActor();
                if(com.maddox.il2.engine.Actor.isValid(actor1))
                {
                    com.maddox.JGP.Point3d point3d1 = actor1.pos.getAbsPoint();
                    if(i1 == 0)
                    {
                        incHeight(point3d1, squareTile, k <= 0 ? -1 : 1);
                    } else
                    {
                        peekHeight(point3d1);
                        fillHeight += k <= 0 ? -1 : 1;
                        if(fillHeight < 0)
                            fillHeight = 0;
                        if(fillHeight > 255)
                            fillHeight = 255;
                        fillHeight(point3d1, squareTile);
                        if(i1 == 2)
                            blurHeight5(point3d1, squareTile);
                    }
                }
            }
        }
    }

    public void msgMouseButton(int i, boolean flag)
    {
    }

    public void msgMouseAbsMove(int i, int j, int k)
    {
    }

    private int HCode2M(int i)
    {
        if(i < 64)
            return i;
        if(i < 96)
            return 64 + (i - 64) * 2;
        if(i < 128)
            return 128 + (i - 96) * 4;
        if(i < 160)
            return 256 + (i - 128) * 8;
        if(i < 192)
            return 512 + (i - 160) * 16;
        if(i < 224)
            return 1024 + (i - 192) * 32;
        else
            return 2048 + (i - 224) * 64;
    }

    private int M2HCode(int i)
    {
        if(i < 63)
            return i;
        if(i < 126)
            return (i - 64) / 2 + 64;
        if(i < 252)
            return (i - 128) / 4 + 96;
        if(i < 504)
            return (i - 256) / 8 + 128;
        if(i < 1008)
            return (i - 512) / 16 + 160;
        if(i < 2016)
        {
            return (i - 1024) / 32 + 192;
        } else
        {
            int j = (i - 2048) / 64 + 224;
            return j >= 256 ? '\377' : j;
        }
    }

    private void blurHeight5(com.maddox.JGP.Point3d point3d, int i)
    {
        int j = com.maddox.il2.ai.World.land().WORLD2PIXX(point3d.x);
        int k = com.maddox.il2.ai.World.land().WORLD2PIXY(point3d.y);
        i |= 1;
        int l = j - i / 2;
        int i1 = k - i / 2;
        com.maddox.il2.ai.World.land();
        int j1 = com.maddox.il2.engine.Landscape.getSizeXpix();
        com.maddox.il2.ai.World.land();
        int k1 = com.maddox.il2.engine.Landscape.getSizeYpix();
        for(int l1 = i1; l1 < i1 + i; l1++)
        {
            for(int i2 = l; i2 < l + i; i2++)
                if(l1 >= 0 && l1 < k1 && i2 >= 0 && i2 < j1)
                {
                    int j2 = HCode2M(landMapH.intI(i2, l1)) * 4;
                    int k2 = 4;
                    if(i2 > 0)
                    {
                        j2 += HCode2M(landMapH.intI(i2 - 1, l1));
                        k2++;
                    }
                    if(i2 + 1 < j1)
                    {
                        j2 += HCode2M(landMapH.intI(i2 + 1, l1));
                        k2++;
                    }
                    if(l1 > 0)
                    {
                        j2 += HCode2M(landMapH.intI(i2, l1 - 1));
                        k2++;
                    }
                    if(l1 + 1 < k1)
                    {
                        j2 += HCode2M(landMapH.intI(i2, l1 + 1));
                        k2++;
                    }
                    if(k2 > 4)
                    {
                        int l2 = fillHeight;
                        fillHeight = M2HCode(j2 / k2);
                        setHeight(i2, l1);
                        fillHeight = l2;
                    }
                }

        }

    }

    private void changeTiles(com.maddox.JGP.Point3d point3d, int i, int j)
    {
        int k = com.maddox.il2.ai.World.land().WORLD2PIXX(point3d.x);
        int l = com.maddox.il2.ai.World.land().WORLD2PIXY(point3d.y);
        j |= 1;
        int i1 = k - j / 2;
        int j1 = l - j / 2;
        com.maddox.il2.ai.World.land();
        int k1 = com.maddox.il2.engine.Landscape.getSizeXpix();
        com.maddox.il2.ai.World.land();
        int l1 = com.maddox.il2.engine.Landscape.getSizeYpix();
        for(int i2 = j1; i2 < j1 + j; i2++)
        {
            for(int j2 = i1; j2 < i1 + j; j2++)
                if(i2 >= 0 && i2 < l1 && j2 >= 0 && j2 < k1)
                    changeTile(point3d, j2, i2, i);

        }

    }

    private void changeTile(com.maddox.JGP.Point3d point3d, int i)
    {
        int j = com.maddox.il2.ai.World.land().WORLD2PIXX(point3d.x);
        int k = com.maddox.il2.ai.World.land().WORLD2PIXY(point3d.y);
        changeTile(point3d, j, k, i);
    }

    private void changeTile(com.maddox.JGP.Point3d point3d, int i, int j, int k)
    {
        bTMapChanged = true;
        int l = tileType(k);
        if(l == -1)
            return;
        int i1 = landMapT.intI(i, j);
        i1 = i1 & 0xffffffe0 | l;
        landMapT.I(i, j, i1);
        com.maddox.il2.ai.World.land();
        com.maddox.il2.engine.Landscape.setPixelMapT(i, j, i1);
        if(!com.maddox.il2.engine.Engine.dreamEnv().isSleep(point3d))
        {
            int ai[] = {
                i
            };
            com.maddox.il2.ai.World.land();
            int ai1[] = {
                com.maddox.il2.engine.Landscape.getSizeYpix() - 1 - j
            };
            com.maddox.il2.ai.World.cur().statics.msgDreamGlobal(false, 1, ai, ai1);
            com.maddox.il2.ai.World.cur().statics.msgDreamGlobal(true, 1, ai, ai1);
        }
    }

    private int tileType(int i)
    {
        byte byte0 = 0;
        switch(i)
        {
        case 0: // '\0'
        default:
            byte0 = 28;
            break;

        case 1: // '\001'
            byte0 = 0;
            break;

        case 2: // '\002'
            byte0 = 1;
            break;

        case 3: // '\003'
            byte0 = 2;
            break;

        case 4: // '\004'
            byte0 = 3;
            break;

        case 5: // '\005'
            byte0 = 4;
            break;

        case 6: // '\006'
            byte0 = 5;
            break;

        case 7: // '\007'
            byte0 = 6;
            break;

        case 8: // '\b'
            byte0 = 7;
            break;

        case 9: // '\t'
            byte0 = 8;
            break;

        case 10: // '\n'
            byte0 = 9;
            break;

        case 11: // '\013'
            byte0 = 10;
            break;

        case 12: // '\f'
            byte0 = 11;
            break;

        case 13: // '\r'
            byte0 = 12;
            break;

        case 14: // '\016'
            byte0 = 13;
            break;

        case 15: // '\017'
            byte0 = 14;
            break;

        case 16: // '\020'
            byte0 = 15;
            break;

        case 17: // '\021'
            byte0 = 16;
            break;

        case 18: // '\022'
            byte0 = 17;
            break;

        case 19: // '\023'
            byte0 = 18;
            break;

        case 20: // '\024'
            byte0 = 19;
            break;

        case 21: // '\025'
            byte0 = 20;
            break;

        case 22: // '\026'
            byte0 = 21;
            break;

        case 23: // '\027'
            byte0 = 22;
            break;

        case 24: // '\030'
            byte0 = 23;
            break;

        case 25: // '\031'
            byte0 = 24;
            break;

        case 26: // '\032'
            byte0 = 30;
            break;

        case 27: // '\033'
            byte0 = 31;
            break;
        }
        return byte0;
    }

    private void _doFill(com.maddox.JGP.Point3d point3d)
    {
        int i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
        int j = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
        if(i < 0 || i >= type.length || j < 0 || j >= type[i].item.length)
            return;
        if(i != type.length - 1)
        {
            return;
        } else
        {
            changeTile(point3d, j);
            return;
        }
    }

    private void _doFill()
    {
        int i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
        int j = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
        if(i < 0 || i >= type.length || j < 0 || j >= type[i].item.length)
            return;
        if(i != type.length - 1 && i != type.length - 2)
            return;
        _startFill.z = 0.0D;
        _endFill.z = 0.0D;
        double d = _endFill.distance(_startFill);
        int k = (int)java.lang.Math.round(d / 200D) + 1;
        float f = 1.0F / (float)k;
        for(int l = 0; l <= k; l++)
        {
            _stepFill.interpolate(_startFill, _endFill, (float)l * f);
            if(i == type.length - 1)
                changeTiles(_stepFill, j, squareTile);
            else
            if(j == 1)
                fillHeight(_stepFill, squareTile);
            else
            if(j == 2)
                blurHeight5(_stepFill, squareTile);
        }

    }

    public void beginFill(com.maddox.JGP.Point3d point3d)
    {
        _startFill.set(point3d);
    }

    public void fill(com.maddox.JGP.Point3d point3d)
    {
        _endFill.set(point3d);
        _doFill();
        _startFill.set(point3d);
    }

    public void endFill(com.maddox.JGP.Point3d point3d)
    {
    }

    public void fillPopUpMenu(com.maddox.gwindow.GWindowMenuPopUp gwindowmenupopup, com.maddox.JGP.Point3d point3d)
    {
        int i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
        int j = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
        if(i < 0 || i >= type.length || j < 0 || j >= type[i].item.length || i != type.length - 2 && i != type.length - 1)
        {
            gwindowmenupopup.addItem(new com.maddox.gwindow.GWindowMenuItem(gwindowmenupopup, "&Paste From File...", null) {

                public void execute()
                {
                    pasteFromFile(root);
                }

            }
);
            return;
        } else
        {
            com.maddox.gwindow.GWindowMenuItem gwindowmenuitem = gwindowmenupopup.addItem(new GWindowMenuItem(gwindowmenupopup, "&Fill Tile Size", null));
            gwindowmenuitem.subMenu = (com.maddox.gwindow.GWindowMenu)gwindowmenuitem.create(new GWindowMenu());
            gwindowmenuitem.subMenu.close(false);
            com.maddox.gwindow.GWindowMenuItem gwindowmenuitem1 = gwindowmenuitem.subMenu.addItem(new com.maddox.gwindow.GWindowMenuItem(gwindowmenuitem.subMenu, "&1", null) {

                public void execute()
                {
                    squareTile = 1;
                }

            }
);
            gwindowmenuitem1.bChecked = squareTile == 1;
            gwindowmenuitem1 = gwindowmenuitem.subMenu.addItem(new com.maddox.gwindow.GWindowMenuItem(gwindowmenuitem.subMenu, "&3", null) {

                public void execute()
                {
                    squareTile = 3;
                }

            }
);
            gwindowmenuitem1.bChecked = squareTile == 3;
            gwindowmenuitem1 = gwindowmenuitem.subMenu.addItem(new com.maddox.gwindow.GWindowMenuItem(gwindowmenuitem.subMenu, "&5", null) {

                public void execute()
                {
                    squareTile = 5;
                }

            }
);
            gwindowmenuitem1.bChecked = squareTile == 5;
            gwindowmenuitem1 = gwindowmenuitem.subMenu.addItem(new com.maddox.gwindow.GWindowMenuItem(gwindowmenuitem.subMenu, "&7", null) {

                public void execute()
                {
                    squareTile = 7;
                }

            }
);
            gwindowmenuitem1.bChecked = squareTile == 7;
            gwindowmenuitem1 = gwindowmenuitem.subMenu.addItem(new com.maddox.gwindow.GWindowMenuItem(gwindowmenuitem.subMenu, "&9", null) {

                public void execute()
                {
                    squareTile = 9;
                }

            }
);
            gwindowmenuitem1.bChecked = squareTile == 9;
            gwindowmenupopup.addItem("-", null);
            gwindowmenupopup.addItem(new com.maddox.gwindow.GWindowMenuItem(gwindowmenupopup, "&Paste From File...", null) {

                public void execute()
                {
                    pasteFromFile(root);
                }

            }
);
            gwindowmenupopup.addItem("-", null);
            gwindowmenupopup.addItem(new com.maddox.gwindow.GWindowMenuItem(gwindowmenupopup, "&Insert Pattern As Static...", null) {

                public void execute()
                {
                    doInsertPattern(root);
                }

            }
);
            gwindowmenupopup.addItem(new com.maddox.gwindow.GWindowMenuItem(gwindowmenupopup, "&Remove Pattern From Static...", null) {

                public void execute()
                {
                    doRemovePattern(root);
                }

            }
);
            return;
        }
    }

    public void delete(com.maddox.il2.engine.Loc loc)
    {
        int i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
        int j = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
        if(i < 0 || i >= type.length || j < 0 || j >= type[i].item.length)
            return;
        if(i != type.length - 4)
            if(i == type.length - 3)
            {
                bTMapChanged = true;
                int k = com.maddox.il2.ai.World.land().WORLD2PIXX(loc.getPoint().x);
                int l = com.maddox.il2.ai.World.land().WORLD2PIXY(loc.getPoint().y);
                char c = '\0';
                if(j == 0)
                    c = '@';
                if(j == 1)
                    c = ' ';
                if(j == 2)
                    c = '\200';
                int i1 = landMapT.intI(k, l) & ~c;
                landMapT.I(k, l, i1);
                com.maddox.il2.ai.World.land();
                com.maddox.il2.engine.Landscape.setPixelMapT(k, l, i1);
            } else
            if(i != type.length - 1);
    }

    public void cut()
    {
        if(com.maddox.il2.builder.Plugin.builder.isFreeView())
            return;
        copy(false);
        com.maddox.il2.engine.Actor aactor[] = com.maddox.il2.builder.Plugin.builder.selectedActors();
        for(int i = 0; i < aactor.length; i++)
        {
            com.maddox.il2.engine.Actor actor = aactor[i];
            if(actor == null)
                break;
            if(com.maddox.il2.engine.Actor.isValid(actor) && com.maddox.il2.builder.Plugin.builder.isMiltiSelected(actor))
                if(actor instanceof com.maddox.il2.builder.PAirdrome)
                {
                    com.maddox.il2.builder.PathAirdrome pathairdrome = (com.maddox.il2.builder.PathAirdrome)actor.getOwner();
                    if(pathairdrome.pointIndx((com.maddox.il2.builder.PAirdrome)actor) == 0)
                        pathairdrome.destroy();
                } else
                {
                    actor.destroy();
                }
        }

        com.maddox.il2.builder.Plugin.builder.selectedActorsValidate();
        com.maddox.il2.builder.Plugin.builder.repaint();
    }

    public void copy(boolean flag)
    {
        if(com.maddox.il2.builder.Plugin.builder.isFreeView())
            return;
        _clipSpawns.clear();
        _clipAirdroms.clear();
        _clipLoc.clear();
        int i = 0;
        _clipP0.x = _clipP0.y = _clipP0.z = 0.0D;
        com.maddox.il2.engine.Actor aactor[] = com.maddox.il2.builder.Plugin.builder.selectedActors();
        for(int j = 0; j < aactor.length; j++)
        {
            com.maddox.il2.engine.Actor actor = aactor[j];
            if(actor == null)
                break;
            if(com.maddox.il2.engine.Actor.isValid(actor) && com.maddox.il2.builder.Plugin.builder.isMiltiSelected(actor))
                if(actor instanceof com.maddox.il2.builder.PAirdrome)
                {
                    com.maddox.il2.builder.PAirdrome pairdrome = (com.maddox.il2.builder.PAirdrome)actor;
                    _clipP0.add(actor.pos.getAbsPoint());
                    i++;
                    com.maddox.il2.builder.PathAirdrome pathairdrome = (com.maddox.il2.builder.PathAirdrome)pairdrome.getOwner();
                    if(pathairdrome.pointIndx(pairdrome) == 0)
                        _clipAirdroms.add(com.maddox.il2.builder.PathAirdrome.toSpawnString(pathairdrome));
                } else
                {
                    com.maddox.il2.engine.Loc loc = new Loc();
                    actor.pos.getAbs(loc);
                    _clipSpawns.add(com.maddox.rts.Spawn.get_WithSoftClass(com.maddox.il2.builder.PlMapActors.getFullClassName(actor)));
                    _clipLoc.add(loc);
                    _clipP0.add(loc.getPoint());
                    i++;
                }
        }

        if(i > 1)
        {
            _clipP0.x /= i;
            _clipP0.y /= i;
            _clipP0.z /= i;
        }
        if(flag)
            com.maddox.il2.builder.Plugin.builder.selectActorsClear();
        com.maddox.il2.builder.Plugin.builder.repaint();
    }

    public void paste()
    {
        if(com.maddox.il2.builder.Plugin.builder.isFreeView())
            return;
        com.maddox.il2.builder.Plugin.builder.selectActorsClear();
        int i = _clipSpawns.size();
        if(i == 0 && _clipAirdroms.size() == 0)
            return;
        com.maddox.JGP.Point3d point3d = com.maddox.il2.builder.Plugin.builder.mouseWorldPos();
        com.maddox.il2.engine.Loc loc = new Loc();
        com.maddox.JGP.Point3d point3d1 = new Point3d();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.Loc loc1 = (com.maddox.il2.engine.Loc)_clipLoc.get(j);
            point3d1.sub(loc1.getPoint(), _clipP0);
            point3d1.add(point3d);
            loc.set(point3d1, loc1.getOrient());
            com.maddox.il2.engine.Actor actor = insert((com.maddox.il2.engine.ActorSpawn)_clipSpawns.get(j), loc, false);
            com.maddox.il2.builder.Plugin.builder.selectActorsAdd(actor);
        }

        i = _clipAirdroms.size();
        for(int k = 0; k < i; k++)
        {
            java.lang.String s = (java.lang.String)_clipAirdroms.get(k);
            com.maddox.il2.builder.PathAirdrome pathairdrome = (com.maddox.il2.builder.PathAirdrome)com.maddox.rts.CmdEnv.top().exec(s);
            int l = pathairdrome.points();
            for(int i1 = 0; i1 < l; i1++)
            {
                com.maddox.il2.builder.PPoint ppoint = pathairdrome.point(i1);
                ppoint.pos.getAbs(point3d1);
                point3d1.sub(_clipP0);
                point3d1.add(point3d);
                ppoint.pos.setAbs(point3d1);
                ppoint.pos.reset();
                com.maddox.il2.builder.Plugin.builder.selectActorsAdd(ppoint);
            }

        }

        com.maddox.il2.builder.Plugin.builder.repaint();
    }

    private void pasteFromFile(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        if(dlgPasteFrom == null)
            dlgPasteFrom = new com.maddox.gwindow.GWindowFileOpen(gwindowroot, true, "Paste From File ...", "maps", new com.maddox.gwindow.GFileFilter[] {
                new GFileFilterName("All files", new java.lang.String[] {
                    "*"
                })
            }) {

                public void result(java.lang.String s)
                {
                    if(s != null)
                        load("maps/" + s, false, true);
                }

            }
;
        else
            dlgPasteFrom.activateWindow();
    }

    private void fillComboBox1()
    {
        startComboBox1 = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.size();
        for(int i = 0; i < type.length; i++)
            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.add(type[i].name);

        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.setSelected(0, true, false);
    }

    private void fillComboBox2(int i, int j)
    {
        if(i < startComboBox1 || i >= startComboBox1 + type.length)
            return;
        if(com.maddox.il2.builder.Plugin.builder.wSelect.curFilledType != i)
        {
            com.maddox.il2.builder.Plugin.builder.wSelect.curFilledType = i;
            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.clear(false);
            for(int k = 0; k < type[i].item.length; k++)
                com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.add(type[i].item[k].name);

            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.setSelected(i, true, false);
        }
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.setSelected(j, true, false);
    }

    public void syncSelector()
    {
        com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
        int i = com.maddox.il2.builder.PlMapActors.getFingerOfFullClassName(actor);
        for(int j = 0; j < type.length; j++)
        {
            for(int k = 0; k < type[j].item.length; k++)
                if(i == type[j].item[k].fingerOfFullClassName)
                {
                    fillComboBox2(j, k);
                    return;
                }

        }

        if(actor instanceof com.maddox.il2.objects.bridges.Bridge)
        {
            com.maddox.il2.objects.bridges.Bridge bridge = (com.maddox.il2.objects.bridges.Bridge)actor;
            int l = bridge.type();
            byte byte0 = 0;
            switch(l)
            {
            case 2: // '\002'
                byte0 = 0;
                break;

            case 1: // '\001'
                byte0 = 1;
                break;

            case 0: // '\0'
                byte0 = 2;
                break;
            }
            fillComboBox2(type.length - 4, byte0);
            return;
        } else
        {
            return;
        }
    }

    public void configure()
    {
        matLabel = com.maddox.il2.engine.Mat.New("icons/label.mat");
        com.maddox.il2.builder.Plugin.builder.bMultiSelect = true;
        if(sectFile == null)
            throw new RuntimeException("PlMapActors: field 'sectFile' not defined");
        com.maddox.rts.SectFile sectfile = new SectFile(sectFile, 0);
        int i = sectfile.sections();
        if(i <= 0)
            throw new RuntimeException("PlMapActors: file '" + sectFile + "' is empty");
        int j = 0;
        for(int k = 0; k < i; k++)
            if(sectfile.sectionName(k).equals("***"))
                j++;

        if(j <= 0)
            throw new RuntimeException("PlMapActors: No type groups in file '" + sectFile + "'");
        type = new com.maddox.il2.builder.Type[j + 4];
        int l = sectfile.sectionIndex("***");
        for(int i1 = 0; i1 < j; i1++)
        {
            int j1 = (i1 < j - 1 ? sectfile.sectionIndex("***", l + 1) : i) - 1 - l;
            if(j1 <= 0)
                throw new RuntimeException("PlMapActors: Empty group in file '" + sectFile + "'");
            int k1 = sectfile.varIndex(l, "Title");
            if(k1 < 0)
                throw new RuntimeException("PlMapActors: No 'Title' in file '" + sectFile + "', section '***' (#" + l + ")");
            java.lang.String s = sectfile.value(l, k1);
            com.maddox.il2.builder.Item aitem1[] = new com.maddox.il2.builder.Item[j1];
            for(int l1 = 0; l1 < j1; l1++)
            {
                java.lang.String s1 = sectfile.sectionName(l + 1 + l1);
                int i2 = s1.indexOf(' ');
                if(i2 > 0)
                    s1 = s1.substring(0, i2);
                java.lang.String s2 = sectfile.get(s1, "Title");
                if(s2 == null)
                {
                    s2 = sectfile.get(s1, "equals");
                    if(s2 == null)
                        throw new RuntimeException("PlMapActors: No 'Title' in file '" + sectFile + "', section '" + s1 + "'");
                    int j2 = sectfile.sectionIndex(s2);
                    if(j2 < 0)
                        throw new RuntimeException("PlMapActors: Unknown 'equals' in file '" + sectFile + "', section '" + s1 + "'");
                    java.lang.String s4 = sectfile.sectionName(j2);
                    s2 = sectfile.get(s4, "Title");
                    if(s2 == null)
                        throw new RuntimeException("PlMapActors: No 'Title' in file '" + sectFile + "', section '" + s4 + "'");
                }
                java.lang.String s3 = s1;
                java.lang.String s5 = "";
                int k2 = s1.lastIndexOf('$');
                if(k2 >= 0)
                {
                    s3 = s1.substring(0, k2);
                    s5 = s1.substring(k2 + 1);
                }
                java.lang.Class class1 = null;
                try
                {
                    class1 = com.maddox.rts.ObjIO.classForName(s3);
                }
                catch(java.lang.Exception exception)
                {
                    throw new RuntimeException("PlMapActors: class '" + s3 + "' not found");
                }
                if(k2 >= 0)
                    s1 = class1.getName() + "$" + s5;
                else
                    s1 = class1.getName();
                aitem1[l1] = new Item(s2, s1);
            }

            type[i1] = new Type(s, aitem1);
            l += 1 + j1;
        }

        com.maddox.il2.builder.Item aitem[] = new com.maddox.il2.builder.Item[3];
        aitem[0] = new Item("Rail");
        aitem[1] = new Item("Country");
        aitem[2] = new Item("Highway");
        type[j] = new Type("Bridge", aitem);
        aitem = new com.maddox.il2.builder.Item[3];
        aitem[0] = new Item("Rail");
        aitem[1] = new Item("Country");
        aitem[2] = new Item("Highway");
        type[j + 1] = new Type("Road", aitem);
        aitem = new com.maddox.il2.builder.Item[3];
        aitem[0] = new Item("Peek");
        aitem[1] = new Item("Fill");
        aitem[2] = new Item("Blur");
        type[j + 2] = new Type("Height", aitem);
        aitem = new com.maddox.il2.builder.Item[28];
        aitem[0] = new Item("WATER");
        aitem[1] = new Item("LowLand");
        aitem[2] = new Item("LowLand1");
        aitem[3] = new Item("LowLand2");
        aitem[4] = new Item("LowLand3");
        aitem[5] = new Item("MidLand");
        aitem[6] = new Item("MidLand1");
        aitem[7] = new Item("MidLand2");
        aitem[8] = new Item("MidLand3");
        aitem[9] = new Item("Mount");
        aitem[10] = new Item("Mount1");
        aitem[11] = new Item("Mount2");
        aitem[12] = new Item("Mount3");
        aitem[13] = new Item("Country");
        aitem[14] = new Item("Country1");
        aitem[15] = new Item("Country2");
        aitem[16] = new Item("Country3");
        aitem[17] = new Item("City");
        aitem[18] = new Item("City1");
        aitem[19] = new Item("City2");
        aitem[20] = new Item("City3");
        aitem[21] = new Item("Airfield");
        aitem[22] = new Item("Airfield1");
        aitem[23] = new Item("Airfield2");
        aitem[24] = new Item("Airfield3");
        aitem[25] = new Item("Wood");
        aitem[26] = new Item("CoastRiver");
        aitem[27] = new Item("CoastSea");
        type[j + 3] = new Type("Tile", aitem);
    }

    public void selectAll()
    {
        for(java.util.Map.Entry entry = allActors.nextEntry(null); entry != null; entry = allActors.nextEntry(entry))
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getKey();
            if(com.maddox.il2.engine.Actor.isValid(actor) && actor.isDrawing())
                com.maddox.il2.builder.Plugin.builder.selectActorsAdd(actor);
        }

    }

    void viewUpdate()
    {
        for(java.util.Map.Entry entry = allActors.nextEntry(null); entry != null; entry = allActors.nextEntry(entry))
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getKey();
            if(com.maddox.il2.engine.Actor.isValid(actor))
            {
                java.lang.Integer integer = new Integer(com.maddox.il2.builder.PlMapActors.getFingerOfFullClassName(actor));
                actor.drawing(viewClassFingers.containsKey(integer));
            }
        }

        if(com.maddox.il2.engine.Actor.isValid(com.maddox.il2.builder.Plugin.builder.selectedActor()) && !com.maddox.il2.builder.Plugin.builder.selectedActor().isDrawing())
            com.maddox.il2.builder.Plugin.builder.setSelected(null);
        com.maddox.il2.builder.Plugin.builder.selectedActorsValidate();
        if(!com.maddox.il2.builder.Plugin.builder.isFreeView())
            com.maddox.il2.builder.Plugin.builder.repaint();
    }

    void viewType(int i, boolean flag)
    {
        int j = type[i].item.length;
        for(int k = 0; k < j; k++)
        {
            java.lang.Integer integer = new Integer(type[i].item[k].fingerOfFullClassName);
            if(flag)
                viewClassFingers.put(integer, null);
            else
                viewClassFingers.remove(integer);
        }

        viewUpdate();
    }

    void viewType(int i)
    {
        if(i >= type.length - 4 - 1)
        {
            return;
        } else
        {
            viewType(i, viewType[i].bChecked);
            return;
        }
    }

    public void viewTypeAll(boolean flag)
    {
        if(flag)
        {
            for(int i = 0; i < type.length - 4; i++)
                if(!viewType[i].bChecked)
                {
                    viewType[i].bChecked = true;
                    viewType(i, true);
                }

        } else
        {
            for(int j = 0; j < type.length - 4; j++)
                if(viewType[j].bChecked)
                {
                    viewType[j].bChecked = false;
                    viewType(j, false);
                }

        }
        viewBridge(flag);
        viewRunaway(flag);
    }

    void viewBridge(boolean flag)
    {
        com.maddox.il2.builder.Plugin.builder.conf.bViewBridge = flag;
        viewBridge.bChecked = com.maddox.il2.builder.Plugin.builder.conf.bViewBridge;
    }

    void viewBridge()
    {
        viewBridge(!com.maddox.il2.builder.Plugin.builder.conf.bViewBridge);
    }

    void viewRunaway(boolean flag)
    {
        com.maddox.il2.builder.Plugin.builder.conf.bViewRunaway = flag;
        viewRunaway.bChecked = com.maddox.il2.builder.Plugin.builder.conf.bViewRunaway;
    }

    void viewRunaway()
    {
        viewRunaway(!com.maddox.il2.builder.Plugin.builder.conf.bViewRunaway);
    }

    public void createGUI()
    {
        fillComboBox1();
        fillComboBox2(0, 0);
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.addNotifyListener(new com.maddox.gwindow.GNotifyListener() {

            public boolean notify(com.maddox.gwindow.GWindow gwindow, int j, int k)
            {
                int l = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
                if(l >= 0 && j == 2)
                    fillComboBox2(l, 0);
                return false;
            }

        }
);
        viewType = new com.maddox.il2.builder.ViewItem[type.length];
        for(int i = 0; i < type.length - 4; i++)
        {
            com.maddox.il2.builder.ViewItem viewitem = (com.maddox.il2.builder.ViewItem)com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(new ViewItem(i, com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, "show " + type[i].name, null));
            viewitem.bChecked = true;
            viewType[i] = viewitem;
            viewType(i, true);
        }

        com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem("-", null);
        viewBridge = com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, "show Bridge", null) {

            public void execute()
            {
                viewBridge();
            }

        }
);
        viewBridge.bChecked = com.maddox.il2.builder.Plugin.builder.conf.bViewBridge;
        viewRunaway = com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, "show Runway", null) {

            public void execute()
            {
                viewRunaway();
            }

        }
);
        viewRunaway.bChecked = com.maddox.il2.builder.Plugin.builder.conf.bViewRunaway;
        com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem("-", null);
        com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, "&Show All", null) {

            public void execute()
            {
                viewTypeAll(true);
            }

        }
);
        com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, "&Hide All", null) {

            public void execute()
            {
                viewTypeAll(false);
            }

        }
);
        mLoad = com.maddox.il2.builder.Plugin.builder.mFile.subMenu.addItem(1, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mFile.subMenu, "Load", null) {

            public void execute()
            {
                if(com.maddox.il2.builder.PlMapLoad.mapFileName() != null)
                {
                    com.maddox.il2.builder.PlMapLoad plmapload = (com.maddox.il2.builder.PlMapLoad)com.maddox.il2.builder.Plugin.getPlugin("MapLoad");
                    com.maddox.il2.builder.PlMapLoad.Land land = com.maddox.il2.builder.PlMapLoad.getLandLoaded();
                    plmapload.mapUnload();
                    plmapload.guiMapLoad(land);
                }
            }

        }
);
        mSave = com.maddox.il2.builder.Plugin.builder.mFile.subMenu.addItem(2, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mFile.subMenu, "Save", null) {

            public void execute()
            {
                save();
            }

        }
);
        com.maddox.il2.builder.Plugin.builder.mFile.subMenu.addItem(3, "-", null);
        mLoadAs = com.maddox.il2.builder.Plugin.builder.mFile.subMenu.addItem(4, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mFile.subMenu, "Load As ...", null) {

            public void execute()
            {
                if(dlgLoadAs == null)
                    dlgLoadAs = new com.maddox.gwindow.GWindowFileOpen(root, true, "Load As ...", "maps", new com.maddox.gwindow.GFileFilter[] {
                        new GFileFilterName("All files", new java.lang.String[] {
                            "*"
                        })
                    }) {

                        public void result(java.lang.String s)
                        {
                            if(s != null)
                                loadAs("maps/" + s);
                        }

                    }
;
                else
                    dlgLoadAs.activateWindow();
            }


        }
);
        mLoadSpawn = com.maddox.il2.builder.Plugin.builder.mFile.subMenu.addItem(5, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mFile.subMenu, "Load Spawn ...", null) {

            public void execute()
            {
                if(dlgLoadSpawn == null)
                    dlgLoadSpawn = new com.maddox.gwindow.GWindowFileOpen(root, true, "Load Spawn ...", "maps", new com.maddox.gwindow.GFileFilter[] {
                        new GFileFilterName("All files", new java.lang.String[] {
                            "*"
                        })
                    }) {

                        public void result(java.lang.String s)
                        {
                            if(s != null)
                                loadSpawn("maps/" + s);
                        }

                    }
;
                else
                    dlgLoadSpawn.activateWindow();
            }


        }
);
        mSaveAs = com.maddox.il2.builder.Plugin.builder.mFile.subMenu.addItem(6, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mFile.subMenu, "Save As ...", null) {

            public void execute()
            {
                if(dlgSaveAs == null)
                    dlgSaveAs = new com.maddox.gwindow.GWindowFileSaveAs(root, true, "Save As ...", "maps", new com.maddox.gwindow.GFileFilter[] {
                        new GFileFilterName("All files", new java.lang.String[] {
                            "*"
                        })
                    }) {

                        public void result(java.lang.String s)
                        {
                            if(s != null)
                                saveAs("maps/" + s);
                        }

                    }
;
                else
                    dlgSaveAs.activateWindow();
            }


        }
);
        mSaveSpawn = com.maddox.il2.builder.Plugin.builder.mFile.subMenu.addItem(7, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mFile.subMenu, "Save As Spawn ...", null) {

            public void execute()
            {
                if(dlgSaveSpawn == null)
                    dlgSaveSpawn = new com.maddox.gwindow.GWindowFileSaveAs(root, true, "Save As Spawn ...", "maps", new com.maddox.gwindow.GFileFilter[] {
                        new GFileFilterName("All files", new java.lang.String[] {
                            "*"
                        })
                    }) {

                        public void result(java.lang.String s)
                        {
                            if(s != null)
                                saveSpawn("maps/" + s);
                        }

                    }
;
                else
                    dlgSaveSpawn.activateWindow();
            }


        }
);
        com.maddox.il2.builder.Plugin.builder.mFile.subMenu.addItem(8, "-", null);
        mCreateBridges = com.maddox.il2.builder.Plugin.builder.mFile.subMenu.addItem(9, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mFile.subMenu, "CreateBridges ...", null) {

            public void execute()
            {
                new com.maddox.gwindow.GWindowMessageBox(root, 20F, true, "CreateBridges ...", "Start process creating bridges ?", 1, 0.0F) {

                    public void result(int j)
                    {
                        if(j == 3)
                        {
                            loadAs(null);
                            com.maddox.il2.builder.PlMapLoad.bDrawNumberBridge = true;
                        }
                    }

                }
;
            }


        }
);
        com.maddox.il2.builder.Plugin.builder.mFile.subMenu.addItem(10, "-", null);
        com.maddox.il2.builder.Plugin.builder.mFile.subMenu.addItem(11, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mFile.subMenu, "Clear Roads ...", null) {

            public void execute()
            {
                new com.maddox.gwindow.GWindowMessageBox(root, 20F, true, "Clear Roads ...", "Start process clear roads ?", 1, 0.0F) {

                    public void result(int j)
                    {
                        if(j == 3)
                            doClearRoads();
                    }

                }
;
            }


        }
);
        com.maddox.il2.builder.Plugin.builder.mFile.subMenu.addItem(12, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mFile.subMenu, "Remove Roads ...", null) {

            public void execute()
            {
                new com.maddox.gwindow.GWindowMessageBox(root, 20F, true, "Remove Roads ...", "Start process remove roads ?", 1, 0.0F) {

                    public void result(int j)
                    {
                        if(j == 3)
                            doRemoveRoads();
                    }

                }
;
            }


        }
);
        com.maddox.il2.builder.Plugin.builder.mFile.subMenu.addItem(13, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mFile.subMenu, "Clear Water ...", null) {

            public void execute()
            {
                new com.maddox.gwindow.GWindowMessageBox(root, 20F, true, "Clear Water ...", "Start process clear water ?", 1, 0.0F) {

                    public void result(int j)
                    {
                        if(j == 3)
                            doClearWater();
                    }

                }
;
            }


        }
);
    }

    public void start()
    {
        com.maddox.rts.MsgAddListener.post(64, com.maddox.rts.RTSConf.cur.mouse, this, null);
        com.maddox.rts.HotKeyCmdEnv.setCurrentEnv(com.maddox.il2.builder.Builder.envName);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "cut") {

            public void begin()
            {
                cut();
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "copy") {

            public void begin()
            {
                copy(true);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "paste") {

            public void begin()
            {
                paste();
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "bridgeLeft") {

            public void begin()
            {
                bridgeRotate(true);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "bridgeRight") {

            public void begin()
            {
                bridgeRotate(false);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "bridgeOffset+") {

            public void begin()
            {
                bridgeOffset(true);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "bridgeOffset-") {

            public void begin()
            {
                bridgeOffset(false);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectLowLand") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 1);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectLowLand1") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 2);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectLowLand2") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 3);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectLowLand3") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 4);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectMidLand") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 5);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectMidLand1") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 6);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectMidLand2") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 7);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectMidLand3") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 8);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectMount") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 9);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectMount1") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 10);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectMount2") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 11);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectMount3") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 12);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectCountry") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 13);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectCountry1") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 14);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectCountry2") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 15);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectCountry3") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 16);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectCity") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 17);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectCity1") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 18);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectCity2") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 19);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectCity3") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 20);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectAirfield") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 21);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectAirfield1") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 22);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectAirfield2") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 23);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectAirfield3") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 24);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectWood") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 25);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectCoastRiver") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 26);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectCoastSea") {

            public void begin()
            {
                setSelected(sName, type.length - 1, 27);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectRoadRail") {

            public void begin()
            {
                setSelected(sName, type.length - 3, 0);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectRoadCountry") {

            public void begin()
            {
                setSelected(sName, type.length - 3, 1);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectRoadHighway") {

            public void begin()
            {
                setSelected(sName, type.length - 3, 2);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectBridgeRail") {

            public void begin()
            {
                setSelected(sName, type.length - 4, 0);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectBridgeCountry") {

            public void begin()
            {
                setSelected(sName, type.length - 4, 1);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectBridgeHighway") {

            public void begin()
            {
                setSelected(sName, type.length - 4, 2);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectHeightPeek") {

            public void begin()
            {
                setSelected(sName, type.length - 2, 0);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectHeightFill") {

            public void begin()
            {
                setSelected(sName, type.length - 2, 1);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectHeightBlur") {

            public void begin()
            {
                setSelected(sName, type.length - 2, 2);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "incHeight") {

            public void begin()
            {
                fillHeight++;
                if(fillHeight > 255)
                    fillHeight = 255;
                com.maddox.il2.builder.Plugin.builder.tip("Peek Height code = " + fillHeight);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "decHeight") {

            public void begin()
            {
                fillHeight--;
                if(fillHeight < 0)
                    fillHeight = 0;
                com.maddox.il2.builder.Plugin.builder.tip("Peek Height code = " + fillHeight);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "incHeight10") {

            public void begin()
            {
                fillHeight+= = 10;
                if(fillHeight > 255)
                    fillHeight = 255;
                com.maddox.il2.builder.Plugin.builder.tip("Peek Height code = " + fillHeight);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "decHeight10") {

            public void begin()
            {
                fillHeight-= = 10;
                if(fillHeight < 0)
                    fillHeight = 0;
                com.maddox.il2.builder.Plugin.builder.tip("Peek Height code = " + fillHeight);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "incCurHeight") {

            public void begin()
            {
                if(!com.maddox.il2.builder.Plugin.builder.isFreeView())
                    return;
                com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
                if(!com.maddox.il2.engine.Actor.isValid(actor))
                    return;
                com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
                peekHeight(point3d);
                fillHeight++;
                if(fillHeight > 255)
                    fillHeight = 255;
                com.maddox.il2.builder.Plugin.builder.tip("Peek Cur Height code = " + fillHeight);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "decCurHeight") {

            public void begin()
            {
                if(!com.maddox.il2.builder.Plugin.builder.isFreeView())
                    return;
                com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
                if(!com.maddox.il2.engine.Actor.isValid(actor))
                    return;
                com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
                peekHeight(point3d);
                fillHeight--;
                if(fillHeight < 0)
                    fillHeight = 0;
                com.maddox.il2.builder.Plugin.builder.tip("Peek Cur Height code = " + fillHeight);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "incCurHeight10") {

            public void begin()
            {
                if(!com.maddox.il2.builder.Plugin.builder.isFreeView())
                    return;
                com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
                if(!com.maddox.il2.engine.Actor.isValid(actor))
                    return;
                com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
                peekHeight(point3d);
                fillHeight+= = 10;
                if(fillHeight > 255)
                    fillHeight = 255;
                com.maddox.il2.builder.Plugin.builder.tip("Peek Cur Height code = " + fillHeight);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "decCurHeight10") {

            public void begin()
            {
                if(!com.maddox.il2.builder.Plugin.builder.isFreeView())
                    return;
                com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
                if(!com.maddox.il2.engine.Actor.isValid(actor))
                    return;
                com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
                peekHeight(point3d);
                fillHeight-= = 10;
                if(fillHeight < 0)
                    fillHeight = 0;
                com.maddox.il2.builder.Plugin.builder.tip("Peek Cur Height code = " + fillHeight);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "square1") {

            public void begin()
            {
                setQuare(0);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "square3") {

            public void begin()
            {
                setQuare(1);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "square5") {

            public void begin()
            {
                setQuare(2);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "square7") {

            public void begin()
            {
                setQuare(3);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "square9") {

            public void begin()
            {
                setQuare(4);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "damage") {

            public void begin()
            {
                if(com.maddox.il2.engine.Actor.isValid(com.maddox.il2.builder.Plugin.builder.selectedActor()))
                {
                    com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actor.setDiedFlag(!actor.getDiedFlag());
                }
            }

        }
);
    }

    private void setQuare(int i)
    {
        squareTile = 1 + i * 2;
        com.maddox.il2.builder.Plugin.builder.tip("Set Square size = " + squareTile);
    }

    private void setSelected(java.lang.String s, int i, int j)
    {
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.setSelected(i, true, true);
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.setSelected(j, true, true);
        com.maddox.il2.builder.Plugin.builder.tip(s);
    }

    private void bridgeCreate(com.maddox.il2.engine.Loc loc)
    {
        int i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
        int j = com.maddox.il2.ai.World.land().WORLD2PIXX(loc.getPoint().x);
        int k = com.maddox.il2.ai.World.land().WORLD2PIXY(loc.getPoint().y);
        char c = '\0';
        if(i == 0)
            c = '@';
        if(i == 1)
            c = ' ';
        if(i == 2)
            c = '\200';
        com.maddox.il2.objects.bridges.Bridge bridge = new Bridge(newIndxBridge, c, j, k, j + 1, k, 0.0F);
        com.maddox.rts.Property.set(bridge, "builderSpawn", "");
        com.maddox.il2.builder.PlMapLoad.bridgeActors.add(bridge);
        newIndxBridge++;
        bBridgeChanged = true;
    }

    private boolean setCurBridge()
    {
        if(com.maddox.il2.builder.Plugin.builder.selectedActor() == null || !(com.maddox.il2.builder.Plugin.builder.selectedActor() instanceof com.maddox.il2.objects.bridges.Bridge))
            return false;
        curBridge = (com.maddox.il2.objects.bridges.Bridge)com.maddox.il2.builder.Plugin.builder.selectedActor();
        indxBridge = com.maddox.il2.builder.PlMapLoad.bridgeActors.indexOf(curBridge);
        return com.maddox.il2.engine.Actor.isValid(curBridge) && indxBridge >= 0;
    }

    private void changeCurBridge(int i, int j, int k, int l, int i1, int j1, float f)
    {
        com.maddox.il2.objects.bridges.Bridge bridge = curBridge;
        bridge.destroy();
        bridge = new Bridge(i, j, k, l, i1, j1, f);
        com.maddox.rts.Property.set(bridge, "builderSpawn", "");
        com.maddox.il2.builder.PlMapLoad.bridgeActors.set(indxBridge, bridge);
        com.maddox.il2.builder.Plugin.builder.setSelected(bridge);
        com.maddox.il2.builder.Plugin.builder.repaint();
        bBridgeChanged = true;
    }

    private void bridgeType(boolean flag)
    {
        if(!setCurBridge())
            return;
        com.maddox.il2.objects.bridges.Bridge bridge = curBridge;
        int i = bridge.__type;
        switch(bridge.__type)
        {
        case 64: // '@'
            if(flag)
            {
                i = 128;
                newCurItem = 2;
            } else
            {
                i = 32;
                newCurItem = 1;
            }
            break;

        case 32: // ' '
            if(flag)
            {
                i = 64;
                newCurItem = 0;
            } else
            {
                i = 128;
                newCurItem = 2;
            }
            break;

        case 128: 
            if(flag)
            {
                i = 32;
                newCurItem = 1;
            } else
            {
                i = 64;
                newCurItem = 0;
            }
            break;

        default:
            return;
        }
        changeCurBridge(bridge.__indx, i, bridge.__x1, bridge.__y1, bridge.__x2, bridge.__y2, bridge.__offsetK);
        fillComboBox2(newCurType, newCurItem);
    }

    private void bridgeOffset(boolean flag)
    {
        if(!setCurBridge())
            return;
        com.maddox.il2.objects.bridges.Bridge bridge = curBridge;
        float f = bridge.__offsetK;
        if(flag)
        {
            f += 0.02F;
            if(f > 0.4F)
                return;
        } else
        {
            f -= 0.02F;
            if(f < -0.4F)
                return;
        }
        changeCurBridge(bridge.__indx, bridge.__type, bridge.__x1, bridge.__y1, bridge.__x2, bridge.__y2, f);
    }

    private void bridgeLen(boolean flag)
    {
        if(!setCurBridge())
            return;
        com.maddox.il2.objects.bridges.Bridge bridge = curBridge;
        int i = bridge.__x2 - bridge.__x1;
        int j = bridge.__y2 - bridge.__y1;
        if(flag)
        {
            if(i < 0)
                i = -1;
            else
            if(i > 0)
                i = 1;
            if(j < 0)
                j = -1;
            else
            if(j > 0)
                j = 1;
        } else
        {
            if(i < 0)
                i = 1;
            else
            if(i > 0)
                i = -1;
            if(j < 0)
                j = 1;
            else
            if(j > 0)
                j = -1;
        }
        int k = bridge.__x2 + i;
        int l = bridge.__y2 + j;
        if(k == bridge.__x1 && l == bridge.__y1)
        {
            return;
        } else
        {
            changeCurBridge(bridge.__indx, bridge.__type, bridge.__x1, bridge.__y1, k, l, bridge.__offsetK);
            return;
        }
    }

    private void bridgeRotate(boolean flag)
    {
        if(!setCurBridge())
            return;
        com.maddox.il2.objects.bridges.Bridge bridge = curBridge;
        int i = bridge.__x2 - bridge.__x1;
        int j = bridge.__y2 - bridge.__y1;
        if(flag)
        {
            if(i > 0)
            {
                if(j > 0)
                    j = 0;
                else
                if(j < 0)
                    i = 0;
                else
                    j = -i;
            } else
            if(i < 0)
            {
                if(j > 0)
                    i = 0;
                else
                if(j < 0)
                    j = 0;
                else
                    j = -i;
            } else
            if(j > 0)
                i = j;
            else
            if(j < 0)
                i = j;
        } else
        if(i > 0)
        {
            if(j > 0)
                i = 0;
            else
            if(j < 0)
                j = 0;
            else
                j = i;
        } else
        if(i < 0)
        {
            if(j > 0)
                j = 0;
            else
            if(j < 0)
                i = 0;
            else
                j = i;
        } else
        if(j > 0)
            i = -j;
        else
        if(j < 0)
            i = -j;
        int k = bridge.__x1 + i;
        int l = bridge.__y1 + j;
        if(k == bridge.__x1 && l == bridge.__y1)
        {
            return;
        } else
        {
            changeCurBridge(bridge.__indx, bridge.__type, bridge.__x1, bridge.__y1, k, l, bridge.__offsetK);
            return;
        }
    }

    private void doClearWater()
    {
        com.maddox.il2.builder.Plugin.builder.selectActorsClear();
        com.maddox.il2.builder.Plugin.builder.setSelected(null);
        com.maddox.il2.engine.Landscape landscape = com.maddox.il2.ai.World.land();
        java.util.ArrayList arraylist = new ArrayList();
        for(java.util.Map.Entry entry = allActors.nextEntry(null); entry != null; entry = allActors.nextEntry(entry))
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getKey();
            if(com.maddox.il2.engine.Actor.isValid(actor) && (actor instanceof com.maddox.il2.engine.ActorMesh))
            {
                com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
                com.maddox.il2.engine.Mesh mesh = ((com.maddox.il2.engine.ActorMesh)actor).mesh();
                double d = 0.0D;
                if(mesh instanceof com.maddox.il2.engine.HierMesh)
                {
                    d = ((com.maddox.il2.engine.ActorMesh)actor).mesh().visibilityR() + 2.0F;
                } else
                {
                    mesh.getBoundBox(_clearRoadBound);
                    double d1 = java.lang.Math.max(java.lang.Math.abs(_clearRoadBound[0]), java.lang.Math.abs(_clearRoadBound[3])) + 2.0F;
                    double d2 = java.lang.Math.max(java.lang.Math.abs(_clearRoadBound[1]), java.lang.Math.abs(_clearRoadBound[4])) + 2.0F;
                    d = java.lang.Math.max(d1, d2);
                }
                d /= 2D;
                if(landscape.isWater(point3d.x - d, point3d.y - d) || landscape.isWater(point3d.x - d, point3d.y + d) || landscape.isWater(point3d.x + d, point3d.y + d) || landscape.isWater(point3d.x + d, point3d.y - d))
                    arraylist.add(actor);
            }
        }

        int i = arraylist.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)arraylist.get(j);
            allActors.remove(actor1);
            actor1.destroy();
        }

    }

    private void doRemoveRoads()
    {
        com.maddox.il2.builder.Plugin.builder.selectActorsClear();
        com.maddox.il2.builder.Plugin.builder.setSelected(null);
        bTMapChanged = true;
        com.maddox.il2.engine.Landscape landscape = com.maddox.il2.ai.World.land();
        com.maddox.il2.engine.Landscape _tmp = landscape;
        int i = com.maddox.il2.engine.Landscape.getSizeXpix();
        com.maddox.il2.engine.Landscape _tmp1 = landscape;
        int j = com.maddox.il2.engine.Landscape.getSizeYpix();
        for(int k = 1; k < j - 1; k++)
        {
            int l = j - 1 - k;
            for(int i1 = 1; i1 < i - 1; i1++)
            {
                int j1 = landMapT.intI(i1, l);
                if((j1 & 0xe0) != 0)
                {
                    j1 &= 0xffffff1f;
                    landMapT.I(i1, l, j1);
                    com.maddox.il2.ai.World.land();
                    com.maddox.il2.engine.Landscape.setPixelMapT(i1, l, j1);
                }
            }

            if(k % 5 == 0)
            {
                int k1 = (k * 100) / j;
                com.maddox.rts.RTSConf.cur.mainWindow.setTitle("" + k1 + "%");
            }
        }

        com.maddox.rts.RTSConf.cur.mainWindow.setTitle("100% ");
    }

    private void doClearRoads()
    {
        com.maddox.il2.builder.Plugin.builder.selectActorsClear();
        com.maddox.il2.builder.Plugin.builder.setSelected(null);
        com.maddox.il2.engine.Landscape landscape = com.maddox.il2.ai.World.land();
        com.maddox.il2.engine.Landscape _tmp = landscape;
        int i = com.maddox.il2.engine.Landscape.getSizeXpix();
        com.maddox.il2.engine.Landscape _tmp1 = landscape;
        int j = com.maddox.il2.engine.Landscape.getSizeYpix();
        for(int k = 1; k < j - 1; k++)
        {
            for(int l = 1; l < i - 1; l++)
            {
                int i1 = j - 1 - k;
                int k1 = landMapT.intI(l, i1) & 0xe0;
                if(k1 != 0)
                {
                    tryClearRoads(k1, l, k, -1, 1, j);
                    tryClearRoads(k1, l, k, 0, 1, j);
                    tryClearRoads(k1, l, k, 1, 1, j);
                    tryClearRoads(k1, l, k, 1, 0, j);
                }
            }

            if(k % 5 == 0)
            {
                int j1 = (k * 100) / j;
                com.maddox.rts.RTSConf.cur.mainWindow.setTitle("" + j1 + "%");
            }
        }

        com.maddox.rts.RTSConf.cur.mainWindow.setTitle("100% ");
    }

    private void tryClearRoads(int i, int j, int k, int l, int i1, int j1)
    {
        int k1 = j1 - 1 - k - i1;
        if((landMapT.intI(j + l, k1) & 0xe0 & i) == 0)
            return;
        _clearRoadP0.x = (float)j * 200F + 100F;
        _clearRoadP0.y = (float)(k - 1) * 200F + 100F;
        _clearRoadP0.z = com.maddox.il2.ai.World.land().HQ(_clearRoadP0.x, _clearRoadP0.y) + 1.0D;
        _clearRoadP1.x = (float)(j + l) * 200F + 100F;
        _clearRoadP1.y = (float)((k - 1) + i1) * 200F + 100F;
        _clearRoadP1.z = com.maddox.il2.ai.World.land().HQ(_clearRoadP1.x, _clearRoadP1.y) + 1.0D;
        com.maddox.il2.engine.Engine.drawEnv().getFiltered(_clearRoadArray, _clearRoadP0.x - 100D, _clearRoadP0.y - 100D, _clearRoadP0.x + 100D, _clearRoadP0.y + 100D, 15, _clearRoadFilter);
        com.maddox.il2.engine.Engine.drawEnv().getFiltered(_clearRoadArray, _clearRoadP1.x - 100D, _clearRoadP1.y - 100D, _clearRoadP1.x + 100D, _clearRoadP1.y + 100D, 15, _clearRoadFilter);
        if(_clearRoadArray.size() == 0)
            return;
        for(int l1 = 0; l1 < _clearRoadArray.size(); l1++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)_clearRoadArray.get(l1);
            if(allActors.containsKey(actor) && (actor instanceof com.maddox.il2.engine.ActorMesh))
            {
                com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
                com.maddox.il2.engine.Mesh mesh = ((com.maddox.il2.engine.ActorMesh)actor).mesh();
                double d = 0.0D;
                if(mesh instanceof com.maddox.il2.engine.HierMesh)
                {
                    d = ((com.maddox.il2.engine.ActorMesh)actor).mesh().visibilityR() + 2.0F;
                    d *= d;
                } else
                {
                    mesh.getBoundBox(_clearRoadBound);
                    double d1 = java.lang.Math.max(java.lang.Math.abs(_clearRoadBound[0]), java.lang.Math.abs(_clearRoadBound[3])) + 2.0F;
                    double d2 = java.lang.Math.max(java.lang.Math.abs(_clearRoadBound[1]), java.lang.Math.abs(_clearRoadBound[4])) + 2.0F;
                    d = d1 * d1 + d2 * d2;
                }
                if(intersectLineSphere(_clearRoadP0.x, _clearRoadP0.y, _clearRoadP1.x, _clearRoadP1.y, point3d.x, point3d.y, d) >= 0.0D)
                {
                    allActors.remove(actor);
                    actor.destroy();
                }
            }
        }

        _clearRoadArray.clear();
    }

    private double intersectLineSphere(double d, double d1, double d2, double d3, double d4, double d5, double d6)
    {
        double d7 = d2 - d;
        double d8 = d3 - d1;
        double d9 = d7 * d7 + d8 * d8;
        if(d9 < 9.9999999999999995E-007D)
            return d6 < (d - d4) * (d - d4) + (d1 - d5) * (d1 - d5) ? -1D : 0.0D;
        double d10 = ((d4 - d) * d7 + (d5 - d1) * d8) / d9;
        if(d10 >= 0.0D && d10 <= 1.0D)
        {
            double d11 = d + d10 * d7;
            double d13 = d1 + d10 * d8;
            double d15 = (d11 - d4) * (d11 - d4) + (d13 - d5) * (d13 - d5);
            double d16 = d6 - d15;
            if(d16 < 0.0D)
                return -1D;
            if(d10 < 0.0D)
                d10 = 0.0D;
            return d10;
        }
        double d12 = (d2 - d4) * (d2 - d4) + (d3 - d5) * (d3 - d5);
        double d14 = (d - d4) * (d - d4) + (d1 - d5) * (d1 - d5);
        if(d12 <= d6 || d14 <= d6)
            return d12 >= d14 ? 0.0D : 1.0D;
        else
            return -1D;
    }

    private void doInsertPattern(com.maddox.gwindow.GWindow gwindow)
    {
        int i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
        int j = tileType(i);
        if(j == -1)
        {
            java.lang.System.out.println("Unknown type of ladscape");
            return;
        }
        _changedPatternType = j;
        if(dlgLoadPattern == null)
            dlgLoadPattern = new com.maddox.gwindow.GWindowFileOpen(gwindow, true, "Load Pattern ...", "maps", new com.maddox.gwindow.GFileFilter[] {
                new GFileFilterName("All files", new java.lang.String[] {
                    "*"
                })
            }) {

                public void result(java.lang.String s)
                {
                    if(s != null)
                        changePattern(true, "maps/" + s);
                }

            }
;
        else
            dlgLoadPattern.activateWindow();
    }

    private void doRemovePattern(com.maddox.gwindow.GWindow gwindow)
    {
        int i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
        int j = tileType(i);
        if(j == -1)
        {
            java.lang.System.out.println("Unknown type of ladscape");
            return;
        }
        _changedPatternType = j;
        if(dlgRemovePattern == null)
            dlgRemovePattern = new com.maddox.gwindow.GWindowFileOpen(gwindow, true, "Load Pattern ...", "maps", new com.maddox.gwindow.GFileFilter[] {
                new GFileFilterName("All files", new java.lang.String[] {
                    "*"
                })
            }) {

                public void result(java.lang.String s)
                {
                    if(s != null)
                        changePattern(false, "maps/" + s);
                }

            }
;
        else
            dlgRemovePattern.activateWindow();
    }

    private int changeOnePattern(java.util.ArrayList arraylist, boolean flag, int i, int j, int k)
    {
        int l = 0;
        int i1 = arraylist.size();
        _changeOffsetXY.x = (float)PIXEL * (float)i;
        _changeOffsetXY.y = (float)PIXEL * (float)(j - 1);
        for(int j1 = 0; j1 < i1; j1++)
        {
            com.maddox.il2.builder.ItemPattern itempattern = (com.maddox.il2.builder.ItemPattern)arraylist.get(j1);
            if(k != 0 ? k != 1 ? k != 2 ? k != 3 || (double)itempattern.x <= PIXELD2 && (double)itempattern.y <= PIXELD2 : (double)itempattern.x >= PIXELD2 && (double)itempattern.y <= PIXELD2 : (double)itempattern.x <= PIXELD2 && (double)itempattern.y >= PIXELD2 : (double)itempattern.x >= PIXELD2 && (double)itempattern.y >= PIXELD2)
                if(flag)
                {
                    _changeLoc.set(itempattern.x + _changeOffsetXY.x, itempattern.y + _changeOffsetXY.y, 0.0D, itempattern.getAzimut(), 0.0F, 0.0F);
                    insert(itempattern.spawn, _changeLoc, false);
                    l++;
                } else
                {
                    com.maddox.il2.engine.Actor actor = findParrtenActor(itempattern, _changeOffsetXY);
                    if(actor != null)
                    {
                        allActors.remove(actor);
                        actor.destroy();
                        l++;
                    }
                }
        }

        return l;
    }

    private void changePattern(boolean flag, java.lang.String s)
    {
        int i = 0;
        com.maddox.il2.builder.Plugin.builder.selectActorsClear();
        com.maddox.il2.builder.Plugin.builder.setSelected(null);
        java.util.ArrayList aarraylist[][] = new java.util.ArrayList[TILE][TILE];
        loadPattern(s, aarraylist);
        com.maddox.il2.engine.Landscape landscape = com.maddox.il2.ai.World.land();
        com.maddox.il2.engine.Landscape _tmp = landscape;
        int j = com.maddox.il2.engine.Landscape.getSizeXpix();
        com.maddox.il2.engine.Landscape _tmp1 = landscape;
        int k = com.maddox.il2.engine.Landscape.getSizeYpix();
        for(int l = 1; l < k - 1; l++)
        {
            for(int i1 = 1; i1 < j - 1; i1++)
            {
                int j1 = k - 1 - l;
                com.maddox.il2.engine.Landscape _tmp2 = landscape;
                int l1 = com.maddox.il2.engine.Landscape.getPixelMapT(i1, j1) & 0x1f;
                if(l1 == _changedPatternType)
                {
                    java.util.ArrayList arraylist = aarraylist[l & TILEMASK][(i1 + TILE) - 1 & TILEMASK];
                    if(arraylist != null && arraylist.size() > 0)
                        i += changeOnePattern(arraylist, flag, i1 - 1, l, 0);
                    arraylist = aarraylist[l & TILEMASK][i1 & TILEMASK];
                    if(arraylist != null && arraylist.size() > 0)
                        i += changeOnePattern(arraylist, flag, i1, l, 1);
                    arraylist = aarraylist[l + 1 & TILEMASK][(i1 + TILE) - 1 & TILEMASK];
                    if(arraylist != null && arraylist.size() > 0)
                        i += changeOnePattern(arraylist, flag, i1 - 1, l + 1, 2);
                    arraylist = aarraylist[l + 1 & TILEMASK][i1 & TILEMASK];
                    if(arraylist != null && arraylist.size() > 0)
                        i += changeOnePattern(arraylist, flag, i1, l + 1, 3);
                }
            }

            if(l % 5 == 0)
            {
                int k1 = (l * 100) / k;
                com.maddox.rts.RTSConf.cur.mainWindow.setTitle("" + k1 + "% " + i);
            }
        }

        com.maddox.rts.RTSConf.cur.mainWindow.setTitle("100% " + i);
        java.lang.String s1 = null;
        if(flag)
            s1 = " " + i + " pattern objects inserted as static";
        else
            s1 = " " + i + " pattern objects removed from static";
        java.lang.System.out.println(s1);
        com.maddox.il2.builder.Plugin.builder.tip(s1);
    }

    public com.maddox.il2.engine.Actor findParrtenActor(com.maddox.il2.builder.ItemPattern itempattern, com.maddox.JGP.Point3f point3f)
    {
        double d = itempattern.x + point3f.x;
        double d1 = itempattern.y + point3f.y;
        for(java.util.Map.Entry entry = allActors.nextEntry(null); entry != null; entry = allActors.nextEntry(entry))
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getKey();
            if(com.maddox.il2.engine.Actor.isValid(actor))
            {
                double d2 = actor.pos.getAbsPoint().x;
                double d3 = actor.pos.getAbsPoint().y;
                double d4 = (d - d2) * (d - d2) + (d1 - d3) * (d1 - d3);
                if(d4 < 1.0D && com.maddox.il2.builder.PlMapActors.getFingerOfFullClassName(actor) == itempattern.finger)
                    return actor;
            }
        }

        return null;
    }

    private void loadPattern(java.lang.String s, java.util.ArrayList aarraylist[][])
    {
        try
        {
            java.io.DataInputStream datainputstream = new DataInputStream(new SFSInputStream(s));
            java.lang.System.out.println("Read pattern objects ...");
            boolean flag = true;
            int i = datainputstream.readInt();
            if(i == -65535)
            {
                flag = false;
                i = datainputstream.readInt();
            }
            int k = 0;
            int l = 0;
            if(flag)
            {
                for(int i1 = 0; i1 < i; i1++)
                {
                    datainputstream.readInt();
                    datainputstream.readInt();
                    datainputstream.readInt();
                    datainputstream.readInt();
                    datainputstream.readInt();
                    datainputstream.readFloat();
                }

                i = datainputstream.readInt();
                k = i;
                if(i > 0)
                {
                    while(i-- > 0) 
                    {
                        java.lang.String s2 = datainputstream.readUTF();
                        com.maddox.rts.Spawn.get_WithSoftClass(s2);
                    }
                    i = datainputstream.readInt();
                    l = i;
                    while(i-- > 0) 
                    {
                        int j1 = datainputstream.readInt();
                        float f = datainputstream.readFloat();
                        float f1 = datainputstream.readFloat();
                        float f3 = datainputstream.readFloat();
                        float f6 = datainputstream.readFloat();
                        float f9 = datainputstream.readFloat();
                        float f10 = datainputstream.readFloat();
                        addPattern(j1, f, f1, f6, aarraylist);
                    }
                }
            } else
            {
                int j = datainputstream.readInt();
                k = j;
                Object obj = null;
                if(j > 0)
                {
                    int ai[] = new int[j];
                    for(int k1 = 0; k1 < j; k1++)
                    {
                        java.lang.String s3 = datainputstream.readUTF();
                        ai[k1] = com.maddox.rts.Finger.Int(s3);
                        com.maddox.rts.Spawn.get_WithSoftClass(s3);
                    }

                    j = datainputstream.readInt();
                    l = j;
                    while(j-- > 0) 
                    {
                        int i2 = datainputstream.readInt();
                        float f2 = datainputstream.readFloat();
                        float f4 = datainputstream.readFloat();
                        float f7 = datainputstream.readFloat();
                        addPattern(ai[i2], f2, f4, f7, aarraylist);
                    }
                }
                j = datainputstream.readInt();
                k += j;
                if(j > 0)
                {
                    int ai1[] = new int[j];
                    for(int l1 = 0; l1 < j; l1++)
                    {
                        java.lang.String s4 = datainputstream.readUTF();
                        ai1[l1] = com.maddox.rts.Finger.Int(s4);
                        com.maddox.rts.Spawn.get_WithSoftClass(s4);
                    }

                    for(int j2 = datainputstream.readInt(); j2-- > 0;)
                    {
                        int k2 = datainputstream.readInt();
                        float f5 = (float)(k2 & 0xffff) * 200F;
                        float f8 = (float)(k2 >> 16 & 0xffff) * 200F;
                        int l2 = datainputstream.readInt();
                        l += j;
                        while(l2-- > 0) 
                        {
                            int i3 = datainputstream.readInt();
                            int j3 = datainputstream.readInt();
                            int k3 = i3 & 0x7fff;
                            if(k3 < ai1.length && ai1[k3] != 0)
                            {
                                int l3 = (short)(i3 >> 16);
                                int i4 = (short)(j3 & 0xffff);
                                int j4 = (short)(j3 >> 16 & 0xffff);
                                float f11 = ((float)l3 * 360F) / 32000F;
                                float f12 = ((float)i4 * 200F) / 32000F + f5;
                                float f13 = ((float)j4 * 200F) / 32000F + f8;
                                addPattern(ai1[k3], f12, f13, -f11, aarraylist);
                            }
                        }
                    }

                }
            }
            datainputstream.close();
            java.lang.System.out.println("" + k + " class " + l + " actors in pattern");
        }
        catch(java.lang.Exception exception)
        {
            java.lang.String s1 = "Pattern actors load from '" + s + "' FAILED: " + exception.getMessage();
            java.lang.System.out.println(s1);
            exception.printStackTrace();
        }
    }

    private void addPattern(int i, float f, float f1, float f2, java.util.ArrayList aarraylist[][])
    {
        f1 = (float)((double)f1 + PIXEL);
        double d = (double)f % QUAD;
        double d1 = (double)f1 % QUAD;
        int j = (int)(d / PIXEL);
        int k = (int)(d1 / PIXEL);
        if(aarraylist[k][j] == null)
            aarraylist[k][j] = new ArrayList();
        aarraylist[k][j].add(new ItemPattern(i, (float)(d % PIXEL), (float)(d1 % PIXEL), f2));
    }

    public void freeResources()
    {
        dlgPasteFrom = null;
        dlgLoadAs = null;
        dlgLoadSpawn = null;
        dlgLoadPattern = null;
        dlgRemovePattern = null;
        dlgSaveAs = null;
        dlgSaveSpawn = null;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    protected com.maddox.util.HashMapExt allActors;
    protected com.maddox.TexImage.TexImage landMapT;
    protected com.maddox.TexImage.TexImage landMapH;
    protected com.maddox.il2.engine.Mat matLabel;
    private static final int EXT_TYPES = 4;
    private static final int OFS_BRIDGE = 4;
    private static final int OFS_ROAD = 3;
    private static final int OFS_HEIGHT = 2;
    private static final int OFS_TILE = 1;
    private boolean bTMapChanged;
    private boolean bHMapChanged;
    private boolean bBridgeChanged;
    com.maddox.il2.builder.Type type[];
    private com.maddox.util.HashMapExt _saveCls0Map;
    private com.maddox.util.HashMapExt _saveClsMap;
    private com.maddox.JGP.Point2d p2d;
    private int newCurType;
    private int newCurItem;
    private com.maddox.il2.engine.ActorSpawnArg spawnArg;
    private int fillHeight;
    private com.maddox.JGP.Point3d _startFill;
    private com.maddox.JGP.Point3d _endFill;
    private com.maddox.JGP.Point3d _stepFill;
    private int squareTile;
    private java.util.ArrayList _clipSpawns;
    private java.util.ArrayList _clipAirdroms;
    private java.util.ArrayList _clipLoc;
    private com.maddox.JGP.Point3d _clipP0;
    private com.maddox.gwindow.GWindowFileOpen dlgPasteFrom;
    private int startComboBox1;
    com.maddox.il2.builder.ViewItem viewType[];
    com.maddox.gwindow.GWindowMenuItem viewBridge;
    com.maddox.gwindow.GWindowMenuItem viewRunaway;
    java.util.HashMap viewClassFingers;
    com.maddox.gwindow.GWindowMenuItem mSave;
    com.maddox.gwindow.GWindowMenuItem mLoad;
    com.maddox.gwindow.GWindowMenuItem mSaveSpawn;
    com.maddox.gwindow.GWindowMenuItem mLoadSpawn;
    com.maddox.gwindow.GWindowMenuItem mSaveAs;
    com.maddox.gwindow.GWindowMenuItem mLoadAs;
    com.maddox.gwindow.GWindowMenuItem mCreateBridges;
    private com.maddox.gwindow.GWindowFileOpen dlgLoadAs;
    private com.maddox.gwindow.GWindowFileOpen dlgLoadSpawn;
    private com.maddox.gwindow.GWindowFileSaveAs dlgSaveAs;
    private com.maddox.gwindow.GWindowFileSaveAs dlgSaveSpawn;
    private int newIndxBridge;
    private com.maddox.il2.objects.bridges.Bridge curBridge;
    private int indxBridge;
    private com.maddox.JGP.Point3d _clearRoadP0;
    private com.maddox.JGP.Point3d _clearRoadP1;
    private java.util.ArrayList _clearRoadArray;
    private float _clearRoadBound[];
    private com.maddox.il2.builder.ClearRoadFilter _clearRoadFilter;
    private com.maddox.gwindow.GWindowFileOpen dlgLoadPattern;
    private com.maddox.gwindow.GWindowFileOpen dlgRemovePattern;
    private double PIXEL;
    private double PIXELD2;
    private int TILE;
    private int TILEMASK;
    private double QUAD;
    private int _changedPatternType;
    com.maddox.JGP.Point3f _changeOffsetXY;
    com.maddox.il2.engine.Loc _changeLoc;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.builder.PlMapActors.class, "name", "MapActors");
    }




























}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PlMisAir.java

package com.maddox.il2.builder;

import com.maddox.JGP.Color4f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BmpUtils;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.GUIRenders;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.LightEnv;
import com.maddox.il2.engine.LightEnvXY;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.Renders;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.gui.GUIAirArming;
import com.maddox.il2.net.NetFileServerNoseart;
import com.maddox.il2.net.NetFileServerPilot;
import com.maddox.il2.net.NetFileServerSkin;
import com.maddox.il2.objects.ActorSimpleHMesh;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.PaintScheme;
import com.maddox.rts.Finger;
import com.maddox.rts.HomePath;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.util.HashMapInt;
import com.maddox.util.NumberTokenizer;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, PathAir, PAir, PPoint, 
//            Path, PlMission, ResSquadron, PNodes, 
//            Builder, Pathes, WSelect

public class PlMisAir extends com.maddox.il2.builder.Plugin
{
    class _Render3D extends com.maddox.il2.engine.Render
    {

        public void preRender()
        {
            checkMesh(planeIndx);
            if(com.maddox.il2.engine.Actor.isValid(actorMesh[planeIndx]))
            {
                if(animateMeshA[planeIndx] != 0.0F || animateMeshT[planeIndx] != 0.0F)
                {
                    actorMesh[planeIndx].pos.getAbs(_orient);
                    _orient.set(_orient.azimut() + animateMeshA[planeIndx] * com.maddox.il2.game.Main3D.cur3D().guiManager.root.deltaTimeSec, _orient.tangage() + animateMeshT[planeIndx] * com.maddox.il2.game.Main3D.cur3D().guiManager.root.deltaTimeSec, 0.0F);
                    _orient.wrap360();
                    actorMesh[planeIndx].pos.setAbs(_orient);
                    actorMesh[planeIndx].pos.reset();
                }
                actorMesh[planeIndx].draw.preRender(actorMesh[planeIndx]);
            }
        }

        public void render()
        {
            if(com.maddox.il2.engine.Actor.isValid(actorMesh[planeIndx]))
            {
                com.maddox.il2.engine.Render.prepareStates();
                actorMesh[planeIndx].draw.render(actorMesh[planeIndx]);
            }
        }

        int planeIndx;

        public _Render3D(com.maddox.il2.engine.Renders renders1, float f)
        {
            super(renders1, f);
            planeIndx = _planeIndx;
            setClearColor(new Color4f(0.5F, 0.78F, 0.92F, 1.0F));
            useClearStencil(true);
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

    public static class DefaultArmy
    {

        public void save(com.maddox.il2.builder.PathAir pathair)
        {
            iRegiment = pathair.iRegiment;
            iSquadron = pathair.iSquadron;
            iWing = pathair.iWing;
        }

        public void load(com.maddox.il2.builder.PathAir pathair)
        {
            pathair.iRegiment = iRegiment;
            pathair.iSquadron = iSquadron;
            pathair.iWing = iWing;
        }

        public int iRegiment;
        public int iSquadron;
        public int iWing;

        public DefaultArmy()
        {
        }
    }

    public static class Type
    {

        public java.lang.String name;
        public com.maddox.il2.builder.Item item[];

        public Type(java.lang.String s, com.maddox.il2.builder.Item aitem[])
        {
            name = s;
            item = aitem;
        }
    }

    public static class Item
    {

        public java.lang.String name;
        public java.lang.Class clazz;
        public int army;
        public boolean bEnablePlayer;
        public double speedMin;
        public double speedMax;
        public double speedRunway;

        public Item(java.lang.String s, java.lang.Class class1, int i)
        {
            speedMin = 200D;
            speedMax = 500D;
            speedRunway = 200D;
            name = s;
            clazz = class1;
            army = i;
            bEnablePlayer = com.maddox.rts.Property.containsValue(class1, "cockpitClass");
            java.lang.String s1 = com.maddox.rts.Property.stringValue(class1, "FlightModel", null);
            if(s1 != null)
            {
                com.maddox.rts.SectFile sectfile = com.maddox.il2.fm.FlightModelMain.sectFile(s1);
                speedMin = sectfile.get("Params", "Vmin", (float)speedMin);
                speedMax = sectfile.get("Params", "VmaxH", (float)speedMax);
                speedRunway = speedMin;
            }
        }
    }


    public PlMisAir()
    {
        defaultHeight = 500D;
        defaultSpeed = 300D;
        int i = com.maddox.il2.ai.Army.amountSingle();
        if(i < com.maddox.il2.ai.Army.amountNet())
            i = com.maddox.il2.ai.Army.amountNet();
        defaultArmy = new com.maddox.il2.builder.DefaultArmy[i];
        for(int j = 0; j < i; j++)
            defaultArmy[j] = new DefaultArmy();

        iArmyRegimentList = 0;
        regimentList = new ArrayList();
        viewMap = new HashMapInt();
        _actorInfo = new java.lang.String[2];
        tabSkin = new com.maddox.gwindow.GWindowTabDialogClient.Tab[4];
        _squads = new java.lang.Object[4];
        _wings = new java.lang.Object[4];
        wPlayer = new com.maddox.gwindow.GWindowCheckBox[4];
        wSkills = new com.maddox.gwindow.GWindowComboControl[4];
        wSkins = new com.maddox.gwindow.GWindowComboControl[4];
        wNoseart = new com.maddox.gwindow.GWindowComboControl[4];
        wPilots = new com.maddox.gwindow.GWindowComboControl[4];
        renders = new com.maddox.il2.engine.GUIRenders[4];
        camera3D = new com.maddox.il2.engine.Camera3D[4];
        render3D = new com.maddox.il2.builder._Render3D[4];
        meshName = null;
        actorMesh = new com.maddox.il2.objects.ActorSimpleHMesh[4];
        _orient = new Orient();
    }

    private void makeRegimentList(int i)
    {
        if(iArmyRegimentList == i)
            return;
        regimentList.clear();
        java.util.List list = com.maddox.il2.ai.Regiment.getAll();
        int j = list.size();
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.ai.Regiment regiment = (com.maddox.il2.ai.Regiment)list.get(k);
            if(regiment.getArmy() == i)
                regimentList.add(regiment);
        }

        iArmyRegimentList = i;
        wRegiment.clear(false);
        wRegiment.setSelected(-1, false, false);
        j = regimentList.size();
        if(wRegiment.posEnable == null || wRegiment.posEnable.length < j)
            wRegiment.posEnable = new boolean[j];
        for(int l = 0; l < j; l++)
        {
            com.maddox.il2.ai.Regiment regiment1 = (com.maddox.il2.ai.Regiment)regimentList.get(l);
            java.lang.String s = com.maddox.il2.game.I18N.regimentShort(regiment1.shortInfo());
            if(s != null && s.length() > 0 && s.charAt(0) == '<')
                s = com.maddox.il2.game.I18N.regimentInfo(regiment1.info());
            wRegiment.add(s);
            wRegiment.posEnable[l] = true;
        }

    }

    public void load(com.maddox.rts.SectFile sectfile)
    {
        int i = sectfile.sectionIndex("Wing");
        if(i < 0)
            return;
        int j = sectfile.vars(i);
        com.maddox.JGP.Point3d point3d = new Point3d();
        for(int k = 0; k < j; k++)
        {
            java.lang.String s = sectfile.var(i, k);
            java.lang.String s1 = s + "_Way";
            if(!sectfile.sectionExist(s))
            {
                builder.tipErr("MissionLoad: Section '" + s + "' not found");
                continue;
            }
            int l = sectfile.sectionIndex(s1);
            if(l < 0)
            {
                builder.tipErr("MissionLoad: Section '" + s1 + "' not found");
                continue;
            }
            int i1 = sectfile.vars(l);
            if(i1 == 0)
            {
                builder.tipErr("MissionLoad: Section '" + s1 + "' is empty");
                continue;
            }
            java.lang.String s2 = sectfile.get(s, "Class", (java.lang.String)null);
            if(s2 == null)
            {
                builder.tipErr("MissionLoad: In section '" + s + "' field 'Class' not present");
                continue;
            }
            java.lang.Class class1 = null;
            try
            {
                class1 = com.maddox.rts.ObjIO.classForName(s2);
            }
            catch(java.lang.Exception exception)
            {
                builder.tipErr("MissionLoad: In section '" + s + "' field 'Class' contains unknown class");
                continue;
            }
            int j1 = 0;
            int k1 = 0;
            j1 = 0;
            do
            {
                if(j1 >= type.length)
                    break;
                for(k1 = 0; k1 < type[j1].item.length && type[j1].item[k1].clazz != class1; k1++);
                if(k1 < type[j1].item.length)
                    break;
                j1++;
            } while(true);
            if(j1 >= type.length)
            {
                builder.tipErr("MissionLoad: In section '" + s + "' field 'Class' contains unknown class");
                continue;
            }
            boolean flag = sectfile.get(s, "OnlyAI", 0, 0, 1) == 1;
            boolean flag1 = sectfile.get(s, "Parachute", 1, 0, 1) == 1;
            int l1 = sectfile.get(s, "Fuel", 100, 0, 100);
            int i2 = sectfile.get(s, "Planes", 1, 1, 4);
            int j2 = -1;
            if(sectfile.exist(s, "Skill"))
                j2 = sectfile.get(s, "Skill", 1, 0, 3);
            int ai[] = new int[4];
            for(int k2 = 0; k2 < 4; k2++)
                if(sectfile.exist(s, "Skill" + k2))
                    ai[k2] = sectfile.get(s, "Skill" + k2, 1, 0, 3);
                else
                    ai[k2] = j2 != -1 ? j2 : 1;

            java.lang.String as[] = new java.lang.String[4];
            for(int l2 = 0; l2 < 4; l2++)
                as[l2] = sectfile.get(s, "skin" + l2, (java.lang.String)null);

            java.lang.String as1[] = new java.lang.String[4];
            for(int i3 = 0; i3 < 4; i3++)
                as1[i3] = sectfile.get(s, "noseart" + i3, (java.lang.String)null);

            java.lang.String as2[] = new java.lang.String[4];
            for(int j3 = 0; j3 < 4; j3++)
                as2[j3] = sectfile.get(s, "pilot" + j3, (java.lang.String)null);

            boolean aflag[] = new boolean[4];
            for(int k3 = 0; k3 < 4; k3++)
                aflag[k3] = sectfile.get(s, "numberOn" + k3, 1, 0, 1) == 1;

            java.lang.String s3 = s.substring(0, s.length() - 2);
            com.maddox.il2.ai.Regiment regiment = (com.maddox.il2.ai.Regiment)com.maddox.il2.engine.Actor.getByName(s3);
            int l3 = 0;
            int i4 = 0;
            int j4 = 0;
            if(regiment != null)
            {
                makeRegimentList(regiment.getArmy());
                l3 = regimentList.indexOf(regiment);
                if(l3 >= 0)
                {
                    j4 = java.lang.Character.getNumericValue(s.charAt(s.length() - 1)) - java.lang.Character.getNumericValue('0');
                    i4 = java.lang.Character.getNumericValue(s.charAt(s.length() - 2)) - java.lang.Character.getNumericValue('0');
                    if(j4 < 0)
                        j4 = 0;
                    if(j4 > 3)
                        j4 = 3;
                    if(i4 < 0)
                        i4 = 0;
                    if(i4 > 3)
                        i4 = 3;
                } else
                {
                    regiment = null;
                }
            }
            if(regiment == null)
            {
                int k4 = sectfile.get(s, "Army", 0);
                if(k4 < 1 || k4 >= com.maddox.il2.builder.Builder.armyAmount())
                    k4 = 1;
                makeRegimentList(k4);
                regiment = (com.maddox.il2.ai.Regiment)regimentList.get(0);
                s3 = regiment.name();
            }
            java.lang.String s4 = sectfile.get(s, "weapons", (java.lang.String)null);
            int l4 = sectfile.get(s, "StartTime", 0);
            if(l4 < 0)
                l4 = 0;
            com.maddox.il2.builder.PathAir pathair = new PathAir(builder.pathes, j1, k1);
            pathair.setArmy(regiment.getArmy());
            pathair.sRegiment = s3;
            pathair.iRegiment = l3;
            pathair.iSquadron = i4;
            pathair.iWing = j4;
            pathair.fuel = l1;
            pathair.bOnlyAI = flag;
            pathair.bParachute = flag1;
            pathair.planes = i2;
            pathair.skill = j2;
            pathair.skills = ai;
            pathair.skins = as;
            pathair.noseart = as1;
            pathair.pilots = as2;
            pathair.bNumberOn = aflag;
            pathair.weapons = s4;
            if(!searchEnabledSlots(pathair))
            {
                pathair.destroy();
                builder.tipErr("MissionLoad: Section '" + s + "', regiment table very small");
                continue;
            }
            pathair.setName(pathair.sRegiment + pathair.iSquadron + pathair.iWing);
            com.maddox.rts.Property.set(pathair, "builderPlugin", this);
            pathair.drawing(viewMap.containsKey(j1));
            java.lang.Object obj = null;
            for(int i5 = 0; i5 < i1; i5++)
            {
                java.lang.String s5 = sectfile.var(l, i5);
                byte byte0 = -1;
                if("NORMFLY".equals(s5))
                    byte0 = 0;
                else
                if("TAKEOFF".equals(s5))
                    byte0 = 1;
                else
                if("LANDING".equals(s5))
                    byte0 = 2;
                else
                if("GATTACK".equals(s5))
                {
                    byte0 = 3;
                } else
                {
                    builder.tipErr("MissionLoad: Section '" + s1 + "' contains unknown type waypoint");
                    pathair.destroy();
                    continue;
                }
                java.lang.String s6 = sectfile.value(l, i5);
                if(s6 == null || s6.length() <= 0)
                {
                    builder.tipErr("MissionLoad: Section '" + s1 + "' type '" + s5 + "' is empty");
                    pathair.destroy();
                    continue;
                }
                com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(s6);
                point3d.x = numbertokenizer.next(-1E+030D, -1E+030D, 1E+030D);
                point3d.y = numbertokenizer.next(-1E+030D, -1E+030D, 1E+030D);
                double d = numbertokenizer.next(0.0D, 0.0D, 10000D);
                double d1 = numbertokenizer.next(0.0D, 0.0D, 1000D);
                java.lang.String s7 = null;
                int j5 = 0;
                java.lang.String s8 = null;
                s7 = numbertokenizer.next(null);
                if(s7 != null)
                    if(s7.equals("&0"))
                    {
                        s8 = s7;
                        s7 = null;
                    } else
                    if(s7.equals("&1"))
                    {
                        s8 = s7;
                        s7 = null;
                    } else
                    {
                        j5 = numbertokenizer.next(0);
                        s8 = numbertokenizer.next(null);
                    }
                switch(byte0)
                {
                default:
                    break;

                case 0: // '\0'
                    byte0 = 0;
                    break;

                case 3: // '\003'
                    if(s7 != null && s7.startsWith("Bridge"))
                        s7 = " " + s7;
                    byte0 = 3;
                    break;

                case 1: // '\001'
                    byte0 = 1;
                    break;

                case 2: // '\002'
                    byte0 = 2;
                    break;
                }
                obj = new PAir(pathair, ((com.maddox.il2.builder.PPoint) (obj)), point3d, byte0, d, d1);
                if(s7 != null)
                {
                    obj.iTarget = j5;
                    obj.sTarget = s7;
                }
                if(s8 != null && s8.equals("&1"))
                    obj.bRadioSilence = true;
            }

            if(l4 > 0)
            {
                com.maddox.il2.builder.PAir pair = (com.maddox.il2.builder.PAir)pathair.point(0);
                pair.time = (double)l4 * 60D;
                pathair.computeTimes(false);
            }
        }

    }

    public boolean save(com.maddox.rts.SectFile sectfile)
    {
        if(builder.pathes == null)
            return true;
        int i = sectfile.sectionIndex("Wing");
        java.lang.Object aobj[] = builder.pathes.getOwnerAttached();
label0:
        for(int j = 0; j < aobj.length; j++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)aobj[j];
            if(actor == null)
                break;
            if(!(actor instanceof com.maddox.il2.builder.PathAir))
                continue;
            com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)actor;
            int k = pathair.points();
            if(i <= -1)
                i = sectfile.sectionAdd("Wing");
            java.lang.String s = pathair.sRegiment + pathair.iSquadron + pathair.iWing;
            java.lang.String s1 = s + "_Way";
            sectfile.lineAdd(i, s, "");
            int l = sectfile.sectionAdd(s);
            sectfile.lineAdd(l, "Planes", "" + pathair.planes);
            if(pathair.bOnlyAI)
                sectfile.lineAdd(l, "OnlyAI", "1");
            if(!pathair.bParachute)
                sectfile.lineAdd(l, "Parachute", "0");
            if(pathair.skill != -1)
                sectfile.lineAdd(l, "Skill", "" + pathair.skill);
            for(int i1 = 0; i1 < 4; i1++)
                if(pathair.skill != pathair.skills[i1])
                    sectfile.lineAdd(l, "Skill" + i1, "" + pathair.skills[i1]);

            for(int j1 = 0; j1 < 4; j1++)
                if(pathair.skins[j1] != null)
                    sectfile.lineAdd(l, "skin" + j1, pathair.skins[j1]);

            for(int k1 = 0; k1 < 4; k1++)
                if(pathair.noseart[k1] != null)
                    sectfile.lineAdd(l, "noseart" + k1, pathair.noseart[k1]);

            for(int l1 = 0; l1 < 4; l1++)
                if(pathair.pilots[l1] != null)
                    sectfile.lineAdd(l, "pilot" + l1, pathair.pilots[l1]);

            for(int i2 = 0; i2 < 4; i2++)
                if(!pathair.bNumberOn[i2])
                    sectfile.lineAdd(l, "numberOn" + i2, "0");

            sectfile.lineAdd(l, "Class", com.maddox.rts.ObjIO.classGetName(type[pathair._iType].item[pathair._iItem].clazz));
            sectfile.lineAdd(l, "Fuel", "" + pathair.fuel);
            if(pathair.weapons != null)
                sectfile.lineAdd(l, "weapons", "" + pathair.weapons);
            else
                sectfile.lineAdd(l, "weapons", "none");
            com.maddox.il2.builder.PAir pair = (com.maddox.il2.builder.PAir)pathair.point(0);
            if(pair.time > 0.0D)
                sectfile.lineAdd(l, "StartTime", "" + (int)java.lang.Math.round(pair.time / 60D));
            int j2 = sectfile.sectionAdd(s1);
            int k2 = 0;
            do
            {
                if(k2 >= k)
                    continue label0;
                com.maddox.il2.builder.PAir pair1 = (com.maddox.il2.builder.PAir)pathair.point(k2);
                com.maddox.JGP.Point3d point3d = pair1.pos.getAbsPoint();
                switch(pair1.type())
                {
                case 0: // '\0'
                    sectfile.lineAdd(j2, "NORMFLY", fmt(point3d.x) + " " + fmt(point3d.y) + " " + fmt(pair1.height) + " " + fmt(pair1.speed) + saveTarget(pair1) + saveRadioSilence(pair1));
                    break;

                case 3: // '\003'
                    sectfile.lineAdd(j2, "GATTACK", fmt(point3d.x) + " " + fmt(point3d.y) + " " + fmt(pair1.height) + " " + fmt(pair1.speed) + saveTarget(pair1) + saveRadioSilence(pair1));
                    break;

                case 1: // '\001'
                    sectfile.lineAdd(j2, "TAKEOFF", fmt(point3d.x) + " " + fmt(point3d.y) + " 0 0" + saveTarget(pair1) + saveRadioSilence(pair1));
                    break;

                case 2: // '\002'
                    sectfile.lineAdd(j2, "LANDING", fmt(point3d.x) + " " + fmt(point3d.y) + " 0 0" + saveTarget(pair1) + saveRadioSilence(pair1));
                    break;
                }
                k2++;
            } while(true);
        }

        return true;
    }

    private java.lang.String fmt(double d)
    {
        boolean flag = d < 0.0D;
        if(flag)
            d = -d;
        double d1 = (d + 0.0050000000000000001D) - (double)(int)d;
        if(d1 >= 0.10000000000000001D)
            return (flag ? "-" : "") + (int)d + "." + (int)(d1 * 100D);
        else
            return (flag ? "-" : "") + (int)d + ".0" + (int)(d1 * 100D);
    }

    private java.lang.String saveTarget(com.maddox.il2.builder.PAir pair)
    {
        if(!com.maddox.il2.engine.Actor.isValid(pair.getTarget()))
            return "";
        if(pair.getTarget() instanceof com.maddox.il2.builder.PPoint)
        {
            com.maddox.il2.builder.PPoint ppoint = (com.maddox.il2.builder.PPoint)pair.getTarget();
            com.maddox.il2.builder.Path path = (com.maddox.il2.builder.Path)ppoint.getOwner();
            return " " + path.name() + " " + path.pointIndx(ppoint);
        } else
        {
            return " " + pair.getTarget().name() + " 0";
        }
    }

    private java.lang.String saveRadioSilence(com.maddox.il2.builder.PAir pair)
    {
        return " " + (pair.bRadioSilence ? "&1" : "&0");
    }

    private void clampSpeed(com.maddox.il2.builder.PAir pair)
    {
        com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)pair.getOwner();
        if(pair.type() == 0 || pair.type() == 3)
        {
            if(pair.speed < type[pathair._iType].item[pathair._iItem].speedMin)
                pair.speed = type[pathair._iType].item[pathair._iItem].speedMin;
            if(pair.speed > type[pathair._iType].item[pathair._iItem].speedMax)
                pair.speed = type[pathair._iType].item[pathair._iItem].speedMax;
        } else
        {
            pair.speed = 0.0D;
        }
    }

    private void clampSpeed(com.maddox.il2.builder.PathAir pathair)
    {
        int i = pathair.points();
        for(int j = 0; j < i; j++)
            clampSpeed((com.maddox.il2.builder.PAir)pathair.point(j));

    }

    public void insert(com.maddox.il2.engine.Loc loc, boolean flag)
    {
        com.maddox.il2.builder.PathAir pathair = null;
        com.maddox.JGP.Point3d point3d;
        int i;
        int j;
        com.maddox.il2.builder.Path path;
        point3d = loc.getPoint();
        i = builder.wSelect.comboBox1.getSelected();
        j = builder.wSelect.comboBox2.getSelected();
        if(builder.selectedPath() == null)
            break MISSING_BLOCK_LABEL_98;
        path = builder.selectedPath();
        if(!(path instanceof com.maddox.il2.builder.PathAir))
            return;
        pathair = (com.maddox.il2.builder.PathAir)path;
        if(i - startComboBox1 != pathair._iType || j != pathair._iItem)
            builder.setSelected(null);
        if(builder.selectedPoint() == null)
            break MISSING_BLOCK_LABEL_163;
        com.maddox.il2.builder.PAir pair1 = (com.maddox.il2.builder.PAir)builder.selectedPoint();
        if(pair1.type() == 2)
            return;
        com.maddox.il2.builder.PAir pair;
        pair = new PAir(builder.selectedPath(), builder.selectedPoint(), point3d, 0, defaultHeight, defaultSpeed);
        break MISSING_BLOCK_LABEL_372;
        if(i < startComboBox1 || i >= startComboBox1 + type.length)
            return;
        i -= startComboBox1;
        if(j < 0 || j >= type[i].item.length)
            return;
        pathair = new PathAir(builder.pathes, i, j);
        pathair.setArmy(type[i].item[j].army);
        defaultArmy[type[i].item[j].army].load(pathair);
        if(!searchEnabledSlots(pathair))
        {
            pathair.destroy();
            return;
        }
        pathair.setName(pathair.sRegiment + pathair.iSquadron + pathair.iWing);
        com.maddox.rts.Property.set(pathair, "builderPlugin", this);
        pathair.drawing(viewMap.containsKey(i));
        pair = new PAir(pathair, null, point3d, 0, defaultHeight, defaultSpeed);
        clampSpeed((com.maddox.il2.builder.PAir)pair);
        builder.setSelected(pair);
        com.maddox.il2.builder.PlMission.setChanged();
        break MISSING_BLOCK_LABEL_420;
        java.lang.Exception exception;
        exception;
        if(pathair != null && pathair.points() == 0)
            pathair.destroy();
        java.lang.System.out.println(exception);
    }

    public void changeType()
    {
        int i = builder.wSelect.comboBox1.getSelected() - startComboBox1;
        int j = builder.wSelect.comboBox2.getSelected();
        com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)builder.selectedPath();
        if(i != pathair._iType)
            return;
        pathair.skins = new java.lang.String[4];
        pathair.noseart = new java.lang.String[4];
        pathair._iItem = j;
        java.lang.Class class1 = type[i].item[j].clazz;
        java.lang.String as[] = com.maddox.il2.objects.air.Aircraft.getWeaponsRegistered(class1);
        if(as != null && as.length > 0)
        {
            int k = 0;
            do
            {
                if(k >= as.length)
                    break;
                java.lang.String s = as[k];
                if(s.equalsIgnoreCase(pathair.weapons))
                    break;
                k++;
            } while(true);
            if(k == as.length)
                pathair.weapons = as[0];
        }
        clampSpeed(pathair);
        fillDialogWay();
        fillSkins();
        fillNoseart();
        syncSkins();
        syncNoseart();
        syncPilots();
        resetMesh();
        com.maddox.il2.builder.PlMission.setChanged();
    }

    public void configure()
    {
        if(com.maddox.il2.builder.PlMisAir.getPlugin("Mission") == null)
            throw new RuntimeException("PlMisAir: plugin 'Mission' not found");
        pluginMission = (com.maddox.il2.builder.PlMission)com.maddox.il2.builder.PlMisAir.getPlugin("Mission");
        if(sectFile == null)
            throw new RuntimeException("PlMisAir: field 'sectFile' not defined");
        com.maddox.rts.SectFile sectfile = new SectFile(sectFile, 0);
        int i = sectfile.sections();
        if(i <= 0)
            throw new RuntimeException("PlMisAir: file '" + sectFile + "' is empty");
        type = new com.maddox.il2.builder.Type[i];
        for(int j = 0; j < i; j++)
        {
            java.lang.String s = sectfile.sectionName(j);
            int k = sectfile.vars(j);
            com.maddox.il2.builder.Item aitem[] = new com.maddox.il2.builder.Item[k];
            for(int l = 0; l < k; l++)
            {
                java.lang.String s1 = sectfile.var(j, l);
                com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.value(j, l));
                java.lang.String s2 = numbertokenizer.next((java.lang.String)null);
                int i1 = numbertokenizer.next(1, 1, com.maddox.il2.builder.Builder.armyAmount() - 1);
                java.lang.Class class1 = null;
                try
                {
                    class1 = com.maddox.rts.ObjIO.classForName(s2);
                }
                catch(java.lang.Exception exception)
                {
                    throw new RuntimeException("PlMisAir: class '" + s2 + "' not found");
                }
                aitem[l] = new Item(s1, class1, i1);
            }

            type[j] = new Type(s, aitem);
        }

    }

    void viewUpdate()
    {
        if(builder.pathes == null)
            return;
        java.lang.Object aobj[] = builder.pathes.getOwnerAttached();
        for(int i = 0; i < aobj.length; i++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)aobj[i];
            if(actor == null)
                break;
            if(actor instanceof com.maddox.il2.builder.PathAir)
            {
                com.maddox.il2.builder.PathAir pathair1 = (com.maddox.il2.builder.PathAir)actor;
                pathair1.drawing(viewMap.containsKey(pathair1._iType));
            }
        }

        if(builder.selectedPath() != null)
        {
            com.maddox.il2.builder.Path path = builder.selectedPath();
            if(path instanceof com.maddox.il2.builder.PathAir)
            {
                com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)path;
                if(!viewMap.containsKey(pathair._iType))
                    builder.setSelected(null);
            }
        }
        if(!builder.isFreeView())
            builder.repaint();
    }

    void viewType(int i, boolean flag)
    {
        if(flag)
            viewMap.put(i, null);
        else
            viewMap.remove(i);
        viewUpdate();
    }

    void viewType(int i)
    {
        viewType(i, viewType[i].bChecked);
    }

    public void viewTypeAll(boolean flag)
    {
        for(int i = 0; i < type.length; i++)
            if(viewType[i].bChecked != flag)
            {
                viewType[i].bChecked = flag;
                viewType(i, flag);
            }

    }

    private void fillComboBox1()
    {
        startComboBox1 = builder.wSelect.comboBox1.size();
        for(int i = 0; i < type.length; i++)
            builder.wSelect.comboBox1.add(com.maddox.il2.game.I18N.technic(type[i].name));

        if(startComboBox1 == 0)
            builder.wSelect.comboBox1.setSelected(0, true, false);
    }

    private void fillComboBox2(int i, int j)
    {
        if(i < startComboBox1 || i >= startComboBox1 + type.length)
            return;
        if(builder.wSelect.curFilledType != i)
        {
            builder.wSelect.curFilledType = i;
            builder.wSelect.comboBox2.clear(false);
            for(int k = 0; k < type[i - startComboBox1].item.length; k++)
                builder.wSelect.comboBox2.add(com.maddox.il2.game.I18N.plane(type[i - startComboBox1].item[k].name));

            builder.wSelect.comboBox1.setSelected(i, true, false);
        }
        builder.wSelect.comboBox2.setSelected(j, true, false);
        java.lang.Class class1 = type[i].item[j].clazz;
        builder.wSelect.setMesh(com.maddox.il2.objects.air.Aircraft.getPropertyMesh(class1, currentCountry()), false);
    }

    public java.lang.String[] actorInfo(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)actor.getOwner();
        _actorInfo[0] = com.maddox.il2.game.I18N.technic(type[pathair._iType].name) + "." + com.maddox.il2.game.I18N.plane(type[pathair._iType].item[pathair._iItem].name) + ":" + pathair.typedName;
        com.maddox.il2.builder.PAir pair = (com.maddox.il2.builder.PAir)actor;
        int i = pathair.pointIndx(pair);
        _actorInfo[1] = "(" + i + ") " + com.maddox.il2.builder.PlMisAir.timeSecToString(pair.time + (double)(int)(com.maddox.il2.ai.World.getTimeofDay() * 60F * 60F));
        return _actorInfo;
    }

    public void syncSelector()
    {
        builder.wSelect.tabsClient.addTab(1, tabActor);
        fillEditActor();
        com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)builder.selectedPath();
        fillComboBox2(pathair._iType + startComboBox1, pathair._iItem);
        builder.wSelect.tabsClient.addTab(2, tabWay);
        fillDialogWay();
        com.maddox.il2.builder.PathAir pathair1 = (com.maddox.il2.builder.PathAir)builder.selectedPath();
        for(int i = 0; i < pathair1.planes; i++)
        {
            builder.wSelect.tabsClient.addTab(i + 3, tabSkin[i]);
            fillEditSkin(i);
        }

        fillSkins();
        fillNoseart();
        fillPilots();
        syncSkins();
        syncNoseart();
        syncPilots();
        resetMesh();
    }

    public void updateSelector()
    {
        fillDialogWay();
    }

    public void updateSelectorMesh()
    {
        com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)builder.selectedPath();
        com.maddox.il2.objects.air.PaintScheme paintscheme = com.maddox.il2.objects.air.Aircraft.getPropertyPaintScheme(type[pathair._iType].item[pathair._iItem].clazz, pathair.regiment().country());
        if(paintscheme != null)
        {
            com.maddox.il2.engine.HierMesh hiermesh = builder.wSelect.getHierMesh();
            if(hiermesh != null)
                paintscheme.prepare(type[pathair._iType].item[pathair._iItem].clazz, hiermesh, pathair.regiment(), pathair.iSquadron, pathair.iWing, 0);
        }
    }

    public void createGUI()
    {
        fillComboBox1();
        fillComboBox2(0, 0);
        builder.wSelect.comboBox1.addNotifyListener(new com.maddox.gwindow.GNotifyListener() {

            public boolean notify(com.maddox.gwindow.GWindow gwindow, int k, int l)
            {
                int i1 = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
                if(i1 >= 0 && k == 2)
                    fillComboBox2(i1, 0);
                return false;
            }

        }
);
        builder.wSelect.comboBox2.addNotifyListener(new com.maddox.gwindow.GNotifyListener() {

            public boolean notify(com.maddox.gwindow.GWindow gwindow, int k, int l)
            {
                if(k != 2)
                    return false;
                int i1 = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
                if(i1 < startComboBox1 || i1 >= startComboBox1 + type.length)
                    return false;
                int j1 = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
                java.lang.Class class1 = type[i1 - startComboBox1].item[j1].clazz;
                com.maddox.il2.builder.Plugin.builder.wSelect.setMesh(com.maddox.il2.objects.air.Aircraft.getPropertyMesh(class1, currentCountry()), false);
                resetMesh();
                fillSkins();
                fillNoseart();
                fillPilots();
                syncSkins();
                syncNoseart();
                syncPilots();
                com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                if(pathair != null)
                    pathair.updateTypedName();
                fillEditActor();
                return false;
            }

        }
);
        int i;
        for(i = builder.mDisplayFilter.subMenu.size() - 1; i >= 0 && pluginMission.viewBridge != builder.mDisplayFilter.subMenu.getItem(i); i--);
        if(--i >= 0)
        {
            int j = i;
            i = type.length - 1;
            viewType = new com.maddox.il2.builder.ViewItem[type.length];
            for(; i >= 0; i--)
            {
                com.maddox.il2.builder.ViewItem viewitem = null;
                if("de".equals(com.maddox.rts.RTSConf.cur.locale.getLanguage()))
                    viewitem = (com.maddox.il2.builder.ViewItem)builder.mDisplayFilter.subMenu.addItem(j, new ViewItem(i, builder.mDisplayFilter.subMenu, com.maddox.il2.game.I18N.technic(type[i].name) + " " + com.maddox.il2.builder.PlMisAir.i18n("show"), null));
                else
                    viewitem = (com.maddox.il2.builder.ViewItem)builder.mDisplayFilter.subMenu.addItem(j, new ViewItem(i, builder.mDisplayFilter.subMenu, com.maddox.il2.builder.PlMisAir.i18n("show") + " " + com.maddox.il2.game.I18N.technic(type[i].name), null));
                viewitem.bChecked = true;
                viewType[i] = viewitem;
                viewType(i, true);
            }

        }
        initEditActor();
        initEditWay();
        initEditSkin();
    }

    private boolean searchEnabledSlots(com.maddox.il2.builder.PathAir pathair)
    {
        makeRegimentList(pathair.getArmy());
        int i = regimentList.size();
        if(pathair.iRegiment < 0)
            pathair.iRegiment = 0;
        if(pathair.iRegiment >= i)
            pathair.iRegiment = i - 1;
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.Regiment regiment = (com.maddox.il2.ai.Regiment)regimentList.get(pathair.iRegiment);
            pathair.sRegiment = regiment.name();
            if(isEnabledRegiment(regiment))
            {
                for(int k = 0; k < 4; k++)
                {
                    com.maddox.il2.builder.ResSquadron ressquadron = (com.maddox.il2.builder.ResSquadron)com.maddox.il2.engine.Actor.getByName(regiment.name() + pathair.iSquadron);
                    if(ressquadron == null)
                    {
                        defaultArmy[pathair.getArmy()].save(pathair);
                        return true;
                    }
                    if(isEnabledSquad(ressquadron))
                    {
                        for(int l = 0; l < 4; l++)
                        {
                            if(com.maddox.il2.engine.Actor.getByName(regiment.name() + pathair.iSquadron + pathair.iWing) == null)
                            {
                                defaultArmy[pathair.getArmy()].save(pathair);
                                return true;
                            }
                            pathair.iWing = (pathair.iWing + 1) % 4;
                        }

                    }
                    pathair.iWing = 0;
                    pathair.iSquadron = (pathair.iSquadron + 1) % 4;
                }

            } else
            {
                pathair.iWing = 0;
            }
            pathair.iSquadron = 0;
            pathair.iRegiment = (pathair.iRegiment + 1) % i;
        }

        return false;
    }

    private void fillEnabledRegiments(int i)
    {
        int j = regimentList.size();
        for(int k = 0; k < j; k++)
            if(k == i)
            {
                wRegiment.posEnable[k] = true;
            } else
            {
                com.maddox.il2.ai.Regiment regiment = (com.maddox.il2.ai.Regiment)regimentList.get(k);
                wRegiment.posEnable[k] = isEnabledRegiment(regiment);
            }

    }

    private java.lang.String currentCountry()
    {
        if(wRegiment == null)
            return "ru";
        int i = wRegiment.getSelected();
        if(i < 0)
            return "ru";
        else
            return ((com.maddox.il2.ai.Regiment)regimentList.get(i)).country();
    }

    private boolean isEnabledRegiment(com.maddox.il2.ai.Regiment regiment)
    {
        if(regiment.getOwnerAttachedCount() < 4)
            return true;
        _squads = regiment.getOwnerAttached(_squads);
        boolean flag = false;
        for(int i = 0; i < 4; i++)
        {
            com.maddox.il2.builder.ResSquadron ressquadron = (com.maddox.il2.builder.ResSquadron)_squads[i];
            _squads[i] = null;
            if(ressquadron == null || isEnabledSquad(ressquadron))
                flag = true;
        }

        return flag;
    }

    private void fillEnabledSquads(com.maddox.il2.ai.Regiment regiment, int i)
    {
        for(int j = 0; j < 4; j++)
            wSquadron.posEnable[j] = true;

        _squads = regiment.getOwnerAttached(_squads);
        for(int k = 0; k < 4; k++)
        {
            com.maddox.il2.builder.ResSquadron ressquadron = (com.maddox.il2.builder.ResSquadron)_squads[k];
            if(ressquadron == null)
                break;
            _squads[k] = null;
            if(ressquadron.index() != i)
                wSquadron.posEnable[ressquadron.index()] = isEnabledSquad(ressquadron);
        }

    }

    private boolean isEnabledSquad(com.maddox.il2.builder.ResSquadron ressquadron)
    {
        return ressquadron.getAttachedCount() < 4;
    }

    private void fillEnabledWings(com.maddox.il2.builder.ResSquadron ressquadron, int i)
    {
        for(int j = 0; j < 4; j++)
            wWing.posEnable[j] = true;

        _wings = ressquadron.getAttached(_wings);
        for(int k = 0; k < 4; k++)
        {
            com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)_wings[k];
            if(pathair == null)
                break;
            _wings[k] = null;
            if(pathair.iWing != i)
                wWing.posEnable[pathair.iWing] = false;
        }

    }

    private void controlResized(com.maddox.gwindow.GWindowDialogClient gwindowdialogclient, com.maddox.gwindow.GWindow gwindow)
    {
        if(gwindow == null)
        {
            return;
        } else
        {
            gwindow.setSize(gwindowdialogclient.win.dx - gwindow.win.x - gwindowdialogclient.lAF().metric(1.0F), gwindow.win.dy);
            return;
        }
    }

    private void editResized(com.maddox.gwindow.GWindowDialogClient gwindowdialogclient)
    {
        controlResized(gwindowdialogclient, wArmy);
        controlResized(gwindowdialogclient, wRegiment);
        controlResized(gwindowdialogclient, wSquadron);
        controlResized(gwindowdialogclient, wWing);
        controlResized(gwindowdialogclient, wWeapons);
        controlResized(gwindowdialogclient, wFuel);
        controlResized(gwindowdialogclient, wPlanes);
        controlResized(gwindowdialogclient, wSkill);
    }

    public void initEditActor()
    {
        com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)builder.wSelect.tabsClient.create(new com.maddox.gwindow.GWindowDialogClient() {

            public void resized()
            {
                super.resized();
                editResized(this);
            }

        }
);
        tabActor = builder.wSelect.tabsClient.createTab(com.maddox.il2.builder.PlMisAir.i18n("AircraftActor"), gwindowdialogclient);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 1.0F, 7F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("Army"), null));
        gwindowdialogclient.addControl(wArmy = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 9F, 1.0F, 7F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                for(int i = 1; i < com.maddox.il2.builder.Builder.armyAmount(); i++)
                    add(com.maddox.il2.game.I18N.army(com.maddox.il2.ai.Army.name(i)));

            }

            public boolean notify(int i, int j)
            {
                if(i != 2)
                    return false;
                com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                int k = getSelected() + 1;
                int l = pathair.getArmy();
                int i1 = pathair.iRegiment;
                int j1 = pathair.iSquadron;
                int k1 = pathair.iWing;
                pathair.setArmy(k);
                defaultArmy[k].load(pathair);
                if(!searchEnabledSlots(pathair))
                {
                    pathair.setArmy(l);
                    pathair.iRegiment = i1;
                    pathair.iSquadron = j1;
                    pathair.iWing = k1;
                    searchEnabledSlots(pathair);
                }
                pathair.setName(pathair.sRegiment + pathair.iSquadron + pathair.iWing);
                fillEditActor();
                fillNoseart();
                syncNoseart();
                java.lang.Class class1 = type[pathair._iType].item[pathair._iItem].clazz;
                com.maddox.il2.builder.Plugin.builder.wSelect.setMesh(com.maddox.il2.objects.air.Aircraft.getPropertyMesh(class1, currentCountry()), false);
                resetMesh();
                if(com.maddox.il2.builder.Path.player == pathair)
                    com.maddox.il2.builder.PlMission.cur.missionArmy = k;
                com.maddox.il2.builder.PlMission.setChanged();
                com.maddox.il2.builder.PlMission.checkShowCurrentArmy();
                return false;
            }

        }
);
        gwindowdialogclient.addControl(wRegiment = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 1.0F, 3F, 15F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
            }

            public boolean notify(int i, int j)
            {
                if(i != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                    pathair.iRegiment = getSelected();
                    searchEnabledSlots(pathair);
                    pathair.setName(pathair.sRegiment + pathair.iSquadron + pathair.iWing);
                    defaultArmy[pathair.getArmy()].save(pathair);
                    fillEditActor();
                    fillNoseart();
                    syncNoseart();
                    java.lang.Class class1 = type[pathair._iType].item[pathair._iItem].clazz;
                    com.maddox.il2.builder.Plugin.builder.wSelect.setMesh(com.maddox.il2.objects.air.Aircraft.getPropertyMesh(class1, currentCountry()), false);
                    resetMesh();
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 5F, 7F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("Squadron"), null));
        gwindowdialogclient.addControl(wSquadron = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 9F, 5F, 7F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                add("1");
                add("2");
                add("3");
                add("4");
                posEnable = new boolean[4];
            }

            public boolean notify(int i, int j)
            {
                if(i != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                    pathair.iSquadron = getSelected();
                    searchEnabledSlots(pathair);
                    pathair.setName(pathair.sRegiment + pathair.iSquadron + pathair.iWing);
                    defaultArmy[pathair.getArmy()].save(pathair);
                    fillEditActor();
                    resetMesh();
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 7F, 7F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("Wing"), null));
        gwindowdialogclient.addControl(wWing = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 9F, 7F, 7F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                add("1");
                add("2");
                add("3");
                add("4");
                posEnable = new boolean[4];
            }

            public boolean notify(int i, int j)
            {
                if(i != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                    pathair.iWing = getSelected();
                    pathair.setName(pathair.sRegiment + pathair.iSquadron + pathair.iWing);
                    defaultArmy[pathair.getArmy()].save(pathair);
                    fillEditActor();
                    resetMesh();
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        lWeapons = new ArrayList();
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 9F, 7F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("Weapons"), null));
        gwindowdialogclient.addControl(wWeapons = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 9F, 9F, 7F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
            }

            public boolean notify(int i, int j)
            {
                if(i != 2)
                    return false;
                com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                int k = getSelected();
                if(k < 0)
                    pathair.weapons = null;
                else
                    pathair.weapons = (java.lang.String)lWeapons.get(k);
                com.maddox.il2.builder.PlMission.setChanged();
                return false;
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 11F, 7F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("Fuel"), null));
        gwindowdialogclient.addControl(wFuel = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 9F, 11F, 7F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bDelayedNotify = true;
                bNumericOnly = true;
            }

            public boolean notify(int i, int j)
            {
                if(i != 2)
                    return false;
                com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                java.lang.String s = getValue();
                int k = 0;
                if(s != null)
                    try
                    {
                        k = java.lang.Integer.parseInt(s);
                    }
                    catch(java.lang.Exception exception)
                    {
                        setValue("" + ((com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath()).fuel, false);
                        return false;
                    }
                if(k < 0)
                    k = 0;
                else
                if(k > 100)
                    k = 100;
                pathair.fuel = k;
                com.maddox.il2.builder.PlMission.setChanged();
                return false;
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 13F, 7F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("Planes"), null));
        gwindowdialogclient.addControl(wPlanes = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 9F, 13F, 7F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                add("1");
                add("2");
                add("3");
                add("4");
            }

            public boolean notify(int i, int j)
            {
                if(i != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                    pathair.planes = getSelected() + 1;
                    checkEditSkinTabs();
                    checkEditSkinSkills();
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 15F, 7F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("Skill"), null));
        gwindowdialogclient.addControl(wSkill = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 9F, 15F, 7F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                add(com.maddox.il2.builder.Plugin.i18n("Rookie"));
                add(com.maddox.il2.builder.Plugin.i18n("Average"));
                add(com.maddox.il2.builder.Plugin.i18n("Veteran"));
                add(com.maddox.il2.builder.Plugin.i18n("Ace"));
            }

            public boolean notify(int i, int j)
            {
                if(i != 2)
                    return false;
                com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                pathair.skill = getSelected();
                for(int k = 0; k < 4; k++)
                    pathair.skills[k] = pathair.skill;

                checkEditSkinSkills();
                com.maddox.il2.builder.PlMission.setChanged();
                return false;
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 17F, 7F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("OnlyAI"), null));
        gwindowdialogclient.addControl(wOnlyAI = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 9F, 17F, null) {

            public void preRender()
            {
                super.preRender();
                com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                if(pathair == null)
                {
                    return;
                } else
                {
                    setChecked(pathair.bOnlyAI, false);
                    setEnable(type[pathair._iType].item[pathair._iItem].bEnablePlayer);
                    return;
                }
            }

            public boolean notify(int i, int j)
            {
                if(i != 2)
                    return false;
                com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                pathair.bOnlyAI = isChecked();
                if(isChecked() && com.maddox.il2.builder.Path.player == pathair)
                {
                    com.maddox.il2.builder.Path.player = null;
                    com.maddox.il2.builder.Path.playerNum = 0;
                }
                com.maddox.il2.builder.PlMission.setChanged();
                return false;
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 19F, 7F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("Parachute"), null));
        gwindowdialogclient.addControl(wParachute = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 9F, 19F, null) {

            public void preRender()
            {
                super.preRender();
                com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                if(pathair == null)
                {
                    return;
                } else
                {
                    setChecked(pathair.bParachute, false);
                    return;
                }
            }

            public boolean notify(int i, int j)
            {
                if(i != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                    pathair.bParachute = isChecked();
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
    }

    private void fillEditEnabled(com.maddox.il2.builder.PathAir pathair)
    {
        makeRegimentList(pathair.getArmy());
        fillEnabledRegiments(pathair.iRegiment);
        fillEnabledSquads(pathair.regiment(), pathair.iSquadron);
        fillEnabledWings(pathair.squadron(), pathair.iWing);
        defaultArmy[pathair.getArmy()].save(pathair);
    }

    public void fillEditActor()
    {
        com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)builder.selectedPath();
        if(pathair == null)
            return;
        fillEditEnabled(pathair);
        wRegiment.setSelected(pathair.iRegiment, true, false);
        wSquadron.setSelected(pathair.iSquadron, true, false);
        wWing.setSelected(pathair.iWing, true, false);
        wArmy.setSelected(pathair.getArmy() - 1, true, false);
        wFuel.setValue("" + pathair.fuel, false);
        wPlanes.setSelected(pathair.planes - 1, true, false);
        if(pathair.skill != -1)
            wSkill.setSelected(pathair.skill, true, false);
        else
            wSkill.setValue(com.maddox.il2.builder.PlMisAir.i18n("Custom"));
        wWeapons.clear(false);
        lWeapons.clear();
        java.lang.Class class1 = type[pathair._iType].item[pathair._iItem].clazz;
        java.lang.String as[] = com.maddox.il2.objects.air.Aircraft.getWeaponsRegistered(class1);
        java.lang.String s = type[pathair._iType].item[pathair._iItem].name;
        if(as != null && as.length > 0)
        {
            int i = -1;
            for(int j = 0; j < as.length; j++)
            {
                java.lang.String s1 = as[j];
                if(s1.equalsIgnoreCase(pathair.weapons))
                    i = j;
                wWeapons.add(com.maddox.il2.game.I18N.weapons(s, s1));
                lWeapons.add(s1);
            }

            if(i == -1)
                i = 0;
            wWeapons.setSelected(i, true, false);
        }
    }

    private void fillDialogWay()
    {
        com.maddox.il2.builder.PAir pair = (com.maddox.il2.builder.PAir)builder.selectedPoint();
        com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)builder.selectedPath();
        int i = pathair.pointIndx(pair);
        wPrev.setEnable(i > 0);
        wNext.setEnable(i < pathair.points() - 1);
        wCur.cap = new GCaption("" + i + "(" + pathair.points() + ")");
        wHeight.setValue("" + (int)pair.height, false);
        wSpeed.setValue("" + (int)pair.speed, false);
        wType.setSelected(pair.type(), true, false);
        for(int j = 0; j < com.maddox.il2.builder.PAir.types.length; j++)
            wType.posEnable[j] = true;

        if(i > 0)
            wType.posEnable[1] = false;
        if(i < pathair.points() - 1)
            wType.posEnable[2] = false;
        _curPointType = pair.type();
        int k = (int)java.lang.Math.round(pair.time / 60D + (double)(com.maddox.il2.ai.World.getTimeofDay() * 60F));
        wTimeH.setValue("" + (k / 60) % 24, false);
        wTimeM.setValue("" + k % 60, false);
        if(pair.getTarget() != null)
        {
            if(pair.getTarget() instanceof com.maddox.il2.builder.PPoint)
            {
                if(pair.getTarget() instanceof com.maddox.il2.builder.PAir)
                    wTarget.cap.set(((com.maddox.il2.builder.PathAir)pair.getTarget().getOwner()).typedName);
                else
                if(pair.getTarget() instanceof com.maddox.il2.builder.PNodes)
                    wTarget.cap.set(com.maddox.rts.Property.stringValue(pair.getTarget().getOwner(), "i18nName", ""));
                else
                    wTarget.cap.set(pair.getTarget().getOwner().name());
            } else
            {
                wTarget.cap.set(com.maddox.rts.Property.stringValue(pair.getTarget().getClass(), "i18nName", ""));
            }
        } else
        {
            wTarget.cap.set("");
        }
    }

    public void initEditWay()
    {
        com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)builder.wSelect.tabsClient.create(new GWindowDialogClient());
        tabWay = builder.wSelect.tabsClient.createTab(com.maddox.il2.builder.PlMisAir.i18n("Waypoint"), gwindowdialogclient);
        gwindowdialogclient.addControl(wPrev = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 1.0F, 1.0F, 5F, 1.6F, com.maddox.il2.builder.PlMisAir.i18n("&Prev"), null) {

            public boolean notify(int i, int j)
            {
                if(i == 2)
                {
                    com.maddox.il2.builder.PAir pair = (com.maddox.il2.builder.PAir)com.maddox.il2.builder.Plugin.builder.selectedPoint();
                    com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                    int k = pathair.pointIndx(pair);
                    if(k > 0)
                    {
                        com.maddox.il2.builder.Plugin.builder.setSelected(pathair.point(k - 1));
                        fillDialogWay();
                        com.maddox.il2.builder.Plugin.builder.repaint();
                    }
                    return true;
                } else
                {
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addControl(wNext = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 9F, 1.0F, 5F, 1.6F, com.maddox.il2.builder.PlMisAir.i18n("&Next"), null) {

            public boolean notify(int i, int j)
            {
                if(i == 2)
                {
                    com.maddox.il2.builder.PAir pair = (com.maddox.il2.builder.PAir)com.maddox.il2.builder.Plugin.builder.selectedPoint();
                    com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                    int k = pathair.pointIndx(pair);
                    if(k < pathair.points() - 1)
                    {
                        com.maddox.il2.builder.Plugin.builder.setSelected(pathair.point(k + 1));
                        fillDialogWay();
                        com.maddox.il2.builder.Plugin.builder.repaint();
                    }
                    return true;
                } else
                {
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(wCur = new GWindowLabel(gwindowdialogclient, 15F, 1.0F, 4F, 1.6F, "1(1)", null));
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 3F, 7F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("Height"), null));
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 15F, 3F, 4F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("[M]"), null));
        gwindowdialogclient.addControl(wHeight = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 9F, 3F, 5F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public boolean notify(int i, int j)
            {
                if(i != 2)
                    return false;
                com.maddox.il2.builder.PAir pair = (com.maddox.il2.builder.PAir)com.maddox.il2.builder.Plugin.builder.selectedPoint();
                java.lang.String s = getValue();
                double d = 0.0D;
                try
                {
                    d = java.lang.Double.parseDouble(s);
                }
                catch(java.lang.Exception exception)
                {
                    setValue("" + ((com.maddox.il2.builder.PAir)com.maddox.il2.builder.Plugin.builder.selectedPoint()).height, false);
                    return false;
                }
                if(d < 0.0D)
                    d = 0.0D;
                else
                if(d > 12000D)
                    d = 12000D;
                pair.height = d;
                defaultHeight = d;
                com.maddox.il2.builder.PlMission.setChanged();
                return false;
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 5F, 7F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("Speed"), null));
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 15F, 5F, 4F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("[kM/H]"), null));
        gwindowdialogclient.addControl(wSpeed = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 9F, 5F, 5F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public boolean notify(int i, int j)
            {
                if(i != 2)
                    return false;
                com.maddox.il2.builder.PAir pair = (com.maddox.il2.builder.PAir)com.maddox.il2.builder.Plugin.builder.selectedPoint();
                java.lang.String s = getValue();
                double d = 0.0D;
                try
                {
                    d = java.lang.Double.parseDouble(s);
                }
                catch(java.lang.Exception exception)
                {
                    setValue("" + ((com.maddox.il2.builder.PAir)com.maddox.il2.builder.Plugin.builder.selectedPoint()).speed, false);
                    return false;
                }
                com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                if(pair.type() == 1 || pair.type() == 2)
                    d = 0.0D;
                else
                if(d < type[pathair._iType].item[pathair._iItem].speedMin)
                {
                    d = type[pathair._iType].item[pathair._iItem].speedMin;
                    defaultSpeed = d;
                } else
                if(d > type[pathair._iType].item[pathair._iItem].speedMax)
                {
                    d = type[pathair._iType].item[pathair._iItem].speedMax;
                    defaultSpeed = d;
                } else
                {
                    defaultSpeed = d;
                }
                pair.speed = d;
                pathair.computeTimes();
                com.maddox.il2.builder.PlMission.setChanged();
                return false;
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 7F, 7F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("Time"), null));
        gwindowdialogclient.addControl(wTimeH = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 9F, 7F, 2.0F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public boolean notify(int i, int j)
            {
                if(i != 2)
                {
                    return false;
                } else
                {
                    getTimeOut();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 11.2F, 7F, 1.0F, 1.3F, ":", null));
        gwindowdialogclient.addControl(wTimeM = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 11.5F, 7F, 2.0F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public boolean notify(int i, int j)
            {
                if(i != 2)
                {
                    return false;
                } else
                {
                    getTimeOut();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 9F, 7F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("lType"), null));
        gwindowdialogclient.addControl(wType = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 9F, 9F, 7F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                for(int i = 0; i < com.maddox.il2.builder.PAir.types.length; i++)
                    add(com.maddox.il2.builder.Plugin.i18n(com.maddox.il2.builder.PAir.types[i]));

                boolean aflag[] = new boolean[com.maddox.il2.builder.PAir.types.length];
                for(int j = 0; j < com.maddox.il2.builder.PAir.types.length; j++)
                    aflag[j] = true;

                posEnable = aflag;
            }

            public boolean notify(int i, int j)
            {
                if(i == 2)
                {
                    if(_curPointType != iSelected)
                    {
                        com.maddox.il2.builder.PAir pair = (com.maddox.il2.builder.PAir)com.maddox.il2.builder.Plugin.builder.selectedPoint();
                        boolean flag = true;
                        if(_curPointType == 1 || _curPointType == 2)
                            flag = false;
                        boolean flag1 = true;
                        if(iSelected == 1 || iSelected == 2)
                            flag1 = false;
                        if(flag != flag1)
                            if(flag1)
                            {
                                pair.height = defaultHeight;
                                pair.speed = defaultSpeed;
                                clampSpeed(pair);
                                wHeight.setValue("" + (int)defaultHeight, false);
                                wSpeed.setValue("" + (int)pair.speed, false);
                            } else
                            {
                                wHeight.setValue("0", false);
                                wSpeed.setValue("0", false);
                                pair.height = 0.0D;
                                pair.speed = 0.0D;
                            }
                        _curPointType = iSelected;
                        pair.setType(iSelected);
                        com.maddox.il2.builder.PlMission.setChanged();
                        if(iSelected == 1 || iSelected == 2)
                        {
                            com.maddox.il2.ai.Airport airport = com.maddox.il2.ai.Airport.nearest(pair.pos.getAbsPoint(), -1, 7);
                            if(airport != null)
                                if(airport.nearestRunway(pair.pos.getAbsPoint(), com.maddox.il2.builder.PlMisAir.nearestRunway))
                                    pair.pos.setAbs(com.maddox.il2.builder.PlMisAir.nearestRunway.getPoint());
                                else
                                    pair.pos.setAbs(airport.pos.getAbsPoint());
                            pair.setTarget(null);
                            pair.sTarget = null;
                            wTarget.cap.set("");
                        } else
                        {
                            com.maddox.il2.engine.Actor actor = pair.getTarget();
                            if(actor != null && (iSelected == 0 && !(actor instanceof com.maddox.il2.builder.PAir) || iSelected == 3 && (actor instanceof com.maddox.il2.builder.PAir)))
                            {
                                pair.setTarget(null);
                                pair.sTarget = null;
                                wTarget.cap.set("");
                            }
                        }
                        com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                        pathair.computeTimes();
                    }
                    return true;
                } else
                {
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 11F, 7F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("RadioSilence"), null));
        gwindowdialogclient.addControl(wRadioSilence = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 9F, 11F, null) {

            public void preRender()
            {
                super.preRender();
                com.maddox.il2.builder.PAir pair = (com.maddox.il2.builder.PAir)com.maddox.il2.builder.Plugin.builder.selectedPoint();
                if(pair == null)
                {
                    return;
                } else
                {
                    setChecked(pair.bRadioSilence, false);
                    return;
                }
            }

            public boolean notify(int i, int j)
            {
                if(i != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.PAir pair = (com.maddox.il2.builder.PAir)com.maddox.il2.builder.Plugin.builder.selectedPoint();
                    pair.bRadioSilence = isChecked();
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 13F, 7F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("Target"), null));
        gwindowdialogclient.addLabel(wTarget = new GWindowLabel(gwindowdialogclient, 9F, 13F, 7F, 1.3F, "", null));
        gwindowdialogclient.addControl(wTargetSet = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 1.0F, 15F, 5F, 1.6F, com.maddox.il2.builder.PlMisAir.i18n("&Set"), null) {

            public boolean notify(int i, int j)
            {
                if(i == 2)
                    com.maddox.il2.builder.Plugin.builder.beginSelectTarget();
                return false;
            }

        }
);
        gwindowdialogclient.addControl(wTargetClear = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 9F, 15F, 5F, 1.6F, com.maddox.il2.builder.PlMisAir.i18n("&Clear"), null) {

            public boolean notify(int i, int j)
            {
                if(i == 2)
                {
                    com.maddox.il2.builder.PAir pair = (com.maddox.il2.builder.PAir)com.maddox.il2.builder.Plugin.builder.selectedPoint();
                    pair.setTarget(null);
                    pair.sTarget = null;
                    wTarget.cap.set("");
                    com.maddox.il2.builder.PlMission.setChanged();
                }
                return false;
            }

        }
);
    }

    private void getTimeOut()
    {
        com.maddox.il2.builder.PAir pair = (com.maddox.il2.builder.PAir)builder.selectedPoint();
        com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)builder.selectedPath();
        java.lang.String s = wTimeH.getValue();
        double d = 0.0D;
        try
        {
            d = java.lang.Double.parseDouble(s);
        }
        catch(java.lang.Exception exception) { }
        if(d < 0.0D)
            d = 0.0D;
        if(d > 23D)
            d = 23D;
        s = wTimeM.getValue();
        double d1 = 0.0D;
        try
        {
            d1 = java.lang.Double.parseDouble(s);
        }
        catch(java.lang.Exception exception1) { }
        if(d1 < 0.0D)
            d1 = 0.0D;
        if(d1 > 59D)
            d1 = 59D;
        double d2 = (d * 60D + d1) * 60D - (double)java.lang.Math.round(com.maddox.il2.ai.World.getTimeofDay() * 60F * 60F);
        if(d2 < 0.0D)
            d2 = 0.0D;
        int i = pathair.pointIndx(pair);
        if(i == 0)
        {
            if(pathair == com.maddox.il2.builder.Path.player)
                pair.time = 0.0D;
            else
                pair.time = d2;
        } else
        if(pair.type() != 2)
        {
            com.maddox.il2.builder.PPoint ppoint = pathair.point(i - 1);
            double d3 = d2 - ppoint.time;
            double d4 = 0.0D;
            if(d3 <= 0.0D)
            {
                d4 = type[pathair._iType].item[pathair._iItem].speedMax;
            } else
            {
                double d5 = ppoint.pos.getAbsPoint().distance(pair.pos.getAbsPoint());
                d4 = ((d5 / d3) * 3600D) / 1000D;
                if(d4 > type[pathair._iType].item[pathair._iItem].speedMax)
                    d4 = type[pathair._iType].item[pathair._iItem].speedMax;
                if(d4 < type[pathair._iType].item[pathair._iItem].speedMin)
                    d4 = type[pathair._iType].item[pathair._iItem].speedMin;
            }
            pair.speed = d4;
        }
        pathair.computeTimes();
        com.maddox.il2.builder.PlMission.setChanged();
    }

    private void fillEditSkin(int i)
    {
        com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)builder.selectedPath();
        if(pathair == null)
        {
            return;
        } else
        {
            wSkills[i].setSelected(pathair.skills[i], true, false);
            return;
        }
    }

    private void fillSkins()
    {
        for(int i = 0; i < 4; i++)
        {
            wSkins[i].clear(false);
            wSkins[i].add(com.maddox.il2.builder.PlMisAir.i18n("Default"));
        }

        java.lang.String s;
        int j;
        s = com.maddox.il2.game.Main.cur().netFileServerSkin.primaryPath();
        j = builder.wSelect.comboBox1.getSelected();
        if(j < startComboBox1 || j >= startComboBox1 + type.length)
            return;
        try
        {
            j -= startComboBox1;
            int k = builder.wSelect.comboBox2.getSelected();
            java.lang.String s1 = com.maddox.il2.gui.GUIAirArming.validateFileName(type[j].item[k].name);
            java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName(s + "/" + s1, 0));
            java.io.File afile[] = file.listFiles();
            if(afile != null)
            {
                for(int l = 0; l < afile.length; l++)
                {
                    java.io.File file1 = afile[l];
                    if(file1.isFile())
                    {
                        java.lang.String s2 = file1.getName();
                        java.lang.String s3 = s2.toLowerCase();
                        if(s3.endsWith(".bmp") && s3.length() + s1.length() <= 122)
                        {
                            int i1 = com.maddox.il2.engine.BmpUtils.squareSizeBMP8Pal(s + "/" + s1 + "/" + s2);
                            if(i1 == 512 || i1 == 1024)
                            {
                                for(int j1 = 0; j1 < 4; j1++)
                                    wSkins[j1].add(s2);

                            } else
                            {
                                java.lang.System.out.println("Skin " + s + "/" + s1 + "/" + s2 + " NOT loaded");
                            }
                        }
                    }
                }

            }
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return;
    }

    private void syncSkins()
    {
        if(!(builder.selectedPath() instanceof com.maddox.il2.builder.PathAir))
            return;
        com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)builder.selectedPath();
        if(pathair == null)
            return;
        for(int i = 0; i < 4; i++)
            if(!syncComboControl(wSkins[i], pathair.skins[i]))
                pathair.skins[i] = null;

    }

    private void fillNoseart()
    {
        for(int i = 0; i < 4; i++)
        {
            wNoseart[i].clear(false);
            wNoseart[i].add(com.maddox.il2.builder.PlMisAir.i18n("None"));
        }

        com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)builder.selectedPath();
        if(pathair == null)
            return;
        java.lang.Class class1 = type[pathair._iType].item[pathair._iItem].clazz;
        boolean flag = com.maddox.rts.Property.intValue(class1, "noseart", 0) != 1;
        if(!flag)
        {
            int j = pathair.iRegiment;
            if(j < 0 && j >= regimentList.size())
            {
                flag = true;
            } else
            {
                com.maddox.il2.ai.Regiment regiment = (com.maddox.il2.ai.Regiment)regimentList.get(j);
                flag = !"us".equals(regiment.country());
            }
        }
        if(flag)
        {
            for(int k = 0; k < 4; k++)
                wNoseart[k].setEnable(false);

            return;
        }
        for(int l = 0; l < 4; l++)
            wNoseart[l].setEnable(true);

        try
        {
            java.lang.String s = com.maddox.il2.game.Main.cur().netFileServerNoseart.primaryPath();
            java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName(s, 0));
            java.io.File afile[] = file.listFiles();
            if(afile != null)
            {
                for(int i1 = 0; i1 < afile.length; i1++)
                {
                    java.io.File file1 = afile[i1];
                    if(file1.isFile())
                    {
                        java.lang.String s1 = file1.getName();
                        java.lang.String s2 = s1.toLowerCase();
                        if(s2.endsWith(".bmp") && s2.length() <= 122)
                            if(com.maddox.il2.engine.BmpUtils.checkBMP8Pal(s + "/" + s1, 256, 512))
                            {
                                for(int j1 = 0; j1 < 4; j1++)
                                    wNoseart[j1].add(s1);

                            } else
                            {
                                java.lang.System.out.println("Noseart " + s + "/" + s1 + " NOT loaded");
                            }
                    }
                }

            }
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void syncNoseart()
    {
        com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)builder.selectedPath();
        if(pathair == null)
            return;
        for(int i = 0; i < 4; i++)
            if(!syncComboControl(wNoseart[i], pathair.noseart[i]))
                pathair.noseart[i] = null;

    }

    private void fillPilots()
    {
        for(int i = 0; i < 4; i++)
        {
            wPilots[i].clear(false);
            wPilots[i].add(com.maddox.il2.builder.PlMisAir.i18n("Default"));
        }

        try
        {
            java.lang.String s = com.maddox.il2.game.Main.cur().netFileServerPilot.primaryPath();
            java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName(s, 0));
            java.io.File afile[] = file.listFiles();
            if(afile != null)
            {
                for(int j = 0; j < afile.length; j++)
                {
                    java.io.File file1 = afile[j];
                    if(file1.isFile())
                    {
                        java.lang.String s1 = file1.getName();
                        java.lang.String s2 = s1.toLowerCase();
                        if(s2.endsWith(".bmp") && s2.length() <= 122)
                            if(com.maddox.il2.engine.BmpUtils.checkBMP8Pal(s + "/" + s1, 256, 256))
                            {
                                for(int k = 0; k < 4; k++)
                                    wPilots[k].add(s1);

                            } else
                            {
                                java.lang.System.out.println("Pilot " + s + "/" + s1 + " NOT loaded");
                            }
                    }
                }

            }
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void syncPilots()
    {
        com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)builder.selectedPath();
        if(pathair == null)
            return;
        for(int i = 0; i < 4; i++)
            if(!syncComboControl(wPilots[i], pathair.pilots[i]))
                pathair.pilots[i] = null;

    }

    private boolean syncComboControl(com.maddox.gwindow.GWindowComboControl gwindowcombocontrol, java.lang.String s)
    {
        if(s == null)
        {
            gwindowcombocontrol.setSelected(0, true, false);
            return true;
        }
        int i = gwindowcombocontrol.size();
        for(int j = 1; j < i; j++)
        {
            java.lang.String s1 = gwindowcombocontrol.get(j);
            if(s.equals(s1))
            {
                gwindowcombocontrol.setSelected(j, true, false);
                return true;
            }
        }

        gwindowcombocontrol.setSelected(0, true, false);
        return false;
    }

    private void checkEditSkinTabs()
    {
        com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)builder.selectedPath();
        int i = builder.wSelect.tabsClient.sizeTabs();
        if(pathair.planes == i - 3)
            return;
        if(pathair.planes > i - 3)
        {
            for(int j = i - 3; j < pathair.planes; j++)
            {
                builder.wSelect.tabsClient.addTab(j + 3, tabSkin[j]);
                fillEditSkin(j);
            }

        } else
        {
            int k = builder.wSelect.tabsClient.current;
            for(; builder.wSelect.tabsClient.sizeTabs() - 3 > pathair.planes; builder.wSelect.tabsClient.removeTab(builder.wSelect.tabsClient.sizeTabs() - 1));
            builder.wSelect.tabsClient.setCurrent(k, false);
            if(pathair == com.maddox.il2.builder.Path.player && pathair.planes >= com.maddox.il2.builder.Path.playerNum)
                com.maddox.il2.builder.Path.playerNum = 0;
        }
    }

    private void checkEditSkinSkills()
    {
        com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)builder.selectedPath();
        if(pathair == null)
            return;
        int i = pathair.skills[0];
        wSkills[0].setSelected(pathair.skills[0], true, false);
        pathair.skill = -2;
        for(int j = 1; j < pathair.planes; j++)
        {
            if(pathair.skills[j] != i)
                pathair.skill = -1;
            wSkills[j].setSelected(pathair.skills[j], true, false);
        }

        if(pathair.skill == -1)
        {
            wSkill.setValue(com.maddox.il2.builder.PlMisAir.i18n("Custom"));
        } else
        {
            pathair.skill = i;
            wSkill.setSelected(pathair.skill, true, false);
        }
    }

    private void resetMesh()
    {
        for(int i = 0; i < 4; i++)
            resetMesh(i);

    }

    private void resetMesh(int i)
    {
        if(com.maddox.il2.engine.Actor.isValid(actorMesh[i]))
        {
            actorMesh[i].destroy();
            actorMesh[i] = null;
        }
    }

    private void checkMesh(int i)
    {
        if(com.maddox.il2.engine.Actor.isValid(actorMesh[i]))
            return;
        com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)builder.selectedPath();
        if(pathair == null)
        {
            return;
        } else
        {
            java.lang.Class class1 = type[pathair._iType].item[pathair._iItem].clazz;
            meshName = com.maddox.il2.objects.air.Aircraft.getPropertyMesh(class1, pathair.regiment().country());
            createMesh(i);
            com.maddox.il2.objects.air.PaintScheme paintscheme = com.maddox.il2.objects.air.Aircraft.getPropertyPaintScheme(class1, pathair.regiment().country());
            paintscheme.prepare(class1, actorMesh[i].hierMesh(), pathair.regiment(), pathair.iSquadron, pathair.iWing, i, pathair.bNumberOn[i]);
            prepareSkin(i, class1, pathair.skins[i]);
            prepareNoseart(i, pathair.noseart[i]);
            preparePilot(i, pathair.pilots[i]);
            return;
        }
    }

    private void createMesh(int i)
    {
        if(com.maddox.il2.engine.Actor.isValid(actorMesh[i]))
            return;
        if(meshName == null)
        {
            return;
        } else
        {
            double d = 20D;
            actorMesh[i] = new ActorSimpleHMesh(meshName);
            d = actorMesh[i].hierMesh().visibilityR();
            actorMesh[i].pos.setAbs(new Orient(90F, 0.0F, 0.0F));
            d *= java.lang.Math.cos(0.26179938779914941D) / java.lang.Math.sin(((double)camera3D[i].FOV() * 3.1415926535897931D) / 180D / 2D);
            camera3D[i].pos.setAbs(new Point3d(d, 0.0D, 0.0D), new Orient(180F, 0.0F, 0.0F));
            camera3D[i].pos.reset();
            return;
        }
    }

    private void prepareSkin(int i, java.lang.Class class1, java.lang.String s)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actorMesh[i]))
            return;
        if(s != null)
        {
            s = com.maddox.il2.game.Main.cur().netFileServerSkin.primaryPath() + "/" + com.maddox.il2.gui.GUIAirArming.validateFileName(com.maddox.rts.Property.stringValue(class1, "keyName", null)) + "/" + s;
            java.lang.String s1 = "PaintSchemes/Cache/" + com.maddox.rts.Finger.file(0L, s, -1);
            com.maddox.il2.objects.air.Aircraft.prepareMeshSkin(meshName, actorMesh[i].hierMesh(), s, s1);
        } else
        {
            com.maddox.il2.objects.air.Aircraft.prepareMeshCamouflage(meshName, actorMesh[i].hierMesh());
        }
    }

    private void prepareNoseart(int i, java.lang.String s)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actorMesh[i]))
            return;
        if(s != null)
        {
            java.lang.String s1 = com.maddox.il2.game.Main.cur().netFileServerNoseart.primaryPath() + "/" + s;
            java.lang.String s2 = s.substring(0, s.length() - 4);
            java.lang.String s3 = "PaintSchemes/Cache/Noseart0" + s2 + ".tga";
            java.lang.String s4 = "PaintSchemes/Cache/Noseart0" + s2 + ".mat";
            java.lang.String s5 = "PaintSchemes/Cache/Noseart1" + s2 + ".tga";
            java.lang.String s6 = "PaintSchemes/Cache/Noseart1" + s2 + ".mat";
            if(com.maddox.il2.engine.BmpUtils.bmp8PalTo2TGA4(s1, s3, s5))
                com.maddox.il2.objects.air.Aircraft.prepareMeshNoseart(actorMesh[i].hierMesh(), s4, s6, s3, s5, null);
        }
    }

    private void preparePilot(int i, java.lang.String s)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actorMesh[i]))
            return;
        if(s != null)
        {
            java.lang.String s1 = com.maddox.il2.game.Main.cur().netFileServerPilot.primaryPath() + "/" + s;
            java.lang.String s2 = s.substring(0, s.length() - 4);
            java.lang.String s3 = "PaintSchemes/Cache/Pilot" + s2 + ".tga";
            java.lang.String s4 = "PaintSchemes/Cache/Pilot" + s2 + ".mat";
            if(com.maddox.il2.engine.BmpUtils.bmp8PalToTGA3(s1, s3))
                com.maddox.il2.objects.air.Aircraft.prepareMeshPilot(actorMesh[i].hierMesh(), 0, s4, s3, null);
        }
    }

    private void initEditSkin()
    {
        for(_planeIndx = 0; _planeIndx < 4; _planeIndx++)
        {
            com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)builder.wSelect.tabsClient.create(new com.maddox.gwindow.GWindowDialogClient() {

                public void resized()
                {
                    super.resized();
                    wSkills[planeIndx].setSize(win.dx - wSkills[planeIndx].win.x - lookAndFeel().metric(1.0F), wSkills[planeIndx].win.dy);
                    wSkins[planeIndx].setSize(win.dx - wSkins[planeIndx].win.x - lookAndFeel().metric(1.0F), wSkins[planeIndx].win.dy);
                    wNoseart[planeIndx].setSize(win.dx - wNoseart[planeIndx].win.x - lookAndFeel().metric(1.0F), wNoseart[planeIndx].win.dy);
                    wPilots[planeIndx].setSize(win.dx - wPilots[planeIndx].win.x - lookAndFeel().metric(1.0F), wPilots[planeIndx].win.dy);
                    renders[planeIndx].setSize(win.dx - renders[planeIndx].win.x - lookAndFeel().metric(1.0F), win.dy - renders[planeIndx].win.y - lookAndFeel().metric(1.0F));
                }

                int planeIndx;

            
            {
                planeIndx = _planeIndx;
            }
            }
);
            tabSkin[_planeIndx] = builder.wSelect.tabsClient.createTab(com.maddox.il2.builder.PlMisAir.i18n("Plane" + (1 + _planeIndx)), gwindowdialogclient);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 1.0F, 7F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("Player"), null));
            gwindowdialogclient.addControl(wPlayer[_planeIndx] = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 9F, 1.0F, null) {

                public void preRender()
                {
                    super.preRender();
                    com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                    if(pathair == null)
                    {
                        return;
                    } else
                    {
                        setChecked(pathair == com.maddox.il2.builder.Path.player && planeIndx == com.maddox.il2.builder.Path.playerNum, false);
                        setEnable(type[pathair._iType].item[pathair._iItem].bEnablePlayer && !pathair.bOnlyAI);
                        return;
                    }
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                        return false;
                    com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                    if(pathair == null)
                        return false;
                    if(isChecked())
                    {
                        com.maddox.il2.builder.Path.player = pathair;
                        com.maddox.il2.builder.Path.playerNum = planeIndx;
                        com.maddox.il2.builder.PAir pair = (com.maddox.il2.builder.PAir)pathair.point(0);
                        pair.time = 0.0D;
                        com.maddox.il2.builder.PlMission.cur.missionArmy = pathair.getArmy();
                        pathair.computeTimes();
                    } else
                    {
                        com.maddox.il2.builder.Path.player = null;
                        com.maddox.il2.builder.Path.playerNum = 0;
                    }
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }

                int planeIndx;

            
            {
                planeIndx = _planeIndx;
            }
            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 3F, 7F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("Skill"), null));
            gwindowdialogclient.addControl(wSkills[_planeIndx] = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 9F, 3F, 7F) {

                public void afterCreated()
                {
                    super.afterCreated();
                    setEditable(false);
                    add(com.maddox.il2.builder.Plugin.i18n("Rookie"));
                    add(com.maddox.il2.builder.Plugin.i18n("Average"));
                    add(com.maddox.il2.builder.Plugin.i18n("Veteran"));
                    add(com.maddox.il2.builder.Plugin.i18n("Ace"));
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                        pathair.skills[planeIndx] = getSelected();
                        checkEditSkinSkills();
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

                int planeIndx;

            
            {
                planeIndx = _planeIndx;
            }
            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 5F, 7F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("Skin"), null));
            gwindowdialogclient.addControl(wSkins[_planeIndx] = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 9F, 5F, 7F) {

                public void afterCreated()
                {
                    super.afterCreated();
                    setEditable(false);
                    add(com.maddox.il2.builder.Plugin.i18n("Default"));
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                        return false;
                    com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                    if(getSelected() == 0)
                        pathair.skins[planeIndx] = null;
                    else
                        pathair.skins[planeIndx] = get(getSelected());
                    resetMesh(planeIndx);
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }

                int planeIndx;

            
            {
                planeIndx = _planeIndx;
            }
            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 7F, 7F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("Noseart"), null));
            gwindowdialogclient.addControl(wNoseart[_planeIndx] = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 9F, 7F, 7F) {

                public void afterCreated()
                {
                    super.afterCreated();
                    setEditable(false);
                    add(com.maddox.il2.builder.Plugin.i18n("None"));
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                        return false;
                    com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                    if(getSelected() == 0)
                        pathair.noseart[planeIndx] = null;
                    else
                        pathair.noseart[planeIndx] = get(getSelected());
                    resetMesh(planeIndx);
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }

                int planeIndx;

            
            {
                planeIndx = _planeIndx;
            }
            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 9F, 7F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("Pilot"), null));
            gwindowdialogclient.addControl(wPilots[_planeIndx] = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 9F, 9F, 7F) {

                public void afterCreated()
                {
                    super.afterCreated();
                    setEditable(false);
                    add(com.maddox.il2.builder.Plugin.i18n("Default"));
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                        return false;
                    com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                    if(getSelected() == 0)
                        pathair.pilots[planeIndx] = null;
                    else
                        pathair.pilots[planeIndx] = get(getSelected());
                    resetMesh(planeIndx);
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }

                int planeIndx;

            
            {
                planeIndx = _planeIndx;
            }
            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 11F, 7F, 1.3F, com.maddox.il2.builder.PlMisAir.i18n("NumberOn"), null));
            gwindowdialogclient.addControl(wPlayer[_planeIndx] = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 9F, 11F, null) {

                public void preRender()
                {
                    super.preRender();
                    com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                    if(pathair == null)
                    {
                        return;
                    } else
                    {
                        setChecked(pathair.bNumberOn[planeIndx], false);
                        return;
                    }
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        com.maddox.il2.builder.PathAir pathair = (com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Plugin.builder.selectedPath();
                        pathair.bNumberOn[planeIndx] = isChecked();
                        resetMesh(planeIndx);
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

                int planeIndx;

            
            {
                planeIndx = _planeIndx;
            }
            }
);
            renders[_planeIndx] = new com.maddox.il2.engine.GUIRenders(gwindowdialogclient, 1.0F, 13F, 17F, 7F, true) {

                public void mouseButton(int i, boolean flag, float f, float f1)
                {
                    super.mouseButton(i, flag, f, f1);
                    if(!flag)
                        return;
                    if(i == 1)
                    {
                        animateMeshA[planeIndx] = animateMeshT[planeIndx] = 0.0F;
                        if(com.maddox.il2.engine.Actor.isValid(actorMesh[planeIndx]))
                            actorMesh[planeIndx].pos.setAbs(new Orient(90F, 0.0F, 0.0F));
                    } else
                    if(i == 0)
                    {
                        f -= win.dx / 2.0F;
                        if(java.lang.Math.abs(f) < win.dx / 16F)
                            animateMeshA[planeIndx] = 0.0F;
                        else
                            animateMeshA[planeIndx] = (-128F * f) / win.dx;
                        f1 -= win.dy / 2.0F;
                        if(java.lang.Math.abs(f1) < win.dy / 16F)
                            animateMeshT[planeIndx] = 0.0F;
                        else
                            animateMeshT[planeIndx] = (-128F * f1) / win.dy;
                    }
                }

                int planeIndx;

            
            {
                planeIndx = _planeIndx;
            }
            }
;
            camera3D[_planeIndx] = new Camera3D();
            camera3D[_planeIndx].set(50F, 1.0F, 800F);
            render3D[_planeIndx] = new _Render3D(renders[_planeIndx].renders, 1.0F);
            render3D[_planeIndx].setCamera(camera3D[_planeIndx]);
            com.maddox.il2.engine.LightEnvXY lightenvxy = new LightEnvXY();
            render3D[_planeIndx].setLightEnv(lightenvxy);
            lightenvxy.sun().setLight(0.5F, 0.5F, 1.0F, 1.0F, 1.0F, 0.8F);
            com.maddox.JGP.Vector3f vector3f = new Vector3f(-2F, 1.0F, -1F);
            vector3f.normalize();
            lightenvxy.sun().set(vector3f);
        }

    }

    public void freeResources()
    {
        resetMesh();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    protected com.maddox.il2.builder.Type type[];
    double defaultHeight;
    double defaultSpeed;
    private com.maddox.il2.builder.DefaultArmy defaultArmy[];
    private int iArmyRegimentList;
    private java.util.ArrayList regimentList;
    private com.maddox.il2.builder.PlMission pluginMission;
    private int startComboBox1;
    com.maddox.il2.builder.ViewItem viewType[];
    com.maddox.util.HashMapInt viewMap;
    private java.lang.String _actorInfo[];
    com.maddox.gwindow.GWindowTabDialogClient.Tab tabActor;
    com.maddox.gwindow.GWindowTabDialogClient.Tab tabWay;
    com.maddox.gwindow.GWindowTabDialogClient.Tab tabSkin[];
    com.maddox.gwindow.GWindowComboControl wArmy;
    com.maddox.gwindow.GWindowComboControl wRegiment;
    com.maddox.gwindow.GWindowComboControl wSquadron;
    com.maddox.gwindow.GWindowComboControl wWing;
    com.maddox.gwindow.GWindowComboControl wWeapons;
    java.util.ArrayList lWeapons;
    com.maddox.gwindow.GWindowEditControl wFuel;
    com.maddox.gwindow.GWindowComboControl wPlanes;
    com.maddox.gwindow.GWindowComboControl wSkill;
    com.maddox.gwindow.GWindowCheckBox wOnlyAI;
    com.maddox.gwindow.GWindowCheckBox wParachute;
    private java.lang.Object _squads[];
    private java.lang.Object _wings[];
    com.maddox.gwindow.GWindowButton wPrev;
    com.maddox.gwindow.GWindowButton wNext;
    com.maddox.gwindow.GWindowLabel wCur;
    com.maddox.gwindow.GWindowEditControl wHeight;
    com.maddox.gwindow.GWindowEditControl wSpeed;
    com.maddox.gwindow.GWindowEditControl wTimeH;
    com.maddox.gwindow.GWindowEditControl wTimeM;
    com.maddox.gwindow.GWindowComboControl wType;
    com.maddox.gwindow.GWindowCheckBox wRadioSilence;
    com.maddox.gwindow.GWindowLabel wTarget;
    com.maddox.gwindow.GWindowButton wTargetSet;
    com.maddox.gwindow.GWindowButton wTargetClear;
    private int _curPointType;
    private static com.maddox.il2.engine.Loc nearestRunway = new Loc();
    com.maddox.gwindow.GWindowCheckBox wPlayer[];
    com.maddox.gwindow.GWindowComboControl wSkills[];
    com.maddox.gwindow.GWindowComboControl wSkins[];
    com.maddox.gwindow.GWindowComboControl wNoseart[];
    com.maddox.gwindow.GWindowComboControl wPilots[];
    com.maddox.il2.engine.GUIRenders renders[];
    com.maddox.il2.engine.Camera3D camera3D[];
    com.maddox.il2.builder._Render3D render3D[];
    java.lang.String meshName;
    com.maddox.il2.objects.ActorSimpleHMesh actorMesh[];
    float animateMeshA[] = {
        0.0F, 0.0F, 0.0F, 0.0F
    };
    float animateMeshT[] = {
        0.0F, 0.0F, 0.0F, 0.0F
    };
    private com.maddox.il2.engine.Orient _orient;
    private int _planeIndx;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.builder.PlMisAir.class, "name", "MisAir");
    }

























}

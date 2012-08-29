// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ZutiSupportMethods.java

package com.maddox.il2.game;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GPoint;
import com.maddox.gwindow.GRegion;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Target;
import com.maddox.il2.ai.TargetsGuard;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ZutiTargetsSupportMethods;
import com.maddox.il2.ai.air.Point_Stay;
import com.maddox.il2.ai.ground.ChiefGround;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GUIRenders;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.gui.GUI;
import com.maddox.il2.gui.GUIBriefing;
import com.maddox.il2.gui.GUIPad;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.ShipGeneric;
import com.maddox.il2.objects.trains.Train;
import com.maddox.il2.objects.vehicles.artillery.AAA;
import com.maddox.il2.objects.vehicles.tanks.TankGeneric;
import com.maddox.rts.LDRres;
import com.maddox.rts.NetAddress;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetEnv;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;
import com.maddox.util.NumberTokenizer;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;

// Referenced classes of package com.maddox.il2.game:
//            ZutiPadObject, ZutiAircraft, ZutiBannedUser, ZutiStayPoint, 
//            Main, Mission, I18N

public class ZutiSupportMethods
{

    public ZutiSupportMethods()
    {
    }

    public static void clear()
    {
        if(ZUTI_BANNED_PILOTS != null)
            ZUTI_BANNED_PILOTS.clear();
        if(ZUTI_DEAD_TARGETS != null)
            ZUTI_DEAD_TARGETS.clear();
    }

    public static void setTargetsLoaded(boolean flag)
    {
        ZUTI_TARGETS_LOADED = flag;
    }

    public static void fillAirInterval(com.maddox.il2.gui.GUIPad guipad)
    {
        com.maddox.il2.game.Mission mission = com.maddox.il2.game.Main.cur().mission;
        if(mission != null)
            mission.getClass();
        else
            return;
        try
        {
            Object obj = null;
            Object obj1 = null;
            com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
            boolean flag = mission.zutiRadar_RefreshInterval > 0;
            java.util.List list = com.maddox.il2.engine.Engine.targets();
            int i = list.size();
            for(int j = 0; j < i; j++)
            {
                com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(j);
                if(!actor.equals(aircraft) && (actor instanceof com.maddox.il2.objects.air.Aircraft) && !actor.getDiedFlag() && !guipad.zutiPadObjects.containsKey(new Integer(actor.hashCode())))
                {
                    com.maddox.il2.game.ZutiPadObject zutipadobject = new ZutiPadObject(actor, flag);
                    zutipadobject.type = 0;
                    guipad.zutiPadObjects.put(new Integer(zutipadobject.hashCode()), zutipadobject);
                }
            }

        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void fillGroundChiefsArray(com.maddox.il2.gui.GUIPad guipad)
    {
        com.maddox.il2.game.Mission mission = com.maddox.il2.game.Main.cur().mission;
        if(mission != null)
            mission.getClass();
        else
            return;
        boolean flag = mission.zutiRadar_RefreshInterval > 0;
        Object obj = null;
        com.maddox.util.HashMapExt hashmapext = com.maddox.il2.engine.Engine.name2Actor();
        Object obj1 = null;
        for(java.util.Map.Entry entry = hashmapext.nextEntry(null); entry != null; entry = hashmapext.nextEntry(entry))
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getValue();
            if(com.maddox.il2.gui.GUI.pad.zutiPadObjects.containsKey(new Integer(actor.hashCode())) || !(actor instanceof com.maddox.il2.ai.Chief) || actor.getDiedFlag())
                continue;
            if(actor instanceof com.maddox.il2.objects.vehicles.tanks.TankGeneric)
            {
                com.maddox.il2.game.ZutiPadObject zutipadobject = new ZutiPadObject(actor, flag);
                zutipadobject.type = 1;
                guipad.zutiPadObjects.put(new Integer(zutipadobject.hashCode()), zutipadobject);
                continue;
            }
            if(actor instanceof com.maddox.il2.objects.trains.Train)
            {
                com.maddox.il2.game.ZutiPadObject zutipadobject1 = new ZutiPadObject(actor, flag);
                zutipadobject1.type = 5;
                guipad.zutiPadObjects.put(new Integer(zutipadobject1.hashCode()), zutipadobject1);
                continue;
            }
            if(actor instanceof com.maddox.il2.objects.vehicles.artillery.AAA)
            {
                if(!actor.getDiedFlag())
                {
                    com.maddox.il2.game.ZutiPadObject zutipadobject2 = new ZutiPadObject(actor, flag);
                    zutipadobject2.type = 2;
                    guipad.zutiPadObjects.put(new Integer(zutipadobject2.hashCode()), zutipadobject2);
                }
                continue;
            }
            if(actor instanceof com.maddox.il2.ai.ground.ChiefGround)
            {
                com.maddox.il2.game.ZutiPadObject zutipadobject3 = new ZutiPadObject(actor, flag);
                zutipadobject3.type = 5;
                guipad.zutiPadObjects.put(new Integer(zutipadobject3.hashCode()), zutipadobject3);
                continue;
            }
            if(((actor instanceof com.maddox.il2.objects.ships.BigshipGeneric) || (actor instanceof com.maddox.il2.objects.ships.ShipGeneric)) && !actor.getDiedFlag())
            {
                com.maddox.il2.game.ZutiPadObject zutipadobject4 = new ZutiPadObject(actor, flag);
                zutipadobject4.type = 4;
                guipad.zutiPadObjects.put(new Integer(zutipadobject4.hashCode()), zutipadobject4);
            }
        }

    }

    public static void fillNeutralHomeBases(java.util.ArrayList arraylist)
    {
        if(arraylist == null)
            arraylist = new ArrayList();
        else
            arraylist.clear();
        java.util.ArrayList arraylist1 = com.maddox.il2.ai.World.cur().bornPlaces;
        if(arraylist1 == null)
            return;
        int i = arraylist1.size();
        Object obj = null;
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.net.BornPlace bornplace = (com.maddox.il2.net.BornPlace)arraylist1.get(j);
            if(bornplace.army != 1 && bornplace.army != 2)
                arraylist.add(bornplace);
        }

    }

    public static java.lang.String getCountryFromNetRegiment(java.lang.String s)
    {
        java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle("i18n/country", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
        java.util.List list = com.maddox.il2.ai.Regiment.getAll();
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.Regiment regiment = (com.maddox.il2.ai.Regiment)list.get(j);
            java.lang.String s1 = resourcebundle.getString(regiment.branch());
            if(regiment.name().equals(s))
                return s1;
        }

        return "NONE";
    }

    public static java.lang.String getUserCfgRegiment(java.lang.String s)
    {
        java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle("i18n/country", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
        java.util.List list = com.maddox.il2.ai.Regiment.getAll();
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.Regiment regiment = (com.maddox.il2.ai.Regiment)list.get(j);
            java.lang.String s1 = resourcebundle.getString(regiment.branch());
            if(s1.equals(s))
                return regiment.name();
        }

        return "NoNe";
    }

    public static java.lang.String getHomeBaseFirstCountry(com.maddox.il2.net.BornPlace bornplace)
    {
        java.util.ArrayList arraylist = bornplace.zutiHomeBaseCountries;
        if(arraylist == null || arraylist.size() == 0)
            return "None";
        else
            return (java.lang.String)arraylist.get(0);
    }

    public static boolean isRegimentValidForSelectedHB(java.lang.String s, com.maddox.il2.net.BornPlace bornplace)
    {
        java.lang.String s1 = com.maddox.il2.game.ZutiSupportMethods.getCountryFromNetRegiment(s);
        if(bornplace.zutiHomeBaseCountries == null)
            return false;
        for(int i = 0; i < bornplace.zutiHomeBaseCountries.size(); i++)
        {
            java.lang.String s2 = (java.lang.String)bornplace.zutiHomeBaseCountries.get(i);
            if(s2.equals(s1))
                return true;
        }

        return false;
    }

    public static int getPlayerArmy()
    {
        if(com.maddox.il2.game.Mission.isDogfight())
            return ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getArmy();
        else
            return com.maddox.il2.ai.World.getPlayerArmy();
    }

    public static void removeTarget(java.lang.String s)
    {
        try
        {
            com.maddox.il2.gui.GUIBriefing.TargetPoint targetpoint = null;
            java.util.Iterator iterator = com.maddox.il2.gui.GUIBriefing.ZUTI_TARGETS.iterator();
            Object obj = null;
            do
            {
                if(!iterator.hasNext())
                    break;
                com.maddox.il2.gui.GUIBriefing.TargetPoint targetpoint1 = (com.maddox.il2.gui.GUIBriefing.TargetPoint)iterator.next();
                if(targetpoint1.nameTargetOrig == null || targetpoint1.nameTargetOrig.indexOf(s.trim()) <= -1)
                    continue;
                targetpoint = targetpoint1;
                break;
            } while(true);
            if(targetpoint != null)
                com.maddox.il2.gui.GUIBriefing.ZUTI_TARGETS.remove(targetpoint);
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static boolean removeTarget(double d, double d1)
    {
        com.maddox.il2.gui.GUIBriefing.TargetPoint targetpoint;
        targetpoint = null;
        Object obj = null;
        java.util.Iterator iterator = com.maddox.il2.gui.GUIBriefing.ZUTI_TARGETS.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            com.maddox.il2.gui.GUIBriefing.TargetPoint targetpoint1 = (com.maddox.il2.gui.GUIBriefing.TargetPoint)iterator.next();
            if((double)targetpoint1.x != d || (double)targetpoint1.y != d1)
                continue;
            targetpoint = targetpoint1;
            break;
        } while(true);
        if(targetpoint == null)
            break MISSING_BLOCK_LABEL_95;
        com.maddox.il2.gui.GUIBriefing.ZUTI_TARGETS.remove(targetpoint);
        return true;
        java.lang.Exception exception;
        exception;
        exception.printStackTrace();
        return false;
    }

    public static void changeTargetIconDescription(com.maddox.il2.ai.Target target)
    {
        try
        {
            com.maddox.il2.gui.GUIBriefing.TargetPoint targetpoint = null;
            java.util.Iterator iterator = com.maddox.il2.gui.GUIBriefing.ZUTI_TARGETS.iterator();
            do
            {
                if(!iterator.hasNext())
                    break MISSING_BLOCK_LABEL_148;
                targetpoint = (com.maddox.il2.gui.GUIBriefing.TargetPoint)iterator.next();
            } while((int)((double)targetpoint.x - target.pos.getAbsPoint().x) != 0 || (int)((double)targetpoint.y - target.pos.getAbsPoint().y) != 0);
            if(targetpoint.type == 1)
            {
                targetpoint.type = 6;
                targetpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/tdefenceground.mat");
                targetpoint.iconOArmy = com.maddox.il2.engine.IconDraw.get("icons/tdestroyground.mat");
            } else
            if(targetpoint.type == 6)
            {
                targetpoint.type = 1;
                targetpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/tdestroyground.mat");
                targetpoint.iconOArmy = com.maddox.il2.engine.IconDraw.get("icons/tdefenceground.mat");
            }
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static com.maddox.JGP.Point3d getNearestTarget(com.maddox.JGP.Point3d point3d, boolean flag)
    {
        Object obj = null;
        com.maddox.JGP.Point3d point3d1 = null;
        double d = 100000D;
        java.util.Iterator iterator = com.maddox.il2.gui.GUIBriefing.ZUTI_TARGETS.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            com.maddox.il2.gui.GUIBriefing.TargetPoint targetpoint = (com.maddox.il2.gui.GUIBriefing.TargetPoint)iterator.next();
            if(flag && (targetpoint.type == 0 || targetpoint.type == 1 || targetpoint.type == 2 || targetpoint.type == 4))
            {
                double d1 = java.lang.Math.sqrt(java.lang.Math.pow(point3d.x - (double)targetpoint.x, 2D) + java.lang.Math.pow(point3d.y - (double)targetpoint.y, 2D));
                if(d1 < d)
                {
                    d = d1;
                    point3d1 = new Point3d(targetpoint.x, targetpoint.y, 0.0D);
                }
            }
            if(!flag && (targetpoint.type == 4 || targetpoint.type == 5 || targetpoint.type == 6 || targetpoint.type == 7))
            {
                double d2 = java.lang.Math.sqrt(java.lang.Math.pow(point3d.x - (double)targetpoint.x, 2D) + java.lang.Math.pow(point3d.y - (double)targetpoint.y, 2D));
                if(d2 < d)
                {
                    d = d2;
                    point3d1 = new Point3d(targetpoint.x, targetpoint.y, 0.0D);
                }
            }
        } while(true);
        return point3d1;
    }

    public static void assignTargetActor(com.maddox.il2.gui.GUIBriefing.TargetPoint targetpoint)
    {
        if(targetpoint == null)
            return;
        com.maddox.il2.game.Main.cur().mission.getClass();
        try
        {
            targetpoint.actor = com.maddox.il2.engine.Actor.getByName(targetpoint.nameTarget);
            if(targetpoint.actor != null && (targetpoint.actor instanceof com.maddox.il2.ai.Wing))
            {
                com.maddox.il2.ai.Wing wing = (com.maddox.il2.ai.Wing)targetpoint.actor;
                targetpoint.isBaseActorWing = true;
                targetpoint.wing = wing;
                int j = wing.airc.length;
                int l = 0;
                do
                {
                    if(l >= j)
                        break;
                    if(wing.airc[l] != null && !wing.airc[l].getDiedFlag())
                    {
                        targetpoint.actor = wing.airc[l];
                        break;
                    }
                    l++;
                } while(true);
            } else
            if(targetpoint.isBaseActorWing && targetpoint.wing != null)
            {
                int i = targetpoint.wing.airc.length;
                int k = 0;
                do
                {
                    if(k >= i)
                        break;
                    if(targetpoint.wing.airc[k] != null && !targetpoint.wing.airc[k].getDiedFlag())
                    {
                        targetpoint.actor = targetpoint.wing.airc[k];
                        break;
                    }
                    k++;
                } while(true);
            }
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void fillTargets(com.maddox.rts.SectFile sectfile)
    {
        if(ZUTI_TARGETS_LOADED)
            return;
        com.maddox.il2.gui.GUIBriefing.ZUTI_TARGETS.clear();
        int i = sectfile.sectionIndex("Target");
        if(i >= 0)
        {
            int j = sectfile.vars(i);
            for(int k = 0; k < j; k++)
            {
                com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, k));
                int j1 = numbertokenizer.next(0, 0, 7);
                int k1 = numbertokenizer.next(0, 0, 2);
                if(k1 == 2)
                    continue;
                com.maddox.il2.gui.GUIBriefing.TargetPoint targetpoint = new com.maddox.il2.gui.GUIBriefing.TargetPoint();
                targetpoint.type = j1;
                targetpoint.importance = k1;
                numbertokenizer.next(0);
                numbertokenizer.next(0, 0, 720);
                numbertokenizer.next(0);
                targetpoint.x = numbertokenizer.next(0);
                targetpoint.y = numbertokenizer.next(0);
                int l1 = numbertokenizer.next(0);
                if(targetpoint.type == 3 || targetpoint.type == 6 || targetpoint.type == 1)
                {
                    if(l1 < 50)
                        l1 = 50;
                    if(l1 > 3000)
                        l1 = 3000;
                }
                targetpoint.r = l1;
                numbertokenizer.next(0);
                targetpoint.nameTarget = numbertokenizer.next((java.lang.String)null);
                if(targetpoint.nameTarget != null && targetpoint.nameTarget.startsWith("Bridge"))
                {
                    targetpoint.nameTargetOrig = targetpoint.nameTarget;
                    targetpoint.nameTarget = null;
                }
                int i2 = numbertokenizer.next(0);
                int j2 = numbertokenizer.next(0);
                if(i2 != 0 && j2 != 0)
                {
                    targetpoint.x = i2;
                    targetpoint.y = j2;
                }
                switch(targetpoint.type)
                {
                case 0: // '\0'
                    targetpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/tdestroyair.mat");
                    targetpoint.iconOArmy = com.maddox.il2.engine.IconDraw.get("icons/tdefence.mat");
                    if(targetpoint.nameTarget != null && sectfile.exist("Chiefs", targetpoint.nameTarget))
                    {
                        targetpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/tdestroychief.mat");
                        targetpoint.iconOArmy = com.maddox.il2.engine.IconDraw.get("icons/tdefence.mat");
                    }
                    com.maddox.il2.game.ZutiSupportMethods.assignTargetActor(targetpoint);
                    break;

                case 1: // '\001'
                    targetpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/tdestroyground.mat");
                    targetpoint.iconOArmy = com.maddox.il2.engine.IconDraw.get("icons/tdefenceground.mat");
                    break;

                case 2: // '\002'
                    targetpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/tdestroybridge.mat");
                    targetpoint.iconOArmy = com.maddox.il2.engine.IconDraw.get("icons/tdefencebridge.mat");
                    targetpoint.nameTarget = null;
                    break;

                case 3: // '\003'
                    targetpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/tinspect.mat");
                    targetpoint.iconOArmy = com.maddox.il2.engine.IconDraw.get("icons/tdefence.mat");
                    com.maddox.il2.game.ZutiSupportMethods.assignTargetActor(targetpoint);
                    break;

                case 4: // '\004'
                    targetpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/tescort.mat");
                    targetpoint.iconOArmy = com.maddox.il2.engine.IconDraw.get("icons/tdestroychief.mat");
                    com.maddox.il2.game.ZutiSupportMethods.assignTargetActor(targetpoint);
                    break;

                case 5: // '\005'
                    targetpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/tdefence.mat");
                    targetpoint.iconOArmy = com.maddox.il2.engine.IconDraw.get("icons/tdestroychief.mat");
                    com.maddox.il2.game.ZutiSupportMethods.assignTargetActor(targetpoint);
                    break;

                case 6: // '\006'
                    targetpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/tdefenceground.mat");
                    targetpoint.iconOArmy = com.maddox.il2.engine.IconDraw.get("icons/tdestroyground.mat");
                    break;

                case 7: // '\007'
                    targetpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/tdefencebridge.mat");
                    targetpoint.iconOArmy = com.maddox.il2.engine.IconDraw.get("icons/tdestroybridge.mat");
                    targetpoint.nameTarget = null;
                    break;
                }
                if(targetpoint.nameTarget != null)
                {
                    targetpoint.nameTargetOrig = targetpoint.nameTarget;
                } else
                {
                    targetpoint.nameTarget = (new Float(targetpoint.x)).toString() + (new Float(targetpoint.y)).toString();
                    if(targetpoint.nameTargetOrig == null)
                        targetpoint.nameTargetOrig = targetpoint.nameTarget;
                }
                if(targetpoint.nameTarget != null)
                    if(sectfile.exist("Chiefs", targetpoint.nameTarget))
                        try
                        {
                            java.util.StringTokenizer stringtokenizer = new StringTokenizer(sectfile.get("Chiefs", targetpoint.nameTarget, (java.lang.String)null));
                            java.lang.String s1 = stringtokenizer.nextToken();
                            int k2 = s1.indexOf(".");
                            targetpoint.nameTarget = com.maddox.il2.game.I18N.technic(s1.substring(0, k2)) + " " + com.maddox.il2.game.I18N.technic(s1.substring(k2 + 1));
                        }
                        catch(java.lang.Exception exception)
                        {
                            targetpoint.nameTarget = null;
                        }
                    else
                    if(sectfile.sectionIndex(targetpoint.nameTarget) >= 0)
                        try
                        {
                            java.lang.String s = sectfile.get(targetpoint.nameTarget, "Class", (java.lang.String)null);
                            java.lang.Class class1 = com.maddox.rts.ObjIO.classForName(s);
                            targetpoint.nameTarget = com.maddox.rts.Property.stringValue(class1, "iconFar_shortClassName", null);
                        }
                        catch(java.lang.Exception exception1)
                        {
                            targetpoint.nameTarget = null;
                        }
                    else
                        targetpoint.nameTarget = null;
                com.maddox.il2.gui.GUIBriefing.ZUTI_TARGETS.add(targetpoint);
            }

        }
        com.maddox.il2.ai.ZutiTargetsSupportMethods.checkForDeactivatedTargets();
        java.util.ArrayList arraylist = com.maddox.il2.ai.World.cur().targetsGuard.zutiTargetNamesToRemove;
        i = arraylist.size();
        for(int l = 0; l < i; l++)
            com.maddox.il2.game.ZutiSupportMethods.removeTarget((java.lang.String)arraylist.get(l));

        com.maddox.il2.ai.World.cur().targetsGuard.zutiTargetNamesToRemove.clear();
        arraylist = com.maddox.il2.ai.World.cur().targetsGuard.zutiTargetPosToRemove;
        i = arraylist.size();
        for(int i1 = 0; i1 < i; i1++)
        {
            com.maddox.il2.ai.Target target = (com.maddox.il2.ai.Target)arraylist.get(i1);
            com.maddox.il2.game.ZutiSupportMethods.removeTarget(target.pos.getAbs().getX(), target.pos.getAbs().getY());
        }

        com.maddox.il2.ai.World.cur().targetsGuard.zutiTargetPosToRemove.clear();
        ZUTI_TARGETS_LOADED = true;
    }

    private static void drawTargets(com.maddox.il2.engine.GUIRenders guirenders, com.maddox.il2.engine.TTFont ttfont, com.maddox.il2.engine.Mat mat, com.maddox.il2.engine.CameraOrtho2D cameraortho2d, java.util.Set set, int i, boolean flag)
    {
        com.maddox.il2.game.Mission mission = com.maddox.il2.game.Main.cur().mission;
        mission.getClass();
        try
        {
            if(set.size() != 0)
            {
                com.maddox.gwindow.GPoint gpoint = guirenders.getMouseXY();
                float f = gpoint.x;
                float f1 = guirenders.win.dy - 1.0F - gpoint.y;
                float f2 = com.maddox.il2.engine.IconDraw.scrSizeX() / 2;
                float f3 = f;
                float f4 = f1;
                com.maddox.il2.engine.IconDraw.setColor(0xff00ffff);
                Object obj = null;
                com.maddox.il2.gui.GUIBriefing.TargetPoint targetpoint1 = null;
                java.util.Iterator iterator = set.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break;
                    com.maddox.il2.gui.GUIBriefing.TargetPoint targetpoint = (com.maddox.il2.gui.GUIBriefing.TargetPoint)iterator.next();
                    if(targetpoint.icon != null)
                    {
                        if(targetpoint.isBaseActorWing && (targetpoint.actor == null || targetpoint.actor.getDiedFlag()))
                            com.maddox.il2.game.ZutiSupportMethods.assignTargetActor(targetpoint);
                        float f5 = (float)(((double)targetpoint.x - cameraortho2d.worldXOffset) * cameraortho2d.worldScale);
                        float f7 = (float)(((double)targetpoint.y - cameraortho2d.worldYOffset) * cameraortho2d.worldScale);
                        mission.getClass();
                        if(flag)
                            com.maddox.il2.engine.IconDraw.render(targetpoint.icon, f5, f7);
                        else
                            com.maddox.il2.engine.IconDraw.render(targetpoint.iconOArmy, f5, f7);
                        if(f5 >= f - f2 && f5 <= f + f2 && f7 >= f1 - f2 && f7 <= f1 + f2)
                        {
                            targetpoint1 = targetpoint;
                            f3 = f5;
                            f4 = f7;
                        }
                    }
                } while(true);
                if(targetpoint1 != null)
                {
                    for(int j = 0; j < 3; j++)
                        ZUTI_TIP[j] = null;

                    if(targetpoint1.importance == 0)
                        ZUTI_TIP[0] = com.maddox.il2.game.I18N.gui("brief.Primary");
                    else
                        ZUTI_TIP[0] = com.maddox.il2.game.I18N.gui("brief.Secondary");
                    if(flag)
                        switch(targetpoint1.type)
                        {
                        case 0: // '\0'
                            ZUTI_TIP[1] = com.maddox.il2.game.I18N.gui("brief.Destroy");
                            break;

                        case 1: // '\001'
                            ZUTI_TIP[1] = com.maddox.il2.game.I18N.gui("brief.DestroyGround");
                            break;

                        case 2: // '\002'
                            ZUTI_TIP[1] = com.maddox.il2.game.I18N.gui("brief.DestroyBridge");
                            break;

                        case 3: // '\003'
                            ZUTI_TIP[1] = com.maddox.il2.game.I18N.gui("brief.Inspect");
                            break;

                        case 4: // '\004'
                            ZUTI_TIP[1] = com.maddox.il2.game.I18N.gui("brief.Escort");
                            break;

                        case 5: // '\005'
                            ZUTI_TIP[1] = com.maddox.il2.game.I18N.gui("brief.Defence");
                            break;

                        case 6: // '\006'
                            ZUTI_TIP[1] = com.maddox.il2.game.I18N.gui("brief.DefenceGround");
                            break;

                        case 7: // '\007'
                            ZUTI_TIP[1] = com.maddox.il2.game.I18N.gui("brief.DefenceBridge");
                            break;
                        }
                    else
                        switch(targetpoint1.type)
                        {
                        case 0: // '\0'
                            ZUTI_TIP[1] = com.maddox.il2.game.I18N.gui("brief.Defence");
                            break;

                        case 1: // '\001'
                            ZUTI_TIP[1] = com.maddox.il2.game.I18N.gui("brief.DefenceGround");
                            break;

                        case 2: // '\002'
                            ZUTI_TIP[1] = com.maddox.il2.game.I18N.gui("brief.DefenceBridge");
                            break;

                        case 3: // '\003'
                            ZUTI_TIP[1] = com.maddox.il2.game.I18N.gui("brief.Defence");
                            break;

                        case 4: // '\004'
                            ZUTI_TIP[1] = com.maddox.il2.game.I18N.gui("brief.Destroy");
                            break;

                        case 5: // '\005'
                            ZUTI_TIP[1] = com.maddox.il2.game.I18N.gui("brief.Destroy");
                            break;

                        case 6: // '\006'
                            ZUTI_TIP[1] = com.maddox.il2.game.I18N.gui("brief.DestroyGround");
                            break;

                        case 7: // '\007'
                            ZUTI_TIP[1] = com.maddox.il2.game.I18N.gui("brief.DestroyBridge");
                            break;
                        }
                    if(targetpoint1.nameTarget != null)
                        ZUTI_TIP[2] = targetpoint1.nameTarget;
                    float f6 = ttfont.width(ZUTI_TIP[0]);
                    int k = 1;
                    for(int l = 1; l < 3 && ZUTI_TIP[l] != null; l++)
                    {
                        k = l;
                        float f9 = ttfont.width(ZUTI_TIP[l]);
                        if(f6 < f9)
                            f6 = f9;
                    }

                    float f8 = -ttfont.descender();
                    float f10 = (float)ttfont.height() + f8;
                    f6 += 2.0F * f8;
                    float f11 = f10 * (float)(k + 1) + 2.0F * f8;
                    float f12 = f3 - f6 / 2.0F;
                    float f13 = f4 + f2;
                    if(f12 + f6 > guirenders.win.dx)
                        f12 = guirenders.win.dx - f6;
                    if(f13 + f11 > guirenders.win.dy)
                        f13 = guirenders.win.dy - f11;
                    if(f12 < 0.0F)
                        f12 = 0.0F;
                    if(f13 < 0.0F)
                        f13 = 0.0F;
                    com.maddox.il2.engine.Render.drawTile(f12, f13, f6, f11, 0.0F, mat, 0xcf7fffff, 0.0F, 0.0F, 1.0F, 1.0F);
                    com.maddox.il2.engine.Render.drawEnd();
                    for(int i1 = 0; i1 <= k; i1++)
                        ttfont.output(0xff000000, f12 + f8, f13 + f8 + (float)(k - i1) * f10 + f8, 0.0F, ZUTI_TIP[i1]);

                }
            }
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void drawTargets(com.maddox.il2.engine.GUIRenders guirenders, com.maddox.il2.engine.TTFont ttfont, com.maddox.il2.engine.Mat mat, com.maddox.il2.engine.CameraOrtho2D cameraortho2d)
    {
        int i;
        i = com.maddox.il2.game.ZutiSupportMethods.getPlayerArmy();
        if(i < 1)
            return;
        try
        {
            if(i == com.maddox.il2.ai.World.getMissionArmy())
                com.maddox.il2.game.ZutiSupportMethods.drawTargets(guirenders, ttfont, mat, cameraortho2d, com.maddox.il2.gui.GUIBriefing.ZUTI_TARGETS, i, true);
            else
                com.maddox.il2.game.ZutiSupportMethods.drawTargets(guirenders, ttfont, mat, cameraortho2d, com.maddox.il2.gui.GUIBriefing.ZUTI_TARGETS, i, false);
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
        return;
    }

    public static java.util.ArrayList getAirNames(java.util.ArrayList arraylist)
    {
        java.util.ArrayList arraylist1 = new ArrayList();
        if(arraylist == null)
            return arraylist1;
        for(int i = 0; i < arraylist.size(); i++)
        {
            com.maddox.il2.game.ZutiAircraft zutiaircraft = (com.maddox.il2.game.ZutiAircraft)arraylist.get(i);
            arraylist1.add(zutiaircraft.getAcName());
        }

        return arraylist1;
    }

    public static com.maddox.il2.ai.Airport getAirport(double d, double d1)
    {
        java.util.ArrayList arraylist = new ArrayList();
        com.maddox.il2.ai.World.getAirports(arraylist);
        double d2 = 1000000D;
        com.maddox.il2.ai.Airport airport = null;
        try
        {
            int i = arraylist.size();
            for(int j = 0; j < i; j++)
            {
                com.maddox.il2.ai.Airport airport1 = (com.maddox.il2.ai.Airport)arraylist.get(j);
                com.maddox.JGP.Point3d point3d = airport1.pos.getAbsPoint();
                double d3 = java.lang.Math.sqrt(java.lang.Math.pow(d - point3d.x, 2D) + java.lang.Math.pow(d1 - point3d.y, 2D));
                if(d3 < d2)
                {
                    d2 = d3;
                    airport = airport1;
                }
            }

        }
        catch(java.lang.Exception exception) { }
        return airport;
    }

    public static com.maddox.il2.net.BornPlace getPlayerBornPlace(com.maddox.JGP.Point3d point3d, int i)
    {
        com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
        int j = netuser.getBornPlace();
        com.maddox.il2.net.BornPlace bornplace = (com.maddox.il2.net.BornPlace)com.maddox.il2.ai.World.cur().bornPlaces.get(j);
        if(point3d == null)
            return bornplace;
        if(j == -1)
            bornplace = com.maddox.il2.game.ZutiSupportMethods.getNearestBornPlace(point3d.x, point3d.y, i);
        return bornplace;
    }

    public static java.lang.String getACSelectedLoadoutName(java.util.List list, java.lang.String s, int i, boolean flag)
    {
        if(list == null)
            return ZUTI_LOADOUT_NONE;
        int j;
        int k;
        j = list.size();
        k = 0;
_L3:
        com.maddox.il2.game.ZutiAircraft zutiaircraft;
        java.lang.String s1;
        if(k >= j)
            break MISSING_BLOCK_LABEL_131;
        zutiaircraft = (com.maddox.il2.game.ZutiAircraft)list.get(k);
        if(!zutiaircraft.getAcName().equals(s))
            break MISSING_BLOCK_LABEL_91;
        s1 = zutiaircraft.getLoadoutById(i);
        if(s1 == null) goto _L2; else goto _L1
_L1:
        if(flag)
            return zutiaircraft.getWeaponI18NName(zutiaircraft.getLoadoutById(i));
        return zutiaircraft.getLoadoutById(i);
_L2:
        return ZUTI_LOADOUT_NULL;
        k++;
          goto _L3
        java.lang.Exception exception;
        exception;
        java.lang.System.out.println("BornPlace error, ID_02: " + exception.toString());
        return ZUTI_LOADOUT_NONE;
    }

    public static com.maddox.il2.net.BornPlace getNearestBornPlace_AnyArmy(double d, double d1)
    {
        java.util.ArrayList arraylist = com.maddox.il2.ai.World.cur().bornPlaces;
        double d2 = 1000000D;
        com.maddox.il2.net.BornPlace bornplace = null;
        try
        {
            int i = arraylist.size();
            for(int j = 0; j < i; j++)
            {
                com.maddox.il2.net.BornPlace bornplace1 = (com.maddox.il2.net.BornPlace)arraylist.get(j);
                double d3 = java.lang.Math.sqrt(java.lang.Math.pow(d - bornplace1.place.x, 2D) + java.lang.Math.pow(d1 - bornplace1.place.y, 2D));
                if(d3 < d2)
                {
                    d2 = d3;
                    bornplace = bornplace1;
                }
            }

        }
        catch(java.lang.Exception exception) { }
        if(bornplace == null || d2 > (double)bornplace.r)
            return null;
        else
            return bornplace;
    }

    public static com.maddox.il2.net.BornPlace getNearestBornPlace(double d, double d1, int i)
    {
        java.util.ArrayList arraylist = com.maddox.il2.ai.World.cur().bornPlaces;
        double d2 = 1000000D;
        com.maddox.il2.net.BornPlace bornplace = null;
        try
        {
            int j = arraylist.size();
            for(int k = 0; k < j; k++)
            {
                com.maddox.il2.net.BornPlace bornplace1 = (com.maddox.il2.net.BornPlace)arraylist.get(k);
                if(bornplace1.army == i)
                {
                    double d3 = java.lang.Math.sqrt(java.lang.Math.pow(d - bornplace1.place.x, 2D) + java.lang.Math.pow(d1 - bornplace1.place.y, 2D));
                    if(d3 < d2)
                    {
                        d2 = d3;
                        bornplace = bornplace1;
                    }
                }
            }

        }
        catch(java.lang.Exception exception) { }
        return bornplace;
    }

    public static java.util.List getUnavailableAircraftList(com.maddox.il2.net.BornPlace bornplace)
    {
        java.util.ArrayList arraylist = new ArrayList();
        if(bornplace == null)
            return arraylist;
        java.util.ArrayList arraylist1 = bornplace.zutiGetNotAvailablePlanesList();
        java.lang.StringBuffer stringbuffer = new StringBuffer();
        if(arraylist1 != null)
        {
            for(int i = 0; i < arraylist1.size(); i++)
                if(stringbuffer.toString().length() < 200)
                {
                    stringbuffer.append((java.lang.String)arraylist1.get(i));
                    stringbuffer.append(" ");
                } else
                {
                    arraylist.add(stringbuffer.toString().trim());
                    stringbuffer = new StringBuffer();
                    stringbuffer.append((java.lang.String)arraylist1.get(i));
                    stringbuffer.append(" ");
                }

            arraylist.add(stringbuffer.toString().trim());
        }
        return arraylist;
    }

    public static void setAircraftAvailabilityForHomeBase(java.util.ArrayList arraylist, double d, double d1)
    {
        if(com.maddox.il2.ai.World.cur() == null || com.maddox.il2.game.Main.cur().netServerParams == null || com.maddox.il2.game.Main.cur().netServerParams.isMaster())
            return;
        java.util.ArrayList arraylist1 = com.maddox.il2.ai.World.cur().bornPlaces;
        if(arraylist1 == null)
            return;
        for(int i = 0; i < arraylist1.size(); i++)
        {
            com.maddox.il2.net.BornPlace bornplace = (com.maddox.il2.net.BornPlace)arraylist1.get(i);
            if(bornplace.place.x == d && bornplace.place.y == d1)
                bornplace.zutiActivatePlanes(arraylist);
        }

    }

    public static com.maddox.il2.net.NetUser getNetUser(java.lang.String s)
    {
        java.util.List list = com.maddox.rts.NetEnv.hosts();
        com.maddox.il2.net.NetUser netuser = null;
        netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
        if(netuser.uniqueName().equals(s))
            return netuser;
        for(int i = 0; i < list.size(); i++)
        {
            netuser = (com.maddox.il2.net.NetUser)list.get(i);
            if(netuser.uniqueName().equals(s))
                return netuser;
        }

        return netuser;
    }

    public static void setPlayerBanDuration(long l)
    {
        com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
        java.lang.String s = netuser.uniqueName();
        java.lang.String s1 = "127.0.0.1";
        try
        {
            s1 = netuser.masterChannel().remoteAddress().getHostAddress().toString();
        }
        catch(java.lang.Exception exception) { }
        com.maddox.il2.game.ZutiBannedUser zutibanneduser = null;
        Object obj = null;
        if(ZUTI_BANNED_PILOTS != null)
        {
            int i = ZUTI_BANNED_PILOTS.size();
            int j = 0;
            do
            {
                if(j >= i)
                    break;
                com.maddox.il2.game.ZutiBannedUser zutibanneduser1 = (com.maddox.il2.game.ZutiBannedUser)ZUTI_BANNED_PILOTS.get(j);
                if(zutibanneduser1.isMatch(s, s1))
                {
                    zutibanneduser = zutibanneduser1;
                    break;
                }
                j++;
            } while(true);
        }
        if(zutibanneduser != null)
            zutibanneduser.setDuration(com.maddox.rts.Time.current() + l * 1000L);
    }

    public static boolean isPlayerBanned(java.lang.String s, java.lang.String s1)
    {
        if(com.maddox.il2.game.Main.cur().mission == null)
            return false;
        com.maddox.il2.game.ZutiBannedUser zutibanneduser = null;
        if(ZUTI_BANNED_PILOTS != null)
        {
            int i = ZUTI_BANNED_PILOTS.size();
            int j = 0;
            do
            {
                if(j >= i)
                    break;
                com.maddox.il2.game.ZutiBannedUser zutibanneduser1 = (com.maddox.il2.game.ZutiBannedUser)ZUTI_BANNED_PILOTS.get(j);
                if(zutibanneduser1.isMatch(s, s1))
                {
                    zutibanneduser = zutibanneduser1;
                    break;
                }
                j++;
            } while(true);
        }
        if(zutibanneduser == null)
        {
            zutibanneduser = new ZutiBannedUser();
            zutibanneduser.setName(s);
            zutibanneduser.setIP(s1);
            zutibanneduser.setDuration(0L);
            ZUTI_BANNED_PILOTS.add(zutibanneduser);
        } else
        {
            if(com.maddox.il2.game.Main.cur().mission.zutiMisc_EnableReflyOnlyIfBailedOrDied && zutibanneduser.isBanned())
                return true;
            if(com.maddox.il2.game.Main.cur().mission.zutiMisc_DisableReflyForMissionDuration)
                return true;
        }
        return false;
    }

    public static void managePilotBornPlacePlaneCounter(com.maddox.il2.objects.air.NetAircraft netaircraft, boolean flag)
    {
        if(netaircraft == null)
            return;
        if(netaircraft.net == null)
            return;
        com.maddox.il2.net.NetUser netuser = ((com.maddox.il2.objects.air.NetAircraft.AircraftNet)netaircraft.net).netUser;
        if(netuser == null)
            return;
        java.lang.String s = com.maddox.rts.Property.stringValue(((com.maddox.il2.objects.air.Aircraft)netaircraft).getClass(), "keyName");
        com.maddox.il2.net.BornPlace bornplace = (com.maddox.il2.net.BornPlace)com.maddox.il2.ai.World.cur().bornPlaces.get(netuser.getBornPlace());
        if(bornplace != null)
            bornplace.zutiReleaseAircraft(netaircraft.FM, s, com.maddox.il2.game.ZutiAircraft.isPlaneUsable(netaircraft.FM), false, flag);
    }

    public static com.maddox.JGP.Point3d getWingTakeoffLocation(com.maddox.rts.SectFile sectfile, java.lang.String s)
    {
        java.lang.String s1 = s + "_Way";
        int i = sectfile.sectionIndex(s1);
        if(i < 0)
            return null;
        int j = sectfile.vars(i);
        if(j < 0)
            return null;
        java.lang.String s2 = sectfile.var(i, 0);
        if(s2.equalsIgnoreCase("TAKEOFF"))
        {
            com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.value(i, 0));
            com.maddox.JGP.Point3d point3d = new Point3d();
            point3d.x = numbertokenizer.next(0.0F, -1000000F, 1000000F);
            point3d.y = numbertokenizer.next(0.0F, -1000000F, 1000000F);
            return point3d;
        } else
        {
            return null;
        }
    }

    public static boolean isPlaneStationary(com.maddox.il2.fm.FlightModel flightmodel)
    {
        return (double)flightmodel.getSpeedKMH() < 1.0D && (double)flightmodel.getVertSpeed() < 1.0D;
    }

    public static boolean isStaticActor(com.maddox.il2.engine.Actor actor)
    {
        if(actor.getArmy() == 0)
            return false;
        if(actor instanceof com.maddox.il2.objects.ships.ShipGeneric)
            return ((com.maddox.il2.objects.ships.ShipGeneric)actor).zutiIsStatic();
        if(actor instanceof com.maddox.il2.objects.ships.BigshipGeneric)
            return ((com.maddox.il2.objects.ships.BigshipGeneric)actor).zutiIsStatic();
        if(actor instanceof com.maddox.il2.objects.air.Aircraft)
            return false;
        if(actor instanceof com.maddox.il2.ai.Chief)
            return false;
        if(actor instanceof com.maddox.il2.ai.Wing)
            return false;
        else
            return !com.maddox.il2.engine.Actor.isValid(actor.getOwner()) || !(actor.getOwner() instanceof com.maddox.il2.ai.Chief);
    }

    public static void removeBornPlace(com.maddox.il2.net.BornPlace bornplace)
    {
        com.maddox.il2.game.ZutiSupportMethods.disconnectPilotsFromBornPlace(bornplace);
        int i = bornplace.zutiBpStayPoints.size();
        Object obj = null;
        for(int j = 0; j < i; j++)
            try
            {
                com.maddox.il2.game.ZutiStayPoint zutistaypoint = (com.maddox.il2.game.ZutiStayPoint)bornplace.zutiBpStayPoints.get(j);
                zutistaypoint.pointStay.set(-1000000F, -1000000F);
            }
            catch(java.lang.Exception exception) { }

        com.maddox.il2.ai.World.cur().bornPlaces.remove(bornplace);
        bornplace = null;
    }

    public static void disconnectPilotsFromBornPlace(com.maddox.il2.net.BornPlace bornplace)
    {
        java.util.ArrayList arraylist = com.maddox.il2.ai.World.cur().bornPlaces;
        int i = arraylist.size();
        Object obj = null;
        Object obj1 = null;
label0:
        for(int j = 0; j < i;)
            try
            {
                if((com.maddox.il2.net.BornPlace)arraylist.get(j) != bornplace)
                    continue;
                com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
                if(netuser.getBornPlace() == j)
                    netuser.setBornPlace(-1);
                java.util.List list = com.maddox.rts.NetEnv.hosts();
                int k = list.size();
                int l = 0;
                do
                {
                    if(l >= k)
                        break label0;
                    com.maddox.il2.net.NetUser netuser1 = (com.maddox.il2.net.NetUser)list.get(l);
                    if(netuser1.getBornPlace() == j)
                        netuser1.setBornPlace(-1);
                    l++;
                } while(true);
            }
            catch(java.lang.Exception exception)
            {
                j++;
            }

    }

    public static com.maddox.il2.net.BornPlace getBornPlace(double d, double d1)
    {
        com.maddox.il2.ai.World world = com.maddox.il2.ai.World.cur();
        if(world == null || world.bornPlaces == null)
            return null;
        java.util.ArrayList arraylist = world.bornPlaces;
        Object obj = null;
        for(int i = 0; i < arraylist.size(); i++)
        {
            com.maddox.il2.net.BornPlace bornplace = (com.maddox.il2.net.BornPlace)arraylist.get(i);
            if(bornplace.place.x == d && bornplace.place.y == d1)
                return bornplace;
        }

        return null;
    }

    private static java.lang.String ZUTI_LOADOUT_NONE = "";
    private static java.lang.String ZUTI_LOADOUT_NULL = "";
    private static java.lang.String ZUTI_TIP[] = new java.lang.String[3];
    private static boolean ZUTI_TARGETS_LOADED = false;
    public static int ZUTI_KIA_COUNTER = 0;
    public static boolean ZUTI_KIA_DELAY_CLEARED = false;
    public static java.util.List ZUTI_BANNED_PILOTS;
    public static java.util.List ZUTI_DEAD_TARGETS;
    public static long BASE_CAPRUTING_LAST_CHECK = 0L;
    public static int BASE_CAPTURING_INTERVAL = 2000;

}

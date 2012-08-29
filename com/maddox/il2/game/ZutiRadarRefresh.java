// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ZutiRadarRefresh.java

package com.maddox.il2.game;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.gui.GUI;
import com.maddox.il2.gui.GUIBriefing;
import com.maddox.il2.gui.GUIPad;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.buildings.House;
import com.maddox.il2.objects.buildings.HouseManager;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.ShipGeneric;
import com.maddox.rts.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Referenced classes of package com.maddox.il2.game:
//            ZutiRadarObject, ZutiPadObject, Main, Mission, 
//            ZutiSupportMethods

public class ZutiRadarRefresh
{
    class ZutiRadar_CompareByRange
        implements java.util.Comparator
    {

        public int compare(java.lang.Object obj, java.lang.Object obj1)
        {
            com.maddox.il2.game.ZutiRadarObject zutiradarobject = (com.maddox.il2.game.ZutiRadarObject)obj;
            com.maddox.il2.game.ZutiRadarObject zutiradarobject1 = (com.maddox.il2.game.ZutiRadarObject)obj1;
            if(zutiradarobject.getRange() < zutiradarobject1.getRange())
                return -1;
            return zutiradarobject.getRange() <= zutiradarobject1.getRange() ? 0 : 1;
        }

        ZutiRadar_CompareByRange()
        {
        }
    }


    public static void reset()
    {
        ZUTI_RADARS = null;
    }

    public ZutiRadarRefresh()
    {
        tickMin = 0;
        countRadarsStartTime = 0L;
        findRadarsStartTime = 0L;
        startTime = 0L;
        refreshIntervalSet = false;
        deadObjects = null;
        radars = new ArrayList();
        mission = com.maddox.il2.game.Main.cur().mission;
        deadObjects = new ArrayList();
        if(mission != null)
            refreshIntervalSet = mission.zutiRadar_RefreshInterval > 0;
        else
            refreshIntervalSet = false;
    }

    public static void resetStartTimes()
    {
        if(com.maddox.il2.ai.World.cur().diffCur.No_Fog_Of_War_Icons)
            return;
        if(ZUTI_RADARS == null)
            ZUTI_RADARS = new ZutiRadarRefresh();
        ZUTI_RADARS.startTime = 0L;
        ZUTI_RADARS.countRadarsStartTime = 0L;
    }

    public static void update(boolean flag)
    {
        if(com.maddox.il2.ai.World.cur().diffCur.No_Fog_Of_War_Icons)
            return;
        if(ZUTI_RADARS == null)
            ZUTI_RADARS = new ZutiRadarRefresh();
        ZUTI_RADARS.findRadarsInterval();
        ZUTI_RADARS.countRadars();
        ZUTI_RADARS.updatePadObjectsPositions();
        ZUTI_RADARS.executeVisibilityCheck(flag);
    }

    private void updatePadObjectsPositions()
    {
        if(!com.maddox.il2.gui.GUIBriefing.ZUTI_IS_BRIEFING_ACTIVE && (com.maddox.il2.gui.GUI.pad == null || !com.maddox.il2.gui.GUI.pad.isActive()))
            return;
        if(!refreshIntervalSet || com.maddox.rts.Time.current() - startTime > (long)mission.zutiRadar_RefreshInterval)
        {
            refreshZutiPadObjectsPositions();
            refreshZutiTargetsPositions();
            com.maddox.il2.game.ZutiSupportMethods.fillAirInterval(com.maddox.il2.gui.GUI.pad);
            startTime = com.maddox.rts.Time.current();
        }
    }

    private void countRadars()
    {
        if(mission == null)
            return;
        if(com.maddox.rts.Time.current() - countRadarsStartTime > 5000L)
        {
            mission.zutiRadar_PlayerSideHasRadars = false;
            int i = radars.size();
            java.util.ArrayList arraylist = new ArrayList();
            int j = 0;
            do
            {
                if(j >= i)
                    break;
                com.maddox.il2.game.ZutiRadarObject zutiradarobject = (com.maddox.il2.game.ZutiRadarObject)radars.get(j);
                if(zutiradarobject.isAlive())
                {
                    mission.zutiRadar_PlayerSideHasRadars = true;
                    break;
                }
                arraylist.add(zutiradarobject);
                j++;
            } while(true);
            i = arraylist.size();
            for(int k = 0; k < i; k++)
                radars.remove((com.maddox.il2.game.ZutiRadarObject)arraylist.get(k));

            countRadarsStartTime = com.maddox.rts.Time.current();
        }
    }

    private void executeVisibilityCheck(boolean flag)
    {
        if(mission == null)
            return;
        if(!com.maddox.il2.gui.GUIBriefing.ZUTI_IS_BRIEFING_ACTIVE && (com.maddox.il2.gui.GUI.pad == null || !com.maddox.il2.gui.GUI.pad.isActive()))
            return;
        int i = com.maddox.il2.game.ZutiSupportMethods.getPlayerArmy();
        double d = com.maddox.il2.gui.GUI.pad.cameraMap2D.worldXOffset;
        double d1 = com.maddox.il2.gui.GUI.pad.cameraMap2D.worldYOffset;
        double d2 = com.maddox.il2.gui.GUI.pad.cameraMap2D.worldXOffset + (double)(com.maddox.il2.gui.GUI.pad.cameraMap2D.right - com.maddox.il2.gui.GUI.pad.cameraMap2D.left) / com.maddox.il2.gui.GUI.pad.cameraMap2D.worldScale;
        double d3 = com.maddox.il2.gui.GUI.pad.cameraMap2D.worldYOffset + (double)(com.maddox.il2.gui.GUI.pad.cameraMap2D.top - com.maddox.il2.gui.GUI.pad.cameraMap2D.bottom) / com.maddox.il2.gui.GUI.pad.cameraMap2D.worldScale;
        int j = (int)com.maddox.rts.Time.current();
        if(j - tickMin >= 2048)
            flag = true;
        int k = 0;
        int l = 0;
        if(!flag)
        {
            k = tickMin % 2048;
            l = j % 2048;
        }
        tickMin = j + 1;
        runIfActive_RefreshPadObjects(d, d1, d2, d3, i, flag, k, l);
        runIfActive_RefreshTargets(d, d1, d2, d3, i, flag, k, l);
    }

    private void runIfActive_RefreshPadObjects(double d, double d1, double d2, double d3, int i, boolean flag, int j, int k)
    {
        java.util.Iterator iterator;
        boolean flag1;
        deadObjects.clear();
        iterator = com.maddox.il2.gui.GUI.pad.zutiPadObjects.keySet().iterator();
        Object obj = null;
        Object obj1 = null;
        Object obj2 = null;
        flag1 = mission.zutiRadar_PlayerSideHasRadars;
_L3:
        if(iterator.hasNext())
        {
            com.maddox.il2.game.ZutiPadObject zutipadobject = (com.maddox.il2.game.ZutiPadObject)com.maddox.il2.gui.GUI.pad.zutiPadObjects.get(iterator.next());
            if(!flag)
            {
                int l = zutipadobject.hashCode() % 2048;
                if(k >= j ? l < j || l > k : l < j && l > k)
                    continue; /* Loop/switch isn't completed */
            }
            if(!zutipadobject.isAlive() || zutipadobject.getOwner() == null)
            {
                deadObjects.add(zutipadobject);
                continue; /* Loop/switch isn't completed */
            }
            if(!com.maddox.il2.game.Mission.ZUTI_RADAR_IN_ADV_MODE || zutipadobject.isPlayerArmyScout())
            {
                if(flag1)
                    zutipadobject.setVisibleForPlayerArmy(true);
                else
                    zutipadobject.setVisibleForPlayerArmy(false);
                continue; /* Loop/switch isn't completed */
            }
            com.maddox.JGP.Point3d point3d = zutipadobject.getPosition();
            if(point3d.x < d || point3d.x > d2 || point3d.y < d1 || point3d.y > d3)
                continue; /* Loop/switch isn't completed */
            zutipadobject.setVisibleForPlayerArmy(false);
            int i1 = 0;
            do
            {
                if(i1 >= radars.size())
                    continue; /* Loop/switch isn't completed */
                com.maddox.il2.game.ZutiRadarObject zutiradarobject = (com.maddox.il2.game.ZutiRadarObject)radars.get(i1);
                if((!zutipadobject.isGroundUnit() || zutiradarobject.getType() == 3) && !zutipadobject.isVisibleForPlayerArmy() && zutiradarobject.isCoordinateCovered(zutipadobject.getPosition()))
                {
                    zutipadobject.setVisibleForPlayerArmy(true);
                    continue; /* Loop/switch isn't completed */
                }
                i1++;
            } while(true);
        }
        for(int j1 = 0; j1 < deadObjects.size(); j1++)
            com.maddox.il2.gui.GUI.pad.zutiPadObjects.remove((com.maddox.il2.game.ZutiPadObject)deadObjects.get(j1));

        deadObjects.clear();
          goto _L1
        java.lang.Exception exception;
        exception;
        exception.printStackTrace();
_L1:
        return;
        if(true) goto _L3; else goto _L2
_L2:
    }

    private void runIfActive_RefreshTargets(double d, double d1, double d2, double d3, int i, boolean flag, int j, int k)
    {
        if(com.maddox.il2.gui.GUIBriefing.ZUTI_TARGETS == null) goto _L2; else goto _L1
_L1:
        boolean flag1;
        java.util.Iterator iterator;
        mission.getClass();
        deadObjects.clear();
        Object obj = null;
        Object obj1 = null;
        Object obj2 = null;
        flag1 = mission.zutiRadar_PlayerSideHasRadars;
        iterator = com.maddox.il2.gui.GUIBriefing.ZUTI_TARGETS.iterator();
_L4:
        if(iterator.hasNext())
        {
            com.maddox.il2.gui.GUIBriefing.TargetPoint targetpoint = (com.maddox.il2.gui.GUIBriefing.TargetPoint)iterator.next();
            if(!flag)
            {
                int l = targetpoint.hashCode() % 2048;
                if(k >= j ? l < j || l > k : l < j && l > k)
                    continue; /* Loop/switch isn't completed */
            }
            if(targetpoint.actor != null && !targetpoint.actor.isAlive())
            {
                deadObjects.add(targetpoint);
                continue; /* Loop/switch isn't completed */
            }
            if(targetpoint.type != 0 && targetpoint.type != 4 && targetpoint.type != 5)
            {
                targetpoint.setVisibleForPlayerArmy(true);
                continue; /* Loop/switch isn't completed */
            }
            if(!com.maddox.il2.game.Mission.ZUTI_RADAR_IN_ADV_MODE)
            {
                if(flag1)
                    targetpoint.setVisibleForPlayerArmy(true);
                else
                    targetpoint.setVisibleForPlayerArmy(false);
                continue; /* Loop/switch isn't completed */
            }
            com.maddox.JGP.Point3d point3d = new Point3d(targetpoint.x, targetpoint.y, targetpoint.z);
            if(targetpoint.actor != null)
                point3d = targetpoint.actor.pos.getAbsPoint();
            if(point3d.x < d || point3d.x > d2 || point3d.y < d1 || point3d.y > d3)
                continue; /* Loop/switch isn't completed */
            targetpoint.setVisibleForPlayerArmy(false);
            if(targetpoint.actor == null || !targetpoint.getIsAlive())
                continue; /* Loop/switch isn't completed */
            int i1 = 0;
            do
            {
                if(i1 >= radars.size())
                    continue; /* Loop/switch isn't completed */
                com.maddox.il2.game.ZutiRadarObject zutiradarobject = (com.maddox.il2.game.ZutiRadarObject)radars.get(i1);
                if(!targetpoint.isVisibleForPlayerArmy() && zutiradarobject.isCoordinateCovered(point3d))
                {
                    targetpoint.setVisibleForPlayerArmy(true);
                    continue; /* Loop/switch isn't completed */
                }
                i1++;
            } while(true);
        }
        for(int j1 = 0; j1 < deadObjects.size(); j1++)
            com.maddox.il2.gui.GUIBriefing.ZUTI_TARGETS.remove((com.maddox.il2.gui.GUIBriefing.TargetPoint)deadObjects.get(j1));

        deadObjects.clear();
          goto _L2
        java.lang.Exception exception;
        exception;
        exception.printStackTrace();
_L2:
        return;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void refreshZutiPadObjectsPositions()
    {
        if(mission == null || com.maddox.il2.gui.GUI.pad == null)
            return;
        java.util.Iterator iterator = com.maddox.il2.gui.GUI.pad.zutiPadObjects.keySet().iterator();
        Object obj = null;
        com.maddox.il2.game.ZutiPadObject zutipadobject;
        for(; iterator.hasNext(); zutipadobject.refreshPosition())
            zutipadobject = (com.maddox.il2.game.ZutiPadObject)com.maddox.il2.gui.GUI.pad.zutiPadObjects.get(iterator.next());

    }

    private void refreshZutiTargetsPositions()
    {
        if(mission != null && com.maddox.il2.gui.GUIBriefing.ZUTI_TARGETS != null)
            mission.getClass();
        else
            return;
        java.util.Iterator iterator = com.maddox.il2.gui.GUIBriefing.ZUTI_TARGETS.iterator();
        Object obj = null;
        com.maddox.il2.gui.GUIBriefing.TargetPoint targetpoint;
        for(; iterator.hasNext(); targetpoint.refreshPosition())
            targetpoint = (com.maddox.il2.gui.GUIBriefing.TargetPoint)iterator.next();

    }

    public static void findRadars(int i)
    {
        if(com.maddox.il2.ai.World.cur().diffCur.No_Fog_Of_War_Icons)
            return;
        if(ZUTI_RADARS == null)
            ZUTI_RADARS = new ZutiRadarRefresh();
        if(ZUTI_RADARS.mission == null)
        {
            return;
        } else
        {
            ZUTI_RADARS.clear();
            ZUTI_RADARS.findLandBasedRadars(i);
            ZUTI_RADARS.findSeaBasedRadars(i);
            ZUTI_RADARS.findAirborneRadars(i);
            ZUTI_RADARS.sortRadars();
            return;
        }
    }

    private void findRadarsInterval()
    {
        if(mission == null)
            return;
        if(com.maddox.rts.Time.current() - findRadarsStartTime > 30000L)
        {
            com.maddox.il2.game.ZutiRadarRefresh.findRadars(com.maddox.il2.game.ZutiSupportMethods.getPlayerArmy());
            findRadarsStartTime = com.maddox.rts.Time.current();
        }
    }

    private void findLandBasedRadars(int i)
    {
        if(com.maddox.il2.ai.World.cur().houseManager == null)
            return;
        com.maddox.il2.objects.buildings.House ahouse[] = com.maddox.il2.ai.World.cur().houseManager.zutiGetHouses();
        if(ahouse == null)
            return;
        int j = ahouse.length;
        if(j <= 0)
            return;
        if(com.maddox.il2.ai.World.cur() == null)
            return;
        java.util.ArrayList arraylist = com.maddox.il2.ai.World.cur().bornPlaces;
        if(arraylist == null)
            return;
        int k = arraylist.size();
        if(k <= 0)
            return;
        Object obj = null;
        Object obj1 = null;
        Object obj2 = null;
        Object obj3 = null;
        double d = 0.0D;
label0:
        for(int l = 0; l < j; l++)
            try
            {
                int i1 = 0;
                do
                {
                    if(i1 >= k)
                        continue label0;
                    com.maddox.il2.net.BornPlace bornplace = (com.maddox.il2.net.BornPlace)arraylist.get(i1);
                    if(bornplace.army == i)
                    {
                        com.maddox.il2.objects.buildings.House house = ahouse[l];
                        if(house != null && house.name() != null && house.name().indexOf("Radar") > -1)
                        {
                            double d2 = bornplace.r * bornplace.r;
                            com.maddox.JGP.Point3d point3d = house.pos.getAbsPoint();
                            double d1 = (point3d.x - bornplace.place.x) * (point3d.x - bornplace.place.x) + (point3d.y - bornplace.place.y) * (point3d.y - bornplace.place.y);
                            if(d1 <= d2)
                            {
                                com.maddox.il2.game.ZutiRadarObject zutiradarobject = new ZutiRadarObject(house, 1);
                                zutiradarobject.setRange(bornplace.zutiRadarRange);
                                zutiradarobject.setMinHeight(bornplace.zutiRadarHeight_MIN);
                                zutiradarobject.setMaxHeight(bornplace.zutiRadarHeight_MAX);
                                radars.add(zutiradarobject);
                            }
                        }
                    }
                    i1++;
                } while(true);
            }
            catch(java.lang.Exception exception)
            {
                exception.printStackTrace();
            }

    }

    private void findSeaBasedRadars(int i)
    {
        if(!mission.zutiRadar_ShipsAsRadar)
            return;
        java.util.ArrayList arraylist = mission.getAllActors();
        if(arraylist == null)
            return;
        int j = arraylist.size();
        if(j <= 0)
            return;
        boolean flag = mission.zutiRadar_EnableBigShip_Radar;
        boolean flag1 = mission.zutiRadar_EnableSmallShip_Radar;
        Object obj = null;
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)arraylist.get(k);
            if(actor == null || actor.getArmy() != i || actor.getDiedFlag() || !(actor instanceof com.maddox.il2.objects.ships.BigshipGeneric) && !(actor instanceof com.maddox.il2.objects.ships.ShipGeneric))
                continue;
            boolean flag2 = false;
            if(flag)
            {
                int l = 0;
                do
                {
                    if(l >= com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_RADAR_SHIPS.length)
                        break;
                    if(actor.toString().indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_RADAR_SHIPS[l]) != -1)
                    {
                        com.maddox.il2.game.ZutiRadarObject zutiradarobject = new ZutiRadarObject(actor, 2);
                        zutiradarobject.setRange(mission.zutiRadar_ShipRadar_MaxRange);
                        zutiradarobject.setMinHeight(mission.zutiRadar_ShipRadar_MinHeight);
                        zutiradarobject.setMaxHeight(mission.zutiRadar_ShipRadar_MaxHeight);
                        radars.add(zutiradarobject);
                        flag2 = true;
                        break;
                    }
                    l++;
                } while(true);
            }
            if(!flag1 || flag2)
                continue;
            int i1 = com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_RADAR_SHIPS_SMALL.length;
            for(int j1 = 0; j1 < i1; j1++)
                if(actor.toString().indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_RADAR_SHIPS_SMALL[j1]) != -1)
                {
                    com.maddox.il2.game.ZutiRadarObject zutiradarobject1 = new ZutiRadarObject(actor, 2);
                    zutiradarobject1.setRange(mission.zutiRadar_ShipSmallRadar_MaxRange);
                    zutiradarobject1.setMinHeight(mission.zutiRadar_ShipSmallRadar_MinHeight);
                    zutiradarobject1.setMaxHeight(mission.zutiRadar_ShipSmallRadar_MaxHeight);
                    radars.add(zutiradarobject1);
                }

        }

    }

    private void findAirborneRadars(int i)
    {
        if(!mission.zutiRadar_ScoutsAsRadar)
            return;
        java.util.ArrayList arraylist = mission.getAllActors();
        if(arraylist == null)
            return;
        int j = arraylist.size();
        if(j <= 0)
            return;
        Object obj = null;
        Object obj1 = null;
        float f = tanAlfa[mission.zutiRadar_ScoutGroundObjects_Alpha - 1];
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)arraylist.get(k);
            if(actor != null && actor.getArmy() == i && !actor.getDiedFlag() && (actor instanceof com.maddox.il2.objects.air.Aircraft) && com.maddox.il2.game.ZutiRadarObject.isPlayerArmyScout(actor, i))
            {
                com.maddox.il2.game.ZutiRadarObject zutiradarobject = new ZutiRadarObject(actor, 3);
                zutiradarobject.setRange(f);
                zutiradarobject.setMinHeight(mission.zutiRadar_ScoutRadar_DeltaHeight);
                zutiradarobject.setMaxHeight(zutiradarobject.getMinHeight());
                radars.add(zutiradarobject);
            }
        }

    }

    private void sortRadars()
    {
        java.util.Collections.sort(radars, new ZutiRadar_CompareByRange());
        for(int i = 0; i < radars.size(); i++)
        {
            com.maddox.il2.game.ZutiRadarObject zutiradarobject = (com.maddox.il2.game.ZutiRadarObject)radars.get(i);
        }

    }

    private void clear()
    {
        radars.clear();
    }

    private static final int RADARS_COUNT_INTERVAL = 5000;
    private static final int RADARS_FIND_INTERVAL = 30000;
    private static final int TICKS_TO_REFRESH = 2048;
    private int tickMin;
    private float tanAlfa[] = {
        0.577F, 0.7F, 0.839F, 1.0F, 1.192F, 1.428F, 1.732F, 2.145F, 2.747F, 3.732F, 
        5.671F
    };
    private java.util.List radars;
    private com.maddox.il2.game.Mission mission;
    private long countRadarsStartTime;
    private long findRadarsStartTime;
    private long startTime;
    private boolean refreshIntervalSet;
    private java.util.List deadObjects;
    private static com.maddox.il2.game.ZutiRadarRefresh ZUTI_RADARS = null;

}

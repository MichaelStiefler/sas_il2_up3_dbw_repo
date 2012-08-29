package com.maddox.il2.game;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.gui.GUI;
import com.maddox.il2.gui.GUIBriefing;
import com.maddox.il2.gui.GUIBriefing.TargetPoint;
import com.maddox.il2.gui.GUIPad;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.buildings.House;
import com.maddox.il2.objects.buildings.HouseManager;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.ShipGeneric;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ZutiRadarRefresh
{
  private static final int RADARS_COUNT_INTERVAL = 5000;
  private static final int RADARS_FIND_INTERVAL = 30000;
  private static final int TICKS_TO_REFRESH = 2048;
  private int tickMin = 0;

  private float[] tanAlfa = { 0.577F, 0.7F, 0.839F, 1.0F, 1.192F, 1.428F, 1.732F, 2.145F, 2.747F, 3.732F, 5.671F };
  private List radars;
  private Mission mission;
  private long countRadarsStartTime = 0L;
  private long findRadarsStartTime = 0L;
  private long startTime = 0L;
  private boolean refreshIntervalSet = false;
  private List deadObjects = null;

  private static ZutiRadarRefresh ZUTI_RADARS = null;

  public static void reset()
  {
    ZUTI_RADARS = null;
  }

  public ZutiRadarRefresh()
  {
    this.radars = new ArrayList();
    this.mission = Main.cur().mission;
    this.deadObjects = new ArrayList();

    if (this.mission != null)
      this.refreshIntervalSet = (this.mission.zutiRadar_RefreshInterval > 0);
    else
      this.refreshIntervalSet = false;
  }

  public static void resetStartTimes()
  {
    if (World.cur().diffCur.No_Fog_Of_War_Icons) {
      return;
    }
    if (ZUTI_RADARS == null) {
      ZUTI_RADARS = new ZutiRadarRefresh();
    }
    ZUTI_RADARS.startTime = 0L;
    ZUTI_RADARS.countRadarsStartTime = 0L;
  }

  public static void update(boolean paramBoolean)
  {
    if (World.cur().diffCur.No_Fog_Of_War_Icons) {
      return;
    }
    if (ZUTI_RADARS == null) {
      ZUTI_RADARS = new ZutiRadarRefresh();
    }
    ZUTI_RADARS.findRadarsInterval();
    ZUTI_RADARS.countRadars();
    ZUTI_RADARS.updatePadObjectsPositions();
    ZUTI_RADARS.executeVisibilityCheck(paramBoolean);
  }

  private void updatePadObjectsPositions()
  {
    if ((!GUIBriefing.ZUTI_IS_BRIEFING_ACTIVE) && ((GUI.pad == null) || (!GUI.pad.isActive()))) {
      return;
    }
    if ((!this.refreshIntervalSet) || (Time.current() - this.startTime > this.mission.zutiRadar_RefreshInterval))
    {
      refreshZutiPadObjectsPositions();
      refreshZutiTargetsPositions();

      ZutiSupportMethods.fillAirInterval(GUI.pad);

      this.startTime = Time.current();
    }
  }

  private void countRadars()
  {
    if (this.mission == null) {
      return;
    }
    if (Time.current() - this.countRadarsStartTime > 5000L)
    {
      this.mission.zutiRadar_PlayerSideHasRadars = false;

      int i = this.radars.size();
      ArrayList localArrayList = new ArrayList();

      for (int j = 0; j < i; j++)
      {
        ZutiRadarObject localZutiRadarObject = (ZutiRadarObject)this.radars.get(j);
        if (localZutiRadarObject.isAlive())
        {
          this.mission.zutiRadar_PlayerSideHasRadars = true;
        }
        else
        {
          localArrayList.add(localZutiRadarObject);
        }
      }
      i = localArrayList.size();
      for (j = 0; j < i; j++) {
        this.radars.remove((ZutiRadarObject)localArrayList.get(j));
      }

      this.countRadarsStartTime = Time.current();
    }
  }

  private void executeVisibilityCheck(boolean paramBoolean)
  {
    if (this.mission == null) {
      return;
    }
    if ((!GUIBriefing.ZUTI_IS_BRIEFING_ACTIVE) && ((GUI.pad == null) || (!GUI.pad.isActive()))) {
      return;
    }
    int i = ZutiSupportMethods.getPlayerArmy();

    double d1 = GUI.pad.cameraMap2D.worldXOffset;
    double d2 = GUI.pad.cameraMap2D.worldYOffset;
    double d3 = GUI.pad.cameraMap2D.worldXOffset + (GUI.pad.cameraMap2D.right - GUI.pad.cameraMap2D.left) / GUI.pad.cameraMap2D.worldScale;
    double d4 = GUI.pad.cameraMap2D.worldYOffset + (GUI.pad.cameraMap2D.top - GUI.pad.cameraMap2D.bottom) / GUI.pad.cameraMap2D.worldScale;

    int j = (int)Time.current();

    if (j - this.tickMin >= 2048)
      paramBoolean = true;
    int k = 0; int m = 0;
    if (!paramBoolean)
    {
      k = this.tickMin % 2048;
      m = j % 2048;
    }

    this.tickMin = (j + 1);

    runIfActive_RefreshPadObjects(d1, d2, d3, d4, i, paramBoolean, k, m);
    runIfActive_RefreshTargets(d1, d2, d3, d4, i, paramBoolean, k, m);
  }

  private void runIfActive_RefreshPadObjects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3)
  {
    try
    {
      this.deadObjects.clear();

      Iterator localIterator = GUI.pad.zutiPadObjects.keySet().iterator();
      ZutiPadObject localZutiPadObject = null;
      ZutiRadarObject localZutiRadarObject = null;
      Point3d localPoint3d = null;
      boolean bool = this.mission.zutiRadar_PlayerSideHasRadars;

      while (localIterator.hasNext())
      {
        localZutiPadObject = (ZutiPadObject)GUI.pad.zutiPadObjects.get(localIterator.next());

        if (!paramBoolean)
        {
          i = localZutiPadObject.hashCode() % 2048;
          if (paramInt3 < paramInt2 ? 
            (i < paramInt2) || (i > paramInt3) : 
            (i < paramInt2) || (i > paramInt3))
          {
            continue;
          }
        }
        if ((!localZutiPadObject.isAlive()) || (localZutiPadObject.getOwner() == null))
        {
          this.deadObjects.add(localZutiPadObject);
          continue;
        }

        if ((!Mission.ZUTI_RADAR_IN_ADV_MODE) || (localZutiPadObject.isPlayerArmyScout()))
        {
          if (bool) {
            localZutiPadObject.setVisibleForPlayerArmy(true); continue;
          }
          localZutiPadObject.setVisibleForPlayerArmy(false);
          continue;
        }

        localPoint3d = localZutiPadObject.getPosition();

        if ((localPoint3d.x < paramDouble1) || (localPoint3d.x > paramDouble3) || (localPoint3d.y < paramDouble2) || (localPoint3d.y > paramDouble4))
        {
          continue;
        }

        localZutiPadObject.setVisibleForPlayerArmy(false);

        for (i = 0; i < this.radars.size(); i++)
        {
          localZutiRadarObject = (ZutiRadarObject)this.radars.get(i);

          if ((localZutiPadObject.isGroundUnit()) && (localZutiRadarObject.getType() != 3)) {
            continue;
          }
          if ((localZutiPadObject.isVisibleForPlayerArmy()) || (!localZutiRadarObject.isCoordinateCovered(localZutiPadObject.getPosition())))
            continue;
          localZutiPadObject.setVisibleForPlayerArmy(true);
          break;
        }

      }

      for (int i = 0; i < this.deadObjects.size(); i++) {
        GUI.pad.zutiPadObjects.remove((ZutiPadObject)this.deadObjects.get(i));
      }
      this.deadObjects.clear();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void runIfActive_RefreshTargets(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3)
  {
    try
    {
      if (GUIBriefing.ZUTI_TARGETS != null) { this.mission.getClass();

        this.deadObjects.clear();

        Point3d localPoint3d = null;
        GUIBriefing.TargetPoint localTargetPoint = null;
        ZutiRadarObject localZutiRadarObject = null;
        boolean bool = this.mission.zutiRadar_PlayerSideHasRadars;

        Iterator localIterator = GUIBriefing.ZUTI_TARGETS.iterator();
        while (localIterator.hasNext())
        {
          localTargetPoint = (GUIBriefing.TargetPoint)localIterator.next();

          if (!paramBoolean)
          {
            i = localTargetPoint.hashCode() % 2048;
            if (paramInt3 < paramInt2 ? 
              (i < paramInt2) || (i > paramInt3) : 
              (i < paramInt2) || (i > paramInt3)) {
              continue;
            }
          }
          if ((localTargetPoint.actor != null) && (!localTargetPoint.actor.isAlive()))
          {
            this.deadObjects.add(localTargetPoint);
            continue;
          }

          if ((localTargetPoint.type != 0) && (localTargetPoint.type != 4) && (localTargetPoint.type != 5))
          {
            localTargetPoint.setVisibleForPlayerArmy(true);
            continue;
          }

          if (!Mission.ZUTI_RADAR_IN_ADV_MODE)
          {
            if (bool) {
              localTargetPoint.setVisibleForPlayerArmy(true); continue;
            }
            localTargetPoint.setVisibleForPlayerArmy(false);

            continue;
          }

          localPoint3d = new Point3d(localTargetPoint.x, localTargetPoint.y, localTargetPoint.z);
          if (localTargetPoint.actor != null)
          {
            localPoint3d = localTargetPoint.actor.pos.getAbsPoint();
          }

          if ((localPoint3d.x < paramDouble1) || (localPoint3d.x > paramDouble3) || (localPoint3d.y < paramDouble2) || (localPoint3d.y > paramDouble4))
          {
            continue;
          }

          localTargetPoint.setVisibleForPlayerArmy(false);

          if ((localTargetPoint.actor == null) || (!localTargetPoint.getIsAlive()))
            continue;
          for (i = 0; i < this.radars.size(); i++)
          {
            localZutiRadarObject = (ZutiRadarObject)this.radars.get(i);

            if ((localTargetPoint.isVisibleForPlayerArmy()) || (!localZutiRadarObject.isCoordinateCovered(localPoint3d)))
              continue;
            localTargetPoint.setVisibleForPlayerArmy(true);

            break;
          }

        }

        for (int i = 0; i < this.deadObjects.size(); i++) {
          GUIBriefing.ZUTI_TARGETS.remove((GUIBriefing.TargetPoint)this.deadObjects.get(i));
        }
        this.deadObjects.clear();
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void refreshZutiPadObjectsPositions()
  {
    if ((this.mission == null) || (GUI.pad == null)) {
      return;
    }

    Iterator localIterator = GUI.pad.zutiPadObjects.keySet().iterator();
    ZutiPadObject localZutiPadObject = null;

    while (localIterator.hasNext())
    {
      localZutiPadObject = (ZutiPadObject)GUI.pad.zutiPadObjects.get(localIterator.next());
      localZutiPadObject.refreshPosition();
    }
  }

  private void refreshZutiTargetsPositions()
  {
    if ((this.mission != null) && (GUIBriefing.ZUTI_TARGETS != null)) this.mission.getClass(); else {
      return;
    }

    Iterator localIterator = GUIBriefing.ZUTI_TARGETS.iterator();
    GUIBriefing.TargetPoint localTargetPoint = null;

    while (localIterator.hasNext())
    {
      localTargetPoint = (GUIBriefing.TargetPoint)localIterator.next();
      localTargetPoint.refreshPosition();
    }
  }

  public static void findRadars(int paramInt)
  {
    if (World.cur().diffCur.No_Fog_Of_War_Icons) {
      return;
    }
    if (ZUTI_RADARS == null) {
      ZUTI_RADARS = new ZutiRadarRefresh();
    }
    if (ZUTI_RADARS.mission == null)
    {
      return;
    }

    ZUTI_RADARS.clear();

    ZUTI_RADARS.findLandBasedRadars(paramInt);

    ZUTI_RADARS.findSeaBasedRadars(paramInt);

    ZUTI_RADARS.findAirborneRadars(paramInt);

    ZUTI_RADARS.sortRadars();
  }

  private void findRadarsInterval()
  {
    if (this.mission == null) {
      return;
    }
    if (Time.current() - this.findRadarsStartTime > 30000L)
    {
      findRadars(ZutiSupportMethods.getPlayerArmy());
      this.findRadarsStartTime = Time.current();
    }
  }

  private void findLandBasedRadars(int paramInt)
  {
    if (World.cur().houseManager == null) {
      return;
    }
    House[] arrayOfHouse = World.cur().houseManager.zutiGetHouses();

    if (arrayOfHouse == null) {
      return;
    }
    int i = arrayOfHouse.length;
    if (i <= 0) {
      return;
    }
    if (World.cur() == null) {
      return;
    }
    ArrayList localArrayList = World.cur().bornPlaces;
    if (localArrayList == null) {
      return;
    }
    int j = localArrayList.size();
    if (j <= 0) {
      return;
    }
    ZutiRadarObject localZutiRadarObject = null;
    House localHouse = null;
    BornPlace localBornPlace = null;
    Point3d localPoint3d = null;
    double d1 = 0.0D;

    for (int k = 0; k < i; k++)
    {
      try
      {
        for (int m = 0; m < j; m++)
        {
          localBornPlace = (BornPlace)localArrayList.get(m);

          if (localBornPlace.army != paramInt) {
            continue;
          }
          localHouse = arrayOfHouse[k];

          if ((localHouse == null) || (localHouse.name() == null) || (localHouse.name().indexOf("Radar") <= -1))
            continue;
          double d2 = localBornPlace.r * localBornPlace.r;

          localPoint3d = localHouse.pos.getAbsPoint();

          d1 = (localPoint3d.x - localBornPlace.place.x) * (localPoint3d.x - localBornPlace.place.x) + (localPoint3d.y - localBornPlace.place.y) * (localPoint3d.y - localBornPlace.place.y);
          if (d1 > d2)
            continue;
          localZutiRadarObject = new ZutiRadarObject(localHouse, 1);
          localZutiRadarObject.setRange(localBornPlace.zutiRadarRange);
          localZutiRadarObject.setMinHeight(localBornPlace.zutiRadarHeight_MIN);
          localZutiRadarObject.setMaxHeight(localBornPlace.zutiRadarHeight_MAX);

          this.radars.add(localZutiRadarObject);
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  private void findSeaBasedRadars(int paramInt) {
    if (!this.mission.zutiRadar_ShipsAsRadar) {
      return;
    }
    ArrayList localArrayList = this.mission.getAllActors();
    if (localArrayList == null) {
      return;
    }
    int i = localArrayList.size();
    if (i <= 0) {
      return;
    }
    boolean bool1 = this.mission.zutiRadar_EnableBigShip_Radar;
    boolean bool2 = this.mission.zutiRadar_EnableSmallShip_Radar;

    ZutiRadarObject localZutiRadarObject = null;

    for (int j = 0; j < i; j++)
    {
      Actor localActor = (Actor)localArrayList.get(j);

      if ((localActor == null) || (localActor.getArmy() != paramInt) || (localActor.getDiedFlag()) || ((!(localActor instanceof BigshipGeneric)) && (!(localActor instanceof ShipGeneric)))) {
        continue;
      }
      int k = 0;

      if (bool1)
      {
        for (m = 0; m < BigshipGeneric.ZUTI_RADAR_SHIPS.length; m++)
        {
          if (localActor.toString().indexOf(BigshipGeneric.ZUTI_RADAR_SHIPS[m]) == -1)
            continue;
          localZutiRadarObject = new ZutiRadarObject(localActor, 2);
          localZutiRadarObject.setRange(this.mission.zutiRadar_ShipRadar_MaxRange);
          localZutiRadarObject.setMinHeight(this.mission.zutiRadar_ShipRadar_MinHeight);
          localZutiRadarObject.setMaxHeight(this.mission.zutiRadar_ShipRadar_MaxHeight);

          this.radars.add(localZutiRadarObject);
          k = 1;
          break;
        }

      }

      if ((!bool2) || (k != 0))
        continue;
      int m = BigshipGeneric.ZUTI_RADAR_SHIPS_SMALL.length;
      for (int n = 0; n < m; n++)
      {
        if (localActor.toString().indexOf(BigshipGeneric.ZUTI_RADAR_SHIPS_SMALL[n]) == -1)
          continue;
        localZutiRadarObject = new ZutiRadarObject(localActor, 2);
        localZutiRadarObject.setRange(this.mission.zutiRadar_ShipSmallRadar_MaxRange);
        localZutiRadarObject.setMinHeight(this.mission.zutiRadar_ShipSmallRadar_MinHeight);
        localZutiRadarObject.setMaxHeight(this.mission.zutiRadar_ShipSmallRadar_MaxHeight);

        this.radars.add(localZutiRadarObject);
      }
    }
  }

  private void findAirborneRadars(int paramInt)
  {
    if (!this.mission.zutiRadar_ScoutsAsRadar) {
      return;
    }

    Map.Entry localEntry = Engine.name2Actor().nextEntry(null);
    while (localEntry != null)
    {
      Actor localActor = (Actor)localEntry.getValue();
      if ((localActor instanceof Aircraft))
      {
        if ((localActor.getArmy() == paramInt) || (localActor.isAlive()))
        {
          if (ZutiRadarObject.isPlayerArmyScout(localActor, paramInt))
          {
            ZutiRadarObject localZutiRadarObject = null;
            float f = this.tanAlfa[(this.mission.zutiRadar_ScoutGroundObjects_Alpha - 1)];
            localZutiRadarObject = new ZutiRadarObject(localActor, 3);
            localZutiRadarObject.setRange(f);
            localZutiRadarObject.setMinHeight(this.mission.zutiRadar_ScoutRadar_DeltaHeight);
            localZutiRadarObject.setMaxHeight(localZutiRadarObject.getMinHeight());
            this.radars.add(localZutiRadarObject);
          }
        }
      }
      localEntry = Engine.name2Actor().nextEntry(localEntry);
    }
  }

  private void sortRadars()
  {
    Collections.sort(this.radars, new ZutiRadar_CompareByRange());

    for (int i = 0; i < this.radars.size(); i++)
    {
      ZutiRadarObject localZutiRadarObject = (ZutiRadarObject)this.radars.get(i);
    }
  }

  private void clear()
  {
    this.radars.clear();
  }

  class ZutiRadar_CompareByRange
    implements Comparator
  {
    ZutiRadar_CompareByRange()
    {
    }

    public int compare(Object paramObject1, Object paramObject2)
    {
      ZutiRadarObject localZutiRadarObject1 = (ZutiRadarObject)paramObject1;
      ZutiRadarObject localZutiRadarObject2 = (ZutiRadarObject)paramObject2;

      if (localZutiRadarObject1.getRange() < localZutiRadarObject2.getRange())
        return -1;
      if (localZutiRadarObject1.getRange() > localZutiRadarObject2.getRange()) {
        return 1;
      }
      return 0;
    }
  }
}
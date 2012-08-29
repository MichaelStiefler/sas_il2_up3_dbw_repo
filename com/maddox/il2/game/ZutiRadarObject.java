package com.maddox.il2.game;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Property;
import java.util.ArrayList;

public class ZutiRadarObject
{
  public static final String ZUTI_RADAR_OBJECT_NAME = "Radar";
  private int type;
  private double range;
  private int minHeight;
  private int maxHeight;
  private Point3d position;
  private Actor owner;

  public ZutiRadarObject(Actor paramActor, int paramInt)
  {
    this.owner = paramActor;
    this.type = paramInt;
    this.position = paramActor.pos.getAbsPoint();
  }

  public int getType()
  {
    return this.type;
  }

  public double getRange()
  {
    if (this.type == 3)
    {
      return Math.pow(this.position.z * this.range / 1000.0D, 2.0D);
    }

    return this.range;
  }

  public void setRange(double paramDouble)
  {
    if (this.type == 3)
    {
      this.range = paramDouble;
    }
    else
      this.range = Math.pow(paramDouble, 2.0D);
  }

  public int getMinHeight()
  {
    if (this.type == 3)
    {
      return (int)(this.position.z - this.minHeight);
    }

    return this.minHeight;
  }

  public void setMinHeight(int paramInt)
  {
    this.minHeight = paramInt;
  }

  public int getMaxHeight()
  {
    if (this.type == 3)
    {
      return (int)(this.position.z - this.maxHeight);
    }

    return this.maxHeight;
  }

  public void setMaxHeight(int paramInt)
  {
    this.maxHeight = paramInt;
  }

  public Point3d getPosition()
  {
    return this.position;
  }

  public boolean isAlive()
  {
    if (this.owner == null) {
      return false;
    }
    return !this.owner.getDiedFlag();
  }

  private Actor getOwner() {
    return this.owner;
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ZutiRadarObject)) {
      return false;
    }
    ZutiRadarObject localZutiRadarObject = (ZutiRadarObject)paramObject;

    return this.owner.equals(localZutiRadarObject.getOwner());
  }

  public int hashCode()
  {
    return this.owner.hashCode();
  }

  public boolean isCoordinateCovered(Point3d paramPoint3d)
  {
    double d1 = getRange();
    double d2 = (Math.pow(this.position.x - paramPoint3d.x, 2.0D) + Math.pow(this.position.y - paramPoint3d.y, 2.0D)) / 1000000.0D;

    return (d2 <= d1) && (paramPoint3d.z >= getMinHeight()) && (paramPoint3d.z <= getMaxHeight());
  }

  public static boolean isPlayerArmyScout(Actor paramActor, int paramInt)
  {
    if ((paramActor == null) || (!(paramActor instanceof SndAircraft)) || (paramActor.getArmy() != paramInt))
      return false;
    ArrayList localArrayList;
    String str1;
    int i;
    int j;
    String str2;
    if (paramInt == 1)
    {
      localArrayList = Main.cur().mission.ScoutsRed;
      str1 = Property.stringValue(((Aircraft)paramActor).getClass(), "keyName");
      i = localArrayList.size();
      for (j = 0; j < i; j++)
      {
        str2 = (String)localArrayList.get(j);
        if (str1.indexOf(str2) != -1)
          return true;
      }
    }
    else
    {
      localArrayList = Main.cur().mission.ScoutsBlue;
      str1 = Property.stringValue(((Aircraft)paramActor).getClass(), "keyName");
      i = localArrayList.size();
      for (j = 0; j < i; j++)
      {
        str2 = (String)localArrayList.get(j);
        if (str1.indexOf(str2) != -1)
        {
          return true;
        }
      }
    }

    return false;
  }
}
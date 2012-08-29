package com.maddox.il2.ai.air;

public class AirGroupList
{
  public AirGroup G;
  AirGroupList next;

  public AirGroupList()
  {
    this.G = null;
    this.next = null;
  }

  public AirGroupList(AirGroup paramAirGroup)
  {
    this.G = paramAirGroup;
    this.next = null;
  }

  public void release()
  {
    AirGroupList localAirGroupList = this;
    while (localAirGroupList != null) {
      localAirGroupList.G = null;
      if (localAirGroupList.next != null) localAirGroupList = localAirGroupList.next; else
        localAirGroupList = null;
    }
  }

  public static void addAirGroup(AirGroupList[] paramArrayOfAirGroupList, int paramInt, AirGroup paramAirGroup)
  {
    if (paramArrayOfAirGroupList[paramInt] == null) {
      paramArrayOfAirGroupList[paramInt] = new AirGroupList(paramAirGroup);
      return;
    }
    AirGroupList localAirGroupList = paramArrayOfAirGroupList[paramInt];
    while (localAirGroupList.next != null) localAirGroupList = localAirGroupList.next;
    localAirGroupList.next = new AirGroupList(paramAirGroup);
  }

  public static void delAirGroup(AirGroupList[] paramArrayOfAirGroupList, int paramInt, AirGroup paramAirGroup)
  {
    AirGroupList localAirGroupList1 = paramArrayOfAirGroupList[paramInt];
    AirGroupList localAirGroupList2 = null;
    while (localAirGroupList1 != null) {
      if (localAirGroupList1.G == paramAirGroup) {
        localAirGroupList1.G = null;
        if (localAirGroupList2 == null) paramArrayOfAirGroupList[paramInt] = localAirGroupList1.next; else
          localAirGroupList2.next = localAirGroupList1.next;
        localAirGroupList1.next = null;
        return;
      }
      localAirGroupList2 = localAirGroupList1;
      localAirGroupList1 = localAirGroupList1.next;
    }
  }

  public static int length(AirGroupList paramAirGroupList)
  {
    if (paramAirGroupList == null) return 0;
    AirGroupList localAirGroupList = paramAirGroupList;
    int i = 0;
    while (localAirGroupList != null) {
      localAirGroupList = localAirGroupList.next;
      i++;
    }
    return i;
  }

  public static AirGroup getGroup(AirGroupList paramAirGroupList, int paramInt)
  {
    if (paramAirGroupList == null) return null;
    AirGroupList localAirGroupList = paramAirGroupList;
    int i = 0;
    while (localAirGroupList != null) {
      if (i == paramInt) return localAirGroupList.G;
      localAirGroupList = localAirGroupList.next;
      i++;
    }
    return null;
  }

  public static boolean groupInList(AirGroupList paramAirGroupList, AirGroup paramAirGroup)
  {
    if (paramAirGroupList == null) return false;
    AirGroupList localAirGroupList = paramAirGroupList;
    while (localAirGroupList != null) {
      if (localAirGroupList.G == paramAirGroup) return true;
      localAirGroupList = localAirGroupList.next;
    }
    return false;
  }

  public static void update(AirGroupList paramAirGroupList)
  {
    AirGroupList localAirGroupList = paramAirGroupList;
    while (localAirGroupList != null) {
      localAirGroupList.G.update();
      localAirGroupList = localAirGroupList.next;
    }
  }
}
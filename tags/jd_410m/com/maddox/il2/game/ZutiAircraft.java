package com.maddox.il2.game;

import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Property;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class ZutiAircraft
{
  private ArrayList selectedWeaponNames = null;
  private ArrayList weaponNames = null;
  private String aircraftName = null;
  private int maxAllowed = -1;
  private int inUse = 0;
  private boolean modifiedRuntime = false;
  private String acNameFromClass = null;

  public void setAcName(String paramString)
  {
    this.aircraftName = paramString;

    fillWeapons();
  }

  public String getAcName() {
    return this.aircraftName;
  }

  public void setMaxAllowed(int paramInt) {
    this.maxAllowed = paramInt;
  }

  public int getMaxAllowed() {
    return this.maxAllowed;
  }

  private void fillWeapons()
  {
    this.weaponNames = new ArrayList();
    Class localClass = (Class)Property.value(this.aircraftName, "airClass", null);

    String[] arrayOfString = Aircraft.getWeaponsRegistered(localClass);
    if ((arrayOfString != null) && (arrayOfString.length > 0))
    {
      for (int i = 0; i < arrayOfString.length; i++)
      {
        String str = arrayOfString[i];

        this.weaponNames.add(str);
      }

      if (this.weaponNames.size() == 0)
      {
        this.weaponNames.add(arrayOfString[0]);
      }
    }
  }

  public String getWeaponI18NName(String paramString)
  {
    return I18N.weapons(this.aircraftName, paramString);
  }

  public String getLoadoutById(int paramInt)
  {
    if ((this.selectedWeaponNames == null) || (this.selectedWeaponNames.size() < 1))
    {
      if (paramInt < this.weaponNames.size()) {
        return (String)this.weaponNames.get(paramInt);
      }
      return null;
    }

    if (paramInt > this.selectedWeaponNames.size() - 1) {
      return null;
    }
    return (String)this.selectedWeaponNames.get(paramInt);
  }

  public ArrayList getSelectedWeaponI18NNames()
  {
    ArrayList localArrayList = new ArrayList();
    if (this.selectedWeaponNames == null) {
      return localArrayList;
    }
    for (int i = 0; i < this.selectedWeaponNames.size(); i++)
    {
      localArrayList.add(getWeaponI18NName((String)this.selectedWeaponNames.get(i)));
    }
    return localArrayList;
  }

  public ArrayList getWeaponI18NNames()
  {
    ArrayList localArrayList = new ArrayList();
    if (this.weaponNames == null) {
      return localArrayList;
    }
    for (int i = 0; i < this.weaponNames.size(); i++)
    {
      localArrayList.add(getWeaponI18NName((String)this.weaponNames.get(i)));
    }
    return localArrayList;
  }

  public void listWeapons()
  {
    for (int i = 0; i < this.weaponNames.size(); i++);
  }

  private String getSelectedWeaponsIds()
  {
    if ((this.selectedWeaponNames == null) || (this.selectedWeaponNames.size() < 0)) {
      return "";
    }
    String str = "";
    ArrayList localArrayList1 = getWeaponI18NNames();
    if (this.selectedWeaponNames.size() == localArrayList1.size())
    {
      for (int i = 0; i < localArrayList1.size(); i++)
        str = str + " " + i;
    }
    else
    {
      ArrayList localArrayList2 = new ArrayList();

      for (int j = 0; j < this.selectedWeaponNames.size(); j++)
      {
        for (int k = 0; k < localArrayList1.size(); k++)
        {
          if (!this.selectedWeaponNames.get(j).equals(localArrayList1.get(k)))
            continue;
          localArrayList2.add(new Integer(k));
          break;
        }

      }

      Collections.sort(localArrayList2);

      for (j = 0; j < localArrayList2.size(); j++)
        str = str + " " + ((Integer)localArrayList2.get(j)).intValue();
    }
    return str.trim();
  }

  public void setSelectedWeapons(ArrayList paramArrayList)
  {
    if (this.selectedWeaponNames == null) {
      this.selectedWeaponNames = new ArrayList();
    }
    this.selectedWeaponNames.clear();

    for (int i = 0; i < paramArrayList.size(); i++)
    {
      this.selectedWeaponNames.add(paramArrayList.get(i));
    }
  }

  public void setLoadedWeapons(String paramString, boolean paramBoolean)
  {
    if (this.selectedWeaponNames == null)
      this.selectedWeaponNames = new ArrayList();
    this.selectedWeaponNames.clear();

    if (paramString == null) {
      return;
    }

    ArrayList localArrayList = new ArrayList();
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString);
    while (localStringTokenizer.hasMoreTokens())
      try {
        localArrayList.add(Integer.valueOf(localStringTokenizer.nextToken()));
      }
      catch (Exception localException1)
      {
      }
    Collections.sort(localArrayList);

    for (int i = 0; i < localArrayList.size(); i++)
    {
      try
      {
        Integer localInteger = (Integer)localArrayList.get(i);
        if (paramBoolean)
        {
          this.selectedWeaponNames.add(getWeaponI18NName((String)this.weaponNames.get(localInteger.intValue())));
        }
        else
        {
          this.selectedWeaponNames.add((String)this.weaponNames.get(localInteger.intValue()));
        }
      }
      catch (Exception localException2)
      {
      }
    }
  }

  public String getMissionLine(boolean paramBoolean)
  {
    return this.aircraftName + " " + this.maxAllowed + " " + getSelectedWeaponsIds();
  }

  public String getMissionLineNoLoadouts()
  {
    return this.aircraftName + " " + this.maxAllowed;
  }

  public void setModifiedRuntime(boolean paramBoolean)
  {
    this.modifiedRuntime = paramBoolean;
  }

  public boolean getModifiedRuntime()
  {
    return this.modifiedRuntime;
  }

  public boolean wasModified()
  {
    return ((this.selectedWeaponNames != null) && (this.selectedWeaponNames.size() > 0)) || (this.maxAllowed >= 0);
  }

  public void decreaseInUse()
  {
    if ((Main.cur().netServerParams != null) && (Main.cur().netServerParams.isMaster()))
      this.inUse -= 1;
  }

  public void increaseInUse()
  {
    if ((Main.cur().netServerParams != null) && (Main.cur().netServerParams.isMaster()))
      this.inUse += 1;
  }

  public void decreasePlaneCount()
  {
    if ((Main.cur().netServerParams != null) && (Main.cur().netServerParams.isMaster()))
    {
      if (this.maxAllowed <= 0) {
        return;
      }

      this.maxAllowed -= 1;
    }
  }

  public void increasePlaneCount()
  {
    if ((Main.cur().netServerParams != null) && (Main.cur().netServerParams.isMaster()))
    {
      if (this.maxAllowed == -1) {
        return;
      }
      this.maxAllowed += 1;
    }
  }

  public boolean isAvailable(boolean paramBoolean)
  {
    if ((this.maxAllowed == -1) || (!paramBoolean)) {
      return true;
    }

    if (paramBoolean)
    {
      if ((this.maxAllowed > 0) && (this.inUse < this.maxAllowed)) {
        return true;
      }

    }
    else if (this.inUse < this.maxAllowed) {
      return true;
    }
    return false;
  }

  public String getClassShortAcName()
  {
    if (this.acNameFromClass == null) {
      this.acNameFromClass = getShortAcNameFromClass(((Class)Property.value(this.aircraftName, "airClass", null)).toString());
    }
    return this.acNameFromClass;
  }

  public static String getStaticAcNameFromActor(String paramString) {
    try {
      return paramString.substring(paramString.indexOf("$") + 1, paramString.indexOf("@")); } catch (Exception localException) {
    }return "";
  }

  public static String getShortAcNameFromClass(String paramString) {
    try {
      return paramString.substring(paramString.lastIndexOf(".") + 1, paramString.length()); } catch (Exception localException) {
    }return "";
  }

  public static boolean isPlaneLandedAndDamaged(FlightModel paramFlightModel)
  {
    return (BornPlace.isLandedOnHomeBase(paramFlightModel, null)) && ((paramFlightModel.Gears.isAnyDamaged()) || (!paramFlightModel.isCapableOfBMP()) || (paramFlightModel.isSentControlsOutNote()));
  }

  public static boolean isPlaneUsable(FlightModel paramFlightModel)
  {
    return (!paramFlightModel.Gears.isAnyDamaged()) && (paramFlightModel.isCapableOfBMP());
  }
}
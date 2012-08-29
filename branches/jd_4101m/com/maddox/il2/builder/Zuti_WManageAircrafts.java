package com.maddox.il2.builder;

import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowBoxSeparate;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowTable;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.ZutiAircraft;
import com.maddox.il2.objects.air.Scheme1;
import com.maddox.il2.objects.air.TypeBomber;
import com.maddox.il2.objects.air.TypeDiveBomber;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.air.TypeSailPlane;
import com.maddox.il2.objects.air.TypeScout;
import com.maddox.il2.objects.air.TypeStormovik;
import com.maddox.rts.LDRres;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

public class Zuti_WManageAircrafts extends GWindowFramed
{
  Table lstAvailable;
  Table lstInReserve;
  GWindowButton bAddAll;
  GWindowButton bAdd;
  GWindowButton bRemAll;
  GWindowButton bRem;
  GWindowButton bModifyPlane;
  GWindowLabel lSeparate;
  GWindowBoxSeparate bSeparate;
  GWindowLabel lCountry;
  GWindowComboControl cCountry;
  static ArrayList lstCountry = new ArrayList();
  GWindowButton bCountryAdd;
  GWindowButton bCountryRem;
  GWindowLabel lYear;
  GWindowComboControl cYear;
  static ArrayList lstYear = new ArrayList();
  GWindowButton bYearAdd;
  GWindowButton bYearRem;
  GWindowLabel lType;
  GWindowComboControl cType;
  static ArrayList lstType = new ArrayList();
  GWindowButton bTypeAdd;
  GWindowButton bTypeRem;
  private ArrayList airNames = null;
  private GWindowEditControl parentEditControl = null;
  public Zuti_WAircraftLoadout zutiCapturedAcLoadouts = null;

  private static boolean showAI = false;

  public void windowShown()
  {
    super.windowShown();
  }

  public void windowHidden()
  {
    super.windowHidden();
    setShowAIPlanes(false);
  }

  public void created()
  {
    this.bAlwaysOnTop = true;
    super.created();
    this.title = Plugin.i18n("mds.zAircrafts.title");
    this.clientWindow = create(new GWindowDialogClient()
    {
      public void resized()
      {
        super.resized();
        Zuti_WManageAircrafts.this.setAircraftSizes(this);
      }
    });
    GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)this.clientWindow;

    this.lstAvailable = new Table(localGWindowDialogClient, Plugin.i18n("bplace_planes"), 1.0F, 1.0F, 6.0F, 10.0F);
    this.lstInReserve = new Table(localGWindowDialogClient, Plugin.i18n("bplace_list"), 14.0F, 1.0F, 6.0F, 10.0F);
    localGWindowDialogClient.addControl(this.bAddAll = new GWindowButton(localGWindowDialogClient, 8.0F, 1.0F, 5.0F, 2.0F, Plugin.i18n("bplace_addall"), null)
    {
      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        Zuti_WManageAircrafts.this.lstAvailable.lst.clear();
        Zuti_WManageAircrafts.this.addAllAircraft(Zuti_WManageAircrafts.this.lstAvailable.lst);
        Zuti_WManageAircrafts.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient.addControl(this.bAdd = new GWindowButton(localGWindowDialogClient, 8.0F, 3.0F, 5.0F, 2.0F, Plugin.i18n("bplace_add"), null)
    {
      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        int i = Zuti_WManageAircrafts.this.lstInReserve.selectRow;
        if ((i < 0) || (i >= Zuti_WManageAircrafts.this.lstInReserve.lst.size())) {
          return true;
        }
        Zuti_WManageAircrafts.this.lstAvailable.lst.add(Zuti_WManageAircrafts.this.lstInReserve.lst.get(i));
        Zuti_WManageAircrafts.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient.addControl(this.bRemAll = new GWindowButton(localGWindowDialogClient, 8.0F, 6.0F, 5.0F, 2.0F, Plugin.i18n("bplace_delall"), null)
    {
      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        Zuti_WManageAircrafts.this.lstAvailable.lst.clear();
        Zuti_WManageAircrafts.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient.addControl(this.bRem = new GWindowButton(localGWindowDialogClient, 8.0F, 8.0F, 5.0F, 2.0F, Plugin.i18n("bplace_del"), null)
    {
      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) return false;
        int i = Zuti_WManageAircrafts.this.lstAvailable.selectRow;
        if ((i < 0) || (i >= Zuti_WManageAircrafts.this.lstAvailable.lst.size())) return true;
        Zuti_WManageAircrafts.this.lstAvailable.lst.remove(i);
        Zuti_WManageAircrafts.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient.addControl(this.bModifyPlane = new GWindowButton(localGWindowDialogClient, 8.0F, 12.0F, 5.0F, 2.0F, Plugin.i18n("mds.aircraft.modify"), null)
    {
      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        if (Zuti_WManageAircrafts.this.zutiCapturedAcLoadouts == null) {
          return false;
        }
        if (!Zuti_WManageAircrafts.this.zutiCapturedAcLoadouts.isVisible())
        {
          if ((Zuti_WManageAircrafts.this.lstAvailable.selectRow < 0) || (Zuti_WManageAircrafts.this.lstAvailable.selectRow >= Zuti_WManageAircrafts.this.lstAvailable.lst.size())) {
            return true;
          }

          Zuti_WManageAircrafts.this.zutiCapturedAcLoadouts.setSelectedAircraft((String)Zuti_WManageAircrafts.this.lstAvailable.lst.get(Zuti_WManageAircrafts.this.lstAvailable.selectRow));
          Zuti_WManageAircrafts.this.zutiCapturedAcLoadouts.setTitle((String)Zuti_WManageAircrafts.this.lstAvailable.lst.get(Zuti_WManageAircrafts.this.lstAvailable.selectRow));
          Zuti_WManageAircrafts.this.zutiCapturedAcLoadouts.showWindow();
          return true;
        }

        return true;
      }
    });
    localGWindowDialogClient.addLabel(this.lSeparate = new GWindowLabel(localGWindowDialogClient, 3.0F, 12.0F, 12.0F, 1.6F, " " + Plugin.i18n("bplace_cats") + " ", null));
    this.bSeparate = new GWindowBoxSeparate(localGWindowDialogClient, 1.0F, 12.5F, 27.0F, 8.0F);
    this.bSeparate.exclude = this.lSeparate;
    localGWindowDialogClient.addLabel(this.lCountry = new GWindowLabel(localGWindowDialogClient, 2.0F, 14.0F, 7.0F, 1.6F, Plugin.i18n("bplace_country"), null));
    localGWindowDialogClient.addControl(this.cCountry = new GWindowComboControl(localGWindowDialogClient, 9.0F, 14.0F, 7.0F)
    {
      public void afterCreated()
      {
        super.afterCreated();
        setEditable(false);
        ResourceBundle localResourceBundle = ResourceBundle.getBundle("i18n/country", RTSConf.cur.locale, LDRres.loader());
        TreeMap localTreeMap = new TreeMap();
        ArrayList localArrayList = Main.cur().airClasses;
        Object localObject;
        String str1;
        for (int i = 0; i < localArrayList.size(); i++)
        {
          localObject = (Class)localArrayList.get(i);
          if ((!Property.containsValue((Class)localObject, "cockpitClass")) && (!Zuti_WManageAircrafts.showAI))
            continue;
          str1 = Property.stringValue((Class)localObject, "originCountry", null);
          if (str1 == null)
            continue;
          String str2;
          try {
            str2 = localResourceBundle.getString(str1);
          }
          catch (Exception localException)
          {
            continue;
          }
          localTreeMap.put(str2, str1);
        }

        Iterator localIterator = localTreeMap.keySet().iterator();
        while (localIterator.hasNext())
        {
          localObject = (String)localIterator.next();
          str1 = (String)localTreeMap.get(localObject);
          Zuti_WManageAircrafts.lstCountry.add(str1);
          add((String)localObject);
        }
        if (Zuti_WManageAircrafts.lstCountry.size() > 0) setSelected(0, true, false);
      }
    });
    localGWindowDialogClient.addControl(this.bCountryAdd = new GWindowButton(localGWindowDialogClient, 17.0F, 14.0F, 5.0F, 1.6F, Plugin.i18n("bplace_add"), null)
    {
      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) return false;
        String str1 = (String)Zuti_WManageAircrafts.lstCountry.get(Zuti_WManageAircrafts.this.cCountry.getSelected());
        ArrayList localArrayList = Main.cur().airClasses;
        for (int i = 0; i < localArrayList.size(); i++)
        {
          Class localClass = (Class)localArrayList.get(i);
          if (((!Property.containsValue(localClass, "cockpitClass")) && (!Zuti_WManageAircrafts.showAI)) || (!str1.equals(Property.stringValue(localClass, "originCountry", null))))
            continue;
          String str2 = Property.stringValue(localClass, "keyName");
          if (Zuti_WManageAircrafts.this.lstAvailable.lst.contains(str2)) continue; Zuti_WManageAircrafts.this.lstAvailable.lst.add(str2);
        }

        Zuti_WManageAircrafts.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient.addControl(this.bCountryRem = new GWindowButton(localGWindowDialogClient, 22.0F, 14.0F, 5.0F, 1.6F, Plugin.i18n("bplace_del"), null)
    {
      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) return false;
        String str1 = (String)Zuti_WManageAircrafts.lstCountry.get(Zuti_WManageAircrafts.this.cCountry.getSelected());
        ArrayList localArrayList = Main.cur().airClasses;
        for (int i = 0; i < localArrayList.size(); i++)
        {
          Class localClass = (Class)localArrayList.get(i);
          if (((!Property.containsValue(localClass, "cockpitClass")) && (!Zuti_WManageAircrafts.showAI)) || (!str1.equals(Property.stringValue(localClass, "originCountry", null))))
            continue;
          String str2 = Property.stringValue(localClass, "keyName");
          int j = Zuti_WManageAircrafts.this.lstAvailable.lst.indexOf(str2);
          if (j < 0) continue; Zuti_WManageAircrafts.this.lstAvailable.lst.remove(j);
        }

        Zuti_WManageAircrafts.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient.addLabel(this.lYear = new GWindowLabel(localGWindowDialogClient, 2.0F, 16.0F, 7.0F, 1.6F, Plugin.i18n("bplace_year"), null));
    localGWindowDialogClient.addControl(this.cYear = new GWindowComboControl(localGWindowDialogClient, 9.0F, 16.0F, 7.0F)
    {
      public void afterCreated()
      {
        super.afterCreated();
        setEditable(false);
        TreeMap localTreeMap = new TreeMap();
        ArrayList localArrayList = Main.cur().airClasses;
        Object localObject;
        for (int i = 0; i < localArrayList.size(); i++)
        {
          localObject = (Class)localArrayList.get(i);
          if ((!Property.containsValue((Class)localObject, "cockpitClass")) && (!Zuti_WManageAircrafts.showAI))
            continue;
          float f = Property.floatValue((Class)localObject, "yearService", 0.0F);
          if (f == 0.0F) continue; localTreeMap.put("" + (int)f, null);
        }

        Iterator localIterator = localTreeMap.keySet().iterator();
        while (localIterator.hasNext())
        {
          localObject = (String)localIterator.next();
          Zuti_WManageAircrafts.lstYear.add(localObject);
          add((String)localObject);
        }
        if (Zuti_WManageAircrafts.lstYear.size() > 0) setSelected(0, true, false);
      }
    });
    localGWindowDialogClient.addControl(this.bYearAdd = new GWindowButton(localGWindowDialogClient, 17.0F, 16.0F, 5.0F, 1.6F, Plugin.i18n("bplace_add"), null)
    {
      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) return false;
        String str1 = (String)Zuti_WManageAircrafts.lstYear.get(Zuti_WManageAircrafts.this.cYear.getSelected());
        ArrayList localArrayList = Main.cur().airClasses;
        for (int i = 0; i < localArrayList.size(); i++)
        {
          Class localClass = (Class)localArrayList.get(i);
          if (((!Property.containsValue(localClass, "cockpitClass")) && (!Zuti_WManageAircrafts.showAI)) || (!str1.equals("" + (int)Property.floatValue(localClass, "yearService", 0.0F))))
            continue;
          String str2 = Property.stringValue(localClass, "keyName");
          if (Zuti_WManageAircrafts.this.lstAvailable.lst.contains(str2)) continue; Zuti_WManageAircrafts.this.lstAvailable.lst.add(str2);
        }

        Zuti_WManageAircrafts.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient.addControl(this.bYearRem = new GWindowButton(localGWindowDialogClient, 22.0F, 16.0F, 5.0F, 1.6F, Plugin.i18n("bplace_del"), null)
    {
      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) return false;
        String str1 = (String)Zuti_WManageAircrafts.lstYear.get(Zuti_WManageAircrafts.this.cYear.getSelected());
        ArrayList localArrayList = Main.cur().airClasses;
        for (int i = 0; i < localArrayList.size(); i++)
        {
          Class localClass = (Class)localArrayList.get(i);
          if (((!Property.containsValue(localClass, "cockpitClass")) && (!Zuti_WManageAircrafts.showAI)) || (!str1.equals("" + (int)Property.floatValue(localClass, "yearService", 0.0F))))
            continue;
          String str2 = Property.stringValue(localClass, "keyName");
          int j = Zuti_WManageAircrafts.this.lstAvailable.lst.indexOf(str2);
          if (j < 0) continue; Zuti_WManageAircrafts.this.lstAvailable.lst.remove(j);
        }

        Zuti_WManageAircrafts.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient.addLabel(this.lType = new GWindowLabel(localGWindowDialogClient, 2.0F, 18.0F, 7.0F, 1.6F, Plugin.i18n("bplace_category"), null));
    localGWindowDialogClient.addControl(this.cType = new GWindowComboControl(localGWindowDialogClient, 9.0F, 18.0F, 7.0F)
    {
      public void afterCreated()
      {
        super.afterCreated();
        setEditable(false);
        TreeMap localTreeMap = new TreeMap();
        ArrayList localArrayList = Main.cur().airClasses;
        Object localObject;
        for (int i = 0; i < localArrayList.size(); i++)
        {
          localObject = (Class)localArrayList.get(i);
          if ((!Property.containsValue((Class)localObject, "cockpitClass")) && (!Zuti_WManageAircrafts.showAI))
            continue;
          if (TypeStormovik.class.isAssignableFrom((Class)localObject))
            localTreeMap.put(Plugin.i18n("bplace_sturm"), TypeStormovik.class);
          if (TypeFighter.class.isAssignableFrom((Class)localObject)) {
            localTreeMap.put(Plugin.i18n("bplace_fiter"), TypeFighter.class);
          }

          if (TypeBomber.class.isAssignableFrom((Class)localObject))
            localTreeMap.put(Plugin.i18n("bplace_bomber"), TypeBomber.class);
          if (TypeScout.class.isAssignableFrom((Class)localObject))
            localTreeMap.put(Plugin.i18n("bplace_recon"), TypeScout.class);
          if (TypeDiveBomber.class.isAssignableFrom((Class)localObject))
            localTreeMap.put(Plugin.i18n("bplace_diver"), TypeDiveBomber.class);
          if (TypeSailPlane.class.isAssignableFrom((Class)localObject))
            localTreeMap.put(Plugin.i18n("bplace_sailer"), TypeSailPlane.class);
          if (Scheme1.class.isAssignableFrom((Class)localObject))
            localTreeMap.put(Plugin.i18n("bplace_single"), Scheme1.class);
          else {
            localTreeMap.put(Plugin.i18n("bplace_multi"), null);
          }
        }
        Iterator localIterator = localTreeMap.keySet().iterator();
        while (localIterator.hasNext())
        {
          localObject = (String)localIterator.next();
          Class localClass = (Class)localTreeMap.get(localObject);
          PlMisBorn.lstType.add(localClass);
          add((String)localObject);
        }
        if (PlMisBorn.lstType.size() > 0) setSelected(0, true, false);
      }
    });
    localGWindowDialogClient.addControl(this.bTypeAdd = new GWindowButton(localGWindowDialogClient, 17.0F, 18.0F, 5.0F, 1.6F, Plugin.i18n("bplace_add"), null)
    {
      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) return false;
        Class localClass1 = (Class)PlMisBorn.lstType.get(Zuti_WManageAircrafts.this.cType.getSelected());
        ArrayList localArrayList = Main.cur().airClasses;
        for (int i = 0; i < localArrayList.size(); i++)
        {
          Class localClass2 = (Class)localArrayList.get(i);
          if ((!Property.containsValue(localClass2, "cockpitClass")) && (!Zuti_WManageAircrafts.showAI))
            continue;
          if (localClass1 == null ? 
            Scheme1.class.isAssignableFrom(localClass2) : 
            !localClass1.isAssignableFrom(localClass2)) continue;
          String str = Property.stringValue(localClass2, "keyName");
          if (Zuti_WManageAircrafts.this.lstAvailable.lst.contains(str)) continue; Zuti_WManageAircrafts.this.lstAvailable.lst.add(str);
        }

        Zuti_WManageAircrafts.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient.addControl(this.bTypeRem = new GWindowButton(localGWindowDialogClient, 22.0F, 18.0F, 5.0F, 1.6F, Plugin.i18n("bplace_del"), null)
    {
      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) return false;
        Class localClass1 = (Class)PlMisBorn.lstType.get(Zuti_WManageAircrafts.this.cType.getSelected());
        ArrayList localArrayList = Main.cur().airClasses;
        for (int i = 0; i < localArrayList.size(); i++)
        {
          Class localClass2 = (Class)localArrayList.get(i);
          if ((!Property.containsValue(localClass2, "cockpitClass")) && (!Zuti_WManageAircrafts.showAI))
            continue;
          if (localClass1 == null ? 
            Scheme1.class.isAssignableFrom(localClass2) : 
            !localClass1.isAssignableFrom(localClass2)) continue;
          String str = Property.stringValue(localClass2, "keyName");
          int j = Zuti_WManageAircrafts.this.lstAvailable.lst.indexOf(str);
          if (j < 0) continue; Zuti_WManageAircrafts.this.lstAvailable.lst.remove(j);
        }

        Zuti_WManageAircrafts.this.fillTabAircraft();
        return true;
      }
    });
  }

  private void fillTabAircraft() {
    int i = this.lstAvailable.selectRow;
    int j = this.lstInReserve.selectRow;
    this.lstAvailable.lst = this.airNames;
    this.lstInReserve.lst.clear();
    ArrayList localArrayList = Main.cur().airClasses;
    Class localClass;
    String str;
    for (int k = 0; k < localArrayList.size(); k++)
    {
      localClass = (Class)localArrayList.get(k);
      if ((!Property.containsValue(localClass, "cockpitClass")) && (!showAI))
        continue;
      str = Property.stringValue(localClass, "keyName");
      if (!this.lstAvailable.lst.contains(str)) {
        this.lstInReserve.lst.add(str);
      }
    }
    if (this.lstAvailable.lst.size() > 0)
    {
      this.lstAvailable.lst.clear();
      for (k = 0; k < localArrayList.size(); k++)
      {
        localClass = (Class)localArrayList.get(k);
        if ((!Property.containsValue(localClass, "cockpitClass")) && (!showAI))
          continue;
        str = Property.stringValue(localClass, "keyName");
        if (this.lstInReserve.lst.contains(str)) continue; this.lstAvailable.lst.add(str);
      }
    }

    if (i >= 0)
    {
      if (this.lstAvailable.lst.size() > 0)
      {
        if (i >= this.lstAvailable.lst.size()) i = this.lstAvailable.lst.size() - 1;
      }
      else
        i = -1;
    }
    this.lstAvailable.setSelect(i, 0);
    if (j >= 0)
    {
      if (this.lstInReserve.lst.size() > 0)
      {
        if (j >= this.lstInReserve.lst.size()) j = this.lstInReserve.lst.size() - 1;
      }
      else
        j = -1;
    }
    this.lstInReserve.setSelect(j, 0);
    this.lstAvailable.resized();
    this.lstInReserve.resized();
  }

  private void addAllAircraft(ArrayList paramArrayList)
  {
    ArrayList localArrayList = Main.cur().airClasses;
    for (int i = 0; i < localArrayList.size(); i++)
    {
      Class localClass = (Class)localArrayList.get(i);
      if ((!Property.containsValue(localClass, "cockpitClass")) && (!showAI))
        continue;
      String str = Property.stringValue(localClass, "keyName");
      if (!paramArrayList.contains(str))
        paramArrayList.add(str);
    }
  }

  private void setAircraftSizes(GWindow paramGWindow)
  {
    float f1 = paramGWindow.win.dx;
    float f2 = paramGWindow.win.dy;
    GFont localGFont = paramGWindow.root.textFonts[0];
    float f3 = paramGWindow.lAF().metric();
    GSize localGSize = new GSize();
    localGFont.size(Plugin.i18n("bplace_addall"), localGSize);
    float f4 = localGSize.dx;
    localGFont.size(Plugin.i18n("bplace_add"), localGSize);
    float f5 = localGSize.dx;
    localGFont.size(Plugin.i18n("bplace_delall"), localGSize);
    float f6 = localGSize.dx;
    localGFont.size(Plugin.i18n("bplace_del"), localGSize);
    float f7 = localGSize.dx;
    localGFont.size(Plugin.i18n("bplace_planes"), localGSize);
    float f8 = localGSize.dx;
    localGFont.size(Plugin.i18n("bplace_list"), localGSize);
    float f9 = localGSize.dx;
    float f10 = f4;
    if (f10 < f5)
      f10 = f5;
    if (f10 < f6)
      f10 = f6;
    if (f10 < f7)
      f10 = f7;
    float f11 = f3 + f10;
    f10 += f3 + 4.0F * f3 + f8 + 4.0F * f3 + f9 + 4.0F * f3;
    if (f1 < f10)
      f1 = f10;
    float f12 = 10.0F * f3 + 10.0F * f3 + 2.0F * f3;
    if (f2 < f12)
      f2 = f12;
    float f13 = (f1 - f11) / 2.0F;
    this.bAddAll.setPosSize(f13, f3, f11, 2.0F * f3);
    this.bAdd.setPosSize(f13, f3 + 2.0F * f3, f11, 2.0F * f3);
    this.bRemAll.setPosSize(f13, 2.0F * f3 + 4.0F * f3, f11, 2.0F * f3);
    this.bRem.setPosSize(f13, 2.0F * f3 + 6.0F * f3, f11, 2.0F * f3);
    this.bModifyPlane.setPosSize(f13, 2.0F * f3 + 10.0F * f3, f11, 2.0F * f3);
    float f14 = (f1 - f11 - 4.0F * f3) / 2.0F;
    float f15 = f2 - 6.0F * f3 - 2.0F * f3 - 3.0F * f3;
    this.lstAvailable.setPosSize(f3, f3, f14, f15);
    this.lstInReserve.setPosSize(f1 - f3 - f14, f3, f14, f15);
    localGFont.size(" " + Plugin.i18n("bplace_cats") + " ", localGSize);
    f14 = localGSize.dx;
    float f16 = f3 + f15;
    this.lSeparate.setPosSize(2.0F * f3, f16, f14, 2.0F * f3);
    this.bSeparate.setPosSize(f3, f16 + f3, f1 - 2.0F * f3, f2 - f16 - 2.0F * f3);
    localGFont.size(Plugin.i18n("bplace_country"), localGSize);
    float f17 = localGSize.dx;
    localGFont.size(Plugin.i18n("bplace_year"), localGSize);
    if (f17 < localGSize.dx)
      f17 = localGSize.dx;
    localGFont.size(Plugin.i18n("bplace_category"), localGSize);
    if (f17 < localGSize.dx)
      f17 = localGSize.dx;
    f11 = 2.0F * f3 + f5 + f7;
    f14 = f1 - f17 - f11 - 6.0F * f3;
    float f18 = paramGWindow.lAF().getComboH();
    this.lCountry.setPosSize(2.0F * f3, f16 + 2.0F * f3, f17, 2.0F * f3);
    this.cCountry.setPosSize(2.0F * f3 + f17 + f3, f16 + 2.0F * f3 + (2.0F * f3 - f18) / 2.0F, f14, f18);
    this.bCountryAdd.setPosSize(f1 - 4.0F * f3 - f7 - f5, f16 + 2.0F * f3, f3 + f5, 2.0F * f3);
    this.bCountryRem.setPosSize(f1 - 3.0F * f3 - f7, f16 + 2.0F * f3, f3 + f7, 2.0F * f3);
    this.lYear.setPosSize(2.0F * f3, f16 + 4.0F * f3, f17, 2.0F * f3);
    this.cYear.setPosSize(2.0F * f3 + f17 + f3, f16 + 4.0F * f3 + (2.0F * f3 - f18) / 2.0F, f14, f18);
    this.bYearAdd.setPosSize(f1 - 4.0F * f3 - f7 - f5, f16 + 4.0F * f3, f3 + f5, 2.0F * f3);
    this.bYearRem.setPosSize(f1 - 3.0F * f3 - f7, f16 + 4.0F * f3, f3 + f7, 2.0F * f3);
    this.lType.setPosSize(2.0F * f3, f16 + 6.0F * f3, f17, 2.0F * f3);
    this.cType.setPosSize(2.0F * f3 + f17 + f3, f16 + 6.0F * f3 + (2.0F * f3 - f18) / 2.0F, f14, f18);
    this.bTypeAdd.setPosSize(f1 - 4.0F * f3 - f7 - f5, f16 + 6.0F * f3, f3 + f5, 2.0F * f3);
    this.bTypeRem.setPosSize(f1 - 3.0F * f3 - f7, f16 + 6.0F * f3, f3 + f7, 2.0F * f3);
  }

  public void afterCreated()
  {
    super.afterCreated();

    resized();
    close(false);
  }

  public void close(boolean paramBoolean)
  {
    if (this.parentEditControl != null)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      for (int i = 0; i < this.lstAvailable.lst.size(); i++)
      {
        localStringBuffer.append(this.lstAvailable.lst.get(i));
        localStringBuffer.append(" ");
      }
      this.parentEditControl.setValue(localStringBuffer.toString());
      this.parentEditControl.notify(2, 0);

      syncLists();

      PlMission.setChanged();
    }

    super.close(paramBoolean);
  }

  public Zuti_WManageAircrafts()
  {
    doNew(Plugin.builder.clientWindow, 2.0F, 2.0F, 40.0F, 40.0F, true);
    this.bSizable = true;
  }

  public void setShowAIPlanes(boolean paramBoolean)
  {
    showAI = paramBoolean;
    this.bModifyPlane.bEnable = (!showAI);
    this.bModifyPlane.bVisible = (!showAI);
  }

  public void setParentEditControl(GWindowEditControl paramGWindowEditControl)
  {
    this.parentEditControl = paramGWindowEditControl;

    if (paramGWindowEditControl != null)
    {
      zutiParseCapturedBaseAircrafts(this.parentEditControl.getValue());
      fillTabAircraft();
    }

    if (this.airNames == null)
      this.airNames = new ArrayList();
  }

  public void clearAirNames()
  {
    this.airNames.clear();
    this.airNames = null;
  }

  private void zutiParseCapturedBaseAircrafts(String paramString)
  {
    this.airNames = new ArrayList();
    int i = 0;

    if (paramString == null) {
      return;
    }
    while (true)
    {
      i++;

      if (paramString.indexOf(" ") > 0)
      {
        String str = paramString.substring(0, paramString.indexOf(" ")).trim();
        paramString = paramString.substring(paramString.indexOf(" ") + 1, paramString.length()).trim();

        if (str.length() > 0)
          this.airNames.add(str);
      }
      else
      {
        if (paramString.length() <= 0) break;
        this.airNames.add(paramString); break;
      }

      if (i > 1000)
        break;
    }
  }

  public void setTitle(String paramString) {
    this.title = paramString;
  }

  public void setAircraftLoadout(Zuti_WAircraftLoadout paramZuti_WAircraftLoadout)
  {
    this.zutiCapturedAcLoadouts = paramZuti_WAircraftLoadout;

    if (this.zutiCapturedAcLoadouts == null)
    {
      this.zutiCapturedAcLoadouts = new Zuti_WAircraftLoadout();
    }
  }

  public Zuti_WAircraftLoadout getAircraftLoadout()
  {
    return this.zutiCapturedAcLoadouts;
  }

  private void syncLists()
  {
    ArrayList localArrayList = new ArrayList();

    if (this.zutiCapturedAcLoadouts == null)
      this.zutiCapturedAcLoadouts = new Zuti_WAircraftLoadout();
    if (this.zutiCapturedAcLoadouts.modifiedAircrafts == null)
      this.zutiCapturedAcLoadouts.modifiedAircrafts = new ArrayList();
    Object localObject1;
    int j;
    Object localObject2;
    for (int i = 0; i < this.airNames.size(); i++)
    {
      localObject1 = (String)this.airNames.get(i);
      j = 0;
      for (int k = 0; k < this.zutiCapturedAcLoadouts.modifiedAircrafts.size(); k++)
      {
        localObject2 = (ZutiAircraft)this.zutiCapturedAcLoadouts.modifiedAircrafts.get(k);

        if (!((ZutiAircraft)localObject2).getAcName().equals(localObject1))
          continue;
        j = 1;
        break;
      }

      if (j != 0) {
        continue;
      }
      ZutiAircraft localZutiAircraft = new ZutiAircraft();
      localZutiAircraft.setAcName((String)localObject1);
      localZutiAircraft.setMaxAllowed(0);
      localObject2 = new ArrayList();
      ((ArrayList)localObject2).add("Default");
      localZutiAircraft.setSelectedWeapons((ArrayList)localObject2);
      localArrayList.add(localZutiAircraft);
    }

    for (i = 0; i < localArrayList.size(); i++) {
      this.zutiCapturedAcLoadouts.modifiedAircrafts.add(localArrayList.get(i));
    }

    localArrayList.clear();
    for (i = 0; i < this.zutiCapturedAcLoadouts.modifiedAircrafts.size(); i++)
    {
      localObject1 = (ZutiAircraft)this.zutiCapturedAcLoadouts.modifiedAircrafts.get(i);
      j = 0;
      for (int m = 0; m < this.airNames.size(); m++)
      {
        localObject2 = (String)this.airNames.get(m);
        if (!((ZutiAircraft)localObject1).getAcName().equals(localObject2))
          continue;
        j = 1;
        break;
      }

      if (j == 0) {
        localArrayList.add(localObject1);
      }
    }
    for (i = 0; i < localArrayList.size(); i++)
      this.zutiCapturedAcLoadouts.modifiedAircrafts.remove(localArrayList.get(i));
  }

  static class Item
  {
    public String name;

    public Item(String paramString)
    {
      this.name = paramString;
    }
  }

  class Table extends GWindowTable
  {
    public ArrayList lst = new ArrayList();

    public int countRows()
    {
      return this.lst != null ? this.lst.size() : 0;
    }

    public Object getValueAt(int paramInt1, int paramInt2)
    {
      if (this.lst == null) return null;
      if ((paramInt1 < 0) || (paramInt1 >= this.lst.size())) return null;
      String str = (String)this.lst.get(paramInt1);
      return I18N.plane(str);
    }

    public void resolutionChanged()
    {
      this.vSB.scroll = rowHeight(0);
      super.resolutionChanged();
    }

    public Table(GWindow paramString, String paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float arg7)
    {
      super(paramFloat2, paramFloat3, paramFloat4, localObject);
      this.bColumnsSizable = false;
      addColumn(paramFloat1, null);
      this.vSB.scroll = rowHeight(0);
      resized();
    }
  }
}
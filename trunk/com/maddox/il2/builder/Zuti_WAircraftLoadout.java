package com.maddox.il2.builder;

import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowTable;
import com.maddox.il2.game.ZutiAircraft;
import java.util.ArrayList;

public class Zuti_WAircraftLoadout extends GWindowFramed
{
  private Table lstSelected;
  private Table lstAvailable;
  private GWindowEditControl wMaxAllowed;
  private GWindowButton bAdd;
  private GWindowButton bAddAll;
  private GWindowButton bRemove;
  private GWindowButton bRemoveAll;
  private GWindowCheckBox wUnlimitedPlanes;
  public ArrayList modifiedAircrafts = null;
  public ZutiAircraft selectedAircraft = null;

  public Zuti_WAircraftLoadout()
  {
    doNew(Plugin.builder.clientWindow, 2.0F, 2.0F, 45.0F, 30.0F, true);
    this.bSizable = true;
  }

  public void afterCreated()
  {
    super.afterCreated();

    close(false);
  }

  public void windowShown()
  {
    super.windowShown();

    if (this.lstSelected != null)
      this.lstSelected.resolutionChanged();
    if (this.lstAvailable != null)
      this.lstAvailable.resolutionChanged();
  }

  public void windowHidden()
  {
    super.windowHidden();
  }

  public void created()
  {
    this.bAlwaysOnTop = true;
    super.created();
    this.title = Plugin.i18n("mds.zLoadouts.title");
    this.clientWindow = create(new GWindowDialogClient()
    {
      public void resized()
      {
        super.resized();
        Zuti_WAircraftLoadout.this.setSizes(this);
      }
    });
    GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)this.clientWindow;

    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 1.0F, 13.0F, 1.3F, Plugin.i18n("mds.zLoadouts.max"), null));
    localGWindowDialogClient.addControl(this.wMaxAllowed = new GWindowEditControl(localGWindowDialogClient, 15.0F, 1.0F, 3.0F, 1.3F, "")
    {
      public void afterCreated()
      {
        super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true;
      }

      public void preRender()
      {
        if (getValue().trim().length() > 0) {
          return;
        }
        super.preRender();
        setValue(new Integer(Zuti_WAircraftLoadout.this.selectedAircraft.getMaxAllowed()).toString(), false);
      }

      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        if (Zuti_WAircraftLoadout.this.selectedAircraft != null)
        {
          Zuti_WAircraftLoadout.this.selectedAircraft.setMaxAllowed(Integer.parseInt(getValue()));
          Zuti_WAircraftLoadout.this.selectedAircraft.setModifiedRuntime(true);
          PlMission.setChanged();
        }
        return false;
      }
    });
    localGWindowDialogClient.addControl(this.wUnlimitedPlanes = new GWindowCheckBox(localGWindowDialogClient, 19.0F, 1.0F, null)
    {
      public void preRender()
      {
        super.preRender();
      }

      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        if (isChecked())
        {
          Zuti_WAircraftLoadout.this.selectedAircraft.setMaxAllowed(-1);
          Zuti_WAircraftLoadout.this.wMaxAllowed.setEnable(false);
        }
        else
        {
          Zuti_WAircraftLoadout.this.wMaxAllowed.setEnable(true);
          Zuti_WAircraftLoadout.this.selectedAircraft.setMaxAllowed(Integer.parseInt(Zuti_WAircraftLoadout.this.wMaxAllowed.getValue()));
        }
        Zuti_WAircraftLoadout.this.selectedAircraft.setModifiedRuntime(true);
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 20.0F, 1.0F, 12.0F, 1.3F, Plugin.i18n("mds.zLoadouts.unlimited"), null));

    this.lstSelected = new Table(localGWindowDialogClient, Plugin.i18n("mds.zLoadouts.selected"), 1.0F, 3.0F, 15.0F, 20.0F);
    this.lstAvailable = new Table(localGWindowDialogClient, Plugin.i18n("mds.zLoadouts.available"), 23.0F, 3.0F, 15.0F, 20.0F);

    localGWindowDialogClient.addControl(this.bAddAll = new GWindowButton(localGWindowDialogClient, 17.0F, 3.0F, 5.0F, 2.0F, Plugin.i18n("bplace_addall"), null)
    {
      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        Zuti_WAircraftLoadout.this.addAllWeaponOptions();

        Zuti_WAircraftLoadout.this.selectedAircraft.setModifiedRuntime(true);
        PlMission.setChanged();
        return true;
      }
    });
    localGWindowDialogClient.addControl(this.bAdd = new GWindowButton(localGWindowDialogClient, 17.0F, 5.0F, 5.0F, 2.0F, Plugin.i18n("bplace_add"), null)
    {
      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        int i = Zuti_WAircraftLoadout.this.lstAvailable.selectRow;
        if ((i < 0) || (i >= Zuti_WAircraftLoadout.this.lstAvailable.lst.size())) {
          return true;
        }
        if (!Zuti_WAircraftLoadout.this.lstSelected.lst.contains(Zuti_WAircraftLoadout.this.lstAvailable.lst.get(i)))
        {
          Zuti_WAircraftLoadout.this.lstSelected.lst.add(Zuti_WAircraftLoadout.this.lstAvailable.lst.get(i));
          Zuti_WAircraftLoadout.this.lstAvailable.lst.remove(i);
        }
        Zuti_WAircraftLoadout.this.lstSelected.resized();
        Zuti_WAircraftLoadout.this.lstAvailable.resized();
        Zuti_WAircraftLoadout.this.selectedAircraft.setModifiedRuntime(true);
        PlMission.setChanged();
        return true;
      }
    });
    localGWindowDialogClient.addControl(this.bRemoveAll = new GWindowButton(localGWindowDialogClient, 17.0F, 8.0F, 5.0F, 2.0F, Plugin.i18n("bplace_delall"), null)
    {
      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        Zuti_WAircraftLoadout.this.lstSelected.lst.clear();
        Zuti_WAircraftLoadout.this.fillAvailableLoadouts();
        Zuti_WAircraftLoadout.this.selectedAircraft.setModifiedRuntime(true);

        PlMission.setChanged();
        return true;
      }
    });
    localGWindowDialogClient.addControl(this.bRemove = new GWindowButton(localGWindowDialogClient, 17.0F, 10.0F, 5.0F, 2.0F, Plugin.i18n("bplace_del"), null)
    {
      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        int i = Zuti_WAircraftLoadout.this.lstSelected.selectRow;
        if ((i < 0) || (i >= Zuti_WAircraftLoadout.this.lstSelected.lst.size())) {
          return true;
        }
        if (!Zuti_WAircraftLoadout.this.lstAvailable.lst.contains(Zuti_WAircraftLoadout.this.lstSelected.lst.get(i)))
          Zuti_WAircraftLoadout.this.lstAvailable.lst.add(Zuti_WAircraftLoadout.this.lstSelected.lst.get(i));
        Zuti_WAircraftLoadout.this.lstSelected.lst.remove(i);
        Zuti_WAircraftLoadout.this.lstSelected.resized();
        Zuti_WAircraftLoadout.this.lstAvailable.resized();
        Zuti_WAircraftLoadout.this.selectedAircraft.setModifiedRuntime(true);

        PlMission.setChanged();
        return true;
      }
    });
  }

  private void setSizes(GWindow paramGWindow) {
    float f1 = 30.0F;
    float f2 = paramGWindow.win.dx;
    float f3 = paramGWindow.win.dy;
    GFont localGFont = paramGWindow.root.textFonts[0];
    float f4 = paramGWindow.lAF().metric();
    GSize localGSize = new GSize();
    localGFont.size(Plugin.i18n("bplace_addall"), localGSize);
    float f5 = localGSize.dx;
    localGFont.size(Plugin.i18n("bplace_add"), localGSize);
    float f6 = localGSize.dx;
    localGFont.size(Plugin.i18n("bplace_delall"), localGSize);
    float f7 = localGSize.dx;
    localGFont.size(Plugin.i18n("bplace_del"), localGSize);
    float f8 = localGSize.dx;
    localGFont.size(Plugin.i18n("bplace_planes"), localGSize);
    float f9 = localGSize.dx;
    localGFont.size(Plugin.i18n("bplace_list"), localGSize);
    float f10 = localGSize.dx;
    float f11 = f5;
    if (f11 < f6)
      f11 = f6;
    if (f11 < f7)
      f11 = f7;
    if (f11 < f8)
      f11 = f8;
    float f12 = f4 + f11;
    f11 += f4 + 4.0F * f4 + f9 + 4.0F * f4 + f10 + 4.0F * f4;
    if (f2 < f11)
      f2 = f11;
    float f13 = 10.0F * f4 + 10.0F * f4 + 2.0F * f4;
    if (f3 < f13)
      f3 = f13;
    float f14 = (f2 - f12) / 2.0F;
    this.bAddAll.setPosSize(f14, f4 + f1, f12, 2.0F * f4);
    this.bAdd.setPosSize(f14, f4 + 2.0F * f4 + f1, f12, 2.0F * f4);
    this.bRemoveAll.setPosSize(f14, 2.0F * f4 + 4.0F * f4 + f1, f12, 2.0F * f4);
    this.bRemove.setPosSize(f14, 2.0F * f4 + 6.0F * f4 + f1, f12, 2.0F * f4);
    float f15 = (f2 - f12 - 4.0F * f4) / 2.0F;
    float f16 = f3 - f4 - f1 - 12.0F;
    this.lstAvailable.setPosSize(f2 - f4 - f15, f4 + f1, f15, f16);
    this.lstSelected.setPosSize(f4, f4 + f1, f15, f16);
  }

  public void setSelectedAircraft(String paramString)
  {
    this.lstAvailable.lst.clear();
    this.lstSelected.lst.clear();

    this.selectedAircraft = getAircraft(paramString);

    if (this.selectedAircraft == null)
    {
      this.selectedAircraft = new ZutiAircraft();
      this.selectedAircraft.setAcName(paramString);
      this.lstSelected.lst.clear();
      this.wMaxAllowed.setValue("0");
      this.wUnlimitedPlanes.setChecked(true, true);
    }
    else
    {
      assignSelectedWeaponsToTable(this.selectedAircraft.getSelectedWeaponI18NNames());
      int i = this.selectedAircraft.getMaxAllowed();
      if (i == -1)
      {
        this.wMaxAllowed.setValue("0");
        this.wUnlimitedPlanes.setChecked(true, true);
      }
      else
      {
        this.wMaxAllowed.setValue(new Integer(i).toString());
        this.wUnlimitedPlanes.setChecked(false, true);
      }
    }

    fillAvailableLoadouts();
    syncLists();

    this.selectedAircraft.setModifiedRuntime(false);
  }

  private void fillAvailableLoadouts()
  {
    this.lstAvailable.lst.clear();
    ArrayList localArrayList = this.selectedAircraft.getWeaponI18NNames();
    if (localArrayList != null)
    {
      for (int i = 0; i < localArrayList.size(); i++)
        this.lstAvailable.lst.add(localArrayList.get(i));
    }
    this.lstSelected.resized();
    this.lstAvailable.resized();
  }

  private void assignSelectedWeaponsToTable(ArrayList paramArrayList)
  {
    this.lstSelected.lst.clear();

    if (paramArrayList == null) {
      return;
    }
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      this.lstSelected.lst.add(paramArrayList.get(i));
    }
  }

  private ZutiAircraft getAircraft(String paramString) {
    if (this.modifiedAircrafts == null) {
      return null;
    }
    for (int i = 0; i < this.modifiedAircrafts.size(); i++)
    {
      ZutiAircraft localZutiAircraft = (ZutiAircraft)this.modifiedAircrafts.get(i);
      if (localZutiAircraft.getAcName().equals(paramString)) {
        return localZutiAircraft;
      }
    }
    return null;
  }

  public void close(boolean paramBoolean) {
    super.close(paramBoolean);

    if ((this.selectedAircraft != null) && (this.selectedAircraft.getModifiedRuntime()))
    {
      if (this.modifiedAircrafts == null) {
        this.modifiedAircrafts = new ArrayList();
      }
      this.selectedAircraft.setSelectedWeapons(this.lstSelected.lst);
      if (this.wUnlimitedPlanes.isChecked())
        this.selectedAircraft.setMaxAllowed(-1);
      else {
        this.selectedAircraft.setMaxAllowed(new Integer(this.wMaxAllowed.getValue()).intValue());
      }
      if (!this.modifiedAircrafts.contains(this.selectedAircraft))
        this.modifiedAircrafts.add(this.selectedAircraft);
    }
  }

  private void addAllWeaponOptions() {
    this.lstSelected.lst.clear();
    ArrayList localArrayList = this.selectedAircraft.getWeaponI18NNames();
    if (localArrayList != null)
    {
      for (int i = 0; i < localArrayList.size(); i++)
        this.lstSelected.lst.add(localArrayList.get(i));
    }
    this.lstAvailable.lst.clear();
    this.lstSelected.resized();
    this.lstAvailable.resized();
  }

  private void syncLists() {
    for (int i = 0; i < this.lstSelected.lst.size(); i++)
      this.lstAvailable.lst.remove(this.lstSelected.lst.get(i));
  }

  public void setTitle(String paramString)
  {
    this.title = (Plugin.i18n("mds.zLoadouts.title") + " " + Plugin.i18n("mds.for") + " " + paramString);
  }

  public void clearArrays()
  {
    if (this.lstAvailable.lst != null)
      this.lstAvailable.lst.clear();
    if (this.lstSelected.lst != null)
      this.lstSelected.lst.clear();
    if (this.modifiedAircrafts != null)
      this.modifiedAircrafts.clear();
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
      if (this.lst == null)
        return null;
      if ((paramInt1 < 0) || (paramInt1 >= this.lst.size()))
        return null;
      String str = (String)this.lst.get(paramInt1);
      return str;
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
package com.maddox.il2.builder;

import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowTable;
import com.maddox.il2.ai.Regiment;
import com.maddox.rts.LDRres;
import com.maddox.rts.RTSConf;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Zuti_WHomeBaseCountries extends GWindowFramed
{
  private Table lstSelected;
  private Table lstAvailable;
  private GWindowButton bAdd;
  private GWindowButton bAddAll;
  private GWindowButton bRemove;
  private GWindowButton bRemoveAll;
  private ArrayList fullCountriesList = new ArrayList();
  private ActorBorn selectedActorBorn;
  private int mode = 0;

  public Zuti_WHomeBaseCountries()
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
    this.title = Plugin.i18n("mds.zCountries.title");
    this.clientWindow = create(new GWindowDialogClient()
    {
      public void resized()
      {
        super.resized();
        Zuti_WHomeBaseCountries.this.setSizes(this);
      }
    });
    GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)this.clientWindow;

    this.lstSelected = new Table(localGWindowDialogClient, Plugin.i18n("mds.zCountries.selected"), 1.0F, 3.0F, 15.0F, 20.0F);
    this.lstAvailable = new Table(localGWindowDialogClient, Plugin.i18n("mds.zCountries.available"), 23.0F, 3.0F, 15.0F, 20.0F);

    localGWindowDialogClient.addControl(this.bAddAll = new GWindowButton(localGWindowDialogClient, 17.0F, 3.0F, 5.0F, 2.0F, Plugin.i18n("bplace_addall"), null)
    {
      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }

        Zuti_WHomeBaseCountries.this.addAllCountries();

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
        int i = Zuti_WHomeBaseCountries.this.lstAvailable.selectRow;
        if ((i < 0) || (i >= Zuti_WHomeBaseCountries.this.lstAvailable.lst.size())) {
          return true;
        }
        if (!Zuti_WHomeBaseCountries.this.lstSelected.lst.contains(Zuti_WHomeBaseCountries.this.lstAvailable.lst.get(i)))
        {
          Zuti_WHomeBaseCountries.this.lstSelected.lst.add(Zuti_WHomeBaseCountries.this.lstAvailable.lst.get(i));
          Zuti_WHomeBaseCountries.this.lstAvailable.lst.remove(i);
        }
        Zuti_WHomeBaseCountries.this.lstSelected.resized();
        Zuti_WHomeBaseCountries.this.lstAvailable.resized();
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
        Zuti_WHomeBaseCountries.this.lstSelected.lst.clear();
        Zuti_WHomeBaseCountries.this.fillAvailableCountries();

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
        int i = Zuti_WHomeBaseCountries.this.lstSelected.selectRow;
        if ((i < 0) || (i >= Zuti_WHomeBaseCountries.this.lstSelected.lst.size())) {
          return true;
        }
        if (!Zuti_WHomeBaseCountries.this.lstAvailable.lst.contains(Zuti_WHomeBaseCountries.this.lstSelected.lst.get(i)))
          Zuti_WHomeBaseCountries.this.lstAvailable.lst.add(Zuti_WHomeBaseCountries.this.lstSelected.lst.get(i));
        Zuti_WHomeBaseCountries.this.lstSelected.lst.remove(i);
        Zuti_WHomeBaseCountries.this.lstSelected.resized();
        Zuti_WHomeBaseCountries.this.lstAvailable.resized();
        PlMission.setChanged();
        return true;
      }
    });
  }

  private void setSizes(GWindow paramGWindow) {
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
    this.bRemoveAll.setPosSize(f13, 2.0F * f3 + 4.0F * f3, f11, 2.0F * f3);
    this.bRemove.setPosSize(f13, 2.0F * f3 + 6.0F * f3, f11, 2.0F * f3);
    float f14 = (f1 - f11 - 4.0F * f3) / 2.0F;
    float f15 = f2 - f3 - 12.0F;
    this.lstAvailable.setPosSize(f1 - f3 - f14, f3, f14, f15);
    this.lstSelected.setPosSize(f3, f3, f14, f15);
  }

  public void setSelectedCountries(ActorBorn paramActorBorn)
  {
    this.lstSelected.lst.clear();
    this.lstAvailable.lst.clear();

    this.selectedActorBorn = paramActorBorn;

    if ((this.fullCountriesList == null) || (this.fullCountriesList.size() < 1)) {
      fillCountries();
    }
    fillAvailableCountries();

    switch (this.mode)
    {
    case 0:
      if ((this.selectedActorBorn.zutiHomeBaseCountries == null) || (this.selectedActorBorn.zutiHomeBaseCountries.size() <= 0)) {
        break;
      }
      for (int i = 0; i < this.selectedActorBorn.zutiHomeBaseCountries.size(); i++)
      {
        try
        {
          String str = (String)this.selectedActorBorn.zutiHomeBaseCountries.get(i);
          if (this.lstAvailable.lst.contains(str))
            this.lstSelected.lst.add(str);
        }
        catch (Exception localException)
        {
        }
      }
      break;
    case 1:
      break;
    case 2:
    }

    syncLists();
  }

  private void fillCountries()
  {
    ResourceBundle localResourceBundle = ResourceBundle.getBundle("i18n/country", RTSConf.cur.locale, LDRres.loader());

    List localList = Regiment.getAll();
    int i = localList.size();
    for (int j = 0; j < i; j++)
    {
      Regiment localRegiment = (Regiment)localList.get(j);
      String str = localResourceBundle.getString(localRegiment.branch());
      if (!this.fullCountriesList.contains(str))
        this.fullCountriesList.add(str);
    }
  }

  private void fillAvailableCountries()
  {
    this.lstAvailable.lst.clear();
    for (int i = 0; i < this.fullCountriesList.size(); i++)
      this.lstAvailable.lst.add(this.fullCountriesList.get(i));
    this.lstSelected.resized();
    this.lstAvailable.resized();
  }

  public void close(boolean paramBoolean)
  {
    super.close(paramBoolean);

    if (this.selectedActorBorn != null)
    {
      switch (this.mode)
      {
      case 0:
        if (this.selectedActorBorn.zutiHomeBaseCountries == null) {
          this.selectedActorBorn.zutiHomeBaseCountries = new ArrayList();
        }
        this.selectedActorBorn.zutiHomeBaseCountries.clear();
        for (int i = 0; i < this.lstSelected.lst.size(); i++) {
          this.selectedActorBorn.zutiHomeBaseCountries.add(this.lstSelected.lst.get(i));
        }
        break;
      case 1:
        break;
      case 2:
      }
    }
  }

  private void addAllCountries()
  {
    this.lstSelected.lst.clear();
    for (int i = 0; i < this.fullCountriesList.size(); i++)
      this.lstSelected.lst.add(this.fullCountriesList.get(i));
    this.lstAvailable.lst.clear();
    this.lstSelected.resized();
    this.lstAvailable.resized();
  }

  private void syncLists()
  {
    for (int i = 0; i < this.lstSelected.lst.size(); i++)
      this.lstAvailable.lst.remove(this.lstSelected.lst.get(i));
  }

  public void setTitle(String paramString)
  {
    this.title = paramString;
  }

  public void clearArrays()
  {
    if (this.lstAvailable.lst != null)
      this.lstAvailable.lst.clear();
    if (this.lstSelected.lst != null)
      this.lstSelected.lst.clear();
  }

  public void setMode(int paramInt)
  {
    this.mode = paramInt;
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
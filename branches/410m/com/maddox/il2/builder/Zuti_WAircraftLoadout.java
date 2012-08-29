// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Zuti_WAircraftLoadout.java

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
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.game.ZutiAircraft;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, Builder, PlMission

public class Zuti_WAircraftLoadout extends com.maddox.gwindow.GWindowFramed
{
    class Table extends com.maddox.gwindow.GWindowTable
    {

        public int countRows()
        {
            return lst == null ? 0 : lst.size();
        }

        public java.lang.Object getValueAt(int i, int j)
        {
            if(lst == null)
                return null;
            if(i < 0 || i >= lst.size())
            {
                return null;
            } else
            {
                java.lang.String s = (java.lang.String)lst.get(i);
                return s;
            }
        }

        public void resolutionChanged()
        {
            vSB.scroll = rowHeight(0);
            super.resolutionChanged();
        }

        public java.util.ArrayList lst;

        public Table(com.maddox.gwindow.GWindow gwindow, java.lang.String s, float f, float f1, float f2, float f3)
        {
            super(gwindow, f, f1, f2, f3);
            lst = new ArrayList();
            bColumnsSizable = false;
            addColumn(s, null);
            vSB.scroll = rowHeight(0);
            resized();
        }
    }


    public Zuti_WAircraftLoadout()
    {
        modifiedAircrafts = null;
        selectedAircraft = null;
        doNew(com.maddox.il2.builder.Plugin.builder.clientWindow, 2.0F, 2.0F, 45F, 30F, true);
        bSizable = true;
    }

    public void afterCreated()
    {
        super.afterCreated();
        close(false);
    }

    public void windowShown()
    {
        super.windowShown();
        if(lstSelected != null)
            lstSelected.resolutionChanged();
        if(lstAvailable != null)
            lstAvailable.resolutionChanged();
    }

    public void windowHidden()
    {
        super.windowHidden();
    }

    public void created()
    {
        bAlwaysOnTop = true;
        super.created();
        title = com.maddox.il2.builder.Plugin.i18n("mds.zLoadouts.title");
        clientWindow = create(new com.maddox.gwindow.GWindowDialogClient() {

            public void resized()
            {
                super.resized();
                setSizes(this);
            }

        }
);
        com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)clientWindow;
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 1.0F, 13F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.zLoadouts.max"), null));
        gwindowdialogclient.addControl(wMaxAllowed = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 15F, 1.0F, 3F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public void preRender()
            {
                if(getValue().trim().length() > 0)
                {
                    return;
                } else
                {
                    super.preRender();
                    setValue((new Integer(selectedAircraft.getMaxAllowed())).toString(), false);
                    return;
                }
            }

            public boolean notify(int i, int j)
            {
                if(i != 2)
                    return false;
                if(selectedAircraft != null)
                {
                    selectedAircraft.setMaxAllowed(java.lang.Integer.parseInt(getValue()));
                    selectedAircraft.setModifiedRuntime(true);
                    com.maddox.il2.builder.PlMission.setChanged();
                }
                return false;
            }

        }
);
        gwindowdialogclient.addControl(wUnlimitedPlanes = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 19F, 1.0F, null) {

            public void preRender()
            {
                super.preRender();
            }

            public boolean notify(int i, int j)
            {
                if(i != 2)
                    return false;
                if(isChecked())
                {
                    selectedAircraft.setMaxAllowed(-1);
                    wMaxAllowed.setEnable(false);
                } else
                {
                    wMaxAllowed.setEnable(true);
                    selectedAircraft.setMaxAllowed(java.lang.Integer.parseInt(wMaxAllowed.getValue()));
                }
                selectedAircraft.setModifiedRuntime(true);
                com.maddox.il2.builder.PlMission.setChanged();
                return false;
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 20F, 1.0F, 12F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.zLoadouts.unlimited"), null));
        lstSelected = new Table(gwindowdialogclient, com.maddox.il2.builder.Plugin.i18n("mds.zLoadouts.selected"), 1.0F, 3F, 15F, 20F);
        lstAvailable = new Table(gwindowdialogclient, com.maddox.il2.builder.Plugin.i18n("mds.zLoadouts.available"), 23F, 3F, 15F, 20F);
        gwindowdialogclient.addControl(bAddAll = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 17F, 3F, 5F, 2.0F, com.maddox.il2.builder.Plugin.i18n("bplace_addall"), null) {

            public boolean notify(int i, int j)
            {
                if(i != 2)
                {
                    return false;
                } else
                {
                    addAllWeaponOptions();
                    selectedAircraft.setModifiedRuntime(true);
                    com.maddox.il2.builder.PlMission.setChanged();
                    return true;
                }
            }

        }
);
        gwindowdialogclient.addControl(bAdd = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 17F, 5F, 5F, 2.0F, com.maddox.il2.builder.Plugin.i18n("bplace_add"), null) {

            public boolean notify(int i, int j)
            {
                if(i != 2)
                    return false;
                int k = lstAvailable.selectRow;
                if(k < 0 || k >= lstAvailable.lst.size())
                    return true;
                if(!lstSelected.lst.contains(lstAvailable.lst.get(k)))
                {
                    lstSelected.lst.add(lstAvailable.lst.get(k));
                    lstAvailable.lst.remove(k);
                }
                lstSelected.resized();
                lstAvailable.resized();
                selectedAircraft.setModifiedRuntime(true);
                com.maddox.il2.builder.PlMission.setChanged();
                return true;
            }

        }
);
        gwindowdialogclient.addControl(bRemoveAll = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 17F, 8F, 5F, 2.0F, com.maddox.il2.builder.Plugin.i18n("bplace_delall"), null) {

            public boolean notify(int i, int j)
            {
                if(i != 2)
                {
                    return false;
                } else
                {
                    lstSelected.lst.clear();
                    fillAvailableLoadouts();
                    selectedAircraft.setModifiedRuntime(true);
                    com.maddox.il2.builder.PlMission.setChanged();
                    return true;
                }
            }

        }
);
        gwindowdialogclient.addControl(bRemove = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 17F, 10F, 5F, 2.0F, com.maddox.il2.builder.Plugin.i18n("bplace_del"), null) {

            public boolean notify(int i, int j)
            {
                if(i != 2)
                    return false;
                int k = lstSelected.selectRow;
                if(k < 0 || k >= lstSelected.lst.size())
                    return true;
                if(!lstAvailable.lst.contains(lstSelected.lst.get(k)))
                    lstAvailable.lst.add(lstSelected.lst.get(k));
                lstSelected.lst.remove(k);
                lstSelected.resized();
                lstAvailable.resized();
                selectedAircraft.setModifiedRuntime(true);
                com.maddox.il2.builder.PlMission.setChanged();
                return true;
            }

        }
);
    }

    private void setSizes(com.maddox.gwindow.GWindow gwindow)
    {
        float f = 30F;
        float f1 = gwindow.win.dx;
        float f2 = gwindow.win.dy;
        com.maddox.gwindow.GFont gfont = gwindow.root.textFonts[0];
        float f3 = gwindow.lAF().metric();
        com.maddox.gwindow.GSize gsize = new GSize();
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_addall"), gsize);
        float f4 = gsize.dx;
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_add"), gsize);
        float f5 = gsize.dx;
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_delall"), gsize);
        float f6 = gsize.dx;
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_del"), gsize);
        float f7 = gsize.dx;
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_planes"), gsize);
        float f8 = gsize.dx;
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_list"), gsize);
        float f9 = gsize.dx;
        float f10 = f4;
        if(f10 < f5)
            f10 = f5;
        if(f10 < f6)
            f10 = f6;
        if(f10 < f7)
            f10 = f7;
        float f11 = f3 + f10;
        f10 += f3 + 4F * f3 + f8 + 4F * f3 + f9 + 4F * f3;
        if(f1 < f10)
            f1 = f10;
        float f12 = 10F * f3 + 10F * f3 + 2.0F * f3;
        if(f2 < f12)
            f2 = f12;
        float f13 = (f1 - f11) / 2.0F;
        bAddAll.setPosSize(f13, f3 + f, f11, 2.0F * f3);
        bAdd.setPosSize(f13, f3 + 2.0F * f3 + f, f11, 2.0F * f3);
        bRemoveAll.setPosSize(f13, 2.0F * f3 + 4F * f3 + f, f11, 2.0F * f3);
        bRemove.setPosSize(f13, 2.0F * f3 + 6F * f3 + f, f11, 2.0F * f3);
        float f14 = (f1 - f11 - 4F * f3) / 2.0F;
        float f15 = f2 - f3 - f - 12F;
        lstAvailable.setPosSize(f1 - f3 - f14, f3 + f, f14, f15);
        lstSelected.setPosSize(f3, f3 + f, f14, f15);
    }

    public void setSelectedAircraft(java.lang.String s)
    {
        lstAvailable.lst.clear();
        lstSelected.lst.clear();
        selectedAircraft = getAircraft(s);
        if(selectedAircraft == null)
        {
            selectedAircraft = new ZutiAircraft();
            selectedAircraft.setAcName(s);
            lstSelected.lst.clear();
            wMaxAllowed.setValue("0");
            wUnlimitedPlanes.setChecked(true, true);
        } else
        {
            assignSelectedWeaponsToTable(selectedAircraft.getSelectedWeaponI18NNames());
            int i = selectedAircraft.getMaxAllowed();
            if(i == -1)
            {
                wMaxAllowed.setValue("0");
                wUnlimitedPlanes.setChecked(true, true);
            } else
            {
                wMaxAllowed.setValue((new Integer(i)).toString());
                wUnlimitedPlanes.setChecked(false, true);
            }
        }
        fillAvailableLoadouts();
        syncLists();
        selectedAircraft.setModifiedRuntime(false);
    }

    private void fillAvailableLoadouts()
    {
        lstAvailable.lst.clear();
        java.util.ArrayList arraylist = selectedAircraft.getWeaponI18NNames();
        if(arraylist != null)
        {
            for(int i = 0; i < arraylist.size(); i++)
                lstAvailable.lst.add(arraylist.get(i));

        }
        lstSelected.resized();
        lstAvailable.resized();
    }

    private void assignSelectedWeaponsToTable(java.util.ArrayList arraylist)
    {
        lstSelected.lst.clear();
        if(arraylist == null)
            return;
        for(int i = 0; i < arraylist.size(); i++)
            lstSelected.lst.add(arraylist.get(i));

    }

    private com.maddox.il2.game.ZutiAircraft getAircraft(java.lang.String s)
    {
        if(modifiedAircrafts == null)
            return null;
        for(int i = 0; i < modifiedAircrafts.size(); i++)
        {
            com.maddox.il2.game.ZutiAircraft zutiaircraft = (com.maddox.il2.game.ZutiAircraft)modifiedAircrafts.get(i);
            if(zutiaircraft.getAcName().equals(s))
                return zutiaircraft;
        }

        return null;
    }

    public void close(boolean flag)
    {
        super.close(flag);
        if(selectedAircraft != null && selectedAircraft.getModifiedRuntime())
        {
            if(modifiedAircrafts == null)
                modifiedAircrafts = new ArrayList();
            selectedAircraft.setSelectedWeapons(lstSelected.lst);
            if(wUnlimitedPlanes.isChecked())
                selectedAircraft.setMaxAllowed(-1);
            else
                selectedAircraft.setMaxAllowed((new Integer(wMaxAllowed.getValue())).intValue());
            if(!modifiedAircrafts.contains(selectedAircraft))
                modifiedAircrafts.add(selectedAircraft);
        }
    }

    private void addAllWeaponOptions()
    {
        lstSelected.lst.clear();
        java.util.ArrayList arraylist = selectedAircraft.getWeaponI18NNames();
        if(arraylist != null)
        {
            for(int i = 0; i < arraylist.size(); i++)
                lstSelected.lst.add(arraylist.get(i));

        }
        lstAvailable.lst.clear();
        lstSelected.resized();
        lstAvailable.resized();
    }

    private void syncLists()
    {
        for(int i = 0; i < lstSelected.lst.size(); i++)
            lstAvailable.lst.remove(lstSelected.lst.get(i));

    }

    public void setTitle(java.lang.String s)
    {
        title = com.maddox.il2.builder.Plugin.i18n("mds.zLoadouts.title") + " " + com.maddox.il2.builder.Plugin.i18n("mds.for") + " " + s;
    }

    public void clearArrays()
    {
        if(lstAvailable.lst != null)
            lstAvailable.lst.clear();
        if(lstSelected.lst != null)
            lstSelected.lst.clear();
        if(modifiedAircrafts != null)
            modifiedAircrafts.clear();
    }

    private com.maddox.il2.builder.Table lstSelected;
    private com.maddox.il2.builder.Table lstAvailable;
    private com.maddox.gwindow.GWindowEditControl wMaxAllowed;
    private com.maddox.gwindow.GWindowButton bAdd;
    private com.maddox.gwindow.GWindowButton bAddAll;
    private com.maddox.gwindow.GWindowButton bRemove;
    private com.maddox.gwindow.GWindowButton bRemoveAll;
    private com.maddox.gwindow.GWindowCheckBox wUnlimitedPlanes;
    public java.util.ArrayList modifiedAircrafts;
    public com.maddox.il2.game.ZutiAircraft selectedAircraft;






}

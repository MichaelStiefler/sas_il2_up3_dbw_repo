// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Zuti_WHomeBaseCountries.java

package com.maddox.il2.builder;

import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.Regiment;
import com.maddox.rts.LDRres;
import com.maddox.rts.RTSConf;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, Builder, ActorBorn, PlMission

public class Zuti_WHomeBaseCountries extends com.maddox.gwindow.GWindowFramed
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


    public Zuti_WHomeBaseCountries()
    {
        fullCountriesList = new ArrayList();
        mode = 0;
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
        title = com.maddox.il2.builder.Plugin.i18n("mds.zCountries.title");
        clientWindow = create(new com.maddox.gwindow.GWindowDialogClient() {

            public void resized()
            {
                super.resized();
                setSizes(this);
            }

        }
);
        com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)clientWindow;
        lstSelected = new Table(gwindowdialogclient, com.maddox.il2.builder.Plugin.i18n("mds.zCountries.selected"), 1.0F, 3F, 15F, 20F);
        lstAvailable = new Table(gwindowdialogclient, com.maddox.il2.builder.Plugin.i18n("mds.zCountries.available"), 23F, 3F, 15F, 20F);
        gwindowdialogclient.addControl(bAddAll = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 17F, 3F, 5F, 2.0F, com.maddox.il2.builder.Plugin.i18n("bplace_addall"), null) {

            public boolean notify(int i, int j)
            {
                if(i != 2)
                {
                    return false;
                } else
                {
                    addAllCountries();
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
                    fillAvailableCountries();
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
                com.maddox.il2.builder.PlMission.setChanged();
                return true;
            }

        }
);
    }

    private void setSizes(com.maddox.gwindow.GWindow gwindow)
    {
        float f = gwindow.win.dx;
        float f1 = gwindow.win.dy;
        com.maddox.gwindow.GFont gfont = gwindow.root.textFonts[0];
        float f2 = gwindow.lAF().metric();
        com.maddox.gwindow.GSize gsize = new GSize();
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_addall"), gsize);
        float f3 = gsize.dx;
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_add"), gsize);
        float f4 = gsize.dx;
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_delall"), gsize);
        float f5 = gsize.dx;
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_del"), gsize);
        float f6 = gsize.dx;
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_planes"), gsize);
        float f7 = gsize.dx;
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_list"), gsize);
        float f8 = gsize.dx;
        float f9 = f3;
        if(f9 < f4)
            f9 = f4;
        if(f9 < f5)
            f9 = f5;
        if(f9 < f6)
            f9 = f6;
        float f10 = f2 + f9;
        f9 += f2 + 4F * f2 + f7 + 4F * f2 + f8 + 4F * f2;
        if(f < f9)
            f = f9;
        float f11 = 10F * f2 + 10F * f2 + 2.0F * f2;
        if(f1 < f11)
            f1 = f11;
        float f12 = (f - f10) / 2.0F;
        bAddAll.setPosSize(f12, f2, f10, 2.0F * f2);
        bAdd.setPosSize(f12, f2 + 2.0F * f2, f10, 2.0F * f2);
        bRemoveAll.setPosSize(f12, 2.0F * f2 + 4F * f2, f10, 2.0F * f2);
        bRemove.setPosSize(f12, 2.0F * f2 + 6F * f2, f10, 2.0F * f2);
        float f13 = (f - f10 - 4F * f2) / 2.0F;
        float f14 = f1 - f2 - 12F;
        lstAvailable.setPosSize(f - f2 - f13, f2, f13, f14);
        lstSelected.setPosSize(f2, f2, f13, f14);
    }

    public void setSelectedCountries(com.maddox.il2.builder.ActorBorn actorborn)
    {
        lstSelected.lst.clear();
        lstAvailable.lst.clear();
        selectedActorBorn = actorborn;
        if(fullCountriesList == null || fullCountriesList.size() < 1)
            fillCountries();
        fillAvailableCountries();
        switch(mode)
        {
        case 1: // '\001'
        case 2: // '\002'
        default:
            break;

        case 0: // '\0'
            if(selectedActorBorn.zutiHomeBaseCountries != null && selectedActorBorn.zutiHomeBaseCountries.size() > 0)
            {
                for(int i = 0; i < selectedActorBorn.zutiHomeBaseCountries.size(); i++)
                    try
                    {
                        java.lang.String s = (java.lang.String)selectedActorBorn.zutiHomeBaseCountries.get(i);
                        if(lstAvailable.lst.contains(s))
                            lstSelected.lst.add(s);
                    }
                    catch(java.lang.Exception exception) { }

            }
            break;
        }
        syncLists();
    }

    private void fillCountries()
    {
        java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle("i18n/country", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
        java.util.List list = com.maddox.il2.ai.Regiment.getAll();
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.Regiment regiment = (com.maddox.il2.ai.Regiment)list.get(j);
            java.lang.String s = resourcebundle.getString(regiment.branch());
            if(!fullCountriesList.contains(s))
                fullCountriesList.add(s);
        }

    }

    private void fillAvailableCountries()
    {
        lstAvailable.lst.clear();
        for(int i = 0; i < fullCountriesList.size(); i++)
            lstAvailable.lst.add(fullCountriesList.get(i));

        lstSelected.resized();
        lstAvailable.resized();
    }

    public void close(boolean flag)
    {
        super.close(flag);
        if(selectedActorBorn != null)
            switch(mode)
            {
            case 0: // '\0'
                if(selectedActorBorn.zutiHomeBaseCountries == null)
                    selectedActorBorn.zutiHomeBaseCountries = new ArrayList();
                selectedActorBorn.zutiHomeBaseCountries.clear();
                for(int i = 0; i < lstSelected.lst.size(); i++)
                    selectedActorBorn.zutiHomeBaseCountries.add(lstSelected.lst.get(i));

                break;
            }
    }

    private void addAllCountries()
    {
        lstSelected.lst.clear();
        for(int i = 0; i < fullCountriesList.size(); i++)
            lstSelected.lst.add(fullCountriesList.get(i));

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
        title = s;
    }

    public void clearArrays()
    {
        if(lstAvailable.lst != null)
            lstAvailable.lst.clear();
        if(lstSelected.lst != null)
            lstSelected.lst.clear();
    }

    public void setMode(int i)
    {
        mode = i;
    }

    private com.maddox.il2.builder.Table lstSelected;
    private com.maddox.il2.builder.Table lstAvailable;
    private com.maddox.gwindow.GWindowButton bAdd;
    private com.maddox.gwindow.GWindowButton bAddAll;
    private com.maddox.gwindow.GWindowButton bRemove;
    private com.maddox.gwindow.GWindowButton bRemoveAll;
    private java.util.ArrayList fullCountriesList;
    private com.maddox.il2.builder.ActorBorn selectedActorBorn;
    private int mode;





}

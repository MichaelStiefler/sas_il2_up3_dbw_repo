// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Zuti_WManageAircrafts.java

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
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.ZutiAircraft;
import com.maddox.rts.LDRres;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

// Referenced classes of package com.maddox.il2.builder:
//            Zuti_WAircraftLoadout, Plugin, PlMission, Builder, 
//            PlMisBorn

public class Zuti_WManageAircrafts extends com.maddox.gwindow.GWindowFramed
{
    static class Item
    {

        public java.lang.String name;

        public Item(java.lang.String s)
        {
            name = s;
        }
    }

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
                return com.maddox.il2.game.I18N.plane(s);
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


    public void windowShown()
    {
        super.windowShown();
    }

    public void windowHidden()
    {
        super.windowHidden();
    }

    public void created()
    {
        bAlwaysOnTop = true;
        super.created();
        title = com.maddox.il2.builder.Plugin.i18n("mds.zAircrafts.title");
        clientWindow = create(new com.maddox.gwindow.GWindowDialogClient() {

            public void resized()
            {
                super.resized();
                setAircraftSizes(this);
            }

        }
);
        com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)clientWindow;
        lstAvailable = new Table(gwindowdialogclient, com.maddox.il2.builder.Plugin.i18n("bplace_planes"), 1.0F, 1.0F, 6F, 10F);
        lstInReserve = new Table(gwindowdialogclient, com.maddox.il2.builder.Plugin.i18n("bplace_list"), 14F, 1.0F, 6F, 10F);
        gwindowdialogclient.addControl(bAddAll = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 8F, 1.0F, 5F, 2.0F, com.maddox.il2.builder.Plugin.i18n("bplace_addall"), null) {

            public boolean notify(int i, int j)
            {
                if(i != 2)
                {
                    return false;
                } else
                {
                    lstAvailable.lst.clear();
                    addAllAircraft(lstAvailable.lst);
                    fillTabAircraft();
                    return true;
                }
            }

        }
);
        gwindowdialogclient.addControl(bAdd = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 8F, 3F, 5F, 2.0F, com.maddox.il2.builder.Plugin.i18n("bplace_add"), null) {

            public boolean notify(int i, int j)
            {
                if(i != 2)
                    return false;
                int k = lstInReserve.selectRow;
                if(k < 0 || k >= lstInReserve.lst.size())
                {
                    return true;
                } else
                {
                    lstAvailable.lst.add(lstInReserve.lst.get(k));
                    fillTabAircraft();
                    return true;
                }
            }

        }
);
        gwindowdialogclient.addControl(bRemAll = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 8F, 6F, 5F, 2.0F, com.maddox.il2.builder.Plugin.i18n("bplace_delall"), null) {

            public boolean notify(int i, int j)
            {
                if(i != 2)
                {
                    return false;
                } else
                {
                    lstAvailable.lst.clear();
                    fillTabAircraft();
                    return true;
                }
            }

        }
);
        gwindowdialogclient.addControl(bRem = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 8F, 8F, 5F, 2.0F, com.maddox.il2.builder.Plugin.i18n("bplace_del"), null) {

            public boolean notify(int i, int j)
            {
                if(i != 2)
                    return false;
                int k = lstAvailable.selectRow;
                if(k < 0 || k >= lstAvailable.lst.size())
                {
                    return true;
                } else
                {
                    lstAvailable.lst.remove(k);
                    fillTabAircraft();
                    return true;
                }
            }

        }
);
        gwindowdialogclient.addControl(bModifyPlane = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 8F, 12F, 5F, 2.0F, com.maddox.il2.builder.Plugin.i18n("mds.aircraft.modify"), null) {

            public boolean notify(int i, int j)
            {
                if(i != 2)
                    return false;
                if(zutiCapturedAcLoadouts == null)
                    return false;
                if(!zutiCapturedAcLoadouts.isVisible())
                {
                    if(lstAvailable.selectRow < 0 || lstAvailable.selectRow >= lstAvailable.lst.size())
                    {
                        return true;
                    } else
                    {
                        zutiCapturedAcLoadouts.setSelectedAircraft((java.lang.String)lstAvailable.lst.get(lstAvailable.selectRow));
                        zutiCapturedAcLoadouts.setTitle((java.lang.String)lstAvailable.lst.get(lstAvailable.selectRow));
                        zutiCapturedAcLoadouts.showWindow();
                        return true;
                    }
                } else
                {
                    return true;
                }
            }

        }
);
        gwindowdialogclient.addLabel(lSeparate = new GWindowLabel(gwindowdialogclient, 3F, 12F, 12F, 1.6F, " " + com.maddox.il2.builder.Plugin.i18n("bplace_cats") + " ", null));
        bSeparate = new GWindowBoxSeparate(gwindowdialogclient, 1.0F, 12.5F, 27F, 8F);
        bSeparate.exclude = lSeparate;
        gwindowdialogclient.addLabel(lCountry = new GWindowLabel(gwindowdialogclient, 2.0F, 14F, 7F, 1.6F, com.maddox.il2.builder.Plugin.i18n("bplace_country"), null));
        gwindowdialogclient.addControl(cCountry = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 9F, 14F, 7F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle("i18n/country", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
                java.util.TreeMap treemap = new TreeMap();
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int i = 0; i < arraylist.size(); i++)
                {
                    java.lang.Class class1 = (java.lang.Class)arraylist.get(i);
                    if(!com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                        continue;
                    java.lang.String s1 = com.maddox.rts.Property.stringValue(class1, "originCountry", null);
                    if(s1 == null)
                        continue;
                    java.lang.String s3;
                    try
                    {
                        s3 = resourcebundle.getString(s1);
                    }
                    catch(java.lang.Exception exception)
                    {
                        continue;
                    }
                    treemap.put(s3, s1);
                }

                java.lang.String s;
                for(java.util.Iterator iterator = treemap.keySet().iterator(); iterator.hasNext(); add(s))
                {
                    s = (java.lang.String)iterator.next();
                    java.lang.String s2 = (java.lang.String)treemap.get(s);
                    com.maddox.il2.builder.Zuti_WManageAircrafts.lstCountry.add(s2);
                }

                if(com.maddox.il2.builder.Zuti_WManageAircrafts.lstCountry.size() > 0)
                    setSelected(0, true, false);
            }

        }
);
        gwindowdialogclient.addControl(bCountryAdd = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 17F, 14F, 5F, 1.6F, com.maddox.il2.builder.Plugin.i18n("bplace_add"), null) {

            public boolean notify(int i, int j)
            {
                if(i != 2)
                    return false;
                java.lang.String s = (java.lang.String)com.maddox.il2.builder.Zuti_WManageAircrafts.lstCountry.get(cCountry.getSelected());
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int k = 0; k < arraylist.size(); k++)
                {
                    java.lang.Class class1 = (java.lang.Class)arraylist.get(k);
                    if(!com.maddox.rts.Property.containsValue(class1, "cockpitClass") || !s.equals(com.maddox.rts.Property.stringValue(class1, "originCountry", null)))
                        continue;
                    java.lang.String s1 = com.maddox.rts.Property.stringValue(class1, "keyName");
                    if(!lstAvailable.lst.contains(s1))
                        lstAvailable.lst.add(s1);
                }

                fillTabAircraft();
                return true;
            }

        }
);
        gwindowdialogclient.addControl(bCountryRem = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 22F, 14F, 5F, 1.6F, com.maddox.il2.builder.Plugin.i18n("bplace_del"), null) {

            public boolean notify(int i, int j)
            {
                if(i != 2)
                    return false;
                java.lang.String s = (java.lang.String)com.maddox.il2.builder.Zuti_WManageAircrafts.lstCountry.get(cCountry.getSelected());
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int k = 0; k < arraylist.size(); k++)
                {
                    java.lang.Class class1 = (java.lang.Class)arraylist.get(k);
                    if(!com.maddox.rts.Property.containsValue(class1, "cockpitClass") || !s.equals(com.maddox.rts.Property.stringValue(class1, "originCountry", null)))
                        continue;
                    java.lang.String s1 = com.maddox.rts.Property.stringValue(class1, "keyName");
                    int l = lstAvailable.lst.indexOf(s1);
                    if(l >= 0)
                        lstAvailable.lst.remove(l);
                }

                fillTabAircraft();
                return true;
            }

        }
);
        gwindowdialogclient.addLabel(lYear = new GWindowLabel(gwindowdialogclient, 2.0F, 16F, 7F, 1.6F, com.maddox.il2.builder.Plugin.i18n("bplace_year"), null));
        gwindowdialogclient.addControl(cYear = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 9F, 16F, 7F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                java.util.TreeMap treemap = new TreeMap();
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int i = 0; i < arraylist.size(); i++)
                {
                    java.lang.Class class1 = (java.lang.Class)arraylist.get(i);
                    if(!com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                        continue;
                    float f = com.maddox.rts.Property.floatValue(class1, "yearService", 0.0F);
                    if(f != 0.0F)
                        treemap.put("" + (int)f, null);
                }

                java.lang.String s;
                for(java.util.Iterator iterator = treemap.keySet().iterator(); iterator.hasNext(); add(s))
                {
                    s = (java.lang.String)iterator.next();
                    com.maddox.il2.builder.Zuti_WManageAircrafts.lstYear.add(s);
                }

                if(com.maddox.il2.builder.Zuti_WManageAircrafts.lstYear.size() > 0)
                    setSelected(0, true, false);
            }

        }
);
        gwindowdialogclient.addControl(bYearAdd = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 17F, 16F, 5F, 1.6F, com.maddox.il2.builder.Plugin.i18n("bplace_add"), null) {

            public boolean notify(int i, int j)
            {
                if(i != 2)
                    return false;
                java.lang.String s = (java.lang.String)com.maddox.il2.builder.Zuti_WManageAircrafts.lstYear.get(cYear.getSelected());
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int k = 0; k < arraylist.size(); k++)
                {
                    java.lang.Class class1 = (java.lang.Class)arraylist.get(k);
                    if(!com.maddox.rts.Property.containsValue(class1, "cockpitClass") || !s.equals("" + (int)com.maddox.rts.Property.floatValue(class1, "yearService", 0.0F)))
                        continue;
                    java.lang.String s1 = com.maddox.rts.Property.stringValue(class1, "keyName");
                    if(!lstAvailable.lst.contains(s1))
                        lstAvailable.lst.add(s1);
                }

                fillTabAircraft();
                return true;
            }

        }
);
        gwindowdialogclient.addControl(bYearRem = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 22F, 16F, 5F, 1.6F, com.maddox.il2.builder.Plugin.i18n("bplace_del"), null) {

            public boolean notify(int i, int j)
            {
                if(i != 2)
                    return false;
                java.lang.String s = (java.lang.String)com.maddox.il2.builder.Zuti_WManageAircrafts.lstYear.get(cYear.getSelected());
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int k = 0; k < arraylist.size(); k++)
                {
                    java.lang.Class class1 = (java.lang.Class)arraylist.get(k);
                    if(!com.maddox.rts.Property.containsValue(class1, "cockpitClass") || !s.equals("" + (int)com.maddox.rts.Property.floatValue(class1, "yearService", 0.0F)))
                        continue;
                    java.lang.String s1 = com.maddox.rts.Property.stringValue(class1, "keyName");
                    int l = lstAvailable.lst.indexOf(s1);
                    if(l >= 0)
                        lstAvailable.lst.remove(l);
                }

                fillTabAircraft();
                return true;
            }

        }
);
        gwindowdialogclient.addLabel(lType = new GWindowLabel(gwindowdialogclient, 2.0F, 18F, 7F, 1.6F, com.maddox.il2.builder.Plugin.i18n("bplace_category"), null));
        gwindowdialogclient.addControl(cType = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 9F, 18F, 7F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                java.util.TreeMap treemap = new TreeMap();
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int i = 0; i < arraylist.size(); i++)
                {
                    java.lang.Class class1 = (java.lang.Class)arraylist.get(i);
                    if(!com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                        continue;
                    if((com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeStormovik != null ? com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeStormovik : (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeStormovik = com.maddox.il2.builder.Zuti_WManageAircrafts._mthclass$("com.maddox.il2.objects.air.TypeStormovik"))).isAssignableFrom(class1))
                        treemap.put(com.maddox.il2.builder.Plugin.i18n("bplace_sturm"), com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeStormovik != null ? ((java.lang.Object) (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeStormovik)) : ((java.lang.Object) (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeStormovik = com.maddox.il2.builder.Zuti_WManageAircrafts._mthclass$("com.maddox.il2.objects.air.TypeStormovik"))));
                    if((com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeFighter != null ? com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeFighter : (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeFighter = com.maddox.il2.builder.Zuti_WManageAircrafts._mthclass$("com.maddox.il2.objects.air.TypeFighter"))).isAssignableFrom(class1))
                        treemap.put(com.maddox.il2.builder.Plugin.i18n("bplace_fiter"), com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeFighter != null ? ((java.lang.Object) (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeFighter)) : ((java.lang.Object) (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeFighter = com.maddox.il2.builder.Zuti_WManageAircrafts._mthclass$("com.maddox.il2.objects.air.TypeFighter"))));
                    if((com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeBomber != null ? com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeBomber : (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeBomber = com.maddox.il2.builder.Zuti_WManageAircrafts._mthclass$("com.maddox.il2.objects.air.TypeBomber"))).isAssignableFrom(class1))
                        treemap.put(com.maddox.il2.builder.Plugin.i18n("bplace_bomber"), com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeBomber != null ? ((java.lang.Object) (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeBomber)) : ((java.lang.Object) (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeBomber = com.maddox.il2.builder.Zuti_WManageAircrafts._mthclass$("com.maddox.il2.objects.air.TypeBomber"))));
                    if((com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeScout != null ? com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeScout : (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeScout = com.maddox.il2.builder.Zuti_WManageAircrafts._mthclass$("com.maddox.il2.objects.air.TypeScout"))).isAssignableFrom(class1))
                        treemap.put(com.maddox.il2.builder.Plugin.i18n("bplace_recon"), com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeScout != null ? ((java.lang.Object) (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeScout)) : ((java.lang.Object) (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeScout = com.maddox.il2.builder.Zuti_WManageAircrafts._mthclass$("com.maddox.il2.objects.air.TypeScout"))));
                    if((com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeDiveBomber != null ? com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeDiveBomber : (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeDiveBomber = com.maddox.il2.builder.Zuti_WManageAircrafts._mthclass$("com.maddox.il2.objects.air.TypeDiveBomber"))).isAssignableFrom(class1))
                        treemap.put(com.maddox.il2.builder.Plugin.i18n("bplace_diver"), com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeDiveBomber != null ? ((java.lang.Object) (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeDiveBomber)) : ((java.lang.Object) (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeDiveBomber = com.maddox.il2.builder.Zuti_WManageAircrafts._mthclass$("com.maddox.il2.objects.air.TypeDiveBomber"))));
                    if((com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeSailPlane != null ? com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeSailPlane : (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeSailPlane = com.maddox.il2.builder.Zuti_WManageAircrafts._mthclass$("com.maddox.il2.objects.air.TypeSailPlane"))).isAssignableFrom(class1))
                        treemap.put(com.maddox.il2.builder.Plugin.i18n("bplace_sailer"), com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeSailPlane != null ? ((java.lang.Object) (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeSailPlane)) : ((java.lang.Object) (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$TypeSailPlane = com.maddox.il2.builder.Zuti_WManageAircrafts._mthclass$("com.maddox.il2.objects.air.TypeSailPlane"))));
                    if((com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$Scheme1 != null ? com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$Scheme1 : (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$Scheme1 = com.maddox.il2.builder.Zuti_WManageAircrafts._mthclass$("com.maddox.il2.objects.air.Scheme1"))).isAssignableFrom(class1))
                        treemap.put(com.maddox.il2.builder.Plugin.i18n("bplace_single"), com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$Scheme1 != null ? ((java.lang.Object) (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$Scheme1)) : ((java.lang.Object) (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$Scheme1 = com.maddox.il2.builder.Zuti_WManageAircrafts._mthclass$("com.maddox.il2.objects.air.Scheme1"))));
                    else
                        treemap.put(com.maddox.il2.builder.Plugin.i18n("bplace_multi"), null);
                }

                java.lang.String s;
                for(java.util.Iterator iterator = treemap.keySet().iterator(); iterator.hasNext(); add(s))
                {
                    s = (java.lang.String)iterator.next();
                    java.lang.Class class2 = (java.lang.Class)treemap.get(s);
                    com.maddox.il2.builder.PlMisBorn.lstType.add(class2);
                }

                if(com.maddox.il2.builder.PlMisBorn.lstType.size() > 0)
                    setSelected(0, true, false);
            }

        }
);
        gwindowdialogclient.addControl(bTypeAdd = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 17F, 18F, 5F, 1.6F, com.maddox.il2.builder.Plugin.i18n("bplace_add"), null) {

            public boolean notify(int i, int j)
            {
                if(i != 2)
                    return false;
                java.lang.Class class1 = (java.lang.Class)com.maddox.il2.builder.PlMisBorn.lstType.get(cType.getSelected());
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int k = 0; k < arraylist.size(); k++)
                {
                    java.lang.Class class2 = (java.lang.Class)arraylist.get(k);
                    if(!com.maddox.rts.Property.containsValue(class2, "cockpitClass") || (class1 != null ? !class1.isAssignableFrom(class2) : (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$Scheme1 != null ? com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$Scheme1 : (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$Scheme1 = com.maddox.il2.builder.Zuti_WManageAircrafts._mthclass$("com.maddox.il2.objects.air.Scheme1"))).isAssignableFrom(class2)))
                        continue;
                    java.lang.String s = com.maddox.rts.Property.stringValue(class2, "keyName");
                    if(!lstAvailable.lst.contains(s))
                        lstAvailable.lst.add(s);
                }

                fillTabAircraft();
                return true;
            }

        }
);
        gwindowdialogclient.addControl(bTypeRem = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 22F, 18F, 5F, 1.6F, com.maddox.il2.builder.Plugin.i18n("bplace_del"), null) {

            public boolean notify(int i, int j)
            {
                if(i != 2)
                    return false;
                java.lang.Class class1 = (java.lang.Class)com.maddox.il2.builder.PlMisBorn.lstType.get(cType.getSelected());
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int k = 0; k < arraylist.size(); k++)
                {
                    java.lang.Class class2 = (java.lang.Class)arraylist.get(k);
                    if(!com.maddox.rts.Property.containsValue(class2, "cockpitClass") || (class1 != null ? !class1.isAssignableFrom(class2) : (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$Scheme1 != null ? com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$Scheme1 : (com.maddox.il2.builder.Zuti_WManageAircrafts.class$com$maddox$il2$objects$air$Scheme1 = com.maddox.il2.builder.Zuti_WManageAircrafts._mthclass$("com.maddox.il2.objects.air.Scheme1"))).isAssignableFrom(class2)))
                        continue;
                    java.lang.String s = com.maddox.rts.Property.stringValue(class2, "keyName");
                    int l = lstAvailable.lst.indexOf(s);
                    if(l >= 0)
                        lstAvailable.lst.remove(l);
                }

                fillTabAircraft();
                return true;
            }

        }
);
    }

    private void fillTabAircraft()
    {
        int i = lstAvailable.selectRow;
        int j = lstInReserve.selectRow;
        lstAvailable.lst = airNames;
        lstInReserve.lst.clear();
        java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
        for(int k = 0; k < arraylist.size(); k++)
        {
            java.lang.Class class1 = (java.lang.Class)arraylist.get(k);
            if(!com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                continue;
            java.lang.String s = com.maddox.rts.Property.stringValue(class1, "keyName");
            if(!lstAvailable.lst.contains(s))
                lstInReserve.lst.add(s);
        }

        if(lstAvailable.lst.size() > 0)
        {
            lstAvailable.lst.clear();
            for(int l = 0; l < arraylist.size(); l++)
            {
                java.lang.Class class2 = (java.lang.Class)arraylist.get(l);
                if(!com.maddox.rts.Property.containsValue(class2, "cockpitClass"))
                    continue;
                java.lang.String s1 = com.maddox.rts.Property.stringValue(class2, "keyName");
                if(!lstInReserve.lst.contains(s1))
                    lstAvailable.lst.add(s1);
            }

        }
        if(i >= 0)
            if(lstAvailable.lst.size() > 0)
            {
                if(i >= lstAvailable.lst.size())
                    i = lstAvailable.lst.size() - 1;
            } else
            {
                i = -1;
            }
        lstAvailable.setSelect(i, 0);
        if(j >= 0)
            if(lstInReserve.lst.size() > 0)
            {
                if(j >= lstInReserve.lst.size())
                    j = lstInReserve.lst.size() - 1;
            } else
            {
                j = -1;
            }
        lstInReserve.setSelect(j, 0);
        lstAvailable.resized();
        lstInReserve.resized();
    }

    private void addAllAircraft(java.util.ArrayList arraylist)
    {
        java.util.ArrayList arraylist1 = com.maddox.il2.game.Main.cur().airClasses;
        for(int i = 0; i < arraylist1.size(); i++)
        {
            java.lang.Class class1 = (java.lang.Class)arraylist1.get(i);
            if(!com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                continue;
            java.lang.String s = com.maddox.rts.Property.stringValue(class1, "keyName");
            if(!arraylist.contains(s))
                arraylist.add(s);
        }

    }

    private void setAircraftSizes(com.maddox.gwindow.GWindow gwindow)
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
        bRemAll.setPosSize(f12, 2.0F * f2 + 4F * f2, f10, 2.0F * f2);
        bRem.setPosSize(f12, 2.0F * f2 + 6F * f2, f10, 2.0F * f2);
        bModifyPlane.setPosSize(f12, 2.0F * f2 + 10F * f2, f10, 2.0F * f2);
        float f13 = (f - f10 - 4F * f2) / 2.0F;
        float f14 = f1 - 6F * f2 - 2.0F * f2 - 3F * f2;
        lstAvailable.setPosSize(f2, f2, f13, f14);
        lstInReserve.setPosSize(f - f2 - f13, f2, f13, f14);
        gfont.size(" " + com.maddox.il2.builder.Plugin.i18n("bplace_cats") + " ", gsize);
        f13 = gsize.dx;
        float f15 = f2 + f14;
        lSeparate.setPosSize(2.0F * f2, f15, f13, 2.0F * f2);
        bSeparate.setPosSize(f2, f15 + f2, f - 2.0F * f2, f1 - f15 - 2.0F * f2);
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_country"), gsize);
        float f16 = gsize.dx;
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_year"), gsize);
        if(f16 < gsize.dx)
            f16 = gsize.dx;
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_category"), gsize);
        if(f16 < gsize.dx)
            f16 = gsize.dx;
        f10 = 2.0F * f2 + f4 + f6;
        f13 = f - f16 - f10 - 6F * f2;
        float f17 = gwindow.lAF().getComboH();
        lCountry.setPosSize(2.0F * f2, f15 + 2.0F * f2, f16, 2.0F * f2);
        cCountry.setPosSize(2.0F * f2 + f16 + f2, f15 + 2.0F * f2 + (2.0F * f2 - f17) / 2.0F, f13, f17);
        bCountryAdd.setPosSize(f - 4F * f2 - f6 - f4, f15 + 2.0F * f2, f2 + f4, 2.0F * f2);
        bCountryRem.setPosSize(f - 3F * f2 - f6, f15 + 2.0F * f2, f2 + f6, 2.0F * f2);
        lYear.setPosSize(2.0F * f2, f15 + 4F * f2, f16, 2.0F * f2);
        cYear.setPosSize(2.0F * f2 + f16 + f2, f15 + 4F * f2 + (2.0F * f2 - f17) / 2.0F, f13, f17);
        bYearAdd.setPosSize(f - 4F * f2 - f6 - f4, f15 + 4F * f2, f2 + f4, 2.0F * f2);
        bYearRem.setPosSize(f - 3F * f2 - f6, f15 + 4F * f2, f2 + f6, 2.0F * f2);
        lType.setPosSize(2.0F * f2, f15 + 6F * f2, f16, 2.0F * f2);
        cType.setPosSize(2.0F * f2 + f16 + f2, f15 + 6F * f2 + (2.0F * f2 - f17) / 2.0F, f13, f17);
        bTypeAdd.setPosSize(f - 4F * f2 - f6 - f4, f15 + 6F * f2, f2 + f4, 2.0F * f2);
        bTypeRem.setPosSize(f - 3F * f2 - f6, f15 + 6F * f2, f2 + f6, 2.0F * f2);
    }

    public void afterCreated()
    {
        super.afterCreated();
        resized();
        close(false);
    }

    public void close(boolean flag)
    {
        if(parentEditControl != null)
        {
            java.lang.StringBuffer stringbuffer = new StringBuffer();
            for(int i = 0; i < lstAvailable.lst.size(); i++)
            {
                stringbuffer.append(lstAvailable.lst.get(i));
                stringbuffer.append(" ");
            }

            parentEditControl.setValue(stringbuffer.toString());
            parentEditControl.notify(2, 0);
            syncLists();
            com.maddox.il2.builder.PlMission.setChanged();
        }
        super.close(flag);
    }

    public Zuti_WManageAircrafts()
    {
        airNames = null;
        parentEditControl = null;
        zutiCapturedAcLoadouts = null;
        doNew(com.maddox.il2.builder.Plugin.builder.clientWindow, 2.0F, 2.0F, 40F, 40F, true);
        bSizable = true;
    }

    public void setParentEditControl(com.maddox.gwindow.GWindowEditControl gwindoweditcontrol)
    {
        parentEditControl = gwindoweditcontrol;
        if(gwindoweditcontrol != null)
        {
            zutiParseCapturedBaseAircrafts(parentEditControl.getValue());
            fillTabAircraft();
        }
        if(airNames == null)
            airNames = new ArrayList();
    }

    public void clearAirNames()
    {
        airNames.clear();
        airNames = null;
    }

    private void zutiParseCapturedBaseAircrafts(java.lang.String s)
    {
        airNames = new ArrayList();
        int i = 0;
        if(s == null)
            return;
        do
        {
            i++;
            if(s.indexOf(" ") > 0)
            {
                java.lang.String s1 = s.substring(0, s.indexOf(" ")).trim();
                s = s.substring(s.indexOf(" ") + 1, s.length()).trim();
                if(s1.length() > 0)
                    airNames.add(s1);
                continue;
            }
            if(s.length() > 0)
                airNames.add(s);
            break;
        } while(i <= 1000);
    }

    public void setTitle(java.lang.String s)
    {
        title = s;
    }

    public void setAircraftLoadout(com.maddox.il2.builder.Zuti_WAircraftLoadout zuti_waircraftloadout)
    {
        zutiCapturedAcLoadouts = zuti_waircraftloadout;
        if(zutiCapturedAcLoadouts == null)
            zutiCapturedAcLoadouts = new Zuti_WAircraftLoadout();
    }

    public com.maddox.il2.builder.Zuti_WAircraftLoadout getAircraftLoadout()
    {
        return zutiCapturedAcLoadouts;
    }

    private void syncLists()
    {
        java.util.ArrayList arraylist = new ArrayList();
        if(zutiCapturedAcLoadouts == null)
            zutiCapturedAcLoadouts = new Zuti_WAircraftLoadout();
        if(zutiCapturedAcLoadouts.modifiedAircrafts == null)
            zutiCapturedAcLoadouts.modifiedAircrafts = new ArrayList();
        for(int i = 0; i < airNames.size(); i++)
        {
            java.lang.String s = (java.lang.String)airNames.get(i);
            boolean flag = false;
            int i1 = 0;
            do
            {
                if(i1 >= zutiCapturedAcLoadouts.modifiedAircrafts.size())
                    break;
                com.maddox.il2.game.ZutiAircraft zutiaircraft2 = (com.maddox.il2.game.ZutiAircraft)zutiCapturedAcLoadouts.modifiedAircrafts.get(i1);
                if(zutiaircraft2.getAcName().equals(s))
                {
                    flag = true;
                    break;
                }
                i1++;
            } while(true);
            if(!flag)
            {
                com.maddox.il2.game.ZutiAircraft zutiaircraft1 = new ZutiAircraft();
                zutiaircraft1.setAcName(s);
                zutiaircraft1.setMaxAllowed(0);
                java.util.ArrayList arraylist1 = new ArrayList();
                arraylist1.add("Default");
                zutiaircraft1.setSelectedWeapons(arraylist1);
                arraylist.add(zutiaircraft1);
            }
        }

        for(int j = 0; j < arraylist.size(); j++)
            zutiCapturedAcLoadouts.modifiedAircrafts.add(arraylist.get(j));

        arraylist.clear();
        for(int k = 0; k < zutiCapturedAcLoadouts.modifiedAircrafts.size(); k++)
        {
            com.maddox.il2.game.ZutiAircraft zutiaircraft = (com.maddox.il2.game.ZutiAircraft)zutiCapturedAcLoadouts.modifiedAircrafts.get(k);
            boolean flag1 = false;
            int j1 = 0;
            do
            {
                if(j1 >= airNames.size())
                    break;
                java.lang.String s1 = (java.lang.String)airNames.get(j1);
                if(zutiaircraft.getAcName().equals(s1))
                {
                    flag1 = true;
                    break;
                }
                j1++;
            } while(true);
            if(!flag1)
                arraylist.add(zutiaircraft);
        }

        for(int l = 0; l < arraylist.size(); l++)
            zutiCapturedAcLoadouts.modifiedAircrafts.remove(arraylist.get(l));

    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    com.maddox.il2.builder.Table lstAvailable;
    com.maddox.il2.builder.Table lstInReserve;
    com.maddox.gwindow.GWindowButton bAddAll;
    com.maddox.gwindow.GWindowButton bAdd;
    com.maddox.gwindow.GWindowButton bRemAll;
    com.maddox.gwindow.GWindowButton bRem;
    com.maddox.gwindow.GWindowButton bModifyPlane;
    com.maddox.gwindow.GWindowLabel lSeparate;
    com.maddox.gwindow.GWindowBoxSeparate bSeparate;
    com.maddox.gwindow.GWindowLabel lCountry;
    com.maddox.gwindow.GWindowComboControl cCountry;
    static java.util.ArrayList lstCountry = new ArrayList();
    com.maddox.gwindow.GWindowButton bCountryAdd;
    com.maddox.gwindow.GWindowButton bCountryRem;
    com.maddox.gwindow.GWindowLabel lYear;
    com.maddox.gwindow.GWindowComboControl cYear;
    static java.util.ArrayList lstYear = new ArrayList();
    com.maddox.gwindow.GWindowButton bYearAdd;
    com.maddox.gwindow.GWindowButton bYearRem;
    com.maddox.gwindow.GWindowLabel lType;
    com.maddox.gwindow.GWindowComboControl cType;
    static java.util.ArrayList lstType = new ArrayList();
    com.maddox.gwindow.GWindowButton bTypeAdd;
    com.maddox.gwindow.GWindowButton bTypeRem;
    private java.util.ArrayList airNames;
    private com.maddox.gwindow.GWindowEditControl parentEditControl;
    public com.maddox.il2.builder.Zuti_WAircraftLoadout zutiCapturedAcLoadouts;
    static java.lang.Class class$com$maddox$il2$objects$air$TypeStormovik; /* synthetic field */
    static java.lang.Class class$com$maddox$il2$objects$air$TypeFighter; /* synthetic field */
    static java.lang.Class class$com$maddox$il2$objects$air$TypeBomber; /* synthetic field */
    static java.lang.Class class$com$maddox$il2$objects$air$TypeScout; /* synthetic field */
    static java.lang.Class class$com$maddox$il2$objects$air$TypeDiveBomber; /* synthetic field */
    static java.lang.Class class$com$maddox$il2$objects$air$TypeSailPlane; /* synthetic field */
    static java.lang.Class class$com$maddox$il2$objects$air$Scheme1; /* synthetic field */




}

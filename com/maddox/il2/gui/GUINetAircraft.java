// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUINetAircraft.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.GameSpy;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetGunner;
import com.maddox.il2.objects.air.PaintScheme;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetConnect;
import com.maddox.rts.NetEnv;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.il2.gui:
//            GUINetClientGuard, GUIClient, GUIInfoMenu, GUIInfoName, 
//            GUILookAndFeel, GUIButton, GUIAirArming, GUIDialogClient, 
//            GUISeparate

public class GUINetAircraft extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bPrev)
            {
                if(com.maddox.il2.game.Main.cur().netServerParams.isMaster())
                    com.maddox.il2.game.Main.stateStack().change(45);
                else
                    com.maddox.il2.game.Main.stateStack().change(46);
                return true;
            }
            if(gwindow == bLoodout)
            {
                if(((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getPlace() == -1)
                    return true;
                if(com.maddox.il2.gui.GUINetAircraft.selectedCockpitNum() > 0)
                {
                    return true;
                } else
                {
                    com.maddox.il2.gui.GUIAirArming.stateId = 3;
                    com.maddox.il2.game.Main.stateStack().push(55);
                    return true;
                }
            }
            if(gwindow == bFly)
            {
                if(((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getPlace() == -1)
                {
                    return true;
                } else
                {
                    _doFly();
                    return true;
                }
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(624F), x1024(960F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(96F), y1024(656F), x1024(128F), y1024(48F), 0, i18n("netair.Brief"));
            draw(x1024(528F), y1024(656F), x1024(176F), y1024(48F), 2, i18n("netair.Arming"));
            draw(x1024(768F), y1024(656F), x1024(160F), y1024(48F), 2, i18n("netair.Fly"));
        }

        public void setPosSize()
        {
            set1024PosSize(0.0F, 32F, 1024F, 736F);
            wTable.set1024PosSize(32F, 32F, 960F, 560F);
            bPrev.setPosC(x1024(56F), y1024(680F));
            bLoodout.setPosC(x1024(744F), y1024(680F));
            bFly.setPosC(x1024(968F), y1024(680F));
        }

        public DialogClient()
        {
        }
    }

    public class Table extends com.maddox.gwindow.GWindowTable
    {

        public int countRows()
        {
            if(items == null)
                return 0;
            int i = items.size();
            com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
            if(netuser.getPlace() == -1)
                i++;
            java.util.List list = com.maddox.rts.NetEnv.hosts();
            for(int j = 0; j < list.size(); j++)
            {
                com.maddox.il2.net.NetUser netuser1 = (com.maddox.il2.net.NetUser)list.get(j);
                if(netuser1.getPlace() == -1)
                    i++;
            }

            return i;
        }

        public void renderCell(int i, int j, boolean flag, float f, float f1)
        {
            setCanvasFont(0);
            if(flag)
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, lookAndFeel().regionWhite);
            }
            if(flag)
                setCanvasColorWHITE();
            else
                setCanvasColorBLACK();
            com.maddox.il2.gui.Item item = null;
            if(i < items.size())
                item = (com.maddox.il2.gui.Item)items.get(i);
            java.lang.String s = null;
            int k = 1;
            switch(j)
            {
            case 0: // '\0'
                if(item != null)
                {
                    s = "" + item.indexInArmy;
                    if(item.reg.getArmy() == 1)
                        setCanvasColor(com.maddox.gwindow.GColor.Red);
                    else
                        setCanvasColor(com.maddox.gwindow.GColor.Blue);
                }
                break;

            case 1: // '\001'
                if(item != null)
                {
                    float f2 = f1;
                    setCanvasColorWHITE();
                    draw(0.0F, 0.0F, f2, f2, item.texture);
                    if(flag)
                        setCanvasColorWHITE();
                    else
                        setCanvasColorBLACK();
                    draw(1.5F * f2, 0.0F, f - 1.5F * f2, f1, 0, item.reg.shortInfo());
                }
                return;

            case 2: // '\002'
                if(item != null)
                {
                    k = 0;
                    s = com.maddox.il2.game.I18N.plane(item.keyName);
                }
                break;

            case 3: // '\003'
                if(item != null)
                    s = item.number;
                break;

            case 4: // '\004'
                if(item != null)
                    s = item.cocName;
                break;

            case 5: // '\005'
                k = 0;
                if(item != null)
                {
                    s = findPlayer(i);
                } else
                {
                    int l = i - items.size();
                    com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
                    if(netuser.getPlace() == -1)
                        if(l == 0)
                            s = netuser.uniqueName();
                        else
                            l--;
                    if(s == null)
                    {
                        java.util.List list = com.maddox.rts.NetEnv.hosts();
                        for(int i1 = 0; i1 < list.size(); i1++)
                        {
                            com.maddox.il2.net.NetUser netuser1 = (com.maddox.il2.net.NetUser)list.get(i1);
                            if(netuser1.getPlace() != -1)
                                continue;
                            if(l == 0)
                            {
                                s = netuser1.uniqueName();
                                break;
                            }
                            l--;
                        }

                    }
                }
                break;
            }
            if(s != null)
                draw(0.0F, 0.0F, f, f1, k, s);
        }

        public void setSelect(int i, int j)
        {
            if(serverPlace == -1 && items != null && i >= 0 && i < items.size())
            {
                ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).requestPlace(i);
                ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setPilot(null);
                ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setSkin(null);
                ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setNoseart(null);
                com.maddox.il2.gui.Item item = (com.maddox.il2.gui.Item)items.get(i);
                com.maddox.il2.ai.EventLog.onTryOccupied("" + item.wingName + item.iAircraft, (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host(), com.maddox.il2.objects.air.Aircraft.netCockpitAstatePilotIndx(item.clsAircraft, item.iCockpitNum));
            }
            super.setSelect(i, j);
        }

        public void afterCreated()
        {
            super.afterCreated();
            bColumnsSizable = true;
            bSelecting = true;
            bSelectRow = true;
            addColumn("N", null);
            addColumn(com.maddox.il2.game.I18N.gui("netair.Regiment"), null);
            addColumn(com.maddox.il2.game.I18N.gui("netair.Plane"), null);
            addColumn(com.maddox.il2.game.I18N.gui("netair.Number"), null);
            addColumn(com.maddox.il2.game.I18N.gui("netair.Position"), null);
            addColumn(com.maddox.il2.game.I18N.gui("netair.Player"), null);
            vSB.scroll = rowHeight(0);
            getColumn(0).setRelativeDx(1.0F);
            getColumn(1).setRelativeDx(4F);
            getColumn(2).setRelativeDx(4F);
            getColumn(3).setRelativeDx(4F);
            getColumn(4).setRelativeDx(4F);
            getColumn(5).setRelativeDx(4F);
            alignColumns();
            wClient.bNotify = true;
            resized();
        }

        public void resolutionChanged()
        {
            vSB.scroll = rowHeight(0);
            super.resolutionChanged();
        }

        public java.util.ArrayList items;

        public Table(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow, 0.0F, 0.0F, 100F, 100F);
            items = new ArrayList();
        }
    }

    public static class Item
    {

        public int indexInArmy;
        public int iSectWing;
        public int iAircraft;
        public int iCockpitNum;
        public java.lang.String cocName;
        public java.lang.String wingName;
        public java.lang.String keyName;
        public com.maddox.il2.ai.Regiment reg;
        public com.maddox.gwindow.GTexture texture;
        public java.lang.String number;
        public java.lang.Class clsAircraft;

        public Item()
        {
        }
    }


    public static com.maddox.il2.gui.Item getItem(int i)
    {
        if(i < 0)
            return null;
        com.maddox.il2.gui.GUINetAircraft guinetaircraft = (com.maddox.il2.gui.GUINetAircraft)com.maddox.il2.game.GameState.get(44);
        guinetaircraft.fillListItems();
        if(i >= guinetaircraft.wTable.items.size())
            return null;
        else
            return (com.maddox.il2.gui.Item)guinetaircraft.wTable.items.get(i);
    }

    protected static boolean isSelectedValid()
    {
        int i = ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getPlace();
        if(i < 0)
        {
            return false;
        } else
        {
            com.maddox.il2.gui.GUINetAircraft guinetaircraft = (com.maddox.il2.gui.GUINetAircraft)com.maddox.il2.game.GameState.get(44);
            guinetaircraft.fillListItems();
            return i < guinetaircraft.wTable.items.size();
        }
    }

    protected static int selectedCockpitNum()
    {
        int i = ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getPlace();
        if(i < 0)
        {
            return 0;
        } else
        {
            com.maddox.il2.gui.GUINetAircraft guinetaircraft = (com.maddox.il2.gui.GUINetAircraft)com.maddox.il2.game.GameState.get(44);
            guinetaircraft.fillListItems();
            com.maddox.il2.gui.Item item = (com.maddox.il2.gui.Item)guinetaircraft.wTable.items.get(i);
            return item.iCockpitNum;
        }
    }

    protected static java.lang.String selectedSpawnName()
    {
        int i = ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getPlace();
        if(i < 0)
        {
            return null;
        } else
        {
            com.maddox.il2.gui.GUINetAircraft guinetaircraft = (com.maddox.il2.gui.GUINetAircraft)com.maddox.il2.game.GameState.get(44);
            guinetaircraft.fillListItems();
            com.maddox.il2.gui.Item item = (com.maddox.il2.gui.Item)guinetaircraft.wTable.items.get(i);
            return item.wingName + item.iAircraft;
        }
    }

    protected static com.maddox.il2.ai.Regiment selectedRegiment()
    {
        int i = ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getPlace();
        if(i < 0)
        {
            return null;
        } else
        {
            com.maddox.il2.gui.GUINetAircraft guinetaircraft = (com.maddox.il2.gui.GUINetAircraft)com.maddox.il2.game.GameState.get(44);
            guinetaircraft.fillListItems();
            return ((com.maddox.il2.gui.Item)guinetaircraft.wTable.items.get(i)).reg;
        }
    }

    protected static java.lang.String selectedAircraftName()
    {
        return com.maddox.il2.game.I18N.plane(com.maddox.il2.gui.GUINetAircraft.selectedAircraftKeyName());
    }

    protected static java.lang.String selectedAircraftKeyName()
    {
        int i = ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getPlace();
        if(i < 0)
        {
            return null;
        } else
        {
            com.maddox.il2.gui.GUINetAircraft guinetaircraft = (com.maddox.il2.gui.GUINetAircraft)com.maddox.il2.game.GameState.get(44);
            guinetaircraft.fillListItems();
            return ((com.maddox.il2.gui.Item)guinetaircraft.wTable.items.get(i)).keyName;
        }
    }

    protected static int selectedAircraftNumInWing()
    {
        int i = ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getPlace();
        if(i < 0)
        {
            return 0;
        } else
        {
            com.maddox.il2.gui.GUINetAircraft guinetaircraft = (com.maddox.il2.gui.GUINetAircraft)com.maddox.il2.game.GameState.get(44);
            guinetaircraft.fillListItems();
            return ((com.maddox.il2.gui.Item)guinetaircraft.wTable.items.get(i)).iAircraft;
        }
    }

    protected static java.lang.String selectedWingName()
    {
        int i = ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getPlace();
        if(i < 0)
        {
            return null;
        } else
        {
            com.maddox.il2.gui.GUINetAircraft guinetaircraft = (com.maddox.il2.gui.GUINetAircraft)com.maddox.il2.game.GameState.get(44);
            guinetaircraft.fillListItems();
            return ((com.maddox.il2.gui.Item)guinetaircraft.wTable.items.get(i)).wingName;
        }
    }

    protected static java.lang.Class selectedAircraftClass()
    {
        int i = ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getPlace();
        if(i < 0)
        {
            return null;
        } else
        {
            com.maddox.il2.gui.GUINetAircraft guinetaircraft = (com.maddox.il2.gui.GUINetAircraft)com.maddox.il2.game.GameState.get(44);
            guinetaircraft.fillListItems();
            return ((com.maddox.il2.gui.Item)guinetaircraft.wTable.items.get(i)).clsAircraft;
        }
    }

    public static int serverPlace()
    {
        com.maddox.il2.gui.GUINetAircraft guinetaircraft = (com.maddox.il2.gui.GUINetAircraft)com.maddox.il2.game.GameState.get(44);
        guinetaircraft.fillListItems();
        if(guinetaircraft.sectMission == null)
            return -1;
        java.lang.String s = guinetaircraft.sectMission.get("MAIN", "player", (java.lang.String)null);
        if(s == null)
            return guinetaircraft.serverPlace = -1;
        int i = guinetaircraft.sectMission.get("MAIN", "playerNum", 0, 0, 3);
        int j = guinetaircraft.wTable.items.size();
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.gui.Item item = (com.maddox.il2.gui.Item)guinetaircraft.wTable.items.get(k);
            if(item.wingName.equals(s) && item.iAircraft == i)
                return guinetaircraft.serverPlace = k;
        }

        return guinetaircraft.serverPlace = -1;
    }

    private void fillListItems()
    {
        if(com.maddox.il2.game.Mission.cur() == null || com.maddox.il2.game.Mission.cur().isDestroyed())
        {
            sectMission = null;
            wTable.items.clear();
            return;
        }
        if(sectMission == com.maddox.il2.game.Mission.cur().sectFile())
        {
            return;
        } else
        {
            wTable.items.clear();
            wTable.setSelect(-1, -1);
            serverPlace = -1;
            com.maddox.rts.SectFile sectfile = com.maddox.il2.game.Mission.cur().sectFile();
            createListItems(sectfile, 1);
            createListItems(sectfile, 2);
            sectMission = sectfile;
            return;
        }
    }

    private void createListItems(com.maddox.rts.SectFile sectfile, int i)
    {
        int j = sectfile.sectionIndex("Wing");
        if(j < 0)
            return;
        int k = sectfile.vars(j);
        int l = 1;
        for(int i1 = 0; i1 < k; i1++)
        {
            java.lang.String s = sectfile.var(j, i1);
            if(sectfile.sectionIndex(s) < 0 || s.length() < 3)
                continue;
            com.maddox.il2.ai.Regiment regiment = (com.maddox.il2.ai.Regiment)com.maddox.il2.engine.Actor.getByName(s.substring(0, s.length() - 2));
            if(regiment.getArmy() != i)
                continue;
            java.lang.String s1 = sectfile.get(s, "Class", (java.lang.String)null);
            if(s1 == null)
                continue;
            java.lang.Class class1 = null;
            try
            {
                class1 = com.maddox.rts.ObjIO.classForName(s1);
            }
            catch(java.lang.Exception exception)
            {
                continue;
            }
            int j1 = sectfile.get(s, "StartTime", 0);
            if(j1 <= 0)
            {
                boolean flag = sectfile.get(s, "OnlyAI", 0, 0, 1) == 1;
                if(!flag)
                {
                    java.lang.Object obj = com.maddox.rts.Property.value(class1, "cockpitClass");
                    if(obj != null)
                    {
                        int k1 = 0;
                        java.lang.Class aclass[] = null;
                        if(obj instanceof java.lang.Class)
                        {
                            k1 = 1;
                        } else
                        {
                            aclass = (java.lang.Class[])obj;
                            k1 = aclass.length;
                        }
                        java.lang.String s2 = com.maddox.rts.Property.stringValue(class1, "keyName", null);
                        com.maddox.il2.objects.air.PaintScheme paintscheme = com.maddox.il2.objects.air.Aircraft.getPropertyPaintScheme(class1, regiment.country());
                        int l1 = s.charAt(s.length() - 2) - 48;
                        int i2 = s.charAt(s.length() - 1) - 48;
                        com.maddox.il2.objects.air.PaintScheme _tmp = paintscheme;
                        com.maddox.il2.engine.Mat mat = com.maddox.il2.objects.air.PaintScheme.makeMatGUI(regiment.name() + "GUI", regiment.fileNameTga(), 1.0F, 1.0F, 1.0F);
                        com.maddox.gwindow.GTexture gtexture = com.maddox.gwindow.GTexture.New(mat.Name());
                        int j2 = 0;
                        crewFunction[0] = 1;
                        for(int k2 = 1; k2 < crewFunction.length; k2++)
                            crewFunction[k2] = 7;

                        java.lang.String s3 = com.maddox.rts.Property.stringValue(class1, "FlightModel", null);
                        com.maddox.rts.SectFile sectfile1 = com.maddox.il2.fm.FlightModelMain.sectFile(s3);
                        j2 = sectfile1.get("Aircraft", "Crew", 1, 1, 20);
                        for(int k3 = 0; k3 < crewFunction.length; k3++)
                            crewFunction[k3] = sectfile1.get("Aircraft", "CrewFunction" + k3, crewFunction[k3], 1, com.maddox.il2.fm.AircraftState.astateHUDPilotHits.length);

                        int l2 = sectfile.get(s, "Planes", 1, 1, 4);
                        for(int i3 = 0; i3 < l2; i3++)
                        {
                            for(int j3 = 0; j3 < k1; j3++)
                                if(j3 <= 0 || !(com.maddox.il2.objects.air.CockpitPilot.class).isAssignableFrom(aclass[j3]))
                                {
                                    com.maddox.il2.gui.Item item = new Item();
                                    item.indexInArmy = l++;
                                    item.iSectWing = i1;
                                    item.iAircraft = i3;
                                    item.iCockpitNum = j3;
                                    item.wingName = s;
                                    item.keyName = s2;
                                    item.cocName = com.maddox.il2.fm.AircraftState.astateHUDPilotHits[1];
                                    if(aclass != null)
                                    {
                                        int l3 = com.maddox.rts.Property.intValue(aclass[j3], "astatePilotIndx", 0);
                                        if(l3 < j2)
                                        {
                                            l3 = crewFunction[l3];
                                            if(l3 < com.maddox.il2.fm.AircraftState.astateHUDPilotHits.length)
                                                item.cocName = com.maddox.il2.fm.AircraftState.astateHUDPilotHits[l3];
                                        }
                                    }
                                    item.cocName = com.maddox.il2.game.I18N.hud_log(item.cocName);
                                    item.reg = regiment;
                                    item.clsAircraft = class1;
                                    item.number = paintscheme.typedName(class1, regiment, l1, i2, i3);
                                    item.texture = gtexture;
                                    wTable.items.add(item);
                                }

                        }

                    }
                }
            }
        }

    }

    private java.lang.String findPlayer(int i)
    {
        com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
        if(netuser.getPlace() == i)
            return netuser.uniqueName();
        java.util.List list = com.maddox.rts.NetEnv.hosts();
        for(int j = 0; j < list.size(); j++)
        {
            com.maddox.il2.net.NetUser netuser1 = (com.maddox.il2.net.NetUser)list.get(j);
            if(netuser1.getPlace() == i)
                return netuser1.uniqueName();
        }

        return null;
    }

    public void _enter()
    {
        fillListItems();
        client.activateWindow();
        wTable.resized();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    protected static void doFly()
    {
        com.maddox.il2.gui.GUINetAircraft guinetaircraft = (com.maddox.il2.gui.GUINetAircraft)com.maddox.il2.game.GameState.get(44);
        guinetaircraft._doFly();
    }

    private void _doFly()
    {
        int i = ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getPlace();
        if(i < 0)
            return;
        fillListItems();
        com.maddox.il2.gui.Item item = (com.maddox.il2.gui.Item)wTable.items.get(i);
        if(item.iCockpitNum == 0)
        {
            com.maddox.il2.ai.UserCfg usercfg = com.maddox.il2.ai.World.cur().userCfg;
            int j = com.maddox.il2.game.Main.cur().currentMissionFile.get("MAIN", "WEAPONSCONSTANT", 0, 0, 1);
            if(j != 1)
            {
                boolean flag = usercfg.getWeapon(item.keyName) != null;
                if(flag)
                    flag = com.maddox.il2.objects.air.Aircraft.weaponsExist(item.clsAircraft, usercfg.getWeapon(item.keyName));
                if(!flag)
                {
                    com.maddox.il2.gui.GUIAirArming.stateId = 3;
                    com.maddox.il2.game.Main.stateStack().push(55);
                    return;
                }
            }
            com.maddox.il2.game.Main.cur().resetUser();
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).checkReplicateSkin(item.keyName);
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).checkReplicateNoseart(item.keyName);
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).checkReplicatePilot();
            java.lang.String s = usercfg.getWeapon(item.keyName);
            int k = (int)usercfg.fuel;
            if(j == 1)
            {
                s = com.maddox.il2.game.Main.cur().currentMissionFile.get(com.maddox.il2.gui.GUINetAircraft.selectedWingName(), "weapons", "default");
                k = com.maddox.il2.game.Main.cur().currentMissionFile.get(com.maddox.il2.gui.GUINetAircraft.selectedWingName(), "Fuel", 100, 0, 100);
            }
            try
            {
                com.maddox.rts.CmdEnv.top().exec("spawn " + item.clsAircraft.getName() + " PLAYER NAME net" + item.wingName + item.iAircraft + " WEAPONS " + s + " FUEL " + k + (usercfg.netNumberOn ? "" : " NUMBEROFF") + " OVR");
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
            com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
            if(!com.maddox.il2.engine.Actor.isValid(aircraft))
            {
                com.maddox.gwindow.GWindowMessageBox gwindowmessagebox = new GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, "Create aircraft", "Selected aircraft NOT created", 3, 0.0F);
                com.maddox.il2.gui.GUINetClientGuard guinetclientguard = (com.maddox.il2.gui.GUINetClientGuard)com.maddox.il2.game.Main.cur().netChannelListener;
                if(guinetclientguard != null)
                    guinetclientguard.curMessageBox = gwindowmessagebox;
                return;
            }
        } else
        {
            com.maddox.il2.game.Main.cur().resetUser();
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).checkReplicatePilot();
            com.maddox.il2.objects.air.NetGunner netgunner = new NetGunner("" + item.wingName + item.iAircraft, (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host(), 0, item.iCockpitNum);
        }
        ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).doWaitStartCoopMission();
        if(com.maddox.il2.game.Main.cur().netServerParams.isMaster())
        {
            com.maddox.rts.NetEnv.cur().connect.bindEnable(false);
            com.maddox.il2.game.Main.cur().netServerParams.USGSupdate();
            if(com.maddox.il2.game.Main.cur().netGameSpy != null)
                com.maddox.il2.game.Main.cur().netGameSpy.sendStatechanged();
            com.maddox.il2.game.Main.stateStack().change(45);
            com.maddox.il2.game.Main.stateStack().change(47);
        } else
        {
            com.maddox.il2.game.Main.stateStack().change(46);
            com.maddox.il2.game.Main.stateStack().change(48);
        }
    }

    protected void init(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("netair.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        wTable = new Table(dialogClient);
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bPrev = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bLoodout = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bFly = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public GUINetAircraft(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(44);
        serverPlace = -1;
        init(gwindowroot);
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton bLoodout;
    public com.maddox.il2.gui.GUIButton bPrev;
    public com.maddox.il2.gui.GUIButton bFly;
    public com.maddox.il2.gui.Table wTable;
    public com.maddox.rts.SectFile sectMission;
    public int serverPlace;
    private static int crewFunction[] = new int[20];



}

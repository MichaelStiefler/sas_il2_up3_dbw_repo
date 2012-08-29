// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIQuick.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.AirportCarrier;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.IniFile;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import com.maddox.util.SharedTokenizer;
import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintStream;
import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Random;

// Referenced classes of package com.maddox.il2.gui:
//            GUIQuickLoad, GUIQuickSave, GUIClient, GUIInfoMenu, 
//            GUIInfoName, GUILookAndFeel, GUIButton, GUIQuickStats, 
//            GUIAirArming, GUIDialogClient, GUISeparate

public class GUIQuick extends com.maddox.il2.game.GameState
{
    public class DirFilter
        implements java.io.FilenameFilter
    {

        public boolean accept(java.io.File file, java.lang.String s)
        {
            java.io.File file1 = new File(file, s);
            return file1.isDirectory();
        }

        public DirFilter()
        {
        }
    }

    public class IOState
    {

        public void save()
        {
            our = OUR;
            scramble = bScramble;
            situation = wSituation.getSelected();
            map = getMapName();
            target = wTarget.getSelected();
            defence = wDefence.getSelected();
            altitude = wAltitude.getValue();
            pos = wPos.getValue();
            weather = wWeather.getSelected();
            cldheight = wCldHeight.getValue();
            timeH = wTimeHour.getSelected();
            timeM = wTimeMins.getSelected();
            noneTARGET = wLevel.getSelected();
        }

        public void loaded()
        {
            OUR = our;
            wMap.setValue(com.maddox.il2.game.I18N.map(map));
            wSituation.setSelected(situation, true, false);
            wTarget.setSelected(target, true, false);
            wDefence.setSelected(defence, true, false);
            if(pos == null)
            {
                if(situation != 0)
                    pos = "700";
                else
                    pos = "0";
                cldheight = "2000";
                int i = java.lang.Integer.parseInt(altitude);
                altitude = wAltitude.get(i);
                scramble = false;
            }
            bScramble = scramble;
            wPos.setValue(pos);
            validateEditableComboControl(wPos);
            wAltitude.setValue(altitude);
            validateEditableComboControl(wAltitude);
            wCldHeight.setValue(cldheight);
            validateEditableComboControl(wCldHeight);
            wWeather.setSelected(weather, true, false);
            wTimeHour.setSelected(timeH, true, false);
            wTimeMins.setSelected(timeM, true, false);
            wLevel.setSelected(noneTARGET, true, false);
            if(target == 0)
                noneTarget = true;
            else
                noneTarget = false;
        }

        public boolean our;
        public boolean scramble;
        public int situation;
        public java.lang.String map;
        public int target;
        public int defence;
        public java.lang.String altitude;
        public java.lang.String pos;
        public int weather;
        public java.lang.String cldheight;
        public int timeH;
        public int timeM;
        public int noneTARGET;

        public IOState()
        {
        }
    }

    class WComboNum extends com.maddox.gwindow.GWindowComboControl
    {

        public boolean notify(int i, int j)
        {
            if(i == 2)
            {
                int k = getSelected();
                if(k < 0)
                {
                    return true;
                } else
                {
                    wing[indx].planes = indx == 0 ? k + 1 : k;
                    return true;
                }
            } else
            {
                return super.notify(i, j);
            }
        }

        private int indx;

        public WComboNum(int i, com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2)
        {
            super(gwindow, f, f1, f2);
            indx = i;
        }
    }

    class WComboSkill extends com.maddox.gwindow.GWindowComboControl
    {

        public boolean notify(int i, int j)
        {
            if(i == 2)
            {
                int k = getSelected();
                if(k < 0)
                {
                    return true;
                } else
                {
                    wing[indx].skill = k;
                    return true;
                }
            } else
            {
                return super.notify(i, j);
            }
        }

        private int indx;

        public WComboSkill(int i, com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2)
        {
            super(gwindow, f, f1, f2);
            indx = i;
        }
    }

    class WComboPlane extends com.maddox.gwindow.GWindowComboControl
    {

        public boolean notify(int i, int j)
        {
            if(i == 2)
            {
                int k = getSelected();
                if(k < 0)
                    return true;
                com.maddox.il2.gui.ItemAir itemair;
                if(indx == 0)
                    itemair = (com.maddox.il2.gui.ItemAir)(bPlaneArrestor ? playerPlaneC : playerPlane).get(k);
                else
                    itemair = (com.maddox.il2.gui.ItemAir)(bPlaneArrestor ? aiPlaneC : aiPlane).get(k);
                java.lang.String s = fillComboWeapon(dlg[indx].wLoadout, itemair, 0);
                wing[indx].setPlane(k);
                boolean flag = false;
                if(indx == 0)
                {
                    wing[indx].fromUserCfg();
                    java.lang.String as[] = com.maddox.il2.objects.air.Aircraft.getWeaponsRegistered(wing[indx].plane.clazz);
                    int l = 0;
                    do
                    {
                        if(l >= as.length)
                            break;
                        if(as[l].equals(wing[indx].weapon))
                        {
                            fillComboWeapon(dlg[indx].wLoadout, wing[indx].plane, l);
                            flag = true;
                            break;
                        }
                        l++;
                    } while(true);
                }
                if(!flag)
                    wing[indx].weapon = s;
                return true;
            } else
            {
                return super.notify(i, j);
            }
        }

        private int indx;

        public WComboPlane(int i, com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2)
        {
            super(gwindow, f, f1, f2);
            indx = i;
        }
    }

    class WComboLoadout extends com.maddox.gwindow.GWindowComboControl
    {

        public boolean notify(int i, int j)
        {
            if(i == 2)
            {
                int k = getSelected();
                if(k < 0)
                    return true;
                wing[indx].setWeapon(k);
                if(indx == 0)
                    wing[indx].toUserCfg();
                return true;
            } else
            {
                return super.notify(i, j);
            }
        }

        private int indx;

        public WComboLoadout(int i, com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2)
        {
            super(gwindow, f, f1, f2);
            indx = i;
        }
    }

    class WButtonArming extends com.maddox.il2.gui.GUIButton
    {

        public boolean notify(int i, int j)
        {
            if(i == 2)
            {
                if(wing[indx].planes == 0)
                {
                    return true;
                } else
                {
                    indxAirArming = indx;
                    wing[indx].toAirArming();
                    com.maddox.il2.gui.GUIAirArming.stateId = 4;
                    com.maddox.il2.game.Main.stateStack().push(55);
                    return true;
                }
            } else
            {
                return super.notify(i, j);
            }
        }

        private int indx;

        public WButtonArming(int i, com.maddox.gwindow.GWindow gwindow, com.maddox.gwindow.GTexture gtexture, float f, float f1, float f2, 
                float f3)
        {
            super(gwindow, gtexture, f, f1, f2, f3);
            indx = i;
        }
    }

    class ItemDlg
    {

        public com.maddox.il2.gui.WComboNum wNum;
        public com.maddox.il2.gui.WComboSkill wSkill;
        public com.maddox.il2.gui.WComboPlane wPlane;
        public com.maddox.il2.gui.WComboLoadout wLoadout;
        public com.maddox.il2.gui.WButtonArming bArming;

        ItemDlg()
        {
        }
    }

    public class ItemWing
    {

        public java.lang.String getPlane()
        {
            return plane.name;
        }

        public void setPlane(java.lang.String s)
        {
            java.util.ArrayList arraylist = null;
            if(bPlaneArrestor)
                arraylist = indx == 0 ? playerPlaneC : aiPlaneC;
            else
                arraylist = indx == 0 ? playerPlane : aiPlane;
            int i = 0;
            do
            {
                if(i >= arraylist.size())
                    break;
                plane = (com.maddox.il2.gui.ItemAir)arraylist.get(i);
                if(plane.name.equals(s))
                    break;
                i++;
            } while(true);
        }

        public void loaded()
        {
            dlg[indx].wNum.setSelected(indx == 0 ? planes - 1 : planes, true, false);
            dlg[indx].wSkill.setSelected(skill, true, false);
            java.util.ArrayList arraylist = null;
            if(bPlaneArrestor)
                arraylist = indx == 0 ? playerPlaneC : aiPlaneC;
            else
                arraylist = indx == 0 ? playerPlane : aiPlane;
            int i = 0;
            do
            {
                if(i >= arraylist.size())
                    break;
                if(plane == arraylist.get(i))
                {
                    dlg[indx].wPlane.setSelected(i, true, false);
                    break;
                }
                i++;
            } while(true);
            java.lang.String as[] = com.maddox.il2.objects.air.Aircraft.getWeaponsRegistered(plane.clazz);
            int j = 0;
            do
            {
                if(j >= as.length)
                    break;
                if(as[j].equals(weapon))
                {
                    fillComboWeapon(dlg[indx].wLoadout, plane, j);
                    break;
                }
                j++;
            } while(true);
            if(indx == 0)
                toUserCfg();
        }

        public void toUserCfg()
        {
            if(indx != 0)
            {
                return;
            } else
            {
                com.maddox.il2.ai.UserCfg usercfg = com.maddox.il2.ai.World.cur().userCfg;
                usercfg.setSkin(plane.name, skin[0]);
                usercfg.setNoseart(plane.name, noseart[0]);
                usercfg.netPilot = pilot[0];
                usercfg.setWeapon(plane.name, weapon);
                usercfg.netNumberOn = numberOn[0];
                return;
            }
        }

        public void fromUserCfg()
        {
            if(indx != 0)
            {
                return;
            } else
            {
                com.maddox.il2.ai.UserCfg usercfg = com.maddox.il2.ai.World.cur().userCfg;
                skin[0] = usercfg.getSkin(plane.name);
                noseart[0] = usercfg.getNoseart(plane.name);
                pilot[0] = usercfg.netPilot;
                weapon = usercfg.getWeapon(plane.name);
                numberOn[0] = usercfg.netNumberOn;
                return;
            }
        }

        public java.lang.String prepareWing(com.maddox.rts.SectFile sectfile)
        {
            java.lang.String s;
            if(indx < 8)
            {
                s = OUR ? r01 : g01;
                if(indx < 4)
                    s = s + "0" + iwing;
                else
                    s = s + "1" + iwing;
            } else
            {
                s = OUR ? g01 : r01;
                if(indx < 12)
                    s = s + "0" + iwing;
                else
                    s = s + "1" + iwing;
            }
            int i = sectfile.sectionIndex("Wing");
            if(planes == 0)
            {
                sectfile.lineRemove(i, sectfile.varIndex(i, s));
                sectfile.sectionRemove(sectfile.sectionIndex(s));
                sectfile.sectionRemove(sectfile.sectionIndex(s + "_Way"));
                return null;
            }
            java.lang.String s1 = null;
            int j;
            if(regiment != null)
            {
                if(indx > 3 && indx < 8 || indx > 11)
                    s1 = regiment + "1" + iwing;
                else
                    s1 = regiment + "0" + iwing;
                sectfile.lineRemove(i, sectfile.varIndex(i, s));
                sectfile.lineAdd(i, s1);
                sectfile.sectionRename(sectfile.sectionIndex(s + "_Way"), s1 + "_Way");
                sectfile.sectionRename(sectfile.sectionIndex(s), s1);
                j = sectfile.sectionIndex(s1);
            } else
            {
                j = sectfile.sectionIndex(s);
                s1 = s;
            }
            sectfile.sectionClear(j);
            sectfile.lineAdd(j, "Planes " + planes);
            sectfile.lineAdd(j, "Skill " + skill);
            sectfile.lineAdd(j, "Class " + plane.className);
            sectfile.lineAdd(j, "Fuel " + fuel);
            if(weapon != null)
                sectfile.lineAdd(j, "weapons " + weapon);
            else
                sectfile.lineAdd(j, "weapons default");
            for(int k = 0; k < planes; k++)
            {
                if(skin[k] != null)
                    sectfile.lineAdd(j, "skin" + k + " " + skin[k]);
                if(noseart[k] != null)
                    sectfile.lineAdd(j, "noseart" + k + " " + noseart[k]);
                if(pilot[k] != null)
                    sectfile.lineAdd(j, "pilot" + k + " " + pilot[k]);
                if(!numberOn[k])
                    sectfile.lineAdd(j, "numberOn" + k + " 0");
            }

            return s1;
        }

        public void prepereWay(com.maddox.rts.SectFile sectfile, java.lang.String as[], java.lang.String as1[])
        {
            int i = sectfile.sectionIndex(as1[indx] + "_Way");
            int j = sectfile.vars(i);
            for(int k = 0; k < j; k++)
            {
                com.maddox.util.SharedTokenizer.set(sectfile.line(i, k));
                java.lang.String s = com.maddox.util.SharedTokenizer.next("");
                java.lang.String s1 = s + " " + com.maddox.util.SharedTokenizer.next("") + " " + com.maddox.util.SharedTokenizer.next("") + " ";
                java.lang.String s2 = wAltitude.getValue();
                if(wSituation.getSelected() != 0)
                {
                    int l = 500;
                    try
                    {
                        l = java.lang.Integer.parseInt(wAltitude.getValue());
                    }
                    catch(java.lang.Exception exception) { }
                    if(wSituation.getSelected() == 1)
                    {
                        if(indx <= 7)
                            l += java.lang.Integer.parseInt(wPos.getValue());
                    } else
                    if(indx > 7)
                        l += java.lang.Integer.parseInt(wPos.getValue());
                    s2 = "" + l;
                }
                com.maddox.util.SharedTokenizer.next("");
                float f = (float)com.maddox.util.SharedTokenizer.next((plane.speedMin + plane.speedMax) / 2D, plane.speedMin, plane.speedMax);
                if("TAKEOFF".equals(s) || "LANDING".equals(s))
                {
                    s2 = "0";
                    f = 0.0F;
                }
                java.lang.String s3 = com.maddox.util.SharedTokenizer.next((java.lang.String)null);
                java.lang.String s4 = com.maddox.util.SharedTokenizer.next((java.lang.String)null);
                if(s3 != null && (com.maddox.il2.gui.GUIQuick.class$com$maddox$il2$objects$air$TypeTransport == null ? (com.maddox.il2.gui.GUIQuick.class$com$maddox$il2$objects$air$TypeTransport = com.maddox.il2.gui.GUIQuick._mthclass$("com.maddox.il2.objects.air.TypeTransport")) : com.maddox.il2.gui.GUIQuick.class$com$maddox$il2$objects$air$TypeTransport).isAssignableFrom(plane.clazz))
                    s3 = null;
                if(s3 != null)
                {
                    int i1 = 0;
                    do
                    {
                        if(i1 >= 8)
                            break;
                        if(s3.equals(as[i1]))
                        {
                            s3 = as1[i1];
                            break;
                        }
                        i1++;
                    } while(true);
                }
                if(s3 != null)
                {
                    if(s4 != null)
                        sectfile.line(i, k, s1 + s2 + " " + f + " " + s3 + " " + s4);
                    else
                        sectfile.line(i, k, s1 + s2 + " " + f + " " + s3);
                } else
                {
                    sectfile.line(i, k, s1 + s2 + " " + f);
                }
            }

        }

        public void setPlane(int i)
        {
            java.util.ArrayList arraylist = null;
            if(bPlaneArrestor)
                arraylist = indx == 0 ? playerPlaneC : aiPlaneC;
            else
                arraylist = indx == 0 ? playerPlane : aiPlane;
            plane = (com.maddox.il2.gui.ItemAir)arraylist.get(i);
            for(int j = 0; j < 4; j++)
            {
                skin[j] = null;
                noseart[j] = null;
            }

        }

        public void setWeapon(int i)
        {
            java.lang.String as[] = com.maddox.il2.objects.air.Aircraft.getWeaponsRegistered(plane.clazz);
            weapon = as[i];
        }

        public void toAirArming()
        {
            com.maddox.il2.gui.GUIAirArming guiairarming = (com.maddox.il2.gui.GUIAirArming)com.maddox.il2.game.GameState.get(55);
            guiairarming.quikPlayer = indx == 0;
            if(indx < 8)
                guiairarming.quikArmy = OUR ? 1 : 2;
            else
                guiairarming.quikArmy = OUR ? 2 : 1;
            guiairarming.quikPlanes = planes;
            guiairarming.quikPlane = plane.name;
            guiairarming.quikWeapon = weapon;
            guiairarming.quikCurPlane = 0;
            guiairarming.quikRegiment = regiment;
            guiairarming.quikWing = iwing;
            guiairarming.quikFuel = fuel;
            for(int i = 0; i < 4; i++)
            {
                guiairarming.quikSkin[i] = skin[i];
                guiairarming.quikNoseart[i] = noseart[i];
                guiairarming.quikPilot[i] = pilot[i];
                guiairarming.quikNumberOn[i] = numberOn[i];
            }

            guiairarming.quikListPlane.clear();
            java.util.ArrayList arraylist = null;
            if(bPlaneArrestor)
                arraylist = indx == 0 ? playerPlaneC : aiPlaneC;
            else
                arraylist = indx == 0 ? playerPlane : aiPlane;
            for(int j = 0; j < arraylist.size(); j++)
                guiairarming.quikListPlane.add(((com.maddox.il2.gui.ItemAir)arraylist.get(j)).clazz);

        }

        public void fromAirArming()
        {
            com.maddox.il2.gui.GUIAirArming guiairarming = (com.maddox.il2.gui.GUIAirArming)com.maddox.il2.game.GameState.get(55);
            com.maddox.il2.gui.ItemAir itemair = null;
            java.util.ArrayList arraylist = null;
            if(bPlaneArrestor)
                arraylist = indx == 0 ? playerPlaneC : aiPlaneC;
            else
                arraylist = indx == 0 ? playerPlane : aiPlane;
            int i = 0;
            do
            {
                if(i >= arraylist.size())
                    break;
                itemair = (com.maddox.il2.gui.ItemAir)arraylist.get(i);
                if(itemair.name.equals(guiairarming.quikPlane))
                    break;
                i++;
            } while(true);
            plane = itemair;
            weapon = guiairarming.quikWeapon;
            regiment = guiairarming.quikRegiment;
            fuel = guiairarming.quikFuel;
            for(int j = 0; j < 4; j++)
            {
                skin[j] = guiairarming.quikSkin[j];
                noseart[j] = guiairarming.quikNoseart[j];
                pilot[j] = guiairarming.quikPilot[j];
                numberOn[j] = guiairarming.quikNumberOn[j];
            }

            loaded();
        }

        public int indx;
        public int planes;
        public com.maddox.il2.gui.ItemAir plane;
        public java.lang.String weapon;
        public java.lang.String regiment;
        public int iwing;
        public java.lang.String skin[] = {
            null, null, null, null
        };
        public java.lang.String noseart[] = {
            null, null, null, null
        };
        public java.lang.String pilot[] = {
            null, null, null, null
        };
        public boolean numberOn[] = {
            true, true, true, true
        };
        public int fuel;
        public int skill;

        public ItemWing(int i)
        {
            indx = 0;
            planes = 0;
            plane = null;
            weapon = "default";
            regiment = null;
            iwing = 0;
            fuel = 100;
            skill = 1;
            if(i == 0)
                planes = 1;
            indx = i;
            iwing = i % 4;
            if(indx == 0)
                plane = (com.maddox.il2.gui.ItemAir)(bPlaneArrestor ? playerPlaneC : playerPlane).get(0);
            else
                plane = (com.maddox.il2.gui.ItemAir)(bPlaneArrestor ? aiPlaneC : aiPlane).get(0);
            if(indx <= 7)
                regiment = r01;
            else
                regiment = g01;
        }
    }

    static class ItemAir
    {

        public java.lang.String name;
        public java.lang.String className;
        public java.lang.Class clazz;
        public boolean bEnablePlayer;
        public double speedMin;
        public double speedMax;

        public ItemAir(java.lang.String s, java.lang.Class class1, java.lang.String s1)
        {
            speedMin = 200D;
            speedMax = 500D;
            name = s;
            clazz = class1;
            className = s1;
            bEnablePlayer = com.maddox.rts.Property.containsValue(class1, "cockpitClass");
            java.lang.String s2 = com.maddox.rts.Property.stringValue(class1, "FlightModel", null);
            if(s2 != null)
            {
                com.maddox.rts.SectFile sectfile = com.maddox.il2.fm.FlightModelMain.sectFile(s2);
                speedMin = sectfile.get("Params", "Vmin", (float)speedMin);
                speedMax = sectfile.get("Params", "VmaxH", (float)speedMax);
            }
        }
    }

    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            validateEditableComboControl(wCldHeight);
            validateEditableComboControl(wPos);
            validateEditableComboControl(wAltitude);
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bArmy)
            {
                if(OUR)
                    OUR = false;
                else
                    OUR = true;
                onArmyChange();
                if(r01.equals("usa01"))
                    com.maddox.il2.game.Main3D.menuMusicPlay(OUR ? "us" : "ja");
                else
                    com.maddox.il2.game.Main3D.menuMusicPlay(OUR ? "ru" : "de");
                for(int k = 0; k < 16; k++)
                    if(k <= 7)
                        wing[k].regiment = OUR ? r01 : g01;
                    else
                        wing[k].regiment = OUR ? g01 : r01;

                return true;
            }
            if(gwindow == wTarget)
            {
                if(wTarget.getSelected() == 4)
                    bScramble = true;
                else
                    bScramble = false;
                if(wTarget.getSelected() == 0)
                {
                    noneTarget = true;
                    wLevel.showWindow();
                } else
                {
                    noneTarget = false;
                    wLevel.hideWindow();
                }
                return true;
            }
            if(gwindow == bExit)
            {
                com.maddox.il2.gui.GUIQuick.setQMB(false);
                save(true);
                com.maddox.il2.game.Main.stateStack().pop();
                return true;
            }
            if(gwindow == bDiff)
            {
                com.maddox.il2.game.Main.stateStack().push(17);
                return true;
            }
            if(gwindow == bFly)
                if(bNoAvailableMissions)
                {
                    return true;
                } else
                {
                    validateEditableComboControl(wCldHeight);
                    validateEditableComboControl(wPos);
                    validateEditableComboControl(wAltitude);
                    startQuickMission();
                    return true;
                }
            if(gwindow == bReset)
            {
                setDefaultValues();
                return true;
            }
            if(gwindow == bStat)
            {
                com.maddox.il2.game.Main.stateStack().push(71);
                return true;
            }
            if(gwindow == bNext)
            {
                bFirst = false;
                showHide();
                return true;
            }
            if(gwindow == bBack)
            {
                bFirst = true;
                showHide();
                return true;
            }
            if(gwindow == bLoad)
            {
                ssect = null;
                com.maddox.il2.game.Main.stateStack().push(25);
                return true;
            }
            if(gwindow == bSave)
            {
                validateEditableComboControl(wCldHeight);
                validateEditableComboControl(wPos);
                validateEditableComboControl(wAltitude);
                save(false);
                com.maddox.il2.game.Main.stateStack().push(24);
                return true;
            }
            if(gwindow == wPlaneList)
            {
                com.maddox.rts.IniFile inifile = com.maddox.il2.engine.Config.cur.ini;
                inifile.set("QMB", "PlaneList", wPlaneList.getSelected());
                com.maddox.il2.gui.GUIQuick.setPlaneList(wPlaneList.getSelected());
                fillArrayPlanes();
                for(int l = 0; l < 16; l++)
                {
                    fillComboPlane(dlg[l].wPlane, l == 0);
                    int i1 = dlg[l].wPlane.getSelected();
                    if(i1 == 0)
                        dlg[l].wPlane.setSelected(1, false, false);
                    dlg[l].wPlane.setSelected(0, true, true);
                }

                return true;
            }
            if(gwindow == wMap)
            {
                onArmyChange();
                mapChanged();
                defaultRegiments();
                if(com.maddox.il2.game.Main.cur() != null)
                {
                    com.maddox.il2.game.Main.cur();
                    if(com.maddox.il2.game.Main.stateStack() != null)
                    {
                        com.maddox.il2.game.Main.cur();
                        if(com.maddox.il2.game.Main.stateStack().peek() != null)
                        {
                            com.maddox.il2.game.Main.cur();
                            if(com.maddox.il2.game.Main.stateStack().peek().id() == 14)
                                if(r01.equals("usa01"))
                                    com.maddox.il2.game.Main3D.menuMusicPlay(OUR ? "us" : "ja");
                                else
                                    com.maddox.il2.game.Main3D.menuMusicPlay(OUR ? "ru" : "de");
                        }
                    }
                }
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            setCanvasColorWHITE();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(48F), y1024(630F), x1024(924F), 2.5F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(150F), y1024(640F), 1.0F, x1024(80F));
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(720F), y1024(640F), 1.0F, x1024(80F));
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(875F), y1024(640F), 1.0F, x1024(80F));
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(457F), y1024(640F), 1.0F, x1024(46F));
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(567F), y1024(640F), 1.0F, x1024(46F));
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(457F), y1024(686F), x1024(30F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(537F), y1024(686F), x1024(30F), 2.0F);
            setCanvasFont(0);
            draw(x1024(0.0F), y1024(633F), x1024(170F), M(2.0F), 1, Localize("quick.BAC"));
            draw(x1024(143F), y1024(633F), x1024(170F), M(2.0F), 1, Localize("quick.LOD"));
            draw(x1024(285F), y1024(633F), x1024(170F), M(2.0F), 1, Localize("quick.SAV"));
            if(!bNoAvailableMissions)
                draw(x1024(427F), y1024(633F), x1024(170F), M(2.0F), 1, Localize("quick.FLY"));
            draw(x1024(569F), y1024(633F), x1024(170F), M(2.0F), 1, Localize("quick.DIF"));
            if(bFirst)
            {
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(48F), y1024(500F), x1024(924F), 2.0F);
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(48F), y1024(285F), x1024(924F), 2.0F);
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(864F), y1024(130F), x1024(94F), 2.0F);
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(864F), y1024(317F), x1024(94F), 2.0F);
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(958F), y1024(110F), 2.0F, x1024(56F));
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(958F), y1024(317F), 2.0F, x1024(28F));
                draw(x1024(710F), y1024(633F), x1024(170F), M(2.0F), 1, Localize("quick.RES"));
                draw(x1024(853F), y1024(633F), x1024(170F), M(2.0F), 1, Localize("quick.GFC"));
                setCanvasFont(1);
                draw(x1024(38F), y1024(13F), x1024(602F), M(2.0F), 0, Localize("quick.YOU"));
                draw(x1024(38F), y1024(108F), x1024(602F), M(2.0F), 0, Localize("quick.FRI"));
                draw(x1024(38F), y1024(291F), x1024(602F), M(2.0F), 0, Localize("quick.ENM"));
                setCanvasFont(0);
                draw(x1024(48F), y1024(38F), x1024(82F), M(2.0F), 0, Localize("quick.NUM"));
                draw(x1024(144F), y1024(38F), x1024(160F), M(2.0F), 0, Localize("quick.SKI"));
                draw(x1024(318F), y1024(38F), x1024(274F), M(2.0F), 0, Localize("quick.PLA"));
                draw(x1024(606F), y1024(38F), x1024(196F), M(2.0F), 0, Localize("quick.TNT"));
                draw(x1024(48F), y1024(143F), x1024(82F), M(2.0F), 0, Localize("quick.NUM"));
                draw(x1024(144F), y1024(143F), x1024(160F), M(2.0F), 0, Localize("quick.SKI"));
                draw(x1024(318F), y1024(143F), x1024(274F), M(2.0F), 0, Localize("quick.PLA"));
                draw(x1024(606F), y1024(143F), x1024(196F), M(2.0F), 0, Localize("quick.TNT"));
                draw(x1024(48F), y1024(320F), x1024(82F), M(2.0F), 0, Localize("quick.NUM"));
                draw(x1024(144F), y1024(320F), x1024(160F), M(2.0F), 0, Localize("quick.SKI"));
                draw(x1024(318F), y1024(320F), x1024(274F), M(2.0F), 0, Localize("quick.PLA"));
                draw(x1024(606F), y1024(320F), x1024(196F), M(2.0F), 0, Localize("quick.TNT"));
                draw(x1024(606F), y1024(508F), x1024(192F), M(2.0F), 0, Localize("quick.MAP"));
                draw(x1024(606F), y1024(542F), x1024(192F), M(2.0F), 0, Localize("quick.PLALST"));
                draw(x1024(318F), y1024(508F), x1024(100F), M(2.0F), 0, Localize("quick.ALT"));
                draw(x1024(318F), y1024(542F), x1024(100F), M(2.0F), 0, Localize("quick.SIT"));
                draw(x1024(318F), y1024(576F), x1024(100F), M(2.0F), 0, Localize("quick.POS"));
                draw(x1024(48F), y1024(508F), x1024(100F), M(2.0F), 0, Localize("quick.TAR"));
                draw(x1024(48F), y1024(576F), x1024(100F), M(2.0F), 0, Localize("quick.DEF"));
                if(noneTarget)
                    draw(x1024(48F), y1024(542F), x1024(100F), M(2.0F), 0, Localize("quick.+/-"));
                draw(x1024(320F), y1024(118F), x1024(528F), y1024(32F), 2, Localize("quick.ASET"));
                draw(x1024(320F), y1024(298F), x1024(528F), y1024(32F), 2, Localize("quick.ASET"));
                setCanvasFont(0);
                if(OUR)
                    draw(x1024(566F), y1024(21F), x1024(362F), M(2.0F), 2, Localize("quick.SEL_Allies"));
                else
                    draw(x1024(566F), y1024(21F), x1024(362F), M(2.0F), 2, Localize("quick.SEL_Axis"));
            } else
            {
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(48F), y1024(500F), x1024(924F), 2.0F);
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(48F), y1024(215F), x1024(924F), 2.0F);
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(864F), y1024(288F), x1024(94F), 2.0F);
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(958F), y1024(232F), 2.0F, x1024(112F));
                setCanvasFont(0);
                draw(x1024(318F), y1024(508F), x1024(120F), M(2.0F), 0, Localize("quick.WEA"));
                draw(x1024(606F), y1024(508F), x1024(120F), M(2.0F), 0, Localize("quick.CLD"));
                draw(x1024(48F), y1024(508F), x1024(100F), M(2.0F), 0, Localize("quick.TIM"));
                draw(x1024(213F), y1024(508F), x1024(8F), M(2.0F), 1, ":");
                draw(x1024(853F), y1024(633F), x1024(170F), M(2.0F), 1, Localize("quick.STAT"));
                draw(x1024(48F), y1024(37F), x1024(82F), M(2.0F), 0, Localize("quick.NUM"));
                draw(x1024(144F), y1024(37F), x1024(160F), M(2.0F), 0, Localize("quick.SKI"));
                draw(x1024(318F), y1024(37F), x1024(274F), M(2.0F), 0, Localize("quick.PLA"));
                draw(x1024(606F), y1024(37F), x1024(196F), M(2.0F), 0, Localize("quick.TNT"));
                draw(x1024(48F), y1024(320F), x1024(82F), M(2.0F), 0, Localize("quick.NUM"));
                draw(x1024(144F), y1024(320F), x1024(160F), M(2.0F), 0, Localize("quick.SKI"));
                draw(x1024(318F), y1024(320F), x1024(274F), M(2.0F), 0, Localize("quick.PLA"));
                draw(x1024(606F), y1024(320F), x1024(196F), M(2.0F), 0, Localize("quick.TNT"));
                draw(x1024(320F), y1024(274F), x1024(528F), y1024(32F), 2, Localize("quick.ASET"));
                setCanvasFont(1);
                draw(x1024(38F), y1024(13F), x1024(602F), M(2.0F), 0, Localize("quick.ADDFRI"));
                draw(x1024(38F), y1024(291F), x1024(602F), M(2.0F), 0, Localize("quick.ADDENM"));
            }
        }

        public void setPosSize()
        {
            set1024PosSize(0.0F, 32F, 1024F, 736F);
            bExit.setPosC(x1024(85F), y1024(689F));
            bBack.setPosC(x1024(85F), y1024(689F));
            bLoad.setPosC(x1024(228F), y1024(689F));
            bSave.setPosC(x1024(370F), y1024(689F));
            bFly.setPosC(x1024(512F), y1024(689F));
            bStat.setPosC(x1024(939F), y1024(689F));
            bReset.setPosC(x1024(796F), y1024(689F));
            bArmy.setPosC(x1024(966F), y1024(39F));
            bNext.setPosC(x1024(939F), y1024(689F));
            bDiff.setPosC(x1024(654F), y1024(689F));
            wAltitude.setPosSize(x1024(432F), y1024(508F), x1024(160F), M(1.7F));
            wSituation.setPosSize(x1024(432F), y1024(542F), x1024(160F), M(1.7F));
            wPos.setPosSize(x1024(432F), y1024(576F), x1024(160F), M(1.7F));
            wTarget.setPosSize(x1024(142F), y1024(508F), x1024(160F), M(1.7F));
            wLevel.setPosSize(x1024(142F), y1024(542F), x1024(160F), M(1.7F));
            wDefence.setPosSize(x1024(142F), y1024(576F), x1024(160F), M(1.7F));
            wMap.setPosSize(x1024(688F), y1024(508F), x1024(256F), M(1.7F));
            wPlaneList.setPosSize(x1024(688F), y1024(542F), x1024(256F), M(1.7F));
            wWeather.setPosSize(x1024(432F), y1024(508F), x1024(160F), M(1.7F));
            wCldHeight.setPosSize(x1024(784F), y1024(508F), x1024(160F), M(1.7F));
            wTimeHour.setPosSize(x1024(132F), y1024(508F), x1024(80F), M(1.7F));
            wTimeMins.setPosSize(x1024(222F), y1024(508F), x1024(80F), M(1.7F));
            dlg[0].wNum.setPosSize(x1024(48F), y1024(70F), x1024(80F), M(1.7F));
            dlg[0].wSkill.setPosSize(x1024(144F), y1024(70F), x1024(160F), M(1.7F));
            dlg[0].wPlane.setPosSize(x1024(318F), y1024(70F), x1024(274F), M(1.7F));
            dlg[0].wLoadout.setPosSize(x1024(609F), y1024(70F), x1024(332F), M(1.7F));
            dlg[0].bArming.setPosC(x1024(959F), y1024(84F));
            for(int i = 0; i < 7; i++)
                if(i < 3)
                {
                    dlg[i + 1].wNum.setPosSize(x1024(48F), y1024(175 + 34 * i), x1024(80F), M(1.7F));
                    dlg[i + 1].wSkill.setPosSize(x1024(144F), y1024(175 + 34 * i), x1024(160F), M(1.7F));
                    dlg[i + 1].wPlane.setPosSize(x1024(318F), y1024(175 + 34 * i), x1024(274F), M(1.7F));
                    dlg[i + 1].wLoadout.setPosSize(x1024(609F), y1024(175 + 34 * i), x1024(332F), M(1.7F));
                    dlg[i + 1].bArming.setPosC(x1024(959F), y1024((175 + 34 * i + 16) - 2));
                } else
                {
                    dlg[i + 1].wNum.setPosSize(x1024(48F), y1024(104 + 34 * (i - 4)), x1024(80F), M(1.7F));
                    dlg[i + 1].wSkill.setPosSize(x1024(144F), y1024(104 + 34 * (i - 4)), x1024(160F), M(1.7F));
                    dlg[i + 1].wPlane.setPosSize(x1024(318F), y1024(104 + 34 * (i - 4)), x1024(274F), M(1.7F));
                    dlg[i + 1].wLoadout.setPosSize(x1024(609F), y1024(104 + 34 * (i - 4)), x1024(332F), M(1.7F));
                    dlg[i + 1].bArming.setPosC(x1024(959F), y1024((104 + 34 * (i - 4) + 16) - 2));
                }

            for(int j = 0; j < 8; j++)
                if(j < 4)
                {
                    dlg[j + 8].wNum.setPosSize(x1024(48F), y1024(352 + 34 * j), x1024(80F), M(1.7F));
                    dlg[j + 8].wSkill.setPosSize(x1024(144F), y1024(352 + 34 * j), x1024(160F), M(1.7F));
                    dlg[j + 8].wPlane.setPosSize(x1024(318F), y1024(352 + 34 * j), x1024(274F), M(1.7F));
                    dlg[j + 8].wLoadout.setPosSize(x1024(609F), y1024(352 + 34 * j), x1024(332F), M(1.7F));
                    dlg[j + 8].bArming.setPosC(x1024(959F), y1024((352 + 34 * j + 16) - 2));
                } else
                {
                    dlg[j + 8].wNum.setPosSize(x1024(48F), y1024(352 + 34 * (j - 4)), x1024(80F), M(1.7F));
                    dlg[j + 8].wSkill.setPosSize(x1024(144F), y1024(352 + 34 * (j - 4)), x1024(160F), M(1.7F));
                    dlg[j + 8].wPlane.setPosSize(x1024(318F), y1024(352 + 34 * (j - 4)), x1024(274F), M(1.7F));
                    dlg[j + 8].wLoadout.setPosSize(x1024(609F), y1024(352 + 34 * (j - 4)), x1024(332F), M(1.7F));
                    dlg[j + 8].bArming.setPosC(x1024(959F), y1024((352 + 34 * (j - 4) + 16) - 2));
                }

        }

        public DialogClient()
        {
        }
    }

    public class byI18N_name
        implements java.util.Comparator
    {

        public int compare(java.lang.Object obj, java.lang.Object obj1)
        {
            if(com.maddox.rts.RTSConf.cur.locale.getLanguage().equals("ru"))
            {
                return collator.compare(com.maddox.il2.game.I18N.plane(((com.maddox.il2.gui.ItemAir)obj).name), com.maddox.il2.game.I18N.plane(((com.maddox.il2.gui.ItemAir)obj1).name));
            } else
            {
                java.text.Collator collator1 = java.text.Collator.getInstance(com.maddox.rts.RTSConf.cur.locale);
                collator1.setStrength(1);
                collator1.setDecomposition(2);
                return collator1.compare(com.maddox.il2.game.I18N.plane(((com.maddox.il2.gui.ItemAir)obj).name), com.maddox.il2.game.I18N.plane(((com.maddox.il2.gui.ItemAir)obj1).name));
            }
        }

        public byI18N_name()
        {
        }
    }


    private void defaultRegiments()
    {
        r01 = "r01";
        r010 = "r010";
        g01 = "g01";
        g010 = "g010";
        try
        {
            com.maddox.rts.SectFile sectfile = new SectFile(currentMissionName, 0);
            if(sectfile.sectionIndex("r0100") < 0)
            {
                r01 = "usa01";
                r010 = "usa010";
                g01 = "ja01";
                g010 = "ja010";
            }
            for(int i = 0; i < 8; i++)
                if(OUR)
                {
                    wing[i].regiment = r01;
                    wing[i + 8].regiment = g01;
                } else
                {
                    wing[i].regiment = g01;
                    wing[i + 8].regiment = r01;
                }

        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("WARNING: No quick missions in Missions folder.");
        }
    }

    private void mapChanged()
    {
        int i = wMap.getSelected();
        if(i >= 0 && i < _mapKey.length && !bNoAvailableMissions)
        {
            java.util.ArrayList arraylist = getAvailableMissionsS(getMapName(), getMapName() + getArmyString() + _targetKey[wTarget.getSelected()] + (wTarget.getSelected() != 0 ? "" : noneTargetSuffix[wLevel.getSelected()]));
            java.util.Random random = new Random();
            int j = random.nextInt(arraylist.size());
            java.lang.String s = "Missions/Quick/" + getMapName() + "/" + (java.lang.String)arraylist.get(j);
            currentMissionName = s;
            com.maddox.rts.SectFile sectfile = new SectFile(currentMissionName, 0);
            if(sectfile.sectionIndex("r0100") >= 0)
            {
                r01 = "r01";
                g01 = "g01";
            } else
            {
                r01 = "usa01";
                g01 = "ja01";
            }
            java.lang.String s1 = sectfile.get("MAIN", "MAP", (java.lang.String)null);
            if(s1 != null)
            {
                com.maddox.rts.IniFile inifile = new IniFile("maps/" + s1, 0);
                java.lang.String s2 = inifile.get("WORLDPOS", "CAMOUFLAGE", "SUMMER");
                if(com.maddox.il2.ai.World.cur() != null)
                    com.maddox.il2.ai.World.cur().setCamouflage(s2);
            }
        }
    }

    public void enterPush(com.maddox.il2.game.GameState gamestate)
    {
        com.maddox.il2.ai.World.cur().diffUser.set(com.maddox.il2.ai.World.cur().userCfg.singleDifficulty);
        if(com.maddox.il2.gui.GUIQuick.exisstFile("quicks/.last.quick"))
        {
            com.maddox.il2.gui.GUIQuickLoad guiquickload = (com.maddox.il2.gui.GUIQuickLoad)com.maddox.il2.game.GameState.get(25);
            guiquickload.execute(".last", true);
            load();
        }
        wing[0].fromUserCfg();
        mapChanged();
        if(r01.equals("usa01"))
            com.maddox.il2.game.Main3D.menuMusicPlay(OUR ? "us" : "ja");
        else
            com.maddox.il2.game.Main3D.menuMusicPlay(OUR ? "ru" : "de");
        _enter();
    }

    public void enterPop(com.maddox.il2.game.GameState gamestate)
    {
        if(gamestate.id() == 17)
        {
            com.maddox.il2.ai.World.cur().userCfg.singleDifficulty = com.maddox.il2.ai.World.cur().diffUser.get();
            com.maddox.il2.ai.World.cur().userCfg.saveConf();
        } else
        if(gamestate.id() == 55)
            wing[indxAirArming].fromAirArming();
        else
        if(gamestate.id() == 25)
        {
            wPlaneList.setSelected(0, true, false);
            com.maddox.il2.gui.GUIQuick.setPlaneList(wPlaneList.getSelected());
            fillArrayPlanes();
            for(int i = 0; i < 16; i++)
            {
                fillComboPlane(dlg[i].wPlane, i == 0);
                int j = dlg[i].wPlane.getSelected();
                if(j == 0)
                    dlg[i].wPlane.setSelected(1, false, false);
                dlg[i].wPlane.setSelected(0, true, true);
            }

            load();
        }
        _enter();
    }

    public void _enter()
    {
        com.maddox.il2.game.Main.cur().currentMissionFile = null;
        com.maddox.il2.gui.GUIQuick.setQMB(true);
        java.lang.String s = "users/" + com.maddox.il2.ai.World.cur().userCfg.sId + "/QMB.ini";
        if(!com.maddox.il2.gui.GUIQuick.exisstFile(s))
            com.maddox.il2.gui.GUIQuick.initStat();
        client.activateWindow();
    }

    public void _leave()
    {
        com.maddox.il2.ai.World.cur().userCfg.saveConf();
        client.hideWindow();
    }

    private static int getPlaneList()
    {
        return pl;
    }

    private static void setPlaneList(int i)
    {
        pl = i;
    }

    static boolean isQMB()
    {
        return bIsQuick;
    }

    static void setQMB(boolean flag)
    {
        bIsQuick = flag;
    }

    static void initStat()
    {
        java.lang.String s = "users/" + com.maddox.il2.ai.World.cur().userCfg.sId + "/QMB.ini";
        com.maddox.rts.SectFile sectfile = new SectFile(s, 1, false, com.maddox.il2.ai.World.cur().userCfg.krypto());
        java.lang.String s1 = "MAIN";
        sectfile.sectionAdd(s1);
        int i = sectfile.sectionIndex(s1);
        float f = 0.0F;
        int j = 0;
        sectfile.lineAdd(i, "qmbTotalScore", "" + j);
        sectfile.lineAdd(i, "qmbTotalAirKill", "" + j);
        sectfile.lineAdd(i, "qmbTotalGroundKill", "" + j);
        sectfile.lineAdd(i, "qmbTotalBulletsFired", "" + j);
        sectfile.lineAdd(i, "qmbTotalBulletsHitAir", "" + j);
        sectfile.lineAdd(i, "qmbTotalBulletsFiredHitGround", "" + j);
        sectfile.lineAdd(i, "qmbTotalPctAir", "" + f);
        sectfile.lineAdd(i, "qmbTotalPctGround", "" + f);
        sectfile.lineAdd(i, "qmbTotalTankKill", "" + j);
        sectfile.lineAdd(i, "qmbTotalCarKill", "" + j);
        sectfile.lineAdd(i, "qmbTotalArtilleryKill", "" + j);
        sectfile.lineAdd(i, "qmbTotalAAAKill", "" + j);
        sectfile.lineAdd(i, "qmbTotalTrainKill", "" + j);
        sectfile.lineAdd(i, "qmbTotalShipKill", "" + j);
        sectfile.lineAdd(i, "qmbTotalAirStaticKill", "" + j);
        sectfile.lineAdd(i, "qmbTotalBridgeKill", "" + j);
        sectfile.lineAdd(i, "qmbTotalPara", "" + j);
        sectfile.lineAdd(i, "qmbTotalDead", "" + j);
        sectfile.lineAdd(i, "qmbTotalBombsFired", "" + j);
        sectfile.lineAdd(i, "qmbTotalBombsHit", "" + j);
        sectfile.lineAdd(i, "qmbTotalRocketsFired", "" + j);
        sectfile.lineAdd(i, "qmbTotalRocketsHit", "" + j);
        sectfile.lineAdd(i, "qmbTotalPctBomb", "" + f);
        sectfile.lineAdd(i, "qmbTotalPctRocket", "" + f);
        sectfile.saveFile();
    }

    private static boolean exisstFile(java.lang.String s)
    {
        try
        {
            com.maddox.rts.SFSInputStream sfsinputstream = new SFSInputStream(s);
            sfsinputstream.close();
        }
        catch(java.lang.Exception exception)
        {
            return false;
        }
        return true;
    }

    private void validateEditableComboControl(com.maddox.gwindow.GWindowComboControl gwindowcombocontrol)
    {
        java.lang.String s = gwindowcombocontrol.getValue();
        if(s.equals(""))
        {
            gwindowcombocontrol.setSelected(0, true, false);
            s = gwindowcombocontrol.get(0);
        }
        int i = java.lang.Integer.parseInt(s);
        if(i < java.lang.Integer.parseInt(gwindowcombocontrol.get(0)))
        {
            gwindowcombocontrol.setSelected(0, true, false);
            i = java.lang.Integer.parseInt(gwindowcombocontrol.getValue());
        }
        if(i > java.lang.Integer.parseInt(gwindowcombocontrol.get(gwindowcombocontrol.size() - 1)))
        {
            gwindowcombocontrol.setSelected(gwindowcombocontrol.size() - 1, true, false);
            i = java.lang.Integer.parseInt(gwindowcombocontrol.getValue());
        }
        for(int j = 0; j < gwindowcombocontrol.size(); j++)
            if(i == java.lang.Integer.parseInt(gwindowcombocontrol.get(j)))
                gwindowcombocontrol.setSelected(j, true, false);

    }

    public void startQuickMission()
    {
        java.util.ArrayList arraylist = getAvailableMissionsS(getMapName(), getMapName() + getArmyString() + _targetKey[wTarget.getSelected()] + (wTarget.getSelected() != 0 ? "" : noneTargetSuffix[wLevel.getSelected()]));
        java.util.Random random = new Random();
        int i = random.nextInt(arraylist.size());
        java.lang.String s = "Missions/Quick/" + getMapName() + "/" + (java.lang.String)arraylist.get(i);
        try
        {
            com.maddox.rts.SectFile sectfile = new SectFile(s, 0);
            sectfile.set("MAIN", "TIME", wTimeHour.getValue() + "." + wTimeMins.getSelected() * 25);
            for(int j = 0; j < 8; j++)
            {
                java.lang.String s1;
                if(j < 4)
                    s1 = r010 + java.lang.Character.forDigit(j, 10);
                else
                    s1 = r01 + "1" + java.lang.Character.forDigit(j - 4, 10);
                if(!sectfile.exist("Wing", s1))
                    throw new Exception("Section " + s1 + " not found");
            }

            for(int k = 0; k < 8; k++)
            {
                java.lang.String s2;
                if(k < 4)
                    s2 = g010 + java.lang.Character.forDigit(k, 10);
                else
                    s2 = g01 + "1" + java.lang.Character.forDigit(k - 4, 10);
                if(!sectfile.exist("Wing", s2))
                    throw new Exception("Section " + s2 + " not found");
            }

            sectfile.set("MAIN", "CloudType", wWeather.getSelected());
            sectfile.set("MAIN", "CloudHeight", java.lang.Integer.parseInt(wCldHeight.getValue()));
            java.lang.String as[] = new java.lang.String[16];
            java.lang.String as1[] = new java.lang.String[16];
            for(int l = 0; l < 16; l++)
            {
                if(l < 8)
                    as[l] = (OUR ? r01 : g01) + (l >= 4 ? "1" : "0") + l;
                else
                    as[l] = (OUR ? g01 : r01) + (l >= 12 ? "1" : "0") + l;
                as1[l] = wing[l].prepareWing(sectfile);
            }

            if(as1[0] != null)
                sectfile.set("MAIN", "player", as1[0]);
            else
                sectfile.set("MAIN", "player", as[0]);
            for(int i1 = 0; i1 < 16; i1++)
                if(as1[i1] != null)
                    wing[i1].prepereWay(sectfile, as, as1);

            if(wDefence.getSelected() == 0)
            {
                for(int j1 = 0; j1 < 2; j1++)
                {
                    java.lang.String s3 = j1 == 0 ? "Stationary" : "NStationary";
                    int i2 = sectfile.sectionIndex(s3);
                    if(i2 < 0)
                        continue;
                    sectfile.sectionRename(i2, "Stationary_Temp");
                    sectfile.sectionAdd(s3);
                    int k2 = sectfile.sectionIndex(s3);
                    int i3 = sectfile.vars(i2);
                    for(int j3 = 0; j3 < i3; j3++)
                    {
                        com.maddox.util.SharedTokenizer.set(sectfile.line(i2, j3));
                        java.lang.String s6 = null;
                        if(j1 == 1)
                            s6 = com.maddox.util.SharedTokenizer.next("");
                        java.lang.String s8 = com.maddox.util.SharedTokenizer.next("");
                        int l3 = com.maddox.util.SharedTokenizer.next(0);
                        java.lang.String s10 = null;
                        if(s6 != null)
                            s10 = s6 + " " + s8 + " " + l3 + " " + com.maddox.util.SharedTokenizer.getGap();
                        else
                            s10 = s8 + " " + l3 + " " + com.maddox.util.SharedTokenizer.getGap();
                        if(l3 == 0)
                        {
                            sectfile.lineAdd(k2, s10);
                            continue;
                        }
                        if(l3 == 1 && OUR && !bScramble)
                        {
                            sectfile.lineAdd(k2, s10);
                            continue;
                        }
                        if(l3 == 2 && !OUR && !bScramble)
                        {
                            sectfile.lineAdd(k2, s10);
                            continue;
                        }
                        try
                        {
                            java.lang.Class class1 = com.maddox.rts.ObjIO.classForName(s8);
                            if((com.maddox.il2.objects.vehicles.artillery.AAA.class).isAssignableFrom(class1))
                                continue;
                            if(s8.startsWith("ships."))
                            {
                                com.maddox.util.SharedTokenizer.set(sectfile.line(i2, j3));
                                if(j1 == 1)
                                    s6 = com.maddox.util.SharedTokenizer.next("");
                                java.lang.String s12 = com.maddox.util.SharedTokenizer.next("");
                                java.lang.String s13 = com.maddox.util.SharedTokenizer.next("");
                                java.lang.String s14 = com.maddox.util.SharedTokenizer.next("");
                                java.lang.String s15 = com.maddox.util.SharedTokenizer.next("");
                                java.lang.String s16 = com.maddox.util.SharedTokenizer.next("");
                                java.lang.String s17 = com.maddox.util.SharedTokenizer.next("");
                                java.lang.String s18 = com.maddox.util.SharedTokenizer.next("");
                                java.lang.String s19 = com.maddox.util.SharedTokenizer.next("");
                                if(j1 == 1)
                                    sectfile.lineAdd(k2, s6 + " " + s12 + " " + s13 + " " + s14 + " " + s15 + " " + s16 + " " + s17 + " " + 5940 + " " + s19);
                                else
                                    sectfile.lineAdd(k2, s12 + " " + s13 + " " + s14 + " " + s15 + " " + s16 + " " + s17 + " " + 5940 + " " + s19);
                            } else
                            {
                                sectfile.lineAdd(k2, s10);
                            }
                        }
                        catch(java.lang.Throwable throwable) { }
                    }

                    sectfile.sectionRemove(i2);
                }

                int k1 = sectfile.sectionIndex("Chiefs");
                if(k1 >= 0)
                {
                    sectfile.sectionRename(k1, "Chiefs_Temp");
                    sectfile.sectionAdd("Chiefs");
                    int l1 = sectfile.sectionIndex("Chiefs");
                    int j2 = sectfile.vars(k1);
                    for(int l2 = 0; l2 < j2; l2++)
                    {
                        java.lang.String s4 = sectfile.line(k1, l2);
                        com.maddox.util.SharedTokenizer.set(s4);
                        java.lang.String s5 = com.maddox.util.SharedTokenizer.next("");
                        java.lang.String s7 = com.maddox.util.SharedTokenizer.next("");
                        if(s7.startsWith("Ships."))
                        {
                            int k3 = com.maddox.util.SharedTokenizer.next(0);
                            if(k3 == 0)
                            {
                                sectfile.lineAdd(l1, s4);
                                continue;
                            }
                            if(k3 == 1 && OUR && !bScramble)
                            {
                                sectfile.lineAdd(l1, s4);
                                continue;
                            }
                            if(k3 == 2 && !OUR && !bScramble)
                            {
                                sectfile.lineAdd(l1, s4);
                            } else
                            {
                                java.lang.String s9 = com.maddox.util.SharedTokenizer.next("");
                                java.lang.String s11 = com.maddox.util.SharedTokenizer.next("");
                                sectfile.lineAdd(l1, s5 + " " + s7 + " " + k3 + " " + 5940 + " " + s11);
                            }
                        } else
                        {
                            sectfile.lineAdd(l1, s4);
                        }
                    }

                    sectfile.sectionRemove(k1);
                }
            }
            com.maddox.il2.gui.GUIQuickStats.resetMissionStat();
            com.maddox.il2.game.Main.cur().currentMissionFile = sectfile;
            com.maddox.il2.game.Main.stateStack().push(5);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
            java.lang.System.out.println(">> no file: " + s + "");
            com.maddox.gwindow.GWindowMessageBox gwindowmessagebox = new GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, "Error", "Data file corrupt", 3, 0.0F);
            return;
        }
    }

    public void save(boolean flag)
    {
        try
        {
            ssect = new SectFile();
            ssect.sectionAdd("states");
            ioState.save();
            ssect.set("states", "head", ioState, false);
            for(int i = 0; i < 16; i++)
                ssect.set("states", "wing" + i, wing[i], false);

        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("sorry, cant save");
            com.maddox.gwindow.GWindowMessageBox gwindowmessagebox = new GWindowMessageBox(client, 20F, true, "Error", "Can't save data file", 3, 0.0F);
        }
        com.maddox.il2.gui.GUIQuickSave guiquicksave = (com.maddox.il2.gui.GUIQuickSave)com.maddox.il2.game.GameState.get(24);
        guiquicksave.sect = ssect;
        if(flag)
            guiquicksave.execute(".last", false);
    }

    public void load()
    {
        setDefaultValues();
        byte byte0 = 0;
        byte byte1 = 16;
        if(ssect == null)
            return;
        try
        {
            ssect.get("states", "head", ioState);
            if(ssect.get("states", "wing8", wing[8]) == null)
            {
                byte0 = 4;
                byte1 = 8;
            }
            for(int i = 0; i < byte1; i++)
            {
                byte byte2 = 0;
                if(byte0 > 0 && i > 3)
                    byte2 = 4;
                ssect.get("states", "wing" + i, wing[i + byte2]);
            }

        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("sorry, data corrupt");
            com.maddox.gwindow.GWindowMessageBox gwindowmessagebox = new GWindowMessageBox(client, 20F, true, "Error", "Data file corrupt", 3, 0.0F);
        }
        onArmyChange();
        setShow(bFirst && noneTarget, wLevel);
    }

    private boolean checkCustomAirIni(java.lang.String s)
    {
        com.maddox.rts.SectFile sectfile = new SectFile(s);
        if(sectfile.sections() <= 0)
            return false;
        com.maddox.rts.SectFile sectfile1 = new SectFile("com/maddox/il2/objects/air.ini");
        int i = sectfile.vars(0);
        for(int j = 0; j < i; j++)
            if(sectfile1.varExist(0, sectfile.var(0, j)))
                return true;

        return false;
    }

    public void fillArrayPlanes()
    {
        playerPlane.clear();
        playerPlaneC.clear();
        aiPlane.clear();
        aiPlaneC.clear();
        boolean flag = false;
        java.lang.String s;
        if(com.maddox.il2.gui.GUIQuick.getPlaneList() < 2)
        {
            s = "com/maddox/il2/objects/air.ini";
            if(com.maddox.il2.gui.GUIQuick.getPlaneList() == 1)
                flag = true;
        } else
        if(checkCustomAirIni("Missions/Quick/QMBair_" + com.maddox.il2.gui.GUIQuick.getPlaneList() + ".ini"))
        {
            s = "Missions/Quick/QMBair_" + com.maddox.il2.gui.GUIQuick.getPlaneList() + ".ini";
        } else
        {
            s = "com/maddox/il2/objects/air.ini";
            wPlaneList.setSelected(0, true, false);
        }
        com.maddox.rts.SectFile sectfile = new SectFile(s, 0);
        com.maddox.rts.SectFile sectfile1 = new SectFile("com/maddox/il2/objects/air.ini");
        boolean flag1 = false;
        int j = sectfile.sections();
        if(j <= 0)
            throw new RuntimeException("GUIQuick: file '" + s + "' is empty");
        for(int k = 0; k < j; k++)
        {
            int l = sectfile.vars(k);
            for(int i1 = 0; i1 < l; i1++)
            {
                java.lang.String s1 = sectfile.var(k, i1);
                if(!sectfile1.varExist(0, s1))
                    continue;
                int i = sectfile1.varIndex(0, s1);
                com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile1.value(k, i));
                java.lang.String s2 = numbertokenizer.next((java.lang.String)null);
                boolean flag2 = true;
                do
                {
                    if(!numbertokenizer.hasMoreTokens())
                        break;
                    if(!"NOQUICK".equals(numbertokenizer.next()))
                        continue;
                    flag2 = false;
                    break;
                } while(true);
                if(!flag2)
                    continue;
                java.lang.Class class1 = null;
                try
                {
                    class1 = com.maddox.rts.ObjIO.classForName(s2);
                }
                catch(java.lang.Exception exception)
                {
                    java.lang.System.out.println("GUIQuick: class '" + s2 + "' not found");
                    break;
                }
                com.maddox.il2.gui.ItemAir itemair = new ItemAir(s1, class1, s2);
                if(itemair.bEnablePlayer)
                {
                    playerPlane.add(itemair);
                    if(com.maddox.il2.ai.AirportCarrier.isPlaneContainsArrestor(class1))
                        playerPlaneC.add(itemair);
                }
                aiPlane.add(itemair);
                if(com.maddox.il2.ai.AirportCarrier.isPlaneContainsArrestor(class1))
                    aiPlaneC.add(itemair);
            }

        }

        if(flag)
        {
            java.util.Collections.sort(playerPlane, new byI18N_name());
            java.util.Collections.sort(playerPlaneC, new byI18N_name());
            java.util.Collections.sort(aiPlane, new byI18N_name());
            java.util.Collections.sort(aiPlaneC, new byI18N_name());
        }
    }

    public void fillComboPlane(com.maddox.gwindow.GWindowComboControl gwindowcombocontrol, boolean flag)
    {
        gwindowcombocontrol.clear();
        java.util.ArrayList arraylist = null;
        if(bPlaneArrestor)
            arraylist = flag ? playerPlaneC : aiPlaneC;
        else
            arraylist = flag ? playerPlane : aiPlane;
        int i = arraylist.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.gui.ItemAir itemair = (com.maddox.il2.gui.ItemAir)arraylist.get(j);
            gwindowcombocontrol.add(com.maddox.il2.game.I18N.plane(itemair.name));
        }

        gwindowcombocontrol.setSelected(0, true, false);
    }

    public java.lang.String fillComboWeapon(com.maddox.gwindow.GWindowComboControl gwindowcombocontrol, com.maddox.il2.gui.ItemAir itemair, int i)
    {
        gwindowcombocontrol.clear();
        java.lang.Class class1 = itemair.clazz;
        java.lang.String as[] = com.maddox.il2.objects.air.Aircraft.getWeaponsRegistered(class1);
        if(as != null && as.length > 0)
        {
            for(int j = 0; j < as.length; j++)
            {
                java.lang.String s = as[j];
                gwindowcombocontrol.add(com.maddox.il2.game.I18N.weapons(itemair.name, s));
            }

            gwindowcombocontrol.setSelected(i, true, false);
        }
        return as[i];
    }

    public java.lang.String Localize(java.lang.String s)
    {
        return com.maddox.il2.game.I18N.gui(s);
    }

    public GUIQuick(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(14);
        russianMixedRules = "< ',' < '.' < '-' <\u0430,\u0410< a,A <\u0431,\u0411< b,B <\u0432,\u0412< v,V <\u0433,\u0413< g,G <\u0434,\u0414< d,D <\u0435,\u0415 < \u0451,\u0401 < \u0436,\u0416 < \u0437,\u0417< z,Z <\u0438,\u0418< i,I <\u0439,\u0419< j,J <\u043A,\u041A< k,K <\u043B,\u041B< l,L <\u043C,\u041C< m,M <\u043D,\u041D< n,N <\u043E,\u041E< o,O <\u043F,\u041F< p,P <\u0440,\u0420< r,R <\u0441,\u0421< s,S <\u0442,\u0422< t,T <\u0443,\u0423< u,U <\u0444,\u0424< f,F <\u0445,\u0425< h,H <\u0446,\u0426< c,C <\u0447,\u0427 < \u0448,\u0428 < \u0449,\u0429 < \u044A,\u042A < \u044B,\u042B< i,I <\u044C,\u042C < \u044D,\u042D< e,E <\u044E,\u042E < \u044F,\u042F< q,Q < x,X < y,Y";
        currentMissionName = "";
        bNoAvailableMissions = false;
        try
        {
            collator = new RuleBasedCollator(russianMixedRules);
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
        bFirst = true;
        OUR = true;
        bScramble = false;
        noneTarget = true;
        pl = com.maddox.il2.engine.Config.cur.ini.get("QMB", "PlaneList", 0, 0, 3);
        if(com.maddox.il2.engine.Config.cur.ini.get("QMB", "DumpPlaneList", 0, 0, 1) > 0)
            dumpFullPlaneList();
        java.io.File file = new File("Missions/Quick/QMBair_" + pl + ".ini");
        if(!file.exists() && pl > 1)
            pl = 0;
        else
        if(!checkCustomAirIni("Missions/Quick/QMBair_" + pl + ".ini"))
            pl = 0;
        r01 = "r01";
        r010 = "r010";
        g01 = "g01";
        g010 = "g010";
        playerPlane = new ArrayList();
        aiPlane = new ArrayList();
        playerPlaneC = new ArrayList();
        aiPlaneC = new ArrayList();
        wing = new com.maddox.il2.gui.ItemWing[16];
        dlg = new com.maddox.il2.gui.ItemDlg[16];
        ioState = new IOState();
        bPlaneArrestor = false;
        fillArrayPlanes();
        for(int i = 0; i < 16; i++)
            wing[i] = new ItemWing(i);

        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = Localize("quick.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        com.maddox.gwindow.GTexture gtexture1 = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons;
        bArmy = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 0.0F, 48F, 48F));
        bReset = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bBack = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bLoad = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bSave = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bFly = (com.maddox.il2.gui.GUIButton)dialogClient.addDefault(new GUIButton(dialogClient, gtexture, 0.0F, 192F, 48F, 48F));
        bNext = (com.maddox.il2.gui.GUIButton)dialogClient.addDefault(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bStat = (com.maddox.il2.gui.GUIButton)dialogClient.addDefault(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bDiff = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        wSituation = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wMap = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wPlaneList = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wTarget = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wPos = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wDefence = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wAltitude = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wWeather = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wCldHeight = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wTimeHour = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wTimeMins = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wLevel = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wLevel.add("=");
        wLevel.add("+");
        wLevel.add("-");
        wLevel.setEditable(false);
        wLevel.setSelected(0, true, false);
        wLevel.posEnable = (new boolean[] {
            true, true, true
        });
        wAltitude.add("100");
        wAltitude.add("150");
        wAltitude.add("200");
        wAltitude.add("250");
        wAltitude.add("300");
        wAltitude.add("400");
        wAltitude.add("500");
        wAltitude.add("750");
        wAltitude.add("1000");
        wAltitude.add("1500");
        wAltitude.add("2000");
        wAltitude.add("3000");
        wAltitude.add("5000");
        wAltitude.add("7500");
        wAltitude.add("10000");
        wAltitude.setEditable(true);
        wAltitude.setNumericOnly(true);
        wAltitude.setSelected(8, true, false);
        wPos.add("0");
        wPos.add("500");
        wPos.add("1000");
        wPos.add("2000");
        wPos.add("3000");
        wPos.setEditable(true);
        wPos.setNumericOnly(true);
        wPos.setSelected(0, true, false);
        wTimeHour.add("00");
        wTimeHour.add("01");
        wTimeHour.add("02");
        wTimeHour.add("03");
        wTimeHour.add("04");
        wTimeHour.add("05");
        wTimeHour.add("06");
        wTimeHour.add("07");
        wTimeHour.add("08");
        wTimeHour.add("09");
        wTimeHour.add("10");
        wTimeHour.add("11");
        wTimeHour.add("12");
        wTimeHour.add("13");
        wTimeHour.add("14");
        wTimeHour.add("15");
        wTimeHour.add("16");
        wTimeHour.add("17");
        wTimeHour.add("18");
        wTimeHour.add("19");
        wTimeHour.add("20");
        wTimeHour.add("21");
        wTimeHour.add("22");
        wTimeHour.add("23");
        wTimeHour.setEditable(false);
        wTimeHour.setSelected(12, true, false);
        wTimeMins.add("00");
        wTimeMins.add("15");
        wTimeMins.add("30");
        wTimeMins.add("45");
        wTimeMins.setEditable(false);
        wTimeMins.setSelected(0, true, false);
        wWeather.add(Localize("quick.CLE"));
        wWeather.add(Localize("quick.GOO"));
        wWeather.add(Localize("quick.HAZ"));
        wWeather.add(Localize("quick.POO"));
        wWeather.add(Localize("quick.BLI"));
        wWeather.add(Localize("quick.RAI"));
        wWeather.add(Localize("quick.THU"));
        wWeather.setEditable(false);
        wWeather.setSelected(0, true, false);
        wCldHeight.add("500");
        wCldHeight.add("750");
        wCldHeight.add("1000");
        wCldHeight.add("1250");
        wCldHeight.add("1500");
        wCldHeight.add("1750");
        wCldHeight.add("2000");
        wCldHeight.add("2250");
        wCldHeight.add("2500");
        wCldHeight.add("2750");
        wCldHeight.add("3000");
        wCldHeight.setEditable(true);
        wCldHeight.setSelected(6, true, false);
        wCldHeight.setNumericOnly(true);
        wPlaneList.add(Localize("quick.STD"));
        wPlaneList.add(Localize("quick.ABC"));
        file = new File("Missions/Quick/QMBair_2.ini");
        if(file.exists() && checkCustomAirIni("Missions/Quick/QMBair_2.ini"))
            wPlaneList.add(Localize("quick.CUS1"));
        file = new File("Missions/Quick/QMBair_3.ini");
        if(file.exists() && checkCustomAirIni("Missions/Quick/QMBair_3.ini"))
            wPlaneList.add(Localize("quick.CUS2"));
        wPlaneList.setEditable(false);
        wPlaneList.setSelected(com.maddox.il2.gui.GUIQuick.getPlaneList(), true, false);
        wSituation.add(Localize("quick.NON"));
        wSituation.add(Localize("quick.ADV"));
        wSituation.add(Localize("quick.DIS"));
        wSituation.setEditable(false);
        wSituation.setSelected(0, true, false);
        boolean aflag[] = new boolean[_targetKey.length];
        for(int j = 0; j < _targetKey.length; j++)
        {
            wTarget.add(Localize("quick." + _targetKey[j]));
            aflag[j] = true;
        }

        wTarget.setEditable(false);
        wTarget.setSelected(0, true, false);
        wTarget.posEnable = aflag;
        wDefence.add(Localize("quick.NOND"));
        wDefence.add(Localize("quick.AAA"));
        wDefence.setEditable(false);
        wDefence.setSelected(1, true, false);
        star = com.maddox.gwindow.GTexture.New("GUI/Game/QM/star.mat");
        cross = com.maddox.gwindow.GTexture.New("GUI/Game/QM/cross.mat");
        for(int k = 0; k < 16; k++)
        {
            dlg[k] = new ItemDlg();
            dlg[k].wNum = (com.maddox.il2.gui.WComboNum)dialogClient.addControl(new WComboNum(k, dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
            dlg[k].wSkill = (com.maddox.il2.gui.WComboSkill)dialogClient.addControl(new WComboSkill(k, dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
            dlg[k].wPlane = (com.maddox.il2.gui.WComboPlane)dialogClient.addControl(new WComboPlane(k, dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
            dlg[k].wLoadout = (com.maddox.il2.gui.WComboLoadout)dialogClient.addControl(new WComboLoadout(k, dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
            dlg[k].bArming = (com.maddox.il2.gui.WButtonArming)dialogClient.addControl(new WButtonArming(k, dialogClient, gtexture1, 0.0F, 48F, 32F, 32F));
            dlg[k].wNum.setEditable(false);
            if(k == 0)
            {
                for(int l = 1; l < 5; l++)
                    dlg[k].wNum.add("" + l);

            } else
            {
                for(int i1 = 0; i1 < 5; i1++)
                    dlg[k].wNum.add("" + i1);

            }
            dlg[k].wNum.setSelected(0, true, false);
            dlg[k].wSkill.setEditable(false);
            dlg[k].wSkill.add(Localize("quick.ROO"));
            dlg[k].wSkill.add(Localize("quick.EXP"));
            dlg[k].wSkill.add(Localize("quick.VET"));
            dlg[k].wSkill.add(Localize("quick.ACE"));
            dlg[k].wSkill.setSelected(1, true, false);
            dlg[k].wPlane.setEditable(false);
            dlg[k].wPlane.listVisibleLines = 16;
            fillComboPlane(dlg[k].wPlane, k == 0);
            dlg[k].wLoadout.setEditable(false);
            fillComboWeapon(dlg[k].wLoadout, wing[k].plane, 0);
        }

        fillMapKey();
        wMap.setSelected(0, true, false);
        onArmyChange();
        wMap.setEditable(false);
        mapChanged();
        defaultRegiments();
        dialogClient.activateWindow();
        showHide();
        client.hideWindow();
    }

    private void dumpFullPlaneList()
    {
        com.maddox.rts.SectFile sectfile = new SectFile("com/maddox/il2/objects/air.ini");
        com.maddox.rts.SectFile sectfile1 = new SectFile("./Missions/Quick/FullPlaneList.dump", 1);
        sectfile1.sectionAdd("AIR");
        int i = sectfile.vars(sectfile.sectionIndex("AIR"));
        for(int j = 0; j < i; j++)
            sectfile1.varAdd(0, sectfile.var(0, j));

    }

    private void fillMapKey()
    {
        com.maddox.il2.gui.DirFilter dirfilter = new DirFilter();
        folderNames = (new File("Missions/Quick/")).list(dirfilter);
        if(folderNames != null)
        {
            _mapKey = new java.lang.String[folderNames.length];
            for(int i = 0; i < _mapKey.length; i++)
                _mapKey[i] = folderNames[i];

        } else
        {
            _mapKey = new java.lang.String[1];
            _mapKey[0] = Localize("quick.NOMISSIONS");
            bNoAvailableMissions = true;
        }
        resetwMap();
    }

    private java.util.ArrayList getAvailableMissionsS(java.lang.String s, java.lang.String s1)
    {
        java.io.File file = new File("Missions/Quick/" + s);
        java.lang.String as[] = file.list();
        java.util.ArrayList arraylist = new ArrayList();
        arraylist.clear();
        if(as == null)
            return arraylist;
        for(int i = 0; i < as.length; i++)
            if(as[i].startsWith(s1) && as[i].endsWith(".mis"))
                arraylist.add(as[i]);

        return arraylist;
    }

    private java.lang.String getArmyString()
    {
        if(OUR)
            return "Red";
        else
            return "Blue";
    }

    private void resetwMap()
    {
        wMap.clear();
        if(bNoAvailableMissions)
        {
            wMap.add(Localize("quick.NOMISSIONS"));
            return;
        }
        for(int i = 0; i < _mapKey.length; i++)
        {
            if(getAvailableMissionsS(_mapKey[i], _mapKey[i] + getArmyString()).size() <= 0)
                continue;
            java.lang.String s = _mapKey[i];
            java.lang.String s1 = com.maddox.il2.game.I18N.map(s);
            if(!s.equals(s1))
                wMap.add(s1);
            else
                wMap.add(com.maddox.il2.game.I18N.map(_mapKey[i]));
        }

    }

    private java.lang.String getMapName()
    {
        java.lang.String s = wMap.getValue();
        for(int i = 0; i < _mapKey.length; i++)
        {
            java.lang.String s1 = _mapKey[i];
            java.lang.String s2 = com.maddox.il2.game.I18N.map(s1);
            if(s2.equals(s))
                return s1;
        }

        return s;
    }

    private void onArmyChange()
    {
        java.lang.String s = getMapName();
        java.lang.String s1 = wMap.getValue();
        resetwMap();
        int i = wMap.list.indexOf(s1);
        if(getAvailableMissionsS(s, s + getArmyString()).size() > 0)
            wMap.setSelected(i, true, false);
        else
            wMap.setSelected(0, true, false);
        onTargetChange();
    }

    private void onTargetChange()
    {
        int i = wTarget.getSelected();
        int j = 0;
        for(int k = wTarget.size() - 1; k > -1; k--)
            if(getAvailableMissionsS(getMapName(), getMapName() + getArmyString() + _targetKey[k]).size() > 0)
            {
                wTarget.posEnable[k] = true;
                j = k;
            } else
            {
                wTarget.posEnable[k] = false;
            }

        if(wTarget.posEnable[i])
            wTarget.setSelected(i, true, false);
        else
            wTarget.setSelected(j, true, false);
        checkNoneTarget();
    }

    private void checkNoneTarget()
    {
        boolean flag = false;
        int j = 0;
        if(wTarget.getSelected() == 0)
        {
            noneTarget = true;
            int i = wLevel.getSelected();
            for(int k = wLevel.size() - 1; k > -1; k--)
                if(getAvailableMissionsS(getMapName(), getMapName() + getArmyString() + _targetKey[0] + noneTargetSuffix[k]).size() > 0)
                {
                    wLevel.posEnable[k] = true;
                    j = k;
                } else
                {
                    wLevel.posEnable[k] = false;
                }

            if(wLevel.posEnable[i])
                wLevel.setSelected(i, true, false);
            else
                wLevel.setSelected(j, true, false);
            wLevel.showWindow();
        } else
        {
            noneTarget = false;
            wLevel.hideWindow();
        }
    }

    private void showHide()
    {
        for(int i = 0; i < 4; i++)
        {
            setShow(bFirst, dlg[i].wNum);
            setShow(bFirst, dlg[i].wSkill);
            setShow(bFirst, dlg[i].wPlane);
            setShow(bFirst, dlg[i].wLoadout);
            setShow(bFirst, dlg[i].bArming);
            setShow(bFirst, dlg[i + 8].wNum);
            setShow(bFirst, dlg[i + 8].wSkill);
            setShow(bFirst, dlg[i + 8].wPlane);
            setShow(bFirst, dlg[i + 8].wLoadout);
            setShow(bFirst, dlg[i + 8].bArming);
        }

        for(int j = 4; j < 8; j++)
        {
            setShow(!bFirst, dlg[j].wNum);
            setShow(!bFirst, dlg[j].wSkill);
            setShow(!bFirst, dlg[j].wPlane);
            setShow(!bFirst, dlg[j].wLoadout);
            setShow(!bFirst, dlg[j].bArming);
            setShow(!bFirst, dlg[j + 8].wNum);
            setShow(!bFirst, dlg[j + 8].wSkill);
            setShow(!bFirst, dlg[j + 8].wPlane);
            setShow(!bFirst, dlg[j + 8].wLoadout);
            setShow(!bFirst, dlg[j + 8].bArming);
        }

        setShow(bFirst, wPlaneList);
        setShow(bFirst, wTarget);
        setShow(bFirst, wMap);
        setShow(bFirst, bNext);
        setShow(bFirst, bExit);
        setShow(bFirst, bArmy);
        setShow(bFirst, wAltitude);
        setShow(bFirst, wDefence);
        setShow(bFirst, wSituation);
        setShow(bFirst, wPos);
        setShow(bFirst, bReset);
        setShow(bFirst && noneTarget, wLevel);
        setShow(!bFirst, bBack);
        setShow(!bFirst, bStat);
        setShow(!bFirst, wWeather);
        setShow(!bFirst, wTimeHour);
        setShow(!bFirst, wTimeMins);
        setShow(!bFirst, wCldHeight);
        setShow(!bNoAvailableMissions, bFly);
        dialogClient.doResolutionChanged();
        dialogClient.setPosSize();
    }

    private void setShow(boolean flag, com.maddox.gwindow.GWindow gwindow)
    {
        if(flag)
            gwindow.showWindow();
        else
            gwindow.hideWindow();
    }

    private void setDefaultValues()
    {
        wAltitude.setSelected(8, true, false);
        wPos.setSelected(0, true, false);
        wTimeHour.setSelected(12, true, false);
        wTimeMins.setSelected(0, true, false);
        wWeather.setSelected(0, true, false);
        wCldHeight.setSelected(6, true, false);
        wDefence.setSelected(1, true, false);
        wSituation.setSelected(0, true, false);
        for(int i = 0; i < 16; i++)
        {
            dlg[i].wNum.setSelected(0, true, false);
            dlg[i].wSkill.setSelected(1, true, false);
            dlg[i].wPlane.setSelected(0, true, false);
            dlg[i].wLoadout.setSelected(0, true, false);
            dlg[i].wNum.notify(2, 0);
            dlg[i].wSkill.notify(2, 0);
            dlg[i].wPlane.notify(2, 0);
            dlg[i].wLoadout.notify(2, 0);
        }

    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        java.lang.Class class1;
        try
        {
            class1 = java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
        return class1;
    }

    private java.lang.String russianMixedRules;
    private java.text.RuleBasedCollator collator;
    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.rts.SectFile ssect;
    public com.maddox.gwindow.GTexture cross;
    public com.maddox.gwindow.GTexture star;
    public boolean OUR;
    public boolean bScramble;
    public com.maddox.il2.gui.GUIButton bArmy;
    public com.maddox.il2.gui.GUIButton bNext;
    public com.maddox.il2.gui.GUIButton bStat;
    public com.maddox.il2.gui.GUIButton bExit;
    public com.maddox.il2.gui.GUIButton bBack;
    public com.maddox.il2.gui.GUIButton bLoad;
    public com.maddox.il2.gui.GUIButton bSave;
    public com.maddox.il2.gui.GUIButton bFly;
    public com.maddox.il2.gui.GUIButton bDiff;
    public com.maddox.il2.gui.GUIButton bReset;
    public com.maddox.gwindow.GWindowComboControl wSituation;
    public com.maddox.gwindow.GWindowComboControl wMap;
    public com.maddox.gwindow.GWindowComboControl wTarget;
    public com.maddox.gwindow.GWindowComboControl wPos;
    public com.maddox.gwindow.GWindowComboControl wDefence;
    public com.maddox.gwindow.GWindowComboControl wAltitude;
    public com.maddox.gwindow.GWindowComboControl wWeather;
    public com.maddox.gwindow.GWindowComboControl wCldHeight;
    public com.maddox.gwindow.GWindowComboControl wTimeHour;
    public com.maddox.gwindow.GWindowComboControl wTimeMins;
    public com.maddox.gwindow.GWindowComboControl wLevel;
    public com.maddox.gwindow.GWindowComboControl wPlaneList;
    private java.lang.String r01;
    private java.lang.String r010;
    private java.lang.String g01;
    private java.lang.String g010;
    private java.lang.String _mapKey[];
    private java.lang.String folderNames[];
    private java.lang.String _targetKey[] = {
        "None", "Armor", "Bridge", "Airbase", "Scramble"
    };
    private java.util.ArrayList playerPlane;
    private java.util.ArrayList aiPlane;
    private java.util.ArrayList playerPlaneC;
    private java.util.ArrayList aiPlaneC;
    private com.maddox.il2.gui.ItemWing wing[];
    private com.maddox.il2.gui.ItemDlg dlg[];
    private com.maddox.il2.gui.IOState ioState;
    private int indxAirArming;
    private boolean bPlaneArrestor;
    private boolean bFirst;
    static boolean bIsQuick;
    private static int pl;
    private static final java.lang.String PREFIX = "Missions/Quick/";
    private boolean noneTarget;
    private java.lang.String noneTargetSuffix[] = {
        "N", "A", "D"
    };
    private static final int NONE = 0;
    private static final int ARMOR = 1;
    private static final int BRIDGE = 2;
    private static final int AIRBASE = 3;
    private static final int SCRAMBLE = 4;
    private static final int ADVANTAGE = 1;
    private java.lang.String currentMissionName;
    private boolean bNoAvailableMissions;
    static java.lang.Class class$com$maddox$il2$objects$air$TypeTransport;

    static 
    {
        com.maddox.rts.ObjIO.fields(com.maddox.il2.gui.GUIQuick$IOState.class, new java.lang.String[] {
            "our", "situation", "map", "target", "defence", "altitude", "weather", "timeH", "timeM", "pos", 
            "cldheight", "scramble", "noneTARGET"
        });
        com.maddox.rts.ObjIO.validate(com.maddox.il2.gui.GUIQuick$IOState.class, "loaded");
        com.maddox.rts.ObjIO.fields(com.maddox.il2.gui.GUIQuick$ItemWing.class, new java.lang.String[] {
            "planes", "weapon", "regiment", "skin", "noseart", "pilot", "numberOn", "fuel", "skill"
        });
        com.maddox.rts.ObjIO.accessStr(com.maddox.il2.gui.GUIQuick$ItemWing.class, "plane", "getPlane", "setPlane");
        com.maddox.rts.ObjIO.validate(com.maddox.il2.gui.GUIQuick$ItemWing.class, "loaded");
    }
























}

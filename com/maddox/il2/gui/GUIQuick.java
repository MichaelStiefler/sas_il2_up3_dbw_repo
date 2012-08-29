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
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import com.maddox.util.SharedTokenizer;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

// Referenced classes of package com.maddox.il2.gui:
//            GUIQuickSave, GUIClient, GUIInfoMenu, GUIInfoName, 
//            GUILookAndFeel, GUIButton, GUIAirArming, GUIDialogClient, 
//            GUISeparate

public class GUIQuick extends com.maddox.il2.game.GameState
{
    public class IOState
    {

        public void save()
        {
            our = OUR;
            scramble = SCRAMBLE;
            situation = wSituation.getSelected();
            map = wMap.getSelected();
            target = wTarget.getSelected();
            defence = wDefence.getSelected();
            altitude = wAltitude.getValue();
            pos = wPos.getValue();
            weather = wWeather.getSelected();
            cldheight = wCldHeight.getValue();
            timeH = wTimeHour.getSelected();
            timeM = wTimeMins.getSelected();
        }

        public void loaded()
        {
            OUR = our;
            wMap.setSelected(map, true, true);
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
            SCRAMBLE = scramble;
            wPos.setValue(pos);
            wAltitude.setValue(altitude);
            wCldHeight.setValue(cldheight);
            wWeather.setSelected(weather, true, false);
            wTimeHour.setSelected(timeH, true, false);
            wTimeMins.setSelected(timeM, true, false);
        }

        public boolean our;
        public boolean scramble;
        public int situation;
        public int map;
        public int target;
        public int defence;
        public java.lang.String altitude;
        public java.lang.String pos;
        public int weather;
        public java.lang.String cldheight;
        public int timeH;
        public int timeM;

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
                    for(int l = 0; l < as.length; l++)
                    {
                        if(!as[l].equals(wing[indx].weapon))
                            continue;
                        fillComboWeapon(dlg[indx].wLoadout, wing[indx].plane, l);
                        flag = true;
                        break;
                    }

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
            for(int i = 0; i < arraylist.size(); i++)
            {
                plane = (com.maddox.il2.gui.ItemAir)arraylist.get(i);
                if(plane.name.equals(s))
                    break;
            }

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
            for(int i = 0; i < arraylist.size(); i++)
            {
                if(plane != arraylist.get(i))
                    continue;
                dlg[indx].wPlane.setSelected(i, true, false);
                break;
            }

            java.lang.String as[] = com.maddox.il2.objects.air.Aircraft.getWeaponsRegistered(plane.clazz);
            for(int j = 0; j < as.length; j++)
            {
                if(!as[j].equals(weapon))
                    continue;
                fillComboWeapon(dlg[indx].wLoadout, plane, j);
                break;
            }

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
            if(indx <= 3)
                s = (OUR ? r010 : g010) + iwing;
            else
                s = (OUR ? g010 : r010) + iwing;
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
                        if(indx <= 3)
                            l += java.lang.Integer.parseInt(wPos.getValue());
                    } else
                    if(indx > 3)
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
                if(s3 != null && !(com.maddox.il2.gui.GUIQuick.class$com$maddox$il2$objects$air$TypeTransport != null ? com.maddox.il2.gui.GUIQuick.class$com$maddox$il2$objects$air$TypeTransport : (com.maddox.il2.gui.GUIQuick.class$com$maddox$il2$objects$air$TypeTransport = com.maddox.il2.gui.GUIQuick._mthclass$("com.maddox.il2.objects.air.TypeTransport"))).isAssignableFrom(plane.clazz))
                    s3 = null;
                if(s3 != null)
                {
                    for(int i1 = 0; i1 < 8; i1++)
                    {
                        if(!s3.equals(as[i1]))
                            continue;
                        s3 = as1[i1];
                        break;
                    }

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
            if(indx <= 3)
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
            for(int i = 0; i < arraylist.size(); i++)
            {
                itemair = (com.maddox.il2.gui.ItemAir)arraylist.get(i);
                if(itemair.name.equals(guiairarming.quikPlane))
                    break;
            }

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
            if(indx <= 3)
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
            validateCldHeight();
            validatePos();
            validateAltitude();
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bArmy)
            {
                if(OUR)
                    OUR = false;
                else
                    OUR = true;
                mapChanged();
                if(r01.equals("usa01"))
                    com.maddox.il2.game.Main3D.menuMusicPlay(OUR ? "us" : "ja");
                else
                    com.maddox.il2.game.Main3D.menuMusicPlay(OUR ? "ru" : "de");
                for(int k = 0; k < 8; k++)
                    if(k <= 3)
                        wing[k].regiment = OUR ? r01 : g01;
                    else
                        wing[k].regiment = OUR ? g01 : r01;

                return true;
            }
            if(gwindow == wTarget)
            {
                if(wTarget.getSelected() == 4)
                    SCRAMBLE = true;
                else
                    SCRAMBLE = false;
                if(wTarget.getSelected() == 0)
                {
                    LEVEL = true;
                    wLevel.showWindow();
                } else
                {
                    LEVEL = false;
                    wLevel.hideWindow();
                }
                return true;
            }
            if(gwindow == bExit)
            {
                com.maddox.il2.game.Main.stateStack().pop();
                return true;
            }
            if(gwindow == bDiff)
            {
                com.maddox.il2.game.Main.stateStack().push(17);
                return true;
            }
            if(gwindow == bFly)
            {
                validateCldHeight();
                validatePos();
                validateAltitude();
                startQuickMission();
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
                validateCldHeight();
                validatePos();
                validateAltitude();
                save();
                com.maddox.il2.game.Main.stateStack().push(24);
                return true;
            }
            if(gwindow == wMap)
            {
                mapChanged();
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
                int l = wMap.getSelected();
                boolean flag = "NetCoralSea".equals(_mapKey[l]);
                if(flag != bPlaneArrestor)
                {
                    bPlaneArrestor = flag;
                    if(bPlaneArrestor)
                    {
                        wTarget.posEnable[1] = false;
                        wTarget.posEnable[2] = false;
                        wTarget.posEnable[4] = false;
                    } else
                    {
                        wTarget.posEnable[1] = true;
                        wTarget.posEnable[2] = true;
                        wTarget.posEnable[4] = true;
                    }
                    if(wTarget.getSelected() != 0 && wTarget.getSelected() != 3)
                        wTarget.setSelected(0, true, false);
                    for(int i1 = 0; i1 < 8; i1++)
                    {
                        fillComboPlane(dlg[i1].wPlane, i1 == 0);
                        int j1 = dlg[i1].wPlane.getSelected();
                        if(j1 == 0)
                            dlg[i1].wPlane.setSelected(1, false, false);
                        dlg[i1].wPlane.setSelected(0, true, true);
                    }

                }
                if("MTO".equals(_mapKey[l]))
                {
                    wTarget.posEnable[2] = false;
                    if(wTarget.getSelected() == 2)
                        wTarget.setSelected(0, true, false);
                } else
                if(!"NetCoralSea".equals(_mapKey[l]))
                    wTarget.posEnable[2] = true;
            }
            return super.notify(gwindow, i, j);
        }

        public void render()
        {
            super.render();
            setCanvasColorWHITE();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(48F), y1024(530F), x1024(924F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(48F), y1024(305F), x1024(924F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(864F), y1024(144F), x1024(94F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(864F), y1024(336F), x1024(94F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(958F), y1024(120F), 2.0F, x1024(56F));
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(958F), y1024(336F), 2.0F, x1024(32F));
            draw(x1024(90F), y1024(678F), x1024(170F), M(2.0F), 0, Localize("quick.BAC"));
            draw(x1024(170F), y1024(678F), x1024(170F), M(2.0F), 2, Localize("quick.LOD"));
            draw(x1024(492F), y1024(678F), x1024(82F), M(2.0F), 0, Localize("quick.SAV"));
            draw(x1024(846F), y1024(678F), x1024(86F), M(2.0F), 2, Localize("quick.FLY"));
            draw(x1024(735F), y1024(678F), x1024(112F), M(2.0F), 0, Localize("quick.DIF"));
            setCanvasFont(1);
            draw(x1024(80F), y1024(16F), x1024(602F), M(2.0F), 0, Localize("quick.YOU"));
            draw(x1024(80F), y1024(128F), x1024(602F), M(2.0F), 0, Localize("quick.FRI"));
            draw(x1024(80F), y1024(320F), x1024(602F), M(2.0F), 0, Localize("quick.ENM"));
            setCanvasFont(0);
            draw(x1024(48F), y1024(48F), x1024(82F), M(2.0F), 0, Localize("quick.NUM"));
            draw(x1024(144F), y1024(48F), x1024(160F), M(2.0F), 0, Localize("quick.SKI"));
            draw(x1024(318F), y1024(48F), x1024(274F), M(2.0F), 0, Localize("quick.PLA"));
            draw(x1024(606F), y1024(48F), x1024(196F), M(2.0F), 0, Localize("quick.TNT"));
            draw(x1024(48F), y1024(160F), x1024(82F), M(2.0F), 0, Localize("quick.NUM"));
            draw(x1024(144F), y1024(160F), x1024(160F), M(2.0F), 0, Localize("quick.SKI"));
            draw(x1024(318F), y1024(160F), x1024(274F), M(2.0F), 0, Localize("quick.PLA"));
            draw(x1024(606F), y1024(160F), x1024(196F), M(2.0F), 0, Localize("quick.TNT"));
            draw(x1024(48F), y1024(350F), x1024(82F), M(2.0F), 0, Localize("quick.NUM"));
            draw(x1024(144F), y1024(350F), x1024(160F), M(2.0F), 0, Localize("quick.SKI"));
            draw(x1024(318F), y1024(350F), x1024(274F), M(2.0F), 0, Localize("quick.PLA"));
            draw(x1024(606F), y1024(350F), x1024(196F), M(2.0F), 0, Localize("quick.TNT"));
            if(LEVEL)
                draw(x1024(847F), y1024(557F), x1024(132F), M(2.0F), 2, Localize("quick.+/-"));
            draw(x1024(35F), y1024(538F), x1024(100F), M(2.0F), 2, Localize("quick.MAP"));
            draw(x1024(35F), y1024(588F), x1024(100F), M(2.0F), 2, Localize("quick.WEA"));
            draw(x1024(35F), y1024(624F), x1024(100F), M(2.0F), 2, Localize("quick.CLD"));
            draw(x1024(308F), y1024(538F), x1024(100F), M(2.0F), 2, Localize("quick.ALT"));
            draw(x1024(308F), y1024(588F), x1024(100F), M(2.0F), 2, Localize("quick.SIT"));
            draw(x1024(308F), y1024(624F), x1024(100F), M(2.0F), 2, Localize("quick.POS"));
            draw(x1024(548F), y1024(588F), x1024(192F), M(2.0F), 2, Localize("quick.TAR"));
            draw(x1024(548F), y1024(624F), x1024(192F), M(2.0F), 2, Localize("quick.DEF"));
            draw(x1024(608F), y1024(538F), x1024(132F), M(2.0F), 2, Localize("quick.TIM"));
            draw(x1024(320F), y1024(128F), x1024(528F), y1024(32F), 2, Localize("quick.ASET"));
            draw(x1024(320F), y1024(320F), x1024(528F), y1024(32F), 2, Localize("quick.ASET"));
            draw(x1024(856F), y1024(538F), x1024(8F), M(2.0F), 1, ":");
            setCanvasFont(0);
            if(OUR)
                draw(x1024(566F), y1024(28F), x1024(362F), M(2.0F), 2, Localize("quick.SEL_Allies"));
            else
                draw(x1024(566F), y1024(28F), x1024(362F), M(2.0F), 2, Localize("quick.SEL_Axis"));
        }

        public void setPosSize()
        {
            set1024PosSize(0.0F, 32F, 1024F, 736F);
            bExit.setPosC(x1024(55F), y1024(696F));
            bLoad.setPosC(x1024(374F), y1024(696F));
            bSave.setPosC(x1024(456F), y1024(696F));
            bFly.setPosC(x1024(966F), y1024(696F));
            bArmy.setPosC(x1024(966F), y1024(48F));
            bDiff.setPosC(x1024(700F), y1024(696F));
            wMap.setPosSize(x1024(142F), y1024(542F), x1024(160F), M(1.7F));
            wWeather.setPosSize(x1024(142F), y1024(590F), x1024(160F), M(1.7F));
            wCldHeight.setPosSize(x1024(142F), y1024(624F), x1024(160F), M(1.7F));
            wAltitude.setPosSize(x1024(432F), y1024(542F), x1024(160F), M(1.7F));
            wSituation.setPosSize(x1024(432F), y1024(590F), x1024(160F), M(1.7F));
            wPos.setPosSize(x1024(432F), y1024(624F), x1024(160F), M(1.7F));
            wTarget.setPosSize(x1024(753F), y1024(590F), x1024(190F), M(1.7F));
            wDefence.setPosSize(x1024(753F), y1024(624F), x1024(190F), M(1.7F));
            wTimeHour.setPosSize(x1024(753F), y1024(542F), x1024(80F), M(1.7F));
            wTimeMins.setPosSize(x1024(859F), y1024(542F), x1024(80F), M(1.7F));
            wLevel.setPosSize(x1024(952F), y1024(590F), x1024(60F), M(1.7F));
            dlg[0].wNum.setPosSize(x1024(48F), y1024(80F), x1024(80F), M(1.7F));
            dlg[0].wSkill.setPosSize(x1024(144F), y1024(80F), x1024(160F), M(1.7F));
            dlg[0].wPlane.setPosSize(x1024(318F), y1024(80F), x1024(274F), M(1.7F));
            dlg[0].wLoadout.setPosSize(x1024(609F), y1024(80F), x1024(332F), M(1.7F));
            dlg[0].bArming.setPosC(x1024(959F), y1024(94F));
            for(int i = 0; i < 3; i++)
            {
                dlg[i + 1].wNum.setPosSize(x1024(48F), y1024(192 + 34 * i), x1024(80F), M(1.7F));
                dlg[i + 1].wSkill.setPosSize(x1024(144F), y1024(192 + 34 * i), x1024(160F), M(1.7F));
                dlg[i + 1].wPlane.setPosSize(x1024(318F), y1024(192 + 34 * i), x1024(274F), M(1.7F));
                dlg[i + 1].wLoadout.setPosSize(x1024(609F), y1024(192 + 34 * i), x1024(332F), M(1.7F));
                dlg[i + 1].bArming.setPosC(x1024(959F), y1024((192 + 34 * i + 16) - 2));
            }

            for(int j = 0; j < 4; j++)
            {
                dlg[j + 4].wNum.setPosSize(x1024(48F), y1024(384 + 34 * j), x1024(80F), M(1.7F));
                dlg[j + 4].wSkill.setPosSize(x1024(144F), y1024(384 + 34 * j), x1024(160F), M(1.7F));
                dlg[j + 4].wPlane.setPosSize(x1024(318F), y1024(384 + 34 * j), x1024(274F), M(1.7F));
                dlg[j + 4].wLoadout.setPosSize(x1024(609F), y1024(384 + 34 * j), x1024(332F), M(1.7F));
                dlg[j + 4].bArming.setPosC(x1024(959F), y1024((384 + 34 * j + 16) - 2));
            }

        }

        public DialogClient()
        {
        }
    }


    private void mapChanged()
    {
        int i = wMap.getSelected();
        if(i >= 0 && i < _mapKey.length)
        {
            java.lang.String s = _mapKey[i];
            java.lang.String s1 = "Missions/Quick/" + s + "RedNone00.mis";
            com.maddox.rts.SectFile sectfile = new SectFile(s1, 0);
            if(sectfile.sectionIndex("r0100") >= 0)
            {
                r01 = "r01";
                r010 = "r010";
                g01 = "g01";
                g010 = "g010";
                for(int j = 0; j < 8; j++)
                {
                    if(wing[j].regiment.equals("usa01"))
                        wing[j].regiment = "r01";
                    if(wing[j].regiment.equals("ja01"))
                        wing[j].regiment = "g01";
                }

            } else
            {
                r01 = "usa01";
                r010 = "usa010";
                g01 = "ja01";
                g010 = "ja010";
                for(int k = 0; k < 8; k++)
                {
                    if(wing[k].regiment.equals("r01"))
                        wing[k].regiment = "usa01";
                    if(wing[k].regiment.equals("g01"))
                        wing[k].regiment = "ja01";
                }

            }
            java.lang.String s2 = sectfile.get("MAIN", "MAP", (java.lang.String)null);
            if(s2 != null)
            {
                com.maddox.rts.SectFile sectfile1 = new SectFile("maps/" + s2, 0);
                com.maddox.rts.IniFile inifile = new IniFile("maps/" + s2, 0);
                java.lang.String s3 = inifile.get("WORLDPOS", "CAMOUFLAGE", "SUMMER");
                if(com.maddox.il2.ai.World.cur() != null)
                    com.maddox.il2.ai.World.cur().setCamouflage(s3);
            }
        }
    }

    public void enterPush(com.maddox.il2.game.GameState gamestate)
    {
        com.maddox.il2.ai.World.cur().diffUser.set(com.maddox.il2.ai.World.cur().userCfg.singleDifficulty);
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
            load();
        _enter();
    }

    public void _enter()
    {
        com.maddox.il2.game.Main.cur().currentMissionFile = null;
        client.activateWindow();
    }

    public void _leave()
    {
        com.maddox.il2.ai.World.cur().userCfg.saveConf();
        client.hideWindow();
    }

    private void validateCldHeight()
    {
        java.lang.String s = wCldHeight.getValue();
        if(s.equals(""))
        {
            wCldHeight.setSelected(0, true, false);
            s = wCldHeight.get(0);
        }
        int i = java.lang.Integer.parseInt(s);
        if(i < java.lang.Integer.parseInt(wCldHeight.get(0)))
            wCldHeight.setSelected(0, true, false);
        if(i > java.lang.Integer.parseInt(wCldHeight.get(wCldHeight.size() - 1)))
            wCldHeight.setSelected(wCldHeight.size() - 1, true, false);
    }

    private void validatePos()
    {
        java.lang.String s = wPos.getValue();
        if(s.equals(""))
        {
            wPos.setSelected(0, true, false);
            s = wPos.get(0);
        }
        int i = java.lang.Integer.parseInt(s);
        if(i < java.lang.Integer.parseInt(wPos.get(0)))
            wPos.setSelected(0, true, false);
        if(i > java.lang.Integer.parseInt(wPos.get(wPos.size() - 1)))
            wPos.setSelected(wPos.size() - 1, true, false);
    }

    private void validateAltitude()
    {
        java.lang.String s = wAltitude.getValue();
        if(s.equals(""))
        {
            wPos.setSelected(0, true, false);
            s = wAltitude.get(0);
        }
        int i = java.lang.Integer.parseInt(s);
        if(i < java.lang.Integer.parseInt(wAltitude.get(0)))
            wAltitude.setSelected(0, true, false);
        if(i > java.lang.Integer.parseInt(wAltitude.get(wAltitude.size() - 1)))
            wAltitude.setSelected(wAltitude.size() - 1, true, false);
    }

    public void startQuickMission()
    {
        java.lang.String s = "Missions/Quick/" + _mapKey[wMap.getSelected()];
        java.util.Random random = new Random();
        int i = random.nextInt(3);
        if(OUR)
            s = s + "Red";
        else
            s = s + "Blue";
        s = s + _targetKey[wTarget.getSelected()];
        if(wTarget.getSelected() == 4)
            s = s + "0" + i + ".mis";
        else
        if(LEVEL)
            s = s + wLevel.getSelected() + "0.mis";
        else
            s = s + "00.mis";
        try
        {
            com.maddox.rts.SectFile sectfile = new SectFile(s, 0);
            sectfile.set("MAIN", "TIME", wTimeHour.getValue() + "." + wTimeMins.getSelected() * 25);
            for(int j = 0; j < 4; j++)
            {
                java.lang.String s1 = r010 + java.lang.Character.forDigit(j, 10);
                if(!sectfile.exist("Wing", s1))
                    throw new Exception("Section " + s1 + " not found");
            }

            for(int k = 0; k < 4; k++)
            {
                java.lang.String s2 = g010 + java.lang.Character.forDigit(k, 10);
                if(!sectfile.exist("Wing", s2))
                    throw new Exception("Section " + s2 + " not found");
            }

            sectfile.set("MAIN", "CloudType", wWeather.getSelected());
            sectfile.set("MAIN", "CloudHeight", wCldHeight.getValue());
            java.lang.String as[] = new java.lang.String[8];
            java.lang.String as1[] = new java.lang.String[8];
            for(int l = 0; l < 8; l++)
            {
                if(l <= 3)
                    as[l] = (OUR ? r010 : g010) + l;
                else
                    as[l] = (OUR ? g010 : r010) + l;
                as1[l] = wing[l].prepareWing(sectfile);
            }

            if(as1[0] != null)
                sectfile.set("MAIN", "player", as1[0]);
            else
                sectfile.set("MAIN", "player", as[0]);
            for(int i1 = 0; i1 < 8; i1++)
                if(as1[i1] != null)
                    wing[i1].prepereWay(sectfile, as, as1);

            if(wDefence.getSelected() == 0)
            {
                for(int j1 = 0; j1 < 2; j1++)
                {
                    java.lang.String s3 = j1 == 0 ? "Stationary" : "NStationary";
                    int l1 = sectfile.sectionIndex(s3);
                    if(l1 >= 0)
                    {
                        sectfile.sectionRename(l1, "Stationary_Temp");
                        sectfile.sectionAdd(s3);
                        int j2 = sectfile.sectionIndex(s3);
                        int l2 = sectfile.vars(l1);
                        for(int j3 = 0; j3 < l2; j3++)
                        {
                            com.maddox.util.SharedTokenizer.set(sectfile.line(l1, j3));
                            java.lang.String s5 = null;
                            if(j1 == 1)
                                s5 = com.maddox.util.SharedTokenizer.next("");
                            java.lang.String s7 = com.maddox.util.SharedTokenizer.next("");
                            int k3 = com.maddox.util.SharedTokenizer.next(0);
                            java.lang.String s9 = null;
                            if(s5 != null)
                                s9 = s5 + " " + s7 + " " + k3 + " " + com.maddox.util.SharedTokenizer.getGap();
                            else
                                s9 = s7 + " " + k3 + " " + com.maddox.util.SharedTokenizer.getGap();
                            if(k3 == 0)
                                sectfile.lineAdd(j2, s9);
                            else
                            if(k3 == 1 && OUR && !SCRAMBLE)
                                sectfile.lineAdd(j2, s9);
                            else
                            if(k3 == 2 && !OUR && !SCRAMBLE)
                                sectfile.lineAdd(j2, s9);
                            else
                                try
                                {
                                    java.lang.Class class1 = com.maddox.rts.ObjIO.classForName(s7);
                                    if(!(com.maddox.il2.objects.vehicles.artillery.AAA.class).isAssignableFrom(class1))
                                        if(s7.startsWith("ships."))
                                        {
                                            com.maddox.util.SharedTokenizer.set(sectfile.line(l1, j3));
                                            if(j1 == 1)
                                                s5 = com.maddox.util.SharedTokenizer.next("");
                                            java.lang.String s12 = com.maddox.util.SharedTokenizer.next("");
                                            java.lang.String s13 = com.maddox.util.SharedTokenizer.next("");
                                            java.lang.String s14 = com.maddox.util.SharedTokenizer.next("");
                                            java.lang.String s15 = com.maddox.util.SharedTokenizer.next("");
                                            java.lang.String s16 = com.maddox.util.SharedTokenizer.next("");
                                            java.lang.String s17 = com.maddox.util.SharedTokenizer.next("");
                                            java.lang.String s18 = com.maddox.util.SharedTokenizer.next("");
                                            java.lang.String s19 = com.maddox.util.SharedTokenizer.next("");
                                            if(j1 == 1)
                                                sectfile.lineAdd(j2, s5 + " " + s12 + " " + s13 + " " + s14 + " " + s15 + " " + s16 + " " + s17 + " " + 5940 + " " + s19);
                                            else
                                                sectfile.lineAdd(j2, s12 + " " + s13 + " " + s14 + " " + s15 + " " + s16 + " " + s17 + " " + 5940 + " " + s19);
                                        } else
                                        {
                                            sectfile.lineAdd(j2, s9);
                                        }
                                }
                                catch(java.lang.Throwable throwable) { }
                        }

                        sectfile.sectionRemove(l1);
                    }
                }

                int k1 = sectfile.sectionIndex("Chiefs");
                if(k1 >= 0)
                {
                    sectfile.sectionRename(k1, "Chiefs_Temp");
                    sectfile.sectionAdd("Chiefs");
                    int i2 = sectfile.sectionIndex("Chiefs");
                    int k2 = sectfile.vars(k1);
                    for(int i3 = 0; i3 < k2; i3++)
                    {
                        java.lang.String s4 = sectfile.line(k1, i3);
                        com.maddox.util.SharedTokenizer.set(s4);
                        java.lang.String s6 = com.maddox.util.SharedTokenizer.next("");
                        java.lang.String s8 = com.maddox.util.SharedTokenizer.next("");
                        if(s8.startsWith("Ships."))
                        {
                            int l3 = com.maddox.util.SharedTokenizer.next(0);
                            if(l3 == 0)
                                sectfile.lineAdd(i2, s4);
                            else
                            if(l3 == 1 && OUR && !SCRAMBLE)
                                sectfile.lineAdd(i2, s4);
                            else
                            if(l3 == 2 && !OUR && !SCRAMBLE)
                            {
                                sectfile.lineAdd(i2, s4);
                            } else
                            {
                                java.lang.String s10 = com.maddox.util.SharedTokenizer.next("");
                                java.lang.String s11 = com.maddox.util.SharedTokenizer.next("");
                                sectfile.lineAdd(i2, s6 + " " + s8 + " " + l3 + " " + 5940 + " " + s11);
                            }
                        } else
                        {
                            sectfile.lineAdd(i2, s4);
                        }
                    }

                    sectfile.sectionRemove(k1);
                }
            }
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

    public void save()
    {
        try
        {
            ssect = new SectFile();
            ssect.sectionAdd("states");
            ioState.save();
            ssect.set("states", "head", ioState, false);
            for(int i = 0; i < 8; i++)
                ssect.set("states", "wing" + i, wing[i], false);

        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("sorry, cant save");
            com.maddox.gwindow.GWindowMessageBox gwindowmessagebox = new GWindowMessageBox(client, 20F, true, "Error", "Can't save data file", 3, 0.0F);
        }
        com.maddox.il2.gui.GUIQuickSave guiquicksave = (com.maddox.il2.gui.GUIQuickSave)com.maddox.il2.game.GameState.get(24);
        guiquicksave.sect = ssect;
    }

    public void load()
    {
        if(ssect == null)
            return;
        try
        {
            ssect.get("states", "head", ioState);
            for(int i = 0; i < 8; i++)
                ssect.get("states", "wing" + i, wing[i]);

        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("sorry, data corrupt");
            com.maddox.gwindow.GWindowMessageBox gwindowmessagebox = new GWindowMessageBox(client, 20F, true, "Error", "Data file corrupt", 3, 0.0F);
        }
    }

    public void fillArrayPlanes()
    {
        java.lang.String s = "com/maddox/il2/objects/air.ini";
        com.maddox.rts.SectFile sectfile = new SectFile(s, 0);
        int i = sectfile.sections();
        if(i <= 0)
            throw new RuntimeException("GUIQuick: file '" + s + "' is empty");
        for(int j = 0; j < i; j++)
        {
            java.lang.String s1 = sectfile.sectionName(j);
            int k = sectfile.vars(j);
            for(int l = 0; l < k; l++)
            {
                java.lang.String s2 = sectfile.var(j, l);
                com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.value(j, l));
                java.lang.String s3 = numbertokenizer.next((java.lang.String)null);
                boolean flag = true;
                while(numbertokenizer.hasMoreTokens()) 
                    if("NOQUICK".equals(numbertokenizer.next()))
                    {
                        flag = false;
                        break;
                    }
                if(flag)
                {
                    java.lang.Class class1 = null;
                    try
                    {
                        class1 = com.maddox.rts.ObjIO.classForName(s3);
                    }
                    catch(java.lang.Exception exception)
                    {
                        throw new RuntimeException("PlMisAir: class '" + s3 + "' not found");
                    }
                    com.maddox.il2.gui.ItemAir itemair = new ItemAir(s2, class1, s3);
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
        OUR = true;
        SCRAMBLE = false;
        LEVEL = true;
        r01 = "r01";
        r010 = "r010";
        g01 = "g01";
        g010 = "g010";
        playerPlane = new ArrayList();
        aiPlane = new ArrayList();
        playerPlaneC = new ArrayList();
        aiPlaneC = new ArrayList();
        wing = new com.maddox.il2.gui.ItemWing[8];
        dlg = new com.maddox.il2.gui.ItemDlg[8];
        ioState = new IOState();
        bPlaneArrestor = false;
        fillArrayPlanes();
        for(int i = 0; i < 8; i++)
            wing[i] = new ItemWing(i);

        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = Localize("quick.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        com.maddox.gwindow.GTexture gtexture1 = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons;
        bArmy = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 0.0F, 48F, 48F));
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bLoad = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bSave = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bFly = (com.maddox.il2.gui.GUIButton)dialogClient.addDefault(new GUIButton(dialogClient, gtexture, 0.0F, 192F, 48F, 48F));
        bDiff = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        wSituation = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wMap = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wTarget = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wPos = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wDefence = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wAltitude = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wWeather = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wCldHeight = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wTimeHour = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wTimeMins = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wLevel = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wLevel.add(Localize("quick.0"));
        wLevel.add(Localize("quick.+"));
        wLevel.add(Localize("quick.-"));
        wLevel.setEditable(false);
        wLevel.setSelected(0, true, false);
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
        wCldHeight.setEditable(true);
        wCldHeight.setSelected(6, true, false);
        wCldHeight.setNumericOnly(true);
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
        for(int k = 0; k < 8; k++)
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

        for(int j1 = 0; j1 < _mapKey.length; j1++)
        {
            java.lang.String s = "quick" + _mapKey[j1];
            java.lang.String s1 = com.maddox.il2.game.I18N.map(s);
            if(!s.equals(s1))
                wMap.add(com.maddox.il2.game.I18N.map(s1));
            else
                wMap.add(com.maddox.il2.game.I18N.map(_mapKey[j1]));
        }

        wMap.setEditable(false);
        wMap.setSelected(0, true, true);
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.rts.SectFile ssect;
    public com.maddox.gwindow.GTexture cross;
    public com.maddox.gwindow.GTexture star;
    public boolean OUR;
    public boolean SCRAMBLE;
    public boolean LEVEL;
    public com.maddox.il2.gui.GUIButton bArmy;
    public com.maddox.il2.gui.GUIButton bExit;
    public com.maddox.il2.gui.GUIButton bLoad;
    public com.maddox.il2.gui.GUIButton bSave;
    public com.maddox.il2.gui.GUIButton bFly;
    public com.maddox.il2.gui.GUIButton bDiff;
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
    private java.lang.String r01;
    private java.lang.String r010;
    private java.lang.String g01;
    private java.lang.String g010;
    private java.lang.String _mapKey[] = {
        "Okinawa", "NetCoralSea", "Net8Islands", "Smolensk", "Moscow", "Crimea", "Kuban", "Bessarabia", "MTO", "SlovakiaW", 
        "SlovakiaS"
    };
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
    static java.lang.Class class$com$maddox$il2$objects$air$TypeTransport; /* synthetic field */

    static 
    {
        com.maddox.rts.ObjIO.fields(com.maddox.il2.gui.GUIQuick$IOState.class, new java.lang.String[] {
            "our", "situation", "map", "target", "defence", "altitude", "weather", "timeH", "timeM", "pos", 
            "cldheight", "scramble"
        });
        com.maddox.rts.ObjIO.validate(com.maddox.il2.gui.GUIQuick$IOState.class, "loaded");
        com.maddox.rts.ObjIO.fields(com.maddox.il2.gui.GUIQuick$ItemWing.class, new java.lang.String[] {
            "planes", "weapon", "regiment", "skin", "noseart", "pilot", "numberOn", "fuel", "skill"
        });
        com.maddox.rts.ObjIO.accessStr(com.maddox.il2.gui.GUIQuick$ItemWing.class, "plane", "getPlane", "setPlane");
        com.maddox.rts.ObjIO.validate(com.maddox.il2.gui.GUIQuick$ItemWing.class, "loaded");
    }


















}

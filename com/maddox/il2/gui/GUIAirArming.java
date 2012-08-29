// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIAirArming.java

package com.maddox.il2.gui;

import com.maddox.JGP.Color4f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BmpUtils;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.GUIRenders;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.LightEnv;
import com.maddox.il2.engine.LightEnvXY;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.Renders;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetFileServerNoseart;
import com.maddox.il2.net.NetFileServerPilot;
import com.maddox.il2.net.NetFileServerReg;
import com.maddox.il2.net.NetFileServerSkin;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.NetUserRegiment;
import com.maddox.il2.objects.ActorSimpleHMesh;
import com.maddox.il2.objects.ActorSimpleMesh;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.PaintScheme;
import com.maddox.rts.HomePath;
import com.maddox.rts.LDRres;
import com.maddox.rts.NetEnv;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SectFile;
import com.maddox.util.HashMapExt;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUISwitchBox3, GUIButton, GUINetAircraft, GUIDialogClient, 
//            GUISeparate

public class GUIAirArming extends com.maddox.il2.game.GameState
{
    static class UserRegiment
    {

        protected java.lang.String country;
        protected java.lang.String branch;
        protected java.lang.String fileName;
        protected char id[];
        protected java.lang.String shortInfo;
        protected int gruppeNumber;

        public UserRegiment(java.lang.String s)
            throws java.lang.Exception
        {
            id = new char[2];
            gruppeNumber = 1;
            fileName = s;
            java.lang.String s1 = com.maddox.il2.game.Main.cur().netFileServerReg.primaryPath();
            java.util.PropertyResourceBundle propertyresourcebundle = new PropertyResourceBundle(new SFSInputStream(s1 + "/" + s));
            country = propertyresourcebundle.getString("country");
            country = country.toLowerCase().intern();
            branch = country;
            country = com.maddox.il2.ai.Regiment.getCountryFromBranch(branch);
            java.lang.String s2 = propertyresourcebundle.getString("id");
            id[0] = s2.charAt(0);
            id[1] = s2.charAt(1);
            if((id[0] < '0' || id[0] > '9') && (id[0] < 'A' || id[0] > 'Z'))
                throw new RuntimeException("Bad regiment id[0]");
            if((id[1] < '0' || id[1] > '9') && (id[1] < 'A' || id[1] > 'Z'))
                throw new RuntimeException("Bad regiment id[1]");
            try
            {
                java.lang.String s3 = propertyresourcebundle.getString("short");
                if(s3 == null || s3.length() == 0)
                    s3 = s;
                shortInfo = s3;
            }
            catch(java.lang.Exception exception)
            {
                shortInfo = s;
            }
            try
            {
                java.lang.String s4 = propertyresourcebundle.getString("gruppeNumber");
                if(s4 != null)
                {
                    try
                    {
                        gruppeNumber = java.lang.Integer.parseInt(s4);
                    }
                    catch(java.lang.Exception exception2) { }
                    if(gruppeNumber < 1)
                        gruppeNumber = 1;
                    if(gruppeNumber > 5)
                        gruppeNumber = 5;
                }
            }
            catch(java.lang.Exception exception1) { }
        }
    }

    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            com.maddox.il2.ai.UserCfg usercfg = com.maddox.il2.ai.World.cur().userCfg;
            if(gwindow == bJoy)
            {
                com.maddox.il2.game.Main.stateStack().push(53);
                return true;
            }
            if(gwindow == cAircraft)
            {
                if(com.maddox.il2.gui.GUIAirArming.stateId != 2 && com.maddox.il2.gui.GUIAirArming.stateId != 4)
                    return true;
                if(com.maddox.il2.gui.GUIAirArming.stateId == 2)
                    usercfg.netAirName = airName();
                else
                if(com.maddox.il2.gui.GUIAirArming.stateId == 4)
                    quikPlane = airName();
                if(zutiSelectedBornPlace == null)
                    fillWeapons();
                else
                    zutiFillWeapons(zutiSelectedBornPlace);
                selectWeapon();
                fillSkins();
                selectSkin();
                selectNoseart();
                setMesh();
                prepareMesh();
                prepareWeapons();
                prepareSkin();
                preparePilot();
                prepareNoseart();
                return true;
            }
            if(gwindow == cCountry)
            {
                if(com.maddox.il2.gui.GUIAirArming.stateId != 2 && com.maddox.il2.gui.GUIAirArming.stateId != 4)
                    return true;
                fillRegiments();
                java.lang.String s = (java.lang.String)countryLst.get(cCountry.getSelected());
                java.util.ArrayList arraylist = (java.util.ArrayList)regList.get(s);
                java.lang.Object obj = arraylist.get(cRegiment.getSelected());
                if(com.maddox.il2.gui.GUIAirArming.stateId == 2)
                {
                    if(obj instanceof com.maddox.il2.ai.Regiment)
                        usercfg.netRegiment = ((com.maddox.il2.ai.Regiment)obj).name();
                    else
                        usercfg.netRegiment = ((com.maddox.il2.gui.UserRegiment)obj).fileName;
                } else
                if(com.maddox.il2.gui.GUIAirArming.stateId == 4)
                    quikRegiment = ((com.maddox.il2.ai.Regiment)obj).name();
                selectNoseart();
                setMesh();
                prepareMesh();
                prepareWeapons();
                prepareSkin();
                preparePilot();
                prepareNoseart();
                return true;
            }
            if(gwindow == cRegiment)
            {
                if(com.maddox.il2.gui.GUIAirArming.stateId != 2 && com.maddox.il2.gui.GUIAirArming.stateId != 4)
                    return true;
                java.lang.String s1 = (java.lang.String)countryLst.get(cCountry.getSelected());
                java.util.ArrayList arraylist1 = (java.util.ArrayList)regList.get(s1);
                java.lang.Object obj1 = arraylist1.get(cRegiment.getSelected());
                if(com.maddox.il2.gui.GUIAirArming.stateId == 2)
                {
                    if(obj1 instanceof com.maddox.il2.ai.Regiment)
                        usercfg.netRegiment = ((com.maddox.il2.ai.Regiment)obj1).name();
                    else
                        usercfg.netRegiment = ((com.maddox.il2.gui.UserRegiment)obj1).fileName;
                } else
                if(com.maddox.il2.gui.GUIAirArming.stateId == 4)
                    quikRegiment = ((com.maddox.il2.ai.Regiment)obj1).name();
                prepareMesh();
                return true;
            }
            if(gwindow == cWeapon)
            {
                if(!bEnableWeaponChange)
                    return true;
                if(isNet())
                    usercfg.setWeapon(airName(), (java.lang.String)weaponNames.get(cWeapon.getSelected()));
                else
                if(com.maddox.il2.gui.GUIAirArming.stateId == 4)
                    quikWeapon = (java.lang.String)weaponNames.get(cWeapon.getSelected());
                else
                    com.maddox.il2.game.Main.cur().currentMissionFile.set(planeName, "weapons", (java.lang.String)weaponNames.get(cWeapon.getSelected()));
                prepareWeapons();
                return true;
            }
            if(gwindow == cSkin)
            {
                int k = cSkin.getSelected();
                if(com.maddox.il2.gui.GUIAirArming.stateId == 4)
                {
                    if(k == 0)
                        quikSkin[quikCurPlane] = null;
                    else
                        quikSkin[quikCurPlane] = cSkin.get(k);
                } else
                if(k == 0)
                    usercfg.setSkin(airName(), null);
                else
                    usercfg.setSkin(airName(), cSkin.get(k));
                prepareSkin();
                return true;
            }
            if(gwindow == cNoseart)
            {
                int l = cNoseart.getSelected();
                if(com.maddox.il2.gui.GUIAirArming.stateId == 4)
                {
                    if(l == 0)
                        quikNoseart[quikCurPlane] = null;
                    else
                        quikNoseart[quikCurPlane] = cNoseart.get(l);
                } else
                if(l == 0)
                    usercfg.setNoseart(airName(), null);
                else
                    usercfg.setNoseart(airName(), cNoseart.get(l));
                if(l == 0)
                {
                    setMesh();
                    prepareMesh();
                    prepareWeapons();
                    prepareSkin();
                    preparePilot();
                }
                prepareNoseart();
                return true;
            }
            if(gwindow == cPilot)
            {
                if(com.maddox.il2.gui.GUIAirArming.stateId == 4)
                {
                    if(cPilot.getSelected() == 0)
                        quikPilot[quikCurPlane] = null;
                    else
                        quikPilot[quikCurPlane] = cPilot.getValue();
                } else
                if(cPilot.getSelected() == 0)
                    usercfg.netPilot = null;
                else
                    usercfg.netPilot = cPilot.getValue();
                preparePilot();
                return true;
            }
            if(gwindow == wMashineGun)
            {
                usercfg.coverMashineGun = clampValue(wMashineGun, usercfg.coverMashineGun, 100F, 1000F);
                return true;
            }
            if(gwindow == wCannon)
            {
                usercfg.coverCannon = clampValue(wCannon, usercfg.coverCannon, 100F, 1000F);
                return true;
            }
            if(gwindow == wRocket)
            {
                usercfg.coverRocket = clampValue(wRocket, usercfg.coverRocket, 100F, 1000F);
                return true;
            }
            if(gwindow == wRocketDelay)
            {
                usercfg.rocketDelay = clampValue(wRocketDelay, usercfg.rocketDelay, 1.0F, 60F);
                return true;
            }
            if(gwindow == wBombDelay)
            {
                usercfg.bombDelay = clampValue(wBombDelay, usercfg.bombDelay, 0.0F, 10F);
                return true;
            }
            if(gwindow == cFuel)
            {
                if(bEnableWeaponChange)
                    if(isNet())
                        usercfg.fuel = (cFuel.getSelected() + 1) * 10;
                    else
                    if(com.maddox.il2.gui.GUIAirArming.stateId == 4)
                        quikFuel = (cFuel.getSelected() + 1) * 10;
                    else
                        com.maddox.il2.game.Main.cur().currentMissionFile.set(planeName, "Fuel", (cFuel.getSelected() + 1) * 10);
            } else
            {
                if(gwindow == cSquadron)
                    if(com.maddox.il2.gui.GUIAirArming.stateId != 2)
                    {
                        return true;
                    } else
                    {
                        usercfg.netSquadron = cSquadron.getSelected();
                        prepareMesh();
                        return true;
                    }
                if(gwindow == wNumber)
                {
                    if(com.maddox.il2.gui.GUIAirArming.stateId != 2)
                        return true;
                    java.lang.String s2 = wNumber.getValue();
                    int i1 = usercfg.netTacticalNumber;
                    try
                    {
                        i1 = java.lang.Integer.parseInt(s2);
                    }
                    catch(java.lang.Exception exception) { }
                    if(i1 < 1)
                        i1 = 1;
                    if(i1 > 99)
                        i1 = 99;
                    wNumber.setValue("" + i1, false);
                    usercfg.netTacticalNumber = i1;
                    prepareMesh();
                    return true;
                }
                if(gwindow == sNumberOn)
                {
                    if(com.maddox.il2.gui.GUIAirArming.stateId == 4)
                        quikNumberOn[quikCurPlane] = sNumberOn.isChecked();
                    else
                        usercfg.netNumberOn = sNumberOn.isChecked();
                    prepareMesh();
                } else
                {
                    if(gwindow == cPlane)
                    {
                        if(com.maddox.il2.gui.GUIAirArming.stateId != 4)
                            return true;
                        quikCurPlane = cPlane.getSelected();
                        if(quikPlayer && quikCurPlane == 0)
                        {
                            wMashineGun.showWindow();
                            wCannon.showWindow();
                            wRocket.showWindow();
                            wRocketDelay.showWindow();
                            wBombDelay.showWindow();
                        } else
                        {
                            wMashineGun.hideWindow();
                            wCannon.hideWindow();
                            wRocket.hideWindow();
                            wRocketDelay.hideWindow();
                            wBombDelay.hideWindow();
                        }
                        sNumberOn.setChecked(quikNumberOn[quikCurPlane], false);
                        fillSkins();
                        selectSkin();
                        selectNoseart();
                        fillPilots();
                        selectPilot();
                        setMesh();
                        prepareMesh();
                        prepareWeapons();
                        prepareSkin();
                        preparePilot();
                        prepareNoseart();
                        return true;
                    }
                    if(gwindow == bBack)
                        switch(com.maddox.il2.gui.GUIAirArming.stateId)
                        {
                        case 2: // '\002'
                            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).replicateNetUserRegiment();
                            // fall through

                        case 3: // '\003'
                            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).replicateSkin();
                            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).replicateNoseart();
                            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).replicatePilot();
                            // fall through

                        case 4: // '\004'
                        default:
                            usercfg.saveConf();
                            destroyMesh();
                            airNames.clear();
                            cAircraft.clear(false);
                            regList.clear();
                            regHash.clear();
                            cRegiment.clear(false);
                            countryLst.clear();
                            cCountry.clear(false);
                            cWeapon.clear();
                            weaponNames.clear();
                            cSkin.clear(false);
                            cPilot.clear(false);
                            cNoseart.clear(false);
                            com.maddox.il2.game.Main.stateStack().pop();
                            return true;
                        }
                }
            }
            return super.notify(gwindow, i, j);
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(628F), y1024(156F), x1024(364F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(628F), y1024(300F), x1024(364F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(640F), x1024(962F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(644F), y1024(12F), x1024(332F), y1024(32F), 1, i18n("neta.Aircraft"));
            draw(x1024(644F), y1024(76F), x1024(332F), y1024(32F), 1, i18n("neta.WeaponLoadout"));
            draw(x1024(644F), y1024(156F), x1024(332F), y1024(32F), 1, i18n("neta.Country"));
            draw(x1024(644F), y1024(220F), x1024(332F), y1024(32F), 1, i18n("neta.Regiment"));
            draw(x1024(644F), y1024(300F), x1024(332F), y1024(32F), 1, i18n("neta.Skin"));
            draw(x1024(644F), y1024(364F), x1024(332F), y1024(32F), 1, i18n("neta.Pilot"));
            if(com.maddox.il2.gui.GUIAirArming.stateId == 2)
            {
                draw(x1024(628F), y1024(450F), x1024(220F), y1024(32F), 0, i18n("neta.Number"));
                draw(x1024(644F), y1024(450F), x1024(220F), y1024(32F), 2, i18n("neta.Squadron"));
            } else
            {
                draw(x1024(628F), y1024(450F), x1024(220F), y1024(32F), 0, i18n("neta.NumberOn"));
            }
            com.maddox.il2.gui.GUILookAndFeel guilookandfeel = (com.maddox.il2.gui.GUILookAndFeel)lookAndFeel();
            if(com.maddox.il2.gui.GUIAirArming.stateId != 4 || quikPlayer && quikCurPlane == 0)
            {
                draw(x1024(32F), y1024(496F), x1024(576F), y1024(32F), 1, i18n("neta.WeaponConver"));
                draw(x1024(32F), y1024(544F), x1024(160F), y1024(32F), 2, i18n("neta.MachineGuns") + " ");
                draw(x1024(32F), y1024(592F), x1024(160F), y1024(32F), 2, i18n("neta.Cannons") + " ");
                draw(x1024(272F), y1024(544F), x1024(48F), y1024(32F), 0, " " + i18n("neta.m."));
                draw(x1024(272F), y1024(592F), x1024(48F), y1024(32F), 0, " " + i18n("neta.m."));
                draw(x1024(320F), y1024(544F), x1024(160F), y1024(32F), 2, i18n("neta.Rockets") + " ");
                draw(x1024(320F), y1024(592F), x1024(160F), y1024(32F), 2, i18n("neta.RocketDelay") + " ");
                draw(x1024(560F), y1024(544F), x1024(48F), y1024(32F), 0, " " + i18n("neta.m."));
                draw(x1024(560F), y1024(592F), x1024(48F), y1024(32F), 0, " " + i18n("neta.sec."));
                draw(x1024(608F), y1024(544F), x1024(224F), y1024(32F), 2, i18n("neta.BombDelay") + " ");
                draw(x1024(928F) - guilookandfeel.getVScrollBarW(), y1024(544F), x1024(48F), y1024(32F), 0, " " + i18n("neta.sec."));
            }
            draw(x1024(608F), y1024(592F), x1024(224F), y1024(32F), 2, i18n("neta.FuelQuantity") + " ");
            draw(x1024(928F), y1024(592F), x1024(48F), y1024(32F), 0, " %");
            draw(x1024(96F), y1024(656F), x1024(320F), y1024(48F), 0, i18n("neta.Apply"));
            draw(x1024(326F), y1024(656F), x1024(620F), y1024(48F), 0, i18n("neta.Joystick"));
            if(cNoseart.isVisible())
                draw(x1024(292F), y1024(496F), x1024(320F), y1024(32F), 2, i18n("neta.Noseart"));
            setCanvasColorWHITE();
            guilookandfeel.drawBevel(this, x1024(32F), y1024(32F), x1024(564F), y1024(432F), guilookandfeel.bevelComboDown, guilookandfeel.basicelements);
        }

        public void setPosSize()
        {
            set1024PosSize(0.0F, 32F, 1024F, 736F);
            wMashineGun.set1024PosSize(192F, 544F, 80F, 32F);
            wCannon.set1024PosSize(192F, 592F, 80F, 32F);
            wRocket.set1024PosSize(480F, 544F, 80F, 32F);
            wRocketDelay.set1024PosSize(480F, 592F, 80F, 32F);
            com.maddox.il2.gui.GUILookAndFeel guilookandfeel = (com.maddox.il2.gui.GUILookAndFeel)lookAndFeel();
            wBombDelay.setPosSize(x1024(832F), y1024(544F), x1024(96F) - guilookandfeel.getVScrollBarW(), y1024(32F));
            cFuel.set1024PosSize(832F, 592F, 96F, 32F);
            if(com.maddox.il2.gui.GUIAirArming.stateId == 4)
                cAircraft.set1024PosSize(628F, 44F, 298F, 32F);
            else
                cAircraft.set1024PosSize(628F, 44F, 364F, 32F);
            cPlane.set1024PosSize(932F, 44F, 60F, 32F);
            cWeapon.set1024PosSize(628F, 108F, 364F, 32F);
            cCountry.set1024PosSize(628F, 188F, 364F, 32F);
            cRegiment.set1024PosSize(628F, 252F, 364F, 32F);
            cSkin.set1024PosSize(628F, 332F, 364F, 32F);
            cPilot.set1024PosSize(628F, 396F, 364F, 32F);
            wNumber.set1024PosSize(704F, 450F, 64F, 32F);
            cSquadron.set1024PosSize(896F, 450F, 96F, 32F);
            sNumberOn.setPosC(x1024(944F), y1024(466F));
            cNoseart.set1024PosSize(628F, 496F, 364F, 32F);
            com.maddox.il2.gui.GUILookAndFeel guilookandfeel1 = (com.maddox.il2.gui.GUILookAndFeel)lookAndFeel();
            com.maddox.gwindow.GBevel gbevel = guilookandfeel1.bevelComboDown;
            renders.setPosSize(x1024(32F) + gbevel.L.dx, y1024(32F) + gbevel.T.dy, x1024(564F) - gbevel.L.dx - gbevel.R.dx, y1024(432F) - gbevel.T.dy - gbevel.B.dy);
            bBack.setPosC(x1024(56F), y1024(680F));
            bJoy.setPosC(x1024(286F), y1024(680F));
        }

        public DialogClient()
        {
        }
    }

    class _Render3D extends com.maddox.il2.engine.Render
    {

        public void preRender()
        {
            if(com.maddox.il2.engine.Actor.isValid(actorMesh))
            {
                if(animateMeshA != 0.0F || animateMeshT != 0.0F)
                {
                    actorMesh.pos.getAbs(_orient);
                    _orient.set(_orient.azimut() + animateMeshA * client.root.deltaTimeSec, _orient.tangage() + animateMeshT * client.root.deltaTimeSec, 0.0F);
                    float f;
                    for(f = _orient.getYaw(); f > 360F; f -= 360F);
                    for(; f < 0.0F; f += 360F);
                    _orient.setYaw(f);
                    actorMesh.pos.setAbs(_orient);
                    actorMesh.pos.reset();
                }
                actorMesh.draw.preRender(actorMesh);
                for(int i = 0; i < weaponMeshs.size(); i++)
                {
                    com.maddox.il2.engine.ActorMesh actormesh = (com.maddox.il2.engine.ActorMesh)weaponMeshs.get(i);
                    if(com.maddox.il2.engine.Actor.isValid(actormesh))
                        actormesh.draw.preRender(actormesh);
                }

            }
        }

        public void render()
        {
            if(com.maddox.il2.engine.Actor.isValid(actorMesh))
            {
                com.maddox.il2.engine.Render.prepareStates();
                actorMesh.draw.render(actorMesh);
                for(int i = 0; i < weaponMeshs.size(); i++)
                {
                    com.maddox.il2.engine.ActorMesh actormesh = (com.maddox.il2.engine.ActorMesh)weaponMeshs.get(i);
                    if(com.maddox.il2.engine.Actor.isValid(actormesh))
                        actormesh.draw.render(actormesh);
                }

            }
        }

        public _Render3D(com.maddox.il2.engine.Renders renders1, float f)
        {
            super(renders1, f);
            setClearColor(new Color4f(0.5F, 0.78F, 0.92F, 1.0F));
            useClearStencil(true);
        }
    }


    private boolean isNet()
    {
        return stateId == 2 || stateId == 3;
    }

    private java.lang.String airName()
    {
        return (java.lang.String)airNames.get(cAircraft.getSelected());
    }

    public void _enter()
    {
        zutiSelectedBornPlace = null;
        com.maddox.il2.ai.UserCfg usercfg;
        if(resCountry == null)
            resCountry = java.util.ResourceBundle.getBundle("i18n/country", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
        bEnableWeaponChange = true;
        cFuel.setEnable(true);
        usercfg = com.maddox.il2.ai.World.cur().userCfg;
        wMashineGun.setValue("" + usercfg.coverMashineGun, false);
        wCannon.setValue("" + usercfg.coverCannon, false);
        wRocket.setValue("" + usercfg.coverRocket, false);
        wRocketDelay.setValue("" + usercfg.rocketDelay, false);
        wBombDelay.setValue("" + usercfg.bombDelay, false);
        com.maddox.rts.SectFile sectfile = com.maddox.il2.game.Main.cur().currentMissionFile;
        if(sectfile == null)
        {
            com.maddox.il2.ai.World.cur().setWeaponsConstant(false);
        } else
        {
            int j = sectfile.get("MAIN", "WEAPONSCONSTANT", 0, 0, 1);
            com.maddox.il2.ai.World.cur().setWeaponsConstant(j == 1);
        }
        stateId;
        JVM INSTR tableswitch 0 4: default 4061
    //                   0 288
    //                   1 288
    //                   2 1347
    //                   3 878
    //                   4 2983;
           goto _L1 _L2 _L2 _L3 _L4 _L5
_L1:
        break; /* Loop/switch isn't completed */
_L2:
        wMashineGun.showWindow();
        wCannon.showWindow();
        wRocket.showWindow();
        wRocketDelay.showWindow();
        wBombDelay.showWindow();
        cPlane.hideWindow();
        wNumber.hideWindow();
        cSquadron.hideWindow();
        sNumberOn.showWindow();
        sNumberOn.setChecked(usercfg.netNumberOn, false);
        com.maddox.rts.SectFile sectfile1 = com.maddox.il2.game.Main.cur().currentMissionFile;
        java.lang.String s = sectfile1.get("MAIN", "player", (java.lang.String)null);
        planeName = s;
        java.lang.String s1 = s.substring(0, s.length() - 1);
        java.lang.String s2 = s1.substring(0, s1.length() - 1);
        com.maddox.il2.ai.Regiment regiment = (com.maddox.il2.ai.Regiment)com.maddox.il2.engine.Actor.getByName(s2);
        java.lang.String s4 = sectfile1.get(planeName, "Class", (java.lang.String)null);
        java.lang.Class class2 = com.maddox.rts.ObjIO.classForName(s4);
        java.lang.String s9 = com.maddox.rts.Property.stringValue(class2, "keyName", null);
        airNames.add(s9);
        cAircraft.add(com.maddox.il2.game.I18N.plane(s9));
        cAircraft.setSelected(0, true, false);
        countryLst.add(regiment.branch());
        cCountry.add(resCountry.getString(regiment.branch()));
        cCountry.setSelected(0, true, false);
        java.util.ArrayList arraylist6 = new ArrayList();
        arraylist6.add(regiment);
        regList.put(regiment.branch(), arraylist6);
        cRegiment.add(regiment.shortInfo());
        cRegiment.setSelected(0, true, false);
        int k3 = sectfile1.get(planeName, "Fuel", 100, 0, 100);
        if(k3 <= 10)
            cFuel.setSelected(0, true, false);
        else
        if(k3 <= 20)
            cFuel.setSelected(1, true, false);
        else
        if(k3 <= 30)
            cFuel.setSelected(2, true, false);
        else
        if(k3 <= 40)
            cFuel.setSelected(3, true, false);
        else
        if(k3 <= 50)
            cFuel.setSelected(4, true, false);
        else
        if(k3 <= 60)
            cFuel.setSelected(5, true, false);
        else
        if(k3 <= 70)
            cFuel.setSelected(6, true, false);
        else
        if(k3 <= 80)
            cFuel.setSelected(7, true, false);
        else
        if(k3 <= 90)
            cFuel.setSelected(8, true, false);
        else
            cFuel.setSelected(9, true, false);
        playerNum = sectfile1.get("Main", "playerNum", 0);
        if(stateId == 1)
            bEnableWeaponChange = playerNum == 0 && !com.maddox.il2.ai.World.cur().isWeaponsConstant();
        else
            bEnableWeaponChange = !com.maddox.il2.ai.World.cur().isWeaponsConstant();
        cFuel.setEnable(bEnableWeaponChange);
        break; /* Loop/switch isn't completed */
_L4:
        playerNum = -1;
        wMashineGun.showWindow();
        wCannon.showWindow();
        wRocket.showWindow();
        wRocketDelay.showWindow();
        wBombDelay.showWindow();
        cPlane.hideWindow();
        wNumber.hideWindow();
        cSquadron.hideWindow();
        sNumberOn.showWindow();
        sNumberOn.setChecked(usercfg.netNumberOn, false);
        planeName = com.maddox.il2.gui.GUINetAircraft.selectedWingName();
        bEnableWeaponChange = !com.maddox.il2.ai.World.cur().isWeaponsConstant();
        int i = (int)usercfg.fuel;
        if(!bEnableWeaponChange)
        {
            com.maddox.rts.SectFile sectfile2 = com.maddox.il2.game.Main.cur().currentMissionFile;
            i = sectfile2.get(planeName, "Fuel", 100, 0, 100);
        }
        if(i <= 10)
            cFuel.setSelected(0, true, false);
        else
        if(i <= 20)
            cFuel.setSelected(1, true, false);
        else
        if(i <= 30)
            cFuel.setSelected(2, true, false);
        else
        if(i <= 40)
            cFuel.setSelected(3, true, false);
        else
        if(i <= 50)
            cFuel.setSelected(4, true, false);
        else
        if(i <= 60)
            cFuel.setSelected(5, true, false);
        else
        if(i <= 70)
            cFuel.setSelected(6, true, false);
        else
        if(i <= 80)
            cFuel.setSelected(7, true, false);
        else
        if(i <= 90)
            cFuel.setSelected(8, true, false);
        else
            cFuel.setSelected(9, true, false);
        cFuel.setEnable(bEnableWeaponChange);
        airNames.add(com.maddox.il2.gui.GUINetAircraft.selectedAircraftKeyName());
        cAircraft.add(com.maddox.il2.gui.GUINetAircraft.selectedAircraftName());
        cAircraft.setSelected(0, true, false);
        countryLst.add(com.maddox.il2.gui.GUINetAircraft.selectedRegiment().branch());
        cCountry.add(resCountry.getString(com.maddox.il2.gui.GUINetAircraft.selectedRegiment().branch()));
        cCountry.setSelected(0, true, false);
        java.util.ArrayList arraylist1 = new ArrayList();
        arraylist1.add(com.maddox.il2.gui.GUINetAircraft.selectedRegiment());
        regList.put(com.maddox.il2.gui.GUINetAircraft.selectedRegiment().branch(), arraylist1);
        cRegiment.add(com.maddox.il2.gui.GUINetAircraft.selectedRegiment().shortInfo());
        cRegiment.setSelected(0, true, false);
        break; /* Loop/switch isn't completed */
_L3:
        java.util.ArrayList arraylist2;
        java.util.TreeMap treemap1;
        playerNum = -1;
        wMashineGun.showWindow();
        wCannon.showWindow();
        wRocket.showWindow();
        wRocketDelay.showWindow();
        wBombDelay.showWindow();
        cPlane.hideWindow();
        wNumber.showWindow();
        cSquadron.showWindow();
        sNumberOn.hideWindow();
        if(usercfg.fuel <= 10F)
            cFuel.setSelected(0, true, false);
        else
        if(usercfg.fuel <= 20F)
            cFuel.setSelected(1, true, false);
        else
        if(usercfg.fuel <= 30F)
            cFuel.setSelected(2, true, false);
        else
        if(usercfg.fuel <= 40F)
            cFuel.setSelected(3, true, false);
        else
        if(usercfg.fuel <= 50F)
            cFuel.setSelected(4, true, false);
        else
        if(usercfg.fuel <= 60F)
            cFuel.setSelected(5, true, false);
        else
        if(usercfg.fuel <= 70F)
            cFuel.setSelected(6, true, false);
        else
        if(usercfg.fuel <= 80F)
            cFuel.setSelected(7, true, false);
        else
        if(usercfg.fuel <= 90F)
            cFuel.setSelected(8, true, false);
        else
            cFuel.setSelected(9, true, false);
        com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
        int k = netuser.getBornPlace();
        com.maddox.il2.net.BornPlace bornplace = (com.maddox.il2.net.BornPlace)com.maddox.il2.ai.World.cur().bornPlaces.get(k);
        zutiSelectedBornPlace = bornplace;
        arraylist2 = zutiSelectedBornPlace.zutiHomeBaseCountries;
        java.util.ArrayList arraylist3 = zutiSelectedBornPlace.zutiGetAvailablePlanesList();
        if(arraylist3 != null)
        {
            for(int k1 = 0; k1 < arraylist3.size(); k1++)
            {
                java.lang.String s6 = (java.lang.String)arraylist3.get(k1);
                java.lang.Class class3 = (java.lang.Class)com.maddox.rts.Property.value(s6, "airClass", null);
                if(class3 != null && !airNames.contains(s6))
                {
                    airNames.add(s6);
                    cAircraft.add(com.maddox.il2.game.I18N.plane(s6));
                }
            }

        }
        if(airNames.size() == 0)
        {
            java.util.ArrayList arraylist4 = com.maddox.il2.game.Main.cur().airClasses;
            for(int j2 = 0; j2 < arraylist4.size(); j2++)
            {
                java.lang.Class class4 = (java.lang.Class)arraylist4.get(j2);
                java.lang.String s10 = com.maddox.rts.Property.stringValue(class4, "keyName");
                if(com.maddox.rts.Property.containsValue(class4, "cockpitClass"))
                {
                    airNames.add(s10);
                    cAircraft.add(com.maddox.il2.game.I18N.plane(s10));
                }
            }

        }
        java.util.List list1 = com.maddox.il2.ai.Regiment.getAll();
        treemap1 = new TreeMap();
        int k2 = list1.size();
        for(int j3 = 0; j3 < k2; j3++)
        {
            com.maddox.il2.ai.Regiment regiment2 = (com.maddox.il2.ai.Regiment)list1.get(j3);
            java.lang.String s14 = regiment2.name();
            if(regHash.containsKey(s14))
                continue;
            regHash.put(s14, regiment2);
            java.util.ArrayList arraylist8 = (java.util.ArrayList)regList.get(regiment2.branch());
            if(arraylist8 == null)
            {
                java.lang.String s16 = null;
                try
                {
                    s16 = resCountry.getString(regiment2.branch());
                }
                catch(java.lang.Exception exception5)
                {
                    continue;
                }
                arraylist8 = new ArrayList();
                regList.put(regiment2.branch(), arraylist8);
                treemap1.put(s16, regiment2.branch());
            }
            arraylist8.add(regiment2);
        }

        java.lang.String s11;
        java.io.File afile[];
        s11 = com.maddox.il2.game.Main.cur().netFileServerReg.primaryPath();
        java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName(s11, 0));
        afile = file.listFiles();
        if(afile == null) goto _L7; else goto _L6
_L6:
        int i4 = 0;
_L17:
        java.io.File file1;
        if(i4 >= afile.length)
            break; /* Loop/switch isn't completed */
        file1 = afile[i4];
        if(file1.isFile()) goto _L9; else goto _L8
_L9:
        java.lang.String s17 = file1.getName();
        if(!regHash.containsKey(s17)) goto _L10; else goto _L8
_L10:
        java.lang.String s18 = s17.toLowerCase();
        if(!s18.endsWith(".bmp") && !s18.endsWith(".tga") && s18.length() <= 123) goto _L11; else goto _L8
_L11:
        int l4 = com.maddox.il2.engine.BmpUtils.squareSizeBMP8Pal(s11 + "/" + s18 + ".bmp");
        if(l4 == 64 || l4 == 128) goto _L13; else goto _L12
_L12:
        java.lang.System.out.println("File " + s11 + "/" + s18 + ".bmp NOT loaded");
          goto _L8
_L13:
        com.maddox.il2.gui.UserRegiment userregiment;
        java.util.ArrayList arraylist10;
        userregiment = new UserRegiment(s17);
        regHash.put(s17, userregiment);
        arraylist10 = (java.util.ArrayList)regList.get(userregiment.branch);
        if(arraylist10 != null) goto _L15; else goto _L14
_L14:
        java.lang.String s19 = null;
        s19 = resCountry.getString(userregiment.branch);
          goto _L16
        java.lang.Exception exception7;
        exception7;
          goto _L8
_L16:
        arraylist10 = new ArrayList();
        regList.put(userregiment.branch, arraylist10);
        treemap1.put(s19, userregiment.branch);
_L15:
        arraylist10.add(userregiment);
          goto _L8
        java.lang.Exception exception6;
        exception6;
        java.lang.System.out.println(exception6.getMessage());
        java.lang.System.out.println("Regiment " + s17 + " NOT loaded");
_L8:
        i4++;
        if(true) goto _L17; else goto _L7
        java.lang.Exception exception2;
        exception2;
        java.lang.System.out.println(exception2.getMessage());
        exception2.printStackTrace();
_L7:
        java.util.Iterator iterator1 = treemap1.keySet().iterator();
        do
        {
            if(!iterator1.hasNext())
                break;
            java.lang.String s13 = (java.lang.String)iterator1.next();
            if(arraylist2 == null || arraylist2.size() == 0)
            {
                countryLst.add(treemap1.get(s13));
                cCountry.add(s13);
            } else
            if(arraylist2.contains(s13))
            {
                countryLst.add(treemap1.get(s13));
                cCountry.add(s13);
            }
        } while(true);
        treemap1.clear();
        cCountry.setSelected(0, true, false);
        fillRegiments();
        wNumber.setValue("" + usercfg.netTacticalNumber, false);
        cSquadron.setSelected(usercfg.netSquadron, true, false);
        if(usercfg.netRegiment != null)
        {
            java.lang.Object obj1 = regHash.get(usercfg.netRegiment);
            if(obj1 != null)
            {
                java.lang.String s15 = null;
                if(obj1 instanceof com.maddox.il2.ai.Regiment)
                    s15 = ((com.maddox.il2.ai.Regiment)obj1).branch();
                else
                    s15 = ((com.maddox.il2.gui.UserRegiment)obj1).branch;
                int j4;
                for(j4 = 0; j4 < countryLst.size() && !s15.equals(countryLst.get(j4)); j4++);
                if(j4 < countryLst.size())
                {
                    cCountry.setSelected(j4, true, false);
                    fillRegiments();
                    java.util.ArrayList arraylist9 = (java.util.ArrayList)regList.get(countryLst.get(j4));
                    if(arraylist9 != null)
                    {
                        int k4 = 0;
                        do
                        {
                            if(k4 >= arraylist9.size())
                                break;
                            if(obj1.equals(arraylist9.get(k4)))
                            {
                                cRegiment.setSelected(k4, true, false);
                                break;
                            }
                            k4++;
                        } while(true);
                    }
                }
            }
        }
        cAircraft.setSelected(-1, false, false);
        try
        {
            int l3 = 0;
            do
            {
                if(l3 >= airNames.size())
                    break;
                if(usercfg.netAirName.equals(airNames.get(l3)))
                {
                    cAircraft.setSelected(l3, true, false);
                    break;
                }
                l3++;
            } while(true);
        }
        catch(java.lang.Exception exception3) { }
        if(cAircraft.getSelected() < 0)
        {
            cAircraft.setSelected(0, true, false);
            usercfg.netAirName = (java.lang.String)airNames.get(0);
        }
        if(usercfg.netRegiment == null && cRegiment.size() > 0)
        {
            cRegiment.setSelected(-1, false, false);
            cRegiment.setSelected(0, true, true);
        }
        break; /* Loop/switch isn't completed */
_L5:
        if(quikPlayer)
        {
            wMashineGun.showWindow();
            wCannon.showWindow();
            wRocket.showWindow();
            wRocketDelay.showWindow();
            wBombDelay.showWindow();
        } else
        {
            wMashineGun.hideWindow();
            wCannon.hideWindow();
            wRocket.hideWindow();
            wRocketDelay.hideWindow();
            wBombDelay.hideWindow();
        }
        cPlane.showWindow();
        wNumber.hideWindow();
        cSquadron.hideWindow();
        sNumberOn.showWindow();
        quikCurPlane = 0;
        sNumberOn.setChecked(quikNumberOn[quikCurPlane], false);
        if(quikFuel <= 10)
            cFuel.setSelected(0, true, false);
        else
        if(quikFuel <= 20)
            cFuel.setSelected(1, true, false);
        else
        if(quikFuel <= 30)
            cFuel.setSelected(2, true, false);
        else
        if(quikFuel <= 40)
            cFuel.setSelected(3, true, false);
        else
        if(quikFuel <= 50)
            cFuel.setSelected(4, true, false);
        else
        if(quikFuel <= 60)
            cFuel.setSelected(5, true, false);
        else
        if(quikFuel <= 70)
            cFuel.setSelected(6, true, false);
        else
        if(quikFuel <= 80)
            cFuel.setSelected(7, true, false);
        else
        if(quikFuel <= 90)
            cFuel.setSelected(8, true, false);
        else
            cFuel.setSelected(9, true, false);
        java.util.ArrayList arraylist = quikListPlane;
        for(int l = 0; l < arraylist.size(); l++)
        {
            java.lang.Class class1 = (java.lang.Class)arraylist.get(l);
            java.lang.String s3 = com.maddox.rts.Property.stringValue(class1, "keyName");
            if(!quikPlayer || com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
            {
                airNames.add(s3);
                cAircraft.add(com.maddox.il2.game.I18N.plane(s3));
            }
        }

        java.util.List list = com.maddox.il2.ai.Regiment.getAll();
        java.util.TreeMap treemap = new TreeMap();
        int i1 = list.size();
        for(int j1 = 0; j1 < i1; j1++)
        {
            com.maddox.il2.ai.Regiment regiment1 = (com.maddox.il2.ai.Regiment)list.get(j1);
            if(regiment1.getArmy() != quikArmy)
                continue;
            java.lang.String s7 = regiment1.name();
            if(regHash.containsKey(s7))
                continue;
            regHash.put(s7, regiment1);
            java.util.ArrayList arraylist5 = (java.util.ArrayList)regList.get(regiment1.branch());
            if(arraylist5 == null)
            {
                java.lang.String s12 = null;
                try
                {
                    s12 = resCountry.getString(regiment1.branch());
                }
                catch(java.lang.Exception exception4)
                {
                    continue;
                }
                arraylist5 = new ArrayList();
                regList.put(regiment1.branch(), arraylist5);
                treemap.put(s12, regiment1.branch());
            }
            arraylist5.add(regiment1);
        }

        java.lang.String s5;
        for(java.util.Iterator iterator = treemap.keySet().iterator(); iterator.hasNext(); cCountry.add(s5))
        {
            s5 = (java.lang.String)iterator.next();
            countryLst.add(treemap.get(s5));
        }

        treemap.clear();
        cCountry.setSelected(0, true, false);
        fillRegiments();
        if(quikRegiment != null)
        {
            java.lang.Object obj = regHash.get(quikRegiment);
            if(obj != null)
            {
                java.lang.String s8 = ((com.maddox.il2.ai.Regiment)obj).branch();
                int l2;
                for(l2 = 0; l2 < countryLst.size() && !s8.equals(countryLst.get(l2)); l2++);
                if(l2 < countryLst.size())
                {
                    cCountry.setSelected(l2, true, false);
                    fillRegiments();
                    java.util.ArrayList arraylist7 = (java.util.ArrayList)regList.get(countryLst.get(l2));
                    if(arraylist7 != null)
                    {
                        int i3 = 0;
                        do
                        {
                            if(i3 >= arraylist7.size())
                                break;
                            if(obj.equals(arraylist7.get(i3)))
                            {
                                cRegiment.setSelected(i3, true, false);
                                break;
                            }
                            i3++;
                        } while(true);
                    }
                }
            }
        }
        cAircraft.setSelected(-1, false, false);
        try
        {
            int l1 = 0;
            do
            {
                if(l1 >= airNames.size())
                    break;
                if(quikPlane.equals(airNames.get(l1)))
                {
                    cAircraft.setSelected(l1, true, false);
                    break;
                }
                l1++;
            } while(true);
        }
        catch(java.lang.Exception exception1) { }
        if(cAircraft.getSelected() < 0)
        {
            cAircraft.setSelected(0, true, false);
            quikPlane = (java.lang.String)airNames.get(0);
        }
        if(quikRegiment == null && cRegiment.size() > 0)
        {
            cRegiment.setSelected(-1, false, false);
            cRegiment.setSelected(0, true, true);
        }
        cPlane.clear(false);
        for(int i2 = 0; i2 < quikPlanes; i2++)
            cPlane.add(" " + (i2 + 1));

        cPlane.setSelected(quikCurPlane, true, false);
        if(zutiSelectedBornPlace != null && zutiSelectedBornPlace.zutiEnablePlaneLimits)
            zutiFillWeapons(zutiSelectedBornPlace);
        else
            fillWeapons();
        selectWeapon();
        fillSkins();
        selectSkin();
        fillPilots();
        selectPilot();
        fillNoseart();
        selectNoseart();
        setMesh();
        prepareMesh();
        prepareWeapons();
        prepareSkin();
        preparePilot();
        prepareNoseart();
        break MISSING_BLOCK_LABEL_4171;
        java.lang.Exception exception;
        exception;
        java.lang.System.out.println(exception.getMessage());
        exception.printStackTrace();
        com.maddox.il2.game.Main.stateStack().pop();
        return;
        dialogClient.setPosSize();
        client.activateWindow();
        return;
    }

    public void _leave()
    {
        com.maddox.il2.ai.World.cur().setUserCovers();
        client.hideWindow();
    }

    private void fillRegiments()
    {
        if(stateId != 2 && stateId != 4)
            return;
        cRegiment.clear();
        int i = cCountry.getSelected();
        if(i < 0)
            return;
        java.lang.String s = (java.lang.String)countryLst.get(i);
        java.util.ArrayList arraylist = (java.util.ArrayList)regList.get(s);
        if(arraylist.size() > 0)
        {
            for(int j = 0; j < arraylist.size(); j++)
            {
                java.lang.Object obj = arraylist.get(j);
                if(obj instanceof com.maddox.il2.ai.Regiment)
                    cRegiment.add(((com.maddox.il2.ai.Regiment)obj).shortInfo());
                else
                    cRegiment.add(((com.maddox.il2.gui.UserRegiment)obj).shortInfo);
            }

            cRegiment.setSelected(0, true, false);
        }
    }

    private void fillWeapons()
    {
        cWeapon.clear();
        weaponNames.clear();
        int i = cAircraft.getSelected();
        if(i < 0)
            return;
        java.lang.Class class1 = (java.lang.Class)com.maddox.rts.Property.value(airNames.get(i), "airClass", null);
        java.lang.String as[] = com.maddox.il2.objects.air.Aircraft.getWeaponsRegistered(class1);
        if(as != null && as.length > 0)
        {
            java.lang.String s = (java.lang.String)airNames.get(i);
            for(int j = 0; j < as.length; j++)
            {
                java.lang.String s1 = as[j];
                if(!com.maddox.il2.objects.air.Aircraft.isWeaponDateOk(class1, s1))
                    continue;
                if(!bEnableWeaponChange)
                {
                    java.lang.String s2 = com.maddox.il2.game.Main.cur().currentMissionFile.get(planeName, "weapons", (java.lang.String)null);
                    if(!s1.equals(s2))
                        continue;
                }
                weaponNames.add(s1);
                cWeapon.add(com.maddox.il2.game.I18N.weapons(s, s1));
            }

            if(weaponNames.size() == 0)
            {
                weaponNames.add(as[0]);
                cWeapon.add(com.maddox.il2.game.I18N.weapons(s, as[0]));
            }
            cWeapon.setSelected(0, true, false);
        }
    }

    private void selectWeapon()
    {
        if(bEnableWeaponChange)
        {
            com.maddox.il2.ai.UserCfg usercfg = com.maddox.il2.ai.World.cur().userCfg;
            java.lang.String s = null;
            if(isNet())
                s = usercfg.getWeapon(airName());
            else
            if(stateId == 4)
                s = quikWeapon;
            else
                s = com.maddox.il2.game.Main.cur().currentMissionFile.get(planeName, "weapons", (java.lang.String)null);
            cWeapon.setSelected(-1, false, false);
            int i = 0;
            do
            {
                if(i >= weaponNames.size())
                    break;
                java.lang.String s1 = (java.lang.String)weaponNames.get(i);
                if(s1.equals(s))
                {
                    cWeapon.setSelected(i, true, false);
                    break;
                }
                i++;
            } while(true);
            if(cWeapon.getSelected() < 0)
            {
                cWeapon.setSelected(0, true, false);
                if(isNet())
                    usercfg.setWeapon(airName(), (java.lang.String)weaponNames.get(0));
                else
                if(stateId == 4)
                    quikWeapon = (java.lang.String)weaponNames.get(0);
                else
                    com.maddox.il2.game.Main.cur().currentMissionFile.set(planeName, "weapons", (java.lang.String)weaponNames.get(0));
            }
        } else
        {
            cWeapon.setSelected(0, true, false);
        }
    }

    public static java.lang.String validateFileName(java.lang.String s)
    {
        if(s.indexOf('\\') >= 0)
            s = s.replace('\\', '_');
        if(s.indexOf('/') >= 0)
            s = s.replace('/', '_');
        if(s.indexOf('?') >= 0)
            s = s.replace('?', '_');
        return s;
    }

    private void fillSkins()
    {
        cSkin.clear();
        cSkin.add(i18n("neta.Default"));
        try
        {
            int i = cAircraft.getSelected();
            java.lang.String s = com.maddox.il2.game.Main.cur().netFileServerSkin.primaryPath();
            java.lang.String s1 = com.maddox.il2.gui.GUIAirArming.validateFileName((java.lang.String)airNames.get(i));
            java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName(s + "/" + s1, 0));
            java.io.File afile[] = file.listFiles();
            if(afile != null)
            {
                for(int j = 0; j < afile.length; j++)
                {
                    java.io.File file1 = afile[j];
                    if(file1.isFile())
                    {
                        java.lang.String s2 = file1.getName();
                        java.lang.String s3 = s2.toLowerCase();
                        if(s3.endsWith(".bmp") && s3.length() + s1.length() <= 122)
                        {
                            int k = com.maddox.il2.engine.BmpUtils.squareSizeBMP8Pal(s + "/" + s1 + "/" + s2);
                            if(k == 512 || k == 1024)
                                cSkin.add(s2);
                            else
                                java.lang.System.out.println("Skin " + s + "/" + s1 + "/" + s2 + " NOT loaded");
                        }
                    }
                }

            }
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        cSkin.setSelected(0, true, false);
    }

    private void selectSkin()
    {
        com.maddox.il2.ai.UserCfg usercfg = com.maddox.il2.ai.World.cur().userCfg;
        cSkin.setSelected(-1, false, false);
        java.lang.String s = usercfg.getSkin(airName());
        if(stateId == 4)
            s = quikSkin[quikCurPlane];
        int i = 1;
        do
        {
            if(i >= cSkin.size())
                break;
            java.lang.String s1 = cSkin.get(i);
            if(s1.equals(s))
            {
                cSkin.setSelected(i, true, false);
                break;
            }
            i++;
        } while(true);
        if(cSkin.getSelected() < 0)
        {
            cSkin.setSelected(0, true, false);
            if(stateId == 4)
                quikSkin[quikCurPlane] = null;
            else
                usercfg.setSkin(airName(), null);
        }
    }

    private void fillPilots()
    {
        cPilot.clear();
        cPilot.add(i18n("neta.Default"));
        try
        {
            java.lang.String s = com.maddox.il2.game.Main.cur().netFileServerPilot.primaryPath();
            java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName(s, 0));
            java.io.File afile[] = file.listFiles();
            if(afile != null)
            {
                for(int i = 0; i < afile.length; i++)
                {
                    java.io.File file1 = afile[i];
                    if(file1.isFile())
                    {
                        java.lang.String s1 = file1.getName();
                        java.lang.String s2 = s1.toLowerCase();
                        if(s2.endsWith(".bmp") && s2.length() <= 122)
                            if(com.maddox.il2.engine.BmpUtils.checkBMP8Pal(s + "/" + s1, 256, 256))
                                cPilot.add(s1);
                            else
                                java.lang.System.out.println("Pilot " + s + "/" + s1 + " NOT loaded");
                    }
                }

            }
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        cPilot.setSelected(0, true, false);
    }

    private void selectPilot()
    {
        com.maddox.il2.ai.UserCfg usercfg = com.maddox.il2.ai.World.cur().userCfg;
        cPilot.setSelected(-1, false, false);
        java.lang.String s = usercfg.netPilot;
        if(stateId == 4)
            s = quikPilot[quikCurPlane];
        int i = 1;
        do
        {
            if(i >= cPilot.size())
                break;
            java.lang.String s1 = cPilot.get(i);
            if(s1.equals(s))
            {
                cPilot.setSelected(i, true, false);
                break;
            }
            i++;
        } while(true);
        if(cPilot.getSelected() < 0)
        {
            cPilot.setSelected(0, true, false);
            if(stateId == 4)
                quikPilot[quikCurPlane] = null;
            else
                usercfg.netPilot = null;
        }
    }

    private void fillNoseart()
    {
        cNoseart.clear();
        cNoseart.add(i18n("neta.None"));
        try
        {
            java.lang.String s = com.maddox.il2.game.Main.cur().netFileServerNoseart.primaryPath();
            java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName(s, 0));
            java.io.File afile[] = file.listFiles();
            if(afile != null)
            {
                for(int i = 0; i < afile.length; i++)
                {
                    java.io.File file1 = afile[i];
                    if(file1.isFile())
                    {
                        java.lang.String s1 = file1.getName();
                        java.lang.String s2 = s1.toLowerCase();
                        if(s2.endsWith(".bmp") && s2.length() <= 122)
                            if(com.maddox.il2.engine.BmpUtils.checkBMP8Pal(s + "/" + s1, 256, 512))
                                cNoseart.add(s1);
                            else
                                java.lang.System.out.println("Noseart " + s + "/" + s1 + " NOT loaded");
                    }
                }

            }
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        cNoseart.setSelected(0, true, false);
    }

    private void selectNoseart()
    {
        com.maddox.il2.ai.UserCfg usercfg = com.maddox.il2.ai.World.cur().userCfg;
        cNoseart.setSelected(-1, false, false);
        boolean flag = true;
        int i = cAircraft.getSelected();
        if(i < 0)
        {
            flag = false;
        } else
        {
            java.lang.Class class1 = (java.lang.Class)com.maddox.rts.Property.value(airNames.get(i), "airClass", null);
            flag = com.maddox.rts.Property.intValue(class1, "noseart", 0) == 1;
            if(flag)
            {
                int j = cCountry.getSelected();
                if(j < 0)
                {
                    flag = false;
                } else
                {
                    java.lang.String s1 = (java.lang.String)countryLst.get(j);
                    java.lang.String s3 = com.maddox.il2.ai.Regiment.getCountryFromBranch(s1);
                    flag = "us".equals(s3);
                }
            }
        }
        if(flag)
        {
            java.lang.String s = usercfg.getNoseart(airName());
            if(stateId == 4)
                s = quikNoseart[quikCurPlane];
            int k = 1;
            do
            {
                if(k >= cNoseart.size())
                    break;
                java.lang.String s2 = cNoseart.get(k);
                if(s2.equals(s))
                {
                    cNoseart.setSelected(k, true, false);
                    break;
                }
                k++;
            } while(true);
            cNoseart.showWindow();
        } else
        {
            cNoseart.hideWindow();
        }
        if(cNoseart.getSelected() < 0)
        {
            cNoseart.setSelected(0, true, false);
            if(stateId == 4)
                quikNoseart[quikCurPlane] = null;
            else
                usercfg.setNoseart(airName(), null);
        }
    }

    private void createRender()
    {
        renders = new com.maddox.il2.engine.GUIRenders(dialogClient) {

            public void mouseButton(int i, boolean flag, float f, float f1)
            {
                super.mouseButton(i, flag, f, f1);
                if(!flag)
                    return;
                if(i == 1)
                {
                    animateMeshA = animateMeshT = 0.0F;
                    if(com.maddox.il2.engine.Actor.isValid(actorMesh))
                        actorMesh.pos.setAbs(new Orient(90F, 0.0F, 0.0F));
                } else
                if(i == 0)
                {
                    f -= win.dx / 2.0F;
                    if(java.lang.Math.abs(f) < win.dx / 16F)
                        animateMeshA = 0.0F;
                    else
                        animateMeshA = (-128F * f) / win.dx;
                    f1 -= win.dy / 2.0F;
                    if(java.lang.Math.abs(f1) < win.dy / 16F)
                        animateMeshT = 0.0F;
                    else
                        animateMeshT = (-128F * f1) / win.dy;
                }
            }

        }
;
        camera3D = new Camera3D();
        camera3D.set(50F, 1.0F, 100F);
        render3D = new _Render3D(renders.renders, 1.0F);
        render3D.setCamera(camera3D);
        com.maddox.il2.engine.LightEnvXY lightenvxy = new LightEnvXY();
        render3D.setLightEnv(lightenvxy);
        lightenvxy.sun().setLight(0.5F, 0.5F, 1.0F, 1.0F, 1.0F, 0.8F);
        com.maddox.JGP.Vector3f vector3f = new Vector3f(-2F, 1.0F, -1F);
        vector3f.normalize();
        lightenvxy.sun().set(vector3f);
    }

    private void setMesh()
    {
        destroyMesh();
        int i = cAircraft.getSelected();
        if(i < 0)
            return;
        try
        {
            java.lang.Class class1 = (java.lang.Class)com.maddox.rts.Property.value(airNames.get(i), "airClass", null);
            java.lang.String s = (java.lang.String)countryLst.get(cCountry.getSelected());
            java.lang.String s1 = com.maddox.il2.ai.Regiment.getCountryFromBranch(s);
            java.lang.String s2 = com.maddox.il2.objects.air.Aircraft.getPropertyMesh(class1, s1);
            actorMesh = new ActorSimpleHMesh(s2);
            double d = actorMesh.hierMesh().visibilityR();
            com.maddox.il2.objects.air.Aircraft.prepareMeshCamouflage(s2, actorMesh.hierMesh());
            actorMesh.pos.setAbs(new Orient(90F, 0.0F, 0.0F));
            actorMesh.pos.reset();
            d *= java.lang.Math.cos(0.26179938779914941D) / java.lang.Math.sin(((double)camera3D.FOV() * 3.1415926535897931D) / 180D / 2D);
            camera3D.pos.setAbs(new Point3d(d, 0.0D, 0.0D), new Orient(180F, 0.0F, 0.0F));
            camera3D.pos.reset();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void destroyMesh()
    {
        if(com.maddox.il2.engine.Actor.isValid(actorMesh))
            actorMesh.destroy();
        actorMesh = null;
        destroyWeaponMeshs();
    }

    private void destroyWeaponMeshs()
    {
        for(int i = 0; i < weaponMeshs.size(); i++)
        {
            com.maddox.il2.engine.ActorMesh actormesh = (com.maddox.il2.engine.ActorMesh)weaponMeshs.get(i);
            if(com.maddox.il2.engine.Actor.isValid(actormesh))
                actormesh.destroy();
        }

        weaponMeshs.clear();
    }

    private void prepareMesh()
    {
        if(!com.maddox.il2.engine.Actor.isValid(actorMesh))
            return;
        int i = cAircraft.getSelected();
        if(i < 0)
            return;
        switch(stateId)
        {
        default:
            break;

        case 3: // '\003'
            java.lang.Class class1 = com.maddox.il2.gui.GUINetAircraft.selectedAircraftClass();
            com.maddox.il2.ai.Regiment regiment = com.maddox.il2.gui.GUINetAircraft.selectedRegiment();
            java.lang.String s = regiment.country();
            java.lang.String s2 = com.maddox.il2.gui.GUINetAircraft.selectedWingName();
            com.maddox.il2.objects.air.PaintScheme paintscheme1 = com.maddox.il2.objects.air.Aircraft.getPropertyPaintScheme(class1, s);
            if(paintscheme1 == null)
                return;
            int l = s2.charAt(s2.length() - 2) - 48;
            int i1 = s2.charAt(s2.length() - 1) - 48;
            int j1 = com.maddox.il2.gui.GUINetAircraft.selectedAircraftNumInWing();
            com.maddox.il2.ai.UserCfg usercfg1 = com.maddox.il2.ai.World.cur().userCfg;
            paintscheme1.prepare(class1, actorMesh.hierMesh(), regiment, l, i1, j1, usercfg1.netNumberOn);
            break;

        case 0: // '\0'
        case 1: // '\001'
        case 2: // '\002'
            java.lang.Class class2 = (java.lang.Class)com.maddox.rts.Property.value(airNames.get(i), "airClass", null);
            com.maddox.il2.objects.air.PaintScheme paintscheme = com.maddox.il2.objects.air.Aircraft.getPropertyPaintScheme(class2, (java.lang.String)countryLst.get(cCountry.getSelected()));
            if(paintscheme == null)
                return;
            int k = cCountry.getSelected();
            if(k < 0)
                return;
            java.lang.String s3 = (java.lang.String)countryLst.get(k);
            java.util.ArrayList arraylist1 = (java.util.ArrayList)regList.get(s3);
            if(arraylist1 == null)
                return;
            java.lang.Object obj = arraylist1.get(cRegiment.getSelected());
            java.lang.Object obj1 = null;
            if(obj instanceof com.maddox.il2.ai.Regiment)
            {
                obj1 = (com.maddox.il2.ai.Regiment)obj;
                com.maddox.il2.net.NetUserRegiment netuserregiment = ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).netUserRegiment;
                ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setUserRegiment(netuserregiment.branch(), "", netuserregiment.aid(), netuserregiment.gruppeNumber());
            } else
            {
                com.maddox.il2.gui.UserRegiment userregiment = (com.maddox.il2.gui.UserRegiment)obj;
                ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setUserRegiment(userregiment.country, userregiment.fileName + ".bmp", userregiment.id, userregiment.gruppeNumber);
                obj1 = ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).netUserRegiment;
            }
            if(obj1 == null)
                return;
            if(isNet())
            {
                if(cSquadron.getSelected() < 0)
                    return;
                com.maddox.il2.ai.UserCfg usercfg = com.maddox.il2.ai.World.cur().userCfg;
                boolean flag = usercfg.netNumberOn;
                if(stateId == 2)
                    flag = true;
                paintscheme.prepareNum(class2, actorMesh.hierMesh(), ((com.maddox.il2.ai.Regiment) (obj1)), cSquadron.getSelected(), 0, usercfg.netTacticalNumber, flag);
            } else
            {
                int k1 = planeName.charAt(planeName.length() - 2) - 48;
                int l1 = planeName.charAt(planeName.length() - 1) - 48;
                int i2 = com.maddox.il2.game.Main.cur().currentMissionFile.get("Main", "playerNum", 0);
                com.maddox.il2.ai.UserCfg usercfg2 = com.maddox.il2.ai.World.cur().userCfg;
                paintscheme.prepare(class2, actorMesh.hierMesh(), ((com.maddox.il2.ai.Regiment) (obj1)), k1, l1, i2, usercfg2.netNumberOn);
            }
            break;

        case 4: // '\004'
            java.lang.Class class3 = (java.lang.Class)com.maddox.rts.Property.value(airNames.get(i), "airClass", null);
            int j = cCountry.getSelected();
            if(j < 0)
                return;
            java.lang.String s1 = (java.lang.String)countryLst.get(j);
            java.util.ArrayList arraylist = (java.util.ArrayList)regList.get(s1);
            if(arraylist == null)
                return;
            com.maddox.il2.ai.Regiment regiment1 = (com.maddox.il2.ai.Regiment)arraylist.get(cRegiment.getSelected());
            if(regiment1 == null)
                return;
            com.maddox.il2.objects.air.PaintScheme paintscheme2 = com.maddox.il2.objects.air.Aircraft.getPropertyPaintScheme(class3, regiment1.country());
            if(paintscheme2 == null)
                return;
            paintscheme2.prepare(class3, actorMesh.hierMesh(), regiment1, quikSquadron, quikWing, quikCurPlane, quikNumberOn[quikCurPlane]);
            break;
        }
    }

    private void prepareWeapons()
    {
        destroyWeaponMeshs();
        if(!com.maddox.il2.engine.Actor.isValid(actorMesh))
            return;
        int i = cAircraft.getSelected();
        if(i < 0)
            return;
        java.lang.Class class1 = (java.lang.Class)com.maddox.rts.Property.value(airNames.get(i), "airClass", null);
        java.lang.String as[] = zutiSyncWeaponsLists(com.maddox.il2.objects.air.Aircraft.getWeaponsRegistered(class1));
        if(as == null || as.length == 0)
            return;
        i = cWeapon.getSelected();
        if(i < 0 || i >= as.length)
            return;
        java.lang.String as1[] = com.maddox.il2.objects.air.Aircraft.getWeaponHooksRegistered(class1);
        com.maddox.il2.objects.air.Aircraft._WeaponSlot a_lweaponslot[] = com.maddox.il2.objects.air.Aircraft.getWeaponSlotsRegistered(class1, as[i]);
        if(as1 == null || a_lweaponslot == null)
            return;
        for(int j = 0; j < as1.length;)
        {
            if(as1[j] == null || a_lweaponslot[j] == null)
                continue;
            java.lang.Class class2 = a_lweaponslot[j].clazz;
            if((com.maddox.il2.objects.weapons.BombGun.class).isAssignableFrom(class2) && !com.maddox.rts.Property.containsValue(class2, "external"))
                continue;
            java.lang.String s = com.maddox.rts.Property.stringValue(class2, "mesh", null);
            if(s == null)
            {
                java.lang.Class class3 = (java.lang.Class)com.maddox.rts.Property.value(class2, "bulletClass", null);
                if(class3 != null)
                    s = com.maddox.rts.Property.stringValue(class3, "mesh", null);
            }
            if(s == null)
                continue;
            try
            {
                com.maddox.il2.objects.ActorSimpleMesh actorsimplemesh = new ActorSimpleMesh(s);
                actorsimplemesh.pos.setBase(actorMesh, new HookNamed(actorMesh, as1[j]), false);
                actorsimplemesh.pos.changeHookToRel();
                actorsimplemesh.pos.resetAsBase();
                weaponMeshs.add(actorsimplemesh);
                continue;
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                j++;
            }
        }

    }

    private void prepareSkin()
    {
        int i = cSkin.getSelected();
        if(i < 0)
            return;
        java.lang.Class class1 = (java.lang.Class)com.maddox.rts.Property.value(airNames.get(cAircraft.getSelected()), "airClass", null);
        java.lang.String s = (java.lang.String)countryLst.get(cCountry.getSelected());
        java.lang.String s1 = com.maddox.il2.ai.Regiment.getCountryFromBranch(s);
        java.lang.String s2 = com.maddox.il2.objects.air.Aircraft.getPropertyMesh(class1, s1);
        if(i == 0)
        {
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setSkin(null);
            com.maddox.il2.objects.air.Aircraft.prepareMeshCamouflage(s2, actorMesh.hierMesh());
        } else
        {
            java.lang.String s3 = com.maddox.il2.gui.GUIAirArming.validateFileName(airName());
            java.lang.String s4 = s3 + "/" + cSkin.get(i);
            java.lang.String s5 = s2;
            int j = s5.lastIndexOf('/');
            if(j >= 0)
                s5 = s5.substring(0, j + 1) + "summer";
            else
                s5 = s5 + "summer";
            java.lang.String s6 = "PaintSchemes/Cache/" + s3;
            try
            {
                java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName(s6, 0));
                if(!file.isDirectory())
                {
                    file.mkdir();
                } else
                {
                    java.io.File afile[] = file.listFiles();
                    if(afile != null)
                    {
                        for(int k = 0; k < afile.length; k++)
                            if(afile[k] != null)
                            {
                                java.lang.String s8 = afile[k].getName();
                                if(s8.regionMatches(true, s8.length() - 4, ".tg", 0, 3))
                                    afile[k].delete();
                            }

                    }
                }
            }
            catch(java.lang.Exception exception)
            {
                return;
            }
            java.lang.String s7 = com.maddox.il2.game.Main.cur().netFileServerSkin.primaryPath();
            if(!com.maddox.il2.engine.BmpUtils.bmp8PalTo4TGA4(s7 + "/" + s4, s5, s6))
                return;
            com.maddox.il2.objects.air.Aircraft.prepareMeshCamouflage(s2, actorMesh.hierMesh(), s6);
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setSkin(s4);
        }
    }

    private void preparePilot()
    {
        int i = cPilot.getSelected();
        if(i < 0)
            return;
        if(i == 0)
        {
            java.lang.Class class1 = (java.lang.Class)com.maddox.rts.Property.value(airNames.get(cAircraft.getSelected()), "airClass", null);
            java.lang.String s1 = (java.lang.String)countryLst.get(cCountry.getSelected());
            java.lang.String s3 = com.maddox.il2.ai.Regiment.getCountryFromBranch(s1);
            java.lang.String s5 = com.maddox.il2.objects.air.Aircraft.getPropertyMesh(class1, s3);
            java.lang.String s7 = com.maddox.rts.HomePath.concatNames(s5, "pilot1.mat");
            com.maddox.il2.objects.air.Aircraft.prepareMeshPilot(actorMesh.hierMesh(), 0, s7, "3do/plane/textures/pilot1.tga");
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setPilot(null);
        } else
        {
            java.lang.String s = com.maddox.il2.game.Main.cur().netFileServerPilot.primaryPath();
            java.lang.String s2 = cPilot.get(i);
            java.lang.String s4 = s2.substring(0, s2.length() - 4);
            java.lang.String s6 = "PaintSchemes/Cache/Pilot" + s4 + ".mat";
            java.lang.String s8 = "PaintSchemes/Cache/Pilot" + s4 + ".tga";
            if(!com.maddox.il2.engine.BmpUtils.bmp8PalToTGA3(s + "/" + s2, s8))
                return;
            com.maddox.il2.objects.air.Aircraft.prepareMeshPilot(actorMesh.hierMesh(), 0, s6, s8);
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setPilot(s2);
        }
    }

    private void prepareNoseart()
    {
        int i = cNoseart.getSelected();
        if(i > 0)
        {
            java.lang.String s = com.maddox.il2.game.Main.cur().netFileServerNoseart.primaryPath();
            java.lang.String s1 = cNoseart.get(i);
            java.lang.String s2 = s1.substring(0, s1.length() - 4);
            java.lang.String s3 = "PaintSchemes/Cache/Noseart0" + s2 + ".mat";
            java.lang.String s4 = "PaintSchemes/Cache/Noseart0" + s2 + ".tga";
            java.lang.String s5 = "PaintSchemes/Cache/Noseart1" + s2 + ".mat";
            java.lang.String s6 = "PaintSchemes/Cache/Noseart1" + s2 + ".tga";
            if(!com.maddox.il2.engine.BmpUtils.bmp8PalTo2TGA4(s + "/" + s1, s4, s6))
                return;
            com.maddox.il2.objects.air.Aircraft.prepareMeshNoseart(actorMesh.hierMesh(), s3, s5, s4, s6);
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setNoseart(s1);
        }
    }

    private float clampValue(com.maddox.gwindow.GWindowEditControl gwindoweditcontrol, float f, float f1, float f2)
    {
        java.lang.String s = gwindoweditcontrol.getValue();
        try
        {
            f = java.lang.Float.parseFloat(s);
        }
        catch(java.lang.Exception exception) { }
        if(f < f1)
            f = f1;
        if(f > f2)
            f = f2;
        gwindoweditcontrol.setValue("" + f, false);
        return f;
    }

    public GUIAirArming(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(55);
        zutiSelectedBornPlace = null;
        airNames = new ArrayList();
        weaponNames = new ArrayList();
        regList = new HashMapExt();
        regHash = new HashMapExt();
        countryLst = new ArrayList();
        bEnableWeaponChange = true;
        quikPlayer = true;
        quikArmy = 1;
        quikPlanes = 4;
        quikPlane = "Il-2_M3";
        quikWeapon = "default";
        quikCurPlane = 0;
        quikRegiment = "r01";
        quikSquadron = 0;
        quikWing = 0;
        quikFuel = 100;
        quikListPlane = new ArrayList();
        playerNum = -1;
        weaponMeshs = new ArrayList();
        animateMeshA = 0.0F;
        animateMeshT = 0.0F;
        _orient = new Orient();
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("neta.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        wMashineGun = (com.maddox.gwindow.GWindowEditControl)dialogClient.addControl(new GWindowEditControl(dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null));
        wMashineGun.bNumericOnly = wMashineGun.bNumericFloat = true;
        wMashineGun.bDelayedNotify = true;
        wCannon = (com.maddox.gwindow.GWindowEditControl)dialogClient.addControl(new GWindowEditControl(dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null));
        wCannon.bNumericOnly = wCannon.bNumericFloat = true;
        wCannon.bDelayedNotify = true;
        wRocket = (com.maddox.gwindow.GWindowEditControl)dialogClient.addControl(new GWindowEditControl(dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null));
        wRocket.bNumericOnly = wRocket.bNumericFloat = true;
        wRocket.bDelayedNotify = true;
        wRocketDelay = (com.maddox.gwindow.GWindowEditControl)dialogClient.addControl(new GWindowEditControl(dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null));
        wRocketDelay.bNumericOnly = wRocketDelay.bNumericFloat = true;
        wRocketDelay.bDelayedNotify = true;
        wBombDelay = (com.maddox.gwindow.GWindowEditControl)dialogClient.addControl(new GWindowEditControl(dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null));
        wBombDelay.bNumericOnly = wBombDelay.bNumericFloat = true;
        wBombDelay.bDelayedNotify = true;
        wNumber = (com.maddox.gwindow.GWindowEditControl)dialogClient.addControl(new com.maddox.gwindow.GWindowEditControl(dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null) {

            public void keyboardKey(int i, boolean flag)
            {
                super.keyboardKey(i, flag);
                if(i == 10 && flag)
                    notify(2, 0);
            }

        }
);
        wNumber.bNumericOnly = true;
        wNumber.bDelayedNotify = true;
        wNumber.align = 1;
        sNumberOn = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        cFuel = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 0.0F, 0.0F, 1.0F));
        cFuel.setEditable(false);
        cFuel.add("10");
        cFuel.add("20");
        cFuel.add("30");
        cFuel.add("40");
        cFuel.add("50");
        cFuel.add("60");
        cFuel.add("70");
        cFuel.add("80");
        cFuel.add("90");
        cFuel.add("100");
        cAircraft = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 0.0F, 0.0F, 1.0F));
        cAircraft.setEditable(false);
        cAircraft.listVisibleLines = 16;
        cWeapon = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 0.0F, 0.0F, 1.0F));
        cWeapon.setEditable(false);
        cCountry = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 0.0F, 0.0F, 1.0F));
        cCountry.setEditable(false);
        cRegiment = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 0.0F, 0.0F, 1.0F));
        cRegiment.setEditable(false);
        cSkin = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 0.0F, 0.0F, 1.0F));
        cSkin.setEditable(false);
        cPilot = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 0.0F, 0.0F, 1.0F));
        cPilot.setEditable(false);
        cSquadron = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 0.0F, 0.0F, 1.0F));
        cSquadron.setEditable(false);
        cSquadron.editBox.align = cSquadron.align = 1;
        cSquadron.add("1");
        cSquadron.add("2");
        cSquadron.add("3");
        cSquadron.add("4");
        cPlane = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 0.0F, 0.0F, 1.0F));
        cPlane.setEditable(false);
        cNoseart = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 0.0F, 0.0F, 1.0F));
        cNoseart.setEditable(false);
        bBack = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bJoy = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        createRender();
        dialogClient.activateWindow();
        client.hideWindow();
    }

    private void zutiFillWeapons(com.maddox.il2.net.BornPlace bornplace)
    {
        cWeapon.clear();
        weaponNames.clear();
        int i = cAircraft.getSelected();
        java.util.ArrayList arraylist = bornplace.zutiGetAcLoadouts((java.lang.String)airNames.get(i));
        if(arraylist == null || arraylist.size() < 1)
        {
            fillWeapons();
            return;
        }
        if(i < 0)
            return;
        java.lang.Class class1 = (java.lang.Class)com.maddox.rts.Property.value(airNames.get(i), "airClass", null);
        java.lang.String as[] = com.maddox.il2.objects.air.Aircraft.getWeaponsRegistered(class1);
        if(as != null && as.length > 0)
        {
            java.lang.String s = (java.lang.String)airNames.get(i);
            for(int j = 0; j < as.length; j++)
            {
                java.lang.String s1 = as[j];
                if(!com.maddox.il2.objects.air.Aircraft.isWeaponDateOk(class1, s1))
                    continue;
                java.lang.String s2 = com.maddox.il2.game.I18N.weapons(s, s1);
                if(!bEnableWeaponChange)
                {
                    java.lang.String s3 = com.maddox.il2.game.Main.cur().currentMissionFile.get(planeName, "weapons", (java.lang.String)null);
                    if(!s1.equals(s3))
                        continue;
                }
                if(arraylist.contains(s2))
                {
                    weaponNames.add(s1);
                    cWeapon.add(s2);
                }
            }

            if(weaponNames.size() == 0)
            {
                weaponNames.add(as[0]);
                cWeapon.add(com.maddox.il2.game.I18N.weapons(s, as[0]));
            }
            cWeapon.setSelected(0, true, false);
        }
    }

    private java.lang.String[] zutiSyncWeaponsLists(java.lang.String as[])
    {
        java.util.ArrayList arraylist = new ArrayList();
        for(int i = 0; i < as.length; i++)
            if(weaponNames.contains(as[i]))
                arraylist.add(as[i]);

        java.lang.String as1[] = new java.lang.String[arraylist.size()];
        for(int j = 0; j < arraylist.size(); j++)
            as1[j] = (java.lang.String)arraylist.get(j);

        return as1;
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.gwindow.GWindowEditControl wMashineGun;
    public com.maddox.gwindow.GWindowEditControl wCannon;
    public com.maddox.gwindow.GWindowEditControl wRocket;
    public com.maddox.gwindow.GWindowEditControl wRocketDelay;
    public com.maddox.gwindow.GWindowEditControl wBombDelay;
    public com.maddox.gwindow.GWindowComboControl cFuel;
    public com.maddox.gwindow.GWindowComboControl cAircraft;
    public com.maddox.gwindow.GWindowComboControl cWeapon;
    public com.maddox.gwindow.GWindowComboControl cCountry;
    public com.maddox.gwindow.GWindowComboControl cRegiment;
    public com.maddox.gwindow.GWindowComboControl cSkin;
    public com.maddox.gwindow.GWindowComboControl cNoseart;
    public com.maddox.gwindow.GWindowComboControl cPilot;
    public com.maddox.gwindow.GWindowEditControl wNumber;
    public com.maddox.gwindow.GWindowComboControl cSquadron;
    public com.maddox.il2.gui.GUISwitchBox3 sNumberOn;
    public com.maddox.gwindow.GWindowComboControl cPlane;
    public com.maddox.il2.gui.GUIButton bJoy;
    private com.maddox.il2.net.BornPlace zutiSelectedBornPlace;
    public com.maddox.il2.gui.GUIButton bBack;
    public static final int SINGLE = 0;
    public static final int CAMPAIGN = 1;
    public static final int DFIGHT = 2;
    public static final int COOP = 3;
    public static final int QUIK = 4;
    public static int stateId = 2;
    public java.util.ArrayList airNames;
    public java.util.ArrayList weaponNames;
    public com.maddox.util.HashMapExt regList;
    public com.maddox.util.HashMapExt regHash;
    public java.util.ResourceBundle resCountry;
    public java.util.ArrayList countryLst;
    private boolean bEnableWeaponChange;
    protected boolean quikPlayer;
    protected int quikArmy;
    protected int quikPlanes;
    protected java.lang.String quikPlane;
    protected java.lang.String quikWeapon;
    protected int quikCurPlane;
    protected java.lang.String quikRegiment;
    protected int quikSquadron;
    protected int quikWing;
    protected java.lang.String quikSkin[] = {
        null, null, null, null
    };
    protected java.lang.String quikNoseart[] = {
        null, null, null, null
    };
    protected java.lang.String quikPilot[] = {
        null, null, null, null
    };
    protected boolean quikNumberOn[] = {
        true, true, true, true
    };
    protected int quikFuel;
    protected java.util.ArrayList quikListPlane;
    private java.lang.String planeName;
    private int playerNum;
    public com.maddox.il2.engine.GUIRenders renders;
    public com.maddox.il2.engine.Camera3D camera3D;
    public com.maddox.il2.gui._Render3D render3D;
    public com.maddox.il2.engine.ActorHMesh actorMesh;
    public java.util.ArrayList weaponMeshs;
    public float animateMeshA;
    public float animateMeshT;
    private com.maddox.il2.engine.Orient _orient;
























}

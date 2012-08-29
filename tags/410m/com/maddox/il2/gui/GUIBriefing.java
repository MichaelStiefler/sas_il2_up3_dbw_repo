// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIBriefing.java

package com.maddox.il2.gui;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.gwindow.GPoint;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.AirportCarrier;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ZutiTargetsSupportMethods;
import com.maddox.il2.ai.air.CellAirField;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.GUIRenders;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiNetSendMethods;
import com.maddox.il2.game.ZutiPadObject;
import com.maddox.il2.game.ZutiRadarRefresh;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.NetUserRegiment;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.vehicles.artillery.RocketryRocket;
import com.maddox.il2.objects.vehicles.radios.Beacon;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetEnv;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

// Referenced classes of package com.maddox.il2.gui:
//            GUIBriefingGeneric, GUINetAircraft, GUIClient, GUIAirArming

public class GUIBriefing extends com.maddox.il2.gui.GUIBriefingGeneric
{
    public static class BeaconPoint
    {

        public float x;
        public float y;
        public com.maddox.il2.engine.Mat icon;
        public int army;
        public java.lang.String id;

        public BeaconPoint()
        {
        }
    }

    public static class TargetPoint
    {

        public boolean isGroundUnit()
        {
            return com.maddox.il2.game.ZutiPadObject.isGroundUnit(actor);
        }

        public boolean getIsAlive()
        {
            if(actor == null)
                return false;
            if(actor instanceof com.maddox.il2.objects.vehicles.artillery.RocketryRocket)
                return !((com.maddox.il2.objects.vehicles.artillery.RocketryRocket)actor).isDamaged();
            else
                return !actor.getDiedFlag();
        }

        public boolean equals(java.lang.Object obj)
        {
            if(!(obj instanceof com.maddox.il2.gui.TargetPoint))
                return false;
            com.maddox.il2.gui.TargetPoint targetpoint = (com.maddox.il2.gui.TargetPoint)obj;
            if(actor != null)
            {
                if(actor.equals(targetpoint.actor))
                    return true;
            } else
            if(nameTargetOrig.equals(targetpoint.nameTargetOrig))
                return true;
            return false;
        }

        public int hashCode()
        {
            if(actor != null)
                return actor.hashCode();
            else
                return nameTargetOrig.hashCode();
        }

        public void refreshPosition()
        {
            mission.getClass();
            if(!mission.zutiRadar_PlayerSideHasRadars)
                return;
            if(actor != null && actor.pos != null)
            {
                com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
                x = (float)point3d.x;
                y = (float)point3d.y;
                z = (float)point3d.z;
            }
        }

        public boolean isVisibleForPlayerArmy()
        {
            return visibleForPlayerArmy;
        }

        public void setVisibleForPlayerArmy(boolean flag)
        {
            visibleForPlayerArmy = flag;
        }

        public float x;
        public float y;
        public float z;
        public int r;
        public int type;
        public int typeOArmy;
        public int importance;
        public com.maddox.il2.engine.Mat icon;
        public com.maddox.il2.engine.Mat iconOArmy;
        public java.lang.String nameTarget;
        public java.lang.String nameTargetOrig;
        public com.maddox.il2.engine.Actor actor;
        public boolean isBaseActorWing;
        public com.maddox.il2.ai.Wing wing;
        private boolean visibleForPlayerArmy;
        private com.maddox.il2.game.Mission mission;

        public TargetPoint()
        {
            z = 0.0F;
            isBaseActorWing = false;
            wing = null;
            visibleForPlayerArmy = false;
            mission = null;
            mission = com.maddox.il2.game.Main.cur().mission;
        }
    }

    private static class PathPoint
    {

        public int type;
        public float x;
        public float y;

        private PathPoint()
        {
        }

    }


    public void _enter()
    {
        playerPath.clear();
        playerName = null;
        super._enter();
        com.maddox.il2.game.ZutiRadarRefresh.findRadars(com.maddox.il2.game.ZutiSupportMethods.getPlayerArmy());
        com.maddox.il2.game.ZutiRadarRefresh.resetStartTimes();
        ZUTI_IS_BRIEFING_ACTIVE = true;
        com.maddox.il2.game.ZutiNetSendMethods.requestUnavailableAircraftList();
        com.maddox.il2.game.ZutiNetSendMethods.requestCompletedReconList();
        com.maddox.il2.ai.ZutiTargetsSupportMethods.checkForDeactivatedTargets();
        com.maddox.il2.objects.air.NetAircraft.ZUTI_REFLY_OWERRIDE = false;
        fillBeacons();
    }

    public void _leave()
    {
        super._leave();
        ZUTI_IS_BRIEFING_ACTIVE = false;
    }

    private void drawBornPlaces()
    {
        if(iconBornPlace == null)
            return;
        java.util.ArrayList arraylist = com.maddox.il2.ai.World.cur().bornPlaces;
        if(arraylist == null || arraylist.size() == 0)
            return;
        int i = arraylist.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.net.BornPlace bornplace = (com.maddox.il2.net.BornPlace)arraylist.get(j);
            bornplace.tmpForBrief = 0;
        }

        com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
        int k = netuser.getBornPlace();
        if(k >= 0 && k < i)
        {
            com.maddox.il2.net.BornPlace bornplace1 = (com.maddox.il2.net.BornPlace)arraylist.get(k);
            bornplace1.tmpForBrief = 1;
        }
        java.util.List list = com.maddox.rts.NetEnv.hosts();
        for(int l = 0; l < list.size(); l++)
        {
            com.maddox.il2.net.NetUser netuser1 = (com.maddox.il2.net.NetUser)list.get(l);
            int j1 = netuser1.getBornPlace();
            if(j1 >= 0 && j1 < i)
            {
                com.maddox.il2.net.BornPlace bornplace3 = (com.maddox.il2.net.BornPlace)arraylist.get(j1);
                bornplace3.tmpForBrief++;
            }
        }

        for(int i1 = 0; i1 < i; i1++)
        {
            com.maddox.il2.net.BornPlace bornplace2 = (com.maddox.il2.net.BornPlace)arraylist.get(i1);
            com.maddox.il2.engine.IconDraw.setColor(com.maddox.il2.ai.Army.color(bornplace2.army));
            float f = (float)((bornplace2.place.x - cameraMap2D.worldXOffset) * cameraMap2D.worldScale);
            float f1 = (float)((bornplace2.place.y - cameraMap2D.worldYOffset) * cameraMap2D.worldScale);
            if(bornplace2.zutiStaticPositionOnly)
            {
                f = (float)((bornplace2.zutiOriginalX - cameraMap2D.worldXOffset) * cameraMap2D.worldScale);
                f1 = (float)((bornplace2.zutiOriginalY - cameraMap2D.worldYOffset) * cameraMap2D.worldScale);
            }
            com.maddox.il2.engine.IconDraw.render(iconBornPlace, f, f1);
            if(i1 == k && iconPlayer != null)
                com.maddox.il2.engine.Render.drawTile(f, f1, com.maddox.il2.engine.IconDraw.scrSizeX(), com.maddox.il2.engine.IconDraw.scrSizeY(), 0.0F, iconPlayer, com.maddox.il2.ai.Army.color(bornplace2.army), 0.0F, 1.0F, 1.0F, -1F);
            if(bornplace2.tmpForBrief > 0 && !com.maddox.il2.game.Main.cur().mission.zutiMisc_HidePlayersCountOnHomeBase)
                gridFont.output(com.maddox.il2.ai.Army.color(bornplace2.army), (int)f + com.maddox.il2.engine.IconDraw.scrSizeX() / 2 + 2, (int)f1 - com.maddox.il2.engine.IconDraw.scrSizeY() / 2 - 2, 0.0F, "" + bornplace2.tmpForBrief);
        }

    }

    private void fillBeacons()
    {
        com.maddox.rts.SectFile sectfile = com.maddox.il2.game.Main.cur().currentMissionFile;
        int i = -1;
        if(com.maddox.il2.game.Mission.isDogfight())
            i = ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getArmy();
        else
            i = sectfile.get("MAIN", "army", 0);
        beacons.clear();
        int j = sectfile.sectionIndex("NStationary");
        if(j < 0)
            return;
        int k = sectfile.vars(j);
        for(int l = 0; l < k; l++)
        {
            com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(j, l));
            com.maddox.il2.gui.BeaconPoint beaconpoint = loadbeacon(i, numbertokenizer.next(""), numbertokenizer.next(""), numbertokenizer.next(0), numbertokenizer.next(0.0D), numbertokenizer.next(0.0D));
            if(beaconpoint != null)
                beacons.add(beaconpoint);
        }

    }

    private com.maddox.il2.gui.BeaconPoint loadbeacon(int i, java.lang.String s, java.lang.String s1, int j, double d, double d1)
    {
        if(i != j)
            return null;
        java.lang.Class class1 = null;
        try
        {
            class1 = com.maddox.rts.ObjIO.classForName(s1);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("Mission: class '" + s1 + "' not found");
            return null;
        }
        if((com.maddox.il2.objects.vehicles.radios.TypeHasBeacon.class).isAssignableFrom(class1))
        {
            com.maddox.il2.gui.BeaconPoint beaconpoint = new BeaconPoint();
            beaconpoint.x = (float)d;
            beaconpoint.y = (float)d1;
            beaconpoint.army = j;
            if((com.maddox.il2.objects.vehicles.radios.Beacon$RadioBeacon.class).isAssignableFrom(class1))
                beaconpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/beacon.mat");
            else
            if((com.maddox.il2.objects.vehicles.radios.Beacon$RadioBeaconLowVis.class).isAssignableFrom(class1))
                beaconpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/beacon.mat");
            else
            if((com.maddox.il2.objects.vehicles.radios.Beacon$YGBeacon.class).isAssignableFrom(class1))
                beaconpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/beaconYG.mat");
            else
            if((com.maddox.il2.objects.vehicles.radios.Beacon$LorenzBLBeacon.class).isAssignableFrom(class1))
                beaconpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/ILS.mat");
            else
            if((com.maddox.il2.objects.vehicles.radios.Beacon$LorenzBLBeacon_LongRunway.class).isAssignableFrom(class1))
                beaconpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/ILS.mat");
            else
            if((com.maddox.il2.objects.vehicles.radios.Beacon$LorenzBLBeacon_AAIAS.class).isAssignableFrom(class1))
                beaconpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/ILS.mat");
            else
                return null;
            java.lang.String s2 = com.maddox.il2.objects.vehicles.radios.Beacon.getBeaconID(beacons.size());
            beaconpoint.id = s2;
            return beaconpoint;
        } else
        {
            return null;
        }
    }

    public static void fillTargets(com.maddox.rts.SectFile sectfile, java.util.ArrayList arraylist)
    {
        arraylist.clear();
        int i = sectfile.sectionIndex("Target");
        if(i >= 0)
        {
            int j = sectfile.vars(i);
            for(int k = 0; k < j; k++)
            {
                com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, k));
                int l = numbertokenizer.next(0, 0, 7);
                int i1 = numbertokenizer.next(0, 0, 2);
                if(i1 == 2)
                    continue;
                com.maddox.il2.gui.TargetPoint targetpoint = new TargetPoint();
                targetpoint.type = l;
                targetpoint.importance = i1;
                boolean flag = numbertokenizer.next(0) == 1;
                int j1 = numbertokenizer.next(0, 0, 720);
                boolean flag1 = numbertokenizer.next(0) == 1;
                targetpoint.x = numbertokenizer.next(0);
                targetpoint.y = numbertokenizer.next(0);
                int k1 = numbertokenizer.next(0);
                if(targetpoint.type == 3 || targetpoint.type == 6 || targetpoint.type == 1)
                {
                    if(k1 < 50)
                        k1 = 50;
                    if(k1 > 3000)
                        k1 = 3000;
                }
                targetpoint.r = k1;
                int l1 = numbertokenizer.next(0);
                targetpoint.nameTarget = numbertokenizer.next(null);
                if(targetpoint.nameTarget != null && targetpoint.nameTarget.startsWith("Bridge"))
                    targetpoint.nameTarget = null;
                int i2 = numbertokenizer.next(0);
                int j2 = numbertokenizer.next(0);
                if(i2 != 0 && j2 != 0)
                {
                    targetpoint.x = i2;
                    targetpoint.y = j2;
                }
                switch(targetpoint.type)
                {
                case 0: // '\0'
                    targetpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/tdestroyair.mat");
                    if(targetpoint.nameTarget != null && sectfile.exist("Chiefs", targetpoint.nameTarget))
                        targetpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/tdestroychief.mat");
                    break;

                case 1: // '\001'
                    targetpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/tdestroyground.mat");
                    break;

                case 2: // '\002'
                    targetpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/tdestroybridge.mat");
                    targetpoint.nameTarget = null;
                    break;

                case 3: // '\003'
                    targetpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/tinspect.mat");
                    break;

                case 4: // '\004'
                    targetpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/tescort.mat");
                    break;

                case 5: // '\005'
                    targetpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/tdefence.mat");
                    break;

                case 6: // '\006'
                    targetpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/tdefenceground.mat");
                    break;

                case 7: // '\007'
                    targetpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/tdefencebridge.mat");
                    targetpoint.nameTarget = null;
                    break;
                }
                if(targetpoint.nameTarget != null)
                    if(sectfile.exist("Chiefs", targetpoint.nameTarget))
                        try
                        {
                            java.util.StringTokenizer stringtokenizer = new StringTokenizer(sectfile.get("Chiefs", targetpoint.nameTarget, (java.lang.String)null));
                            java.lang.String s1 = stringtokenizer.nextToken();
                            int k2 = s1.indexOf(".");
                            targetpoint.nameTarget = com.maddox.il2.game.I18N.technic(s1.substring(0, k2)) + " " + com.maddox.il2.game.I18N.technic(s1.substring(k2 + 1));
                        }
                        catch(java.lang.Exception exception)
                        {
                            targetpoint.nameTarget = null;
                        }
                    else
                    if(sectfile.sectionIndex(targetpoint.nameTarget) >= 0)
                        try
                        {
                            java.lang.String s = sectfile.get(targetpoint.nameTarget, "Class", (java.lang.String)null);
                            java.lang.Class class1 = com.maddox.rts.ObjIO.classForName(s);
                            targetpoint.nameTarget = com.maddox.rts.Property.stringValue(class1, "iconFar_shortClassName", null);
                        }
                        catch(java.lang.Exception exception1)
                        {
                            targetpoint.nameTarget = null;
                        }
                    else
                        targetpoint.nameTarget = null;
                arraylist.add(targetpoint);
            }

        }
    }

    public static void drawTargets(com.maddox.il2.engine.GUIRenders guirenders, com.maddox.il2.engine.TTFont ttfont, com.maddox.il2.engine.Mat mat, com.maddox.il2.engine.CameraOrtho2D cameraortho2d, java.util.ArrayList arraylist)
    {
        int i = arraylist.size();
        if(i == 0)
            return;
        com.maddox.gwindow.GPoint gpoint = guirenders.getMouseXY();
        int j = -1;
        float f = gpoint.x;
        float f1 = guirenders.win.dy - 1.0F - gpoint.y;
        float f2 = com.maddox.il2.engine.IconDraw.scrSizeX() / 2;
        float f3 = f;
        float f4 = f1;
        com.maddox.il2.engine.IconDraw.setColor(0xff00ffff);
        for(int k = 0; k < i; k++)
        {
            com.maddox.il2.gui.TargetPoint targetpoint1 = (com.maddox.il2.gui.TargetPoint)arraylist.get(k);
            if(targetpoint1.icon == null)
                continue;
            float f6 = (float)(((double)targetpoint1.x - cameraortho2d.worldXOffset) * cameraortho2d.worldScale);
            float f7 = (float)(((double)targetpoint1.y - cameraortho2d.worldYOffset) * cameraortho2d.worldScale);
            com.maddox.il2.engine.IconDraw.render(targetpoint1.icon, f6, f7);
            if(f6 >= f - f2 && f6 <= f + f2 && f7 >= f1 - f2 && f7 <= f1 + f2)
            {
                j = k;
                f3 = f6;
                f4 = f7;
            }
        }

        if(j != -1)
        {
            com.maddox.il2.gui.TargetPoint targetpoint = (com.maddox.il2.gui.TargetPoint)arraylist.get(j);
            for(int l = 0; l < 3; l++)
                tip[l] = null;

            if(targetpoint.importance == 0)
                tip[0] = com.maddox.il2.game.I18N.gui("brief.Primary");
            else
                tip[0] = com.maddox.il2.game.I18N.gui("brief.Secondary");
            switch(targetpoint.type)
            {
            case 0: // '\0'
                tip[1] = com.maddox.il2.game.I18N.gui("brief.Destroy");
                break;

            case 1: // '\001'
                tip[1] = com.maddox.il2.game.I18N.gui("brief.DestroyGround");
                break;

            case 2: // '\002'
                tip[1] = com.maddox.il2.game.I18N.gui("brief.DestroyBridge");
                break;

            case 3: // '\003'
                tip[1] = com.maddox.il2.game.I18N.gui("brief.Inspect");
                break;

            case 4: // '\004'
                tip[1] = com.maddox.il2.game.I18N.gui("brief.Escort");
                break;

            case 5: // '\005'
                tip[1] = com.maddox.il2.game.I18N.gui("brief.Defence");
                break;

            case 6: // '\006'
                tip[1] = com.maddox.il2.game.I18N.gui("brief.DefenceGround");
                break;

            case 7: // '\007'
                tip[1] = com.maddox.il2.game.I18N.gui("brief.DefenceBridge");
                break;
            }
            if(targetpoint.nameTarget != null)
                tip[2] = targetpoint.nameTarget;
            float f5 = ttfont.width(tip[0]);
            int i1 = 1;
            for(int j1 = 1; j1 < 3 && tip[j1] != null; j1++)
            {
                i1 = j1;
                float f9 = ttfont.width(tip[j1]);
                if(f5 < f9)
                    f5 = f9;
            }

            float f8 = -ttfont.descender();
            float f10 = (float)ttfont.height() + f8;
            f5 += 2.0F * f8;
            float f11 = f10 * (float)(i1 + 1) + 2.0F * f8;
            float f12 = f3 - f5 / 2.0F;
            float f13 = f4 + f2;
            if(f12 + f5 > guirenders.win.dx)
                f12 = guirenders.win.dx - f5;
            if(f13 + f11 > guirenders.win.dy)
                f13 = guirenders.win.dy - f11;
            if(f12 < 0.0F)
                f12 = 0.0F;
            if(f13 < 0.0F)
                f13 = 0.0F;
            com.maddox.il2.engine.Render.drawTile(f12, f13, f5, f11, 0.0F, mat, 0xcf7fffff, 0.0F, 0.0F, 1.0F, 1.0F);
            com.maddox.il2.engine.Render.drawEnd();
            for(int k1 = 0; k1 <= i1; k1++)
                ttfont.output(0xff000000, f12 + f8, f13 + f8 + (float)(i1 - k1) * f10 + f8, 0.0F, tip[k1]);

        }
    }

    public void drawBeacons(com.maddox.il2.engine.GUIRenders guirenders, com.maddox.il2.engine.TTFont ttfont, com.maddox.il2.engine.Mat mat, com.maddox.il2.engine.CameraOrtho2D cameraortho2d, java.util.ArrayList arraylist)
    {
        int i = arraylist.size();
        if(i == 0)
            return;
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.gui.BeaconPoint beaconpoint = (com.maddox.il2.gui.BeaconPoint)arraylist.get(j);
            int k = com.maddox.il2.ai.Army.color(beaconpoint.army);
            com.maddox.il2.engine.IconDraw.setColor(k);
            if(beaconpoint.icon == null)
                continue;
            float f = (float)(((double)beaconpoint.x - cameraortho2d.worldXOffset) * cameraortho2d.worldScale);
            float f1 = (float)(((double)beaconpoint.y - cameraortho2d.worldYOffset) * cameraortho2d.worldScale);
            com.maddox.il2.engine.IconDraw.render(beaconpoint.icon, f, f1);
            if(cameraortho2d.worldScale > 0.019999999552965164D)
            {
                float f2 = 20F;
                float f3 = 15F;
                gridFont.output(k, f + f2, f1 - f3, 0.0F, beaconpoint.id);
            }
        }

    }

    private void drawBeacons()
    {
        if(com.maddox.il2.ai.World.cur().diffCur.RealisticNavigationInstruments)
            drawBeacons(renders, gridFont, emptyMat, cameraMap2D, beacons);
    }

    private void drawTargets()
    {
        com.maddox.il2.gui.GUIBriefing.drawTargets(renders, gridFont, emptyMat, cameraMap2D, targets);
    }

    private com.maddox.il2.engine.Mat getIconAir(int i)
    {
        java.lang.String s = null;
        switch(i)
        {
        case 0: // '\0'
            s = "normfly";
            break;

        case 1: // '\001'
            s = "takeoff";
            break;

        case 2: // '\002'
            s = "landing";
            break;

        case 3: // '\003'
            s = "gattack";
            break;

        default:
            return null;
        }
        return com.maddox.il2.engine.IconDraw.get("icons/" + s + ".mat");
    }

    private void drawPlayerPath()
    {
        checkPlayerPath();
        int i = playerPath.size();
        if(i == 0)
            return;
        if(lineNXYZ.length / 3 <= i)
            lineNXYZ = new float[(i + 1) * 3];
        lineNCounter = 0;
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.gui.PathPoint pathpoint = (com.maddox.il2.gui.PathPoint)playerPath.get(j);
            lineNXYZ[lineNCounter * 3 + 0] = (float)(((double)pathpoint.x - cameraMap2D.worldXOffset) * cameraMap2D.worldScale);
            lineNXYZ[lineNCounter * 3 + 1] = (float)(((double)pathpoint.y - cameraMap2D.worldYOffset) * cameraMap2D.worldScale);
            lineNXYZ[lineNCounter * 3 + 2] = 0.0F;
            lineNCounter++;
        }

        com.maddox.il2.engine.Render.drawBeginLines(-1);
        com.maddox.il2.engine.Render.drawLines(lineNXYZ, lineNCounter, 2.0F, 0xff000000, com.maddox.il2.engine.Mat.NOWRITEZ | com.maddox.il2.engine.Mat.MODULATE | com.maddox.il2.engine.Mat.NOTEXTURE | com.maddox.il2.engine.Mat.BLEND, 3);
        com.maddox.il2.engine.Render.drawEnd();
        com.maddox.il2.engine.IconDraw.setColor(0xff00ffff);
        float f = 0.0F;
        for(int k = 0; k < i; k++)
        {
            com.maddox.il2.gui.PathPoint pathpoint1 = (com.maddox.il2.gui.PathPoint)playerPath.get(k);
            float f1 = (float)(((double)pathpoint1.x - cameraMap2D.worldXOffset) * cameraMap2D.worldScale);
            float f2 = (float)(((double)pathpoint1.y - cameraMap2D.worldYOffset) * cameraMap2D.worldScale);
            com.maddox.il2.engine.IconDraw.render(getIconAir(pathpoint1.type), f1, f2);
            if(k == i - 1)
                gridFont.output(0xff000000, (int)f1 + com.maddox.il2.engine.IconDraw.scrSizeX() / 2 + 2, (int)f2 - com.maddox.il2.engine.IconDraw.scrSizeY() / 2 - 2, 0.0F, "" + (k + 1));
            float f3 = 1.0F - (float)curScale / 7F;
            if(k >= i - 1)
                continue;
            com.maddox.il2.gui.PathPoint pathpoint2 = (com.maddox.il2.gui.PathPoint)playerPath.get(k + 1);
            com.maddox.JGP.Point3f point3f = new Point3f(pathpoint1.x, pathpoint1.y, 0.0F);
            com.maddox.JGP.Point3f point3f1 = new Point3f(pathpoint2.x, pathpoint2.y, 0.0F);
            point3f.sub(point3f1);
            float f4 = 57.32484F * (float)java.lang.Math.atan2(point3f.x, point3f.y);
            for(f4 = (f4 + 180F) % 360F; f4 < 0.0F; f4 += 360F);
            for(; f4 >= 360F; f4 -= 360F);
            f4 = java.lang.Math.round(f4);
            float f5 = 0.0F;
            float f6 = 0.0F;
            if(f4 >= 0.0F && f4 < 90F)
            {
                f5 = 15F;
                f6 = -40F;
                if(f >= 270F && f <= 360F)
                {
                    f5 = -70F;
                    f6 = 60F;
                }
            } else
            if(f4 >= 90F && f4 < 180F)
            {
                f5 = 15F;
                f6 = 60F;
                if(f >= 180F && f < 270F)
                {
                    f5 = -70F;
                    f6 = -15F;
                }
            } else
            if(f4 >= 180F && f4 < 270F)
            {
                f5 = -70F;
                f6 = 60F;
                if(f >= 90F && f < 180F)
                {
                    f5 = 15F;
                    f6 = 60F;
                }
            } else
            if(f4 >= 270F && f4 <= 360F)
            {
                f5 = -70F;
                f6 = -40F;
                if(f >= 0.0F && f < 90F)
                {
                    f5 = 15F;
                    f6 = -40F;
                }
            }
            f5 *= f3;
            f6 *= f3;
            if(curScale >= 3)
            {
                if(f5 < 0.0F)
                    f5 /= 2.0F;
                if(f6 > 0.0F)
                    f6 /= 2.0F;
            }
            f = f4;
            gridFont.output(0xff000000, f1 + f5, f2 + f6, 0.0F, "" + (k + 1));
            if(curScale >= 2)
                continue;
            double d = java.lang.Math.sqrt(point3f.y * point3f.y + point3f.x * point3f.x) / 1000D;
            if(d < 0.5D)
                continue;
            java.lang.String s = " km";
            if(com.maddox.il2.game.HUD.drawSpeed() == 2 || com.maddox.il2.game.HUD.drawSpeed() == 3)
            {
                d *= 0.53995698690414429D;
                s = " nm";
            }
            java.lang.String s1 = "" + d;
            s1 = s1.substring(0, s1.indexOf(".") + 2);
            gridFont.output(0xff000000, f1 + f5, (f2 + f6) - 22F, 0.0F, (int)f4 + "\260");
            gridFont.output(0xff000000, f1 + f5, (f2 + f6) - 44F, 0.0F, s1 + s);
        }

    }

    private void checkPlayerPath()
    {
        com.maddox.rts.SectFile sectfile = com.maddox.il2.game.Main.cur().currentMissionFile;
        java.lang.String s = null;
        if(com.maddox.il2.game.Mission.isCoop())
        {
            s = com.maddox.il2.gui.GUINetAircraft.selectedWingName();
            if(s == null)
                s = sectfile.get("MAIN", "player", (java.lang.String)null);
        } else
        {
            s = sectfile.get("MAIN", "player", (java.lang.String)null);
        }
        if(s == null)
            if(playerName == null)
            {
                return;
            } else
            {
                playerPath.clear();
                playerName = null;
                return;
            }
        if(s.equals(playerName))
            return;
        playerName = s;
        playerPath.clear();
        if(playerName != null)
        {
            int i = sectfile.sectionIndex(playerName + "_WAY");
            if(i >= 0)
            {
                int j = sectfile.vars(i);
                for(int k = 0; k < j; k++)
                {
                    com.maddox.il2.gui.PathPoint pathpoint = new PathPoint();
                    java.lang.String s1 = sectfile.var(i, k);
                    if("NORMFLY".equals(s1))
                        pathpoint.type = 0;
                    else
                    if("TAKEOFF".equals(s1))
                        pathpoint.type = 1;
                    else
                    if("LANDING".equals(s1))
                        pathpoint.type = 2;
                    else
                    if("GATTACK".equals(s1))
                        pathpoint.type = 3;
                    else
                        pathpoint.type = 0;
                    java.lang.String s2 = sectfile.value(i, k);
                    if(s2 == null || s2.length() <= 0)
                    {
                        pathpoint.x = pathpoint.y = 0.0F;
                    } else
                    {
                        com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(s2);
                        pathpoint.x = numbertokenizer.next(-1E+030F, -1E+030F, 1E+030F);
                        pathpoint.y = numbertokenizer.next(-1E+030F, -1E+030F, 1E+030F);
                        double d = numbertokenizer.next(0.0D, 0.0D, 10000D);
                        double d1 = numbertokenizer.next(0.0D, 0.0D, 1000D);
                    }
                    playerPath.add(pathpoint);
                }

            }
        }
    }

    protected void doRenderMap2D()
    {
        com.maddox.il2.game.ZutiRadarRefresh.update(lastScale < cameraMap2D.worldScale);
        lastScale = cameraMap2D.worldScale;
        int i = (int)java.lang.Math.round(((double)com.maddox.il2.game.Mission.ZUTI_ICON_SIZE * (double)client.root.win.dx) / 1024D);
        com.maddox.il2.engine.IconDraw.setScrSize(i, i);
        try
        {
            com.maddox.il2.game.Mission mission = com.maddox.il2.game.Main.cur().mission;
            if(mission != null)
            {
                mission.getClass();
                mission.getClass();
                com.maddox.il2.game.ZutiSupportMethods.drawTargets(renders, gridFont, emptyMat, cameraMap2D);
            } else
            {
                drawTargets();
            }
            drawBornPlaces();
            drawPlayerPath();
            drawBeacons();
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
    }

    protected int findBornPlace(float f, float f1)
    {
        if(id() != 40 && id() != 39)
            return -1;
        java.util.ArrayList arraylist = com.maddox.il2.ai.World.cur().bornPlaces;
        if(arraylist == null || arraylist.size() == 0)
            return -1;
        int i = arraylist.size();
        double d = (double)(com.maddox.il2.engine.IconDraw.scrSizeX() / 2) / cameraMap2D.worldScale;
        d = 2D * d * d;
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.net.BornPlace bornplace = (com.maddox.il2.net.BornPlace)arraylist.get(j);
            if(bornplace.zutiDisableSpawning)
                continue;
            double d1 = bornplace.place.x;
            double d2 = bornplace.place.y;
            if(bornplace.zutiStaticPositionOnly)
            {
                d1 = bornplace.zutiOriginalX;
                d2 = bornplace.zutiOriginalY;
            }
            if((d1 - (double)f) * (d1 - (double)f) + (d2 - (double)f1) * (d2 - (double)f1) < d && bornplace.army != 0 && bornplace.zutiCanUserJoin())
                return j;
        }

        return -1;
    }

    protected boolean isBornPlace(float f, float f1)
    {
        return findBornPlace(f, f1) >= 0;
    }

    protected void setBornPlace(float f, float f1)
    {
        int i = findBornPlace(f, f1);
        if(i < 0)
            return;
        com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
        int j = netuser.getArmy();
        netuser.setBornPlace(i);
        if(j != netuser.getArmy() && briefSound != null)
        {
            java.lang.String s = com.maddox.il2.game.Main.cur().currentMissionFile.get("MAIN", "briefSound" + netuser.getArmy());
            if(s != null)
            {
                briefSound = s;
                com.maddox.rts.CmdEnv.top().exec("music LIST " + briefSound);
            }
        }
        com.maddox.il2.net.BornPlace bornplace = (com.maddox.il2.net.BornPlace)com.maddox.il2.ai.World.cur().bornPlaces.get(i);
        com.maddox.il2.ai.UserCfg usercfg = com.maddox.il2.ai.World.cur().userCfg;
        if(usercfg != null && !com.maddox.il2.game.ZutiSupportMethods.isRegimentValidForSelectedHB(usercfg.netRegiment, bornplace))
        {
            java.lang.String s1 = com.maddox.il2.game.ZutiSupportMethods.getHomeBaseFirstCountry(bornplace);
            s1 = com.maddox.il2.game.ZutiSupportMethods.getUserCfgRegiment(s1);
            usercfg.netRegiment = s1;
            usercfg.netSquadron = 0;
            com.maddox.il2.gui.GUIAirArming.stateId = 2;
            com.maddox.il2.game.Main.stateStack().push(55);
        }
        if(usercfg != null)
        {
            boolean flag = false;
            java.util.ArrayList arraylist = bornplace.zutiGetAcLoadouts(usercfg.netAirName);
            int k = 0;
            do
            {
                if(k >= arraylist.size())
                    break;
                if(((java.lang.String)arraylist.get(k)).equals(com.maddox.il2.game.I18N.weapons(usercfg.netAirName, usercfg.getWeapon(usercfg.netAirName))))
                {
                    flag = true;
                    break;
                }
                k++;
            } while(true);
            if(!flag)
            {
                usercfg.setWeapon(usercfg.netAirName, (java.lang.String)arraylist.get(0));
                com.maddox.il2.gui.GUIAirArming.stateId = 2;
                com.maddox.il2.game.Main.stateStack().push(55);
            }
        }
        com.maddox.il2.game.ZutiRadarRefresh.findRadars(com.maddox.il2.game.ZutiSupportMethods.getPlayerArmy());
        fillBeacons();
    }

    protected void doMouseButton(int i, boolean flag, float f, float f1)
    {
        com.maddox.il2.engine.GUIRenders _tmp = renders;
        if(i == 0)
        {
            bLPressed = flag;
            if(bSelectBorn)
            {
                if(bLPressed)
                {
                    float f2 = (float)(cameraMap2D.worldXOffset + (double)f / cameraMap2D.worldScale);
                    float f3 = (float)(cameraMap2D.worldYOffset + (double)(renders.win.dy - f1 - 1.0F) / cameraMap2D.worldScale);
                    setBornPlace(f2, f3);
                }
                return;
            }
        }
        super.doMouseButton(i, flag, f, f1);
    }

    protected void doMouseMove(float f, float f1)
    {
        if(bLPressed && !bSelectBorn)
        {
            super.doMouseMove(f, f1);
        } else
        {
            float f2 = (float)(cameraMap2D.worldXOffset + (double)f / cameraMap2D.worldScale);
            float f3 = (float)(cameraMap2D.worldYOffset + (double)(renders.win.dy - f1 - 1.0F) / cameraMap2D.worldScale);
            bSelectBorn = isBornPlace(f2, f3);
            renders.mouseCursor = bSelectBorn ? 2 : 3;
        }
    }

    protected void fillMap()
        throws java.lang.Exception
    {
        super.fillMap();
        com.maddox.rts.SectFile sectfile = com.maddox.il2.game.Main.cur().currentMissionFile;
        try
        {
            iconBornPlace = com.maddox.il2.engine.IconDraw.get("icons/born.mat");
            iconPlayer = com.maddox.il2.engine.IconDraw.get("icons/player.mat");
            com.maddox.il2.game.ZutiSupportMethods.setTargetsLoaded(false);
            if(com.maddox.il2.game.Mission.cur() != null)
                com.maddox.il2.game.ZutiSupportMethods.fillTargets(sectfile);
            else
                com.maddox.il2.gui.GUIBriefing.fillTargets(sectfile, targets);
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
    }

    protected void clientRender()
    {
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient dialogclient = dialogClient;
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp = dialogclient;
        dialogclient.draw(dialogclient.x1024(427F), dialogclient.y1024(633F), dialogclient.x1024(170F), dialogclient.y1024(48F), 1, i18n("brief.Fly"));
    }

    protected java.lang.String infoMenuInfo()
    {
        return i18n("brief.info");
    }

    public GUIBriefing(int i)
    {
        super(i);
        playerPath = new ArrayList();
        targets = new ArrayList();
        beacons = new ArrayList();
        lastScale = 0.0D;
        lineNXYZ = new float[6];
        bSelectBorn = false;
    }

    protected com.maddox.il2.ai.AirportCarrier getCarrier(com.maddox.il2.net.NetUser netuser)
    {
        com.maddox.il2.net.BornPlace bornplace = (com.maddox.il2.net.BornPlace)com.maddox.il2.ai.World.cur().bornPlaces.get(netuser.getBornPlace());
        if(!com.maddox.il2.ai.World.land().isWater(bornplace.place.x, bornplace.place.y))
        {
            return null;
        } else
        {
            com.maddox.il2.engine.Loc loc = new Loc(bornplace.place.x, bornplace.place.y, 0.0D, 0.0F, 0.0F, 0.0F);
            com.maddox.il2.ai.AirportCarrier airportcarrier = (com.maddox.il2.ai.AirportCarrier)com.maddox.il2.ai.Airport.nearest(loc.getPoint(), -1, 4);
            return airportcarrier;
        }
    }

    protected boolean isCarrierDeckFree(com.maddox.il2.net.NetUser netuser)
    {
        com.maddox.il2.ai.AirportCarrier airportcarrier;
        com.maddox.il2.net.BornPlace bornplace = (com.maddox.il2.net.BornPlace)com.maddox.il2.ai.World.cur().bornPlaces.get(netuser.getBornPlace());
        if(bornplace.zutiAirspawnIfCarrierFull || bornplace.zutiAirspawnOnly || !com.maddox.il2.ai.World.cur().diffCur.Takeoff_N_Landing || !com.maddox.il2.ai.World.land().isWater(bornplace.place.x, bornplace.place.y))
            return true;
        com.maddox.il2.engine.Loc loc = new Loc(bornplace.place.x, bornplace.place.y, 0.0D, 0.0F, 0.0F, 0.0F);
        airportcarrier = (com.maddox.il2.ai.AirportCarrier)com.maddox.il2.ai.Airport.nearest(loc.getPoint(), -1, 4);
        if(airportcarrier == null || !airportcarrier.ship().isAlive() || airportcarrier.ship().zutiIsStatic())
            break MISSING_BLOCK_LABEL_238;
        com.maddox.il2.ai.UserCfg usercfg = com.maddox.il2.ai.World.cur().userCfg;
        java.lang.Class class1 = (java.lang.Class)com.maddox.rts.Property.value(usercfg.netAirName, "airClass", null);
        com.maddox.il2.objects.air.NetAircraft netaircraft = (com.maddox.il2.objects.air.NetAircraft)class1.newInstance();
        com.maddox.il2.ai.air.CellAirField cellairfield = airportcarrier.ship().getCellTO();
        com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)netaircraft;
        if(aircraft.FM != null);
        aircraft.setFM(1, false);
        com.maddox.il2.ai.air.CellAirPlane cellairplane = aircraft.getCellAirPlane();
        if(!cellairfield.findPlaceForAirPlane(cellairplane))
            break MISSING_BLOCK_LABEL_224;
        aircraft = null;
        return true;
        Object obj = null;
        return false;
        java.lang.Exception exception;
        exception;
        exception.printStackTrace();
        return false;
        return true;
    }

    protected boolean isValidArming()
    {
        com.maddox.il2.ai.UserCfg usercfg;
        usercfg = com.maddox.il2.ai.World.cur().userCfg;
        if(usercfg.netRegiment == null)
            return false;
        if(((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).netUserRegiment.isEmpty() && com.maddox.il2.engine.Actor.getByName(usercfg.netRegiment) == null)
            return false;
        if(usercfg.netAirName == null)
            return false;
        if(com.maddox.rts.Property.value(usercfg.netAirName, "airClass", null) == null)
            return false;
        if(usercfg.getWeapon(usercfg.netAirName) == null)
            return false;
        java.lang.Class class1;
        boolean flag;
        class1 = (java.lang.Class)com.maddox.rts.Property.value(usercfg.netAirName, "airClass", null);
        com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
        int i = netuser.getBornPlace();
        com.maddox.il2.net.BornPlace bornplace = (com.maddox.il2.net.BornPlace)com.maddox.il2.ai.World.cur().bornPlaces.get(i);
        if(bornplace.airNames == null)
            break MISSING_BLOCK_LABEL_215;
        java.util.ArrayList arraylist = bornplace.airNames;
        flag = false;
        int j = 0;
        do
        {
            if(j >= arraylist.size())
                break;
            java.lang.String s = (java.lang.String)arraylist.get(j);
            java.lang.Class class2 = (java.lang.Class)com.maddox.rts.Property.value(s, "airClass", null);
            if(class2 != null && class1 == class2)
            {
                flag = true;
                break;
            }
            j++;
        } while(true);
        if(!flag)
            return false;
        return com.maddox.il2.objects.air.Aircraft.weaponsExist(class1, usercfg.getWeapon(usercfg.netAirName));
        java.lang.Exception exception;
        exception;
        return false;
    }

    protected java.lang.String playerName;
    protected java.util.ArrayList playerPath;
    protected java.util.ArrayList targets;
    protected java.util.ArrayList beacons;
    protected com.maddox.il2.engine.Mat iconBornPlace;
    protected com.maddox.il2.engine.Mat iconPlayer;
    private double lastScale;
    public static java.util.Set ZUTI_TARGETS = new HashSet();
    public static boolean ZUTI_IS_BRIEFING_ACTIVE = false;
    private static java.lang.String tip[] = new java.lang.String[3];
    private float lineNXYZ[];
    private int lineNCounter;
    protected boolean bSelectBorn;

}

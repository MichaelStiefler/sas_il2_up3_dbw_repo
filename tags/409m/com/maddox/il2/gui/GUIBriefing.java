// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIBriefing.java

package com.maddox.il2.gui;

import com.maddox.JGP.Point2d;
import com.maddox.gwindow.GPoint;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.GUIRenders;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetEnv;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

// Referenced classes of package com.maddox.il2.gui:
//            GUIBriefingGeneric, GUINetAircraft

public class GUIBriefing extends com.maddox.il2.gui.GUIBriefingGeneric
{
    public static class TargetPoint
    {

        public float x;
        public float y;
        public int r;
        public int type;
        public int importance;
        public com.maddox.il2.engine.Mat icon;
        public java.lang.String nameTarget;

        public TargetPoint()
        {
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
            com.maddox.il2.engine.IconDraw.render(iconBornPlace, f, f1);
            if(i1 == k && iconPlayer != null)
                com.maddox.il2.engine.Render.drawTile(f, f1, com.maddox.il2.engine.IconDraw.scrSizeX(), com.maddox.il2.engine.IconDraw.scrSizeY(), 0.0F, iconPlayer, com.maddox.il2.ai.Army.color(bornplace2.army), 0.0F, 1.0F, 1.0F, -1F);
            if(bornplace2.tmpForBrief > 0)
                gridFont.output(com.maddox.il2.ai.Army.color(bornplace2.army), (int)f + com.maddox.il2.engine.IconDraw.scrSizeX() / 2 + 2, (int)f1 - com.maddox.il2.engine.IconDraw.scrSizeY() / 2 - 2, 0.0F, "" + bornplace2.tmpForBrief);
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
                if(i1 != 2)
                {
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
            com.maddox.il2.gui.TargetPoint targetpoint = (com.maddox.il2.gui.TargetPoint)arraylist.get(k);
            if(targetpoint.icon != null)
            {
                float f5 = (float)(((double)targetpoint.x - cameraortho2d.worldXOffset) * cameraortho2d.worldScale);
                float f6 = (float)(((double)targetpoint.y - cameraortho2d.worldYOffset) * cameraortho2d.worldScale);
                com.maddox.il2.engine.IconDraw.render(targetpoint.icon, f5, f6);
                if(f5 >= f - f2 && f5 <= f + f2 && f6 >= f1 - f2 && f6 <= f1 + f2)
                {
                    j = k;
                    f3 = f5;
                    f4 = f6;
                }
            }
        }

        if(j != -1)
        {
            com.maddox.il2.gui.TargetPoint targetpoint1 = (com.maddox.il2.gui.TargetPoint)arraylist.get(j);
            for(int l = 0; l < 3; l++)
                tip[l] = null;

            if(targetpoint1.importance == 0)
                tip[0] = com.maddox.il2.game.I18N.gui("brief.Primary");
            else
                tip[0] = com.maddox.il2.game.I18N.gui("brief.Secondary");
            switch(targetpoint1.type)
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
            if(targetpoint1.nameTarget != null)
                tip[2] = targetpoint1.nameTarget;
            float f7 = ttfont.width(tip[0]);
            int i1 = 1;
            for(int j1 = 1; j1 < 3; j1++)
            {
                if(tip[j1] == null)
                    break;
                i1 = j1;
                float f8 = ttfont.width(tip[j1]);
                if(f7 < f8)
                    f7 = f8;
            }

            float f9 = -ttfont.descender();
            float f10 = (float)ttfont.height() + f9;
            f7 += 2.0F * f9;
            float f11 = f10 * (float)(i1 + 1) + 2.0F * f9;
            float f12 = f3 - f7 / 2.0F;
            float f13 = f4 + f2;
            if(f12 + f7 > guirenders.win.dx)
                f12 = guirenders.win.dx - f7;
            if(f13 + f11 > guirenders.win.dy)
                f13 = guirenders.win.dy - f11;
            if(f12 < 0.0F)
                f12 = 0.0F;
            if(f13 < 0.0F)
                f13 = 0.0F;
            com.maddox.il2.engine.Render.drawTile(f12, f13, f7, f11, 0.0F, mat, 0xcf7fffff, 0.0F, 0.0F, 1.0F, 1.0F);
            com.maddox.il2.engine.Render.drawEnd();
            for(int k1 = 0; k1 <= i1; k1++)
                ttfont.output(0xff000000, f12 + f9, f13 + f9 + (float)(i1 - k1) * f10 + f9, 0.0F, tip[k1]);

        }
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
        for(int k = 0; k < i; k++)
        {
            com.maddox.il2.gui.PathPoint pathpoint1 = (com.maddox.il2.gui.PathPoint)playerPath.get(k);
            float f = (float)(((double)pathpoint1.x - cameraMap2D.worldXOffset) * cameraMap2D.worldScale);
            float f1 = (float)(((double)pathpoint1.y - cameraMap2D.worldYOffset) * cameraMap2D.worldScale);
            com.maddox.il2.engine.IconDraw.render(getIconAir(pathpoint1.type), f, f1);
            gridFont.output(0xff000000, (int)f + com.maddox.il2.engine.IconDraw.scrSizeX() / 2 + 2, (int)f1 - com.maddox.il2.engine.IconDraw.scrSizeY() / 2 - 2, 0.0F, "" + (k + 1));
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
        if(id() == 40 || id() == 39)
        {
            drawBornPlaces();
        } else
        {
            drawPlayerPath();
            drawTargets();
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
            if((bornplace.place.x - (double)f) * (bornplace.place.x - (double)f) + (bornplace.place.y - (double)f1) * (bornplace.place.y - (double)f1) < d && bornplace.army != 0)
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
        if(id() == 40 || id() == 39)
        {
            iconBornPlace = com.maddox.il2.engine.IconDraw.get("icons/born.mat");
            iconPlayer = com.maddox.il2.engine.IconDraw.get("icons/player.mat");
        } else
        {
            com.maddox.il2.gui.GUIBriefing.fillTargets(sectfile, targets);
        }
    }

    protected void clientRender()
    {
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient dialogclient = dialogClient;
        com.maddox.il2.gui.GUIBriefingGeneric.DialogClient _tmp = dialogclient;
        dialogclient.draw(dialogclient.x1024(768F), dialogclient.y1024(656F), dialogclient.x1024(160F), dialogclient.y1024(48F), 2, i18n("brief.Fly"));
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
        lineNXYZ = new float[6];
        bSelectBorn = false;
    }

    protected java.lang.String playerName;
    protected java.util.ArrayList playerPath;
    protected java.util.ArrayList targets;
    protected com.maddox.il2.engine.Mat iconBornPlace;
    protected com.maddox.il2.engine.Mat iconPlayer;
    private static java.lang.String tip[] = new java.lang.String[3];
    private float lineNXYZ[];
    private int lineNCounter;
    protected boolean bSelectBorn;

}

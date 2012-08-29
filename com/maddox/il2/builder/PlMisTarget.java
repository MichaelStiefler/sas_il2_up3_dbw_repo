// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PlMisTarget.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowHSliderInt;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.il2.ai.Army;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.game.I18N;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, ActorTarget, PPoint, Path, 
//            PlMission, PathAir, PathChief, Builder, 
//            BldConfig, WSelect

public class PlMisTarget extends com.maddox.il2.builder.Plugin
{
    static class Item
    {

        public java.lang.String name;
        public int indx;

        public Item(java.lang.String s, int i)
        {
            name = s;
            indx = i;
        }
    }


    public PlMisTarget()
    {
        allActors = new ArrayList();
        line2XYZ = new float[6];
        p2d = new Point2d();
        p2dt = new Point2d();
        p3d = new Point3d();
        _actorInfo = new java.lang.String[2];
    }

    private int targetColor(int i, boolean flag)
    {
        if(flag)
            return com.maddox.il2.builder.Builder.colorSelected();
        switch(i)
        {
        case 0: // '\0'
            return -1;

        case 1: // '\001'
            return 0xff00ff00;

        case 2: // '\002'
            return 0xff7f0000;
        }
        return 0;
    }

    public void renderMap2DBefore()
    {
        if(com.maddox.il2.builder.Plugin.builder.isFreeView())
            return;
        if(!viewType.bChecked)
            return;
        com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
        int i = allActors.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.builder.ActorTarget actortarget = (com.maddox.il2.builder.ActorTarget)allActors.get(j);
            if(com.maddox.il2.engine.Actor.isValid(actortarget.getTarget()) && com.maddox.il2.builder.Plugin.builder.project2d(actortarget.pos.getAbsPoint(), p2d) && com.maddox.il2.builder.Plugin.builder.project2d(actortarget.getTarget().pos.getAbsPoint(), p2dt) && p2d.distance(p2dt) > 4D)
            {
                int k = targetColor(actortarget.importance, actortarget == actor);
                line2XYZ[0] = (float)p2d.x;
                line2XYZ[1] = (float)p2d.y;
                line2XYZ[2] = 0.0F;
                line2XYZ[3] = (float)p2dt.x;
                line2XYZ[4] = (float)p2dt.y;
                line2XYZ[5] = 0.0F;
                com.maddox.il2.engine.Render.drawBeginLines(-1);
                com.maddox.il2.engine.Render.drawLines(line2XYZ, 2, 1.0F, k, com.maddox.il2.engine.Mat.NOWRITEZ | com.maddox.il2.engine.Mat.MODULATE | com.maddox.il2.engine.Mat.NOTEXTURE | com.maddox.il2.engine.Mat.BLEND, 3);
                com.maddox.il2.engine.Render.drawEnd();
            }
        }

    }

    public void renderMap2DAfter()
    {
        if(com.maddox.il2.builder.Plugin.builder.isFreeView())
            return;
        if(!viewType.bChecked)
            return;
        com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
        int i = allActors.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.builder.ActorTarget actortarget = (com.maddox.il2.builder.ActorTarget)allActors.get(j);
            if(com.maddox.il2.builder.Plugin.builder.project2d(actortarget.pos.getAbsPoint(), p2d))
            {
                int k = targetColor(actortarget.importance, actortarget == actor);
                com.maddox.il2.engine.IconDraw.setColor(k);
                if(com.maddox.il2.engine.Actor.isValid(actortarget.getTarget()) && com.maddox.il2.builder.Plugin.builder.project2d(actortarget.getTarget().pos.getAbsPoint(), p2dt) && p2d.distance(p2dt) > 4D)
                    com.maddox.il2.engine.Render.drawTile((float)(p2dt.x - (double)(com.maddox.il2.builder.Plugin.builder.conf.iconSize / 2)), (float)(p2dt.y - (double)(com.maddox.il2.builder.Plugin.builder.conf.iconSize / 2)), com.maddox.il2.builder.Plugin.builder.conf.iconSize, com.maddox.il2.builder.Plugin.builder.conf.iconSize, 0.0F, com.maddox.il2.builder.Plugin.targetIcon, k, 0.0F, 1.0F, 1.0F, -1F);
                com.maddox.il2.engine.IconDraw.render(actortarget, p2d.x, p2d.y);
                if(actortarget.type == 3 || actortarget.type == 6 || actortarget.type == 1)
                {
                    actortarget.pos.getAbs(p3d);
                    p3d.x += actortarget.r;
                    if(com.maddox.il2.builder.Plugin.builder.project2d(p3d, p2dt))
                    {
                        double d = p2dt.x - p2d.x;
                        if(d > (double)(com.maddox.il2.builder.Plugin.builder.conf.iconSize / 3))
                            drawCircle(p2d.x, p2d.y, d, k);
                    }
                }
            }
        }

    }

    private void drawCircle(double d, double d1, double d2, int i)
    {
        int j = 48;
        double d3 = 6.2831853071795862D / (double)j;
        double d4 = 0.0D;
        for(int k = 0; k < j; k++)
        {
            _circleXYZ[k * 3 + 0] = (float)(d + d2 * java.lang.Math.cos(d4));
            _circleXYZ[k * 3 + 1] = (float)(d1 + d2 * java.lang.Math.sin(d4));
            _circleXYZ[k * 3 + 2] = 0.0F;
            d4 += d3;
        }

        com.maddox.il2.engine.Render.drawBeginLines(-1);
        com.maddox.il2.engine.Render.drawLines(_circleXYZ, j, 1.0F, i, com.maddox.il2.engine.Mat.NOWRITEZ | com.maddox.il2.engine.Mat.MODULATE | com.maddox.il2.engine.Mat.NOTEXTURE, 4);
        com.maddox.il2.engine.Render.drawEnd();
    }

    public boolean save(com.maddox.rts.SectFile sectfile)
    {
        int i = allActors.size();
        if(i == 0)
            return true;
        int j = sectfile.sectionAdd("Target");
        for(int k = 0; k < i; k++)
        {
            com.maddox.il2.builder.ActorTarget actortarget = (com.maddox.il2.builder.ActorTarget)allActors.get(k);
            java.lang.String s = "";
            int l = 0;
            int i1 = 0;
            int j1 = 0;
            if(com.maddox.il2.engine.Actor.isValid(actortarget.target))
            {
                if(actortarget.target instanceof com.maddox.il2.builder.PPoint)
                {
                    s = actortarget.target.getOwner().name();
                    l = ((com.maddox.il2.builder.Path)actortarget.target.getOwner()).pointIndx((com.maddox.il2.builder.PPoint)actortarget.target);
                } else
                {
                    s = actortarget.target.name();
                }
                com.maddox.JGP.Point3d point3d = actortarget.target.pos.getAbsPoint();
                i1 = (int)point3d.x;
                j1 = (int)point3d.y;
            }
            sectfile.lineAdd(j, "" + actortarget.type + " " + actortarget.importance + " " + (actortarget.bTimeout ? "1 " : "0 ") + actortarget.timeout + " " + actortarget.destructLevel + (actortarget.bLanding ? 1 : 0) + " " + (int)actortarget.pos.getAbsPoint().x + " " + (int)actortarget.pos.getAbsPoint().y + " " + actortarget.r + (s.length() <= 0 ? "" : " " + l + " " + s + " " + i1 + " " + j1));
        }

        return true;
    }

    public void load(com.maddox.rts.SectFile sectfile)
    {
        int i = sectfile.sectionIndex("Target");
        if(i >= 0)
        {
            int j = sectfile.vars(i);
            com.maddox.JGP.Point3d point3d = new Point3d();
            for(int k = 0; k < j; k++)
            {
                com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, k));
                int l = numbertokenizer.next(0, 0, 7);
                int i1 = numbertokenizer.next(0, 0, 2);
                boolean flag = numbertokenizer.next(0) == 1;
                int j1 = numbertokenizer.next(0, 0, 720);
                int k1 = numbertokenizer.next(0);
                boolean flag1 = (k1 & 1) == 1;
                k1 /= 10;
                if(k1 < 0)
                    k1 = 0;
                if(k1 > 100)
                    k1 = 100;
                point3d.x = numbertokenizer.next(0);
                point3d.y = numbertokenizer.next(0);
                int l1 = numbertokenizer.next(0);
                if(l == 3 || l == 6 || l == 1)
                {
                    if(l1 < 2)
                        l1 = 2;
                    if(l1 > 3000)
                        l1 = 3000;
                }
                int i2 = numbertokenizer.next(0);
                java.lang.String s = numbertokenizer.next(null);
                if(s != null && s.startsWith("Bridge"))
                    s = " " + s;
                com.maddox.il2.builder.ActorTarget actortarget = insert(point3d, l, s, i2, false);
                if(actortarget != null)
                {
                    actortarget.importance = i1;
                    actortarget.bTimeout = flag;
                    actortarget.timeout = j1;
                    actortarget.r = l1;
                    actortarget.bLanding = flag1;
                    actortarget.destructLevel = k1;
                }
            }

        }
    }

    public void deleteAll()
    {
        int i = allActors.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.builder.ActorTarget actortarget = (com.maddox.il2.builder.ActorTarget)allActors.get(j);
            actortarget.destroy();
        }

        allActors.clear();
    }

    public void delete(com.maddox.il2.engine.Actor actor)
    {
        allActors.remove(actor);
        actor.destroy();
    }

    public void afterDelete()
    {
        for(int i = 0; i < allActors.size();)
        {
            com.maddox.il2.builder.ActorTarget actortarget = (com.maddox.il2.builder.ActorTarget)allActors.get(i);
            if(actortarget.target != null && !com.maddox.il2.engine.Actor.isValid(actortarget.target))
            {
                actortarget.destroy();
                allActors.remove(i);
            } else
            {
                i++;
            }
        }

    }

    private com.maddox.il2.builder.ActorTarget insert(com.maddox.JGP.Point3d point3d, int i, java.lang.String s, int j, boolean flag)
    {
        com.maddox.il2.builder.ActorTarget actortarget = new ActorTarget(point3d, i, s, j);
        if(!com.maddox.il2.engine.Actor.isValid(actortarget.target)) goto _L2; else goto _L1
_L1:
        int k = 0;
          goto _L3
_L4:
        com.maddox.il2.builder.ActorTarget actortarget1 = (com.maddox.il2.builder.ActorTarget)allActors.get(k);
        if(actortarget.type != actortarget1.type || !com.maddox.il2.engine.Actor.isValid(actortarget1.target) || actortarget1.target != actortarget.target && (!(actortarget.target instanceof com.maddox.il2.builder.PPoint) || actortarget.target.getOwner() != actortarget1.target.getOwner()))
            continue; /* Loop/switch isn't completed */
        actortarget.destroy();
        return null;
        k++;
_L3:
        if(k < allActors.size()) goto _L4; else goto _L2
_L2:
        com.maddox.il2.builder.Plugin.builder.align(actortarget);
        com.maddox.rts.Property.set(actortarget, "builderSpawn", "");
        com.maddox.rts.Property.set(actortarget, "builderPlugin", this);
        allActors.add(actortarget);
        if(flag)
            com.maddox.il2.builder.Plugin.builder.setSelected(actortarget);
        com.maddox.il2.builder.PlMission.setChanged();
        return actortarget;
        java.lang.Exception exception;
        exception;
        return null;
    }

    public void insert(com.maddox.il2.engine.Loc loc, boolean flag)
    {
        int i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
        int j = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
        if(i != startComboBox1)
            return;
        if(j < 0 || j >= item.length)
        {
            return;
        } else
        {
            insert(loc.getPoint(), item[j].indx, null, 0, flag);
            return;
        }
    }

    public void changeType()
    {
        com.maddox.il2.builder.Plugin.builder.setSelected(null);
    }

    private void updateView()
    {
        int i = allActors.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.builder.ActorTarget actortarget = (com.maddox.il2.builder.ActorTarget)allActors.get(j);
            actortarget.drawing(viewType.bChecked);
        }

    }

    public void configure()
    {
        if(com.maddox.il2.builder.Plugin.getPlugin("Mission") == null)
        {
            throw new RuntimeException("PlMisTarget: plugin 'Mission' not found");
        } else
        {
            pluginMission = (com.maddox.il2.builder.PlMission)com.maddox.il2.builder.Plugin.getPlugin("Mission");
            return;
        }
    }

    private void fillComboBox2(int i)
    {
        if(i != startComboBox1)
            return;
        if(com.maddox.il2.builder.Plugin.builder.wSelect.curFilledType != i)
        {
            com.maddox.il2.builder.Plugin.builder.wSelect.curFilledType = i;
            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.clear(false);
            for(int j = 0; j < item.length; j++)
                com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.add(com.maddox.il2.builder.Plugin.i18n(item[j].name));

            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.setSelected(i, true, false);
        }
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.setSelected(0, true, false);
        com.maddox.il2.builder.Plugin.builder.wSelect.setMesh(null, true);
    }

    public void viewTypeAll(boolean flag)
    {
        viewType.bChecked = flag;
        updateView();
    }

    public java.lang.String[] actorInfo(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.builder.ActorTarget actortarget = (com.maddox.il2.builder.ActorTarget)actor;
        switch(actortarget.importance)
        {
        case 0: // '\0'
            _actorInfo[0] = com.maddox.il2.builder.Plugin.i18n("Primary") + " " + com.maddox.il2.builder.Plugin.i18n(item[actortarget.type].name);
            break;

        case 1: // '\001'
            _actorInfo[0] = com.maddox.il2.builder.Plugin.i18n("Secondary") + " " + com.maddox.il2.builder.Plugin.i18n(item[actortarget.type].name);
            break;

        case 2: // '\002'
            _actorInfo[0] = com.maddox.il2.builder.Plugin.i18n("Secret") + " " + com.maddox.il2.builder.Plugin.i18n(item[actortarget.type].name);
            break;
        }
        if(com.maddox.il2.engine.Actor.isValid(actortarget.getTarget()) && (actortarget.getTarget() instanceof com.maddox.il2.builder.PPoint))
        {
            com.maddox.il2.builder.Path path = (com.maddox.il2.builder.Path)actortarget.getTarget().getOwner();
            if(path instanceof com.maddox.il2.builder.PathAir)
                _actorInfo[1] = ((com.maddox.il2.builder.PathAir)path).typedName;
            else
            if(path instanceof com.maddox.il2.builder.PathChief)
                _actorInfo[1] = com.maddox.rts.Property.stringValue(path, "i18nName", "");
            else
                _actorInfo[1] = path.name();
        } else
        {
            _actorInfo[1] = null;
        }
        return _actorInfo;
    }

    public void syncSelector()
    {
        com.maddox.il2.builder.ActorTarget actortarget = (com.maddox.il2.builder.ActorTarget)com.maddox.il2.builder.Plugin.builder.selectedActor();
        fillComboBox2(startComboBox1);
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.setSelected(actortarget.type, true, false);
        com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.addTab(1, tabTarget);
        wType.cap.set(com.maddox.il2.builder.Plugin.i18n(item[actortarget.type].name));
        float f = 3F;
        if(com.maddox.il2.engine.Actor.isValid(actortarget.getTarget()) && (actortarget.getTarget() instanceof com.maddox.il2.builder.PPoint))
        {
            wTarget.showWindow();
            com.maddox.il2.builder.Path path = (com.maddox.il2.builder.Path)actortarget.getTarget().getOwner();
            if(path instanceof com.maddox.il2.builder.PathAir)
                wTarget.cap.set(((com.maddox.il2.builder.PathAir)path).typedName);
            else
            if(path instanceof com.maddox.il2.builder.PathChief)
                wTarget.cap.set(com.maddox.rts.Property.stringValue(path, "i18nName", ""));
            else
                wTarget.cap.set(path.name());
            f += 2.0F;
        } else
        {
            wTarget.hideWindow();
        }
        if(actortarget.type == 3 || actortarget.type == 6 || actortarget.type == 7)
        {
            wBTimeout.hideWindow();
        } else
        {
            wBTimeout.showWindow();
            wBTimeout.setMetricPos(wBTimeout.metricWin.x, f);
        }
        wBTimeout.setChecked(actortarget.bTimeout, false);
        wLTimeout.setMetricPos(wLTimeout.metricWin.x, f);
        wTimeoutH.setEnable(actortarget.bTimeout);
        wTimeoutM.setEnable(actortarget.bTimeout);
        wTimeoutH.setMetricPos(wTimeoutH.metricWin.x, f);
        wTimeoutM.setMetricPos(wTimeoutM.metricWin.x, f);
        wTimeoutH.setValue("" + (actortarget.timeout / 60) % 24, false);
        wTimeoutM.setValue("" + actortarget.timeout % 60, false);
        f += 2.0F;
        if(actortarget.type == 3 || actortarget.type == 6 || actortarget.type == 1)
        {
            wR.setPos(actortarget.r / 50, false);
            wR.showWindow();
            wR.setMetricPos(wR.metricWin.x, f);
            f += 2.0F;
        } else
        {
            wR.hideWindow();
        }
        if(actortarget.type == 3)
        {
            wBLanding.showWindow();
            wLLanding.showWindow();
            wBLanding.setMetricPos(wBLanding.metricWin.x, f);
            wLLanding.setMetricPos(wLLanding.metricWin.x, f);
            wBLanding.setChecked(actortarget.bLanding, false);
            f += 2.0F;
        } else
        {
            wBLanding.hideWindow();
            wLLanding.hideWindow();
        }
        wImportance.setMetricPos(wImportance.metricWin.x, f);
        wImportance.setSelected(actortarget.importance, true, false);
        f += 2.0F;
        if(actortarget.type == 3 || actortarget.type == 2 || actortarget.type == 7)
        {
            wLDestruct.hideWindow();
            wDestruct.hideWindow();
        } else
        {
            wLDestruct.showWindow();
            wDestruct.showWindow();
            wLDestruct.setMetricPos(wLDestruct.metricWin.x, f);
            f += 2.0F;
            wDestruct.setMetricPos(wDestruct.metricWin.x, f);
            f += 2.0F;
            byte byte0;
            if(actortarget.destructLevel < 12)
                byte0 = 0;
            else
            if(actortarget.destructLevel < 37)
                byte0 = 1;
            else
            if(actortarget.destructLevel < 62)
                byte0 = 2;
            else
            if(actortarget.destructLevel < 87)
                byte0 = 3;
            else
                byte0 = 4;
            wDestruct.setSelected(byte0, true, false);
            if(actortarget.type == 0 || actortarget.type == 1)
            {
                wDestruct.posEnable[0] = false;
                wDestruct.posEnable[4] = true;
            } else
            {
                wDestruct.posEnable[0] = true;
                wDestruct.posEnable[4] = false;
            }
        }
        wLArmy.setMetricPos(wLArmy.metricWin.x, f);
        f += 2.0F;
        wArmy.setMetricPos(wArmy.metricWin.x, f);
        if(com.maddox.il2.engine.Actor.isValid(com.maddox.il2.builder.Path.player))
        {
            wArmy.setSelected(com.maddox.il2.builder.Path.player.getArmy() - 1, true, false);
            wArmy.bEnable = false;
        } else
        {
            wArmy.setSelected(com.maddox.il2.builder.PlMission.cur.missionArmy - 1, true, false);
            wArmy.bEnable = true;
        }
    }

    public void createGUI()
    {
        startComboBox1 = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.size();
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.add(com.maddox.il2.builder.Plugin.i18n("tTarget"));
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.addNotifyListener(new com.maddox.gwindow.GNotifyListener() {

            public boolean notify(com.maddox.gwindow.GWindow gwindow, int j, int k)
            {
                int l = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
                if(l >= 0 && j == 2)
                    fillComboBox2(l);
                return false;
            }

        }
);
        int i;
        for(i = com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.size() - 1; i >= 0; i--)
            if(pluginMission.viewBridge == com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.getItem(i))
                break;

        if(--i >= 0)
        {
            viewType = com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(i, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, com.maddox.il2.builder.Plugin.i18n("showTarget"), null) {

                public void execute()
                {
                    bChecked = !bChecked;
                    updateView();
                }

            }
);
            viewType.bChecked = true;
        }
        com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.create(new GWindowDialogClient());
        tabTarget = com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.createTab(com.maddox.il2.builder.Plugin.i18n("tTarget"), gwindowdialogclient);
        gwindowdialogclient.addLabel(wType = new GWindowLabel(gwindowdialogclient, 1.0F, 1.0F, 15F, 1.3F, com.maddox.il2.builder.Plugin.i18n("lType"), null));
        gwindowdialogclient.addLabel(wTarget = new GWindowLabel(gwindowdialogclient, 1.0F, 3F, 15F, 1.3F, com.maddox.il2.builder.Plugin.i18n("tTarget"), null));
        gwindowdialogclient.addControl(wBTimeout = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 1.0F, 5F, null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorTarget actortarget = (com.maddox.il2.builder.ActorTarget)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actortarget.bTimeout = isChecked();
                    wTimeoutH.setEnable(actortarget.bTimeout);
                    wTimeoutM.setEnable(actortarget.bTimeout);
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(wLTimeout = new GWindowLabel(gwindowdialogclient, 3F, 5F, 5F, 1.3F, com.maddox.il2.builder.Plugin.i18n("TimeOut"), null));
        gwindowdialogclient.addControl(wTimeoutH = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 9F, 5F, 2.0F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    getTimeOut();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addControl(wTimeoutM = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 12F, 5F, 2.0F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    getTimeOut();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addControl(wR = new com.maddox.gwindow.GWindowHSliderInt(gwindowdialogclient, 0, 61, 11, 1.0F, 7F, 10F) {

            public void afterCreated()
            {
                super.afterCreated();
                bSlidingNotify = true;
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                    return false;
                com.maddox.il2.builder.ActorTarget actortarget = (com.maddox.il2.builder.ActorTarget)com.maddox.il2.builder.Plugin.builder.selectedActor();
                actortarget.r = pos() * 50;
                if(actortarget.r < 2)
                    actortarget.r = 2;
                com.maddox.il2.builder.PlMission.setChanged();
                return false;
            }

        }
);
        gwindowdialogclient.addControl(wBLanding = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 1.0F, 9F, null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorTarget actortarget = (com.maddox.il2.builder.ActorTarget)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actortarget.bLanding = isChecked();
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(wLLanding = new GWindowLabel(gwindowdialogclient, 3F, 9F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("landing"), null));
        gwindowdialogclient.addControl(wImportance = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 1.0F, 11F, 10F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                add(com.maddox.il2.builder.Plugin.i18n("Primary"));
                add(com.maddox.il2.builder.Plugin.i18n("Secondary"));
                add(com.maddox.il2.builder.Plugin.i18n("Secret"));
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorTarget actortarget = (com.maddox.il2.builder.ActorTarget)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actortarget.importance = getSelected();
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(wLDestruct = new GWindowLabel(gwindowdialogclient, 1.0F, 13F, 12F, 1.3F, com.maddox.il2.builder.Plugin.i18n("DestructLevel"), null));
        gwindowdialogclient.addControl(wDestruct = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 1.0F, 15F, 10F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                add("0 %");
                add("25 %");
                add("50 %");
                add("75 %");
                add("100 %");
                boolean aflag[] = new boolean[5];
                for(int j = 0; j < 5; j++)
                    aflag[j] = true;

                posEnable = aflag;
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorTarget actortarget = (com.maddox.il2.builder.ActorTarget)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actortarget.destructLevel = getSelected() * 25;
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(wLArmy = new GWindowLabel(gwindowdialogclient, 1.0F, 15F, 12F, 1.3F, com.maddox.il2.builder.Plugin.i18n("AppliesArmy"), null));
        gwindowdialogclient.addControl(wArmy = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 1.0F, 17F, 10F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                add(com.maddox.il2.game.I18N.army(com.maddox.il2.ai.Army.name(1)));
                add(com.maddox.il2.game.I18N.army(com.maddox.il2.ai.Army.name(2)));
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.PlMission.cur.missionArmy = getSelected() + 1;
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
    }

    private void getTimeOut()
    {
        com.maddox.il2.builder.ActorTarget actortarget = (com.maddox.il2.builder.ActorTarget)com.maddox.il2.builder.Plugin.builder.selectedActor();
        java.lang.String s = wTimeoutH.getValue();
        double d = 0.0D;
        try
        {
            d = java.lang.Double.parseDouble(s);
        }
        catch(java.lang.Exception exception) { }
        if(d < 0.0D)
            d = 0.0D;
        if(d > 12D)
            d = 12D;
        s = wTimeoutM.getValue();
        double d1 = 0.0D;
        try
        {
            d1 = java.lang.Double.parseDouble(s);
        }
        catch(java.lang.Exception exception1) { }
        if(d1 < 0.0D)
            d1 = 0.0D;
        if(d1 > 59D)
            d1 = 59D;
        actortarget.timeout = (int)(d * 60D + d1);
        wTimeoutH.setValue("" + (actortarget.timeout / 60) % 24, false);
        wTimeoutM.setValue("" + actortarget.timeout % 60, false);
        com.maddox.il2.builder.PlMission.setChanged();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    protected java.util.ArrayList allActors;
    com.maddox.il2.builder.Item item[] = {
        new Item("Destroy", 0), new Item("DestroyGround", 1), new Item("DestroyBridge", 2), new Item("Inspect", 3), new Item("Escort", 4), new Item("Defence", 5), new Item("DefenceGround", 6), new Item("DefenceBridge", 7)
    };
    private float line2XYZ[];
    private com.maddox.JGP.Point2d p2d;
    private com.maddox.JGP.Point2d p2dt;
    private com.maddox.JGP.Point3d p3d;
    private static final int NCIRCLESEGMENTS = 48;
    private static float _circleXYZ[] = new float[144];
    private com.maddox.il2.builder.PlMission pluginMission;
    private int startComboBox1;
    private com.maddox.gwindow.GWindowMenuItem viewType;
    private java.lang.String _actorInfo[];
    com.maddox.gwindow.GWindowTabDialogClient.Tab tabTarget;
    com.maddox.gwindow.GWindowLabel wType;
    com.maddox.gwindow.GWindowLabel wTarget;
    com.maddox.gwindow.GWindowCheckBox wBTimeout;
    com.maddox.gwindow.GWindowLabel wLTimeout;
    com.maddox.gwindow.GWindowEditControl wTimeoutH;
    com.maddox.gwindow.GWindowEditControl wTimeoutM;
    com.maddox.gwindow.GWindowHSliderInt wR;
    com.maddox.gwindow.GWindowCheckBox wBLanding;
    com.maddox.gwindow.GWindowLabel wLLanding;
    com.maddox.gwindow.GWindowComboControl wImportance;
    com.maddox.gwindow.GWindowLabel wLDestruct;
    com.maddox.gwindow.GWindowComboControl wDestruct;
    com.maddox.gwindow.GWindowLabel wLArmy;
    com.maddox.gwindow.GWindowComboControl wArmy;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.builder.PlMisTarget.class, "name", "MisTarget");
    }



}

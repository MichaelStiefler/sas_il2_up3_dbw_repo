// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PlMisChief.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.game.I18N;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.util.HashMapInt;
import com.maddox.util.NumberTokenizer;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Locale;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, PathChief, PNodes, PlMission, 
//            Builder, Pathes, WSelect, Path, 
//            PPoint

public class PlMisChief extends com.maddox.il2.builder.Plugin
{
    class ViewItem extends com.maddox.gwindow.GWindowMenuItem
    {

        public void execute()
        {
            bChecked = !bChecked;
            viewType(indx);
        }

        int indx;

        public ViewItem(int i, com.maddox.gwindow.GWindowMenu gwindowmenu, java.lang.String s, java.lang.String s1)
        {
            super(gwindowmenu, s, s1);
            indx = i;
        }
    }

    static class Type
    {

        public java.lang.String name;
        public int moveType;
        public com.maddox.il2.builder.Item item[];

        public Type(java.lang.String s, int i, com.maddox.il2.builder.Item aitem[])
        {
            name = s;
            moveType = i;
            item = aitem;
        }
    }

    static class Item
    {

        public java.lang.String name;
        public int iSectUnits;
        public java.lang.String shortClassName;
        public int army;
        public java.lang.String iconName;
        public double speed;
        public boolean bAirport;

        public Item(int i, java.lang.String s, java.lang.String s1, java.lang.String s2, com.maddox.rts.SectFile sectfile, int j, java.lang.String s3)
        {
            bAirport = false;
            name = s1;
            iconName = s3;
            iSectUnits = sectfile.sectionIndex(s + "." + s1);
            if(iSectUnits < 0)
                throw new RuntimeException("PlMisChief: section '" + s + "." + s1 + "' not found");
            army = j;
            if(i == 3)
            {
                speed = 11.111111640930176D;
            } else
            {
                try
                {
                    speed = -1D;
                    int k = sectfile.vars(iSectUnits);
                    for(int l = 0; l < k; l++)
                    {
                        java.lang.String s4 = sectfile.var(iSectUnits, l);
                        java.lang.Class class1 = com.maddox.il2.builder.PlMisChief.ForceClassLoading(s4);
                        double d = com.maddox.rts.Property.doubleValue(class1, "speed", -1D);
                        if(d < 0.0D)
                            throw new RuntimeException("PlMisChief: property 'speed' NOT found in class '" + s4);
                        if(d > 0.0D && (speed < 0.0D || d < speed))
                            speed = d;
                        if("true".equals(com.maddox.rts.Property.stringValue(class1, "IsAirport", (java.lang.String)null)))
                            bAirport = true;
                    }

                }
                catch(java.lang.Exception exception)
                {
                    java.lang.System.out.println(exception.getMessage());
                    exception.printStackTrace();
                    throw new RuntimeException(exception.toString());
                }
                if(speed <= 0.0D)
                    throw new RuntimeException("PlMisChief: section '" + s + "." + s1 + "' speed == 0");
                if(i == 2)
                    speed *= 2D;
            }
        }
    }


    public PlMisChief()
    {
        p = new Point3d();
        roadOffset = new int[1];
        timeMinAbs = new int[1];
        viewMap = new HashMapInt();
        _actorInfo = new java.lang.String[2];
    }

    public static java.lang.Class ForceClassLoading(java.lang.String s)
    {
        java.lang.Class class1;
        try
        {
            class1 = java.lang.Class.forName(s);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("PlMisChief: class '" + s + "' not found.");
            exception.printStackTrace();
            throw new RuntimeException("Failure");
        }
        int i = s.lastIndexOf('$');
        if(i >= 0)
        {
            java.lang.String s1 = s.substring(0, i);
            try
            {
                com.maddox.rts.ObjIO.classForName(s1);
            }
            catch(java.lang.Exception exception1)
            {
                java.lang.System.out.println("PlMisChief: outer class '" + s1 + "' not found.");
                exception1.printStackTrace();
                throw new RuntimeException("Failure");
            }
        }
        return class1;
    }

    public static int moveType(int i)
    {
        com.maddox.il2.builder.PlMisChief plmischief = (com.maddox.il2.builder.PlMisChief)com.maddox.il2.builder.Plugin.getPlugin("MisChief");
        return plmischief.type[i].moveType;
    }

    public static double speed(int i, int j)
    {
        com.maddox.il2.builder.PlMisChief plmischief = (com.maddox.il2.builder.PlMisChief)com.maddox.il2.builder.Plugin.getPlugin("MisChief");
        return plmischief.type[i].item[j].speed;
    }

    public static boolean isAirport(int i, int j)
    {
        com.maddox.il2.builder.PlMisChief plmischief = (com.maddox.il2.builder.PlMisChief)com.maddox.il2.builder.Plugin.getPlugin("MisChief");
        return plmischief.type[i].item[j].bAirport;
    }

    private java.lang.String makeName()
    {
        int i = 0;
        do
        {
            java.lang.String s = i + "_Chief";
            if(com.maddox.il2.engine.Actor.getByName(s) == null)
                return s;
            i++;
        } while(true);
    }

    public void load(com.maddox.rts.SectFile sectfile)
    {
        int i = sectfile.sectionIndex("Chiefs");
        if(i < 0)
            return;
        int j = sectfile.vars(i);
        for(int k = 0; k < j; k++)
        {
            com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, k));
            java.lang.String s = numbertokenizer.next("");
            java.lang.String s1 = numbertokenizer.next("");
            int l = numbertokenizer.next(0);
            if(l < 1 && l >= com.maddox.il2.builder.Builder.armyAmount())
            {
                com.maddox.il2.builder.Plugin.builder.tipErr("MissionLoad: Wrong chief's army '" + l + "'");
                continue;
            }
            float f = numbertokenizer.next(0.0F);
            int i1 = numbertokenizer.next(2, 0, 3);
            float f1 = numbertokenizer.next(1.0F, 0.5F, 100F);
            if(fSectsUnits.sectionIndex(s1) < 0)
            {
                com.maddox.il2.builder.Plugin.builder.tipErr("MissionLoad: Wrong chief's type '" + s1 + "'");
                continue;
            }
            int j1 = s1.indexOf('.');
            if(j1 <= 0)
            {
                com.maddox.il2.builder.Plugin.builder.tipErr("MissionLoad: Wrong chief's type '" + s1 + "'");
                continue;
            }
            java.lang.String s2 = s1.substring(0, j1);
            java.lang.String s3 = s1.substring(j1 + 1);
            java.lang.String s4 = fSectsUnits.get(s2, s3);
            if(s4 == null)
            {
                com.maddox.il2.builder.Plugin.builder.tipErr("MissionLoad: Wrong chief's type '" + s1 + "'");
                continue;
            }
            if(com.maddox.il2.engine.Actor.getByName(s) != null)
            {
                com.maddox.il2.builder.Plugin.builder.tipErr("MissionLoad: actor '" + s + "' alredy exist");
                continue;
            }
            int k1;
            for(k1 = 0; k1 < type.length; k1++)
                if(type[k1].name.equals(s2))
                    break;

            if(k1 == type.length)
            {
                com.maddox.il2.builder.Plugin.builder.tipErr("MissionLoad: Wrong chief's category '" + s1 + "'");
                continue;
            }
            int l1;
            for(l1 = 0; l1 < type[k1].item.length; l1++)
                if(type[k1].item[l1].name.equals(s3))
                    break;

            if(l1 == type[k1].item.length)
            {
                com.maddox.il2.builder.Plugin.builder.tipErr("MissionLoad: Wrong chief's type '" + s1 + "'");
                continue;
            }
            int i2 = sectfile.sectionIndex(s + "_Road");
            if(i2 < 0)
            {
                com.maddox.il2.builder.Plugin.builder.tipErr("MissionLoad: Wrong chief's road '" + s + "'");
                continue;
            }
            com.maddox.il2.builder.PathChief pathchief = new PathChief(com.maddox.il2.builder.Plugin.builder.pathes, type[k1].moveType, k1, l1, fSectsUnits, type[k1].item[l1].iSectUnits, p);
            pathchief.setName(s);
            pathchief.setArmy(l);
            pathchief.drawing(viewMap.containsKey(k1));
            pathchief._sleep = java.lang.Math.round(f);
            pathchief._skill = i1;
            pathchief._slowfire = f1;
            com.maddox.rts.Property.set(pathchief, "builderPlugin", this);
            com.maddox.rts.Property.set(pathchief, "i18nName", com.maddox.il2.game.I18N.technic(type[k1].item[l1].name));
            com.maddox.il2.builder.PNodes pnodes = new PNodes(pathchief, null, type[k1].item[l1].iconName, null);
            roadOffset[0] = 0;
            try
            {
                do
                {
                    timeMinAbs[0] = 0;
                    pnodes.posXYZ = loadRoadSegments(sectfile, i2, roadOffset, timeMinAbs, type[k1].item[l1].speed / 2D);
                    pnodes.speed = roadSpeed;
                    clampSpeed(pnodes);
                    if(pnodes.posXYZ == null)
                        break;
                    pnodes.timeoutMin = timeMinAbs[0];
                    pnodes = new PNodes(pathchief, pnodes, null);
                } while(true);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.builder.Plugin.builder.tipErr("MissionLoad: Wrong chief's road '" + s + "'");
                pathchief.destroy();
                continue;
            }
            int j2 = pathchief.points();
            if(j2 < 2)
            {
                com.maddox.il2.builder.Plugin.builder.tipErr("MissionLoad: Wrong chief's road '" + s + "'");
            } else
            {
                for(int k2 = 0; k2 < j2 - 1; k2++)
                {
                    com.maddox.il2.builder.PNodes pnodes1 = (com.maddox.il2.builder.PNodes)pathchief.point(k2);
                    p.set(pnodes1.posXYZ[0], pnodes1.posXYZ[1], pnodes1.posXYZ[2]);
                    pnodes1.pos.setAbs(p);
                }

                com.maddox.il2.builder.PNodes pnodes2 = (com.maddox.il2.builder.PNodes)pathchief.point(j2 - 2);
                int l2 = pnodes2.posXYZ.length / 4 - 1;
                p.set(pnodes2.posXYZ[l2 * 4 + 0], pnodes2.posXYZ[l2 * 4 + 1], pnodes2.posXYZ[l2 * 4 + 2]);
                pnodes2 = (com.maddox.il2.builder.PNodes)pathchief.point(j2 - 1);
                pnodes2.pos.setAbs(p);
                pathchief.updateUnitsPos();
                pathchief.computeTimesLoaded();
            }
        }

    }

    private float[] loadRoadSegments(com.maddox.rts.SectFile sectfile, int i, int ai[], int ai1[], double d)
    {
        float af[] = null;
        int j = ai[0];
        if(sectfile.vars(i) <= j)
            return null;
        com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, j));
        numbertokenizer.next(0.0F);
        numbertokenizer.next(0.0F);
        numbertokenizer.next(0.0F);
        ai1[0] = numbertokenizer.next(0);
        int k = numbertokenizer.next(0);
        if(k == 0)
            return null;
        roadSpeed = numbertokenizer.next(d);
        af = new float[k * 4];
        for(int l = 0; k-- > 0; l += 4)
        {
            com.maddox.util.NumberTokenizer numbertokenizer1 = new NumberTokenizer(sectfile.line(i, j++));
            af[l + 0] = numbertokenizer1.next(0.0F);
            af[l + 1] = numbertokenizer1.next(0.0F);
            af[l + 2] = com.maddox.il2.engine.Engine.land().HQ(af[l + 0], af[l + 1]);
            af[l + 3] = numbertokenizer1.next(0.0F);
            numbertokenizer1.next(0);
        }

        ai[0] = j - 1;
        return af;
    }

    private void saveRoadSegment(com.maddox.il2.builder.PNodes pnodes, int i, com.maddox.rts.SectFile sectfile, int j, double d)
    {
        float af[] = pnodes.posXYZ;
        float f = af[i * 4 + 0];
        float f1 = af[i * 4 + 1];
        float f2 = af[i * 4 + 2];
        float f3 = af[i * 4 + 3];
        if(i == 0)
        {
            if(pnodes.timeoutMin > 0.0D)
                sectfile.lineAdd(j, formatPosFloat(f), formatPosFloat(f1) + " " + formatPosFloat(f3) + " " + java.lang.Math.round((pnodes.time + pnodes.timeoutMin * 60D) / 60D) + " " + af.length / 4 + " " + d);
            else
                sectfile.lineAdd(j, formatPosFloat(f), formatPosFloat(f1) + " " + formatPosFloat(f3) + " 0 " + af.length / 4 + " " + d);
        } else
        {
            sectfile.lineAdd(j, formatPosFloat(f), formatPosFloat(f1) + " " + formatPosFloat(f3));
        }
    }

    public boolean save(com.maddox.rts.SectFile sectfile)
    {
        if(com.maddox.il2.builder.Plugin.builder.pathes == null)
            return true;
        int i = sectfile.sectionIndex("Chiefs");
        java.lang.Object aobj[] = com.maddox.il2.builder.Plugin.builder.pathes.getOwnerAttached();
        for(int j = 0; j < aobj.length; j++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)aobj[j];
            if(actor == null)
                break;
            if(actor instanceof com.maddox.il2.builder.PathChief)
            {
                com.maddox.il2.builder.PathChief pathchief = (com.maddox.il2.builder.PathChief)actor;
                int k = pathchief.points();
                if(k < 2)
                {
                    new GWindowMessageBox(com.maddox.il2.builder.Plugin.builder.viewWindow.root, 20F, true, "Save error", "Chief '" + pathchief.name() + "' contains only one waypoint", 3, 0.0F);
                    return false;
                }
                if(i <= -1)
                    i = sectfile.sectionAdd("Chiefs");
                java.lang.String s = pathchief.name();
                java.lang.String s1 = type[pathchief._iType].name + "." + type[pathchief._iType].item[pathchief._iItem].name;
                int l = pathchief.getArmy();
                java.lang.String s2 = s + "_Road";
                if(type[pathchief._iType].moveType == 2)
                    sectfile.lineAdd(i, s, s1 + " " + l + " " + pathchief._sleep + " " + pathchief._skill + " " + pathchief._slowfire);
                else
                    sectfile.lineAdd(i, s, s1 + " " + l);
                int i1 = sectfile.sectionAdd(s2);
                for(int j1 = 0; j1 < k - 1; j1++)
                {
                    com.maddox.il2.builder.PNodes pnodes = (com.maddox.il2.builder.PNodes)pathchief.point(j1);
                    int k1 = pnodes.posXYZ.length / 4;
                    for(int l1 = 0; l1 < k1 - 1; l1++)
                        saveRoadSegment(pnodes, l1, sectfile, i1, pnodes.speed);

                }

                com.maddox.il2.builder.PNodes pnodes1 = (com.maddox.il2.builder.PNodes)pathchief.point(k - 2);
                saveRoadSegment(pnodes1, pnodes1.posXYZ.length / 4 - 1, sectfile, i1, pnodes1.speed);
            }
        }

        return true;
    }

    private java.lang.String formatPosFloat(float f)
    {
        boolean flag = (double)f < 0.0D;
        if(flag)
            f = -f;
        double d = ((double)f + 0.0050000000000000001D) - (double)(int)f;
        if(d >= 0.10000000000000001D)
            return (flag ? "-" : "") + (int)f + "." + (int)(d * 100D);
        else
            return (flag ? "-" : "") + (int)f + ".0" + (int)(d * 100D);
    }

    public void insert(com.maddox.il2.engine.Loc loc, boolean flag)
    {
        java.lang.Object obj = null;
        com.maddox.JGP.Point3d point3d;
        int i;
        int j;
        point3d = loc.getPoint();
        i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
        j = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
        if(com.maddox.il2.builder.Plugin.builder.selectedPath() == null)
            break MISSING_BLOCK_LABEL_98;
        obj = com.maddox.il2.builder.Plugin.builder.selectedPath();
        if(!(obj instanceof com.maddox.il2.builder.PathChief))
            return;
        com.maddox.il2.builder.PathChief pathchief = (com.maddox.il2.builder.PathChief)obj;
        if(i - startComboBox1 != pathchief._iType || j != pathchief._iItem)
            com.maddox.il2.builder.Plugin.builder.setSelected(null);
        if(com.maddox.il2.builder.Plugin.builder.selectedPoint() == null)
            break MISSING_BLOCK_LABEL_191;
        if(type[i - startComboBox1].moveType == 3 && ((com.maddox.il2.builder.Path) (obj)).points() >= 2)
            return;
        com.maddox.il2.builder.PNodes pnodes;
        pnodes = new PNodes(com.maddox.il2.builder.Plugin.builder.selectedPath(), com.maddox.il2.builder.Plugin.builder.selectedPoint(), point3d);
        pnodes.speed = type[i - startComboBox1].item[j].speed / 2D;
        break MISSING_BLOCK_LABEL_424;
        if(i < startComboBox1 || i >= startComboBox1 + type.length)
            return;
        i -= startComboBox1;
        if(j < 0 || j >= type[i].item.length)
            return;
        obj = new PathChief(com.maddox.il2.builder.Plugin.builder.pathes, type[i].moveType, i, j, fSectsUnits, type[i].item[j].iSectUnits, point3d);
        ((com.maddox.il2.builder.Path) (obj)).setName(makeName());
        ((com.maddox.il2.builder.Path) (obj)).setArmy(type[i].item[j].army);
        com.maddox.rts.Property.set(obj, "builderPlugin", this);
        com.maddox.rts.Property.set(obj, "i18nName", com.maddox.il2.game.I18N.technic(type[i].item[j].name));
        ((com.maddox.il2.builder.Path) (obj)).drawing(viewMap.containsKey(i));
        pnodes = new PNodes(((com.maddox.il2.builder.Path) (obj)), null, type[i].item[j].iconName, point3d);
        pnodes.speed = type[i].item[j].speed / 2D;
        com.maddox.il2.builder.Plugin.builder.setSelected(pnodes);
        com.maddox.il2.builder.PlMission.setChanged();
        break MISSING_BLOCK_LABEL_466;
        java.lang.Exception exception;
        exception;
        if(obj != null && ((com.maddox.il2.builder.Path) (obj)).points() == 0)
            ((com.maddox.il2.builder.Path) (obj)).destroy();
        java.lang.System.out.println(exception);
    }

    private void clampSpeed(com.maddox.il2.builder.PNodes pnodes)
    {
        com.maddox.il2.builder.PathChief pathchief = (com.maddox.il2.builder.PathChief)pnodes.getOwner();
        if(pnodes.speed > type[pathchief._iType].item[pathchief._iItem].speed)
            pnodes.speed = type[pathchief._iType].item[pathchief._iItem].speed;
        if(pnodes.speed < 1.0D)
            pnodes.speed = 1.0D;
    }

    private void clampSpeed(com.maddox.il2.builder.PathChief pathchief)
    {
        int i = pathchief.points();
        for(int j = 0; j < i; j++)
            clampSpeed((com.maddox.il2.builder.PNodes)pathchief.point(j));

    }

    public void changeType()
    {
        int i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected() - startComboBox1;
        int j = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
        com.maddox.il2.builder.PathChief pathchief = (com.maddox.il2.builder.PathChief)com.maddox.il2.builder.Plugin.builder.selectedPath();
        if(i != pathchief._iType)
            return;
        pathchief.setUnits(i, j, fSectsUnits, type[i].item[j].iSectUnits, pathchief.point(0).pos.getAbsPoint());
        com.maddox.rts.Property.set(pathchief, "i18nName", com.maddox.il2.game.I18N.technic(type[i].item[j].name));
        if(com.maddox.il2.builder.PlMisChief.moveType(i) == 2)
            clampSpeed(pathchief);
        pathchief.updateUnitsPos();
        com.maddox.il2.builder.PlMission.setChanged();
    }

    public void configure()
    {
        if(com.maddox.il2.builder.Plugin.getPlugin("Mission") == null)
            throw new RuntimeException("PlMisChief: plugin 'Mission' not found");
        pluginMission = (com.maddox.il2.builder.PlMission)com.maddox.il2.builder.Plugin.getPlugin("Mission");
        if(sectFile == null)
            throw new RuntimeException("PlMisChief: field 'sectFile' not defined");
        com.maddox.rts.SectFile sectfile = new SectFile(sectFile, 0);
        int i = sectfile.sections();
        if(i <= 0)
            throw new RuntimeException("PlMisChief: file '" + sectFile + "' is empty");
        int j = 0;
        for(int k = 0; k < i; k++)
        {
            java.lang.String s = sectfile.sectionName(k);
            if(s.indexOf('.') < 0)
                j++;
        }

        if(j == 0)
            throw new RuntimeException("PlMisChief: file '" + sectFile + "' is empty");
        fSectsUnits = sectfile;
        type = new com.maddox.il2.builder.Type[j];
        int l = 0;
        for(int i1 = 0; i1 < i; i1++)
        {
            java.lang.String s1 = sectfile.sectionName(i1);
            if(s1.indexOf('.') < 0)
            {
                int j1 = sectfile.vars(i1);
                if(j1 < 2)
                    throw new RuntimeException("PlMisChief: file '" + sectFile + "', section '" + s1 + "' missing moveType and/or class");
                if(sectfile.varIndex(i1, "moveType") != 0)
                    throw new RuntimeException("PlMisChief: file '" + sectFile + "', section '" + s1 + "' moveType must be first row");
                java.lang.String s2 = sectfile.value(i1, 0);
                byte byte0 = 0;
                if("VEHICLE".equals(s2))
                    byte0 = 0;
                else
                if("TROOPER".equals(s2))
                    byte0 = 1;
                else
                if("SHIP".equals(s2))
                    byte0 = 2;
                else
                if("TRAIN".equals(s2))
                    byte0 = 3;
                else
                    throw new RuntimeException("PlMisChief: file '" + sectFile + "', section '" + s1 + "' unknown moveType '" + s2 + "'");
                com.maddox.il2.builder.Item aitem[] = new com.maddox.il2.builder.Item[j1 - 1];
                for(int k1 = 1; k1 < j1; k1++)
                {
                    java.lang.String s3 = sectfile.var(i1, k1);
                    com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.value(i1, k1));
                    java.lang.String s4 = numbertokenizer.next((java.lang.String)null);
                    int l1 = numbertokenizer.next(1, 1, com.maddox.il2.builder.Builder.armyAmount() - 1);
                    java.lang.String s5 = numbertokenizer.next("icons/tank.mat");
                    aitem[k1 - 1] = new Item(byte0, s1, s3, s4, sectfile, l1, s5);
                }

                type[l++] = new Type(s1, byte0, aitem);
            }
        }

        fSectsEmpty = new SectFile();
    }

    void viewUpdate()
    {
        if(com.maddox.il2.builder.Plugin.builder.pathes == null)
            return;
        java.lang.Object aobj[] = com.maddox.il2.builder.Plugin.builder.pathes.getOwnerAttached();
        for(int i = 0; i < aobj.length; i++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)aobj[i];
            if(actor == null)
                break;
            if(actor instanceof com.maddox.il2.builder.PathChief)
            {
                com.maddox.il2.builder.PathChief pathchief = (com.maddox.il2.builder.PathChief)actor;
                pathchief.drawing(viewMap.containsKey(pathchief._iType));
            }
        }

        if(com.maddox.il2.builder.Plugin.builder.selectedPath() != null)
        {
            com.maddox.il2.builder.Path path = com.maddox.il2.builder.Plugin.builder.selectedPath();
            if(path instanceof com.maddox.il2.builder.PathChief)
            {
                com.maddox.il2.builder.PathChief pathchief1 = (com.maddox.il2.builder.PathChief)path;
                if(!viewMap.containsKey(pathchief1._iType))
                    com.maddox.il2.builder.Plugin.builder.setSelected(null);
            }
        }
        if(!com.maddox.il2.builder.Plugin.builder.isFreeView())
            com.maddox.il2.builder.Plugin.builder.repaint();
    }

    void viewType(int i, boolean flag)
    {
        if(flag)
            viewMap.put(i, null);
        else
            viewMap.remove(i);
        viewUpdate();
    }

    void viewType(int i)
    {
        viewType(i, viewType[i].bChecked);
    }

    public void viewTypeAll(boolean flag)
    {
        for(int i = 0; i < type.length; i++)
            if(viewType[i].bChecked != flag)
            {
                viewType[i].bChecked = flag;
                viewType(i, flag);
            }

    }

    private void fillComboBox1()
    {
        startComboBox1 = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.size();
        for(int i = 0; i < type.length; i++)
            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.add(com.maddox.il2.game.I18N.technic(type[i].name));

        if(startComboBox1 == 0)
            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.setSelected(0, true, false);
    }

    private void fillComboBox2(int i, int j)
    {
        if(i < startComboBox1 || i >= startComboBox1 + type.length)
            return;
        if(com.maddox.il2.builder.Plugin.builder.wSelect.curFilledType != i)
        {
            com.maddox.il2.builder.Plugin.builder.wSelect.curFilledType = i;
            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.clear(false);
            for(int k = 0; k < type[i - startComboBox1].item.length; k++)
                com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.add(com.maddox.il2.game.I18N.technic(type[i - startComboBox1].item[k].name));

            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.setSelected(i, true, false);
        }
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.setSelected(j, true, false);
        setSelectorMesh();
    }

    public void setSelectorMesh()
    {
        int i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
        if(i < startComboBox1 || i >= startComboBox1 + type.length)
            return;
        int j = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
        try
        {
            java.lang.String s = fSectsUnits.var(type[i - startComboBox1].item[j].iSectUnits, 0);
            java.lang.Class class1 = com.maddox.il2.builder.PlMisChief.ForceClassLoading(s);
            java.lang.String s1 = com.maddox.rts.Property.stringValue(class1, "meshName", null);
            if(s1 == null)
            {
                java.lang.reflect.Method method = class1.getMethod("getMeshNameForEditor", null);
                s1 = (java.lang.String)method.invoke(class1, null);
            }
            com.maddox.il2.builder.Plugin.builder.wSelect.setMesh(s1, true);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
            com.maddox.il2.builder.Plugin.builder.wSelect.setMesh(null, true);
        }
    }

    public java.lang.String[] actorInfo(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.builder.PathChief pathchief = (com.maddox.il2.builder.PathChief)actor.getOwner();
        _actorInfo[0] = com.maddox.il2.game.I18N.technic(type[pathchief._iType].name) + "." + com.maddox.il2.game.I18N.technic(type[pathchief._iType].item[pathchief._iItem].name);
        com.maddox.il2.builder.PNodes pnodes = (com.maddox.il2.builder.PNodes)actor;
        int i = pathchief.pointIndx(pnodes);
        if(pnodes.timeoutMin > 0.0D)
            _actorInfo[1] = "(" + i + ") in:" + com.maddox.il2.builder.Plugin.timeSecToString(pnodes.time + (double)(int)(com.maddox.il2.ai.World.getTimeofDay() * 60F * 60F)) + " out:" + com.maddox.il2.builder.Plugin.timeSecToString(pnodes.time + (double)(int)(com.maddox.il2.ai.World.getTimeofDay() * 60F * 60F) + pnodes.timeoutMin * 60D);
        else
            _actorInfo[1] = "(" + i + ") " + com.maddox.il2.builder.Plugin.timeSecToString(pnodes.time + (double)(int)(com.maddox.il2.ai.World.getTimeofDay() * 60F * 60F));
        return _actorInfo;
    }

    public void syncSelector()
    {
        com.maddox.il2.builder.PathChief pathchief = (com.maddox.il2.builder.PathChief)com.maddox.il2.builder.Plugin.builder.selectedPath();
        fillComboBox2(pathchief._iType + startComboBox1, pathchief._iItem);
        com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.addTab(1, tabActor);
        wName.cap.set(com.maddox.rts.Property.stringValue(pathchief, "i18nName", ""));
        wArmy.setSelected(pathchief.getArmy() - 1, true, false);
        if(com.maddox.il2.builder.PlMisChief.moveType(pathchief._iType) == 2)
        {
            wLSleepM.showWindow();
            wSleepM.showWindow();
            wLSleepS.showWindow();
            wSleepS.showWindow();
            wSleepM.setValue("" + (pathchief._sleep / 60) % 99, false);
            wSleepS.setValue("" + pathchief._sleep % 60, false);
            wLSkill.showWindow();
            wSkill.showWindow();
            wSkill.setSelected(pathchief._skill, true, false);
            wLSlowfire.showWindow();
            wSlowfire.showWindow();
            wSlowfire.setValue("" + pathchief._slowfire);
        } else
        {
            wLSleepM.hideWindow();
            wSleepM.hideWindow();
            wLSleepS.hideWindow();
            wSleepS.hideWindow();
            wLSkill.hideWindow();
            wSkill.hideWindow();
            wLSlowfire.hideWindow();
            wSlowfire.hideWindow();
        }
        if(com.maddox.il2.builder.PlMisChief.moveType(pathchief._iType) != 3)
        {
            com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.addTab(2, tabWay);
            fillDialogWay();
        }
    }

    public void updateSelector()
    {
        fillDialogWay();
    }

    private void controlResized(com.maddox.gwindow.GWindowDialogClient gwindowdialogclient, com.maddox.gwindow.GWindow gwindow)
    {
        if(gwindow == null)
        {
            return;
        } else
        {
            gwindow.setSize(gwindowdialogclient.win.dx - gwindow.win.x - gwindowdialogclient.lAF().metric(1.0F), gwindow.win.dy);
            return;
        }
    }

    private void editResized(com.maddox.gwindow.GWindowDialogClient gwindowdialogclient)
    {
        controlResized(gwindowdialogclient, wName);
        controlResized(gwindowdialogclient, wArmy);
    }

    public void createGUI()
    {
        fillComboBox1();
        fillComboBox2(0, 0);
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.addNotifyListener(new com.maddox.gwindow.GNotifyListener() {

            public boolean notify(com.maddox.gwindow.GWindow gwindow, int k, int l)
            {
                int i1 = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
                if(i1 >= 0 && k == 2)
                    fillComboBox2(i1, 0);
                return false;
            }

        }
);
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.addNotifyListener(new com.maddox.gwindow.GNotifyListener() {

            public boolean notify(com.maddox.gwindow.GWindow gwindow, int k, int l)
            {
                if(k != 2)
                {
                    return false;
                } else
                {
                    setSelectorMesh();
                    return false;
                }
            }

        }
);
        int i;
        for(i = com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.size() - 1; i >= 0; i--)
            if(pluginMission.viewBridge == com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.getItem(i))
                break;

        if(--i >= 0)
        {
            int j = i;
            i = type.length - 1;
            viewType = new com.maddox.il2.builder.ViewItem[type.length];
            for(; i >= 0; i--)
            {
                com.maddox.il2.builder.ViewItem viewitem = null;
                if("de".equals(com.maddox.rts.RTSConf.cur.locale.getLanguage()))
                    viewitem = (com.maddox.il2.builder.ViewItem)com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(j, new ViewItem(i, com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, com.maddox.il2.game.I18N.technic(type[i].name) + " " + com.maddox.il2.builder.Plugin.i18n("show"), null));
                else
                    viewitem = (com.maddox.il2.builder.ViewItem)com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(j, new ViewItem(i, com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, com.maddox.il2.builder.Plugin.i18n("show") + " " + com.maddox.il2.game.I18N.technic(type[i].name), null));
                viewitem.bChecked = true;
                viewType[i] = viewitem;
                viewType(i, true);
            }

        }
        com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.create(new com.maddox.gwindow.GWindowDialogClient() {

            public void resized()
            {
                super.resized();
                editResized(this);
            }

        }
);
        tabActor = com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.createTab(com.maddox.il2.builder.Plugin.i18n("ChiefActor"), gwindowdialogclient);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 1.0F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Name"), null));
        gwindowdialogclient.addLabel(wName = new GWindowLabel(gwindowdialogclient, 9F, 1.0F, 7F, 1.3F, "", null));
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 3F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Army"), null));
        gwindowdialogclient.addControl(wArmy = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 9F, 3F, 7F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                for(int k = 1; k < com.maddox.il2.builder.Builder.armyAmount(); k++)
                    add(com.maddox.il2.game.I18N.army(com.maddox.il2.ai.Army.name(k)));

            }

            public boolean notify(int k, int l)
            {
                if(k != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.Path path = com.maddox.il2.builder.Plugin.builder.selectedPath();
                    int i1 = getSelected() + 1;
                    path.setArmy(i1);
                    com.maddox.il2.builder.PlMission.setChanged();
                    com.maddox.il2.builder.PlMission.checkShowCurrentArmy();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(wLSleepM = new GWindowLabel(gwindowdialogclient, 1.0F, 5F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Sleep"), null));
        gwindowdialogclient.addControl(wSleepM = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 9F, 5F, 2.0F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public boolean notify(int k, int l)
            {
                if(k != 2)
                {
                    return false;
                } else
                {
                    getSleep();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(wLSleepS = new GWindowLabel(gwindowdialogclient, 11.2F, 5F, 1.0F, 1.3F, ":", null));
        gwindowdialogclient.addControl(wSleepS = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 11.5F, 5F, 2.0F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public boolean notify(int k, int l)
            {
                if(k != 2)
                {
                    return false;
                } else
                {
                    getSleep();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(wLSkill = new GWindowLabel(gwindowdialogclient, 1.0F, 7F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Skill"), null));
        gwindowdialogclient.addControl(wSkill = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 9F, 7F, 7F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                add(com.maddox.il2.builder.Plugin.i18n("Rookie"));
                add(com.maddox.il2.builder.Plugin.i18n("Average"));
                add(com.maddox.il2.builder.Plugin.i18n("Veteran"));
                add(com.maddox.il2.builder.Plugin.i18n("Ace"));
            }

            public boolean notify(int k, int l)
            {
                if(k != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.PathChief pathchief = (com.maddox.il2.builder.PathChief)com.maddox.il2.builder.Plugin.builder.selectedPath();
                    pathchief._skill = getSelected();
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(wLSlowfire = new GWindowLabel(gwindowdialogclient, 1.0F, 9F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Slowfire"), null));
        gwindowdialogclient.addControl(wSlowfire = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 9F, 9F, 3F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = bNumericFloat = true;
                bDelayedNotify = true;
            }

            public boolean notify(int k, int l)
            {
                if(k != 2)
                    return false;
                java.lang.String s = getValue();
                float f = 1.0F;
                try
                {
                    f = java.lang.Float.parseFloat(s);
                }
                catch(java.lang.Exception exception) { }
                if(f < 0.5F)
                    f = 0.5F;
                if(f > 100F)
                    f = 100F;
                setValue("" + f, false);
                com.maddox.il2.builder.PathChief pathchief = (com.maddox.il2.builder.PathChief)com.maddox.il2.builder.Plugin.builder.selectedPath();
                pathchief._slowfire = f;
                com.maddox.il2.builder.PlMission.setChanged();
                return false;
            }

        }
);
        initEditWay();
    }

    private void getSleep()
    {
        com.maddox.il2.builder.PathChief pathchief = (com.maddox.il2.builder.PathChief)com.maddox.il2.builder.Plugin.builder.selectedPath();
        java.lang.String s = wSleepM.getValue();
        double d = 0.0D;
        try
        {
            d = java.lang.Double.parseDouble(s);
        }
        catch(java.lang.Exception exception) { }
        if(d < 0.0D)
            d = 0.0D;
        if(d > 99D)
            d = 99D;
        s = wSleepS.getValue();
        double d1 = 0.0D;
        try
        {
            d1 = java.lang.Double.parseDouble(s);
        }
        catch(java.lang.Exception exception1) { }
        if(d1 < 0.0D)
            d1 = 0.0D;
        if(d1 > 60D)
            d1 = 60D;
        pathchief._sleep = (int)(d * 60D + d1);
        com.maddox.il2.builder.PlMission.setChanged();
    }

    private void fillDialogWay()
    {
        com.maddox.il2.builder.PNodes pnodes = (com.maddox.il2.builder.PNodes)com.maddox.il2.builder.Plugin.builder.selectedPoint();
        com.maddox.il2.builder.PathChief pathchief = (com.maddox.il2.builder.PathChief)com.maddox.il2.builder.Plugin.builder.selectedPath();
        int i = pathchief.pointIndx(pnodes);
        wCur.cap = new GCaption("" + i + "(" + pathchief.points() + ")");
        wTime.cap = new GCaption(com.maddox.il2.builder.Plugin.timeSecToString(pnodes.time + (double)(com.maddox.il2.ai.World.getTimeofDay() * 60F * 60F)));
        wPrev.setEnable(i > 0);
        if(i < pathchief.points() - 1)
        {
            wNext.setEnable(true);
            wTimeOutH.setEnable(true);
            wTimeOutM.setEnable(true);
            wTimeOutH.setValue("" + (int)((pnodes.timeoutMin / 60D) % 24D), false);
            wTimeOutM.setValue("" + (int)(pnodes.timeoutMin % 60D), false);
        } else
        {
            pnodes.timeoutMin = 0.0D;
            wNext.setEnable(false);
            wTimeOutH.setEnable(false);
            wTimeOutM.setEnable(false);
            wTimeOutH.setValue("0", false);
            wTimeOutM.setValue("0", false);
        }
        if(com.maddox.il2.builder.PlMisChief.moveType(pathchief._iType) != 2)
        {
            wLSpeed0.hideWindow();
            wLSpeed1.hideWindow();
            wSpeed.hideWindow();
        } else
        {
            wLSpeed0.showWindow();
            wLSpeed1.showWindow();
            wSpeed.showWindow();
            wSpeed.setValue("" + (int)java.lang.Math.round(pnodes.speed * 3.6000000000000001D), false);
        }
    }

    public void initEditWay()
    {
        com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.create(new GWindowDialogClient());
        tabWay = com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.createTab(com.maddox.il2.builder.Plugin.i18n("Waypoint"), gwindowdialogclient);
        gwindowdialogclient.addControl(wPrev = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 1.0F, 1.0F, 5F, 1.6F, com.maddox.il2.builder.Plugin.i18n("&Prev"), null) {

            public boolean notify(int i, int j)
            {
                if(i == 2)
                {
                    com.maddox.il2.builder.PNodes pnodes = (com.maddox.il2.builder.PNodes)com.maddox.il2.builder.Plugin.builder.selectedPoint();
                    com.maddox.il2.builder.PathChief pathchief = (com.maddox.il2.builder.PathChief)com.maddox.il2.builder.Plugin.builder.selectedPath();
                    int k = pathchief.pointIndx(pnodes);
                    if(k > 0)
                    {
                        com.maddox.il2.builder.Plugin.builder.setSelected(pathchief.point(k - 1));
                        fillDialogWay();
                        com.maddox.il2.builder.Plugin.builder.repaint();
                    }
                    return true;
                } else
                {
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addControl(wNext = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 9F, 1.0F, 5F, 1.6F, com.maddox.il2.builder.Plugin.i18n("&Next"), null) {

            public boolean notify(int i, int j)
            {
                if(i == 2)
                {
                    com.maddox.il2.builder.PNodes pnodes = (com.maddox.il2.builder.PNodes)com.maddox.il2.builder.Plugin.builder.selectedPoint();
                    com.maddox.il2.builder.PathChief pathchief = (com.maddox.il2.builder.PathChief)com.maddox.il2.builder.Plugin.builder.selectedPath();
                    int k = pathchief.pointIndx(pnodes);
                    if(k < pathchief.points() - 1)
                    {
                        com.maddox.il2.builder.Plugin.builder.setSelected(pathchief.point(k + 1));
                        fillDialogWay();
                        com.maddox.il2.builder.Plugin.builder.repaint();
                    }
                    return true;
                } else
                {
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(wCur = new GWindowLabel(gwindowdialogclient, 15F, 1.0F, 4F, 1.6F, "1(1)", null));
        gwindowdialogclient.addLabel(wLTime = new GWindowLabel(gwindowdialogclient, 1.0F, 3F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Time"), null));
        gwindowdialogclient.addLabel(wTime = new GWindowLabel(gwindowdialogclient, 9F, 3F, 6F, 1.3F, "0:00", null));
        gwindowdialogclient.addLabel(wLTimeOutH = new GWindowLabel(gwindowdialogclient, 1.0F, 5F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("TimeOut"), null));
        gwindowdialogclient.addControl(wTimeOutH = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 9F, 5F, 2.0F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public boolean notify(int i, int j)
            {
                if(i != 2)
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
        gwindowdialogclient.addLabel(wLTimeOutM = new GWindowLabel(gwindowdialogclient, 11.2F, 5F, 1.0F, 1.3F, ":", null));
        gwindowdialogclient.addControl(wTimeOutM = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 11.5F, 5F, 2.0F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public boolean notify(int i, int j)
            {
                if(i != 2)
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
        gwindowdialogclient.addLabel(wLSpeed0 = new GWindowLabel(gwindowdialogclient, 1.0F, 7F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Speed"), null));
        gwindowdialogclient.addLabel(wLSpeed1 = new GWindowLabel(gwindowdialogclient, 15F, 7F, 4F, 1.3F, com.maddox.il2.builder.Plugin.i18n("[kM/H]"), null));
        gwindowdialogclient.addControl(wSpeed = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 9F, 7F, 5F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public boolean notify(int i, int j)
            {
                if(i != 2)
                    return false;
                com.maddox.il2.builder.PNodes pnodes = (com.maddox.il2.builder.PNodes)com.maddox.il2.builder.Plugin.builder.selectedPoint();
                java.lang.String s = getValue();
                double d = 0.0D;
                try
                {
                    d = java.lang.Double.parseDouble(s) / 3.6000000000000001D;
                }
                catch(java.lang.Exception exception)
                {
                    setValue("" + (int)java.lang.Math.round(((com.maddox.il2.builder.PNodes)com.maddox.il2.builder.Plugin.builder.selectedPoint()).speed * 3.6000000000000001D), false);
                    return false;
                }
                com.maddox.il2.builder.PathChief pathchief = (com.maddox.il2.builder.PathChief)com.maddox.il2.builder.Plugin.builder.selectedPath();
                if(d > type[pathchief._iType].item[pathchief._iItem].speed)
                    d = type[pathchief._iType].item[pathchief._iItem].speed;
                pnodes.speed = d;
                pathchief.computeTimes();
                com.maddox.il2.builder.PlMission.setChanged();
                return false;
            }

        }
);
    }

    private void getTimeOut()
    {
        com.maddox.il2.builder.PNodes pnodes = (com.maddox.il2.builder.PNodes)com.maddox.il2.builder.Plugin.builder.selectedPoint();
        com.maddox.il2.builder.PathChief pathchief = (com.maddox.il2.builder.PathChief)com.maddox.il2.builder.Plugin.builder.selectedPath();
        java.lang.String s = wTimeOutH.getValue();
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
        s = wTimeOutM.getValue();
        double d1 = 0.0D;
        try
        {
            d1 = java.lang.Double.parseDouble(s);
        }
        catch(java.lang.Exception exception1) { }
        if(d1 < 0.0D)
            d1 = 0.0D;
        if(d1 > 60D)
            d1 = 60D;
        pnodes.timeoutMin = d * 60D + d1;
        pathchief.computeTimes();
        com.maddox.il2.builder.PlMission.setChanged();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    com.maddox.rts.SectFile fSectsUnits;
    com.maddox.rts.SectFile fSectsEmpty;
    com.maddox.il2.builder.Type type[];
    private com.maddox.JGP.Point3d p;
    private int roadOffset[];
    private int timeMinAbs[];
    private double roadSpeed;
    private com.maddox.il2.builder.PlMission pluginMission;
    private int startComboBox1;
    com.maddox.il2.builder.ViewItem viewType[];
    com.maddox.util.HashMapInt viewMap;
    private java.lang.String _actorInfo[];
    com.maddox.gwindow.GWindowTabDialogClient.Tab tabActor;
    com.maddox.gwindow.GWindowTabDialogClient.Tab tabWay;
    com.maddox.gwindow.GWindowLabel wName;
    com.maddox.gwindow.GWindowComboControl wArmy;
    com.maddox.gwindow.GWindowLabel wLSleepM;
    com.maddox.gwindow.GWindowEditControl wSleepM;
    com.maddox.gwindow.GWindowLabel wLSleepS;
    com.maddox.gwindow.GWindowEditControl wSleepS;
    com.maddox.gwindow.GWindowLabel wLSkill;
    com.maddox.gwindow.GWindowComboControl wSkill;
    com.maddox.gwindow.GWindowLabel wLSlowfire;
    com.maddox.gwindow.GWindowEditControl wSlowfire;
    com.maddox.gwindow.GWindowButton wPrev;
    com.maddox.gwindow.GWindowButton wNext;
    com.maddox.gwindow.GWindowLabel wCur;
    com.maddox.gwindow.GWindowLabel wLTime;
    com.maddox.gwindow.GWindowLabel wTime;
    com.maddox.gwindow.GWindowLabel wLTimeOutH;
    com.maddox.gwindow.GWindowEditControl wTimeOutH;
    com.maddox.gwindow.GWindowLabel wLTimeOutM;
    com.maddox.gwindow.GWindowEditControl wTimeOutM;
    com.maddox.gwindow.GWindowLabel wLSpeed0;
    com.maddox.gwindow.GWindowLabel wLSpeed1;
    com.maddox.gwindow.GWindowEditControl wSpeed;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.builder.PlMisChief.class, "name", "MisChief");
    }





}

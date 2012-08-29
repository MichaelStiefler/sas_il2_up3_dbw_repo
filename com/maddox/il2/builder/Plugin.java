// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Plugin.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GWindowMenuPopUp;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.game.I18N;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import java.util.ArrayList;
import java.util.HashMap;

// Referenced classes of package com.maddox.il2.builder:
//            Builder

public abstract class Plugin
{

    public Plugin()
    {
    }

    public final java.lang.String name()
    {
        return com.maddox.rts.Property.stringValue(getClass(), "name", null);
    }

    public static java.lang.String i18n(java.lang.String s)
    {
        return com.maddox.il2.game.I18N.bld(s);
    }

    public void render3D()
    {
    }

    public void preRenderMap2D()
    {
    }

    public void renderMap2DBefore()
    {
    }

    public void renderMap2D()
    {
    }

    public void renderMap2DAfter()
    {
    }

    public void insert(com.maddox.il2.engine.Loc loc, boolean flag)
    {
    }

    public void beginFill(com.maddox.JGP.Point3d point3d)
    {
    }

    public void fill(com.maddox.JGP.Point3d point3d)
    {
    }

    public void endFill(com.maddox.JGP.Point3d point3d)
    {
    }

    public void delete(com.maddox.il2.engine.Loc loc)
    {
    }

    public void deleteAll()
    {
    }

    public void delete(com.maddox.il2.engine.Actor actor)
    {
        actor.destroy();
    }

    public void afterDelete()
    {
    }

    public void selectAll()
    {
    }

    public void changeType(boolean flag, boolean flag1)
    {
    }

    public void changeType()
    {
    }

    public void fillPopUpMenu(com.maddox.gwindow.GWindowMenuPopUp gwindowmenupopup, com.maddox.JGP.Point3d point3d)
    {
    }

    public void syncSelector()
    {
    }

    public void updateSelector()
    {
    }

    public void updateSelectorMesh()
    {
    }

    public java.lang.String[] actorInfo(com.maddox.il2.engine.Actor actor)
    {
        return null;
    }

    public static boolean isValidActorName(java.lang.String s)
    {
        if(s == null)
            return false;
        if(s.length() == 0)
            return false;
        for(int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if(java.lang.Character.isWhitespace(c) || java.lang.Character.isISOControl(c))
                return false;
        }

        return true;
    }

    public void viewTypeAll(boolean flag)
    {
    }

    public void load(com.maddox.rts.SectFile sectfile)
    {
    }

    public boolean save(com.maddox.rts.SectFile sectfile)
    {
        return true;
    }

    public void mapLoaded()
    {
    }

    public boolean exitBuilder()
    {
        return true;
    }

    public void freeResources()
    {
    }

    public void configure()
    {
    }

    public void createGUI()
    {
    }

    public void start()
    {
    }

    protected static com.maddox.il2.builder.Plugin getPlugin(java.lang.String s)
    {
        return (com.maddox.il2.builder.Plugin)mapNames.get(s);
    }

    protected static void loadAll(com.maddox.rts.SectFile sectfile, java.lang.String s, com.maddox.il2.builder.Builder builder1)
    {
        builder = builder1;
        int i = sectfile.sectionIndex(s);
        if(i < 0)
            return;
        int j = sectfile.vars(i);
        if(j <= 0)
            return;
        for(int k = 0; k < j; k++)
        {
            java.lang.Object obj = com.maddox.rts.ObjIO.fromString(sectfile.line(i, k));
            if(obj != null && (obj instanceof com.maddox.il2.builder.Plugin))
            {
                com.maddox.il2.builder.Plugin plugin = (com.maddox.il2.builder.Plugin)obj;
                java.lang.String s1 = plugin.name();
                if(s1 != null && !mapNames.containsKey(s1))
                {
                    all.add(obj);
                    mapNames.put(s1, plugin);
                }
            }
        }

        int l = all.size();
        for(int i1 = 0; i1 < l; i1++)
            ((com.maddox.il2.builder.Plugin)all.get(i1)).configure();

    }

    public static java.lang.String timeSecToString(double d)
    {
        int i = (int)java.lang.Math.round(d / 60D);
        int j = i % 60;
        if(j < 10)
            return "" + (i / 60) % 24 + ":0" + j;
        else
            return "" + (i / 60) % 24 + ":" + j;
    }

    protected static void doRender3D()
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.builder.Plugin)all.get(j)).render3D();

    }

    protected static void doPreRenderMap2D()
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.builder.Plugin)all.get(j)).preRenderMap2D();

    }

    protected static void doRenderMap2DBefore()
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.builder.Plugin)all.get(j)).renderMap2DBefore();

    }

    protected static void doRenderMap2D()
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.builder.Plugin)all.get(j)).renderMap2D();

    }

    protected static void doRenderMap2DAfter()
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.builder.Plugin)all.get(j)).renderMap2DAfter();

    }

    protected static void doCreateGUI()
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.builder.Plugin)all.get(j)).createGUI();

    }

    protected static void doStart()
    {
        selectIcon = com.maddox.il2.engine.IconDraw.get("icons/SelectIcon.mat");
        targetIcon = com.maddox.il2.engine.IconDraw.get("icons/target.mat");
        playerIcon = com.maddox.il2.engine.IconDraw.get("icons/player.mat");
        int i = all.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.builder.Plugin)all.get(j)).start();

    }

    protected static void doInsert(com.maddox.il2.engine.Loc loc, boolean flag)
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.builder.Plugin)all.get(j)).insert(loc, flag);

    }

    protected static void doFillPopUpMenu(com.maddox.gwindow.GWindowMenuPopUp gwindowmenupopup, com.maddox.JGP.Point3d point3d)
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.builder.Plugin)all.get(j)).fillPopUpMenu(gwindowmenupopup, point3d);

    }

    protected static void doBeginFill(com.maddox.JGP.Point3d point3d)
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.builder.Plugin)all.get(j)).beginFill(point3d);

    }

    protected static void doFill(com.maddox.JGP.Point3d point3d)
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.builder.Plugin)all.get(j)).fill(point3d);

    }

    protected static void doEndFill(com.maddox.JGP.Point3d point3d)
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.builder.Plugin)all.get(j)).endFill(point3d);

    }

    protected static void doDelete(com.maddox.il2.engine.Loc loc)
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.builder.Plugin)all.get(j)).delete(loc);

    }

    protected static void doDeleteAll()
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.builder.Plugin)all.get(j)).deleteAll();

    }

    protected static void doAfterDelete()
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.builder.Plugin)all.get(j)).afterDelete();

    }

    protected static void doSelectAll()
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.builder.Plugin)all.get(j)).selectAll();

    }

    protected static void doChangeType(boolean flag, boolean flag1)
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.builder.Plugin)all.get(j)).changeType(flag, flag1);

    }

    protected static void doViewTypeAll(boolean flag)
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.builder.Plugin)all.get(j)).viewTypeAll(flag);

    }

    protected static void doLoad(com.maddox.rts.SectFile sectfile)
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.builder.Plugin)all.get(j)).load(sectfile);

    }

    protected static boolean doSave(com.maddox.rts.SectFile sectfile)
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
            if(!((com.maddox.il2.builder.Plugin)all.get(j)).save(sectfile))
                return false;

        return true;
    }

    protected static void doMapLoaded()
    {
        builder.mapLoaded();
        int i = all.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.builder.Plugin)all.get(j)).mapLoaded();

    }

    protected static void doFreeResources()
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.builder.Plugin)all.get(j)).freeResources();

    }

    protected static boolean doExitBuilder()
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
            if(!((com.maddox.il2.builder.Plugin)all.get(j)).exitBuilder())
                return false;

        return true;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public static com.maddox.il2.builder.Builder builder;
    public static final java.lang.String SELECT_ICON = "icons/SelectIcon.mat";
    public static final java.lang.String TARGET_ICON = "icons/target.mat";
    public static final java.lang.String PLAYER_ICON = "icons/player.mat";
    public static com.maddox.il2.engine.Mat selectIcon;
    public static com.maddox.il2.engine.Mat targetIcon;
    public static com.maddox.il2.engine.Mat playerIcon;
    public java.lang.String sectFile;
    private static java.util.ArrayList all = new ArrayList();
    private static java.util.HashMap mapNames = new HashMap();

    static 
    {
        com.maddox.rts.ObjIO.field(com.maddox.il2.builder.Plugin.class, "sectFile");
    }
}

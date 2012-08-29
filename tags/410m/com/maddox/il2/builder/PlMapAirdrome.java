// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PlMapAirdrome.java

package com.maddox.il2.builder;

import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Loc;
import com.maddox.rts.Property;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, PathAirdrome, PAirdrome, Path, 
//            PlMapActors, Builder, Pathes, WSelect, 
//            PPoint

public class PlMapAirdrome extends com.maddox.il2.builder.Plugin
{
    public static class Type
    {

        public java.lang.String name;

        public Type(java.lang.String s)
        {
            name = s;
        }
    }


    public PlMapAirdrome()
    {
    }

    public void deleteAll()
    {
        if(builder.pathes == null)
            return;
        java.lang.Object aobj[] = builder.pathes.getOwnerAttached();
        for(int i = 0; i < aobj.length; i++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)aobj[i];
            if(actor == null)
                break;
            if(actor instanceof com.maddox.il2.builder.PathAirdrome)
            {
                com.maddox.il2.builder.PathAirdrome pathairdrome = (com.maddox.il2.builder.PathAirdrome)actor;
                pathairdrome.destroy();
            }
        }

    }

    public void delete(com.maddox.il2.engine.Actor actor)
    {
        if(actor instanceof com.maddox.il2.builder.PAirdrome)
        {
            if(builder.selectedPoint() == actor)
                builder.pathes.currentPPoint = null;
            com.maddox.il2.builder.PAirdrome pairdrome = (com.maddox.il2.builder.PAirdrome)actor;
            com.maddox.il2.builder.PathAirdrome pathairdrome = (com.maddox.il2.builder.PathAirdrome)pairdrome.getOwner();
            if(com.maddox.il2.engine.Actor.isValid(pathairdrome) && pathairdrome.points() == 1)
            {
                pathairdrome.destroy();
                return;
            }
            pairdrome.destroy();
        }
    }

    public void selectAll()
    {
        if(builder.pathes == null)
            return;
        java.lang.Object aobj[] = builder.pathes.getOwnerAttached();
        for(int i = 0; i < aobj.length; i++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)aobj[i];
            if(actor == null)
                break;
            if(!(actor instanceof com.maddox.il2.builder.PathAirdrome))
                continue;
            int j = ((com.maddox.il2.builder.Path)actor).points();
            for(int k = 0; k < j; k++)
                builder.selectActorsAdd(((com.maddox.il2.builder.Path)actor).point(k));

        }

    }

    public void insert(com.maddox.il2.engine.Loc loc, boolean flag)
    {
        int j;
        com.maddox.il2.builder.PathAirdrome pathairdrome;
        int i = builder.wSelect.comboBox1.getSelected();
        j = builder.wSelect.comboBox2.getSelected();
        if(startComboBox1 != i)
            return;
        pathairdrome = null;
        com.maddox.JGP.Point3d point3d;
        com.maddox.il2.builder.Path path;
        point3d = loc.getPoint();
        if(builder.selectedPath() == null)
            break MISSING_BLOCK_LABEL_95;
        path = builder.selectedPath();
        if(!(path instanceof com.maddox.il2.builder.PathAirdrome))
            return;
        pathairdrome = (com.maddox.il2.builder.PathAirdrome)path;
        if(j != pathairdrome._iType)
            builder.setSelected(null);
        com.maddox.il2.builder.PAirdrome pairdrome;
        if(builder.selectedPoint() != null)
        {
            com.maddox.il2.builder.PAirdrome pairdrome1 = (com.maddox.il2.builder.PAirdrome)builder.selectedPoint();
            pairdrome = new PAirdrome(builder.selectedPath(), builder.selectedPoint(), point3d, pairdrome1.type());
            break MISSING_BLOCK_LABEL_215;
        }
        if(j < 0 || j >= type.length)
            return;
        pathairdrome = new PathAirdrome(builder.pathes, j);
        com.maddox.rts.Property.set(pathairdrome, "builderPlugin", this);
        pathairdrome.drawing(mView.bChecked);
        pairdrome = new PAirdrome(pathairdrome, null, point3d, j);
        com.maddox.rts.Property.set(pairdrome, "builderPlugin", this);
        com.maddox.rts.Property.set(pairdrome, "builderSpawn", "");
        pairdrome.drawing(pairdrome.getOwner().isDrawing());
        if(flag)
            builder.setSelected(pairdrome);
        break MISSING_BLOCK_LABEL_288;
        java.lang.Exception exception;
        exception;
        if(pathairdrome != null && pathairdrome.points() == 0)
            pathairdrome.destroy();
        java.lang.System.out.println(exception);
    }

    public void configure()
    {
        if(com.maddox.il2.builder.PlMapAirdrome.getPlugin("MapActors") == null)
        {
            throw new RuntimeException("PlMapAirdrome: plugin 'MapActors' not found");
        } else
        {
            pluginActors = (com.maddox.il2.builder.PlMapActors)com.maddox.il2.builder.PlMapAirdrome.getPlugin("MapActors");
            com.maddox.il2.builder.PathAirdrome.configure();
            type = (new com.maddox.il2.builder.Type[] {
                new Type(com.maddox.il2.builder.PAirdrome.types[0]), new Type(com.maddox.il2.builder.PAirdrome.types[1]), new Type(com.maddox.il2.builder.PAirdrome.types[2])
            });
            return;
        }
    }

    private void viewUpdate()
    {
        if(builder.pathes == null)
            return;
        java.lang.Object aobj[] = builder.pathes.getOwnerAttached();
        for(int i = 0; i < aobj.length; i++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)aobj[i];
            if(actor == null)
                break;
            if(!(actor instanceof com.maddox.il2.builder.PathAirdrome))
                continue;
            actor.drawing(mView.bChecked);
            int j = ((com.maddox.il2.builder.Path)actor).points();
            for(int k = 0; k < j; k++)
                ((com.maddox.il2.builder.Path)actor).point(k).drawing(mView.bChecked);

        }

        if(!mView.bChecked && builder.selectedPath() != null && (builder.selectedPath() instanceof com.maddox.il2.builder.PathAirdrome))
            builder.setSelected(null);
        if(!builder.isFreeView())
            builder.repaint();
    }

    public void viewTypeAll(boolean flag)
    {
        mView.bChecked = flag;
        viewUpdate();
    }

    public void syncSelector()
    {
        com.maddox.il2.builder.PathAirdrome pathairdrome = (com.maddox.il2.builder.PathAirdrome)builder.selectedPath();
        fillComboBox2(startComboBox1, pathairdrome._iType);
    }

    private void fillComboBox1()
    {
        startComboBox1 = builder.wSelect.comboBox1.size();
        builder.wSelect.comboBox1.add("Airdrome Points");
        if(startComboBox1 == 0)
            builder.wSelect.comboBox1.setSelected(0, true, false);
    }

    private void fillComboBox2(int i, int j)
    {
        if(i != startComboBox1)
            return;
        if(builder.wSelect.curFilledType != i)
        {
            builder.wSelect.curFilledType = i;
            builder.wSelect.comboBox2.clear(false);
            for(int k = 0; k < type.length; k++)
                builder.wSelect.comboBox2.add(type[k].name);

            builder.wSelect.comboBox1.setSelected(i, true, false);
        }
        builder.wSelect.comboBox2.setSelected(j, true, false);
    }

    public void createGUI()
    {
        fillComboBox1();
        fillComboBox2(0, 0);
        builder.wSelect.comboBox1.addNotifyListener(new com.maddox.gwindow.GNotifyListener() {

            public boolean notify(com.maddox.gwindow.GWindow gwindow, int k, int l)
            {
                int i1 = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
                if(i1 == startComboBox1 && k == 2)
                    fillComboBox2(i1, 0);
                return false;
            }

        }
);
        int i;
        for(i = builder.mDisplayFilter.subMenu.size() - 1; i >= 0 && pluginActors.viewBridge != builder.mDisplayFilter.subMenu.getItem(i); i--);
        if(--i >= 0)
        {
            int j = i;
            mView = builder.mDisplayFilter.subMenu.addItem(j, new com.maddox.gwindow.GWindowMenuItem(builder.mDisplayFilter.subMenu, "show Airdrome Points", null) {

                public void execute()
                {
                    bChecked = !bChecked;
                    viewUpdate();
                }

            }
);
            mView.bChecked = true;
            viewUpdate();
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    protected com.maddox.il2.builder.Type type[];
    private com.maddox.il2.builder.PlMapActors pluginActors;
    private int startComboBox1;
    public com.maddox.gwindow.GWindowMenuItem mView;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.builder.PlMapAirdrome.class, "name", "MapAirdrome");
    }



}

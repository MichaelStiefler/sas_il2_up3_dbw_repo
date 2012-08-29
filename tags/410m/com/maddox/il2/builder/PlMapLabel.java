// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PlMapLabel.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Render;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.Property;
import com.maddox.rts.SFSReader;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, ActorLabel, ActorText, PlMapActors, 
//            PlMapLoad, Builder, WSelect

public class PlMapLabel extends com.maddox.il2.builder.Plugin
{

    public PlMapLabel()
    {
        allActors = new ArrayList();
        p2d = new Point2d();
    }

    private java.lang.String staticFileName()
    {
        return "maps/" + com.maddox.il2.builder.PlMapLoad.mapDirName() + "/labels.txt";
    }

    public boolean save(com.maddox.rts.SectFile sectfile)
    {
        java.lang.String s;
        s = staticFileName();
        if(s == null)
            return true;
        java.io.PrintWriter printwriter = new PrintWriter(new BufferedWriter(new FileWriter(s)));
        int i = allActors.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.builder.ActorLabel actorlabel = (com.maddox.il2.builder.ActorLabel)allActors.get(j);
            printwriter.println("" + (int)actorlabel.pos.getAbsPoint().x + " " + (int)actorlabel.pos.getAbsPoint().y);
        }

        printwriter.close();
        return true;
        java.lang.Exception exception;
        exception;
        java.lang.System.out.println("MapLabels save failed: " + exception.getMessage());
        return false;
    }

    private void load()
    {
        java.lang.String s = staticFileName();
        if(s == null)
            return;
        try
        {
            com.maddox.JGP.Point3d point3d = new Point3d();
            java.io.BufferedReader bufferedreader = new BufferedReader(new SFSReader(s));
            do
            {
                java.lang.String s1 = bufferedreader.readLine();
                if(s1 == null)
                    break;
                com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(s1);
                point3d.x = numbertokenizer.next(0);
                point3d.y = numbertokenizer.next(0);
                point3d.z = 0.0D;
                com.maddox.il2.builder.ActorLabel actorlabel = new ActorLabel(point3d);
                insert(actorlabel, false);
            } while(true);
            bufferedreader.close();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("MapLabels load failed: " + exception.getMessage());
        }
    }

    public void mapLoaded()
    {
        deleteAll();
        if(!builder.isLoadedLandscape())
        {
            return;
        } else
        {
            load();
            return;
        }
    }

    private void _deleteAll()
    {
        int i = allActors.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.builder.ActorText actortext = (com.maddox.il2.builder.ActorText)allActors.get(j);
            actortext.destroy();
        }

        allActors.clear();
    }

    public void deleteAll()
    {
    }

    public void delete(com.maddox.il2.engine.Actor actor)
    {
        allActors.remove(actor);
        actor.destroy();
    }

    public void selectAll()
    {
    }

    public static void insert(com.maddox.JGP.Point3d point3d)
    {
        com.maddox.il2.builder.PlMapLabel plmaplabel = (com.maddox.il2.builder.PlMapLabel)com.maddox.il2.builder.PlMapLabel.getPlugin("MapLabel");
        plmaplabel._insert(point3d);
    }

    public void _insert(com.maddox.JGP.Point3d point3d)
    {
        com.maddox.il2.builder.ActorLabel actorlabel = new ActorLabel(point3d);
        insert(actorlabel, false);
    }

    public void insert(com.maddox.il2.engine.Loc loc, boolean flag)
    {
        int i = builder.wSelect.comboBox1.getSelected();
        if(i != startComboBox1)
        {
            return;
        } else
        {
            com.maddox.il2.builder.ActorLabel actorlabel = new ActorLabel(loc.getPoint());
            insert(actorlabel, flag);
            return;
        }
    }

    private void insert(com.maddox.il2.builder.ActorLabel actorlabel, boolean flag)
    {
        builder.align(actorlabel);
        com.maddox.rts.Property.set(actorlabel, "builderSpawn", "");
        com.maddox.rts.Property.set(actorlabel, "builderPlugin", this);
        allActors.add(actorlabel);
        if(flag)
            builder.setSelected(actorlabel);
    }

    public void renderMap2D()
    {
        if(builder.isFreeView())
            return;
        com.maddox.il2.engine.Render.prepareStates();
        com.maddox.il2.engine.IconDraw.setColor(255, 255, 255, 255);
        for(int i = 0; i < allActors.size(); i++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)allActors.get(i);
            if(!builder.project2d(actor.pos.getAbsPoint(), p2d))
                continue;
            if(builder.selectedActor() == actor)
            {
                com.maddox.il2.engine.IconDraw.setColor(255, 255, 0, 255);
                com.maddox.il2.engine.IconDraw.render(actor, p2d.x, p2d.y);
                com.maddox.il2.engine.IconDraw.setColor(255, 255, 255, 255);
            } else
            {
                com.maddox.il2.engine.IconDraw.render(actor, p2d.x, p2d.y);
            }
        }

    }

    public void configure()
    {
        if(com.maddox.il2.builder.PlMapLabel.getPlugin("MapActors") == null)
        {
            throw new RuntimeException("PlMisStatic: plugin 'MapActors' not found");
        } else
        {
            pluginActors = (com.maddox.il2.builder.PlMapActors)com.maddox.il2.builder.PlMapLabel.getPlugin("MapActors");
            return;
        }
    }

    private void fillComboBox2(int i)
    {
        if(i != startComboBox1)
            return;
        if(builder.wSelect.curFilledType != i)
        {
            builder.wSelect.curFilledType = i;
            builder.wSelect.comboBox2.clear(false);
            builder.wSelect.comboBox2.add("Label");
            builder.wSelect.comboBox1.setSelected(i, true, false);
        }
        builder.wSelect.comboBox2.setSelected(0, true, false);
    }

    public void createGUI()
    {
        startComboBox1 = builder.wSelect.comboBox1.size();
        builder.wSelect.comboBox1.add("Label");
        builder.wSelect.comboBox1.addNotifyListener(new com.maddox.gwindow.GNotifyListener() {

            public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
            {
                int k = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
                if(k >= 0 && i == 2)
                    fillComboBox2(k);
                return false;
            }

        }
);
    }

    public void start()
    {
        com.maddox.rts.HotKeyCmdEnv.setCurrentEnv(com.maddox.il2.builder.Builder.envName);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "selectLabel") {

            public void begin()
            {
                setSelected(sName, startComboBox1, 0);
            }

        }
);
    }

    private void setSelected(java.lang.String s, int i, int j)
    {
        builder.wSelect.comboBox1.setSelected(i, true, true);
        builder.wSelect.comboBox2.setSelected(j, true, true);
        builder.tip(s);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    protected java.util.ArrayList allActors;
    private com.maddox.JGP.Point2d p2d;
    private com.maddox.il2.builder.PlMapActors pluginActors;
    private int startComboBox1;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.builder.PlMapLabel.class, "name", "MapLabel");
    }



}

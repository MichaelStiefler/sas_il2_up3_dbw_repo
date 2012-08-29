// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PlMapText.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.Loc;
import com.maddox.rts.HomePath;
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
//            Plugin, ActorText, PlMapActors, PlMapLoad, 
//            Builder, WSelect

public class PlMapText extends com.maddox.il2.builder.Plugin
{

    public PlMapText()
    {
        allActors = new ArrayList();
    }

    private java.lang.String staticFileName()
    {
        java.lang.String s = "maps/" + com.maddox.il2.builder.PlMapLoad.mapFileName();
        com.maddox.rts.SectFile sectfile = new SectFile(s);
        int i = sectfile.sectionIndex("text");
        if(i >= 0 && sectfile.vars(i) > 0)
        {
            java.lang.String s1 = sectfile.var(i, 0);
            return com.maddox.rts.HomePath.concatNames(s, s1);
        } else
        {
            return null;
        }
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
            com.maddox.il2.builder.ActorText actortext = (com.maddox.il2.builder.ActorText)allActors.get(j);
            int k = 0;
            if(actortext.bLevel[0])
                k |= 1;
            if(actortext.bLevel[1])
                k |= 2;
            if(actortext.bLevel[2])
                k |= 4;
            printwriter.println("" + (int)actortext.pos.getAbsPoint().x + " " + (int)actortext.pos.getAbsPoint().y + " " + k + " " + actortext.align + " " + actortext.getFont() + " " + actortext.color + " " + actortext.getText());
        }

        printwriter.close();
        return true;
        java.lang.Exception exception;
        exception;
        java.lang.System.out.println("MapTexts save failed: " + exception.getMessage());
        exception.printStackTrace();
        return false;
    }

    private void load()
    {
        java.lang.String s;
        s = staticFileName();
        if(s == null)
            return;
        com.maddox.JGP.Point3d point3d;
        java.io.BufferedReader bufferedreader;
        point3d = new Point3d();
        bufferedreader = new BufferedReader(new SFSReader(s));
_L2:
        int i;
        int j;
        int k;
        int l;
        java.lang.String s2;
        int i1;
        int j1;
        java.lang.String s1 = bufferedreader.readLine();
        if(s1 == null)
            break MISSING_BLOCK_LABEL_352;
        com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(s1);
        point3d.x = numbertokenizer.next(0);
        point3d.y = numbertokenizer.next(0);
        point3d.z = 0.0D;
        i = numbertokenizer.next(7, 1, 7);
        j = numbertokenizer.next(1, 0, 2);
        k = numbertokenizer.next(1, 0, 2);
        l = numbertokenizer.next(0, 0, 19);
        s2 = numbertokenizer.nextToken("");
        i1 = 0;
        for(j1 = s2.length() - 1; i1 < j1; i1++)
            if(s2.charAt(i1) > ' ')
                break;

        for(; i1 < j1; j1--)
            if(s2.charAt(j1) > ' ')
                break;

        if(i1 == j1)
            return;
        if(i1 != 0 || j1 != s2.length() - 1)
            s2 = s2.substring(i1, j1 + 1);
        com.maddox.il2.builder.ActorText actortext = new ActorText(point3d);
        actortext.setFont(k);
        actortext.setText(s2);
        actortext.align = j;
        actortext.color = l;
        actortext.bLevel[0] = (i & 1) != 0;
        actortext.bLevel[1] = (i & 2) != 0;
        actortext.bLevel[2] = (i & 4) != 0;
        insert(actortext, false);
        if(true) goto _L2; else goto _L1
_L1:
        bufferedreader.close();
        break MISSING_BLOCK_LABEL_395;
        java.lang.Exception exception;
        exception;
        java.lang.System.out.println("MapTexts load failed: " + exception.getMessage());
        exception.printStackTrace();
    }

    public void mapLoaded()
    {
        deleteAll();
        if(!com.maddox.il2.builder.Plugin.builder.isLoadedLandscape())
        {
            return;
        } else
        {
            load();
            return;
        }
    }

    public void deleteAll()
    {
        int i = allActors.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.builder.ActorText actortext = (com.maddox.il2.builder.ActorText)allActors.get(j);
            actortext.destroy();
        }

        allActors.clear();
    }

    public void delete(com.maddox.il2.engine.Actor actor)
    {
        allActors.remove(actor);
        actor.destroy();
    }

    public void selectAll()
    {
        int i = allActors.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.builder.ActorText actortext = (com.maddox.il2.builder.ActorText)allActors.get(j);
            com.maddox.il2.builder.Plugin.builder.selectActorsAdd(actortext);
        }

    }

    public void insert(com.maddox.il2.engine.Loc loc, boolean flag)
    {
        int i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
        if(i != startComboBox1)
        {
            return;
        } else
        {
            com.maddox.il2.builder.ActorText actortext = new ActorText(loc.getPoint());
            insert(actortext, flag);
            return;
        }
    }

    private void insert(com.maddox.il2.builder.ActorText actortext, boolean flag)
    {
        com.maddox.il2.builder.Plugin.builder.align(actortext);
        com.maddox.rts.Property.set(actortext, "builderSpawn", "");
        com.maddox.rts.Property.set(actortext, "builderPlugin", this);
        allActors.add(actortext);
        if(flag)
            com.maddox.il2.builder.Plugin.builder.setSelected(actortext);
    }

    public void renderMap2D()
    {
        if(com.maddox.il2.builder.Plugin.builder.isFreeView())
            return;
        if(com.maddox.il2.builder.Plugin.builder.isView3D())
            return;
        if(!viewType.bChecked)
            return;
        double d = com.maddox.il2.builder.Plugin.builder.camera2D.worldScale;
        byte byte0 = 1;
        if(d < 0.01D)
            byte0 = 0;
        else
        if(d > 0.050000000000000003D)
            byte0 = 2;
        com.maddox.il2.builder.ActorText.setRenderLevel(byte0);
        com.maddox.il2.builder.ActorText.setRenderClip(0.0D, 0.0D, com.maddox.il2.builder.Plugin.builder.clientWindow.win.dx, com.maddox.il2.builder.Plugin.builder.clientWindow.win.dy);
        int i = allActors.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.builder.ActorText actortext = (com.maddox.il2.builder.ActorText)allActors.get(j);
            actortext.render2d();
        }

    }

    public void syncSelector()
    {
        com.maddox.il2.builder.ActorText actortext = (com.maddox.il2.builder.ActorText)com.maddox.il2.builder.Plugin.builder.selectedActor();
        fillComboBox2(startComboBox1);
        com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.addTab(1, tabText);
        wText.setValue(actortext.getText(), false);
        wFont.setSelected(actortext.getFont(), true, false);
        wAlign.setSelected(actortext.align, true, false);
        wColor.setSelected(actortext.color, true, false);
        wLevel0.setChecked(actortext.bLevel[0], false);
        wLevel1.setChecked(actortext.bLevel[1], false);
        wLevel2.setChecked(actortext.bLevel[2], false);
        actortext.saveAsDef();
    }

    private void updateView()
    {
        int i = allActors.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.builder.ActorText actortext = (com.maddox.il2.builder.ActorText)allActors.get(j);
            actortext.drawing(viewType.bChecked);
        }

    }

    public void configure()
    {
        if(com.maddox.il2.builder.Plugin.getPlugin("MapActors") == null)
        {
            throw new RuntimeException("PlMisStatic: plugin 'MapActors' not found");
        } else
        {
            pluginActors = (com.maddox.il2.builder.PlMapActors)com.maddox.il2.builder.Plugin.getPlugin("MapActors");
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
            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.add("Text");
            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.setSelected(i, true, false);
        }
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.setSelected(0, true, false);
    }

    public void viewTypeAll(boolean flag)
    {
        viewType.bChecked = flag;
        updateView();
    }

    public void createGUI()
    {
        startComboBox1 = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.size();
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.add("Text");
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
            if(pluginActors.viewBridge == com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.getItem(i))
                break;

        if(--i >= 0)
        {
            viewType = com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(i, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, "show Text", null) {

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
        tabText = com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.createTab("Text", gwindowdialogclient);
        gwindowdialogclient.addControl(wText = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 1.0F, 1.0F, 16F, 1.3F, "") {

            public void created()
            {
                super.created();
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorText actortext = (com.maddox.il2.builder.ActorText)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actortext.setText(getValue());
                    actortext.saveAsDef();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 3F, 4F, 1.3F, "Font:", null));
        gwindowdialogclient.addControl(wFont = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 6F, 3F, 10F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                add("Small");
                add("Medium");
                add("Large");
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorText actortext = (com.maddox.il2.builder.ActorText)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actortext.setFont(getSelected());
                    actortext.saveAsDef();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 5F, 4F, 1.3F, "Align:", null));
        gwindowdialogclient.addControl(wAlign = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 6F, 5F, 10F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                add("Left");
                add("Center");
                add("Right");
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorText actortext = (com.maddox.il2.builder.ActorText)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actortext.align = getSelected();
                    actortext.saveAsDef();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 7F, 4F, 1.3F, "Color:", null));
        gwindowdialogclient.addControl(wColor = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 6F, 7F, 10F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                add("(  0,  0,  0)");
                add("(128,  0,  0)");
                add("(  0,128,  0)");
                add("(128,128,  0)");
                add("(  0,  0,128)");
                add("(128,  0,128)");
                add("(  0,128,128)");
                add("(192,192,192)");
                add("(192,220,192)");
                add("(166,202,240)");
                add("(255,251,240)");
                add("(160,160,164");
                add("(128,128,128)");
                add("(255,  0,  0)");
                add("(  0,255,  0)");
                add("(255,255,  0)");
                add("(  0,  0,255)");
                add("(255,  0,255)");
                add("(  0,255,255)");
                add("(255,255,255)");
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorText actortext = (com.maddox.il2.builder.ActorText)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actortext.color = getSelected();
                    actortext.saveAsDef();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 9F, 4F, 1.3F, "Level:", null));
        gwindowdialogclient.addControl(wLevel0 = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 6F, 9F, null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorText actortext = (com.maddox.il2.builder.ActorText)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actortext.bLevel[0] = isChecked();
                    actortext.checkLevels(0);
                    setChecked(actortext.bLevel[0], false);
                    actortext.saveAsDef();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addControl(wLevel1 = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 8F, 9F, null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorText actortext = (com.maddox.il2.builder.ActorText)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actortext.bLevel[1] = isChecked();
                    actortext.checkLevels(1);
                    setChecked(actortext.bLevel[1], false);
                    actortext.saveAsDef();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addControl(wLevel2 = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 10F, 9F, null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorText actortext = (com.maddox.il2.builder.ActorText)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actortext.bLevel[2] = isChecked();
                    actortext.checkLevels(2);
                    setChecked(actortext.bLevel[2], false);
                    actortext.saveAsDef();
                    return false;
                }
            }

        }
);
    }

    public void start()
    {
        com.maddox.il2.builder.ActorText.setupFonts();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    protected java.util.ArrayList allActors;
    com.maddox.gwindow.GWindowTabDialogClient.Tab tabText;
    com.maddox.gwindow.GWindowEditControl wText;
    com.maddox.gwindow.GWindowComboControl wFont;
    com.maddox.gwindow.GWindowComboControl wAlign;
    com.maddox.gwindow.GWindowComboControl wColor;
    com.maddox.gwindow.GWindowCheckBox wLevel0;
    com.maddox.gwindow.GWindowCheckBox wLevel1;
    com.maddox.gwindow.GWindowCheckBox wLevel2;
    private com.maddox.il2.builder.PlMapActors pluginActors;
    private int startComboBox1;
    private com.maddox.gwindow.GWindowMenuItem viewType;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.builder.PlMapText.class, "name", "MapText");
    }


}

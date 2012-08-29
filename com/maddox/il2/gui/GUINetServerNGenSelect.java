// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUINetServerNGenSelect.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.USGS;
import com.maddox.rts.HomePath;
import com.maddox.rts.NetConnect;
import com.maddox.rts.NetEnv;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUIDialogClient, GUINetServer, GUISeparate

public class GUINetServerNGenSelect extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bExit)
            {
                com.maddox.il2.gui.GUINetServer.exitServer(true);
                return true;
            }
            if(gwindow == bDel)
            {
                if(wTable.selectRow < 0)
                    return true;
                if(wTable.selectRow >= wTable.campList.size())
                    return true;
                com.maddox.il2.gui.GUINetServerNGenSelect.cur = null;
                com.maddox.il2.gui.Item item = (com.maddox.il2.gui.Item)wTable.campList.get(wTable.selectRow);
                try
                {
                    java.lang.String s = item.fileName;
                    int k = s.lastIndexOf("/conf.dat");
                    if(k >= 0)
                        s = s.substring(0, k);
                    java.io.File file = new File(com.maddox.rts.HomePath.get(0), s);
                    clearDir(file);
                    wTable.campList.remove(wTable.selectRow);
                    if(wTable.selectRow >= wTable.campList.size())
                        wTable.setSelect(wTable.campList.size() - 1, 0);
                }
                catch(java.lang.Exception exception)
                {
                    java.lang.System.out.println(exception.getMessage());
                    exception.printStackTrace();
                    return true;
                }
                return true;
            }
            if(gwindow == bStart)
            {
                if(wTable.selectRow < 0)
                    return true;
                if(wTable.selectRow >= wTable.campList.size())
                    return true;
                com.maddox.il2.gui.GUINetServerNGenSelect.cur = null;
                com.maddox.il2.gui.Item item1 = (com.maddox.il2.gui.Item)wTable.campList.get(wTable.selectRow);
                if(item1.bNew)
                    try
                    {
                        java.lang.String s1 = item1.prefix;
                        int l = 1;
                        do
                        {
                            if(l <= 0)
                                break;
                            java.io.File file1 = new File(com.maddox.rts.HomePath.get(0), "missions/net/ngen/" + s1 + l);
                            if(!file1.exists())
                            {
                                s1 = "missions/net/ngen/" + s1 + l;
                                file1.mkdirs();
                                break;
                            }
                            l++;
                        } while(true);
                        java.lang.String s2 = s1 + "/conf.dat";
                        com.maddox.il2.gui.Item item2 = new Item(item1);
                        item2.bNew = false;
                        item2.fileName = s2;
                        com.maddox.rts.SectFile sectfile = new SectFile(item1.fileName, 0, true, null, com.maddox.rts.RTSConf.charEncoding, true);
                        sectfile.saveFile(s2);
                        com.maddox.il2.gui.GUINetServerNGenSelect.cur = item2;
                    }
                    catch(java.lang.Exception exception1)
                    {
                        java.lang.System.out.println(exception1.getMessage());
                        exception1.printStackTrace();
                        return true;
                    }
                else
                    com.maddox.il2.gui.GUINetServerNGenSelect.cur = item1;
                if(com.maddox.il2.gui.GUINetServerNGenSelect.cur != null)
                    com.maddox.il2.game.Main.stateStack().change(69);
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        private void clearDir(java.io.File file)
        {
            java.io.File afile[] = file.listFiles();
            if(afile != null)
            {
                for(int i = 0; i < afile.length; i++)
                {
                    java.io.File file1 = afile[i];
                    java.lang.String s = file1.getName();
                    if(".".equals(s) || "..".equals(s))
                        continue;
                    if(file1.isDirectory())
                        clearDir(file1);
                    else
                        file1.delete();
                }

            }
            file.delete();
        }

        public void preRender()
        {
            super.preRender();
            if(wTable.isEnableDel())
            {
                if(!bDel.isVisible())
                    bDel.showWindow();
            } else
            if(bDel.isVisible())
                bDel.hideWindow();
            if(wTable.isEnableLoad())
            {
                if(!bStart.isVisible())
                    bStart.showWindow();
            } else
            if(bStart.isVisible())
                bStart.hideWindow();
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(624F), x1024(960F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(96F), y1024(658F), x1024(128F), y1024(48F), 0, !com.maddox.il2.net.USGS.isUsed() && com.maddox.il2.game.Main.cur().netGameSpy == null ? i18n("netsms.MainMenu") : i18n("main.Quit"));
            if(wTable.isEnableDel())
                draw(x1024(256F), y1024(658F), x1024(160F), y1024(48F), 2, i18n("camps.Delete"));
            if(wTable.isEnableLoad())
                draw(x1024(766F), y1024(658F), x1024(160F), y1024(48F), 2, i18n("camps.Load"));
        }

        public void setPosSize()
        {
            set1024PosSize(0.0F, 32F, 1024F, 736F);
            bExit.setPosC(x1024(56F), y1024(682F));
            bDel.setPosC(x1024(456F), y1024(682F));
            bStart.setPosC(x1024(968F), y1024(682F));
            wTable.set1024PosSize(32F, 32F, 960F, 480F);
        }

        public DialogClient()
        {
        }
    }

    public class Table extends com.maddox.gwindow.GWindowTable
    {

        public int countRows()
        {
            return campList == null ? 0 : campList.size();
        }

        public void renderCell(int i, int j, boolean flag, float f, float f1)
        {
            setCanvasFont(0);
            if(flag)
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, lookAndFeel().regionWhite);
            }
            com.maddox.il2.gui.Item item = (com.maddox.il2.gui.Item)campList.get(i);
            float f2 = 0.0F;
            java.lang.String s = null;
            int k = 0;
            switch(j)
            {
            case 0: // '\0'
                s = item.name;
                break;

            case 1: // '\001'
                if(item.bNew)
                    s = com.maddox.il2.game.I18N.gui("ngens.new");
                else
                if(item.bEnd)
                    s = com.maddox.il2.game.I18N.gui("ngens.complete");
                else
                    s = com.maddox.il2.game.I18N.gui("ngens.progress");
                k = 1;
                break;

            case 2: // '\002'
                s = "" + item.missions;
                k = 1;
                break;

            case 3: // '\003'
                s = item.note;
                break;
            }
            if(flag)
            {
                setCanvasColorWHITE();
                draw(f2, 0.0F, f, f1, k, s);
            } else
            {
                setCanvasColorBLACK();
                draw(f2, 0.0F, f, f1, k, s);
            }
        }

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(super.notify(gwindow, i, j))
            {
                return true;
            } else
            {
                notify(i, j);
                return false;
            }
        }

        public void afterCreated()
        {
            super.afterCreated();
            bColumnsSizable = true;
            bSelectRow = true;
            addColumn(com.maddox.il2.game.I18N.gui("ngens.name"), null);
            addColumn(com.maddox.il2.game.I18N.gui("ngens.state"), null);
            addColumn(com.maddox.il2.game.I18N.gui("ngens.missions"), null);
            addColumn(com.maddox.il2.game.I18N.gui("ngens.note"), null);
            vSB.scroll = rowHeight(0);
            getColumn(0).setRelativeDx(10F);
            getColumn(1).setRelativeDx(5F);
            getColumn(2).setRelativeDx(5F);
            getColumn(3).setRelativeDx(20F);
            alignColumns();
            bNotify = true;
            wClient.bNotify = true;
            resized();
        }

        public boolean isEnableDel()
        {
            if(campList == null)
                return false;
            if(selectRow < 0)
                return false;
            if(selectRow >= campList.size())
            {
                return false;
            } else
            {
                com.maddox.il2.gui.Item item = (com.maddox.il2.gui.Item)campList.get(selectRow);
                return !item.bNew;
            }
        }

        public boolean isEnableLoad()
        {
            if(campList == null)
                return false;
            if(selectRow < 0)
                return false;
            if(selectRow >= campList.size())
            {
                return false;
            } else
            {
                com.maddox.il2.gui.Item item = (com.maddox.il2.gui.Item)campList.get(selectRow);
                return !item.bEnd;
            }
        }

        public void resolutionChanged()
        {
            vSB.scroll = rowHeight(0);
            super.resolutionChanged();
        }

        public java.util.ArrayList campList;

        public Table(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
            campList = new ArrayList();
        }
    }

    static class Item
    {

        public boolean equals(java.lang.Object obj)
        {
            if(obj == null)
                return false;
            if(!(obj instanceof com.maddox.il2.gui.Item))
            {
                return false;
            } else
            {
                com.maddox.il2.gui.Item item = (com.maddox.il2.gui.Item)obj;
                return fileName.equalsIgnoreCase(item.fileName);
            }
        }

        public boolean bNew;
        public boolean bEnd;
        public java.lang.String prefix;
        public java.lang.String fileName;
        public java.lang.String name;
        public int missions;
        public java.lang.String note;

        public Item()
        {
            name = "";
            missions = 0;
            note = "";
        }

        public Item(com.maddox.il2.gui.Item item)
        {
            name = "";
            missions = 0;
            note = "";
            bNew = item.bNew;
            bEnd = item.bEnd;
            fileName = item.fileName;
            name = item.name;
            missions = item.missions;
            note = item.note;
        }
    }


    public void _enter()
    {
        com.maddox.rts.NetEnv.cur().connect.bindEnable(true);
        com.maddox.il2.game.Main.cur().netServerParams.USGSupdate();
        fillCampList();
        wTable.resized();
        client.activateWindow();
    }

    public void _leave()
    {
        wTable.campList.clear();
        client.hideWindow();
    }

    private void fillCampList()
    {
        java.lang.String s = com.maddox.rts.RTSConf.cur.locale.getLanguage();
        java.lang.String s1 = "campaign";
        java.lang.String s2 = null;
        if(!"us".equals(s))
            s2 = "_" + s + ".dat";
        java.lang.String s3 = ".dat";
        java.io.File file = new File(com.maddox.rts.HomePath.get(0), "ngen");
        java.io.File afile[] = file.listFiles();
        java.lang.String as[] = file.list();
        if(as == null || as.length == 0)
            return;
        java.util.TreeMap treemap = new TreeMap();
        for(int i = 0; i < as.length; i++)
        {
            if(afile[i].isDirectory() || afile[i].isHidden())
                continue;
            java.lang.String s4 = as[i];
            if(s4 == null)
                continue;
            s4 = s4.toLowerCase();
            if(s4.length() <= s1.length() || !s4.regionMatches(true, 0, s1, 0, s1.length()))
                continue;
            int l = -1;
            boolean flag = false;
            if(s2 != null && s4.length() > s2.length() && s4.regionMatches(true, s4.length() - s2.length(), s2, 0, s2.length()))
            {
                l = s4.length() - s2.length();
                flag = true;
            }
            if(l == -1 && s4.length() > s3.length() && s4.regionMatches(true, s4.length() - s3.length(), s3, 0, s3.length()))
            {
                l = s4.length() - s3.length();
                if(s4.length() > s3.length() + 3 && s4.charAt(s4.length() - s3.length() - 3) == '_')
                    continue;
                flag = false;
            }
            if(l < s1.length())
                continue;
            java.lang.String s7 = s4.substring(s1.length(), l);
            if(flag || !treemap.containsKey(s7))
                treemap.put(s7, s4);
        }

        if(treemap.size() == 0)
            return;
        com.maddox.il2.gui.Item item1;
        for(java.util.Iterator iterator = treemap.keySet().iterator(); iterator.hasNext(); wTable.campList.add(item1))
        {
            java.lang.String s5 = (java.lang.String)iterator.next();
            java.lang.String s6 = (java.lang.String)treemap.get(s5);
            item1 = new Item();
            item1.bNew = true;
            item1.prefix = s5;
            item1.fileName = "ngen/" + s6;
            com.maddox.rts.SectFile sectfile = new SectFile(item1.fileName, 4, true, null, com.maddox.rts.RTSConf.charEncoding, true);
            item1.name = sectfile.get("$locale", "name", "");
            item1.note = sectfile.get("$locale", "note", "");
        }

        file = new File(com.maddox.rts.HomePath.get(0), "missions/net/ngen");
        afile = file.listFiles();
        if(afile != null && afile.length > 0)
        {
            for(int j = 0; j < afile.length;)
            {
                if(!afile[j].isDirectory() || afile[j].isHidden())
                    continue;
                try
                {
                    java.io.File file1 = new File(afile[j], "conf.dat");
                    if(!file1.exists())
                        continue;
                    com.maddox.il2.gui.Item item2 = new Item();
                    item2.bNew = false;
                    item2.fileName = "missions/net/ngen/" + afile[j].getName().toLowerCase() + "/conf.dat";
                    com.maddox.rts.SectFile sectfile1 = new SectFile(item2.fileName, 4, true, null, com.maddox.rts.RTSConf.charEncoding, true);
                    item2.bEnd = sectfile1.get("$select", "complete", false);
                    item2.name = sectfile1.get("$locale", "name", "");
                    item2.note = sectfile1.get("$locale", "note", "");
                    wTable.campList.add(item2);
                    int i1 = sectfile1.sectionIndex("$missions");
                    if(i1 >= 0)
                        item2.missions = sectfile1.vars(i1);
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
        if(wTable.campList.size() == 0)
            return;
        if(cur != null)
        {
            int k = 0;
            do
            {
                if(k >= wTable.campList.size())
                    break;
                com.maddox.il2.gui.Item item = (com.maddox.il2.gui.Item)wTable.campList.get(k);
                if(cur.equals(item))
                    break;
                k++;
            } while(true);
            if(k < wTable.campList.size())
                wTable.setSelect(k, 0);
            else
                wTable.setSelect(0, 0);
        } else
        {
            wTable.setSelect(0, 0);
        }
    }

    public GUINetServerNGenSelect(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(68);
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("ngens.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        wTable = new Table(dialogClient);
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bDel = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bStart = (com.maddox.il2.gui.GUIButton)dialogClient.addDefault(new GUIButton(dialogClient, gtexture, 0.0F, 192F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.Table wTable;
    public com.maddox.il2.gui.GUIButton bExit;
    public com.maddox.il2.gui.GUIButton bDel;
    public com.maddox.il2.gui.GUIButton bStart;
    static com.maddox.il2.gui.Item cur = null;

}

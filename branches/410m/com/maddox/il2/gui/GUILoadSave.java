// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUILoadSave.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.rts.HomePath;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUIDialogClient, GUISeparate

public class GUILoadSave extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == wPrev)
            {
                com.maddox.il2.game.Main.stateStack().pop();
                return true;
            }
            if(gwindow == wDel)
            {
                java.lang.String s = wEdit.getValue();
                if(s == null || s.length() == 0)
                    return true;
                if(delete(s, isExistFile(s)))
                    fillFiles(false);
                return true;
            }
            if(gwindow == wSave)
            {
                java.lang.String s1 = wEdit.getValue();
                if(s1 == null || s1.length() == 0)
                    return true;
                if(execute(s1, isExistFile(s1)))
                    if(exitOnDo())
                        com.maddox.il2.game.Main.stateStack().pop();
                    else
                        fillFiles(false);
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(608F), x1024(384F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(64F), y1024(384F), x1024(320F), y1024(32F), 1, getEditName());
            draw(x1024(96F), y1024(480F), x1024(208F), y1024(48F), 0, getDelName());
            draw(x1024(96F), y1024(544F), x1024(208F), y1024(48F), 0, getDoName());
            draw(x1024(96F), y1024(624F), x1024(208F), y1024(48F), 0, getBackName());
        }

        public void setPosSize()
        {
            set1024PosSize(304F, 48F, 448F, 704F);
            wDel.setPosC(x1024(56F), y1024(504F));
            wSave.setPosC(x1024(56F), y1024(568F));
            wPrev.setPosC(x1024(56F), y1024(648F));
            wTable.setPosSize(x1024(32F), y1024(32F), x1024(384F), y1024(336F));
            wEdit.setPosSize(x1024(32F), y1024(432F), x1024(384F), y1024(32F));
        }

        public DialogClient()
        {
        }
    }

    public class Table extends com.maddox.gwindow.GWindowTable
    {

        public int countRows()
        {
            return files == null ? 0 : files.size();
        }

        public void renderCell(int i, int j, boolean flag, float f, float f1)
        {
            setCanvasFont(0);
            if(flag)
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, lookAndFeel().regionWhite);
                setCanvasColorWHITE();
                draw(0.0F, 0.0F, f, f1, 0, (java.lang.String)files.get(i));
            } else
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, 0, (java.lang.String)files.get(i));
            }
        }

        public void setSelect(int i, int j)
        {
            super.setSelect(i, j);
            if(i < 0 || i >= wTable.files.size())
            {
                return;
            } else
            {
                java.lang.String s = (java.lang.String)files.get(i);
                wEdit.setValue(s);
                return;
            }
        }

        public void resolutionChanged()
        {
            vSB.scroll = rowHeight(0);
            super.resolutionChanged();
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

        public java.util.ArrayList files;

        public Table(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
            files = new ArrayList();
            bColumnsSizable = false;
            addColumn(getListName(), null);
            vSB.scroll = rowHeight(0);
            bNotify = true;
            wClient.bNotify = true;
            resized();
        }
    }


    public java.lang.String getPathFiles()
    {
        return "records";
    }

    public java.lang.String getFileExtension()
    {
        return ".trk";
    }

    public java.lang.String getInfo()
    {
        return "Save Track";
    }

    public java.lang.String getListName()
    {
        return "Track files";
    }

    public java.lang.String getEditName()
    {
        return "Type track name";
    }

    public java.lang.String getDelName()
    {
        return "Delete";
    }

    public java.lang.String getDoName()
    {
        return "Save";
    }

    public java.lang.String getBackName()
    {
        return "Done";
    }

    public boolean exitOnDo()
    {
        return true;
    }

    public boolean execute(java.lang.String s, boolean flag)
    {
        return false;
    }

    public boolean delete(java.lang.String s, boolean flag)
    {
        if(!flag)
        {
            return false;
        } else
        {
            _deleteFileName = s;
            new com.maddox.gwindow.GWindowMessageBox(client.root, 20F, true, i18n("warning.Warning"), i18n("warning.DeleteFile"), 1, 0.0F) {

                public void result(int i)
                {
                    if(i != 3)
                        return;
                    int j = wTable.selectRow;
                    java.lang.String s1 = (java.lang.String)wTable.files.get(j);
                    try
                    {
                        java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName(getPathFiles() + "/" + _deleteFileName, 0));
                        file.delete();
                    }
                    catch(java.lang.Exception exception) { }
                    fillFiles(false);
                    if(s1.compareToIgnoreCase(_deleteFileName) == 0)
                    {
                        if(j >= wTable.files.size())
                            j = wTable.files.size() - 1;
                        if(j >= 0)
                        {
                            wTable.setSelect(j, 0);
                        } else
                        {
                            fillFiles(true);
                            wEdit.clear(false);
                        }
                    }
                }

            }
;
            return false;
        }
    }

    public java.lang.String appendExtension(java.lang.String s)
    {
        if(!s.toLowerCase().endsWith(getFileExtension().toLowerCase()))
            return s + getFileExtension();
        else
            return s;
    }

    public void _enter()
    {
        fillFiles(true);
        client.activateWindow();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    public void fillFiles(boolean flag)
    {
        wTable.files.clear();
        java.io.File file = new File(com.maddox.rts.HomePath.get(0), getPathFiles());
        java.io.File afile[] = file.listFiles();
        if(afile != null && afile.length > 0)
        {
            for(int i = 0; i < afile.length; i++)
                if(!afile[i].isDirectory() && !afile[i].isHidden() && afile[i].getName().toLowerCase().endsWith(getFileExtension().toLowerCase()))
                    _scanMap.put(afile[i].getName(), null);

            for(java.util.Iterator iterator = _scanMap.keySet().iterator(); iterator.hasNext(); wTable.files.add(iterator.next()));
            if(_scanMap.size() > 0 && flag)
                wTable.setSelect(0, 0);
            _scanMap.clear();
        }
        wTable.resized();
    }

    public boolean isExistFile(java.lang.String s)
    {
        java.io.File file = new File(com.maddox.rts.HomePath.get(0), getPathFiles());
        java.io.File afile[] = file.listFiles();
        if(afile != null && afile.length > 0)
        {
            for(int i = 0; i < afile.length; i++)
                if(s.compareToIgnoreCase(afile[i].getName()) == 0)
                    return true;

        }
        return false;
    }

    public void init(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = getInfo();
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        wTable = new Table(dialogClient);
        wEdit = (com.maddox.gwindow.GWindowEditControl)dialogClient.addControl(new GWindowEditControl(dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null));
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        wDel = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        wSave = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        wPrev = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public GUILoadSave(int i)
    {
        super(i);
        _scanMap = new TreeMap();
    }

    private java.lang.String _deleteFileName;
    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton wPrev;
    public com.maddox.il2.gui.GUIButton wSave;
    public com.maddox.il2.gui.GUIButton wDel;
    public com.maddox.il2.gui.Table wTable;
    public com.maddox.gwindow.GWindowEditControl wEdit;
    public java.util.TreeMap _scanMap;

}

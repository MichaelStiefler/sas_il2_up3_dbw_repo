// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PlMisBrief.java

package com.maddox.il2.builder;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowEditText;
import com.maddox.gwindow.GWindowEditTextControl;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuBarItem;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.rts.HomePath;
import com.maddox.rts.Property;
import com.maddox.rts.SFSReader;
import com.maddox.rts.SectFile;
import com.maddox.util.SharedTokenizer;
import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, PlMission, Builder

public class PlMisBrief extends com.maddox.il2.builder.Plugin
{
    class WEditor extends com.maddox.gwindow.GWindowFramed
    {

        public void windowShown()
        {
            mEditor.bChecked = true;
            super.windowShown();
        }

        public void windowHidden()
        {
            mEditor.bChecked = false;
            super.windowHidden();
        }

        public void created()
        {
            bAlwaysOnTop = true;
            super.created();
            title = com.maddox.il2.builder.Plugin.i18n("Texts");
            clientWindow = create(new GWindowTabDialogClient());
            com.maddox.gwindow.GWindowTabDialogClient gwindowtabdialogclient = (com.maddox.gwindow.GWindowTabDialogClient)clientWindow;
            gwindowtabdialogclient.addTab(com.maddox.il2.builder.Plugin.i18n("textName"), gwindowtabdialogclient.create(nameClient = new GWindowDialogClient()));
            nameClient.addControl(wName = new com.maddox.gwindow.GWindowEditControl(nameClient, 0.0F, 0.0F, 1.0F, 1.0F, "") {

                public boolean notify(int i, int j)
                {
                    if(i == 2 && j == 0)
                        com.maddox.il2.builder.PlMission.setChanged();
                    return super.notify(i, j);
                }

            }
);
            gwindowtabdialogclient.addTab(com.maddox.il2.builder.Plugin.i18n("textShort"), gwindowtabdialogclient.create(wShort = new com.maddox.gwindow.GWindowEditTextControl() {

                public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
                {
                    if(gwindow == edit && i == 2 && j == 0)
                        com.maddox.il2.builder.PlMission.setChanged();
                    return super.notify(gwindow, i, j);
                }

            }
));
            gwindowtabdialogclient.addTab(com.maddox.il2.builder.Plugin.i18n("textDescr"), gwindowtabdialogclient.create(wInfo = new com.maddox.gwindow.GWindowEditTextControl() {

                public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
                {
                    if(gwindow == edit && i == 2 && j == 0)
                        com.maddox.il2.builder.PlMission.setChanged();
                    return super.notify(gwindow, i, j);
                }

            }
));
        }

        public void deleteAll()
        {
            wName.setValue("", false);
            wShort.edit.clear(false);
            wInfo.edit.clear(false);
        }

        public void resized()
        {
            super.resized();
            if(wName != null)
                wName.setSize(nameClient.win.dx, 1.3F * lookAndFeel().metric());
        }

        public void afterCreated()
        {
            super.afterCreated();
            resized();
            close(false);
        }

        public com.maddox.gwindow.GWindowDialogClient nameClient;
        public com.maddox.gwindow.GWindowEditControl wName;
        public com.maddox.gwindow.GWindowEditTextControl wShort;
        public com.maddox.gwindow.GWindowEditTextControl wInfo;

        public WEditor()
        {
            doNew(com.maddox.il2.builder.Plugin.builder.clientWindow, 2.0F, 2.0F, 26F, 20F, true);
        }
    }


    public PlMisBrief()
    {
    }

    public void load(com.maddox.rts.SectFile sectfile)
    {
        java.lang.String s = textFileName(sectfile);
        wEditor.wName.clear(false);
        wEditor.wShort.edit.clear(false);
        wEditor.wInfo.edit.clear(false);
        java.io.BufferedReader bufferedreader = null;
        try
        {
            bufferedreader = new BufferedReader(new SFSReader(s));
            do
            {
                java.lang.String s1 = bufferedreader.readLine();
                if(s1 == null)
                    break;
                int i = s1.length();
                if(i != 0)
                {
                    com.maddox.util.SharedTokenizer.set(s1);
                    java.lang.String s2 = com.maddox.util.SharedTokenizer.next();
                    if(s2 != null)
                        if("Name".compareToIgnoreCase(s2) == 0)
                        {
                            java.lang.String s3 = com.maddox.util.SharedTokenizer.getGap();
                            if(s3 != null)
                                wEditor.wName.setValue(com.maddox.util.UnicodeTo8bit.load(s3), false);
                        } else
                        if("Short".compareToIgnoreCase(s2) == 0)
                        {
                            java.lang.String s4 = com.maddox.util.SharedTokenizer.getGap();
                            if(s4 != null)
                                fillEditor(wEditor.wShort.edit, s4);
                        } else
                        if("Description".compareToIgnoreCase(s2) == 0)
                        {
                            java.lang.String s5 = com.maddox.util.SharedTokenizer.getGap();
                            if(s5 != null)
                                fillEditor(wEditor.wInfo.edit, s5);
                        }
                }
            } while(true);
        }
        catch(java.lang.Exception exception) { }
        if(bufferedreader != null)
            try
            {
                bufferedreader.close();
            }
            catch(java.lang.Exception exception1) { }
    }

    private void fillEditor(com.maddox.gwindow.GWindowEditText gwindowedittext, java.lang.String s)
    {
        java.lang.String s1 = com.maddox.util.UnicodeTo8bit.load(s);
        java.util.ArrayList arraylist = new ArrayList();
        int i = 0;
        int j = 0;
        for(int k = s1.length(); j < k; j++)
        {
            char c = s1.charAt(j);
            if(c == '\n')
            {
                if(i < j)
                    arraylist.add(s1.substring(i, j));
                else
                    arraylist.add("");
                i = j + 1;
            }
        }

        if(i < j)
            arraylist.add(s1.substring(i, j));
        gwindowedittext.insert(arraylist, true);
        gwindowedittext.clearUnDo();
    }

    private java.lang.String getEditor(com.maddox.gwindow.GWindowEditText gwindowedittext)
    {
        if(gwindowedittext.isEmpty())
            return "";
        java.util.ArrayList arraylist = gwindowedittext.text;
        java.lang.StringBuffer stringbuffer = new StringBuffer();
        for(int i = 0; i < arraylist.size(); i++)
        {
            java.lang.StringBuffer stringbuffer1 = (java.lang.StringBuffer)arraylist.get(i);
            if(stringbuffer1 != null && stringbuffer1.length() > 0)
                stringbuffer.append(stringbuffer1.toString());
            stringbuffer.append('\n');
        }

        return com.maddox.util.UnicodeTo8bit.save(stringbuffer.toString(), false);
    }

    public boolean save(com.maddox.rts.SectFile sectfile)
    {
        java.lang.String s = textFileName(sectfile);
        java.io.PrintWriter printwriter = null;
        try
        {
            printwriter = new PrintWriter(new BufferedWriter(new FileWriter(com.maddox.rts.HomePath.toFileSystemName(s, 0))));
            java.lang.String s1 = wEditor.wName.getValue();
            if(s1 != null && s1.length() > 0)
                printwriter.println("Name " + com.maddox.util.UnicodeTo8bit.save(s1, false));
            java.lang.String s2 = getEditor(wEditor.wShort.edit);
            if(s2 != null && s2.length() > 0)
                printwriter.println("Short " + s2);
            s2 = getEditor(wEditor.wInfo.edit);
            if(s2 != null && s2.length() > 0)
                printwriter.println("Description " + s2);
        }
        catch(java.lang.Exception exception) { }
        if(printwriter != null)
            try
            {
                printwriter.close();
            }
            catch(java.lang.Exception exception1) { }
        return true;
    }

    private java.lang.String textFileName(com.maddox.rts.SectFile sectfile)
    {
        java.lang.String s = "";
        java.lang.String s1 = java.util.Locale.getDefault().getLanguage();
        if(!"us".equals(s1))
            s = "_" + s1;
        java.lang.String s2 = sectfile.fileName();
        for(int i = s2.length() - 1; i >= 0; i--)
        {
            char c = s2.charAt(i);
            if(c == '/' || c == '\\')
                break;
            if(c == '.')
                return s2.substring(0, i) + s + ".properties";
        }

        return s2 + s + ".properties";
    }

    public void deleteAll()
    {
        wEditor.deleteAll();
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

    public void createGUI()
    {
        mEditor = com.maddox.il2.builder.Plugin.builder.mEdit.subMenu.addItem(1, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mEdit.subMenu, com.maddox.il2.builder.Plugin.i18n("&Texts"), com.maddox.il2.builder.Plugin.i18n("TIPTexts")) {

            public void execute()
            {
                if(wEditor.isVisible())
                    wEditor.hideWindow();
                else
                    wEditor.showWindow();
            }

        }
);
        wEditor = new WEditor();
    }

    public void freeResources()
    {
        if(wEditor.isVisible())
            wEditor.hideWindow();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    com.maddox.il2.builder.WEditor wEditor;
    com.maddox.gwindow.GWindowMenuItem mEditor;
    private com.maddox.il2.builder.PlMission pluginMission;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.builder.PlMisBrief.class, "name", "MisBrief");
    }
}

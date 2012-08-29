// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUISetupVideo.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.opengl.Provider;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.IniFile;
import com.maddox.rts.MsgAction;
import com.maddox.rts.ScreenMode;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUISwitch16, GUISwitchN, GUISwitchBox3, GUIButton, 
//            GUIDialogClient, GUISeparate

public class GUISetupVideo extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == sFull)
            {
                updateComboResolution(sFull.getState() == 1);
                return true;
            }
            if(gwindow == bExit)
            {
                com.maddox.il2.game.Main.stateStack().pop();
                return true;
            }
            if(gwindow == bApply)
            {
                new com.maddox.rts.MsgAction(72, 0.0D) {

                    public void doAction()
                    {
                        java.lang.String s = cmdLine();
                        if(s == null)
                            return;
                        if(s.equals(enterCmdLine) && enterUse3Renders == bCmdLineUse3Renders)
                            return;
                        if(com.maddox.rts.CmdEnv.top().exec(s) == com.maddox.rts.CmdEnv.RETURN_OK)
                            doConfirm();
                        else
                            update();
                    }

                }
;
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(208F), x1024(384F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(428F), x1024(384F), 2.0F);
            setCanvasColorWHITE();
            draw(x1024(64F), y1024(256F), x1024(64F), y1024(64F), texFull);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(64F), y1024(32F), x1024(320F), y1024(32F), 1, i18n("setupVideo.Driver"));
            draw(x1024(64F), y1024(112F), x1024(320F), y1024(32F), 1, i18n("setupVideo.Resolution"));
            draw(x1024(192F), y1024(240F), x1024(224F), y1024(32F), 0, i18n("setupVideo.Windowed"));
            draw(x1024(192F), y1024(304F), x1024(224F), y1024(32F), 0, i18n("setupVideo.FullScreen"));
            draw(x1024(192F), y1024(368F), x1024(224F), y1024(48F), 0, i18n("setupVideo.Stencil"));
            draw(x1024(96F), y1024(480F), x1024(224F), y1024(48F), 0, i18n("setupVideo.Apply"));
            draw(x1024(96F), y1024(544F), x1024(224F), y1024(48F), 0, i18n("setupVideo.Back"));
        }

        public void setPosSize()
        {
            set1024PosSize(302F, 80F, 448F, 624F);
            comboProvider.set1024PosSize(32F, 64F, 384F, 32F);
            comboResolution.set1024PosSize(32F, 144F, 384F, 32F);
            sFull.setPosC(x1024(96F), y1024(288F));
            sStencil.setPosC(x1024(120F), y1024(392F));
            bApply.setPosC(x1024(56F), y1024(504F));
            bExit.setPosC(x1024(56F), y1024(568F));
            update();
        }


        public DialogClient()
        {
        }
    }


    public int _findVideoMode(com.maddox.rts.ScreenMode screenmode, boolean flag)
    {
        for(int i = 0; i < screenModes.size(); i++)
        {
            com.maddox.rts.ScreenMode screenmode1 = (com.maddox.rts.ScreenMode)screenModes.get(i);
            if(screenmode1.width() == screenmode.width() && screenmode1.height() == screenmode.height() && screenmode1.colourBits() == screenmode.colourBits() && flag == (screenmode1.ext != null))
                return i;
        }

        return 0;
    }

    public int _findVideoModeWindow(int i, int j)
    {
        for(int k = 0; k < j; k++)
            if(screenModesWindow[k] == i)
                return k;

        if(i < screenModesWindow[0])
            return 0;
        else
            return j - 1;
    }

    public void _enter()
    {
        if(screenModes.size() <= 0)
        {
            com.maddox.il2.game.Main.stateStack().pop();
            return;
        } else
        {
            update();
            enterCmdLine = cmdLine();
            enterSaveAspect = com.maddox.il2.engine.Config.cur.windowSaveAspect;
            enterUse3Renders = com.maddox.il2.engine.Config.cur.windowUse3Renders;
            client.activateWindow();
            return;
        }
    }

    public void _leave()
    {
        client.hideWindow();
    }

    private void fillComboProvider()
    {
        java.lang.String as[] = com.maddox.il2.engine.Config.cur.ini.getVariables("GLPROVIDERS");
        if(as != null && as.length > 0)
        {
            for(int i = 0; i < as.length; i++)
            {
                java.lang.String s1 = com.maddox.il2.engine.Config.cur.ini.get("GLPROVIDERS", as[i], (java.lang.String)null);
                if(s1 != null)
                {
                    providerName.add(as[i]);
                    providerDll.add(s1);
                }
            }

        }
        java.lang.String s = com.maddox.opengl.Provider.GLname();
        int j = -1;
        for(int k = 0; k < providerDll.size(); k++)
        {
            if(((java.lang.String)providerDll.get(k)).compareToIgnoreCase(s) != 0)
                continue;
            j = k;
            break;
        }

        if(j == -1)
        {
            boolean flag = false;
            providerName.add(0, "OpenGL");
            providerDll.add(0, s);
        }
        for(int l = 0; l < providerName.size(); l++)
            comboProvider.add((java.lang.String)providerName.get(l));

    }

    private int _findProvider()
    {
        java.lang.String s = com.maddox.opengl.Provider.GLname();
        int i = -1;
        for(int j = 0; j < providerDll.size(); j++)
        {
            if(((java.lang.String)providerDll.get(j)).compareToIgnoreCase(s) != 0)
                continue;
            i = j;
            break;
        }

        if(i == -1)
            i = 0;
        return i;
    }

    public void update()
    {
        if(screenModes.size() <= 0)
            return;
        comboProvider.setSelected(_findProvider(), true, false);
        if(com.maddox.il2.engine.Config.cur.windowChangeScreenRes)
        {
            updateComboResolution(true);
            sFull.setState(1, false);
            sStencil.setChecked(com.maddox.il2.engine.Config.cur.windowStencilBits == 8, false);
        } else
        {
            updateComboResolution(false);
            sFull.setState(0, false);
            sStencil.setChecked(com.maddox.il2.engine.Config.cur.windowStencilBits == 8, false);
        }
    }

    private void prepareUse3Renders(boolean flag)
    {
        if(!flag)
            bCmdLineUse3Renders = enterUse3Renders;
        if(bCmdLineUse3Renders)
        {
            com.maddox.il2.game.Main3D.cur3D().setSaveAspect(true);
            com.maddox.il2.engine.Config.cur.windowUse3Renders = true;
            com.maddox.il2.engine.Config.cur.checkWindowUse3Renders();
        } else
        {
            com.maddox.il2.engine.Config.cur.windowUse3Renders = false;
        }
        if(!com.maddox.il2.engine.Config.cur.isUse3Renders())
            if(com.maddox.rts.ScreenMode.current().width() == com.maddox.rts.ScreenMode.current().height() * 4)
                com.maddox.il2.game.Main3D.cur3D().setSaveAspect(false);
            else
                com.maddox.il2.game.Main3D.cur3D().setSaveAspect(true);
    }

    public void updateComboResolution(boolean flag)
    {
        if(flag)
        {
            comboResolution.clear(false);
            for(int i = 0; i < screenModes.size(); i++)
            {
                com.maddox.rts.ScreenMode screenmode = (com.maddox.rts.ScreenMode)screenModes.get(i);
                if(screenmode.ext != null)
                    comboResolution.add("3x" + screenmode.width() / 3 + "x" + screenmode.height() + "x" + screenmode.colourBits());
                else
                    comboResolution.add(screenmode.width() + "x" + screenmode.height() + "x" + screenmode.colourBits());
            }

            comboResolution.setSelected(_findVideoMode(com.maddox.rts.ScreenMode.current(), com.maddox.il2.engine.Config.cur.isUse3Renders()), true, false);
        } else
        {
            int j = -1;
            if(comboResolution.size() > 0 && comboResolution.getSelected() >= 0)
            {
                java.lang.String s = comboResolution.getValue();
                if(s != null && s.indexOf('x') >= 0)
                {
                    s = s.substring(0, s.indexOf('x'));
                    j = java.lang.Integer.parseInt(s);
                }
            }
            comboResolution.clear(false);
            int k = com.maddox.rts.ScreenMode.startup().width();
            if(j < 0)
                j = k;
            for(int l = 0; l < screenModesWindow.length; l++)
            {
                int i1 = screenModesWindow[l];
                if(i1 >= k)
                    break;
                comboResolution.add(i1 + "x" + (i1 * 3) / 4);
            }

            comboResolution.setValue(com.maddox.il2.engine.Config.cur.windowWidth + "x" + com.maddox.il2.engine.Config.cur.windowHeight, false);
            comboResolution.setSelected(_findVideoModeWindow(j, comboResolution.size()), false, false);
        }
    }

    private java.lang.String cmdLine()
    {
        int i = comboResolution.getSelected();
        if(i < 0)
            return null;
        java.lang.String s = null;
        bCmdLineUse3Renders = false;
        if(sFull.getState() == 1)
        {
            com.maddox.rts.ScreenMode screenmode = (com.maddox.rts.ScreenMode)screenModes.get(i);
            s = "window " + screenmode.width() + " " + screenmode.height() + " " + screenmode.colourBits() + " " + screenmode.colourBits() + (sStencil.isChecked() ? " 8" : " 0") + " FULL PROVIDER " + providerDll.get(comboProvider.getSelected());
            bCmdLineUse3Renders = screenmode.ext != null;
        } else
        {
            int j = screenModesWindow[i];
            s = "window " + j + " " + (j * 3) / 4 + " " + com.maddox.il2.engine.Config.cur.windowColourBits + " " + com.maddox.il2.engine.Config.cur.windowColourBits + (sStencil.isChecked() ? " 8" : " 0") + " PROVIDER " + providerDll.get(comboProvider.getSelected());
        }
        return s;
    }

    private void doConfirm()
    {
        prepareUse3Renders(true);
        update();
        new com.maddox.rts.MsgAction(72, 0.0D) {

            public void doAction()
            {
                new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("setupVideo.Confirm"), i18n("setupVideo.Keep"), 1, 0.0F) {

                    public void result(int i)
                    {
                        if(i == 4 || i == 5)
                        {
                            com.maddox.rts.CmdEnv.top().exec(enterCmdLine);
                            prepareUse3Renders(false);
                            update();
                        } else
                        {
                            enterCmdLine = cmdLine();
                            enterSaveAspect = com.maddox.il2.engine.Config.cur.windowSaveAspect;
                            enterUse3Renders = com.maddox.il2.engine.Config.cur.windowUse3Renders;
                        }
                    }

                }
;
            }


        }
;
    }

    public GUISetupVideo(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(12);
        providerName = new ArrayList();
        providerDll = new ArrayList();
        screenModes = new ArrayList();
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("setupVideo.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        texFull = new GTexRegion("GUI/game/staticelements.mat", 192F, 96F, 64F, 64F);
        sFull = (com.maddox.il2.gui.GUISwitchN)dialogClient.addControl(new GUISwitch16(dialogClient, new int[] {
            10, 15
        }, new boolean[] {
            true, true
        }));
        sStencil = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        bApply = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        dialogClient.addControl(comboProvider = new GWindowComboControl(dialogClient, 0.0F, 0.0F, 1.0F));
        comboProvider.setEditable(false);
        comboProvider.resized();
        fillComboProvider();
        com.maddox.rts.ScreenMode ascreenmode[] = com.maddox.rts.ScreenMode.all();
        for(int i = 0; i < ascreenmode.length; i++)
        {
            com.maddox.rts.ScreenMode screenmode = ascreenmode[i];
            if(screenmode.colourBits() >= 15 && (float)screenmode.width() >= 640F)
                if(screenmode.width() == (screenmode.height() * 4) / 3)
                    screenModes.add(screenmode);
                else
                if(screenmode.width() == screenmode.height() * 4)
                {
                    com.maddox.rts.ScreenMode screenmode1 = new ScreenMode(screenmode);
                    screenmode1.ext = new Object();
                    screenModes.add(screenmode);
                    screenModes.add(screenmode1);
                }
        }

        if(screenModes.size() <= 0)
        {
            client.hideWindow();
            return;
        } else
        {
            dialogClient.addControl(comboResolution = new GWindowComboControl(dialogClient, 0.0F, 0.0F, 1.0F));
            comboResolution.setEditable(false);
            comboResolution.resized();
            update();
            dialogClient.activateWindow();
            client.hideWindow();
            return;
        }
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUISwitchN sFull;
    public com.maddox.il2.gui.GUISwitchBox3 sStencil;
    public com.maddox.il2.gui.GUIButton bApply;
    public com.maddox.il2.gui.GUIButton bExit;
    public com.maddox.gwindow.GTexRegion texFull;
    public com.maddox.gwindow.GWindowComboControl comboProvider;
    public com.maddox.gwindow.GWindowComboControl comboResolution;
    public java.util.ArrayList providerName;
    public java.util.ArrayList providerDll;
    public java.lang.String enterCmdLine;
    public boolean enterSaveAspect;
    public boolean enterUse3Renders;
    public int screenModesWindow[] = {
        640, 800, 1024
    };
    public java.util.ArrayList screenModes;
    private boolean bCmdLineUse3Renders;




}

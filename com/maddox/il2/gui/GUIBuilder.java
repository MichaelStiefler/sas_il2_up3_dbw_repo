// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIBuilder.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GWin95LookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowRootMenu;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.builder.Builder;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.RendersMain;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Time;

public class GUIBuilder extends com.maddox.il2.game.GameState
{

    public void _enter()
    {
        com.maddox.il2.engine.GUIWindowManager guiwindowmanager = com.maddox.il2.game.Main3D.cur3D().guiManager;
        guiwindowmanager.unActivateAll();
        com.maddox.il2.engine.RendersMain.setRenderFocus(null);
        guiwindowmanager.render.setShow(false);
        guiManager.activateTime(true);
        guiManager.activateKeyboard(true);
        guiManager.activateMouse(true);
        com.maddox.rts.Time.setPause(true);
        guiManager.activateJoy(true);
        com.maddox.rts.Time.setRealOnly(true);
        guiManager.render.setShow(true);
        com.maddox.il2.ai.World.cur().diffCur.NewCloudsRender = com.maddox.il2.engine.Config.cur.newCloudsRender;
        builder.enter();
        com.maddox.il2.game.Main3D.cur3D();
        com.maddox.il2.game.Main3D.cur3D().enableOnlyHotKeyCmdEnvs(com.maddox.il2.game.Main3D.builderHotKeyCmdEnvs);
        com.maddox.rts.CmdEnv.top().exec("music PUSH");
        com.maddox.rts.CmdEnv.top().exec("music STOP");
    }

    public void _leave()
    {
        builder.leave();
        com.maddox.rts.Time.setRealOnly(false);
        guiManager.unActivateAll();
        guiManager.render.setShow(false);
        com.maddox.il2.engine.GUIWindowManager guiwindowmanager = com.maddox.il2.game.Main3D.cur3D().guiManager;
        guiwindowmanager.activateTime(true);
        guiwindowmanager.activateKeyboard(true);
        guiwindowmanager.activateMouse(true);
        com.maddox.rts.Time.setPause(true);
        guiwindowmanager.activateJoy(true);
        guiwindowmanager.render.setShow(true);
        com.maddox.il2.engine.RendersMain.setRenderFocus((com.maddox.il2.engine.Render)com.maddox.il2.engine.Actor.getByName("renderGUI"));
        com.maddox.il2.game.Main3D.cur3D().disableAllHotKeyCmdEnv();
        com.maddox.rts.CmdEnv.top().exec("music POP");
        com.maddox.rts.CmdEnv.top().exec("music PLAY");
    }

    public GUIBuilder(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(18);
        rootWindow = new GWindowRootMenu();
        guiManager = new GUIWindowManager(-2.5F, rootWindow, new GWin95LookAndFeel(), "renderGUIBuilder");
        rootWindow.textFonts[0] = com.maddox.gwindow.GFont.New("arial8");
        rootWindow.textFonts[1] = com.maddox.gwindow.GFont.New("arialb8");
        ((com.maddox.gwindow.GWin95LookAndFeel)rootWindow.lAF()).metric = (int)(rootWindow.textFonts[0].height + 0.5F);
        rootWindow.resized();
        builder = new Builder(rootWindow, "builder");
        guiManager.render.setShow(false);
    }

    public static final java.lang.String envName = "builder";
    public com.maddox.gwindow.GWindowRootMenu rootWindow;
    public com.maddox.il2.engine.GUIWindowManager guiManager;
    public com.maddox.il2.builder.Builder builder;
}

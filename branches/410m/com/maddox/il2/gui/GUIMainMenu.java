// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIMainMenu.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.net.GameSpy;
import com.maddox.il2.net.USGS;

// Referenced classes of package com.maddox.il2.gui:
//            GUIRoot, GUIClient, GUIInfoMenu, GUIInfoName, 
//            GUILookAndFeel, GUIButton, GUIPocket, WindowPreferences, 
//            GUIDialogClient, GUISeparate

public class GUIMainMenu extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bInfo)
            {
                com.maddox.il2.game.Main.stateStack().push(15);
                return true;
            }
            if(gwindow == bPilot)
            {
                com.maddox.il2.game.Main.stateStack().push(1);
                return true;
            }
            if(gwindow == bControls)
            {
                com.maddox.il2.game.Main.stateStack().push(20);
                return true;
            }
            if(gwindow == bSingle)
            {
                com.maddox.il2.game.Main.stateStack().push(3);
                return true;
            }
            if(gwindow == bCampaigns)
            {
                com.maddox.il2.game.Main.stateStack().push(27);
                return true;
            }
            if(gwindow == bQuick)
            {
                com.maddox.il2.game.Main.stateStack().push(14);
                return true;
            }
            if(gwindow == bTraining)
            {
                com.maddox.il2.game.Main3D.cur3D().viewSet_Save();
                com.maddox.il2.game.Main.stateStack().push(56);
                return true;
            }
            if(gwindow == bRecord)
            {
                com.maddox.il2.game.Main3D.cur3D().viewSet_Save();
                com.maddox.il2.game.Main.stateStack().push(7);
                return true;
            }
            if(gwindow == bBuilder)
            {
                com.maddox.il2.game.Main.stateStack().push(18);
                return true;
            }
            if(gwindow == bMultiplay)
            {
                com.maddox.il2.game.Main.stateStack().push(33);
                return true;
            }
            if(gwindow == bSetup)
            {
                com.maddox.il2.game.Main.stateStack().push(10);
                return true;
            }
            if(gwindow == bCredits)
            {
                com.maddox.il2.game.Main.stateStack().push(16);
                return true;
            }
            if(gwindow == bExit)
            {
                new com.maddox.gwindow.GWindowMessageBox(root, 20F, true, i18n("main.ConfirmQuit"), i18n("main.ReallyQuit"), 1, 0.0F) {

                    public void result(int k)
                    {
                        if(k == 3)
                            com.maddox.il2.game.Main.doGameExit();
                        else
                            client.activateWindow();
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
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(336F), x1024(336F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(496F), x1024(336F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(384F), y1024(32F), 2.0F, y1024(560F));
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(400F), y1024(336F), x1024(288F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(400F), y1024(496F), x1024(288F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(104F), y1024(64F) - M(1.0F), x1024(256F), M(2.0F), 0, i18n("main.SingleMissions"));
            draw(x1024(104F), y1024(120F) - M(1.0F), x1024(256F), M(2.0F), 0, i18n("main.PilotCareer"));
            draw(x1024(104F), y1024(176F) - M(1.0F), x1024(256F), M(2.0F), 0, i18n("main.Multiplay"));
            draw(x1024(104F), y1024(232F) - M(1.0F), x1024(256F), M(2.0F), 0, i18n("main.Quick"));
            draw(x1024(104F), y1024(288F) - M(1.0F), x1024(256F), M(2.0F), 0, i18n("main.Builder"));
            draw(x1024(104F), y1024(376F) - M(1.0F), x1024(256F), M(2.0F), 0, i18n("main.Training"));
            draw(x1024(104F), y1024(457F) - M(1.0F), x1024(256F), M(2.0F), 0, i18n("main.PlayTrack"));
            com.maddox.il2.gui.GUILookAndFeel guilookandfeel = (com.maddox.il2.gui.GUILookAndFeel)lookAndFeel();
            guilookandfeel.drawBevel(this, x1024(152F), y1024(560F) - M(1.0F), x1024(128F), y1024(16F) + M(2.0F), guilookandfeel.bevelRed, guilookandfeel.basicelements);
            draw(x1024(168F), y1024(568F) - M(1.0F), x1024(112F), M(2.0F), 0, i18n("main.Quit"));
            guilookandfeel.drawBevel(this, x1024(416F), y1024(40F), x1024(264F), y1024(64F), guilookandfeel.bevelBlacked, guilookandfeel.basicelements);
            setCanvasFont(1);
            draw(x1024(416F), y1024(40F), x1024(272F), y1024(64F), 1, i18n("main.PilotSelector"));
            setCanvasFont(0);
            draw(x1024(416F), y1024(148F) - M(1.0F), x1024(200F), M(2.0F), 2, i18n("main.Pilot"));
            draw(x1024(416F), y1024(288F) - M(1.0F), x1024(200F), M(2.0F), 2, i18n("main.Controls"));
            draw(x1024(416F), y1024(376F) - M(1.0F), x1024(200F), M(2.0F), 2, i18n("main.ViewObjects"));
            draw(x1024(416F), y1024(457F) - M(1.0F), x1024(200F), M(2.0F), 2, i18n("main.Credits"));
            draw(x1024(416F), y1024(568F) - M(1.0F), x1024(200F), M(2.0F), 2, i18n("main.Setup"));
        }

        public void setPosSize()
        {
            set1024PosSize(144F, 80F, 720F, 624F);
            bSingle.setPosC(x1024(56F), y1024(64F));
            bCampaigns.setPosC(x1024(56F), y1024(120F));
            bMultiplay.setPosC(x1024(56F), y1024(176F));
            bQuick.setPosC(x1024(56F), y1024(232F));
            bBuilder.setPosC(x1024(56F), y1024(288F));
            bTraining.setPosC(x1024(56F), y1024(376F));
            bRecord.setPosC(x1024(56F), y1024(457F));
            bExit.setPosC(x1024(120F), y1024(568F));
            bPilot.setPosC(x1024(664F), y1024(148F));
            pPilotName.setPosSize(x1024(424F), y1024(204F), x1024(264F), y1024(32F));
            bControls.setPosC(x1024(664F), y1024(288F));
            bInfo.setPosC(x1024(664F), y1024(376F));
            bCredits.setPosC(x1024(664F), y1024(457F));
            bSetup.setPosC(x1024(664F), y1024(568F));
        }


        public DialogClient()
        {
        }
    }


    public void enterPop(com.maddox.il2.game.GameState gamestate)
    {
        if(com.maddox.il2.net.USGS.isUsing())
        {
            com.maddox.il2.game.Main.doGameExit();
            return;
        }
        if(com.maddox.il2.game.Main.cur().netGameSpy != null)
        {
            com.maddox.il2.game.Main.cur().netGameSpy.sendExiting();
            com.maddox.il2.game.Main.doGameExit();
            return;
        } else
        {
            _enter();
            return;
        }
    }

    public void _enter()
    {
        ((com.maddox.il2.gui.GUIRoot)dialogClient.root).setBackCountry(null, null);
        client.activateWindow();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    public GUIMainMenu(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(2);
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("main.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bCampaigns = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bSingle = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bQuick = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bMultiplay = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bBuilder = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bTraining = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 192F, 48F, 48F));
        bRecord = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bPilot = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bControls = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bInfo = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bCredits = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bSetup = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        setup = com.maddox.il2.gui.WindowPreferences.create(gwindowroot);
        setup.hideWindow();
        texReg12B = new GTexRegion("GUI/game/staticelements.mat", 128F, 32F, 96F, 48F);
        pPilotName = new GUIPocket(dialogClient, "Demo 'demo' Player");
        dialogClient.activateWindow();
        client.hideWindow();
    }

    com.maddox.gwindow.GWindowFramed setup;
    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.gwindow.GTexRegion texReg12B;
    public com.maddox.il2.gui.GUIPocket pPilotName;
    public com.maddox.il2.gui.GUIButton bInfo;
    public com.maddox.il2.gui.GUIButton bPilot;
    public com.maddox.il2.gui.GUIButton bControls;
    public com.maddox.il2.gui.GUIButton bSingle;
    public com.maddox.il2.gui.GUIButton bCampaigns;
    public com.maddox.il2.gui.GUIButton bQuick;
    public com.maddox.il2.gui.GUIButton bTraining;
    public com.maddox.il2.gui.GUIButton bRecord;
    public com.maddox.il2.gui.GUIButton bBuilder;
    public com.maddox.il2.gui.GUIButton bMultiplay;
    public com.maddox.il2.gui.GUIButton bSetup;
    public com.maddox.il2.gui.GUIButton bCredits;
    public com.maddox.il2.gui.GUIButton bExit;
}

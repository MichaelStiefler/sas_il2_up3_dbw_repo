// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUISetup3D.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.rts.CfgFlags;
import com.maddox.rts.CfgInt;
import com.maddox.rts.CfgTools;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUISwitch7, 
//            GUISwitchN, GUISwitch3, GUISwitchBox, GUILookAndFeel, 
//            GUIButton, GUIDialogClient, GUISeparate

public class GUISetup3D extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bExit)
            {
                com.maddox.il2.game.Main.stateStack().pop();
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(48F), y1024(52F), x1024(112F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(384F), y1024(52F), x1024(224F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(832F), y1024(52F), x1024(112F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(272F), y1024(308F), x1024(224F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(48F), y1024(453F), x1024(224F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(688F), y1024(420F), x1024(256F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(336F), y1024(476F), x1024(48F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(608F), y1024(476F), x1024(32F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(272F), y1024(532F), x1024(224F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(48F), y1024(52F), 2.0F, y1024(400F));
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(272F), y1024(308F), 2.0F, y1024(224F));
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(496F), y1024(52F), 2.0F, y1024(258F));
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(336F), y1024(444F), 2.0F, y1024(34F));
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(496F), y1024(500F), 2.0F, y1024(34F));
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(640F), y1024(444F), 2.0F, y1024(34F));
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(943F), y1024(52F), 2.0F, y1024(370F));
            draw(x1024(176F), y1024(52F) - M(1.0F), x1024(192F), M(2.0F), 1, i18n("setup3d.TextureDetail"));
            draw(x1024(624F), y1024(52F) - M(1.0F), x1024(192F), M(2.0F), 1, i18n("setup3d.Light"));
            setCanvasColorWHITE();
            draw(x1024(128F), y1024(84F), x1024(64F), y1024(64F), tex4);
            draw(x1024(128F), y1024(228F), x1024(64F), y1024(64F), tex4);
            draw(x1024(128F), y1024(340F), x1024(64F), y1024(64F), tex4);
            draw(x1024(352F), y1024(84F), x1024(64F), y1024(64F), tex3);
            draw(x1024(352F), y1024(180F), x1024(64F), y1024(64F), tex3);
            draw(x1024(352F), y1024(340F), x1024(64F), y1024(64F), tex4);
            draw(x1024(576F), y1024(84F), x1024(64F), y1024(64F), tex3);
            draw(x1024(576F), y1024(192F), x1024(64F), y1024(64F), tex3);
            draw(x1024(560F), y1024(340F), x1024(64F), y1024(64F), tex4);
            draw(x1024(800F), y1024(196F), x1024(64F), y1024(64F), tex4);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            draw(x1024(96F), y1024(164F) - M(1.0F), x1024(128F), M(2.0F), 1, i18n("setup3d.TextureQuality"));
            draw(x1024(320F), y1024(164F) - M(1.0F), x1024(128F), M(2.0F), 1, i18n("setup3d.Forest"));
            draw(x1024(96F), y1024(308F) - M(1.0F), x1024(128F), M(2.0F), 1, i18n("setup3d.TextureFilter"));
            draw(x1024(320F), y1024(260F) - M(1.0F), x1024(128F), M(2.0F), 1, i18n("setup3d.Landscape"));
            draw(x1024(96F), y1024(420F) - M(1.0F), x1024(128F), M(2.0F), 1, i18n("setup3d.Sky"));
            draw(x1024(320F), y1024(420F) - M(1.0F), x1024(128F), M(2.0F), 1, i18n("setup3d.LandLighting"));
            draw(x1024(544F), y1024(164F) - M(1.0F), x1024(128F), M(2.0F), 1, i18n("setup3d.Diffuse"));
            draw(x1024(544F), y1024(276F) - M(1.0F), x1024(128F), M(2.0F), 1, i18n("setup3d.SpecularLight"));
            draw(x1024(768F), y1024(276F) - M(1.0F), x1024(128F), M(2.0F), 1, i18n("setup3d.Specular"));
            draw(x1024(528F), y1024(420F) - M(1.0F), x1024(128F), M(2.0F), 1, i18n("setup3d.Shadows"));
            draw(x1024(735F), y1024(128F), x1024(112F), y1024(32F), 2, i18n("setup3d.Dynamic"));
            draw(x1024(464F), y1024(464F), x1024(112F), y1024(32F), 2, i18n("setup3d.Smooth"));
            draw(x1024(112F), y1024(508F), x1024(208F), y1024(32F), 0, i18n("setup3d.Back"));
        }

        public void setPosSize()
        {
            set1024PosSize(16F, 124F, 992F, 580F);
            sTexQual.setPosC(x1024(160F), y1024(116F));
            sTexMip.setPosC(x1024(160F), y1024(260F));
            sTexSky.setPosC(x1024(160F), y1024(372F));
            sForest.setPosC(x1024(384F), y1024(116F));
            sLandDetails.setPosC(x1024(384F), y1024(212F));
            sLandShading.setPosC(x1024(384F), y1024(372F));
            sDiffuseLight.setPosC(x1024(608F), y1024(116F));
            sSpecularLight.setPosC(x1024(608F), y1024(228F));
            sShadows.setPosC(x1024(592F), y1024(372F));
            sSpecular.setPosC(x1024(832F), y1024(228F));
            sDynamicalLights.setPosC(x1024(886F), y1024(132F));
            sSmothBorder.setPosC(x1024(456F), y1024(476F));
            bExit.setPosC(x1024(64F), y1024(524F));
        }

        public DialogClient()
        {
        }
    }

    public class SwitchSky extends com.maddox.il2.gui.GUISwitch7
    {

        public void refresh()
        {
            super.refresh();
            if(com.maddox.il2.game.Mission.isNet())
                sTexSky.enable[0] = !com.maddox.il2.ai.World.cur().diffCur.Clouds;
        }

        public SwitchSky(com.maddox.gwindow.GWindow gwindow, int ai[], com.maddox.rts.CfgInt cfgint, boolean flag)
        {
            super(gwindow, ai, cfgint, flag);
        }
    }


    public void _enter()
    {
        sTexSky.refresh();
        client.activateWindow();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    public GUISetup3D(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(11);
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("setup3d.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        sTexQual = (com.maddox.il2.gui.GUISwitchN)dialogClient.addControl(new GUISwitch7(dialogClient, new int[] {
            0, 2, 4, 6
        }, (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get("TexQual"), true));
        sTexMip = (com.maddox.il2.gui.GUISwitchN)dialogClient.addControl(new GUISwitch7(dialogClient, new int[] {
            0, 2, 4, 6
        }, (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get("TexMipFilter"), true));
        sTexSky = (com.maddox.il2.gui.SwitchSky)dialogClient.addControl(new SwitchSky(dialogClient, new int[] {
            0, 2, 4, 6
        }, (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get("Sky"), true));
        sForest = (com.maddox.il2.gui.GUISwitchN)dialogClient.addControl(new GUISwitch7(dialogClient, new int[] {
            0, 3, 6
        }, (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get("Forest"), true));
        sLandDetails = (com.maddox.il2.gui.GUISwitchN)dialogClient.addControl(new GUISwitch7(dialogClient, new int[] {
            0, 3, 6
        }, (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get("LandDetails"), true));
        sLandShading = (com.maddox.il2.gui.GUISwitchN)dialogClient.addControl(new GUISwitch7(dialogClient, new int[] {
            0, 2, 4, 6
        }, (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get("LandShading"), true));
        sDiffuseLight = (com.maddox.il2.gui.GUISwitchN)dialogClient.addControl(new GUISwitch7(dialogClient, new int[] {
            0, 3, 6
        }, (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get("DiffuseLight"), true));
        sSpecularLight = (com.maddox.il2.gui.GUISwitchN)dialogClient.addControl(new GUISwitch7(dialogClient, new int[] {
            0, 3, 6
        }, (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get("SpecularLight"), true));
        sShadows = (com.maddox.il2.gui.GUISwitchN)dialogClient.addControl(new GUISwitch7(dialogClient, new int[] {
            0, 2, 4, 6
        }, (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get("Shadows"), true));
        sSpecular = (com.maddox.il2.gui.GUISwitchN)dialogClient.addControl(new GUISwitch7(dialogClient, new int[] {
            0, 2, 4, 6
        }, (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get("Specular"), true));
        sDynamicalLights = (com.maddox.il2.gui.GUISwitchN)dialogClient.addControl(new GUISwitch3(dialogClient, new int[] {
            0, 2
        }, (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get("DynamicalLights"), true));
        sSmothBorder = (com.maddox.il2.gui.GUISwitchBox)dialogClient.addControl(new GUISwitchBox(dialogClient, (com.maddox.rts.CfgFlags)com.maddox.rts.CfgTools.get("ShadowsFlags"), 0, true));
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        tex3 = new GTexRegion("GUI/game/staticelements.mat", 64F, 48F, 64F, 64F);
        tex4 = new GTexRegion("GUI/game/staticelements.mat", 0.0F, 48F, 64F, 64F);
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUISwitchN sTexQual;
    public com.maddox.il2.gui.GUISwitchN sTexMip;
    public com.maddox.il2.gui.SwitchSky sTexSky;
    public com.maddox.il2.gui.GUISwitchN sForest;
    public com.maddox.il2.gui.GUISwitchN sLandDetails;
    public com.maddox.il2.gui.GUISwitchN sLandShading;
    public com.maddox.il2.gui.GUISwitchN sDiffuseLight;
    public com.maddox.il2.gui.GUISwitchN sSpecularLight;
    public com.maddox.il2.gui.GUISwitchN sShadows;
    public com.maddox.il2.gui.GUISwitchN sSpecular;
    public com.maddox.il2.gui.GUISwitchN sDynamicalLights;
    public com.maddox.il2.gui.GUISwitchBox sSmothBorder;
    public com.maddox.il2.gui.GUIButton bExit;
    public com.maddox.gwindow.GTexRegion tex3;
    public com.maddox.gwindow.GTexRegion tex4;
}

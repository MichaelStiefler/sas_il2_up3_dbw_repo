// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIDGenPilot.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowScrollingDialogClient;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.BmpUtils;
import com.maddox.il2.engine.GUITexture;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.campaign.Campaign;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.gui:
//            GUIDGenRoster, GUIClient, GUIInfoMenu, GUIInfoName, 
//            GUILookAndFeel, GUIButton, GUIDialogClient, GUISeparate, 
//            GUIAwards

public class GUIDGenPilot extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bBack)
            {
                com.maddox.il2.game.Main.stateStack().pop();
                return true;
            }
            if(gwindow == bDetailProfile)
            {
                if(pilot.bPlayer)
                    com.maddox.il2.game.Main.stateStack().push(67);
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
            draw(x1024(96F), y1024(656F), x1024(288F), y1024(48F), 0, i18n("camps.Back"));
            if(bDetailProfile.isVisible())
                draw(x1024(464F), y1024(656F), x1024(460F), y1024(48F), 2, i18n("dgenpilot.Detail"));
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(624F), x1024(960F), 2.0F);
        }

        public void setPosSize()
        {
            set1024PosSize(0.0F, 32F, 1024F, 736F);
            bBack.setPosC(x1024(56F), y1024(680F));
            bDetailProfile.setPosC(x1024(968F), y1024(680F));
            wScroll.set1024PosSize(32F, 32F, 962F, 560F);
            bViewAward.set1024PosSize(32F, 304F, 192F, 164F);
        }

        public DialogClient()
        {
        }
    }

    public class Scroll extends com.maddox.gwindow.GWindowScrollingDialogClient
    {

        public void created()
        {
            fixed = wInfo = (com.maddox.il2.gui.Info)create(new Info());
            fixed.bNotify = true;
            bNotify = true;
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

        public void resized()
        {
            if(wInfo != null)
                wInfo.computeSize();
            super.resized();
            if(vScroll.isVisible())
            {
                com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
                vScroll.setPos(win.dx - lookAndFeel().getVScrollBarW() - gbevel.R.dx, gbevel.T.dy);
                vScroll.setSize(lookAndFeel().getVScrollBarW(), win.dy - gbevel.T.dy - gbevel.B.dy);
            }
        }

        public void render()
        {
            setCanvasColorWHITE();
            com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
            lookAndFeel().drawBevel(this, 0.0F, 0.0F, win.dx, win.dy, gbevel, ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).basicelements, true);
        }

        public Scroll()
        {
        }
    }

    public class Info extends com.maddox.gwindow.GWindowDialogClient
    {

        public void afterCreated()
        {
            super.afterCreated();
            fnt = com.maddox.gwindow.GFont.New("courSmall");
        }

        public void render()
        {
            if(pilot == null)
                return;
            clipRegion.set(root.C.clip);
            pushClip();
            com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
            clipRegion.y += gbevel.T.dy + 2.0F;
            clipRegion.dy -= gbevel.T.dy + gbevel.B.dy + 4F;
            root.C.clip.set(clipRegion);
            if(bMatPhotoValid)
            {
                int i = root.C.alpha;
                root.C.alpha = 255;
                setCanvasColorWHITE();
                draw(x1024(32F), y1024(32F), x1024(192F), y1024(256F), matPhoto, 0.0F, 0.0F, 192F, 256F);
                root.C.alpha = i;
            }
            com.maddox.il2.gui.GUISeparate.draw(this, myBrass, x1024(416F), y1024(128F), x1024(480F), 1.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, myBrass, x1024(416F), y1024(160F), x1024(480F), 1.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, myBrass, x1024(416F), y1024(192F), x1024(224F), 1.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, myBrass, x1024(800F), y1024(192F), x1024(96F), 1.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, myBrass, x1024(416F), y1024(224F), x1024(480F), 1.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, myBrass, x1024(416F), y1024(256F), x1024(112F), 1.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, myBrass, x1024(416F), y1024(288F), x1024(112F), 1.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, myBrass, x1024(416F), y1024(320F), x1024(112F), 1.0F);
            setCanvasColorBLACK();
            root.C.font = fnt;
            draw(x1024(272F), y1024(112F), x1024(128F), y1024(16F), 2, i18n("dgenpilot.Name"));
            draw(x1024(272F), y1024(144F), x1024(128F), y1024(16F), 2, i18n("dgenpilot.Surname"));
            draw(x1024(272F), y1024(176F), x1024(128F), y1024(16F), 2, i18n("dgenpilot.Rank"));
            draw(x1024(272F), y1024(208F), x1024(128F), y1024(16F), 2, i18n("dgenpilot.Place"));
            draw(x1024(272F), y1024(240F), x1024(128F), y1024(16F), 2, i18n("dgenpilot.Sorties"));
            draw(x1024(272F), y1024(272F), x1024(128F), y1024(16F), 2, i18n("dgenpilot.Kills"));
            draw(x1024(272F), y1024(304F), x1024(128F), y1024(16F), 2, i18n("dgenpilot.GroundKills"));
            draw(x1024(648F), y1024(176F), x1024(144F), y1024(16F), 2, i18n("dgenpilot.Year"));
            setCanvasFont(0);
            draw(x1024(400F), y1024(16F), x1024(368F), y1024(32F), 1, i18n("dgenpilot.Title"));
            draw(x1024(416F), y1024(100F), x1024(480F), y1024(32F), 0, pilot.firstName);
            draw(x1024(416F), y1024(132F), x1024(480F), y1024(32F), 0, pilot.lastName);
            draw(x1024(416F), y1024(164F), x1024(224F), y1024(32F), 0, "" + pilot.sRank);
            draw(x1024(800F), y1024(164F), x1024(96F), y1024(32F), 0, pilot.dateBirth);
            draw(x1024(416F), y1024(196F), x1024(480F), y1024(32F), 0, pilot.placeBirth);
            draw(x1024(416F), y1024(228F), x1024(112F), y1024(32F), 0, "" + pilot.sorties);
            draw(x1024(416F), y1024(260F), x1024(112F), y1024(32F), 0, "" + pilot.kills);
            draw(x1024(416F), y1024(292F), x1024(112F), y1024(32F), 0, "" + pilot.ground);
            int j = (int)y1024(352F);
            for(int k = 0; k < pilot.events.size(); k++)
            {
                java.lang.String s = (java.lang.String)pilot.events.get(k);
                int l = drawLines(x1024(288F), j, s, 0, s.length(), x1024(590F), root.C.font.height - root.C.font.descender);
                j = (int)((float)j + (float)l * (root.C.font.height - root.C.font.descender));
            }

            popClip();
        }

        public void doComputeDy()
        {
            dy = (int)y1024(352F);
            setCanvasFont(0);
            for(int i = 0; i < pilot.events.size(); i++)
            {
                java.lang.String s = (java.lang.String)pilot.events.get(i);
                int j = computeLines(s, 0, s.length(), x1024(590F));
                dy += (float)j * (root.C.font.height - root.C.font.descender);
            }

            dy -= 2.0F * root.C.font.descender;
            computeSize();
        }

        public void computeSize()
        {
            win.dx = parentWindow.win.dx - lookAndFeel().getVScrollBarW();
            win.dy = parentWindow.win.dy;
            if((float)dy > win.dy)
                win.dy = dy;
        }

        private com.maddox.gwindow.GFont fnt;
        private com.maddox.gwindow.GColor myBrass;
        private com.maddox.gwindow.GRegion clipRegion;
        private int dy;

        public Info()
        {
            myBrass = new GColor(99, 89, 74);
            clipRegion = new GRegion();
            dy = 0;
        }
    }

    class WAwardButton extends com.maddox.gwindow.GWindowButton
    {

        public boolean notify(int i, int j)
        {
            if(i != 2)
            {
                return super.notify(i, j);
            } else
            {
                com.maddox.il2.gui.GUIAwards.indexIcons = pilot.medals;
                com.maddox.il2.game.Main.stateStack().push(32);
                return true;
            }
        }

        public void render()
        {
            super.render();
            if(lastAward != null)
            {
                setCanvasColorWHITE();
                int i = root.C.alpha;
                root.C.alpha = 255;
                if(bDown)
                    draw(win.dx / 5F + 1.0F, win.dy / 5F + 1.0F, (3F * win.dx) / 5F, (3F * win.dy) / 5F, lastAward);
                else
                    draw(win.dx / 5F, win.dy / 5F, (3F * win.dx) / 5F, (3F * win.dy) / 5F, lastAward);
                root.C.alpha = i;
            }
        }

        public WAwardButton(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
        }
    }


    public void enterPush(com.maddox.il2.game.GameState gamestate)
    {
        pilot = ((com.maddox.il2.gui.GUIDGenRoster)gamestate).pilotCur;
        if(pilot.bPlayer)
            bDetailProfile.showWindow();
        else
            bDetailProfile.hideWindow();
        wInfo.doComputeDy();
        wScroll.resized();
        java.lang.String s = pilot.photo;
        if(s != null && !s.endsWith(".bmp"))
            s = s + ".bmp";
        if(s != null && com.maddox.il2.engine.BmpUtils.bmp8Pal192x256ToTGA3(s, "PaintSchemes/Cache/photo.tga"))
        {
            matPhoto.mat.set('\0', "PaintSchemes/Cache/photo.tga");
            bMatPhotoValid = true;
        } else
        {
            bMatPhotoValid = false;
        }
        lastAward = null;
        if(pilot.medals != null)
        {
            int i = pilot.medals.length - 1;
            if(com.maddox.il2.game.Main.cur().campaign.branch().equals("de") && com.maddox.il2.ai.World.cur().isHakenAllowed())
                lastAward = com.maddox.gwindow.GTexture.New("missions/campaign/de/awardh" + pilot.medals[i] + ".mat");
            else
            if(com.maddox.il2.game.Main.cur().campaign.branch().equals("fi") && com.maddox.il2.ai.World.cur().isHakenAllowed())
                lastAward = com.maddox.gwindow.GTexture.New("missions/campaign/fi/awardh" + pilot.medals[i] + ".mat");
            else
                lastAward = com.maddox.gwindow.GTexture.New("missions/campaign/" + com.maddox.il2.game.Main.cur().campaign.branch() + "/award" + pilot.medals[i] + ".mat");
            bViewAward.showWindow();
        } else
        {
            bViewAward.hideWindow();
        }
        client.activateWindow();
    }

    public void enterPop(com.maddox.il2.game.GameState gamestate)
    {
        client.activateWindow();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    public GUIDGenPilot(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(66);
        pilot = null;
        bMatPhotoValid = false;
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("dgenpilot.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        matPhoto = (com.maddox.il2.engine.GUITexture)com.maddox.il2.engine.GUITexture.New("gui/game/photo.mat");
        matPhoto.mat.setLayer(0);
        dialogClient.create(wScroll = new Scroll());
        bViewAward = (com.maddox.il2.gui.WAwardButton)wInfo.addControl(new WAwardButton(wInfo));
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bBack = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bDetailProfile = (com.maddox.il2.gui.GUIButton)dialogClient.addDefault(new GUIButton(dialogClient, gtexture, 0.0F, 192F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIDGenRoster.Pilot pilot;
    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.Scroll wScroll;
    public com.maddox.il2.gui.Info wInfo;
    public com.maddox.il2.gui.WAwardButton bViewAward;
    public com.maddox.il2.gui.GUIButton bBack;
    public com.maddox.il2.gui.GUIButton bDetailProfile;
    private com.maddox.il2.engine.GUITexture matPhoto;
    private boolean bMatPhotoValid;
    private com.maddox.gwindow.GTexture lastAward;



}

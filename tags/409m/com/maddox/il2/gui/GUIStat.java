// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIStat.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowScrollingDialogClient;
import com.maddox.il2.ai.Scores;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetMissionTrack;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUIDialogClient

public class GUIStat extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bSave)
            {
                doRecordSave();
                return true;
            }
            if(gwindow == bRefly)
            {
                doRefly();
                return true;
            }
            if(gwindow == bNext)
            {
                doNext();
                return true;
            }
            if(gwindow == bExit)
            {
                doExit();
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            clientRender();
        }

        public void setPosSize()
        {
            clientSetPosSize();
        }

        public DialogClient()
        {
        }
    }

    public class ScrollClient extends com.maddox.gwindow.GWindowScrollingDialogClient
    {

        public void render()
        {
            com.maddox.il2.gui.GUILookAndFeel guilookandfeel = (com.maddox.il2.gui.GUILookAndFeel)lookAndFeel();
            setCanvasColorWHITE();
            guilookandfeel.drawBevel(this, 0.0F, 0.0F, win.dx, win.dy, guilookandfeel.bevelComboDown, guilookandfeel.basicelements);
        }

        public void doChildrensRender(boolean flag)
        {
            com.maddox.il2.gui.GUILookAndFeel guilookandfeel = (com.maddox.il2.gui.GUILookAndFeel)lookAndFeel();
            _clipReg.x = guilookandfeel.bevelComboDown.L.dx;
            _clipReg.y = guilookandfeel.bevelComboDown.T.dy;
            _clipReg.dx = win.dx - guilookandfeel.bevelComboDown.L.dx - guilookandfeel.bevelComboDown.R.dx;
            _clipReg.dy = win.dy - guilookandfeel.bevelComboDown.T.dy - guilookandfeel.bevelComboDown.B.dy;
            if(pushClipRegion(_clipReg))
            {
                super.doChildrensRender(flag);
                popClip();
            }
        }

        public ScrollClient(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
        }
    }

    public class FixedClient extends com.maddox.gwindow.GWindowDialogClient
    {

        public void draw(boolean flag)
        {
            setCanvasColorWHITE();
            float f = 16F;
            float f1 = 8F;
            float f2 = 16F;
            float f3 = f;
            float f4 = f2;
            if(bAir)
            {
                int i = com.maddox.il2.ai.Scores.enemyAirKill;
                int k = 0;
                while(i >= 100) 
                {
                    i -= 100;
                    if(flag)
                        draw(x1024(f3), y1024(f4), x1024(48F), x1024(48F), texAir[iArmy][2]);
                    if(i > 0)
                        if(++k == 10)
                        {
                            f3 = f;
                            f4 += f2 + 48F;
                            k = 0;
                        } else
                        {
                            f3 += f1 + 48F;
                        }
                }
                while(i >= 10) 
                {
                    i -= 10;
                    if(flag)
                        draw(x1024(f3), y1024(f4), x1024(48F), x1024(48F), texAir[iArmy][1]);
                    if(i > 0)
                        if(++k == 10)
                        {
                            f3 = f;
                            f4 += f2 + 48F;
                            k = 0;
                        } else
                        {
                            f3 += f1 + 48F;
                        }
                }
                while(i > 0) 
                {
                    i--;
                    if(flag)
                        draw(x1024(f3), y1024(f4), x1024(48F), y1024(48F), texAir[iArmy][0]);
                    if(i > 0)
                        if(++k == 10)
                        {
                            f3 = f;
                            f4 += f2 + 48F;
                            k = 0;
                        } else
                        {
                            f3 += f1 + 48F;
                        }
                }
            } else
            {
                int j = com.maddox.il2.ai.Scores.enemyGroundKill;
                int l = 0;
                if(com.maddox.il2.ai.Scores.arrayEnemyGroundKill != null)
                {
                    for(int i1 = 0; i1 < com.maddox.il2.ai.Scores.arrayEnemyGroundKill.length; i1++)
                    {
                        com.maddox.gwindow.GTexRegion gtexregion = null;
                        switch(com.maddox.il2.ai.Scores.arrayEnemyGroundKill[i1])
                        {
                        case 1: // '\001'
                            gtexregion = texTank;
                            break;

                        case 2: // '\002'
                            gtexregion = texCar;
                            break;

                        case 3: // '\003'
                            gtexregion = texArtillery;
                            break;

                        case 4: // '\004'
                            gtexregion = texAAA;
                            break;

                        case 6: // '\006'
                            gtexregion = texTrain;
                            break;

                        case 7: // '\007'
                            gtexregion = texShip;
                            break;

                        case 5: // '\005'
                            gtexregion = texBridge;
                            break;

                        case 8: // '\b'
                            gtexregion = texAirStatic;
                            break;
                        }
                        if(gtexregion != null)
                        {
                            j--;
                            if(flag)
                                draw(x1024(f3), y1024(f4), x1024(48F), y1024(32F), gtexregion);
                            if(j > 0)
                                if(++l == 10)
                                {
                                    f3 = f;
                                    f4 += f2 + 32F;
                                    l = 0;
                                } else
                                {
                                    f3 += f1 + 48F;
                                }
                        }
                    }

                }
            }
            if(!flag)
            {
                if(bAir)
                    setSize(x1024(f + 480F + 9F * f1), y1024(f4 + 48F + f2));
                else
                    setSize(x1024(f + 480F + 9F * f1), y1024(f4 + 32F + f2));
                parentWindow.resized();
            }
        }

        public void render()
        {
            draw(true);
        }

        boolean bAir;

        public FixedClient(boolean flag)
        {
            bAir = flag;
        }
    }


    protected void updateScrollSizes()
    {
        airFixed.draw(false);
        airScroll.updateScrollsPos();
        groundFixed.draw(false);
        groundScroll.updateScrollsPos();
    }

    public void _enter()
    {
        com.maddox.il2.ai.Scores.compute();
        updateScrollSizes();
        if(com.maddox.il2.net.NetMissionTrack.countRecorded == 0)
            bSave.showWindow();
        else
            bSave.hideWindow();
        client.activateWindow();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    protected void tryShowCapturedMessage()
    {
        if(!com.maddox.il2.ai.World.isPlayerCaptured())
        {
            return;
        } else
        {
            new GWindowMessageBox(client.root, 20F, true, i18n("warning.Warning"), i18n("warning.PlayerCaptured"), 3, -1F);
            return;
        }
    }

    protected void doRecordSave()
    {
        com.maddox.il2.game.Main.stateStack().push(9);
    }

    protected void doRefly()
    {
    }

    protected void doNext()
    {
    }

    protected void doExit()
    {
    }

    protected void clientRender()
    {
    }

    protected void clientSetPosSize()
    {
    }

    protected void init(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = "Statistic";
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        texStat = new GTexRegion("GUI/game/staticelements.mat", 0.0F, 112F, 64F, 80F);
        airScroll = new ScrollClient(dialogClient);
        airScroll.fixed = (com.maddox.gwindow.GWindowDialogClient)airScroll.create(airFixed = new FixedClient(true));
        groundScroll = new ScrollClient(dialogClient);
        groundScroll.fixed = (com.maddox.gwindow.GWindowDialogClient)groundScroll.create(groundFixed = new FixedClient(false));
        bSave = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bRefly = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bNext = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 192F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
        gtexture = com.maddox.gwindow.GTexture.New("GUI/game/score.mat");
        texAir[0][0] = new GTexRegion(gtexture, 0.0F, 0.0F, 48F, 48F);
        texAir[0][1] = new GTexRegion(gtexture, 48F, 0.0F, 48F, 48F);
        texAir[0][2] = new GTexRegion(gtexture, 96F, 0.0F, 48F, 48F);
        texAir[1][0] = new GTexRegion(gtexture, 0.0F, 48F, 48F, 48F);
        texAir[1][1] = new GTexRegion(gtexture, 48F, 48F, 48F, 48F);
        texAir[1][2] = new GTexRegion(gtexture, 96F, 48F, 48F, 48F);
        texTrain = new GTexRegion(gtexture, 208F, 0.0F, 48F, 32F);
        texAAA = new GTexRegion(gtexture, 208F, 32F, 48F, 32F);
        texCar = new GTexRegion(gtexture, 208F, 64F, 48F, 32F);
        texTank = new GTexRegion(gtexture, 208F, 96F, 48F, 32F);
        texSub = new GTexRegion(gtexture, 208F, 128F, 48F, 32F);
        texShip = new GTexRegion(gtexture, 208F, 160F, 48F, 32F);
        texArtillery = new GTexRegion(gtexture, 208F, 192F, 48F, 32F);
        texBridge = new GTexRegion(gtexture, 208F, 224F, 48F, 32F);
        texAirStatic = new GTexRegion(gtexture, 160F, 224F, 48F, 32F);
    }

    public GUIStat(int i)
    {
        super(i);
        iArmy = 0;
        texAir = new com.maddox.gwindow.GTexRegion[2][3];
        _clipReg = new GRegion();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton bSave;
    public com.maddox.il2.gui.GUIButton bNext;
    public com.maddox.il2.gui.GUIButton bRefly;
    public com.maddox.il2.gui.GUIButton bExit;
    com.maddox.gwindow.GTexRegion texStat;
    public com.maddox.il2.gui.FixedClient airFixed;
    public com.maddox.il2.gui.FixedClient groundFixed;
    public com.maddox.il2.gui.ScrollClient airScroll;
    public com.maddox.il2.gui.ScrollClient groundScroll;
    public int iArmy;
    com.maddox.gwindow.GTexRegion texAir[][];
    com.maddox.gwindow.GTexRegion texTank;
    com.maddox.gwindow.GTexRegion texCar;
    com.maddox.gwindow.GTexRegion texAAA;
    com.maddox.gwindow.GTexRegion texTrain;
    com.maddox.gwindow.GTexRegion texSub;
    com.maddox.gwindow.GTexRegion texShip;
    com.maddox.gwindow.GTexRegion texArtillery;
    com.maddox.gwindow.GTexRegion texBridge;
    com.maddox.gwindow.GTexRegion texAirStatic;
    private com.maddox.gwindow.GRegion _clipReg;

}

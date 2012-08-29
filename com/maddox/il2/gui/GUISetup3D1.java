// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUISetup3D1.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.CfgGObj;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.RendersMain;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.rts.Cfg;
import com.maddox.rts.CfgFlags;
import com.maddox.rts.CfgInt;
import com.maddox.rts.CfgTools;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUIDialogClient, GUISeparate

public class GUISetup3D1 extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bApply)
            {
                int k = 0;
                if(bCommon)
                {
                    k |= cCommon.apply();
                } else
                {
                    k |= cVisible.apply();
                    k |= cObjLight.apply();
                    k |= cObjDetail.apply();
                    k |= cLandLight.apply();
                    k |= cLandDetail.apply();
                    k |= cSky.apply();
                }
                com.maddox.il2.engine.CfgGObj.ApplyExtends(k);
                cCommon.reset();
                return true;
            }
            if(gwindow == bCustom)
            {
                bCommon = !bCommon;
                cCommon.reset();
                setPosSize();
                return true;
            }
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
            if(bCommon)
            {
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(104F), x1024(416F), 2.0F);
                setCanvasFont(0);
                setCanvasColor(com.maddox.gwindow.GColor.Gray);
                draw(x1024(48F), y1024(24F), x1024(384F), y1024(32F), 1, i18n("setup3d.Common"));
                draw(x1024(80F), y1024(176F), x1024(160F), y1024(48F), 0, i18n("setup3d.Back"));
                draw(x1024(144F), y1024(120F), x1024(256F), y1024(48F), 2, i18n("setup3d.Custom"));
                draw(x1024(240F), y1024(176F), x1024(160F), y1024(48F), 2, i18n("setup3d.Apply"));
            } else
            {
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(504F), x1024(416F), 2.0F);
                setCanvasFont(0);
                setCanvasColor(com.maddox.gwindow.GColor.Gray);
                draw(x1024(48F), y1024(24F), x1024(384F), y1024(32F), 1, i18n("setup3d.Visible"));
                draw(x1024(48F), y1024(104F), x1024(384F), y1024(32F), 1, i18n("setup3d.ObjLight"));
                draw(x1024(48F), y1024(184F), x1024(384F), y1024(32F), 1, i18n("setup3d.ObjDetail"));
                draw(x1024(48F), y1024(264F), x1024(384F), y1024(32F), 1, i18n("setup3d.LandLight"));
                draw(x1024(48F), y1024(344F), x1024(384F), y1024(32F), 1, i18n("setup3d.LandDetail"));
                draw(x1024(48F), y1024(424F), x1024(384F), y1024(32F), 1, i18n("setup3d.Sky"));
                draw(x1024(80F), y1024(576F), x1024(160F), y1024(48F), 0, i18n("setup3d.Back"));
                draw(x1024(144F), y1024(520F), x1024(256F), y1024(48F), 2, i18n("setup3d.Simple"));
                draw(x1024(240F), y1024(576F), x1024(160F), y1024(48F), 2, i18n("setup3d.Apply"));
            }
        }

        public void setPosSize()
        {
            if(bCommon)
            {
                cCommon.showWindow();
                cVisible.hideWindow();
                cObjLight.hideWindow();
                cObjDetail.hideWindow();
                cLandLight.hideWindow();
                cLandDetail.hideWindow();
                cSky.hideWindow();
                set1024PosSize(0.0F, 512F, 480F, 256F);
                cCommon.set1024PosSize(80F, 56F, 320F, 32F);
                bExit.setPosC(x1024(56F), y1024(200F));
                bCustom.setPosC(x1024(424F), y1024(144F));
                bApply.setPosC(x1024(424F), y1024(200F));
            } else
            {
                cCommon.hideWindow();
                cVisible.showWindow();
                cObjLight.showWindow();
                cObjDetail.showWindow();
                cLandLight.showWindow();
                cLandDetail.showWindow();
                cSky.showWindow();
                set1024PosSize(0.0F, 112F, 480F, 656F);
                cVisible.set1024PosSize(80F, 56F, 320F, 32F);
                cObjLight.set1024PosSize(80F, 136F, 320F, 32F);
                cObjDetail.set1024PosSize(80F, 216F, 320F, 32F);
                cLandLight.set1024PosSize(80F, 296F, 320F, 32F);
                cLandDetail.set1024PosSize(80F, 376F, 320F, 32F);
                cSky.set1024PosSize(80F, 456F, 320F, 32F);
                bExit.setPosC(x1024(56F), y1024(600F));
                bCustom.setPosC(x1024(424F), y1024(544F));
                bApply.setPosC(x1024(424F), y1024(600F));
            }
        }

        public DialogClient()
        {
        }
    }

    public class ComboCfgSky extends com.maddox.il2.gui.ComboCfg
    {

        public boolean reset()
        {
            boolean flag = super.reset();
            posEnable[0] = !com.maddox.il2.game.Mission.isNet();
            return flag;
        }

        public ComboCfgSky(com.maddox.gwindow.GWindow gwindow, java.lang.Object aobj[], int ai[][], java.lang.String as[])
        {
            super(gwindow, aobj, ai, as);
        }
    }

    public class ComboCfg extends com.maddox.gwindow.GWindowComboControl
    {

        public boolean reset()
        {
            for(int i = 0; i < cfg.length; i++)
                if(cfg[i] instanceof com.maddox.rts.Cfg)
                    ((com.maddox.rts.Cfg)cfg[i]).reset();
                else
                    ((com.maddox.il2.gui.ComboCfg)cfg[i]).reset();

            int j = state.length;
            int k = 0;
            if(posEnable != null)
                for(; k < j; k++)
                {
                    int ai[] = state[k];
                    posEnable[k] = true;
                    for(int l = 0; l < cfg.length; l++)
                        if(cfg[l] instanceof com.maddox.rts.CfgFlags)
                        {
                            com.maddox.rts.CfgFlags cfgflags = (com.maddox.rts.CfgFlags)cfg[l];
                            if(!cfgflags.isEnabledFlag(0))
                                posEnable[k] = false;
                        } else
                        if(cfg[l] instanceof com.maddox.rts.CfgInt)
                        {
                            com.maddox.rts.CfgInt cfgint = (com.maddox.rts.CfgInt)cfg[l];
                            if(!cfgint.isEnabledState(ai[l]))
                                posEnable[k] = false;
                        }

                }

            k = 0;
            boolean flag = true;
            for(; k < j; k++)
            {
                flag = true;
                int ai1[] = state[k];
                for(int i1 = 0; i1 < cfg.length; i1++)
                {
                    if(cfg[i1] instanceof com.maddox.rts.CfgFlags)
                    {
                        com.maddox.rts.CfgFlags cfgflags1 = (com.maddox.rts.CfgFlags)cfg[i1];
                        flag = cfgflags1.get(0) == (ai1[i1] != 0);
                    } else
                    if(cfg[i1] instanceof com.maddox.rts.CfgInt)
                    {
                        com.maddox.rts.CfgInt cfgint1 = (com.maddox.rts.CfgInt)cfg[i1];
                        int j1 = cfgint1.get();
                        int l1;
                        for(l1 = ai1[i1]; l1 > 0 && !cfgint1.isEnabledState(l1); l1--);
                        flag = j1 == l1;
                    } else
                    {
                        com.maddox.il2.gui.ComboCfg combocfg = (com.maddox.il2.gui.ComboCfg)cfg[i1];
                        int k1 = combocfg.getSelected();
                        int i2;
                        for(i2 = ai1[i1]; i2 > 0 && !combocfg.posEnable[i2]; i2--);
                        flag = k1 == i2;
                    }
                    if(!flag)
                        break;
                }

                if(flag)
                    break;
            }

            if(k == j)
                k = j - 1;
            if(posEnable != null)
                for(; k > 0; k--)
                    if(posEnable[k])
                        break;

            setSelected(k, true, false);
            return flag;
        }

        public int apply()
        {
            int i = 0;
            int j = getSelected();
            int ai[] = state[j];
            for(int k = 0; k < cfg.length; k++)
                if(cfg[k] instanceof com.maddox.rts.CfgFlags)
                {
                    com.maddox.rts.CfgFlags cfgflags = (com.maddox.rts.CfgFlags)cfg[k];
                    cfgflags.set(0, ai[k] != 0);
                    i |= cfgflags.apply();
                } else
                if(cfg[k] instanceof com.maddox.rts.CfgInt)
                {
                    com.maddox.rts.CfgInt cfgint = (com.maddox.rts.CfgInt)cfg[k];
                    int l;
                    for(l = ai[k]; l > 0 && !cfgint.isEnabledState(l); l--);
                    cfgint.set(l);
                    i |= cfgint.apply();
                } else
                {
                    com.maddox.il2.gui.ComboCfg combocfg = (com.maddox.il2.gui.ComboCfg)cfg[k];
                    int i1;
                    for(i1 = ai[k]; i1 > 0 && !combocfg.posEnable[i1]; i1--);
                    combocfg.setSelected(i1, true, false);
                    i |= combocfg.apply();
                }

            return i;
        }

        public java.lang.Object cfg[];
        public int state[][];

        public ComboCfg(com.maddox.gwindow.GWindow gwindow, java.lang.Object aobj[], int ai[][], java.lang.String as[])
        {
            super(gwindow, 0.0F, 0.0F, 10F);
            setEditable(false);
            cfg = aobj;
            state = ai;
            for(int i = 0; i < as.length; i++)
                add(as[i]);

            if(!(aobj[0] instanceof com.maddox.il2.gui.ComboCfg))
                posEnable = new boolean[as.length];
            reset();
        }
    }


    public void _enter()
    {
        if(com.maddox.il2.game.Mission.isPlaying() && com.maddox.il2.engine.RendersMain.getRenderFocus() == (com.maddox.il2.engine.Render)com.maddox.il2.engine.Actor.getByName("renderGUI"))
        {
            com.maddox.il2.engine.RendersMain.setRenderFocus(null);
            bRestoreRenderFocus = true;
        } else
        {
            bRestoreRenderFocus = false;
        }
        bCommon = cCommon.reset();
        dialogClient.setPosSize();
        client.activateWindow();
    }

    public void _leave()
    {
        if(bRestoreRenderFocus)
            com.maddox.il2.engine.RendersMain.setRenderFocus((com.maddox.il2.engine.Render)com.maddox.il2.engine.Actor.getByName("renderGUI"));
        client.hideWindow();
    }

    public GUISetup3D1(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(11);
        bRestoreRenderFocus = false;
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("setup3d.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        cVisible = new ComboCfg(dialogClient, new java.lang.Object[] {
            com.maddox.rts.CfgTools.get("VisibilityDistance")
        }, new int[][] {
            new int[] {
                0
            }, new int[] {
                1
            }, new int[] {
                2
            }, new int[] {
                3
            }
        }, new java.lang.String[] {
            i18n("setup3d.Visible0"), i18n("setup3d.Visible1"), i18n("setup3d.Visible2"), i18n("setup3d.Visible3")
        });
        cObjLight = new ComboCfg(dialogClient, new java.lang.Object[] {
            com.maddox.rts.CfgTools.get("DiffuseLight"), com.maddox.rts.CfgTools.get("SpecularLight"), com.maddox.rts.CfgTools.get("DynamicalLights"), com.maddox.rts.CfgTools.get("Specular")
        }, new int[][] {
            new int[] {
                0, 0, 0, 1
            }, new int[] {
                1, 1, 1, 1
            }, new int[] {
                2, 2, 1, 2
            }
        }, new java.lang.String[] {
            i18n("setup3d.ObjLight0"), i18n("setup3d.ObjLight1"), i18n("setup3d.ObjLight2")
        });
        cObjDetail = new ComboCfg(dialogClient, new java.lang.Object[] {
            com.maddox.rts.CfgTools.get("MeshDetail"), com.maddox.rts.CfgTools.get("TexQual"), com.maddox.rts.CfgTools.get("TexLarge")
        }, new int[][] {
            new int[] {
                0, 1, 0
            }, new int[] {
                1, 2, 0
            }, new int[] {
                2, 3, 0
            }, new int[] {
                2, 3, 1
            }
        }, new java.lang.String[] {
            i18n("setup3d.ObjDetail0"), i18n("setup3d.ObjDetail1"), i18n("setup3d.ObjDetail2"), i18n("setup3d.ObjDetail3")
        });
        cLandLight = new ComboCfg(dialogClient, new java.lang.Object[] {
            com.maddox.rts.CfgTools.get("LandShading"), com.maddox.rts.CfgTools.get("Shadows")
        }, new int[][] {
            new int[] {
                0, 0
            }, new int[] {
                1, 1
            }, new int[] {
                2, 1
            }, new int[] {
                3, 2
            }
        }, new java.lang.String[] {
            i18n("setup3d.LandLight0"), i18n("setup3d.LandLight1"), i18n("setup3d.LandLight2"), i18n("setup3d.LandLight3")
        });
        cLandDetail = new ComboCfg(dialogClient, new java.lang.Object[] {
            com.maddox.rts.CfgTools.get("Forest"), com.maddox.rts.CfgTools.get("LandDetails"), com.maddox.rts.CfgTools.get("LandGeom"), com.maddox.rts.CfgTools.get("TexLandQual"), com.maddox.rts.CfgTools.get("TexLandLarge"), com.maddox.rts.CfgTools.get("HardwareShaders")
        }, new int[][] {
            new int[] {
                0, 0, 0, 1, 0, 0
            }, new int[] {
                0, 0, 1, 2, 0, 0
            }, new int[] {
                1, 1, 2, 3, 0, 0
            }, new int[] {
                1, 2, 2, 3, 0, 0
            }, new int[] {
                2, 2, 2, 3, 1, 0
            }, new int[] {
                2, 2, 2, 3, 1, 1
            }
        }, new java.lang.String[] {
            i18n("setup3d.LandDetail0"), i18n("setup3d.LandDetail1"), i18n("setup3d.LandDetail2"), i18n("setup3d.LandDetail3"), i18n("setup3d.LandDetail4"), i18n("setup3d.LandDetail5")
        });
        cSky = new ComboCfgSky(dialogClient, new java.lang.Object[] {
            com.maddox.rts.CfgTools.get("Sky")
        }, new int[][] {
            new int[] {
                0
            }, new int[] {
                1
            }, new int[] {
                2
            }
        }, new java.lang.String[] {
            i18n("setup3d.Sky0"), i18n("setup3d.Sky1"), i18n("setup3d.Sky2")
        });
        cCommon = new ComboCfg(dialogClient, new java.lang.Object[] {
            cVisible, cObjLight, cObjDetail, cLandLight, cLandDetail, cSky
        }, new int[][] {
            new int[] {
                0, 0, 1, 0, 0, 1
            }, new int[] {
                1, 1, 1, 1, 1, 1
            }, new int[] {
                2, 2, 2, 2, 2, 1
            }, new int[] {
                2, 2, 2, 2, 3, 2
            }, new int[] {
                3, 2, 3, 3, 4, 2
            }
        }, new java.lang.String[] {
            i18n("setup3d.Common0"), i18n("setup3d.Common1"), i18n("setup3d.Common2"), i18n("setup3d.Common3"), i18n("setup3d.Common4")
        });
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bApply = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bCustom = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bCommon = false;
        cCommon.hideWindow();
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.ComboCfg cCommon;
    public com.maddox.il2.gui.ComboCfg cVisible;
    public com.maddox.il2.gui.ComboCfg cObjLight;
    public com.maddox.il2.gui.ComboCfg cObjDetail;
    public com.maddox.il2.gui.ComboCfg cLandLight;
    public com.maddox.il2.gui.ComboCfg cLandDetail;
    public com.maddox.il2.gui.ComboCfg cSky;
    public com.maddox.il2.gui.GUIButton bApply;
    public com.maddox.il2.gui.GUIButton bCustom;
    public com.maddox.il2.gui.GUIButton bExit;
    private boolean bCommon;
    private boolean bRestoreRenderFocus;


}

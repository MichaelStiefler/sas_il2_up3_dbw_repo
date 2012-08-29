// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIArming.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.PaintScheme;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUIPocket, 
//            GUILookAndFeel, GUIButton, GUIDialogClient, GUIAirArming, 
//            GUISeparate

public class GUIArming extends com.maddox.il2.game.GameState
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
            if(gwindow == bAirArming)
            {
                if(bSingleMission)
                    com.maddox.il2.gui.GUIAirArming.stateId = 0;
                else
                    com.maddox.il2.gui.GUIAirArming.stateId = 1;
                com.maddox.il2.game.Main.stateStack().push(55);
            }
            return super.notify(gwindow, i, j);
        }

        public void render()
        {
            super.render();
            setCanvasColorWHITE();
            draw(x1024(80F), y1024(32F), x1024(64F), y1024(64F), texRegiment);
            setCanvasFont(1);
            draw(x1024(160F), y1024(48F), x1024(784F), y1024(32F), 0, com.maddox.il2.game.I18N.regimentInfo(regiment.info()));
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(480F), x1024(962F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(64F), y1024(144F), x1024(464F), y1024(32F), 1, i18n("arming.Aircraft"));
            draw(x1024(608F), y1024(144F), x1024(384F), y1024(32F), 1, i18n("arming.Weapons"));
            draw(x1024(96F), y1024(536F), x1024(320F), y1024(48F), 0, i18n("arming.Apply"));
            draw(x1024(430F), y1024(536F), x1024(492F), y1024(48F), 2, i18n("arming.WeaponDist"));
        }

        public void setPosSize()
        {
            set1024PosSize(0.0F, 92F, 1024F, 616F);
            for(int i = 0; i < 4; i++)
            {
                pAircraft[i].set1024PosSize(32F, 192 + i * 64, 544F, 32F);
                cWeapon[i].set1024PosSize(608F, 192 + i * 64, 384F, 32F);
            }

            bBack.setPosC(x1024(56F), y1024(560F));
            bAirArming.setPosC(x1024(960F), y1024(560F));
        }

        public DialogClient()
        {
        }
    }

    static class Slot
    {

        boolean bEnable;
        java.lang.String wingName;
        int players;
        int fuel;
        java.lang.Class planeClass;
        java.lang.String planeKey;
        java.lang.String weapons[];
        int weapon;

        Slot()
        {
        }
    }


    public void enterPush(com.maddox.il2.game.GameState gamestate)
    {
        bSingleMission = gamestate.id() == 4;
        super.enterPush(gamestate);
    }

    public void _enter()
    {
        com.maddox.il2.ai.UserCfg usercfg = com.maddox.il2.ai.World.cur().userCfg;
        try
        {
            com.maddox.rts.SectFile sectfile = com.maddox.il2.game.Main.cur().currentMissionFile;
            java.lang.String s = sectfile.get("MAIN", "player", (java.lang.String)null);
            java.lang.String s1 = s.substring(0, s.length() - 1);
            java.lang.String s2 = s1.substring(0, s1.length() - 1);
            regiment = (com.maddox.il2.ai.Regiment)com.maddox.il2.engine.Actor.getByName(s2);
            com.maddox.il2.engine.Mat mat = com.maddox.il2.objects.air.PaintScheme.makeMat(regiment.name(), regiment.fileNameTga(), 1.0F, 1.0F, 1.0F);
            texRegiment = com.maddox.gwindow.GTexture.New(mat.Name());
            int i = sectfile.get("MAIN", "WEAPONSCONSTANT", 0, 0, 1);
            com.maddox.il2.ai.World.cur().setWeaponsConstant(i == 1);
            playerNum = sectfile.get("Main", "playerNum", 0);
            slot = new com.maddox.il2.gui.Slot[4];
            playerSlot = -1;
            int j = sectfile.sectionIndex("Wing");
            int k = sectfile.vars(j);
label0:
            for(int l = 0; l < k; l++)
            {
                java.lang.String s3 = sectfile.var(j, l);
                if(!s3.startsWith(s1))
                    continue;
                int j1 = s3.charAt(s3.length() - 1) - 48;
                if(s3.equals(s))
                    playerSlot = j1;
                int l1 = sectfile.get(s3, "Planes", 0, 0, 4);
                com.maddox.il2.gui.Slot slot2 = new Slot();
                slot[j1] = slot2;
                slot2.wingName = s3;
                slot2.players = l1;
                slot2.fuel = sectfile.get(s3, "Fuel", 100, 0, 100);
                java.lang.String s4 = sectfile.get(s3, "Class", (java.lang.String)null);
                slot2.planeClass = com.maddox.rts.ObjIO.classForName(s4);
                slot2.planeKey = com.maddox.rts.Property.stringValue(slot2.planeClass, "keyName", null);
                java.lang.String s5 = sectfile.get(s3, "weapons", (java.lang.String)null);
                slot2.weapons = com.maddox.il2.objects.air.Aircraft.getWeaponsRegistered(slot2.planeClass);
                slot2.weapon = 0;
                int i2 = 0;
                do
                {
                    if(i2 >= slot2.weapons.length)
                        continue label0;
                    if(slot2.weapons[i2].equals(s5))
                    {
                        slot2.weapon = i2;
                        continue label0;
                    }
                    i2++;
                } while(true);
            }

            for(int i1 = 0; i1 < 4; i1++)
                if(slot[i1] == null)
                {
                    pAircraft[i1].setEnable(false);
                    cWeapon[i1].clear(false);
                } else
                {
                    com.maddox.il2.gui.Slot slot1 = slot[i1];
                    if(com.maddox.il2.ai.World.cur().isWeaponsConstant())
                        slot1.bEnable = false;
                    else
                    if(bSingleMission)
                        slot1.bEnable = true;
                    else
                    if(playerNum == 0)
                    {
                        if(playerSlot == 0)
                            slot1.bEnable = true;
                        else
                            slot1.bEnable = playerSlot == i1;
                    } else
                    {
                        slot1.bEnable = false;
                    }
                    pAircraft[i1].cap = new GCaption(slot1.players + " * " + com.maddox.il2.game.I18N.plane(slot1.planeKey));
                    if(i1 == playerSlot)
                        pAircraft[i1].color = 255;
                    else
                        pAircraft[i1].color = 0;
                    pAircraft[i1].setEnable(true);
                    cWeapon[i1].clear(false);
                    if(slot1.bEnable)
                    {
                        for(int k1 = 0; k1 < slot1.weapons.length; k1++)
                            if(com.maddox.il2.objects.air.Aircraft.isWeaponDateOk(slot1.planeClass, slot1.weapons[k1]))
                                cWeapon[i1].add(com.maddox.il2.game.I18N.weapons(slot1.planeKey, slot1.weapons[k1]));

                        cWeapon[i1].setSelected(slot1.weapon, true, false);
                    } else
                    {
                        cWeapon[i1].add(com.maddox.il2.game.I18N.weapons(slot1.planeKey, slot1.weapons[slot1.weapon]));
                        cWeapon[i1].setSelected(0, true, false);
                    }
                }

        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
            com.maddox.il2.game.Main.stateStack().pop();
            return;
        }
        client.activateWindow();
    }

    public void _leave()
    {
        try
        {
            com.maddox.rts.SectFile sectfile = com.maddox.il2.game.Main.cur().currentMissionFile;
            for(int i = 0; i < 4; i++)
            {
                if(slot[i] == null)
                    continue;
                com.maddox.il2.gui.Slot slot1 = slot[i];
                if(slot1.bEnable)
                    sectfile.set(slot1.wingName, "weapons", slot1.weapons[cWeapon[i].getSelected()]);
            }

            regiment = null;
            texRegiment = null;
            slot = null;
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        com.maddox.il2.ai.World.cur().setUserCovers();
        client.hideWindow();
    }

    private float clampValue(com.maddox.gwindow.GWindowEditControl gwindoweditcontrol, float f, float f1, float f2)
    {
        java.lang.String s = gwindoweditcontrol.getValue();
        try
        {
            f = java.lang.Float.parseFloat(s);
        }
        catch(java.lang.Exception exception) { }
        if(f < f1)
            f = f1;
        if(f > f2)
            f = f2;
        gwindoweditcontrol.setValue("" + f, false);
        return f;
    }

    public GUIArming(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(54);
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("arming.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        pAircraft = new com.maddox.il2.gui.GUIPocket[4];
        cWeapon = new com.maddox.gwindow.GWindowComboControl[4];
        for(int i = 0; i < 4; i++)
        {
            pAircraft[i] = new GUIPocket(dialogClient, "");
            pAircraft[i].setEnable(false);
            cWeapon[i] = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 0.0F, 0.0F, 1.0F));
            cWeapon[i].setEditable(false);
        }

        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bBack = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bAirArming = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIPocket pAircraft[];
    public com.maddox.gwindow.GWindowComboControl cWeapon[];
    public com.maddox.il2.gui.GUIButton bAirArming;
    public com.maddox.il2.gui.GUIButton bBack;
    private com.maddox.il2.ai.Regiment regiment;
    private com.maddox.gwindow.GTexture texRegiment;
    private com.maddox.il2.gui.Slot slot[];
    private int playerNum;
    private int playerSlot;
    private boolean bSingleMission;



}

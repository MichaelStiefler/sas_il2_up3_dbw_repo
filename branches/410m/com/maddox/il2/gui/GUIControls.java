// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIControls.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowDialogControl;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowScrollingDialogClient;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.IniFile;
import com.maddox.rts.Joy;
import com.maddox.rts.LDRres;
import com.maddox.rts.RTSConf;
import com.maddox.rts.VK;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import com.maddox.util.IntHashtable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUI, GUIDialogClient, GUISeparate

public class GUIControls extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bBack)
            {
                com.maddox.il2.ai.World.cur().userCfg.saveConf();
                com.maddox.il2.game.Main.stateStack().pop();
                return true;
            }
            if(gwindow == bDefault)
            {
                com.maddox.rts.IniFile inifile = new IniFile("users/default.ini", 0);
                java.lang.String as[] = com.maddox.il2.ai.UserCfg.nameHotKeyEnvs;
                for(int k = 0; k < as.length; k++)
                {
                    com.maddox.rts.HotKeyEnv.env(as[k]).all().clear();
                    com.maddox.rts.HotKeyEnv.fromIni(as[k], inifile, "HotKey " + as[k]);
                }

                fillItems();
            }
            return super.notify(gwindow, i, j);
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(624F), x1024(962F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(96F), y1024(656F), x1024(224F), y1024(48F), 0, i18n("ctrl.Apply"));
            draw(x1024(400F), y1024(656F), x1024(352F), y1024(48F), 0, i18n("ctrl.Reset"));
        }

        public void setPosSize()
        {
            set1024PosSize(0.0F, 32F, 1024F, 736F);
            scrollClient.set1024PosSize(32F, 32F, 962F, 562F);
            fixedClient.setSize(fixedClient.getMinSize().dx, fixedClient.getMinSize().dy);
            setPosItems(((scrollClient.win.dx - scrollClient.vScroll.win.dx) * 1024F) / root.win.dx);
            bBack.setPosC(x1024(56F), y1024(680F));
            bDefault.setPosC(x1024(360F), y1024(680F));
        }

        public DialogClient()
        {
        }
    }

    public class FixedClient extends com.maddox.gwindow.GWindowDialogClient
    {

        public void render()
        {
        }

        public void mouseButton(int i, boolean flag, float f, float f1)
        {
            super.mouseButton(i, flag, f, f1);
            if(flag)
                setKeyFocus();
        }

        public boolean doNotify(int i, int j)
        {
            if(i == 17)
                return super.doNotify(i, j);
            else
                return false;
        }

        public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GSize gsize)
        {
            gsize.dx = scrollClient.win.dx - scrollClient.vScroll.win.dx - 1.0F;
            com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
            gsize.dy = y1024(itemsDY_1024);
            return gsize;
        }

        public FixedClient()
        {
        }
    }

    public class ScrollClient extends com.maddox.gwindow.GWindowScrollingDialogClient
    {

        public void created()
        {
            fixed = fixedClient = (com.maddox.il2.gui.FixedClient)create(new FixedClient());
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
            super.resized();
            com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
            if(vScroll.isVisible())
            {
                vScroll.setPos(win.dx - lookAndFeel().getVScrollBarW() - gbevel.R.dx, gbevel.T.dy);
                vScroll.setSize(lookAndFeel().getVScrollBarW(), win.dy - gbevel.T.dy - gbevel.B.dy);
            }
            clipReg.set(0.0F, 0.0F, win.dx - gbevel.R.dx, win.dy - gbevel.B.dy);
        }

        public void render()
        {
            setCanvasColorWHITE();
            com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
            lookAndFeel().drawBevel(this, 0.0F, 0.0F, win.dx, win.dy, gbevel, ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).basicelements, true);
        }

        public void doChildrensRender(boolean flag)
        {
            pushClipRegion(clipReg, true, 0.0F);
            super.doChildrensRender(flag);
            popClip();
        }

        com.maddox.gwindow.GRegion clipReg;

        public ScrollClient()
        {
            clipReg = new GRegion();
        }
    }

    public class ItemControl extends com.maddox.gwindow.GWindowLabel
    {

        public void keyFocusExit()
        {
            if(messageBox == null)
                curKey[0] = curKey[1] = 0;
        }

        private boolean findItem(boolean flag)
        {
            boolean flag1 = false;
            com.maddox.il2.gui.Item item = items[indx];
            for(int i = 0; i < items.length; i++)
            {
                com.maddox.il2.gui.Item item1 = items[i];
                if(item1.control == null || item1.cmd.hotKeyCmdEnv().name().equals(mapEnvLink.get(item.cmd.hotKeyCmdEnv().name())))
                    continue;
                if(item1.key[0] == keySum || item1.key[0] == keySum2)
                {
                    iFinded = i;
                    if(!flag)
                        return true;
                    flag1 = true;
                    item1.key[0] = item1.key[1];
                    item1.key[1] = 0;
                } else
                {
                    if(item1.key[1] != keySum && item1.key[1] != keySum2)
                        continue;
                    iFinded = i;
                    if(!flag)
                        return true;
                    flag1 = true;
                    item1.key[1] = 0;
                }
                if(item1.env.all() != null)
                {
                    item1.env.all().remove(keySum);
                    item1.env.all().remove(keySum2);
                }
                item1.control.fillCaption();
            }

            return flag1;
        }

        private void fillItem(boolean flag)
        {
            if(flag)
                findItem(true);
            com.maddox.il2.gui.Item item = items[indx];
            item.env.add(curKey[0], curKey[1], item.cmd.name());
            if(item.key[0] != 0)
            {
                if(item.key[1] != 0)
                {
                    item.env.all().remove(item.key[1]);
                    item.env.all().remove((item.key[1] & 0xffff) << 16 | (item.key[1] & 0xffff0000) >>> 16);
                }
                item.key[1] = item.key[0];
            }
            item.key[0] = keySum;
            curKey[0] = curKey[1] = 0;
            fillCaption();
            parentWindow.setKeyFocus();
        }

        private void requestItem()
        {
            com.maddox.il2.gui.Item item = items[iFinded];
            java.lang.String s = item.label.cap.caption;
            messageBox = new com.maddox.gwindow.GWindowMessageBox(client, 24F, true, com.maddox.il2.game.I18N.gui("ctrl.Warning"), com.maddox.il2.game.I18N.gui("ctrl.ReplaceCommand0") + s + com.maddox.il2.game.I18N.gui("ctrl.ReplaceCommand1"), 1, 0.0F) {

                public void result(int i)
                {
                    messageBox = null;
                    if(i == 3)
                    {
                        fillItem(true);
                    } else
                    {
                        curKey[0] = curKey[1] = 0;
                        fillCaption();
                        parentWindow.setKeyFocus();
                    }
                }

            }
;
        }

        protected void doKey(int i, boolean flag)
        {
            doKey(i, flag, false, false);
        }

        protected void doKey(int i, boolean flag, boolean flag1, boolean flag2)
        {
            if(flag)
            {
                if(curKey[0] == 0)
                    curKey[0] = i;
                else
                if(curKey[1] == 0)
                    curKey[1] = i;
            } else
            {
                if(messageBox != null)
                    return;
                if(curKey[0] == i || curKey[1] == i)
                {
                    if(curKey[0] > curKey[1])
                    {
                        int j = curKey[0];
                        curKey[0] = curKey[1];
                        curKey[1] = j;
                    }
                    keySum = (curKey[0] & 0xffff) << 16 | curKey[1] & 0xffff;
                    keySum2 = (curKey[1] & 0xffff) << 16 | curKey[0] & 0xffff;
                    com.maddox.il2.gui.Item item = items[indx];
                    boolean flag3 = false;
                    if(!flag1)
                        if(item.key[0] == keySum || item.key[0] == keySum2)
                        {
                            item.env.all().remove(item.key[1]);
                            item.env.all().remove((item.key[1] & 0xffff) << 16 | (item.key[1] & 0xffff0000) >>> 16);
                            item.key[1] = 0;
                            flag3 = true;
                        } else
                        if(item.key[1] == keySum || item.key[1] == keySum2)
                        {
                            item.env.all().remove(item.key[0]);
                            item.env.all().remove((item.key[0] & 0xffff) << 16 | (item.key[0] & 0xffff0000) >>> 16);
                            item.key[0] = item.key[1];
                            item.key[1] = 0;
                            flag3 = true;
                        }
                    if(!flag3)
                    {
                        boolean flag4 = findItem(false);
                        if(!flag1)
                        {
                            if(flag4)
                                requestItem();
                            else
                                fillItem(false);
                            return;
                        }
                        if(flag4)
                            findItem(true);
                        ((com.maddox.il2.gui.ItemJoy)item).bMinus = flag2;
                        if(flag2)
                            item.env.add(curKey[0], curKey[1], "-" + item.cmd.name());
                        else
                            item.env.add(curKey[0], curKey[1], item.cmd.name());
                        if(item.key[0] != 0)
                        {
                            item.env.all().remove(item.key[0]);
                            item.env.all().remove((item.key[0] & 0xffff) << 16 | (item.key[0] & 0xffff0000) >>> 16);
                        }
                        item.key[0] = keySum;
                    }
                    curKey[0] = curKey[1] = 0;
                    fillCaption();
                    parentWindow.setKeyFocus();
                }
            }
        }

        public void keyboardKey(int i, boolean flag)
        {
            super.keyboardKey(i, flag);
            if(i == 27)
            {
                parentWindow.setKeyFocus();
                return;
            }
            if(isKeyFocus() && !(items[indx] instanceof com.maddox.il2.gui.ItemJoy) && messageBox == null)
                doKey(i, flag);
        }

        public void mouseButton(int i, boolean flag, float f, float f1)
        {
            super.mouseButton(i, flag, f, f1);
            if(isKeyFocus() && !(items[indx] instanceof com.maddox.il2.gui.ItemJoy) && messageBox == null)
                doKey(i + 524, flag);
        }

        public void joyButton(int i, int j, boolean flag)
        {
            if(isKeyFocus() && !(items[indx] instanceof com.maddox.il2.gui.ItemJoy) && messageBox == null)
            {
                if(flag)
                {
                    curKey[0] = curKey[1] = 0;
                    doKey(i, flag);
                }
                doKey(j, flag);
                if(!flag)
                    doKey(i, flag);
            }
        }

        public void joyPov(int i, int j)
        {
            if(isKeyFocus() && !(items[indx] instanceof com.maddox.il2.gui.ItemJoy) && messageBox == null && j != 571)
            {
                curKey[0] = curKey[1] = 0;
                doKey(i, true);
                doKey(j, true);
                doKey(j, false);
                doKey(i, false);
            }
        }

        public void joyMove(int i, int j, int k)
        {
            int l = i | j << 16;
            int i1 = hashJoyMove.get(l);
            if(i1 == -1)
            {
                hashJoyMove.put(l, k);
                return;
            }
            if((double)java.lang.Math.abs(com.maddox.rts.Joy.normal(k - i1)) < 0.20000000000000001D)
                return;
            hashJoyMove.put(l, k);
            if(isKeyFocus() && (items[indx] instanceof com.maddox.il2.gui.ItemJoy) && messageBox == null)
            {
                boolean flag = k - i1 < 0;
                curKey[0] = curKey[1] = 0;
                doKey(i, true);
                doKey(j, true);
                doKey(j, false, true, flag);
                doKey(i, false, true, flag);
                int ai[] = new int[13];
                com.maddox.rts.Joy.adapter().getSensitivity(i - 580, j - 563, ai);
                if(ai[12] > 0)
                    if(flag)
                        ai[12] = 2;
                    else
                        ai[12] = 1;
                com.maddox.rts.Joy.adapter().setSensitivity(i - 580, j - 563, ai);
                int j1 = com.maddox.il2.engine.Config.cur.ini.get("rts", "JoyProfile", 0, 0, 3);
                java.lang.String s = "rts_joystick";
                if(j1 > 0)
                    s = s + j1;
                com.maddox.rts.Joy.adapter().saveConfig(com.maddox.il2.engine.Config.cur.ini, s);
            }
        }

        public void mouseRelMove(float f, float f1, float f2)
        {
            super.mouseRelMove(f, f1, f2);
            if(f2 != 0.0F && isKeyFocus() && (items[indx] instanceof com.maddox.il2.gui.ItemJoy) && messageBox == null)
            {
                doKey(530, true);
                doKey(530, false, true, f2 < 0.0F);
            }
        }

        public void keyFocusEnter()
        {
            super.keyFocusEnter();
            hashJoyMove.clear();
            for(int i = 0; i < povState.length; i++)
                povState[i] = 0;

        }

        public void fillCaption()
        {
            com.maddox.il2.gui.Item item = items[indx];
            java.lang.String s;
            if(item.key[0] == 0 && item.key[1] == 0)
                s = "";
            else
            if(item.key[0] != 0 && item.key[1] != 0)
                s = " " + keyToStr(item.key[0]) + ", " + keyToStr(item.key[1]);
            else
            if((item instanceof com.maddox.il2.gui.ItemJoy) && ((com.maddox.il2.gui.ItemJoy)item).bMinus)
                s = " -" + keyToStr(item.key[0]);
            else
                s = " " + keyToStr(item.key[0]);
            cap = new GCaption(s);
        }

        private java.lang.String keyToStr(int i)
        {
            if(i == 0)
                return "";
            if((i & 0xffff0000) == 0)
                return resName(com.maddox.rts.VK.getKeyText(i));
            else
                return resName(com.maddox.rts.VK.getKeyText(i >> 16 & 0xffff)) + " " + resName(com.maddox.rts.VK.getKeyText(i & 0xffff));
        }

        public boolean isMousePassThrough(float f, float f1)
        {
            return false;
        }

        public boolean notify(int i, int j)
        {
            if(i == 17 && isActivated())
                return false;
            else
                return super.notify(i, j);
        }

        public void render()
        {
            if(isActivated())
            {
                setCanvasColorWHITE();
                com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
                lookAndFeel().drawBevel(this, 0.0F, 0.0F, win.dx, win.dy, gbevel, ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).basicelements, true);
            }
            super.render();
        }

        public int indx;



        public ItemControl(com.maddox.gwindow.GWindow gwindow, java.lang.String s, java.lang.String s1)
        {
            super(gwindow, 0.0F, 0.0F, 1.0F, 1.0F, s, s1);
        }
    }

    public class ItemLabel extends com.maddox.gwindow.GWindowLabel
    {

        public boolean isMousePassThrough(float f, float f1)
        {
            return true;
        }

        public ItemLabel(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2, float f3, java.lang.String s, 
                java.lang.String s1)
        {
            super(gwindow, f, f1, f2, f3, s, s1);
        }
    }

    public class ItemJoy extends com.maddox.il2.gui.Item
    {

        boolean bMinus;

        public ItemJoy()
        {
        }
    }

    public class Item
    {

        com.maddox.gwindow.GWindowDialogControl label;
        com.maddox.il2.gui.ItemControl control;
        com.maddox.rts.HotKeyCmd cmd;
        com.maddox.rts.HotKeyEnv env;
        float y;
        int indx;
        int key[];

        public Item()
        {
            key = new int[2];
        }
    }


    public void _enter()
    {
        fillItems();
        client.showWindow();
        client.doResolutionChanged();
        client.doResolutionChanged();
        com.maddox.il2.gui.GUI.activateJoy();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    private void fillItems()
    {
        for(int i = 0; i < items.length; i++)
        {
            com.maddox.il2.gui.Item item = items[i];
            item.key[0] = item.key[1] = 0;
        }

        java.util.ArrayList arraylist = new ArrayList();
        java.lang.String as[] = com.maddox.il2.ai.UserCfg.nameHotKeyEnvs;
        for(int j = 0; j < as.length; j++)
        {
            com.maddox.rts.HotKeyEnv hotkeyenv = com.maddox.rts.HotKeyEnv.env(as[j]);
            boolean flag = "move".equals(as[j]);
            com.maddox.util.HashMapInt hashmapint = hotkeyenv.all();
            for(com.maddox.util.HashMapIntEntry hashmapintentry = hashmapint.nextEntry(null); hashmapintentry != null; hashmapintentry = hashmapint.nextEntry(hashmapintentry))
            {
                int l = hashmapintentry.getKey();
                java.lang.String s = (java.lang.String)(java.lang.String)hashmapintentry.getValue();
                com.maddox.il2.gui.Item item2 = (com.maddox.il2.gui.Item)mapItems.get(s);
                if(item2 != null)
                {
                    if(item2.key[0] == 0)
                        item2.key[0] = l;
                    else
                    if(item2.key[1] == 0)
                        item2.key[1] = l;
                    else
                        arraylist.add(new Integer(l));
                    if(!flag)
                        continue;
                    ((com.maddox.il2.gui.ItemJoy)item2).bMinus = false;
                    if(item2.key[1] != 0)
                    {
                        arraylist.add(new Integer(item2.key[1]));
                        item2.key[1] = 0;
                    }
                    continue;
                }
                if(!flag || s.charAt(0) != '-')
                    continue;
                com.maddox.il2.gui.ItemJoy itemjoy = (com.maddox.il2.gui.ItemJoy)mapItems.get(s.substring(1));
                if(itemjoy == null)
                    continue;
                itemjoy.bMinus = true;
                if(itemjoy.key[0] == 0)
                    itemjoy.key[0] = l;
                else
                    arraylist.add(new Integer(l));
                if(itemjoy.key[1] != 0)
                {
                    arraylist.add(new Integer(itemjoy.key[1]));
                    itemjoy.key[1] = 0;
                }
            }

            if(arraylist.size() <= 0)
                continue;
            for(int i1 = 0; i1 < arraylist.size(); i1++)
            {
                java.lang.Integer integer = (java.lang.Integer)arraylist.get(i1);
                hashmapint.remove(integer.intValue());
            }

            arraylist.clear();
        }

        for(int k = 0; k < items.length; k++)
        {
            com.maddox.il2.gui.Item item1 = items[k];
            if(item1.control != null)
                item1.control.fillCaption();
        }

    }

    private void initResource()
    {
        try
        {
            resource = java.util.ResourceBundle.getBundle("i18n/controls", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
        }
        catch(java.lang.Exception exception) { }
    }

    private java.lang.String resName(java.lang.String s)
    {
        if(resource == null)
            return s;
        return resource.getString(s);
        java.lang.Exception exception;
        exception;
        return s;
    }

    private void createItems()
    {
        initResource();
        mapEnvLink.put("PanView", "SnapView");
        mapEnvLink.put("SnapView", "PanView");
        java.util.ArrayList arraylist = new ArrayList();
        mapItems = new HashMapExt();
        java.lang.String as[] = com.maddox.il2.ai.UserCfg.nameHotKeyEnvs;
        float f = 0.0F;
        for(int i = 0; i < as.length; i++)
        {
            com.maddox.il2.gui.Item item = new Item();
            f += 32F;
            item.y = f;
            if(as[i].startsWith("$$$"))
                item.label = fixedClient.addLabel(new ItemLabel(fixedClient, 0.0F, 0.0F, 1.0F, 1.0F, "", null));
            else
                item.label = fixedClient.addLabel(new ItemLabel(fixedClient, 0.0F, 0.0F, 1.0F, 1.0F, resName(as[i]), null));
            item.label.color = 0xff0235b6;
            arraylist.add(item);
            com.maddox.rts.HotKeyCmdEnv hotkeycmdenv = com.maddox.rts.HotKeyCmdEnv.env(as[i]);
            com.maddox.rts.HotKeyEnv hotkeyenv = com.maddox.rts.HotKeyEnv.env(as[i]);
            item.env = hotkeyenv;
            com.maddox.util.HashMapExt hashmapext = hotkeycmdenv.all();
            java.util.Iterator iterator = hashmapext.keySet().iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                com.maddox.rts.HotKeyCmd hotkeycmd = (com.maddox.rts.HotKeyCmd)hashmapext.get(iterator.next());
                if(hotkeycmd.sortingName != null)
                    _sortMap.put(hotkeycmd.sortingName, hotkeycmd);
            } while(true);
            boolean flag = "move".equals(as[i]);
            for(java.util.Iterator iterator1 = _sortMap.keySet().iterator(); iterator1.hasNext();)
            {
                com.maddox.rts.HotKeyCmd hotkeycmd1 = (com.maddox.rts.HotKeyCmd)_sortMap.get(iterator1.next());
                if(hotkeycmd1.name().startsWith("$$$"))
                {
                    com.maddox.il2.gui.Item item1 = new Item();
                    f += 32F;
                    item1.y = f;
                    item1.label = fixedClient.addLabel(new ItemLabel(fixedClient, 0.0F, 0.0F, 1.0F, 1.0F, "", null));
                    arraylist.add(item1);
                } else
                if(hotkeycmd1.name().startsWith("$$+"))
                {
                    com.maddox.il2.gui.Item item2 = new Item();
                    f += 32F;
                    item2.y = f;
                    item2.label = fixedClient.addLabel(new ItemLabel(fixedClient, 0.0F, 0.0F, 1.0F, 1.0F, resName(hotkeycmd1.name().substring(3)), null));
                    item2.label.color = 0xff0235b6;
                    arraylist.add(item2);
                } else
                {
                    java.lang.Object obj;
                    if(flag)
                    {
                        com.maddox.il2.gui.ItemJoy itemjoy = new ItemJoy();
                        obj = itemjoy;
                    } else
                    {
                        obj = new Item();
                    }
                    f += 32F;
                    obj.y = f;
                    obj.cmd = hotkeycmd1;
                    obj.env = hotkeyenv;
                    obj.label = fixedClient.addLabel(new ItemLabel(fixedClient, 0.0F, 0.0F, 1.0F, 1.0F, resName(hotkeycmd1.name()), null));
                    obj.control = (com.maddox.il2.gui.ItemControl)fixedClient.addControl(new ItemControl(fixedClient, hotkeycmd1.name(), null));
                    arraylist.add(obj);
                    mapItems.put(hotkeycmd1.name(), obj);
                }
            }

            _sortMap.clear();
        }

        items = new com.maddox.il2.gui.Item[arraylist.size()];
        for(int j = 0; j < arraylist.size(); j++)
        {
            items[j] = (com.maddox.il2.gui.Item)arraylist.get(j);
            items[j].indx = j;
            if(items[j].control != null)
                items[j].control.indx = j;
        }

        arraylist.clear();
        f += 64F;
        itemsDY_1024 = f;
    }

    private void setPosItems(float f)
    {
        for(int i = 0; i < items.length; i++)
        {
            com.maddox.il2.gui.Item item = items[i];
            if(item.control == null)
            {
                item.label.set1024PosSize(16F, item.y, f - 16F - 16F, 32F);
            } else
            {
                item.label.set1024PosSize(48F, item.y, f - 48F - 320F - 32F, 32F);
                item.control.set1024PosSize(f - 320F - 48F, item.y, 352F, 32F);
            }
        }

    }

    public GUIControls(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(20);
        itemsDY_1024 = 1000F;
        hashJoyMove = new IntHashtable();
        mapEnvLink = new HashMap();
        povState = new int[16];
        _sortMap = new TreeMap();
        curKey = new int[2];
        messageBox = null;
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("ctrl.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bBack = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bDefault = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        scrollClient = (com.maddox.il2.gui.ScrollClient)dialogClient.create(new ScrollClient());
        createItems();
        dialogClient.setPosSize();
        client.hideWindow();
    }

    private static final java.lang.String JOYENV = "move";
    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.ScrollClient scrollClient;
    public com.maddox.il2.gui.FixedClient fixedClient;
    private com.maddox.il2.gui.Item items[];
    private com.maddox.util.HashMapExt mapItems;
    private com.maddox.util.HashMapExt mapItemsJoy;
    private float itemsDY_1024;
    private com.maddox.util.IntHashtable hashJoyMove;
    private java.util.HashMap mapEnvLink;
    private int povState[];
    public com.maddox.il2.gui.GUIButton bBack;
    public com.maddox.il2.gui.GUIButton bDefault;
    java.util.ResourceBundle resource;
    java.util.TreeMap _sortMap;
    int curKey[];
    int keySum;
    int keySum2;
    int iFinded;
    com.maddox.gwindow.GWindowMessageBox messageBox;








}

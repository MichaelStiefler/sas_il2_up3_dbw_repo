// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIChatDialog.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GPoint;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.Army;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.ChatMessage;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Console;
import com.maddox.rts.IniFile;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetHost;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;
import com.maddox.sound.AudioDevice;
import com.maddox.sound.CfgNpFlags;
import com.maddox.util.UnicodeTo8bit;
import java.io.PrintStream;
import java.util.List;

// Referenced classes of package com.maddox.il2.gui:
//            GUILookAndFeel, GUISeparate, GUI

public class GUIChatDialog extends com.maddox.gwindow.GWindow
{
    public class WClient extends com.maddox.gwindow.GWindowDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i == 15)
            {
                com.maddox.il2.gui.GUI.chatActivate();
                return true;
            }
            if(i == 11 && j == 27)
            {
                if(mode() == 0)
                {
                    if(chatCurEditSlot != -1)
                    {
                        chatCurEditSlot = -1;
                        wEdit.clear(false);
                    }
                } else
                if(mode() == 1)
                {
                    chatStateSend = false;
                    wEdit.clear(false);
                } else
                if(mode() == 2)
                    wEdit.setValue("", false);
                com.maddox.il2.gui.GUI.chatUnactivate();
                return true;
            }
            com.maddox.il2.net.Chat chat = com.maddox.il2.game.Main.cur().chat;
            if(gwindow != wEdit || i != 10 || j != 10 || chat == null)
                return super.notify(gwindow, i, j);
            java.lang.String s = wEdit.getValue();
            if(s == null || s.length() == 0)
                return super.notify(gwindow, i, j);
            java.lang.String s1 = null;
            boolean flag = false;
            boolean flag1 = false;
            switch(mode())
            {
            case 0: // '\0'
                if(chatCurEditSlot >= 0)
                    com.maddox.il2.engine.Config.cur.ini.set("chat", "msg" + chatCurEditSlot, com.maddox.util.UnicodeTo8bit.save(s, true));
                chatMessage = s;
                wEdit.setValue(chatAdrSlot[chatCurAdrSlot], false);
                chatCurEditSlot = -1;
                chatStateSend = true;
                break;

            case 1: // '\001'
                com.maddox.il2.engine.Config.cur.ini.set("chat", "adr" + chatCurAdrSlot, com.maddox.util.UnicodeTo8bit.save(s, true));
                if(chatCurAdrSlot >= 2)
                    s = "chat " + chatMessage + " TO " + s;
                else
                    s = "chat " + chatMessage + " " + s;
                flag = true;
                flag1 = true;
                chatStateSend = false;
                wEdit.clear(false);
                break;

            case 2: // '\002'
                com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
                switch(radioCurSlot)
                {
                case 1: // '\001'
                    if(!netuser.isRadioNone())
                    {
                        s = "radio NONE";
                        s1 = "radioNone";
                        flag1 = true;
                    }
                    break;

                case 2: // '\002'
                    if(!netuser.isRadioCommon())
                    {
                        s = "radio COMMON";
                        s1 = "radioCommon";
                        flag1 = true;
                    }
                    break;

                case 3: // '\003'
                    if(netuser.getArmy() != 0 && !netuser.isRadioArmy())
                    {
                        s = "radio ARMY";
                        s1 = "radioArmy";
                        flag1 = true;
                    }
                    break;

                case 4: // '\004'
                    if(s.length() == 1)
                    {
                        if(!netuser.isRadioNone())
                        {
                            s = "radio NONE";
                            s1 = "radioNone";
                            flag1 = true;
                        }
                    } else
                    {
                        java.lang.String s2 = s.substring(1);
                        if(!s2.equals(netuser.radio()))
                        {
                            s = "radio " + s2;
                            s1 = "radioPrivate";
                            flag1 = true;
                        }
                    }
                    break;
                }
                break;

            case 3: // '\003'
                s = s.substring(1);
                flag1 = true;
                wEdit.setValue(">", false);
                break;

            default:
                return true;
            }
            if(flag1)
            {
                java.lang.System.out.println(com.maddox.rts.RTSConf.cur.console.getPrompt() + s);
                com.maddox.rts.RTSConf.cur.console.getEnv().exec(s);
                com.maddox.rts.RTSConf.cur.console.addHistoryCmd(s);
                com.maddox.rts.RTSConf.cur.console.curHistoryCmd = -1;
                if(s1 != null && !com.maddox.rts.Time.isPaused())
                    com.maddox.il2.game.HUD.log(s);
            }
            if(flag)
                com.maddox.il2.gui.GUI.chatUnactivate();
            return true;
        }

        private void drawScroll(float f, float f1, float f2, float f3, int i, int j, int k)
        {
            float f4 = (float)(i - k - j) / (float)i;
            float f5 = (float)k / (float)i;
            float f6 = (float)j / (float)i;
            if(f4 > 0.0F)
                com.maddox.il2.gui.GUISeparate.draw(this, 65535, f, f1, f2, f3 * f4);
            if(f5 > 0.0F)
                com.maddox.il2.gui.GUISeparate.draw(this, 0, f, f1 + f3 * f4, f2, f3 * f5);
            if(f6 > 0.0F)
                com.maddox.il2.gui.GUISeparate.draw(this, 65535, f, f1 + f3 * (f4 + f5), f2, f3 - f3 * (f4 + f5));
        }

        public void render()
        {
            if(isTransparent())
                return;
            super.render();
            com.maddox.il2.net.Chat chat = com.maddox.il2.game.Main.cur().chat;
            if(chat == null)
                return;
            com.maddox.gwindow.GFont gfont = root.textFonts[0];
            float f = gfont.height;
            int i = chat.buf.size();
            int j = (int)(wDrawChat.win.dy / f);
            if(j > i)
                j = i;
            if(j > 0)
                drawScroll(wViewChat.win.x + wViewChat.win.dx, wViewChat.win.y, win.dx - wViewChat.win.x - wViewChat.win.dx, wViewChat.win.dy, i, posChat, j);
            if(!isDownVisible())
                return;
            if(mode() != 3)
                return;
            java.util.List list = com.maddox.rts.RTSConf.cur.console.historyOut();
            gfont = consoleFont;
            f = gfont.height;
            i = list.size();
            j = (int)(wDrawConsole.win.dy / f);
            if(j > i)
                j = i;
            if(j > 0)
                drawScroll(wViewConsole.win.x + wViewConsole.win.dx, wViewConsole.win.y, win.dx - wViewConsole.win.x - wViewConsole.win.dx, wViewConsole.win.dy, i, posConsole, j);
        }

        public void resized()
        {
            wEdit.setPosSize(0.0F, clientHeight - y1024(32F), win.dx, y1024(32F));
            wViewChat.setPosSize(x1024(2.0F), y1024(2.0F), win.dx - x1024(4F), clientHeight - y1024(36F));
            wDrawChat.setPosSize(0.0F, 0.0F, wViewChat.win.dx, wViewChat.win.dy);
            wViewConsole.setPosSize(x1024(2.0F), y1024(2.0F) + clientHeight, win.dx - x1024(4F), win.dy - clientHeight - y1024(4F));
            wDrawConsole.setPosSize(0.0F, 0.0F, wViewConsole.win.dx, wViewConsole.win.dy);
            super.resized();
        }

        public WClient()
        {
        }
    }

    public class WDrawConsole extends com.maddox.gwindow.GWindow
    {

        public void mouseButton(int i, boolean flag, float f, float f1)
        {
            super.mouseButton(i, flag, f, f1);
            if(!isDownVisible())
                return;
            if(i == 0 && !flag)
                consoleDrawPos(f1 < win.dy / 2.0F);
        }

        public void render()
        {
            if(com.maddox.il2.game.Main3D.cur3D().hud.isDrawNetStat())
                return;
            if(!isDownVisible())
                return;
            switch(mode())
            {
            default:
                break;

            case 3: // '\003'
                java.util.List list = com.maddox.rts.RTSConf.cur.console.historyOut();
                com.maddox.gwindow.GFont gfont = consoleFont;
                float f = gfont.height;
                int i = list.size();
                int j = (int)(win.dy / f);
                if(i > j)
                    i = j;
                if(i <= 0)
                    return;
                float f3 = win.dy - f;
                int k = posConsole;
                if(k + i >= list.size())
                    k = list.size() - i;
                setCanvasColor(0xffffff);
                root.C.font = gfont;
                if(isTransparent())
                    root.C.alpha = 127;
                for(int l = 0; l < i; l++)
                {
                    java.lang.String s = (java.lang.String)list.get(k);
                    int i1 = s.length();
                    int j1 = 0;
                    do
                    {
                        if(j1 >= i1)
                            break;
                        char c = s.charAt(j1);
                        if(c < ' ' && c != '\t')
                        {
                            s = s.substring(0, j1);
                            break;
                        }
                        j1++;
                    } while(true);
                    draw(0.0F, f3, win.dx, f, 0, s);
                    f3 -= f;
                    k++;
                }

                if(isTransparent())
                    root.C.alpha = 0;
                break;

            case 0: // '\0'
                renderTable(chatEditSlot, chatCurEditSlot);
                break;

            case 1: // '\001'
                renderTable(chatAdrSlot, chatCurAdrSlot);
                break;

            case 2: // '\002'
                com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
                com.maddox.gwindow.GFont gfont1 = root.textFonts[0];
                float f1 = gfont1.height;
                float f2 = win.dy - f1 * 10F - f1 / 2.0F;
                setCanvasColor(0xffffff);
                draw(0.0F, f2, win.dx, f1, 0, radioSlot[0]);
                f2 += f1;
                renderRadioSlot(f2, gfont1, 1, netuser.isRadioNone(), null);
                f2 += f1;
                renderRadioSlot(f2, gfont1, 2, netuser.isRadioCommon(), " 0");
                f2 += f1;
                renderRadioSlot(f2, gfont1, 3, netuser.isRadioArmy(), netuser.getArmy() == 0 ? null : " " + netuser.getArmy());
                f2 += f1;
                renderRadioSlot(f2, gfont1, 4, netuser.isRadioPrivate(), netuser.isRadioPrivate() ? netuser.radio() : null);
                break;
            }
        }

        private void renderTable(java.lang.String as[], int i)
        {
            com.maddox.gwindow.GFont gfont = root.textFonts[0];
            float f = gfont.height;
            float f1 = win.dy - f * (float)as.length - f / 2.0F;
            for(int j = 0; j < as.length; j++)
            {
                if(i == j)
                    setCanvasColor(0);
                else
                    setCanvasColor(0xffffff);
                draw(0.0F, f1, win.dx, f, 0, " " + j + ". " + as[j]);
                f1 += f;
            }

        }

        private void renderRadioSlot(float f, com.maddox.gwindow.GFont gfont, int i, boolean flag, java.lang.String s)
        {
            if(radioCurSlot == i)
                setCanvasColor(0);
            else
                setCanvasColor(0xffffff);
            int j = 0;
            if(flag)
                j++;
            java.util.List list = com.maddox.rts.NetEnv.hosts();
            for(int k = 0; k < list.size(); k++)
            {
                com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)list.get(k);
                if(s == null)
                {
                    if(i == 1 && netuser.radio() == null)
                        j++;
                    continue;
                }
                if(s.equals(netuser.radio()))
                    j++;
            }

            if(flag)
                draw(0.0F, f, win.dx, gfont.height, 0, " " + i + ". (" + j + ")  \t *" + radioSlot[i]);
            else
                draw(0.0F, f, win.dx, gfont.height, 0, " " + i + ". (" + j + ")  \t  " + radioSlot[i]);
        }

        public WDrawConsole()
        {
        }
    }

    public class WDrawChat extends com.maddox.gwindow.GWindow
    {

        public void mouseButton(int i, boolean flag, float f, float f1)
        {
            super.mouseButton(i, flag, f, f1);
            if(i == 0 && !flag)
                chatDrawPos(f1 < win.dy / 2.0F);
        }

        public void render()
        {
            if(com.maddox.il2.game.Main3D.cur3D().hud.isDrawNetStat())
                return;
            com.maddox.il2.net.Chat chat = com.maddox.il2.game.Main.cur().chat;
            if(chat == null)
                return;
            com.maddox.gwindow.GFont gfont = root.textFonts[0];
            float f = gfont.height;
            int i = chat.buf.size();
            int j = (int)(win.dy / f);
            if(i > j)
                i = j;
            if(i <= 0)
                return;
            float f1 = win.dy - f;
            int k = posChat;
            if(k + i >= chat.buf.size())
                k = chat.buf.size() - i;
            setCanvasColor(0xffffff);
            setCanvasFont(0);
            if(isTransparent())
                root.C.alpha = 255;
            for(int l = 0; l < i; l++)
            {
                com.maddox.il2.net.ChatMessage chatmessage = (com.maddox.il2.net.ChatMessage)chat.buf.get(k);
                if(chatmessage.from != null)
                {
                    java.lang.String s = chatmessage.from.shortName() + ":\t" + chatmessage.msg;
                    setCanvasColor(0);
                    draw(0.0F, f1 + 1.0F, win.dx, f, 0, s);
                    draw(1.0F, f1, win.dx, f, 0, s);
                    draw(1.0F, f1 + 1.0F, win.dx, f, 0, s);
                    setCanvasColor(com.maddox.il2.ai.Army.color(((com.maddox.il2.net.NetUser)chatmessage.from).getArmy()));
                    draw(0.0F, f1, win.dx, f, 0, s);
                } else
                {
                    setCanvasColor(0xffffff);
                    draw(0.0F, f1, win.dx, f, 0, "--- " + chatmessage.msg);
                }
                f1 -= f;
                k++;
            }

            if(isTransparent())
                root.C.alpha = 0;
        }

        public WDrawChat()
        {
        }
    }

    public class WEdit extends com.maddox.gwindow.GWindowEditControl
    {

        public boolean notify(int i, int j)
        {
            boolean flag = super.notify(i, j);
            if(i == 2)
                switch(mode())
                {
                case 3: // '\003'
                default:
                    break;

                case 0: // '\0'
                    java.lang.String s = getValue();
                    if(chatCurEditSlot >= 0)
                        chatEditSlot[chatCurEditSlot] = s;
                    else
                    if(s.length() == 1 && java.lang.Character.isDigit(s.charAt(0)))
                    {
                        int k = s.charAt(0) - 48;
                        setValue(chatEditSlot[k], false);
                        chatCurEditSlot = k;
                        break;
                    }
                    if(caretOffset >= 2 && caretOffset <= s.length() && s.charAt(caretOffset - 2) == '\\' && java.lang.Character.isDigit(s.charAt(caretOffset - 1)))
                    {
                        int l = s.charAt(caretOffset - 1) - 48;
                        value.deleteCharAt(caretOffset - 1);
                        value.deleteCharAt(caretOffset - 2);
                        value.insert(caretOffset - 2, chatEditSlot[l]);
                        setValue(value.toString(), false);
                    }
                    break;

                case 1: // '\001'
                    if(chatCurAdrSlot == 0 || chatCurAdrSlot == 1)
                    {
                        setValue(chatAdrSlot[chatCurAdrSlot], false);
                        break;
                    }
                    if(chatCurAdrSlot >= 2)
                        chatAdrSlot[chatCurAdrSlot] = getValue();
                    break;

                case 2: // '\002'
                    java.lang.String s1 = getValue();
                    if(s1.length() == 1)
                    {
                        if(!com.maddox.sound.AudioDevice.npFlags.get(0))
                        {
                            setValue("", false);
                            break;
                        }
                        if(((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).isRadioPrivate())
                            radioSlot[4] = ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).radio();
                        else
                            radioSlot[4] = "";
                        if(radioCurSlot == 4)
                            setValue("." + radioSlot[4], false);
                        break;
                    }
                    if(s1.length() == 2 && java.lang.Character.isDigit(s1.charAt(1)))
                    {
                        int i1 = s1.charAt(1) - 48;
                        if(i1 != radioCurSlot && i1 >= 1 && i1 <= 4)
                        {
                            radioCurSlot = i1;
                            if(radioCurSlot == 4)
                                setValue("." + radioSlot[4], false);
                            else
                                setValue(".", false);
                            break;
                        }
                    }
                    if(radioCurSlot == 4)
                    {
                        for(; getValue().length() > 1 && getValue().charAt(1) == ' '; setValue("." + getValue().substring(2), false));
                        radioSlot[radioCurSlot] = getValue().substring(1);
                    } else
                    {
                        setValue(".", false);
                    }
                    break;
                }
            return flag;
        }

        public void keyboardKey(int i, boolean flag)
        {
            switch(i)
            {
            case 33: // '!'
            case 34: // '"'
                if(!flag)
                    chatDrawPos(i == 33);
                return;

            case 38: // '&'
            case 40: // '('
                if(!flag)
                {
                    if(bControlDown)
                    {
                        consoleDrawPos(i == 38);
                        return;
                    }
                    switch(mode())
                    {
                    default:
                        break;

                    case 3: // '\003'
                        java.util.List list = com.maddox.rts.RTSConf.cur.console.historyCmd();
                        int j = com.maddox.rts.RTSConf.cur.console.curHistoryCmd;
                        if(list.size() > 0)
                            if(i == 38)
                            {
                                if(j < list.size())
                                    j++;
                                else
                                    j = 0;
                            } else
                            if(j >= 0)
                                j--;
                            else
                                j = list.size() - 1;
                        if(j >= 0 && j < list.size())
                        {
                            java.lang.String s = (java.lang.String)list.get(j);
                            setValue(">" + s, false);
                        }
                        com.maddox.rts.RTSConf.cur.console.curHistoryCmd = j;
                        break;

                    case 0: // '\0'
                        if(i == 40)
                            chatCurEditSlot = (chatCurEditSlot + 1) % chatEditSlot.length;
                        else
                        if(chatCurEditSlot == -1)
                            chatCurEditSlot = 9;
                        else
                            chatCurEditSlot = ((chatCurEditSlot - 1) + chatEditSlot.length) % chatEditSlot.length;
                        setValue(chatEditSlot[chatCurEditSlot], false);
                        break;

                    case 1: // '\001'
                        if(i == 40)
                            chatCurAdrSlot = (chatCurAdrSlot + 1) % chatAdrSlot.length;
                        else
                            chatCurAdrSlot = ((chatCurAdrSlot - 1) + chatAdrSlot.length) % chatAdrSlot.length;
                        setValue(chatAdrSlot[chatCurAdrSlot], false);
                        break;

                    case 2: // '\002'
                        if(i == 40)
                        {
                            radioCurSlot++;
                            if(radioCurSlot > 4)
                                radioCurSlot = 1;
                        } else
                        {
                            radioCurSlot--;
                            if(radioCurSlot < 1)
                                radioCurSlot = 4;
                        }
                        if(radioCurSlot == 4)
                            setValue("." + radioSlot[radioCurSlot], false);
                        else
                            setValue(".", false);
                        break;
                    }
                }
                return;

            case 35: // '#'
            case 36: // '$'
            case 37: // '%'
            case 39: // '\''
            default:
                super.keyboardKey(i, flag);
                return;
            }
        }

        public void render()
        {
            super.render();
        }

        public void keyFocusExit()
        {
            super.keyFocusExit();
            setEditable(false);
        }

        public WEdit(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2, float f3, java.lang.String s)
        {
            super(gwindow, f, f1, f2, f3, s);
        }
    }


    public com.maddox.il2.gui.GUIChatDialog THIS()
    {
        return this;
    }

    public int mode()
    {
        int i = wEdit.getFirstChar();
        if(i == 46)
            return 2;
        if(i == 62)
            return 3;
        else
            return chatStateSend ? 1 : 0;
    }

    public boolean isTransparent()
    {
        return !wEdit.bCanEdit;
    }

    public boolean isDownVisible()
    {
        if(isTransparent())
            return mode() == 3;
        else
            return true;
    }

    public float downHeight()
    {
        return root.textFonts[0].height * 11F;
    }

    private void chatDrawPos(boolean flag)
    {
        com.maddox.il2.net.Chat chat = com.maddox.il2.game.Main.cur().chat;
        if(chat == null)
            return;
        com.maddox.gwindow.GFont gfont = root.textFonts[0];
        float f = gfont.height;
        int i = chat.buf.size();
        if(i == 0)
        {
            posChat = 0;
            return;
        }
        int j = (int)(wViewChat.win.dy / f);
        int k = j / 3;
        if(k == 0)
            k = 1;
        posChat = flag ? posChat + k : posChat - k;
        if(posChat + j >= i)
            posChat = i - j;
        if(posChat < 0)
            posChat = 0;
    }

    private void consoleDrawPos(boolean flag)
    {
        if(!isDownVisible())
            return;
        if(mode() != 3)
            return;
        java.util.List list = com.maddox.rts.RTSConf.cur.console.historyOut();
        com.maddox.gwindow.GFont gfont = consoleFont;
        float f = gfont.height;
        int i = list.size();
        if(i == 0)
        {
            posConsole = 0;
            return;
        }
        int j = (int)(wViewConsole.win.dy / f);
        int k = j / 3;
        if(k == 0)
            k = 1;
        posConsole = flag ? posConsole + k : posConsole - k;
        if(posConsole + j >= i)
            posConsole = i - j;
        if(posConsole < 0)
            posConsole = 0;
    }

    public void afterCreated()
    {
        for(int i = 0; i < 10; i++)
            chatEditSlot[i] = com.maddox.util.UnicodeTo8bit.load(com.maddox.il2.engine.Config.cur.ini.get("chat", "msg" + i, new String()));

        chatAdrSlot[0] = "ALL";
        chatAdrSlot[1] = "MY_ARMY";
        for(int j = 2; j < 10; j++)
            chatAdrSlot[j] = com.maddox.util.UnicodeTo8bit.load(com.maddox.il2.engine.Config.cur.ini.get("chat", "adr" + j, new String()));

        wClient = (com.maddox.il2.gui.WClient)create(new WClient());
        wEdit = (com.maddox.il2.gui.WEdit)wClient.addDefault(new WEdit(wClient, 0.0F, 0.0F, 1.0F, 2.0F, null));
        wEdit.setHistory(false);
        wEdit.maxLength = 80;
        wEdit.setEditable(false);
        wViewChat = wClient.create(new GWindow());
        wViewChat.bNotify = true;
        wDrawChat = (com.maddox.il2.gui.WDrawChat)wViewChat.create(new WDrawChat());
        wDrawChat.bAcceptsKeyFocus = false;
        wViewConsole = wClient.create(new GWindow());
        wViewConsole.bNotify = true;
        consoleFont = com.maddox.gwindow.GFont.New("courSmall");
        wDrawConsole = (com.maddox.il2.gui.WDrawConsole)wViewConsole.create(new WDrawConsole());
        wDrawConsole.bAcceptsKeyFocus = false;
        texIndicator = com.maddox.gwindow.GTexture.New("GUI/game/indicator.mat");
    }

    public void render()
    {
        if(!isTransparent())
        {
            com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
            setCanvasColorWHITE();
            lookAndFeel().drawBevel(this, 0.0F, 0.0F, win.dx, win.dy, gbevel, ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).basicelements, false);
        } else
        {
            if(com.maddox.il2.game.Main3D.cur3D().hud.isDrawNetStat())
                return;
            com.maddox.il2.engine.GUIWindowManager guiwindowmanager = com.maddox.il2.game.Main3D.cur3D().guiManager;
            if(guiwindowmanager.isMouseActive())
            {
                root.C.alpha = 255;
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Black, 0.0F, 0.0F, win.dx, 2.0F);
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Black, 0.0F, 0.0F, 2.0F, win.dy);
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Black, win.dx - 2.0F, 0.0F, 2.0F, win.dy);
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Black, 0.0F, win.dy - 2.0F, win.dx, 2.0F);
                root.C.alpha = 0;
            }
        }
    }

    private void drawU(int i, int j, float f, float f1)
    {
        float f2 = x1024(6F);
        float f3 = y1024(9F);
        float f4 = x1024(9F);
        if(i > 8)
            i = 8;
        if(i > 0)
        {
            for(int k = 0; k < i; k++)
            {
                com.maddox.gwindow.GColor gcolor = com.maddox.gwindow.GColor.Green;
                if(k >= 6)
                    gcolor = com.maddox.gwindow.GColor.Yellow;
                com.maddox.il2.gui.GUISeparate.draw(wEdit, gcolor, f + (float)k * f4, f1, f2, f3);
            }

        }
        if(j > 5)
            j = 5;
        if(j > 0)
        {
            float f5 = 9F * f4;
            for(int l = 0; l < j; l++)
            {
                com.maddox.gwindow.GColor gcolor1 = com.maddox.gwindow.GColor.Yellow;
                if(l >= 1)
                    gcolor1 = com.maddox.gwindow.GColor.Red;
                com.maddox.il2.gui.GUISeparate.draw(wEdit, gcolor1, f5 + f + (float)l * f4, f1, f2, f3);
            }

        }
    }

    public boolean isFindTestOk(com.maddox.gwindow.GWindow gwindow, boolean flag)
    {
        return gwindow.isVisible();
    }

    public void doRender(boolean flag)
    {
        boolean flag1 = isTransparent();
        int i = root.C.alpha;
        if(flag1)
            root.C.alpha = 0;
        super.doRender(flag);
        if(flag1)
            root.C.alpha = i;
        int j = root.C.alpha;
        root.C.alpha = 255;
        float f = 0.0F;
        int k = com.maddox.sound.AudioDevice.getRadioStatus();
        if(getChild(root, com.maddox.il2.gui.GUIInfoMenu.class, false, true) != null)
            f = 32F;
        if((k & 3) >= 2)
        {
            int l = java.lang.Math.round(((float)com.maddox.sound.AudioDevice.getRadioLevel() / 100F) * 8F);
            int j1 = java.lang.Math.round(((float)com.maddox.sound.AudioDevice.getRadioOverflow() / 100F) * 5F);
            if((k & 0x10) == 0)
            {
                if(k == 2)
                    setCanvasColor(com.maddox.gwindow.GColor.Blue);
                else
                    setCanvasColor(0xffffff);
            } else
            if(k == 2)
                setCanvasColor(com.maddox.gwindow.GColor.Yellow);
            else
                setCanvasColor(com.maddox.gwindow.GColor.Green);
            draw(x1024(882F), y1024(f), x1024(16F), y1024(16F), texIndicator, 0.0F, 0.0F, 16F, 16F);
            setCanvasColorWHITE();
            drawU(l, j1, x1024(900F), y1024(f + 2.0F));
        }
        if(com.maddox.il2.game.Main.cur().netServerParams != null && com.maddox.il2.game.Main.cur().netServerParams.isMirror())
        {
            int i1 = com.maddox.il2.game.Main.cur().netServerParams.masterChannel().getCurTimeout();
            int k1 = com.maddox.il2.game.Main.cur().netServerParams.masterChannel().ping();
            byte byte0 = 8;
            if(k1 < 50)
                byte0 = 1;
            else
            if(k1 < 100)
                byte0 = 2;
            else
            if(k1 < 150)
                byte0 = 3;
            else
            if(k1 < 200)
                byte0 = 4;
            else
            if(k1 < 250)
                byte0 = 5;
            else
            if(k1 < 400)
                byte0 = 6;
            else
            if(k1 < 600)
                byte0 = 7;
            byte byte1 = 5;
            if(i1 < 5000)
                byte1 = 0;
            else
            if(i1 < 10000)
                byte1 = 1;
            else
            if(i1 < 15000)
                byte1 = 2;
            else
            if(i1 < 20000)
                byte1 = 3;
            else
            if(i1 < 25000)
                byte1 = 4;
            setCanvasColorWHITE();
            draw(x1024(882F), y1024(f + 16F), x1024(16F), y1024(16F), texIndicator, 0.0F, 16F, 16F, 16F);
            drawU(byte0, byte1, x1024(900F), y1024(f + 2.0F + 16F));
        }
        root.C.alpha = j;
    }

    public void preRender()
    {
        com.maddox.il2.net.Chat chat = com.maddox.il2.game.Main.cur().chat;
        if(chat == null)
            hideWindow();
        if(isDownVisible())
        {
            if(wClient.win.dy == clientHeight + downHeight())
                return;
            com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
            setSize(win.dx, clientHeight + downHeight() + gbevel.T.dy + gbevel.B.dy);
        } else
        {
            if(wClient.win.dy == clientHeight)
                return;
            com.maddox.gwindow.GBevel gbevel1 = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
            setSize(win.dx, clientHeight + gbevel1.T.dy + gbevel1.B.dy);
        }
    }

    public void resized()
    {
        com.maddox.gwindow.GSize gsize = getMinSize();
        if(win.dx < gsize.dx)
            win.dx = gsize.dx;
        if(win.dy < gsize.dy)
            win.dy = gsize.dy;
        com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
        wClient.setPosSize(gbevel.L.dx, gbevel.T.dy, win.dx - gbevel.L.dx - gbevel.R.dx, win.dy - gbevel.T.dy - gbevel.B.dy);
    }

    public void resolutionChanged()
    {
        consoleFont.resolutionChanged();
        loadRegion();
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GSize gsize)
    {
        float f = x1024(100F);
        float f1 = y1024(50F);
        gsize.set(f, f1);
        return gsize;
    }

    public GUIChatDialog(com.maddox.gwindow.GWindow gwindow)
    {
        posChat = 0;
        posConsole = 0;
        chatStateSend = false;
        chatCurEditSlot = -1;
        chatCurAdrSlot = 0;
        chatEditSlot = new java.lang.String[10];
        chatAdrSlot = new java.lang.String[10];
        radioCurSlot = 1;
        sizingState = 0;
        bAlwaysOnTop = true;
        gwindow.create(this);
        loadRegion();
    }

    private void loadRegion()
    {
        com.maddox.gwindow.GRegion gregion = new GRegion(0.0F, 0.2F, 0.3F, 0.2F);
        com.maddox.il2.engine.Config.cur.ini.get("chat", "region", gregion);
        clientHeight = gregion.dy * root.win.dy;
        com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
        float f = clientHeight + gbevel.T.dy + gbevel.B.dy;
        if(isDownVisible())
            f += downHeight();
        setPosSize(gregion.x * root.win.dx, gregion.y * root.win.dy, gregion.dx * root.win.dx, f);
    }

    private void saveRegion()
    {
        com.maddox.gwindow.GRegion gregion = new GRegion();
        gregion.x = win.x / root.win.dx;
        gregion.y = win.y / root.win.dy;
        gregion.dx = win.dx / root.win.dx;
        gregion.dy = clientHeight / root.win.dy;
        com.maddox.il2.engine.Config.cur.ini.set("chat", "region", gregion, false);
    }

    int frameHitTest(float f, float f1)
    {
        com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
        if(f < 0.0F || f > win.dx || f1 < 0.0F || f1 > win.dy)
            return 0;
        if(f <= gbevel.L.dx)
        {
            if(f1 <= gbevel.T.dy)
                return 2;
            return f1 < win.dy - gbevel.T.dy - gbevel.B.dy ? 5 : 7;
        }
        if(f >= win.dx - gbevel.L.dx - gbevel.R.dx)
        {
            if(f1 <= gbevel.T.dy)
                return 4;
            return f1 < win.dy - gbevel.T.dy - gbevel.B.dy ? 6 : 9;
        }
        if(f1 <= gbevel.T.dy)
            return 1;
        return f1 < win.dy - gbevel.T.dy - gbevel.B.dy ? 1 : 8;
    }

    public void mouseButton(int i, boolean flag, float f, float f1)
    {
        super.mouseButton(i, flag, f, f1);
        if(i == 0)
        {
            if(flag)
            {
                if(isMouseCaptured())
                    return;
                if(isTransparent())
                    return;
                int j = frameHitTest(f, f1);
                if(j == 0)
                    return;
                sizingState = j;
                mouseCapture(true);
            } else
            if(isMouseCaptured())
            {
                sizingState = 0;
                mouseCapture(false);
                mouseCursor = 1;
                saveRegion();
            }
        } else
        if(i == 1)
            if(flag)
            {
                if(isMouseCaptured())
                    return;
                if(isTransparent())
                    return;
                int k = frameHitTest(f, f1);
                if(k == 0)
                    return;
                sizingState = 1;
                mouseCursor = 3;
                mouseCapture(true);
            } else
            if(isMouseCaptured())
            {
                sizingState = 0;
                mouseCapture(false);
                mouseCursor = 1;
                saveRegion();
            }
    }

    public void mouseMove(float f, float f1)
    {
        super.mouseMove(f, f1);
        com.maddox.gwindow.GRegion gregion = root.getClientRegion();
        if(root.mousePos.x < gregion.x || root.mousePos.x >= gregion.x + gregion.dx || root.mousePos.y < gregion.y || root.mousePos.y >= gregion.y + gregion.dy)
            return;
        com.maddox.gwindow.GSize gsize = null;
        if(sizingState != 1 && sizingState != 0)
        {
            _newSize.set(win.dx, win.dy);
            gsize = getMinSize();
            gsize.dy += downHeight();
        }
        switch(sizingState)
        {
        default:
            break;

        case 0: // '\0'
            int i = frameHitTest(f, f1);
            mouseCursor = 1;
            if(i == 0)
                return;
            switch(i)
            {
            case 2: // '\002'
                mouseCursor = 10;
                break;

            case 3: // '\003'
                mouseCursor = 9;
                break;

            case 4: // '\004'
                mouseCursor = 8;
                break;

            case 5: // '\005'
                mouseCursor = 11;
                break;

            case 6: // '\006'
                mouseCursor = 11;
                break;

            case 7: // '\007'
                mouseCursor = 8;
                break;

            case 8: // '\b'
                mouseCursor = 9;
                break;

            case 9: // '\t'
                mouseCursor = 10;
                break;

            case 1: // '\001'
                mouseCursor = 3;
                break;
            }
            return;

        case 1: // '\001'
            setPos(win.x + root.mouseStep.dx, win.y + root.mouseStep.dy);
            return;

        case 2: // '\002'
            _newSize.add(-root.mouseStep.dx, -root.mouseStep.dy);
            if(_newSize.dx >= gsize.dx && _newSize.dy >= gsize.dy)
            {
                setPos(win.x + root.mouseStep.dx, win.y + root.mouseStep.dy);
                setSize();
                return;
            }
            break;

        case 3: // '\003'
            _newSize.add(0.0F, -root.mouseStep.dy);
            if(_newSize.dy >= gsize.dy)
            {
                setPos(win.x, win.y + root.mouseStep.dy);
                setSize();
                return;
            }
            break;

        case 4: // '\004'
            _newSize.add(root.mouseStep.dx, -root.mouseStep.dy);
            if(_newSize.dx >= gsize.dx && _newSize.dy >= gsize.dy)
            {
                setPos(win.x, win.y + root.mouseStep.dy);
                setSize();
                return;
            }
            break;

        case 5: // '\005'
            _newSize.add(-root.mouseStep.dx, 0.0F);
            if(_newSize.dx >= gsize.dx)
            {
                setPos(win.x + root.mouseStep.dx, win.y);
                setSize();
                return;
            }
            break;

        case 6: // '\006'
            _newSize.add(root.mouseStep.dx, 0.0F);
            if(_newSize.dx >= gsize.dx)
            {
                setSize();
                return;
            }
            break;

        case 7: // '\007'
            _newSize.add(-root.mouseStep.dx, root.mouseStep.dy);
            if(_newSize.dx >= gsize.dx && _newSize.dy >= gsize.dy)
            {
                setPos(win.x + root.mouseStep.dx, win.y);
                setSize();
                return;
            }
            break;

        case 8: // '\b'
            _newSize.add(0.0F, root.mouseStep.dy);
            if(_newSize.dy >= gsize.dy)
            {
                setSize();
                return;
            }
            break;

        case 9: // '\t'
            _newSize.add(root.mouseStep.dx, root.mouseStep.dy);
            if(_newSize.dx >= gsize.dx && _newSize.dy >= gsize.dy)
            {
                setSize();
                return;
            }
            break;
        }
        sizingState = 0;
        mouseCapture(false);
        mouseCursor = 1;
    }

    private void setSize()
    {
        com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
        clientHeight = _newSize.dy - downHeight() - gbevel.T.dy - gbevel.B.dy;
        setSize(_newSize.dx, _newSize.dy);
    }

    public static final int MODE_CHAT = 0;
    public static final int MODE_CHAT_TO = 1;
    public static final int MODE_RADIO = 2;
    public static final int MODE_CONSOLE = 3;
    public static final int EDIT_SLOTS = 10;
    public static final int ADR_SLOTS = 10;
    public com.maddox.il2.gui.WClient wClient;
    public float clientHeight;
    public com.maddox.il2.gui.WEdit wEdit;
    public com.maddox.gwindow.GWindow wViewChat;
    public com.maddox.il2.gui.WDrawChat wDrawChat;
    public com.maddox.gwindow.GWindow wViewConsole;
    public com.maddox.il2.gui.WDrawConsole wDrawConsole;
    public com.maddox.gwindow.GFont consoleFont;
    public com.maddox.gwindow.GTexture texIndicator;
    public int posChat;
    public int posConsole;
    public boolean chatStateSend;
    public int chatCurEditSlot;
    public int chatCurAdrSlot;
    public java.lang.String chatEditSlot[];
    public java.lang.String chatAdrSlot[];
    public java.lang.String chatMessage;
    public static final int RADIO_NONE = 1;
    public static final int RADIO_COMMON = 2;
    public static final int RADIO_ARMY = 3;
    public static final int RADIO_PRIVATE = 4;
    public java.lang.String radioSlot[] = {
        "    Radio channels:", "None", "Common", "Army", ""
    };
    public int radioCurSlot;
    static final int SIZING_NONE = 0;
    static final int SIZING_MOVE = 1;
    static final int SIZING_TL = 2;
    static final int SIZING_T = 3;
    static final int SIZING_TR = 4;
    static final int SIZING_L = 5;
    static final int SIZING_R = 6;
    static final int SIZING_BL = 7;
    static final int SIZING_B = 8;
    static final int SIZING_BR = 9;
    int sizingState;
    private static com.maddox.gwindow.GSize _newSize = new GSize();



}

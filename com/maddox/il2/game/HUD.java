// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HUD.java

package com.maddox.il2.game;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.order.Order;
import com.maddox.il2.game.order.OrderAnyone_Help_Me;
import com.maddox.il2.game.order.OrderVector_To_Home_Base;
import com.maddox.il2.game.order.OrderVector_To_Target;
import com.maddox.il2.game.order.OrdersTree;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.NetUserStat;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.Cockpit;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.IniFile;
import com.maddox.rts.LDRres;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

// Referenced classes of package com.maddox.il2.game:
//            Main3D, Main, Mission, GameTrack, 
//            AircraftHotKeys, I18N

public class HUD
{
    static class Ptr
    {

        public void set(float f, float f1, int i, float f2, float f3)
        {
            x = f;
            y = f1;
            color = i;
            alpha = f2;
            angle = (float)((double)(f3 * 180F) / 3.1415926535897931D);
        }

        float x;
        float y;
        int color;
        float alpha;
        float angle;

        public Ptr(float f, float f1, int i, float f2, float f3)
        {
            set(f, f1, i, f2, f3);
        }
    }

    static class StatUser
    {

        com.maddox.il2.net.NetUser user;
        int iNum;
        java.lang.String sNum;
        int iPing;
        java.lang.String sPing;
        int iScore;
        java.lang.String sScore;
        int iArmy;
        java.lang.String sArmy;
        com.maddox.il2.objects.air.Aircraft aAircraft;
        java.lang.String sAircraft;
        java.lang.String sAircraftType;

        StatUser()
        {
        }
    }

    static class MsgLine
    {

        java.lang.String msg;
        int iActor;
        int army;
        long time0;
        int len;

        MsgLine(java.lang.String s, int i, int j, int k, long l)
        {
            msg = s;
            len = i;
            iActor = j;
            army = k;
            time0 = l;
        }
    }


    public static int drawSpeed()
    {
        return com.maddox.il2.game.Main3D.cur3D().hud.iDrawSpeed;
    }

    public static void setDrawSpeed(int i)
    {
        com.maddox.il2.game.Main3D.cur3D().hud.iDrawSpeed = i;
    }

    private final void renderSpeed()
    {
        if(!com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.getPlayerAircraft()))
            return;
        if(iDrawSpeed == 0)
            return;
        if(!bDrawAllMessages)
            return;
        if(com.maddox.il2.game.Main.cur().netServerParams != null && !com.maddox.il2.game.Main.cur().netServerParams.isShowSpeedBar())
            return;
        com.maddox.il2.engine.TTFont ttfont = com.maddox.il2.engine.TTFont.font[1];
        int i = ttfont.height();
        int j = 0xc00000ff;
        int k = (int)(com.maddox.il2.ai.World.getPlayerFM().Or.getYaw() + 0.5F);
        k = k <= 90 ? 90 - k : 450 - k;
        boolean flag = false;
        float f = com.maddox.il2.ai.World.getPlayerFM().getLoadDiff();
        if(f <= com.maddox.il2.ai.World.getPlayerFM().getLimitLoad() * 0.25F && f > com.maddox.il2.ai.World.getPlayerFM().getLimitLoad() * 0.1F)
        {
            flag = true;
            cnt = 0;
            timeLoadLimit = 0L;
        } else
        if(f <= com.maddox.il2.ai.World.getPlayerFM().getLimitLoad() * 0.1F && com.maddox.rts.Time.current() < timeLoadLimit)
            flag = false;
        else
        if(f <= com.maddox.il2.ai.World.getPlayerFM().getLimitLoad() * 0.1F && com.maddox.rts.Time.current() >= timeLoadLimit)
        {
            flag = true;
            cnt++;
            if(cnt == 22)
            {
                timeLoadLimit = 125L + com.maddox.rts.Time.current();
                cnt = 0;
            }
        } else
        {
            cnt = 0;
            timeLoadLimit = 0L;
        }
        java.lang.String s;
        int l;
        int i1;
        switch(iDrawSpeed)
        {
        case 1: // '\001'
        default:
            s = ".si";
            l = (int)(com.maddox.il2.ai.World.getPlayerFM().getAltitude() * 0.1F) * 10;
            i1 = (int)(3.6F * com.maddox.il2.fm.Pitot.Indicator((float)com.maddox.il2.ai.World.getPlayerFM().Loc.z, com.maddox.il2.ai.World.getPlayerFM().getSpeed()) * 0.1F) * 10;
            break;

        case 2: // '\002'
            s = ".gb";
            l = (int)(3.28084F * com.maddox.il2.ai.World.getPlayerFM().getAltitude() * 0.02F) * 50;
            i1 = (int)(1.943845F * com.maddox.il2.fm.Pitot.Indicator((float)com.maddox.il2.ai.World.getPlayerFM().Loc.z, com.maddox.il2.ai.World.getPlayerFM().getSpeed()) * 0.1F) * 10;
            break;

        case 3: // '\003'
            s = ".us";
            l = (int)(3.28084F * com.maddox.il2.ai.World.getPlayerFM().getAltitude() * 0.02F) * 50;
            i1 = (int)(2.236936F * com.maddox.il2.fm.Pitot.Indicator((float)com.maddox.il2.ai.World.getPlayerFM().Loc.z, com.maddox.il2.ai.World.getPlayerFM().getSpeed()) * 0.1F) * 10;
            break;
        }
        if(iDrawSpeed != lastDrawSpeed)
            try
            {
                renderSpeedSubstrings[0][0] = resLog.getString("HDG");
                renderSpeedSubstrings[1][0] = resLog.getString("ALT");
                renderSpeedSubstrings[2][0] = resLog.getString("SPD");
                renderSpeedSubstrings[3][0] = resLog.getString("G");
                renderSpeedSubstrings[0][1] = resLog.getString("HDG" + s);
                renderSpeedSubstrings[1][1] = resLog.getString("ALT" + s);
                renderSpeedSubstrings[2][1] = resLog.getString("SPD" + s);
                renderSpeedSubstrings[3][1] = resLog.getString("Ga");
            }
            catch(java.lang.Exception exception)
            {
                renderSpeedSubstrings[0][0] = "HDG";
                renderSpeedSubstrings[1][0] = "ALT";
                renderSpeedSubstrings[2][0] = "SPD";
                renderSpeedSubstrings[3][0] = "G";
                renderSpeedSubstrings[0][1] = "";
                renderSpeedSubstrings[1][1] = "";
                renderSpeedSubstrings[2][1] = "";
                renderSpeedSubstrings[3][1] = "";
            }
        ttfont.output(j, 5F, 5F, 0.0F, renderSpeedSubstrings[0][0] + " " + k + " " + renderSpeedSubstrings[0][1]);
        if(com.maddox.il2.ai.World.cur().diffCur.NoSpeedBar)
            return;
        ttfont.output(j, 5F, 5 + i, 0.0F, renderSpeedSubstrings[1][0] + " " + l + " " + renderSpeedSubstrings[1][1]);
        ttfont.output(j, 5F, 5 + i + i, 0.0F, renderSpeedSubstrings[2][0] + " " + i1 + " " + renderSpeedSubstrings[2][1]);
        if(flag)
            ttfont.output(j, 5F, 5 + i + i + i, 0.0F, renderSpeedSubstrings[3][0]);
    }

    public void clearSpeed()
    {
        iDrawSpeed = 1;
    }

    private void initSpeed()
    {
        iDrawSpeed = 1;
    }

    public static void order(com.maddox.il2.game.order.Order aorder[])
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.game.Main3D.cur3D().hud._order(aorder);
            return;
        }
    }

    public void _order(com.maddox.il2.game.order.Order aorder[])
    {
        if(aorder == null)
        {
            order = null;
            return;
        }
        order = new com.maddox.il2.game.order.Order[aorder.length];
        orderStr = new java.lang.String[aorder.length];
        int i = com.maddox.il2.ai.World.getPlayerArmy();
        for(int j = 0; j < order.length; j++)
        {
            order[j] = aorder[j];
            if(aorder[j] == null || order[j].name(i) == null)
                continue;
            java.lang.String s = order[j].name(i);
            java.lang.String s1 = null;
            java.lang.String s2 = com.maddox.il2.ai.World.getPlayerLastCountry();
            if(s2 != null)
                try
                {
                    s1 = resOrder.getString(s + "_" + s2);
                }
                catch(java.lang.Exception exception) { }
            if(s1 == null)
                try
                {
                    s1 = resOrder.getString(s);
                }
                catch(java.lang.Exception exception1)
                {
                    s1 = s;
                }
            orderStr[j] = s1;
        }

    }

    private void renderOrder()
    {
        if(order == null)
            return;
        if(!bDrawAllMessages)
            return;
        com.maddox.il2.engine.TTFont ttfont = com.maddox.il2.engine.TTFont.font[1];
        int i = (int)((double)viewDX * 0.050000000000000003D);
        int j = ttfont.height();
        int k = viewDY - 2 * ttfont.height();
        java.lang.String s = null;
        int l = k;
        int i1 = 0;
        int j1 = 0;
        boolean flag = false;
        boolean flag1 = false;
        for(int k1 = 0; k1 < order.length; k1++)
        {
            if(orderStr[k1] != null)
            {
                if(order[k1] instanceof com.maddox.il2.game.order.OrderAnyone_Help_Me)
                    flag = true;
                if(com.maddox.il2.game.Main3D.cur3D().ordersTree.frequency() == null)
                    flag1 = true;
                if(s != null)
                    drawOrder(s, i, l, j1 != 0 ? j1 : -1, i1, flag1);
                j1 = k1;
                s = orderStr[k1];
                l = k;
                i1 = order[k1].attrib();
                if(((order[k1] instanceof com.maddox.il2.game.order.OrderVector_To_Home_Base) || (order[k1] instanceof com.maddox.il2.game.order.OrderVector_To_Target)) && com.maddox.il2.game.Main.cur().mission.zutiRadar_DisableVectoring)
                    flag1 = true;
                else
                    flag1 = false;
            }
            k -= j;
        }

        if(com.maddox.il2.game.Main3D.cur3D().ordersTree.frequency() == null)
            flag1 = true;
        if(s != null)
            drawOrder(s, i, l, 0, i1, flag1);
        if(flag)
        {
            java.lang.String as[] = com.maddox.il2.game.Main3D.cur3D().ordersTree.getShipIDs();
            for(int l1 = 0; l1 < as.length; l1++)
            {
                if(l1 == 0 && as[l1] != null)
                {
                    k -= j;
                    k -= j;
                    drawShipIDs(resOrder.getString("ShipIDs"), i, k);
                    k -= j;
                }
                if(as[l1] != null)
                {
                    drawShipIDs(as[l1], i, k);
                    k -= j;
                }
            }

        }
    }

    private void drawShipIDs(java.lang.String s, int i, int j)
    {
        int k = 0xff0000ff;
        com.maddox.il2.engine.TTFont ttfont = com.maddox.il2.engine.TTFont.font[1];
        ttfont.output(k, i, j, 0.0F, s);
    }

    private void drawOrder(java.lang.String s, int i, int j, int k, int l, boolean flag)
    {
        int i1 = 0xff0000ff;
        if((l & 1) != 0)
            i1 = 0xff00007f;
        else
        if((l & 2) != 0)
            i1 = 0xff007fff;
        com.maddox.il2.engine.TTFont ttfont = com.maddox.il2.engine.TTFont.font[1];
        if(flag)
            i1 = 0x7f7f7f7f;
        if(k >= 0)
            ttfont.output(i1, i, j, 0.0F, "" + k + ". " + s);
        else
            ttfont.output(i1, i, j, 0.0F, s);
    }

    public void clearOrder()
    {
        order = null;
    }

    private void initOrder()
    {
        clearOrder();
        resOrder = java.util.ResourceBundle.getBundle("i18n/hud_order", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
    }

    public static void message(int ai[], int i, int j, boolean flag)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.game.Main3D.cur3D().hud._message(ai, i, j, flag);
            return;
        }
    }

    public void _message(int ai[], int i, int j, boolean flag)
    {
        if(!bDrawVoiceMessages)
            return;
        if(bNoSubTitles)
            return;
        if(i < 1)
            return;
        if(i > 9)
            return;
        if(j < 1)
            return;
        if(j > 2)
            return;
        if(ai == null)
            return;
        com.maddox.il2.engine.TTFont ttfont = com.maddox.il2.engine.TTFont.font[1];
        for(int k = 0; k < ai.length && ai[k] != 0; k++)
        {
            java.lang.String s = com.maddox.il2.objects.sounds.Voice.vbStr[ai[k]];
            try
            {
                java.lang.String s1 = resMsg.getString(s);
                if(s1 != null)
                    s = s1;
            }
            catch(java.lang.Exception exception) { }
            for(java.util.StringTokenizer stringtokenizer = new StringTokenizer(s); stringtokenizer.hasMoreTokens(); flag = false)
            {
                java.lang.String s2 = stringtokenizer.nextToken();
                int l = (int)ttfont.width(s2);
                if(msgLines.size() == 0)
                {
                    msgLines.add(new MsgLine(s2, l, i, j, com.maddox.rts.Time.current()));
                    continue;
                }
                com.maddox.il2.game.MsgLine msgline1 = (com.maddox.il2.game.MsgLine)msgLines.get(msgLines.size() - 1);
                if(msgline1.iActor == i && msgline1.army == j && !flag)
                {
                    int i1 = msgline1.len + msgSpaceLen + l;
                    if(i1 < msgDX)
                    {
                        msgline1.msg = msgline1.msg + " " + s2;
                        msgline1.len = i1;
                    } else
                    {
                        msgLines.add(new MsgLine(s2, l, i, j, 0L));
                    }
                } else
                {
                    msgLines.add(new MsgLine(s2, l, i, j, 0L));
                }
            }

        }

        while(msgLines.size() > msgSIZE) 
        {
            msgLines.remove(0);
            com.maddox.il2.game.MsgLine msgline = (com.maddox.il2.game.MsgLine)msgLines.get(0);
            msgline.time0 = com.maddox.rts.Time.current();
        }
    }

    private int msgColor(int i, int j)
    {
        return msgColor[j - 1][i - 1];
    }

    private void renderMsg()
    {
        if(!bDrawVoiceMessages)
            return;
        if(!bDrawAllMessages)
            return;
        int i = msgLines.size();
        if(i == 0)
            return;
        com.maddox.il2.game.MsgLine msgline = (com.maddox.il2.game.MsgLine)msgLines.get(0);
        long l = msgline.time0 + (long)(msgline.msg.length() * 250);
        if(l < com.maddox.rts.Time.current())
        {
            msgLines.remove(0);
            if(--i == 0)
                return;
            com.maddox.il2.game.MsgLine msgline1 = (com.maddox.il2.game.MsgLine)msgLines.get(0);
            msgline1.time0 = com.maddox.rts.Time.current();
        }
        com.maddox.il2.engine.TTFont ttfont = com.maddox.il2.engine.TTFont.font[1];
        int j = msgX0;
        int k = msgY0 + msgDY;
        for(int i1 = 0; i1 < i; i1++)
        {
            com.maddox.il2.game.MsgLine msgline2 = (com.maddox.il2.game.MsgLine)msgLines.get(i1);
            ttfont.output(msgColor(msgline2.iActor, msgline2.army), j, k, 0.0F, msgline2.msg);
            k -= ttfont.height();
        }

    }

    public void clearMsg()
    {
        msgLines.clear();
    }

    public void resetMsgSizes()
    {
        clearMsg();
        com.maddox.il2.engine.TTFont ttfont = com.maddox.il2.engine.TTFont.font[1];
        msgX0 = (int)((double)viewDX * 0.29999999999999999D);
        msgDX = (int)((double)viewDX * 0.59999999999999998D);
        msgDY = ttfont.height() * subTitlesLines;
        if(msgDY > (int)((double)viewDY * 0.90000000000000002D))
            msgDY = (int)((double)viewDY * 0.90000000000000002D);
        int i = msgDY / ttfont.height();
        if(i == 0)
            i = 1;
        msgDY = ttfont.height() * i;
        msgSIZE = i;
        msgY0 = (int)((double)viewDY * 0.94999999999999996D) - msgDY;
        msgSpaceLen = java.lang.Math.round(ttfont.width(" "));
    }

    private void initMsg()
    {
        resetMsgSizes();
        resMsg = java.util.ResourceBundle.getBundle("i18n/hud_msg", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
    }

    public static void training(java.lang.String s)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.game.Main3D.cur3D().hud._training(s);
            return;
        }
    }

    public void _training(java.lang.String s)
    {
        trainingLines.clear();
        if(s == null)
            return;
        com.maddox.il2.engine.TTFont ttfont = com.maddox.il2.engine.TTFont.font[2];
        java.util.StringTokenizer stringtokenizer = new StringTokenizer(s);
        do
        {
            if(!stringtokenizer.hasMoreTokens())
                break;
            java.lang.String s1 = stringtokenizer.nextToken();
            int i = (int)ttfont.width(s1);
            if(trainingLines.size() == 0)
            {
                trainingLines.add(s1);
                continue;
            }
            java.lang.String s2 = (java.lang.String)trainingLines.get(trainingLines.size() - 1);
            int j = (int)ttfont.width(s2);
            int k = j + trainingSpaceLen + i;
            if(k < trainingDX)
            {
                trainingLines.set(trainingLines.size() - 1, s2 + " " + s1);
                continue;
            }
            if(trainingLines.size() >= trainingSIZE)
                break;
            trainingLines.add(s1);
        } while(true);
    }

    private void renderTraining()
    {
        int i = trainingLines.size();
        if(i == 0)
            return;
        com.maddox.il2.engine.TTFont ttfont = com.maddox.il2.engine.TTFont.font[2];
        int j = trainingX0;
        int k = trainingY0 + trainingDY;
        for(int l = 0; l < i; l++)
        {
            java.lang.String s = (java.lang.String)trainingLines.get(l);
            ttfont.output(0xff0000ff, j, k, 0.0F, s);
            k -= ttfont.height();
        }

    }

    public void clearTraining()
    {
        trainingLines.clear();
    }

    public void resetTrainingSizes()
    {
        clearTraining();
        com.maddox.il2.engine.TTFont ttfont = com.maddox.il2.engine.TTFont.font[2];
        trainingX0 = (int)((double)viewDX * 0.29999999999999999D);
        trainingDX = (int)((double)viewDX * 0.5D);
        trainingY0 = (int)((double)viewDY * 0.5D);
        trainingDY = (int)((double)viewDY * 0.45000000000000001D);
        int i = trainingDY / ttfont.height();
        if(i == 0)
            i = 1;
        trainingDY = ttfont.height() * i;
        trainingSIZE = i;
        trainingSpaceLen = java.lang.Math.round(ttfont.width(" "));
    }

    private void initTraining()
    {
        resetTrainingSizes();
    }

    public static void intro(java.lang.String s)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.game.Main3D.cur3D().hud._intro(s);
            return;
        }
    }

    public void _intro(java.lang.String s)
    {
        if(s == null)
        {
            logIntro = null;
            return;
        } else
        {
            logIntro = s;
            return;
        }
    }

    public static void introESC(java.lang.String s)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.game.Main3D.cur3D().hud._introESC(s);
            return;
        }
    }

    public void _introESC(java.lang.String s)
    {
        if(s == null)
        {
            logIntroESC = null;
            return;
        } else
        {
            logIntroESC = s;
            return;
        }
    }

    public static int makeIdLog()
    {
        return idLog++;
    }

    public static void log(java.lang.String s, java.lang.Object aobj[])
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.game.HUD.log(0, s, aobj);
            return;
        }
    }

    public static void log(int i, java.lang.String s, java.lang.Object aobj[])
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        if(com.maddox.il2.game.Main3D.cur3D().gameTrackPlay() != null)
            return;
        if(com.maddox.il2.game.Main3D.cur3D().gameTrackRecord() != null && aobj != null && aobj.length == 1 && (aobj[0] instanceof java.lang.Integer))
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(0);
                netmsgguaranted.writeInt(i);
                netmsgguaranted.write255(s);
                netmsgguaranted.writeInt(((java.lang.Integer)aobj[0]).intValue());
                com.maddox.il2.game.Main3D.cur3D().gameTrackRecord().postTo(com.maddox.il2.game.Main3D.cur3D().gameTrackRecord().channel(), netmsgguaranted);
            }
            catch(java.lang.Exception exception) { }
        com.maddox.il2.game.Main3D.cur3D().hud._log(i, s, aobj);
    }

    public void _log(int i, java.lang.String s, java.lang.Object aobj[])
    {
        if(bNoHudLog)
            return;
        int j = __log(i, s);
        java.lang.String s1 = null;
        try
        {
            s1 = resLog.getString(s);
        }
        catch(java.lang.Exception exception)
        {
            s1 = s;
        }
        logBufStr[j] = java.text.MessageFormat.format(s1, aobj);
    }

    public static void log(java.lang.String s)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.game.HUD.log(0, s);
            return;
        }
    }

    public static void log(int i, java.lang.String s)
    {
        com.maddox.il2.game.HUD.log(i, s, true);
    }

    public static void log(int i, java.lang.String s, boolean flag)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        if(flag)
        {
            if(com.maddox.il2.game.Main3D.cur3D().gameTrackPlay() != null)
                return;
            if(com.maddox.il2.game.Main3D.cur3D().gameTrackRecord() != null)
                try
                {
                    com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                    netmsgguaranted.writeByte(1);
                    netmsgguaranted.writeInt(i);
                    netmsgguaranted.write255(s);
                    com.maddox.il2.game.Main3D.cur3D().gameTrackRecord().postTo(com.maddox.il2.game.Main3D.cur3D().gameTrackRecord().channel(), netmsgguaranted);
                }
                catch(java.lang.Exception exception) { }
        }
        com.maddox.il2.game.Main3D.cur3D().hud._log(i, s);
    }

    public void _log(int i, java.lang.String s)
    {
        if(bNoHudLog)
            return;
        int j = __log(i, s);
        try
        {
            logBufStr[j] = resLog.getString(s);
        }
        catch(java.lang.Exception exception)
        {
            logBufStr[j] = s;
        }
    }

    public static void logRightBottom(java.lang.String s)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        if(com.maddox.il2.game.Main3D.cur3D().gameTrackPlay() != null)
            return;
        if(com.maddox.il2.game.Main3D.cur3D().gameTrackRecord() != null)
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(2);
                netmsgguaranted.write255(s != null ? s : "");
                com.maddox.il2.game.Main3D.cur3D().gameTrackRecord().postTo(com.maddox.il2.game.Main3D.cur3D().gameTrackRecord().channel(), netmsgguaranted);
            }
            catch(java.lang.Exception exception) { }
        com.maddox.il2.game.Main3D.cur3D().hud._logRightBottom(s);
    }

    public void _logRightBottom(java.lang.String s)
    {
        if(bNoHudLog)
            return;
        if(s == null)
        {
            logRightBottom = null;
            return;
        }
        try
        {
            logRightBottom = resLog.getString(s);
        }
        catch(java.lang.Exception exception)
        {
            logRightBottom = s;
        }
        logRightBottomTime = com.maddox.rts.Time.current();
    }

    public static void logCenter(java.lang.String s)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        if(com.maddox.il2.game.Main3D.cur3D().gameTrackPlay() != null)
            return;
        if(com.maddox.il2.game.Main3D.cur3D().gameTrackRecord() != null)
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(3);
                netmsgguaranted.write255(s != null ? s : "");
                com.maddox.il2.game.Main3D.cur3D().gameTrackRecord().postTo(com.maddox.il2.game.Main3D.cur3D().gameTrackRecord().channel(), netmsgguaranted);
            }
            catch(java.lang.Exception exception) { }
        com.maddox.il2.game.Main3D.cur3D().hud._logCenter(s);
    }

    public void _logCenter(java.lang.String s)
    {
        if(s == null)
        {
            logCenter = null;
            return;
        }
        try
        {
            logCenter = resLog.getString(s);
        }
        catch(java.lang.Exception exception)
        {
            logCenter = s;
        }
        logCenterTime = com.maddox.rts.Time.current();
    }

    public static void logCoopTimeStart(long l)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.game.Main3D.cur3D().hud._logCoopTimeStart(l);
            return;
        }
    }

    public void _logCoopTimeStart(long l)
    {
        bCoopTimeStart = true;
        coopTimeStart = l;
    }

    public boolean netInputLog(int i, com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            int j = netmsginput.readInt();
            java.lang.String s2 = netmsginput.read255();
            java.lang.Object aobj[] = new java.lang.Object[1];
            aobj[0] = new Integer(netmsginput.readInt());
            _log(j, s2, aobj);
            break;

        case 1: // '\001'
            int k = netmsginput.readInt();
            java.lang.String s3 = netmsginput.read255();
            _log(k, s3);
            break;

        case 2: // '\002'
            java.lang.String s = netmsginput.read255();
            if("".equals(s))
                s = null;
            _logRightBottom(s);
            break;

        case 3: // '\003'
            java.lang.String s1 = netmsginput.read255();
            if("".equals(s1))
                s1 = null;
            _logCenter(s1);
            break;
        }
        return true;
    }

    public void clearLog()
    {
        logRightBottom = null;
        logPtr = 0;
        logLen = 0;
        logCenter = null;
        logIntro = null;
        logIntroESC = null;
        bCoopTimeStart = false;
    }

    private void initLog()
    {
        clearLog();
        resLog = java.util.ResourceBundle.getBundle("i18n/hud_log", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
        fntCenter = com.maddox.il2.engine.TTFont.font[2];
    }

    private void renderLog()
    {
        long l = com.maddox.rts.Time.current();
        if(bCoopTimeStart)
        {
            int i = (int)(coopTimeStart - com.maddox.rts.Time.currentReal());
            if(i < 0)
                bCoopTimeStart = false;
            else
            if(bDrawAllMessages)
            {
                com.maddox.il2.engine.TTFont ttfont5 = fntCenter;
                java.lang.String s = "" + (i + 500) / 1000;
                float f3 = ttfont5.width(s);
                ttfont5.output(0xff00ffff, ((float)viewDX - f3) / 2.0F, (float)viewDY * 0.75F, 0.0F, s);
            }
        } else
        if(logIntro != null)
        {
            com.maddox.il2.engine.TTFont ttfont = fntCenter;
            float f = ttfont.width(logIntro);
            int i1 = 0xff000000;
            ttfont.output(i1, ((float)viewDX - f) / 2.0F, (float)viewDY * 0.75F, 0.0F, logIntro);
        } else
        if(logCenter != null)
            if(l > logCenterTime + 5000L)
                logCenter = null;
            else
            if(bDrawAllMessages)
            {
                com.maddox.il2.engine.TTFont ttfont1 = fntCenter;
                float f1 = ttfont1.width(logCenter);
                int j1 = 0xff0000ff;
                int i2 = 255 - (int)(((double)(l - logCenterTime) / 5000D) * 255D);
                j1 |= i2 << 8;
                ttfont1.output(j1, ((float)viewDX - f1) / 2.0F, (float)viewDY * 0.75F, 0.0F, logCenter);
            }
        if(logIntroESC != null)
        {
            com.maddox.il2.engine.TTFont ttfont2 = com.maddox.il2.engine.TTFont.font[0];
            float f2 = ttfont2.width(logIntroESC);
            byte byte0 = -1;
            ttfont2.output(byte0, ((float)viewDX - f2) / 2.0F, (float)viewDY * 0.05F, 0.0F, logIntroESC);
        }
        if(!com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.isAfterburner())
            logRightBottom = null;
        if(logRightBottom != null && bDrawAllMessages)
        {
            com.maddox.il2.engine.TTFont ttfont3 = com.maddox.il2.engine.TTFont.font[1];
            int j = (int)((double)viewDX * 0.94999999999999996D);
            int k1 = (int)ttfont3.width(logRightBottom);
            int j2 = (int)((double)viewDY * 0.45000000000000001D - (double)(3 * ttfont3.height()));
            int l2 = 0xff0000ff;
            int j3 = (int)((510F * (float)((com.maddox.rts.Time.current() - logRightBottomTime) % 5000L)) / 5000F);
            if((j3 -= 255) < 0)
                j3 = -j3;
            ttfont3.output(l2 | j3 << 8, j - k1, j2, 0.0F, logRightBottom);
        }
        if(logLen == 0)
            return;
        for(; logLen > 0 && l >= logTime[logPtr] + 10000L; logLen--)
            logPtr = (logPtr + 1) % 3;

        if(logLen == 0)
            return;
        com.maddox.il2.engine.TTFont ttfont4 = com.maddox.il2.engine.TTFont.font[1];
        int k = (int)((double)viewDX * 0.94999999999999996D);
        int l1 = ttfont4.height();
        int k2 = (int)((double)viewDY * 0.45000000000000001D) - (3 - logLen) * l1;
        for(int i3 = 0; i3 < logLen; i3++)
        {
            int k3 = (logPtr + i3) % 3;
            int l3 = 0xffff0000;
            if(l < logTime[k3] + 5000L)
            {
                int i4 = (int)(((double)((logTime[k3] + 5000L) - l) / 5000D) * 255D);
                l3 |= i4 | i4 << 8;
            }
            float f4 = ttfont4.width(logBufStr[k3]);
            if(bDrawAllMessages)
                ttfont4.output(l3, (float)k - f4, k2, 0.0F, logBufStr[k3]);
            k2 -= l1;
        }

    }

    private int __log(int i, java.lang.String s)
    {
        if(logLen > 0 && i != 0)
        {
            int j = ((logPtr + logLen) - 1) % 3;
            if(logBufId[j] == i)
            {
                logTime[j] = com.maddox.rts.Time.current();
                logBuf[j] = s;
                return j;
            }
        }
        if(logLen >= 3)
        {
            logPtr = (logPtr + 1) % 3;
            logLen = 2;
        }
        int k = (logPtr + logLen) % 3;
        logBuf[k] = s;
        logBufId[k] = i;
        logTime[k] = com.maddox.rts.Time.current();
        logLen++;
        return k;
    }

    private void syncStatUser(int i, com.maddox.il2.net.NetUser netuser)
    {
        if(i == statUsers.size())
            statUsers.add(new StatUser());
        com.maddox.il2.game.StatUser statuser = (com.maddox.il2.game.StatUser)statUsers.get(i);
        statuser.user = netuser;
        if(statuser.iNum != i + 1 || statuser.sNum == null)
        {
            statuser.iNum = i + 1;
            statuser.sNum = statuser.iNum + ".";
        }
        if(statuser.iPing != netuser.ping || statuser.sPing == null)
        {
            statuser.iPing = netuser.ping;
            statuser.sPing = "(" + statuser.iPing + ")";
        }
        int j = (int)netuser.stat().score;
        if(statuser.iScore != j || statuser.sScore == null)
        {
            statuser.iScore = j;
            statuser.sScore = "" + statuser.iScore;
        }
        if(statuser.iArmy != netuser.getArmy() || statuser.sArmy == null)
        {
            statuser.iArmy = netuser.getArmy();
            statuser.sArmy = "(" + statuser.iArmy + ")" + com.maddox.il2.game.I18N.army(com.maddox.il2.ai.Army.name(statuser.iArmy));
        }
        if(!com.maddox.il2.engine.Actor.isAlive(statuser.aAircraft) || statuser.aAircraft.netUser() != netuser || statuser.sAircraft == null)
        {
            com.maddox.il2.objects.air.Aircraft aircraft = netuser.findAircraft();
            statuser.aAircraft = aircraft;
            if(aircraft == null)
            {
                statuser.sAircraft = "";
                statuser.sAircraftType = "";
            } else
            {
                statuser.sAircraft = aircraft.typedName();
                statuser.sAircraftType = com.maddox.il2.game.I18N.plane(com.maddox.rts.Property.stringValue(aircraft.getClass(), "keyName"));
            }
        }
    }

    private void syncNetStat()
    {
        syncStatUser(0, (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host());
        for(int i = 0; i < com.maddox.rts.NetEnv.hosts().size(); i++)
            syncStatUser(i + 1, (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.hosts().get(i));

        for(; statUsers.size() > com.maddox.rts.NetEnv.hosts().size() + 1; statUsers.remove(statUsers.size() - 1));
    }

    private int x1024(float f)
    {
        return (int)(((float)viewDX / 1024F) * f);
    }

    private int y1024(float f)
    {
        return (int)(((float)viewDY / 768F) * f);
    }

    public void startNetStat()
    {
        if(bDrawNetStat)
            return;
        if(!com.maddox.il2.game.Mission.isPlaying())
            return;
        if(com.maddox.il2.game.Mission.isSingle())
        {
            return;
        } else
        {
            syncNetStat();
            com.maddox.il2.engine.TTFont ttfont = com.maddox.il2.engine.TTFont.font[1];
            int i = ttfont.height() - ttfont.descender();
            int j = y1024(740F);
            int k = 2 * i;
            pageSizeNetStat = (j - k) / i;
            bDrawNetStat = true;
            return;
        }
    }

    public void stopNetStat()
    {
        if(!bDrawNetStat)
        {
            return;
        } else
        {
            statUsers.clear();
            bDrawNetStat = false;
            pageNetStat = 0;
            return;
        }
    }

    public boolean isDrawNetStat()
    {
        return bDrawNetStat;
    }

    public void pageNetStat()
    {
        if(!bDrawNetStat)
            return;
        pageNetStat++;
        if(pageSizeNetStat * pageNetStat > statUsers.size())
            pageNetStat = 0;
    }

    public void renderNetStat()
    {
        if(!bDrawNetStat)
            return;
        if(!com.maddox.il2.game.Mission.isPlaying())
            return;
        if(com.maddox.il2.game.Mission.isSingle())
            return;
        com.maddox.il2.engine.TTFont ttfont = com.maddox.il2.engine.TTFont.font[1];
        com.maddox.il2.engine.TTFont ttfont1 = com.maddox.il2.engine.TTFont.font[3];
        if(com.maddox.il2.game.Main.cur().netServerParams.netStat_DisableStatistics)
            return;
        int i = ttfont.height() - ttfont.descender();
        int j = y1024(740F);
        int k = 0;
        int l = 0;
        int i1 = 0;
        int j1 = 0;
        int k1 = 0;
        int l1 = 0;
        for(int i2 = pageSizeNetStat * pageNetStat; i2 < pageSizeNetStat * (pageNetStat + 1) && i2 < statUsers.size(); i2++)
        {
            com.maddox.il2.game.StatUser statuser = (com.maddox.il2.game.StatUser)statUsers.get(i2);
            int l2 = 0;
            if(com.maddox.il2.game.Main.cur().netServerParams.netStat_ShowPilotNumber)
            {
                l2 = (int)ttfont.width(statuser.sNum);
                if(k < l2)
                    k = l2;
            }
            if(com.maddox.il2.game.Main.cur().netServerParams.netStat_ShowPilotPing)
            {
                l2 = (int)ttfont1.width(statuser.sPing);
                if(l < l2)
                    l = l2;
            }
            if(com.maddox.il2.game.Main.cur().netServerParams.netStat_ShowPilotName)
            {
                l2 = (int)ttfont.width(statuser.user.uniqueName());
                if(i1 < l2)
                    i1 = l2;
            }
            if(com.maddox.il2.game.Main.cur().netServerParams.netStat_ShowPilotScore)
            {
                l2 = (int)ttfont.width(statuser.sScore);
                if(j1 < l2)
                    j1 = l2;
            }
            if(com.maddox.il2.game.Main.cur().netServerParams.netStat_ShowPilotArmy)
            {
                l2 = (int)ttfont.width(statuser.sArmy);
                if(k1 < l2)
                    k1 = l2;
            }
            if(!com.maddox.il2.game.Main.cur().netServerParams.netStat_ShowPilotACDesignation)
                continue;
            l2 = (int)ttfont.width(statuser.sAircraft);
            if(l1 < l2)
                l1 = l2;
        }

        int j2 = x1024(40F) + k;
        int k2 = j2 + l + x1024(16F);
        int i3 = k2 + i1 + x1024(16F);
        int j3 = i3 + j1 + x1024(16F);
        if(com.maddox.il2.game.Mission.isCoop())
            j3 = i3;
        int k3 = j3 + k1 + x1024(16F);
        int l3 = k3 + l1 + x1024(16F);
        int i4 = j;
        for(int j4 = pageSizeNetStat * pageNetStat; j4 < pageSizeNetStat * (pageNetStat + 1) && j4 < statUsers.size(); j4++)
        {
            com.maddox.il2.game.StatUser statuser1 = (com.maddox.il2.game.StatUser)statUsers.get(j4);
            i4 -= i;
            int k4 = com.maddox.il2.ai.Army.color(statuser1.iArmy);
            if(!com.maddox.il2.game.Main.cur().netServerParams.netStat_ShowPilotArmy)
                k4 = -1;
            if(com.maddox.il2.game.Main.cur().netServerParams.netStat_ShowPilotNumber)
                ttfont.output(k4, (float)j2 - ttfont.width(statuser1.sNum), i4, 0.0F, statuser1.sNum);
            if(com.maddox.il2.game.Main.cur().netServerParams.netStat_ShowPilotPing)
                ttfont1.output(-1, (float)k2 - ttfont1.width(statuser1.sPing) - (float)x1024(4F), i4, 0.0F, statuser1.sPing);
            if(com.maddox.il2.game.Main.cur().netServerParams.netStat_ShowPilotName)
                ttfont.output(k4, k2, i4, 0.0F, statuser1.user.uniqueName());
            if(!com.maddox.il2.game.Mission.isCoop() && com.maddox.il2.game.Main.cur().netServerParams.netStat_ShowPilotScore)
                ttfont.output(k4, i3, i4, 0.0F, statuser1.sScore);
            if(com.maddox.il2.game.Main.cur().netServerParams.netStat_ShowPilotArmy)
                ttfont.output(k4, j3, i4, 0.0F, statuser1.sArmy);
            if(com.maddox.il2.game.Main.cur().netServerParams.netStat_ShowPilotACDesignation)
                ttfont.output(k4, k3, i4, 0.0F, statuser1.sAircraft);
            if(com.maddox.il2.game.Main.cur().netServerParams.netStat_ShowPilotACType)
                ttfont.output(k4, l3, i4, 0.0F, statuser1.sAircraftType);
        }

    }

    public static void addPointer(float f, float f1, int i, float f2, float f3)
    {
        com.maddox.il2.game.Main3D.cur3D().hud._addPointer(f, f1, i, f2, f3);
    }

    private void _addPointer(float f, float f1, int i, float f2, float f3)
    {
        if(nPointers == pointers.size())
        {
            pointers.add(new Ptr(f, f1, i, f2, f3));
        } else
        {
            com.maddox.il2.game.Ptr ptr = (com.maddox.il2.game.Ptr)pointers.get(nPointers);
            ptr.set(f, f1, i, f2, f3);
        }
        nPointers++;
    }

    private void renderPointers()
    {
        if(nPointers == 0)
            return;
        float f = (float)viewDX / 1024F;
        int i = com.maddox.il2.engine.IconDraw.scrSizeX();
        int j = com.maddox.il2.engine.IconDraw.scrSizeY();
        for(int k = 0; k < nPointers; k++)
        {
            com.maddox.il2.game.Ptr ptr = (com.maddox.il2.game.Ptr)pointers.get(k);
            int l = (int)(64F * f * ptr.alpha);
            com.maddox.il2.engine.IconDraw.setScrSize(l, l);
            com.maddox.il2.engine.IconDraw.setColor(ptr.color & 0xffffff | (int)(ptr.alpha * 255F) << 24);
            com.maddox.il2.engine.IconDraw.render(spritePointer, ptr.x, ptr.y, 90F - ptr.angle);
        }

        com.maddox.il2.engine.IconDraw.setScrSize(i, j);
        nPointers = 0;
    }

    public void clearPointers()
    {
        nPointers = 0;
    }

    private void initPointers()
    {
        spritePointer = com.maddox.il2.engine.Mat.New("gui/game/hud/pointer.mat");
    }

    private void preRenderDashBoard()
    {
        if(!com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.getPlayerAircraft()))
            return;
        if(!com.maddox.il2.engine.Actor.isValid(main3d.cockpitCur))
            return;
        if(main3d.isViewOutside())
        {
            if(main3d.viewActor() != com.maddox.il2.ai.World.getPlayerAircraft())
                return;
            if(!main3d.cockpitCur.isNullShow())
                return;
        } else
        if(main3d.isViewInsideShow())
            return;
        if(!bDrawDashBoard)
        {
            return;
        } else
        {
            spriteLeft.preRender();
            spriteRight.preRender();
            spriteG.preRender();
            meshNeedle1.preRender();
            meshNeedle2.preRender();
            meshNeedle3.preRender();
            meshNeedle4.preRender();
            meshNeedle5.preRender();
            meshNeedle6.preRender();
            meshNeedleMask.preRender();
            return;
        }
    }

    private void renderDashBoard()
    {
        if(!com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.getPlayerAircraft()))
            return;
        if(!com.maddox.il2.engine.Actor.isValid(main3d.cockpitCur))
            return;
        if(main3d.isViewOutside())
        {
            if(main3d.viewActor() != com.maddox.il2.ai.World.getPlayerAircraft())
                return;
            if(!main3d.cockpitCur.isNullShow())
                return;
        } else
        if(main3d.isViewInsideShow())
            return;
        if(!bDrawDashBoard)
            return;
        float f = viewDX;
        float f1 = viewDY;
        float f2 = f / 1024F;
        float f3 = f1 / 768F;
        com.maddox.il2.engine.Render.drawTile(0.0F, 0.0F, 256F * f2, 256F * f3, 0.0F, spriteLeft, -1, 0.0F, 1.0F, 1.0F, -1F);
        com.maddox.il2.engine.Render.drawTile(768F * f2, 0.0F, 256F * f2, 256F * f3, 0.0F, spriteRight, -1, 0.0F, 1.0F, 1.0F, -1F);
        com.maddox.il2.engine.Render.drawTile(200F * f2, 168F * f3, 64F * f2, 64F * f3, 0.0F, spriteG, -1, 0.0F, 1.0F, 1.0F, -1F);
        com.maddox.JGP.Point3d point3d = com.maddox.il2.ai.World.getPlayerAircraft().pos.getAbsPoint();
        com.maddox.il2.engine.Orient orient = com.maddox.il2.ai.World.getPlayerAircraft().pos.getAbsOrient();
        float f4 = (float)(point3d.z - com.maddox.il2.ai.World.land().HQ(point3d.x, point3d.y));
        _p.x = 172F * f2;
        _p.y = 84F * f3;
        _o.set(cvt(f4, 0.0F, 10000F, 0.0F, 3600F), 0.0F, 0.0F);
        meshNeedle2.setPos(_p, _o);
        meshNeedle2.render();
        _o.set(cvt(f4, 0.0F, 10000F, 0.0F, 360F), 0.0F, 0.0F);
        meshNeedle1.setPos(_p, _o);
        meshNeedle1.render();
        java.lang.String s = "" + (int)((double)f4 + 0.5D);
        float f6 = fntLcd.width(s);
        fntLcd.output(-1, 208F * f2 - f6, 70F * f3, 0.0F, s);
        if(f4 > 90F)
            meshNeedle5.setScale(90F * f2);
        else
            meshNeedle5.setScaleXYZ(90F * f2, 90F * f2, cvt(f4, 0.0F, 90F, 13F, 90F) * f2);
        f4 = (float)com.maddox.il2.ai.World.getPlayerAircraft().getSpeed(null);
        f4 *= 3.6F;
        _o.set(cvt(f4, 0.0F, 900F, 0.0F, 270F) + 180F, 0.0F, 0.0F);
        _p.x = 83F * f2;
        _p.y = 167F * f3;
        meshNeedle2.setPos(_p, _o);
        meshNeedle2.render();
        s = "" + (int)((double)f4 + 0.5D);
        f6 = fntLcd.width(s);
        fntLcd.output(-1, 104F * f2 - f6, 135F * f3, 0.0F, s);
        for(f4 = orient.azimut() + 90F; f4 < 0.0F; f4 += 360F);
        f4 %= 360F;
        _o.set(f4, 0.0F, 0.0F);
        _p.x = 939F * f2;
        _p.y = 167F * f3;
        meshNeedle3.setPos(_p, _o);
        meshNeedle3.render();
        s = "" + (int)((double)f4 + 0.5D);
        f6 = fntLcd.width(s);
        fntLcd.output(-1, 960F * f2 - f6, 216F * f3, 0.0F, s);
        com.maddox.il2.engine.Orient orient1 = main3d.camera3D.pos.getAbsOrient();
        _p.x = 511F * f2;
        _p.y = 96F * f3;
        if(orient1.tangage() < 0.0F)
        {
            _o1.set(orient1);
            _o1.increment(0.0F, 0.0F, 90F);
            _o1.increment(0.0F, 90F, 0.0F);
            _o.sub(_oNull, _o1);
            meshNeedle5.setPos(_p, _o);
            meshNeedle5.render();
        }
        _o1.set(orient1);
        _o1.increment(0.0F, 0.0F, 90F);
        _o1.increment(0.0F, 90F, 0.0F);
        _o.sub(orient, _o1);
        meshNeedle4.setPos(_p, _o);
        meshNeedle4.render();
        if(orient1.tangage() >= 0.0F)
        {
            _o1.set(orient1);
            _o1.increment(0.0F, 0.0F, 90F);
            _o1.increment(0.0F, 90F, 0.0F);
            _o.sub(_oNull, _o1);
            meshNeedle5.setPos(_p, _o);
            meshNeedle5.render();
        }
        _p.x = 851F * f2;
        _p.y = 84F * f3;
        _o1.set(orient);
        _o1.set(0.0F, -_o1.tangage(), _o1.kren());
        _o1.increment(0.0F, 0.0F, 90F);
        _o1.increment(0.0F, 90F, 0.0F);
        _o.sub(_oNull, _o1);
        meshNeedle6.setPos(_p, _o);
        meshNeedle6.render();
        _o.set(0.0F, 0.0F, 0.0F);
        meshNeedleMask.setPos(_p, _o);
        meshNeedleMask.render();
        orient1 = (int)(com.maddox.il2.ai.World.getPlayerFM().getOverload() * 10F);
        float f5 = (float)orient1 / 10F;
        java.lang.String s1 = "" + f5;
        float f7 = fntLcd.width(s1);
        if(com.maddox.il2.ai.World.getPlayerFM().getLoadDiff() < com.maddox.il2.ai.World.getPlayerFM().getLimitLoad() * 0.25F)
            fntLcd.output(0xff0000ff, 249F * f2 - f7, 182F * f3, 0.0F, s1);
        else
        if(orient1 < 0)
            fntLcd.output(0xff000000, 249F * f2 - f7, 182F * f3, 0.0F, s1);
        else
            fntLcd.output(-1, 249F * f2 - f7, 182F * f3, 0.0F, s1);
    }

    private float cvt(float f, float f1, float f2, float f3, float f4)
    {
        f = java.lang.Math.min(java.lang.Math.max(f, f1), f2);
        return f3 + ((f4 - f3) * (f - f1)) / (f2 - f1);
    }

    private void initDashBoard()
    {
        spriteLeft = com.maddox.il2.engine.Mat.New("gui/game/hud/hudleft.mat");
        spriteRight = com.maddox.il2.engine.Mat.New("gui/game/hud/hudright.mat");
        meshNeedle1 = new Mesh("gui/game/hud/needle1/mono.sim");
        meshNeedle2 = new Mesh("gui/game/hud/needle2/mono.sim");
        meshNeedle3 = new Mesh("gui/game/hud/needle3/mono.sim");
        meshNeedle4 = new Mesh("gui/game/hud/needle4/mono.sim");
        meshNeedle5 = new Mesh("gui/game/hud/needle5/mono.sim");
        meshNeedle6 = new Mesh("gui/game/hud/needle6/mono.sim");
        meshNeedleMask = new Mesh("gui/game/hud/needlemask/mono.sim");
        spriteG = com.maddox.il2.engine.Mat.New("gui/game/hud/hudg.mat");
        fntLcd = com.maddox.il2.engine.TTFont.get("lcdnova");
        setScales();
    }

    private void setScales()
    {
        float f = viewDX;
        float f1 = f / 1024F;
        meshNeedle1.setScale(140F * f1);
        meshNeedle2.setScale(140F * f1);
        meshNeedle3.setScale(75F * f1);
        meshNeedle4.setScale(100F * f1);
        meshNeedle5.setScale(90F * f1);
        meshNeedle6.setScale(150F * f1);
        meshNeedleMask.setScale(150F * f1);
    }

    public void render()
    {
        renderSpeed();
        renderOrder();
        renderMsg();
        renderTraining();
        renderLog();
        renderDashBoard();
        renderPointers();
        renderNetStat();
    }

    public void preRender()
    {
        preRenderDashBoard();
    }

    public void resetGame()
    {
        setScales();
        clearSpeed();
        clearOrder();
        clearMsg();
        clearTraining();
        clearLog();
        clearPointers();
        stopNetStat();
    }

    public void contextResize(int i, int j)
    {
        viewDX = main3d.renderHUD.getViewPortWidth();
        viewDY = main3d.renderHUD.getViewPortHeight();
        setScales();
        resetMsgSizes();
        resetTrainingSizes();
    }

    public HUD()
    {
        bDrawAllMessages = true;
        bDrawVoiceMessages = true;
        bNoSubTitles = false;
        subTitlesLines = 11;
        bNoHudLog = false;
        timeLoadLimit = 0L;
        iDrawSpeed = 1;
        lastDrawSpeed = -1;
        msgLines = new ArrayList();
        trainingLines = new ArrayList();
        bCoopTimeStart = false;
        logBuf = new java.lang.String[3];
        logBufStr = new java.lang.String[3];
        logBufId = new int[3];
        logTime = new long[3];
        logPtr = 0;
        logLen = 0;
        bDrawNetStat = false;
        pageNetStat = 0;
        pageSizeNetStat = 0;
        statUsers = new ArrayList();
        pointers = new ArrayList();
        nPointers = 0;
        bDrawDashBoard = true;
        _p = new Point3d();
        _o = new Orient();
        _o1 = new Orient();
        _oNull = new Orient(0.0F, 0.0F, 0.0F);
        main3d = com.maddox.il2.game.Main3D.cur3D();
        viewDX = main3d.renderHUD.getViewPortWidth();
        viewDY = main3d.renderHUD.getViewPortHeight();
        initSpeed();
        initOrder();
        initMsg();
        initTraining();
        initLog();
        initDashBoard();
        initPointers();
        bNoSubTitles = com.maddox.il2.engine.Config.cur.ini.get("game", "NoSubTitles", bNoSubTitles);
        subTitlesLines = com.maddox.il2.engine.Config.cur.ini.get("game", "SubTitlesLines", subTitlesLines);
        if(subTitlesLines < 1)
            subTitlesLines = 1;
        bNoHudLog = com.maddox.il2.engine.Config.cur.ini.get("game", "NoHudLog", bNoHudLog);
    }

    public boolean bDrawAllMessages;
    public boolean bDrawVoiceMessages;
    private boolean bNoSubTitles;
    private int subTitlesLines;
    private boolean bNoHudLog;
    private com.maddox.il2.game.Main3D main3d;
    private int viewDX;
    private int viewDY;
    long timeLoadLimit;
    int cnt;
    private java.lang.String renderSpeedSubstrings[][] = {
        {
            null, null, null
        }, {
            null, null, null
        }, {
            null, null, null
        }, {
            null, null, null
        }
    };
    private int iDrawSpeed;
    private int lastDrawSpeed;
    private com.maddox.il2.game.order.Order order[];
    private java.lang.String orderStr[];
    public java.util.ResourceBundle resOrder;
    public java.util.ResourceBundle resMsg;
    private int msgX0;
    private int msgY0;
    private int msgDX;
    private int msgDY;
    private int msgSIZE;
    private int msgSpaceLen;
    private java.util.ArrayList msgLines;
    private int msgColor[][] = {
        {
            0xcf0000ff, 0xcf0000ff, 0xcf0000ff, 0xcf005f9f, 0xcf003fdf, 0xcf0000ff, 0xcf0000ff, 0xcf0000ff, 0xcf0000ff
        }, {
            0xcfff0000, 0xcfff0000, 0xcfff0000, 0xcf9f5f00, 0xcfdf3f00, 0xcfff0000, 0xcfff0000, 0xcfff0000, 0xcfff0000
        }
    };
    private int trainingX0;
    private int trainingY0;
    private int trainingDX;
    private int trainingDY;
    private int trainingSIZE;
    private int trainingSpaceLen;
    private java.util.ArrayList trainingLines;
    private static int idLog = 1;
    public java.util.ResourceBundle resLog;
    private java.lang.String logRightBottom;
    private long logRightBottomTime;
    private static final long logCenterTimeLife = 5000L;
    private java.lang.String logCenter;
    private long logCenterTime;
    private java.lang.String logIntro;
    private java.lang.String logIntroESC;
    private com.maddox.il2.engine.TTFont fntCenter;
    private boolean bCoopTimeStart;
    private long coopTimeStart;
    private static final int lenLogBuf = 3;
    private static final long logTimeLife = 10000L;
    private static final long logTimeFire = 5000L;
    private java.lang.String logBuf[];
    private java.lang.String logBufStr[];
    private int logBufId[];
    private long logTime[];
    private int logPtr;
    private int logLen;
    private boolean bDrawNetStat;
    private int pageNetStat;
    private int pageSizeNetStat;
    java.util.ArrayList statUsers;
    private java.util.ArrayList pointers;
    private int nPointers;
    private com.maddox.il2.engine.Mat spritePointer;
    public boolean bDrawDashBoard;
    private com.maddox.JGP.Point3d _p;
    private com.maddox.il2.engine.Orient _o;
    private com.maddox.il2.engine.Orient _o1;
    private com.maddox.il2.engine.Orient _oNull;
    private com.maddox.il2.engine.Mat spriteLeft;
    private com.maddox.il2.engine.Mat spriteRight;
    private com.maddox.il2.engine.Mat spriteG;
    private com.maddox.il2.engine.Mesh meshNeedle1;
    private com.maddox.il2.engine.Mesh meshNeedle2;
    private com.maddox.il2.engine.Mesh meshNeedle3;
    private com.maddox.il2.engine.Mesh meshNeedle4;
    private com.maddox.il2.engine.Mesh meshNeedle5;
    private com.maddox.il2.engine.Mesh meshNeedle6;
    private com.maddox.il2.engine.Mesh meshNeedleMask;
    private com.maddox.il2.engine.TTFont fntLcd;

}

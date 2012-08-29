// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   ConsoleGL0Render.java

package com.maddox.il2.engine;

import com.maddox.rts.Console;
import com.maddox.rts.MainWin32;
import com.maddox.rts.RTSConf;
import com.maddox.rts.RTSConfWin;
import com.maddox.rts.Time;
import java.util.List;
import java.util.Locale;

// Referenced classes of package com.maddox.il2.engine:
//            Render, GObj, Renders, ConsoleGL0, 
//            RendersMain, TTFont

class ConsoleGL0Render extends com.maddox.il2.engine.Render
{

    public void exlusiveDraw()
    {
        if(com.maddox.rts.RTSConf.cur instanceof com.maddox.rts.RTSConfWin)
            ((com.maddox.rts.MainWin32)com.maddox.rts.RTSConf.cur.mainWindow).loopMsgs();
        com.maddox.il2.engine.GObj.DeleteCppObjects();
        renders().paint(((com.maddox.il2.engine.Render) (this)));
    }

    public void exlusiveDrawStep(java.lang.String s, int i)
    {
        sstep = s;
        istep = i;
        renders().paint(((com.maddox.il2.engine.Render) (this)));
    }

    public void render()
    {
        if(com.maddox.il2.engine.ConsoleGL0.backgroundMat != null)
        {
            com.maddox.il2.engine.ConsoleGL0Render.drawTile(0.0F, 0.0F, com.maddox.il2.engine.RendersMain.getViewPortWidth(), com.maddox.il2.engine.RendersMain.getViewPortHeight(), 0.0F, com.maddox.il2.engine.ConsoleGL0.backgroundMat, -1, 0.0F, 1.0F, 1.0F, -1F);
            if(sstep != null)
            {
                float f = com.maddox.il2.engine.TTFont.font[2].width(sstep);
                int i = 0xff0000ff;
                if("ru".equalsIgnoreCase(com.maddox.rts.RTSConf.cur.locale.getLanguage()))
                {
                    com.maddox.il2.engine.TTFont.font[2].output(i, (float)com.maddox.il2.engine.RendersMain.getViewPortWidth() * 0.083F, (float)com.maddox.il2.engine.RendersMain.getViewPortHeight() * 0.12F, 0.0F, sstep);
                    com.maddox.il2.engine.TTFont.font[2].output(i, (float)com.maddox.il2.engine.RendersMain.getViewPortWidth() * 0.083F, ((float)com.maddox.il2.engine.RendersMain.getViewPortHeight() * 0.12F + (float)com.maddox.il2.engine.TTFont.font[2].height()) - (float)com.maddox.il2.engine.TTFont.font[2].descender(), 0.0F, "UP 3.0RC (Based on V 4.10.1m)");
                } else
                {
                    com.maddox.il2.engine.TTFont.font[2].output(i, (float)com.maddox.il2.engine.RendersMain.getViewPortWidth() * 0.02F, (float)com.maddox.il2.engine.RendersMain.getViewPortHeight() * 0.17F, 0.0F, sstep);
                    com.maddox.il2.engine.TTFont.font[2].output(i, (float)com.maddox.il2.engine.RendersMain.getViewPortWidth() * 0.02F, ((float)com.maddox.il2.engine.RendersMain.getViewPortHeight() * 0.17F + (float)com.maddox.il2.engine.TTFont.font[2].height()) - (float)com.maddox.il2.engine.TTFont.font[2].descender(), 0.0F, "UP 3.0RC (Based on V 4.10.1m)");
                }
            }
            return;
        }
        if(com.maddox.il2.engine.ConsoleGL0.bActive || com.maddox.il2.engine.ConsoleGL0.consoleListener != null)
        {
            java.util.List list = com.maddox.rts.RTSConf.cur.console.historyOut();
            int j = com.maddox.rts.RTSConf.cur.console.startHistoryOut();
            if(!com.maddox.rts.RTSConf.cur.console.isShowHistoryOut())
            {
                list = com.maddox.rts.RTSConf.cur.console.historyCmd();
                j = com.maddox.rts.RTSConf.cur.console.startHistoryCmd();
            }
            int k = getViewPortHeight() / com.maddox.il2.engine.ConsoleGL0.font.height() - 1;
            if(com.maddox.il2.engine.ConsoleGL0.bActive)
            {
                int l = com.maddox.rts.RTSConf.cur.console.editBuf.length();
                java.lang.String s = com.maddox.rts.RTSConf.cur.console.getPrompt();
                int l1 = s.length();
                int i2 = 0;
                if(l1 != 0)
                    s.getChars(0, l1, buf, 0);
                if(l + l1 > 0)
                {
                    if(l + l1 > buf.length)
                        buf = new char[l + l1 + 16];
                    if(l != 0)
                    {
                        com.maddox.rts.RTSConf.cur.console.editBuf.getChars(0, l, buf, l1);
                        float f2 = (float)getViewPortWidth() - com.maddox.il2.engine.ConsoleGL0.typeOffset;
                        do
                        {
                            float f4 = com.maddox.il2.engine.ConsoleGL0.font.width(buf, 0, (com.maddox.rts.RTSConf.cur.console.editPos - i2) + l1);
                            if(f4 < f2)
                                break;
                            i2++;
                            com.maddox.rts.RTSConf.cur.console.editBuf.getChars(i2, l, buf, l1);
                        } while(true);
                    }
                    com.maddox.il2.engine.ConsoleGL0.font.output(-1, com.maddox.il2.engine.ConsoleGL0.typeOffset, -com.maddox.il2.engine.ConsoleGL0.font.descender(), 0.0F, buf, 0, (l - i2) + l1);
                }
                if((com.maddox.rts.Time.endReal() / 500L) % 3L != 0L)
                {
                    float f3 = 0.0F;
                    if(com.maddox.rts.RTSConf.cur.console.editPos + l1 > 0)
                        f3 = com.maddox.il2.engine.ConsoleGL0.font.width(buf, 0, (com.maddox.rts.RTSConf.cur.console.editPos - i2) + l1);
                    buf[0] = '|';
                    com.maddox.il2.engine.ConsoleGL0.font.output(-1, (com.maddox.il2.engine.ConsoleGL0.typeOffset + f3) - 1.0F, -com.maddox.il2.engine.ConsoleGL0.font.descender(), 0.0F, buf, 0, 1);
                }
            }
            if(com.maddox.rts.RTSConf.cur.console.bWrap)
            {
                int i1 = j;
                int k1 = 1;
                for(; k > 0 && i1 < list.size(); i1++)
                {
                    java.lang.String s1 = (java.lang.String)list.get(i1);
                    int j2;
                    for(j2 = s1.length() - 1; j2 >= 0 && s1.charAt(j2) < ' '; j2--);
                    if(j2 > 0)
                    {
                        j2++;
                        int l2 = 0;
                        int i3 = 0;
                        int j3 = j2;
                        ofs[l2] = i3;
                        do
                        {
                            if(j3 <= 0 || com.maddox.il2.engine.ConsoleGL0.font.width(s1, i3, j3) <= 0.0F)
                                break;
                            int k3 = j3;
                            do
                            {
                                if(com.maddox.il2.engine.ConsoleGL0.font.width(s1, i3, j3) + com.maddox.il2.engine.ConsoleGL0.typeOffset <= (float)getViewPortWidth())
                                    break;
                                while(--j3 > 0 && s1.charAt(i3 + j3) != ' ') ;
                            } while(j3 != 0);
                            if(j3 == 0)
                                for(j3 = k3; com.maddox.il2.engine.ConsoleGL0.font.width(s1, i3, j3) + com.maddox.il2.engine.ConsoleGL0.typeOffset > (float)getViewPortWidth() && --j3 != 0;);
                            if(l2 + 1 >= ofs.length)
                                break;
                            l2++;
                            if(j3 == 0)
                            {
                                ofs[l2] = j2;
                                break;
                            }
                            i3 += j3;
                            j3 = j2 - i3;
                            ofs[l2] = i3;
                        } while(true);
                        for(; l2 > 0 && k > 0; k--)
                        {
                            com.maddox.il2.engine.ConsoleGL0.font.output(-1, com.maddox.il2.engine.ConsoleGL0.typeOffset, com.maddox.il2.engine.ConsoleGL0.font.height() * k1 - com.maddox.il2.engine.ConsoleGL0.font.descender(), 0.0F, s1, ofs[l2 - 1], ofs[l2] - ofs[l2 - 1]);
                            k1++;
                            l2--;
                        }

                    }
                }

            } else
            {
                if(k > list.size() - j)
                    k = list.size() - j;
                for(int j1 = 0; j1 < k; j1++)
                {
                    float f1 = com.maddox.il2.engine.ConsoleGL0.font.height() * (j1 + 1) - com.maddox.il2.engine.ConsoleGL0.font.descender();
                    java.lang.String s2 = (java.lang.String)list.get(j1 + j);
                    int k2;
                    for(k2 = s2.length() - 1; k2 >= 0 && s2.charAt(k2) < ' '; k2--);
                    if(k2 > 0)
                        com.maddox.il2.engine.ConsoleGL0.font.output(-1, com.maddox.il2.engine.ConsoleGL0.typeOffset, f1, 0.0F, s2, 0, k2 + 1);
                }

            }
        }
    }

    public ConsoleGL0Render(float f)
    {
        super(f);
        buf = new char[128];
        ofs = new int[128];
        sstep = null;
        istep = 0;
        useClearDepth(false);
        useClearColor(false);
    }

    public static final int COLOR = -1;
    private char buf[];
    private int ofs[];
    public java.lang.String sstep;
    private int istep;
}

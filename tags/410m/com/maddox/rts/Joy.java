// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Joy.java

package com.maddox.rts;

import com.maddox.util.NumberTokenizer;

// Referenced classes of package com.maddox.rts:
//            MsgJoy, Listeners, MessageCache, MsgAddListenerListener, 
//            MsgRemoveListenerListener, RTSConf, Message, Time, 
//            IniFile, JoyFF

public class Joy
    implements com.maddox.rts.MsgAddListenerListener, com.maddox.rts.MsgRemoveListenerListener
{

    public static float normal(int i)
    {
        return (float)i * 0.008F;
    }

    public static com.maddox.rts.Joy adapter()
    {
        return com.maddox.rts.RTSConf.cur.joy;
    }

    public boolean isAttached()
    {
        return bAttached;
    }

    public int buttons(int i)
    {
        return countButtons[i];
    }

    public int axes(int i)
    {
        return countAxes[i];
    }

    public int POVs(int i)
    {
        return countPOVs[i];
    }

    public int caps(int i)
    {
        return caps[i];
    }

    public boolean isExistAxe(int i, int j)
    {
        if(j < 0)
            return false;
        if(j > 7)
            return false;
        else
            return (caps[i] & 1 << j) != 0;
    }

    public boolean isExistPov(int i, int j)
    {
        if(j < 0)
            return false;
        if(j >= 4)
            return false;
        else
            return (caps[i] & 1 << j + 8) != 0;
    }

    public static java.lang.String axeName(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            return "X";

        case 1: // '\001'
            return "Y";

        case 2: // '\002'
            return "Z";

        case 3: // '\003'
            return "RX";

        case 4: // '\004'
            return "RY";

        case 5: // '\005'
            return "RZ";

        case 6: // '\006'
            return "U";

        case 7: // '\007'
            return "V";
        }
        return null;
    }

    public boolean isPressed(int i, int j)
    {
        if(j < 0 || j >= 70)
            return false;
        else
            return buttons[i][j];
    }

    public java.lang.Object[] getListeners()
    {
        return listeners.get();
    }

    public java.lang.Object[] getRealListeners()
    {
        return realListeners.get();
    }

    public void getPos(int i, int ai[])
    {
        for(int j = 0; j < 8; j++)
        {
            boolean flag = filterUpdated[i][j];
            filterUpdated[i][j] = true;
            updateMove(i, j);
            ai[j] = cur_mov[i][j];
            filterUpdated[i][j] = flag;
        }

    }

    public void getNotFilteredPos(int i, int ai[])
    {
        for(int j = 0; j < 8; j++)
            ai[j] = mov[i][j];

    }

    public void getPov(int i, int ai[])
    {
        for(int j = 0; j < 4; j++)
            ai[j] = pov[i][j];

    }

    public void getSensitivity(int i, int j, int ai[])
    {
        for(int k = 0; k < 13; k++)
            ai[k] = koof[i][j][k];

    }

    public void setSensitivity(int i, int j, int ai[])
    {
        for(int k = 0; k < 13; k++)
            koof[i][j][k] = ai[k];

    }

    public void msgAddListener(java.lang.Object obj, java.lang.Object obj1)
    {
        if(com.maddox.rts.Message.current().isRealTime())
            realListeners.addListener(obj);
        else
            listeners.addListener(obj);
    }

    public void msgRemoveListener(java.lang.Object obj, java.lang.Object obj1)
    {
        if(com.maddox.rts.Message.current().isRealTime())
            realListeners.removeListener(obj);
        else
            listeners.removeListener(obj);
    }

    private com.maddox.rts.MsgJoy msgNext(boolean flag, long l)
    {
        com.maddox.rts.MsgJoy msgjoy = (com.maddox.rts.MsgJoy)cache.get();
        msgjoy.setTickPos(0x7ffffffe);
        if(flag)
        {
            msgjoy.setFlags(64);
            msgjoy.setTime(l);
        } else
        {
            msgjoy.setFlags(0);
            msgjoy.setTime(com.maddox.rts.Time.current());
        }
        return msgjoy;
    }

    private void postButton(long l, int i, int j, boolean flag)
    {
        if(focus != null)
        {
            com.maddox.rts.MsgJoy msgjoy = msgNext(true, l);
            msgjoy.setButton(i, j, flag);
            msgjoy.post(focus);
            return;
        }
        java.lang.Object aobj[] = realListeners.get();
        if(aobj != null)
        {
            com.maddox.rts.MsgJoy msgjoy1 = msgNext(true, l);
            msgjoy1.setButton(i, j, flag);
            msgjoy1.post(((java.lang.Object) (aobj)));
        }
        if(!com.maddox.rts.Time.isPaused())
        {
            java.lang.Object aobj1[] = listeners.get();
            if(aobj1 != null)
            {
                com.maddox.rts.MsgJoy msgjoy2 = msgNext(false, l);
                msgjoy2.setButton(i, j, flag);
                msgjoy2.post(((java.lang.Object) (aobj1)));
            }
        }
    }

    private void postMove(long l, int i, int j, int k)
    {
        postMove(true, l, i, j, k);
    }

    private void postMove(boolean flag, long l, int i, int j, int k)
    {
        if(focus != null)
        {
            com.maddox.rts.MsgJoy msgjoy = msgNext(true, l);
            msgjoy.setMove(i, j, k);
            msgjoy.post(focus);
            return;
        }
        java.lang.Object aobj[] = realListeners.get();
        if(aobj != null)
        {
            com.maddox.rts.MsgJoy msgjoy1 = msgNext(true, l);
            msgjoy1.setMove(i, j, k);
            msgjoy1.post(((java.lang.Object) (aobj)));
        }
        if(!flag || !com.maddox.rts.Time.isPaused())
        {
            java.lang.Object aobj1[] = listeners.get();
            if(aobj1 != null)
            {
                com.maddox.rts.MsgJoy msgjoy2 = msgNext(false, l);
                msgjoy2.setMove(i, j, k);
                msgjoy2.post(((java.lang.Object) (aobj1)));
            }
        }
    }

    private int idPov(int i)
    {
        if(i == -1)
            return 571;
        if(i > 337)
            return 572;
        if(i > 292)
            return 579;
        if(i > 247)
            return 578;
        if(i > 202)
            return 577;
        if(i > 157)
            return 576;
        if(i > 112)
            return 575;
        if(i > 67)
            return 574;
        return i <= 22 ? 572 : 573;
    }

    private void postPov(long l, int i, int j, int k)
    {
        int i1 = i * 4 + j;
        int j1 = idPov(k);
        if(focus != null)
        {
            com.maddox.rts.MsgJoy msgjoy = msgNext(true, l);
            msgjoy.setPov(i1, j1);
            msgjoy.post(focus);
            return;
        }
        java.lang.Object aobj[] = realListeners.get();
        if(aobj != null)
        {
            com.maddox.rts.MsgJoy msgjoy1 = msgNext(true, l);
            msgjoy1.setPov(i1, j1);
            msgjoy1.post(((java.lang.Object) (aobj)));
        }
        if(!com.maddox.rts.Time.isPaused())
        {
            java.lang.Object aobj1[] = listeners.get();
            if(aobj1 != null)
            {
                com.maddox.rts.MsgJoy msgjoy2 = msgNext(false, l);
                msgjoy2.setPov(i1, j1);
                msgjoy2.post(((java.lang.Object) (aobj1)));
            }
        }
    }

    private void postPoll(long l)
    {
        if(focus != null)
        {
            com.maddox.rts.MsgJoy msgjoy = msgNext(true, l);
            msgjoy.setPoll();
            msgjoy.post(focus);
            return;
        }
        java.lang.Object aobj[] = realListeners.get();
        if(aobj != null)
        {
            com.maddox.rts.MsgJoy msgjoy1 = msgNext(true, l);
            msgjoy1.setPoll();
            msgjoy1.post(((java.lang.Object) (aobj)));
        }
        if(!com.maddox.rts.Time.isPaused())
        {
            java.lang.Object aobj1[] = listeners.get();
            if(aobj1 != null)
            {
                com.maddox.rts.MsgJoy msgjoy2 = msgNext(false, l);
                msgjoy2.setPoll();
                msgjoy2.post(((java.lang.Object) (aobj1)));
            }
        }
    }

    public void setAttached(boolean flag)
    {
        bAttached = flag;
    }

    public void setAmount(int i)
    {
        amount = i;
    }

    public void setCaps(int i, int j, int k, int l, int i1)
    {
        countButtons[i] = j;
        countAxes[i] = k;
        countPOVs[i] = l;
        caps[i] = i1;
    }

    public java.lang.Object focus()
    {
        return focus;
    }

    public void setFocus(java.lang.Object obj)
    {
        focus = obj;
    }

    public void setEnable(boolean flag)
    {
        bEnabled = flag;
    }

    public boolean isEnable()
    {
        return bEnabled;
    }

    public void setPress(long l, int i, int j)
    {
        if(!bEnabled)
            return;
        if(j < 0 || j >= 70)
            return;
        if(buttons[i][j])
        {
            return;
        } else
        {
            buttons[i][j] = true;
            postButton(l, i, j, true);
            return;
        }
    }

    public void setRelease(long l, int i, int j)
    {
        if(!bEnabled)
            return;
        if(j < 0 || j >= 70)
            return;
        if(!buttons[i][j])
        {
            return;
        } else
        {
            buttons[i][j] = false;
            postButton(l, i, j, false);
            return;
        }
    }

    public void clear()
    {
        if(!bEnabled)
        {
            return;
        } else
        {
            _clear();
            return;
        }
    }

    protected void _clear()
    {
        for(int i = 0; i < 4; i++)
        {
            for(int k = 0; k < 70; k++)
                if(buttons[i][k])
                    setRelease(com.maddox.rts.Time.currentReal(), i, k);

        }

        for(int j = 0; j < 4; j++)
        {
            for(int l = 0; l < 4; l++)
                pov[j][l] = -1;

        }

    }

    public void rePostMoves()
    {
        long l = com.maddox.rts.Time.currentReal();
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 8; j++)
                if(cur_mov[i][j] != -1)
                    postMove(false, l, i, j, cur_mov[i][j]);

        }

    }

    public void setMove(long l, int i, int j, int k)
    {
        if(!bEnabled)
            return;
        mov[i][j] = k;
        if(updateMove(i, j))
            postMove(l, i, j, cur_mov[i][j]);
    }

    public void setPov(long l, int i, int j, int k)
    {
        if(!bEnabled)
        {
            return;
        } else
        {
            pov[i][j] = k;
            postPov(l, i, j, k);
            return;
        }
    }

    public void poll(long l)
    {
        if(!bEnabled)
            return;
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(filterUpdate(i, j) && updateMove(i, j))
                    postMove(l, i, j, cur_mov[i][j]);
                filterUpdated[i][j] = false;
            }

        }

        postPoll(l);
    }

    private boolean filterUpdate(int i, int j)
    {
        if(filterUpdated[i][j] || koof[i][j][11] == 0)
            return false;
        filterUpdated[i][j] = true;
        int k = koof[i][j][11];
        if(mov[i][j] == -1)
            return false;
        if(mov[i][j] < -125)
            mov[i][j] = -125;
        if(mov[i][j] > 125)
            mov[i][j] = 125;
        float f = (0.8F * (float)k) / 100F;
        int l = java.lang.Math.round((float)mov[i][j] * (1.0F - f) + (float)filter[i][j] * f);
        if(l == -1)
            l++;
        boolean flag = filter[i][j] != l;
        filter[i][j] = l;
        return flag;
    }

    public boolean updateMoveMW2(int i, int j)
    {
        int k = 0;
        if(mov[i][j] == -125)
        {
            k = -125;
        } else
        {
            int l;
            if(koof[i][j][11] == 0)
            {
                if(mov[i][j] < -125)
                    mov[i][j] = -125;
                if(mov[i][j] > 125)
                    mov[i][j] = 125;
                l = mov[i][j];
            } else
            {
                filterUpdate(i, j);
                if(filter[i][j] < -125)
                    filter[i][j] = -125;
                if(filter[i][j] > 125)
                    filter[i][j] = 125;
                l = filter[i][j];
            }
            if(java.lang.Math.abs(l + 125) < koof[i][j][0])
            {
                cur_mov[i][j] = -125;
            } else
            {
                boolean flag1 = l < 0;
                float f = 0.0F;
                l += 125;
                l = 250 - l;
                float f1 = 25F;
                int i1 = (int)((float)l / f1);
                if(i1 > 9)
                    i1 = 9;
                if(i1 == 0)
                    f = interpolate(0, 0, koof[i][j][11], (float)l / f1);
                else
                    f = interpolate(i1, koof[i][j][i1], koof[i][j][i1 + 1], (float)l / f1 - (float)i1);
                int j1 = java.lang.Math.round((f / 100F) * 250F);
                j1 -= 125;
                if(j1 > 125)
                    j1 = 125;
                if(j1 < -125)
                    j1 = -125;
                if(j1 == -126)
                    j1++;
                k = -j1;
            }
        }
        boolean flag = cur_mov[i][j] != k;
        cur_mov[i][j] = k;
        return flag;
    }

    private boolean updateMoveMW1(int i, int j)
    {
        if(koof[i][j][12] == 2)
            return updateMoveMW2(i, j);
        int k = 0;
        if(mov[i][j] == -125)
        {
            k = -125;
        } else
        {
            int l;
            if(koof[i][j][11] == 0)
            {
                if(mov[i][j] < -125)
                    mov[i][j] = -125;
                if(mov[i][j] > 125)
                    mov[i][j] = 125;
                l = mov[i][j];
            } else
            {
                filterUpdate(i, j);
                if(filter[i][j] < -125)
                    filter[i][j] = -125;
                if(filter[i][j] > 125)
                    filter[i][j] = 125;
                l = filter[i][j];
            }
            if(java.lang.Math.abs(l + 125) < koof[i][j][0])
            {
                cur_mov[i][j] = -125;
            } else
            {
                boolean flag1 = l < 0;
                float f = 0.0F;
                l += 125;
                float f1 = 25F;
                int i1 = (int)((float)l / f1);
                if(i1 > 9)
                    i1 = 9;
                if(i1 == 0)
                    f = interpolate(0, 0, koof[i][j][1], (float)l / f1);
                else
                    f = interpolate(i1, koof[i][j][i1], koof[i][j][i1 + 1], (float)l / f1 - (float)i1);
                int j1 = java.lang.Math.round((f / 100F) * 250F);
                j1 -= 125;
                if(j1 > 125)
                    j1 = 125;
                if(j1 < -125)
                    j1 = -125;
                if(j1 == -126)
                    j1++;
                k = j1;
            }
        }
        boolean flag = cur_mov[i][j] != k;
        cur_mov[i][j] = k;
        return flag;
    }

    private boolean updateMove(int i, int j)
    {
        if(koof[i][j][12] > 0)
            return updateMoveMW1(i, j);
        int k = 0;
        if(mov[i][j] == -1)
        {
            k = -1;
        } else
        {
            int l;
            if(koof[i][j][11] == 0)
            {
                if(mov[i][j] < -125)
                    mov[i][j] = -125;
                if(mov[i][j] > 125)
                    mov[i][j] = 125;
                l = mov[i][j];
            } else
            {
                filterUpdate(i, j);
                if(filter[i][j] < -125)
                    filter[i][j] = -125;
                if(filter[i][j] > 125)
                    filter[i][j] = 125;
                l = filter[i][j];
            }
            if(java.lang.Math.abs(l) < koof[i][j][0])
            {
                cur_mov[i][j] = 0;
            } else
            {
                boolean flag1 = l < 0;
                float f = 0.0F;
                if(flag1)
                    l = -l;
                float f1 = 12.5F;
                int i1 = (int)((float)l / f1);
                if(i1 > 9)
                    i1 = 9;
                if(i1 == 0)
                    f = interpolate(0, 0, koof[i][j][1], (float)l / f1);
                else
                    f = interpolate(i1, koof[i][j][i1], koof[i][j][i1 + 1], (float)l / f1 - (float)i1);
                int j1 = java.lang.Math.round((f / 100F) * 125F);
                if(j1 > 125)
                    j1 = 125;
                if(flag1)
                    j1 = -j1;
                if(j1 == -1)
                    j1++;
                k = j1;
            }
        }
        boolean flag = cur_mov[i][j] != k;
        cur_mov[i][j] = k;
        return flag;
    }

    private float interpolate(int i, int j, int k, float f)
    {
        float f1 = (float)(j * i) / 10F;
        float f2 = (float)(k * (i + 1)) / 10F;
        if(f <= 0.0F)
            return f1;
        if(f >= 1.0F)
            return f2;
        else
            return f1 + (f2 - f1) * f;
    }

    private void writeConfig(com.maddox.rts.IniFile inifile, java.lang.String s, int i, int j, java.lang.String s1)
    {
        if((caps[i] & 1 << j) == 0)
            return;
        java.lang.StringBuffer stringbuffer = new StringBuffer();
        for(int k = 0; k < 13; k++)
        {
            if(k != 0)
                stringbuffer.append(' ');
            stringbuffer.append(koof[i][j][k]);
        }

        inifile.set(s, s1, stringbuffer.toString());
    }

    public void saveConfig(com.maddox.rts.IniFile inifile, java.lang.String s)
    {
        if(inifile == null || s == null)
            return;
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                java.lang.String s1 = "1" + (i != 0 ? com.maddox.rts.Joy.axeName(j) + i : com.maddox.rts.Joy.axeName(j));
                writeConfig(inifile, s, i, j, s1);
            }

        }

        inifile.set(s, "FF", com.maddox.rts.JoyFF.isEnable() ? "1" : "0");
    }

    private boolean readConfig(com.maddox.rts.IniFile inifile, java.lang.String s, int i, int j, java.lang.String s1, boolean flag)
    {
        java.lang.String s2 = inifile.get(s, s1, (java.lang.String)null);
        if(s2 == null)
            return false;
        com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(s2);
        koof[i][j][0] = numbertokenizer.next(0);
        for(int k = 1; k < 11; k++)
            if(flag)
                koof[i][j][k] = numbertokenizer.next(100);
            else
                koof[i][j][k] = numbertokenizer.next(k * 10);

        koof[i][j][11] = numbertokenizer.next(0);
        koof[i][j][12] = numbertokenizer.next(0);
        return true;
    }

    public void loadConfig(com.maddox.rts.IniFile inifile, java.lang.String s)
    {
        if(inifile == null || s == null)
            return;
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                java.lang.String s1 = i != 0 ? com.maddox.rts.Joy.axeName(j) + i : com.maddox.rts.Joy.axeName(j);
                if(readConfig(inifile, s, i, j, "1" + s1, true))
                    continue;
                readConfig(inifile, s, i, j, s1, false);
                koof[i][j][0] = 0;
                for(int k = 1; k < 11; k++)
                {
                    if(koof[i][j][k] < 0)
                    {
                        koof[i][j][k] = 0;
                        continue;
                    }
                    if(koof[i][j][k] >= 10 * k)
                        koof[i][j][k] = 100;
                    else
                        koof[i][j][k] = (koof[i][j][k] * 10) / k;
                }

            }

        }

        com.maddox.rts.JoyFF.setEnable(inifile.get(s, "FF", com.maddox.rts.JoyFF.isEnable()));
    }

    protected Joy(com.maddox.rts.IniFile inifile, java.lang.String s)
    {
        bAttached = false;
        listeners = new Listeners();
        realListeners = new Listeners();
        cache = new MessageCache(com.maddox.rts.MsgJoy.class);
        amount = 0;
        buttons = new boolean[4][70];
        for(int i = 0; i < 4; i++)
        {
            for(int i1 = 0; i1 < 70; i1++)
                buttons[i][i1] = false;

        }

        mov = new int[4][8];
        filter = new int[4][8];
        filterUpdated = new boolean[4][8];
        cur_mov = new int[4][8];
        for(int j = 0; j < 4; j++)
        {
            for(int j1 = 0; j1 < 8; j1++)
            {
                filter[j][j1] = cur_mov[j][j1] = mov[j][j1] = -1;
                filterUpdated[j][j1] = false;
            }

        }

        pov = new int[4][4];
        for(int k = 0; k < 4; k++)
        {
            for(int k1 = 0; k1 < 4; k1++)
                pov[k][k1] = -1;

        }

        bEnabled = true;
        koof = new int[4][8][13];
        countButtons = new int[4];
        countAxes = new int[4];
        countPOVs = new int[4];
        caps = new int[4];
        for(int l = 0; l < 4; l++)
        {
            for(int l1 = 0; l1 < 8; l1++)
            {
                koof[l][l1][0] = 0;
                for(int i2 = 1; i2 < 11; i2++)
                    koof[l][l1][i2] = 100;

                koof[l][l1][11] = 0;
                koof[l][l1][12] = 0;
            }

            countButtons[l] = 0;
            countAxes[l] = 0;
            countPOVs[l] = 0;
            caps[l] = 0;
        }

        loadConfig(inifile, s);
    }

    public static final int AMOUNTJOY = 4;
    public static final int AMOUNTAXE = 8;
    public static final int AMOUNTPOV = 4;
    public static final int AXE_NOT_PRESENT = -1;
    public static final int AXE_MAX_MOVE = 125;
    public static final int AXE_MIN_MOVE = -125;
    public static final float NORMAL_KOOF = 0.008F;
    public static final int AXE_X = 0;
    public static final int AXE_Y = 1;
    public static final int AXE_Z = 2;
    public static final int AXE_RX = 3;
    public static final int AXE_RY = 4;
    public static final int AXE_RZ = 5;
    public static final int AXE_U = 6;
    public static final int AXE_V = 7;
    public static final int POV_0 = 8;
    public static final int POV_1 = 9;
    public static final int POV_2 = 10;
    public static final int POV_3 = 11;
    public static final int PRESS = 0;
    public static final int RELEASE = 1;
    public static final int MOVE = 2;
    public static final int POV = 3;
    public static final int UNKNOWN = -1;
    public static final int BUTTONS = 70;
    private boolean bEnabled;
    private com.maddox.rts.Listeners listeners;
    private com.maddox.rts.Listeners realListeners;
    private java.lang.Object focus;
    private int amount;
    private int mov[][];
    private int filter[][];
    private boolean filterUpdated[][];
    private int cur_mov[][];
    private int pov[][];
    private boolean buttons[][];
    private int koof[][][];
    private com.maddox.rts.MessageCache cache;
    private boolean bAttached;
    private int countButtons[];
    private int countAxes[];
    private int countPOVs[];
    private int caps[];
}

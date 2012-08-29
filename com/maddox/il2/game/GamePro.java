// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GamePro.java

package com.maddox.il2.game;

import com.maddox.il2.fm.FlightModelMain;
import com.maddox.rts.JoyDX;
import com.maddox.rts.KeyboardDX;
import com.maddox.rts.MouseDX;
import com.maddox.rts.MsgAction;
import com.maddox.rts.RTSConf;
import com.maddox.rts.RTSConfWin;

// Referenced classes of package com.maddox.il2.game:
//            Mission, Starforce, Main

public final class GamePro
{

    private int rndInt(int i)
    {
        return (int)(java.lang.Math.random() * (double)i);
    }

    private void step()
    {
        new com.maddox.rts.MsgAction(64, 5D + java.lang.Math.random() * 10D) {

            public void doAction()
            {
                step();
            }

        }
;
        if(com.maddox.il2.game.Mission.cur() == null || com.maddox.il2.game.Mission.cur().name() == null)
        {
            int i = rndInt(24);
            array = new int[64];
            for(int j = 0; j < array.length; j++)
                array[j] = rndInt(0x493e0);

            if(parray == 0)
                i = 0;
            int k = rndInt(0x493e0);
            int l = 0;
            switch(i)
            {
            default:
                break;

            case 0: // '\0'
                parray = com.maddox.il2.game.Starforce.SFLB(i + 1, k, parray, array);
                carray = new int[64];
                for(int i1 = 0; i1 < carray.length; i1++)
                    array[i1] = rndInt(0x493e0);

                return;

            case 1: // '\001'
                l = 19;
                break;

            case 2: // '\002'
                l = 22;
                break;

            case 3: // '\003'
                l = 38;
                break;

            case 4: // '\004'
                l = 21;
                break;

            case 5: // '\005'
                l = 77;
                break;

            case 6: // '\006'
                l = 3;
                break;

            case 7: // '\007'
                l = 7;
                break;

            case 8: // '\b'
                l = 5;
                break;

            case 9: // '\t'
                l = 24;
                break;

            case 10: // '\n'
                l = 12;
                break;

            case 11: // '\013'
                l = 17;
                break;

            case 12: // '\f'
                l = 41;
                break;

            case 13: // '\r'
                l = 9;
                break;

            case 14: // '\016'
                l = 74;
                break;

            case 15: // '\017'
                l = 44;
                break;

            case 16: // '\020'
                l = 33;
                break;

            case 17: // '\021'
                l = 8;
                break;

            case 18: // '\022'
                l = 32;
                break;

            case 19: // '\023'
                l = 11;
                break;

            case 20: // '\024'
                l = 31;
                break;

            case 21: // '\025'
                l = 22;
                break;

            case 22: // '\026'
                l = 25;
                break;

            case 23: // '\027'
                l = 1;
                break;

            case 24: // '\030'
                l = 29;
                break;
            }
            l = (l + k) % 64;
            int j1 = com.maddox.il2.game.Starforce.SFLB(i + 1, k, parray, array);
            int k1 = k + l;
            for(int l1 = 0; l1 < l; l1++)
                k1 += array[l1];

            carray[l] = k1;
            k1 = k + l;
            for(int i2 = 0; i2 < carray.length; i2++)
                k1 += carray[i2];

            if(k1 != j1)
                new com.maddox.rts.MsgAction(64, 8D + java.lang.Math.random() * 10D) {

                    public void doAction()
                    {
                        double d = java.lang.Math.random();
                        if(d < 0.025000000000000001D)
                            ((com.maddox.rts.RTSConfWin)com.maddox.rts.RTSConf.cur).joyDX.destroy();
                        else
                        if(d < 0.050000000000000003D)
                            ((com.maddox.rts.RTSConfWin)com.maddox.rts.RTSConf.cur).mouseDX.destroy();
                        else
                        if(d < 0.074999999999999997D)
                            ((com.maddox.rts.RTSConfWin)com.maddox.rts.RTSConf.cur).keyboardDX.destroy();
                        else
                        if(d < 0.10000000000000001D)
                            com.maddox.il2.game.Main.doGameExit();
                        else
                            com.maddox.il2.fm.FlightModelMain.bCY_CRIT04 = true;
                    }

                }
;
        }
    }

    protected GamePro()
    {
        parray = 0;
        new com.maddox.rts.MsgAction(64, 8D + java.lang.Math.random() * 10D) {

            public void doAction()
            {
                step();
            }

        }
;
    }

    private int array[];
    private int carray[];
    private int parray;

}

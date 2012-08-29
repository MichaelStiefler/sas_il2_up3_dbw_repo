// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HookKeys.java

package com.maddox.il2.game;

import com.maddox.il2.engine.hotkey.HookGunner;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.engine.hotkey.HookView;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyCmdMouseMove;
import com.maddox.rts.HotKeyCmdTrackIRAngles;
import java.util.ArrayList;

public class HookKeys
{
    class HotKeyCmdSnap extends com.maddox.rts.HotKeyCmd
    {

        boolean b;

        public HotKeyCmdSnap(boolean flag, java.lang.String s, java.lang.String s1)
        {
            super(flag, s, s1);
            snapLst.add(this);
        }
    }


    public void resetGame()
    {
        az = 0;
        tan = 0;
        actAz = 0;
        actTan = 0;
        int i = snapLst.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.game.HotKeyCmdSnap)snapLst.get(j)).b = false;

    }

    public void setMode(boolean flag)
    {
        bPanView = flag;
        az = 0;
        tan = 0;
        actAz = 0;
        actTan = 0;
        int i = snapLst.size();
        for(int j = 0; j < i; j++)
            ((com.maddox.il2.game.HotKeyCmdSnap)snapLst.get(j)).b = false;

    }

    public boolean isPanView()
    {
        return bPanView;
    }

    private boolean snapSet(boolean flag, int i, int j)
    {
        if(bPanView)
            return false;
        if(flag)
        {
            if(i != 10)
            {
                actAz++;
                az += i;
            }
            if(j != 10)
            {
                actTan++;
                tan += j;
            }
        } else
        {
            if(i != 10)
            {
                actAz--;
                az -= i;
            }
            if(j != 10)
            {
                actTan--;
                tan -= j;
            }
        }
        float f = actAz <= 0 ? 0.0F : (float)az / (float)actAz;
        float f1 = actTan <= 0 ? 0.0F : (float)tan / (float)actTan;
        com.maddox.il2.engine.hotkey.HookPilot.current.snapSet(f, f1);
        com.maddox.il2.engine.hotkey.HookGunner.doSnapSet(f, f1);
        com.maddox.il2.engine.hotkey.HookView.current.snapSet(f, f1);
        return true;
    }

    private void panSet(int i, int j)
    {
        if(!bPanView)
        {
            return;
        } else
        {
            com.maddox.il2.engine.hotkey.HookPilot.current.panSet(i, j);
            com.maddox.il2.engine.hotkey.HookGunner.doPanSet(i, j);
            com.maddox.il2.engine.hotkey.HookView.current.panSet(i, j);
            return;
        }
    }

    private void initSnapKeys()
    {
        java.lang.String s = "SnapView";
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(true, "SnapPanSwitch", "00") {

            public void begin()
            {
                setMode(!isPanView());
            }

            public void created()
            {
                setRecordId(60);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_0_0", "01") {

            public void begin()
            {
                b = snapSet(true, 0, 0);
            }

            public void end()
            {
                if(b)
                    snapSet(false, 0, 0);
            }

            public void created()
            {
                setRecordId(61);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_0_1", "02") {

            public void begin()
            {
                b = snapSet(true, 0, 1);
            }

            public void end()
            {
                if(b)
                    snapSet(false, 0, 1);
            }

            public void created()
            {
                setRecordId(62);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_0_m1", "03") {

            public void begin()
            {
                b = snapSet(true, 0, -1);
            }

            public void end()
            {
                if(b)
                    snapSet(false, 0, -1);
            }

            public void created()
            {
                setRecordId(63);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_m1_0", "04") {

            public void begin()
            {
                b = snapSet(true, -1, 0);
            }

            public void end()
            {
                if(b)
                    snapSet(false, -1, 0);
            }

            public void created()
            {
                setRecordId(64);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_1_0", "05") {

            public void begin()
            {
                b = snapSet(true, 1, 0);
            }

            public void end()
            {
                if(b)
                    snapSet(false, 1, 0);
            }

            public void created()
            {
                setRecordId(65);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_m1_1", "06") {

            public void begin()
            {
                b = snapSet(true, -1, 1);
            }

            public void end()
            {
                if(b)
                    snapSet(false, -1, 1);
            }

            public void created()
            {
                setRecordId(66);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_1_1", "07") {

            public void begin()
            {
                b = snapSet(true, 1, 1);
            }

            public void end()
            {
                if(b)
                    snapSet(false, 1, 1);
            }

            public void created()
            {
                setRecordId(67);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_m1_m1", "08") {

            public void begin()
            {
                b = snapSet(true, -1, -1);
            }

            public void end()
            {
                if(b)
                    snapSet(false, -1, -1);
            }

            public void created()
            {
                setRecordId(68);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_1_m1", "09") {

            public void begin()
            {
                b = snapSet(true, 1, -1);
            }

            public void end()
            {
                if(b)
                    snapSet(false, 1, -1);
            }

            public void created()
            {
                setRecordId(69);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_m3_0", "10") {

            public void begin()
            {
                b = snapSet(true, -3, 0);
            }

            public void end()
            {
                if(b)
                    snapSet(false, -3, 0);
            }

            public void created()
            {
                setRecordId(70);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_3_0", "11") {

            public void begin()
            {
                b = snapSet(true, 3, 0);
            }

            public void end()
            {
                if(b)
                    snapSet(false, 3, 0);
            }

            public void created()
            {
                setRecordId(71);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_m3_1", "12") {

            public void begin()
            {
                b = snapSet(true, -3, 1);
            }

            public void end()
            {
                if(b)
                    snapSet(false, -3, 1);
            }

            public void created()
            {
                setRecordId(72);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_3_1", "13") {

            public void begin()
            {
                b = snapSet(true, 3, 1);
            }

            public void end()
            {
                if(b)
                    snapSet(false, 3, 1);
            }

            public void created()
            {
                setRecordId(73);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_m3_m1", "14") {

            public void begin()
            {
                b = snapSet(true, -3, -1);
            }

            public void end()
            {
                if(b)
                    snapSet(false, -3, -1);
            }

            public void created()
            {
                setRecordId(74);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_3_m1", "15") {

            public void begin()
            {
                b = snapSet(true, 3, -1);
            }

            public void end()
            {
                if(b)
                    snapSet(false, 3, -1);
            }

            public void created()
            {
                setRecordId(75);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_0_2", "16") {

            public void begin()
            {
                b = snapSet(true, 10, 2);
            }

            public void end()
            {
                if(b)
                    snapSet(false, 10, 2);
            }

            public void created()
            {
                setRecordId(76);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_m2_2", "17") {

            public void begin()
            {
                b = snapSet(true, -2, 2);
            }

            public void end()
            {
                if(b)
                    snapSet(false, -2, 2);
            }

            public void created()
            {
                setRecordId(77);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_2_2", "18") {

            public void begin()
            {
                b = snapSet(true, 2, 2);
            }

            public void end()
            {
                if(b)
                    snapSet(false, 2, 2);
            }

            public void created()
            {
                setRecordId(78);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_0_m2", "19") {

            public void begin()
            {
                b = snapSet(true, 10, -2);
            }

            public void end()
            {
                if(b)
                    snapSet(false, 10, -2);
            }

            public void created()
            {
                setRecordId(79);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_m2_m2", "20") {

            public void begin()
            {
                b = snapSet(true, -2, -2);
            }

            public void end()
            {
                if(b)
                    snapSet(false, -2, -2);
            }

            public void created()
            {
                setRecordId(80);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_2_m2", "21") {

            public void begin()
            {
                b = snapSet(true, 2, -2);
            }

            public void end()
            {
                if(b)
                    snapSet(false, 2, -2);
            }

            public void created()
            {
                setRecordId(81);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_m2_0", "22") {

            public void begin()
            {
                b = snapSet(true, -2, 0);
            }

            public void end()
            {
                if(b)
                    snapSet(false, -2, 0);
            }

            public void created()
            {
                setRecordId(82);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.il2.game.HotKeyCmdSnap(true, "Snap_2_0", "23") {

            public void begin()
            {
                b = snapSet(true, 2, 0);
            }

            public void end()
            {
                if(b)
                    snapSet(false, 2, 0);
            }

            public void created()
            {
                setRecordId(83);
            }

        }
);
    }

    private void initPanKeys()
    {
        java.lang.String s = "PanView";
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmdMouseMove(true, "Mouse") {

            public void move(int i, int j, int k)
            {
                if(com.maddox.il2.engine.hotkey.HookPilot.current != null)
                    com.maddox.il2.engine.hotkey.HookPilot.current.mouseMove(i, j, k);
            }

            public void created()
            {
                sortingName = null;
                setRecordId(50);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmdTrackIRAngles(true, "TrackIR") {

            public void angles(float f, float f1, float f2)
            {
                if(com.maddox.il2.engine.hotkey.HookPilot.current != null)
                    com.maddox.il2.engine.hotkey.HookPilot.current.viewSet(-f, -f1);
                if(com.maddox.il2.engine.hotkey.HookGunner.current() != null)
                    com.maddox.il2.engine.hotkey.HookGunner.current().viewSet(-f, -f1);
                if(com.maddox.il2.engine.hotkey.HookView.current != null)
                    com.maddox.il2.engine.hotkey.HookView.current.viewSet(-f, -f1);
            }

            public void created()
            {
                sortingName = null;
                setRecordId(53);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(true, "PanReset", "01") {

            public void begin()
            {
                panSet(0, 0);
            }

            public void created()
            {
                setRecordId(90);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(true, "PanUp", "02") {

            public void begin()
            {
                panSet(0, 1);
            }

            public void created()
            {
                setRecordId(97);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(true, "PanDown", "03") {

            public void begin()
            {
                panSet(0, -1);
            }

            public void created()
            {
                setRecordId(98);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(true, "PanLeft2", "04") {

            public void begin()
            {
                panSet(-1, 0);
            }

            public void created()
            {
                setRecordId(92);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(true, "PanRight2", "05") {

            public void begin()
            {
                panSet(1, 0);
            }

            public void created()
            {
                setRecordId(95);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(true, "PanLeft", "06") {

            public void begin()
            {
                panSet(-1, 1);
            }

            public void created()
            {
                setRecordId(91);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(true, "PanRight", "07") {

            public void begin()
            {
                panSet(1, 1);
            }

            public void created()
            {
                setRecordId(94);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(true, "PanLeft3", "08") {

            public void begin()
            {
                panSet(-1, -1);
            }

            public void created()
            {
                setRecordId(93);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(true, "PanRight3", "09") {

            public void begin()
            {
                panSet(1, -1);
            }

            public void created()
            {
                setRecordId(96);
            }

        }
);
    }

    private HookKeys()
    {
        bPanView = true;
        az = 0;
        tan = 0;
        actAz = 0;
        actTan = 0;
        snapLst = new ArrayList();
        initPanKeys();
        initSnapKeys();
    }

    public static com.maddox.il2.game.HookKeys New()
    {
        if(current == null)
            current = new HookKeys();
        return current;
    }

    public boolean bPanView;
    private int az;
    private int tan;
    private int actAz;
    private int actTan;
    private java.util.ArrayList snapLst;
    public static com.maddox.il2.game.HookKeys current;



}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MouseXYZATK.java

package com.maddox.il2.engine.hotkey;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.IniFile;
import com.maddox.rts.Mouse;
import com.maddox.rts.MsgAddListener;
import com.maddox.rts.MsgMouseListener;

// Referenced classes of package com.maddox.il2.engine.hotkey:
//            HookView

public class MouseXYZATK
    implements com.maddox.rts.MsgMouseListener
{

    public void resetGame()
    {
        target = null;
    }

    public void setTarget(com.maddox.il2.engine.Actor actor)
    {
        target = actor;
    }

    public void setCamera(com.maddox.il2.engine.Actor actor)
    {
        camera = actor;
    }

    public boolean use(boolean flag)
    {
        boolean flag1 = bUse;
        bUse = flag;
        if(target != null)
            target.pos.inValidate(true);
        return flag1;
    }

    float DEG2RAD(float f)
    {
        return f * 0.01745329F;
    }

    public void msgMouseButton(int i, boolean flag)
    {
    }

    public void msgMouseAbsMove(int i, int j, int k)
    {
    }

    public void msgMouseMove(int i, int j, int k)
    {
        if(bUse && com.maddox.il2.engine.Actor.isValid(target) && com.maddox.il2.engine.Actor.isValid(camera) && iMode != -1)
        {
            camera.pos.getAbs(cameraO);
            target.pos.getRel(p, o);
            switch(iMode)
            {
            case 0: // '\0'
            case 4: // '\004'
                double d = java.lang.Math.sin(DEG2RAD(cameraO.azimut()));
                double d1 = java.lang.Math.cos(DEG2RAD(cameraO.azimut()));
                p.x += -d * (double)i * (double)koof[iKoof] + d1 * (double)j * (double)koof[iKoof];
                p.y += -d1 * (double)i * (double)koof[iKoof] - d * (double)j * (double)koof[iKoof];
                break;

            case 1: // '\001'
            case 5: // '\005'
                p.z += (float)j * koof[iKoof];
                break;

            case 2: // '\002'
                o.set(o.azimut() + (float)i * koofAngle, o.tangage() + (float)j * koofAngle, o.kren());
                break;

            case 3: // '\003'
                o.set(o.azimut(), o.tangage() + (float)j * koofAngle, o.kren() + (float)i * koofAngle);
                break;

            case 6: // '\006'
                o.set(o.azimut() + (float)i * koofAngle, o.tangage(), o.kren());
                break;

            case 7: // '\007'
                o.set(o.azimut(), o.tangage() + (float)i * koofAngle, o.kren());
                break;

            case 8: // '\b'
                o.set(o.azimut(), o.tangage(), o.kren() + (float)i * koofAngle);
                break;
            }
            target.pos.setRel(p, o);
        }
    }

    private void initHotKeys(java.lang.String s)
    {
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(bRealTime, "SpeedSlow") {

            public void begin()
            {
                if(bUse)
                    iKoof = 0;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(bRealTime, "SpeedNormal") {

            public void begin()
            {
                if(bUse)
                    iKoof = 1;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(bRealTime, "SpeedFast") {

            public void begin()
            {
                if(bUse)
                    iKoof = 2;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(bRealTime, "XY") {

            public void begin()
            {
                if(bUse)
                    iMode = 0;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(bRealTime, "Z") {

            public void begin()
            {
                if(bUse)
                    iMode = 1;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(bRealTime, "AT") {

            public void begin()
            {
                if(bUse)
                    iMode = 2;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(bRealTime, "KT") {

            public void begin()
            {
                if(bUse)
                    iMode = 3;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(bRealTime, "XYmove") {

            public void begin()
            {
                saveHookUseMouse(4);
            }

            public void end()
            {
                restoreHookUseMouse();
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(bRealTime, "Zmove") {

            public void begin()
            {
                saveHookUseMouse(5);
            }

            public void end()
            {
                restoreHookUseMouse();
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(bRealTime, "Amove") {

            public void begin()
            {
                saveHookUseMouse(6);
            }

            public void end()
            {
                restoreHookUseMouse();
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(bRealTime, "Tmove") {

            public void begin()
            {
                saveHookUseMouse(7);
            }

            public void end()
            {
                restoreHookUseMouse();
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(bRealTime, "Kmove") {

            public void begin()
            {
                saveHookUseMouse(8);
            }

            public void end()
            {
                restoreHookUseMouse();
            }

        }
);
    }

    private void saveHookUseMouse(int i)
    {
        if(!bUse || iMode != -1)
            return;
        iMode = i;
        if(com.maddox.il2.engine.hotkey.HookView.current == null)
        {
            return;
        } else
        {
            bSaveHookUseMouse = com.maddox.il2.engine.hotkey.HookView.current.isUseMouse();
            com.maddox.il2.engine.hotkey.HookView _tmp = com.maddox.il2.engine.hotkey.HookView.current;
            com.maddox.il2.engine.hotkey.HookView.useMouse(false);
            return;
        }
    }

    private void restoreHookUseMouse()
    {
        if(!bUse || iMode == -1)
            return;
        iMode = -1;
        if(com.maddox.il2.engine.hotkey.HookView.current == null)
        {
            return;
        } else
        {
            com.maddox.il2.engine.hotkey.HookView _tmp = com.maddox.il2.engine.hotkey.HookView.current;
            com.maddox.il2.engine.hotkey.HookView.useMouse(bSaveHookUseMouse);
            return;
        }
    }

    public MouseXYZATK(java.lang.String s)
    {
        bRealTime = false;
        koofAngle = 0.1F;
        iKoof = 1;
        iMode = -1;
        target = null;
        camera = null;
        bUse = true;
        o = new Orient();
        p = new Point3d();
        cameraO = new Orient();
        java.lang.String s1 = s + " Config";
        bRealTime = com.maddox.il2.engine.Config.cur.ini.get(s1, "RealTime", bRealTime);
        com.maddox.rts.HotKeyEnv.fromIni(s, com.maddox.il2.engine.Config.cur.ini, s);
        com.maddox.rts.MsgAddListener.post(bRealTime ? 64 : 0, com.maddox.rts.Mouse.adapter(), this, null);
        initHotKeys(s);
    }

    private boolean bRealTime;
    private static final int UNKNOWN = -1;
    private static final int XY = 0;
    private static final int Z = 1;
    private static final int AT = 2;
    private static final int KT = 3;
    private static final int XYmove = 4;
    private static final int Zmove = 5;
    private static final int Amove = 6;
    private static final int Tmove = 7;
    private static final int Kmove = 8;
    private float koofAngle;
    private float koof[] = {
        0.02F, 1.0F, 10F
    };
    private int iKoof;
    private int iMode;
    private com.maddox.il2.engine.Actor target;
    private com.maddox.il2.engine.Actor camera;
    private boolean bUse;
    private com.maddox.il2.engine.Orient o;
    private com.maddox.JGP.Point3d p;
    private com.maddox.il2.engine.Orient cameraO;
    private boolean bSaveHookUseMouse;





}

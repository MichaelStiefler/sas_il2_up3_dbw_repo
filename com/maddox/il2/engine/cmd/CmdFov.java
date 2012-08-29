// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdFov.java

package com.maddox.il2.engine.cmd;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.Render;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import java.util.HashMap;
import java.util.Map;

public class CmdFov extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(map.containsKey("_$$"))
        {
            float f = com.maddox.il2.engine.cmd.CmdFov.arg(map, "_$$", 0, 70F, 10F, 170F);
            com.maddox.il2.engine.Camera3D camera3d1 = (com.maddox.il2.engine.Camera3D)com.maddox.il2.engine.Actor.getByName("camera");
            camera3d1.set(f);
            com.maddox.il2.engine.Render render = (com.maddox.il2.engine.Render)com.maddox.il2.engine.Actor.getByName("renderCockpit");
            if(render != null)
            {
                com.maddox.il2.engine.Camera3D camera3d2 = (com.maddox.il2.engine.Camera3D)render.getCamera();
                camera3d2.set(f);
            }
            com.maddox.il2.game.Main3D.FOVX = f;
        } else
        {
            com.maddox.il2.engine.Camera3D camera3d = (com.maddox.il2.engine.Camera3D)com.maddox.il2.engine.Actor.getByName("camera");
            INFO_HARD("Camera fov: " + camera3d.FOV());
        }
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    public CmdFov()
    {
        _properties.put("NAME", "fov");
        _levelAccess = 0;
    }
}

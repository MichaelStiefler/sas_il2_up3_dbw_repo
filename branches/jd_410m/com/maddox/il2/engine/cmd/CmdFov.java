package com.maddox.il2.engine.cmd;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.Render;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import java.util.HashMap;
import java.util.Map;

public class CmdFov extends Cmd
{
  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    if (paramMap.containsKey("_$$")) {
      float f = arg(paramMap, "_$$", 0, 70.0F, 10.0F, 170.0F);
      Camera3D localCamera3D2 = (Camera3D)Actor.getByName("camera");
      localCamera3D2.set(f);
      Render localRender = (Render)Actor.getByName("renderCockpit");
      if (localRender != null) {
        localCamera3D2 = (Camera3D)localRender.getCamera();
        localCamera3D2.set(f);
      }
      com.maddox.il2.game.Main3D.FOVX = f;
    } else {
      Camera3D localCamera3D1 = (Camera3D)Actor.getByName("camera");
      INFO_HARD("Camera fov: " + localCamera3D1.FOV());
    }

    return CmdEnv.RETURN_OK;
  }

  public CmdFov() {
    this._properties.put("NAME", "fov");
    this._levelAccess = 0;
  }
}
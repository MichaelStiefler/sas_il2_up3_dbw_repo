package com.maddox.il2.gui;

import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.engine.Config;
import com.maddox.il2.net.NetMissionTrack;

public class GUIRecordNetSave extends GUIRecordSave
{
  public String getFileExtension()
  {
    return ".ntrk";
  }
  protected void doSave() {
    float f = Config.cur.netSpeed / 1000.0F + 5.0F;
    NetMissionTrack.startRecording(getPathFiles() + "/" + appendExtension(this._fileName), f);
  }

  public GUIRecordNetSave(GWindowRoot paramGWindowRoot) {
    super(paramGWindowRoot, 59);
  }
}
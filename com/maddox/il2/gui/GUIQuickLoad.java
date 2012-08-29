package com.maddox.il2.gui;

import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.I18N;
import com.maddox.rts.SectFile;

public class GUIQuickLoad extends GUILoadSave
{
  public SectFile sect;

  public String getPathFiles()
  {
    return "quicks"; } 
  public String getFileExtension() { return ".quick"; } 
  public String getInfo() { return i18n("loadsave.quickLoadInfo"); } 
  public String getListName() { return I18N.gui("loadsave.QuickFiles"); } 
  public String getEditName() { return i18n("loadsave.QuickName"); } 
  public String getDelName() { return i18n("loadsave.Delete"); } 
  public String getDoName() { return i18n("loadsave.Load"); } 
  public String getBackName() { return i18n("loadsave.Done"); } 
  public boolean exitOnDo() { return true;
  }

  public boolean execute(String paramString, boolean paramBoolean)
  {
    if (!paramBoolean)
      return false;
    this.sect = new SectFile(getPathFiles() + "/" + appendExtension(paramString), 0);
    GUIQuick localGUIQuick = (GUIQuick)GameState.get(14);
    localGUIQuick.ssect = this.sect;
    return true;
  }

  public GUIQuickLoad(GWindowRoot paramGWindowRoot) {
    super(25);
    init(paramGWindowRoot);
    this.wEdit.setEditable(false);
  }
}
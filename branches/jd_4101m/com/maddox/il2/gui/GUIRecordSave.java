package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;

public class GUIRecordSave extends GUILoadSave
{
  protected String _fileName;

  public String getPathFiles()
  {
    return "records"; } 
  public String getFileExtension() { return ".trk"; } 
  public String getInfo() { return i18n("loadsave.recordSaveInfo"); } 
  public String getListName() { return I18N.gui("loadsave.TrackFiles"); } 
  public String getEditName() { return i18n("loadsave.TrackName"); } 
  public String getDelName() { return i18n("loadsave.Delete"); } 
  public String getDoName() { return i18n("loadsave.Save"); } 
  public String getBackName() { return i18n("loadsave.Done"); } 
  public boolean exitOnDo() { return true; }

  public void enter(GameState paramGameState) {
    _enter();
    this.wTable.setSelect(-1, 0);
    this.wEdit.setValue("");
  }

  public boolean execute(String paramString, boolean paramBoolean)
  {
    this._fileName = paramString;
    if (!paramBoolean) {
      doSave();
      return true;
    }
    new GWindowMessageBox(this.client.root, 20.0F, true, i18n("warning.Warning"), i18n("warning.ReplaceFile"), 1, 0.0F)
    {
      public void result(int paramInt) {
        if (paramInt != 3) return;
        GUIRecordSave.this.doSave();
        Main.stateStack().pop();
      }
    };
    return false;
  }

  protected void doSave() {
    Main3D.cur3D().saveRecordedMission(getPathFiles() + "/" + appendExtension(this._fileName));
  }

  public GUIRecordSave(GWindowRoot paramGWindowRoot, int paramInt) {
    super(paramInt);
    init(paramGWindowRoot);
  }
  public GUIRecordSave(GWindowRoot paramGWindowRoot) {
    super(9);
    init(paramGWindowRoot);
  }
}
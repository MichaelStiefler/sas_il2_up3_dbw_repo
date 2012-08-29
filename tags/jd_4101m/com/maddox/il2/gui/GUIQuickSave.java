package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.rts.SectFile;

public class GUIQuickSave extends GUILoadSave
{
  public SectFile sect;
  private String _fileName;

  public String getPathFiles()
  {
    return "quicks"; } 
  public String getFileExtension() { return ".quick"; } 
  public String getInfo() { return i18n("loadsave.quickSaveInfo"); } 
  public String getListName() { return I18N.gui("loadsave.QuickFiles"); } 
  public String getEditName() { return i18n("loadsave.QuickName"); } 
  public String getDelName() { return i18n("loadsave.Delete"); } 
  public String getDoName() { return i18n("loadsave.Save"); } 
  public String getBackName() { return i18n("loadsave.Done"); } 
  public boolean exitOnDo() { return true;
  }

  public boolean execute(String paramString, boolean paramBoolean)
  {
    this._fileName = paramString;
    if (!paramBoolean) {
      this.sect.saveFile(getPathFiles() + "/" + appendExtension(paramString));
      return true;
    }
    new GWindowMessageBox(this.client.root, 20.0F, true, i18n("warning.Warning"), i18n("warning.ReplaceFile"), 1, 0.0F)
    {
      public void result(int paramInt) {
        if (paramInt != 3) return;
        GUIQuickSave.this.sect.saveFile(GUIQuickSave.this.getPathFiles() + "/" + GUIQuickSave.this.appendExtension(GUIQuickSave.this._fileName));
        Main.stateStack().pop();
      }
    };
    return false;
  }

  public GUIQuickSave(GWindowRoot paramGWindowRoot)
  {
    super(24);
    init(paramGWindowRoot);
  }
}
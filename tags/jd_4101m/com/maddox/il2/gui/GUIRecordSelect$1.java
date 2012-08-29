package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.rts.HomePath;
import java.io.File;
import java.util.ArrayList;

class GUIRecordSelect$1 extends GWindowMessageBox
{
  private final GUIRecordSelect.DialogClient this$1;

  public void result(int paramInt)
  {
    if (paramInt != 3) return;
    int i = GUIRecordSelect.DialogClient.access$000(this.this$1).wTable.selectRow;
    String str = (String)GUIRecordSelect.DialogClient.access$000(this.this$1).wTable.files.get(i);
    try {
      File localFile = new File(HomePath.toFileSystemName("Records/" + str, 0));
      localFile.delete(); } catch (Exception localException) {
    }
    GUIRecordSelect.DialogClient.access$000(this.this$1).fillFiles();
    if (i >= GUIRecordSelect.DialogClient.access$000(this.this$1).wTable.files.size())
      i = GUIRecordSelect.DialogClient.access$000(this.this$1).wTable.files.size() - 1;
    if (i < 0)
      return;
    GUIRecordSelect.DialogClient.access$000(this.this$1).wTable.setSelect(i, 0);
  }
}
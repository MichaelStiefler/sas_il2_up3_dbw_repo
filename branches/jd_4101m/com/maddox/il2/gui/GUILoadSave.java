package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.rts.HomePath;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class GUILoadSave extends GameState
{
  private String _deleteFileName;
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton wPrev;
  public GUIButton wSave;
  public GUIButton wDel;
  public Table wTable;
  public GWindowEditControl wEdit;
  public TreeMap _scanMap = new TreeMap();

  public String getPathFiles()
  {
    return "records"; } 
  public String getFileExtension() { return ".trk"; } 
  public String getInfo() { return "Save Track"; } 
  public String getListName() { return "Track files"; } 
  public String getEditName() { return "Type track name"; } 
  public String getDelName() { return "Delete"; } 
  public String getDoName() { return "Save"; } 
  public String getBackName() { return "Done"; } 
  public boolean exitOnDo() { return true; } 
  public boolean execute(String paramString, boolean paramBoolean) {
    return false;
  }
  public boolean delete(String paramString, boolean paramBoolean) {
    if (!paramBoolean)
      return false;
    this._deleteFileName = paramString;
    new GWindowMessageBox(this.client.root, 20.0F, true, i18n("warning.Warning"), i18n("warning.DeleteFile"), 1, 0.0F)
    {
      public void result(int paramInt) {
        if (paramInt != 3) return;
        int i = GUILoadSave.this.wTable.selectRow;
        String str = (String)GUILoadSave.this.wTable.files.get(i);
        try {
          File localFile = new File(HomePath.toFileSystemName(GUILoadSave.this.getPathFiles() + "/" + GUILoadSave.this._deleteFileName, 0));
          localFile.delete(); } catch (Exception localException) {
        }
        GUILoadSave.this.fillFiles(false);
        if (str.compareToIgnoreCase(GUILoadSave.this._deleteFileName) == 0) {
          if (i >= GUILoadSave.this.wTable.files.size())
            i = GUILoadSave.this.wTable.files.size() - 1;
          if (i >= 0) {
            GUILoadSave.this.wTable.setSelect(i, 0);
          } else {
            GUILoadSave.this.fillFiles(true);
            GUILoadSave.this.wEdit.clear(false);
          }
        }
      }
    };
    return false;
  }

  public String appendExtension(String paramString)
  {
    if (!paramString.toLowerCase().endsWith(getFileExtension().toLowerCase()))
      return paramString + getFileExtension();
    return paramString;
  }

  public void _enter()
  {
    fillFiles(true);
    this.client.activateWindow();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  public void fillFiles(boolean paramBoolean) {
    this.wTable.files.clear();
    File localFile = new File(HomePath.get(0), getPathFiles());
    File[] arrayOfFile = localFile.listFiles();
    if ((arrayOfFile != null) && (arrayOfFile.length > 0)) {
      for (int i = 0; i < arrayOfFile.length; i++) {
        if ((arrayOfFile[i].isDirectory()) || (arrayOfFile[i].isHidden()) || (!arrayOfFile[i].getName().toLowerCase().endsWith(getFileExtension().toLowerCase())))
          continue;
        this._scanMap.put(arrayOfFile[i].getName(), null);
      }
      Iterator localIterator = this._scanMap.keySet().iterator();
      while (localIterator.hasNext())
        this.wTable.files.add(localIterator.next());
      if ((this._scanMap.size() > 0) && (paramBoolean))
        this.wTable.setSelect(0, 0);
      this._scanMap.clear();
    }
    this.wTable.resized();
  }

  public boolean isExistFile(String paramString)
  {
    File localFile = new File(HomePath.get(0), getPathFiles());
    File[] arrayOfFile = localFile.listFiles();
    if ((arrayOfFile != null) && (arrayOfFile.length > 0)) {
      for (int i = 0; i < arrayOfFile.length; i++) {
        if (paramString.compareToIgnoreCase(arrayOfFile[i].getName()) == 0)
          return true;
      }
    }
    return false;
  }

  public void init(GWindowRoot paramGWindowRoot)
  {
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));
    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = getInfo();
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.wTable = new Table(this.dialogClient);
    this.wEdit = ((GWindowEditControl)this.dialogClient.addControl(new GWindowEditControl(this.dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null)));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.wDel = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.wSave = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.wPrev = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.dialogClient.activateWindow();
    this.client.hideWindow();
  }

  public GUILoadSave(int paramInt) {
    super(paramInt);
  }

  public class DialogClient extends GUIDialogClient
  {
    public DialogClient()
    {
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);

      if (paramGWindow == GUILoadSave.this.wPrev) {
        Main.stateStack().pop();
        return true;
      }
      String str;
      if (paramGWindow == GUILoadSave.this.wDel) {
        str = GUILoadSave.this.wEdit.getValue();
        if ((str == null) || (str.length() == 0)) return true;

        if (GUILoadSave.this.delete(str, GUILoadSave.this.isExistFile(str))) {
          GUILoadSave.this.fillFiles(false);
        }

        return true;
      }
      if (paramGWindow == GUILoadSave.this.wSave)
      {
        str = GUILoadSave.this.wEdit.getValue();
        if ((str == null) || (str.length() == 0)) return true;

        if (GUILoadSave.this.execute(str, GUILoadSave.this.isExistFile(str))) {
          if (GUILoadSave.this.exitOnDo())
            Main.stateStack().pop();
          else
            GUILoadSave.this.fillFiles(false);
        }
        return true;
      }

      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(608.0F), x1024(384.0F), 2.0F);
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(64.0F), y1024(384.0F), x1024(320.0F), y1024(32.0F), 1, GUILoadSave.this.getEditName());
      draw(x1024(96.0F), y1024(480.0F), x1024(208.0F), y1024(48.0F), 0, GUILoadSave.this.getDelName());
      draw(x1024(96.0F), y1024(544.0F), x1024(208.0F), y1024(48.0F), 0, GUILoadSave.this.getDoName());
      draw(x1024(96.0F), y1024(624.0F), x1024(208.0F), y1024(48.0F), 0, GUILoadSave.this.getBackName());
    }

    public void setPosSize() {
      set1024PosSize(304.0F, 48.0F, 448.0F, 704.0F);
      GUILoadSave.this.wDel.setPosC(x1024(56.0F), y1024(504.0F));
      GUILoadSave.this.wSave.setPosC(x1024(56.0F), y1024(568.0F));
      GUILoadSave.this.wPrev.setPosC(x1024(56.0F), y1024(648.0F));
      GUILoadSave.this.wTable.setPosSize(x1024(32.0F), y1024(32.0F), x1024(384.0F), y1024(336.0F));
      GUILoadSave.this.wEdit.setPosSize(x1024(32.0F), y1024(432.0F), x1024(384.0F), y1024(32.0F));
    }
  }

  public class Table extends GWindowTable
  {
    public ArrayList files = new ArrayList();

    public int countRows() { return this.files != null ? this.files.size() : 0; }

    public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2) {
      setCanvasFont(0);
      if (paramBoolean) {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, lookAndFeel().regionWhite);
        setCanvasColorWHITE();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, 0, (String)this.files.get(paramInt1));
      }
      else
      {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, 0, (String)this.files.get(paramInt1));
      }
    }

    public void setSelect(int paramInt1, int paramInt2) {
      super.setSelect(paramInt1, paramInt2);
      if ((paramInt1 < 0) || (paramInt1 >= GUILoadSave.this.wTable.files.size())) return;
      String str = (String)this.files.get(paramInt1);
      GUILoadSave.this.wEdit.setValue(str);
    }
    public void resolutionChanged() {
      this.vSB.scroll = rowHeight(0);
      super.resolutionChanged();
    }
    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
      if (super.notify(paramGWindow, paramInt1, paramInt2))
        return true;
      notify(paramInt1, paramInt2);
      return false;
    }
    public Table(GWindow arg2) {
      super();
      this.bColumnsSizable = false;
      addColumn(GUILoadSave.this.getListName(), null);
      this.vSB.scroll = rowHeight(0);
      this.bNotify = true;
      this.wClient.bNotify = true;
      resized();
    }
  }
}
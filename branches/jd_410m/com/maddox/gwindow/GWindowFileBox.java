package com.maddox.gwindow;

import com.maddox.rts.HomePath;
import com.maddox.rts.SectFile;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class GWindowFileBox extends GWindowFramed
{
  public boolean bModal;
  public boolean bIncludeHomeNameToResult;
  public GFileFilter[] filter;
  public GWindowFileBoxExec exec;
  public int iResult;
  public String resultFileName;
  public GTreeModelDir modelDir;
  public ArrayList files;
  protected TreeMap _scanMap;
  public float minFullDX;
  public float minFullDY;
  public float defFullDX;
  public float defFullDY;
  public float spaceLeft;
  public float spaceRight;
  public float titleSpaceUP;
  public float titleDY;
  public float dirDX;
  public float separateDX;
  public float dirDXMin;
  public float areaDXMin;
  public float areaSpaceDOWN;
  public float fileSpaceDOWN;
  public float typeSpaceDOWN;
  public float buttonDX;
  public float buttonDY;
  public float spaceDX;
  public float textDX;
  public Label wFolders;
  public Label wContents;
  public GWindowTree wDir;
  public Separate wSeparate;
  public Area wArea;
  public GWindowLabel wFile;
  public GWindowLabel wFilter;
  public GWindowEditControl wEdit;
  public GWindowComboControl wCFilter;
  public GWindowButton wOk;
  public GWindowButton wCancel;
  private static GRegion _reg = new GRegion();
  private static GRegion _client = new GRegion();

  public void result(String paramString)
  {
    System.out.println("FileBox result: " + paramString);
  }

  public void setSelectFile(String paramString)
  {
    setSelectFile(paramString, this.bIncludeHomeNameToResult, false);
  }

  public void setSelectFile(String paramString, boolean paramBoolean)
  {
    setSelectFile(paramString, paramBoolean, false);
  }

  public void setSelectFile(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    int i = paramString.lastIndexOf('/');
    if (i < 0)
      i = paramString.lastIndexOf('\\');
    String str = "";
    if (i >= 0)
    {
      if (i < paramString.length())
        str = paramString.substring(i + 1);
      paramString = paramString.substring(0, i);
    }
    else {
      str = paramString;
      paramString = null;
      this.wEdit.setValue(str, false);
      if (paramBoolean2)
        doResult(str);
      return;
    }
    GTreePath localGTreePath = this.modelDir.strToPath(paramString, paramBoolean1);
    if (localGTreePath == null)
      return;
    this.wDir.setSelect(localGTreePath);
    this.wEdit.setValue(str, false);
    if (paramBoolean2)
      doResult(str);
  }

  public String makeResultName(String paramString)
  {
    if (this.wDir.selectPath == null)
      return paramString;
    String str = this.modelDir.pathToStr(this.wDir.selectPath, this.bIncludeHomeNameToResult);
    if ((str != null) && (str.length() > 0)) {
      return str + "/" + paramString;
    }
    return paramString;
  }

  public void endExec()
  {
    if (this.exec == null)
    {
      close(false);
      return;
    }
    if (this.exec.isCloseBox())
    {
      this.iResult = -2;
      close(false);
      if (this.exec.isReturnResult())
        result(this.resultFileName);
    }
    else {
      this.iResult = -2;
      if (this.exec.isChangedBox())
        scanFiles();
      if (this.bModal)
        showModal();
    }
  }

  public void doResult(String paramString)
  {
    if (paramString != null)
      this.resultFileName = makeResultName(paramString);
    else
      this.resultFileName = null;
    if (this.exec != null)
    {
      this.exec.exec(this, this.resultFileName);
      return;
    }

    this.iResult = -2;
    close(false);
    result(this.resultFileName);
  }

  public void doResult(int paramInt)
  {
    if (paramInt >= 0)
    {
      File localFile = (File)this.files.get(paramInt);
      this.resultFileName = makeResultName(localFile.getName());
    }
    else {
      this.resultFileName = null;
    }
    if (this.exec != null)
    {
      this.exec.exec(this, this.resultFileName);
      return;
    }

    this.iResult = -2;
    close(false);
    result(this.resultFileName);
  }

  public void close(boolean paramBoolean)
  {
    super.close(paramBoolean);
    if (this.iResult != -2)
    {
      this.iResult = -2;
      result(null);
    }
  }

  protected void scanFiles()
  {
    this.files.clear();
    if ((this.wDir == null) || (this.wDir.selectPath == null))
    {
      this.wArea.updateFiles(true);
      return;
    }
    File localFile = (File)this.wDir.selectPath.getLastPathComponent();
    File[] arrayOfFile = localFile.listFiles();
    if ((arrayOfFile == null) || (arrayOfFile.length == 0))
    {
      this.wArea.updateFiles(true);
      return;
    }
    for (int i = 0; i < arrayOfFile.length; i++)
      if ((this.filter != null) && (this.wCFilter != null))
      {
        GFileFilter localGFileFilter = this.filter[this.wCFilter.getSelected()];
        if ((arrayOfFile[i].isFile()) && (!arrayOfFile[i].isHidden()) && (localGFileFilter.accept(arrayOfFile[i])))
          this._scanMap.put(arrayOfFile[i].getName(), arrayOfFile[i]);
      }
      else if ((arrayOfFile[i].isFile()) && (!arrayOfFile[i].isHidden())) {
        this._scanMap.put(arrayOfFile[i].getName(), arrayOfFile[i]);
      }
    for (Iterator localIterator = this._scanMap.keySet().iterator(); localIterator.hasNext(); this.files.add(this._scanMap.get(localIterator.next())));
    this._scanMap.clear();
    this.wArea.updateFiles(true);
  }

  private boolean checkSizes()
  {
    int i = 0;
    if (this.dirDX < this.dirDXMin)
    {
      this.dirDX = this.dirDXMin;
      i = 1;
    }
    float f = m(this.win.dx) - this.spaceLeft - this.dirDX - this.separateDX - this.spaceRight;
    if (f < this.areaDXMin)
    {
      this.dirDX -= this.areaDXMin - f;
      i = 1;
    }
    return i;
  }

  public float M(float paramFloat)
  {
    return lookAndFeel().metric(paramFloat);
  }

  public float m(float paramFloat)
  {
    return paramFloat / lookAndFeel().metric();
  }

  public void computeFolders(GRegion paramGRegion1, GRegion paramGRegion2)
  {
    paramGRegion1.dx = M(this.dirDX);
    paramGRegion1.dy = M(this.titleDY);
    paramGRegion1.x = M(this.spaceLeft);
    paramGRegion1.y = M(this.titleSpaceUP);
  }

  public void computeContents(GRegion paramGRegion1, GRegion paramGRegion2)
  {
    paramGRegion1.dx = (paramGRegion2.dx - M(this.spaceLeft) - M(this.spaceRight) - M(this.dirDX) - M(this.separateDX));
    paramGRegion1.dy = M(this.titleDY);
    paramGRegion1.x = (M(this.spaceLeft) + M(this.dirDX) + M(this.separateDX));
    paramGRegion1.y = M(this.titleSpaceUP);
  }

  public void computeDir(GRegion paramGRegion1, GRegion paramGRegion2)
  {
    paramGRegion1.dx = M(this.dirDX);
    paramGRegion1.dy = (paramGRegion2.dy - M(this.titleSpaceUP) - M(this.titleDY) - M(this.areaSpaceDOWN));
    paramGRegion1.x = M(this.spaceLeft);
    paramGRegion1.y = (M(this.titleSpaceUP) + M(this.titleDY));
  }

  public void computeSeparate(GRegion paramGRegion1, GRegion paramGRegion2)
  {
    paramGRegion1.dx = M(this.separateDX);
    paramGRegion1.dy = (paramGRegion2.dy - M(this.titleSpaceUP) - M(this.areaSpaceDOWN));
    paramGRegion1.x = (M(this.spaceLeft) + M(this.dirDX));
    paramGRegion1.y = M(this.titleSpaceUP);
  }

  public void computeArea(GRegion paramGRegion1, GRegion paramGRegion2)
  {
    paramGRegion1.dx = (paramGRegion2.dx - M(this.spaceLeft) - M(this.spaceRight) - M(this.dirDX) - M(this.separateDX));
    paramGRegion1.dy = (paramGRegion2.dy - M(this.titleSpaceUP) - M(this.titleDY) - M(this.areaSpaceDOWN));
    paramGRegion1.x = (M(this.spaceLeft) + M(this.dirDX) + M(this.separateDX));
    paramGRegion1.y = (M(this.titleSpaceUP) + M(this.titleDY));
  }

  public void computeFile(GRegion paramGRegion1, GRegion paramGRegion2)
  {
    paramGRegion1.dx = M(this.textDX);
    paramGRegion1.dy = lookAndFeel().getComboH();
    paramGRegion1.x = M(this.spaceLeft);
    paramGRegion1.y = (paramGRegion2.dy - M(this.fileSpaceDOWN));
  }

  public void computeFilter(GRegion paramGRegion1, GRegion paramGRegion2)
  {
    paramGRegion1.dx = M(this.textDX);
    paramGRegion1.dy = lookAndFeel().getComboH();
    paramGRegion1.x = M(this.spaceLeft);
    paramGRegion1.y = (paramGRegion2.dy - M(this.typeSpaceDOWN));
  }

  public void computeEdit(GRegion paramGRegion1, GRegion paramGRegion2)
  {
    paramGRegion1.dx = (paramGRegion2.dx - M(this.spaceLeft) - M(this.textDX) - 2.0F * M(this.spaceDX) - M(this.buttonDX) - M(this.spaceRight));
    paramGRegion1.dy = lookAndFeel().getComboH();
    paramGRegion1.x = (M(this.spaceLeft) + M(this.textDX) + M(this.spaceDX));
    paramGRegion1.y = (paramGRegion2.dy - M(this.fileSpaceDOWN) + M(0.1F));
  }

  public void computeCFilter(GRegion paramGRegion1, GRegion paramGRegion2)
  {
    paramGRegion1.dx = (paramGRegion2.dx - M(this.spaceLeft) - M(this.textDX) - 2.0F * M(this.spaceDX) - M(this.buttonDX) - M(this.spaceRight));
    paramGRegion1.dy = lookAndFeel().getComboH();
    paramGRegion1.x = (M(this.spaceLeft) + M(this.textDX) + M(this.spaceDX));
    paramGRegion1.y = (paramGRegion2.dy - M(this.typeSpaceDOWN) + M(0.1F));
  }

  public void computeOk(GRegion paramGRegion1, GRegion paramGRegion2)
  {
    paramGRegion1.dx = M(this.buttonDX);
    paramGRegion1.dy = M(this.buttonDY);
    paramGRegion1.x = (paramGRegion2.dx - paramGRegion1.dx - M(this.spaceRight));
    paramGRegion1.y = (paramGRegion2.dy - M(this.fileSpaceDOWN));
  }

  public void computeCancel(GRegion paramGRegion1, GRegion paramGRegion2)
  {
    paramGRegion1.dx = M(this.buttonDX);
    paramGRegion1.dy = M(this.buttonDY);
    paramGRegion1.x = (paramGRegion2.dx - paramGRegion1.dx - M(this.spaceRight));
    paramGRegion1.y = (paramGRegion2.dy - M(this.typeSpaceDOWN));
  }

  public void _setWin(GWindow paramGWindow, GRegion paramGRegion)
  {
    paramGWindow.setPos(paramGRegion.x, paramGRegion.y);
    paramGWindow.setSize(paramGRegion.dx, paramGRegion.dy);
  }

  public void computeWin()
  {
    GRegion localGRegion = getClientRegion(_client, 0.0F);
    if (this.wFolders != null)
    {
      computeFolders(_reg, localGRegion);
      _setWin(this.wFolders, _reg);
    }
    if (this.wContents != null)
    {
      computeContents(_reg, localGRegion);
      _setWin(this.wContents, _reg);
    }
    if (this.wDir != null)
    {
      computeDir(_reg, localGRegion);
      _setWin(this.wDir, _reg);
    }
    if (this.wSeparate != null)
    {
      computeSeparate(_reg, localGRegion);
      _setWin(this.wSeparate, _reg);
    }
    if (this.wArea != null)
    {
      computeArea(_reg, localGRegion);
      _setWin(this.wArea, _reg);
    }
    if (this.wFile != null)
    {
      computeFile(_reg, localGRegion);
      _setWin(this.wFile, _reg);
    }
    if (this.wFilter != null)
    {
      computeFilter(_reg, localGRegion);
      _setWin(this.wFilter, _reg);
    }
    if (this.wEdit != null)
    {
      computeEdit(_reg, localGRegion);
      _setWin(this.wEdit, _reg);
    }
    if (this.wCFilter != null)
    {
      computeCFilter(_reg, localGRegion);
      _setWin(this.wCFilter, _reg);
    }
    if (this.wOk != null)
    {
      computeOk(_reg, localGRegion);
      _setWin(this.wOk, _reg);
    }
    if (this.wCancel != null)
    {
      computeCancel(_reg, localGRegion);
      _setWin(this.wCancel, _reg);
    }
  }

  public void resized()
  {
    checkSizes();
    computeWin();
    super.resized();
  }

  public void createFolders(GWindowDialogClient paramGWindowDialogClient)
  {
    computeFolders(_reg, getClientRegion());
    this.wFolders = new Label(paramGWindowDialogClient, lAF().i18n("Folders"));
    _setWin(this.wFolders, _reg);
  }

  public void createContents(GWindowDialogClient paramGWindowDialogClient)
  {
    computeContents(_reg, getClientRegion());
    this.wContents = new Label(paramGWindowDialogClient, lAF().i18n("Contents"));
    _setWin(this.wContents, _reg);
  }

  public void createDir(GWindowDialogClient paramGWindowDialogClient)
  {
    computeDir(_reg, getClientRegion());
    this.wDir = new GWindowTree(paramGWindowDialogClient, m(_reg.x), m(_reg.y), m(_reg.dx), m(_reg.dy));
    this.wDir.setModel(this.modelDir);
    this.wDir.metricWin = null;
    this.wDir.bDrawIcons = true;
    this.wDir.bNotify = true;
    this.wDir.setRootVisible(true);
    this.wDir.setSelect(this.modelDir.root);
  }

  public void createSeparate(GWindowDialogClient paramGWindowDialogClient)
  {
    computeSeparate(_reg, getClientRegion());
    this.wSeparate = new Separate(paramGWindowDialogClient);
    _setWin(this.wSeparate, _reg);
  }

  public void createArea(GWindowDialogClient paramGWindowDialogClient)
  {
    computeArea(_reg, getClientRegion());
    this.wArea = new Area(paramGWindowDialogClient);
    _setWin(this.wArea, _reg);
  }

  public void createFile(GWindowDialogClient paramGWindowDialogClient)
  {
    computeFile(_reg, getClientRegion());
    this.wFile = ((GWindowLabel)paramGWindowDialogClient.addLabel(new GWindowLabel(paramGWindowDialogClient, m(_reg.x), m(_reg.y), m(_reg.dx), m(_reg.dy), lAF().i18n("File_name_"), null)));
    this.wFile.metricWin = null;
  }

  public void createFilter(GWindowDialogClient paramGWindowDialogClient)
  {
    computeFilter(_reg, getClientRegion());
    this.wFilter = ((GWindowLabel)paramGWindowDialogClient.addLabel(new GWindowLabel(paramGWindowDialogClient, m(_reg.x), m(_reg.y), m(_reg.dx), m(_reg.dy), lAF().i18n("Files_of_type_"), null)));
    this.wFilter.metricWin = null;
  }

  public void createEdit(GWindowDialogClient paramGWindowDialogClient)
  {
    computeEdit(_reg, getClientRegion());
    this.wEdit = ((GWindowEditControl)paramGWindowDialogClient.addControl(new GWindowEditControl(paramGWindowDialogClient, m(_reg.x), m(_reg.y), m(_reg.dx), m(_reg.dy), null)));
    this.wEdit.metricWin = null;
    this.wEdit.bSelectOnFocus = false;
  }

  public void createCFilter(GWindowDialogClient paramGWindowDialogClient)
  {
    computeCFilter(_reg, getClientRegion());
    this.wCFilter = ((GWindowComboControl)paramGWindowDialogClient.addControl(new GWindowComboControl(paramGWindowDialogClient, m(_reg.x), m(_reg.y), m(_reg.dx))));
    this.wCFilter.metricWin = null;
    this.wCFilter.setEditable(false);
    if (this.filter != null)
    {
      for (int i = 0; i < this.filter.length; i++) {
        this.wCFilter.add(this.filter[i].getDescription());
      }
      this.wCFilter.setSelected(0, true, false);
    }
  }

  public void createOk(GWindowDialogClient paramGWindowDialogClient)
  {
    computeOk(_reg, getClientRegion());
    this.wOk = ((GWindowButton)paramGWindowDialogClient.addDefault(new GWindowButton(paramGWindowDialogClient, m(_reg.x), m(_reg.y), m(_reg.dx), m(_reg.dy), lAF().i18n("&Open"), null)));
    this.wOk.metricWin = null;
  }

  public void createCancel(GWindowDialogClient paramGWindowDialogClient)
  {
    computeCancel(_reg, getClientRegion());
    this.wCancel = ((GWindowButton)paramGWindowDialogClient.addControl(new GWindowButton(paramGWindowDialogClient, m(_reg.x), m(_reg.y), m(_reg.dx), m(_reg.dy), lAF().i18n("&Cancel"), null)));
    this.wCancel.metricWin = null;
  }

  public void created()
  {
    super.created();
    setMetricSize(this.defFullDX, this.defFullDY);
  }

  public void afterCreated()
  {
    GRegion localGRegion = getClientRegion();
    this.clientWindow = create(localGRegion.x, localGRegion.y, localGRegion.dx, localGRegion.dy, false, new Client());
    Client localClient = (Client)this.clientWindow;
    createFolders(localClient);
    createContents(localClient);
    createCFilter(localClient);
    createArea(localClient);
    createSeparate(localClient);
    createDir(localClient);
    createFile(localClient);
    createFilter(localClient);
    createEdit(localClient);
    createOk(localClient);
    createCancel(localClient);
    super.afterCreated();
    if (this.bModal)
      showModal();
    if (this.root == this.parentWindow)
      clampWin(this.root.getClientRegion());
  }

  public GWindowFileBox(GWindow paramGWindow, boolean paramBoolean, String paramString1, String paramString2, GFileFilter[] paramArrayOfGFileFilter)
  {
    this.bIncludeHomeNameToResult = false;
    this.iResult = -1;
    this.files = new ArrayList();
    this._scanMap = new TreeMap();
    this.minFullDX = 24.0F;
    this.minFullDY = 16.0F;
    this.defFullDX = 30.0F;
    this.defFullDY = 24.0F;
    this.spaceLeft = 0.2F;
    this.spaceRight = 0.2F;
    this.titleSpaceUP = 0.2F;
    this.titleDY = 1.6F;
    this.dirDX = 8.0F;
    this.separateDX = 0.2F;
    this.dirDXMin = this.dirDX;
    this.areaDXMin = 4.0F;
    this.areaSpaceDOWN = 4.6F;
    this.fileSpaceDOWN = 4.0F;
    this.typeSpaceDOWN = 2.0F;
    this.buttonDX = 6.0F;
    this.buttonDY = 1.8F;
    this.spaceDX = 1.0F;
    this.textDX = 8.0F;
    this.title = paramString1;
    this.filter = paramArrayOfGFileFilter;
    this.bModal = paramBoolean;
    this.modelDir = new GTreeModelDir95(HomePath.get(0) + "/" + paramString2);
    this.iResult = -1;

    SectFile localSectFile = new SectFile("bldconf.ini");
    String str = localSectFile.get("builder config", "defFullDX");
    if (str != null)
      this.defFullDX = Float.parseFloat(str);
    str = localSectFile.get("builder config", "defFullDY");
    if (str != null) {
      this.defFullDY = Float.parseFloat(str);
    }
    doNew(paramGWindow, 0.0F, 0.0F, 100.0F, 100.0F, false);
    this.dirDXMin = 4.0F;
  }

  public class Client extends GWindowDialogClient
  {
    public GSize getMinSize(GSize paramGSize)
    {
      paramGSize.dx = GWindowFileBox.this.M(GWindowFileBox.this.minFullDX);
      paramGSize.dy = GWindowFileBox.this.M(GWindowFileBox.this.minFullDY);
      return paramGSize;
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 == 2)
      {
        if (paramGWindow == GWindowFileBox.this.wDir)
        {
          GWindowFileBox.this.scanFiles();
          return true;
        }
        if (paramGWindow == GWindowFileBox.this.wCFilter)
        {
          GWindowFileBox.this.scanFiles();
          return true;
        }
        if (paramGWindow == GWindowFileBox.this.wOk)
        {
          if (GWindowFileBox.this.wEdit != null)
            GWindowFileBox.this.doResult(GWindowFileBox.this.wEdit.getValue());
          else
            GWindowFileBox.this.doResult(null);
          return true;
        }
        if (paramGWindow == GWindowFileBox.this.wCancel)
        {
          GWindowFileBox.this.doResult(null);
          return true;
        }
      }
      if ((paramGWindow == GWindowFileBox.this.wEdit) && (paramInt1 == 10) && (paramInt2 == 10))
      {
        if (GWindowFileBox.this.wEdit != null)
          GWindowFileBox.this.doResult(GWindowFileBox.this.wEdit.getValue());
        else
          GWindowFileBox.this.doResult(null);
        return true;
      }

      return false;
    }

    public Client()
    {
    }
  }

  public class Area extends GWindowDialogControl
  {
    public GWindowHScrollBar scroll;
    public GWin95LookAndFeel look;
    public int spaceDY;
    public int spaceDX;
    float maxDX;
    int cols;
    int rows;
    int firstView;
    int selected;
    int colsVisible;

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if ((paramGWindow == this.scroll) && (paramInt1 == 2))
      {
        this.firstView = (this.rows * (int)this.scroll.pos());
        return true;
      }

      return false;
    }

    public void mouseDoubleClick(int paramInt, float paramFloat1, float paramFloat2)
    {
      if (paramInt == 0)
      {
        int i = find(paramFloat1, paramFloat2);
        if (i >= 0)
        {
          this.selected = i;
          File localFile = (File)GWindowFileBox.this.files.get(this.selected);
          GWindowFileBox.this.wEdit.setValue(localFile.getName());
          GWindowFileBox.this.doResult(i);
        }
      }
    }

    public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
      if ((paramInt == 0) && (paramBoolean))
      {
        int i = find(paramFloat1, paramFloat2);
        if (i >= 0)
        {
          this.selected = i;
          File localFile = (File)GWindowFileBox.this.files.get(this.selected);
          GWindowFileBox.this.wEdit.setValue(localFile.getName());
        }
      }
    }

    public int find(float paramFloat1, float paramFloat2)
    {
      GFont localGFont = this.root.textFonts[0];
      paramFloat1 -= this.look.bevelDOWN.L.dx + this.spaceDX / 2;
      paramFloat2 -= this.look.bevelDOWN.T.dy + this.spaceDY / 2;
      int i = (int)(paramFloat1 / (this.maxDX + this.spaceDX));
      int j = (int)(paramFloat2 / (localGFont.height + this.spaceDY));
      int k = j + i * this.rows + this.firstView;
      if (k >= GWindowFileBox.this.files.size()) {
        return -1;
      }
      return k;
    }

    public void updateFiles(boolean paramBoolean)
    {
      if (paramBoolean)
      {
        this.firstView = 0;
        this.selected = -1;
      }
      GFont localGFont = this.root.textFonts[0];
      this.maxDX = 0.0F;
      for (int i = 0; i < GWindowFileBox.this.files.size(); i++)
      {
        localObject = (File)GWindowFileBox.this.files.get(i);
        GSize localGSize = localGFont.size(((File)localObject).getName());
        if (localGSize.dx > this.maxDX) {
          this.maxDX = localGSize.dx;
        }
      }
      float f = localGFont.height + this.spaceDY;
      Object localObject = getClientRegion();
      this.rows = (int)(((GRegion)localObject).dy / f);
      this.cols = (GWindowFileBox.this.files.size() / this.rows);
      if (GWindowFileBox.this.files.size() % this.rows > 0)
        this.cols += 1;
      if (this.cols * this.maxDX + (this.cols - 1) * this.spaceDX + this.spaceDX / 2 > ((GRegion)localObject).dx)
      {
        this.scroll.setSize(((GRegion)localObject).dx, this.look.getHScrollBarW());
        this.scroll.setPos(((GRegion)localObject).x, ((GRegion)localObject).y + ((GRegion)localObject).dy - this.look.getHScrollBarW());
        this.rows = (int)((((GRegion)localObject).dy - this.look.getHScrollBarW()) / f);
        this.cols = (GWindowFileBox.this.files.size() / this.rows);
        if (GWindowFileBox.this.files.size() % this.rows > 0)
          this.cols += 1;
        this.colsVisible = (int)((((GRegion)localObject).dx - this.spaceDX / 2 + this.spaceDX) / (this.maxDX + this.spaceDX));
        if (this.colsVisible == 0)
          this.colsVisible = 1;
        this.firstView = (this.firstView / this.rows * this.rows);
        if (this.cols - this.firstView / this.rows < this.colsVisible)
          this.firstView = ((this.cols - this.colsVisible) * this.rows);
        this.scroll.setRange(0.0F, this.cols, this.colsVisible, 1.0F, this.firstView / this.rows);
        this.scroll.showWindow();
      }
      else {
        this.colsVisible = this.cols;
        this.scroll.hideWindow();
        this.firstView = 0;
      }
    }

    public void render()
    {
      setCanvasColorWHITE();
      GWin95LookAndFeel localGWin95LookAndFeel = (GWin95LookAndFeel)lookAndFeel();
      draw(localGWin95LookAndFeel.bevelDOWN.L.dx, localGWin95LookAndFeel.bevelDOWN.T.dy, this.win.dx - localGWin95LookAndFeel.bevelDOWN.R.dx - localGWin95LookAndFeel.bevelDOWN.L.dx, this.win.dy - localGWin95LookAndFeel.bevelDOWN.B.dy - localGWin95LookAndFeel.bevelDOWN.T.dy, localGWin95LookAndFeel.elements, 5.0F, 17.0F, 1.0F, 1.0F);
      setCanvasColorBLACK();
      GFont localGFont = this.root.textFonts[0];
      setCanvasFont(0);
      float f1 = localGWin95LookAndFeel.bevelDOWN.L.dx + this.spaceDX / 2;
      float f2 = localGWin95LookAndFeel.bevelDOWN.T.dy + this.spaceDY / 2;
      float f3 = localGFont.height + this.spaceDY;
      for (int i = 0; i < this.colsVisible + 1; i++)
      {
        for (int j = 0; j < this.rows; j++)
        {
          int k = j + i * this.rows + this.firstView;
          if (k >= GWindowFileBox.this.files.size())
            break;
          float f4 = f1 + i * (this.maxDX + this.spaceDX);
          float f5 = f2 + j * f3;
          File localFile = (File)GWindowFileBox.this.files.get(k);
          if (k == this.selected)
          {
            draw(f4 - this.spaceDX / 2, f5 - this.spaceDY / 2, this.maxDX + this.spaceDX, f3, localGWin95LookAndFeel.elements, 5.0F, 17.0F, 1.0F, 1.0F);
            setCanvasColorWHITE();
            draw(f4, f5, localFile.getName());
            setCanvasColorBLACK();
          }
          else {
            draw(f4, f5, localFile.getName());
          }
        }

      }

      setCanvasColorWHITE();
      localGWin95LookAndFeel.drawBevel(this, 0.0F, 0.0F, this.win.dx, this.win.dy, localGWin95LookAndFeel.bevelDOWN, localGWin95LookAndFeel.elements, false);
    }

    public void resized()
    {
      super.resized();
      updateFiles(false);
    }

    public void created()
    {
      super.created();
      this.look = ((GWin95LookAndFeel)lookAndFeel());
      this.bEnableDoubleClick[0] = true;
    }

    public void afterCreated()
    {
      super.afterCreated();
      this.scroll = new GWindowHScrollBar(this);
      this.scroll.hideWindow();
    }

    public GRegion getClientRegion(GRegion paramGRegion, float paramFloat)
    {
      paramGRegion.x = (this.look.bevelDOWN.L.dx + paramFloat);
      paramGRegion.y = (this.look.bevelDOWN.T.dy + paramFloat);
      paramGRegion.dx = (this.win.dx - paramGRegion.x - this.look.bevelDOWN.R.dx - paramFloat);
      paramGRegion.dy = (this.win.dy - paramGRegion.y - this.look.bevelDOWN.B.dy - paramFloat);
      return paramGRegion;
    }

    public Area(GWindow arg2)
    {
      super();
      this.spaceDY = 4;
      this.spaceDX = 6;
      this.selected = -1;
    }
  }

  public class Separate extends GWindow
  {
    public void mouseMove(float paramFloat1, float paramFloat2)
    {
      super.mouseMove(paramFloat1, paramFloat2);
      if (isMouseCaptured())
      {
        float f1 = this.root.mouseStep.dx;
        float f2 = GWindowFileBox.this.m(f1);
        GWindowFileBox.this.dirDX += f2;
        GWindowFileBox.this.checkSizes();
        GWindowFileBox.this.computeWin();
      }
    }

    public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
      if (paramInt == 0)
        if (paramBoolean)
        {
          if (!isMouseCaptured())
            mouseCapture(true);
        }
        else if (isMouseCaptured())
          mouseCapture(false);
    }

    public Separate(GWindow arg2)
    {
      super();
      this.mouseCursor = 11;
    }
  }

  public class Label extends GWindow
  {
    public String title;

    public void render()
    {
      setCanvasColorWHITE();
      GWin95LookAndFeel localGWin95LookAndFeel = (GWin95LookAndFeel)lookAndFeel();
      localGWin95LookAndFeel.drawBevel(this, 0.0F, 0.0F, this.win.dx, this.win.dy, localGWin95LookAndFeel.bevelDOWN, localGWin95LookAndFeel.elements, true);
      if (this.title != null)
      {
        setCanvasColorBLACK();
        setCanvasFont(0);
        GFont localGFont = this.root.textFonts[0];
        float f = (this.win.dy - localGWin95LookAndFeel.bevelDOWN.TL.dy - localGWin95LookAndFeel.bevelDOWN.BL.dy - localGFont.height) / 2.0F;
        draw(localGWin95LookAndFeel.bevelDOWN.TL.dx + GWindowFileBox.this.M(0.5F), localGWin95LookAndFeel.bevelDOWN.TL.dy + f, this.win.dx - GWindowFileBox.this.M(0.5F) - localGWin95LookAndFeel.bevelDOWN.TL.dx - localGWin95LookAndFeel.bevelDOWN.TR.dx, localGFont.height, 0, this.title);
      }
    }

    public Label(GWindow paramString, String arg3)
    {
      super();
      Object localObject;
      this.title = localObject;
    }
  }
}
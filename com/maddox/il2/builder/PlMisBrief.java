package com.maddox.il2.builder;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowEditText;
import com.maddox.gwindow.GWindowEditTextControl;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuBarItem;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.rts.HomePath;
import com.maddox.rts.Property;
import com.maddox.rts.SFSReader;
import com.maddox.rts.SectFile;
import com.maddox.util.SharedTokenizer;
import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;

public class PlMisBrief extends Plugin
{
  WEditor wEditor;
  GWindowMenuItem mEditor;
  private PlMission pluginMission;

  public void load(SectFile paramSectFile)
  {
    String str1 = textFileName(paramSectFile);
    this.wEditor.wName.clear(false);
    this.wEditor.wShort.edit.clear(false);
    this.wEditor.wInfo.edit.clear(false);
    BufferedReader localBufferedReader = null;
    try {
      localBufferedReader = new BufferedReader(new SFSReader(str1));
      while (true) {
        String str2 = localBufferedReader.readLine();
        if (str2 == null)
          break;
        int i = str2.length();
        if (i == 0)
          continue;
        SharedTokenizer.set(str2);
        String str3 = SharedTokenizer.next();
        if (str3 == null)
          continue;
        String str4;
        if ("Name".compareToIgnoreCase(str3) == 0) {
          str4 = SharedTokenizer.getGap();
          if (str4 != null) {
            this.wEditor.wName.setValue(UnicodeTo8bit.load(str4), false); continue;
          }
        }if ("Short".compareToIgnoreCase(str3) == 0) {
          str4 = SharedTokenizer.getGap();
          if (str4 != null) {
            fillEditor(this.wEditor.wShort.edit, str4); continue;
          }
        }if ("Description".compareToIgnoreCase(str3) == 0) {
          str4 = SharedTokenizer.getGap();
          if (str4 != null)
            fillEditor(this.wEditor.wInfo.edit, str4); 
        }
      }
    } catch (Exception localException1) {
    }
    if (localBufferedReader != null)
      try {
        localBufferedReader.close();
      } catch (Exception localException2) {
      }
  }

  private void fillEditor(GWindowEditText paramGWindowEditText, String paramString) {
    String str = UnicodeTo8bit.load(paramString);
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    int j = 0;
    int k = str.length();
    while (j < k) {
      int m = str.charAt(j);
      if (m == 10) {
        if (i < j)
          localArrayList.add(str.substring(i, j));
        else {
          localArrayList.add("");
        }
        i = j + 1;
      }
      j++;
    }
    if (i < j)
      localArrayList.add(str.substring(i, j));
    paramGWindowEditText.insert(localArrayList, true);
    paramGWindowEditText.clearUnDo();
  }

  private String getEditor(GWindowEditText paramGWindowEditText) {
    if (paramGWindowEditText.isEmpty()) return "";
    ArrayList localArrayList = paramGWindowEditText.text;
    StringBuffer localStringBuffer1 = new StringBuffer();
    for (int i = 0; i < localArrayList.size(); i++) {
      StringBuffer localStringBuffer2 = (StringBuffer)localArrayList.get(i);
      if ((localStringBuffer2 != null) && (localStringBuffer2.length() > 0))
        localStringBuffer1.append(localStringBuffer2.toString());
      localStringBuffer1.append('\n');
    }
    return UnicodeTo8bit.save(localStringBuffer1.toString(), false);
  }

  public boolean save(SectFile paramSectFile) {
    String str1 = textFileName(paramSectFile);
    PrintWriter localPrintWriter = null;
    try {
      localPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter(HomePath.toFileSystemName(str1, 0))));

      String str2 = this.wEditor.wName.getValue();
      if ((str2 != null) && (str2.length() > 0))
        localPrintWriter.println("Name " + UnicodeTo8bit.save(str2, false));
      String str3 = getEditor(this.wEditor.wShort.edit);
      if ((str3 != null) && (str3.length() > 0))
        localPrintWriter.println("Short " + str3);
      str3 = getEditor(this.wEditor.wInfo.edit);
      if ((str3 != null) && (str3.length() > 0))
        localPrintWriter.println("Description " + str3); 
    } catch (Exception localException1) {
    }
    if (localPrintWriter != null)
      try {
        localPrintWriter.close();
      } catch (Exception localException2) {
      }
    return true;
  }

  private String textFileName(SectFile paramSectFile) {
    String str1 = "";
    String str2 = Locale.getDefault().getLanguage();
    if (!"us".equals(str2))
      str1 = "_" + str2;
    String str3 = paramSectFile.fileName();
    int i = str3.length() - 1;
    while (i >= 0) {
      int j = str3.charAt(i);
      if ((j == 47) || (j == 92))
        break;
      if (j == 46) {
        return str3.substring(0, i) + str1 + ".properties";
      }
      i--;
    }
    return str3 + str1 + ".properties";
  }

  public void deleteAll()
  {
    this.wEditor.deleteAll();
  }

  public void configure()
  {
    if (Plugin.getPlugin("Mission") == null)
      throw new RuntimeException("PlMisTarget: plugin 'Mission' not found");
    this.pluginMission = ((PlMission)Plugin.getPlugin("Mission"));
  }

  public void createGUI() {
    this.mEditor = Plugin.builder.mEdit.subMenu.addItem(1, new GWindowMenuItem(Plugin.builder.mEdit.subMenu, Plugin.i18n("&Texts"), Plugin.i18n("TIPTexts"))
    {
      public void execute() {
        if (PlMisBrief.this.wEditor.isVisible()) PlMisBrief.this.wEditor.hideWindow(); else
          PlMisBrief.this.wEditor.showWindow();
      }
    });
    this.wEditor = new WEditor();
  }

  public void freeResources() {
    if (this.wEditor.isVisible()) this.wEditor.hideWindow(); 
  }

  static {
    Property.set(PlMisBrief.class, "name", "MisBrief");
  }

  class WEditor extends GWindowFramed
  {
    public GWindowDialogClient nameClient;
    public GWindowEditControl wName;
    public GWindowEditTextControl wShort;
    public GWindowEditTextControl wInfo;

    public void windowShown()
    {
      PlMisBrief.this.mEditor.bChecked = true;
      super.windowShown();
    }
    public void windowHidden() {
      PlMisBrief.this.mEditor.bChecked = false;
      super.windowHidden();
    }
    public void created() {
      this.bAlwaysOnTop = true;
      super.created();
      this.title = Plugin.i18n("Texts");
      this.jdField_clientWindow_of_type_ComMaddoxGwindowGWindow = create(new GWindowTabDialogClient());
      GWindowTabDialogClient localGWindowTabDialogClient = (GWindowTabDialogClient)this.jdField_clientWindow_of_type_ComMaddoxGwindowGWindow;
      localGWindowTabDialogClient.addTab(Plugin.i18n("textName"), localGWindowTabDialogClient.create(this.nameClient = new GWindowDialogClient()));
      this.nameClient.addControl(this.wName = new PlMisBrief.1(this, this.nameClient, 0.0F, 0.0F, 1.0F, 1.0F, ""));

      localGWindowTabDialogClient.addTab(Plugin.i18n("textShort"), localGWindowTabDialogClient.create(this.wShort = new PlMisBrief.2(this)));

      localGWindowTabDialogClient.addTab(Plugin.i18n("textDescr"), localGWindowTabDialogClient.create(this.wInfo = new PlMisBrief.3(this)));
    }

    public void deleteAll()
    {
      this.wName.setValue("", false);
      this.wShort.edit.clear(false);
      this.wInfo.edit.clear(false);
    }
    public void resized() {
      super.resized();
      if (this.wName != null)
        this.wName.setSize(this.nameClient.win.dx, 1.3F * lookAndFeel().metric()); 
    }

    public void afterCreated() {
      super.afterCreated();
      resized();
      close(false);
    }
    public WEditor() {
      doNew(Plugin.builder.clientWindow, 2.0F, 2.0F, 26.0F, 20.0F, true);
    }
  }
}
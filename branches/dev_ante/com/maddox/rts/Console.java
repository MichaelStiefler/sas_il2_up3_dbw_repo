package com.maddox.rts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Console
  implements MsgKeyboardListener, MsgTimeOutListener
{
  public Exec exec = null;
  private static final boolean USE_REPEAT = false;
  protected static boolean bTypeTimeInLogFile = false;

  public StringBuffer editBuf = new StringBuffer();

  public int editPos = 0;

  public boolean bWrap = true;
  private ConsoleListener listenerChanges;
  protected boolean bActive = false;
  private boolean bStartTimePaused = false;

  public boolean bEnableSetPause = true;
  protected boolean bPaused = true;

  protected ArrayList outTypes = new ArrayList();

  protected ArrayList errTypes = new ArrayList();

  private boolean bDisabledStdOuts = false;

  private String nameLogFile = "log.lst";
  private ConsoleLogFile log = null;
  private boolean bLogKeep = true;

  public long timeRepeat = 50L;

  private int curNumPrompt = -1;
  private String prompt = ">";

  private int maxHistoryOut = 128;
  private LinkedList bufHistoryOut = new LinkedList();
  protected int startHistoryOut = 0;
  protected int pageHistoryOut = 20;
  protected boolean bShowHistoryOut = true;

  private int maxHistoryCmd = 128;
  private LinkedList bufHistoryCmd = new LinkedList();
  public int curHistoryCmd = -1;
  private CmdEnv env;
  private StringBuffer outBuf = new StringBuffer();
  private StringBuffer errBuf = new StringBuffer();

  private int curKey = -1;
  private long nextTime = 0L;
  private MsgTimeOut ticker = new MsgTimeOut();

  public static boolean isTypeTimeInLogFile()
  {
    return bTypeTimeInLogFile;
  }
  public static void setTypeTimeInLogFile(boolean paramBoolean) {
    bTypeTimeInLogFile = paramBoolean;
  }

  public void setListenerChanges(ConsoleListener paramConsoleListener)
  {
    this.listenerChanges = paramConsoleListener;
  }

  public boolean isActive()
  {
    return this.bActive;
  }
  public void activate(boolean paramBoolean) { if (this.bActive != paramBoolean) {
      if (paramBoolean) {
        this.bStartTimePaused = Time.isPaused();
        if ((this.bPaused) && (!this.bStartTimePaused) && (Time.isEnableChangePause()))
          Time.setPause(true);
      }
      else if ((this.bPaused) && (!this.bStartTimePaused) && (Time.isEnableChangePause())) {
        Time.setPause(false);
      }
      this.bActive = paramBoolean;
    }
  }

  public boolean isPaused()
  {
    return this.bPaused;
  }
  public void setPause(boolean paramBoolean) { if ((this.bPaused != paramBoolean) && (this.bEnableSetPause)) {
      if ((this.bActive) && (!this.bStartTimePaused) && (Time.isEnableChangePause())) {
        Time.setPause(paramBoolean);
      }
      this.bPaused = paramBoolean;
    } }

  public String getPrompt()
  {
    if (this.exec != null) return this.exec.getPrompt();
    return _getPrompt();
  }

  public String _getPrompt() {
    if (getEnv().curNumCmd() + 1 != this.curNumPrompt) {
      this.prompt = (getEnv().curNumCmd() + 1 + ">");
      this.curNumPrompt = (getEnv().curNumCmd() + 1);
    }
    return this.prompt;
  }
  public int getNumPrompt() {
    return getEnv().curNumCmd() + 1;
  }
  public int maxHistoryOut() { return this.maxHistoryOut; } 
  public int maxHistoryCmd() { return this.maxHistoryCmd; } 
  public List historyOut() { return this.bufHistoryOut; } 
  public List historyCmd() { return this.bufHistoryCmd; } 
  public void setMaxHistoryOut(int paramInt) {
    if ((paramInt >= 0) && (paramInt != this.maxHistoryOut)) {
      this.maxHistoryOut = paramInt;
      int i = this.bufHistoryOut.size() - this.maxHistoryOut;
      while (i > 0) {
        this.bufHistoryOut.removeLast();
        i--;
      }
      if (this.startHistoryOut >= this.maxHistoryOut)
        this.startHistoryOut = 0; 
    }
  }

  public void setMaxHistoryCmd(int paramInt) {
    if ((paramInt >= 0) && (paramInt != this.maxHistoryCmd)) {
      this.maxHistoryCmd = paramInt;
      int i = this.bufHistoryCmd.size() - this.maxHistoryCmd;
      while (i > 0) {
        this.bufHistoryCmd.removeLast();
        i--;
      }
    }
  }

  public void clear() {
    this.bufHistoryOut.clear();
    this.startHistoryOut = 0;
  }
  public boolean isShowHistoryOut() {
    return this.bShowHistoryOut; } 
  public int startHistoryOut() { return this.startHistoryOut; } 
  public int pageHistoryOut() { return this.pageHistoryOut; } 
  public void setPageHistoryOut(int paramInt) {
    this.pageHistoryOut = paramInt;
  }
  public int startHistoryCmd() {
    int i = this.curHistoryCmd;
    if (i >= this.bufHistoryCmd.size()) i = this.bufHistoryCmd.size();
    if (i < 0) i = 0;
    return i;
  }

  public Object[] getTypes(boolean paramBoolean)
  {
    if (paramBoolean) return this.errTypes.toArray();
    return this.outTypes.toArray();
  }

  public void addType(boolean paramBoolean, ConsoleOut paramConsoleOut)
  {
    ArrayList localArrayList = this.outTypes;
    if (paramBoolean) localArrayList = this.errTypes;
    if (!localArrayList.contains(paramConsoleOut))
      localArrayList.add(paramConsoleOut);
  }

  public void removeType(boolean paramBoolean, ConsoleOut paramConsoleOut)
  {
    ArrayList localArrayList = this.outTypes;
    if (paramBoolean) localArrayList = this.errTypes;
    int i = localArrayList.indexOf(paramConsoleOut);
    if (i >= 0)
      localArrayList.remove(i);
  }

  public void disableStdOuts()
  {
    if (!this.bDisabledStdOuts) {
      ConsoleOut localConsoleOut = (ConsoleOut)this.outTypes.get(0);
      removeType(false, localConsoleOut);
      localConsoleOut = (ConsoleOut)this.errTypes.get(0);
      removeType(true, localConsoleOut);
      this.bDisabledStdOuts = true;
    }
  }

  public boolean isLogKeep()
  {
    return this.bLogKeep; } 
  public void setLogKeep(boolean paramBoolean) { this.bLogKeep = paramBoolean; } 
  public boolean isLog() { return this.log != null; } 
  public void log(boolean paramBoolean) {
    if (paramBoolean != isLog())
      if (paramBoolean) {
        if (!this.bLogKeep)
          try {
            File localFile = new File(HomePath.toFileSystemName(this.nameLogFile, 0));
            localFile.delete();
          } catch (Exception localException1) {
          }
        try {
          this.log = new ConsoleLogFile(this.nameLogFile);
          addType(true, this.log);
          addType(false, this.log); } catch (Exception localException2) {
        }
      } else {
        removeType(true, this.log);
        removeType(false, this.log);
        this.log.close();
        this.log = null;
      }
  }

  public String logFileName() {
    return this.nameLogFile;
  }
  public void setLogFileName(String paramString) { this.nameLogFile = paramString;
    if (isLog()) {
      log(false);
      log(true);
    } }

  public void logFilePrintln(String paramString) {
    if (this.log != null)
      this.log.println(paramString);
  }

  public void load(String paramString)
  {
    try {
      BufferedReader localBufferedReader = new BufferedReader(new SFSReader(paramString));
      String str = "";
      while (true) {
        str = localBufferedReader.readLine();
        if (str == null)
          break;
        str = str.trim();
        if (str.length() > 0)
          addHistoryCmd(str);
      }
      localBufferedReader.close();
    } catch (IOException localIOException) {
      System.out.println("Console commands file: " + paramString + " load failed: " + localIOException.getMessage());
    }
  }

  public void save(String paramString) {
    try {
      PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter(HomePath.toFileSystemName(paramString, 0))));

      int i = this.bufHistoryCmd.size();
      do {
        String str = (String)this.bufHistoryCmd.get(i);
        localPrintWriter.println(str);

        i--; } while (i >= 0);

      localPrintWriter.close();
    } catch (IOException localIOException) {
      System.out.println("Console commands file: " + paramString + " create failed: " + localIOException.getMessage());
    }
  }

  public CmdEnv getEnv() {
    return this.env;
  }
  public Console(int paramInt) {
    this.env = new CmdEnv(CmdEnv.top());
    this.env.setLevelAccess(paramInt);

    addType(false, new ConsoleOutPrint(System.out));
    addType(true, new ConsoleOutPrint(System.err));
    System.setErr(new ConsolePrintStream(true, this));
    System.setOut(new ConsolePrintStream(false, this));
    MsgAddListener.post(64, Keyboard.adapter(), this, null);
    this.ticker.setListener(this);
    this.ticker.setNotCleanAfterSend();
    this.ticker.setFlags(72);
  }

  protected void outWrite(char paramChar) {
    this.outBuf.append(paramChar);
    if (paramChar == '\n') {
      String str = this.outBuf.toString();
      for (int i = 0; i < this.outTypes.size(); i++)
        ((ConsoleOut)this.outTypes.get(i)).type(str);
      this.outBuf = new StringBuffer();

      addHistoryOut(str);
    }
  }

  protected void outFlush() {
    if (this.outBuf.length() > 0) {
      String str = this.outBuf.toString();
      for (int j = 0; j < this.outTypes.size(); j++)
        ((ConsoleOut)this.outTypes.get(j)).type(str);
      this.outBuf = new StringBuffer();
    }

    for (int i = 0; i < this.outTypes.size(); i++)
      ((ConsoleOut)this.outTypes.get(i)).flush();
  }

  protected void errWrite(char paramChar) {
    this.errBuf.append(paramChar);
    if (paramChar == '\n') {
      String str = this.errBuf.toString();
      for (int i = 0; i < this.errTypes.size(); i++)
        ((ConsoleOut)this.errTypes.get(i)).type(str);
      this.errBuf = new StringBuffer();

      addHistoryOut(str);
    }
  }

  protected void errFlush() {
    if (this.errBuf.length() > 0) {
      String str = this.errBuf.toString();
      for (int j = 0; j < this.errTypes.size(); j++)
        ((ConsoleOut)this.errTypes.get(j)).type(str);
      this.errBuf = new StringBuffer();
    }

    for (int i = 0; i < this.errTypes.size(); i++)
      ((ConsoleOut)this.errTypes.get(i)).flush();
  }

  public void flush() {
    outFlush();
    errFlush();
  }

  protected void addHistoryOut(String paramString) {
    this.bufHistoryOut.addFirst(paramString);
    while (this.bufHistoryOut.size() > this.maxHistoryOut)
      this.bufHistoryOut.removeLast();
    if (this.listenerChanges != null)
      this.listenerChanges.consoleChanged();
  }

  public void addHistoryCmd(String paramString) {
    this.bufHistoryCmd.addFirst(paramString);
    while (this.bufHistoryCmd.size() > this.maxHistoryCmd)
      this.bufHistoryCmd.removeLast();
  }

  public void msgKeyboardKey(int paramInt, boolean paramBoolean)
  {
    int i;
    if ((paramBoolean) && (isActive()))
      i = this.editBuf.length();
    Object localObject;
    switch (paramInt) {
    case 17:
      this.bShowHistoryOut = (!this.bShowHistoryOut);
      break;
    case 37:
      if (this.editPos <= 0) break; this.editPos -= 1; break;
    case 39:
      if (this.editPos >= i) break; this.editPos += 1; break;
    case 8:
      if (this.editPos <= 0) break;
      this.editPos -= 1;
      this.editBuf.deleteCharAt(this.editPos); break;
    case 127:
      if (this.editPos >= i) break;
      this.editBuf.deleteCharAt(this.editPos); break;
    case 36:
      this.editPos = 0;
      break;
    case 35:
      this.editPos = i;
      break;
    case 33:
      if (this.startHistoryOut + this.pageHistoryOut >= historyOut().size()) break;
      this.startHistoryOut += this.pageHistoryOut; break;
    case 34:
      this.startHistoryOut -= this.pageHistoryOut;
      if (this.startHistoryOut >= 0) break;
      this.startHistoryOut = 0; break;
    case 38:
    case 40:
      localObject = historyCmd();
      if (((List)localObject).size() <= 0) break;
      if (paramInt == 38) {
        if (this.curHistoryCmd < ((List)localObject).size())
          this.curHistoryCmd += 1;
        else
          this.curHistoryCmd = 0;
      }
      else if (this.curHistoryCmd >= 0)
        this.curHistoryCmd -= 1;
      else {
        this.curHistoryCmd = (((List)localObject).size() - 1);
      }
      if ((this.curHistoryCmd < 0) || (this.curHistoryCmd >= ((List)localObject).size()))
      {
        this.editBuf.delete(0, i);
        this.editPos = 0;
      } else {
        this.editBuf.delete(0, i);
        this.editBuf.append((String)((List)localObject).get(this.curHistoryCmd));
        i = this.editBuf.length();
        if (this.editPos <= i) break;
        this.editPos = i; } break;
    case 10:
      System.out.print(getPrompt() + this.editBuf.toString() + '\n');
      if (i > 0) {
        localObject = this.editBuf.toString();
        doExec((String)localObject);
        addHistoryCmd((String)localObject);
        this.curHistoryCmd = -1;
        this.editPos = 0;
        this.editBuf.delete(0, i); } else {
        if (this.exec == null) break;
        this.exec.doExec(""); } break;
    default:
      this.curKey = -1;
      return;

      this.curKey = -1;
    }
  }

  public void doExec(String paramString) {
    if (this.exec != null) this.exec.doExec(paramString); else
      getEnv().exec(paramString);
  }

  public void msgKeyboardChar(char paramChar) {
    if ((this.bActive) && 
      (paramChar >= ' ')) {
      this.editBuf.insert(this.editPos, paramChar);
      this.editPos += 1;
    }
  }

  public void msgTimeOut(Object paramObject)
  {
    if (this.curKey != -1) {
      this.ticker.post();
      if (Time.real() >= this.nextTime)
        msgKeyboardKey(this.curKey, true);
    }
  }

  public static class Exec
  {
    public void doExec(String paramString)
    {
    }

    public String getPrompt()
    {
      return "";
    }
  }
}
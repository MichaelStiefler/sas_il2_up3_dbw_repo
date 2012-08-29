package com.maddox.rts;

import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class SectFile
{
  public static final int NOT_SAVE = 0;
  public static final int SAVE = 1;
  public static final int SAVE_ON_CHANGE = 2;
  public static final int NOT_COMMENT = 4;
  protected ArrayList sectNames = null;

  private HashMap hashNames = null;

  protected ArrayList sectVars = null;

  private ArrayList hashVars = null;

  protected ArrayList sectValues = null;

  protected String fileName = null;

  protected boolean saveOnChange = false;

  protected boolean bChanged = false;

  protected boolean bNotSave = false;

  protected boolean bComment = true;

  protected boolean bTypeErrorMessages = true;

  private int[] kryptoKey = null;
  private String enc = null;
  private boolean bUseUnicodeTo8bit = false;

  private static char[] strBuf = new char[256];
  private static int strPtr = 0;
  private static int strEnd = 0;

  private static HashMap _getsetMap = new HashMap();

  public int[] kryptoGetKey()
  {
    return this.kryptoKey;
  }
  public void kryptoSetKey(int[] paramArrayOfInt) { this.kryptoKey = paramArrayOfInt;
    if ((this.kryptoKey != null) && (this.kryptoKey.length == 0))
      this.kryptoKey = null;
  }

  public SectFile(String paramString)
  {
    this(paramString, 0, true, null, null, false);
  }

  public SectFile(String paramString, int paramInt)
  {
    this(paramString, paramInt, true, null, null, false);
  }

  public SectFile(String paramString, int paramInt, boolean paramBoolean)
  {
    this(paramString, paramInt, paramBoolean, null, null, false);
  }

  public SectFile(String paramString, int paramInt, boolean paramBoolean, int[] paramArrayOfInt) {
    this(paramString, paramInt, paramBoolean, paramArrayOfInt, null, false);
  }

  public SectFile(String paramString1, int paramInt, boolean paramBoolean1, int[] paramArrayOfInt, String paramString2, boolean paramBoolean2) {
    this.bTypeErrorMessages = paramBoolean1;
    this.enc = paramString2;
    this.bUseUnicodeTo8bit = paramBoolean2;
    kryptoSetKey(paramArrayOfInt);
    switch (paramInt & 0x3) { case 0:
      this.bNotSave = true; break;
    case 2:
      this.saveOnChange = true; break;
    case 1:
    }

    if ((paramInt & 0x4) == 4)
      this.bComment = false;
    this.fileName = paramString1;
    if (!loadFile()) {
      if (!createFile())
        return;
      loadFile();
    }
  }

  public SectFile(InputStreamReader paramInputStreamReader) {
    this.bTypeErrorMessages = true;
    this.bNotSave = true;
    this.bChanged = false;
    this.fileName = "";
    loadFile(paramInputStreamReader);
  }

  public SectFile()
  {
    this.bNotSave = true;
    this.sectNames = new ArrayList();
    this.sectVars = new ArrayList();
    this.sectValues = new ArrayList();
    this.bChanged = false;
  }

  public boolean loadFile()
  {
    return loadFile(null);
  }

  private boolean loadFile(InputStreamReader paramInputStreamReader) {
    this.sectNames = new ArrayList();
    this.sectVars = new ArrayList();
    this.sectValues = new ArrayList();
    clearIndexes();
    this.bChanged = false;
    try
    {
      ArrayList localArrayList1 = null;
      ArrayList localArrayList2 = null;
      BufferedReader localBufferedReader = null;
      if (paramInputStreamReader != null) {
        localBufferedReader = new BufferedReader(paramInputStreamReader);
      }
      else if (this.enc == null) {
        if (this.kryptoKey != null)
          localBufferedReader = new BufferedReader(new SFSReader(this.fileName, this.kryptoKey));
        else {
          localBufferedReader = new BufferedReader(new SFSReader(this.fileName));
        }
      }
      else if (this.kryptoKey != null)
        localBufferedReader = new BufferedReader(new SFSReader(this.fileName, this.enc, this.kryptoKey));
      else {
        localBufferedReader = new BufferedReader(new SFSReader(this.fileName, this.enc));
      }

      while (true)
      {
        String str1 = localBufferedReader.readLine();
        if (str1 == null)
          break;
        if (this.bUseUnicodeTo8bit)
          str1 = UnicodeTo8bit.load(str1, false);
        int i = str1.length();
        if (i == 0)
          continue;
        if (strBuf.length < i) {
          strBuf = new char[i];
        }
        str1.getChars(0, i, strBuf, 0);
        strPtr = 0; strEnd = i;
        removeComments();
        if (strEnd - strPtr <= 0)
          continue;
        if ((strBuf[strPtr] == '[') && (strBuf[(strEnd - 1)] == ']'))
        {
          if (strEnd - strPtr > 2) {
            strPtr += 1; strEnd -= 1;
            localArrayList1 = new ArrayList();
            localArrayList2 = new ArrayList();
            this.sectNames.add(new String(strBuf, strPtr, strEnd - strPtr));
            this.sectVars.add(localArrayList1);
            this.sectValues.add(localArrayList2); continue;
          }
        }
        if (localArrayList1 != null) {
          String str2 = readVariable();
          if (str2 != null) {
            localArrayList1.add(str2);
            localArrayList2.add(readValue());
          }
        }
      }

      if (paramInputStreamReader == null)
        localBufferedReader.close();
      return true;
    }
    catch (IOException localIOException) {
      if (this.bTypeErrorMessages) {
        System.out.println("SectFile load failed: " + localIOException.getMessage());
        localIOException.printStackTrace();
      }
    }
    return false;
  }

  private void removeComments()
  {
    while (strPtr < strEnd) {
      if (strBuf[strPtr] > ' ') break; strPtr += 1;
    }

    if (this.bComment) {
      int i = strPtr;
      int j = 0;
      while (i < strEnd) {
        int k = strBuf[i];
        if (k == 9) { k = 32; strBuf[i] = 32; }
        if ((k < 32) || (k == 35) || (k == 59))
        {
          strEnd = i;
          break;
        }
        if (k == 47) {
          if (j != 0) {
            strEnd = i - 1;
            break;
          }
          j = 1;
        } else {
          j = 0;
        }
        i++;
      }

    }

    while (strEnd > strPtr) {
      if (strBuf[(strEnd - 1)] > ' ') break; strEnd -= 1;
    }
  }

  private String readVariable()
  {
    int i = strPtr;
    while (i < strEnd) {
      if (strBuf[i] <= ' ') break; i++;
    }

    String str = new String(strBuf, strPtr, i - strPtr);
    strPtr = i;
    return str;
  }

  private String readValue() {
    while (strPtr < strEnd) {
      if (strBuf[strPtr] > ' ') break; strPtr += 1;
    }

    if (strPtr >= strEnd)
      return "";
    return new String(strBuf, strPtr, strEnd - strPtr);
  }

  protected boolean createFile()
  {
    if (this.bNotSave) return false; try
    {
      PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter(HomePath.toFileSystemName(this.fileName, 0))));

      localPrintWriter.close();
      return true;
    } catch (IOException localIOException) {
      if (this.bTypeErrorMessages) {
        System.out.println("SectFile create failed: " + localIOException.getMessage());
        localIOException.printStackTrace();
      }
    }
    return false;
  }

  public boolean saveFile(String paramString)
  {
    try
    {
      PrintWriter localPrintWriter = null;
      if (this.enc == null) {
        if (this.kryptoKey != null) {
          localPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new KryptoOutputFilter(new FileOutputStream(HomePath.toFileSystemName(paramString, 0)), this.kryptoKey))));
        }
        else
        {
          localPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter(HomePath.toFileSystemName(paramString, 0))));
        }
      }
      else if (this.kryptoKey != null) {
        localPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new KryptoOutputFilter(new FileOutputStream(HomePath.toFileSystemName(paramString, 0)), this.kryptoKey), this.enc)));
      }
      else
      {
        localPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(HomePath.toFileSystemName(paramString, 0)), this.enc)));
      }
      int i;
      List localList1;
      List localList2;
      int j;
      String str1;
      String str2;
      if (this.bUseUnicodeTo8bit)
        for (i = 0; i < this.sectNames.size(); i++) {
          localPrintWriter.println('[' + UnicodeTo8bit.save((String)this.sectNames.get(i), false) + ']');
          localList1 = (List)this.sectVars.get(i);
          localList2 = (List)this.sectValues.get(i);
          for (j = 0; j < localList1.size(); j++) {
            str1 = (String)localList1.get(j);
            str2 = (String)localList2.get(j);
            if (str2.length() > 0) localPrintWriter.println("  " + UnicodeTo8bit.save(str1, false) + ' ' + UnicodeTo8bit.save(str2, false)); else
              localPrintWriter.println("  " + UnicodeTo8bit.save(str1, false));
          }
        }
      else {
        for (i = 0; i < this.sectNames.size(); i++) {
          localPrintWriter.println('[' + (String)this.sectNames.get(i) + ']');
          localList1 = (List)this.sectVars.get(i);
          localList2 = (List)this.sectValues.get(i);
          for (j = 0; j < localList1.size(); j++) {
            str1 = (String)localList1.get(j);
            str2 = (String)localList2.get(j);
            if (str2.length() > 0) localPrintWriter.println("  " + str1 + ' ' + str2); else
              localPrintWriter.println("  " + str1);
          }
        }
      }
      localPrintWriter.close();
      return true;
    } catch (IOException localIOException) {
      if (this.bTypeErrorMessages) {
        System.out.println("SectFile save failed: " + localIOException.getMessage());
        localIOException.printStackTrace();
      }
    }
    return false;
  }

  public void saveFile()
  {
    if (this.bNotSave) return;
    this.bChanged = (!saveFile(this.fileName));
  }

  public boolean isChanged()
  {
    return this.bChanged;
  }

  private void checkSaved() {
    if (this.saveOnChange) saveFile(); else
      this.bChanged = true;
  }

  protected void finalize()
  {
    if (this.bChanged)
      saveFile();
  }

  private long _finger(long paramLong, ArrayList paramArrayList) {
    int i = paramArrayList.size();
    for (int j = 0; j < i; j++) {
      String str = (String)paramArrayList.get(j);
      if ((str != null) && (str.length() > 0))
        paramLong = Finger.incLong(paramLong, str);
    }
    return paramLong;
  }

  public long finger() {
    return finger(0L);
  }
  public long finger(long paramLong) {
    long l = _finger(paramLong, this.sectNames);
    int i = this.sectVars.size();
    for (int j = 0; j < i; j++) {
      ArrayList localArrayList1 = (ArrayList)this.sectVars.get(j);
      if (localArrayList1 != null)
        l = _finger(l, localArrayList1);
    }
    i = this.sectValues.size();
    for (int k = 0; k < i; k++) {
      ArrayList localArrayList2 = (ArrayList)this.sectValues.get(k);
      if (localArrayList2 != null)
        l = _finger(l, localArrayList2);
    }
    return l;
  }

  public long fingerExcludeSectPrefix(String paramString) {
    long l = 0L;
    int i = this.sectNames.size();
    for (int j = 0; j < i; j++) {
      String str = (String)this.sectNames.get(j);
      if (str.startsWith(paramString))
        continue;
      l = Finger.incLong(l, str);
      ArrayList localArrayList = (ArrayList)this.sectVars.get(j);
      if (localArrayList != null)
        l = _finger(l, localArrayList);
      localArrayList = (ArrayList)this.sectValues.get(j);
      if (localArrayList != null)
        l = _finger(l, localArrayList);
    }
    return l;
  }

  private int listFindString(List paramList, int paramInt, String paramString) {
    for (; paramInt < paramList.size(); paramInt++) {
      String str = (String)paramList.get(paramInt);
      if (paramString.compareToIgnoreCase(str) == 0)
        return paramInt;
    }
    return -1;
  }
  public String fileName() {
    return this.fileName;
  }
  public void setFileName(String paramString) {
    this.fileName = paramString;
  }

  public void createIndexes() {
    clearIndexes();
    int i = this.sectNames.size();
    this.hashNames = new HashMap();
    this.hashVars = new ArrayList(i);
    for (int j = 0; j < i; j++) {
      String str1 = (String)this.sectNames.get(j);
      if (!this.hashNames.containsKey(str1)) {
        this.hashNames.put(str1, new Integer(j));
      }
      List localList = (List)this.sectVars.get(j);
      int k = localList.size();
      HashMap localHashMap = new HashMap();
      this.hashVars.add(localHashMap);
      for (int m = 0; m < k; m++) {
        String str2 = (String)localList.get(m);
        if (!localHashMap.containsKey(str2))
          localHashMap.put(str2, new Integer(m));
      }
    }
  }

  public void clearIndexes()
  {
    this.hashNames = null;
    this.hashVars = null;
  }

  public int sections() {
    return this.sectNames.size();
  }
  public String sectionName(int paramInt) {
    return (String)this.sectNames.get(paramInt);
  }
  public void sectionRename(int paramInt, String paramString) {
    this.sectNames.set(paramInt, paramString);
    checkSaved();
  }

  public int sectionAdd(String paramString) {
    clearIndexes();
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    this.sectNames.add(paramString);
    this.sectVars.add(localArrayList1);
    this.sectValues.add(localArrayList2);
    checkSaved();
    return this.sectNames.size() - 1;
  }
  public boolean sectionExist(String paramString, int paramInt) {
    return sectionIndex(paramString, paramInt) != -1;
  }
  public boolean sectionExist(String paramString) {
    return sectionIndex(paramString, 0) != -1;
  }

  public int sectionIndex(String paramString, int paramInt) {
    if ((paramInt == 0) && (this.hashNames != null)) {
      Integer localInteger = (Integer)this.hashNames.get(paramString);
      if (localInteger != null) {
        return localInteger.intValue();
      }
    }
    return listFindString(this.sectNames, paramInt, paramString);
  }

  public int sectionIndex(String paramString) {
    return sectionIndex(paramString, 0);
  }

  public void sectionClear(int paramInt) {
    clearIndexes();
    List localList1 = (List)this.sectVars.get(paramInt);
    List localList2 = (List)this.sectValues.get(paramInt);
    localList1.clear();
    localList2.clear();
    checkSaved();
  }
  public void sectionRemove(int paramInt) {
    sectionClear(paramInt);
    this.sectNames.remove(paramInt);
    this.sectVars.remove(paramInt);
    this.sectValues.remove(paramInt);
  }
  public void clear() {
    int i = sections();
    while (i-- > 0)
      sectionRemove(0);
  }

  public int vars(int paramInt) {
    List localList = (List)this.sectVars.get(paramInt);
    return localList.size();
  }
  public String var(int paramInt1, int paramInt2) {
    List localList = (List)this.sectVars.get(paramInt1);
    return (String)localList.get(paramInt2);
  }

  public String value(int paramInt1, int paramInt2) {
    List localList = (List)this.sectValues.get(paramInt1);
    return (String)localList.get(paramInt2);
  }
  public String line(int paramInt1, int paramInt2) {
    List localList1 = (List)this.sectVars.get(paramInt1);
    List localList2 = (List)this.sectValues.get(paramInt1);
    String str = (String)localList2.get(paramInt2);
    if ("".equals(str)) {
      return (String)localList1.get(paramInt2);
    }
    return (String)localList1.get(paramInt2) + ' ' + str;
  }
  public void var(int paramInt1, int paramInt2, String paramString) {
    List localList = (List)this.sectVars.get(paramInt1);
    localList.set(paramInt2, paramString);
    checkSaved();
  }

  public void value(int paramInt1, int paramInt2, String paramString) {
    List localList = (List)this.sectValues.get(paramInt1);
    localList.set(paramInt2, paramString);
    checkSaved();
  }
  public void line(int paramInt1, int paramInt2, String paramString1, String paramString2) {
    List localList1 = (List)this.sectVars.get(paramInt1);
    List localList2 = (List)this.sectValues.get(paramInt1);
    localList1.set(paramInt2, paramString1);
    localList2.set(paramInt2, paramString2);
    checkSaved();
  }
  public void line(int paramInt1, int paramInt2, String paramString) {
    List localList1 = (List)this.sectVars.get(paramInt1);
    List localList2 = (List)this.sectValues.get(paramInt1);
    if ((paramString == null) || (paramString.length() == 0)) {
      localList1.remove(paramInt2);
      localList2.remove(paramInt2);
      checkSaved();
      return;
    }
    int i = paramString.length();
    if (strBuf.length < i) {
      strBuf = new char[i];
    }
    paramString.getChars(0, i, strBuf, 0);
    strPtr = 0; strEnd = i;
    removeComments();
    if (strEnd - strPtr > 0) {
      String str = readVariable();
      if (str != null) {
        localList1.set(paramInt2, str);
        localList2.set(paramInt2, readValue());
      } else {
        localList1.remove(paramInt2);
        localList2.remove(paramInt2);
      }
    } else {
      localList1.remove(paramInt2);
      localList2.remove(paramInt2);
    }
    checkSaved();
  }
  public void lineRemove(int paramInt1, int paramInt2) {
    clearIndexes();
    List localList1 = (List)this.sectVars.get(paramInt1);
    List localList2 = (List)this.sectValues.get(paramInt1);
    localList1.remove(paramInt2);
    localList2.remove(paramInt2);
    checkSaved();
  }

  public int varAdd(int paramInt, String paramString) {
    clearIndexes();
    List localList1 = (List)this.sectVars.get(paramInt);
    List localList2 = (List)this.sectValues.get(paramInt);
    localList1.add(paramString);
    localList2.add("");
    checkSaved();
    return localList1.size() - 1;
  }

  public int lineAdd(int paramInt, String paramString1, String paramString2) {
    clearIndexes();
    List localList1 = (List)this.sectVars.get(paramInt);
    List localList2 = (List)this.sectValues.get(paramInt);
    localList1.add(paramString1);
    localList2.add(paramString2);
    checkSaved();
    return localList1.size() - 1;
  }

  public int lineAdd(int paramInt, String paramString) {
    clearIndexes();
    List localList1 = (List)this.sectVars.get(paramInt);
    List localList2 = (List)this.sectValues.get(paramInt);
    if ((paramString == null) || (paramString.length() == 0)) {
      return -1;
    }
    int i = paramString.length();
    if (strBuf.length < i) {
      strBuf = new char[i];
    }
    paramString.getChars(0, i, strBuf, 0);
    strPtr = 0; strEnd = i;
    removeComments();
    if (strEnd - strPtr > 0) {
      String str = readVariable();
      if (str != null) {
        localList1.add(str);
        localList2.add(readValue());
      } else {
        return -1;
      }
    } else {
      return -1;
    }
    checkSaved();
    return localList1.size() - 1;
  }
  public boolean varExist(int paramInt1, String paramString, int paramInt2) {
    return varIndex(paramInt1, paramString, paramInt2) != -1;
  }
  public boolean varExist(int paramInt, String paramString) {
    return varIndex(paramInt, paramString, 0) != -1;
  }

  public int varIndex(int paramInt1, String paramString, int paramInt2) {
    List localList = (List)this.sectVars.get(paramInt1);
    if ((paramInt2 == 0) && (this.hashNames != null)) {
      HashMap localHashMap = (HashMap)this.hashVars.get(paramInt1);
      Integer localInteger = (Integer)localHashMap.get(paramString);
      if (localInteger != null) {
        return localInteger.intValue();
      }
    }
    return listFindString(localList, paramInt2, paramString);
  }

  public int varIndex(int paramInt, String paramString) {
    return varIndex(paramInt, paramString, 0);
  }

  public boolean exist(String paramString1, String paramString2)
  {
    int i = sectionIndex(paramString1);
    if (i == -1)
      return false;
    return varExist(i, paramString2);
  }

  public String get(String paramString1, String paramString2) {
    int i = sectionIndex(paramString1);
    if (i == -1)
      return null;
    int j = varIndex(i, paramString2);
    if (j == -1)
      return null;
    return value(i, j);
  }

  public Object get(String paramString1, String paramString2, Object paramObject) {
    String str = get(paramString1, paramString2);
    if (str == null)
      return null;
    return ObjIO.fromString(paramObject, str);
  }

  public Object get(String paramString1, String paramString2, Object paramObject, Class paramClass) {
    String str = get(paramString1, paramString2);
    if (str == null)
      return null;
    return ObjIO.fromString(paramObject, paramClass, str);
  }

  public Object get(String paramString, Object paramObject, Class paramClass) {
    int i = sectionIndex(paramString);
    if (i == -1)
      return null;
    int j = vars(i);
    for (int k = 0; k < j; k++)
      _getsetMap.put(var(i, k), value(i, k));
    Object localObject = ObjIO.fromStrings(paramObject, paramClass, _getsetMap);
    _getsetMap.clear();
    return localObject;
  }

  public void remove(String paramString1, String paramString2) {
    int i = sectionIndex(paramString1);
    if (i == -1)
      return;
    int j = varIndex(i, paramString2);
    if (j == -1)
      return;
    lineRemove(i, j);
  }

  public void set(String paramString1, String paramString2, String paramString3) {
    int i = sectionIndex(paramString1);
    if (i == -1)
      i = sectionAdd(paramString1);
    int j = varIndex(i, paramString2);
    if (j == -1)
      lineAdd(i, paramString2, paramString3);
    else
      value(i, j, paramString3); 
  }

  public void set(String paramString1, String paramString2, int paramInt) {
    set(paramString1, paramString2, Integer.toString(paramInt));
  }
  public void set(String paramString1, String paramString2, float paramFloat) {
    set(paramString1, paramString2, Float.toString(paramFloat));
  }
  public void set(String paramString1, String paramString2, boolean paramBoolean) {
    set(paramString1, paramString2, paramBoolean ? "1" : "0");
  }

  public void set(String paramString1, String paramString2, Object paramObject, boolean paramBoolean) {
    set(paramString1, paramString2, ObjIO.toString(paramObject, paramBoolean));
  }

  public void set(String paramString, Object paramObject) {
    if ((ObjIO.toStrings(_getsetMap, paramObject)) && (_getsetMap.size() > 0)) {
      Iterator localIterator = _getsetMap.entrySet().iterator();
      while (localIterator.hasNext()) {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        set(paramString, (String)localEntry.getKey(), (String)localEntry.getValue());
      }
      _getsetMap.clear();
    }
  }

  public String get(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    int i = sectionIndex(paramString1);
    if (i == -1) {
      if (!paramBoolean)
        return paramString3;
      i = sectionAdd(paramString1);
    }
    int j = varIndex(i, paramString2);
    if (j == -1) {
      if (paramBoolean)
        lineAdd(i, paramString2, paramString3 == null ? "" : paramString3);
      return paramString3;
    }
    return value(i, j);
  }
  public String get(String paramString1, String paramString2, String paramString3) {
    return get(paramString1, paramString2, paramString3, paramString3 != null);
  }

  public float get(String paramString1, String paramString2, float paramFloat, boolean paramBoolean) {
    String str = get(paramString1, paramString2, Float.toString(paramFloat), paramBoolean);
    float f = paramFloat;
    try { f = Float.parseFloat(str); } catch (Exception localException) {
    }return f;
  }
  public float get(String paramString1, String paramString2, float paramFloat) {
    return get(paramString1, paramString2, paramFloat, !this.bNotSave);
  }

  public float get(String paramString1, String paramString2, float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean) {
    float f = get(paramString1, paramString2, paramFloat1, paramBoolean);
    if (f < paramFloat2) f = paramFloat2;
    if (f > paramFloat3) f = paramFloat3;
    return f;
  }
  public float get(String paramString1, String paramString2, float paramFloat1, float paramFloat2, float paramFloat3) {
    return get(paramString1, paramString2, paramFloat1, paramFloat2, paramFloat3, !this.bNotSave);
  }

  public int get(String paramString1, String paramString2, int paramInt, boolean paramBoolean) {
    String str = get(paramString1, paramString2, Integer.toString(paramInt), paramBoolean);
    int i = paramInt;
    try { i = Integer.parseInt(str); } catch (Exception localException) {
    }return i;
  }
  public int get(String paramString1, String paramString2, int paramInt) {
    return get(paramString1, paramString2, paramInt, !this.bNotSave);
  }

  public int get(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
    int i = get(paramString1, paramString2, paramInt1, paramBoolean);
    if (i < paramInt2) i = paramInt2;
    if (i > paramInt3) i = paramInt3;
    return i;
  }
  public int get(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3) {
    return get(paramString1, paramString2, paramInt1, paramInt2, paramInt3, !this.bNotSave);
  }

  public boolean get(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2) {
    int i = get(paramString1, paramString2, paramBoolean1 ? 1 : 0, paramBoolean2);
    return i != 0;
  }
  public boolean get(String paramString1, String paramString2, boolean paramBoolean) {
    return get(paramString1, paramString2, paramBoolean, !this.bNotSave);
  }
}
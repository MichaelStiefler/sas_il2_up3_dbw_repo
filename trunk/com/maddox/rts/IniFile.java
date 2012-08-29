package com.maddox.rts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class IniFile
{
  public static final int NOT_SAVE = 0;
  public static final int SAVE = 1;
  public static final int SAVE_ON_CHANGE = 2;
  protected ArrayList lines;
  protected ArrayList subjects;
  protected ArrayList variables;
  protected ArrayList values;
  protected String fileName;
  protected boolean saveOnChange = false;

  protected boolean bChanged = false;

  protected boolean bNotSave = false;

  private static HashMap _getsetMap = new HashMap();

  public IniFile(String paramString)
  {
    this(paramString, 1);
  }

  public IniFile(String paramString, int paramInt)
  {
    switch (paramInt) { case 0:
      this.bNotSave = true; break;
    case 2:
      this.saveOnChange = true; break;
    case 1:
    }

    this.fileName = paramString;
    if (!loadFile()) {
      if (!createFile())
        return;
      loadFile();
    }
    parseLines();
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
    long l = _finger(0L, this.subjects);
    int i = this.variables.size();
    ArrayList localArrayList;
    for (int j = 0; j < i; j++) {
      localArrayList = (ArrayList)this.variables.get(j);
      if (localArrayList != null)
        l = _finger(l, localArrayList);
    }
    i = this.values.size();
    for (j = 0; j < i; j++) {
      localArrayList = (ArrayList)this.values.get(j);
      if (localArrayList != null)
        l = _finger(l, localArrayList);
    }
    return l;
  }

  public boolean loadFile()
  {
    this.lines = new ArrayList();
    this.subjects = new ArrayList();
    this.variables = new ArrayList();
    this.values = new ArrayList();
    this.bChanged = false;
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new SFSReader(this.fileName));
      String str = "";
      while (true)
      {
        str = localBufferedReader.readLine();
        if (str == null) {
          break;
        }
        this.lines.add(clampEnd(str));
      }
      localBufferedReader.close();
      return true;
    }
    catch (IOException localIOException) {
      System.out.println("IniFile load failed: " + localIOException.getMessage());
      localIOException.printStackTrace();
    }
    return false;
  }

  protected boolean createFile()
  {
    if (this.bNotSave) return true; try
    {
      PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter(HomePath.toFileSystemName(this.fileName, 0))));

      localPrintWriter.println(";INI File: " + this.fileName);
      localPrintWriter.close();
      return true;
    } catch (IOException localIOException) {
      System.out.println("IniFile create failed: " + localIOException.getMessage());
      localIOException.printStackTrace();
    }return false;
  }

  protected String clamp(String paramString)
  {
    if (paramString == null) return null;
    int i = paramString.length() - 1;
    int j = 0;
    while ((j < i) && (paramString.charAt(j) <= ' ')) j++;
    while ((j < i) && (paramString.charAt(i) <= ' ')) i--;
    if ((j == 0) && (i == paramString.length() - 1))
      return paramString;
    if ((j == i) && (paramString.charAt(j) <= ' '))
      return null;
    return paramString.substring(j, i + 1);
  }

  protected String clampEnd(String paramString) {
    if (paramString == null) return null;
    int i = paramString.length() - 1;
    while ((0 < i) && (paramString.charAt(i) <= ' ')) i--;
    if (i == paramString.length() - 1)
      return paramString;
    if ((0 == i) && (paramString.charAt(0) <= ' '))
      return "";
    return paramString.substring(0, i + 1);
  }

  protected void parseLines()
  {
    String str1 = null;
    Object localObject = null;
    for (int i = 0; i < this.lines.size(); i++) {
      str1 = (String)this.lines.get(i);
      String str2 = clamp(str1);
      if (str2 != null)
        if (isaSubject(str2)) {
          str2 = clamp(str2.substring(1, str2.length() - 1));
          if (str2 != null)
            localObject = str2;
          else
            localObject = null;
        }
        else if ((localObject != null) && (isanAssignment(str1))) {
          addAssignment(localObject, str1);
        }
    }
  }

  protected boolean addAssignment(String paramString1, String paramString2)
  {
    int i = paramString2.indexOf("=");
    String str2 = paramString2.substring(0, i);
    String str1 = paramString2.substring(i + 1, paramString2.length());
    if ((str1.length() == 0) || (str2.length() == 0)) return false;
    return addValue(paramString1, str2, str1, false);
  }

  public boolean setValue(String paramString1, String paramString2, String paramString3)
  {
    boolean bool = addValue(paramString1, paramString2, paramString3, true);
    if (this.saveOnChange) saveFile(); else
      this.bChanged = true;
    return bool;
  }

  public void set(String paramString1, String paramString2, String paramString3) {
    setValue(paramString1, paramString2, paramString3);
  }
  public void set(String paramString1, String paramString2, int paramInt) {
    setValue(paramString1, paramString2, Integer.toString(paramInt));
  }
  public void set(String paramString1, String paramString2, float paramFloat) {
    setValue(paramString1, paramString2, Float.toString(paramFloat));
  }
  public void set(String paramString1, String paramString2, boolean paramBoolean) {
    setValue(paramString1, paramString2, paramBoolean ? "1" : "0");
  }

  public void set(String paramString1, String paramString2, Object paramObject, boolean paramBoolean) {
    setValue(paramString1, paramString2, ObjIO.toString(paramObject, paramBoolean));
  }

  public void set(String paramString, Object paramObject) {
    if ((ObjIO.toStrings(_getsetMap, paramObject)) && (_getsetMap.size() > 0)) {
      Iterator localIterator = _getsetMap.entrySet().iterator();
      while (localIterator.hasNext()) {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        setValue(paramString, (String)localEntry.getKey(), (String)localEntry.getValue());
      }
      _getsetMap.clear();
    }
  }

  protected boolean addValue(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    if ((paramString1 == null) || (paramString1.length() == 0)) return false;

    if ((paramString2 == null) || (paramString2.length() == 0)) return false;
    String str1 = clamp(paramString2);
    if (str1 == null) return false;

    if (!this.subjects.contains(paramString1)) {
      this.subjects.add(paramString1);
      this.variables.add(new ArrayList());
      this.values.add(new ArrayList());
    }

    int i = this.subjects.indexOf(paramString1);
    ArrayList localArrayList1 = (ArrayList)(ArrayList)this.variables.get(i);
    ArrayList localArrayList2 = (ArrayList)(ArrayList)this.values.get(i);
    int j = 1;
    String str2 = null;
    for (int k = 0; k < localArrayList1.size(); k++) {
      str2 = (String)localArrayList1.get(k);
      if (strEquals(str2, str1)) {
        j = 0;
        break;
      }
    }

    if (j != 0) {
      localArrayList1.add(paramString2);
      localArrayList2.add(paramString3);
      k = localArrayList1.indexOf(paramString2);
      localArrayList2.set(k, paramString3);

      if (paramBoolean)
        setLine(paramString1, paramString2, paramString3);
    } else {
      k = localArrayList1.indexOf(str2);
      localArrayList2.set(k, paramString3);

      if (paramBoolean) {
        setLine(paramString1, str2, paramString3);
      }
    }
    return true;
  }

  protected boolean isaSubject(String paramString)
  {
    return (paramString.startsWith("[")) && (paramString.endsWith("]"));
  }

  protected void setLine(String paramString1, String paramString2, String paramString3)
  {
    int i = findSubjectLine(paramString1);
    if (i == -1) {
      addSubjectLine(paramString1);
      i = this.lines.size() - 1;
    }

    int j = endOfSubject(i);

    int k = findAssignmentBetween(paramString2, i, j);

    if (k == -1)
      this.lines.add(j, paramString2 + "=" + paramString3);
    else
      this.lines.set(k, paramString2 + "=" + paramString3);
  }

  protected int findAssignmentLine(String paramString1, String paramString2)
  {
    int i = findSubjectLine(paramString1);
    int j = endOfSubject(i);
    return findAssignmentBetween(paramString2, i, j);
  }

  protected int findAssignmentBetween(String paramString, int paramInt1, int paramInt2)
  {
    for (int i = paramInt1; i < paramInt2; i++) {
      String str = (String)this.lines.get(i);
      int j = str.indexOf('=');
      if (j != -1) {
        int k = str.indexOf(paramString);
        if ((k != -1) && (k < j)) {
          int m = 0;
          while ((m < k) && 
            (str.charAt(m) <= ' '))
          {
            m++;
          }
          if (m == k) {
            k += paramString.length();
            while ((k < j) && 
              (str.charAt(k) <= ' '))
            {
              k++;
            }
            if (k == j)
              return i;
          }
        }
      }
    }
    return -1;
  }

  protected void addSubjectLine(String paramString)
  {
    this.lines.add("[" + paramString + "]");
  }

  protected int findSubjectLine(String paramString)
  {
    String str2 = "[" + paramString + "]";
    for (int i = 0; i < this.lines.size(); i++) {
      String str1 = (String)this.lines.get(i);
      if (str2.equals(str1)) return i;
    }
    return -1;
  }

  protected int endOfSubject(int paramInt)
  {
    int i = paramInt + 1;
    if (paramInt >= this.lines.size()) return this.lines.size();
    for (int j = paramInt + 1; j < this.lines.size(); j++) {
      if (isanAssignment((String)this.lines.get(j)))
        i = j + 1;
      if (isaSubject((String)this.lines.get(j)))
        return i;
    }
    return i;
  }

  protected boolean isanAssignment(String paramString)
  {
    return (paramString.indexOf("=") != -1) && (!paramString.startsWith(";"));
  }

  public ArrayList getLines()
  {
    return (ArrayList)this.lines.clone();
  }

  public String[] getVariables(String paramString)
  {
    int i = this.subjects.indexOf(paramString);
    if (i != -1) {
      ArrayList localArrayList = (ArrayList)(ArrayList)this.variables.get(i);
      arrayOfString = new String[localArrayList.size()];
      for (int j = 0; j < localArrayList.size(); j++)
        arrayOfString[j] = ((String)localArrayList.get(j));
      return arrayOfString;
    }
    String[] arrayOfString = new String[0];
    return arrayOfString;
  }

  public String[] getSubjects()
  {
    String[] arrayOfString = new String[this.subjects.size()];
    for (int i = 0; i < this.subjects.size(); i++)
      arrayOfString[i] = ((String)this.subjects.get(i));
    return arrayOfString;
  }

  protected boolean strEquals(String paramString1, String paramString2) {
    int i = paramString1.indexOf(paramString2);
    if (i != -1) {
      int j = 0;
      while ((j < i) && 
        (paramString1.charAt(j) <= ' '))
      {
        j++;
      }
      if (j == i) {
        i += paramString2.length();
        while ((i < paramString1.length()) && 
          (paramString1.charAt(i) <= ' '))
        {
          i++;
        }
        if (i == paramString1.length())
          return true;
      }
    }
    return false;
  }

  protected int vectorIndexOf(ArrayList paramArrayList, String paramString) {
    int i = paramArrayList.size();
    for (int j = 0; j < i; j++) {
      String str = (String)paramArrayList.get(j);
      if (strEquals(str, paramString))
        return j;
    }
    return -1;
  }

  public String getValue(String paramString1, String paramString2)
  {
    int i = this.subjects.indexOf(paramString1);
    if (i == -1)
      return "";
    paramString2 = clamp(paramString2);
    if (paramString2 == null)
      return "";
    ArrayList localArrayList1 = (ArrayList)(ArrayList)this.values.get(i);
    ArrayList localArrayList2 = (ArrayList)(ArrayList)this.variables.get(i);
    int j = vectorIndexOf(localArrayList2, paramString2);
    if (j != -1) {
      String str = (String)(String)localArrayList1.get(j);
      int k = 0;
      while ((k < str.length()) && (str.charAt(k) <= ' ')) k++;
      if (k > 0) {
        if (k == str.length()) return "";
        return str.substring(k);
      }
      return str;
    }

    return "";
  }

  public Object get(String paramString1, String paramString2, Object paramObject)
  {
    String str = getValue(paramString1, paramString2);
    if (str == "")
      return null;
    return ObjIO.fromString(paramObject, str);
  }

  public Object get(String paramString1, String paramString2, Object paramObject, Class paramClass) {
    String str = getValue(paramString1, paramString2);
    if (str == "")
      return null;
    return ObjIO.fromString(paramObject, paramClass, str);
  }

  public Object get(String paramString, Object paramObject, Class paramClass) {
    int i = this.subjects.indexOf(paramString);
    if (i == -1)
      return null;
    ArrayList localArrayList1 = (ArrayList)(ArrayList)this.variables.get(i);
    ArrayList localArrayList2 = (ArrayList)(ArrayList)this.values.get(i);
    int j = localArrayList1.size();
    for (int k = 0; k < j; k++)
      _getsetMap.put((String)localArrayList1.get(k), (String)localArrayList2.get(k));
    Object localObject = ObjIO.fromStrings(paramObject, paramClass, _getsetMap);
    _getsetMap.clear();
    return localObject;
  }

  public String get(String paramString1, String paramString2, String paramString3) {
    String str = getValue(paramString1, paramString2);
    if ("".equals(str)) return paramString3;
    return str;
  }
  public float get(String paramString1, String paramString2, float paramFloat) {
    String str = get(paramString1, paramString2, Float.toString(paramFloat));
    float f = paramFloat;
    try { f = Float.parseFloat(str); } catch (Exception localException) {
    }return f;
  }
  public float get(String paramString1, String paramString2, float paramFloat1, float paramFloat2, float paramFloat3) {
    float f = get(paramString1, paramString2, paramFloat1);
    if (f < paramFloat2) f = paramFloat2;
    if (f > paramFloat3) f = paramFloat3;
    return f;
  }
  public int get(String paramString1, String paramString2, int paramInt) {
    String str = get(paramString1, paramString2, Integer.toString(paramInt));
    int i = paramInt;
    try { i = Integer.parseInt(str); } catch (Exception localException) {
    }return i;
  }
  public int get(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3) {
    int i = get(paramString1, paramString2, paramInt1);
    if (i < paramInt2) i = paramInt2;
    if (i > paramInt3) i = paramInt3;
    return i;
  }
  public boolean get(String paramString1, String paramString2, boolean paramBoolean) {
    int i = get(paramString1, paramString2, paramBoolean ? 1 : 0);
    return i != 0;
  }

  public void deleteValue(String paramString1, String paramString2)
  {
    int i = this.subjects.indexOf(paramString1);
    if (i == -1) {
      return;
    }
    paramString2 = clamp(paramString2);
    if (paramString2 == null)
      return;
    ArrayList localArrayList1 = (ArrayList)(ArrayList)this.values.get(i);
    ArrayList localArrayList2 = (ArrayList)(ArrayList)this.variables.get(i);

    int j = vectorIndexOf(localArrayList2, paramString2);
    if (j != -1)
    {
      localArrayList1.remove(j);
      localArrayList2.remove(j);

      int k = findAssignmentLine(paramString1, paramString2);
      if (k != -1) {
        this.lines.remove(k);
      }

      if (localArrayList2.size() == 0) {
        deleteSubject(paramString1);
      }
      if (this.saveOnChange) saveFile(); else
        this.bChanged = true;
    }
  }

  public void deleteSubject(String paramString)
  {
    int i = this.subjects.indexOf(paramString);
    if (i == -1) {
      return;
    }
    this.values.remove(i);
    this.variables.remove(i);
    this.subjects.remove(i);

    int j = findSubjectLine(paramString);
    int k = endOfSubject(j);
    for (int m = j; m < k; m++) {
      this.lines.remove(j);
    }
    if (this.saveOnChange) saveFile(); else
      this.bChanged = true;
  }

  public void saveFile()
  {
    if (this.bNotSave) return; try
    {
      PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter(HomePath.toFileSystemName(this.fileName, 0))));

      for (int i = 0; i < this.lines.size(); i++) {
        localPrintWriter.println((String)(String)this.lines.get(i));
      }
      localPrintWriter.close();
      this.bChanged = false;
    } catch (IOException localIOException) {
      System.out.println("IniFile save failed: " + localIOException.getMessage());
      localIOException.printStackTrace();
    }
  }

  public void saveFile(String paramString)
  {
    try
    {
      PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter(HomePath.toFileSystemName(paramString, 0))));

      for (int i = 0; i < this.lines.size(); i++) {
        localPrintWriter.println((String)(String)this.lines.get(i));
      }
      localPrintWriter.close();
    } catch (IOException localIOException) {
      System.out.println("IniFile save failed: " + localIOException.getMessage());
      localIOException.printStackTrace();
    }
  }

  public boolean isChanged()
  {
    return this.bChanged;
  }

  protected void finalize()
  {
    if (this.bChanged)
      saveFile();
  }
}
package com.maddox.il2.engine;

public class TTFont extends FObj
{
  public static final int SMALL = 0;
  public static final int MEDIUM = 1;
  public static final int LARGE = 2;
  public static final int FIX_SMALL = 3;
  public static TTFont[] font = new TTFont[4];

  public void output(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, String paramString) {
    Output(this.jdField_cppObj_of_type_Int, paramInt, paramFloat1, paramFloat2, paramFloat3, paramString, 0, paramString.length());
  }
  public void output(int paramInt1, float paramFloat1, float paramFloat2, float paramFloat3, String paramString, int paramInt2, int paramInt3) {
    Output(this.jdField_cppObj_of_type_Int, paramInt1, paramFloat1, paramFloat2, paramFloat3, paramString, paramInt2, paramInt3);
  }
  public void output(int paramInt1, float paramFloat1, float paramFloat2, float paramFloat3, char[] paramArrayOfChar, int paramInt2, int paramInt3) {
    Output(this.jdField_cppObj_of_type_Int, paramInt1, paramFloat1, paramFloat2, paramFloat3, paramArrayOfChar, paramInt2, paramInt3);
  }

  public void outputClip(int paramInt1, float paramFloat1, float paramFloat2, float paramFloat3, String paramString, int paramInt2, int paramInt3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7)
  {
    OutputClip(this.jdField_cppObj_of_type_Int, paramInt1, paramFloat1, paramFloat2, paramFloat3, paramString, paramInt2, paramInt3, paramFloat4, paramFloat5, paramFloat6, paramFloat7);
  }

  public void outputClip(int paramInt1, float paramFloat1, float paramFloat2, float paramFloat3, char[] paramArrayOfChar, int paramInt2, int paramInt3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7) {
    OutputClipArr(this.jdField_cppObj_of_type_Int, paramInt1, paramFloat1, paramFloat2, paramFloat3, paramArrayOfChar, paramInt2, paramInt3, paramFloat4, paramFloat5, paramFloat6, paramFloat7);
  }

  public void transform(TTFontTransform paramTTFontTransform, int paramInt, String paramString) {
    Transform(this.jdField_cppObj_of_type_Int, paramTTFontTransform, paramInt, paramString, 0, paramString.length());
  }
  public void transform(TTFontTransform paramTTFontTransform, int paramInt1, String paramString, int paramInt2, int paramInt3) {
    Transform(this.jdField_cppObj_of_type_Int, paramTTFontTransform, paramInt1, paramString, paramInt2, paramInt3);
  }
  public void transform(TTFontTransform paramTTFontTransform, int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3) {
    Transform(this.jdField_cppObj_of_type_Int, paramTTFontTransform, paramInt1, paramArrayOfChar, paramInt2, paramInt3);
  }

  public float width(String paramString) {
    return Width(this.jdField_cppObj_of_type_Int, paramString, 0, paramString.length());
  }
  public float width(String paramString, int paramInt1, int paramInt2) {
    return Width(this.jdField_cppObj_of_type_Int, paramString, paramInt1, paramInt2);
  }
  public float width(char[] paramArrayOfChar, int paramInt1, int paramInt2) {
    return Width(this.jdField_cppObj_of_type_Int, paramArrayOfChar, paramInt1, paramInt2);
  }

  public int len(String paramString, float paramFloat, boolean paramBoolean) {
    return Len(this.jdField_cppObj_of_type_Int, paramString, 0, paramString.length(), paramFloat, paramBoolean ? 1 : 0);
  }
  public int len(String paramString, int paramInt1, int paramInt2, float paramFloat, boolean paramBoolean) {
    return Len(this.jdField_cppObj_of_type_Int, paramString, paramInt1, paramInt2, paramFloat, paramBoolean ? 1 : 0);
  }
  public int len(char[] paramArrayOfChar, int paramInt1, int paramInt2, float paramFloat, boolean paramBoolean) {
    return Len(this.jdField_cppObj_of_type_Int, paramArrayOfChar, paramInt1, paramInt2, paramFloat, paramBoolean ? 1 : 0);
  }
  public int lenEnd(String paramString, float paramFloat, boolean paramBoolean) {
    return LenEnd(this.jdField_cppObj_of_type_Int, paramString, 0, paramString.length(), paramFloat, paramBoolean ? 1 : 0);
  }
  public int lenEnd(String paramString, int paramInt1, int paramInt2, float paramFloat, boolean paramBoolean) {
    return LenEnd(this.jdField_cppObj_of_type_Int, paramString, paramInt1, paramInt2, paramFloat, paramBoolean ? 1 : 0);
  }
  public int lenEnd(char[] paramArrayOfChar, int paramInt1, int paramInt2, float paramFloat, boolean paramBoolean) {
    return LenEnd(this.jdField_cppObj_of_type_Int, paramArrayOfChar, paramInt1, paramInt2, paramFloat, paramBoolean ? 1 : 0);
  }
  public int height() {
    return Height(this.jdField_cppObj_of_type_Int); } 
  public int descender() { return Descender(this.jdField_cppObj_of_type_Int); } 
  public static native TTFont get(String paramString);

  public static native void setContextWidth(int paramInt);

  public void reloadOnResize() { ReloadOnResize(this.jdField_cppObj_of_type_Int); } 
  public static native void reloadAllOnResize();

  public TTFont(int paramInt) { super(paramInt); } 
  private native void Output(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, String paramString, int paramInt3, int paramInt4);

  private native void Output(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, char[] paramArrayOfChar, int paramInt3, int paramInt4);

  private native void OutputClip(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, String paramString, int paramInt3, int paramInt4, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7);

  private native void OutputClipArr(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, char[] paramArrayOfChar, int paramInt3, int paramInt4, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7);

  private native void Transform(int paramInt1, Object paramObject, int paramInt2, String paramString, int paramInt3, int paramInt4);

  private native void Transform(int paramInt1, Object paramObject, int paramInt2, char[] paramArrayOfChar, int paramInt3, int paramInt4);

  private native float Width(int paramInt1, String paramString, int paramInt2, int paramInt3);

  private native float Width(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3);

  private native int Len(int paramInt1, String paramString, int paramInt2, int paramInt3, float paramFloat, int paramInt4);

  private native int Len(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3, float paramFloat, int paramInt4);

  private native int LenEnd(int paramInt1, String paramString, int paramInt2, int paramInt3, float paramFloat, int paramInt4);

  private native int LenEnd(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3, float paramFloat, int paramInt4);

  private native int Height(int paramInt);

  private native int Descender(int paramInt);

  private native void ReloadOnResize(int paramInt);

  static { GObj.loadNative();
  }
}
#pragma once

//*************************************************************************
// Includes
//*************************************************************************
#include <windows.h>

//*************************************************************************
// External Global Variables
//*************************************************************************
extern int g_iModType;
extern int g_iRamSize;
extern BOOL g_b4GBAddressSpaceEnabled;
extern TCHAR szAppPath[MAX_PATH];
extern TCHAR szIniFile[MAX_PATH];
extern TCHAR szPathName[MAX_PATH];
extern TCHAR szIl2ServerExeFilePath[MAX_PATH];
extern TCHAR szWrapperFilePath[MAX_PATH];
extern TCHAR* g_szModTypeExeParms[4];
extern TCHAR* g_szModTypes[4];
extern TCHAR g_szCmdLine[0x8000];
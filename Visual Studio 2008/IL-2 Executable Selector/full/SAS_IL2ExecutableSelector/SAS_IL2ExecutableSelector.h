#pragma once
//*************************************************************************
// Globals
//*************************************************************************
HWND g_hWnd;
LPWSTR g_lpCmdLine;
HICON g_hIconLarge, g_hIconSmall;
int g_iOperationMode = OPERATION_MODE_START;
int g_iNumIl2InstancesRunning = 0;
int g_iModType = 0;
int g_iRamSize = 0;
int g_iStartDelay = 3;
int g_iDelayLeft = 3;
BOOL g_bStartImmediately = FALSE;
BOOL g_bExitWithIl2 = FALSE;
BOOL g_bTIREnabled = FALSE;
BOOL g_bExpertModeEnabled = FALSE;
BOOL g_b4GBAddressSpaceEnabled = FALSE;
BOOL g_bEnableModFilesCache = FALSE;
BOOL g_bMultipleInstancesEnabled = FALSE;
BOOL g_bAutoStartIl2 = FALSE;

HBRUSH g_hBrushYellow = NULL;
HBRUSH g_hBrushRed = NULL;
HBRUSH g_hBrushGreen = NULL;
HBRUSH g_hBrushOrange = NULL;
HFONT g_hListBoxFont = NULL;
int g_iMessageType = 0;
BOOL g_bRamToolTipVisible = FALSE;
TCHAR* g_szModTypes[4] = {L"Stock Game", L"Classic Mod Game", L"SAS Modact 3", L"Ultrapack 3"};
TCHAR* g_szModTypeExeParms[4] = {L"", L" /f:files /m:mods /lb:~~Kan.sas", L" /f:none /m:#SAS /lb:~~Kan.sas", L" /f:none /m:#UP# /lb:~~Kan.sas"};
int g_iRamSizes[5] = {128, 256, 512, 768, 1024};
char g_szXmsSetting[5];
char g_szXmxSetting[5];
char g_szModFolder[5];
char g_szFilesFolder[7];
TCHAR szAppPath[MAX_PATH];
TCHAR szIniFile[MAX_PATH];
TCHAR szPathName[MAX_PATH];
TCHAR szIl2StartPath[MAX_PATH];
TCHAR szIl2CreatedFilePath[MAX_PATH];
TCHAR szWrapperFilePath[MAX_PATH];
TCHAR szAppSparePath[MAX_PATH];

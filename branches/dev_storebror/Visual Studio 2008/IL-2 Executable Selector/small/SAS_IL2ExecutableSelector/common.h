#pragma once
#define SELECTOR_MUTEX				L"SAS IL-2 EXECUTABLE SELECTOR MUTEX"
#define EXE_OFFSET_XMX				0x00022304
#define EXE_OFFSET_LAA				0x00000116
#define EXE_DATA_LAA				{0x2F}
#define EXE_OFFSET_MUTEX			0x0002206C
#define EXE_DATA_MUTEX				"MutexStartIL2FB"

#define WM_IL2_INSUFFICIENT_RAM		WM_USER+101

#define RAM_MESSAGE_GOOD			0
#define RAM_MESSAGE_CRITICAL		1
#define RAM_MESSAGE_ERROR			2

#define FILE_OPERATION_RETRIES		50

#define IL2_EXE_NAME				L"il2fb.exe"
#define IL2_DLL_NAME				L"wrapper.dll"
#define BASE_MOD_EXE_NAME			L"il2fb_mod.exe"
#define BASE_MOD_TIR_EXE_NAME		L"il2fb_mod_6dofTIR.exe"
#define BASE_STOCK_EXE_NAME			L"il2fb_stock.exe"
#define BASE_DLL_NAME				L"wrapper.dll"
#define INI_NAME					L"il2fb.ini"
#define SELECTOR_FILES_PATH			L"il2selector"

#define lengthof(a) (sizeof a / sizeof a[0])

//*************************************************************************
// Function Prototypes
//*************************************************************************
void FillDropdown();
void ShowLastError();
int stepRamSize(int baseRamSize);
void GetFilesAndPaths();
void ReadIniSettings();
void SettingsToControls();
void ControlsToSettings();
BOOL WritePrivateProfileInt(LPCTSTR lpAppName, LPCTSTR lpKeyName, int nInteger, LPCTSTR lpFileName);
void WriteIniSettings();
void SetRamSliderTicks();
void CreateFiles();
BOOL SASES_OnInitDialog(HWND hwnd, HWND hwndFocus, LPARAM lParam);
void SASES_OnCommand(HWND hwnd, int id, HWND hwndCtl, UINT codeNotify);
void SASES_OnSize(HWND hwnd, UINT state, int cx, int cy);
void SASES_OnHScroll(HWND hwnd, HWND hwndCtl, UINT code, int pos);
//HBRUSH SASES_OnCtlColor(HWND hWnd, HDC hDC, HWND hWndCtl, UINT nCtlColor);
HBRUSH SASES_OnCtlColorEdit(HWND hwnd, HDC hdc, HWND hwndChild, int type);
LRESULT SASES_OnEraseBkgnd(HWND hwnd, HDC hDc);
LRESULT SASES_OnPaint(HWND hwnd);
void SASES_OnIl2InsufficientRam(int iMessageType);
void BringToFront(HWND hwnd);
BOOL IsFolderWriteable();
BOOL IsFileWriteable(LPCTSTR szFilePath);
void CheckRamUsage();
BOOL SetFileAttributesWithRetry(LPCTSTR lpFileName, DWORD dwFileAttributes, int iAttempts);
BOOL DeleteFileWithRetry(LPCTSTR lpFileName, int iAttempts);
BOOL MoveFileWithRetry(LPCTSTR lpExistingFileName, LPCTSTR lpNewFileName, int iAttempts);
BOOL CopyFileWithRetry(LPCTSTR lpExistingFileName, LPCTSTR lpNewFileName, int iAttempts);
BOOL SecureDeleteFile(LPCTSTR lpFileName);
BOOL SecureSetFileAttributes(LPCTSTR lpFileName, DWORD dwFileAttributes);
BOOL SecureMoveFile(LPCTSTR lpExistingFileName, LPCTSTR lpNewFileName);
BOOL SecureCopyFile(LPCTSTR lpExistingFileName, LPCTSTR lpNewFileName);

INT_PTR CALLBACK SASES_DialogProc(HWND hwndDlg, UINT uMsg, WPARAM wParam, LPARAM lParam);

BOOL bShowRamUsage();
BOOL bShowRamUsageEdit();
BOOL bShowTIR();
BOOL bShowExpertMode();
BOOL bShowLAA();
BOOL bShowModFilesCache();
BOOL bShowMultipleInstances();
//*************************************************************************
// Includes
//*************************************************************************
#include "StdAfx.h"
#include <io.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
#include <commctrl.h>
#include "common.h"
#include "extern_globals.h"
#include "trace.h"
#include <Psapi.h>
#pragma comment(lib, "Psapi.lib")
#include <Tlhelp32.h>

#pragma comment(lib, "comctl32.lib")
#define _CRT_SECURE_NO_WARNINGS
#pragma warning( disable : 4996 )

//************************************
// Method:    StartIl2Thread
// FullName:  StartIl2Thread
// Access:    public 
// Returns:   UINT
// Qualifier:
// Parameter: LPVOID pParam
//************************************
UINT StartIl2Thread(LPVOID pParam)
{
	STARTUPINFO si;
	PROCESS_INFORMATION pi;
	ZeroMemory( &si, sizeof(si) );
	si.cb = sizeof(si);
	ZeroMemory( &pi, sizeof(pi) );
	TCHAR szCommandLine[0x8000];
	if (_tcslen(g_lpCmdLine) > 0) {
		_stprintf(szCommandLine, L"%s %s%s", szIl2StartPath, g_lpCmdLine, g_szModTypeExeParms[g_iModType]);
	} else {
		_stprintf(szCommandLine, L"%s%s", szIl2StartPath, g_szModTypeExeParms[g_iModType]);
	}
	RenameExe(TRUE);
	SecureSetFileAttributes(szIl2CreatedFilePath, FILE_ATTRIBUTE_NORMAL);
	SecureMoveFile(szIl2CreatedFilePath, szIl2StartPath);
	SecureSetFileAttributes(szIl2StartPath,
		FILE_ATTRIBUTE_HIDDEN);
	if (CreateProcess(NULL, szCommandLine, NULL,
		NULL, FALSE, CREATE_NEW_CONSOLE | CREATE_SUSPENDED, NULL, NULL, &si, &pi))
	{
		DWORD dwExitCodeFailCount = 50;
		DWORD dwExitCode = 0;
		BOOL bIl2LauncherRunning = TRUE;
		while (!IsIl2Process(&pi)) {
			if (GetExitCodeProcess(pi.hProcess, &dwExitCode) == FALSE)
			{
				dwExitCodeFailCount--;
				TRACE(L"IL-2 launcher process Get Exit Code Error No. %d\r\n", 50-dwExitCodeFailCount);
			} else {
				if (dwExitCode != STILL_ACTIVE) dwExitCodeFailCount--;
			}
			if (!dwExitCodeFailCount) {
				bIl2LauncherRunning = FALSE;
				break;
			}
			Sleep(100);
		}
		SecureSetFileAttributes(szIl2StartPath, FILE_ATTRIBUTE_NORMAL);
		SecureMoveFile(szIl2StartPath, szIl2CreatedFilePath);
		SecureSetFileAttributes(szIl2CreatedFilePath,
			FILE_ATTRIBUTE_HIDDEN);
		if (bIl2LauncherRunning) {
			RenameExe(FALSE);
			ResumeThread(pi.hThread);
			PostMessage(g_hWnd, WM_IL2_STARTED, 0, 0);
			SetTimer(g_hWnd, TIMER_REENABLE_START, 5000, NULL);
			CloseHandle(pi.hThread);
			WaitForSingleObject(pi.hProcess, INFINITE);
			CloseHandle(pi.hProcess);
			PostMessage(g_hWnd, WM_IL2_STOPPED, 0, 0);
		} else {
			RenameExe(FALSE);
			PostMessage(g_hWnd, WM_IL2_START_ERROR, 0, 0);
			ShowLastError();
		}
	} else {
		RenameExe(FALSE);
		PostMessage(g_hWnd, WM_IL2_START_ERROR, 0, 0);
		ShowLastError();
	}
	return 0;
}

//************************************
// Method:    GetFilesAndPaths
// FullName:  GetFilesAndPaths
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void GetFilesAndPaths()
{
	memset(szPathName, 0, sizeof(szPathName));
	GetModuleFileName(NULL,szAppPath,MAX_PATH);
	_tcscpy(szPathName, szAppPath);
	LPTSTR pszFileName = NULL;
	pszFileName = _tcsrchr(szPathName, '\\') + 1;
	*pszFileName = '\0';

	_tcscpy(szIniFile, szPathName);
	_tcscat(szIniFile, L"il2fb.ini");
	_tcscpy(szIl2StartPath, szPathName);
	_tcscat(szIl2StartPath, IL2_EXE_NAME);
	_tcscpy(szIl2CreatedFilePath, szPathName);
	_tcscat(szIl2CreatedFilePath, IL2_CREATED_NAME);
	_tcscpy(szAppSparePath, szPathName);
	_tcscat(szAppSparePath, APP_SPARE_NAME);
	_tcscpy(szWrapperFilePath, szPathName);
	_tcscat(szWrapperFilePath, WRAPPER_NAME);
}

//************************************
// Method:    LaunchIl2
// FullName:  LaunchIl2
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void LaunchIl2()
{
	EnableSettingChanges(FALSE);
	if (g_iNumIl2InstancesRunning == 0) CreateIl2FbExe();
	RunWorker(StartIl2Thread);
	KillTimer(g_hWnd, TIMER_COUNTDOWN);
	ShowWindow(GetDlgItem(g_hWnd, IDC_STATIC_START_COUNTDOWN), FALSE);
	EnableWindow(GetDlgItem(g_hWnd, IDC_BUTTON_START_NOW), FALSE);
}

//************************************
// Method:    ShowLastError
// FullName:  ShowLastError
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void ShowLastError()
{
	LPVOID lpMsgBuf;
	FormatMessage( 
		FORMAT_MESSAGE_ALLOCATE_BUFFER | 
		FORMAT_MESSAGE_FROM_SYSTEM | 
		FORMAT_MESSAGE_IGNORE_INSERTS,
		NULL,
		GetLastError(),
		0, // Default language
		(LPTSTR) &lpMsgBuf,
		0,
		NULL 
		);
	// Process any inserts in lpMsgBuf.
	// ...
	// Display the string.
	MessageBox( NULL, (LPCTSTR)lpMsgBuf, L"SAS IL-2 Executable Selector Error", MB_OK | MB_ICONINFORMATION );
	// Free the buffer.
	LocalFree( lpMsgBuf );
}

//************************************
// Method:    IsKeyDown
// FullName:  IsKeyDown
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: int vKey
//************************************
BOOL IsKeyDown(int vKey) {
	return (GetAsyncKeyState(vKey) & 0x8000);
}

//************************************
// Method:    LoadFileInResource
// FullName:  LoadFileInResource
// Access:    public 
// Returns:   void
// Qualifier:
// Parameter: int name
// Parameter: int type
// Parameter: DWORD & size
// Parameter: const char * & data
//************************************
void LoadFileInResource(int name, int type, DWORD& size, const char*& data)
{
	HMODULE handle = GetModuleHandle(NULL);
	HRSRC rc = FindResource(handle, MAKEINTRESOURCE(name), MAKEINTRESOURCE(type));
	HGLOBAL rcData = LoadResource(handle, rc);
	size = SizeofResource(handle, rc);
	data = static_cast<const char*>(LockResource(rcData));
}

//************************************
// Method:    CreateIl2FbExe
// FullName:  CreateIl2FbExe
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void CreateIl2FbExe()
{
	DWORD size = 0;
	const char* data = NULL;
	if (g_iModType == 0) {
		LoadFileInResource(IDR_IL2FB_STOCK, BIN, size, data);
	} else {
		if (g_bTIREnabled) {
			LoadFileInResource(IDR_IL2FB_TIR, BIN, size, data);
		} else {
			LoadFileInResource(IDR_IL2FB_MOD, BIN, size, data);
		}
	}
	DeleteTempIl2Files();
	FILE *outfile = _wfopen (szIl2CreatedFilePath, L"wbT");
	fwrite(data, sizeof(char), size, outfile);
	if (g_iModType != 0) {
		char strMemSize[16];
		memset(strMemSize, 0, 6);
		_itoa_s(g_iRamSize, strMemSize, 16, 10);
		strcat(strMemSize, "M");
		_fseek_nolock(outfile, EXE_OFFSET_XMX, SEEK_SET);
		fwrite(strMemSize, sizeof(char), strlen(strMemSize), outfile);

		if (g_b4GBAddressSpaceEnabled) {
			BYTE dataLAA[1] = EXE_DATA_LAA;
			_fseek_nolock(outfile, EXE_OFFSET_LAA, SEEK_SET);
			fwrite(dataLAA, sizeof(BYTE), 1, outfile);
		}
		if (!g_bMultipleInstancesEnabled) {
			_fseek_nolock(outfile, EXE_OFFSET_MUTEX, SEEK_SET);
			fwrite(EXE_DATA_MUTEX, sizeof(BYTE), strlen(EXE_DATA_MUTEX), outfile);
		}
	}
	fflush(outfile);
	fclose(outfile);
	SecureSetFileAttributes(szIl2CreatedFilePath,
		FILE_ATTRIBUTE_HIDDEN);

	if (g_iModType != 0) {
		LoadFileInResource(IDR_WRAPPER, BIN, size, data);
		outfile = _wfopen (szWrapperFilePath, L"wbT");
		fwrite(data, sizeof(char), size, outfile);
		fclose(outfile);
		SecureSetFileAttributes(szWrapperFilePath,
			FILE_ATTRIBUTE_HIDDEN);
	}
}

//************************************
// Method:    CreateIl2FbExe
// FullName:  CreateIl2FbExe
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void CreateIl2FbExeLL()
{
	DWORD size = 0;
	int fhExe = 0;
	int fhDll = 0;
	int iRet = 0;
	const char* data = NULL;
	if (g_iModType == 0) {
		LoadFileInResource(IDR_IL2FB_STOCK, BIN, size, data);
	} else {
		if (g_bTIREnabled) {
			LoadFileInResource(IDR_IL2FB_TIR, BIN, size, data);
		} else {
			LoadFileInResource(IDR_IL2FB_MOD, BIN, size, data);
		}
	}
	DeleteTempIl2Files();
	//FILE *outfile = _wfopen (szIl2CreatedFilePath, L"wbT");
	fhExe = _topen(szIl2CreatedFilePath, _O_CREAT | _O_BINARY | _O_RANDOM | _O_WRONLY, _S_IWRITE);
	if (fhExe == -1) {
		MessageBox(NULL, L"Error (1) creating il2fb.exe!", L"SAS/UP IL-2 Executable Selector", MB_ICONEXCLAMATION | MB_SETFOREGROUND | MB_TOPMOST | MB_OK);
		return;
	}
	iRet = _write(fhExe, data, size);
	if (iRet == -1) {
		MessageBox(NULL, L"Error (2) creating il2fb.exe!", L"SAS/UP IL-2 Executable Selector", MB_ICONEXCLAMATION | MB_SETFOREGROUND | MB_TOPMOST | MB_OK);
		_close(fhExe);
		return;
	}
	if (g_iModType != 0) {
		char strMemSize[16];
		memset(strMemSize, 0, 6);
		_itoa_s(g_iRamSize, strMemSize, 16, 10);
		strcat(strMemSize, "M");
		iRet = _lseek(fhExe, SEEK_SET, EXE_OFFSET_XMX);
		if (iRet == -1) {
			MessageBox(NULL, L"Error (3) creating il2fb.exe!", L"SAS/UP IL-2 Executable Selector", MB_ICONEXCLAMATION | MB_SETFOREGROUND | MB_TOPMOST | MB_OK);
			_close(fhExe);
			return;
		}
		iRet = _write(fhExe, strMemSize, strlen(strMemSize));
		if (iRet == -1) {
			MessageBox(NULL, L"Error (4) creating il2fb.exe!", L"SAS/UP IL-2 Executable Selector", MB_ICONEXCLAMATION | MB_SETFOREGROUND | MB_TOPMOST | MB_OK);
			_close(fhExe);
			return;
		}
		if (g_b4GBAddressSpaceEnabled) {
			BYTE dataLAA[1] = EXE_DATA_LAA;
			iRet = _lseek(fhExe, SEEK_SET, EXE_OFFSET_LAA);
			if (iRet == -1) {
				MessageBox(NULL, L"Error (5) creating il2fb.exe!", L"SAS/UP IL-2 Executable Selector", MB_ICONEXCLAMATION | MB_SETFOREGROUND | MB_TOPMOST | MB_OK);
				_close(fhExe);
				return;
			}
			iRet = _write(fhExe, dataLAA, 1);
			if (iRet == -1) {
				MessageBox(NULL, L"Error (6) creating il2fb.exe!", L"SAS/UP IL-2 Executable Selector", MB_ICONEXCLAMATION | MB_SETFOREGROUND | MB_TOPMOST | MB_OK);
				_close(fhExe);
				return;
			}
		}
		if (!g_bMultipleInstancesEnabled) {
			iRet = _lseek(fhExe, SEEK_SET, EXE_OFFSET_MUTEX);
			if (iRet == -1) {
				MessageBox(NULL, L"Error (7) creating il2fb.exe!", L"SAS/UP IL-2 Executable Selector", MB_ICONEXCLAMATION | MB_SETFOREGROUND | MB_TOPMOST | MB_OK);
				_close(fhExe);
				return;
			}
			iRet = _write(fhExe, EXE_DATA_MUTEX, strlen(EXE_DATA_MUTEX));
			if (iRet == -1) {
				MessageBox(NULL, L"Error (8) creating il2fb.exe!", L"SAS/UP IL-2 Executable Selector", MB_ICONEXCLAMATION | MB_SETFOREGROUND | MB_TOPMOST | MB_OK);
				_close(fhExe);
				return;
			}
		}
	}
	iRet = _commit(fhExe);
	if (iRet == -1) {
		MessageBox(NULL, L"Error (9) creating il2fb.exe!", L"SAS/UP IL-2 Executable Selector", MB_ICONEXCLAMATION | MB_SETFOREGROUND | MB_TOPMOST | MB_OK);
		_close(fhExe);
		return;
	}
	iRet = _close(fhExe);
	if (iRet == -1) {
		MessageBox(NULL, L"Error (10) creating il2fb.exe!", L"SAS/UP IL-2 Executable Selector", MB_ICONEXCLAMATION | MB_SETFOREGROUND | MB_TOPMOST | MB_OK);
		return;
	}
	SecureSetFileAttributes(szIl2CreatedFilePath,
		FILE_ATTRIBUTE_HIDDEN);

	if (g_iModType != 0) {
		LoadFileInResource(IDR_WRAPPER, BIN, size, data);
		fhDll = _topen(szWrapperFilePath, _O_CREAT | _O_BINARY | _O_RANDOM | _O_WRONLY, _S_IWRITE);
		if (fhDll == -1) {
			MessageBox(NULL, L"Error (1) creating wrapper.dll!", L"SAS/UP IL-2 Executable Selector", MB_ICONEXCLAMATION | MB_SETFOREGROUND | MB_TOPMOST | MB_OK);
			return;
		}
		iRet = _write(fhDll, data, size);
		if (iRet == -1) {
			MessageBox(NULL, L"Error (2) creating wrapper.dll!", L"SAS/UP IL-2 Executable Selector", MB_ICONEXCLAMATION | MB_SETFOREGROUND | MB_TOPMOST | MB_OK);
			_close(fhDll);
			return;
		}
		iRet = _commit(fhDll);
		if (iRet == -1) {
			MessageBox(NULL, L"Error (3) creating wrapper.dll!", L"SAS/UP IL-2 Executable Selector", MB_ICONEXCLAMATION | MB_SETFOREGROUND | MB_TOPMOST | MB_OK);
			_close(fhDll);
			return;
		}
		iRet = _close(fhDll);
		if (iRet == -1) {
			MessageBox(NULL, L"Error (4) creating wrapper.dll!", L"SAS/UP IL-2 Executable Selector", MB_ICONEXCLAMATION | MB_SETFOREGROUND | MB_TOPMOST | MB_OK);
			return;
		}
		SecureSetFileAttributes(szWrapperFilePath,
			FILE_ATTRIBUTE_HIDDEN);
	}
}

//************************************
// Method:    RenameExe
// FullName:  RenameExe
// Access:    public 
// Returns:   void
// Qualifier:
// Parameter: BOOL bStepOutOfWay
//************************************
void RenameExe(BOOL bStepOutOfWay)
{
	if (bStepOutOfWay) {
		SecureMoveFile(szAppPath, szAppSparePath);
	} else {
		SecureMoveFile(szAppSparePath, szAppPath);
	}
}
//************************************
// Method:    DeleteTempIl2Files
// FullName:  DeleteTempIl2Files
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void DeleteTempIl2Files() {
	SecureDeleteFile(szIl2CreatedFilePath);
	SecureDeleteFile(szWrapperFilePath);
}

//************************************
// Method:    IsIl2Process
// FullName:  IsIl2Process
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: DWORD dwProcessId
//************************************
BOOL IsIl2Process(PPROCESS_INFORMATION ppi)
{
	PROCESSENTRY32 processInfo;
	processInfo.dwSize = sizeof(processInfo);

	HANDLE processesSnapshot = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, NULL);
	if ( processesSnapshot == INVALID_HANDLE_VALUE )
		return 0;

	Process32First(processesSnapshot, &processInfo);
	if (processInfo.th32ProcessID == ppi->dwProcessId)
	{
		if (!_tcsnicmp(processInfo.szExeFile, IL2_EXE_NAME, _tcslen(IL2_EXE_NAME)))
		{
			CloseHandle(processesSnapshot);
			return TRUE;
		}
	}

	while ( Process32Next(processesSnapshot, &processInfo) )
	{
		if (processInfo.th32ProcessID == ppi->dwProcessId)
		{
			if (!_tcsnicmp(processInfo.szExeFile, IL2_EXE_NAME, _tcslen(IL2_EXE_NAME)))
			{
				CloseHandle(processesSnapshot);
				return TRUE;
			}
		}
	}

	CloseHandle(processesSnapshot);
	return FALSE;
}

//************************************
// Method:    IsFolderWriteable
// FullName:  IsFolderWriteable
// Access:    public 
// Returns:   BOOL
// Qualifier:
//************************************
BOOL IsFolderWriteable()
{
	return (IsFileWriteable(szIniFile) && IsFileWriteable(szWrapperFilePath) && IsFileWriteable(szIl2CreatedFilePath));
}

//************************************
// Method:    IsFileWriteable
// FullName:  IsFileWriteable
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR szFilePath
//************************************
BOOL IsFileWriteable(LPCTSTR szFilePath) {
	BOOL bRetVal = TRUE;
	FILE* fTest;
	fTest = _wfopen(szFilePath, L"a+");
	if (fTest == NULL)
		bRetVal = FALSE;
	else
		fclose(fTest);
	return bRetVal;
}

//************************************
// Method:    SetFileAttributesWithRetry
// FullName:  SetFileAttributesWithRetry
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR lpFileName
// Parameter: DWORD dwFileAttributes
// Parameter: int iAttempts
//************************************
BOOL SetFileAttributesWithRetry(LPCTSTR lpFileName, DWORD dwFileAttributes, int iAttempts) {
	if (GetFileAttributes(lpFileName) == INVALID_FILE_ATTRIBUTES) return FALSE;
	while (!SetFileAttributes(lpFileName, dwFileAttributes) && (iAttempts > 0)) {
		iAttempts--;
		TRACE(L"SetFileAttributes(%s, %08X) failed, %d attempts left\r\n", lpFileName, dwFileAttributes, iAttempts);
		Sleep(100);
	}
	return (iAttempts > 0);
}

//************************************
// Method:    DeleteFileWithRetry
// FullName:  DeleteFileWithRetry
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR lpFileName
// Parameter: int iAttempts
//************************************
BOOL DeleteFileWithRetry(LPCTSTR lpFileName, int iAttempts) {
	if (GetFileAttributes(lpFileName) == INVALID_FILE_ATTRIBUTES) return TRUE;
	while (!DeleteFile(lpFileName) && (iAttempts > 0)) {
		iAttempts--;
		TRACE(L"DeleteFile(%s) failed, %d attempts left\r\n", lpFileName, iAttempts);
		Sleep(100);
	}
	return (iAttempts > 0);
}

//************************************
// Method:    MoveFileWithRetry
// FullName:  MoveFileWithRetry
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR lpExistingFileName
// Parameter: LPCTSTR lpNewFileName
// Parameter: int iAttempts
//************************************
BOOL MoveFileWithRetry(LPCTSTR lpExistingFileName, LPCTSTR lpNewFileName, int iAttempts) {
	if (GetFileAttributes(lpExistingFileName) == INVALID_FILE_ATTRIBUTES) return FALSE;
	while (!MoveFile(lpExistingFileName, lpNewFileName) && (iAttempts > 0)) {
		iAttempts--;
		TRACE(L"MoveFile(%s, %s) failed, %d attempts left\r\n", lpExistingFileName, lpNewFileName, iAttempts);
		Sleep(100);
	}
	return (iAttempts > 0);
}

//************************************
// Method:    SecureDeleteFile
// FullName:  SecureDeleteFile
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR lpFileName
//************************************
BOOL SecureDeleteFile(LPCTSTR lpFileName) {
	if (GetFileAttributes(lpFileName) == INVALID_FILE_ATTRIBUTES) return TRUE;
	SecureSetFileAttributes(lpFileName, FILE_ATTRIBUTE_NORMAL);
	return DeleteFileWithRetry(lpFileName, FILE_OPERATION_RETRIES);
}

//************************************
// Method:    SecureSetFileAttributes
// FullName:  SecureSetFileAttributes
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR lpFileName
// Parameter: DWORD dwFileAttributes
//************************************
BOOL SecureSetFileAttributes(LPCTSTR lpFileName, DWORD dwFileAttributes) {
	if (GetFileAttributes(lpFileName) == INVALID_FILE_ATTRIBUTES) return FALSE;
	return SetFileAttributesWithRetry(lpFileName, dwFileAttributes, FILE_OPERATION_RETRIES);
}

//************************************
// Method:    SecureMoveFile
// FullName:  SecureMoveFile
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR lpExistingFileName
// Parameter: LPCTSTR lpNewFileName
//************************************
BOOL SecureMoveFile(LPCTSTR lpExistingFileName, LPCTSTR lpNewFileName) {
	if (GetFileAttributes(lpExistingFileName) == INVALID_FILE_ATTRIBUTES) return FALSE;
	SecureSetFileAttributes(lpNewFileName, FILE_ATTRIBUTE_NORMAL);
	SecureDeleteFile(lpNewFileName);
	return MoveFileWithRetry(lpExistingFileName, lpNewFileName, FILE_OPERATION_RETRIES);
}
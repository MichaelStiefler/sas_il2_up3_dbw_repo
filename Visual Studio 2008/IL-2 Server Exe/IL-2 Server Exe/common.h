#pragma once

//*************************************************************************
// Includes
//*************************************************************************
#include <windows.h>

//*************************************************************************
// Defines
//*************************************************************************
#define IL2SERVER_MUTEX				L"SAS/UP IL-2 SERVER EXECUTABLE MUTEX"
#define EXE_OFFSET_XMX				0x0001EBF0
#define EXE_OFFSET_LAA				0x0000010E
#define EXE_DATA_LAA				{0x2F}
#define FILE_OPERATION_RETRIES		50
#define EXE_NAME					L"~~whats.up"
#define DLL_NAME					L"~~teac.up"
#define lengthof(a) (sizeof a / sizeof a[0])
#define RunWorker(ThreadStartRoutine) 	CloseHandle(CreateThread(NULL,0,(LPTHREAD_START_ROUTINE)ThreadStartRoutine,NULL,0,NULL))

//*************************************************************************
// Function Prototypes
//*************************************************************************
void StartIl2Server();
void GetVersionInfo(LPCTSTR lpEntry, LPTSTR lpVersionInfo);
void GetFileVersionX(LPTSTR lpVersionInfo);
void GetProductVersionX(LPTSTR lpVersionInfo);
void ReadIniSettings();
void ShowLastError();
void GetFilesAndPaths();
void CreateIl2FbExe();
void RenameExe(BOOL bStepOutOfWay);
void DeleteTempIl2Files();
BOOL IsIl2Process(PPROCESS_INFORMATION ppi);
BOOL SetFileAttributesWithRetry(LPCTSTR lpFileName, DWORD dwFileAttributes, int iAttempts);
BOOL DeleteFileWithRetry(LPCTSTR lpFileName, int iAttempts);
BOOL MoveFileWithRetry(LPCTSTR lpExistingFileName, LPCTSTR lpNewFileName, int iAttempts);
BOOL SecureDeleteFile(LPCTSTR lpFileName);
BOOL SecureSetFileAttributes(LPCTSTR lpFileName, DWORD dwFileAttributes);
BOOL SecureMoveFile(LPCTSTR lpExistingFileName, LPCTSTR lpNewFileName);
BOOL IsFolderWriteable();
BOOL IsFileWriteable(LPCTSTR szFilePath);
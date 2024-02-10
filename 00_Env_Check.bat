@echo off
setlocal enabledelayedexpansion

echo [94m----------------------------[0m
echo [94mVerify command installation.[0m
echo [94m----------------------------[0m

REM check list.
set "commands=java mvn quasar npm aws"

REM version command.
set "version_options=java:-version mvn:-v quasar:--version npm:-v aws:--version not_found:--version"

REM command check.
for %%i in (%commands%) do (
    set "version_option="
    for %%v in (%version_options%) do (
        for /f "tokens=1,2 delims=:" %%a in ("%%v") do (
            if "%%a"=="%%i" set "version_option=%%b"
        )
    )

    where "%%i" >nul 2>&1
    if not errorlevel 1 (
        echo [32mINSTALLED    %%i[0m

        set "version_info="
        for /f "delims=" %%x in ('%%i !version_option! 2^>^&1') do (
            if "!version_info!"=="" (
                echo              %%x
            ) else (
                echo              %%x
            )
        )
    ) else (
        echo [91m[1m[7mNOT INSTALLED %%i[0m
    )
)

cmd /k echo end file.

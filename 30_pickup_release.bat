@echo off

if exist release\* (
    del /s /q release\*
)else (
    mkdir release
)


xcopy /e /I "aws-low-cost-gpt-client/dist/spa" "release/spa"
cd aws-low-cost-gpt-lambda/target
copy aws-low-cost-gpt-lambda*.jar "../../release"

cmd /k echo end.
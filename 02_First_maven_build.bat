cd aws-low-cost-gpt-model
call mvn clean install
cd ../
cd aws-low-cost-gpt-dummy-api
call mvn clean install
cd ../
cd aws-low-cost-gpt-lambda
call mvn clean install
cmd /k echo end.
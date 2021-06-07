if exist allure-results rmdir allure-results /q /s
call mvn compile
call mvn eclipse:eclipse
call mvn clean test
allure serve allure-results
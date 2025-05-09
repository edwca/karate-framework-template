#!/bin/bash
set -e
#export JAVA_HOME="C:\Program Files\Java\jdk-17"
echo "✅ Iniciando ejecución Karate (ambiente: ${1:-qa})..."
mvn test -Dtest=test.RunAllTests -Dkarate.env=${1:-qa} -Dsurefire.printSummary=false
echo "✅ Ejecución finalizada"

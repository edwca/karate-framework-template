#!/bin/bash
set -euo pipefail

ENVIRONMENT="${1:-qa}"

echo "🚀 Iniciando ejecución de pruebas Karate (entorno: $ENVIRONMENT)..."

# Ejecutar pruebas especificando la clase de test y el entorno
mvn test \
  -Dtest=test.RunAllTests \
  -Dkarate.env="$ENVIRONMENT" \
  -Dsurefire.printSummary=false
  
echo "✅ Ejecución finalizada correctamente para entorno: $ENVIRONMENT"

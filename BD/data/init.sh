#!/bin/bash
set -e

# Espera até que o PostgreSQL esteja pronto via socket
until pg_isready -U admin; do
  echo "A aguardar o PostgreSQL iniciar..."
  sleep 2
done

# Restaura o dump
echo "A restaurar a base de dados a partir do dump..."
pg_restore -U admin -d tastycheck /docker-entrypoint-initdb.d/tastycheck.dump

echo "Restauração concluída."
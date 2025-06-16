#!/bin/bash
# Start WildFly in the background
/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 &

# Espera até o backend estar ativo
until curl -s http://localhost:8080/tastycheck/hello > /dev/null; do
  echo "A aguardar o backend iniciar..."
  sleep 2
done

echo "Backend ativo, pedido inicial feito."

# Mantém o processo principal
wait
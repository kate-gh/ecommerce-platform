#!/bin/bash

HOST="$1"
PORT="$2"
shift 2

echo "Waiting for $HOST:$PORT..."

# Attend que le port soit disponible via bash et /dev/tcp
while ! (echo > /dev/tcp/$HOST/$PORT) 2>/dev/null; do
  echo "Still waiting for $HOST:$PORT..."
  sleep 1
done

echo "$HOST:$PORT is up — starting app"
exec "$@"

#!/bin/bash

HOST="$1"
PORT="$2"
shift 2

echo "Waiting for $HOST:$PORT..."

# Utilise nc (netcat) au lieu de /dev/tcp
until nc -z "$HOST" "$PORT"; do
  echo "Still waiting for $HOST:$PORT..."
  sleep 2
done

echo "$HOST:$PORT is up — starting app"
exec "$@"
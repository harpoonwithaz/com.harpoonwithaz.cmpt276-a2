#!/usr/bin/zsh

# Inject environment variables
set -a && source .env && set +a

# Run the application
./mvnw test -q
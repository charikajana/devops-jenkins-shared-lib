#!/bin/bash
set -e

RELEASE_VERSION=$1

if [ -z "$RELEASE_VERSION" ]; then
  echo "❌ ERROR: Version value missing!"
  echo "Usage: ./update-version.sh <release-version>"
  exit 1
fi

echo "🔧 Updating pom.xml version to: $RELEASE_VERSION"

mvn versions:set -DnewVersion=${RELEASE_VERSION} -DgenerateBackupPoms=false

echo "✔ Version updated successfully!"

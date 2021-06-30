#!/usr/bin/sh

git merge master -m "Merge from master"
npm run release
yes |cp -r resources/public/* docs/
git add docs
git sta
echo git com -m "Release a snapshot"

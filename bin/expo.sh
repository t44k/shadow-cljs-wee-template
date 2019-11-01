#!/bin/bash

pushd `dirname $0`/..

source bin/apply_lan-ip.rc

npx expo-cli start

popd

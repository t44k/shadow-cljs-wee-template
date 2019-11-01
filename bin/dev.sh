#!/bin/bash

pushd `dirname $0`/..

source bin/apply_lan-ip.rc

clojure -A:dev:client:web:electron-main:electron-web:expo:server -m wee.dev

popd

#!/bin/bash

pushd `dirname $0`/..

npx electron .

popd

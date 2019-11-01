#!/bin/bash

URL="http://localhost:9500"

if [[ "$OSTYPE" == "linux-gnu" ]]; then
    xdg-open $URL
elif [[ "$OSTYPE" == "darwin"* ]]; then
    open $URL
else
    start $URL
fi

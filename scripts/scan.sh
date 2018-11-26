#!/bin/sh
export LD_PRELOAD=/lib/libc.so.6
exec /usr/local/uvscan/uvscan --analyse --mime -r --secure --summary $(. cmdLine)  "$@"
#uvscan --version

#!/bin/bash
DEBUG=false
debug() {
   if $DEBUG; then echo "$@" >&2; fi
}   

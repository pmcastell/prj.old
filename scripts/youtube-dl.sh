#!/bin/bash

youtube-dl -f bestaudio --extract-audio --audio-format mp3 --output "%(autonumber)s%(title)s.%(ext)s" $1

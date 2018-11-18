curl 'https://api.ipify.org?format=json' 2>/dev/null | awk -F'"' '{print $4;}'

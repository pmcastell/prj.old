import string
def loadWords2(PATH_TO_FILE="/tmp/loadWords/words2.txt"):
   try:
      inFile = open(PATH_TO_FILE, 'r', 0)
   except IOError as e:
      print "The wordlist doesn't exist; using some fruits for now"
      return ['apple', 'orange', 'pear', 'lime', 'lemon', 'grape', 'pineapple']
   line = inFile.readline()
   wordlist = string.split(line,',')
   print "  ", len(wordlist), "words loaded."
   return wordlist

print loadWords2()
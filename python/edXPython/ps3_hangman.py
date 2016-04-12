# 6.00 Problem Set 3
# 
# Hangman game
#

# -----------------------------------
# Helper code
# You don't need to understand this helper code,
# but you will have to know how to use the functions
# (so be sure to read the docstrings!)

import random
import string

WORDLIST_FILENAME = "words.txt"

def loadWords():
    """
    Returns a list of valid words. Words are strings of lowercase letters.
    
    Depending on the size of the word list, this function may
    take a while to finish.
    """
    print "Loading word list from file..."
    # inFile: file
    inFile = open(WORDLIST_FILENAME, 'r', 0)
    # line: string
    line = inFile.readline()
    # wordlist: list of strings
    wordlist = string.split(line)
    print "  ", len(wordlist), "words loaded."
    return wordlist

def chooseWord(wordlist):
    """
    wordlist (list): list of words (strings)

    Returns a word from wordlist at random
    """
    return random.choice(wordlist)

# end of helper code
# -----------------------------------

# Load the list of words into the variable wordlist
# so that it can be accessed from anywhere in the program
wordlist = loadWords()

def isWordGuessed(secretWord, lettersGuessed):
    '''
    secretWord: string, the word the user is guessing
    lettersGuessed: list, what letters have been guessed so far
    returns: boolean, True if all the letters of secretWord are in lettersGuessed;
      False otherwise
    '''
    wordGuessed=[]
    for i in range(0,len(secretWord)):
        wordGuessed.append("_")
        
    for lg in lettersGuessed:
        for i in range(0,len(secretWord)):
            if (lg==secretWord[i]):
                wordGuessed[i]=secretWord[i]
    for i in range(0,len(secretWord)):
        if (secretWord[i]!=wordGuessed[i]):
            return False
    return True
        

def getGuessedWord(secretWord, lettersGuessed):
    '''
    secretWord: string, the word the user is guessing
    lettersGuessed: list, what letters have been guessed so far
    returns: string, comprised of letters and underscores that represents
      what letters in secretWord have been guessed so far.
    '''
    wordGuessed=[]
    for i in range(0,len(secretWord)):
        wordGuessed.append("_")
        
    for lg in lettersGuessed:
        for i in range(0,len(secretWord)):
            if (lg==secretWord[i]):
                wordGuessed[i]=secretWord[i]
    res=""
    for i in range(0,len(wordGuessed)):
        res+=wordGuessed[i]
    return res



def getAvailableLetters(lettersGuessed):
    '''
    lettersGuessed: list, what letters have been guessed so far
    returns: string, comprised of letters that represents what letters have not
      yet been guessed.
    '''
    letters="abcdefghijklmnopqrstuvwxyz"
    res=""
    for l in letters:
        if (not l in lettersGuessed):
            res+=l
    return res
            
def hangman(secretWord):
    '''
    secretWord: string, the secret word to guess.

    Starts up an interactive game of Hangman.

    * At the start of the game, let the user know how many 
      letters the secretWord contains.
    
    * Ask the user to supply one guess (i.e. letter) per round.

    * The user should receive feedback immediately after each guess 
      about whether their guess appears in the computers word.

    * After each round, you should also display to the user the 
      partially guessed word so far, as well as letters that the 
      user has not yet guessed.

    Follows the other limitations detailed in the problem write-up.
    '''
    print "Welcome to the game, Hangman!"
    print "I am thinking of a word that is",len(secretWord),"letters long."
    lettersEntered=[]
    guessesLeft=8
    while(guessesLeft>0 and not isWordGuessed(secretWord, lettersEntered)):
        print "-------------"
        print "You have",guessesLeft,"guesses left."
        print "Available letters:",getAvailableLetters(lettersEntered)
        l=raw_input("Please guess a letter: ").lower()
        if (l in lettersEntered):
            print "Oops! You've already guessed that letter:",getGuessedWord(secretWord, lettersEntered)
        else:
            lettersEntered.append(l)
            if (l in secretWord):
                print "Good guess:",getGuessedWord(secretWord, lettersEntered)
            else:
                print "Oops! That letter is not in my word:",getGuessedWord(secretWord, lettersEntered)
                guessesLeft-=1
    print "-------------"
    if (isWordGuessed(secretWord, lettersEntered)):
        print "Congratulations, you won!"
    else:
        print "Sorry, you ran out of guesses. The word was",secretWord+"."
        
                
hangman("else")            




# When you've completed your hangman function, uncomment these two lines
# and run this file to test! (hint: you might want to pick your own
# secretWord while you're testing)

# secretWord = chooseWord(wordlist).lower()
# hangman(secretWord)

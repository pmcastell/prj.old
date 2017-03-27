# template for "Guess the number" mini-project
# input will come from buttons and an input field
# all output for the game will be printed in the console
import simplegui, random, math

# initialize global variables used in your code
range_low=0
range_high=100
secret=0
num_guesses=0
# helper functions
# max number of guesses
def max_guesses():
    """ returns max number of tries to guess the number """
    return math.ceil(math.log(range_high-range_low+1,2))

# initialize the game
def init():
    global secret,num_guesses
    secret=random.randrange(0,range_high)
    num_guesses=0
    print ""
    print "New game, Range is from",range_low,"to",range_high
    print "Number of remaining guesses is",max_guesses()
    
# define event handlers for control panel
    
def range100():
    # button that changes range to range [0,100) and restarts
    global range_high
    range_high=100
    init()

def range1000():
    # button that changes range to range [0,1000) and restarts
    global range_high
    range_high=1000
    init()
    
def get_input(guess):
    # main game logic goes here    
    global num_guesses
    player_guess=int(guess)
    num_guesses+=1
    print ""
    print "Guess was",player_guess
    print "Number of remaining guesses is", max_guesses()-num_guesses
    if (player_guess==secret):
        print "Correct!"
        init()
        return
    if (max_guesses()-num_guesses==0):
        print "You ran out of guesses. The number was",secret
        init()
    elif (player_guess<secret):
        print "Higher!"
    else:
        print "Lower!"
    
# create frame
f=simplegui.create_frame("Guess the number", 200, 200)
# add control elements and register event handlers for control elements
f.add_button("Range is [0,100)",range100,200)
f.add_button("Range is [0,1000)",range1000,200)
f.add_input("Enter a guess",get_input,200)

init()
# start frame
f.start()
# always remember to check your completed program against the grading rubric

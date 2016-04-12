def f(x):
    import math
    return 10*math.e**(math.log(0.5)/5.27 * x)

def radiationExposure(start, stop, step):
    '''
    Computes and returns the amount of radiation exposed
    to between the start and stop times. Calls the 
    function f (defined for you in the grading script)
    to obtain the value of the function at any point.
 
    start: integer, the time at which exposure begins
    stop: integer, the time at which exposure ends
    step: float, the width of each rectangle. You can assume that
      the step size will always partition the space evenly.

    returns: float, the amount of radiation exposed to 
      between start and stop times.
    '''
    pos=float(start)
    area=0.0
    while (pos<stop):
        if (pos+step<stop):
            area+=f(pos)*step
        else:
            area+=f(pos)*(stop-pos)
        pos+=step
        
    return area
#print radiationExposure(0, 5, 1)


low=0
high=100
print "Please think of a number between 0 and 100!"
while low<high:
    guess=(low+high)/2
    ans=""
    while (ans not in ['h','l','c']):
        print "Is your secret number",str(guess)+"?"
        ans=raw_input("Enter 'h' to indicate the guess is too high. Enter 'l' to indicate the guess is too low. Enter 'c' to indicate I guessed correctly. ")   
        if (ans=='l'):
            low=guess
        elif (ans=='h'):
            high=guess
        elif (ans=='c'):
            low=high=guess
            break
        else:
            print "Sorry, I did not understand your input."
print "Game over. Your secret number was:",guess
        

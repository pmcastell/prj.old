# implementation of card game - Memory

import simplegui, random, math

CARD_HEIGHT=100; CARD_WIDTH=CARD_HEIGHT//2
cards=[]
exposed=[]
WIDTH=1100;HEIGHT=600
cards_per_row=8
number_of_pairs=8
state=0
last_pair=[0,0]
number_of_moves=0
#WIDTH=cards_per_row*CARD_WIDTH; HEIGHT=((len(cards)-1)//cards_per_row+1)*CARD_HEIGHT
# helper function to initialize globals
def init():
    global cards, exposed, cards_per_row, state, last_pair, number_of_moves
    cards=[]
    for i in range(number_of_pairs):
        cards.append(i); cards.append(i)
    ###cards=range(number_of_pairs); cards+=cards
    random.shuffle(cards)
    exposed=[False for i in range(len(cards))]
    cards_per_row=int(math.sqrt(len(cards)))*2
    state=0; last_pair=[0,0]; number_of_moves=0
    label.set_text("Moves = "+str(number_of_moves))
    inPairs.set_text(str(number_of_pairs))
    if (message.get_text()=="You did it!!!"):
        message.set_text("")

def finished():
    for c in exposed:
        if (not c):
            return False
    return True
     
# define event handlers
def mouseclick(pos):
    # add game state logic here
    global state, last_pair, exposed, number_of_moves
    if (pos[0]//CARD_WIDTH<cards_per_row):
        card=(pos[1]//CARD_HEIGHT)*cards_per_row+pos[0]//CARD_WIDTH
        if (card<=len(cards)-1 and exposed[card]==False):
            exposed[card]=True
            if (state==0):
                state=1
                last_pair[0]=card
                number_of_moves+=1
            elif (state==1):
                last_pair[1]=card
                state=2
            elif (state==2):
                if (cards[last_pair[0]]!=cards[last_pair[1]]):
                    exposed[last_pair[0]]=False; exposed[last_pair[1]]=False
                last_pair[0]=card
                state=1
                number_of_moves+=1
                
# cards are logically 50x100 pixels in size    
def draw(canvas):
    x=0; y=0; font_width=CARD_HEIGHT//3
    label.set_text("Moves = "+str(number_of_moves))
    for i in range(len(cards)):
        if (exposed[i]):
            canvas.draw_polygon([(x,y),(x+CARD_WIDTH,y),(x+CARD_WIDTH,y+CARD_HEIGHT),(x,y+CARD_HEIGHT)],1,"White","#660066")
            if (cards[i]<10):
                posx=x+font_width//2
            else:
                posx=x+font_width//10
            canvas.draw_text(str(cards[i]),[posx,y+CARD_HEIGHT-font_width],font_width,"White")
        else:
            canvas.draw_polygon([(x,y),(x+CARD_WIDTH,y),(x+CARD_WIDTH,y+CARD_HEIGHT),(x,y+CARD_HEIGHT)],1,"White","Green")
        if ((i +1) % cards_per_row == 0):
            x=0; y+=CARD_HEIGHT
        else:
            x+=CARD_WIDTH
    if (finished()):
        message.set_text("You did it!!!")
        #canvas.draw_line([x-5,0],[x-5,HEIGHT],1,"White")

def get_number_of_pairs(text):
    global number_of_pairs
    if (text.isdigit()):
        number_of_pairs=int(text)
        if (number_of_pairs<8): 
            number_of_pairs=8
            message.set_text("Sorry min: 8")
        if (number_of_pairs>64): 
            number_of_pairs=64
            message.set_text("Sorry max: 64")
        inPairs.set_text(str(number_of_pairs))
        init()
    else:
        message.set_text("Error in number: "+text)
        inPairs.set_text(str(number_of_pairs))
    
# create frame and add a button and labels
frame = simplegui.create_frame("Memory", WIDTH, HEIGHT)
frame.add_button("Restart", init,200)
inPairs=frame.add_input("Number of Pairs:",get_number_of_pairs,200)
label = frame.add_label("Moves = 0")
message = frame.add_label("")

# initialize global variables
init()

# register event handlers
frame.set_mouseclick_handler(mouseclick)
frame.set_draw_handler(draw)

# get things rolling
frame.start()


# Always remember to review the grading rubric

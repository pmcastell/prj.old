# Mini-project #6 - Blackjack

import simplegui, random

# load card sprite - 949x392 - source: jfitz.com
CARD_SIZE = (73, 98)
CARD_CENTER = (36.5, 49)
card_images = simplegui.load_image("http://commondatastorage.googleapis.com/codeskulptor-assets/cards.jfitz.png")

CARD_BACK_SIZE = (71, 96)
CARD_BACK_CENTER = (35.5, 48)
card_back = simplegui.load_image("http://commondatastorage.googleapis.com/codeskulptor-assets/card_back.png")    

# initialize some useful global variables=
in_play = False
message = ""
outcome = ""
score = 0
# frame dimensions
WIDTH=950
HEIGHT=550
cheating=False
# define globals for cards
SUITS = ('C', 'S', 'H', 'D')
RANKS = ('A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K')
VALUES = {'A':1, '2':2, '3':3, '4':4, '5':5, '6':6, '7':7, '8':8, '9':9, 'T':10, 'J':10, 'Q':10, 'K':10}

deck=None
dealear_hand=None
player_hand=None

# define card class
class Card:
    def __init__(self, suit, rank,facedUp=False):
        if (suit in SUITS) and (rank in RANKS):
            self.suit = suit
            self.rank = rank
            self.facedUp=facedUp
            self.pos=None
        else:
            self.suit = None
            self.rank = None
            self.facedUp=None
            self.pos=None
            print "Invalid card: ", suit, rank

    def __str__(self):
        return self.suit + self.rank

    def get_suit(self):
        return self.suit

    def get_rank(self):
        return self.rank
    
    def get_facedUp(self):
        return self.facedUp
    
    def set_facedUp(self,facedUp):
        self.facedUp=facedUp
    
    def click(self,pos): #determine if click inside card and, in that case, change facedUp state
        if (self.pos <> None):
            #if (self.pos[0]<=pos[0] and pos[0]<=self.pos[0]+CARD_BACK_SIZE[0] and self.pos[1]<=pos[1] and pos[1]<=self.pos[1]+CARD_BACK_SIZE[1]):
            if (self.click_in_card(pos)):
                self.turn()
                
    def click_in_card(self,pos):
        if (self.pos<>None):
            if (self.pos[0]<=pos[0] and pos[0]<=self.pos[0]+CARD_BACK_SIZE[0] and 
                self.pos[1]<=pos[1] and pos[1]<=self.pos[1]+CARD_BACK_SIZE[1]):
                return True
        return False
    
    def turn(self):
        self.facedUp=not self.facedUp
                
    def draw(self, canvas, pos):
        self.pos=list(pos) # make a copy
        if (self.facedUp):
            card_loc = (CARD_CENTER[0] + CARD_SIZE[0] * RANKS.index(self.rank), 
                    CARD_CENTER[1] + CARD_SIZE[1] * SUITS.index(self.suit))
            canvas.draw_image(card_images, card_loc, CARD_BACK_SIZE, [pos[0] + CARD_CENTER[0], pos[1] + CARD_CENTER[1]], CARD_BACK_SIZE)
        else:
            canvas.draw_image(card_back, CARD_BACK_CENTER, CARD_BACK_SIZE, [pos[0] + CARD_BACK_CENTER[0], pos[1] + CARD_BACK_CENTER[1]], CARD_BACK_SIZE)
            
class CardGroup:
    def __init__(self):
        self.cards=[]
        self.maxNumber=0
        
    def __str__(self):
        res="["; length=len(self.cards);
        i=0
        while(i<length):
            res+=str(self.cards[i])
            if (i<length-1):
                res+=","
            i+=1
        res+="]"
        return res
            
    def add_card(self, card):
        self.cards.append(card)    # add a card object to a hand
        self.maxNumber+=1

    def change_facedUp(self,card_index,facedUp):
        if (card_index>=0 and card_index<len(self.cards)):
            self.cards[card_index].set_facedUp(facedUp)
        else:
            print "Error card",card_index,"doen's exist in this hand"

    def draw(self,canvas,pos):
        pos=list(pos) # make a copy of pos not to modify original
        distance=CARD_BACK_SIZE[0]+CARD_BACK_SIZE[0]/5
        while(pos[0]+distance*(self.maxNumber+3)>WIDTH):
            distance-=1
        for card in self.cards:
            card.draw(canvas,pos)
            pos[0]+=distance

 
    def click(self,pos): #if mouse clicked in a card change it's facedUp State
        i=len(self.cards)-1
        while(i>=0):
            card=self.cards[i]
            actualState=card.get_facedUp()
            card.click(pos)
            if (actualState<>card.get_facedUp()):
                return
            i-=1

    def drag(self,list_pos): #when mouse dragged over the cards turn them upside down
        i=self.click_card_index(list_pos[0])
        j=self.click_card_index(list_pos[1])
        if (i>=0 and j>=0):
            if (j<i): temp=i; i=j; j=temp # swap values between i and j
            while(i<=j): #we go from right to left turning cards upside-down
                self.turn_card(j)
                j-=1
        

    def click_card_index(self,pos): # return the index of a card in the deck that is positioned at pos in the desk, -1 if no one is at pos
        i=len(self.cards)-1
        while(i>=0):
            card=self.cards[i]
            if (card.click_in_card(pos)):
                return i
            else:
                i-=1
        return -1

    def turn_card(self,index):
        card=self.cards[index]
        card.turn()
    
    def get_number_of_cards(self):
        return len(self.cards)

    def set_all_facedUp(self,facedUp):
        for card in self.cards:
            card.set_facedUp(facedUp)
        
# define hand class
class Hand(CardGroup):
    def __init__(self):
        CardGroup.__init__(self)    # create Hand object

    def __str__(self):
        # return a string representation of a hand
        return "Hand contains: "+CardGroup.__init__(self)

    def get_value(self):
        # count aces as 1, if the hand has an ace, then add 10 to hand value if it doesn't bust
        count=0    # compute the value of the hand, see Blackjack video
        foundAce=False
        for card in self.cards:
            count+=VALUES[card.get_rank()]
            if (card.get_rank()=='A'):
                foundAce=True
        if (not foundAce):
            return count
        else:
            if (count+10>21):
                return count
            else:
                return count+10
        
# define deck class 
class Deck(CardGroup):
    def __init__(self):
        self.cards=[Card(suit,rank) for suit in SUITS for rank in RANKS]    # create a Deck object
        self.maxNumber=len(self.cards)

    def shuffle(self):
        # add cards back to deck and shuffle
        random.shuffle(self.cards)    # use random.shuffle() to shuffle the deck

    def deal_card(self,facedUp=False):
        card=self.cards.pop()
        if (len(self.cards)==0):
            self.__init__()
            self.shuffle()
        card.set_facedUp(facedUp)
        return card    # deal a card object from the deck
    
    def __str__(self): # return a string representing the deck
        return "Deck contains: "+CardGroup.__init__(self)
    
def init():
    global deck
    deck=Deck()
    deal()

#define event handlers for buttons
def deal():
    global outcome, message, in_play, dealer_hand, player_hand, score
    message=""
    outcome="Hit or stand?"
    # your code goes here
    if (in_play):
        message="You lost the round"
        score-=1
    deck.shuffle()
    dealer_hand=Hand()
    player_hand=Hand()
    for i in range(2):
        card=deck.deal_card()
        if (i>0):
            card.set_facedUp(True)
        dealer_hand.add_card(card)
        player_hand.add_card(deck.deal_card(True))
    in_play = True

def hit():
    global outcome, message, in_play, score
    # if the hand is in play, hit the player
    if (in_play):
        player_hand.add_card(deck.deal_card(True))
        # if busted, assign a message to outcome, update in_play and score
        if (player_hand.get_value()>21):
            message="You went bust and lose."
            outcome="New deal?"
            in_play=False
            dealer_hand.change_facedUp(0, True)
            score-=1
           
def stand():
    global outcome, message, in_play, score
    # if hand is in play, repeatedly hit dealer until his hand has value 17 or more
    if (in_play):
        in_play=False
        dealer_hand.change_facedUp(0, True)
        while(dealer_hand.get_value()<17):
            dealer_hand.add_card(deck.deal_card(True))
        # assign a message to outcome, update in_play and score
        if (dealer_hand.get_value()>21 or player_hand.get_value()>dealer_hand.get_value()):
            message="You win."
            score+=1
        else: # (player_hand.get_value()<=dealer_hand.get_value()):
            message="You lose."
            score-=1
        outcome="New deal?"



drag_points=[]
def mouse_click(pos):
    global drag_points
    if (cheating):
        drag_points.append(pos)
        if (len(drag_points)>1):
            deck.drag(drag_points)
            player_hand.drag(drag_points)
            dealer_hand.drag(drag_points)
        else:
            deck.click(pos)
            player_hand.click(pos)
            dealer_hand.click(pos)
        drag_points=[]
    
def drag(pos):
    if (cheating):
        if (len(drag_points)<1):
            drag_points.append(pos)

# draw handler    
def draw(canvas):
    # test to make sure that card.draw works, replace with your code below
    BASEX=WIDTH/22
    BASEY=HEIGHT/7.5
    FONT_SIZE=30
    canvas.draw_text("Blackjack            Score: ", [BASEX,BASEY/2.5],FONT_SIZE,"Cyan")
    if (score<0): color="Red" 
    else: color="Cyan"
    canvas.draw_text(str(score), [BASEX*10,BASEY/2.5],FONT_SIZE,color)
    canvas.draw_text("Deck ["+str(deck.get_number_of_cards())+" cards left]",[BASEX/3,BASEY],FONT_SIZE,"Black")
    deck.draw(canvas,[BASEX/2,BASEY*1.25])
    canvas.draw_text("Dealer        "+message, [BASEX/3,BASEY*3.25],FONT_SIZE,"Black")
    dealer_hand.draw(canvas, [BASEX/2,BASEY*3.5])
    canvas.draw_text("Player        "+outcome, [BASEX/3,BASEY*5.5],FONT_SIZE,"Black")
    player_hand.draw(canvas, [BASEX/2,BASEY*5.75])
    
def cheat():
    global cheating
    if (cheating):
        cheating=False
        cheat_button.set_text("Cheat")
        label.set_text("")
        deck.set_all_facedUp(False)
        player_hand.set_all_facedUp(True)
        dealer_hand.set_all_facedUp(True)
        if (in_play):
            dealer_hand.change_facedUp(0, False)
    else:
        cheating=True
        cheat_button.set_text("Don't Cheat")
        label.set_text("Now you can click on or drag the mouse over the cards to face them up an down. But, have you seen the movie 21? Be careful not to end up like the main character :-D")
    

# initialization frame
frame = simplegui.create_frame("Blackjack", WIDTH, HEIGHT)
frame.set_canvas_background("Green")

#create buttons and canvas callback
frame.add_button("Deal", deal, 200)
frame.add_button("Hit",  hit, 200)
frame.add_button("Stand", stand, 200)
cheat_button=frame.add_button("Cheat",cheat,200)
label = frame.add_label("")
frame.set_draw_handler(draw)
frame.set_mouseclick_handler(mouse_click)
frame.set_mousedrag_handler(drag)
init()

# get things rolling
frame.start()


# remember to review the gradic rubric
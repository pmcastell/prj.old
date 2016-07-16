# Implementation of classic arcade game Pong
import simplegui, random
# initialize globals - pos and vel encode vertical info for paddles
WIDTH = 600; HEIGHT = 400; BALL_RADIUS = 20; PAD_WIDTH = 8; PAD_HEIGHT = 80
HALF_PAD_WIDTH = PAD_WIDTH / 2; HALF_PAD_HEIGHT = PAD_HEIGHT / 2
ball_pos=[0.0,0.0]; ball_vel=[0.0,0.0]
paddle1_pos=paddle2_pos=HALF_PAD_HEIGHT
paddle1_vel=paddle2_vel=0.0
score1=score2=0
PAD_CONSTANT_VEL=10 # the velocity with which paddles move
gravity_activated=False #activates g acceleration
g_accel=0 #g acceleration, 0 default
pause_activated=False # is the game paused?
ball_vel_save=ball_vel # if the game is paused set bal_vel to 0 and save old velocity
computer_plays=0 #Wanna play against the computer? you'll always lose
# helper function that spawns a ball by updating the 
# ball's position vector and velocity vector
# if right is True, the ball's velocity is upper right, else upper left
def ball_init(right):
    global ball_pos, ball_vel # these are vectors stored as lists
    if (gravity_activated): ball_pos=[WIDTH/2,HEIGHT/3] # if gravity is activated ball starts higher
    else: ball_pos=[WIDTH/2,HEIGHT/2]
    horizontal_speed=random.randrange(4, 6)
    ball_vel[1]=float(-random.randrange(4, 6))
    if (right): ball_vel[0]=float(horizontal_speed)
    else: ball_vel[0]=float(-horizontal_speed)

def new_game():
    global paddle1_pos, paddle2_pos, paddle1_vel, paddle2_vel  # these are floats
    global score1, score2  # these are ints
    paddle1_pos=paddle2_pos=HEIGHT / 2;
    paddle1_vel=paddle2_vel=0.0
    score1=score2=0
    ball_init(True)
    
# define event handlers

def draw(c):
    global score1, score2, paddle1_pos, paddle2_pos, ball_pos, ball_vel, paddle1_vel, paddle2_vel
    # update paddle's vertical position, keep paddle on the screen
    paddle1_pos+=paddle1_vel; paddle2_pos+=paddle2_vel
    if (paddle1_pos<HALF_PAD_HEIGHT): paddle1_pos=HALF_PAD_HEIGHT
    if (paddle1_pos>HEIGHT-HALF_PAD_HEIGHT): paddle1_pos=HEIGHT-HALF_PAD_HEIGHT
    if (paddle2_pos<HALF_PAD_HEIGHT): paddle2_pos=HALF_PAD_HEIGHT
    if (paddle2_pos>HEIGHT-HALF_PAD_HEIGHT): paddle2_pos=HEIGHT-HALF_PAD_HEIGHT
    # draw mid line and gutters
    c.draw_line([WIDTH / 2, 0],[WIDTH / 2, HEIGHT], 1, "White")
    c.draw_line([PAD_WIDTH, 0],[PAD_WIDTH, HEIGHT], 1, "White")
    c.draw_line([WIDTH - PAD_WIDTH, 0],[WIDTH - PAD_WIDTH, HEIGHT], 1, "White")
    # draw paddles
    if (computer_plays>=1):
        paddle1_pos=ball_pos[1]
    if (computer_plays>=2):
        paddle2_pos=ball_pos[1]
    c.draw_polygon([(0,paddle1_pos-HALF_PAD_HEIGHT), (PAD_WIDTH, paddle1_pos-HALF_PAD_HEIGHT), (PAD_WIDTH, paddle1_pos+HALF_PAD_HEIGHT),
                    (0,paddle1_pos+HALF_PAD_HEIGHT)], 1, "White","White") 
    c.draw_polygon([(WIDTH-PAD_WIDTH,paddle2_pos-HALF_PAD_HEIGHT), (WIDTH-1, paddle2_pos-HALF_PAD_HEIGHT), (WIDTH-1, paddle2_pos+HALF_PAD_HEIGHT),
                    (WIDTH-PAD_WIDTH,paddle2_pos+HALF_PAD_HEIGHT)], 1, "White","White") 
    # update ball
    ball_pos[0]+=ball_vel[0]; ball_pos[1]+=ball_vel[1]
    if (ball_pos[1]<=BALL_RADIUS or ball_pos[1]>=HEIGHT-1-BALL_RADIUS):
        ball_vel[1]=-ball_vel[1]-g_accel
        if (ball_pos[1]<BALL_RADIUS): ball_pos[1]=BALL_RADIUS
        if (ball_pos[1]>HEIGHT-1-BALL_RADIUS): ball_pos[1]=HEIGHT-1-BALL_RADIUS
    #print "g_accel: "+str(g_accel)            
    #print "Antes: "+str(ball_vel[1])            
    ball_vel[1]+=g_accel
    #print "Despues: "+str(ball_vel[1])
    if (ball_pos[0]<=PAD_WIDTH+BALL_RADIUS): #touching the left gutter
        if (abs(paddle1_pos-ball_pos[1])<=HALF_PAD_HEIGHT+BALL_RADIUS*3/4): #bounce
            ball_vel[0]=-ball_vel[0] #reflect horizontal velocity
            if (abs(ball_vel[0])<=20):
                ball_vel[0]=ball_vel[0]*1.1 #increment horizontal velocity
        else:
            ball_init(True)
            score2+=1
    if (ball_pos[0]>=WIDTH-1-PAD_WIDTH-BALL_RADIUS):
        if (abs(paddle2_pos-ball_pos[1])<=HALF_PAD_HEIGHT+BALL_RADIUS*3/4): #BOUNCE
            ball_vel[0]=-ball_vel[0] #reflect horizontal velocity
            if (abs(ball_vel[0])<=20):
                ball_vel[0]=ball_vel[0]*1.1 #increment horizontal velocity
        else:
            ball_init(False)
            score1+=1
    #if (abs(ball_vel[0])>10):
        #ball_vel[0]=10
    # draw ball and scores
    c.draw_text(str(score1), [WIDTH/4-24,HEIGHT/8], 48, "White")
    c.draw_text(str(score2), [WIDTH*3/4-24,HEIGHT/8], 48, "White")
    c.draw_circle(ball_pos,BALL_RADIUS,2,"White","White")
        
def keydown(key):
    global paddle1_vel, paddle2_vel, paddle2_pos
    if (not computer_plays):
        if   (key==simplegui.KEY_MAP['w']): paddle1_vel=-PAD_CONSTANT_VEL
        elif (key==simplegui.KEY_MAP['s']): paddle1_vel=PAD_CONSTANT_VEL
    if (key==simplegui.KEY_MAP['up']): paddle2_vel=-PAD_CONSTANT_VEL
    elif (key==simplegui.KEY_MAP['down']): paddle2_vel=PAD_CONSTANT_VEL

def keyup(key):
    global paddle1_vel, paddle2_vel
    if   (key==simplegui.KEY_MAP['w']): paddle1_vel=0
    elif (key==simplegui.KEY_MAP['s']): paddle1_vel=0
    elif (key==simplegui.KEY_MAP['up']): paddle2_vel=0
    elif (key==simplegui.KEY_MAP['down']): paddle2_vel=0
    
def restart():
    new_game()

def gravity():
    global gravity_activated, g_accel
    gravity_activated=not gravity_activated
    if (gravity_activated):
        gravity_button.set_text("Deactivate Gravity")
        g_accel=1.0/6
        #print "function gravity: g_accel: "+str(g_accel)
        frame.set_canvas_background("#220022")
    else:
        gravity_button.set_text("Activate Gravity")
        g_accel=0
        frame.set_canvas_background("Black")
        
def pause():
    global pause_activated, ball_vel_save, ball_vel
    pause_activated=not pause_activated
    if (pause_activated):
        ball_vel_save=list(ball_vel)
        ball_vel=[0.0,0.0]
        pause.set_text("Go on")
    else:
        ball_vel=list(ball_vel_save)
        pause.set_text("Pause")

def players():
    global computer_plays
    computer_plays=(computer_plays + 1) % 3
    if (computer_plays==0):
        players.set_text("Two Players")
    elif (computer_plays==1):
        players.set_text("Play Against Computer")
    else:
        players.set_text("Computer Plays alone")
            

# create frame
frame = simplegui.create_frame("Pong", WIDTH, HEIGHT)
frame.set_draw_handler(draw)
frame.set_keydown_handler(keydown)
frame.set_keyup_handler(keyup)
frame.add_button("Restart",restart,200)
gravity_button=frame.add_button("Activate Gravity",gravity,200)
pause=frame.add_button("Pause",pause,200)
players=frame.add_button("Play Against Computers",players,200)


# start frame
frame.start()
new_game()


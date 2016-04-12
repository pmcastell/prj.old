# Implementation of classic arcade game Pong
import simplegui, random, math
# initialize globals - pos and vel encode vertical info for paddles
WIDTH = 600; HEIGHT = 400; BALL_RADIUS = 20; PAD_WIDTH = 8; PAD_HEIGHT = 80
HALF_PAD_WIDTH = PAD_WIDTH / 2; HALF_PAD_HEIGHT = PAD_HEIGHT / 2
ball_pos=[0,0]; ball_vel=[0,0]
paddle1_pos=HALF_PAD_HEIGHT; paddle2_pos=HALF_PAD_HEIGHT
paddle1_vel=0.0; paddle2_vel=0.0
score1=0; score2=0
PAD_CONSTANT_VEL=10
# helper function that spawns a ball by updating the 
# ball's position vector and velocity vector
# if right is True, the ball's velocity is upper right, else upper left
def ball_init(right):
    global ball_pos, ball_vel # these are vectors stored as lists
    ball_pos=[WIDTH/2,HEIGHT/2]
    horizontal_speed=random.randrange(3, 4)#random.randrange(120, 240)
    ball_vel[1]=-random.randrange(3, 4)#random.randrange(60, 180)
    if (right): ball_vel[0]=horizontal_speed
    else: ball_vel[0]=-horizontal_speed

def new_game():
    global paddle1_pos, paddle2_pos, paddle1_vel, paddle2_vel  # these are floats
    global score1, score2  # these are ints
    paddle1_pos=paddle2_pos=HEIGHT / 2;
    paddle1_vel=paddle2_vel=0.0
    score1=score2=0
    ball_init(True)

def change_vel(paddle_pos):
    modulus_vel=math.sqrt(ball_vel[0]**2+ball_vel[1]**2)
    sign=1 if (ball_vel[0]<0) else -1
    #ball_vel[0]=-ball_vel[0]*1.1
    if (abs(ball_pos[1]-paddle_pos)>=0.01):
        if (ball_vel[1]<=0.01):
            ball_vel[1]+=((ball_pos[1]-paddle_pos)*4/HALF_PAD_HEIGHT)
        else:
            ball_vel[1]*=((ball_pos[1]-paddle_pos)/(HALF_PAD_HEIGHT+BALL_RADIUS*3/4))
        if (math.sqrt(modulus_vel**2-ball_vel[1]**2)>=0):
            ball_vel[0]=math.sqrt(modulus_vel**2-ball_vel[1]**2)*sign
    else:
        ball_vel[0]=-ball_vel[0]    
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
    c.draw_polygon([(0,paddle1_pos-HALF_PAD_HEIGHT), (PAD_WIDTH, paddle1_pos-HALF_PAD_HEIGHT), (PAD_WIDTH, paddle1_pos+HALF_PAD_HEIGHT),
                    (0,paddle1_pos+HALF_PAD_HEIGHT)], 1, "White","White") 
    c.draw_polygon([(WIDTH-PAD_WIDTH,paddle2_pos-HALF_PAD_HEIGHT), (WIDTH-1, paddle2_pos-HALF_PAD_HEIGHT), (WIDTH-1, paddle2_pos+HALF_PAD_HEIGHT),
                    (WIDTH-PAD_WIDTH,paddle2_pos+HALF_PAD_HEIGHT)], 1, "White","White") 
    # update ball
    ball_vel[1]+=1/6
    ball_pos[0]+=ball_vel[0]; ball_pos[1]+=ball_vel[1]
    if (ball_pos[1]<=BALL_RADIUS or ball_pos[1]>=HEIGHT-1-BALL_RADIUS):
        ball_vel[1]=-ball_vel[1]
    if (ball_pos[0]<=PAD_WIDTH+BALL_RADIUS): #touching the left gutter
        if (abs(paddle1_pos-ball_pos[1])<=HALF_PAD_HEIGHT+BALL_RADIUS*3/4): #bounce
            change_vel(paddle1_pos)
        else:
            ball_init(True)
            score2+=1
    if (ball_pos[0]>=WIDTH-1-PAD_WIDTH-BALL_RADIUS):
        if (abs(paddle2_pos-ball_pos[1])<=HALF_PAD_HEIGHT+BALL_RADIUS*3/4): #BOUNCE
            change_vel(paddle2_pos)
        else:
            ball_init(False)
            score1+=1
    # draw ball and scores
    c.draw_text(str(score1), [WIDTH/4-24,HEIGHT/8], 48, "White")
    c.draw_text(str(score2), [WIDTH*3/4-24,HEIGHT/8], 48, "White")
    c.draw_circle(ball_pos,BALL_RADIUS,2,"White","White")
        
def keydown(key):
    global paddle1_vel, paddle2_vel
    if (key==simplegui.KEY_MAP['w']): paddle1_vel=-PAD_CONSTANT_VEL
    elif (key==simplegui.KEY_MAP['s']): paddle1_vel=PAD_CONSTANT_VEL
    elif (key==simplegui.KEY_MAP['up']): paddle2_vel=-PAD_CONSTANT_VEL
    elif (key==simplegui.KEY_MAP['down']): paddle2_vel=PAD_CONSTANT_VEL

def keyup(key):
    global paddle1_vel, paddle2_vel
    if (key==simplegui.KEY_MAP['w']): paddle1_vel=0
    elif (key==simplegui.KEY_MAP['s']): paddle1_vel=0
    elif (key==simplegui.KEY_MAP['up']): paddle2_vel=0
    elif (key==simplegui.KEY_MAP['down']): paddle2_vel=0
    
def restart():
    new_game()
        

# create frame
frame = simplegui.create_frame("Pong", WIDTH, HEIGHT)
frame.set_draw_handler(draw)
frame.set_keydown_handler(keydown)
frame.set_keyup_handler(keyup)
frame.add_button("Restart",restart,200)

# start frame
frame.start()
new_game()
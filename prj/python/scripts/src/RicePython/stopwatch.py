# template for "Stopwatch: The Game"
import simplegui
# define global variables
sectenths=0
num_tries=0
score=0
width=300
height=200

def init():
    global sectenths,num_tries,score
    sectenths=0
    num_tries=0
    score=0

# define helper function format that converts time
# in tenths of seconds into formatted string A:BC.D
def format(t):
    nsecs=(sectenths//10)
    minutes=nsecs // 60
    seconds=nsecs - minutes*60
    if (seconds<10):
        strSecs='0'+str(seconds)
    else:
        strSecs=str(seconds)
    tenths=sectenths-10*nsecs
    return str(minutes)+':'+strSecs+'.'+str(tenths)
    
# define event handlers for buttons; "Start", "Stop", "Reset"
def start():
    timer.start()

def stop():
    global score, num_tries
    timer.stop()
    num_tries+=1
    if (sectenths-10*(sectenths//10)==0):
        score+=1
        

def reset():
    init()
    timer.stop()
    
# define event handler for timer with 0.1 sec interval
def tick():
    global sectenths
    sectenths+=1
    #print format(sectenths)

# define draw handler
def drawh(canvas):
    canvas.draw_text(format(sectenths), (width//2-50, height//2+18), 36, "White")
    canvas.draw_text(str(score)+'/'+str(num_tries),(width-70,30),24,"Green")
    
# create frame
frame=simplegui.create_frame("Stop Watch", width, height)
timer = simplegui.create_timer(100, tick)
# register event handlers

frame.add_button("Start",start,200)
frame.add_button("Stop",stop,200)
frame.add_button("Reset",reset,200)
frame.set_draw_handler(drawh)
# start frame
init()
frame.start()
# Please remember to review the grading rubric

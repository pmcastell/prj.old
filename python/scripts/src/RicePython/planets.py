# Implementation of classic arcade game Pong
import simplegui, random, math
# initialize globals - pos and vel encode vertical info for paddles
WIDTH = 900; HEIGHT = 600;  
K=1000
INCT=4/1
pause_activated=False

class planet:
    def __init__(self,x,y,radius,vx,vy,color):
        self.x=x; self.y=y; self.radius=radius; self.oldvx=self.vx=vx; self.oldvy=self.vy=vy; self.color=color
        self.init=[x,y,radius,vx,vy,color]; self.positions=[]; self.MAX_POS=120; self.moveCounter=0
    def posModulusSquare(self):
        return self.x**2+self.y**2
    def posModulus(self):
        return math.sqrt(self.posModulusSquare())
    def stop(self):
        self.oldvx=self.vx; self.oldvy=self.vy; self.vx=self.vy=0;
    def start(self):
        self.vx=self.oldvx; self.vy=self.oldvy
    def restart(self):
        self.x=self.init[0]; self.y=self.init[1]; self.radius=self.init[2]; self.vx=self.init[3]; self.vy=self.init[4]; self.color=self.init[5]
    def move(self):
        if (self.x!=0 or self.y!=0):
            a=-K/math.pow(self.posModulusSquare(), 3/2)
            ax=self.x*a; ay=self.y*a
            self.vx+=ax*INCT;  self.vy+=ay*INCT
            self.x+=self.vx*INCT;  self.y+=self.vy*INCT
            self.moveCounter+=1
            if (self.moveCounter % 2 == 1): 
                self.positions.append([self.x,self.y])
                if (self.MAX_POS==1000000):
                    if (abs(self.x-self.positions[0][0])<=2 and abs(self.y-self.positions[0][0])<=2):
                        self.MAX_POS=len(self.positions)
                        print(self.color,self.MAX_POS)
                if (len(self.positions)>self.MAX_POS):
                    self.positions.pop(0)
    def transform(self,point):
        return [WIDTH/2+point[0],HEIGHT/2-point[1]]
            
    def draw(self,canvas):
        tx=WIDTH/2+self.x; ty=HEIGHT/2-self.y
        canvas.draw_circle([tx,ty],self.radius,1,self.color,self.color)
        for i in range(0,len(self.positions)-1):
            canvas.draw_line(self.transform(self.positions[i]),self.transform(self.positions[i+1]),1,self.color)
    def __str__(self):
        return '['+str(self.x)+','+str(self.y)+','+str(self.radius)+','+str(self.vx)+','+str(self.vy)+','+self.color+']'
        
        
        
    
planets=[planet(0,0,15,0,0,"Yellow"),planet(150,0,10,-0.5,2.4,"Blue"),planet(300,0,8,-0.34,1.2,"Red"),planet(400,0,8,-0.13,1.3,"Pink")]

# helper function that spawns a ball by updating the 
# ball's position vector and velocity vector
# if right is True, the ball's velocity is upper right, else upper left

    
# define event handlers

def draw(c):
    for p in planets:
        p.move()
        p.draw(c)
        #print p
        
def pause():
    if (pause_activated):
        for p in planets:
            p.stop()
        pause.set_text("Go on")
    else:
        for p in planets:
            p.start()
        pause.set_text("Pause")
        
def restart():
    for p in planets:
        p.restart()
    
            

# create frame
frame = simplegui.create_frame("Planets", WIDTH, HEIGHT)
frame.set_draw_handler(draw)
pause=frame.add_button("Pause",pause,200)
restart=frame.add_button("Restart",restart,200)

# start frame
frame.start()

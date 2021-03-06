# program template for Spaceship
import simplegui, math, random
# globals for user interface
WIDTH = 800
HEIGHT = 600
SCREEN_DIMENSION=[WIDTH,HEIGHT]
score = 0
lives = 3
time = 0

class ImageInfo:
    def __init__(self, center, size, radius = 0, lifespan = None, animated = False):
        self.center = center
        self.size = size
        self.radius = radius
        if lifespan:
            self.lifespan = lifespan
        else:
            self.lifespan = float('inf')
        self.animated = animated

    def get_center(self):
        return self.center

    def get_size(self):
        return self.size

    def get_radius(self):
        return self.radius

    def get_lifespan(self):
        return self.lifespan

    def get_animated(self):
        return self.animated

    
# art assets created by Kim Lathrop, may be freely re-used in non-commercial projects, please credit Kim
    
# debris images - debris1_brown.png, debris2_brown.png, debris3_brown.png, debris4_brown.png
#                 debris1_blue.png, debris2_blue.png, debris3_blue.png, debris4_blue.png, debris_blend.png
debris_info = ImageInfo([320, 240], [640, 480])
debris_image = simplegui.load_image("http://commondatastorage.googleapis.com/codeskulptor-assets/lathrop/debris2_blue.png")

# nebula images - nebula_brown.png, nebula_blue.png
nebula_info = ImageInfo([400, 300], [800, 600])
nebula_image = simplegui.load_image("http://commondatastorage.googleapis.com/codeskulptor-assets/lathrop/nebula_blue.png")

# splash image
splash_info = ImageInfo([200, 150], [400, 300])
splash_image = simplegui.load_image("http://commondatastorage.googleapis.com/codeskulptor-assets/lathrop/splash.png")

# ship image
ship_info = ImageInfo([45, 45], [90, 90], 35)
ship_image = simplegui.load_image("http://commondatastorage.googleapis.com/codeskulptor-assets/lathrop/double_ship.png")

# missile image - shot1.png, shot2.png, shot3.png
missile_info = ImageInfo([5,5], [10, 10], 3, 50)
missile_image = simplegui.load_image("http://commondatastorage.googleapis.com/codeskulptor-assets/lathrop/shot2.png")

# asteroid images - asteroid_blue.png, asteroid_brown.png, asteroid_blend.png
asteroid_info = ImageInfo([45, 45], [90, 90], 40)
asteroid_image = simplegui.load_image("http://commondatastorage.googleapis.com/codeskulptor-assets/lathrop/asteroid_blue.png")

# animated explosion - explosion_orange.png, explosion_blue.png, explosion_blue2.png, explosion_alpha.png
explosion_info = ImageInfo([64, 64], [128, 128], 17, 24, True)
explosion_image = simplegui.load_image("http://commondatastorage.googleapis.com/codeskulptor-assets/lathrop/explosion_alpha.png")

# sound assets purchased from sounddogs.com, please do not redistribute
soundtrack = simplegui.load_sound("http://commondatastorage.googleapis.com/codeskulptor-assets/sounddogs/soundtrack.mp3")
missile_sound = simplegui.load_sound("http://commondatastorage.googleapis.com/codeskulptor-assets/sounddogs/missile.mp3")
missile_sound.set_volume(.5)
ship_thrust_sound = simplegui.load_sound("http://commondatastorage.googleapis.com/codeskulptor-assets/sounddogs/thrust.mp3")
explosion_sound = simplegui.load_sound("http://commondatastorage.googleapis.com/codeskulptor-assets/sounddogs/explosion.mp3")

# helper functions to handle transformations
def angle_to_vector(ang):
    return [math.cos(ang), math.sin(ang)]

def dist(p,q):
    return math.sqrt((p[0] - q[0]) ** 2+(p[1] - q[1]) ** 2)


# Ship class
class Ship:
    def __init__(self, pos, vel, angle, image, info):
        self.pos = [pos[0],pos[1]]
        self.vel = [vel[0],vel[1]]
        self.thrust = False
        self.angle = angle
        self.angle_vel = 0
        self.image = image
        self.image_center = info.get_center()
        self.image_size = info.get_size()
        self.radius = info.get_radius()

    def set_angle_vel(self,angle_vel):
        self.angle_vel=angle_vel
        
    def set_thrust(self,thrust):
        self.thrust=thrust
        
    def draw(self,canvas):
        if (self.thrust):
            #canvas.draw_image(self.image,[self.image_center[0]+2.5*self.radius,self.image_center[1]],self.image_size,self.pos,self.image_size,self.angle)
            canvas.draw_image(self.image,[self.image_center[0]+self.image_size[0],self.image_center[1]],self.image_size,self.pos,self.image_size,self.angle)
        else:
            canvas.draw_image(self.image,self.image_center,self.image_size,self.pos,self.image_size,self.angle)

    def update(self):
        a=0.2 # acceleration modulus
        b=0.02 # friction constant
        self.vel[0]*=(1-b) #model friction deceleration
        self.vel[1]*=(1-b)
        if (self.thrust):
            self.vel[0]+=a*math.cos(self.angle)  # model thrust acceleration
            self.vel[1]+=a*math.sin(self.angle)
            ship_thrust_sound.set_volume(0.5)
            ship_thrust_sound.play()
        else:
            ship_thrust_sound.pause()
            ship_thrust_sound.rewind()
        for i in range(len(SCREEN_DIMENSION)):
            self.pos[i]=(self.pos[i]+self.vel[i]) % SCREEN_DIMENSION[i]
            #self.pos[0]+=self.vel[0]
            #self.pos[1]+=self.vel[1]
        #self.angle_vel*=(1-b)
        self.angle+=self.angle_vel
        
    def shoot(self):
        global a_missile
        v=8
        tip=self.image_size[0]/2
        forward=angle_to_vector(self.angle)
        pos=[self.pos[0]+tip*forward[0],self.pos[1]+tip*forward[1]]
        vel=list(self.vel)
        vel[0]+=v*forward[0]; vel[1]+=v*forward[1]
        a_missile = Sprite(pos, vel, 0, 0, missile_image, missile_info, missile_sound)
    
    def get_position(self):
        return self.pos
    
    def get_radius(self):
        return self.radius
        
    
# Sprite class
class Sprite:
    def __init__(self, pos, vel, ang, ang_vel, image, info, sound = None):
        self.pos = [pos[0],pos[1]]
        self.vel = [vel[0],vel[1]]
        self.angle = ang
        self.angle_vel = ang_vel
        self.image = image
        self.image_center = info.get_center()
        self.image_size = info.get_size()
        self.radius = info.get_radius()
        self.lifespan = info.get_lifespan()
        self.animated = info.get_animated()
        self.age = 0
        if sound:
            sound.rewind()
            sound.play()
   
    def draw(self, canvas):
        #canvas.draw_circle(self.pos, self.radius, 1, "Red", "Red")
        canvas.draw_image(self.image,self.image_center,self.image_size,self.pos,self.image_size,self.angle)
    
    def update(self):
        self.angle+=self.angle_vel
        for i in range(len(SCREEN_DIMENSION)):
            self.pos[i]=(self.pos[i]+self.vel[i]) % SCREEN_DIMENSION[i]
    
    def get_position(self):
        return self.pos
    
    def get_radius(self):
        return self.radius
    
    def collide(self,other_object):
        return dist(self.pos,object.pos)<=self.radius+other_object.radius
    

def group_collide(other_object,group):
    remove=set([])
    for sprite in group:
        if (sprite.collide(other_object)):
            remove.add(sprite)
    group.difference_update(remove)
    return len(remove)>0

def group_group_collide(sprite_set1, sprite_set2):
    cont=0
    remove=set([])
    for sprite in sprite_set1:
        if group_collide(sprite,sprite_set2):
            remove.add(sprite)
            cont+=1
    sprite_set1.difference_update(remove)
    return cont        

           
def draw(canvas):
    global time
    
    # animiate background
    time += 1
    center = debris_info.get_center()
    size = debris_info.get_size()
    wtime = (time / 8) % center[0]
    canvas.draw_image(nebula_image, nebula_info.get_center(), nebula_info.get_size(), [WIDTH / 2, HEIGHT / 2], [WIDTH, HEIGHT])
    canvas.draw_image(debris_image, [center[0] - wtime, center[1]], [size[0] - 2 * wtime, size[1]], 
                                [WIDTH / 2 + 1.25 * wtime, HEIGHT / 2], [WIDTH - 2.5 * wtime, HEIGHT])
    canvas.draw_image(debris_image, [size[0] - wtime, center[1]], [2 * wtime, size[1]], 
                                [1.25 * wtime, HEIGHT / 2], [2.5 * wtime, HEIGHT])

    # draw ship and sprites
    my_ship.draw(canvas)
    a_rock.draw(canvas)
    a_missile.draw(canvas)
    
    # update ship and sprites
    my_ship.update()
    a_rock.update()
    a_missile.update()
    #show lives remaining and score
    BASEX=WIDTH/12; BASEY=HEIGHT/12
    canvas.draw_text("Lives",[BASEX,BASEY],25,"White")
    canvas.draw_text(str(lives),[BASEX,BASEY*1.5],25,"White")
    canvas.draw_text("Score",[BASEX*10,BASEY],25,"White")
    canvas.draw_text(str(score),[BASEX*10,BASEY*1.5],25,"White")
            
#key handlers
def keydown(key):
    if (key==simplegui.KEY_MAP['up']): my_ship.set_thrust(True)
    elif (key==simplegui.KEY_MAP['down']): pass
    elif (key==simplegui.KEY_MAP['left']): my_ship.set_angle_vel(-0.05)
    elif (key==simplegui.KEY_MAP['right']): my_ship.set_angle_vel(0.05)
    elif (key==simplegui.KEY_MAP['space']): my_ship.shoot()

def keyup(key):
    if (key==simplegui.KEY_MAP['up']): my_ship.set_thrust(False)
    elif (key==simplegui.KEY_MAP['down']): pass
    elif (key==simplegui.KEY_MAP['left']): my_ship.set_angle_vel(0)
    elif (key==simplegui.KEY_MAP['right']): my_ship.set_angle_vel(0)
             
# timer handler that spawns a rock    
def rock_spawner():
    global a_rock
    pos=[]; vel=[]
    pos.append(random.randrange(0,WIDTH)); pos.append(random.randrange(0,HEIGHT))
    vel.append(random.randrange(-300,300)/100.0); vel.append(random.randrange(-100,100)/100.0)
    ang_vel=random.randrange(-20,20)/100.0
    a_rock=Sprite(pos,vel,0,ang_vel,asteroid_image,asteroid_info)
    
# initialize frame
frame = simplegui.create_frame("Asteroids", WIDTH, HEIGHT)

# initialize ship and two sprites
my_ship = Ship([WIDTH / 2, HEIGHT / 2], [0, 0], 0, ship_image, ship_info)
a_rock = Sprite([WIDTH / 3, HEIGHT / 3], [1, 1], 0, 0.05, asteroid_image, asteroid_info)
a_missile = Sprite([2 * WIDTH / 3, 2 * HEIGHT / 3], [-1,1], 0, 0, missile_image, missile_info, missile_sound)

# register handlers
frame.set_draw_handler(draw)
frame.set_keydown_handler(keydown)
frame.set_keyup_handler(keyup)
timer = simplegui.create_timer(1000.0, rock_spawner)

# get things rolling
timer.start()
frame.start()

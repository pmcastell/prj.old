import simplegui,math

WIDTH=800
HEIGHT=800
class ImageInfo:
    def __init__(self,center,size,radius=0,lifespan=None,animated=False):
        self.center=center
        self.size=size
        self.radius=radius
        if (lifespan):
            self.lifespan=lifespan
        else:
            self.lifespan=float('inf')
        self.animated=animated
        
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
    
class ship:
    def __init__(self,pos,vel,angle,image,info):
        self.pos=[pos[0],pos[1]]
        self.vel=[vel[0],vel[1]]
        self.angle=angle
        self.angle_vel=0
        self.thrust=False
        self.image=image
        self.info=info
        self.image_center=info.get_center()
        self.image_size=info.get_image_size()
        self.radius=info.get_radius()
        
    def update(self):
        a=1 # acceleration modulus
        b=0.1 # friction constant
        self.vel[0]*=(1-b) #model friction deceleration
        self.vel[1]*=(1-b)
        if (self.thrust):
            self.vel[0]+=a*math.cos(self.angle)  # model thrust acceleration
            self.vel[1]+=a*math.sin(self.angle)
        self.pos[0]+=self.vel[0]
        self.pos[1]+=self.vel[1]
        self.angle_vel*=(1-b)
        self.angle+=self.angle_vel
        
        
        
        
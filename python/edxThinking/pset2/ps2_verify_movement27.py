# 2014.11.11 23:56:19 CET

def testRobotMovement(robot_type, room_type, delay = 0.4,width=5,height=5):
   """
   Runs a simulation of a single robot of type robot_type in a 5x5 room.
   """
   import ps2_visualize
   room = room_type(width, height)
   robot = robot_type(room, 1)
   anim = ps2_visualize.RobotVisualization(1, width, height, delay)
   while room.getNumCleanedTiles() < room.getNumTiles():
      robot.updatePositionAndClean()
      anim.update(room, [robot])

   anim.done()




# DC_CNC
Arduino program for control cnc with dc motors and rotation encoders


#Comunication protocol
	Speed: 9600 bot
	Package: 
		[[type] [x] [y] [z] [check_sum]] 
			int type - type of package [0 - absolute position, 1 - relative position]
			int x,y,z - number of steps for each axies.
			int check_sum - ((type^(x << 1))^(y << 2))^z << 3
		
		Example:
			"0 10 30 40 41"
		

#Version 0.0.1
	Packages queue 					 - implemented
	Sensor controll via interruption - implemented (X only)
	
	

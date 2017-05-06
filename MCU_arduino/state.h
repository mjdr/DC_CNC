#ifndef state_h
#define state_h

struct s {
  int x,y,z;
  int dir_x,dir_y;
  int dst_x,dst_y;
  bool en_x,en_y;
  bool done;
};

extern struct s state;

void state_init();

#endif

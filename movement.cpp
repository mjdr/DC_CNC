#include "cnc.h"

struct s state;


void movement_init(){
  attachInterrupt(digitalPinToInterrupt(2),updateX,RISING);
}

void disableX(){
  state.en_x = 0;
}

void updateX(){
  #ifdef DEBUG
  Serial.println(state.x);
  #endif
  
  if(state.x != state.dst_x){
    state.x += state.dir_x;
  }
  else{
    disableX();
    if(state.y == state.dst_y)
      state.done = 1;
  }
}


void setupMoveX(int n){
  if(n == 0) return;
  state.dst_x = state.x + n;
  state.dir_x = abs(n)/n;
  state.done = 0;
  
}


void move(){
  if(state.dir_x == 1){}
  else if(state.dir_x == -1){}
}



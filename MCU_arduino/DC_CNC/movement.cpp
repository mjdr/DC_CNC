#include "cnc.h"

struct s state;


void movement_init(){
  attachInterrupt(2,updateX,RISING);
  attachInterrupt(3,updateY,RISING);
}

void disableX(){
  state.en_x = false;
  #ifdef DEBUG
  Serial.print("Disable: X");
  #endif
}

void updateX(){
  #ifdef DEBUG
  Serial.print("X: ");
  Serial.println(state.x);
  #endif
  
  if(state.x == state.dst_x){
    disableX();
    if(state.y == state.dst_y)
      state.done = true;
  }
  else if(state.en_x){
    state.x += state.dir_x;
  }
}
void setupMoveXBy(int n){
  if(n == 0) return;
  state.dst_x = state.x + n;
  state.dir_x = abs(n)/n;
  state.en_x = true;
  state.done = false;
  
}



void disableY(){
  state.en_y = false;
  #ifdef DEBUG
  Serial.print("Disable: Y");
  #endif
}

void updateY(){
  #ifdef DEBUG
  Serial.print("Y: ");
  Serial.println(state.y);
  #endif

  if(state.y == state.dst_y){
    disableY();
    if(state.x == state.dst_x)
      state.done = true;
  }
  else if(state.en_y){
    state.y += state.dir_y;
  }
  
}


void setupMoveYBy(int n){
  if(n == 0) return;
  state.dst_y = state.y + n;
  state.dir_y = abs(n)/n;
  state.en_y = true;
  state.done = false;
  
}


void move(){
  if(state.dir_x == 1){}
  else if(state.dir_x == -1){}
}


void moveZ(bool st){
   #ifdef DEBUG
   if(st)
    Serial.println("Servo up");
   else
    Serial.println("Servo down");
  #endif
}



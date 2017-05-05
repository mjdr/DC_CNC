
#include "cnc.h"

#define TYPE_ABSOLUTE 0
#define TYPE_RELATIVE 1


void executePackage(package* p){

  #ifdef DEBUG
  Serial.print("Execute: ");
  printPackage(p);
  #endif

  if(p->type == TYPE_ABSOLUTE){
    setupMoveXBy(p->x - state.x);
    setupMoveYBy(p->y - state.y);
    move();
  }
  else if(p->type == TYPE_RELATIVE){
    setupMoveXBy(p->x);
    setupMoveYBy(p->y);
    move();
  }
  else{ 
    Serial.println("Package type not defined!");
  }
  
}

void printPackage(package* p) {
  Serial.print("{type: ");
  Serial.print(p->type);
  Serial.print(", x: ");
  Serial.print(p->x);
  Serial.print(", y: ");
  Serial.print(p->y);
  Serial.print(", z: ");
  Serial.print(p->z);
  Serial.print(", sum: ");
  Serial.print(p->check_sum);
  Serial.println("};");
}

bool checkPackage(package* p){
  return check_sum(p) == p->check_sum;
}

int check_sum(package* p){
  int tmp = p->type;
  tmp ^= p->x << 1;
  tmp ^= p->y << 2;
  tmp ^= p->z << 3;
  return tmp;
}














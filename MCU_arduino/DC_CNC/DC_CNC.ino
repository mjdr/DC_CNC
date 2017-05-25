#include "cnc.h"

void setup() {

  Serial.begin(9600);
  
  printData();
  state_init();
  queue_init();
  movement_init();
  

  #ifdef TESTS
  doTests();
  #endif
}

void loop() {
  if(state.done)
    exec();
  if(!isQueueFull())
    loadPackage();
}


void printData(){
  Serial.println("Version: 0.0.1");
}

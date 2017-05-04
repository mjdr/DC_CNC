
#include "cnc.h"
//#define TESTS
//#define DEBUG


#ifdef TESTS
  void doTests();
#endif

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

//-----------------------------TESTS-------------------------------------------------//

#ifdef TESTS
  void doTests(){
    Serial.println("Tests");
    testQueue();
    Serial.println("End tests");
    
  }
  void testQueue(){
    package* p1 = (package*)malloc(sizeof(package));
    package* p2 = (package*)malloc(sizeof(package));
  
    p1->type = 1;
    p1->x = 2;
    p1->y = 3;
    p1->z = 4;
    
    p2->type = 5;
    p2->x = 6;
    p2->y = 7;
    p2->z = 8;
  
    addPackage(p1);
    addPackage(p2);
  
    printPackage(popPackage());
    printPackage(popPackage());

    free(p1);
    free(p2);
  }

#endif

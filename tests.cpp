
#include "cnc.h"
#ifdef TESTS

void testQueue();

void doTests(){
  Serial.println("Tests");
  testQueue();
  Serial.println("End tests");
  
}


void testQueue(){

  Serial.print("Queue test: ");
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

  
  package* p3 = popPackage();
  package* p4 = popPackage();

  if(p1 == p3 && p2 == p4 && currentID == -1)
    Serial.println("OK");
  else
    Serial.println("FALSE");

  free(p1);
  free(p2);
}
#endif

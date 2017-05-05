#include "cnc.h"


package* queue[MAX_QUEUE_LENGTH];
int currentID;


int check_sum(package*);

void queue_init(){
 currentID = -1;
}

void addPackage(package* p){
  queue[++currentID] = p;
}

bool isQueueFull(){
  return currentID >= MAX_QUEUE_LENGTH - 1;
}
bool isQueueEmpty(){
  return currentID == -1;
}


package* popPackage(){
  package* res = queue[0];
  
  for(int i = 1;i <= currentID;i++)
    queue[i - 1] = queue[i];

  currentID--;
  return res;
}


String getValue(String data, char separator, int index)
{
    int found = 0;
    int strIndex[] = { 0, -1 };
    int maxIndex = data.length() - 1;

    for (int i = 0; i <= maxIndex && found <= index; i++) {
        if (data.charAt(i) == separator || i == maxIndex) {
            found++;
            strIndex[0] = strIndex[1] + 1;
            strIndex[1] = (i == maxIndex) ? i+1 : i;
        }
    }
    return found > index ? data.substring(strIndex[0], strIndex[1]) : "";
}

void loadPackage(){
  if(Serial.available() == 0) return;
 
 String str = Serial.readString(); 
 #ifdef DEBUG
 Serial.println("Recive package:");
 Serial.println(str);
 #endif

 package* p = (package*) malloc(sizeof(package));

 p->type = getValue(str,' ',0).toInt();
 p->x = getValue(str,' ',1).toInt();
 p->y = getValue(str,' ',2).toInt();
 p->z = getValue(str,' ',3).toInt();
 p->check_sum = getValue(str,' ',4).toInt();

 #ifdef DEBUG
 printPackage(p);
 Serial.println(check_sum(p));
 Serial.println(checkPackage(p));
 Serial.println();
 #endif
 
 addPackage(p);
}

void exec(){
  if(!isQueueEmpty()){
    package* p = popPackage();
    executePackage(p);
    free(p);
  }
}



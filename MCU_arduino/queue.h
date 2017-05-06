#ifndef queue_h
#define queue_h

#define MAX_QUEUE_LENGTH 10

extern package* queue[MAX_QUEUE_LENGTH];
extern int currentID;

void queue_init();
void addPackage(package*);
bool isQueueFull();
bool isQueueEmpty();
package* popPackage();
void loadPackage();
void exec();



#endif

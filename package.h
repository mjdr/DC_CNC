#ifndef package_h
#define package_h
typedef struct {
  int type;
  int x;
  int y;
  int z;
  int check_sum;
}
package;

void executeCommand(package*);
void printPackage(package*);
int checkPackage(package*);
int check_sum(package*);

#endif

#include "cnc.h"


void state_init(){
  state.done = true;
  state.x = state.y = state.z = 0;
  state.dst_x = state.dst_y = 0;
  state.dir_x = state.dir_y = 1;
  state.en_x = state.en_y = false;
}

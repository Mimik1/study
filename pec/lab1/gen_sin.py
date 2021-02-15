from __future__ import division
from __future__ import print_function

# used standard libraries
import time

from usbtmcDevice import UsbtmcDevice

# connect to instruments
dev_gen = './screen_2.dat'

fd_gen = UsbtmcDevice(dev_gen)
id = fd_gen.ask('*idn?')
print(id)

fd_gen.write('*rst')
time.sleep(1.0)

freq=1000
vpp=10
offset = 0


#fd_gen.write('output off')
#fd_gen.write('func sin')
#fd_gen.write('freq %f' % freq)
#fd_gen.write('volt %f' % vpp)
#fd_gen.write('volt:offs %f' % offset)
#fd_gen.write('output on')

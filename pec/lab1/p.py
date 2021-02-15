from __future__ import division
from __future__ import print_function

# used standard libraries
import time
import os
import pylab

from usbtmcDevice import UsbtmcDevice

d_path = 'figs/'
# connect to instruments
#dev_scope = UsbtmcDevice('/dev/usbtmc0')
dev_scope = UsbtmcDevice('screen_2.dat')
id = dev_scope.ask('*idn?')
print(id)

print('Resetting devices..')
dev_scope.write('*rst')
time.sleep(2)

print('Setting up devices..')
#dev_scope.write(':aut')
dev_scope.write('chan1:disp on')
dev_scope.write('chan1:probe 10')
dev_scope.write('chan1:coupling AC')
dev_scope.write('chan1:coupling DC')
dev_scope.write('chan1:scale 0.5')
dev_scope.write('chan1:offset 1')
dev_scope.write('timebase:scale 1e-4')
time.sleep(5)


def screen_cap(fname):
	print('screen_cap')
	dev_scope.write(':display:data? on,off,png')
	time.sleep(2)
	img_data = dev_scope.read()
	print('img_hdr: ' + img_data[0:11])
	fd = os.open(d_path + fname + '.png', os.O_CREAT | os.O_WRONLY)
	os.write(fd, img_data[11:])
	os.close(fd)

def data2array(data):
    assert data[0] == '#'
    samplenum = int(data[2:int(data[1])+2])
    return bytearray(data[int(data[1])+2:int(data[1])+2+samplenum])

def data_cap(fname):
	samples = data2array(dev_scope.ask('wav:data?'))
	print('samples: ', len(samples))
	fd = os.open(d_path + fname + '.raw', os.O_CREAT | os.O_WRONLY)
	os.write(fd, samples)
	os.close(fd)
	# convert sample bytes to voltages
	yref = float(dev_scope.ask('wav:yref?'))
	yinc = float(dev_scope.ask('wav:yinc?'))
	yori = float(dev_scope.ask('wav:yorigin?'))
	print('[yref, yinc, yori] =', [yref, yinc, yori])
	samples = [ ((s-yref)-yori)*yinc for s in samples ]
	return samples

def data_plot(samples, wf):
	pylab.show(block = False)
	pylab.plot(samples[200:1200])
	pylab.draw()
	pylab.savefig(d_path + wf.lower() + '.png')


def test_auto(fname):
	dev_scope.write(':aut')
	time.sleep(10)
	
	samples = data_cap('s_'+fname)
	data_plot(samples, 'plot_'+fname)
	screen_cap('screen_'+fname)

fname = 'auto'
test_auto('auto')

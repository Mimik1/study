import matplotlib
matplotlib.use('Agg')

import matplotlib.pyplot as plt
from matplotlib.ticker import FuncFormatter
from usbtmc import UsbtmcDevice
import sys


PREAMBULE_BYTES = 11
DEVICE_PATH = sys.argv[1]
PARAMS = sys.stdin.read().splitlines()
RAW_DATA_PATH = 'data.raw'
COMMAND = 'wav:data?'


if __name__ == '__main__':
	print('PARAMS: {0}'.format(PARAMS))

	print('Obtaining data from device...')
	with open(RAW_DATA_PATH, 'wb') as file:
		dev = UsbtmcDevice(DEVICE_PATH)
		file.write(dev.ask(COMMAND))

	print('Processing data...')
	with open(RAW_DATA_PATH, 'rb') as file:
		file.seek(PREAMBULE_BYTES) 
		data = bytearray(file.read())

		samples = [(x - float(PARAMS[0]) - float(PARAMS[1])) * float(PARAMS[2]) for x in data]

		
		plt.plot(samples)
		axes = plt.gca()
		axes.get_xaxis().set_major_formatter(
			FuncFormatter(lambda x, p: format(float(x * float(PARAMS[3]) / 100), ','))
		)
		axes.set_ylim([min(samples) - 50, max(samples) + 50])
		plt.savefig('{0}.png'.format(RAW_DATA_PATH))
		print('Plot saved to {0}.png'.format(RAW_DATA_PATH))
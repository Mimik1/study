from usbtmc import UsbtmcDevice
import sys
import time

DEVICE_PATH = sys.argv[1]

COMMANDS = [
	':WAVeform:YREFerence?',
	':WAVeform:YINCrement?',
	':WAVeform:YORigin?',
	':TIMebase:SCALe? '
]


if __name__ == '__main__':
    dev = UsbtmcDevice(DEVICE_PATH)

    for command in COMMANDS:

    	if command.find('?') >= 0:
    		sys.stdout.write(dev.ask(command).strip() + '\n')
    	else: dev.write(command)

    	time.sleep(0.5)
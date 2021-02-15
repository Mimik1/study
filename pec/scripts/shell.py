from usbtmc import UsbtmcDevice
import sys

DEVICE_PATH = sys.argv[1]



if __name__ == '__main__':
    dev = UsbtmcDevice(DEVICE_PATH)

    while True:
        cmd = raw_input('scpi> ')
        if cmd.find('?') >= 0:
            answer = dev.ask(cmd).strip()
            print(answer)
        else:
            dev.write(cmd)
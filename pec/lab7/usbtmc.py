import os
import time

class UsbtmcDevice:

    def __init__(self, device='/dev/usbtmc1'):
        self.f = os.open(device, os.O_RDWR)

    def __del__(self):
        os.close(self.f)

    def write(self, cmd):
        os.write(self.f, cmd)
        time.sleep(0.01)

    def read(self):
        return os.read(self.f, 4096)

    def ask(self, cmd):
        self.write(cmd)
        return self.read()


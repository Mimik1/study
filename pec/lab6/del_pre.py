import sys

PREAMBULE_BYTES = 11
SRC_FILE = sys.argv[1]
img = None

print('Reading and processing {0}'.format(SRC_FILE))
with open(SRC_FILE, 'rb') as f:
	f.seek(PREAMBULE_BYTES) 
	img = f.read() 


print('Saving {0}'.format(SRC_FILE))
with open("./newdata.raw", 'wb') as f:
	f.write(img)


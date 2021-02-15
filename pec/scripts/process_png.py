import sys

PREAMBULE_BYTES = 11
SRC_FILE = sys.argv[1]
actual_image = None

print('Reading and processing {0}'.format(SRC_FILE))
with open(SRC_FILE, 'rb') as f:
	f.seek(PREAMBULE_BYTES) 
	actual_image = f.read() 


print('Saving {0}'.format(SRC_FILE))
with open(SRC_FILE, 'wb') as f:
	f.write(actual_image)


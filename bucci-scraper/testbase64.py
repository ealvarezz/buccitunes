import requests
import base64
from pylyrics_fixed import *

image = requests.get("http://www3.cs.stonybrook.edu/~pfodor/old_page/index_files/image003.jpg").content

f = open("./test1.b64", "w+")
f.write(image.encode("base64"))
f.close()

f = open("./test2.b64", "w+")
f.write(base64.b64encode(image))
f.close()

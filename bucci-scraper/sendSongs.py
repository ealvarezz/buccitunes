from base64 import b64encode
import requests
import os
import json
import sys
import binascii
 
s = requests.Session()
the_chosen_one = "1146@fake.com"
 
 
PATH = "./bucci/"
ALBUM_ENDPOINT = "http://localhost:8080/request_album"
LOGIN_ENDPOINT = "http://localhost:8080/login"
 
albumPath = PATH+"dummy_album/"
albumFile = os.listdir(albumPath)[0]
album = json.loads(open(albumPath + albumFile, 'r').read())
 
if len(sys.argv) != 2:
    print('Encode a file with Base64\nUsage:\n%s filename' % sys.argv[0])
    sys.exit()
 
def get_decoded_file():
    fname = sys.argv[1]
    with open(fname, 'rb') as f:
        data = f.read()
        return data

def send_request():
 
    login = {}
    login['email'] = the_chosen_one
    login['password'] = 'tmp'
    print(login)
    r = s.post(LOGIN_ENDPOINT, json=login)
    res = r.json()
    print(res)
 
    bytes64 = b64encode(get_decoded_file())
    base64_string = bytes64.decode('utf-8')
    album['songs'][0]['audio'] = base64_string
    r = s.post(ALBUM_ENDPOINT, json=album)
    res = r.json()
    print(res)
 
send_request()


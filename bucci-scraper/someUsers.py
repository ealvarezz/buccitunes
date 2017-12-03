import requests
import os
import json
from random import *


PATH = "./bucci/"
SIGNUP_ENDPOINT = "http://localhost:8080/signup"

userPath = PATH+"users/"
usersFile = os.listdir(userPath)[0]
print(PATH+usersFile)
for i in range(1,12):
    users = json.loads(open(userPath + usersFile, 'r').read())
    for user in users:
        signUp = user
        emailSplit = signUp['userInfo']['email'].split('@');
        email = emailSplit[0] + str(i) + '@' + emailSplit[1]
        print(email)
        signUp['userInfo']['email'] = email
        r = requests.post(SIGNUP_ENDPOINT, json=user)
        print(r)
import requests
import os
import json
from random import *


PATH = "./bucci/"
SIGNUP_ENDPOINT = "http://localhost:8080/signup"
ADMIN_SIGNUP_ENDPOINT = "http://localhost:8080/add_admin"

userPath = PATH+"users/"
usersFile = os.listdir(userPath)[0]
print(PATH+usersFile)
for i in range(0,12):
    users = json.loads(open(userPath + usersFile, 'r').read())
    for user in users:
        signUp = user
        if 'userInfo' in signUp:
            if(i!=0):
                emailSplit = signUp['userInfo']['email'].split('@');
                email = emailSplit[0] + str(i) + '@' + emailSplit[1]
                signUp['userInfo']['email'] = email
            r = requests.post(SIGNUP_ENDPOINT, json=signUp)
            print(signUp['userInfo']['email'])
        else: 
            if(i!=0):
                emailSplit = signUp['email'].split('@')
                email = emailSplit[0] + str(i) + '@' + emailSplit[1]
                signUp['email'] = email
            r = requests.post(ADMIN_SIGNUP_ENDPOINT, json=signUp)
            print(signUp['email'])

import os
import sys

fname = "./bucci/songs/"

for i in os.listdir(fname):
    if(i[0] != '.'):
        print(fname + i)

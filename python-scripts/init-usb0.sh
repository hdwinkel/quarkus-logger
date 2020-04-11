#! /bin/sh

stty -F /dev/ttyUSB0 9600 -parodd cs7 -cstopb parenb -ixoff -crtscts -hupcl -ixon -opost -onlcr -isig -icanon -iexten -echo -echoe -echoctl -echoke



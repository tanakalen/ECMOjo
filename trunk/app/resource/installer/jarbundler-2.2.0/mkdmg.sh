#!/bin/sh

SRC="$1/"
DEST="$2/"
VOLUME="$3"
SIZE="$4"

echo Source $1
echo Destination $2
echo Volume $3
echo Size $4 MB

TEMP="TEMPORARY"

hdiutil create -megabytes $SIZE $DEST$TEMP.dmg -layout NONE
MY_DISK=`hdid -nomount $DEST$TEMP.dmg`
newfs_hfs -v $VOLUME $MY_DISK
hdiutil eject $MY_DISK
hdid $DEST$TEMP.dmg
chflags -R nouchg,noschg "$SRC"
ditto -v "$SRC" "/Volumes/$VOLUME"
#ditto -rsrcFork -v "./background/" "/Volumes/$VOLUME"
hdiutil eject $MY_DISK
hdiutil convert -format UDCO $DEST$TEMP.dmg -o $DEST$VOLUME.dmg
hdiutil internet-enable -yes $DEST$VOLUME.dmg
rm $DEST$TEMP.dmg

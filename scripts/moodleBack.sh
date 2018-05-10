#!/bin/bash

DIR_DEST="/m/Mios/Instituto/mysqlMoodle"
PASS=$(/scripts/getMyPass.sh encfs)
sudo nice -19 7za u -w${DIR_DEST} -mhe -p${PASS} -ms=off -mx=9 -mmt=8 -u- -uq0!${DIR_DEST}/mysqlMoodleTmp.7z ${DIR_DEST}/mysqlMoodle.7z /var/lib/mysql /usr/moodledata
sudo rm ${DIR_DEST}/mysqlMoodle.7z
sudo mv ${DIR_DEST}/mysqlMoodleTmp.7z ${DIR_DEST}/mysqlMoodle.7z
sudo chown usuario:usuario ${DIR_DEST}/mysqlMoodle.7z

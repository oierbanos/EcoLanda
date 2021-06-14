  GNU nano 3.2                                     segurtasun_kopia.sh                                                  
#!/bin/bash
date=$(date +"%d-%b-%Y")

umask 177

mysqldump --user=replication_user --password=bigs3cret EcoLanda > EcoLanda-$date.sql
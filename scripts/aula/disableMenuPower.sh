sudo sed -i 's/^AllowLogoutActions=HALT;REBOOT;HIBERNATE;SUSPEND;CUSTOM_CMD$/AllowLogoutActions=CUSTOM_CMD/' /etc/gdm/gdm.conf
sudo sed -i 's/^SystemCommandsInMenu=HALT;REBOOT;HIBERNATE;SUSPEND;CUSTOM_CMD$/SystemCommandsInMenu=CUSTOM_CMD/' /etc/gdm/gdm.conf
sudo echo '<?xml version="1.0"?>
<gconf>
        <dir name="apps">
                <dir name="gnome-power-manager">
                        <dir name="general">
                                <entry name="can_hibernate" mtime="1222181472" type="bool" value="false">
                                </entry>
                                <entry name="can_suspend" mtime="1222181436" type="bool" value="false">
                                </entry>
                                <entry name="can_halt" mtime="1222181436" type="bool" value="false">
                                </entry>
                                <entry name="can_reboot" mtime="1222181436" type="bool" value="false">
                                </entry>
                        </dir>
                </dir>
        </dir>
</gconf>' >> /etc/gconf/gconf.xml.mandatory/%gconf-tree.xml

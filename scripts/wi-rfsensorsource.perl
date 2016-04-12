#!/usr/pkg/bin/perl -w
use threads;
use threads::shared;
use Env;
use Curses;
$debug = 1;
open(CONF, "probe.conf") || die "Can't open probe.conf: $!";
open(STDERR, ">errors");

while (<CONF>) {
	next if /^#/;
	chomp;
	($name, $value) = split(/ *= */, $_, 2);
	$conf{$name} = $value;
}
close CONF;

if (!defined $conf{'if'}) {
	die "Error: 'if' must be set to an 802.11 interface.";
}
# this would be useful, but is bad for testing.
#system("ifconfig $conf{'if'} up");

my $selected_net:shared = 0;
my $selected_netname:shared = "";
my $error:shared = "";
my $offset:shared = 0;
my $max_offset:shared = 20;
my @nets:shared = ();
my %network:shared = ();
my $done:shared = 0;
my $status:shared = "";
my $key_pressed:shared = "";

initscr();
keypad(1);
if (defined $conf{'ssid'}) {
	&get_status($conf{'ssid'});
} else {
	$status = "About to probe network interfaces...";
}

my $display_thread = threads->new(\&display_loop);
my $scan_thread = threads->new(\&scan_loop);
input_loop();
$scan_thread->join();
$display_thread->join();
move($LINES, 0);
refresh();
noraw();
echo();
endwin();
exit(0);

sub
display_loop {
	while (!$done) {
		display();
		sleep(1);
	}
}

sub
scan_loop {
	while (!$done) {
		if ($status =~ /^About/) {
			lock($status);
			$status = "Probing network interfaces...";
		} elsif ($status =~ /^Found/ || $status =~ /^Probing/) {
			&scan_if($conf{'if'});
		} elsif ($status =~ /^Checking/) {
			&update_check();
		} elsif ($status =~ /^Showing/) {
			&update_status();
		}
		sleep 1;
	}
}

sub
input_loop {
	raw();
	noecho();
	while (!$done) {
		print STDERR "input_loop ($status)\n";
		$c = getch();
		if ($status =~ /^No public/) {
			$c = '' if $c ne 'q';
		}
		lock($key_pressed);
		$key_pressed = $c;
		if ($c =~ /[0-9]/) {
			if ($c <= $#nets) {
				my $netname = $nets[$c]->{'netname (SSID)'};
				lock($selected_net);
				$selected_net = $nets[$c];
				$error = "";
				&get_status($netname);

			} else {
				$error = "$c is out of range.";
			}
		} elsif ($c eq 'c') {
			&update_check($selected_netname);
		} elsif ($c eq 's') {
			&update_status($selected_netname);
		} elsif ($c eq KEY_UP) {
			lock($offset);
			if ($offset > 0) {
				--$offset;
				$error = "";
			} else {
				$error = "Can't scroll up any further..";
			}
		} elsif ($c eq KEY_DOWN) {
			lock($offset);
			if ($offset < $max_offset) {
				++$offset;
				$error = "";
			} else {
				$error = "Can't scroll down any further..";
			}
		} elsif ($c eq '') {
			lock($status);
			$status = "Probing network interfaces...";
		} elsif ($c eq 'q') {
			lock($done);
			$done = 1;
		}
	}
}

sub
display {
	clear();
	move(0, 0);
	printw("Status: $status" . (" " x (70 - length $status)));
	move($LINES - 3, 0);
	if (length $error) {
		printw("Error: $error" . (" " x (70 - length $status)));
	} else {
		printw(" " x 79);
	}
	move($LINES - 2, 0);
	printw("'q' to quit, 'ESC' for top-level menu.           (offset %d) %s", $offset, $key_pressed);
	move(2, 0);
	my ($q, $s, $n) = (0, 0, 0);
	if ($status =~ /^Probing/) {
		printw("...Still probing.");
	} elsif ($status =~ /^Checking/) {
		($q, $s, $n) = split(' ', $network{'Comms quality/signal/noise'});
		$bssid = $network{'Current BSSID'};

		printw("Signal quality:  %d%% (%d dBm signal, %d dBm noise)\n",
			$q, $s, $n);
		if (defined $conf{"quality-$selected_netname"}) {
			printw("Desired quality: %d%%\n", $conf{"quality-$selected_netname"});
			if ($conf{"quality-$selected_netname"} > $q) {
				printw("WARNING:  Quality too low!\n");
			}
		}
		printw("BSSID:  %s\n", $bssid);
		if (defined $conf{"bssid-$selected_netname"}) {
			printw("Desired BSSID:  %s\n", $conf{"bssid-$selected_netname"});
			if ($conf{"bssid-$selected_netname"} ne $bssid) {
				printw("WARNING:  BSSID differs from desired BSSID!\n");
			}
		}
	} elsif ($status =~ /^Found/) {
		my $count = 0;
		foreach $net (@nets) {
			my $netname = $net->{'netname (SSID)'};
			($q, $s, $n) = split(/ \/ /, $net->{'Quality/Signal/Noise [signal]'});
			my $capinfo = $net->{'Capinfo'};
			if ($capinfo =~ /WEP/) {
				$wep = "enabled";
			} else {
				$wep = "disabled";
			}
			printw("%d: '%s'%s (signal %d%%, channel %d), WEP %s\n",
				$count,
				$netname, " " x (32 - length $netname),
				$s,
				$net->{'Channel'},
				$wep);
			++$count;
		}
		printw("\n\nEnter a number to look at that network.");
	} elsif ($status =~ /^No public/) {
		printw("(hit any key to continue.)");
	} elsif ($status =~ /^Showing/) {
		$count = 0;
		foreach $key (sort keys %network) {
			++$count;
			if ($count > $offset) {
				printw("$key: $network{$key}\n");
			}
			last if (($count - $offset) > $LINES - 8);
		}
		if ($count == scalar keys %network) {
			$max_offset = $offset;
		}
	} else {
		printw("Nothing to display for this status.");
	}
	refresh();
}

sub
scan_if {
	my $if = shift;
	open(WICONFIG, "/usr/sbin/wiconfig $if -D |");
	my @new_nets = ();
	while (<WICONFIG>) {
		chomp;
	found_ap:
		if (/^ap/) {
			local $n = $_;
			my %x:shared = ();
			$n =~ s/.*\[//;
			$n =~ s/\].*//;
			print STDERR "Access point $n:\n";
			while (<WICONFIG>) {
				chomp;
				s/^[ 	]*//;
				goto found_ap if /^ap/;
				($name, $value) = split(/:[ 	]+/, $_, 2);
				$value =~ s/^\[ *//;
				$value =~ s/ *\]$//;
				$x{$name} = $value;
				print STDERR "$n: $name -> '$value'\n" if $debug > 1;
			}
			$new_nets[$n] = \%x;
		}
	}
	close WICONFIG;
	if ($status =~ /^Found/ || $status =~ /^Probing/) {
		lock(@nets);
		@nets = @new_nets;
		my $found_nets = scalar @nets;
		lock($status);
		if ($found_nets) {
			$status = "Found $found_nets network" .
				($found_nets == 1 ? "." : "s.");
		} else {
			$status = "No public networks in range.";
		}
	}
}

sub
get_status {
	my $netname = shift;
	if (!defined $netname || length $netname < 1) {
		$error = "No valid network specified.";
		return;
	}
	lock($selected_netname);
	$selected_netname = $netname;
	attach($conf{'if'}, $netname);
	&update_status();
}

sub
update_status {
	&update_wiconfig();
	lock($status);
	$status = "Showing network $selected_netname";
}

sub
update_wiconfig {
	my %new_network = ();
	open(WICONFIG, "/usr/sbin/wiconfig $conf{'if'} |");
	while (<WICONFIG>) {
		chomp;
		if (/:[ 	]*\[/) {
			($name, $value) = split(/:[ 	]+/, $_, 2);
			$value =~ s/^\[ *//;
			$value =~ s/ *\]$//;
			if ($name eq "Encryption keys") {
				$value = '"' . join('","', split(/ ?\]\[ ?/, $value, 4)) . '"';
			}
			$new_network{$name} = $value;
			print STDERR "get_status: $name -> '$value'\n" if $debug > 1;
		}
	}
	close WICONFIG;
	lock(%network);
	%network = %new_network;
}

sub
check {
	my $netname = shift;
	if (!defined $netname || length $netname < 1) {
		$error = "No valid network specified.";
		return;
	}
	lock($selected_netname);
	$selected_netname = $netname;
	attach($conf{'if'}, $netname);
	&update_check();
}

sub
update_check {
	&update_wiconfig();
	lock($status);
	$status = "Checking network $selected_netname";
}

sub
attach {
	my $interface = shift;
	my $netname = shift;
	if ($conf{"nwkey-$netname"}) {
		$nwkey = "nwkey " . $conf{"nwkey-$netname"};
	} else {
		$nwkey = "-nwkey";
	}
	system("ifconfig $interface nwid $netname $nwkey");
}

"""
 Changes and Modifications Aug 30/2008:
 Copyright (c) 2008 Boris Sitsker, <boris@sitsker.com>
 
 Original Script (YouTube2a):
 Copyright (c) 2007 Daniel Svensson, <dsvensson@gmail.com>

 Permission is hereby granted, free of charge, to any person
 obtaining a copy of this software and associated documentation
 files (the "Software"), to deal in the Software without
 restriction, including without limitation the rights to use,
 copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the
 Software is furnished to do so, subject to the following
 conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 OTHER DEALINGS IN THE SOFTWARE.
"""

import re
import urllib2
import cookielib
import os.path
import time
from xml.sax.saxutils import escape
import elementtree.ElementTree
import xbmcutils.net
import xbmc
import random

import gdata.youtube
import gdata.youtube.service


class VideoStreamError(Exception):
	def __init__(self, value):
		self.value = value
	def __str__(self):
		return repr(self.value)

class PrivilegeError(Exception):
	def __init__(self):
		self.value = 'Insufficient permissions, operation aborted.'
	def __str__(self):
		return repr(self.value)

class YouTube:
	"""YouTube wrapper"""

	def __init__(self):
	
		# for getting video-streams		
		self.session_pattern = re.compile("&token=([^&]+)")
		self.id_pattern = re.compile('v=(.+)')
		self.embed_fail_pattern = re.compile("status=fail&errorcode=150")
	
		self.base_path = os.getcwd().replace(';','')

		self.per_page = 28
		self.page_number = 1

		self.ordertypes = ['relevance', 'published', 'viewCount', 'rating']
		self.ordertype = 0

		# Create the data subdirectory if it doesn't exist.
		data_dir = os.path.join(self.base_path, 'data')
		if not os.path.exists(data_dir):
			os.mkdir(data_dir)

		# Callback related stuff
		self.report_hook = None
		self.report_udata = None
		self.filter_hook = None
		self.filter_udata = None
		
		self.yt_service = gdata.youtube.service.YouTubeService()
		
		self.gdata_url = 'http://gdata.youtube.com'
		self.youtube_url = 'http://www.youtube.com'
		self.initUrls()

	def setHighQuality(self):
		self.stream_url = self.youtube_url + '/get_video.php?video_id=%s&t=%s&fmt=18'

	def setRegularQuality(self):
		self.stream_url = self.youtube_url + '/get_video.php?video_id=%s&t=%s'
		
	def initUrls(self):
	
		self.user_url = self.gdata_url + '/feeds/api/users/%s/uploads?max-results=' + str(self.per_page) + "&orderby=" + str(self.ordertypes[self.ordertype]) + "&start-index=" + str(self.per_page*self.page_number-self.per_page+1)
		self.search_url = self.gdata_url + '/feeds/api/videos?vq=%s&max-results=' + str(self.per_page) + "&orderby=" + str(self.ordertypes[self.ordertype]) + "&start-index=" + str(self.per_page*self.page_number-self.per_page+1)
		self.feed_url = self.gdata_url + '/feeds/api/standardfeeds/%s'  + "&max-results=" + str(self.per_page) + "&orderby=" + str(self.ordertypes[self.ordertype]) + "&start-index=" + str(self.per_page*self.page_number-self.per_page+1)
		self.category_url = self.gdata_url + '/feeds/api/videos/-/%s?max-results=' + str(self.per_page) + "&orderby=" + str(self.ordertypes[self.ordertype]) + "&start-index=" + str(self.per_page*self.page_number-self.per_page+1)
		self.favorites_url = self.gdata_url + '/feeds/api/users/%s/favorites?max-results=' + str(self.per_page) + "&orderby=" + str(self.ordertypes[self.ordertype]) + "&start-index=" + str(self.per_page*self.page_number-self.per_page+1)
		self.stream_url = self.youtube_url + '/get_video?video_id=%s&t=%s'
		self.playlist_feed_url = self.gdata_url + '/feeds/api/users/%s/playlists?v=2&max-results=' + str(self.per_page) + "&orderby=" + str(self.ordertypes[self.ordertype]) + "&start-index=" + str(self.per_page*self.page_number-self.per_page+1)
		self.playlist_url = self.gdata_url + '/feeds/api/playlists/%s?v=2&max-results=' + str(self.per_page) + "&orderby=" + str(self.ordertypes[self.ordertype]) + "&start-index=" + str(self.per_page*self.page_number-self.per_page+1)
		
	def get_categories(self):
		
		# categories listed at url below
	
		data = self.retrieve("http://gdata.youtube.com/schemas/2007/categories.cat")

		categories = []
		
		tree = elementtree.ElementTree.XML(data)	
		
		for node in tree.findall('{http://www.w3.org/2005/Atom}category'):
		
			tstbrowse = node.findall('{http://gdata.youtube.com/schemas/2007}browsable')
			label = node.items()
			
			if (len(tstbrowse)>0):
				categories.append(label[2][1])
		
		
		return categories
				

	def get_author(self, pos):
	
		return self.author[pos]
												

	def get_user_favorites(self, user):
	
		self.initUrls()
		
		url = self.favorites_url % user
		
		return self.get_feed_data(url)
		
	def get_user_videos(self, user):
		"""Assemble user videos url and return a (desc, id) list."""

		self.initUrls()
		
		url = self.user_url % user
		
		return self.get_feed_data(url)
			
	def get_user_playlists(self, user):
		"""Assemble user videos url and return a (desc, id) list."""

		self.initUrls()
		
		url = self.playlist_feed_url % user
		
		return self.get_playlist(url)
		
	def reset_page(self):

		self.page_number = 1
		
	def get_page(self):
	
		return str(self.page_number)
		
	def do_next(self):
	
		self.page_number += 1
		return True
	
	def do_prev(self):
		
		self.page_number -= 1
		if (self.page_number <= 0):
			self.page_number = 1
			return False
		else:
			return True	
		
	def _prepareList(self, feed, list):
	
		self.thumbnail = [] #
		self.author = [] #
		self.views = [] 
		self.rating = [] #
		self.duration = [] #
		self.related = [] #
		self.responses = [] #
		
		for entry in feed.entry:
					
			try:
			
				if (entry.media is not None  and len(entry.media.thumbnail)>0):
					self.thumbnail.append(entry.media.thumbnail[0].url)

				if (entry.media is not None and entry.media.duration is not None):
					self.duration.append(entry.media.duration.seconds)

				if (entry.rating is not None): 
					self.rating.append(entry.rating.average)

				if (entry.author is not None and len(entry.author)>0):
					self.author.append(entry.author[0].name.text)

				for link in entry.link:
					if "video.responses" in link.rel:				
						self.responses.append(link.href)
					if "video.related" in link.rel:
						self.related.append(link.href)			

				if (entry.statistics is not None):
					self.views.append(entry.statistics.view_count)					

				list.append((entry.media.title.text, entry.media.player.url))
			except:
				pass			
		
	def _preparePlaylist(self, feed, list):
	
		
		for entry in feed.entry:
												
			list.append((entry.title.text, entry.id.text))
			
	
		
	# returns a list of playlists by user
	def get_playlist(self, url):
			
		self.initUrls()
		
		feed = self.yt_service.GetYouTubePlaylistFeed(url)		

		list = []

		self._preparePlaylist(feed, list)
		
		return list
		
	# gets a list of videos by playlist id #
	def get_playlist_feed(self, myid):
	
		self.initUrls()
		
		"""Assemble feed url and return a (desc, id) list."""				
		
		opt = {}
		
		url = (self.playlist_url % (myid))
		
		list = self.get_feed_data(url)
				
		return list
		
	def get_feed(self, feed):
	
		self.initUrls()
		
		"""Assemble feed url and return a (desc, id) list."""				
		
		opt = {}
		
		url = (self.feed_url % (feed))		
		
		list = self.get_feed_data(url)
				
		return list
		
	def get_feed_data(self, url):
			
		self.initUrls()
		
		feed = self.yt_service.GetYouTubeVideoFeed(url)		

		list = []

		self._prepareList(feed, list)
		
		return list
		
	def get_related(self, pos):
	
		self.initUrls()
	
		url = self.related[pos]
		
		return self.get_feed_data(url)


	def get_responses(self, pos):
	
		self.initUrls()
	
		url = self.responses[pos]
		
		return self.get_feed_data(url)
		
		
	def get_category(self, categorystring):
	
		self.initUrls()
		
		url = (self.category_url % categorystring.replace(' ', ''))
		
		return self.get_feed_data(url)
		

	def search(self, term):
		"""Assemble a search query and return a (desc, id) list."""

		self.initUrls()

		friendly_term = escape(term).replace(' ', '+')		
		url = self.search_url % friendly_term
		
		return self.get_feed_data(url)


	def call_method(self, method, param):
		"""Call a REST method and return the result as an ElementTree."""
		url = self.api_url % (method, param)

		
		data = self.retrieve(url)
		return elementtree.ElementTree.XML(data)
	

	def set_filter_hook(self, hook, udata=None):
		"""Set the content filter handler."""

		self.filter_hook = hook
		self.filter_udata = udata
		
	def get_video_url(self, id, confirmed=False):
		"""Return a proper playback url for some YouTube id."""

		print "Getting video url. Id: " + id
		
		ret = None
		video_id='error'		
		match = self.id_pattern.search(id)
		if match != None and len(match.groups()) >= 1:
			video_id = match.group(1)
			
		print "Video id: " + video_id
		
		#url = id
		url = "http://www.youtube.com/get_video_info?video_id=" + video_id
		data = self.retrieve(url)
		
		if data is not None:
			match2 = self.embed_fail_pattern.search(data)
			if match2 is not None:
				
				data2 = self.retrieve("http://www.youtube.com/watch?v=" + str(video_id))
				more_pattern_match = re.compile('"t": "([^"]+)')
				match2 = more_pattern_match.search(data2)
				
				if match2 is not None:
					session = match2.group(1)
					print "Session: " + str(session)
					ret = self.stream_url % (video_id, session)

			else:
				
				match = self.session_pattern.search(data)
			
				if match is not None and len(match.groups()) >= 1:

					session = match.group(1)
					ret = self.stream_url % (video_id, session)
		
		if ret is None:
			print "Ret is none!"
			# Failed to find the video stream url, better complain.
			raise VideoStreamError(id)	
		else:
			print "Return: " + str(ret)
		
		return ret
	def set_report_hook(self, func, udata=None):
		"""Set the download progress report handler."""

		self.report_hook = func
		self.report_udata = udata

	def retrieve(self, url, data=None, headers={}, timeout=30, sethooks=True):
		"""Downloads an url."""
		
		print "Retrieving url: " + url
		if (sethooks):
			return xbmcutils.net.retrieve (url, data, headers,
			                               self.report_hook,
			                               self.report_udata)
		else:
			return xbmcutils.net.retrieve (url, data)			
			

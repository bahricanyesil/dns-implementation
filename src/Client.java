
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package question;

/**
 * Defines <code>Client</code> class to implement clients with 
 * their ipAddresses, roots and cacheLists. 
 * @author Bahrican Yesil
 *
 */
public class Client {
	
	/**
	 * Defines private <code>CachedContent</code> class to implement cacheLists' elements
	 * with their domainNames, ipAddresses and hitNos.
 	 * @author Bahrican Yesil
	 *
	 */
	private class CachedContent {
		
		/**
		 * The recorded domain name of the element of the cacheList.
		 */
		private String domainName;
		
		/**
		 * The corresponding ip address of the element of the cacheList.
		 */
		private String ipAddress;
		
		/**
		 * The number of uses of the element from the cache.
		 */
		private int hitNo;
		
		/**
		 * Creates a cached content, the element of the cache, 
		 * with <code>domainName</code> and <code>ipAddress</code>.
		 * @param domainName	A string representing the domain name of the cached content.
		 * @param ipAddress		A string representing the corresponding ip address for the cached content.
		 */
		private CachedContent(String domainName, String ipAddress) {
			this.domainName = domainName;
			this.ipAddress = ipAddress;
			// The hitNo of the element is initially set to 0.
			this.hitNo = 0;
		}
	}
	
	/**
	 * The DnsTree which represents the main DNS which client is connected.
	 * This field is for accessing the tree structure.
	 */
	private DnsTree root;
	
	/**
	 * Ip address of the client in the DNS structure.
	 */
	private final String ipAddress;
	
	/**
	 * The cache of a client is presented with a finite-size array.
	 */
	private CachedContent[] cacheList;
	
	/**
	 * Creates a client with its ipAddress and the
	 * main DNS structure that client is connected to. 
	 * @param ipAddress	A string representing the ip address of the client.
	 * @param root		A DnsTree object representing the main DNS which client is connected.
	 */
	public Client(String ipAddress, DnsTree root) {
		this.ipAddress = ipAddress;
		this.root = root;
		// Initializes the cache list with the size of 10 default. 
		this.cacheList = new CachedContent[10];
	}
	
	/**
	 * Firstly, looks for the domain name in the cache. If can be found,
	 * returns the corresponding ip address. Otherwise, a request is sent to the DNS.
	 * @param domainName	A string representing the domain name that is requested.
	 * @return				A string representing the IP address of the requested domain name.
	 */
	public String sendRequest(String domainName) {
		int index = 0;
		// Loop to look for whether domain name is in the cache or not.
		while(index<cacheList.length) {
			if(cacheList[index] != null) {
				/* If it is available in the cache, it directly returns 
				 * the corresponding IP address in the cache record.
				 * The corresponding hitNo is incremented by 1. */
				if(cacheList[index].domainName == domainName) {
					cacheList[index].hitNo++;
					return cacheList[index].ipAddress;
				}
			} 
			index++;
		}
		/* if it is not available in the cache, 
		 * then a request is sent to the DNS via
		 * queryDomain method of the tree. */
		String ip = root.queryDomain(domainName);
		// if there isn't any ip address corresponding to the domain name, returns null.
		if(ip==null) {
			return null;
		}
		// The ip address is added to the cache by addToCache method.
		addToCache(domainName, ip);
		return ip;
	}
	
	/**
	 * Adds the ip address that is obtained through 
	 * sendRequest method to the cache.
	 * @param domainName	A string representing the domain name of the cache content.
	 * @param ipAddress		A string representing the ip address of the cache content.
	 */
	private void addToCache(String domainName, String ipAddress) {
		int index = 0;
		int minHitIndex = -1; // The index of the element that has minimum hitNo.
		int minHit = -1; 
		/* Loop to traverse the cacheList to find firstly if there is an empty element.
		 * if there isn't any, search for the element that has minimum hitNo. */
		while(index<cacheList.length) {
			// if there is an empty element, new content is added.
			if(cacheList[index] == null) {
				cacheList[index] = new CachedContent(domainName, ipAddress);
				return;
			}
			// These if-else statements for finding the element with minimum hitNo.
			if(minHit==-1) {
				minHit = cacheList[index].hitNo;
				minHitIndex = index;
			} else {
				if(cacheList[index].hitNo<minHit) {
					minHit = cacheList[index].hitNo;
					minHitIndex = index;
				}
			}
			index++;
		}
		// The record with the minimum hitNo is removed to open a room for a new record.
		if(minHitIndex != -1) {
			cacheList[minHitIndex] = new CachedContent(domainName, ipAddress);
		}
	}
	
}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE


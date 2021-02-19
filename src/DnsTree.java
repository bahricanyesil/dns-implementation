
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package question;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Defines <code>DnsTree</code> class to implement DNS trees with their main DnsNode, root.
 * Represents the main DNS structure.
 * @author Bahrican Yesil
 *
 */
public class DnsTree {
	
	/**
	 * A DnsNode representing the root of the tree.
	 */
	private DnsNode root;
	
	/**
	 * Creates a DnsTree and initializes the root node with the parameter.
	 * @param root	A DnsNode representing the root of the tree.
	 */
	public DnsTree() {
		this.root = new DnsNode();
	}
	
	/**
	 * inserts a new record for a given domain name.
	 * if a corresponding node is not available in the tree, creates it and
	 * sets its validDomain field as true.
	 * Otherwise, updates the IP address list of the node. 
	 * @param domainName	A string representing the domain name of the node that will be added.
	 * @param ipAddress		A string representing the ip address of the node that will be added.
	 */
	public void insertRecord(String domainName, String ipAddress) {
		String dom = domainName; // A temporary string to store the domain name to change it.
		String last = "";		 // A domain name of the created nodes and intermediate nodes.
		DnsNode tempRoot = this.root;	// A DnsNode to track the level of the tree by considering the root.
		// A loop to process the domain name gradually.
		while(dom.contains(".")) {
			last = dom.substring(dom.lastIndexOf(".") + 1);
			dom = dom.substring(0, dom.lastIndexOf("."));
			// if the current root has a child with the same name, sets the current root to that child. 
			if(tempRoot.getChildNodeList().containsKey(last)) {
				tempRoot = tempRoot.getChildNodeList().get(last);
			}
			/* if the current root hasn't any child with the same name, creates a new node and
			 * puts it into the parent's child list. */
			else {
				DnsNode add = new DnsNode();
				tempRoot.getChildNodeList().put(last, add);
				tempRoot = add;
			}
		}
		// Looks if the node which is wanted to be add is already in the tree.
		if(tempRoot.getChildNodeList().containsKey(dom)) {
			if(tempRoot.getChildNodeList().get(dom).getIpAddresses().contains(ipAddress)) {
				
			} else {
				tempRoot.getChildNodeList().get(dom).getIpAddresses().add(ipAddress);
				tempRoot.getChildNodeList().get(dom).getIps().add(ipAddress);
				tempRoot.getChildNodeList().get(dom).setValidDomain(true);
			}
		} else {
			// if it isn't in the tree, adds it to the tree and sets the validDomain as true.
			DnsNode add = new DnsNode();
			tempRoot.getChildNodeList().put(dom, add);
			tempRoot.getChildNodeList().get(dom).setValidDomain(true);
			tempRoot.getChildNodeList().get(dom).getIpAddresses().add(ipAddress);
			tempRoot.getChildNodeList().get(dom).getIps().add(ipAddress);
		}
	}
	
	/**
	 * Removes the node with the given domainName from the tree. 
	 * if successfully removed, returns true, otherwise, returns false. 
	 * @param domainName	A string indicating the node which is wanted to be removed.
	 * @return				A boolean representing whether the domain name is successfully removed.
	 */
	public boolean removeRecord(String domainName) {
		String dom = domainName; // A temporary string to store the domain name to change it.
		String last = "";		 // A domain name of the leaf nodes and intermediate nodes.
		DnsNode tempRoot = this.root;	// A DnsNode to track the level of the tree by considering the root.
		// A loop to process the domain name gradually.
		while(dom.contains(".")) {
			last = dom.substring(dom.lastIndexOf(".") + 1);
			dom = dom.substring(0, dom.lastIndexOf("."));
			if(tempRoot.getChildNodeList().containsKey(last)) {
				tempRoot = tempRoot.getChildNodeList().get(last);
			} else {
				return false;
			}
		}
		// Looks if the node which is wanted to be removed is in the tree.
		if(tempRoot.getChildNodeList().containsKey(dom)) {
			// if it is in the tree, clear its ipAddresess set and sets it as not valid domain.
			tempRoot.getChildNodeList().get(dom).getIpAddresses().clear();
			tempRoot.getChildNodeList().get(dom).getIps().clear();
			tempRoot.getChildNodeList().get(dom).setValidDomain(false);
			// if it isn't intermediate node, removes it from the parent's child list.
			if(tempRoot.getChildNodeList().get(dom).getChildNodeList().size()==0) {
				tempRoot.getChildNodeList().remove(dom);
			}
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Removes the given ipAddress of a DNS node with the given domainName. 
	 * if successfully removed, return true, otherwise, return false. 
	 * @param domainName	A string indicating the node which is wanted to be removed.
	 * @param ipAddress		A string indicating the specific IP address that is wanted to be removed.
	 * @return				A boolean representing whether the domain name with the specific IP is successfully removed.
	 */
	public boolean removeRecord(String domainName, String ipAddress) {
		String dom = domainName; // A temporary string to store the domain name to change it.
		String last = "";		 // A domain name of the created nodes and intermediate nodes.
		DnsNode tempRoot = this.root;	// A DnsNode to track the level of the tree by considering the root.
		// A loop to process the domain name gradually.
		while(dom.contains(".")) {
			last = dom.substring(dom.lastIndexOf(".") + 1);
			dom = dom.substring(0, dom.lastIndexOf("."));
			if(tempRoot.getChildNodeList().containsKey(last)) {
				tempRoot = tempRoot.getChildNodeList().get(last);
			} else {
				return false;
			}
		}
		// Looks if the node which is wanted to be removed is in the tree.
		if(tempRoot.getChildNodeList().containsKey(dom)) {
			// Looks if the node has the specific ip address.
			if(tempRoot.getChildNodeList().get(dom).getIpAddresses().contains(ipAddress)) {
				tempRoot.getChildNodeList().get(dom).getIpAddresses().remove(ipAddress);
				tempRoot.getChildNodeList().get(dom).getIps().remove(ipAddress);
				// After the operation, if the node doesn't have any ip address, sets it as invalid domain.
				if(tempRoot.getChildNodeList().get(dom).getIpAddresses().size() == 0) {
					tempRoot.getChildNodeList().get(dom).setValidDomain(false);
					// if that node doesn't have any child, removes it from the parent's child list and also tree.
					if(tempRoot.getChildNodeList().get(dom).getChildNodeList().size() == 0) {
						tempRoot.getChildNodeList().remove(dom);
					}
				}
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Queries a domain name within the DNS, and returns the next IP address of the domainName, 
	 * following the Round Robin mechanism. 
	 * if there is no such domain name available in the tree, returns null. 
	 * @param domainName	A string indicating the node whose ip address will be returned.
	 * @return				A string representing the next ip address of the domainName, according to RR Algorithm.
	 */
	public String queryDomain(String domainName) {
		String dom = domainName; // A temporary string to store the domain name to change it.
		String last = "";		 // A domain name of the created nodes and intermediate nodes.
		DnsNode tempRoot = this.root;	// A DnsNode to track the level of the tree by considering the root.
		// A loop to process the domain name gradually.
		while(dom.contains(".")) {
			last = dom.substring(dom.lastIndexOf(".") + 1);
			dom = dom.substring(0, dom.lastIndexOf("."));
			if(tempRoot.getChildNodeList().containsKey(last)) {
				tempRoot = tempRoot.getChildNodeList().get(last);
			} else {
				return null;
			}
		}
		// Looks if the node with that domain name is in the tree.
		if(tempRoot.getChildNodeList().containsKey(dom)) {
			// if it exists but doesn't have any ip address, returns null.
			if(tempRoot.getChildNodeList().get(dom).getIpAddresses().isEmpty()) {
				return null;
			} else {
				String ip = tempRoot.getChildNodeList().get(dom).getIps().poll();
				tempRoot.getChildNodeList().get(dom).getIps().add(ip);
				return ip;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * Returns all the valid domain names in the DNS mechanism with at least 1 IP address. 
	 * @return Map whose keys represent the valid domain names, and value is the set of IP addresses of a particular key (domain name).
	 */
	public Map<String, Set<String>> getAllRecords() {
		Map<String, Set<String>> tempMap = new HashMap<String, Set<String>>();
		Iterator<String> it = root.getChildNodeList().keySet().iterator();
		// This while loop traverses the all children of the root of the tree.
		while(it.hasNext()) {
			String name = it.next();
			if(root.getChildNodeList().get(name).getValidDomain()) {
				tempMap.put(name, root.getChildNodeList().get(name).getIpAddresses());
			}
			// To traverses the all children of the children of the root, calls the getAll() method.
			getAll(name, root.getChildNodeList().get(name), tempMap);
		}
		return tempMap;
	}
	
	/**
	 * By taking the first level of the tree, traverses the all tree.
	 * if a node has valid domain name, puts it to the map.
	 * @param name		String representing the (full) domain name of the node.
	 * @param root		DnsNode representing the current child that we are processing.
	 * @param tempMap	Map representing the return type of getAllRecords() method.
	 */
	private void getAll(String name, DnsNode root, Map<String, Set<String>> tempMap){
		Iterator<String> it = root.getChildNodeList().keySet().iterator();
		// This while loop traverses the all children of the current node givena as parameter.
		while(it.hasNext()) {
			String name2 = it.next();
			if(root.getChildNodeList().get(name2).getValidDomain()) {
				tempMap.put(name2 + "." + name, root.getChildNodeList().get(name2).getIpAddresses());
			}
			// To traverses the all children of the current node, calls the getAll() method.
			getAll(name2 + "." + name, root.getChildNodeList().get(name2), tempMap);
		}
	}
	
	/**
	 * Gets the root of the tree, the getter method of the root.
	 * @return	DnsNode representing the root of the tree
	 */
	public DnsNode getRoot() {
		return this.root;
	}
	
}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE


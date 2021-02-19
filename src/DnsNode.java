
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package question;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Defines <code>DnsNode</code> class to implement DNS nodes with 
 * their childNodeLists, ipAddresses, ips and validDomain fields. 
 * @author Bahrican Yesil
 *
 */
public class DnsNode {
	
	/**
	 * The map contains the children of the DnsNode.
	 * Keys, the name of the child.
	 * Values, itself of the child.
	 */
	private Map<String, DnsNode> childNodeList;
	
	/**
	 * A boolean representing whether the node has at least 1 ip address or not.
	 */
	private boolean validDomain;
	
	/**
	 * A set contains the all IP addresses that the DnsNode have.
	 */
	private Set<String> ipAddresses;
	
	/**
	 * A queue representing the IP addresses that the DnsNode have in a queue format.
	 * A copy of the IP addresses to get the IP addresses with FIFO manner. 
	 */
	private Queue<String> ips;
	
	/**
	 * Creates a DNS node with an empty childNodeList and ipAddresses. 
	 * Besides, the validDomain is initialized as “false”.
	 * Also initializes the ips queue as empty.
	 */
	public DnsNode (){
		this.childNodeList = new HashMap<String, DnsNode>();
		this.ipAddresses = new HashSet<String>();
		this.validDomain = false;
		this.ips = new LinkedList<String>();
	}
	
	/**
	 * Gets the child list of the current node.
	 * @return	Map representing the children of the node with their keys and themselves.
	 */
	public Map<String, DnsNode> getChildNodeList(){
		return this.childNodeList;
	}
	
	/**
	 * Gets the validDomain field of the current node.
	 * @return	Boolean representing whether the node has any IP address or not.
	 */
	public boolean getValidDomain() {
		return this.validDomain;
	}
	
	/**
	 * Gets the IP addresses of the current node.
	 * @return	Set consists of the IP addresses of the node. 
	 */
	public Set<String> getIpAddresses(){
		return this.ipAddresses;
	}
	
	/**
	 * Gets the IP addresses of the current node as a Queue.
	 * @return	Queue consists of the IP addresses of the node. 
	 */
	public Queue<String> getIps(){
		return this.ips;
	}
	
	/**
	 * Sets the validDomain field of the current node to the given parameter.
	 * @param validDomain	Boolean representing whether the node has any IP address or not.
	 */
	public void setValidDomain(boolean validDomain) {
		this.validDomain = validDomain;
	}
}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

